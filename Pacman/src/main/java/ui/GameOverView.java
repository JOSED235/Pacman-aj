package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/** Capa de fin de partida con el puntaje obtenido y las opciones para seguir. */
public class GameOverView {

    public static VBox crear(int puntos, int record, int nivel, Runnable reintentar, Runnable menu) {
        Label titulo = new Label("GAME OVER");
        titulo.getStyleClass().addAll("overlay-titulo", "overlay-titulo-rojo");
        Label puntaje = new Label("Puntaje: " + puntos);
        puntaje.getStyleClass().add("overlay-puntaje");
        Label detalle = new Label("Llegaste al nivel " + nivel + "  ·  Récord: " + record);
        detalle.getStyleClass().add("overlay-texto");

        Button btnReintentar = new Button("Jugar de nuevo");
        btnReintentar.getStyleClass().add("boton");
        btnReintentar.setOnAction(e -> reintentar.run());
        Button btnMenu = new Button("Menú principal");
        btnMenu.getStyleClass().add("boton");
        btnMenu.setOnAction(e -> menu.run());

        VBox capa = new VBox(16, titulo, puntaje, detalle, btnReintentar, btnMenu);
        capa.setAlignment(Pos.CENTER);
        capa.getStyleClass().add("overlay");
        return capa;
    }
}
