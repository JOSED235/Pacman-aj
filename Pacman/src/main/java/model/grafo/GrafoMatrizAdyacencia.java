package model.grafo;

import estructuras.*;

public class GrafoMatrizAdyacencia{

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
        int dato = (x*10) + y;
        matriz[x][y] = 1;
        Vertice v = new Vertice(dato);
        vertices.insert(dato, v);
        numVertices++;
    }

    public void agregarArista(int origen) {
        Vertice first = vertices.get(origen);
        int i = (first.getDato()/10) - 1;
        int j = first.getDato() - i;
        int cont = 1;
        for(int k = j + 1; k < matriz[i].length; k++) {
            if (matriz[i][k] == 1) {
                int a = (i * 10) + k;
                Vertice last = vertices.get(a);
                Arista nuevo = new Arista(first, last, cont);
                first.setRight(nuevo);
                break;
            }else if(matriz[i][k] == -1){break;
            }else if(k == matriz[i].length - 1){
                k = 0;
                int a = (i * 10) + k;
                Vertice last = vertices.get(a);
                Arista nuevo = new Arista(first, last, cont);
                first.setRight(nuevo);
                break;
            }
            cont++;
        }
        cont = 1;
        for(int k = j - 1; k < matriz[i].length && k > -1; k--) {
            if (matriz[i][k] == 1) {
                int a = (i * 10) + k;
                Vertice last = vertices.get(a);
                Arista nuevo = new Arista(first, last, cont);
                first.setLeft(nuevo);
                break;
            }else if(matriz[i][k] == -1){break;
            }else if(k == matriz[i].length - 1){
                k = 0;
                int a = (i * 10) + k;
                Vertice last = vertices.get(a);
                Arista nuevo = new Arista(first, last, cont);
                first.setLeft(nuevo);
                break;
            }
            cont++;
        }
        cont = 1;
        for(int k = i + 1; k < matriz.length; k++) {
            if (matriz[k][j] == 1) {
                int a = (k * 10) + j;
                Vertice last = vertices.get(a);
                Arista nuevo = new Arista(first, last, cont);
                first.setDown(nuevo);
                break;
            }else if(matriz[k][j] == -1){break;}
            cont++;
        }
        cont = 1;
        for(int k = i - 1; k < matriz.length && k > -1; k--) {
            if (matriz[k][j] == 1) {
                int a = (k * 10) + j;
                Vertice last = vertices.get(a);
                Arista nuevo = new Arista(first, last, cont);
                first.setUp(nuevo);
                break;
            }else if(matriz[k][j] == -1){break;}
            cont++;
        }
    }

    public int cantidadVertices() {
        return numVertices;
    }

    public int cantidadAristas() {
        return numAristas;
    }
}
