package com.PI.NoteSync.controller;

import com.PI.NoteSync.dto.request.TagDTORequest;
import com.PI.NoteSync.dto.request.TagDTOUpdateRequest;
import com.PI.NoteSync.dto.response.TagDTOResponse;
import com.PI.NoteSync.dto.response.TagDTOUpdateResponse;
import com.PI.NoteSync.entity.Tag;
import com.PI.NoteSync.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // IMPORTANTE
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name="Tag", description = "API para gerenciamento de tags")
public class TagController {

    private final TagService tagService;
    private final ModelMapper modelMapper;

    public TagController(TagService tagService, ModelMapper modelMapper) {
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar tags por usuário", description = "Endpoint para listar todas as tags de um usuário específico")
    public ResponseEntity<List<TagDTOResponse>> listarTags(@RequestParam Integer usuarioId){
        List<Tag> tags = tagService.listarTags(usuarioId);
        List<TagDTOResponse> response = tags.stream()
                .map(tag -> modelMapper.map(tag, TagDTOResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarPorId/{tagId}")
    @Operation(summary = "Listar tag pelo ID", description = "Endpoint para obter Tag pelo seu id")
    public ResponseEntity<TagDTOResponse> listarPorTagId(@PathVariable("tagId") Integer tagId) {
        Tag tag = tagService.listarPorTagId(tagId);
        return ResponseEntity.ok(modelMapper.map(tag, TagDTOResponse.class));
    }

    // --- MUDANÇA PRINCIPAL AQUI ---
    @PostMapping("/criar")
    @Operation(summary = "Criar nova Tag", description = "Endpoint para criar um novo registro de Tag")
    public ResponseEntity<TagDTOResponse> criarTag(@Valid @RequestBody TagDTORequest tag, Principal principal){
        // Passa o email do token para o serviço
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.criarTag(tag, principal.getName()));
    }

    @PutMapping("/atualizar/{tagId}")
    @Operation(summary = "Atualizar todos os dados da Tag", description = "Endpoint para atualizar todos os dados da tag")
    public ResponseEntity<TagDTOResponse> atualizarTag(@PathVariable("tagId") Integer tagId, @RequestBody TagDTORequest tagDTORequest){
        return ResponseEntity.ok(tagService.atualizarTag(tagId, tagDTORequest));
    }

    @PutMapping("/atualizarParcial/{tagId}")
    @Operation(summary = "Atualiza parcialmente a tag", description = "Endpoint para atualizar parcialmente uma tag")
    public ResponseEntity<TagDTOUpdateResponse> atualizarParcialmenteTag(
            @PathVariable("tagId") Integer tagId,
            @RequestBody TagDTOUpdateRequest tagDTOUpdateRequest){
        return ResponseEntity.ok(tagService.atualizarParcialmenteTag(tagId, tagDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{tagId}")
    @Operation(summary = "Apagar registro de tag", description = "Endpoint para apagar uma tag")
    public ResponseEntity<Void> apagarTag(@PathVariable("tagId") Integer tagId){
        tagService.apagarTag(tagId);
        return ResponseEntity.noContent().build();
    }
}