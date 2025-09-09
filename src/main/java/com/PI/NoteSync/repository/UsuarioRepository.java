package com.PI.NoteSync.repository;
import com.PI.NoteSync.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método para exclusão lógica, atualizando o status para -1
    @Transactional
    @Modifying
    @Query("UPDATE Usuario u SET u.status = -1 WHERE u.id = :id")
    void apagadoLogicoUsuario(@Param("id") Integer usuarioId);

    // Lista apenas os usuários que não foram logicamente excluídos
    @Query("SELECT u FROM Usuario u WHERE u.status >= 0")
    List<Usuario> listarUsuariosAtivos();

    // Busca um usuário específico pelo ID, desde que não tenha sido excluído
    @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.status >= 0")
    Optional<Usuario> obterUsuarioPeloId(@Param("id") Integer usuarioId);

    // Query essencial para o login
    Optional<Usuario> findByEmail(String email);
}
