package cl.sda1085.notificaciones.config;

import cl.sda1085.notificaciones.model.Notificacion;
import cl.sda1085.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor

public class DataInitializer {

    private final NotificacionRepository notificacionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (notificacionRepository.count() > 0) {
            log.info("La base de datos de notificaciones ya tiene datos. Omitiendo inicialización.");
            return;
        }

        log.info("Iniciando precarga de notificaciones de prueba...");

        Notificacion n1 = new Notificacion(
                null,
                5L,
                "Tu oferta por el Producto ID 1 ha sido superada. ¡Puja de nuevo!",
                "oferta_superada",
                false,
                null);

        Notificacion n2 = new Notificacion(
                null,
                6L,
                "¡Felicidades! Has ganado la subasta #1. Procede al pago.",
                "subasta_ganada",
                true,
                null);

        Notificacion n3 = new Notificacion(
                null,
                8L,
                "La subasta del Producto ID 8 termina en 30 minutos.",
                "recordatorio",
                false,
                null);

        Notificacion n4 = new Notificacion(
                null,
                12L,
                "subasta_perdida",
                "recordatorio",
                false,
                null);


        notificacionRepository.saveAll(List.of(n1, n2, n3, n4));

        log.info("Se han precargado {} notificaciones iniciales.", notificacionRepository.count());
    }

}
