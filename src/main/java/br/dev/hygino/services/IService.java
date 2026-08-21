package br.dev.hygino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<I, O> {

    O insert(I dto);

    O update(long id, I dto);

    O findById(long id);

    Page<O> findAll(Pageable pageable);

    void remove(long id);
}
