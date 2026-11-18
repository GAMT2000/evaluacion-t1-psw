package com.pruebas.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

class CursoApiTest {

    private static final String API = "http://localhost:8080/api/cursos";

    @Test
    @DisplayName("La lista de cursos devuelve 200 y contiene cursos")
    void consultaTodosLosCursos() {
        given()
                .when().get(API)
                .then().statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("El curso 1 es Pruebas de Software y tiene precio positivo")
    void consultaCursoUno() {
        given()
                .when().get(API + "/{id}", 1)
                .then().statusCode(200)
                .body("nombre", equalTo("Pruebas de Software"))
                .body("precio", greaterThan(0.0f));
    }
}
