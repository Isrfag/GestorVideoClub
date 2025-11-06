package com.empresa.curso.ejercicio17.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.empresa.curso.ejercicio17.security.JwtFilter;
import com.empresa.curso.ejercicio17.service.UsuarioDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtFilter jwtFilter;


    public SecurityConfig (UsuarioDetailsService usuarioDetailsService, JwtFilter jwtFilter)  {
        this.usuarioDetailsService = usuarioDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // CSRF habilitado para formularios Thymeleaf, deshabilitado para APIs
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**","/h2-console/**"))
                
                //Publico el login jwt
                .authorizeHttpRequests(auth -> auth
                // Login y recursos públicos
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-resources/**",
                    "/webjars/**"
                ).permitAll()

                // Thymeleaf: vistas protegidas
                .requestMatchers("/clientes").hasRole("ADMIN")
                .requestMatchers("/clientes/nuevo", "/clientes/guardar").hasAnyRole("EMPLOYEE", "ADMIN")
                .requestMatchers("/clientes/editar/**", "/clientes/eliminar/**").hasRole("ADMIN")
                .requestMatchers("/peliculas/**", "/alquileres/**").hasAnyRole("EMPLOYEE", "ADMIN")
                .requestMatchers("/usuarios/**", "/admin/**").hasRole("ADMIN")

                   // APIs REST: reglas específicas
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers("/api/clientes/**").hasRole("ADMIN")
                // APIs REST: JWT (general)
                .requestMatchers("/api/**").authenticated()

                // Cualquier otra request requiere autenticación
                .anyRequest().authenticated()
            )
            // Manejo de acceso denegado
            .exceptionHandling(e -> e
                .accessDeniedPage("/error/403")
                .authenticationEntryPoint(restAuthenticationEntryPoint()) // JWT no válido
                .accessDeniedHandler(restAccessDeniedHandler())
            )
            
            // Form login para vistas Thymeleaf
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .userDetailsService(usuarioDetailsService)
            // JWT Filter solo para APIs REST
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager para login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AccessDeniedHandler restAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            if(request.getRequestURI().startsWith("/api/")) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getOutputStream().println("{\"error\": \"Acceso denegado\"}");
            } else {
                response.sendRedirect("/error/403");
            }
        };
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
                if(request.getRequestURI().startsWith("/api/")) {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getOutputStream().println("{\"error\": \"No autorizado\"}");
                } else {
                    // Para vistas normales, redirige al login
                    response.sendRedirect("/login");
                }
            }
        };
    }
}