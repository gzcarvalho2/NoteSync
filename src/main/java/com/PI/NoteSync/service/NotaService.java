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
import com.PI.NoteSync.repository.PastaRepository;
import com.PI.NoteSync.repository.TagRepository;
import com.PI.NoteSync.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PastaRepository pastaRepository;
    private final TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    public NotaService(NotaRepository notaRepository, UsuarioRepository usuarioRepository, PastaRepository pastaRepository, TagRepository tagRepository) {
        this.notaRepository = notaRepository;
        this.usuarioRepository = usuarioRepository;
        this.pastaRepository = pastaRepository;
        this.tagRepository = tagRepository;
    }

    public List<Nota> listarNotas() {
        return this.notaRepository.findAll();
    }

    public Nota listarPorNotaId(Integer notaId) {
        return this.notaRepository.findById(notaId)
                .orElseThrow(() -> new EntityNotFoundException("Nota não encontrada com id: " + notaId));
    }

    // MUDANÇA: Agora recebe o email do usuário logado (token)
    public NotaDTOResponse criarNota(NotaDTORequest notaDTO, String emailUsuario) {
        Nota nota = new Nota();

        // 1. Mapeia dados simples
        nota.setTitulo(notaDTO.getTitulo());
        nota.setConteudo(notaDTO.getConteudo());
        nota.setDataDeCriacao(LocalDateTime.now());
        // Se tiver data de edição na criação, pode setar também
        // nota.setDataDeEdicao(LocalDateTime.now());

        // 2. Busca e define a Pasta pelo ID (pastaId)
        if (notaDTO.getPastaId() != null) {
            Pasta pasta = pastaRepository.findById(notaDTO.getPastaId())
                    .orElseThrow(() -> new EntityNotFoundException("Pasta não encontrada com ID: " + notaDTO.getPastaId()));
            nota.setPasta(pasta);
        }

        // 3. Busca e define as Tags pelos IDs (tagIds)
        if (notaDTO.getTagIds() != null && !notaDTO.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(notaDTO.getTagIds());
            nota.setTags(new HashSet<>(tags));
        }

        // 4. Define o Usuário (Dono da nota) baseado no Token
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuário logado não encontrado no banco."));
        nota.setUsuario(usuario);

        // 5. Salva e Retorna
        Nota notaSalva = this.notaRepository.save(nota);
        return modelMapper.map(notaSalva, NotaDTOResponse.class);
    }

    public NotaDTOResponse atualizarNota(Integer notaId, NotaDTORequest notaDTORequest) {
        Nota nota = this.listarPorNotaId(notaId);

        // Atualiza campos simples
        nota.setTitulo(notaDTORequest.getTitulo());
        nota.setConteudo(notaDTORequest.getConteudo());
        nota.setDataDeEdicao(LocalDateTime.now());

        // Atualiza Pasta se o ID mudou
        if (notaDTORequest.getPastaId() != null) {
            Pasta pasta = pastaRepository.findById(notaDTORequest.getPastaId())
                    .orElseThrow(() -> new EntityNotFoundException("Pasta não encontrada"));
            nota.setPasta(pasta);
        }

        // Atualiza Tags se a lista mudou
        if (notaDTORequest.getTagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(notaDTORequest.getTagIds());
            nota.setTags(new HashSet<>(tags));
        }

        Nota notaSalva = notaRepository.save(nota);
        return modelMapper.map(notaSalva, NotaDTOResponse.class);
    }

    public NotaDTOUpdateResponse atualizarParcialmenteNota(Integer notaId, NotaDTOUpdateRequest notaDTOUpdateRequest) {
        Nota nota = this.listarPorNotaId(notaId);

        if (notaDTOUpdateRequest.getTitulo() != null) {
            nota.setTitulo(notaDTOUpdateRequest.getTitulo());
        }
        if (notaDTOUpdateRequest.getConteudo() != null) {
            nota.setConteudo(notaDTOUpdateRequest.getConteudo());
        }

        nota.setDataDeEdicao(LocalDateTime.now());

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