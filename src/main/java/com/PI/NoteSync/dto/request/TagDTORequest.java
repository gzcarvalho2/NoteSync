package com.PI.NoteSync.dto.request;

import jakarta.validation.constraints.NotBlank;

public class TagDTORequest {

    // Removemos 'id' (gerado pelo banco)
    // Removemos 'usuario' (vem do token)
    // Removemos 'notas' (tag nova começa vazia)

    @NotBlank(message = "O nome da tag é obrigatório")
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}