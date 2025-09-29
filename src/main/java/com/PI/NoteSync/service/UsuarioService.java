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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        return modelMapper.map(usuarioSalvo, UsuarioDTOResponse.class);
    }

    public UsuarioDTOResponse atualizarUsuario(Integer usuarioId, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = this.listarPorUsuarioId(usuarioId);

        // Mapeia todos os campos do DTO (nome, email, status) para a entidade
        modelMapper.map(usuarioDTORequest, usuario);

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