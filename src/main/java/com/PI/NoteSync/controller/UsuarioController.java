package com.PI.NoteSync.controller;

// ... (seus imports)
import com.PI.NoteSync.dto.response.UsuarioDTOResponse;
import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/usuario") // Convenção: usar plural ou singular, mas manter consistente
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper; // CORREÇÃO: Injetar ModelMapper

    // CORREÇÃO: Atualizar construtor
    public UsuarioController(UsuarioService usuarioService, ModelMapper modelMapper) {
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/listar")
    // CORREÇÃO: Retornar uma lista de DTOs para evitar o loop e não expor dados sensíveis
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioDTOResponse> response = usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarPorId/{usuarioId}")
    // CORREÇÃO: Retornar o DTO de resposta
    public ResponseEntity<UsuarioDTOResponse> listarPorUsuarioId(@PathVariable("usuarioId") Integer usuarioId) {
        Usuario usuario = usuarioService.listarPorUsuarioId(usuarioId);
        // A verificação de nulo não é mais necessária aqui
        return ResponseEntity.ok(modelMapper.map(usuario, UsuarioDTOResponse.class));
    }

    // Os outros endpoints (criar, atualizar, apagar) já usavam DTOs ou não retornavam corpo,
    // então eles se beneficiam das correções no Service sem precisar de mudanças aqui.
    // ... (seu código para criar, atualizar e apagar pode permanecer o mesmo)
}