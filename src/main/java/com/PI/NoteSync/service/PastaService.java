package com.PI.NoteSync.service;
import com.PI.NoteSync.dto.request.PastaDTORequest;
import com.PI.NoteSync.dto.request.PastaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.PastaDTOResponse;
import com.PI.NoteSync.dto.response.PastaDTOUpdateResponse;
import com.PI.NoteSync.entity.Pasta;
import com.PI.NoteSync.repository.PastaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastaService {

    private final PastaRepository pastaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PastaService(PastaRepository pastaRepository){
        this.pastaRepository = pastaRepository;
    }

    // A listagem de pastas deve ser por usuário
    public List<Pasta> listarPastas(Integer usuarioId){
        return this.pastaRepository.listarPastasPorUsuario(usuarioId);
    }

    public Pasta listarPorPastaId(Integer pastaId){
        return this.pastaRepository.findById(pastaId).orElse(null);
    }

    public PastaDTOResponse criarPasta(PastaDTORequest pastaDTORequest){
        Pasta pasta = modelMapper.map(pastaDTORequest, Pasta.class);
        // Lógica para associar o usuário logado à pasta deve ser adicionada aqui
        Pasta pastaSalva = this.pastaRepository.save(pasta);
        return modelMapper.map(pastaSalva, PastaDTOResponse.class);
    }

    public PastaDTOResponse atualizarPasta(Integer pastaId, PastaDTORequest pastaDTORequest) {
        Pasta pasta = this.listarPorPastaId(pastaId);
        if (pasta != null) {
            modelMapper.map(pastaDTORequest, pasta);
            Pasta tempResponse = pastaRepository.save(pasta);
            return modelMapper.map(tempResponse, PastaDTOResponse.class);
        } else {
            return null;
        }
    }

    public PastaDTOUpdateResponse atualizarParcialmentePasta(Integer pastaId, PastaDTOUpdateRequest pastaDTOUpdateRequest) {
        Pasta pasta = this.listarPorPastaId(pastaId);
        if (pasta != null) {
            // Exemplo de atualização parcial: alterando apenas o nome
            pasta.setNome(pastaDTOUpdateRequest.getNome());
            Pasta tempResponse = pastaRepository.save(pasta);
            return modelMapper.map(tempResponse, PastaDTOUpdateResponse.class);
        }
        return null;
    }

    public void apagarPasta(Integer pastaId){
        // Exclusão física, já que não há status
        pastaRepository.deleteById(pastaId);
    }
}