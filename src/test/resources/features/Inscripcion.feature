# language: es
Característica: Inscripción a cursos

  Escenario: Inscripción correcta en el curso 2
    Dado un estudiante nuevo con un código único
    Cuando solicita inscribirse en el curso 2
    Entonces el sistema acepta la inscripción con estado 200

  Escenario: Inscripción duplicada en el curso 1
    Dado el estudiante preinscrito con código "I2024001"
    Cuando solicita inscribirse en el curso 1
    Entonces el sistema rechaza la inscripción con un estado 4xx o 5xx
