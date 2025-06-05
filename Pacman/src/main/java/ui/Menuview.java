package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class Menuview {

    @FXML
    public void iniciarJuego(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Juego en curso");
            stage.setScene(new Scene(root));
            stage.show();

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
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void salirApp(ActionEvent event) {
        System.exit(0);
    }
}
