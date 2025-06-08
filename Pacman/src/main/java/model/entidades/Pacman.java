package model.entidades;

import estructuras.Vertice;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Pacman {
    private final ImageView imageView;
    private Vertice posicion;
    private static int CELL_SIZE;

    public Pacman(Vertice posicion, int cell) {
        this.posicion = posicion;
        CELL_SIZE = cell;
        Image image = new Image("/pictures/pacmanRight.png");
        imageView = new ImageView(image);
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);
    }

    public void relocate() {
        imageView.setLayoutX(posicion.getX() * CELL_SIZE);
        imageView.setLayoutY(posicion.getY() * CELL_SIZE);
    }

    public ImageView getNode() {
        return imageView;
    }

    public void moveTo(Duration duration, Runnable onFinished, Movimiento direction) {
        if(!posicion.isNull(direction)) {
            TranslateTransition transition = new TranslateTransition(duration, imageView);
            int x = posicion.getX();
            int y = posicion.getY();

            if (direction == Movimiento.UP) {
                this.posicion = (Vertice) posicion.getUp().getDestino();
            } else if (direction == Movimiento.DOWN) {
                this.posicion = (Vertice) posicion.getDown().getDestino();
            } else if (direction == Movimiento.LEFT) {
                this.posicion = (Vertice) posicion.getLeft().getDestino();
            } else {
                this.posicion = (Vertice) posicion.getRight().getDestino();
            }
            transition.setToX((posicion.getX() - x) * CELL_SIZE);
            transition.setToY((posicion.getY() - y) * CELL_SIZE);

            transition.setOnFinished(e -> {
                imageView.setLayoutX(posicion.getX() * CELL_SIZE);
                imageView.setLayoutY(posicion.getY() * CELL_SIZE);
                imageView.setTranslateX(0);
                imageView.setTranslateY(0);
                if (onFinished != null) onFinished.run();
            });

            transition.play();
        }
    }
}

