package model.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Consumible {
    // Las imágenes se cargan una sola vez y se comparten: el laberinto tiene cientos de consumibles
    private static Image imgScore;
    private static Image imgPill;
    private static Image imgCherry;

    private boolean estado = true;
    private final ImageView consumible;
    private final int score;
    private final TypeOfConsumable tipo;
    private static final int CELL_SIZE = 20;

    public Consumible(TypeOfConsumable type){
        cargarImagenes();
        this.tipo = type;
        if(type == TypeOfConsumable.SCORE){
            this.consumible = new ImageView(imgScore);
            this.score = 10;
        } else if (type == TypeOfConsumable.PILL) {
            this.consumible = new ImageView(imgPill);
            this.score = 50;
        }else{
            this.consumible = new ImageView(imgCherry);
            this.score = 100;
        }
        consumible.setFitWidth(CELL_SIZE);
        consumible.setFitHeight(CELL_SIZE);
    }

    private static synchronized void cargarImagenes() {
        if (imgScore != null) return;
        imgScore = new Image("/consumibles/point.png");
        imgPill = new Image("/consumibles/powerFood.png");
        imgCherry = new Image("/consumibles/cherry.png");
    }

    public int getScore(){return this.score;}
    public boolean getEstado(){return this.estado;}
    public TypeOfConsumable getTipo(){return this.tipo;}

    public void changeEstado(){
        this.estado = false;
        this.consumible.setVisible(false);
    }

    public ImageView getNode() {
        return consumible;
    }
}
