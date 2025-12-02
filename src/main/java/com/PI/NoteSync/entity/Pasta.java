package com.PI.NoteSync.entity;

import jakarta.persistence.*;
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

    // --- NOVO CAMPO ---
    @Column(name = "pasta_status")
    private Integer status;
    // ------------------

    @CreationTimestamp
    @Column(name = "pasta_datadecriacao", updatable = false)
    private LocalDateTime dataDeCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id") // Se der erro de coluna, tente "user_id"
    private Usuario usuario;

    @OneToMany(mappedBy = "pasta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas;

    // --- GETTERS E SETTERS DO NOVO CAMPO ---
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    // ... (Mantenha os outros getters e setters)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataDeCriacao() { return dataDeCriacao; }
    public void setDataDeCriacao(LocalDateTime dataDeCriacao) { this.dataDeCriacao = dataDeCriacao; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<Nota> getNotas() { return notas; }
    public void setNotas(List<Nota> notas) { this.notas = notas; }
}