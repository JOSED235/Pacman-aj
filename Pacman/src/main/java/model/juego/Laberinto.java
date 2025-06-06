package model.juego;

import model.grafo.GrafoMatrizAdyacencia;

import java.util.ArrayList;

public class  Laberinto {

    public static final int CELL_SIZE = 20;
    private static final int[][] mapa = {
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,-1},
            {-1, 1,-1,-1,-1, 1,-1,-1,-1,-1,-1, 1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1, 1}, // Se eliminó el último -1
            {-1, 1,-1, 2,-1, 1,-1, 1, 1, 1,-1, 1,-1, 1,-1, 1, 1, 1,-1, 1,-1, 2,-1, 1,-1, 1, 1, 1}, // Se eliminó el último -1
            {-1, 1,-1,-1,-1, 1,-1,-1,-1,-1,-1, 1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1, 1}, // Se eliminó el último -1
            {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,-1},
            {-1,-1,-1,-1,-1, 1,-1,-1,-1,-1,-1, 1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1,-1},
            { 0, 0, 0, 0,-1, 1,-1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1,-1, 1,-1, 0, 0, 0, 0, 0, 0, 0},
            {-1,-1,-1,-1,-1, 1,-1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1,-1, 1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1, 0, 0, 0, 0, 1, 0, 1,-1,-1, 1, 1, 4, 1, 1,-1,-1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0,-1},
            {-1,-1,-1,-1,-1, 1,-1, 1,-1,-1, 1, 1, 3, 1, 1,-1,-1, 1,-1, 1,-1,-1,-1,-1,-1,-1,-1,-1},
            { 0, 0, 0, 0,-1, 1,-1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1,-1, 1,-1, 0, 0, 0, 0, 0, 0, 0},
            {-1,-1,-1,-1,-1, 1,-1,-1,-1,-1,-1, 1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1,-1, 1,-1,-1,-1,-1},
            {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
    };

    static final int ROWS = mapa.length;
    static final int COLS = mapa[0].length;
    private static GrafoMatrizAdyacencia laberinto;
    private ArrayList<Integer> vertices;


    public Laberinto() {
        laberinto = new GrafoMatrizAdyacencia(ROWS, COLS);
        vertices = new ArrayList<>();
        inicializarMatriz();


    }


    public void inicializarMatriz() {
        if (mapa[0].length != COLS) {
            throw new RuntimeException("La matriz tiene filas con longitudes diferentes");
        }
        for (int i = 0; i < ROWS; i++) {
            if (mapa[i].length != COLS) {
                throw new RuntimeException("Fila " + i + " tiene longitud " + mapa[i].length + " en lugar de " + COLS);
            }
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                laberinto.inicializaMatriz(i, j, mapa[i][j]);

                if (mapa[i][j] == 1 || mapa[i][j] == 2) {
                    laberinto.agregarVertice(i, j);
                    int key = i * COLS + j;
                    vertices.add(key);
                }
            }
        }

        for (int key : vertices) {
            laberinto.agregarArista(key);
        }
    }

    public ArrayList<Sprite> obtenerSprites() {
        ArrayList<Sprite> sprites = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int cell = mapa[i][j];
                SpriteType type;
                switch (cell) {
                    case -1:
                        type = SpriteType.WALL;
                        break;
                    case 0:
                        type = SpriteType.FLOOR;
                        break;
                    case 1:
                        type = SpriteType.PELLET;
                        break;
                    case 2:
                        type = SpriteType.POWER_FOOD;
                        break;
                    case 3:
                        type = SpriteType.GHOST_AREA;
                        break;
                    case 4:
                        type = SpriteType.DOOR;
                        break;
                    default:
                        type = SpriteType.FLOOR;
                        break;
                }
                sprites.add(new Sprite(type, i, j));
            }
        }
        return sprites;
    }

    public int[][] getLaberinto() {
        return mapa;
    }
}

