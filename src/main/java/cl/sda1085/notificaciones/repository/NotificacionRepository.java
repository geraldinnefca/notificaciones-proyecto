package cl.sda1085.notificaciones.repository;

import cl.sda1085.notificaciones.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository <Notificacion, Long> {

    List<Notificacion> findByIdUsuario (Long idUsuario);

    List <Notificacion> findByLeidoFalse();

    List<Notificacion> findByIdUsuarioAndLeidoFalse(Long idUsuario);

}
