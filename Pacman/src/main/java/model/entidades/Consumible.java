package model.entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Consumible {
    private boolean estado = true;
    private ImageView consumible;
    private final int score;
    private static final int CELL_SIZE = 20;

    public Consumible(TypeOfConsumable type){
        Image score = new Image("/consumibles/point.png");
        Image pill = new Image("/consumibles/powerFood.png");
        Image cherry = new Image("/consumibles/cherry.png");
        if(type == TypeOfConsumable.SCORE){
            this.consumible = new ImageView(score);
            this.score = 10;
        } else if (type == TypeOfConsumable.PILL) {
            this.consumible = new ImageView(pill);
            this.score = 50;
        }else{
            this.consumible = new ImageView(cherry);
            this.score = 100;
        }
        consumible.setFitWidth(CELL_SIZE);
        consumible.setFitHeight(CELL_SIZE);
    }

    public int getScore(){return this.score;}
    public boolean getEstado(){return this.estado;}

    public void changeEstado(){
        this.estado = false;
        Image nada = new Image("/consumibles/nada.png");
        this.consumible.setImage(nada);
    }

    public ImageView getNode() {
        return consumible;
    }
}
