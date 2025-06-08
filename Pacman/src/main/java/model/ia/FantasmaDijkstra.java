package model.ia;

import estructuras.*;
import model.entidades.Movimiento;
import model.grafo.Arista;

public class FantasmaDijkstra implements FantasmaIA {

    @Override
    public Movimiento calcularMovimiento(Vertice origen, Vertice destino) {
        TablaHash<Vertice, Integer> distancia = new TablaHash<>();
        TablaHash<Vertice, Vertice> anterior = new TablaHash<>();
        ListaEnlazada<Vertice> noVisitados = new ListaEnlazada<>();

        // Inicializar distancias
        for (Movimiento dir : Movimiento.getDirecciones()) {
            if (!origen.isNull(dir)) {
                Vertice vecino = (Vertice) getArista(origen, dir).getDestino();
                distancia.put(vecino, Integer.MAX_VALUE);
                anterior.put(vecino, null);
                noVisitados.agregar(vecino);
            }
        }

        distancia.put(origen, 0);
        noVisitados.agregar(origen);

        while (!noVisitados.estaVacia()) {
            Vertice actual = noVisitados.obtener(0);
            int minDist = distancia.get(actual);

            for (int i = 1; i < noVisitados.size(); i++) {
                Vertice v = noVisitados.obtener(i);
                int dist = distancia.get(v);
                if (dist < minDist) {
                    actual = v;
                    minDist = dist;
                }
            }

            noVisitados.eliminar(actual);

            if (actual.equals(destino)) break;

            for (Movimiento dir : Movimiento.getDirecciones()) {
                if (!actual.isNull(dir)) {
                    Vertice vecino = (Vertice) getArista(actual, dir).getDestino();
                    int nuevaDistancia = distancia.get(actual) + 1;

                    if (!distancia.containsKey(vecino) || nuevaDistancia < distancia.get(vecino)) {
                        distancia.put(vecino, nuevaDistancia);
                        anterior.put(vecino, actual);
                        if (!noVisitados.contiene(vecino)) {
                            noVisitados.agregar(vecino);
                        }
                    }
                }
            }
        }

        // Reconstruir camino
        Vertice paso = destino;
        while (anterior.get(paso) != null && !anterior.get(paso).equals(origen)) {
            paso = anterior.get(paso);
        }

        return obtenerDireccion(origen, paso);
    }

    private Arista getArista(Vertice vertice, Movimiento dir) {
        return switch (dir) {
            case UP -> vertice.getUp();
            case DOWN -> vertice.getDown();
            case LEFT -> vertice.getLeft();
            case RIGHT -> vertice.getRight();
        };
    }

    private Movimiento obtenerDireccion(Vertice origen, Vertice destino) {
        if (destino == null) return null;

        if (destino.getX() < origen.getX()) return Movimiento.LEFT;
        if (destino.getX() > origen.getX()) return Movimiento.RIGHT;
        if (destino.getY() < origen.getY()) return Movimiento.UP;
        if (destino.getY() > origen.getY()) return Movimiento.DOWN;

        return null;
    }
}
