# Deuda técnica de procesos
El proyecto no cuenta con pipelines que faciliten la integración o el despliegue del mismo.
por lo que en esta etapa se propone construir un pipeline que realice la continua construcción,
ejecución de pruebas y análisis estático del código del proyecto, de esta manera se puede mejorar tanto la experiencia
del desarrollador al ofrecer feedback continuo y oportuno como el proceso de desarrollo al añadir validación constante
sobre la calidad del código.

## Pipeline de integración continua
Los requisitos mínimos para este pipeline de integración continua son los siguientes:
- Construcción del proyecto (build)
- Ejecución de pruebas (test)
- Análisis de sonar (sonar)

Adicionalmente se proponen las siguientes etapas
- Asegurar los estándares de estilo de kotlin (klintCheck)
- Análisis de seguridad del proyecto (snyk)

## Detalles de la implementación y resultados
Se utiliza `gradle` para administrar el ciclo de vida del proyecto y las etapas de build, test, sonar y ktlintCheck,
se realizan las siguiente configuraciones sobre el proyecto de gradle para agrupar algunas de las tareas.

```kotlin
tasks.named("check") {
    dependsOn(tasks.named("ktlintCheck"))
}

tasks.withType<SonarTask> {
    dependsOn(tasks.named("check"))
    dependsOn(tasks.named("jacocoTestReport"))
}
```

Teniendo en cuenta que la fase de check construye el proyecto y ejecuta las pruebas, ahora adicionalmente 
analizará los estándares de estilo. De esta forma basta con utilizar la fase `sonar` para cumplir con
todas las etapas planteadas para el pipeline

El análisis de sonar inicial obtuvo el siguiente resultado:

![InitialAnalysis.png](img%2FgithubActions%2FInitialAnalysis.png)

Dado que el proyecto no cuenta con la suficiente cobertura de pruebas, fue necesario modificar el quality gate
para que permita continuar con el desarrollo, pero asegurando que la calidad se va a mantener, por lo que se decide reducir 
la cobertura requerida a 30%, asegurando que la cobertura de pruebas no disminuirá en el futuro. Este quality gate debe
evolucionar según mejore la cobertura del proyecto hasta alcanzar el 80% que se propone en el quality gate original.
A continuación se muestra el quality gate utilizado

![updatedQualityGate.png](img%2FgithubActions%2FupdatedQualityGate.png)

Posteriormente a la actualización de el quality gate, vemos que el proyecto cumple con los requisitos, lo que permitiría
continuar con el Pull Request

![AnalysisAfterQualityGateUpdate.png](img%2FgithubActions%2FAnalysisAfterQualityGateUpdate.png)



Para realizar el análisis de seguridad se utilizó la tarea `snyk/actions/gradle-jdk17@master` presente en el marketplace de
github actions y que permite analizar vulnerabilidades en proyectos de gradle en Java 17. A continuación se presentan los resultados iniciales

![snykInitialResult.png](img%2FgithubActions%2FsnykInitialResult.png)

A partir de este análisis se identificaron vulnerabilidades en algunas de las dependencias relacionadas a spring, por lo
que se actualizaron las dependencias a las versiones sugeridas para finalmente obtener que el PR pasa todas las validaciones
requeridas y configuradas en el pipeline

![PRPassingChecks.png](img%2FgithubActions%2FPRPassingChecks.png)

## Conclusiones
Github actions permite la implementación de flujos automatizados sobre los proyectos, en este caso la implementación de 
un flujo de integración continua (CI) que permite verificar continuamente la calidad del código, generar feedback oportuno
al desarrollador y evitar la introducción de errores y deuda técnica al proyecto. Si esto se suma con algunas reglas sobre
las ramas y pull requests del proyecto de github, se puede lograr un mejor control sobre el código que va a la versión de master.