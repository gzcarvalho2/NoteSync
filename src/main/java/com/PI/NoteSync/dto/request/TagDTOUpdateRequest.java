package com.PI.NoteSync.dto.request;

import com.PI.NoteSync.entity.Nota;
import com.PI.NoteSync.entity.Usuario;

import java.util.Set;

public class TagDTOUpdateRequest {

    private int id;
    private String nome;

    // Relacionamentos
    private Usuario usuario;
    private Set<Nota> notas;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Nota> getNotas() {
        return notas;
    }

    public void setNotas(Set<Nota> notas) {
        this.notas = notas;
    }
}