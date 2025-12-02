package com.PI.NoteSync.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.Set;

// MUDANÇA CRÍTICA: Não importe com.PI.NoteSync.entity.Usuario !!!
// Importe apenas os DTOs
import com.PI.NoteSync.dto.response.UsuarioDTOResponse;
import com.PI.NoteSync.dto.response.PastaDTOResponse;
import com.PI.NoteSync.dto.response.TagDTOResponse;

public class NotaDTOResponse {

    private int id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime dataDeEdicao;

    // --- RELACIONAMENTOS ---

    // ERRO ANTERIOR: private Usuario usuario;
    // CORREÇÃO: Usar o DTO e bloquear as listas dele para não ter loop
    @JsonIgnoreProperties({"pastas", "notas", "tags"})
    private UsuarioDTOResponse usuario;

    // ERRO ANTERIOR: private Pasta pasta;
    // CORREÇÃO: Usar o DTO e bloquear notas/usuario da pasta
    @JsonIgnoreProperties({"notas", "usuario"})
    private PastaDTOResponse pasta;

    // ERRO ANTERIOR: private Set<Tag> tags;
    // CORREÇÃO: Usar o DTO de Tag
    @JsonIgnoreProperties({"notas", "usuario"})
    private Set<TagDTOResponse> tags;


    // --- GETTERS E SETTERS ---
    // ATENÇÃO: Atualize os tipos nos Getters e Setters também!

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public LocalDateTime getDataDeCriacao() { return dataDeCriacao; }
    public void setDataDeCriacao(LocalDateTime dataDeCriacao) { this.dataDeCriacao = dataDeCriacao; }

    public LocalDateTime getDataDeEdicao() { return dataDeEdicao; }
    public void setDataDeEdicao(LocalDateTime dataDeEdicao) { this.dataDeEdicao = dataDeEdicao; }

    // MUDANÇA DE TIPO AQUI
    public UsuarioDTOResponse getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTOResponse usuario) { this.usuario = usuario; }

    // MUDANÇA DE TIPO AQUI
    public PastaDTOResponse getPasta() { return pasta; }
    public void setPasta(PastaDTOResponse pasta) { this.pasta = pasta; }

    // MUDANÇA DE TIPO AQUI
    public Set<TagDTOResponse> getTags() { return tags; }
    public void setTags(Set<TagDTOResponse> tags) { this.tags = tags; }
}