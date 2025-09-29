package com.PI.NoteSync.service;

import com.PI.NoteSync.dto.request.TagDTORequest;
import com.PI.NoteSync.dto.request.TagDTOUpdateRequest;
import com.PI.NoteSync.dto.response.TagDTOResponse;
import com.PI.NoteSync.dto.response.TagDTOUpdateResponse;
import com.PI.NoteSync.entity.Tag;
import com.PI.NoteSync.entity.Usuario; // CORREÇÃO: Importar Usuario
import com.PI.NoteSync.repository.TagRepository;
import com.PI.NoteSync.repository.UsuarioRepository; // CORREÇÃO: Importar UsuarioRepository
import jakarta.persistence.EntityNotFoundException; // CORREÇÃO: Importar exceção
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository; // CORREÇÃO: Injetar repositório

    @Autowired
    private ModelMapper modelMapper;

    // CORREÇÃO: Atualizar construtor
    public TagService(TagRepository tagRepository, UsuarioRepository usuarioRepository){
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // A listagem de tags deve ser por usuário
    public List<Tag> listarTags(Integer usuarioId){
        return this.tagRepository.listarTagsPorUsuario(usuarioId);
    }

    public Tag listarPorTagId(Integer tagId){
        // CORREÇÃO: Lançar exceção se não encontrar a tag
        return this.tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag não encontrada com o ID: " + tagId));
    }

    public TagDTOResponse criarTag(TagDTORequest tagDTORequest){
        Tag tag = modelMapper.map(tagDTORequest, Tag.class);

        // CORREÇÃO: Lógica ESSENCIAL para buscar o usuário no banco e associá-lo à tag.
        Usuario usuario = usuarioRepository.findById(tagDTORequest.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + tagDTORequest.getUsuario().getId()));
        tag.setUsuario(usuario);

        Tag tagSalva = this.tagRepository.save(tag);
        return modelMapper.map(tagSalva, TagDTOResponse.class);
    }

    public TagDTOResponse atualizarTag(Integer tagId, TagDTORequest tagDTORequest) {
        Tag tagExistente = this.listarPorTagId(tagId); // Reutiliza o método que já lança exceção

        modelMapper.map(tagDTORequest, tagExistente);

        // CORREÇÃO: Lógica de associação de usuário também é necessária na atualização
        if (tagDTORequest.getUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(tagDTORequest.getUsuario().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            tagExistente.setUsuario(usuario);
        }

        Tag tempResponse = tagRepository.save(tagExistente);
        return modelMapper.map(tempResponse, TagDTOResponse.class);
    }

    public TagDTOUpdateResponse atualizarParcialmenteTag(Integer tagId, TagDTOUpdateRequest tagDTOUpdateRequest) {
        Tag tag = this.listarPorTagId(tagId);

        if (tagDTOUpdateRequest.getNome() != null) {
            tag.setNome(tagDTOUpdateRequest.getNome());
        }

        Tag tempResponse = tagRepository.save(tag);
        return modelMapper.map(tempResponse, TagDTOUpdateResponse.class);
    }

    public void apagarTag(Integer tagId){
        // CORREÇÃO: Verificar se a tag existe antes de tentar apagar
        if (!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException("Tag não encontrada com o ID: " + tagId);
        }
        tagRepository.deleteById(tagId);
    }
}