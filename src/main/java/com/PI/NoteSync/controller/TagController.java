import com.PI.NoteSync.dto.request.TagDTORequest;
import com.PI.NoteSync.dto.request.TagDTOUpdateRequest;
import com.PI.NoteSync.dto.response.TagDTOResponse;
import com.PI.NoteSync.dto.response.TagDTOUpdateResponse;
import com.PI.NoteSync.entity.Tag;
import com.PI.NoteSync.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name="Tag", description = "API para gerenciamento de tags")
public class TagController {

    private TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar tags", description = "Endpoint para listar todas as tags do usuário autenticado")
    public ResponseEntity<List<Tag>> listarTags(){
        return ResponseEntity.ok(tagService.listarTags());
    }

    @GetMapping("/listarPorId/{tagId}")
    @Operation(summary = "Listar tag pelo ID", description = "Endpoint para obter Tag pelo seu id")
    public ResponseEntity<Tag> listarPorTagId(@PathVariable("tagId") Integer tagId) {
        Tag tag = tagService.listarPorTagId(tagId);
        if (tag == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(tag);
        }
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova Tag", description = "Endpoint para criar um novo registro de Tag")
    public ResponseEntity<TagDTOResponse> criarTag(@Valid @RequestBody TagDTORequest tag){
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.criarTag(tag));
    }

    @PutMapping("/atualizar/{tagId}")
    @Operation(summary = "Atualizar todos os dados da Tag", description = "Endpoint para atualizar todos os dados da tag")
    public ResponseEntity<TagDTOResponse> atualizarTag(@PathVariable("tagId") Integer tagId, @RequestBody TagDTORequest tagDTORequest){
        return ResponseEntity.ok(tagService.atualizarTag(tagId, tagDTORequest));
    }

    @PutMapping("/atualizarParcial/{tagId}")
    @Operation(summary = "Atualiza parcialmente a tag", description = "Endpoint para atualizar parcialmente uma tag")
    public ResponseEntity<TagDTOUpdateResponse> atualizarParcialmenteTag(
            @PathVariable("tagId") Integer tagId,
            @RequestBody TagDTOUpdateRequest tagDTOUpdateRequest){
        return ResponseEntity.ok(tagService.atualizarParcialmenteTag(tagId, tagDTOUpdateRequest));
    }

    @DeleteMapping("/apagar/{tagId}")
    @Operation(summary = "Apagar registro de tag", description = "Endpoint para apagar uma tag")
    public ResponseEntity apagarTag(@PathVariable("tagId") Integer tagId){
        tagService.apagarTag(tagId);
        return ResponseEntity.noContent().build();
    }
}