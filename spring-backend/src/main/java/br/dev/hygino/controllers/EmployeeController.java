package br.dev.hygino.controllers;

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

import br.dev.hygino.dtos.RequestEmployeeDto;
import br.dev.hygino.dtos.ResponseEmployeeDto;
import br.dev.hygino.services.EmployeeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/employee")
@RequiredArgsConstructor
public class EmployeeController implements IController<RequestEmployeeDto, ResponseEmployeeDto> {
    private final EmployeeService employeeService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseEmployeeDto> insert(@RequestBody RequestEmployeeDto dto) {
        return ResponseEntity.status(201).body(employeeService.insert(dto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseEmployeeDto> update(
            @PathVariable Long id,
            @RequestBody RequestEmployeeDto dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseEmployeeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ResponseEmployeeDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
