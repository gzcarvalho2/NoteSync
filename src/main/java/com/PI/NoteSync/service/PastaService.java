package com.PI.NoteSync.service;

import com.PI.NoteSync.dto.request.PastaDTORequest;
import com.PI.NoteSync.dto.request.PastaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.PastaDTOResponse;
import com.PI.NoteSync.dto.response.PastaDTOUpdateResponse;
import com.PI.NoteSync.entity.Pasta;
import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.repository.PastaRepository;
import com.PI.NoteSync.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PastaService {

    private final PastaRepository pastaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PastaService(PastaRepository pastaRepository, UsuarioRepository usuarioRepository){
        this.pastaRepository = pastaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Se quiser, futuramente pode mudar aqui para listar por email também
    public List<Pasta> listarPastas(Integer usuarioId){
        return this.pastaRepository.listarPastasPorUsuario(usuarioId);
    }

    public Pasta listarPorPastaId(Integer pastaId){
        return this.pastaRepository.findById(pastaId)
                .orElseThrow(() -> new EntityNotFoundException("Pasta não encontrada com o ID: " + pastaId));
    }

    // MUDANÇA CRÍTICA: Recebe o email do usuário logado
    public PastaDTOResponse criarPasta(PastaDTORequest pastaDTORequest, String emailUsuario){
        // 1. Converte DTO para Entidade
        Pasta pasta = modelMapper.map(pastaDTORequest, Pasta.class);

        // 2. Define a data atual
        pasta.setDataDeCriacao(LocalDateTime.now());


        pasta.setStatus(1); // Define como Ativa por padrão

        // 3. Busca o usuário pelo EMAIL do token (Segurança: garante que é o usuário logado)
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário logado não encontrado no banco."));

        // 4. Associa
        pasta.setUsuario(usuario);

        Pasta pastaSalva = this.pastaRepository.save(pasta);
        return modelMapper.map(pastaSalva, PastaDTOResponse.class);
    }

    public PastaDTOResponse atualizarPasta(Integer pastaId, PastaDTORequest pastaDTORequest) {
        Pasta pastaExistente = this.listarPorPastaId(pastaId);

        // O modelMapper atualiza os campos simples (nome, descricao)
        modelMapper.map(pastaDTORequest, pastaExistente);

        // REMOVIDO: Não tentamos mais atualizar o usuário aqui,
        // pois o DTO não traz essa informação e pasta não costuma mudar de dono assim.

        Pasta tempResponse = pastaRepository.save(pastaExistente);
        return modelMapper.map(tempResponse, PastaDTOResponse.class);
    }

    public PastaDTOUpdateResponse atualizarParcialmentePasta(Integer pastaId, PastaDTOUpdateRequest pastaDTOUpdateRequest) {
        Pasta pasta = this.listarPorPastaId(pastaId);

        if (pastaDTOUpdateRequest.getNome() != null) {
            pasta.setNome(pastaDTOUpdateRequest.getNome());
        }
        if (pastaDTOUpdateRequest.getDescricao() != null) {
            pasta.setDescricao(pastaDTOUpdateRequest.getDescricao());
        }

        Pasta tempResponse = pastaRepository.save(pasta);
        return modelMapper.map(tempResponse, PastaDTOUpdateResponse.class);
    }

    public void apagarPasta(Integer pastaId){
        if (!pastaRepository.existsById(pastaId)) {
            throw new EntityNotFoundException("Pasta não encontrada com o ID: " + pastaId);
        }
        pastaRepository.deleteById(pastaId);
    }
}