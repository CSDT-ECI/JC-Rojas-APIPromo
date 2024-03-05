# Análisis inicial de deuda técnica

## Deuda técnica a nivel de documentación
### Estado actual
El proyecto no cuenta con documentación de ningún tipo, simplemente una descripción del propósito general del repositorio,
pero no encontramos documentos que expliquen las funcionalidades ofrecidas, que describan los endpoints expuestos o que 
expliquen piezas en específico del código.

### Mejoras propuestas
* Describir el propósito general del sistema en el archivo README.
* Documentar los endpoints de la aplicación utilizando el formato OpenApi.
* Añadir documentación a las piezas de código que lo requieran por su nivel de complejidad.

## Deuda técnica a nivel de pruebas
El proyecto original no presenta ningún tipo de pruebas, ni unitarias de ni integración, tampoco se documentan
ejemplos de casos de uso con los que se pueda probar que la aplicación cumple con su objetivo

### Mejoras propuestas
* Incluir pruebas unitarias
* Incluir pruebas de integración de los principales casos de uso

## Deuda técnica a nivel de código
A continuación se listan  algunas malas prácticas, code smells  y puntos de mejora detectados durante
el análisis inicial del código original

* Nombres de variables y de métodos poco descriptivos
* Lógica de negocio en clases de `Utils`
* Métodos que no se utilizan
* Mezcla entre archivos .java y .kt
* La estructura de carpetas del código es compleja y dificulta la navegación, no agrupa según las capas del proyecto
* Ausencia de servicios, el uso de los repositorios y los objetos de dominio se realiza directamente en los controladores
* Lógica dispersa en distintas clases, se podría agrupar dentro de los objetos de dominio
* Uso de librerias con vulnerabilidades reportadas
* Uso de versiones antiguas de Java y Kotlin
* Objetos de DTO se utilizan tanto en el response de los endpoints como a los objetos de base de datos

Adicionalmente, hay un error al ejecutar algunas fases del ciclo de vida usando maven
## Propuestas de refactorización
* Reorganizar la estructura de archivos del proyecto
* Incluir servicios con las responsabilidades de llamar a los repositorios y administrar los objetos de dominio
* Mover la lógica hacia los objetos de dominio
* Refactorizar los métodos:
  * Renombrar los métodos para hacer el código más descriptivo
  * Utilizar variables que describan mejor su propósito
  * Introducir métodos auxiliares
  * Remover los métodos que no se utilizan
* Obtener los DTOs desde clases autogeneradas a partir del swagger
* Separar las entidades de repositorio de los DTOs y de los objetos de dominio
* Usar únicamente archivos .kt y utilizar los estándares de codificación de Kotlin
* Actualizar las librerías y las versiones de Java y Kotlin


