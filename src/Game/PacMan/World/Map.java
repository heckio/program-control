package Game.PacMan.World;

import Game.GameStates.PacManState;
import Game.PacMan.entities.Dynamics.BaseDynamic;
import Game.PacMan.entities.Dynamics.Bullet;
import Game.PacMan.entities.Dynamics.Ghost;
import Game.PacMan.entities.Dynamics.PacMan;
import Game.PacMan.entities.Statics.BaseStatic;
import Game.PacMan.entities.Statics.BigDot;
import Game.PacMan.entities.Statics.Dot;
import Game.PacMan.entities.Statics.GhostSpawner;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Map {

    ArrayList<BaseStatic> blocksOnMap;
    ArrayList<BaseDynamic> enemiesOnMap;
    public Handler handler;
    private double bottomBorder;
    private Random rand;
    private int mapBackground;
    public boolean alcapurria = false;
    public int score=11;

    public Map(Handler handler) {
        this.handler = handler;
        this.rand = new Random();
        this.blocksOnMap = new ArrayList<>();
        this.enemiesOnMap = new ArrayList<>();
        bottomBorder = handler.getHeight();
        this.mapBackground = this.rand.nextInt(6);
    }

    public void addBlock(BaseStatic block) {
        blocksOnMap.add(block);
    }

    public void addEnemy(BaseDynamic entity) {

        enemiesOnMap.add(entity);

    }
    //Aquí se añadió la animación para que el BigDot prenda y apague.
    public void drawMap(Graphics2D g2) {
        for (BaseStatic block : blocksOnMap) {
            g2.drawImage(block.sprite, block.x, block.y, block.width, block.height, null);
            if (block instanceof BigDot) {
                g2.drawImage(((BigDot) block).kiki.getCurrentFrame(), block.x, block.y, block.width, block.height, null);
            }
            if(block.getIsFruit() && block instanceof Dot ){
                g2.drawImage(Images.fruits[block.getFruitno()], block.x, block.y, block.width, block.height, null);

            }

        }

        for (BaseDynamic entity : enemiesOnMap) {
            if (entity instanceof Ghost) {
                if (((Ghost) entity).getBounds().intersects(handler.getPacman().getBounds()))
                    if (!((Ghost) entity).frightened) {
                        ((Ghost) entity).goin = true;
                    }
            }

            if (entity instanceof PacMan) {
                switch (((PacMan) entity).facing) {
                    case "Right":
                        if (!((PacMan) entity).pacmandead)
                            g2.drawImage(((PacMan) entity).rightAnim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
                        break;
                    case "Left":
                        if (!((PacMan) entity).pacmandead)
                            g2.drawImage(((PacMan) entity).leftAnim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
                        break;
                    case "Up":
                        if (!((PacMan) entity).pacmandead)
                            g2.drawImage(((PacMan) entity).upAnim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
                        break;
                    case "Down":
                        if (!((PacMan) entity).pacmandead)
                            g2.drawImage(((PacMan) entity).downAnim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
                        break;
                }
                if (((PacMan) entity).pacmandead) {
                    g2.drawImage(((PacMan) entity).deadanim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);

                }


            } else if( entity instanceof Bullet)
                g2.drawImage(entity.sprite, entity.x, entity.y, entity.width, entity.height, null);
        }
        for (BaseDynamic entity : enemiesOnMap) {
            if (entity instanceof Ghost) {
                if (!((Ghost) entity).frightened && !((Ghost) entity).goingin) {
                    g2.drawImage(((Ghost) entity).idleanim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
                }
                if (((Ghost) entity).frightened && ((Ghost) entity).running)
                    g2.drawImage(((Ghost) entity).frightanim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
                if (((Ghost) entity).goingin && ((Ghost) entity).frightened){
                        if(alcapurria)
                            g2.drawImage(((Ghost) entity).fiveanim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);
                        else
                            g2.drawImage(((Ghost) entity).eatenanim.getCurrentFrame(), entity.x, entity.y, entity.width, entity.height, null);


                }

            }

        }

    }

    public ArrayList<BaseStatic> getBlocksOnMap() {
        return blocksOnMap;
    }

    public ArrayList<BaseDynamic> getEnemiesOnMap() {
        return enemiesOnMap;
    }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public void reset() {
        for (BaseDynamic entity : enemiesOnMap) {
            if (entity instanceof PacMan) {
                handler.getPacManState().Mode="Limbo";
                handler.getMusicHandler().playEffect("pacman_death.wav");
            }

            int count = 0;
            int xx=14*30+(handler.getWidth()/2)-(Images.map2.getWidth()*30/2)-15;
            int yy=14*30;
            for (BaseDynamic ent : handler.getMap().getEnemiesOnMap()) {
                if (ent instanceof Ghost) {
                    switch (((Ghost) ent).name) {
                        case "INKY":
                            ((Ghost) ent).x = xx + 2 * MapBuilder.pixelMultiplier;
                            ((Ghost) ent).y = yy;
                            ((Ghost) ent).waiting=true;
                            ((Ghost) ent).frightened=false;
                            ((Ghost) ent).running=true;
                            ((Ghost) ent).frightdelay=0;

                            ((Ghost) ent).out=false;
                            ((Ghost) ent).centered=false;
                            ((Ghost) ent).goingin=false;
                           // ((Ghost) ent).goingout=true;
                            break;
                        case "PINKY":
                            ((Ghost) ent).x = xx;
                            ((Ghost) ent).y = yy;
                            ((Ghost) ent).out=false;
                            ((Ghost) ent).frightdelay=0;

                            ((Ghost) ent).frightened=false;
                            ((Ghost) ent).goingin=false;
                            ((Ghost) ent).running=true;
                            ((Ghost) ent).waiting=true;

                            //((Ghost) ent).goingout=true;
                            break;
                        case "CLYDE":
                            ((Ghost) ent).x = xx - 2 * MapBuilder.pixelMultiplier;
                            ((Ghost) ent).y = yy;
                            ((Ghost) ent).waiting=true;
                            ((Ghost) ent).out=false;
                            ((Ghost) ent).goingin=false;
                            ((Ghost) ent).frightdelay=0;

                            ((Ghost) ent).frightened=false;
                            ((Ghost) ent).running=true;
                            ((Ghost) ent).centered=false;
                            //((Ghost) ent).goingout=true;
                            break;
                        case "BLINKY":
                            ((Ghost) ent).x = xx;
                            ((Ghost) ent).y = yy;
                            ((Ghost) ent).frightened=false;
                            ((Ghost) ent).frightdelay=0;
                            ((Ghost) ent).goingin=false;
                            ((Ghost) ent).centered=false;
                            ((Ghost) ent).running=true;
                            ((Ghost) ent).out=true;
                            ((Ghost) ent).goingout=true;

                            break;
                    }
                }


            }
        }
    }

    public void resetea(){
        score = handler.getScoreManager().getPacmanCurrentScore();
        handler.getPacManState().gh =0;
        handler.getPacManState().counter=0;

        handler.setMap(MapBuilder.createMap(Images.map2, handler));
        handler.getPacManState().startCooldown=60*2;
        handler.getPacManState().fruitsOnMap=0;
    }
    public void nextLVL(){
        handler.getPacManState().counter=0;

        handler.setMap(MapBuilder.createMap(Images.map2, handler));
        handler.getPacManState().gh =0;
        handler.getPacManState().startCooldown=60*2;
        handler.getPacManState().fruitsOnMap=0;

    }

}