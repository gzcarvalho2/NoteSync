package com.PI.NoteSync.service;
import com.PI.NoteSync.dto.request.TagDTORequest;
import com.PI.NoteSync.dto.request.TagDTOUpdateRequest;
import com.PI.NoteSync.dto.response.TagDTOResponse;
import com.PI.NoteSync.dto.response.TagDTOUpdateResponse;
import com.PI.NoteSync.entity.Tag;
import com.PI.NoteSync.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    // A listagem de tags deve ser por usuário
    public List<Tag> listarTags(Integer usuarioId){
        return this.tagRepository.listarTagsPorUsuario(usuarioId);
    }

    public Tag listarPorTagId(Integer tagId){
        return this.tagRepository.findById(tagId).orElse(null);
    }

    public TagDTOResponse criarTag(TagDTORequest tagDTORequest){
        Tag tag = modelMapper.map(tagDTORequest, Tag.class);
        // Lógica para associar o usuário logado à tag deve ser adicionada aqui
        Tag tagSalva = this.tagRepository.save(tag);
        return modelMapper.map(tagSalva, TagDTOResponse.class);
    }

    public TagDTOResponse atualizarTag(Integer tagId, TagDTORequest tagDTORequest) {
        Tag tag = this.listarPorTagId(tagId);
        if (tag != null) {
            modelMapper.map(tagDTORequest, tag);
            Tag tempResponse = tagRepository.save(tag);
            return modelMapper.map(tempResponse, TagDTOResponse.class);
        } else {
            return null;
        }
    }

    public TagDTOUpdateResponse atualizarParcialmenteTag(Integer tagId, TagDTOUpdateRequest tagDTOUpdateRequest) {
        Tag tag = this.listarPorTagId(tagId);
        if (tag != null) {
            // Exemplo de atualização parcial: alterando apenas o nome
            tag.setNome(tagDTOUpdateRequest.getNome());
            Tag tempResponse = tagRepository.save(tag);
            return modelMapper.map(tempResponse, TagDTOUpdateResponse.class);
        }
        return null;
    }

    public void apagarTag(Integer tagId){
        // Exclusão física
        tagRepository.deleteById(tagId);
    }
}