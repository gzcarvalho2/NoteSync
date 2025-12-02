package com.PI.NoteSync.service;

import com.PI.NoteSync.dto.request.UsuarioDTORequest;
import com.PI.NoteSync.dto.request.UsuarioDTOUpdateRequest;
import com.PI.NoteSync.dto.response.UsuarioDTOResponse;
import com.PI.NoteSync.dto.response.UsuarioDTOUpdateResponse;
import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios(){
        return this.usuarioRepository.listarUsuariosAtivos();
    }

    public Usuario listarPorUsuarioId(Integer usuarioId){
        return this.usuarioRepository.obterUsuarioPeloId(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + usuarioId));
    }

    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest){
        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);

        // 1. CORREÇÃO: Pegar a senha do DTO, Criptografar e Setar na Entidade
        // Isso resolve o problema se o DTO chamar "password" e a Entidade chamar "senha"
        if (usuarioDTORequest.getPassword() != null) {
            String senhaCriptografada = passwordEncoder.encode(usuarioDTORequest.getPassword());
            usuario.setSenha(senhaCriptografada);
        }

        if (usuarioDTORequest.getPassword() != null) {
            // Pega direto do DTO para garantir
            String senhaCriptografada = passwordEncoder.encode(usuarioDTORequest.getPassword());
            usuario.setSenha(senhaCriptografada);
        }

        // 2. CORREÇÃO: Definir Status padrão como ATIVO (1)
        // Como removemos esse campo do DTO, precisamos garantir que ele não fique null
        usuario.setStatus(1);

        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        return modelMapper.map(usuarioSalvo, UsuarioDTOResponse.class);


    }

    public UsuarioDTOResponse atualizarUsuario(Integer usuarioId, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = this.listarPorUsuarioId(usuarioId);

        modelMapper.map(usuarioDTORequest, usuario);

        // Se quiser permitir troca de senha no update, descomente e ajuste:
        // if (usuarioDTORequest.getPassword() != null && !usuarioDTORequest.getPassword().isBlank()) {
        //     usuario.setSenha(passwordEncoder.encode(usuarioDTORequest.getPassword()));
        // }

        Usuario tempResponse = usuarioRepository.save(usuario);
        return modelMapper.map(tempResponse, UsuarioDTOResponse.class);
    }

    public UsuarioDTOUpdateResponse atualizarStatusUsuario(Integer usuarioId, UsuarioDTOUpdateRequest usuarioDTOUpdateRequest) {
        Usuario usuario = this.listarPorUsuarioId(usuarioId);
        usuario.setStatus(usuarioDTOUpdateRequest.getStatus());
        Usuario tempResponse = usuarioRepository.save(usuario);
        return modelMapper.map(tempResponse, UsuarioDTOUpdateResponse.class);
    }

    public void apagarUsuario(Integer usuarioId){
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + usuarioId);
        }
        usuarioRepository.apagadoLogicoUsuario(usuarioId);
    }
}