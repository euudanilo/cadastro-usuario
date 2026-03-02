package com.danilo.cadastro_usuario.business;

import com.danilo.cadastro_usuario.infrastructure.entity.Usuario;
import com.danilo.cadastro_usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public Usuario cadastraUsuario(Usuario usuario) {
        repository.findByEmail(usuario.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email já cadastrado");
                });

        return repository.save(usuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return repository.findAll();
    }
}
