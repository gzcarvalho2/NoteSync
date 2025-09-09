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

    // Lista todas as notas de um usuário específico
    @Query("SELECT n FROM Nota n WHERE n.usuario.id = :usuario_id")
    List<Nota> listarNotasPorUsuario(@Param("usuario_id") Integer usuarioId);

    // Busca uma nota específica pelo seu ID
    @Query("SELECT n FROM Nota n WHERE n.id = :nota_id")
    Optional<Nota> obterNotaPeloId(@Param("id") Integer notaId);

    // Lista todas as notas dentro de uma pasta específica
    @Query("SELECT n FROM Nota n WHERE n.pasta.id = :pasta_id")
    List<Nota> listarNotasPorPasta(@Param("pasta_id") Integer pastaId);

    // Lista todas as notas associadas a uma tag específica
    @Query("SELECT n FROM Nota n JOIN n.tags t WHERE t.id = :tag_id")
    List<Nota> listarNotasPorTag(@Param("tag_id") Integer tagId);
}
