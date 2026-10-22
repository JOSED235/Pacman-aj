package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/** Barra superior del juego: puntaje, récord, nivel y vidas restantes. */
public class HudView {
    private static final Image VIDA = new Image("/pictures/pacmanLeft.png");

    private final HBox raiz = new HBox(18);
    private final Label puntos = valor("0");
    private final Label mejor = valor("0");
    private final Label nivel = valor("1");
    private final HBox vidas = new HBox(4);

    public HudView() {
        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        vidas.setAlignment(Pos.CENTER_RIGHT);
        raiz.getStyleClass().add("hud");
        raiz.setAlignment(Pos.CENTER_LEFT);
        raiz.getChildren().addAll(bloque("PUNTAJE", puntos), bloque("RÉCORD", mejor), bloque("NIVEL", nivel), espacio, vidas);
    }

    private static Label valor(String texto) {
        Label l = new Label(texto);
        l.getStyleClass().add("hud-valor");
        return l;
    }

    private static VBox bloque(String titulo, Label valor) {
        Label t = new Label(titulo);
        t.getStyleClass().add("hud-titulo");
        return new VBox(0, t, valor);
    }

    public void actualizar(int puntaje, int record, int numeroNivel, int vidasRestantes) {
        puntos.setText(String.valueOf(puntaje));
        mejor.setText(String.valueOf(record));
        nivel.setText(String.valueOf(numeroNivel));
        vidas.getChildren().clear();
        for (int i = 0; i < vidasRestantes; i++) {
            ImageView icono = new ImageView(VIDA);
            icono.setFitWidth(18);
            icono.setFitHeight(18);
            vidas.getChildren().add(icono);
        }
    }

    public HBox getNode() {
        return raiz;
    }
}
