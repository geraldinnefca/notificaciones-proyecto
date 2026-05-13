package cl.sda1085.notificaciones.exception;

public class NotificacionNotFoundException extends RuntimeException   {
    public NotificacionNotFoundException(Long id) {
        super("No se encontró la notificación con el ID: " + id);
    }
}
