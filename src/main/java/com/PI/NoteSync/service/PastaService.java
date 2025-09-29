package com.PI.NoteSync.service;

import com.PI.NoteSync.dto.request.PastaDTORequest;
import com.PI.NoteSync.dto.request.PastaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.PastaDTOResponse;
import com.PI.NoteSync.dto.response.PastaDTOUpdateResponse;
import com.PI.NoteSync.entity.Pasta;
import com.PI.NoteSync.entity.Usuario; // CORREÇÃO: Importar a entidade Usuario
import com.PI.NoteSync.repository.PastaRepository;
import com.PI.NoteSync.repository.UsuarioRepository; // CORREÇÃO: Importar o repositório de Usuario
import jakarta.persistence.EntityNotFoundException; // CORREÇÃO: Importar exceção para erro 404
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastaService {

    private final PastaRepository pastaRepository;
    private final UsuarioRepository usuarioRepository; // CORREÇÃO: Injetar repositório de usuário

    @Autowired
    private ModelMapper modelMapper;

    // CORREÇÃO: Atualizar construtor
    public PastaService(PastaRepository pastaRepository, UsuarioRepository usuarioRepository){
        this.pastaRepository = pastaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Pasta> listarPastas(Integer usuarioId){
        return this.pastaRepository.listarPastasPorUsuario(usuarioId);
    }

    public Pasta listarPorPastaId(Integer pastaId){
        // CORREÇÃO: Lançar exceção se não encontrar, para um melhor tratamento de erro no controller
        return this.pastaRepository.findById(pastaId)
                .orElseThrow(() -> new EntityNotFoundException("Pasta não encontrada com o ID: " + pastaId));
    }

    public PastaDTOResponse criarPasta(PastaDTORequest pastaDTORequest){
        Pasta pasta = modelMapper.map(pastaDTORequest, Pasta.class);

        // CORREÇÃO: Lógica ESSENCIAL para buscar o usuário no banco e associá-lo à pasta.
        Usuario usuario = usuarioRepository.findById(pastaDTORequest.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + pastaDTORequest.getUsuario().getId()));
        pasta.setUsuario(usuario);

        Pasta pastaSalva = this.pastaRepository.save(pasta);
        return modelMapper.map(pastaSalva, PastaDTOResponse.class);
    }

    public PastaDTOResponse atualizarPasta(Integer pastaId, PastaDTORequest pastaDTORequest) {
        Pasta pastaExistente = this.listarPorPastaId(pastaId); // Reutiliza o método que já lança exceção

        // O modelMapper atualiza os campos (nome, descricao)
        modelMapper.map(pastaDTORequest, pastaExistente);

        // CORREÇÃO: A lógica de associação de usuário também é necessária na atualização
        if (pastaDTORequest.getUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(pastaDTORequest.getUsuario().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            pastaExistente.setUsuario(usuario);
        }

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
        // CORREÇÃO: Verificar se a pasta existe antes de tentar apagar
        if (!pastaRepository.existsById(pastaId)) {
            throw new EntityNotFoundException("Pasta não encontrada com o ID: " + pastaId);
        }
        pastaRepository.deleteById(pastaId);
    }
}