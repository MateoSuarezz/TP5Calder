#  Ejercicio Obligatorio Práctica 5 - 2022
La Asociación de Fútbol de Argentina (AFA) organiza cada año el Torneo de la Liga Profesional de Fútbol (LPF), donde se disputa una rueda de partidos entre los diferentes equipos de fútbol que participan del torneo. Cada equipo participante inicia el torneo con puntaje cero y al finalizar la rueda única de partidos, aquel equipo que haya obtenido la mayor cantidad de puntos será consagrado campeón del Torneo LPF de ese año.
Los participantes del torneo son clasificados en tablas, para poder valorar su actuación. Estas tablas aplican diversos criterios de acuerdo con los resultados, que determinan una puntuación a cada uno de los competidores, cada victoria entrega 3 puntos, 1 por cada empate y 0 por derrota. Para el caso de empate en puntos en cualquiera de las posiciones, se aplican algunas de las disposiciones del Reglamento General de AFA:
- En favor del equipo que registre mayor diferencia de goles.
- De subsistir la igualdad, en favor del equipo que hubiese obtenido mayor cantidad de goles a favor.
- De mantenerse la igualdad, se utilizará el sistema Fair Play, el cual se determina de acuerdo a las amonestaciones y expulsiones recibidas: una tarjeta amarilla es un punto en contra (−1); una segunda amarilla (roja) son tres puntos (−3); una roja directa, cuatro (−4) y una amarilla y roja directa son cinco puntos en contra (−5).

En este problema, se requiere desarrollar un programa Java que maneje la tabla de posiciones de un torneo de la LPF, con las siguientes operaciones y requerimientos de complejidad en el peor caso:
- **agregarPartido**, se encarga de registrar en la tabla de posiciones los valores asociados a los equipos que jugaron un partido del torneo. Incluye agregar todos los datos del partido necesarios para el cálculo de los puntajes según el reglamento del torneo. Esta operación debe realizarse en O(log n).
- **puntos**, operación que dado un equipo retorna los puntos que tiene ese equipo según la tabla de posiciones. Esta operación debe realizarse en O(log n).
- **puntero**, esta operación retorna el equipo que está en la primera posición de la tabla y todos los valores en el torneo asociados a ese equipo. Esta operación debe realizarse en O(1).

- **siguiente**, operación que, dado un equipo, retorna el equipo siguiente en la tabla de posiciones. Esta operación debe realizarse en O(log n).

**Nota:** Para reducir la complejidad en el programa, en la última regla para desempate de puntos entre dos equipos, **Fair Play**, se considerará, como puntaje para las penalizaciones, sólo la cantidad de tarjetas amarillas y rojas obtenidas por los respectivos equipos empatados en puntos. El equipo con menor número de amonestaciones/expulsiones será el ganador. 



**Importante:** Para resolver el problema debe utilizar alguna de las implementaciones de Arbol Binario de Busqueda que se encuentran en el repositorio de la materia.
