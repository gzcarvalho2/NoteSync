package com.PI.NoteSync.service;
import com.PI.NoteSync.dto.request.NotaDTORequest;
import com.PI.NoteSync.dto.request.NotaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.NotaDTOResponse;
import com.PI.NoteSync.dto.response.NotaDTOUpdateResponse;
import com.PI.NoteSync.entity.Nota;
import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.repository.NotaRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {

    private final NotaRepository notaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public NotaService(NotaRepository notaRepository){
        this.notaRepository = notaRepository;
    }

    // A listagem de notas deve ser por usuário
    public List<Nota> listarNotas(){
        return this.notaRepository.listarNotasPorUsuario(usuario_Id);
    }

    public Nota listarPorNotaId(Integer notaId){
        return this.notaRepository.findById(notaId).orElse(null);
    }

    public NotaDTOResponse criarNota(@Valid NotaDTORequest notaDTORequest){
        Nota nota = modelMapper.map(notaDTORequest, Nota.class);
        // Lógica para associar o usuário e a pasta (opcional) deve ser adicionada aqui
        Nota notaSalva = this.notaRepository.save(nota);
        return modelMapper.map(notaSalva, NotaDTOResponse.class);
    }

    public NotaDTOResponse atualizarNota(Integer notaId, NotaDTORequest notaDTORequest) {
        Nota nota = this.listarPorNotaId(notaId);
        if (nota != null) {
            modelMapper.map(notaDTORequest, nota);
            Nota tempResponse = notaRepository.save(nota);
            return modelMapper.map(tempResponse, NotaDTOResponse.class);
        } else {
            return null;
        }
    }

    public NotaDTOUpdateResponse atualizarParcialmenteNota(Integer notaId, NotaDTOUpdateRequest notaDTOUpdateRequest) {
        Nota nota = this.listarPorNotaId(notaId);
        if (nota != null) {
            // Exemplo de atualização parcial: alterando título e conteúdo
            nota.setTitulo(notaDTOUpdateRequest.getTitulo());
            nota.setConteudo(notaDTOUpdateRequest.getConteudo());
            Nota tempResponse = notaRepository.save(nota);
            return modelMapper.map(tempResponse, NotaDTOUpdateResponse.class);
        }
        return null;
    }

    public void apagarNota(Integer notaId){
        // Exclusão física
        notaRepository.deleteById(notaId);
    }
}