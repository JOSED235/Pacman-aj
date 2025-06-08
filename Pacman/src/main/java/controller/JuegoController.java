package controller;

import estructuras.Vertice;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import model.entidades.*;
import model.juego.Laberinto;

public class JuegoController {

    private final Laberinto laberinto;
    private Vertice positionPacman;
    private Pacman pacman;

    @FXML
    private GridPane gridPane;

    public JuegoController() {
        laberinto = new Laberinto();
        positionPacman = null;
        pacman = null;
    }

    @FXML
    public void initialize() {
        GridPane laberintoGraphics = laberinto.obtenerGrid();

        gridPane.getChildren().clear();
        gridPane.getChildren().add(laberintoGraphics);

        GridPane.setHgrow(laberintoGraphics, Priority.ALWAYS);
        GridPane.setVgrow(laberintoGraphics, Priority.ALWAYS);

        pacman = new Pacman(laberinto.getPacman(), Laberinto.CELL_SIZE);
        positionPacman = laberinto.getPacman();
        gridPane.getChildren().add(pacman.getNode());
    }


    @FXML
    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> {
                pacman.moveTo(Duration.millis(150), null, Movimiento.UP);
            }
            case DOWN -> {
                pacman.moveTo(Duration.millis(150), null, Movimiento.DOWN);
            }
            case LEFT -> {
                pacman.moveTo(Duration.millis(150), null, Movimiento.LEFT);
            }
            case RIGHT -> {
                pacman.moveTo(Duration.millis(150), null, Movimiento.RIGHT);
            }
        }
    }
}