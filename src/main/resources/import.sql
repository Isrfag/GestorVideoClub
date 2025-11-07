
INSERT INTO pelicula (titulo, genero, fecha_estreno, director, duracion)
VALUES ('El Señor de los Anillos', 'Aventura', '2001-12-19', 'Peter Jackson', 180);

INSERT INTO pelicula (titulo, genero, fecha_estreno, director, duracion)
VALUES ('Matrix', 'Ciencia ficción', '1999-03-31', 'Lana Wachowski', 136);

INSERT INTO pelicula (titulo, genero, fecha_estreno, director, duracion)
VALUES ('Inception', 'Ciencia ficción', '2010-07-16', 'Christopher Nolan', 148);


INSERT INTO copia (formato, disponible, pelicula_id) VALUES ('DVD', true, 1);
INSERT INTO copia (formato, disponible, pelicula_id) VALUES ('BLURAY', true, 1);
INSERT INTO copia (formato, disponible, pelicula_id) VALUES ('DVD', true, 2);
INSERT INTO copia (formato, disponible, pelicula_id) VALUES ('BLURAY', true, 2);
INSERT INTO copia (formato, disponible, pelicula_id) VALUES ('DVD', true, 3);
INSERT INTO copia (formato, disponible, pelicula_id) VALUES ('BLURAY', true, 3);


INSERT INTO cliente (nombre, apellido, email, telefono)
VALUES ('John', 'Pérez', 'jhonny@example.com', '123456789');