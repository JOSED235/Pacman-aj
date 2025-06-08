package ui.entidades;

import estructuras.Vertice;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.entidades.Fantasma;
import model.entidades.Movimiento;
import model.juego.Laberinto;

public class FantasmaView {
    private final ImageView imageView;
    private final Fantasma fantasma;

    public FantasmaView(Fantasma fantasma) {
        this.fantasma = fantasma;
        this.imageView = new ImageView(new Image("/pictures/redGhost.png"));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
    }


    public void relocate() {
        imageView.setLayoutX(fantasma.getPosicion().getX() * Laberinto.CELL_SIZE);
        imageView.setLayoutY(fantasma.getPosicion().getY() * Laberinto.CELL_SIZE);
    }

    public ImageView getNode() {
        return imageView;
    }

    public void moverA(Duration duracion, Runnable onFinish, Movimiento direccion) {
        if (!fantasma.getPosicion().isNull(direccion)) {
            TranslateTransition transition = new TranslateTransition(duracion, imageView);
            Vertice actual = fantasma.getPosicion();
            int x = actual.getX();
            int y = actual.getY();

            // Actualizar posición lógica del fantasma
            switch (direccion) {
                case UP -> fantasma.setPosicion((Vertice) actual.getUp().getDestino());
                case DOWN -> fantasma.setPosicion((Vertice) actual.getDown().getDestino());
                case LEFT -> fantasma.setPosicion((Vertice) actual.getLeft().getDestino());
                case RIGHT -> fantasma.setPosicion((Vertice) actual.getRight().getDestino());
            }

            // Animación visual
            transition.setToX((fantasma.getPosicion().getX() - x) * Laberinto.CELL_SIZE);
            transition.setToY((fantasma.getPosicion().getY() - y) * Laberinto.CELL_SIZE);

            transition.setOnFinished(e -> {
                relocate(); // Reubica por si hay desfase
                imageView.setTranslateX(0);
                imageView.setTranslateY(0);
                if (onFinish != null) onFinish.run();
            });

            transition.play();
        }
    }
}