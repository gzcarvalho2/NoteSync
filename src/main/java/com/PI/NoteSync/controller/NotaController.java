package com.PI.NoteSync.controller;

import com.PI.NoteSync.dto.request.NotaDTORequest;
import com.PI.NoteSync.dto.request.NotaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.NotaDTOResponse;
import com.PI.NoteSync.dto.response.NotaDTOUpdateResponse;
import com.PI.NoteSync.entity.Nota;
import com.PI.NoteSync.service.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// CORREÇÃO: Adicionar ModelMapper para converter a lista de Entidade para DTO
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/nota")
@Tag(name = "Nota", description = "API para gerenciamento de notas")
public class NotaController {

    private final NotaService notaService;
    // CORREÇÃO: Adicionar ModelMapper para conversões
    private final ModelMapper modelMapper;

    public NotaController(NotaService notaService, ModelMapper modelMapper) {
        this.notaService = notaService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar notas", description = "Endpoint para listar todas as notas")
    // CORREÇÃO: Retornar uma lista de DTOs para evitar o erro de relacionamento circular
    public ResponseEntity<List<NotaDTOResponse>> listarNotas() {
        List<Nota> notas = notaService.listarNotas();
        List<NotaDTOResponse> response = notas.stream()
                .map(nota -> modelMapper.map(nota, NotaDTOResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarPorId/{notaId}")
    @Operation(summary = "Listar nota pelo ID", description = "Endpoint para obter Nota pelo seu id")
    // CORREÇÃO: Retornar DTO em vez da entidade
    public ResponseEntity<NotaDTOResponse> listarPorNotaId(@PathVariable("notaId") Integer notaId) {
        Nota nota = notaService.listarPorNotaId(notaId);
        // O service agora lança uma exceção se não encontrar, então a verificação de nulo não é mais necessária aqui
        return ResponseEntity.ok(modelMapper.map(nota, NotaDTOResponse.class));
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova Nota", description = "Endpoint para criar um novo registro de Nota")
    public ResponseEntity<NotaDTOResponse> criarNota(@Valid @RequestBody NotaDTORequest nota) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notaService.criarNota(nota));
    }

    @PutMapping("/atualizar/{notaId}")
    @Operation(summary = "Atualizar todos os dados da Nota", description = "Endpoint para atualizar todos os dados da nota")
    public ResponseEntity<NotaDTOResponse> atualizarNota(@PathVariable("notaId") Integer notaId, @RequestBody NotaDTORequest notaDTORequest) {
        return ResponseEntity.ok(notaService.atualizarNota(notaId, notaDTORequest));
    }

    @PutMapping("/atualizarParcial/{notaId}")
    @Operation(summary = "Atualiza parcialmente a nota", description = "Endpoint para atualizar parcialmente uma nota")
    public ResponseEntity<NotaDTOUpdateResponse> atualizarParcialmenteNota(
            @PathVariable("notaId") Integer notaId,
            @RequestBody NotaDTOUpdateRequest notaDTOUpdateRequest) {
        return ResponseEntity.ok(notaService.atualizarParcialmenteNota(notaId, notaDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{notaId}")
    @Operation(summary = "Apagar registro de nota", description = "Endpoint para apagar uma nota")
    public ResponseEntity<Void> apagarNota(@PathVariable("notaId") Integer notaId) {
        notaService.apagarNota(notaId);
        return ResponseEntity.noContent().build();
    }
}