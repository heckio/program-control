package Game.PacMan.entities.Statics;

import Game.PacMan.entities.BaseEntity;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.image.BufferedImage;

public class BaseStatic extends BaseEntity {
    public BaseStatic(int x, int y, int width, int height, Handler handler, BufferedImage sprite) {
        super(x, y, width, height, handler, sprite);

    }

    boolean fruit =false;
    int frappe = 0;

    public void setFruit(){
        fruit = true;
    }

    public void setFruit(int t){ frappe = t; }


    public boolean getIsFruit(){
        return fruit;
    }
    public int getFruitno(){
        return frappe;
    }
}
