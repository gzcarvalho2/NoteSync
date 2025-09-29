package com.PI.NoteSync.dto.response;

// CORREÇÃO: Importar o DTO de Nota em vez da Entidade
import com.PI.NoteSync.dto.response.NotaDTOResponse;
import com.PI.NoteSync.entity.Usuario;

import java.util.Set;

public class TagDTOUpdateResponse {

    private int id;
    private String nome;

    // Relacionamentos
    private Usuario usuario;


    private Set<NotaDTOResponse> notas;

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

    public Set<NotaDTOResponse> getNotas() {
        return notas;
    }

    public void setNotas(Set<NotaDTOResponse> notas) {
        this.notas = notas;
    }
}