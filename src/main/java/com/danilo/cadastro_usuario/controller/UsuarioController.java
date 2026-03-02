package com.danilo.cadastro_usuario.controller;

import com.danilo.cadastro_usuario.business.UsuarioService;
import com.danilo.cadastro_usuario.infrastructure.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(service.cadastraUsuario(usuario));
    }

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return service.listarTodosUsuarios();
    }
}
