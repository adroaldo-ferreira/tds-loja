package br.dev.hygino.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.hygino.dto.EstatisticaDto;
import br.dev.hygino.dto.EstatisticaSonoDto;
import br.dev.hygino.dto.EstatisticasPesquisaDto;
import br.dev.hygino.dto.RequestPesquisaDto;
import br.dev.hygino.dto.ResponsePesquisaDto;
import br.dev.hygino.repository.PesquisaRepository;
import br.dev.hygino.service.PesquisaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/respostas")
@CrossOrigin("*")
@RequiredArgsConstructor
public class PesquisaController {

	private final PesquisaService service;
	private final PesquisaRepository repository;

	@PostMapping
	public ResponseEntity<ResponsePesquisaDto> salvar(@RequestBody RequestPesquisaDto resposta) {

		return ResponseEntity.status(201).body(service.inserir(resposta));
	}

	@GetMapping
	public ResponseEntity<List<ResponsePesquisaDto>> listar() {

		return ResponseEntity.ok(service.buscarTodas());
	}

	@GetMapping("/estatisticas/jogos")
	public List<EstatisticaDto> contarJogosOnline() {
		return repository.contarJogosOnline();
	}

	@GetMapping("/estatisticas/esportes")
	public List<EstatisticaDto> contarEsportes() {
		return repository.contarEsportes();
	}

	@GetMapping("/estatisticas/viagens")
	public List<EstatisticaDto> contarViagens() {
		return repository.contarViagens();
	}

	@GetMapping("/estatisticas/memes")
	public List<EstatisticaDto> contarMemes() {
		return repository.contarMemes();
	}

	@GetMapping("/estatisticas/calcados")
	public List<EstatisticaDto> contarCalcados() {
		return repository.contarCalcados();
	}

	@GetMapping("/estatisticas/cantores")
	public List<EstatisticaDto> contarCantores() {
		return repository.contarCantores();
	}

	@GetMapping("/estatisticas/jogadores")
	public List<EstatisticaDto> contarJogadores() {
		return repository.contarJogadores();
	}

	@GetMapping("/estatisticas/filmes")
	public List<EstatisticaDto> contarFilmes() {
		return repository.contarFilmes();
	}

	@GetMapping("/estatisticas/materias")
	public List<EstatisticaDto> contarMaterias() {
		return repository.contarMaterias();
	}

	@GetMapping("/estatisticas/lugares")
	public List<EstatisticaDto> contarLugares() {
		return repository.contarLugares();
	}

	@GetMapping("/estatisticas/marcas")
	public List<EstatisticaDto> contarMarcas() {
		return repository.contarMarcas();
	}

	@GetMapping("/estatisticas/horas-sono")
	public List<EstatisticaSonoDto> contarHorasSono() {
		return repository.contarHorasSono();
	}
	
	@GetMapping("/estatisticas")
	public EstatisticasPesquisaDto estatisticas() {

	    return new EstatisticasPesquisaDto(
	        repository.contarJogosOnline(),
	        repository.contarEsportes(),
	        repository.contarViagens(),
	        repository.contarMemes(),
	        repository.contarCalcados(),
	        repository.contarCantores(),
	        repository.contarJogadores(),
	        repository.contarFilmes(),
	        repository.contarMaterias(),
	        repository.contarLugares(),
	        repository.contarMarcas(),
	        repository.contarHorasSono()
	    );
	}
}