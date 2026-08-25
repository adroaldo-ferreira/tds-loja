package br.dev.hygino.services;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import br.dev.hygino.dtos.RequestUserDto;
import br.dev.hygino.dtos.ResponseUserDto;
import br.dev.hygino.entities.User;
import br.dev.hygino.exceptions.DatabaseException;
import br.dev.hygino.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Validated
@RequiredArgsConstructor
public class UserService implements IService<RequestUserDto, ResponseUserDto> {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ResponseUserDto insert(RequestUserDto dto) {
        User entity = new User();
        entity.setCreatedAt(LocalDateTime.now());
        dtoToEntity(dto, entity);
        entity = userRepository.save(entity);
        return entity.toResponseDto();
    }

    private void dtoToEntity(@Valid RequestUserDto dto, @NotNull User entity) {
        entity.setUsername(dto.username());
        entity.setAccessLevel(dto.accessLevel());
        entity.setPassword(dto.password());
        entity.setFullName(dto.fullName());
        entity.setActive(dto.isActive());
    }

    @Override
    @Transactional
    public ResponseUserDto update(long id, RequestUserDto dto) {
        try {
            User entity = userRepository.getReferenceById(id);
            dtoToEntity(dto, entity);
            entity = userRepository.save(entity);
            return entity.toResponseDto();
        } catch (Exception e) {
            throw new IllegalArgumentException("User does not exists!");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseUserDto findById(long id) {
        return userRepository.findById(id)
                .map(User::toResponseDto)
                .orElseThrow(() -> new IllegalArgumentException("User does not exists!"));
    }

    @Transactional(readOnly = true)
    public ResponseUserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::toResponseDto)
                .orElseThrow(() -> new IllegalArgumentException("User does not exists!"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseUserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(User::toResponseDto);
    }

    @Override
    public void remove(long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found: " + id);
        }

        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Can not delete this user!");
        }
    }
}
