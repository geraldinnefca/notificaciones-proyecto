package cl.sda1085.notificaciones.repository;

import cl.sda1085.notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificacionRepository extends JpaRepository <Notificacion, Long> {

    //Obtener todas las notificaciones de un usuario
    List<Notificacion> findByIdUsuario (Long idUsuario);

    // Cuenta cuántas notificaciones no leídas tiene un usuario
    long countByIdUsuarioAndLeidoFalse(Long idUsuario);

    //Obtener todas las no leídas
    List <Notificacion> findByLeidoFalse();

    // Retorna true si el usuario tiene al menos una notificación sin leer
    boolean existsByIdUsuarioAndLeidoFalse(Long idUsuario);

    //Obtener las no leídas de un usuario específico
    List<Notificacion> findByIdUsuarioAndLeidoFalse(Long idUsuario);

    // Obtiene la última notificación de un usuario (la más nueva)
    Optional<Notificacion> findTopByIdUsuarioOrderByFechaCreacionDesc(Long idUsuario);
}
