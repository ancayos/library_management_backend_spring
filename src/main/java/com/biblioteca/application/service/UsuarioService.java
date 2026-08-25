package com.biblioteca.application.service;

import com.biblioteca.domain.exception.RecursoNoEncontradoException;
import com.biblioteca.domain.model.Usuario;
import com.biblioteca.infrastructure.persistence.entity.UsuarioEntity;
import com.biblioteca.infrastructure.persistence.mapper.UsuarioMapper;
import com.biblioteca.infrastructure.persistence.repository.UsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioService {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public List<Usuario> listarTodos() {
        return usuarioJpaRepository.findAll().stream()
                .map(UsuarioMapper::toDomain)
                .toList();
    }

    public Usuario buscarPorId(Long id) {
        UsuarioEntity entity = usuarioJpaRepository.findById(id)
                .orElseThrow(() -> RecursoNoEncontradoException.de("Usuario", id));
        return UsuarioMapper.toDomain(entity);
    }

    public Usuario crear(Usuario usuario) {
        usuario.setId(null);
        UsuarioEntity guardado = usuarioJpaRepository.save(UsuarioMapper.toEntity(usuario));
        return UsuarioMapper.toDomain(guardado);
    }

    public Usuario actualizar(Long id, Usuario cambios) {
        UsuarioEntity existente = usuarioJpaRepository.findById(id)
                .orElseThrow(() -> RecursoNoEncontradoException.de("Usuario", id));

        existente.setNombre(cambios.getNombre());
        existente.setEmail(cambios.getEmail());

        return UsuarioMapper.toDomain(usuarioJpaRepository.save(existente));
    }

    public void eliminar(Long id) {
        if (!usuarioJpaRepository.existsById(id)) {
            throw RecursoNoEncontradoException.de("Usuario", id);
        }
        usuarioJpaRepository.deleteById(id);
    }
}
