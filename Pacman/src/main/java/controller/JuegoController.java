package controller;

import estructuras.Vertice;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.entidades.*;
import model.ia.FantasmaDijkstra;
import model.ia.FantasmaIA;
import model.juego.Laberinto;
import ui.entidades.*;

import static javafx.scene.input.KeyCode.*;

public class JuegoController {

    private final Laberinto laberinto;
    private Pacman pacman;
    private PacmanView pacmanView;
    private Fantasma fantasma;
    private FantasmaView fantasmaView;
    private FantasmaIA logicaFantasma;
    private Vertice pacmanPosicion;
    private final Pane capaEntidades = new Pane();
    private Thread up;
    private Thread down;
    private Thread left;
    private Thread rigth;
    private KeyCode ultimaAccion;
    private boolean exit = true;
    private int globalScore;

    @FXML
    private StackPane root;

    public JuegoController() {
        laberinto = new Laberinto();
    }

    @FXML
    public void initialize() {
        pacman = new Pacman(laberinto.getPacman());
        pacmanView = new PacmanView(pacman);
        pacmanPosicion = laberinto.getPacman();

        fantasma = new Fantasma(laberinto.getGhost1());
        fantasmaView = new FantasmaView(fantasma);


        GridPane consumibles = laberinto.getConsumibles();

        capaEntidades.getChildren().addAll(
                pacmanView.getNode(),
                fantasmaView.getNode(),
                consumibles
        );
        root.getChildren().addAll(laberinto.obtenerGrid(), capaEntidades);

        root.setFocusTraversable(true);
        root.requestFocus();
        root.setOnKeyPressed(this::onKeyPressed);
        root.setOnMouseClicked(e -> root.requestFocus());
        System.out.println("Focus owner: " + root.isFocused());
    }

    @FXML
    public void onKeyPressed(KeyEvent event) {
        System.out.println("Presionada: " + event.getCode());


        exit = true;
        ultimaAccion = event.getCode();
        switch (event.getCode()) {
            case UP -> {
                up = new Thread( () ->{
                    while (exit){
                        if(pacmanPosicion.getUp() != null){
                            pacmanPosicion = (Vertice) pacmanPosicion.getUp().getDestino();
                            pacmanView.moverA(Duration.millis(250), null, Movimiento.UP);
                            Consumible com = pacmanPosicion.getConsumuble();
                            if(com != null && com.getEstado()){
                                globalScore = globalScore + com.getScore();
                            }
                        }else{
                            exit = false;
                        }
                    }
                });
                up.start();
            }
            case DOWN -> {
                down = new Thread( () ->{
                    while (exit){
                        if(pacmanPosicion.getDown() != null){
                            pacmanPosicion = (Vertice) pacmanPosicion.getDown().getDestino();
                            pacmanView.moverA(Duration.millis(250), null, Movimiento.DOWN);
                            Consumible com = pacmanPosicion.getConsumuble();
                            if(com != null && com.getEstado()){
                                globalScore = globalScore + com.getScore();
                            }
                        }else{
                            exit = false;
                        }
                    }
                });
                down.start();
            }
            case LEFT -> {
                left = new Thread( () ->{
                    while (exit){
                        if(pacmanPosicion.getLeft() != null){
                            pacmanPosicion = (Vertice) pacmanPosicion.getLeft().getDestino();
                            pacmanView.moverA(Duration.millis(250), null, Movimiento.LEFT);
                            Consumible com = pacmanPosicion.getConsumuble();
                            if(com != null && com.getEstado()){
                                globalScore = globalScore + com.getScore();
                            }
                        }else{
                            exit = false;
                        }
                    }
                });
                left.start();
            }
            case RIGHT -> {
                rigth = new Thread( () ->{
                    while (exit){
                        if(pacmanPosicion.getRight() != null){
                            pacmanPosicion = (Vertice) pacmanPosicion.getRight().getDestino();
                            pacmanView.moverA(Duration.millis(250), null, Movimiento.RIGHT);
                            Consumible com = pacmanPosicion.getConsumuble();
                            if(com != null && com.getEstado()){
                                globalScore = globalScore + com.getScore();
                            }
                        }else{
                            exit = false;
                        }
                    }
                });
                rigth.start();
            }
        }
    }

    public void movimientoFantasma() {
        logicaFantasma = new FantasmaDijkstra();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            Movimiento movimiento = logicaFantasma.calcularMovimiento(fantasma.getPosicion(), pacman.getPosicion());
            if (movimiento != null) {
                fantasmaView.moverA(Duration.millis(300), null, movimiento);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
