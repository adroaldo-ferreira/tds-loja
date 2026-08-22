package br.dev.hygino.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestPesquisaDto(

		@NotBlank String jogoOnlineFavorito,

		@NotBlank String esporteFavorito,

		@NotBlank String viagemDosSonhos,

		@NotBlank String memeFavorito,

		@NotBlank String calcadoFavorito,

		@NotBlank String cantorFavorito,

		@NotBlank String jogadorFavorito,

		@NotBlank String filmeFavorito,

		@NotBlank String materiaPreferida,

		@NotBlank String lugarFavorito,

		@NotBlank String marcaPreferida,

		@NotNull Double horasSono

) {
}