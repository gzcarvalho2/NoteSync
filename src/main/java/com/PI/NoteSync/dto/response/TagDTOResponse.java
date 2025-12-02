package com.PI.NoteSync.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Importante
import com.PI.NoteSync.dto.response.NotaDTOResponse;
// MUDANÇA: Importar DTO, não Entidade
import com.PI.NoteSync.dto.response.UsuarioDTOResponse;

import java.util.Set;

public class TagDTOResponse {

    private int id;
    private String nome;

    // Relacionamentos

    // MUDANÇA 1: Usar UsuarioDTOResponse
    // Ignora as listas do usuário para não voltar para Tag
    @JsonIgnoreProperties({"tags", "pastas", "notas"})
    private UsuarioDTOResponse usuario;

    // MUDANÇA 2: Nas notas, ignora 'tags' para não voltar pra cá (loop infinito)
    // Também ignoramos 'usuario' e 'pasta' para deixar o JSON mais limpo
    @JsonIgnoreProperties({"tags", "usuario", "pasta"})
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

    // Ajuste o Getter/Setter para o tipo novo (UsuarioDTOResponse)
    public UsuarioDTOResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTOResponse usuario) {
        this.usuario = usuario;
    }

    public Set<NotaDTOResponse> getNotas() {
        return notas;
    }

    public void setNotas(Set<NotaDTOResponse> notas) {
        this.notas = notas;
    }
}