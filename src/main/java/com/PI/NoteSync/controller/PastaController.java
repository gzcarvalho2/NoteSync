import com.PI.NoteSync.dto.request.PastaDTORequest;
import com.PI.NoteSync.dto.request.PastaDTOUpdateRequest;
import com.PI.NoteSync.dto.response.PastaDTOResponse;
import com.PI.NoteSync.dto.response.PastaDTOUpdateResponse;
import com.PI.NoteSync.entity.Pasta;
import com.PI.NoteSync.service.PastaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pasta")
@Tag(name="Pasta", description = "API para gerenciamento de pastas de notas")
public class PastaController {

    private PastaService pastaService;

    public PastaController(PastaService pastaService) {
        this.pastaService = pastaService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar pastas", description = "Endpoint para listar todas as pastas do usuário autenticado")
    public ResponseEntity<List<Pasta>> listarPastas(){
        return ResponseEntity.ok(pastaService.listarPastas());
    }

    @GetMapping("/listarPorId/{pastaId}")
    @Operation(summary = "Listar pasta pelo ID", description = "Endpoint para obter Pasta pelo seu id")
    public ResponseEntity<Pasta> listarPorPastaId(@PathVariable("pastaId") Integer pastaId) {
        Pasta pasta = pastaService.listarPorPastaId(pastaId);
        if (pasta == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pasta);
        }
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova Pasta", description = "Endpoint para criar um novo registro de Pasta")
    public ResponseEntity<PastaDTOResponse> criarPasta(@Valid @RequestBody PastaDTORequest pasta){
        return ResponseEntity.status(HttpStatus.CREATED).body(pastaService.criarPasta(pasta));
    }

    @PutMapping("/atualizar/{pastaId}")
    @Operation(summary = "Atualizar todos os dados da Pasta", description = "Endpoint para atualizar todos os dados da pasta")
    public ResponseEntity<PastaDTOResponse> atualizarPasta(@PathVariable("pastaId") Integer pastaId, @RequestBody PastaDTORequest pastaDTORequest){
        return ResponseEntity.ok(pastaService.atualizarPasta(pastaId, pastaDTORequest));
    }

    @PutMapping("/atualizarParcial/{pastaId}")
    @Operation(summary = "Atualiza parcialmente a pasta", description = "Endpoint para atualizar parcialmente uma pasta")
    public ResponseEntity<PastaDTOUpdateResponse> atualizarParcialmentePasta(
            @PathVariable("pastaId") Integer pastaId,
            @RequestBody PastaDTOUpdateRequest pastaDTOUpdateRequest){
        return ResponseEntity.ok(pastaService.atualizarParcialmentePasta(pastaId, pastaDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{pastaId}")
    @Operation(summary = "Apagar registro de pasta", description = "Endpoint para apagar uma pasta")
    public ResponseEntity apagarPasta(@PathVariable("pastaId") Integer pastaId){
        pastaService.apagarPasta(pastaId);
        return ResponseEntity.noContent().build();
    }
}