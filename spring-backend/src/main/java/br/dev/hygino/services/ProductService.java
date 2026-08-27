package br.dev.hygino.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.dev.hygino.dtos.RequestProductDto;
import br.dev.hygino.dtos.ResponseProductDto;
import br.dev.hygino.entities.Category;
import br.dev.hygino.entities.Product;
import br.dev.hygino.entities.Supplier;
import br.dev.hygino.exceptions.DatabaseException;
import br.dev.hygino.exceptions.ResourceNotFoundException;
import br.dev.hygino.repositories.CategoryRepository;
import br.dev.hygino.repositories.ProductRepository;
import br.dev.hygino.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class ProductService implements IService<RequestProductDto, ResponseProductDto> {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public ResponseProductDto insert(RequestProductDto dto) {
        final var category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        final var supplier = supplierRepository.findById(dto.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found!"));

        var product = new Product();

        dtoToEntity(dto, product, category, supplier);

        product = productRepository.save(product);
        return product.toResponseProduct();
    }

    private void dtoToEntity(RequestProductDto dto, Product entity, Category category, Supplier supplier) {
        entity.setName(dto.name());
        entity.setCode(dto.code());
        entity.setDescription(dto.description());
        entity.setCategory(category);
        entity.setSupplier(supplier);
        entity.setSize(dto.size());
        entity.setColor(dto.color());
        entity.setPurchasePrice(dto.purchasePrice());
        entity.setSalesPrice(dto.salesPrice());
        entity.setMinimalStock(dto.minimalStock());
        entity.setStock(dto.stock());
        entity.setActive(dto.active());
    }

    @Override
    @Transactional
    public ResponseProductDto update(long id, RequestProductDto dto) {
        final var category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));

        final var supplier = supplierRepository.findById(dto.supplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found!"));

        try {
            var product = productRepository.getReferenceById(id);

            dtoToEntity(dto, product, category, supplier);

            product = productRepository.save(product);
            return product.toResponseProduct();
        } catch (Exception e) {
            throw new DatabaseException("Can not save this product!");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseProductDto findById(long id) {
        return productRepository.findById(id)
                .map(Product::toResponseProduct)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ResponseProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(Product::toResponseProduct);
    }

    @Override
    public void remove(long id) {
        productRepository.deleteById(id);
    }
}
