package controller;

import estructuras.Vertice;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.entidades.*;
import model.ia.*;
import model.juego.Laberinto;
import ui.entidades.*;

public class JuegoController {


    private final Laberinto laberinto;
    private Pacman pacman;
    private PacmanView pacmanView;
    private Fantasma ghost1, ghost2, ghost3, ghost4;
    private FantasmaView view1, view2, view3, view4;
    private final Pane capaEntidades = new Pane();
    private Vertice pacmanPosicion;
    private Thread up, down, left, right;
    private boolean exit = true;
    private int globalScore = 0;
    private KeyCode ultimaAccion;

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

        ghost1 = new Fantasma(laberinto.getGhost1(), new FantasmaDijkstra());
        ghost2 = new Fantasma(laberinto.getGhost2(), new FantasmaAleatorio());
        ghost3 = new Fantasma(laberinto.getGhost3(), new FantasmaAleatorio());
        ghost4 = new Fantasma(laberinto.getGhost4(), new FantasmaAleatorio());

        view1 = new FantasmaView(ghost1, 1);
        view2 = new FantasmaView(ghost2, 2);
        view3 = new FantasmaView(ghost3, 3);
        view4 = new FantasmaView(ghost4, 4);

        GridPane consumibles = laberinto.getConsumibles();

        capaEntidades.getChildren().addAll(
                pacmanView.getNode(),
                view1.getNode(),
                view2.getNode(),
                view3.getNode(),
                view4.getNode(),
                consumibles
        );

        root.getChildren().addAll(laberinto.obtenerGrid(), capaEntidades);

        root.setFocusTraversable(true);
        root.requestFocus();
        root.setOnKeyPressed(this::onKeyPressed);
        root.setOnMouseClicked(e -> root.requestFocus());
        System.out.println("Focus owner: " + root.isFocused());



        // Iniciar fantasmas después de 5s
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                Platform.runLater(this::iniciarHilosFantasmas);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
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
                right = new Thread( () ->{
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
                right.start();
            }
        }
    }

    @FXML
    private void iniciarHilosFantasmas() {
        int tiempoMovimiento = 500;
        new FantasmaThread(ghost1, view1, ghost1.getLogica(), pacman, tiempoMovimiento).start();
        new FantasmaThread(ghost2, view2, ghost2.getLogica(), pacman, tiempoMovimiento).start();
        new FantasmaThread(ghost3, view3, ghost3.getLogica(), pacman, tiempoMovimiento).start();
        new FantasmaThread(ghost4, view4, ghost4.getLogica(), pacman, tiempoMovimiento).start();
    }
}
