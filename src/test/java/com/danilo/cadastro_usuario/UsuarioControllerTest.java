package com.danilo.cadastro_usuario;

import com.danilo.cadastro_usuario.business.UsuarioService;
import com.danilo.cadastro_usuario.controller.UsuarioController;
import com.danilo.cadastro_usuario.infrastructure.entity.Usuario;
import com.danilo.cadastro_usuario.infrastructure.exceptions.EmailJaCadastradoException;
import com.danilo.cadastro_usuario.infrastructure.exceptions.UsuarioNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .setControllerAdvice(new com.danilo.cadastro_usuario.infrastructure.exceptions.GlobalExceptionHandler())
                .build();
    }

    // ==================================
    // TESTE DE CADASTRO
    // ==================================
    @Test
    void cadastraUsuario_Sucesso_DeveRetornar200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@dominio.com");

        when(usuarioService.cadastraUsuario(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste"))
                .andExpect(jsonPath("$.email").value("teste@dominio.com"));

        verify(usuarioService, times(1)).cadastraUsuario(any(Usuario.class));
    }

    @Test
    void cadastraUsuario_EmailJaCadastrado_DeveRetornar409() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@dominio.com");

        when(usuarioService.cadastraUsuario(any(Usuario.class)))
                .thenThrow(new EmailJaCadastradoException("Email já cadastrado"));

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Email já cadastrado"));
    }

    // ==================================
    // TESTE DE BUSCA
    // ==================================
    @Test
    void buscarUsuario_EmailExiste_DeveRetornar200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setEmail("teste@dominio.com");

        when(usuarioService.buscarUsuarioPorEmail("teste@dominio.com")).thenReturn(usuario);

        mockMvc.perform(get("/usuario/teste@dominio.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Teste"))
                .andExpect(jsonPath("$.email").value("teste@dominio.com"));
    }

    @Test
    void buscarUsuario_EmailNaoExiste_DeveRetornar404() throws Exception {
        when(usuarioService.buscarUsuarioPorEmail("nao@existe.com"))
                .thenThrow(new UsuarioNaoEncontradoException("Usuario não encontrado!"));

        mockMvc.perform(get("/usuario/nao@existe.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Usuario não encontrado!"));
    }

    // ==================================
    // TESTE DE DELETE
    // ==================================
    @Test
    void deletarUsuario_Existe_DeveRetornar200() throws Exception {
        doNothing().when(usuarioService).deletarPorEmail("teste@dominio.com");

        mockMvc.perform(delete("/usuario/teste@dominio.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(content().string("Usuário foi deletado com sucesso"));

        verify(usuarioService, times(1)).deletarPorEmail("teste@dominio.com");
    }

    @Test
    void deletarUsuario_NaoExiste_DeveRetornar404() throws Exception {
        doThrow(new UsuarioNaoEncontradoException("Usuario não encontrado!"))
                .when(usuarioService).deletarPorEmail("nao@existe.com");

        mockMvc.perform(delete("/usuario/nao@existe.com"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Usuario não encontrado!"));
    }
}
