package br.dev.hygino.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.dtos.ResponseProductDto;
import br.dev.hygino.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController implements IController<RequestProductDto, ResponseProductDto> {
    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    @PostMapping
    public ResponseEntity<ResponseProductDto> insert(@RequestBody @Valid RequestProductDto dto) {
        logger.info("Dados inseridos: " + dto);
        return ResponseEntity.status(201).body(productService.insert(dto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDto> update(@PathVariable Long id, @RequestBody @Valid RequestProductDto dto) {
        logger.info("Update product with Id: " + id + " " + dto);
        return ResponseEntity.status(200).body(productService.update(id, dto));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(productService.findById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ResponseProductDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(200).body(productService.findAll(pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
