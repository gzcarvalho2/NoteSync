package com.PI.NoteSync.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pasta")
public class Pasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pasta_id")
    private int id;

    @Column(name = "pasta_nome")
    private String nome;

    @Column(name = "pasta_descricao")
    private String descricao;

    @Column(name = "pasta_datadecriacao")
    private LocalDateTime dataDeCriacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "pasta")
    private List<Nota> notas;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDateTime dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
}