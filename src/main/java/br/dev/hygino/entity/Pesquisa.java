package br.dev.hygino.entity;

import br.dev.hygino.dto.ResponsePesquisaDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_pesquisa")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pesquisa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "jogo_online_favorito")
	private String jogoOnlineFavorito;

	@NotBlank
	@Column(name = "esporte_favorito")
	private String esporteFavorito;

	@NotBlank
	@Column(name = "viagem_dos_sonhos")
	private String viagemDosSonhos;

	@NotBlank
	@Column(name = "meme_favorito")
	private String memeFavorito;

	@NotBlank
	@Column(name = "calcado_favorito")
	private String calcadoFavorito;

	@NotBlank
	@Column(name = "cantor_favorito")
	private String cantorFavorito;

	@NotBlank
	@Column(name = "jogador_favorito")
	private String jogadorFavorito;

	@NotBlank
	@Column(name = "filme_favorito")
	private String filmeFavorito;

	@NotBlank
	@Column(name = "materia_preferida")
	private String materiaPreferida;

	@NotBlank
	@Column(name = "lugar_favorito")
	private String lugarFavorito;

	@NotBlank
	@Column(name = "marca_preferida")
	private String marcaPreferida;

	@NotNull
	@Column(name = "horas_sono")
	private Double horasSono;

	public ResponsePesquisaDto toResponse() {
		return new ResponsePesquisaDto(
				id, 
				jogoOnlineFavorito, 
				esporteFavorito, 
				viagemDosSonhos, 
				memeFavorito,
				calcadoFavorito, 
				cantorFavorito, 
				jogadorFavorito, 
				filmeFavorito, 
				materiaPreferida, 
				lugarFavorito,
				marcaPreferida, 
				horasSono);
	}
}