package ui.entidades;

import estructuras.Vertice;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.entidades.Movimiento;
import model.entidades.Pacman;

public class PacmanView {

    private final Pacman pacman;
    private final ImageView imageView;
    private static final int CELL_SIZE = 20;

    private final Image imgUp;
    private final Image imgDown;
    private final Image imgLeft;
    private final Image imgRight;

    public PacmanView(Pacman pacman) {
        this.pacman = pacman;

        // Cargar imágenes de dirección
        imgUp = new Image("/pictures/pacmanUp.png");
        imgDown = new Image("/pictures/pacmanDown.png");
        imgLeft = new Image("/pictures/pacmanLeft.png");
        imgRight = new Image("/pictures/pacmanRight.png");

        // Imagen inicial (mirando a la derecha)
        imageView = new ImageView(imgRight);
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);

        updatePosition();
    }

    public void moverA(Duration duracion, Runnable alFinalizar, Movimiento direccion) {
        if (!pacman.puedeMover(direccion)) return;

        // Obtener posiciones antes y después del movimiento lógico
        Vertice origen = pacman.getPosicion();
        pacman.mover(direccion);
        Vertice destino = pacman.getPosicion();

        // Cambiar sprite visual según la dirección
        switch (direccion) {
            case UP -> imageView.setImage(imgUp);
            case DOWN -> imageView.setImage(imgDown);
            case LEFT -> imageView.setImage(imgLeft);
            case RIGHT -> imageView.setImage(imgRight);
        }

        TranslateTransition transition = new TranslateTransition(duracion, imageView);
        transition.setToX((destino.getX() - origen.getX()) * CELL_SIZE);
        transition.setToY((destino.getY() - origen.getY()) * CELL_SIZE);

        transition.setOnFinished(e -> {
            updatePosition();
            if (alFinalizar != null) alFinalizar.run();
        });

        transition.play();
    }

    private void updatePosition() {
        imageView.setLayoutX(pacman.getPosicion().getX() * CELL_SIZE);
        imageView.setLayoutY(pacman.getPosicion().getY() * CELL_SIZE);
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
    }

    public ImageView getNode() {
        return imageView;
    }
}