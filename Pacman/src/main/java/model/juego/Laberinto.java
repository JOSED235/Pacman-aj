package model.juego;

import estructuras.Vertice;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.entidades.TypeOfConsumable;
import model.grafo.GrafoMatrizAdyacencia;

import java.util.ArrayList;

public class  Laberinto {
    public static final int CELL_SIZE = 20;
    private static final int[][] mapa = {
            {-19,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-28,-29,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-18},
            {-11,  1,  0,  0,  0,  0,  1,  0,  0,  0,  0,  0,  1,-12,-12,  1,  0,  0,  0,  0,  0,  1,  0,  0,  0,  0,  1,-11},
            {-11,  0,-29,-14,-14,-28,  0,-29,-14,-14,-14,-28,  0,-12,-12,  0,-29,-14,-14,-14,-28,  0,-29,-14,-14,-28,  0,-11},
            {-11,  0,-12,  5,  5,-12,  0,-12,  5,  5,  5,-12,  0,-12,-12,  0,-12,  5,  5,  5,-12,  0,-12,  5,  5,-12,  0,-11},
            {-11,  0,-27,-14,-14,-26,  0,-27,-14,-14,-14,-26,  0,-27,-26,  0,-27,-14,-14,-14,-26,  0,-27,-14,-14,-26,  0,-11},
            {-11,  1,  0,  0,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  1,  0,  0,  0,  0,  1,-11},
            {-11, 11,-29,-14,-14,-28,  0,-29,-28,  0,-29,-14,-14,-14,-14,-14,-14,-28,  0,-29,-28,  0,-29,-14,-14,-28, 11,-11},
            {-11,  0,-27,-14,-14,-26,  0,-12,-12,  0,-27,-14,-14,-28,-29,-14,-14,-26,  0,-12,-12,  0,-27,-14,-14,-26,  0,-11},
            {-11,  1,  0,  0,  0,  0,  1,-12,-12,  1,  0,  0,  1,-12,-12,  1,  0,  0,  1,-12,-12,  1,  0,  0,  0,  0,  1,-11},
            {-17,-13,-13,-13,-13,-18,  0,-12,-27,-14,-14,-28,  2,-12,-12,  2,-29,-14,-14,-26,-12,  0,-19,-13,-13,-13,-13,-16},
            { -5, -5, -5, -5, -5,-11,  0,-12,-29,-14,-14,-26,  2,-27,-26,  2,-27,-14,-14,-28,-12,  0,-11, -5, -5, -5, -5, -5},
            { -5, -5, -5, -5, -5,-11,  0,-12,-12,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,-12,-12,  0,-11, -5, -5, -5, -5, -5},
            { -5, -5, -5, -5, -5,-11,  0,-12,-12,  2,-39,-14,-38,-21,-21,-39,-14,-38,  2,-12,-12,  0,-11, -5, -5, -5, -5, -5},
            {-13,-13,-13,-13,-13,-16,  0,-27,-26,  2,-12,-39,-36,-22,-22,-37,-38,-12,  2,-27,-26,  0,-17,-13,-13,-13,-13,-13},
            {  4,  0,  0,  0,  0,  0,  1,  2,  2,  2,-12,-12,  9,  8,  7,  6,-12,-12,  2,  2,  2,  1,  0,  0,  0,  0,  0,  5},
            {-13,-13,-13,-13,-13,-18,  0,-29,-28,  2,-12,-37,-14,-14,-14,-14,-36,-12,  2,-29,-28,  0,-19,-13,-13,-13,-13,-13},
            { -5, -5, -5, -5, -5,-11,  0,-12,-12,  2,-37,-14,-14,-14,-14,-14,-14,-36,  2,-12,-12,  0,-11, -5, -5, -5, -5, -5},
            { -5, -5, -5, -5, -5,-11,  0,-12,-12,  2,  2,  2,  2,  3,  2,  2,  2,  2,  2,-12,-12,  0,-11, -5, -5, -5, -5, -5},
            { -5, -5, -5, -5, -5,-11,  0,-12,-12,  2,-29,-14,-14,-14,-14,-14,-14,-28,  2,-12,-12,  0,-11, -5, -5, -5, -5, -5},
            {-19,-13,-13,-13,-13,-16,  0,-27,-26,  2,-27,-14,-14,-28,-29,-14,-14,-26,  2,-27,-26,  0,-17,-13,-13,-13,-13,-18},
            {-11,  1,  0,  0,  0,  0,  1,  0,  0,  1,  0,  0,  1,-12,-12,  1,  0,  0,  1,  0,  0,  1,  0,  0,  0,  0,  1,-11},
            {-11, 11,-29,-14,-14,-28,  0,-29,-14,-14,-14,-28,  0,-12,-12,  0,-29,-14,-14,-14,-28,  0,-29,-14,-14,-28, 11,-11},
            {-11,  0,-27,-14,-28,-12,  0,-27,-14,-14,-14,-26,  0,-27,-26,  0,-27,-14,-14,-14,-26,  0,-12,-29,-14,-26,  0,-11},
            {-11,  1,  0,  1,-12,-12,  1,  0,  0,  1,  0,  0,  1,  0, 10,  1,  0,  0,  1,  0,  0,  1,-12,-12,  1,  0,  1,-11},
            {-27,-14,-28,  0,-12,-12,  0,-29,-28,  0,-29,-14,-14,-14,-14,-14,-14,-28,  0,-29,-28,  0,-12,-12,  0,-29,-14,-16},
            {-29,-14,-26,  0,-27,-26,  0,-12,-12,  0,-27,-14,-14,-28,-29,-14,-14,-26,  0,-12,-12,  0,-27,-26,  0,-27,-14,-18},
            {-11,  1,  0,  1,  0,  0,  1,-12,-12,  1,  0,  0,  1,-12,-12,  1,  0,  0,  1,-12,-12,  1,  0,  0,  1,  0,  1,-11},
            {-11,  0,-29,-14,-14,-14,-14,-26,-27,-14,-14,-28,  0,-12,-12,  0,-29,-14,-14,-26,-27,-14,-14,-14,-14,-28,  0,-11},
            {-11,  0,-27,-14,-14,-14,-14,-14,-14,-14,-14,-26,  0,-27,-26,  0,-27,-14,-14,-14,-14,-14,-14,-14,-14,-26,  0,-11},
            {-11,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,-11},
            {-17,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-13,-16}
    };

    static final int ROWS = mapa.length;
    static final int COLS = mapa[0].length;
    private static GrafoMatrizAdyacencia laberinto;
    private ArrayList<Integer> vertices;
    private final Vertice ghost1 = new Vertice(14 * COLS + 12,14,12,null);
    private final Vertice ghost2 = new Vertice(14 * COLS + 13,14,13,null);
    private final Vertice ghost3 = new Vertice(14 * COLS + 14,14,14,null);
    private final Vertice ghost4 = new Vertice(14 * COLS + 15,14,15,null);
    private final Vertice Pacman = new Vertice(23 * COLS + 14,23,14,null);
    private int keyObjetivo = 0;
    private int keyOrigen = 0;
    private GridPane consumibles;


    public Laberinto() {
        laberinto = new GrafoMatrizAdyacencia(ROWS, COLS);
        vertices = new ArrayList<>();
        consumibles = new GridPane();
        inicializarMatriz();
    }

    public GridPane getConsumibles(){return consumibles;}

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

                if (mapa[i][j] == 1) {
                    laberinto.agregarVertice(i, j, TypeOfConsumable.SCORE);
                    int key = i * COLS + j;
                    vertices.add(key);
                } else if (mapa[i][j] == 0) {
                    laberinto.agregarVertice(i, j, TypeOfConsumable.SCORE);
                    int key = i * COLS + j;
                    vertices.add(key);
                }else if (mapa[i][j] == 2){
                    laberinto.agregarVertice(i, j, null);
                    int key = i * COLS + j;
                    vertices.add(key);
                }else if (mapa[i][j] == 3){
                    laberinto.agregarVertice(i, j, TypeOfConsumable.TROPHY);
                    int key = i * COLS + j;
                    vertices.add(key);
                }else if (mapa[i][j] == 11){
                    laberinto.agregarVertice(i, j, TypeOfConsumable.PILL);
                    int key = i * COLS + j;
                    vertices.add(key);
                }else if(mapa[i][j] == 9){
                    laberinto.posicionVertice(ghost1);
                    vertices.add(ghost1.getDato());
                } else if (mapa[i][j] == 8) {
                    laberinto.posicionVertice(ghost2);
                    vertices.add(ghost2.getDato());
                }else if (mapa[i][j] == 7) {
                    laberinto.posicionVertice(ghost3);
                    vertices.add(ghost3.getDato());
                } else if (mapa[i][j] == 6) {
                    laberinto.posicionVertice(ghost4);
                    vertices.add(ghost4.getDato());
                } else if (mapa[i][j] == 10) {
                    laberinto.posicionVertice(Pacman);
                    vertices.add(Pacman.getDato());
                }else if (mapa[i][j] == 5) {
                    laberinto.agregarVertice(i, j, TypeOfConsumable.SCORE);
                    int key = i * COLS + j;
                    vertices.add(key);
                    keyOrigen = key;
                }else if (mapa[i][j] == 4) {
                    laberinto.agregarVertice(i, j, TypeOfConsumable.SCORE);
                    int key = i * COLS + j;
                    vertices.add(key);
                    keyObjetivo = key;
                }
            }
        }
        laberinto.crearPuente(keyOrigen,keyObjetivo);
        consumibles = laberinto.getConsumibles();

        for (int key : vertices) {
            laberinto.agregarArista(key);
        }
    }
    public ArrayList<Sprite> obtenerSprites() {
        ArrayList<Sprite> sprites = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int cell = mapa[i][j];
                sprites.add(new Sprite(cell, i, j));
            }
        }
        return sprites;
    }

    public GridPane obtenerGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        Image corner1 = new Image(getClass().getResource("/walks/corner1.png").toExternalForm());
        Image corner2 = new Image(getClass().getResource("/walks/corner2.png").toExternalForm());
        Image corner3 = new Image(getClass().getResource("/walks/corner3.png").toExternalForm());
        Image corner4 = new Image(getClass().getResource("/walks/corner4.png").toExternalForm());
        Image cornerDownLeft = new Image(getClass().getResource("/walks/cornerDownLeft.png").toExternalForm());
        Image cornerDownLeftDoble2 = new Image(getClass().getResource("/walks/cornerDownLeftDoble2.png").toExternalForm());
        Image cornerDownRigth = new Image(getClass().getResource("/walks/cornerDownRigth.png").toExternalForm());
        Image cornerDownRigthDoble2 = new Image(getClass().getResource("/walks/cornerDownRigthDoble2.png").toExternalForm());
        Image cornerUpLeft = new Image(getClass().getResource("/walks/cornerUpLeft.png").toExternalForm());
        Image cornerUpLeftDoble2 = new Image(getClass().getResource("/walks/cornerUpLeftDoble2.png").toExternalForm());
        Image cornerUpRigth = new Image(getClass().getResource("/walks/cornerUpRigth.png").toExternalForm());
        Image cornerUpRigthDoble2 = new Image(getClass().getResource("/walks/cornerUpRigthDoble2.png").toExternalForm());
        Image doorDown = new Image(getClass().getResource("/walks/doorDown.png").toExternalForm());
        Image doorUp = new Image(getClass().getResource("/walks/doorUp.png").toExternalForm());

        Image horizontal = new Image(getClass().getResource("/walks/horizontal.png").toExternalForm());
        Image horizontalDoble = new Image(getClass().getResource("/walks/horizontalDoble.png").toExternalForm());
        Image vertical = new Image(getClass().getResource("/walks/vertical.png").toExternalForm());
        Image verticalDoble = new Image(getClass().getResource("/walks/verticalDoble.png").toExternalForm());

        Image floor = new Image(getClass().getResource("/pictures/floor.png").toExternalForm());
        Image wall = new Image(getClass().getResource("/pictures/wall.png").toExternalForm());

        ArrayList<Sprite> sprites = obtenerSprites();

        for (Sprite sprite : sprites) {
            ImageView view1 = new ImageView(floor);
            view1.setFitWidth(Laberinto.CELL_SIZE);
            view1.setFitHeight(Laberinto.CELL_SIZE);
            gridPane.add(view1, sprite.getCol(), sprite.getRow());

            ImageView view = new ImageView();
            view.setFitWidth(Laberinto.CELL_SIZE);
            view.setFitHeight(Laberinto.CELL_SIZE);
            int cell = sprite.getValor();
            switch (cell) {
                case -1:
                    view.setImage(wall);
                    break;
                case 0:
                    break;
                case 1:
                    break;
                case -11:
                    view.setImage(verticalDoble);
                    break;
                case -12:
                    view.setImage(vertical);
                    break;
                case -13:
                    view.setImage(horizontalDoble);
                    break;
                case -14:
                    view.setImage(horizontal);
                    break;
                case -19:
                    view.setImage(cornerUpLeftDoble2);
                    break;
                case -18:
                    view.setImage(cornerUpRigthDoble2);
                    break;
                case -17:
                    view.setImage(cornerDownLeftDoble2);
                    break;
                case -16:
                    view.setImage(cornerDownRigthDoble2);
                    break;
                case -29:
                    view.setImage(cornerUpLeft);
                    break;
                case -28:
                    view.setImage(cornerUpRigth);
                    break;
                case -27:
                    view.setImage(cornerDownLeft);
                    break;
                case -26:
                    view.setImage(cornerDownRigth);
                    break;
                case -39:
                    view.setImage(corner1);
                    break;
                case -38:
                    view.setImage(corner2);
                    break;
                case -37:
                    view.setImage(corner3);
                    break;
                case -36:
                    view.setImage(corner4);
                    break;
                case -21:
                    view.setImage(doorDown);
                    break;
                case -22:
                    view.setImage(doorUp);
                    break;
            }
            gridPane.add(view, sprite.getCol(), sprite.getRow());
        }
        return gridPane;
    }

    public int[][] getLaberinto() {
        return mapa;
    }

    public Vertice getGhost1(){ return ghost1; }
    public Vertice getGhost2(){ return ghost2; }
    public Vertice getGhost3(){ return ghost3; }
    public Vertice getGhost4(){ return ghost4; }
    public Vertice getPacman(){ return ghost1; }
}

