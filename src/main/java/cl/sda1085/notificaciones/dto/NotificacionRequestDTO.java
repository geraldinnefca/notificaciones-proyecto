package cl.sda1085.notificaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NotificacionRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio.")
    private Long idUsuario;

    @NotBlank(message = "El tipo de notificación no puede estar vacío.")
    @Size(max = 50, message = "El tipo de notificación no puede exceder los 50 caracteres.")
    private String tipo;

    @NotBlank(message = "El mensaje no puede estar vacío.")
    private String mensaje;
}
