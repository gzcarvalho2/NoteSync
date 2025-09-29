package com.PI.NoteSync.entity;

import jakarta.persistence.*;
// CORREÇÃO: Adicionar import para FetchType
import org.hibernate.annotations.CreationTimestamp;
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

    // CORREÇÃO: Deixar o Hibernate gerenciar a data de criação automaticamente
    @CreationTimestamp
    @Column(name = "pasta_datadecriacao", updatable = false)
    private LocalDateTime dataDeCriacao;

    // CORREÇÃO: Adicionar FetchType.LAZY para otimizar a performance
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // CORREÇÃO: Cascade ALL garante que as notas sejam salvas/apagadas junto com a pasta
    @OneToMany(mappedBy = "pasta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas;

    // Getters e Setters ...
    // (Omitidos para brevidade)


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