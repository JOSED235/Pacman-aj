package model.grafo;

import estructuras.*;
import javafx.scene.layout.GridPane;
import model.entidades.Consumible;
import model.entidades.TypeOfConsumable;

public class GrafoMatrizAdyacencia {

    private TablaHashGrafo vertices;
    private double[][] matriz;
    private int numVertices;
    private int numAristas;
    private int numComestibles;
    private final GridPane consumibles;

    public GrafoMatrizAdyacencia(int rows, int cols) {
        consumibles = new GridPane();
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

    public void agregarVertice(int x, int y, TypeOfConsumable type) {
        int dato = (x * matriz[0].length) + y;
        matriz[x][y] = 1;
        Vertice v;
        if(type == null){
            v = new Vertice(dato, x, y,null);
        }else {
            Consumible c = new Consumible(type);
            consumibles.add(c.getNode(),y,x);
            v = new Vertice(dato, x, y, c);
            if (type != TypeOfConsumable.TROPHY) numComestibles++;
        }
        vertices.insert(dato, v);
        numVertices++;
    }

    /** Casilla de la casa de los fantasmas o de su puerta: transitable, sin consumible. */
    public void agregarVerticeCasa(int x, int y) {
        int dato = (x * matriz[0].length) + y;
        matriz[x][y] = 1;
        Vertice v = new Vertice(dato, x, y, null);
        v.setCasaFantasmas(true);
        vertices.insert(dato, v);
        numVertices++;
    }

    /** Registra un vértice ya creado (posición inicial de un personaje) y lo vuelve transitable. */
    public void posicionVertice(Vertice position){
        matriz[position.getX()][position.getY()] = 1;
        vertices.insert(position.getDato(), position);
        numVertices++;
    }

    public void agregarArista(int origen) {
        Vertice first = vertices.get(origen);
        if (first == null) return;

        int i = first.getX();
        int j = first.getY();


        // Derecha
        for (int k = j + 1, cont = 1; k < matriz[i].length; k++, cont++) {
            if (matriz[i][k] == 1 || matriz[i][k] == 0 ) {
                crearArista(first, i, k, cont, "right");
                break;
            } else if (matriz[i][k] < 0) break;
        }

        // Izquierda
        for (int k = j - 1, cont = 1; k >= 0; k--, cont++) {
            if (matriz[i][k] == 1 || matriz[i][k] == 0) {
                crearArista(first, i, k, cont, "left");
                break;
            } else if (matriz[i][k] < 0) break;
        }

        // Abajo
        for (int k = i + 1, cont = 1; k < matriz.length; k++, cont++) {
            if (matriz[k][j] == 1 || matriz[k][j] == 0) {
                crearArista(first, k, j, cont, "down");
                break;
            } else if (matriz[k][j] < 0) break;
        }

        // Arriba
        for (int k = i - 1, cont = 1; k >= 0; k--, cont++) {
            if (matriz[k][j] == 1 || matriz[k][j] == 0) {
                crearArista(first, k, j, cont, "up");
                break;
            } else if (matriz[k][j] < 0) break;
        }
    }

    public void crearPuente(int origen, int objetivo){
        Vertice primero = vertices.get(origen);
        Vertice segundo = vertices.get(objetivo);

        primero.setRight(new Arista(primero, segundo, 1));
        segundo.setLeft(new Arista(segundo, primero, 1));
    }

    private void crearArista(Vertice origen, int i, int j, int peso, String direccion) {
        int clave = (i * matriz[0].length) + j;
        Vertice destino = vertices.get(clave);
        if (destino == null) return;
        // Puerta de un solo sentido: desde el laberinto no se puede entrar a la casa de los fantasmas
        if (!origen.isCasaFantasmas() && destino.isCasaFantasmas()) return;

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

    /** Puntos y pastillas de poder: al comerlos todos se completa el nivel. */
    public int cantidadComestibles() {
        return numComestibles;
    }

    public Vertice getVertice(int fila, int columna) {
        return vertices.get(fila * matriz[0].length + columna);
    }

    public GridPane getConsumibles(){return consumibles;}
}
