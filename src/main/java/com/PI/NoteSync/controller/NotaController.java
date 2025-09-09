import com.PI.NoteSync.dto.request.NotaDTORequest;
import com.PI.NoteSync.dto.request.NotaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.NotaDTOResponse;
import com.PI.NoteSync.dto.response.NotaDTOUpdateResponse;
import com.PI.NoteSync.entity.Nota;
import com.PI.NoteSync.service.NotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/nota")
@Tag(name="Nota", description = "API para gerenciamento de notas")

public class NotaController {

    private NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar notas", description = "Endpoint para listar todas as notas")
    public ResponseEntity<List<Nota>> listarNotas(){
        return ResponseEntity.ok(notaService.listarNotas());
    }

    @GetMapping("/listarPorId/{notaId}")
    @Operation(summary = "Listar nota pelo ID", description = "Endpoint para obter Nota pelo seu id")
    public ResponseEntity<Nota> listarPorNotaId(@PathVariable("notaId") Integer notaId) {
        Nota nota = notaService.listarPorNotaId(notaId);
        if (nota == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(nota);
        }
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova Nota", description = "Endpoint para criar um novo registro de Nota")
    public ResponseEntity<NotaDTOResponse> criarNota(@Valid @RequestBody NotaDTORequest nota){
        return ResponseEntity.status(HttpStatus.CREATED).body(notaService.criarNota(nota));
    }

    @PutMapping("/atualizar/{notaId}")
    @Operation(summary = "Atualizar todos os dados da Nota", description = "Endpoint para atualizar todos os dados da nota")
    public ResponseEntity<NotaDTOResponse> atualizarNota(@PathVariable("notaId") Integer notaId, @RequestBody NotaDTORequest notaDTORequest){
        return ResponseEntity.ok(notaService.atualizarNota(notaId, notaDTORequest));
    }

    @PutMapping("/atualizarParcial/{notaId}")
    @Operation(summary = "Atualiza parcialmente a nota", description = "Endpoint para atualizar parcialmente uma nota")
    public ResponseEntity<NotaDTOUpdateResponse> atualizarParcialmenteNota(
            @PathVariable("notaId") Integer notaId,
            @RequestBody NotaDTOUpdateRequest notaDTOUpdateRequest){
        return ResponseEntity.ok(notaService.atualizarParcialmenteNota(notaId, notaDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{notaId}")
    @Operation(summary = "Apagar registro de nota", description = "Endpoint para apagar uma nota")
    public ResponseEntity apagarNota(@PathVariable("notaId") Integer notaId){
        notaService.apagarNota(notaId);
        return ResponseEntity.noContent().build();
    }
}