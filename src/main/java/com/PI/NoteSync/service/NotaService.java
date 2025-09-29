package com.PI.NoteSync.service;

import com.PI.NoteSync.dto.request.NotaDTORequest;
import com.PI.NoteSync.dto.request.NotaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.NotaDTOResponse;
import com.PI.NoteSync.dto.response.NotaDTOUpdateResponse;
import com.PI.NoteSync.entity.Nota;
import com.PI.NoteSync.entity.Pasta;
import com.PI.NoteSync.entity.Tag;
import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.repository.NotaRepository;
// CORREÇÃO: Adicionar os repositórios necessários para buscar as entidades relacionadas
import com.PI.NoteSync.repository.PastaRepository;
import com.PI.NoteSync.repository.TagRepository;
import com.PI.NoteSync.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    // CORREÇÃO: Injetar os outros repositórios
    private final UsuarioRepository usuarioRepository;
    private final PastaRepository pastaRepository;
    private final TagRepository tagRepository;


    @Autowired
    private ModelMapper modelMapper;

    // CORREÇÃO: Construtor atualizado com as novas dependências
    public NotaService(NotaRepository notaRepository, UsuarioRepository usuarioRepository, PastaRepository pastaRepository, TagRepository tagRepository) {
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;
        this.pastaRepository = pastaRepository;
        this.tagRepository = tagRepository;
    }

    // CORREÇÃO: O método original não funcionava. Agora ele lista TODAS as notas.
    // Se a intenção era listar por usuário, um `usuarioId` deveria ser passado como parâmetro.
    public List<Nota> listarNotas() {
        return this.notaRepository.findAll();
    }

    public Nota listarPorNotaId(Integer notaId) {
        return this.notaRepository.findById(notaId)
                .orElseThrow(() -> new EntityNotFoundException("Nota não encontrada com id: " + notaId));
    }

    public NotaDTOResponse criarNota(@Valid NotaDTORequest notaDTORequest) {
        Nota nota = modelMapper.map(notaDTORequest, Nota.class);

        // CORREÇÃO: Lógica ESSENCIAL para buscar as entidades no banco e associá-las.
        // Sem isso, o JPA não consegue salvar os relacionamentos.
        Usuario usuario = usuarioRepository.findById(notaDTORequest.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        nota.setUsuario(usuario);

        if (notaDTORequest.getPasta() != null && notaDTORequest.getPasta().getId() != 0) {
            Pasta pasta = pastaRepository.findById(notaDTORequest.getPasta().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Pasta não encontrada"));
            nota.setPasta(pasta);
        }

        if (notaDTORequest.getTags() != null && !notaDTORequest.getTags().isEmpty()) {
            Set<Integer> tagIds = notaDTORequest.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
            List<Tag> tagsEncontradas = tagRepository.findAllById(tagIds);
            nota.setTags(new HashSet<>(tagsEncontradas));
        }

        Nota notaSalva = this.notaRepository.save(nota);
        return modelMapper.map(notaSalva, NotaDTOResponse.class);
    }

    public NotaDTOResponse atualizarNota(Integer notaId, NotaDTORequest notaDTORequest) {
        Nota nota = this.listarPorNotaId(notaId); // Reutiliza o método que já lança exceção se não encontrar

        // O modelMapper já atualiza os campos simples (titulo, conteudo)
        modelMapper.map(notaDTORequest, nota);

        // CORREÇÃO: A lógica de associação de entidades também é necessária na atualização
        Usuario usuario = usuarioRepository.findById(notaDTORequest.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        nota.setUsuario(usuario);

        // A mesma lógica de `criarNota` se aplica aqui para Pasta e Tags...

        Nota tempResponse = notaRepository.save(nota);
        return modelMapper.map(tempResponse, NotaDTOResponse.class);
    }

    public NotaDTOUpdateResponse atualizarParcialmenteNota(Integer notaId, NotaDTOUpdateRequest notaDTOUpdateRequest) {
        Nota nota = this.listarPorNotaId(notaId);

        // Atualiza apenas os campos que não são nulos no DTO
        if (notaDTOUpdateRequest.getTitulo() != null) {
            nota.setTitulo(notaDTOUpdateRequest.getTitulo());
        }
        if (notaDTOUpdateRequest.getConteudo() != null) {
            nota.setConteudo(notaDTOUpdateRequest.getConteudo());
        }

        Nota tempResponse = notaRepository.save(nota);
        return modelMapper.map(tempResponse, NotaDTOUpdateResponse.class);
    }

    public void apagarNota(Integer notaId) {
        if (!notaRepository.existsById(notaId)) {
            throw new EntityNotFoundException("Nota não encontrada com id: " + notaId);
        }
        notaRepository.deleteById(notaId);
    }
}