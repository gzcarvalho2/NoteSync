package com.PI.NoteSync.repository;

import com.PI.NoteSync.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

    // Lista todas as tags de um usuário específico
    @Query("SELECT t FROM Tag t WHERE t.usuario.id = :usuarioId")
    List<Tag> listarTagsPorUsuario(@Param("usuarioId") Integer usuarioId);

    // Busca uma tag específica pelo seu ID
    @Query("SELECT t FROM Tag t WHERE t.id = :id")
    Optional<Tag> obterTagPeloId(@Param("id") Integer tagId);
}
