package com.empresa.curso.ejercicio17.config;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.empresa.curso.ejercicio17.dto.UsuarioSeed;
import com.empresa.curso.ejercicio17.entity.Usuario;
import com.empresa.curso.ejercicio17.entity.enums.Rol;
import com.empresa.curso.ejercicio17.repository.UsuarioRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class UsuarioJsonLoader {
    @Bean
    CommandLineRunner cargarUsuariosDesdeJson(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {

            if (repo.count() > 0) {
                System.out.println("👍 Usuarios ya existentes");
                return; // Ya hay usuarios, no cargar nada
            }

            ObjectMapper mapper = new ObjectMapper();

            InputStream input = getClass().getResourceAsStream("/data/usuarios.json");

            List<UsuarioSeed> usuarios = mapper.readValue(
                    input,
                    new TypeReference<List<UsuarioSeed>>() {}
            );

            for (UsuarioSeed u : usuarios) {

                Usuario user = new Usuario();
                user.setUsername(u.getUsername());
                user.setPassword(encoder.encode(u.getPassword()));
                user.setRol(Rol.valueOf(u.getRol()));

                repo.save(user);
            }

            System.out.println("✅ Usuarios cargados desde usuarios.json");
        };
    }
}
