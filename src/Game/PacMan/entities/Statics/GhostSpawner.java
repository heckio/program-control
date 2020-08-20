package Game.PacMan.entities.Statics;

import Game.PacMan.entities.Dynamics.BaseDynamic;
import Game.PacMan.entities.Dynamics.Ghost;
import Game.PacMan.entities.Dynamics.PacMan;
import Game.PacMan.entities.Statics.*;
import Game.PacMan.World.MapBuilder;
import Main.Handler;
import Game.PacMan.World.Map;
import Resources.Images;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;


public class GhostSpawner extends BaseStatic{
    Random  R = new Random();
    int outtimer = R.nextInt(10)*60;

    public GhostSpawner(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.pacmanDots[1]);
    }

    public void  tick(){
        for (BaseDynamic ghost:handler.getMap().getEnemiesOnMap()){
            if(ghost instanceof Ghost){
                if(((Ghost) ghost).waiting){
                    if(outtimer<0){
                        ((Ghost) ghost).out=true;
                    }else
                        outtimer--;
                }
            }
        }
    }
}
