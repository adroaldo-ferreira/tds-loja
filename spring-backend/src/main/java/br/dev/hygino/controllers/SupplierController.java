package br.dev.hygino.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.dev.hygino.dtos.RequestSupplierDto;
import br.dev.hygino.dtos.ResponseSupplierDto;
import br.dev.hygino.services.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/supplier")
@RequiredArgsConstructor
public class SupplierController implements IController<RequestSupplierDto, ResponseSupplierDto> {
    private final SupplierService supplierService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseSupplierDto> insert(@RequestBody @Valid RequestSupplierDto dto) {
        ResponseSupplierDto response = supplierService.insert(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSupplierDto> update(
            @PathVariable Long id,
            @RequestBody @Valid RequestSupplierDto dto) {
        return ResponseEntity.status(200).body(supplierService.update(id, dto));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSupplierDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(supplierService.findById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ResponseSupplierDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(200).body(supplierService.findAll(pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        supplierService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
