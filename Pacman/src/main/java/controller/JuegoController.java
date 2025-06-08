package controller;

import estructuras.Vertice;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import model.entidades.*;
import model.ia.FantasmaDijkstra;
import model.ia.FantasmaIA;
import model.juego.Laberinto;
import ui.entidades.*;
public class JuegoController {

    private final Laberinto laberinto;
    private Pacman pacman;
    private PacmanView pacmanView;
    private Fantasma fantasma;
    private FantasmaView fantasmaView;
    private FantasmaIA logicaFantasma;

    @FXML
    private GridPane gridPane;

    public JuegoController() {
        laberinto = new Laberinto();
    }

    @FXML
    public void initialize() {
        // Mostrar laberinto gráfico
        GridPane laberintoGraphics = laberinto.obtenerGrid();
        gridPane.getChildren().clear();
        gridPane.getChildren().add(laberintoGraphics);

        GridPane.setHgrow(laberintoGraphics, Priority.ALWAYS);
        GridPane.setVgrow(laberintoGraphics, Priority.ALWAYS);

        // Crear Pacman
        pacman = new Pacman(laberinto.getPacman());
        pacmanView = new PacmanView(pacman);
        gridPane.getChildren().add(pacmanView.getNode());

        // Crear Fantasma con lógica Dijkstra
        logicaFantasma = new FantasmaDijkstra();
        fantasma = new Fantasma(laberinto.getGhost1());
        fantasmaView = new FantasmaView(fantasma);
        gridPane.getChildren().add(fantasmaView.getNode());

        // Movimiento automático del fantasma
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            Movimiento movimiento = logicaFantasma.calcularMovimiento(fantasma.getPosicion(), pacman.getPosicion());
            if (movimiento != null) {
                fantasmaView.moverA(Duration.millis(300), null, movimiento);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> pacmanView.moverA(Duration.millis(150), null, Movimiento.UP);
            case DOWN -> pacmanView.moverA(Duration.millis(150), null, Movimiento.DOWN);
            case LEFT -> pacmanView.moverA(Duration.millis(150), null, Movimiento.LEFT);
            case RIGHT -> pacmanView.moverA(Duration.millis(150), null, Movimiento.RIGHT);
        }
    }
}
