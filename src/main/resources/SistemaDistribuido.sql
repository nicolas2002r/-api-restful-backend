CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);
CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    rol_id INT REFERENCES roles(id)
);
CREATE TABLE programa_academico (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);
CREATE TABLE programa_academico_usuario (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario(id),
    programa_academico_id INT REFERENCES programa_academico(id),
    UNIQUE (usuario_id, programa_academico_id)
);
INSERT INTO roles (nombre)
VALUES
('Docente'),
('Decano'),
('Director de Programa');


INSERT INTO usuario (nombre, apellido, dni, correo, rol_id)
VALUES
('Juan', 'Pérez', '12345678', 'juan.perez@example.com', 1), -- Docente
('María', 'Gómez', '87654321', 'maria.gomez@example.com', 2), -- Decano
('Carlos', 'López', '45678912', 'carlos.lopez@example.com', 3); -- Director de Programa


INSERT INTO programa_academico (nombre)
VALUES
('Ingeniería de Sistemas'),
('Ingeniería Industrial'),
('Ingeniería Mecatronica'),
('Ingeniería Energias Renovables');

INSERT INTO programa_academico_usuario (usuario_id, programa_academico_id)
VALUES
(1, 1), -- Juan Pérez accede a Ingeniería de Sistemas
(2, 2), -- María Gómez accede a Ingeniería Industrial
(3, 1), -- Carlos López accede a Ingeniería de Sistemas
(3, 2); -- Carlos López accede a Ingeniería Industrial






SELECT u.nombre, u.apellido, r.nombre AS rol, pa.nombre AS programa_academico
FROM usuario u
JOIN roles r ON u.rol_id = r.id
JOIN programa_academico_usuario pau ON u.id = pau.usuario_id
JOIN programa_academico pa ON pau.programa_academico_id = pa.id;

select * from programa_academico;
SELECT * FROM roles WHERE nombre = 'Decano';


----------------------------------------------------------------------------------------------------------------------

CREATE TABLE actividades (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

CREATE TABLE sub_actividades (
    id SERIAL PRIMARY KEY,
    actividad_id INT REFERENCES actividades(id) ON DELETE CASCADE,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

CREATE TABLE tareas (
    id SERIAL PRIMARY KEY,
    sub_actividad_id INT REFERENCES sub_actividades(id) ON DELETE CASCADE,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    asignable BOOLEAN DEFAULT TRUE
);

SELECT a.nombre AS actividad, sa.nombre AS sub_actividad
FROM actividades a
JOIN sub_actividades sa ON a.id = sa.actividad_id;

SELECT sa.nombre AS sub_actividad, t.nombre AS tarea
FROM sub_actividades sa
JOIN tareas t ON sa.id = t.sub_actividad_id;

SELECT a.nombre AS actividad, sa.nombre AS sub_actividad, t.nombre AS tarea
FROM actividades a
JOIN sub_actividades sa ON a.id = sa.actividad_id
JOIN tareas t ON sa.id = t.sub_actividad_id;

----------------------------------------------------------------------------------------------------------------------
INSERT INTO actividades (nombre, descripcion) VALUES 
('Académicas', 'Actividades relacionadas con la docencia y enseñanza'),
('Formativas', 'Actividades orientadas a la formación continua'),
('Labores científicas', 'Investigación y producción científica'),
('Extensiones', 'Actividades de extensión universitaria'),
('Culturales', 'Promoción y participación en actividades culturales'),
('Gestión Académica - Administrativa', 'Tareas de gestión académica y administrativa');

-- Sub-actividades para la actividad "Académicas" (ID 1)
INSERT INTO sub_actividades (actividad_id, nombre, descripcion) VALUES 
(1, 'Preparación de clases', 'Diseño y preparación de contenido educativo'),
(1, 'Evaluaciones', 'Elaboración y corrección de evaluaciones');

-- Sub-actividades para la actividad "Formativas" (ID 2)
INSERT INTO sub_actividades (actividad_id, nombre, descripcion) VALUES 
(2, 'Cursos de formación', 'Participación en cursos de formación continua'),
(2, 'Seminarios', 'Asistencia a seminarios formativos');

-- Sub-actividades para la actividad "Labores científicas" (ID 3)
INSERT INTO sub_actividades (actividad_id, nombre, descripcion) VALUES 
(3, 'Investigación de campo', 'Realización de investigación en campo'),
(3, 'Publicación científica', 'Escribir y publicar artículos científicos');

-- Sub-actividades para la actividad "Extensiones" (ID 4)
INSERT INTO sub_actividades (actividad_id, nombre, descripcion) VALUES 
(4, 'Charlas educativas', 'Charlas dirigidas a la comunidad'),
(4, 'Actividades comunitarias', 'Proyectos en colaboración con la comunidad');

-- Sub-actividades para la actividad "Culturales" (ID 5)
INSERT INTO sub_actividades (actividad_id, nombre, descripcion) VALUES 
(5, 'Organización de eventos', 'Organización y ejecución de eventos culturales'),
(5, 'Participación en festivales', 'Participación en festivales culturales');

-- Sub-actividades para la actividad "Gestión Académica - Administrativa" (ID 6)
INSERT INTO sub_actividades (actividad_id, nombre, descripcion) VALUES 
(6, 'Reuniones administrativas', 'Asistencia y participación en reuniones'),
(6, 'Gestión de proyectos académicos', 'Gestión y seguimiento de proyectos académicos');


-- Tareas para la sub-actividad "Preparación de clases" (ID 1)
INSERT INTO tareas (sub_actividad_id, nombre, descripcion) VALUES 
(1, 'Diseñar el programa del curso', 'Creación del programa de estudio'),
(1, 'Actualizar materiales de clase', 'Revisión y actualización de los materiales de clase');

-- Tareas para la sub-actividad "Evaluaciones" (ID 2)
INSERT INTO tareas (sub_actividad_id, nombre, descripcion) VALUES 
(2, 'Diseñar exámenes', 'Creación de exámenes para los estudiantes'),
(2, 'Corregir exámenes', 'Corrección y retroalimentación de exámenes');

-- Tareas para la sub-actividad "Cursos de formación" (ID 3)
INSERT INTO tareas (sub_actividad_id, nombre, descripcion) VALUES 
(3, 'Inscribirse en curso', 'Proceso de inscripción en cursos de formación'),
(3, 'Completar curso', 'Participar y completar un curso formativo');

-- Tareas para la sub-actividad "Investigación de campo" (ID 5)
INSERT INTO tareas (sub_actividad_id, nombre, descripcion) VALUES 
(5, 'Recolección de datos', 'Realización de actividades de campo para recolectar datos'),
(5, 'Análisis de datos', 'Análisis e interpretación de los datos recolectados');

-- Tareas para la sub-actividad "Charlas educativas" (ID 7)
INSERT INTO tareas (sub_actividad_id, nombre, descripcion) VALUES 
(7, 'Preparar presentación', 'Preparar una presentación para la charla'),
(7, 'Impartir charla', 'Realización de la charla educativa');

-- Tareas para la sub-actividad "Organización de eventos" (ID 9)
INSERT INTO tareas (sub_actividad_id, nombre, descripcion) VALUES 
(9, 'Coordinar logística', 'Planificación y coordinación de la logística del evento'),
(9, 'Invitar a los participantes', 'Organización de las invitaciones para los participantes');

-- Tareas para la sub-actividad "Reuniones administrativas" (ID 11)
INSERT INTO tareas (sub_actividad_id, nombre, descripcion) VALUES 
(11, 'Organizar agenda', 'Preparación de la agenda para la reunión'),
(11, 'Redactar acta', 'Redacción del acta de la reunión');

SELECT a.nombre AS actividad, sa.nombre AS sub_actividad, t.nombre AS tarea
FROM actividades a
JOIN sub_actividades sa ON a.id = sa.actividad_id
JOIN tareas t ON sa.id = t.sub_actividad_id;
