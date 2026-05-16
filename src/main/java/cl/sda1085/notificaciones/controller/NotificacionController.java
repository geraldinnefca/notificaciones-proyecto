package cl.sda1085.notificaciones.controller;

import cl.sda1085.notificaciones.dto.NotificacionRequestDTO;
import cl.sda1085.notificaciones.dto.NotificacionResponseDTO;
import cl.sda1085.notificaciones.exception.ResourceNotFoundException;
import cl.sda1085.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {

    //Conexión con 'service'
    private final NotificacionService notificacionService;


    //------------------------------
    //CRUD estándar
    //------------------------------

    //Obtener todas las notificaciones
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(notificacionService.obtenerTodas());
    }

    //Obtener notificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return notificacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //Guardar (crear) nueva notificación
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> guardar(@Valid @RequestBody NotificacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.guardar(dto));
    }

    //Actualizar notificación existente
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody NotificacionRequestDTO dto) {
        return notificacionService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    //Eliminar notificación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    //------------------------------
    //CRUD personalizado
    //------------------------------

    //Obtener todas las notificaciones de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(notificacionService.obtenerPorUsuario(idUsuario));
    }

    //Obtener todas las no leídas del sistema
    @GetMapping("/pendientes")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerNoLeidas() {
        return ResponseEntity.ok(notificacionService.obtenerNoLeidas());
    }

    //Obtener las no leídas de un usuario específico
    @GetMapping("/usuario/{idUsuario}/pendientes")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerPendientesUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(notificacionService.obtenerNoLeidasPorUsuario(idUsuario));
    }

    //Contar cuántas notificaciones pendientes tiene un usuario
    @GetMapping("/usuario/{idUsuario}/conteo")
    public ResponseEntity<Long> contarPendientes(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(notificacionService.contarPendientesPorUsuario(idUsuario));
    }

    //Saber si un usuario tiene al menos una notificación sin leer
    @GetMapping("/usuario/{idUsuario}/existe-pendiente")
    public ResponseEntity<Boolean> tienePendientes(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(notificacionService.tieneNotificacionesPendientes(idUsuario));
    }

    //Obtener la última notificación recibida por el usuario
    @GetMapping("/usuario/{idUsuario}/ultima")
    public ResponseEntity<NotificacionResponseDTO> obtenerUltima(@PathVariable Long idUsuario) {
        return notificacionService.obtenerUltimaPorUsuario(idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PatchMapping("/{id}/leer")
    public ResponseEntity<NotificacionResponseDTO> marcarComoLeida(@PathVariable Long id) {
        return notificacionService.marcarComoLeida(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
