package ui.entidades;

import estructuras.Vertice;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import model.entidades.Fantasma;
import model.entidades.Movimiento;
import model.juego.Laberinto;

/** Vista de un fantasma. Todos sus métodos se llaman desde el hilo de JavaFX. */
public class FantasmaView {

    private final ImageView imageView;
    private final Fantasma fantasma;
    private final Image normal;
    private TranslateTransition transicion;

    private static final int CELL_SIZE = Laberinto.CELL_SIZE;
    private static final Image ASUSTADO = new Image("/pictures/scaredGhost.png");

    /** @param tipo 1 rosado, 2 azul, 3 naranja, cualquier otro rojo */
    public FantasmaView(Fantasma fantasma, int tipo) {
        this.fantasma = fantasma;

        String ruta = switch (tipo) {
            case 1 -> "/pictures/pinkGhost.png";
            case 2 -> "/pictures/blueGhost.png";
            case 3 -> "/pictures/orangeGhost.png";
            default -> "/pictures/redGhost.png";
        };

        this.normal = new Image(ruta);
        this.imageView = new ImageView(normal);
        imageView.setFitWidth(CELL_SIZE);
        imageView.setFitHeight(CELL_SIZE);
        relocate();
    }

    public ImageView getNode() {
        return imageView;
    }

    /** Detiene la animación y coloca la imagen exactamente en la casilla del modelo. */
    public void relocate() {
        if (transicion != null) transicion.stop();
        imageView.setLayoutX(fantasma.getPosicion().getY() * CELL_SIZE);
        imageView.setLayoutY(fantasma.getPosicion().getX() * CELL_SIZE);
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
    }

    /** Cambia entre el sprite normal y el azul de fantasma asustado. */
    public void setAsustado(boolean asustado) {
        imageView.setImage(asustado ? ASUSTADO : normal);
        imageView.setOpacity(1);
    }

    /** Parpadeo cuando el efecto de la pastilla está por terminar. */
    public void parpadear(boolean visible) {
        imageView.setOpacity(visible ? 1 : 0.35);
    }

    public void moverA(Duration duracion, Runnable onFinish, Movimiento direccion) {
        if (!fantasma.puedeMover(direccion)) return;

        Vertice origen = fantasma.getPosicion();
        fantasma.mover(direccion);
        Vertice destino = fantasma.getPosicion();

        if (transicion != null) transicion.stop();
        imageView.setLayoutX(origen.getY() * CELL_SIZE);
        imageView.setLayoutY(origen.getX() * CELL_SIZE);
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);

        int dCol = destino.getY() - origen.getY();
        int dFila = destino.getX() - origen.getX();
        if (Math.abs(dCol) > 1 || Math.abs(dFila) > 1) {
            // Túnel: aparece al otro lado sin cruzar el tablero
            relocate();
            if (onFinish != null) onFinish.run();
            return;
        }

        transicion = new TranslateTransition(duracion, imageView);
        transicion.setToX(dCol * CELL_SIZE);
        transicion.setToY(dFila * CELL_SIZE);
        transicion.setOnFinished(e -> {
            relocate();
            if (onFinish != null) onFinish.run();
        });
        transicion.play();
    }
}
