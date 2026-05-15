package cl.sda1085.notificaciones.config;

import cl.sda1085.notificaciones.model.Notificacion;
import cl.sda1085.notificaciones.repository.NotificacionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    //Conexión con 'repository'
    private final NotificacionRepository notificacionRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (notificacionRepository.count() > 0) {
            log.info("La base de datos de notificaciones ya tiene datos. Omitiendo inicialización.");
            return;
        }
        log.info("Iniciando precarga de notificaciones de prueba...");

        Notificacion n1 = new Notificacion(null,
                5L,
                "Subasta",
                "Tu oferta por el Producto ID 1 ha sido superada. ¡Puja de nuevo!",
                false,
                null);

        Notificacion n2 = new Notificacion(
                null,
                6L,
                "Subasta",
                "¡Felicidades! Has ganado la subasta #1. Procede al pago.",
                true,
                null);

        Notificacion n3 = new Notificacion(
                null,
                8L,
                "Recordatorio",
                "La subasta del Producto ID 8 termina en 30 minutos.",
                false,
                null);

        Notificacion n4 = new Notificacion(
                null,
                12L,
                "Subasta",
                "La subasta del lote #12 ha finalizado sin nuevas ofertas.",
                false,
                null);

        Notificacion n5 = new Notificacion(
                null,
                5L,
                "Pago",
                "Tu pago por la 'Vasija Precolombina' ha sido confirmado.",
                true,
                null);

        Notificacion n6 = new Notificacion(
                null,
                1L,
                "Envío",
                "El paquete con tu 'Espada Medieval' ya fue despachado.",
                false,
                null);

        Notificacion n7 = new Notificacion(
                null,
                14L,
                "Alerta",
                "Se ha detectado un nuevo inicio de sesión en tu cuenta desde Viña del Mar.",
                false,
                null);

        Notificacion n8 = new Notificacion(
                null,
                6L,
                "Recordatorio",
                "Un artículo de tu lista de deseos comienza su subasta en 1 hora.",
                false,
                null);

        Notificacion n9 = new Notificacion(
                null,
                8L,
                "Envío",
                "¡Tu pedido ha llegado! Por favor, califica al vendedor.",
                true,
                null);

        Notificacion n10 = new Notificacion(
                null,
                2L,
                "Pago",
                "No pudimos procesar tu tarjeta. Revisa tus datos de facturación.",
                false,
                null);

        notificacionRepository.saveAll(List.of(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10));

        log.info("Se han precargado exitosamente 10 notificaciones iniciales.",
                notificacionRepository.count());
    }
}
