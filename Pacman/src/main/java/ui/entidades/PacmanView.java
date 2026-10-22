package ui.entidades;

import estructuras.Vertice;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.entidades.Movimiento;
import model.entidades.Pacman;
import model.juego.Laberinto;

/** Vista de Pac-Man. Todos sus métodos se llaman desde el hilo de JavaFX. */
public class PacmanView {

    private final Pacman pacman;
    private final ImageView imageView;
    private static final int CELL_SIZE = Laberinto.CELL_SIZE;

    private final Image imgUp;
    private final Image imgDown;
    private final Image imgLeft;
    private final Image imgRight;
    private TranslateTransition transicion;

    public PacmanView(Pacman pacman) {
        this.pacman = pacman;
        imgUp = new Image("/pictures/pacmanUp.png");
        imgDown = new Image("/pictures/pacmanDown.png");
        imgLeft = new Image("/pictures/pacmanLeft.png");
        imgRight = new Image("/pictures/pacmanRight.png");

        imageView = new ImageView(imgRight);
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);

        updatePosition();
    }

    /**
     * Mueve a Pac-Man una casilla en el modelo y anima el desplazamiento.
     * @return false si en esa dirección hay pared
     */
    public boolean moverA(Duration duracion, Runnable alFinalizar, Movimiento direccion) {
        if (!pacman.puedeMover(direccion)) return false;

        Vertice origen = pacman.getPosicion();
        pacman.mover(direccion);
        Vertice destino = pacman.getPosicion();
        mirarHacia(direccion);

        if (transicion != null) transicion.stop();
        // Coordenadas del vértice: x = fila, y = columna. En pantalla X es la columna y Y la fila.
        imageView.setLayoutX(origen.getY() * CELL_SIZE);
        imageView.setLayoutY(origen.getX() * CELL_SIZE);
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);

        int dCol = destino.getY() - origen.getY();
        int dFila = destino.getX() - origen.getX();
        if (Math.abs(dCol) > 1 || Math.abs(dFila) > 1) {
            // Túnel: aparece directamente al otro lado en vez de cruzar todo el tablero
            updatePosition();
            if (alFinalizar != null) alFinalizar.run();
            return true;
        }

        transicion = new TranslateTransition(duracion, imageView);
        transicion.setToX(dCol * CELL_SIZE);
        transicion.setToY(dFila * CELL_SIZE);
        transicion.setOnFinished(e -> {
            updatePosition();
            if (alFinalizar != null) alFinalizar.run();
        });
        transicion.play();
        return true;
    }

    public void mirarHacia(Movimiento direccion) {
        switch (direccion) {
            case UP -> imageView.setImage(imgUp);
            case DOWN -> imageView.setImage(imgDown);
            case LEFT -> imageView.setImage(imgLeft);
            case RIGHT -> imageView.setImage(imgRight);
        }
    }

    /** Detiene la animación y coloca la imagen exactamente en la casilla del modelo. */
    public void updatePosition() {
        if (transicion != null) transicion.stop();
        imageView.setLayoutX(pacman.getPosicion().getY() * CELL_SIZE);
        imageView.setLayoutY(pacman.getPosicion().getX() * CELL_SIZE);
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
    }

    public ImageView getNode() {
        return imageView;
    }
}
