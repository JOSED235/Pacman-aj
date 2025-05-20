package model.grafo;

import estructuras.ListaEnlazada;
import estructuras.Nodo;

public class FloydWarshall<T> {

    private IGrafo<T> grafo;

    public FloydWarshall(IGrafo<T> grafo) {
        this.grafo = grafo;
    }

    /**
     * Calcula la matriz de distancias mínimas entre todos los pares de vértices.
     * @return objeto ResultadoFloyd con matriz y arreglo de vértices para índice
     */
    public ResultadoFloyd calcularDistancias() {
        ListaEnlazada<T> verticesLista = grafo.obtenerVertices();
        int n = verticesLista.size();

        // Convertir lista enlazada de vértices a array para acceso por índice
        Object[] verticesArray = new Object[n];
        Nodo<T> actual = verticesLista.getCabeza();
        int idx = 0;
        while (actual != null) {
            verticesArray[idx++] = actual.getDato();
            actual = actual.getSiguiente();
        }

        // Inicializar matriz de distancias
        double[][] dist = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                } else {
                    dist[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // Llenar la matriz con pesos del grafo
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                @SuppressWarnings("unchecked")
                T origen = (T) verticesArray[i];
                @SuppressWarnings("unchecked")
                T destino = (T) verticesArray[j];
                if (grafo.existeArista(origen, destino)) {
                    dist[i][j] = grafo.obtenerPesoArista(origen, destino);
                }
            }
        }

        // Algoritmo Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != Double.POSITIVE_INFINITY && dist[k][j] != Double.POSITIVE_INFINITY) {
                        double nuevaDist = dist[i][k] + dist[k][j];
                        if (nuevaDist < dist[i][j]) {
                            dist[i][j] = nuevaDist;
                        }
                    }
                }
            }
        }

        return new ResultadoFloyd<>(verticesArray, dist);
    }

    /**
     * Clase contenedora del resultado de Floyd-Warshall:
     * matriz de distancias y arreglo de vértices para índice.
     */
    public static class ResultadoFloyd<T> {
        public Object[] vertices;  // arreglo de vértices para referencia
        public double[][] distancias; // matriz de distancias mínimas

        public ResultadoFloyd(Object[] vertices, double[][] distancias) {
            this.vertices = vertices;
            this.distancias = distancias;
        }
    }
}
