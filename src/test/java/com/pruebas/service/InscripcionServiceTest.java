package com.pruebas.service;

import com.pruebas.model.Inscripcion;
import com.pruebas.model.services.InscripcionService;
import com.pruebas.repository.InscripcionRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {

    @Mock
    private InscripcionRepository repository;

    @InjectMocks
    private InscripcionService service;

    @BeforeAll
    static void identificarEstudiante() {
        System.out.println("Gadiel Adrian Vizcardo Vilela - i202220904");
    }

    @Test
    @DisplayName("Inscribe al estudiante en el curso 2 y devuelve el registro guardado")
    void inscribeEstudiante() {
        String codigo = "I2024050";
        Inscripcion solicitud = inscripcion(codigo, 2L);
        Inscripcion guardada = inscripcion(codigo, 2L);
        guardada.setId(10L);
        when(repository.existsByCodigoEstudianteAndCursoId(codigo, 2L)).thenReturn(false);
        when(repository.save(solicitud)).thenReturn(guardada);

        Inscripcion resultado = service.inscribir(solicitud);

        verify(repository).save(solicitud);
        assertNotNull(resultado);
        assertEquals(guardada, resultado);
    }

    @Test
    @DisplayName("Rechaza una inscripción duplicada sin guardar")
    void rechazaDuplicado() {
        String codigo = "I2024001";
        Inscripcion solicitud = inscripcion(codigo, 1L);
        when(repository.existsByCodigoEstudianteAndCursoId(codigo, 1L)).thenReturn(true);

        RuntimeException error = assertThrows(RuntimeException.class, () -> service.inscribir(solicitud));

        assertEquals("Estudiante ya inscrito", error.getMessage());
        verify(repository, never()).save(any(Inscripcion.class));
    }

    @Test
    @DisplayName("Lista las dos inscripciones del estudiante consultado")
    void listaInscripcionesPorEstudiante() {
        String codigo = "I2024001";
        when(repository.findByCodigoEstudiante(codigo)).thenReturn(
                List.of(inscripcion(codigo, 1L), inscripcion(codigo, 2L)));

        List<Inscripcion> resultado = service.listarPorEstudiante(codigo);

        assertEquals(2, resultado.size());
        resultado.forEach(registro -> assertEquals(codigo, registro.getCodigoEstudiante()));
    }

    private Inscripcion inscripcion(String codigo, Long cursoId) {
        Inscripcion registro = new Inscripcion();
        registro.setCodigoEstudiante(codigo);
        registro.setCursoId(cursoId);
        return registro;
    }
}
