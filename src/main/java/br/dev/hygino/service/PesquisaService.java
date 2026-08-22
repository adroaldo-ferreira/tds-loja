package br.dev.hygino.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.dev.hygino.dto.RequestPesquisaDto;
import br.dev.hygino.dto.ResponsePesquisaDto;
import br.dev.hygino.entity.Pesquisa;
import br.dev.hygino.repository.PesquisaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class PesquisaService {
	private final PesquisaRepository repository;

	@Transactional
	public ResponsePesquisaDto inserir(@Valid RequestPesquisaDto dto) {
		Pesquisa pesquisa = new Pesquisa();
		dtoToEntity(dto, pesquisa);
		pesquisa = repository.save(pesquisa);
		return pesquisa.toResponse();
	}

	private void dtoToEntity(@Valid RequestPesquisaDto dto, @NotNull Pesquisa pesquisa) {

		pesquisa.setJogoOnlineFavorito(dto.jogoOnlineFavorito());
		pesquisa.setEsporteFavorito(dto.esporteFavorito());
		pesquisa.setViagemDosSonhos(dto.viagemDosSonhos());
		pesquisa.setMemeFavorito(dto.memeFavorito());
		pesquisa.setCalcadoFavorito(dto.calcadoFavorito());
		pesquisa.setCantorFavorito(dto.cantorFavorito());
		pesquisa.setJogadorFavorito(dto.jogadorFavorito());
		pesquisa.setFilmeFavorito(dto.filmeFavorito());
		pesquisa.setMateriaPreferida(dto.materiaPreferida());
		pesquisa.setLugarFavorito(dto.lugarFavorito());
		pesquisa.setMarcaPreferida(dto.marcaPreferida());
		pesquisa.setHorasSono(dto.horasSono());
	}

	@Transactional(readOnly = true)
	public List<ResponsePesquisaDto> buscarTodas() {
		return repository.findAll()
				.stream()
				.map(Pesquisa::toResponse)
				.toList();
	}
}
