package model.algoritmos;

import model.grafo.*;

import estructuras.ListaEnlazada;
import estructuras.Nodo;
import estructuras.TablaHash;
import estructuras.UnionFind;

public class Kruskal<T> {

    private IGrafo<T> grafo;

    public Kruskal(IGrafo<T> grafo) {
        this.grafo = grafo;
    }

    /**
     * Calcula el árbol de recubrimiento mínimo usando el algoritmo de Kruskal.
     * Retorna una lista enlazada con las aristas del MST.
     */
    public ListaEnlazada<Arista<T>> calcularMST() {
        ListaEnlazada<Arista<T>> resultado = new ListaEnlazada<>();

        // Obtener todos los vértices
        ListaEnlazada<T> vertices = grafo.obtenerVertices();

        // Inicializar UnionFind para los vértices
        UnionFind<T> uf = new UnionFind<>();
        Nodo<T> actual = vertices.getCabeza();
        while (actual != null) {
            uf.makeSet(actual.getDato());
            actual = actual.getSiguiente();
        }

        // Obtener todas las aristas del grafo sin duplicados
        ListaEnlazada<Arista<T>> todasAristas = obtenerTodasAristasNoDuplicadas();

        // Ordenar las aristas por peso (usaremos un método de ordenación simple)
        ordenarPorPeso(todasAristas);

        // Recorrer las aristas en orden
        Nodo<Arista<T>> nodoArista = todasAristas.getCabeza();
        while (nodoArista != null) {
            Arista<T> arista = nodoArista.getDato();

            T raizOrigen = uf.find(arista.origen);
            T raizDestino = uf.find(arista.destino);

            // Si no forman ciclo, unir y añadir la arista al resultado
            if (!raizOrigen.equals(raizDestino)) {
                resultado.agregar(arista);
                uf.union(raizOrigen, raizDestino);
            }

            nodoArista = nodoArista.getSiguiente();
        }

        return resultado;
    }

    // Obtiene todas las aristas del grafo sin contar (v,u) y (u,v) como distintas
    private ListaEnlazada<Arista<T>> obtenerTodasAristasNoDuplicadas() {
        ListaEnlazada<Arista<T>> lista = new ListaEnlazada<>();
        TablaHash<String, Boolean> visitados = new TablaHash<>();

        ListaEnlazada<T> vertices = grafo.obtenerVertices();
        Nodo<T> nodoVertice = vertices.getCabeza();

        while (nodoVertice != null) {
            T origen = nodoVertice.getDato();
            ListaEnlazada<T> adyacentes = grafo.obtenerAdyacentes(origen);
            Nodo<T> nodoAdyacente = adyacentes.getCabeza();

            while (nodoAdyacente != null) {
                T destino = nodoAdyacente.getDato();

                // Crear clave ordenada para evitar duplicados (e.g. "a,b")
                String clave1 = origen.toString() + "," + destino.toString();
                String clave2 = destino.toString() + "," + origen.toString();

                if (!visitados.containsKey(clave1) && !visitados.containsKey(clave2)) {
                    double peso = grafo.obtenerPesoArista(origen, destino);
                    lista.agregar(new Arista<>(origen, destino, peso));
                    visitados.put(clave1, true);
                    visitados.put(clave2, true);
                }

                nodoAdyacente = nodoAdyacente.getSiguiente();
            }

            nodoVertice = nodoVertice.getSiguiente();
        }

        return lista;
    }

    // Ordena la lista de aristas por peso usando un método de ordenación simple (bubble sort)
    private void ordenarPorPeso(ListaEnlazada<Arista<T>> lista) {
        if (lista == null || lista.size() <= 1) return;

        boolean cambio;
        do {
            cambio = false;
            Nodo<Arista<T>> actual = lista.getCabeza();

            while (actual != null && actual.getSiguiente() != null) {
                Arista<T> a1 = actual.getDato();
                Arista<T> a2 = actual.getSiguiente().getDato();

                if (a1.peso > a2.peso) {
                    // Intercambiar datos
                    actual.setDato(a2);
                    actual.getSiguiente().setDato(a1);
                    cambio = true;
                }
                actual = actual.getSiguiente();
            }
        } while (cambio);
    }
}

