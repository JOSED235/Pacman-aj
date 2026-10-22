package ui;

import controller.JuegoController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menuview {

    /** Crea una escena con la hoja de estilos del juego aplicada. */
    static Scene escena(Parent root) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Menuview.class.getResource("/estilo.css").toExternalForm());
        return scene;
    }

    /** Muestra el menú principal en la ventana indicada (al iniciar o al volver desde el juego). */
    public static void abrirMenu(Stage stage) {
        try {
            Parent root = new FXMLLoader(Menuview.class.getResource("/fxml/menu-view.fxml")).load();
            stage.setTitle("Pac-Man · Grafos");
            stage.setScene(escena(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            throw new IllegalStateException("No se pudo cargar el menú", e);
        }
    }

    @FXML
    public void iniciarJuego(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game-view.fxml"));
            Parent root = loader.load();
            JuegoController juego = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Pac-Man · Grafos");
            stage.setScene(escena(root));
            stage.setResizable(false);
            // Al cerrar la ventana se detienen el ciclo de juego y los hilos de los fantasmas
            stage.setOnHidden(e -> juego.detener());
            stage.show();
            root.requestFocus();

            // Cerrar ventana del menú
            ((Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow()).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void verManual(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manual-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Manual del Jugador");
            stage.setScene(escena(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void salirApp(ActionEvent event) {
        Platform.exit();
    }
}
