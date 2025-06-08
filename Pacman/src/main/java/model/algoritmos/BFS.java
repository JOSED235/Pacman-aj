package model.algoritmos;

import model.grafo.*;
import estructuras.Cola;
import estructuras.ListaEnlazada;
import estructuras.Nodo;


public class BFS<T> {

    private IGrafo<T> grafo;

    public BFS(IGrafo<T> grafo) {
        this.grafo = grafo;
    }

    /**
     * Realiza un recorrido BFS desde el vértice inicial dado
     * @param inicio vértice inicial
     * @return Lista enlazada con los vértices visitados en orden BFS
     */
    public ListaEnlazada<T> recorridoBFS(T inicio) {
        if (!grafo.existeVertice(inicio)) {
            throw new IllegalArgumentException("El vértice inicial no existe en el grafo");
        }

        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        Cola<T> cola = new Cola<>();
        ListaEnlazada<T> marcados = new ListaEnlazada<>();

        cola.encolar(inicio);
        marcados.agregar(inicio);

        while (!cola.estaVacia()) {
            T actual = cola.desencolar();
            visitados.agregar(actual);

            ListaEnlazada<T> adyacentes = grafo.obtenerAdyacentes(actual);
            Nodo<T> nodoAdyacente = adyacentes.getCabeza();

            while (nodoAdyacente != null) {
                T vecino = nodoAdyacente.getDato();
                if (!estaEnLista(marcados, vecino)) {
                    cola.encolar(vecino);
                    marcados.agregar(vecino);
                }
                nodoAdyacente = nodoAdyacente.getSiguiente();
            }
        }

        return visitados;
    }

    // Método auxiliar para verificar si un elemento está en una ListaEnlazada
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
