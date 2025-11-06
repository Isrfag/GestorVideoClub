package com.empresa.curso.ejercicio17.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.empresa.curso.ejercicio17.service.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;

    public SecurityConfig (UsuarioDetailsService usuarioDetailsService)  {
        this.usuarioDetailsService = usuarioDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception  {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()

            //Clientes    
            .requestMatchers("/clientes").hasRole("ADMIN")      
            .requestMatchers("/clientes/nuevo", "/clientes/guardar").hasAnyRole("EMPLOYEE", "ADMIN") 
            .requestMatchers("/clientes/editar/**", "/clientes/eliminar/**").hasRole("ADMIN")
            
            // Películas y alquileres
            .requestMatchers("/peliculas/**", "/alquileres/**")
                .hasAnyRole("EMPLOYEE", "ADMIN")
            
            // Usuarios / admin
            .requestMatchers("/admin/**", "/api/usuarios/**","/usuarios/**").hasRole("ADMIN")                
                
                .anyRequest().authenticated()
            )
            .exceptionHandling(e -> e.accessDeniedPage("/error/403"))
            .userDetailsService(usuarioDetailsService)
            .formLogin(form -> form 
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()    
            );
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
}