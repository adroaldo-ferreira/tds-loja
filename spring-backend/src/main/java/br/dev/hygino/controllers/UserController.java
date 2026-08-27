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

import br.dev.hygino.dtos.RequestUserDto;
import br.dev.hygino.dtos.ResponseUserDto;
import br.dev.hygino.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController implements IController<RequestUserDto, ResponseUserDto> {
    private final UserService userService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseUserDto> insert(@RequestBody @Valid RequestUserDto dto) {
        return ResponseEntity.status(201).body(userService.insert(dto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDto> update(@PathVariable Long id, @RequestBody @Valid RequestUserDto dto) {
        return ResponseEntity.status(200).body(userService.insert(dto));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(userService.findById(id));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<ResponseUserDto> findByUsername(String name) {
        return ResponseEntity.status(200).body(userService.findByUsername(name));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ResponseUserDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(200).body(userService.findAll(pageable));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
