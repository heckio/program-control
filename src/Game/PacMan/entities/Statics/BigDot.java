package Game.PacMan.entities.Statics;

import Game.PacMan.entities.Dynamics.BaseDynamic;
import Game.PacMan.entities.Dynamics.Ghost;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;


public class BigDot extends BaseStatic{
    public Animation kiki;

    public BigDot(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.pacmanDots[1]);
        kiki  = new Animation(128, Images.pacmanDots);
    }
    public void tick(){
        kiki.tick();
    }

}
