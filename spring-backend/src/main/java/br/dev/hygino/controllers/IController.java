package br.dev.hygino.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IController<IN, OUT> {

    ResponseEntity<OUT> insert(IN dto);

    ResponseEntity<OUT> update(Long id, IN dto);

    ResponseEntity<OUT> findById(Long id);

    ResponseEntity<Page<OUT>> findAll(Pageable pageable);

    ResponseEntity<Void> delete(Long id);
}
