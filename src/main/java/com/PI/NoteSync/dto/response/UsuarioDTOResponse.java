package com.PI.NoteSync.dto.response;

// CORREÇÃO: Importar os DTOs das outras entidades
import com.PI.NoteSync.dto.response.NotaDTOResponse;
import com.PI.NoteSync.dto.response.PastaDTOResponse;
import com.PI.NoteSync.dto.response.TagDTOResponse;

import java.util.List;
import java.util.Set; // Tag usa Set, vamos manter a consistência

public class UsuarioDTOResponse {
    private int id;
    private String nome;
    private String email;
    private int status;

    // Campo 'senha' já havia sido removido (correto)

    private List<PastaDTOResponse> pastas;
    private List<NotaDTOResponse> notas;
    private Set<TagDTOResponse> tags;

    // Getters e Setters ...


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

    public List<PastaDTOResponse> getPastas() {
        return pastas;
    }

    public void setPastas(List<PastaDTOResponse> pastas) {
        this.pastas = pastas;
    }

    public List<NotaDTOResponse> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDTOResponse> notas) {
        this.notas = notas;
    }

    public Set<TagDTOResponse> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTOResponse> tags) {
        this.tags = tags;
    }
}