package com.pruebas.bdd.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InscripcionStepDefinitions {

    private String codigoEstudiante;
    private Response respuesta;

    @Dado("un estudiante nuevo con un código único")
    public void estudianteNuevo() {
        codigoEstudiante = "I" + UUID.randomUUID();
    }

    @Dado("el estudiante preinscrito con código {string}")
    public void estudiantePreinscrito(String codigo) {
        codigoEstudiante = codigo;
    }

    @Cuando("solicita inscribirse en el curso {int}")
    public void solicitaInscripcion(int cursoId) {
        String solicitud = """
                {"codigoEstudiante":"%s","cursoId":%d}
                """.formatted(codigoEstudiante, cursoId);
        respuesta = given()
                .contentType(ContentType.JSON)
                .body(solicitud)
                .when().post("http://localhost:8080/api/inscripciones");
    }

    @Entonces("el sistema acepta la inscripción con estado {int}")
    public void aceptaInscripcion(int estado) {
        assertEquals(estado, respuesta.statusCode(), respuesta.asString());
    }

    @Entonces("el sistema rechaza la inscripción con un estado 4xx o 5xx")
    public void rechazaInscripcion() {
        int estado = respuesta.statusCode();
        assertTrue(estado >= 400 && estado < 600, "Estado recibido: " + estado);
    }
}
