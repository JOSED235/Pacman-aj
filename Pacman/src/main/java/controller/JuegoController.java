package controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import model.juego.Laberinto;
import model.juego.Sprite;
import model.juego.SpriteType;

import java.util.ArrayList;

public class JuegoController {

    private Laberinto laberinto;
    private ArrayList<Sprite> sprites;

    @FXML
    private GridPane gridPane;

    private Image wallImg;
    private Image floorImg;
    private Image powerFoodImg;

    public JuegoController() {
        laberinto = new Laberinto();
        sprites = laberinto.obtenerSprites();

        // Cargar imágenes desde resources/pictures
        wallImg = new Image(getClass().getResource("/pictures/wall.png").toExternalForm());
        floorImg = new Image(getClass().getResource("/pictures/floor.png").toExternalForm());
        powerFoodImg = new Image(getClass().getResource("/pictures/powerFood.png").toExternalForm());
    }

    public void setupKeyListeners(Scene scene) {
        scene.setOnKeyPressed(this::actions);
    }

    @FXML
    public void initialize() {
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        Image wall = new Image(getClass().getResource("/pictures/wall.png").toExternalForm());
        Image floor = new Image(getClass().getResource("/pictures/floor.png").toExternalForm());
        Image powerFood = new Image(getClass().getResource("/pictures/powerFood.png").toExternalForm());
        Image pellet = new Image(getClass().getResource("/pictures/pellet.png").toExternalForm()); // Asegúrate de tenerlo

        for (Sprite sprite : laberinto.obtenerSprites()) {
            ImageView view = new ImageView();
            view.setFitWidth(Laberinto.CELL_SIZE);
            view.setFitHeight(Laberinto.CELL_SIZE);

            switch (sprite.getType()) {
                case WALL:
                    view.setImage(wall);
                    break;
                case FLOOR:
                    view.setImage(floor);
                    break;
                case POWER_FOOD:
                    view.setImage(floor);
                    gridPane.add(view, sprite.getCol(), sprite.getRow());

                    ImageView pf = new ImageView(powerFood);
                    pf.setFitWidth(8);
                    pf.setFitHeight(8);
                    gridPane.add(pf, sprite.getCol(), sprite.getRow());
                    continue;

                case PELLET:
                    view.setImage(floor);
                    gridPane.add(view, sprite.getCol(), sprite.getRow());

                    ImageView p = new ImageView(pellet);
                    p.setFitWidth(4);
                    p.setFitHeight(4);
                    gridPane.add(p, sprite.getCol(), sprite.getRow());
                    continue;

                case GHOST_AREA:
                    view.setImage(floor);
                    break;
                case DOOR:
                    view.setImage(floor); // puedes tener image door.png también
                    break;
                default:
                    view.setImage(floor);
                    break;
            }

            gridPane.add(view, sprite.getCol(), sprite.getRow());
        }
    }

    public void actions(KeyEvent event) {
        KeyCode code = event.getCode();
        System.out.println("Tecla presionada: " + code);
    }
}