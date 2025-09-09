package com.PI.NoteSync.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "nota")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nota_id")
    private int id;

    @Column(name = "nota_titulo")
    private String titulo;

    @Column(name = "nota_conteudo")
    private String conteudo;

    @Column(name = "nota_datadecriacao")
    private LocalDateTime dataDeCriacao;

    @Column(name = "nota_datadeedicao")
    private LocalDateTime dataDeEdicao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pasta_id", nullable = true) // nullable = true permite notas sem pasta
    private Pasta pasta;

    @ManyToMany
    @JoinTable(
            name = "tag_nota",
            joinColumns = @JoinColumn(name = "nota_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(LocalDateTime dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public LocalDateTime getDataDeEdicao() {
        return dataDeEdicao;
    }

    public void setDataDeEdicao(LocalDateTime dataDeEdicao) {
        this.dataDeEdicao = dataDeEdicao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pasta getPasta() {
        return pasta;
    }

    public void setPasta(Pasta pasta) {
        this.pasta = pasta;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}