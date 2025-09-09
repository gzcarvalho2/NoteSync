package com.PI.NoteSync.dto.request;

import com.PI.NoteSync.entity.Nota;
import com.PI.NoteSync.entity.Pasta;
import com.PI.NoteSync.entity.Tag;

import java.util.List;

public class UsuarioDTOUpdateRequest {

    private int id;
    private String nome;
    private String email;
    private int status;
    private String senha;

    // Relacionamentos como no modelo
    private List<Pasta> pastas;
    private List<Nota> notas;
    private List<Tag> tags;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Pasta> getPastas() {
        return pastas;
    }

    public void setPastas(List<Pasta> pastas) {
        this.pastas = pastas;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}