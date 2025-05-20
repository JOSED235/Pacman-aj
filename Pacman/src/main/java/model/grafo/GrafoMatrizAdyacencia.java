package model.grafo;

import estructuras.ListaEnlazada;
import estructuras.Nodo;

public class GrafoMatrizAdyacencia<T> implements IGrafo<T> {

    private static final int CAPACIDAD_INICIAL = 100;

    private ListaEnlazada<T> vertices;     // Lista de vértices
    private double[][] matriz;              // Matriz de adyacencia para pesos
    private int numVertices;                // Cantidad actual de vértices
    private int numAristas;                 // Cantidad actual de aristas

    public GrafoMatrizAdyacencia() {
        this.vertices = new ListaEnlazada<>();
        this.matriz = new double[CAPACIDAD_INICIAL][CAPACIDAD_INICIAL];
        this.numVertices = 0;
        this.numAristas = 0;

        // Inicializar matriz con infinito (sin arista)
        for (int i = 0; i < CAPACIDAD_INICIAL; i++) {
            for (int j = 0; j < CAPACIDAD_INICIAL; j++) {
                matriz[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    @Override
    public void agregarVertice(T vertice) {
        if (existeVertice(vertice)) {
            return;
        }
        if (numVertices >= CAPACIDAD_INICIAL) {
            throw new RuntimeException("Capacidad máxima de vértices alcanzada");
        }
        vertices.agregar(vertice);
        numVertices++;
    }

    // Obtiene índice del vértice, o -1 si no existe
    private int indiceVertice(T vertice) {
        Nodo<T> actual = vertices.getCabeza();
        int indice = 0;
        while (actual != null) {
            if (actual.getDato().equals(vertice)) {
                return indice;
            }
            actual = actual.getSiguiente();
            indice++;
        }
        return -1;
    }

    @Override
    public void agregarArista(T origen, T destino, double peso) {
        int i = indiceVertice(origen);
        int j = indiceVertice(destino);

        if (i == -1 || j == -1) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen");
        }

        if (matriz[i][j] == Double.POSITIVE_INFINITY) {
            numAristas++;
        }
        matriz[i][j] = peso;
    }

    @Override
    public ListaEnlazada<T> obtenerAdyacentes(T vertice) {
        int i = indiceVertice(vertice);
        if (i == -1) {
            throw new IllegalArgumentException("El vértice no existe");
        }

        ListaEnlazada<T> adyacentes = new ListaEnlazada<>();
        for (int j = 0; j < numVertices; j++) {
            if (matriz[i][j] != Double.POSITIVE_INFINITY) {
                // Obtener vértice en posición j
                Nodo<T> actual = vertices.getCabeza();
                int idx = 0;
                while (actual != null) {
                    if (idx == j) {
                        adyacentes.agregar(actual.getDato());
                        break;
                    }
                    actual = actual.getSiguiente();
                    idx++;
                }
            }
        }
        return adyacentes;
    }

    @Override
    public boolean existeVertice(T vertice) {
        return indiceVertice(vertice) != -1;
    }

    @Override
    public boolean existeArista(T origen, T destino) {
        int i = indiceVertice(origen);
        int j = indiceVertice(destino);
        if (i == -1 || j == -1) return false;
        return matriz[i][j] != Double.POSITIVE_INFINITY;
    }

    @Override
    public double obtenerPesoArista(T origen, T destino) {
        int i = indiceVertice(origen);
        int j = indiceVertice(destino);
        if (i == -1 || j == -1) {
            throw new IllegalArgumentException("Uno o ambos vértices no existen");
        }
        return matriz[i][j];
    }

    @Override
    public int cantidadVertices() {
        return numVertices;
    }

    @Override
    public int cantidadAristas() {
        return numAristas;
    }
    @Override
    public ListaEnlazada<T> obtenerVertices() {
        return vertices;
    }
}
