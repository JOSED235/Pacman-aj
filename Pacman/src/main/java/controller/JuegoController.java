package controller;
import model.juego.*;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class JuegoController {
    private Laberinto laberinto;
    private int[][] mapa;
    @FXML
    private GridPane gridPane;

    public JuegoController(){
        laberinto = new Laberinto();
        mapa = laberinto.getLaberinto();
        laberinto.inicializarMatriz();
    }

    public void setupKeyListeners(Scene scene){
        scene.setOnKeyPressed(this::actions);
    }

    @FXML
    public void initialize() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                Rectangle cell = new Rectangle(40, 40);
                cell.setFill(mapa[i][j] == -1 ? Color.BLACK : Color.LIGHTGREEN);
                cell.setStroke(Color.GRAY);
                gridPane.add(cell, j, i);
            }
        }
    }

    public void actions(KeyEvent event){

    }
}
