package cl.sda1085.notificaciones.service;

import cl.sda1085.notificaciones.dto.NotificacionRequestDTO;
import cl.sda1085.notificaciones.dto.NotificacionResponseDTO;
import cl.sda1085.notificaciones.model.Notificacion;
import cl.sda1085.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class NotifiicacionService {

    private final NotificacionRepository notificacionRepository;

    private NotificacionResponseDTO convertirADTO(Notificacion notificacion) {
        return new NotificacionResponseDTO(
                notificacion.getId(),
                notificacion.getIdUsuario(),
                notificacion.getTipo(),
                notificacion.getMensaje(),
                notificacion.isLeido()
        );
    }

    public List <NotificacionResponseDTO> obtenerTodas(){
        return notificacionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public Optional<NotificacionResponseDTO> obtenerPorId (Long id) {
        return notificacionRepository.findById(id)
                .map(this::convertirADTO);
    }
    public  NotificacionResponseDTO guardar(NotificacionRequestDTO dto) {

        Notificacion notificacion = new Notificacion();

        notificacion.setIdUsuario(dto.getIdUsuario());
        notificacion.setTipo(dto.getTipo());
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setLeido(false);

        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);

        return convertirADTO(notificacionGuardada);
    }

    public Optional<NotificacionResponseDTO> actualizar (Long id, NotificacionRequestDTO dto) {
        return notificacionRepository.findById(id)
                .map(notificacionExistente -> {

                    notificacionExistente.setIdUsuario(dto.getIdUsuario());
                    notificacionExistente.setTipo(dto.getTipo());
                    notificacionExistente.setMensaje((dto.getMensaje()));

                    return convertirADTO(notificacionRepository.save(notificacionExistente));
                } );
    }

    public void eliminar(Long id) {
        notificacionRepository.deleteById(id);
    }

    //CRUD personalizado

    //Obtener notificación por usuario
    public List<NotificacionResponseDTO> obtenerPorUsuario(Long idUsuario) {
        return notificacionRepository.findByIdUsuario(idUsuario)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Marcar como notificación leída
    public Optional<NotificacionResponseDTO> marcarComoLeida(Long id, NotificacionRequestDTO dto) {
        return notificacionRepository.findById(id)
                .map(notificacion -> {
                    notificacion.setLeido(true);
                    return convertirADTO(notificacionRepository.save(notificacion));
                });
    }

    //Obtener notificaciones no leidas
    public  List<NotificacionResponseDTO> obtenerNoLeidas() {
        return notificacionRepository.findByLeidoFalse()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Obtener notificaciones no leidas por usuario
    public List<NotificacionResponseDTO> obtenerNoLeidasPorUsuario (Long idUsuario) {
        return notificacionRepository.findByIdUsuarioAndLeidoFalse(idUsuario)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

}
