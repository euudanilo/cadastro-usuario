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

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable String email) {
        return ResponseEntity.ok(service.buscarUsuarioPorEmail(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String email, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.atualizarUsuario(email, usuario));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deletarUsuario(@PathVariable String email) {
        service.deletarPorEmail(email);
        return ResponseEntity.ok("Usuário foi deletado com sucesso");
    }
}
