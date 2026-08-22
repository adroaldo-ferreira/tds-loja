package br.dev.hygino.dto;

import java.util.List;

public record EstatisticasPesquisaDto(

    List<EstatisticaDto> jogos,

    List<EstatisticaDto> esportes,

    List<EstatisticaDto> viagens,

    List<EstatisticaDto> memes,

    List<EstatisticaDto> calcados,

    List<EstatisticaDto> cantores,

    List<EstatisticaDto> jogadores,

    List<EstatisticaDto> filmes,

    List<EstatisticaDto> materias,

    List<EstatisticaDto> lugares,

    List<EstatisticaDto> marcas,

    List<EstatisticaSonoDto> horasSono
) {
}