package com.PI.NoteSync.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PastaDTORequest {

    // Removemos 'id' (o banco gera)
    // Removemos 'dataDeCriacao' (o backend define a hora atual)
    // Removemos 'usuario' (vamos pegar do Token de segurança)
    // Removemos 'notas' (criamos a pasta vazia primeiro)

    @NotBlank(message = "O nome da pasta é obrigatório")
    private String nome;

    private String descricao;

    // --- Getters e Setters ---

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
}