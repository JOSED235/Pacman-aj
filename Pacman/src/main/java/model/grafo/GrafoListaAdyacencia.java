package model.grafo;

import estructuras.ListaEnlazada;
import estructuras.Nodo;
import estructuras.TablaHash;

// Implementación de un grafo dirigido usando lista de adyacencia
// Utiliza una tabla hash para mapear cada vértice a una lista enlazada de aristas (destino + peso)
public class GrafoListaAdyacencia<T> implements IGrafo<T> {

    // Clase interna que representa una arista con destino y peso
    private static class Arista<T> {
        T destino;
        double peso;

        Arista(T destino, double peso) {
            this.destino = destino;
            this.peso = peso;
        }

        // Igualdad basada en el destino para evitar duplicados
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Arista)) return false;
            Arista<?> other = (Arista<?>) obj;
            return destino.equals(other.destino);
        }
    }

    // Mapa del grafo: cada vértice se asocia a su lista de aristas adyacentes
    private final TablaHash<T, ListaEnlazada<Arista<T>>> grafo;
    private int totalAristas; // Contador de aristas en el grafo

    // Constructor: inicializa la tabla hash vacía y contador de aristas a cero
    public GrafoListaAdyacencia() {
        grafo = new TablaHash<>();
        totalAristas = 0;
    }

    // Agrega un vértice si no existe ya en el grafo
    @Override
    public void agregarVertice(T vertice) {
        if (!grafo.containsKey(vertice)) {
            grafo.put(vertice, new ListaEnlazada<>());
        }
    }

    // Agrega una arista dirigida desde origen a destino con un peso dado
    // Si la arista ya existe, actualiza su peso
    @Override
    public void agregarArista(T origen, T destino, double peso) {
        if (!grafo.containsKey(origen) || !grafo.containsKey(destino)) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen");
        }
        ListaEnlazada<Arista<T>> adyacentes = grafo.get(origen);
        Nodo<Arista<T>> actual = adyacentes.getCabeza();

        // Buscar si arista ya existe para actualizar peso
        while (actual != null) {
            if (actual.getDato().destino.equals(destino)) {
                actual.getDato().peso = peso;
                return;
            }
            actual = actual.getSiguiente();
        }

        // Si no existe, agregar nueva arista y aumentar contador
        adyacentes.agregar(new Arista<>(destino, peso));
        totalAristas++;
    }

    // Devuelve la lista de vértices adyacentes a un vértice dado
    @Override
    public ListaEnlazada<T> obtenerAdyacentes(T vertice) {
        if (!grafo.containsKey(vertice)) {
            throw new IllegalArgumentException("El vértice no existe");
        }
        ListaEnlazada<T> vecinos = new ListaEnlazada<>();
        ListaEnlazada<Arista<T>> adyacentes = grafo.get(vertice);
        Nodo<Arista<T>> actual = adyacentes.getCabeza();

        // Recorrer las aristas y agregar los destinos a la lista
        while (actual != null) {
            vecinos.agregar(actual.getDato().destino);
            actual = actual.getSiguiente();
        }
        return vecinos;
    }

    // Verifica si el vértice existe en el grafo
    @Override
    public boolean existeVertice(T vertice) {
        return grafo.containsKey(vertice);
    }

    // Verifica si existe una arista de origen a destino
    @Override
    public boolean existeArista(T origen, T destino) {
        if (!grafo.containsKey(origen)) return false;
        ListaEnlazada<Arista<T>> adyacentes = grafo.get(origen);
        Nodo<Arista<T>> actual = adyacentes.getCabeza();

        while (actual != null) {
            if (actual.getDato().destino.equals(destino)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    // Devuelve el peso de la arista de origen a destino, o infinito si no existe
    @Override
    public double obtenerPesoArista(T origen, T destino) {
        if (!grafo.containsKey(origen)) {
            throw new IllegalArgumentException("El vértice origen no existe");
        }
        ListaEnlazada<Arista<T>> adyacentes = grafo.get(origen);
        Nodo<Arista<T>> actual = adyacentes.getCabeza();

        while (actual != null) {
            if (actual.getDato().destino.equals(destino)) {
                return actual.getDato().peso;
            }
            actual = actual.getSiguiente();
        }
        return Double.POSITIVE_INFINITY;
    }

    // Devuelve la cantidad total de vértices en el grafo
    @Override
    public int cantidadVertices() {
        return grafo.size();
    }

    // Devuelve la cantidad total de aristas en el grafo
    @Override
    public int cantidadAristas() {
        return totalAristas;
    }

    // Devuelve una lista con todos los vértices del grafo
    @Override
    public ListaEnlazada<T> obtenerVertices() {
        return grafo.obtenerClaves();
    }
}
