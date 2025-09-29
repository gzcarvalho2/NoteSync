package com.PI.NoteSync.repository;

import com.PI.NoteSync.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {

    // CORREÇÃO: O nome do parâmetro em @Param deve ser o mesmo usado na query com ":"
    @Query("SELECT n FROM Nota n WHERE n.usuario.id = :usuarioId")
    List<Nota> listarNotasPorUsuario(@Param("usuarioId") Integer usuarioId);

    // CORREÇÃO: O nome do parâmetro em @Param deve ser o mesmo usado na query com ":"
    @Query("SELECT n FROM Nota n WHERE n.id = :id")
    Optional<Nota> obterNotaPeloId(@Param("id") Integer id);

    @Query("SELECT n FROM Nota n WHERE n.pasta.id = :pastaId")
    List<Nota> listarNotasPorPasta(@Param("pastaId") Integer pastaId);

    @Query("SELECT n FROM Nota n JOIN n.tags t WHERE t.id = :tagId")
    List<Nota> listarNotasPorTag(@Param("tagId") Integer tagId);
}