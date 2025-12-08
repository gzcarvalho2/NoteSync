package com.PI.NoteSync.service;

import com.PI.NoteSync.dto.request.TagDTORequest;
import com.PI.NoteSync.dto.request.TagDTOUpdateRequest;
import com.PI.NoteSync.dto.response.TagDTOResponse;
import com.PI.NoteSync.dto.response.TagDTOUpdateResponse;
import com.PI.NoteSync.entity.Tag;
import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.repository.TagRepository;
import com.PI.NoteSync.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final UsuarioRepository usuarioRepository; // Necessário para buscar o usuário logado

    @Autowired
    private ModelMapper modelMapper;

    public TagService(TagRepository tagRepository, UsuarioRepository usuarioRepository) {
        this.tagRepository = tagRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Tag> listarTags(Integer usuarioId) {
        return this.tagRepository.listarTagsPorUsuario(usuarioId);
    }

    public Tag listarPorTagId(Integer tagId) {
        return this.tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag não encontrada com ID: " + tagId));
    }

    // MUDANÇA: Recebe o email do usuário logado (Segurança)
    public TagDTOResponse criarTag(TagDTORequest tagDTO, String emailUsuario) {
        Tag tag = modelMapper.map(tagDTO, Tag.class);

        tag.setStatus(1);

        // Busca o usuário pelo Email do Token
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário logado não encontrado no banco."));

        // Associa a tag ao usuário
        tag.setUsuario(usuario);

        Tag tagSalva = this.tagRepository.save(tag);
        return modelMapper.map(tagSalva, TagDTOResponse.class);
    }

    public TagDTOResponse atualizarTag(Integer tagId, TagDTORequest tagDTORequest) {
        Tag tag = this.listarPorTagId(tagId);

        // Atualiza apenas o nome
        modelMapper.map(tagDTORequest, tag);

        Tag tagSalva = tagRepository.save(tag);
        return modelMapper.map(tagSalva, TagDTOResponse.class);
    }

    public TagDTOUpdateResponse atualizarParcialmenteTag(Integer tagId, TagDTOUpdateRequest tagDTOUpdateRequest) {
        Tag tag = this.listarPorTagId(tagId);

        if (tagDTOUpdateRequest.getNome() != null) {
            tag.setNome(tagDTOUpdateRequest.getNome());
        }

        Tag tagSalva = tagRepository.save(tag);
        return modelMapper.map(tagSalva, TagDTOUpdateResponse.class);
    }

    public void apagarTag(Integer tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new EntityNotFoundException("Tag não encontrada com ID: " + tagId);
        }
        tagRepository.deleteById(tagId);
    }
}