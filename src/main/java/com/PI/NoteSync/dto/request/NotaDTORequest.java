package com.PI.NoteSync.dto.request;

import java.util.Set;

public class NotaDTORequest {

    // Não precisamos do ID aqui (é gerado no banco)

    private String titulo;
    private String conteudo;

    // MUDANÇA CRÍTICA: Recebemos apenas o ID da pasta
    private Integer pastaId;

    // MUDANÇA CRÍTICA: Recebemos apenas os IDs das tags (opcional)
    private Set<Integer> tagIds;

    // --- Getters e Setters ---

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

    public Integer getPastaId() {
        return pastaId;
    }

    public void setPastaId(Integer pastaId) {
        this.pastaId = pastaId;
    }

    public Set<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Integer> tagIds) {
        this.tagIds = tagIds;
    }
}