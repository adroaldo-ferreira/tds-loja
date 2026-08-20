-- =====================================================================
--  BANCO DE DADOS - NEUFMODAS (Sistema de Controle de Estoque)
--  Compatível com MySQL 5.7+ / 8.0+
--  Módulos: Login, Dashboard, Inventário, Funcionários, Fornecedores, Alertas
-- =====================================================================

CREATE DATABASE IF NOT EXISTS neufmodas
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE neufmodas;

-- ---------------------------------------------------------------------
-- 1. USUÁRIOS (tela de Login)
-- ---------------------------------------------------------------------
CREATE TABLE usuarios (
    id_usuario      INT AUTO_INCREMENT PRIMARY KEY,
    nome_usuario    VARCHAR(50)  NOT NULL UNIQUE,
    senha_hash      VARCHAR(255) NOT NULL,        -- nunca salvar senha em texto puro
    nome_completo   VARCHAR(120) NOT NULL,
    nivel_acesso    ENUM('ADMIN', 'GERENTE', 'OPERADOR') NOT NULL DEFAULT 'OPERADOR',
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    ultimo_login    DATETIME     NULL,
    criado_em       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 2. FUNCIONÁRIOS
-- ---------------------------------------------------------------------
CREATE TABLE funcionarios (
    id_funcionario  INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(120) NOT NULL,
    cpf             VARCHAR(14)  NOT NULL UNIQUE,
    cargo           VARCHAR(60)  NULL,
    telefone        VARCHAR(20)  NULL,
    email           VARCHAR(120) NULL,
    data_admissao   DATE         NULL,
    salario         DECIMAL(10,2) NULL,
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    id_usuario      INT          NULL,             -- vínculo opcional com o login do sistema
    criado_em       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_funcionario_usuario
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 3. FORNECEDORES
-- ---------------------------------------------------------------------
CREATE TABLE fornecedores (
    id_fornecedor   INT AUTO_INCREMENT PRIMARY KEY,
    nome_empresa    VARCHAR(150) NOT NULL,
    cnpj            VARCHAR(18)  NULL UNIQUE,
    nome_contato    VARCHAR(120) NULL,
    telefone        VARCHAR(20)  NULL,
    email           VARCHAR(120) NULL,
    endereco        VARCHAR(200) NULL,
    cidade          VARCHAR(80)  NULL,
    estado          CHAR(2)      NULL,
    ativo           TINYINT(1)   NOT NULL DEFAULT 1,
    criado_em       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 4. CATEGORIAS (ex: Camisetas, Calças, Vestidos, Acessórios...)
-- ---------------------------------------------------------------------
CREATE TABLE categorias (
    id_categoria    INT AUTO_INCREMENT PRIMARY KEY,
    nome            VARCHAR(80) NOT NULL UNIQUE,
    descricao       VARCHAR(200) NULL
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- 5. PRODUTOS (Inventário)
-- ---------------------------------------------------------------------
CREATE TABLE produtos (
    id_produto        INT AUTO_INCREMENT PRIMARY KEY,
    sku               VARCHAR(40)  NOT NULL UNIQUE,   -- código interno/código de barras
    nome              VARCHAR(150) NOT NULL,
    descricao         VARCHAR(255) NULL,
    id_categoria      INT NULL,
    id_fornecedor     INT NULL,
    tamanho           VARCHAR(10)  NULL,               -- PP, P, M, G, GG, 38, 40...
    cor               VARCHAR(40)  NULL,
    preco_custo       DECIMAL(10,2) NOT NULL DEFAULT 0,
    preco_venda       DECIMAL(10,2) NOT NULL DEFAULT 0,
    quantidade_estoque INT NOT NULL DEFAULT 0,
    estoque_minimo    INT NOT NULL DEFAULT 5,          -- gatilho para alerta de estoque baixo
    ativo             TINYINT(1) NOT NULL DEFAULT 1,
    criado_em         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_produto_fornecedor
        FOREIGN KEY (id_fornecedor) REFERENCES fornecedores(id_fornecedor)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_produtos_nome ON produtos(nome);

-- ---------------------------------------------------------------------
-- 6. MOVIMENTAÇÕES DE ESTOQUE (entradas/saídas -> alimenta o Dashboard)
-- ---------------------------------------------------------------------
CREATE TABLE movimentacoes_estoque (
    id_movimentacao   INT AUTO_INCREMENT PRIMARY KEY,
    id_produto        INT NOT NULL,
    tipo               ENUM('ENTRADA', 'SAIDA', 'AJUSTE') NOT NULL,
    quantidade         INT NOT NULL,
    motivo             VARCHAR(150) NULL,          -- ex: 'Compra', 'Venda', 'Perda', 'Inventário'
    id_funcionario     INT NULL,
    data_movimentacao  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mov_produto
        FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_mov_funcionario
        FOREIGN KEY (id_funcionario) REFERENCES funcionarios(id_funcionario)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE INDEX idx_mov_data ON movimentacoes_estoque(data_movimentacao);

-- ---------------------------------------------------------------------
-- 7. ALERTAS (estoque baixo, produtos parados, etc.)
-- ---------------------------------------------------------------------
CREATE TABLE alertas (
    id_alerta       INT AUTO_INCREMENT PRIMARY KEY,
    id_produto      INT NULL,
    tipo            ENUM('ESTOQUE_BAIXO', 'ESTOQUE_ZERADO', 'OUTRO') NOT NULL DEFAULT 'ESTOQUE_BAIXO',
    mensagem        VARCHAR(255) NOT NULL,
    lida            TINYINT(1) NOT NULL DEFAULT 0,
    criado_em       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alerta_produto
        FOREIGN KEY (id_produto) REFERENCES produtos(id_produto)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =====================================================================
--  TRIGGERS: mantêm quantidade_estoque sincronizada e geram alertas
-- =====================================================================
DELIMITER $$

-- Ao inserir uma movimentação, atualiza o estoque do produto
CREATE TRIGGER trg_mov_after_insert
AFTER INSERT ON movimentacoes_estoque
FOR EACH ROW
BEGIN
    IF NEW.tipo = 'ENTRADA' THEN
        UPDATE produtos
           SET quantidade_estoque = quantidade_estoque + NEW.quantidade
         WHERE id_produto = NEW.id_produto;
    ELSEIF NEW.tipo = 'SAIDA' THEN
        UPDATE produtos
           SET quantidade_estoque = quantidade_estoque - NEW.quantidade
         WHERE id_produto = NEW.id_produto;
    ELSE -- AJUSTE: quantidade define o novo valor absoluto
        UPDATE produtos
           SET quantidade_estoque = NEW.quantidade
         WHERE id_produto = NEW.id_produto;
    END IF;
END$$

-- Sempre que o estoque de um produto for atualizado, verifica se deve gerar alerta
CREATE TRIGGER trg_produto_after_update
AFTER UPDATE ON produtos
FOR EACH ROW
BEGIN
    IF NEW.quantidade_estoque <= 0 THEN
        INSERT INTO alertas (id_produto, tipo, mensagem)
        VALUES (NEW.id_produto, 'ESTOQUE_ZERADO',
                CONCAT('Produto "', NEW.nome, '" está sem estoque.'));
    ELSEIF NEW.quantidade_estoque <= NEW.estoque_minimo
       AND OLD.quantidade_estoque > NEW.estoque_minimo THEN
        INSERT INTO alertas (id_produto, tipo, mensagem)
        VALUES (NEW.id_produto, 'ESTOQUE_BAIXO',
                CONCAT('Produto "', NEW.nome, '" está com estoque baixo (',
                       NEW.quantidade_estoque, ' unid.).'));
    END IF;
END$$

DELIMITER ;

-- =====================================================================
--  VIEWS de apoio para o Dashboard
-- =====================================================================

-- Indicadores gerais (cards do Dashboard)
CREATE OR REPLACE VIEW vw_dashboard_indicadores AS
SELECT
    (SELECT COUNT(*) FROM produtos WHERE ativo = 1)                       AS total_produtos,
    (SELECT IFNULL(SUM(quantidade_estoque),0) FROM produtos WHERE ativo=1) AS total_itens_estoque,
    (SELECT IFNULL(SUM(quantidade_estoque * preco_custo),0)
       FROM produtos WHERE ativo = 1)                                     AS valor_total_estoque,
    (SELECT COUNT(*) FROM produtos
       WHERE ativo = 1 AND quantidade_estoque <= estoque_minimo)          AS produtos_estoque_baixo,
    (SELECT COUNT(*) FROM fornecedores WHERE ativo = 1)                   AS total_fornecedores,
    (SELECT COUNT(*) FROM funcionarios WHERE ativo = 1)                   AS total_funcionarios,
    (SELECT COUNT(*) FROM alertas WHERE lida = 0)                         AS alertas_nao_lidos;

-- Produtos com estoque baixo (tela de Alertas / Inventário)
CREATE OR REPLACE VIEW vw_produtos_estoque_baixo AS
SELECT p.id_produto, p.sku, p.nome, p.quantidade_estoque, p.estoque_minimo,
       c.nome AS categoria, f.nome_empresa AS fornecedor
FROM produtos p
LEFT JOIN categorias c   ON c.id_categoria = p.id_categoria
LEFT JOIN fornecedores f ON f.id_fornecedor = p.id_fornecedor
WHERE p.ativo = 1 AND p.quantidade_estoque <= p.estoque_minimo;

-- =====================================================================
--  DADOS INICIAIS
-- =====================================================================

-- Usuário administrador padrão (equivalente ao login fixo hoje no código Delphi)
-- IMPORTANTE: troque 'senha_hash' por um hash real (ex: bcrypt) gerado na aplicação.
INSERT INTO usuarios (nome_usuario, senha_hash, nome_completo, nivel_acesso)
VALUES ('NeufModas', '$2y$10$SUBSTITUA_POR_UM_HASH_REAL', 'Administrador do Sistema', 'ADMIN');

-- Categorias de exemplo
INSERT INTO categorias (nome, descricao) VALUES
('Camisetas', 'Camisetas em geral'),
('Calças', 'Calças jeans, sarja, moletom'),
('Vestidos', 'Vestidos femininos'),
('Acessórios', 'Bolsas, cintos, bijuterias');

-- Fornecedor de exemplo
INSERT INTO fornecedores (nome_empresa, cnpj, nome_contato, telefone, email, cidade, estado)
VALUES ('Confecções Sul Ltda', '12.345.678/0001-90', 'Marcos Silva', '(46) 99999-0000',
        'contato@confeccoessul.com.br', 'Pato Branco', 'PR');

-- Funcionário de exemplo
INSERT INTO funcionarios (nome, cpf, cargo, telefone, email, data_admissao, salario, id_usuario)
VALUES ('Ana Souza', '123.456.789-00', 'Vendedora', '(46) 98888-1111',
        'ana@neufmodas.com', '2024-03-01', 1800.00, NULL);

-- Produto de exemplo
INSERT INTO produtos (sku, nome, descricao, id_categoria, id_fornecedor, tamanho, cor,
                       preco_custo, preco_venda, quantidade_estoque, estoque_minimo)
VALUES ('CAM-M-AZ-001', 'Camiseta Básica', 'Camiseta 100% algodão', 1, 1, 'M', 'Azul',
        18.00, 39.90, 20, 5);

-- Movimentação inicial de entrada (exemplo de uso do trigger)
INSERT INTO movimentacoes_estoque (id_produto, tipo, quantidade, motivo, id_funcionario)
VALUES (1, 'ENTRADA', 10, 'Compra inicial de estoque', 1);
