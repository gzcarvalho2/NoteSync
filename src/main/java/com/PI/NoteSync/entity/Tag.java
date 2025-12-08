package com.PI.NoteSync.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore; // Importante para não dar loop no JSON

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id") // Verifique se no banco é 'tag_id' ou 'id'
    private int id;

    @Column(name = "tag_nome")
    private String nome;

    // --- CORREÇÃO PREVENTIVA: Adicionar Status ---
    // Se a tabela Pasta tinha status, a Tag provavelmente também tem.
    @Column(name = "tag_status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id") // <--- ATENÇÃO: Verifique se no banco é 'usuario_id' ou 'user_id'
    @JsonIgnore // Evita que o usuário apareça no JSON da Tag e cause loop
    private Usuario usuario;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}