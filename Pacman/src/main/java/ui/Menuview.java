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
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Instrucciones:\n\n- Usa las flechas para mover a Pacman.\n- Come los puntos y evita los fantasmas.\n- Usa las super-píldoras para cambiar el comportamiento.",
                ButtonType.OK);
        alert.setTitle("Manual del Jugador");
        alert.setHeaderText("Cómo jugar");
        alert.showAndWait();
    }

    @FXML
    public void salirApp(ActionEvent event) {
        System.exit(0);
    }
}
