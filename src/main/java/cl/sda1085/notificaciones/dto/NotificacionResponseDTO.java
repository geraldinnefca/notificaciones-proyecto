package cl.sda1085.notificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionResponseDTO {

    private Long id;
    private Long idUsuario;
    private String tipo;
    private String mensaje;
    private boolean leido;
    private LocalDateTime fechaCreacion;
}
