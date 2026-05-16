package cl.sda1085.notificaciones.service;

import cl.sda1085.notificaciones.dto.NotificacionRequestDTO;
import cl.sda1085.notificaciones.dto.NotificacionResponseDTO;
import cl.sda1085.notificaciones.model.Notificacion;
import cl.sda1085.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionService {

    //Conexión con 'repository'
    private final NotificacionRepository notificacionRepository;

    private NotificacionResponseDTO convertirADTO(Notificacion notificacion) {
        return new NotificacionResponseDTO(
                notificacion.getId(),
                notificacion.getIdUsuario(),
                notificacion.getTipo(),
                notificacion.getMensaje(),
                notificacion.isLeido(),
                notificacion.getFechaCreacion()
        );
    }


    //------------------------------
    //CRUD estándar
    //------------------------------

    //Obtener todas las notificaciones
    public List <NotificacionResponseDTO> obtenerTodas(){
        log.info("Consultando el listado completo de notificaciones en el sistema.");
        return notificacionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Obtener notificación por ID
    public Optional<NotificacionResponseDTO> obtenerPorId (Long id) {
        log.info("Buscando notificación con ID: {}", id);
        return notificacionRepository.findById(id)
                .map(this::convertirADTO);
    }

    //Guardar (crear) nueva notificación
    public NotificacionResponseDTO guardar(NotificacionRequestDTO dto) {
        log.info("Registrando nueva notificación tipo [{}] para el usuario ID: {}", dto.getTipo(), dto.getIdUsuario());

        Notificacion notificacion = new Notificacion();
        notificacion.setIdUsuario(dto.getIdUsuario());
        notificacion.setTipo(dto.getTipo());
        notificacion.setMensaje(dto.getMensaje());
        //El estado 'leído' y 'fechaCreacion' se gestionan solos en la entidad con @PrePersist

        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
        log.info("Notificación guardada exitosamente con ID: {}", notificacionGuardada.getId());

        return convertirADTO(notificacionGuardada);
    }

    //Actualizar notificación existente
    @Transactional  //Como este método modifica datos, esta anotación asegura que si algo falla a mitad del proceso la BD no guarde cambios incompletos
    public Optional<NotificacionResponseDTO> actualizar (Long id, NotificacionRequestDTO dto) {
        log.info("Solicitud para actualizar la notificación ID: {}", id);
        return notificacionRepository.findById(id)
                .map(notificacionExistente -> {
                    notificacionExistente.setIdUsuario(dto.getIdUsuario());
                    notificacionExistente.setTipo(dto.getTipo());
                    notificacionExistente.setMensaje(dto.getMensaje());

                    log.info("Notificación ID: {} actualizada en la base de datos", id);
                    return convertirADTO(notificacionRepository.save(notificacionExistente));
                });
    }

    //Eliminar notificación
    @Transactional
    public void eliminar(Long id) {
        log.warn("¡ATENCIÓN! Eliminando de forma permanente la notificación ID: {}", id);
        notificacionRepository.deleteById(id);
        log.info("Notificación ID: {} eliminada correctamente", id);
    }


    //------------------------------
    //CRUD personalizado
    //------------------------------

    //Obtener notificación por usuario
    public List<NotificacionResponseDTO> obtenerPorUsuario(Long idUsuario) {
        log.info("Consultando historial de notificaciones para el usuario ID: {}", idUsuario);
        return notificacionRepository.findByIdUsuario(idUsuario)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Obtener todas las no leídas del sistema
    public List<NotificacionResponseDTO> obtenerNoLeidas() {
        log.info("Consultando todas las notificaciones pendientes (no leídas) en el sistema");
        return notificacionRepository.findByLeidoFalse()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Obtener notificaciones no leidas de un usuario específico
    public List<NotificacionResponseDTO> obtenerNoLeidasPorUsuario(Long idUsuario) {
        log.info("Consultando todas las notificaciones pendientes para el usuario ID: {}", idUsuario);
        return notificacionRepository.findByIdUsuarioAndLeidoFalse(idUsuario)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Contar cuántas notificaciones pendientes tiene un usuario
    public long contarPendientesPorUsuario(Long idUsuario) {
        log.info("Contando notificaciones pendientes para usuario ID: {}", idUsuario);
        return notificacionRepository.countByIdUsuarioAndLeidoFalse(idUsuario);
    }

    //Saber si un usuario tiene al menos una notificación sin leer
    public boolean tieneNotificacionesPendientes(Long idUsuario) {
        log.info("Verificando existencia de notificaciones pendientes para usuario ID: {}", idUsuario);
        return notificacionRepository.existsByIdUsuarioAndLeidoFalse(idUsuario);
    }

    //Obtener la última notificación recibida por el usuario
    public Optional<NotificacionResponseDTO> obtenerUltimaPorUsuario(Long idUsuario) {
        log.info("Recuperando la notificación más reciente para el usuario ID: {}", idUsuario);
        return notificacionRepository.findTopByIdUsuarioOrderByFechaCreacionDesc(idUsuario)
                .map(this::convertirADTO);
    }

    //EXTRA: Marcar como notificación leída
    @Transactional
    public Optional<NotificacionResponseDTO> marcarComoLeida(Long id) {
        log.info("Modificando estado a LEÍDO para la notificación ID: {}", id);
        return notificacionRepository.findById(id)
                .map(notificacion -> {
                    notificacion.setLeido(true);
                    return convertirADTO(notificacionRepository.save(notificacion));
                });
    }
}
