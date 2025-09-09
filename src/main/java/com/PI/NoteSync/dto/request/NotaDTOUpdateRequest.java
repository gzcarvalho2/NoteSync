package com.PI.NoteSync.dto.request;

import com.PI.NoteSync.entity.Pasta;
import com.PI.NoteSync.entity.Tag;
import com.PI.NoteSync.entity.Usuario;

import java.time.LocalDateTime;
import java.util.Set;

public class NotaDTOUpdateRequest {

    private int id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime dataDeEdicao;

    // Relacionamentos
    private Usuario usuario;
    private Pasta pasta;
    private Set<Tag> tags;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDateTime dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public LocalDateTime getDataDeEdicao() {
        return dataDeEdicao;
    }

    public void setDataDeEdicao(LocalDateTime dataDeEdicao) {
        this.dataDeEdicao = dataDeEdicao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pasta getPasta() {
        return pasta;
    }

    public void setPasta(Pasta pasta) {
        this.pasta = pasta;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}