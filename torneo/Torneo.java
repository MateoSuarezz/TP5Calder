package torneo;

import torneo.*;

import colecciones.arbol.Diccionario;
import colecciones.arbol.Diccionario.Orden;

import java.util.Set;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Torneo {
    Set<Equipo> equipos;
    Diccionario<PartidosEquipo> posiciones;
    /**
     * Dado un equipo {@code e}, retorna el equipo siguiente (el que le sigue en cantidad de puntos) en la tabla de posiciones.
     * Esta operacion debe realizarse en O(log n).
     * @param e equipo del que se quiere calcular cual es el que le sigue segun la tabla de posiciones del torneo.
     * @return Equipo siguiente segun la tabla de posiciones del torneo, si hay mas de un equipo con partidos jugados.
     */
    public Equipo siguiente(Equipo e){
            LinkedList lista = new LinkedList<>();
            lista.addAll(posiciones.aLista());
            lista.sort(new SortbyPuntos());
            Equipo aux = (Equipo)lista.get(lista.indexOf(e)+1);
        return aux;
    }

    /**
     * Registra en la tabla de posiciones los valores asociados a los equipos que jugaron un partido del torneo.
     * Incluye agregar todos los datos del partido necesarios para el calculo de los puntajes segun el reglamento del torneo.
     * Esta operacion debe realizarse en O(log n).
     * @param eLocal Equipo Local del partido.
     * @param eVisitante Equipo Visitante del partido.
     * @param golesEL goles a favor del equipo Local.
     * @param golesEV goles a favor del equipo Visitante.
     * @param amarillasEL suma de amonestaciones/expulsiones recibidas por jugadores del equipo Local.
     * @param amarillasEV suma de amonestaciones/expulsiones recibidas por jugadores del equipo Visitante.
     * @param rojasEL suma de amonestaciones/expulsiones recibidas por jugadores del equipo Local.
     * @param rojasEV suma de amonestaciones/expulsiones recibidas por jugadores del equipo Visitante.

     */
    public void agregarPartido(Equipo eLocal, Equipo eVisitante, int golesEL, int golesEV, int amarillasEL, int amarillasEV, int rojasEL, int rojasEV){
        
        if(posiciones != null){
            List<PartidosEquipo> lista = new LinkedList<>();
            lista = posiciones.aLista();
            Boolean visitante, local;
            local = lista.contains(eLocal);
            visitante = lista.contains(eVisitante);


            if (local && visitante){
                calcularPuntos(eLocal, golesEL, golesEV, amarillasEL,rojasEL);
                calcularPuntos(eVisitante,golesEV, golesEL, amarillasEV, rojasEV);
            }else{
                if(local == false){
                    throw new Error("No se ha encontrado el equipo local");
                }else{
                    throw new Error("No se ha encontrado el equipo visitante");
                }
            }
        }
    }

    /**
     * Retorna el equipo con mayor puntaje de la tabla de posiciones y todos los valores en el torneo asociados a ese equipo.
     * Esta operacion debe realizarse en O(1).
     * @return datos de los puntajes asociados a los partidos del equipo con mas puntos en la tabla de posiciones.
     */
    public PartidosEquipo puntero(){
        LinkedList lista = new LinkedList<>();
            lista.addAll(posiciones.aLista());
            lista.sort(new SortbyPuntos());
            PartidosEquipo aux = (PartidosEquipo)lista.getLast();
        return aux;
    }

    /**
     * Dado un equipo {@code e} retorna los puntos que tiene ese equipo segun la tabla de posiciones.
     * Esta operacion debe realizarse en O(log n).
     * @param e equipo del que se quiere extraer los puntos acumulados en el torneo.
     * @return los puntos que tiene el equipo {@code e} segun la tabla de posiciones.
     */
    public int puntos(Equipo e){
        return e.getPuntos();
    }


    public void calcularPuntos(Equipo e, int goles, int goles2, int amarillas, int rojas){
            if(goles > goles2){
                e.setPuntos(e.getPuntos() + 3);
            }
            
            if(goles == goles2){
                e.setPuntos(e.getPuntos() + 1);
            }

        if(amarillas==1){
            e.setPuntos(e.getPuntos() - 1);
        }else{
            if(amarillas==2){
                e.setPuntos(e.getPuntos() - 3);
            }else{
                if(amarillas>2){
                    while(amarillas>1){
                        e.setPuntos(e.getPuntos() - 3);
                        amarillas= amarillas - 2;
                    }
                    if(amarillas==1){
                        e.setPuntos(e.getPuntos() - 1);
                    }
                }
            }
        }
        if(rojas>0){
            e.setPuntos(e.getPuntos() - 4*rojas);
        }
    }

    static class SortbyPuntos implements Comparator<PartidosEquipo> {
        public int compare(PartidosEquipo a, PartidosEquipo b){
            return a.getPuntos() - b.getPuntos();
        }
    }
    
}