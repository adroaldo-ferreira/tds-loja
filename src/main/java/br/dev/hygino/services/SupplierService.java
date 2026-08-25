package br.dev.hygino.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.dev.hygino.dtos.RequestSupplierDto;
import br.dev.hygino.dtos.ResponseSupplierDto;
import br.dev.hygino.entities.Supplier;
import br.dev.hygino.exceptions.DatabaseException;
import br.dev.hygino.repositories.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class SupplierService implements IService<RequestSupplierDto, ResponseSupplierDto> {
    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public ResponseSupplierDto insert(RequestSupplierDto dto) {
        Supplier entity = new Supplier();
        dtoToEntity(dto, entity);
        entity = supplierRepository.save(entity);
        return entity.toResponseSupplier();
    }

    private void dtoToEntity(RequestSupplierDto dto, Supplier entity) {
        entity.setName(dto.name());
        entity.setCnpj(dto.cnpj());
        entity.setContactName(dto.contactName());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setEmail(dto.email());
        entity.setAddress(dto.address());
        entity.setCity(dto.city());
        entity.setState(dto.state());
    }

    @Override
    @Transactional
    public ResponseSupplierDto update(long id, RequestSupplierDto dto) {
        try {
            Supplier entity = supplierRepository.getReferenceById(id);
            dtoToEntity(dto, entity);
            entity = supplierRepository.save(entity);
            return entity.toResponseSupplier();
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Supplier does not exists!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseSupplierDto findById(long id) {
        return supplierRepository.findById(id)
                .map(Supplier::toResponseSupplier)
                .orElseThrow(() -> new IllegalArgumentException("Supplier does not exists!"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseSupplierDto> findAll(Pageable pageable) {
        return supplierRepository.findAll(pageable)
                .map(Supplier::toResponseSupplier);
    }

    @Override
    public void remove(long id) {
        if (!supplierRepository.existsById(id)) {
            throw new EntityNotFoundException("Supplier not found: " + id);
        }

        try {
            supplierRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Can not delete this supplier!");
        }
    }
}
