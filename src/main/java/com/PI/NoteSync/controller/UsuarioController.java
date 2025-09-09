import com.PI.NoteSync.dto.request.UsuarioDTORequest;
import com.PI.NoteSync.dto.request.UsuarioDTOUpdateRequest;
import com.PI.NoteSync.dto.response.UsuarioDTOResponse;
import com.PI.NoteSync.dto.response.UsuarioDTOUpdateResponse;
import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuario")
@Tag(name="Usuario", description = "API para gerenciamento de usuários")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar usuários", description = "Endpoint para listar todos os usuários")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/listarPorId/{usuarioId}")
    @Operation(summary = "Listar usuário pelo ID", description = "Endpoint para obter Usuário pelo seu id")
    public ResponseEntity<Usuario> listarPorUsuarioId(@PathVariable("usuarioId") Integer usuarioId) {
        Usuario usuario = usuarioService.listarPorUsuarioId(usuarioId);
        if (usuario == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuario);
        }
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo Usuário", description = "Endpoint para criar um novo registro de Usuário")
    public ResponseEntity<UsuarioDTOResponse> criarUsuario(@Valid @RequestBody UsuarioDTORequest usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criarUsuario(usuario));
    }

    @PutMapping("/atualizar/{usuarioId}")
    @Operation(summary = "Atualizar todos os dados do Usuário", description = "Endpoint para atualizar todos os dados do usuário")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(@PathVariable("usuarioId") Integer usuarioId, @RequestBody UsuarioDTORequest usuarioDTORequest){
        return ResponseEntity.ok(usuarioService.atualizarUsuario(usuarioId, usuarioDTORequest));
    }

    @PutMapping("/atualizarStatus/{usuarioId}")
    @Operation(summary = "Atualiza o campo status do usuário", description = "Endpoint para atualizar o status do usuário")
    public ResponseEntity<UsuarioDTOUpdateResponse> atualizarStatusUsuario(
            @PathVariable("usuarioId") Integer usuarioId,
            @RequestBody UsuarioDTOUpdateRequest usuarioDTOUpdateRequest){
        return ResponseEntity.ok(usuarioService.atualizarStatusUsuario(usuarioId, usuarioDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{usuarioId}")
    @Operation(summary = "Apagar registro de usuário", description = "Endpoint para apagar um usuário")
    public ResponseEntity apagarUsuario(@PathVariable("usuarioId") Integer usuarioId){
        usuarioService.apagarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }
}