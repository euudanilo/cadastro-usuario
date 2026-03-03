package com.danilo.cadastro_usuario;

import com.danilo.cadastro_usuario.business.UsuarioService;
import com.danilo.cadastro_usuario.infrastructure.entity.Usuario;
import com.danilo.cadastro_usuario.infrastructure.exceptions.EmailJaCadastradoException;
import com.danilo.cadastro_usuario.infrastructure.exceptions.UsuarioNaoEncontradoException;
import com.danilo.cadastro_usuario.infrastructure.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ==================================
    // TESTES DE BUSCA
    // ==================================
    @Test
    void buscarUsuarioPorEmail_ExisteUsuario_DeveRetornarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@dominio.com");
        usuario.setNome("Teste");

        when(repository.findByEmail("teste@dominio.com")).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarUsuarioPorEmail("teste@dominio.com");

        assertEquals("Teste", resultado.getNome());
        assertEquals("teste@dominio.com", resultado.getEmail());
    }

    @Test
    void buscarUsuarioPorEmail_NaoExisteUsuario_DeveLancarException() {
        when(repository.findByEmail("nao@existe.com")).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> usuarioService.buscarUsuarioPorEmail("nao@existe.com")
        );

        assertEquals("Usuario não encontrado!", exception.getMessage());
    }

    // ==================================
    // TESTES DE CADASTRO
    // ==================================
    @Test
    void cadastraUsuario_EmailNaoExiste_DeveSalvar() {
        Usuario usuario = new Usuario();
        usuario.setEmail("novo@dominio.com");
        usuario.setNome("Novo");

        when(repository.exitsByEmail("novo@dominio.com")).thenReturn(false);
        when(repository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioService.cadastraUsuario(usuario);

        assertEquals("Novo", resultado.getNome());
        assertEquals("novo@dominio.com", resultado.getEmail());
        verify(repository, times(1)).save(usuario);
    }

    @Test
    void cadastraUsuario_EmailJaExiste_DeveLancarException() {
        Usuario usuario = new Usuario();
        usuario.setEmail("existente@dominio.com");
        usuario.setNome("Existente");

        when(repository.exitsByEmail("existente@dominio.com")).thenReturn(true);

        EmailJaCadastradoException exception = assertThrows(
                EmailJaCadastradoException.class,
                () -> usuarioService.cadastraUsuario(usuario)
        );

        assertEquals("Email já cadastrado", exception.getMessage());
        verify(repository, never()).save(usuario);
    }

    // ==================================
    // TESTES DE ATUALIZAÇÃO
    // ==================================
    @Test
    void atualizarUsuario_EmailNaoMuda_DeveAtualizarNome() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("teste@dominio.com");
        usuarioExistente.setNome("Antigo");

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("teste@dominio.com");
        novoUsuario.setNome("Atualizado");

        when(repository.findByEmail("teste@dominio.com")).thenReturn(Optional.of(usuarioExistente));
        when(repository.save(usuarioExistente)).thenReturn(usuarioExistente);

        Usuario resultado = usuarioService.atualizarUsuario("teste@dominio.com", novoUsuario);

        assertEquals("Atualizado", resultado.getNome());
        assertEquals("teste@dominio.com", resultado.getEmail());
        verify(repository, times(1)).save(usuarioExistente);
    }

    @Test
    void atualizarUsuario_EmailJaExiste_DeveLancarException() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("teste@dominio.com");
        usuarioExistente.setNome("Antigo");

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("existente@dominio.com");
        novoUsuario.setNome("Atualizado");

        when(repository.findByEmail("teste@dominio.com")).thenReturn(Optional.of(usuarioExistente));
        when(repository.exitsByEmail("existente@dominio.com")).thenReturn(true);

        EmailJaCadastradoException exception = assertThrows(
                EmailJaCadastradoException.class,
                () -> usuarioService.atualizarUsuario("teste@dominio.com", novoUsuario)
        );

        assertEquals("Novo email já está em uso", exception.getMessage());
        verify(repository, never()).save(usuarioExistente);
    }

    // ==================================
    // TESTES DE DELETE
    // ==================================
    @Test
    void deletarPorEmail_UsuarioExiste_DeveChamarDelete() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("teste@dominio.com");

        when(repository.findByEmail("teste@dominio.com")).thenReturn(Optional.of(usuarioExistente));

        usuarioService.deletarPorEmail("teste@dominio.com");

        verify(repository, times(1)).deleteByEmail("teste@dominio.com");
    }

    @Test
    void deletarPorEmail_UsuarioNaoExiste_DeveLancarException() {
        when(repository.findByEmail("nao@existe.com")).thenReturn(Optional.empty());

        UsuarioNaoEncontradoException exception = assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> usuarioService.deletarPorEmail("nao@existe.com")
        );

        assertEquals("Usuario não encontrado!", exception.getMessage());
        verify(repository, never()).deleteByEmail(anyString());
    }
}
