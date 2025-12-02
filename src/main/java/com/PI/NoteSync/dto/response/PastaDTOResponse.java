package com.PI.NoteSync.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;

// Importe os DTOs, NÃO as Entidades
import com.PI.NoteSync.dto.response.UsuarioDTOResponse;
import com.PI.NoteSync.dto.response.NotaDTOResponse;

public class PastaDTOResponse {

    private int id;
    private String nome;
    private String descricao;
    private LocalDateTime dataDeCriacao;

    // MUDANÇA CRÍTICA: Tipo mudou de Usuario para UsuarioDTOResponse
    @JsonIgnoreProperties({"pastas", "notas", "tags"})
    private UsuarioDTOResponse usuario;

    // Lista de notas (Use DTO)
    @JsonIgnoreProperties({"pasta", "usuario", "tags"})
    private List<NotaDTOResponse> notas;

    // --- GETTERS E SETTERS (Atualizados) ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataDeCriacao() { return dataDeCriacao; }
    public void setDataDeCriacao(LocalDateTime dataDeCriacao) { this.dataDeCriacao = dataDeCriacao; }

    public UsuarioDTOResponse getUsuario() { return usuario; }
    public void setUsuario(UsuarioDTOResponse usuario) { this.usuario = usuario; }

    public List<NotaDTOResponse> getNotas() { return notas; }
    public void setNotas(List<NotaDTOResponse> notas) { this.notas = notas; }
}