package br.dev.hygino.services;

import br.dev.hygino.dtos.RequestCategoryDto;
import br.dev.hygino.dtos.ResponseCategoryDto;
import br.dev.hygino.entities.Category;
import br.dev.hygino.exceptions.DatabaseException;
import br.dev.hygino.exceptions.ResourceNotFoundException;
import br.dev.hygino.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CategoryService implements IService<RequestCategoryDto, ResponseCategoryDto> {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ResponseCategoryDto insert(RequestCategoryDto dto) {
        Category entity = new Category();
        dtoToEntity(dto, entity);
        entity = categoryRepository.save(entity);
        return entity.toResponseCategoryDto();
    }

    private void dtoToEntity(RequestCategoryDto dto, Category entity) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
    }

    @Override
    @Transactional
    public ResponseCategoryDto update(long id, RequestCategoryDto dto) {
        try {
            Category entity = categoryRepository.getReferenceById(id);
            dtoToEntity(dto, entity);
            entity = categoryRepository.save(entity);
            return entity.toResponseCategoryDto();
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Category does not exists!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseCategoryDto findById(long id) {
        return categoryRepository.findById(id)
                .map(Category::toResponseCategoryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category does not exists!"));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseCategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(Category::toResponseCategoryDto);
    }

    @Override
    public void remove(long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Can not delete category with associations!");
        }
    }
}
