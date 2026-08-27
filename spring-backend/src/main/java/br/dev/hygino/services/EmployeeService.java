package br.dev.hygino.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.dev.hygino.dtos.RequestEmployeeDto;
import br.dev.hygino.dtos.ResponseEmployeeDto;
import br.dev.hygino.entities.Employee;
import br.dev.hygino.entities.User;
import br.dev.hygino.exceptions.DatabaseException;
import br.dev.hygino.exceptions.ResourceNotFoundException;
import br.dev.hygino.repositories.EmployeeRepository;
import br.dev.hygino.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class EmployeeService implements IService<RequestEmployeeDto, ResponseEmployeeDto> {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseEmployeeDto insert(RequestEmployeeDto dto) {
        Employee employee = new Employee();

        if (dto.userId() != null) {
            User user = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + dto.userId()));
            employee.setUser(user);
        }

        dtoToEntity(dto, employee);
        employee = employeeRepository.save(employee);
        return new ResponseEmployeeDto(employee);
    }

    private void dtoToEntity(RequestEmployeeDto dto, Employee entity) {
        entity.setName(dto.name());
        entity.setCpf(dto.cpf());
        entity.setPosition(dto.position());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setEmail(dto.email());
        entity.setAdmissionDate(dto.admissionDate());
        entity.setSalary(dto.salary());

        if (dto.isActive() != null) {
            entity.setActive(dto.isActive());
        }
    }

    @Override
    @Transactional
    public ResponseEmployeeDto update(long id, RequestEmployeeDto dto) {
        try {
            User user = userRepository.findById(dto.userId()).get();
            Employee employee = employeeRepository.getReferenceById(id);
            employee.setUser(user);
            dtoToEntity(dto, employee);
            employee = employeeRepository.save(employee);
            return new ResponseEmployeeDto(employee);
        } catch (EntityNotFoundException e) {
            throw new IllegalArgumentException("Employee does not exists!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEmployeeDto findById(long id) {
        return employeeRepository.findById(id)
                .map(ResponseEmployeeDto::new)
                .orElseThrow(() -> new IllegalArgumentException("Employee does not exists!"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseEmployeeDto> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(ResponseEmployeeDto::new);
    }

    @Override
    public void remove(long id) {
        try {
            employeeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Can not delete this employee!");
        }
    }
}
