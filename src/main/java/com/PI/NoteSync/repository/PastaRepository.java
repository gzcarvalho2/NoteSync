package com.PI.NoteSync.repository;

import com.PI.NoteSync.entity.Pasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PastaRepository extends JpaRepository<Pasta, Integer> {

    // Lista todas as pastas de um usuário específico
    @Query("SELECT p FROM Pasta p WHERE p.usuario.id = :usuarioId")
    List<Pasta> listarPastasPorUsuario(@Param("usuarioId") Integer usuarioId);

    // Busca uma pasta específica pelo seu ID
    @Query("SELECT p FROM Pasta p WHERE p.id = :id")
    Optional<Pasta> obterPastaPeloId(@Param("id") Integer pastaId);
}