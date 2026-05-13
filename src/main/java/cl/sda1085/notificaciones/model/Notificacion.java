package cl.sda1085.notificaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "notificaciones")

public class Notificacion {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private Long idUsuario;

        @Column(nullable = false, length = 50)
        private String tipo; // "subasta", "pago", "envío"

        @Column(nullable = false)
        private String mensaje;

        @Column(nullable = false)
        private boolean leido = false;

        @Column(nullable = false)
        private LocalDateTime fechaCreacion;

        //Se ejecuta automáticamente antes de insertar una nueva notificación, para que se guarde con fecha actual y marcada como "no leída"
        @PrePersist
        protected void onCreate() {
                this.fechaCreacion = LocalDateTime.now();
                this.leido = false;
        }
    }
