package com.PI.NoteSync.entity;

import jakarta.persistence.*;

@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id") // <--- COLOQUE O NOME EXATO QUE ESTÁ NO BANCO
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name") // <--- Confira se no banco é 'name' ou 'role_name'
    private RoleName name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}