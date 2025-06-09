package model.juego;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Puntuacion {
    private Image uno;
    private Image dos;
    private Image tres;
    private Image cuatro;
    private Image cinco;
    private Image seis;
    private Image siete;
    private Image ocho;
    private Image nueve;
    private Image cero;
    private ImageView[] scores;
    public static int globalScore = 0;
    private static final int CELL_SIZE = 20;
    private final GridPane puntuacion;

    public Puntuacion() {
        scores = new ImageView[6];
        uno = new Image("/numeros/uno.png");
        dos = new Image("/numeros/dos.png");
        tres = new Image("/numeros/tres.png");
        cuatro = new Image("/numeros/cuatro.png");
        cinco = new Image("/numeros/cinco.png");
        seis = new Image("/numeros/seis.png");
        siete = new Image("/numeros/siete.png");
        ocho = new Image("/numeros/ocho.png");
        nueve = new Image("/numeros/nueve.png");
        cero = new Image("/numeros/cero.png");
        ImageView primero = new ImageView(cero);
        ImageView segundo = new ImageView(cero);
        ImageView tercero = new ImageView(cero);
        ImageView cuarto = new ImageView(cero);
        ImageView quinto = new ImageView(cero);
        ImageView sexto = new ImageView(cero);

        primero.setFitWidth(CELL_SIZE);primero.setFitHeight(CELL_SIZE);
        segundo.setFitWidth(CELL_SIZE);segundo.setFitHeight(CELL_SIZE);
        tercero.setFitWidth(CELL_SIZE);tercero.setFitHeight(CELL_SIZE);
        cuarto.setFitWidth(CELL_SIZE);cuarto.setFitHeight(CELL_SIZE);
        quinto.setFitWidth(CELL_SIZE);quinto.setFitHeight(CELL_SIZE);
        sexto.setFitWidth(CELL_SIZE);sexto.setFitHeight(CELL_SIZE);

        scores[0] = primero;scores[1] = segundo;scores[2] = tercero;
        scores[3] = cuarto;scores[4] = quinto;scores[5] = sexto;
        globalScore = 0;
        puntuacion = new GridPane();
    }

    public void primeroGrid(int x, int y) {puntuacion.add(scores[0], x, y);}
    public void segundoGrid(int x, int y) {puntuacion.add(scores[1], x, y);}
    public void terceroGrid(int x, int y) {puntuacion.add(scores[2], x, y);}
    public void cuartoGrid(int x, int y) {puntuacion.add(scores[3], x, y);}
    public void quintoGrid(int x, int y) {puntuacion.add(scores[4], x, y);}
    public void sextoGrid(int x, int y) {puntuacion.add(scores[5], x, y);}

    public void setGlobalScore(int sum) {
        globalScore = globalScore + sum;
        String puntos = String.valueOf(globalScore);
        int j = 6;
        for (int i = 0; i < puntos.length(); i++) {
            char temp = puntos.charAt(i);
            j--;
            if(temp == 0){
                scores[j].setImage(cero);
            } else if (temp == 1) {
                scores[j].setImage(uno);
            } else if (temp == 2) {
                scores[j].setImage(dos);
            } else if (temp == 3) {
                scores[j].setImage(tres);
            } else if (temp == 4) {
                scores[j].setImage(cuatro);
            } else if (temp == 5) {
                scores[j].setImage(cinco);
            } else if (temp == 6) {
                scores[j].setImage(seis);
            } else if (temp == 7) {
                scores[j].setImage(siete);
            } else if (temp == 8) {
                scores[j].setImage(ocho);
            } else if (temp == 9) {
                scores[j].setImage(nueve);
            }
        }
    }

    public GridPane getGridPanePuntuacion() {return puntuacion;}
}
