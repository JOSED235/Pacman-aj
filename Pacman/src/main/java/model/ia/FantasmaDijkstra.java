package model.ia;

import estructuras.*;
import model.entidades.Movimiento;
import model.grafo.Arista;

/**
 * Fantasma perseguidor: calcula el camino más corto hasta Pac-Man con Dijkstra
 * sobre el grafo del laberinto y da el primer paso de ese camino.
 */
public class FantasmaDijkstra implements FantasmaIA {

    @Override
    public Movimiento calcularMovimiento(Vertice origen, Vertice destino) {
        if (origen == null || destino == null || origen.equals(destino)) return null;

        TablaHash<Vertice, Double> distancia = new TablaHash<>();
        TablaHash<Vertice, Vertice> anterior = new TablaHash<>();
        TablaHash<Vertice, Boolean> visitado = new TablaHash<>();
        ColaPrioridad<Vertice> pendientes = new ColaPrioridad<>();

        distancia.put(origen, 0.0);
        pendientes.encolarConPrioridad(origen, 0.0);

        while (!pendientes.estaVacia()) {
            // La cola de prioridad entrega el vértice pendiente con menor distancia acumulada
            Vertice actual = pendientes.desencolar();
            if (visitado.containsKey(actual)) continue; // entrada antigua con una distancia mayor
            visitado.put(actual, true);
            if (actual.equals(destino)) break;

            double distActual = distancia.get(actual);
            for (Movimiento dir : Movimiento.getDirecciones()) {
                if (actual.isNull(dir)) continue;
                Arista arista = actual.getArista(dir);
                Vertice vecino = (Vertice) arista.getDestino();
                if (visitado.containsKey(vecino)) continue;
                double nuevaDistancia = distActual + arista.getPeso();
                if (!distancia.containsKey(vecino) || nuevaDistancia < distancia.get(vecino)) {
                    distancia.put(vecino, nuevaDistancia);
                    anterior.put(vecino, actual);
                    pendientes.encolarConPrioridad(vecino, nuevaDistancia);
                }
            }
        }

        if (!anterior.containsKey(destino)) return null; // Pac-Man no es alcanzable

        // Retroceder desde Pac-Man hasta el vértice inmediatamente después del fantasma
        Vertice paso = destino;
        while (!anterior.get(paso).equals(origen)) {
            paso = anterior.get(paso);
        }
        return direccionHacia(origen, paso);
    }

    /** Dirección de la arista que va de origen a vecino (sirve también para el túnel). */
    private Movimiento direccionHacia(Vertice origen, Vertice vecino) {
        for (Movimiento dir : Movimiento.getDirecciones()) {
            if (!origen.isNull(dir) && origen.getArista(dir).getDestino().equals(vecino)) {
                return dir;
            }
        }
        return null;
    }
}
