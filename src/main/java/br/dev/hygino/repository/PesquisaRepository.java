package br.dev.hygino.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.dev.hygino.dto.EstatisticaDto;
import br.dev.hygino.dto.EstatisticaSonoDto;
import br.dev.hygino.entity.Pesquisa;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.jogoOnlineFavorito,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.jogoOnlineFavorito
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarJogosOnline();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.esporteFavorito,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.esporteFavorito
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarEsportes();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.viagemDosSonhos,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.viagemDosSonhos
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarViagens();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.memeFavorito,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.memeFavorito
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarMemes();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.calcadoFavorito,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.calcadoFavorito
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarCalcados();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.cantorFavorito,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.cantorFavorito
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarCantores();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.jogadorFavorito,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.jogadorFavorito
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarJogadores();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.filmeFavorito,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.filmeFavorito
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarFilmes();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.materiaPreferida,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.materiaPreferida
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarMaterias();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.lugarFavorito,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.lugarFavorito
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarLugares();

    @Query("""
        SELECT new br.dev.hygino.dto.EstatisticaDto(
            p.marcaPreferida,
            COUNT(p)
        )
        FROM Pesquisa p
        GROUP BY p.marcaPreferida
        ORDER BY COUNT(p) DESC
    """)
    List<EstatisticaDto> contarMarcas();
    
    @Query("""
    	    SELECT new br.dev.hygino.dto.EstatisticaSonoDto(
    	        p.horasSono,
    	        COUNT(p)
    	    )
    	    FROM Pesquisa p
    	    GROUP BY p.horasSono
    	    ORDER BY p.horasSono
    	""")
    	List<EstatisticaSonoDto> contarHorasSono();
}