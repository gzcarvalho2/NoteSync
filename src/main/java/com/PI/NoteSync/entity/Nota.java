package com.PI.NoteSync.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "nota")
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nota_id") // Mapeamento correto
    private int id;

    @Column(name = "nota_titulo") // Mapeamento correto
    private String titulo;

    @Column(name = "nota_conteudo") // Mapeamento correto
    private String conteudo;

    // --- NOVO CAMPO OBRIGATÓRIO ---
    @Column(name = "nota_status")
    private Integer status;
    // ------------------------------

    @CreationTimestamp
    @Column(name = "nota_datadecriacao", updatable = false)
    private LocalDateTime dataDeCriacao;

    @UpdateTimestamp
    @Column(name = "nota_datadeedicao")
    private LocalDateTime dataDeEdicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id") // Verifique se é usuario_id ou user_id
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pasta_id") // Verifique se é pasta_id
    private Pasta pasta;

    @ManyToMany
    @JoinTable(
            name = "tag_nota", // Verifique se existe essa tabela intermediária
            joinColumns = @JoinColumn(name = "nota_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    // --- Getters e Setters ---

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }
    public LocalDateTime getDataDeCriacao() { return dataDeCriacao; }
    public void setDataDeCriacao(LocalDateTime dataDeCriacao) { this.dataDeCriacao = dataDeCriacao; }
    public LocalDateTime getDataDeEdicao() { return dataDeEdicao; }
    public void setDataDeEdicao(LocalDateTime dataDeEdicao) { this.dataDeEdicao = dataDeEdicao; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Pasta getPasta() { return pasta; }
    public void setPasta(Pasta pasta) { this.pasta = pasta; }
    public Set<Tag> getTags() { return tags; }
    public void setTags(Set<Tag> tags) { this.tags = tags; }
}