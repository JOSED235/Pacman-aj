package model.ia;

import estructuras.*;
import model.entidades.Movimiento;

public class FantasmaBFS implements FantasmaIA {

    @Override
    public Movimiento calcularMovimiento(Vertice origen, Vertice destino) {
        TablaHash<Vertice, Vertice> padre = new TablaHash<>();
        Cola<Vertice> cola = new Cola<>();
        ListaEnlazada<Vertice> visitados = new ListaEnlazada<>();

        cola.encolar(origen);
        visitados.agregar(origen);

        while (!cola.estaVacia()) {
            Vertice actual = cola.desencolar();

            if (actual.equals(destino)) break;

            for (Movimiento dir : Movimiento.getDirecciones()) {
                if (!actual.isNull(dir)) {
                    Vertice vecino = (Vertice) actual.getArista(dir).getDestino();

                    if (!visitados.contiene(vecino)) {
                        padre.put(vecino, actual);
                        visitados.agregar(vecino);
                        cola.encolar(vecino);
                    }
                }
            }
        }

        // reconstruir camino
        Vertice actual = destino;
        while (padre.containsKey(actual) && !padre.get(actual).equals(origen)) {
            actual = padre.get(actual);
        }

        if (!padre.containsKey(actual)) return null;

        for (Movimiento dir : Movimiento.getDirecciones()) {
            if (!origen.isNull(dir) && origen.getArista(dir).getDestino().equals(actual)) {
                return dir;
            }
        }

        return null;
    }
}
