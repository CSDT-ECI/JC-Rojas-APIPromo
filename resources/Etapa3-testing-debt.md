# Análisis de deuda técnica en pruebas
## Estado Inicial
Al analizar el proyecto se evidencia que no cuenta con ningún tipo de prueba automatizada que 
permita validar el correcto funcionamiento del sistema tal como se puede observar en la única clase de pruebas
del [proyecto](https://github.com/CSDT-ECI/JC-Rojas-APIPromo/blob/7da04ddf4f440be09d952d48279e582faabc58fa/src/test/kotlin/com/riza/apipromo/ApiPromoApplicationTests.kt#L6).

Si bien se exponen dos endpoints en los que se entiende que su propósito es probar el sistema, a continuación
se explica el propósito de cada uno y porque no sirven para probar el sistema
* El endpoint [area/test](https://github.com/CSDT-ECI/JC-Rojas-APIPromo/blob/7da04ddf4f440be09d952d48279e582faabc58fa/src/main/kotlin/com/riza/apipromo/feature/area/AreaController.kt#L26)
  que simplemente permite validar que la aplicación de inició satisfactoriamente, pero no valida ninguna funcionalidad
* El endpoint [user/bulk-user-create](https://github.com/CSDT-ECI/JC-Rojas-APIPromo/blob/7da04ddf4f440be09d952d48279e582faabc58fa/src/main/kotlin/com/riza/apipromo/feature/user/UserController.kt#L135)
  que permite crear un conjunto de usuarios con localizaciones aleatorias, más allá de la lógica de creación este endpoint
  no permite probar mas funcionalidades, adicionalmente al inicializar localizaciones aleatorias se pueden generar combinaciones
  de puntos que no representan poligonos validos, por lo que adicionalmente dificultará las pruebas.

## Estrategia para reducir deuda técnica de pruebas

Para reducir la deuda técnica de pruebas en el proyecto se propone una implementación progresiva de pruebas
unitarias sobre los distintos módulos de la aplicación, siguiendo la siguiente prioridad:
1. Modulo core: En este módulo se encuentra una buena parte de la lógica interna de la aplicación
que corresponde a detectar si un punto se encuentra dentro de un poligono dado, esta parte es fundamental puesto
que varios componentes dependen del funcionamiento los algoritmos de detección y además representan una parte
poco trivial del código
2. Modulo areas: Es el módulo que más utiliza la lógica probada anteriormente, no presenta dependencias modulos distintos
al de detección de puntos en poligonos
3. Modulo users: Operaciones CRUD, es dependencia del modulo de promos 
4. Modulo promos: Operaciones sobre areas y usuarios

## Alcance de etapa 3:
Durante esta etapa de realizarán las pruebas relacionadas al modulo core, los otros modulos seran probados
y refactorizados en etapas siguientes.


## Pruebas del módulo core
## PointInclusion.kt 
Esta clase contiene la implementación de dos algoritmos de geometría para determinar si un punto está dentro de un polígono
estos algoritmos son Crossing Number(Cn) y Winding Number (Wn), la documentación de como funcionan estos algoritmos se puede
encontrar en el siguiente [enlace](http://profs.ic.uff.br/~anselmo/cursos/CGI/slidesNovos/Inclusion%20of%20a%20Point%20in%20a%20Polygon.pdf)

A continuación se presentan los casos de prueba diseñados para esta clase

### Casos generales:
En la siguiente figura se muestra las pruebas generales para ambos algoritmos, donde los puntos Rojos se consideran fuera
del cuadrado, mientras que los puntos verdes dentro, la razón para determinar lo puntos en los bordes superior y derecho 
fuera del poligono se explican en la documentación compartida anteriormente
![SquareTestsCN.png](img%2FEtapa3%2FSquareTestsCN.png)

### Casos de Crossing-Number 
Adicionalmente a este algoritmo se añaden los siguientes casos, este es uno de los casos que se muestran en la documentación,
dónde si un punto se encuentra en una  zona donde el poligono se auto-intersecta se considera fuera del polígono, se añade
un caso donde un punto esta en una zona de auto-intersección y otro caso con un punto considerado dentro del polígono, tal como se
muestra en la siguiente imagen
![complexPolygonCN.png](img%2FEtapa3%2FcomplexPolygonCN.png)

### Casos de Winding-Number
A diferencia del algoritmo de Crossing-Number, las zonas de auto-intersección se consideran parte del polígono, por lo que
para este algoritmo el resultado de esta prueba cambia, adicionalmente se añade un caso donde el punto está en un
área interna del polígono que, donde a pesar de ser un área dentro del perímetro del polígono no se considera parte el
![complexPolygonWN.png](img%2FEtapa3%2FcomplexPolygonWN.png)

### Resultados Crossing-Number
![CNResult.png](img%2FEtapa3%2FCNResult.png)
### Resultados Winding-Number
![WNResult.png](img%2FEtapa3%2FWNResult.png)

### Conclusiones 
Dado que las implementaciones presentadas no estaban pasando las pruebas fue necesario corregir las implementaciones.
Una vez las implementaciones pasaron los casos de prueba propuestos, se decidió refactorizar la lógica del código para 
mejorar la legibilidad y reducir los code smells de este módulo.

## BoundingBox.kt
Esta clase representa un cuadrado que se genera al rededor de un polígono dado y se encarga de verificar si un
punto se encuentra o no dentro de dicho cuadrado, la ilustración que representa los casos de prueba utilizados
se presenta a continuación:
![boundingBox.png](img%2FEtapa3%2FboundingBox.png)
### Resultados
![resultsBoundingBox.png](img%2FEtapa3%2FresultsBoundingBox.png)
### Conclusiones
En este caso no fue necesario modificar el código puesto que la implementación pasa los casos de prueba propuestos.
