package cl.sda1085.notificaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  //Deshabilitado para pruebas con Postman
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated())  //Cualquier petición requiere estar autenticado
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
