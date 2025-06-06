package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar FXML del menú principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu-view.fxml"));
        Parent root = loader.load();

        // Crear la escena principal con el archivo de estilos
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/estilo.css").toExternalForm());

        // Configurar la ventana principal
        primaryStage.setTitle("Pacman Grafo");
        primaryStage.setScene(scene);
        primaryStage.setWidth(800); // Ajustable según necesidad
        primaryStage.setHeight(600);
        primaryStage.setResizable(false); // Deshabilita redimensionamiento
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}