package model.algoritmos;

import model.grafo.*;
import estructuras.*;

public class Dijkstra<T> {

    private final IGrafo<T> grafo;

    public Dijkstra(IGrafo<T> grafo) {
        this.grafo = grafo;
    }

    /**
     * Calcula las distancias mínimas desde el vértice origen a todos los demás vértices.
     * Retorna una TablaHash con las distancias mínimas.
     */
    public TablaHash<T, Double> calcularDistanciasMinimas(T origen) {
        if (!grafo.existeVertice(origen)) {
            throw new IllegalArgumentException("El vértice origen no existe");
        }

        TablaHash<T, Double> distancias = new TablaHash<>();
        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        ListaEnlazada<T> todosVertices = grafo.obtenerVertices(); // Asegúrate que exista este método en IGrafo

        // Inicializar distancias a infinito
        Nodo<T> nodo = todosVertices.getCabeza();
        while (nodo != null) {
            distancias.put(nodo.getDato(), Double.POSITIVE_INFINITY);
            nodo = nodo.getSiguiente();
        }
        distancias.put(origen, 0.0);

        ColaPrioridad<T> cola = new ColaPrioridad<>();
        cola.encolarConPrioridad(origen, 0.0);

        while (!cola.estaVacia()) {
            T actual = cola.desencolar();

            if (estaEnLista(visitados, actual)) continue;
            visitados.agregar(actual);

            ListaEnlazada<T> adyacentes = grafo.obtenerAdyacentes(actual);
            Nodo<T> nodoAdyacente = adyacentes.getCabeza();

            while (nodoAdyacente != null) {
                T vecino = nodoAdyacente.getDato();
                double pesoArista = grafo.obtenerPesoArista(actual, vecino);

                Double distanciaActual = distancias.get(actual);
                Double distanciaVecino = distancias.get(vecino);

                double nuevaDistancia = (distanciaActual != null ? distanciaActual : Double.POSITIVE_INFINITY) + pesoArista;
                if (distanciaVecino == null || nuevaDistancia < distanciaVecino) {
                    distancias.put(vecino, nuevaDistancia);
                    cola.encolarConPrioridad(vecino, nuevaDistancia);
                }

                nodoAdyacente = nodoAdyacente.getSiguiente();
            }
        }

        return distancias;
    }

    private boolean estaEnLista(ListaEnlazada<T> lista, T elemento) {
        Nodo<T> actual = lista.getCabeza();
        while (actual != null) {
            if (actual.getDato().equals(elemento)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
}
