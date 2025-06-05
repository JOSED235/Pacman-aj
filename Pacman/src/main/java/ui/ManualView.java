package ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ManualView {

    @FXML
    private TextArea textAreaManual;
    @FXML
    public void initialize() {
        try (InputStream is = getClass().getResourceAsStream("/manual/instrucciones.txt")) {
            if (is == null) {
                textAreaManual.setText("Error: No se encontró el archivo de instrucciones.");
                return;
            }
            // Leer todo el archivo como String
            String instrucciones = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            textAreaManual.setText(instrucciones);
        } catch (Exception e) {
            textAreaManual.setText("Error al cargar instrucciones:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) textAreaManual.getScene().getWindow();
        stage.close();
    }
}
