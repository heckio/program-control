package Game.GameStates;

import Display.UI.UIManager;
import Game.PacMan.World.MapBuilder;
import Game.PacMan.entities.Dynamics.BaseDynamic;
import Game.PacMan.entities.Dynamics.Ghost;
import Game.PacMan.entities.Dynamics.PacMan;
import Game.PacMan.entities.Statics.BaseStatic;
import Game.PacMan.entities.Statics.BigDot;
import Game.PacMan.entities.Statics.Dot;
import Game.PacMan.entities.Statics.GhostSpawner;
import Main.Handler;
import Resources.Images;

import javax.swing.text.html.parser.Entity;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class PacManState extends State {
    public boolean soundchomp = false;
    public boolean ready = false;
    
    public int chomp =1;
    private UIManager uiManager;
    public String Mode = "Intro";
    boolean espawnea=false;
    int xx;
    int yy;
    Font pixelFont ;
    public int gh =0;
    public int fruitsOnMap=0;
    int one =0;
    int two=0;
    int three=0;
    int four=0;
    int five =0;
    int six =0;
    int seven =0;
    int eight =0;
    public int limbotimer =60*2;
    public int limbo2timer =60;
    public int counter =0;



    ArrayList<BaseDynamic> enemiesOnMap;



    public int startCooldown = 60*4;//seven seconds for the music to finish
    private Object IOException;
    //



    public PacManState(Handler handler){
        super(handler);

        handler.setMap(MapBuilder.createMap(Images.map2, handler));//test change back to map1
        try{
            pixelFont = Font.createFont(Font.TRUETYPE_FONT,new FileInputStream(new File("8bitt.ttf"))).deriveFont(Font.PLAIN,75);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,new File("/8bitt.ttf")));
        }catch(IOException | FontFormatException e){
        }
    }


    @Override
    public void tick() {
        for (BaseStatic block:handler.getMap().getBlocksOnMap()) {
            if(block instanceof BigDot||block instanceof Dot) {
                if(block.getBounds().intersects(handler.getPacman().getBounds())){
                    counter++;
                }
            }
            if (counter==264){
                handler.getMap().nextLVL();
            }
            System.out.println(counter);

            }

            for (BaseStatic block:handler.getMap().getBlocksOnMap()) {
            if(block instanceof BigDot){
                ((BigDot)block ).tick();;
            }
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_C)){
            espawnea=true;
        }
        if(gh<2){
            espawnea=true;
            gh++;
        }
        if(espawnea) {
            int count = 0;
            for (BaseDynamic entity : handler.getMap().getEnemiesOnMap()) {
                if (entity instanceof Ghost) {
                    count++;
                }
            }//determia cuantos ghost hay
            for (BaseStatic entity : handler.getMap().getBlocksOnMap()) {
                if (entity instanceof GhostSpawner) {
                    xx = ((GhostSpawner) entity).getX();
                    yy = ((GhostSpawner) entity).getY();
                }
            }
            
                switch (count) {
                    case 0:
                        BaseDynamic ghost = new Ghost(xx + 2 * MapBuilder.pixelMultiplier, yy, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier, handler, "CLYDE");
                        handler.getMap().addEnemy(ghost);
                        break;
                    case 1:
                        BaseDynamic ghost1 = new Ghost(xx, yy, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier, handler, "INKY");
                        handler.getMap().addEnemy(ghost1);
                        break;
                    case 2:
                        BaseDynamic ghost2 = new Ghost(xx - 2 * MapBuilder.pixelMultiplier, yy, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier, handler, "PINKY");
                        handler.getMap().addEnemy(ghost2);
                        break;
                    case 3:
                        BaseDynamic ghost3 = new Ghost(13* MapBuilder.pixelMultiplier+(handler.getWidth()/2-(28*30)/2), 11 * MapBuilder.pixelMultiplier+2, MapBuilder.pixelMultiplier, MapBuilder.pixelMultiplier, handler, "BLINKY");
                        handler.getMap().addEnemy(ghost3);
                        break;
                }
            
            espawnea=false;
        }
        if (Mode.equals("Stage")){


            if (startCooldown<=0) {
                ready=false;
                //If para que se actualice el High Scorre en cada momento que se obtenga puntos
                ArrayList<BaseStatic> toREmove = new ArrayList<>();
                if(handler.getScoreManager().getPacmanCurrentScore()>handler.getScoreManager().getPacmanHighScore()){
                    handler.getScoreManager().setPacmanHighScore(handler.getScoreManager().getPacmanCurrentScore());
                }
                for (BaseStatic blocks: handler.getMap().getBlocksOnMap()){
                    if (blocks instanceof Dot){
                        if (blocks.getBounds().intersects(handler.getPacman().getBounds())){
                            soundchomp = true;
                            toREmove.add(blocks);
                            if(blocks.getIsFruit()){
                                handler.getScoreManager().addPacmanCurrentScore(120);
                                handler.getMusicHandler().playEffect("pacman_eatfruit.wav");

                            }else{
                                handler.getScoreManager().addPacmanCurrentScore(10);
                            }
                        }
                    }else if (blocks instanceof BigDot){
                        if (blocks.getBounds().intersects(handler.getPacman().getBounds())){
                            soundchomp = true;
                            toREmove.add(blocks);
                            for (BaseDynamic entity : handler.getMap().getEnemiesOnMap()) {
                                if (entity instanceof Ghost) {
                                    if (((Ghost)entity).out) {
                                        ((Ghost)entity).frightdelay =60*7;
                                        ((Ghost)entity).frighton=true;
                                    }
                                }
                            }
                            handler.getScoreManager().addPacmanCurrentScore(100);
                        }
                    }
                }
                if(soundchomp){
                    chomp++;
                    if(chomp%2==0){
                        handler.getMusicHandler().playEffect("chomp1.wav");
                    }else{
                        handler.getMusicHandler().playEffect("chomp2.wav");
                        chomp=1;
                    }
                    soundchomp=false;

                }
                for (BaseDynamic entity : handler.getMap().getEnemiesOnMap()) {
                    entity.tick();
                }

                for (BaseStatic removing: toREmove){
                    handler.getMap().getBlocksOnMap().remove(removing);
                }
            }else{

                startCooldown--;

                ready=true;
            }
        }else if (Mode.equals("Menu")){
            if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
                Mode = "Stage";
                handler.getMusicHandler().playEffect("pacman_beginning.wav");
            }
        }else if(Mode.equals("Limbo")){
            if(limbotimer<0){
                limbotimer=60*2;
                handler.getPacman().deadanim.reset();

                handler.getPacman().setX((int) (13.5*30+handler.getWidth()/2- (28/2)*30));
                handler.getPacman().setY((int) (23*30));

                    Mode ="Stage";
            }else{
                limbotimer--;
                handler.getPacman().deadanim.tick();
            }
        }else if(Mode.equals("Limbo2")){
            if(limbo2timer<0){
                limbo2timer=60;
                Mode="Stage";
                handler.getMap().alcapurria=false;
            }else{
                handler.getMap().alcapurria=true;
                limbo2timer--;
            }
        }else if(Mode.equals("Score")){
            handler.getMap().resetea();
            if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
                Mode = "Stage";
                handler.getScoreManager().setPacmanCurrentScore(0);
            }
        }else{
            if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)){
                Mode = "Menu";
            }
        }



    }

    @Override
    public void render(Graphics g) {

        if (Mode.equals("Stage") ||Mode.equals("Limbo")||Mode.equals("Limbo2")){
            Graphics2D g2 = (Graphics2D) g.create();
            handler.getMap().drawMap(g2);
            g.setColor(Color.RED);
            g.setFont(pixelFont);
            g.drawString("Score ",(handler.getWidth()/2) + (28*30)/2, 225);
            g.drawString("HI-SCORE" ,(handler.getWidth()/2) + (28*30)/2, 75);
            g.setColor(Color.WHITE);
            g.drawString(""+handler.getScoreManager().getPacmanHighScore(),(handler.getWidth()/2) + (28*30)/2, 150);
            g.drawString("" + handler.getScoreManager().getPacmanCurrentScore(),(handler.getWidth()/2) + (28*30)/2, 300);
            //Aqui se implemento un joystick que se queda firme en la pantalla y se mueva en la dirección en la que movemos el PacMan.

            if(handler.getPacman().abouttomoveR  ||   handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)  || handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)  || handler.getKeyManager().right){
                g.drawImage(Images.joysticksprite[1],(handler.getWidth()/2)-40      ,(int)(handler.getHeight()*(.88)),120,120,null);
                g.drawString("Right",0, 75);
            }else if (handler.getPacman().abouttomoveD||handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN  )  || handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)|| handler.getKeyManager().down){
                g.drawImage(Images.joysticksprite[2],(handler.getWidth()/2)-40      ,(int)(handler.getHeight()*(.88)),120,120,null);
                g.drawString("Down",0, 75);
            }else if (handler.getPacman().abouttomoveU||handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)|| handler.getKeyManager().up){
                g.drawImage(Images.joysticksprite[0],(handler.getWidth()/2)-40      ,(int)(handler.getHeight()*(.88)),120,120,null);
                g.drawString("Up",0, 75);
            }else if (handler.getPacman().abouttomoveL||handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)|| handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)|| handler.getKeyManager().left){
                g.drawImage(Images.joysticksprite[3],(handler.getWidth()/2)-40      ,(int)(handler.getHeight()*(.88)),120,120,null);
                g.drawString("Left",0, 75);
            }else{
                g.drawImage(Images.joysticksprite[4],(handler.getWidth()/2)-40      ,(int)(handler.getHeight()*(.88)),120,120,null);
//
            }

            if(fruitsOnMap>7){
                g.setColor(Color.orange);
                g.drawString("PRESS SPACE TO SHOOT", handler.getWidth() / 4 +160, 955);
                g.setColor(Color.WHITE);
            }



            //Este se le anadió para que dibuje las animaciones del PacMan y por cada caso se vayan eliminando.

            switch (handler.getPacman().lives){
                case 3:
                    g.drawImage(Images.pacmanRight[0],(handler.getWidth()/2) + (28*30)/2       ,700,80,80,null);
                    g.drawImage(Images.pacmanRight[0],(handler.getWidth()/2) + (28*30)/2+70+25 ,700,80,80,null);
                    g.drawImage(Images.pacmanRight[0],(handler.getWidth()/2) + (28*30)/2+140+50,700,80,80,null);
                    break;
                case 2:
                    g.drawImage(Images.pacmanRight[0],(handler.getWidth()/2) + (28*30)/2,700,80,80,null);
                    g.drawImage(Images.pacmanRight[0],(handler.getWidth()/2) + (28*30)/2+70+25,700,80,80,null);
                    break;
                case 1:
                    g.drawImage(Images.pacmanRight[0],(handler.getWidth()/2) + (28*30)/2,700,80,80,null);
                    break;
                case 0:
                    g.setColor(Color.RED);
                    g.drawString("GAME OVER", handler.getWidth() / 2 - 145, 548);
                    Mode = "Score";

                    break;
            }

            // Este for se hizo para que dibuje las frutas a la derecha una vez el PacMan se las coma.

            for (BaseStatic entity : handler.getMap().getBlocksOnMap()) {

                if (entity instanceof Dot && entity.getIsFruit() && entity.getBounds().intersects(handler.getPacman().getBounds())) {
                        if(fruitsOnMap<8){
                            fruitsOnMap++;
                        }

                    switch(fruitsOnMap){
                        case 1:
                            one  = entity.getFruitno();
                            break;
                        case 2:
                            two  = entity.getFruitno();
                            break;
                        case 3:
                            three= entity.getFruitno();
                            break;
                        case 4:
                            four = entity.getFruitno();
                            break;
                        case 5:
                            five = entity.getFruitno();
                            break;
                        case 6:
                            six = entity.getFruitno();
                            break;
                        case 7:
                            seven = entity.getFruitno();
                            break;
                        case 8:
                            eight = entity.getFruitno();
                            break;
                    }
                }
            }

            //Aqui se obtiene las fotos de las frutas para que se proyecten en pantalla.

            switch(fruitsOnMap){
                case 1:
                    g.drawImage(Images.fruits[one],(handler.getWidth()/2) + (28*30)/2       ,500,60,60,null);
                    break;
                case 2:
                    g.drawImage(Images.fruits[one],(handler.getWidth()/2) + (28*30)/2       ,500,60,60,null);
                    g.drawImage(Images.fruits[two],(handler.getWidth()/2) + (28*30)/2+(65),500,60,60,null);
                    break;
                case 3:
                    g.drawImage(Images.fruits[one],(handler.getWidth()/2) + (28*30)/2       ,500,60,60,null);
                    g.drawImage(Images.fruits[two],(handler.getWidth()/2) + (28*30)/2+(65),500,60,60,null);
                    g.drawImage(Images.fruits[three],(handler.getWidth()/2) + (28*30)/2+(65*2),500,60,60,null);
                    break;
                case 4:
                    g.drawImage(Images.fruits[one],(handler.getWidth()/2) + (28*30)/2       ,500,60,60,null);
                    g.drawImage(Images.fruits[two],(handler.getWidth()/2) + (28*30)/2+(65),500,60,60,null);
                    g.drawImage(Images.fruits[three],(handler.getWidth()/2) + (28*30)/2+(65*2),500,60,60,null);
                    g.drawImage(Images.fruits[four],(handler.getWidth()/2) + (28*30)/2+(65*3),500,60,60,null);
                    break;
                case 5:
                    g.drawImage(Images.fruits[one],(handler.getWidth()/2) + (28*30)/2       ,500,60,60,null);
                    g.drawImage(Images.fruits[two],(handler.getWidth()/2) + (28*30)/2+(65),500,60,60,null);
                    g.drawImage(Images.fruits[three],(handler.getWidth()/2) + (28*30)/2+(65*2),500,60,60,null);
                    g.drawImage(Images.fruits[four],(handler.getWidth()/2) + (28*30)/2+(65*3),500,60,60,null);
                    g.drawImage(Images.fruits[five],(handler.getWidth()/2) + (28*30)/2+(65*0),570,60,60,null);
                    break;
                case 6:
                    g.drawImage(Images.fruits[one],(handler.getWidth()/2) + (28*30)/2       ,500,60,60,null);
                    g.drawImage(Images.fruits[two],(handler.getWidth()/2) + (28*30)/2+(65),500,60,60,null);
                    g.drawImage(Images.fruits[three],(handler.getWidth()/2) + (28*30)/2+(65*2),500,60,60,null);
                    g.drawImage(Images.fruits[four],(handler.getWidth()/2) + (28*30)/2+(65*3),500,60,60,null);
                    g.drawImage(Images.fruits[five],(handler.getWidth()/2) + (28*30)/2+(65*0),570,60,60,null);
                    g.drawImage(Images.fruits[six],(handler.getWidth()/2) + (28*30)/2+(65*1),570,60,60,null);
                    break;
                case 7:
                    g.drawImage(Images.fruits[one],(handler.getWidth()/2) + (28*30)/2       ,500,60,60,null);
                    g.drawImage(Images.fruits[two],(handler.getWidth()/2) + (28*30)/2+(65),500,60,60,null);
                    g.drawImage(Images.fruits[three],(handler.getWidth()/2) + (28*30)/2+(65*2),500,60,60,null);
                    g.drawImage(Images.fruits[four],(handler.getWidth()/2) + (28*30)/2+(65*3),500,60,60,null);
                    g.drawImage(Images.fruits[five],(handler.getWidth()/2) + (28*30)/2+(65*0),570,60,60,null);
                    g.drawImage(Images.fruits[six],(handler.getWidth()/2) + (28*30)/2+(65*1),570,60,60,null);
                    g.drawImage(Images.fruits[seven],(handler.getWidth()/2) + (28*30)/2+(65*2),570,60,60,null);
                    break;
                case 8:
                    g.drawImage(Images.fruits[one],(handler.getWidth()/2) + (28*30)/2       ,500,60,60,null);
                    g.drawImage(Images.fruits[two],(handler.getWidth()/2) + (28*30)/2+(65),500,60,60,null);
                    g.drawImage(Images.fruits[three],(handler.getWidth()/2) + (28*30)/2+(65*2),500,60,60,null);
                    g.drawImage(Images.fruits[four],(handler.getWidth()/2) + (28*30)/2+(65*3),500,60,60,null);
                    g.drawImage(Images.fruits[five],(handler.getWidth()/2) + (28*30)/2+(65*0),570,60,60,null);
                    g.drawImage(Images.fruits[six],(handler.getWidth()/2) + (28*30)/2+(65*1),570,60,60,null);
                    g.drawImage(Images.fruits[seven],(handler.getWidth()/2) + (28*30)/2+(65*2),570,60,60,null);
                    g.drawImage(Images.fruits[eight],(handler.getWidth()/2) + (28*30)/2+(65*3),570,60,60,null);

                    break;

            }

            g.drawImage(Images.fruits[6],(handler.getWidth()/2) + (10*30)/2+(65*2),Images.map2.getHeight()*30,60,60,null);
            g.drawImage(Images.fruits[7],(handler.getWidth()/2) + (10*30)/2+(65*3),Images.map2.getHeight()*30,60,60,null);
    
    
    
        }else if (Mode.equals("Menu")){
            g.drawImage(Images.start,(handler.getWidth()/4),0,handler.getWidth()/2,handler.getHeight(),null);
        }else if (Mode.equals("Score")){
            g.drawImage(Images.score,handler.getWidth()/4,0,handler.getWidth()/2,handler.getHeight(),null);
            g.setFont(pixelFont);
            g.setColor(Color.YELLOW);
            g.drawString("POS",handler.getWidth()/4+75, 200);
            g.drawString("SCORE",handler.getWidth()/2-75, 200);
            g.drawString("NOMBRE",handler.getWidth()*3/4-75*3, 200);
            g.setColor(Color.magenta);
            g.drawString("1st",handler.getWidth()/4+75, 200+70*1);
            g.drawString("2st",handler.getWidth()/4+75, 200+70*2);
            g.drawString("3st",handler.getWidth()/4+75, 200+70*3);
            g.drawString("4st",handler.getWidth()/4+75, 200+70*4);
            g.drawString("6st",handler.getWidth()/4+75, 200+70*6);
            //g.drawString(Integer.toString(handler.getScoreManager().getPacmanCurrentScore()),handler.getWidth()/2, 75);
            g.drawString(String.format("%05d", handler.getScoreManager().getPacmanCurrentScore()*8),handler.getWidth()/2-75, 200+70*1);
            g.drawString(String.format("%05d", handler.getScoreManager().getPacmanCurrentScore()*5),handler.getWidth()/2-75, 200+70*2);
            g.drawString(String.format("%05d", handler.getScoreManager().getPacmanCurrentScore()*3),handler.getWidth()/2-75, 200+70*3);
            g.drawString(String.format("%05d", handler.getScoreManager().getPacmanCurrentScore()*2),handler.getWidth()/2-75, 200+70*4);
            g.drawString(String.format("%05d", handler.getScoreManager().getPacmanCurrentScore()/2),handler.getWidth()/2-75, 200+70*6);
            g.drawString("BIENVE",handler.getWidth()*3/4-75*3, 200+70*1);
            g.drawString("JOSHUA",handler.getWidth()*3/4-75*3, 200+70*2);
            g.drawString("DIONEL",handler.getWidth()*3/4-75*3, 200+70*3);
            g.drawString("JOSIAN",handler.getWidth()*3/4-75*3, 200+70*4);
            g.drawString("ANDRES",handler.getWidth()*3/4-75*3, 200+70*6);
            g.setColor(Color.CYAN);
            g.drawString("PLAYER",handler.getWidth()*3/4-75*3, 200+70*5);
            g.drawString("5st",handler.getWidth()/4+75, 200+70*5);
            g.drawString(String.format("%05d", handler.getScoreManager().getPacmanCurrentScore()),handler.getWidth()/2-75, 200+70*5);





        }else{
            g.drawImage(Images.intro,handler.getWidth()/4,0,handler.getWidth()/2,handler.getHeight(),null);
        }
        g.setColor(Color.YELLOW);
    
        if(ready) {
            g.drawString("READY !", handler.getWidth() / 2 - 100, 548);
        }
        
    
        g.setColor(Color.WHITE);







    }

    @Override
    public void refresh() {

    }

    /*public void drawfruit(){
        if(fruitsOnMap<2 && !fruitsOnMap==0){
            g.drawImage(Images.fruits[one  ],(handler.getWidth()/2) + (28*30)/2+(65*0),500,60,60,null);
        }
        if(fruitsOnMap<3 && !fruitsOnMap==0){
            g.drawImage(Images.fruits[two  ],(handler.getWidth()/2) + (28*30)/2+(65*1),500,60,60,null);
        }
        if(fruitsOnMap<4 && !fruitsOnMap==0){
            g.drawImage(Images.fruits[three],(handler.getWidth()/2) + (28*30)/2+(65*2),500,60,60,null);
        }
        if(fruitsOnMap<5 && !fruitsOnMap==0){
            g.drawImage(Images.fruits[four ],(handler.getWidth()/2) + (28*30)/2+(65*0),570,60,60,null);
        }
        if(fruitsOnMap<6 && !fruitsOnMap==0){
            g.drawImage(Images.fruits[five ],(handler.getWidth()/2) + (28*30)/2+(65*1),570,60,60,null);
        }
    }*/




}
