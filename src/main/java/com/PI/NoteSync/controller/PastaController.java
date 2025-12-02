package com.PI.NoteSync.controller;

import com.PI.NoteSync.dto.request.PastaDTORequest;
import com.PI.NoteSync.dto.request.PastaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.PastaDTOResponse;
import com.PI.NoteSync.dto.response.PastaDTOUpdateResponse;
import com.PI.NoteSync.entity.Pasta;
import com.PI.NoteSync.service.PastaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // IMPORTANTE: Importar Principal
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pasta")
@Tag(name="Pasta", description = "API para gerenciamento de pastas de notas")
public class PastaController {

    private final PastaService pastaService;
    private final ModelMapper modelMapper;

    public PastaController(PastaService pastaService, ModelMapper modelMapper) {
        this.pastaService = pastaService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar pastas por usuário", description = "Endpoint para listar todas as pastas de um usuário específico")
    public ResponseEntity<List<PastaDTOResponse>> listarPastas(@RequestParam Integer usuarioId){
        List<Pasta> pastas = pastaService.listarPastas(usuarioId);
        List<PastaDTOResponse> response = pastas.stream()
                .map(pasta -> modelMapper.map(pasta, PastaDTOResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listarPorId/{pastaId}")
    @Operation(summary = "Listar pasta pelo ID", description = "Endpoint para obter Pasta pelo seu id")
    public ResponseEntity<PastaDTOResponse> listarPorPastaId(@PathVariable("pastaId") Integer pastaId) {
        Pasta pasta = pastaService.listarPorPastaId(pastaId);
        return ResponseEntity.ok(modelMapper.map(pasta, PastaDTOResponse.class));
    }

    // --- MUDANÇA PRINCIPAL AQUI ---
    @PostMapping("/criar")
    @Operation(summary = "Criar nova Pasta", description = "Endpoint para criar um novo registro de Pasta")
    public ResponseEntity<PastaDTOResponse> criarPasta(@Valid @RequestBody PastaDTORequest pasta, Principal principal){
        // Pega o email do token (principal.getName()) e passa para o serviço
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pastaService.criarPasta(pasta, principal.getName()));
    }

    @PutMapping("/atualizar/{pastaId}")
    @Operation(summary = "Atualizar todos os dados da Pasta", description = "Endpoint para atualizar todos os dados da pasta")
    public ResponseEntity<PastaDTOResponse> atualizarPasta(@PathVariable("pastaId") Integer pastaId, @RequestBody PastaDTORequest pastaDTORequest){
        return ResponseEntity.ok(pastaService.atualizarPasta(pastaId, pastaDTORequest));
    }

    @PutMapping("/atualizarParcial/{pastaId}")
    @Operation(summary = "Atualiza parcialmente a pasta", description = "Endpoint para atualizar parcialmente uma pasta")
    public ResponseEntity<PastaDTOUpdateResponse> atualizarParcialmentePasta(
            @PathVariable("pastaId") Integer pastaId,
            @RequestBody PastaDTOUpdateRequest pastaDTOUpdateRequest){
        return ResponseEntity.ok(pastaService.atualizarParcialmentePasta(pastaId, pastaDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{pastaId}")
    @Operation(summary = "Apagar registro de pasta", description = "Endpoint para apagar uma pasta")
    public ResponseEntity<Void> apagarPasta(@PathVariable("pastaId") Integer pastaId){
        pastaService.apagarPasta(pastaId);
        return ResponseEntity.noContent().build();
    }
}