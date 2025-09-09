package com.PI.NoteSync.dto.request;
import com.PI.NoteSync.entity.Nota;
import com.PI.NoteSync.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class PastaDTORequest {

    private int id;
    private String nome;
    private String descricao;
    private LocalDateTime dataDeCriacao;

    // Relacionamentos
    private Usuario usuario;
    private List<Nota> notas;

    // Getters e Setters
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

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
}
