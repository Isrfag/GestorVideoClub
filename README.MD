# Gestor de VideoClub 🎬

## Características

- Interfaz gráfica web con **Thymeleaf y CSS**
- Gestión de **clientes** y sus datos.
- Gestión de **películas**, incluyendo categorías y disponibilidad.
- Registro de **alquileres y devoluciones**.
- Autenticación con roles **EMPLOYEE y ADMIN** con Spring Security
- API REST para consumo externo con Autenticación **JWT**.
- Documentación de API con OpenApi **/swagger-ui/**
- Configuración basada en **Spring Profiles** (`docker` y `local`).
- Persistencia de datos con **Spring Data JPA** y MySQL.

---

## Tecnologías

- Java 21
- Thymeleaf
- Spring Boot 3.5
- Spring Data JPA / Hibernate
- Spring Security + JWT
- Spring Open Api Web
- MySQL 8
- Docker / Docker Compose
- Maven

---

## Requisitos

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Docker & Docker Compose](https://docs.docker.com/compose/install/)
- Maven 3.8+

---

## Instalación y ejecución

1. **Clona el repositorio**

```bash
git clone https://github.com/tuusuario/gestor-videoclub.git
cd gestor-videoclub
```

2. **Configura el application.properties**
- Usa el perfil para docker **spring.profiles.active=docker**
- Usa el perfil default para H2

2. **Ejecuta docker compose**
```bash
docker compose up --build
```
- Esto levantará:
Contenedor videoclub-mysql con la base de datos.
Contenedor app con la aplicación Spring Boot.

### Aplicación disponible en http://localhost:8080