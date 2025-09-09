package com.PI.NoteSync.service;

import com.PI.NoteSync.dto.request.UsuarioDTORequest;
import com.PI.NoteSync.dto.request.UsuarioDTOUpdateRequest;
import com.PI.NoteSync.dto.response.UsuarioDTOResponse;
import com.PI.NoteSync.dto.response.UsuarioDTOUpdateResponse;
import com.PI.NoteSync.entity.Usuario;

import com.PI.NoteSync.repository.UsuarioRepository;
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
        // Usando o método que já filtra por status
        return this.usuarioRepository.obterUsuarioPeloId(usuarioId).orElse(null);
    }

    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest){
        // Adicionar lógica de codificação de senha aqui antes de mapear
        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);
        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        return modelMapper.map(usuarioSalvo, UsuarioDTOResponse.class);
    }

    public UsuarioDTOResponse atualizarUsuario(Integer usuarioId, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = this.listarPorUsuarioId(usuarioId);
        if (usuario != null) {
            modelMapper.map(usuarioDTORequest, usuario);
            Usuario tempResponse = usuarioRepository.save(usuario);
            return modelMapper.map(tempResponse, UsuarioDTOResponse.class);
        } else {
            return null;
        }
    }

    public UsuarioDTOUpdateResponse atualizarStatusUsuario(Integer usuarioId, UsuarioDTOUpdateRequest usuarioDTOUpdateRequest) {
        Usuario usuario = this.listarPorUsuarioId(usuarioId);
        if (usuario != null) {
            usuario.setStatus(usuarioDTOUpdateRequest.getStatus());
            Usuario tempResponse = usuarioRepository.save(usuario);
            return modelMapper.map(tempResponse, UsuarioDTOUpdateResponse.class);
        }
        return null;
    }

    public void apagarUsuario(Integer usuarioId){
        // Chama o método de exclusão lógica do repositório
        usuarioRepository.apagadoLogicoUsuario(usuarioId);
    }
}