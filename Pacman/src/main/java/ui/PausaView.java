package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/** Capa que se muestra sobre el tablero mientras el juego está en pausa. */
public class PausaView {

    public static VBox crear(Runnable continuar, Runnable menu) {
        Label titulo = new Label("PAUSA");
        titulo.getStyleClass().add("overlay-titulo");
        Label ayuda = new Label("Presiona P o Esc para continuar");
        ayuda.getStyleClass().add("overlay-texto");

        Button btnContinuar = new Button("Continuar");
        btnContinuar.getStyleClass().add("boton");
        btnContinuar.setOnAction(e -> continuar.run());
        Button btnMenu = new Button("Menú principal");
        btnMenu.getStyleClass().add("boton");
        btnMenu.setOnAction(e -> menu.run());

        VBox capa = new VBox(16, titulo, ayuda, btnContinuar, btnMenu);
        capa.setAlignment(Pos.CENTER);
        capa.getStyleClass().add("overlay");
        return capa;
    }
}
