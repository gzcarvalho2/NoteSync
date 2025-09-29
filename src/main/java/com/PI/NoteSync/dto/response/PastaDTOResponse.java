package com.PI.NoteSync.dto.response;


import com.PI.NoteSync.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class PastaDTOResponse {

    private int id;
    private String nome;
    private String descricao;
    private LocalDateTime dataDeCriacao;


    private Usuario usuario;


    private List<NotaDTOResponse> notas;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDateTime dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<NotaDTOResponse> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDTOResponse> notas) {
        this.notas = notas;
    }
}