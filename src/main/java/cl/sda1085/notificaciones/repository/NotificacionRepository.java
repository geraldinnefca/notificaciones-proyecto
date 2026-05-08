package cl.sda1085.notificaciones.repository;

import cl.sda1085.notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository <Notificacion, Long> {

    //obtener todas las notificaciones de un usuario
    List<Notificacion> findByIdUsuario (Long idUsuario);

    //obtener todas las no leídas
    List <Notificacion> findByLeidoFalse();

    //obtener las no leídas de un usuario específico
    List<Notificacion> findByIdUsuarioAndLeidoFalse(Long idUsuario);

}
