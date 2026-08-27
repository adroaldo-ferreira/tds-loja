package br.dev.hygino.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<ResponseProductDto> update(Long id, RequestProductDto dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseEntity<ResponseProductDto> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ResponseEntity<Page<ResponseProductDto>> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
