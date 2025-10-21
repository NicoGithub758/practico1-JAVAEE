-- =====================================================================================
-- SCRIPT DE DATOS DE PRUEBA CORREGIDO PARA IDs NUMÉRICOS AUTOGENERADOS
-- =====================================================================================

-- -----------------------------------------------------
-- PACIENTES
-- Ya no se especifica 'paciente_id'. La base de datos asignará 1, 2, 3, 4.
-- -----------------------------------------------------
INSERT INTO paciente (nombre, apellido) VALUES
('Carlos', 'Santana'),  -- ID será 1
('Ana', 'Torres'),      -- ID será 2
('Luis', 'Giménez'),    -- ID será 3
('Elena', 'Pascual');   -- ID será 4


-- -----------------------------------------------------
-- PROFESIONALES
-- Ya no se especifica 'profesional_id'. La base de datos asignará 1, 2, 3.
-- -----------------------------------------------------
INSERT INTO profesional (nombre_usuario, nombre, apellido, especializacion) VALUES
('ireyes', 'Isabel', 'Reyes', 'Cardiología'),      -- ID será 1
('mcoba', 'Martín', 'Coba', 'Medicina General'),  -- ID será 2
('lfernandez', 'Laura', 'Fernández', 'Dermatología'); -- ID será 3


-- -----------------------------------------------------
-- DOCUMENTOS CLÍNICOS
-- Ya no se especifica 'doc_id'. Las claves foráneas 'paciente_id' y 'profesional_id'
-- ahora usan los IDs numéricos generados arriba.
-- -----------------------------------------------------

-- Documento 1: Carlos Santana (ID=1) con la Dra. Reyes (ID=1)
INSERT INTO documento_clinico (paciente_id, profesional_id, id_externa_doc, tipo, estado, contenido, fecha_creacion) VALUES
(1, 1, 'EXT-001', 'Informe de Consulta', 'Finalizado', 'Paciente consulta por dolor en el pecho. Se solicita electrocardiograma.', '2025-10-01 10:30:00');

-- Documento 2: Ana Torres (ID=2) con el Dr. Coba (ID=2)
INSERT INTO documento_clinico (paciente_id, profesional_id, id_externa_doc, tipo, estado, contenido, fecha_creacion) VALUES
(2, 2, 'EXT-002', 'Nota de Evolución', 'Activo', 'Paciente con cuadro gripal, se indica reposo y paracetamol.', '2025-10-05 15:00:00');

-- Documento 3: Carlos Santana (ID=1) con el Dr. Coba (ID=2)
INSERT INTO documento_clinico (paciente_id, profesional_id, id_externa_doc, tipo, estado, contenido, fecha_creacion) VALUES
(1, 2, 'EXT-003', 'Resultado de Laboratorio', 'Finalizado', 'Hemograma completo dentro de los valores normales. Colesterol LDL ligeramente elevado.', '2025-10-10 09:00:00');

-- Documento 4: Luis Giménez (ID=3) con la Dra. Fernández (ID=3)
INSERT INTO documento_clinico (paciente_id, profesional_id, id_externa_doc, tipo, estado, contenido, fecha_creacion) VALUES
(3, 3, 'EXT-004', 'Receta Médica', 'Activo', 'Crema con hidrocortisona al 1% para aplicar en la zona afectada dos veces al día.', '2025-10-12 11:45:00');

-- Documento 5: Carlos Santana (ID=1) con la Dra. Reyes (ID=1) - Seguimiento
INSERT INTO documento_clinico (paciente_id, profesional_id, id_externa_doc, tipo, estado, contenido, fecha_creacion) VALUES
(1, 1, 'EXT-005', 'Informe de Alta', 'Finalizado', 'Electrocardiograma normal. Se descarta patología cardíaca aguda. Se indica control de dieta y ejercicio.', '2025-10-15 14:20:00');

-- Documento 6: Elena Pascual (ID=4) con el Dr. Coba (ID=2)
INSERT INTO documento_clinico (paciente_id, profesional_id, id_externa_doc, tipo, estado, contenido, fecha_creacion) VALUES
(4, 2, 'EXT-006', 'Informe de Consulta', 'Finalizado', 'Control anual de salud. Sin hallazgos patológicos.', CURRENT_TIMESTAMP);