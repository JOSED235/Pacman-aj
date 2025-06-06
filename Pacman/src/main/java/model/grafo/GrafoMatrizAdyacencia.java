package model.grafo;

import estructuras.*;

public class GrafoMatrizAdyacencia {

    private TablaHashGrafo vertices;
    private double[][] matriz;
    private int numVertices;
    private int numAristas;

    public GrafoMatrizAdyacencia(int rows, int cols) {
        this.vertices = new TablaHashGrafo();
        this.matriz = new double[rows][cols];
        this.numVertices = 0;
        this.numAristas = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matriz[i][j] = Double.POSITIVE_INFINITY;
            }
        }
    }

    public void inicializaMatriz(int x, int y, int dato) {
        matriz[x][y] = dato;
    }

    public void agregarVertice(int x, int y) {
        int dato = (x * matriz[0].length) + y;

        matriz[x][y] = 1;
        Vertice v = new Vertice(dato);
        vertices.insert(dato, v);
        numVertices++;
    }

    public void agregarArista(int origen) {
        Vertice first = vertices.get(origen);
        if (first == null) return;  // 🔒 Protección contra NullPointerException

        int i = origen / matriz[0].length;
        int j = origen % matriz[0].length;


        // Derecha
        for (int k = j + 1, cont = 1; k < matriz[i].length; k++, cont++) {
            if (matriz[i][k] == 1) {
                crearArista(first, i, k, cont, "right");
                break;
            } else if (matriz[i][k] == -1) break;
        }

        // Izquierda
        for (int k = j - 1, cont = 1; k >= 0; k--, cont++) {
            if (matriz[i][k] == 1) {
                crearArista(first, i, k, cont, "left");
                break;
            } else if (matriz[i][k] == -1) break;
        }

        // Abajo
        for (int k = i + 1, cont = 1; k < matriz.length; k++, cont++) {
            if (matriz[k][j] == 1) {
                crearArista(first, k, j, cont, "down");
                break;
            } else if (matriz[k][j] == -1) break;
        }

        // Arriba
        for (int k = i - 1, cont = 1; k >= 0; k--, cont++) {
            if (matriz[k][j] == 1) {
                crearArista(first, k, j, cont, "up");
                break;
            } else if (matriz[k][j] == -1) break;
        }
    }

    private void crearArista(Vertice origen, int i, int j, int peso, String direccion) {
        int clave = (i * 10) + j;
        Vertice destino = vertices.get(clave);
        if (destino == null) return;

        Arista arista = new Arista(origen, destino, peso);
        switch (direccion) {
            case "right" -> origen.setRight(arista);
            case "left" -> origen.setLeft(arista);
            case "down" -> origen.setDown(arista);
            case "up" -> origen.setUp(arista);
        }
        numAristas++;
    }

    public int cantidadVertices() {
        return numVertices;
    }

    public int cantidadAristas() {
        return numAristas;
    }
}
