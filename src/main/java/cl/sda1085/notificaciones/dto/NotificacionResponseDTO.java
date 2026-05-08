package cl.sda1085.notificaciones.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class NotificacionResponseDTO {

    private Long id;
    private Long idUsuario;
    private String tipo;
    private String mensaje;
    private boolean leido;
}
