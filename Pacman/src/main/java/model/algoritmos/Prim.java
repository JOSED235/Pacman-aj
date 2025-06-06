package model.algoritmos;

import model.grafo.*;
import estructuras.*;



public class Prim<T> {

    private IGrafo<T> grafo;

    public Prim(IGrafo<T> grafo) {
        this.grafo = grafo;
    }

    /**
     * Calcula el árbol de recubrimiento mínimo usando Prim.
     * @param inicio vértice inicial para comenzar el MST.
     * @return Lista enlazada con las aristas del MST.
     */
    public ListaEnlazada<Arista<T>> calcularMST(T inicio) {
        if (!grafo.existeVertice(inicio)) {
            throw new IllegalArgumentException("El vértice inicial no existe en el grafo");
        }

        ListaEnlazada<Arista<T>> mst = new ListaEnlazada<>();
        TablaHash<T, Boolean> enMST = new TablaHash<>();
        TablaHash<T, Double> distancias = new TablaHash<>();
        TablaHash<T, T> padres = new TablaHash<>();
        ColaPrioridad<T> cola = new estructuras.ColaPrioridad<T>();

        // Inicializar distancias e inclusión en MST
        ListaEnlazada<T> vertices = grafo.obtenerVertices();
        Nodo<T> actual = vertices.getCabeza();
        while (actual != null) {
            distancias.put(actual.getDato(), Double.POSITIVE_INFINITY);
            enMST.put(actual.getDato(), false);
            padres.put(actual.getDato(), null);
            actual = actual.getSiguiente();
        }

        // Distancia del vértice inicial a 0 y encolar
        distancias.put(inicio, 0.0);
        cola.encolarConPrioridad(inicio, 0.0);

        while (!cola.estaVacia()) {
            T u = cola.desencolar();
            if (enMST.get(u)) continue;  // Si ya está incluido, ignorar
            enMST.put(u, true);

            T padre = padres.get(u);
            if (padre != null) {
                double peso = distancias.get(u);
                mst.agregar(new Arista<>(padre, u, peso));
            }

            // Revisar adyacentes de u
            ListaEnlazada<T> adyacentes = grafo.obtenerAdyacentes(u);
            Nodo<T> nodoAdyacente = adyacentes.getCabeza();
            while (nodoAdyacente != null) {
                T v = nodoAdyacente.getDato();
                if (!enMST.get(v)) {
                    double pesoUV = grafo.obtenerPesoArista(u, v);
                    Double distV = distancias.get(v);
                    if (distV == null || pesoUV < distV) {
                        distancias.put(v, pesoUV);
                        padres.put(v, u);
                        cola.encolarConPrioridad(v, pesoUV);
                    }
                }
                nodoAdyacente = nodoAdyacente.getSiguiente();
            }
        }

        return mst;
    }
}
