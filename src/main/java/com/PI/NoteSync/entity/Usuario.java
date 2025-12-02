package com.PI.NoteSync.entity;

import jakarta.persistence.*;
import java.util.ArrayList; // Importante
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private int id;

    @Column(name = "usuario_nome")
    private String nome;

    @Column(name = "usuario_email", unique = true) // Boa prática: email único
    private String email;

    @Column(name = "usuario_status")
    private int status;

    @Column(name = "usuario_senha")
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pasta> pastas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags;

    // CORREÇÃO CRÍTICA: Inicializar com ArrayList para evitar erro de "NullPointerException" no Login
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="usuario_role",
            // Verifique também se as colunas no banco são 'user_id' e 'role_id'
            // Se no banco for 'usuario_id', mude aqui também!
            joinColumns = @JoinColumn(name = "usuario_id"), // Geralmente segue o nome da PK
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles = new ArrayList<>(); // <--- MUDANÇA AQUI

    // Getters e Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Pasta> getPastas() {
        return pastas;
    }

    public void setPastas(List<Pasta> pastas) {
        this.pastas = pastas;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}