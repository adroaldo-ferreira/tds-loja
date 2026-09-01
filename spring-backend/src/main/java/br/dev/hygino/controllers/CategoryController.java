package br.dev.hygino.controllers;

import br.dev.hygino.dtos.RequestCategoryDto;
import br.dev.hygino.dtos.ResponseCategoryDto;
import br.dev.hygino.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController implements IController<RequestCategoryDto, ResponseCategoryDto> {
    private final CategoryService categoryService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseCategoryDto> insert(@RequestBody RequestCategoryDto dto) {
        return ResponseEntity.status(201).body(categoryService.insert(dto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseCategoryDto> update(
            @PathVariable
            Long id,
            @RequestBody
            RequestCategoryDto dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCategoryDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @Override
    @GetMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity<Page<ResponseCategoryDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(categoryService.findAll(pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
