package com.PI.NoteSync.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Set;

public class UsuarioDTOResponse {
    private int id;
    private String nome;
    private String email;
    private int status;
    // Note que NÃO TEM SENHA aqui. Se aparecer senha no JSON, não está usando essa classe.

    @JsonIgnoreProperties("usuario") // A pasta não vai mostrar o usuário de novo
    private List<PastaDTOResponse> pastas;

    @JsonIgnoreProperties("usuario") // A nota não vai mostrar o usuário de novo
    private List<NotaDTOResponse> notas;

    @JsonIgnoreProperties("usuario")
    private Set<TagDTOResponse> tags;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public List<PastaDTOResponse> getPastas() { return pastas; }
    public void setPastas(List<PastaDTOResponse> pastas) { this.pastas = pastas; }

    public List<NotaDTOResponse> getNotas() { return notas; }
    public void setNotas(List<NotaDTOResponse> notas) { this.notas = notas; }

    public Set<TagDTOResponse> getTags() { return tags; }
    public void setTags(Set<TagDTOResponse> tags) { this.tags = tags; }
}