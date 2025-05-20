package model.grafo;

import estructuras.ListaEnlazada;
import estructuras.Nodo;
import estructuras.Pila;

public class DFS<T> {

    private IGrafo<T> grafo;

    public DFS(IGrafo<T> grafo) {
        this.grafo = grafo;
    }

    /**
     * Realiza un recorrido DFS desde el vértice inicial dado
     * @param inicio vértice inicial
     * @return Lista enlazada con los vértices visitados en orden DFS
     */
    public ListaEnlazada<T> recorridoDFS(T inicio) {
        if (!grafo.existeVertice(inicio)) {
            throw new IllegalArgumentException("El vértice inicial no existe en el grafo");
        }

        ListaEnlazada<T> visitados = new ListaEnlazada<>();
        Pila<T> pila = new Pila<>();
        ListaEnlazada<T> marcados = new ListaEnlazada<>();

        pila.push(inicio);

        while (!pila.estaVacia()) {
            T actual = pila.pop();

            if (!estaEnLista(marcados, actual)) {
                visitados.agregar(actual);
                marcados.agregar(actual);

                ListaEnlazada<T> adyacentes = grafo.obtenerAdyacentes(actual);
                Nodo<T> nodoAdyacente = adyacentes.getCabeza();

                // Empilar los adyacentes (puedes cambiar el orden si quieres)
                while (nodoAdyacente != null) {
                    T vecino = nodoAdyacente.getDato();
                    if (!estaEnLista(marcados, vecino)) {
                        pila.push(vecino);
                    }
                    nodoAdyacente = nodoAdyacente.getSiguiente();
                }
            }
        }

        return visitados;
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
