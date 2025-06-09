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

    private static final int CELL_SIZE = Laberinto.CELL_SIZE;

    // Sprites de fantasmas
    private static final Image RED = new Image("/pictures/redGhost.png");
    private static final Image PINK = new Image("/pictures/pinkGhost.png");
    private static final Image ORANGE = new Image("/pictures/orangeGhost.png");
    private static final Image BLUE = new Image("/pictures/blueGhost.png");

    public FantasmaView(Fantasma fantasma, int tipo) {
        this.fantasma = fantasma;

        String ruta = switch (tipo) {
            case 1 -> "/pictures/pinkGhost.png";
            case 2 -> "/pictures/blueGhost.png";
            case 3 -> "/pictures/orangeGhost.png";
            default -> "/pictures/redGhost.png";
        };

        this.imageView = new ImageView(new Image(ruta));
        imageView.setFitWidth(Laberinto.CELL_SIZE);
        imageView.setFitHeight(Laberinto.CELL_SIZE);
        relocate(); // Posiciona visualmente
    }


    public ImageView getNode() {
        return imageView;
    }

    public void relocate() {
        imageView.setLayoutX(fantasma.getPosicion().getY() * CELL_SIZE); // Y = columna = X
        imageView.setLayoutY(fantasma.getPosicion().getX() * CELL_SIZE); // X = fila = Y
        imageView.setTranslateX(0);
        imageView.setTranslateY(0);
    }

    public void moverA(Duration duracion, Runnable onFinish, Movimiento direccion) {
        if (!fantasma.getPosicion().isNull(direccion)) {
            Vertice origen = fantasma.getPosicion();
            Vertice destino = switch (direccion) {
                case UP -> (Vertice) origen.getUp().getDestino();
                case DOWN -> (Vertice) origen.getDown().getDestino();
                case LEFT -> (Vertice) origen.getLeft().getDestino();
                case RIGHT -> (Vertice) origen.getRight().getDestino();
            };

            // Animación visual
            TranslateTransition transicion = new TranslateTransition(duracion, imageView);
            transicion.setToX((destino.getY() - origen.getY()) * CELL_SIZE);
            transicion.setToY((destino.getX() - origen.getX()) * CELL_SIZE);

            // Al finalizar, actualiza posición lógica y vista
            transicion.setOnFinished(e -> {
                fantasma.setPosicion(destino); // mover lógica aquí (no antes)
                relocate(); // sincroniza posición
                if (onFinish != null) onFinish.run();
            });

            transicion.play();
        }
    }

}
