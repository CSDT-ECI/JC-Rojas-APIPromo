# Análisis de Devex, productividad y uso de IA 
## Devex y Productividad
### Puntos positivos
* Se evidencia un avance en la implementación de pruebas unitarias, se han implementado pruebas para el modulo core y
  el modulo areas del proyecto, lo que permite validar la lógica interna de la aplicación y mejorar los feedback loops.
* Se implementó la herramienta ktlinformat para asegurar y facilitar el cumplimiento de estilo del código con respecto a
  los estándares de Kotlin, lo que permite mantener un código limpio y legible reduciendo mientras se reduce la carga cognitiva
  del desarrollador al no tener que recordar los estándares de estilo.
* Se ha realizado una migración a gradle puesto que el equipo tiene más experiencia con el uso de gradle que con el uso de
  maven, esto permitió reducir la carga cognitiva que genera utilizar herramientas con las que no se tiene experiencia.
* Se ha creado un docker-compose con base de datos mysql para facilitar la ejecución local, esto permite que los desarrolladores
  puedan tener un ambiente de desarrollo más homogéneo y reducir los problemas de configuración de ambientes locales. Esto tiene un
  impacto positivo en el estado de flujo y facilita el onboarding de nuevos desarrolladores.
* Se refactorizó la arquitectura del código para tener más lógica en los objetos de dominio y menos en los servicios y controladores,
  se intenta llevar un modelo de arquitectura hexagonal, esto permite que el código sea más mantenible y escalable, además de que
  facilita la incorporación de nuevas funcionalidades y la modificación de las existentes. Este modelo de estructura de código
  reduce la carga cognitiva del desarrollador al tener una estructura clara y predecible, aunque requiere de algunos conocimientos
  para poder agregar nuevas funcionalidades utilizando esta arquitectura.
* El uso de herramientas como gradle facilita la construcción, ejecución y validación de la aplicación, esto permite reducir la carga cognitiva
  del desarrollador al no tener que recordar los comandos necesarios para ejecutar las distintas fases del ciclo de vida del proyecto.
### Puntos negativos
* A pesar de que se han implementado pruebas unitarias para el modulo core y el modulo areas, no se han implementado pruebas para
  los modulos users y promos, esto puede generar problemas en el futuro al no tener validaciones el comportamiento de estos
  modulos, esto puede impactar los feedback loops del proyecto y  dificultar la incorporación de nuevas funcionalidades.
* No se ha reestructurado la funcionalidad de user location, esto puede generar problemas en el futuro puesto que usa un diseño
  poco escalable y que añade una complejidad innecesaria al proyecto, esto puede impactar la productividad del equipo y en
  la carga cognitiva del proyecto.
* El proyecto no cuenta con documentación de ningún tipo, esto puede generar problemas en el futuro al no tener una guía de
  referencia para los desarrolladores, esto puede impactar la productividad del equipo y dificultar el onboarding de nuevos
  desarrolladores.
* No se cuenta con herramientas de CI/CD, lo que impacta los feedback loops del proyecto y dificulta la entrega de nuevas
  funcionalidades, esto puede impactar la productividad del equipo. 
### Propuestas de mejora
* Implementar pruebas unitarias para los modulos users y promos, esto permitirá validar el comportamiento de estos modulos y
  mejorar los feedback loops del proyecto.
* Implementar pipelines de CI/CD para automatizar las pruebas y despliegues, esto permitirá reducir el tiempo de entrega de
  nuevas funcionalidades, reducir la carga cognitiva del equipo y mejorar los feedback loops.
* Documentar el proyecto, esto permitirá tener una guía de referencia para los desarrolladores, facilitar el onboarding de
  nuevos desarrolladores y mejorar la productividad del equipo.
### Posibles métricas a implementar
* **Porcentaje de cobertura de pruebas unitarias**: Permite medir la cantidad de código que está cubierto por pruebas unitarias,
  esto permite mejorar los feedback loops al ofrecer retroalimentación sobre el código de nuevas funcionalidades
  con respecto a su cobertura, y así mejorar el performance del equipo.
* **Tiempo promedio de ejecución de pruebas unitarias**: Permite identificar cuellos de botella en el proceso de desarrollo
  y mejorar la productividad del equipo al tener mejores feedback loops y ofrecer una experiencia más satisfactoria a 
  los desarrolladores con el sistema.
* **Tiempo promedio de entrega de nuevas funcionalidades**: Permite medir la eficiencia del equipo en la entrega de nuevas
  funcionalidades, identificar posible modulos del código con alta carga cognitiva y evaluar la eficiencia de los sistemas de 
  CI/CD
* **Complejidad percibida del código**: Permite identificar posibles puntos de mejora que permitan reducir la carga cognitiva del equipo y 
  con esto la productividad.
* **Dificultad percibida para entender el propósito del proyecto**: Permite identificar posibles puntos de mejora en la documentación
  del proyecto y facilitar el onboarding de nuevos desarrolladores.
## Uso de IA
Se experimentó utilizando Github Copilot y el plugin disponible para IntelliJ IDEA, se realizaron experimentos para generar
documentación (la escritura de este documento), la generación de pruebas unitarias y sugerencias de refactorización.
### Generación de documentación con Github Copilot
Se utilizó Github Copilot para generar la documentación de este documento, se observaron distintos comportamientos,
la herramienta es capaz de sugerir información coherente y relevante a partir del texto previo utilizando, tanto utilizando
solo la información del archivo actual, como utilizando información de otros archivos del proyecto, tal y como se observa en las siguientes imágenes.
#### Utilizando solo la información del archivo actual
![docBasicExample.png](img%2Fdevex-ia%2FdocBasicExample.png)
#### Utilizando información de otros archivos del proyecto
![docFromAnotherFileInformation.png](img%2Fdevex-ia%2FdocFromAnotherFileInformation.png)
Adicionalmente se observa que es capaz de sugerir parrafos completos de documentación tal y como se observa en el siguiente video:

https://github.com/CSDT-ECI/JC-Rojas-APIPromo/assets/45981880/cabf5fa3-8009-4294-8f0c-41428a46e2b7

Sin embargo, la herramienta no es capaz de sugerir información coherente en todos los casos, y tiende a repetir información,
adicionalmente considero que si bien muchas veces aporta como un guía para la documentación, es necesaria la intervención
humana para asegurar que la información es correcta y se estructura de forma adecuada.

### Generación de pruebas unitarias con Github Copilot
Se utilizó Github Copilot para generar pruebas unitarias para el `userService`, después de realizar varios 
experimentos se encontraron resultados satisfactorios, la herramienta es capaz de sugerir pruebas unitarias coherentes
y relevantes. Para lograr esto fue necesario añadir las clases implicadas y especificar un prompt para que la herramienta generara mejores
pruebas unitarias, tal y como se observa en el siguiente video y en las siguientes imágenes. Sin embargo, la respuesta inicial
solo incluye los casos felices, por lo que es necesario añadir casos de borde y casos de error para tener una cobertura,
estos casos se pueden generar a partir de prompts adicionales en la misma conversación.
#### Uso de Github Copilot para generar pruebas unitarias
https://github.com/CSDT-ECI/JC-Rojas-APIPromo/assets/45981880/f62dfcf6-479b-4353-a849-e077f3a62d2b
#### Ejemplo de estructura de las pruebas generadas
![testStructureExample.png](img%2Fdevex-ia%2FtestStructureExample.png)
#### Reporte de ejecución de las pruebas generadas
![generatedTests.png](img%2Fdevex-ia%2FgeneratedTests.png)
#### Casos borde y casos de error sugeridos
![proposedEdgeCases.png](img%2Fdevex-ia%2FproposedEdgeCases.png)

En general, la herramienta es capaz de sugerir pruebas unitarias coherentes y relevantes, sin embargo, es necesario
que el desarrollador tenga un conocimiento previo de las clases implicadas y que evalue la tanto la calidad como la utilidad de las pruebas generadas
puesto que en ocasiones no cumplen con los estándares de calidad del proyecto, o son pruebas de funcionalidades 
que no existen y que no son relevantes para el proyecto.

Algunas veces las pruebas generadas requieren de intervención del desarrollador para poblamiento de datos o para
ajustar los casos de prueba.
### Sugerencias de refactorización
Se utilizó Github Copilot para generar sugerencias de refactorización para `UserLocation`
al incluir el contexto necesario la herramienta sugirió el siguiente refactor para la clase `UserLocation`:
![suggestedRefactor1.png](img%2Fdevex-ia%2FsuggestedRefactor1.png)
![suggestedRefactor2.png](img%2Fdevex-ia%2FsuggestedRefactor2.png)
Esta sugerencia reduce la complejidad generada por la clase `UserLocation` y facilita la manipulación del código.
Otra sugerencia de refactorización que se obtuvo al especificar que se deseaba un cambio de diseño para poder manejar casos más generales
fue la siguiente: 
![suggestedRedesign.png](img%2Fdevex-ia%2FsuggestedRedesign.png)
### Conclusiones
En general considero que las sugerencias de la herramienta son útiles para guiar al desarrollador en las distintas
tareas que se realizan en el desarrollo de software, sin embargo, es necesario que el desarrollador tenga un conocimiento
para evaluar las respuestas que mejor se ajusten al proyecto y adaptarlas si es necesario, además de poder enriquecer las
interacciones con la herramienta para obtener mejores sugerencias.
