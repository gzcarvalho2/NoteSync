package com.PI.NoteSync.controller;

import com.PI.NoteSync.dto.request.UsuarioDTORequest;
import com.PI.NoteSync.dto.roles.LoginUserDto;
import com.PI.NoteSync.dto.roles.RecoveryJwtTokenDto;
import com.PI.NoteSync.dto.response.UsuarioDTOResponse;
import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.service.JwtTokenService;
import com.PI.NoteSync.service.UsuarioService;
import com.PI.NoteSync.service.UserDetailsImpl;
import jakarta.validation.Valid; // Importante para validar o DTO
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users") // Alinhado com o SecurityConfiguration
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ModelMapper modelMapper;

    // --- LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        try {
            // Mostra no console o que está chegando
            System.out.println("Tentando logar com: " + loginUserDto.email());

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtTokenService.generateToken(userDetails);

            return new ResponseEntity<>(new RecoveryJwtTokenDto(token), HttpStatus.OK);

        } catch (Exception e) {
            // ISSO VAI MOSTRAR O ERRO REAL NO CONSOLE
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Erro no Login: " + e.getMessage());
        }
    }

    // --- CRIAR USUÁRIO (REGISTRO) ---
    // MUDANÇA: Recebe o DTO limpo (UsuarioDTORequest) e valida (@Valid)
    @PostMapping
    public ResponseEntity<UsuarioDTOResponse> createUser(@Valid @RequestBody UsuarioDTORequest usuarioDTO) {
        UsuarioDTOResponse novoUsuario = usuarioService.criarUsuario(usuarioDTO);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    // --- LISTAR ---
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioDTOResponse> response = usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarPorId/{usuarioId}")
    public ResponseEntity<UsuarioDTOResponse> listarPorUsuarioId(@PathVariable("usuarioId") Integer usuarioId) {
        Usuario usuario = usuarioService.listarPorUsuarioId(usuarioId);
        return ResponseEntity.ok(modelMapper.map(usuario, UsuarioDTOResponse.class));
    }
}