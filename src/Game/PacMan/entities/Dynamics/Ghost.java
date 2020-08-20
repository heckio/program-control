package Game.PacMan.entities.Dynamics;

import Game.PacMan.World.Map;
import Game.PacMan.World.MapBuilder;
import Game.PacMan.entities.Statics.BaseStatic;
import Game.PacMan.entities.Statics.BoundBlock;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Ghost extends BaseDynamic{

    protected double velX=3 ,velY=3 ,speed = 3;
    public String facing = "Up";
    public boolean moving = true,turnFlag = false;
    public Animation idleanim;
    public Animation frightanim;
    public Animation eatenanim;
    public Animation fiveanim;
    int turnCooldown = 5;
    public boolean scattered=false;
    public boolean goin=false;
    public boolean running=true;
    public int TPX=0;
    public int TPY=0;

    public boolean collition=false;
    public boolean abouttomoveD=false;
    public boolean waiting = true;
    public boolean goingout = false;
    public boolean goingin = false;
    public boolean centered=false;
    public boolean centralizado=false;
    public int direcccion= 1;
    public boolean stuck =false;
    public boolean out = false;
    public boolean chasing=false;
    public boolean chasing_Ed=false;
    public boolean frightened=false;
    public boolean frighton=false;
    int n =5*60;
    public int frightdelay =60*7;
    public int waitingtimer =60*3;
    int scatteredtimer =60*3;
    int chasingtimer =60*5;
    public int prevX;
    public int prevY;
    int xmapsize= Images.map2.getWidth();
    int ymapsize = Images.map2.getHeight();
    public boolean[][][] nodes = new boolean[ymapsize][xmapsize][5];//makes a 2d all false
    int multip = MapBuilder.pixelMultiplier;
    public boolean[][] Visited = new boolean[ymapsize][xmapsize];//makes a 2d all false
    public int[][][] Queue = new int[ymapsize][xmapsize][3];//
    public boolean[][] nodos = new boolean[ymapsize][ymapsize];//
    public String name;




    public Ghost(int x, int y, int width, int height, Handler handler,String name) {
        super(x, y, width, height, handler,Images.ghostB[0]);
        BufferedImage[] imagenghost;
        imagenghost = new BufferedImage[8];
        //Se le anadió los nombres de cada ghost del juego con su respectiva foto.

        switch(name){
            case "INKY":
                imagenghost = Images.ghostI;
                waitingtimer=60*7;
                break;
            case "BLINKY":
                imagenghost = Images.ghostB;
                waitingtimer=0;
                break;
            case "CLYDE":
                waitingtimer=60*10;
                imagenghost = Images.ghostC;
                break;
            case "PINKY":
                waitingtimer=60*20;
                imagenghost = Images.ghostP;
                break;
        }
        idleanim = new Animation(128,imagenghost);
        frightanim = new Animation(128,Images.frightened);
        eatenanim = new Animation(128,Images.eaten);
        fiveanim = new Animation(128,Images.ghost500);

        this.name = name;
    }



    @Override
    public void tick() {
        //Instrucción para que cuando presione la tecla E salgan todos los ghost del cuadro.

        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
            waiting = false;
            goingout = true;
        }//SENDS THEM GoingOUT

        if (waiting) {
            if(waitingtimer<0){
                waiting = false;
                goingout = true;
                waitingtimer=60*10;
            }
            else{
                waitingtimer--;
                switch (facing) {
                    case "Up"://14:13  14:15
                        if (y > 13 * multip) {
                            y -= 1;
                            idleanim.tick();
                        } else {
                            facing = "Down";
                        }
                        break;
                    case "Down":
                        if (y < 15 * multip) {
                            y += 1;
                            idleanim.tick();
                        } else {
                            facing = "Up";
                        }
                        break;
                }
            }

        }

        else if (goingout) {
            switch (facing) {
                case "Right":
                    x += 1;
                    idleanim.tick();
                    break;
                case "Left":
                    x -= 1;
                    idleanim.tick();
                    break;
                case "Up":
                    y -= 1;
                    idleanim.tick();
                    break;
                case "Down":
                    y += 1;
                    idleanim.tick();
                    break;
            }
            if (!centered) {
                if (getFixedXcoordinate() > 14*30) {//point to reach(14 , 13.5)
                    facing = "Left";
                } else if (getFixedXcoordinate() < 13*30) {//point to reach(14 , 13.5)
                    facing = "Right";
                } else if (y > 14 * multip) {//point to reach(14 , 13.5)
                    facing = "Up";
                }
                if (getFixedXcoordinate() >= 13*30 && getFixedXcoordinate() <= 14*30) {
                    centered = true;
                }
            }
            else {
                //Ghost leave the base and go into chase mode
                if (y > 11 * multip) {
                    facing = "Up";
                } else if(y<=11*multip){
                    //RANDOM FOR THE INITIAL RELEASE DIRECTION
                    int direction_0 = new Random().nextInt(2);
                    switch (direction_0){
                        case 0:
                            facing = "Right";
                            break;
                        case 1:
                            facing = "Left";
                            break;
                    }
                    goingout = false;
                    chasing=true;
                    out = true;
                }
            }
        }

        else if (out) {

            if (frighton) {
                if(frightdelay<0&&!goingin){
                    frightdelay =60*7;
                    frighton=false;
                    frightened = false;
                    chasing=true;
                    goingin=false;
                    chasing_Ed=false;
                    scattered=false;
                }else{
                    frightdelay--;
                    frightened = true;
                    chasing=false;
                    chasing_Ed=false;
                    scattered=false;
                }

            }
            //En este if los ghost utilizan la posición del pacman para poder encontrarte y atacarte
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Y)) {
                frightened=false;
                scattered=false;
                chasing = true;
                chasing_Ed=false;
            }

            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_U)) {


            }
            //SCATTERED
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_I)) {
                scattered=true;
                chasing_Ed = false;
                chasing=false;
                frightened=false;
            }

            //FIXED CHASING
            if (chasing) {
                int gotox = handler.getPacman().getFixedXcoordinate();//coordinates the ghost will follow
                int gotoy = handler.getPacman().getY();
                int deltaX = gotox-getFixedXcoordinate();
                int deltaY = -(gotoy-y  );

                if(chasingtimer<=0){
                    scattered=true;
                    chasing=false;
                    scatteredtimer=n;
                    n-=5;
                }else{
                    chasingtimer--;
                }
                //find node (input direction)
                facingswitch(idleanim);
                //
                //Determina cuales son los pixeles los cuales se pueden girar los ghost, también aplica para el PacMan.

                if (Node(0)) {// at node

                    //int PixlX = ((x+(handler.getWidth()/2-(28*30)/2))/multip - (handler.getPacman().x+(handler.getWidth()/2-(28*30)/2))/multip) ;
                    int PixlX = getFixedXpixel() - handler.getPacman().getFixedXpixel();
                    int PixlY = y/multip - handler.getPacman().y/multip ;
                    switch (name) {
                        case "INKY":
                            switch(handler.getPacman().facing){
                                case"Up":
                                    PixlX = 2*(PixlX+2);
                                    PixlY = 2*(PixlY+2);
                                    break;
                                case"Right":
                                    PixlX = 2*(PixlX-2);
                                    PixlY = 2*PixlY;
                                    break;
                                case"Left":
                                    PixlX = 2*(PixlX+2);
                                    PixlY = 2*PixlY;
                                    break;
                                case "Down":
                                    PixlX = 2*PixlX;
                                    PixlY = 2*(PixlY-2);
                                    break;
                            }
                            break;
                        case "BLINKY":
                            //PixlX = ((x+(handler.getWidth()/2-(28*30)/2))/multip - (handler.getPacman().x+(handler.getWidth()/2-(28*30)/2))/multip) ;
                            PixlX = getFixedXpixel() - handler.getPacman().getFixedXpixel();
                            PixlY = y/multip-handler.getPacman().y/multip ;
                            break;
                        case "CLYDE":
                            //Con el metodo de Pitágoras determina cual es la distancia mas corta entre el ghost y el PacMan, lo cual pueda determinar cual es la forma mas rápida de llegar al PacMan.
                            if(Pithagoras(PixlX,PixlY)<8){
                                chasing=false;
                                scattered=true;
                            }
                            break;
                        case "PINKY":
                            PixlX = getFixedXpixel() - handler.getPacman().getFixedXpixel();
                            //PixlX = ((x+(handler.getWidth()/2-(28*30)/2))/multip - (handler.getPacman().x+(handler.getWidth()/2-(28*30)/2))/multip) ;
                            PixlY = y/multip-handler.getPacman().y/multip ;
                            switch(handler.getPacman().facing){
                                case"Up":
                                    PixlX = PixlX+4;
                                    PixlY = PixlY+4;
                                    break;
                                case"Right":
                                    PixlX = PixlX-4;
                                    break;
                                case"Left":
                                    PixlX = PixlX+4;
                                    break;
                                case "Down":
                                    PixlY = PixlY-4;
                                    break;
                            }
                            break;
                    }
                    TPX=PixlX;
                    TPY=PixlY;
                    if(PixlX<0 && PixlY>=0){

                        double upD = Pithagoras(PixlX,PixlY-1);
                        double rtD = Pithagoras(PixlX+1,PixlY);
                        if(Node(3)&&Node(2)&& facing !="Down"&& facing !="Left") {
                            if (upD >= rtD) {
                                direcccion = 0;
                            } else direcccion = 2;
                        }else if(Node(3)&& facing !="Down"){
                            direcccion = 2;
                        }else if(Node(2)&& facing !="Left"){
                            direcccion = 0;
                        }else if(Node(1)&& facing !="Right"){
                            direcccion = 1;
                        }else if(Node(4)&& facing !="Up"){
                            direcccion = 3;
                        }
                    }else if(PixlX>=0&& PixlY>0){
                        if(Node(3)&&Node(1)&& facing !="Down"&& facing !="Right") {
                            double upD = Pithagoras(PixlX,PixlY-1);
                            double lfD = Pithagoras(PixlX-1,PixlY);
                            if(upD>=lfD){
                                direcccion = 1;
                            }
                            else direcccion = 2;
                        }else if(Node(3)&& facing !="Down"){
                            direcccion = 2;
                        }else if(Node(1)&& facing !="Right"){
                            direcccion = 1;
                        }else if(Node(2)&& facing !="Left"){
                            direcccion = 0;
                        }else if(Node(4)&& facing !="Up"){
                            direcccion = 3;
                        }
                    }else if(PixlX>0&&PixlY<=0){
                        if(Node(4)&&Node(1)&& facing !="Right"&& facing !="Up") {
                            double dnD = Pithagoras(PixlX,PixlY-1);
                            double lfD = Pithagoras(PixlX+1,PixlY);
                            if(dnD>=lfD){
                                direcccion = 1;
                            }else                             direcccion = 3;
                        }else if(Node(1)&& facing !="Right"){
                            direcccion = 1;
                        }else if(Node(4)&& facing !="Up"){
                            direcccion = 3;
                        }else if(Node(3)&& facing !="Down"){
                            direcccion = 2;
                        }else if(Node(2)&& facing !="Left"){
                            direcccion = 0;
                        }

                    }else if(PixlX<=0&& PixlY<0){
                        if(Node(4)&&Node(2)&& facing !="Up"&& facing !="Right") {
                            double rtD = Pithagoras(PixlX + 1, PixlY);
                            double dnD = Pithagoras(PixlX, PixlY + 1);
                            if (rtD >= dnD) {
                                direcccion = 3;
                            } else       direcccion = 0;
                        } else if (Node(4) && facing !="Up"){
                            direcccion = 3;
                        } else if (Node(2)&& facing !="Right"){
                            direcccion = 0;
                        }else if(Node(3)&& facing !="Down"){
                            direcccion = 2;
                        }else if(Node(1)&& facing !="Left"){
                            direcccion = 1;
                        }
                    }
                    switch (direcccion) {
                        case 0:
                            if (checkPreHorizontalCollision("Right") && Node(2)) {
                                facing = "Right";
                                turnFlag = true;
                                turnCooldown = 5;
                            }
                            break;
                        case 1:
                            if (checkPreHorizontalCollision("Left") && Node(1)) {
                                facing = "Left";
                                turnFlag = true;
                                turnCooldown = 5;
                            }
                            break;

                        case 2:
                            if (checkPreVerticalCollisions("Up") && Node(3)) {
                                facing = "Up";//30 30*3 30*6 30*9 30*12 30*15 30*18 30*21 30*26
                                turnFlag = true;
                                turnCooldown = 5;
                            }
                            break;

                        case 3:
                            if (checkPreVerticalCollisions("Down") && Node(4)) {
                                facing = "Down";
                                turnFlag = true;
                                turnCooldown = 5;
                            }
                            break;
                    }
                }//at node

            }
            //No se utiliza el Chasing Ed

            else if (chasing_Ed) {
                switch(name){
                    case "INKY":
                        velX=2.25;
                        velY=2.25;
                        break;
                    case "BLINKY":
                        velX=2.5;
                        velY=2.5;
                        break;
                    case "CLYDE":
                        velX=3;
                        velY=3;
                        break;
                    case "PINKY":
                        velX=2;
                        velY=2;
                        break;
                }

                switch (facing) {
                    case "Right":
                        if(getFixedXcoordinate() == 27*30){
                            x = setFixedXcoordinate(0);
                        }
                        x += velX;
                        idleanim.tick();
                        break;
                    case "Left":
                        if(getFixedXcoordinate()==0){
                            x = setFixedXcoordinate(27*30);
                        }
                        x -= velX;
                        idleanim.tick();
                        break;
                    case "Up":
                        y -= velY;
                        idleanim.tick();
                        break;
                    case "Down":
                        y += velY;
                        idleanim.tick();
                        break;
                }
                if (turnCooldown <= 0) {
                    turnFlag = false;
                }
                if (turnFlag) {
                    turnCooldown--;
                }

                if (facing.equals("Right") || facing.equals("Left")) {
                    checkHorizontalCollision();
                } else {
                    checkVerticalCollisions();
                }
                int TpixelX = handler.getPacman().getFixedXpixel();//TARGET PIXELS
                int TpixelY = handler.getPacman().y / multip;
                switch(name){
                    case "INKY":

                        break;
                    case "BLINKY":

                        break;
                    case "CLYDE":

                        break;
                    case "PINKY":
                        TpixelX = handler.getPacman().getFixedXpixel();//TARGET PIXELS
                        TpixelY = handler.getPacman().y / multip;
                        if(Nodes(0,x / multip,y / multip)){
                            switch (handler.getPacman().facing) {
                                case "Right":
                                    if(handler.getPacman().Node(2)){
                                        int d = Distance(facing,handler.getPacman().x/multip,handler.getPacman().y/multip);
                                        TpixelX = handler.getPacman().getFixedXpixel() + d;
                                    }else {
                                        TpixelX = handler.getPacman().getFixedXpixel();//TARGET PIXELS
                                    }
                                    TpixelY = handler.getPacman().y / multip;
                                    break;
                                case "Left":
                                    if(handler.getPacman().Node(1)){
                                        int d = Distance(facing,handler.getPacman().x/multip,handler.getPacman().y/multip);
                                        TpixelX = handler.getPacman().getFixedXpixel() - d;
                                    }else {
                                        TpixelX = handler.getPacman().getFixedXpixel();//TARGET PIXELS
                                    }
                                    TpixelY = handler.getPacman().y / multip;
                                    break;
                                case "Up":
                                    if(handler.getPacman().Node(3)){
                                        int d = Distance(facing,handler.getPacman().x/multip,handler.getPacman().y/multip);
                                        TpixelY = handler.getPacman().y / multip - d;
                                    }else {
                                        TpixelY = handler.getPacman().y / multip;
                                    }
                                    break;
                                case "Down":
                                    if(handler.getPacman().Node(4)){
                                        int d = Distance(facing,handler.getPacman().x/multip,handler.getPacman().y/multip);
                                        TpixelY = handler.getPacman().y / multip + d;
                                    }else {
                                        TpixelY = handler.getPacman().y / multip;
                                    }
                                    break;
                            }}



                        break;
                }
                //int TpixelX = handler.getPacman().x / multip;//TARGET PIXELS
                //int TpixelY = handler.getPacman().y / multip;
                int PixelX = getFixedXpixel();
                int PixelY = y / multip;

                if (Node(0)&& TpixelX!=0) {
                    facing = Get_next_dir(PixelX, PixelY, TpixelX, TpixelY);
                    //find node (direction) return coordinate
                    //public void Breadth_Search( PixelX , PixelY , TpixelX , TpixelY);
                }


            }
            else if(scattered){

                if(scatteredtimer<=0){
                    scattered=false;
                    chasing=true;
                    chasingtimer=4*60;
                }else{
                    scatteredtimer--;
                }

                int gotox = 1;//coordinates the ghost will follow
                int gotoy = 1;
                switch (name) {
                    case "INKY":
                        gotox = 1;
                        gotoy = 1;
                        break;
                    case "BLINKY":
                        gotox = 22;
                        gotoy = 1;
                        break;
                    case "CLYDE":
                        gotox = 2;
                        gotoy = 35;
                        break;
                    case "PINKY":
                        gotox = 25;
                        gotoy = 35;
                        break;
                }
                int deltaX = gotox*multip-getFixedXcoordinate();
                int deltaY = -(gotoy*multip-y  );


                facingswitch(idleanim);
                //
                if (Node(0)) {// at node\
                    int TPX = 0;
                    int TPY = 0;
                    switch (name) {
                        case "INKY":
                            TPX = 1;
                            TPY = 1;
                            break;
                        case "BLINKY":
                            TPX = 26;
                            TPY = 1;
                            break;
                        case "CLYDE":
                            TPX = 1;
                            TPY = 35;
                            break;
                        case "PINKY":
                            TPX = 26;
                            TPY = 35;
                            break;
                    }
                    int PixlX =getFixedXpixel() - TPX ;
                    int PixlY =y/multip-TPY ;

                    if(deltaX>0 && deltaY>=0){
                     //   System.out.println("Cuad1");

                        double upD = Pithagoras(PixlX,PixlY-1);
                        double rtD = Pithagoras(PixlX+1,PixlY);
                        if(Node(3)&&Node(2)&& facing !="Down"&& facing !="Left") {
                            if (upD >= rtD) {
                                direcccion = 0;
                            } else direcccion = 2;
                        }else if(Node(3)&& facing !="Down"){
                            direcccion = 2;
                        }else if(Node(2)&& facing !="Left"){
                            direcccion = 0;
                        }else if(Node(1)&& facing !="Right"){
                            direcccion = 1;
                        }else if(Node(4)&& facing !="Up"){
                            direcccion = 3;
                        }
                    }else if(deltaX<=0&& deltaY>0){
                  //      System.out.println("Cuad2");
                        if(Node(3)&&Node(1)&& facing !="Down"&& facing !="Right") {
                            double upD = Pithagoras(PixlX,PixlY-1);
                            double lfD = Pithagoras(PixlX-1,PixlY);
                            if(upD>=lfD){
                                direcccion = 1;
                            }
                            else direcccion = 2;
                        }else if(Node(3)&& facing !="Down"){
                            direcccion = 2;
                        }else if(Node(1)&& facing !="Right"){
                            direcccion = 1;
                        }else if(Node(2)&& facing !="Left"){
                            direcccion = 0;
                        }else if(Node(4)&& facing !="Up"){
                            direcccion = 3;
                        }
                    }else if(deltaX<0&&deltaY<=0){
                  //      System.out.println("Cuad3");
                        if(Node(4)&&Node(1)&& facing !="Right"&& facing !="Up") {
                            double dnD = Pithagoras(PixlX,PixlY-1);
                            double lfD = Pithagoras(PixlX+1,PixlY);
                            if(dnD>=lfD){
                                direcccion = 1;
                            }else                             direcccion = 3;
                        }else if(Node(1)&& facing !="Right"){
                            direcccion = 1;
                        }else if(Node(4)&& facing !="Up"){
                            direcccion = 3;
                        }else if(Node(3)&& facing !="Down"){
                            direcccion = 2;
                        }else if(Node(2)&& facing !="Left"){
                            direcccion = 0;
                        }

                    }else if(deltaX>=0&& deltaY<0){
                     //   System.out.println("Cuad4");
                        if(Node(4)&&Node(2)&& facing !="Up"&& facing !="Right") {
                            double rtD = Pithagoras(PixlX + 1, PixlY);
                            double dnD = Pithagoras(PixlX, PixlY + 1);
                            if (rtD >= dnD) {
                                direcccion = 3;
                            } else       direcccion = 0;
                        } else if (Node(4) && facing !="Up"){
                            direcccion = 3;
                        } else if (Node(2)&& facing !="Right"){
                            direcccion = 0;
                        }else if(Node(3)&& facing !="Down"){
                            direcccion = 2;
                        }else if(Node(1)&& facing !="Left"){
                            direcccion = 1;
                        }
                    }
                    switch (direcccion) {
                        case 0:
                            if (checkPreHorizontalCollision("Right") && Node(2)) {
                                facing = "Right";
                                turnFlag = true;
                                turnCooldown = 5;
                            }
                            break;
                        case 1:
                            if (checkPreHorizontalCollision("Left") && Node(1)) {
                                facing = "Left";
                                turnFlag = true;
                                turnCooldown = 5;
                            }
                            break;

                        case 2:
                            if (checkPreVerticalCollisions("Up") && Node(3)) {
                                facing = "Up";//30 30*3 30*6 30*9 30*12 30*15 30*18 30*21 30*26
                                turnFlag = true;
                                turnCooldown = 5;
                            }
                            break;

                        case 3:
                            if (checkPreVerticalCollisions("Down") && Node(4)) {
                                facing = "Down";
                                turnFlag = true;
                                turnCooldown = 5;
                            }
                            break;
                    }
                }//at node

            }
            // Aquí los ghost se ponen azul, la cual el PacMan se los puede comer y suman 500 puntos al Score.
            else if (frightened) {//bluesprite
                if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_R)){
                    goin=true;
                }
                if (goin) {
                    handler.getScoreManager().addPacmanCurrentScore(500);
                    handler.getMusicHandler().playEffect("pacman_eatghost.wav");
                    handler.getMap().
                     handler.getPacManState().Mode="Limbo2";
                    running = false;
                    goingin = true;
                    goin=false;
                }
                if(running){
                    goingin=false;
                    switch (facing) {
                        case "Right":
                            if(getFixedXcoordinate() == 27*30){
                                x = setFixedXcoordinate(0);
                            }
                            x += 3;
                            frightanim.tick();
                            break;
                        case "Left":
                            if(getFixedXcoordinate()==0){
                                x = setFixedXcoordinate(27*30);
                            }
                            x -= 3;
                            frightanim.tick();
                            break;
                        case "Up":
                            y -= 3;
                            frightanim.tick();
                            break;
                        case "Down":
                            y += 3;
                            frightanim.tick();
                            break;
                    }

                    if (facing.equals("Right") || facing.equals("Left")) {
                        // checkHorizontalCollision();
                    } else {
                        // checkVerticalCollisions();
                    }

                    if (Node(0)) {
                        facing = getRandomDirection();
                        //Método para que cuando estan frightened usen una dirección random en la que pueden tomar.

                    }
                }else if (goingin) {//eyes sprite
                    //Aqui se le ponen los ojos solamente al ghost una vez PacMan se le come.

                    if (!centralizado) {

                        velX = 5;
                        velY = 5;


                        switch (facing) {
                            case "Right":
                                if(getFixedXcoordinate() >= 27*30){
                                    x = setFixedXcoordinate(0);
                                }
                                x += velX;
                                eatenanim.tick();
                                break;
                            case "Left":
                                if(getFixedXcoordinate()<=0){
                                    x = setFixedXcoordinate(27*30);
                                }
                                x -= velX;
                                eatenanim.tick();
                                break;
                            case "Up":
                                y -= velY;
                                eatenanim.tick();
                                break;
                            case "Down":
                                y += velY;
                                eatenanim.tick();
                                break;
                        }
                        if (turnCooldown <= 0) {
                            turnFlag = false;
                        }
                        if (turnFlag) {
                            turnCooldown--;
                        }
//Estos métodos se implementaron para que cuando los ghost colisionen con PacMan mueran y vayan al cuadro para que se reinicien.

                        if (facing.equals("Right") || facing.equals("Left")) {
                            checkHorizontalCollision();
                        } else {
                            checkVerticalCollisions();
                        }
                        int TpixelX = 14;//TARGET PIXELS
                        int TpixelY = 13;
                        int PixelX = getFixedXpixel();
                        int PixelY = y / multip;
                        //int PixlX = ((x+(handler.getWidth()/2-(28*30)/2))/multip - (handler.getPacman().x+(handler.getWidth()/2-(28*30)/2))/multip) ;
                        //int PixlY = y/multip-handler.getPacman().y/multip ;

                        if (Node(0) && TpixelX != 0) {
                            facing = Get_next_dir(PixelX, PixelY, TpixelX, TpixelY);
                        }

                        if (getFixedXpixel()>12&&getFixedXpixel()<14 && PixelY == 11) {
                            facing = "Down";
                            centralizado = true;
                        }


                    }else if (centralizado){
                        switch (name) {
                            case "INKY":
                                velX = 2.25;
                                velY = 2.25;
                                break;
                            case "BLINKY":
                                velX = 2.5;
                                velY = 2.5;
                                break;
                            case "CLYDE":
                                velX = 3;
                                velY = 3;
                                break;
                            case "PINKY":
                                velX = 2.4;
                                velY = 2.4;
                                break;
                        }

                        facingswitch(eatenanim);
                        int TpixelX = 13;//TARGET PIXELS
                        int TpixelY = 14;
                        int PixelX = getFixedXpixel();
                        int PixelY = y / multip;

                        if (Node(0) && TpixelX != 0) {
                            facing = Get_next_dir(PixelX, PixelY, TpixelX, TpixelY);
                        }

                        if (PixelX==TpixelX && PixelY == TpixelY) {
                            frightdelay =60*7;
                            centralizado=false;
                            goingin=false;
                            running=true;
                            out=false;
                            frightened=false;
                            waiting=true;
                            frighton=false;
                        }
                    }
                }
            }


        }


    }

    private void facingswitch(Animation eatenanim) {
        switch (facing) {
            case "Right":
                if (x == (xmapsize - 1) * MapBuilder.pixelMultiplier +(handler.getWidth()/2-(28*30)/2)) {
                    x = (handler.getWidth()/2-(28*30)/2);
                }
                x += velX;
                eatenanim.tick();
                break;
            case "Left":
                if (x == (handler.getWidth()/2-(28*30)/2)) {
                    x = (xmapsize - 1) * MapBuilder.pixelMultiplier+(handler.getWidth()/2-(28*30)/2);
                }
                x -= velX;
                eatenanim.tick();
                break;
            case "Up":
                y -= velY;
                eatenanim.tick();
                break;
            case "Down":
                y += velY;
                eatenanim.tick();
                break;
        }
        if (turnCooldown <= 0) {
            turnFlag = false;
        }
        if (turnFlag) {
            turnCooldown--;
        }

        if (facing.equals("Right") || facing.equals("Left")) {
            checkHorizontalCollision();
        } else {
            checkVerticalCollisions();
        }
    }

    public String getRandomDirection(){
        String facng = "Right";
        direcccion = new Random().nextInt(4);
        switch (direcccion) {
            case 0:
                if (checkPreHorizontalCollision("Right") && Node(2) && !facing.equals("Left")) {
                    facng =  "Right";
                }else{
                    return getRandomDirection();
                }
                break;
            case 1:
                if (checkPreHorizontalCollision("Left") && Node(1)&& !facing.equals("Right")) {
                    facng= "Left";
                }else{
                    return getRandomDirection();
                }
                break;

            case 2:
                if (checkPreVerticalCollisions("Up") && Node(3)&& !facing.equals("Down")) {
                    facng=  "Up";//30 30*3 30*6 30*9 30*12 30*15 30*18 30*21 30*26

                }else{
                    return getRandomDirection();
                }
                break;

            case 3:
                if (checkPreVerticalCollisions("Down") && Node(4)&& !facing.equals("Up")) {
                    facng = "Down";
                }else{
                    return getRandomDirection();
                }
                break;
        }
        return facng;

    }

    public double Pithagoras(int a, int b){
        double c = Math.sqrt(  Math.pow((double)a, 2)  +  Math.pow((double)b, 2)  );
        return c;
    }

    public void checkVerticalCollisions() {
        Ghost ghost = this;
        ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
        ArrayList<BaseDynamic> enemies = handler.getMap().getEnemiesOnMap();

        boolean ghostDies = false;
        boolean toUp = moving && facing.equals("Up");

        Rectangle ghostBounds = toUp ? ghost.getTopBounds() : ghost.getBottomBounds();

        velY = speed;
        for (BaseStatic brick : bricks) {
            if (brick instanceof BoundBlock) {
                Rectangle brickBounds = !toUp ? brick.getTopBounds() : brick.getBottomBounds();
                if (ghostBounds.intersects(brickBounds)) {
                    velY = 0;
                    if (toUp)
                        ghost.setY(brick.getY() + ghost.getDimension().height);
                    else
                        ghost.setY(brick.getY() - brick.getDimension().height);
                }
            }
        }

        for(BaseDynamic enemy : enemies){
            if(enemy instanceof PacMan){
                Rectangle enemyBounds = !toUp ? enemy.getTopBounds() : enemy.getBottomBounds();
                if (ghostBounds.intersects(enemyBounds)&&!frightened) {
                    ghostDies = true;
                    break;
                }else if (ghostBounds.intersects(enemyBounds)&&frightened){
                    if(running)     {
                        goin=true;
                    }
                    break;
                }
            }}

        if(ghostDies) {
            handler.getMap().reset();
            handler.getPacman().pacmandead=true;
        }
    }

    public boolean checkPreVerticalCollisions(String facing) {
        Ghost ghost = this;
        ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();

        boolean ghostDies = false;
        boolean toUp = moving && facing.equals("Up");

        Rectangle ghostBounds = toUp ? ghost.getTopBounds() : ghost.getBottomBounds();

        velY = speed;
        for (BaseStatic brick : bricks) {
            if (brick instanceof BoundBlock) {
                Rectangle brickBounds = !toUp ? brick.getTopBounds() : brick.getBottomBounds();
                if (ghostBounds.intersects(brickBounds)) {
                    return false;
                }
            }
        }
        return true;

    }

    public void checkHorizontalCollision(){
        Ghost ghost = this;
        ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
        ArrayList<BaseDynamic> enemies = handler.getMap().getEnemiesOnMap();
        velX = speed;
        boolean ghostDies = false;
        boolean toRight = moving && facing.equals("Right");

        Rectangle ghostBounds = toRight ? ghost.getRightBounds() : ghost.getLeftBounds();

        for(BaseDynamic enemy : enemies){
            if(enemy instanceof PacMan){
                Rectangle enemyBounds = !toRight ? enemy.getRightBounds() : enemy.getLeftBounds();
                if (ghostBounds.intersects(enemyBounds)&&!frightened) {
                    ghostDies = true;
                    break;
                }else if (ghostBounds.intersects(enemyBounds)&&frightened){
                    if(running)     {
                        goin=true;
                    }
                    break;
                }
            }}

        if(ghostDies) {
            handler.getMap().reset();
            handler.getPacman().pacmandead=true;
        }else {

            for (BaseStatic brick : bricks) {
                if (brick instanceof BoundBlock) {
                    Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
                    if (ghostBounds.intersects(brickBounds)) {
                        velX = 0;
                        if (toRight)
                            ghost.setX(brick.getX() - ghost.getDimension().width);
                        else
                            ghost.setX(brick.getX() + brick.getDimension().width);
                    }
                }
            }
        }
    }


    public boolean checkPreHorizontalCollision(String facing){
        Ghost ghost = this;
        ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
        velX = speed;
        boolean toRight = moving && facing.equals("Right");

        Rectangle ghostBounds = toRight ? ghost.getRightBounds() : ghost.getLeftBounds();

        for (BaseStatic brick : bricks) {
            if (brick instanceof BoundBlock) {
                Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
                if (ghostBounds.intersects(brickBounds)) {
                    return false;
                }
            }
        }
        return true;

    }


    ///Testing for node path find

    //remove his thing
    public void Breadth_Search_(int pixelX,int pixelY,int tpixelX,int tpixelY){
        boolean[][] visited = new boolean[ymapsize][xmapsize];//2D array

        for (int j = 0; j <ymapsize; j++) {//sets them all fasle
            for (int i = 0; i <visited[0].length ; i++) {
                visited[j][i]=false;
            }
        }
        visited[pixelY][pixelX]=true;

        if(pixelX==tpixelX && pixelY==tpixelY){

        }

    }


    public String Get_next_dir(int pixelX,int pixelY,int tpixelX,int tpixelY){

            /*for (int j = 0; j <ymapsize; j++) {
                for (int i = 0; i <xmapsize ; i++) {
                    int currentPixel = Images.map2.getRGB(i, j);
                    if(currentPixel != MapBuilder.boundBlock || currentPixel != MapBuilder.base ) nodos[i][j] = true;
                }
            }*/
        for (int j = 0; j <ymapsize; j++) {//set all paths nodes to true
            for (int i = 0; i <Visited[0].length ; i++) {
                int currenPixel = Images.map2.getRGB(i, j);
                nodos[j][i] = (currenPixel != MapBuilder.boundBlock) && (currenPixel != MapBuilder.base);
            }
        }
        for (int j = 0; j <ymapsize; j++) {//set all visited pixels to false
            for (int i = 0; i <Visited[0].length ; i++) {
                Visited[j][i]=false;
            }
        }
        for (int j = 0; j <ymapsize; j++) {//set all disntances to infinite
            for (int i = 0; i <Queue[0].length ; i++) {
                Queue[j][i][0]=99999;
            }
        }
        Queue[pixelY][pixelX][0]=0;//sets initial point 0 distance
        checker(pixelX,pixelY,tpixelX,tpixelY);
        BacktrackX(tpixelX,tpixelY,pixelX,pixelY);
        String dir = "Left";
        if(prevX>pixelX){
            dir = "Right";
        }else if (prevX<pixelX){
            dir = "Left";
        }if (prevY>pixelY){
            dir = "Down";
        }else if (prevY<pixelY){
            dir = "Up";
        }
        return dir;
    }


    public void checker(int pixelX,int pixelY,int tpixelX,int tpixelY){
        //int[] edwin ;

        //Queue[pixelX][pixelY][1]=69420;//sets initial node a magic number to determine it is the initial point
        //Queue[pixelX][pixelY][0]=69420;//sets initial node a magic number to determine it is the initial point
        if(pixelX>1){
            if(nodos[pixelY][pixelX-1] /*&& !Visited[pixelY][pixelX-1]*/) {//left
                if(1+Queue[pixelY][pixelX][0]<Queue[pixelY][pixelX-1][0]) {
                    Queue[pixelY][pixelX-1][0] = 1 + Queue[pixelY][pixelX][0];
                    Queue[pixelY][pixelX-1][1] = pixelX;
                    Queue[pixelY][pixelX-1][2] = pixelY;
                    Visited[pixelY][pixelX]=true;
                    checker(pixelX-1,pixelY,tpixelX,tpixelY);
                }if(pixelX-1==tpixelX && pixelY==tpixelY){
                    //edwin = BacktrackX(tpixelX,tpixelY,x/multip,y/multip);
                    //return edwin;
                }
            }}
        if(pixelX<xmapsize-1){//right
            if(nodos[pixelY][pixelX+1] /*&& !Visited[pixelY][pixelX+1]*/) {
                if(1+Queue[pixelY][pixelX][0]<Queue[pixelY][pixelX+1][0]) {
                    Queue[pixelY][pixelX+1][0] = 1 + Queue[pixelY][pixelX][0];
                    Queue[pixelY][pixelX+1][1] = pixelX;
                    Queue[pixelY][pixelX+1][2] = pixelY;
                    Visited[pixelY][pixelX]=true;
                    checker(pixelX+1,pixelY,tpixelX,tpixelY);
                }if(pixelX+1==tpixelX && pixelY==tpixelY){
                    //edwin = BacktrackX(tpixelX,tpixelY,x/multip,y/multip);
                    //return edwin;
                }
            }}
        if(pixelY>1){//up
            if(nodos[pixelY-1][pixelX] /*&& !Visited[pixelY-1][pixelX]*/) {
                if(1+Queue[pixelY][pixelX][0]<Queue[pixelY-1][pixelX][0]) {
                    Queue[pixelY-1][pixelX][0] = 1 + Queue[pixelY][pixelX][0];
                    Queue[pixelY-1][pixelX][1] = pixelX;
                    Queue[pixelY-1][pixelX][2] = pixelY;
                    Visited[pixelY][pixelX]=true;
                    checker(pixelX,pixelY-1,tpixelX,tpixelY);
                }if(pixelX==tpixelX && pixelY-1==tpixelY){

                    //edwin = BacktrackX(tpixelX,tpixelY,x/multip,y/multip);
                    //return edwin;
                }
            }}
        if(pixelY<30 && nodos[pixelY][pixelX]){//dn
            if(nodos[pixelY+1][pixelX] /*&& !Visited[pixelY+1][pixelX]*/) {
                if(1+Queue[pixelY][pixelX][0]<Queue[pixelY+1][pixelX][0]) {
                    Queue[pixelY+1][pixelX][0] = 1 + Queue[pixelY][pixelX][0];
                    Queue[pixelY+1][pixelX][1] = pixelX;
                    Queue[pixelY+1][pixelX][2] = pixelY;
                    Visited[pixelY][pixelX]=true;
                    checker(pixelX,pixelY+1,tpixelX,tpixelY);
                }if(pixelX==tpixelX && pixelY+1==tpixelY){
                    //edwin = BacktrackX(tpixelX,tpixelY,x/multip,y/multip);
                    //return edwin;
                }
            }}
        //Visited[pixelY][pixelX] = true;
        //return null;
    }

    //go back through the shortest path and find the pixel of the nearest nodes
    public void BacktrackX(int pixlX,int pixlY,int startX,int startY){
        int[] prevcoor = new int[2];
        //int[] coor = new int[2];
        prevcoor[0] = Queue[pixlY][pixlX][1];
        prevcoor[1] = Queue[pixlY][pixlX][2];
        if(prevcoor[0]==startX && prevcoor[1]==startY) {
            prevX=pixlX;
            prevY=pixlY;
            //return coor;
        }else{
            BacktrackX(prevcoor[0],prevcoor[1],startX,startY);
            //break;
        }
        //return coor;
    }

    //find the distance of the next turning pont (Node)
    public int Distance(String direction,int InitialpixelX,int InitialpixelY){//assuming you know that there is a node there
        int dis=0;
        switch (direction){
            case "Right":
                while(!Nodes(0,InitialpixelX+1, InitialpixelY+0) && InitialpixelX+1<xmapsize-1 ){
                    InitialpixelX++;
                    dis ++;
                }
                break;
            case "Left":
                while(!Nodes(0,InitialpixelX-1, InitialpixelY+0) && InitialpixelX-1>1 ){
                    InitialpixelX--;
                    dis ++;
                }
                break;
            case "Up":
                while(!Nodes(0,InitialpixelX+0, InitialpixelY-1) &&InitialpixelY-1>1){
                    InitialpixelY--;
                    dis ++;
                }
                break;
            case "Down":
                while(!Nodes(0,InitialpixelX+0, InitialpixelY+1) && InitialpixelY+1<28 ){
                    InitialpixelY++;
                    dis ++;
                }
                break;
        }
        return dis;
    }




    ////END






    public boolean Node(int function){
        //Z-axis(function) 0 = position ,1 = TURNLEFT , 2 = TURNRIGHT, 3 = TURNUP, 4 = TURNDOWN FOR WHAT?
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][0]=false;
            }
        }
        //POSITIONS
        nodes[1][1][0]=true;
        nodes[1][6][0]=true;
        nodes[1][12][0]=true;
        nodes[1][15][0]=true;
        nodes[1][21][0]=true;
        nodes[1][26][0]=true;
        nodes[5][1][0]=true;
        nodes[5][6][0]=true;
        nodes[5][9][0]=true;
        nodes[5][12][0]=true;
        nodes[5][15][0]=true;
        nodes[5][18][0]=true;
        nodes[5][21][0]=true;
        nodes[5][26][0]=true;
        nodes[8][1][0]=true;
        nodes[8][6][0]=true;
        nodes[8][9][0]=true;
        nodes[8][12][0]=true;
        nodes[8][15][0]=true;
        nodes[8][18][0]=true;
        nodes[8][21][0]=true;
        nodes[8][26][0]=true;
        nodes[11][9][0]=true;
        nodes[11][12][0]=true;
        nodes[11][15][0]=true;
        nodes[11][18][0]=true;
        nodes[14][6][0]=true;
        nodes[14][9][0]=true;
        nodes[14][18][0]=true;
        nodes[14][21][0]=true;
        nodes[17][9][0]=true;
        nodes[17][18][0]=true;
        nodes[20][1][0]=true;
        nodes[20][6][0]=true;
        nodes[20][9][0]=true;
        nodes[20][12][0]=true;
        nodes[20][15][0]=true;
        nodes[20][18][0]=true;
        nodes[20][21][0]=true;
        nodes[20][26][0]=true;
        nodes[23][1][0]=true;
        nodes[23][3][0]=true;
        nodes[23][6][0]=true;
        nodes[23][9][0]=true;
        nodes[23][12][0]=true;
        nodes[23][15][0]=true;
        nodes[23][18][0]=true;
        nodes[23][21][0]=true;
        nodes[23][24][0]=true;
        nodes[23][26][0]=true;
        nodes[26][1][0]=true;
        nodes[26][3][0]=true;
        nodes[26][6][0]=true;
        nodes[26][9][0]=true;
        nodes[26][12][0]=true;
        nodes[26][15][0]=true;
        nodes[26][18][0]=true;
        nodes[26][21][0]=true;
        nodes[26][24][0]=true;
        nodes[26][26][0]=true;
        nodes[29][26][0]=true;
        nodes[29][15][0]=true;
        nodes[29][12][0]=true;
        nodes[29][1][0]=true;
        //TURNLEFT
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][1]=false;
            }
        }
        //nodes[1][1][1]=true;
        nodes[1][6][1]=true;
        nodes[1][12][1]=true;
        //nodes[1][15][1]=true;
        nodes[1][21][1]=true;
        nodes[1][26][1]=true;
        //nodes[5][1][1]=true;
        nodes[5][6][1]=true;
        nodes[5][9][1]=true;
        nodes[5][12][1]=true;
        nodes[5][15][1]=true;
        nodes[5][18][1]=true;
        nodes[5][21][1]=true;
        nodes[5][26][1]=true;
        //nodes[8][1][1]=true;
        nodes[8][6][1]=true;
        //nodes[8][9][1]=true;
        nodes[8][12][1]=true;
        //nodes[8][15][1]=true;
        nodes[8][18][1]=true;
        //nodes[8][21][1]=true;
        nodes[8][26][1]=true;
        //nodes[11][9][1]=true;
        nodes[11][12][1]=true;
        nodes[11][15][1]=true;
        nodes[11][18][1]=true;
        nodes[14][6][1]=true;
        nodes[14][9][1]=true;
        //nodes[14][18][1]=true;
        nodes[14][21][1]=true;
        //nodes[17][9][1]=true;
        nodes[17][18][1]=true;
        //nodes[20][1][1]=true;
        nodes[20][6][1]=true;
        nodes[20][9][1]=true;
        nodes[20][12][1]=true;
        //nodes[20][15][1]=true;
        nodes[20][18][1]=true;
        nodes[20][21][1]=true;
        nodes[20][26][1]=true;
        //nodes[23][1][1]=true;
        nodes[23][3][1]=true;
        //nodes[23][6][1]=true;
        nodes[23][9][1]=true;
        nodes[23][12][1]=true;
        nodes[23][15][1]=true;
        nodes[23][18][1]=true;
        nodes[23][21][1]=true;
        //nodes[23][24][1]=true;
        nodes[23][26][1]=true;
        //nodes[26][1][1]=true;
        nodes[26][3][1]=true;
        nodes[26][6][1]=true;
        //nodes[26][9][1]=true;
        nodes[26][12][1]=true;
        //nodes[26][15][1]=true;
        nodes[26][18][1]=true;
        //nodes[26][21][1]=true;
        nodes[26][24][1]=true;
        nodes[26][26][1]=true;
        nodes[29][26][1]=true;
        nodes[29][15][1]=true;
        nodes[29][12][1]=true;
        //nodes[29][1][1]=true;

        //TURN RIGHT
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][2]=false;
            }
        }
        nodes[1][1][2]=true;
        nodes[1][6][2]=true;
        //nodes[1][12][2]=true;
        nodes[1][15][2]=true;
        nodes[1][21][2]=true;
        //nodes[1][26][2]=true;
        nodes[5][1][2]=true;
        nodes[5][6][2]=true;
        nodes[5][9][2]=true;
        nodes[5][12][2]=true;
        nodes[5][15][2]=true;
        nodes[5][18][2]=true;
        nodes[5][21][2]=true;
        //nodes[5][26][2]=true;
        nodes[8][1][2]=true;
        //nodes[8][6][2]=true;
        nodes[8][9][2]=true;
        //nodes[8][12][2]=true;
        nodes[8][15][2]=true;
        //nodes[8][18][2]=true;
        nodes[8][21][2]=true;
        //nodes[8][26][2]=true;
        nodes[11][9][2]=true;
        nodes[11][12][2]=true;
        nodes[11][15][2]=true;
        //nodes[11][18][2]=true;
        nodes[14][6][2]=true;
        //nodes[14][9][2]=true;
        nodes[14][18][2]=true;
        nodes[14][21][2]=true;
        nodes[17][9][2]=true;
        //nodes[17][18][2]=true;
        nodes[20][1][2]=true;
        nodes[20][6][2]=true;
        nodes[20][9][2]=true;
        //nodes[20][12][2]=true;
        nodes[20][15][2]=true;
        nodes[20][18][2]=true;
        nodes[20][21][2]=true;
        //nodes[20][26][2]=true;
        nodes[23][1][2]=true;
        //nodes[23][3][2]=true;
        nodes[23][6][2]=true;
        nodes[23][9][2]=true;
        nodes[23][12][2]=true;
        nodes[23][15][2]=true;
        nodes[23][18][2]=true;
        //nodes[23][21][2]=true;
        nodes[23][24][2]=true;
        //nodes[23][26][2]=true;
        nodes[26][1][2]=true;
        nodes[26][3][2]=true;
        //nodes[26][6][2]=true;
        nodes[26][9][2]=true;
        //nodes[26][12][2]=true;
        nodes[26][15][2]=true;
        //nodes[26][18][2]=true;
        nodes[26][21][2]=true;
        nodes[26][24][2]=true;
        //nodes[26][26][2]=true;
        //nodes[29][26][2]=true;
        nodes[29][15][2]=true;
        nodes[29][12][2]=true;
        nodes[29][1][2]=true;


        //TURNUP
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][3]=false;
            }
        }
        //nodes[1][1][3]=true;
        //nodes[1][6][3]=true;
        //nodes[1][12][3]=true;
        //nodes[1][15][3]=true;
        //nodes[1][21][3]=true;
        //nodes[1][26][3]=true;
        nodes[5][1][3]=true;
        nodes[5][6][3]=true;
        //nodes[5][9][3]=true;
        nodes[5][12][3]=true;
        nodes[5][15][3]=true;
        //nodes[5][18][3]=true;
        nodes[5][21][3]=true;
        nodes[5][26][3]=true;
        nodes[8][1][3]=true;
        nodes[8][6][3]=true;
        nodes[8][9][3]=true;
        //nodes[8][12][3]=true;
        //nodes[8][15][3]=true;
        nodes[8][18][3]=true;
        nodes[8][21][3]=true;
        nodes[8][26][3]=true;
        //nodes[11][9][3]=true;
        nodes[11][12][3]=true;
        nodes[11][15][3]=true;
        //nodes[11][18][3]=true;
        nodes[14][6][3]=true;
        nodes[14][9][3]=true;
        nodes[14][18][3]=true;
        nodes[14][21][3]=true;
        nodes[17][9][3]=true;
        nodes[17][18][3]=true;
        //nodes[20][1][3]=true;
        nodes[20][6][3]=true;
        nodes[20][9][3]=true;
        //nodes[20][12][3]=true;
        //nodes[20][15][3]=true;
        nodes[20][18][3]=true;
        nodes[20][21][3]=true;
        //nodes[20][26][3]=true;
        nodes[23][1][3]=true;
        //nodes[23][3][3]=true;
        nodes[23][6][3]=true;
        //nodes[23][9][3]=true;
        nodes[23][12][3]=true;
        nodes[23][15][3]=true;
        //nodes[23][18][3]=true;
        nodes[23][21][3]=true;
        //nodes[23][24][3]=true;
        nodes[23][26][3]=true;
        //nodes[26][1][3]=true;
        nodes[26][3][3]=true;
        nodes[26][6][3]=true;
        nodes[26][9][3]=true;
        //nodes[26][12][3]=true;
        //nodes[26][15][3]=true;
        nodes[26][18][3]=true;
        nodes[26][21][3]=true;
        nodes[26][24][3]=true;
        //nodes[26][26][3]=true;
        nodes[29][26][3]=true;
        nodes[29][15][3]=true;
        nodes[29][12][3]=true;
        nodes[29][1][3]=true;

        //TURNDOWN
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][4]=false;
            }
        }
        nodes[1][1][4]=true;
        nodes[1][6][4]=true;
        nodes[1][12][4]=true;
        nodes[1][15][4]=true;
        nodes[1][21][4]=true;
        nodes[1][26][4]=true;
        nodes[5][1][4]=true;
        nodes[5][6][4]=true;
        nodes[5][9][4]=true;
        //nodes[5][12][4]=true;
        //nodes[5][15][4]=true;
        nodes[5][18][4]=true;
        nodes[5][21][4]=true;
        nodes[5][26][4]=true;
        //nodes[8][1][4]=true;
        nodes[8][6][4]=true;
        //nodes[8][9][4]=true;
        nodes[8][12][4]=true;
        nodes[8][15][4]=true;
        //nodes[8][18][4]=true;
        nodes[8][21][4]=true;
        //nodes[8][26][4]=true;
        nodes[11][9][4]=true;
        //nodes[11][12][4]=true;
        //nodes[11][15][4]=true;
        nodes[11][18][4]=true;
        nodes[14][6][4]=true;
        nodes[14][9][4]=true;
        nodes[14][18][4]=true;
        nodes[14][21][4]=true;
        nodes[17][9][4]=true;
        nodes[17][18][4]=true;
        nodes[20][1][4]=true;
        nodes[20][6][4]=true;
        //nodes[20][9][4]=true;
        nodes[20][12][4]=true;
        nodes[20][15][4]=true;
        //nodes[20][18][4]=true;
        nodes[20][21][4]=true;
        nodes[20][26][4]=true;
        //[23][1][4]=true;
        nodes[23][3][4]=true;
        nodes[23][6][4]=true;
        nodes[23][9][4]=true;
        //nodes[23][12][4]=true;
        //nodes[23][15][4]=true;
        nodes[23][18][4]=true;
        nodes[23][21][4]=true;
        nodes[23][24][4]=true;
        //nodes[23][26][4]=true;
        nodes[26][1][4]=true;
        //nodes[26][3][4]=true;
        //nodes[26][6][4]=true;
        //nodes[26][9][4]=true;
        nodes[26][12][4]=true;
        nodes[26][15][4]=true;
        //nodes[26][18][4]=true;
        //nodes[26][21][4]=true;
        //nodes[26][24][4]=true;
        nodes[26][26][4]=true;
        //nodes[29][26][4]=true;
        //nodes[29][15][4]=true;
        //nodes[29][12][4]=true;
        //nodes[29][1][4]=true;


        if(getFixedXcoordinate()%multip==0&&y%multip==0) {
            int pixelX = (x-(handler.getWidth()/2-(28*30)/2))/MapBuilder.pixelMultiplier;
            int pixelY = y/MapBuilder.pixelMultiplier;
            return nodes[pixelY][pixelX][function];
        }else {
            return false;
        }
    }
    public boolean Nodes(int function,int XPX,int YPX){
        //Z-axis(function) 0 = position ,1 = TURNLEFT , 2 = TURNRIGHT, 3 = TURNUP, 4 = TURNDOWN FOR WHAT?
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][0]=false;
            }
        }
        //POSITIONS
        nodes[1][1][0]=true;
        nodes[1][6][0]=true;
        nodes[1][12][0]=true;
        nodes[1][15][0]=true;
        nodes[1][21][0]=true;
        nodes[1][26][0]=true;
        nodes[5][1][0]=true;
        nodes[5][6][0]=true;
        nodes[5][9][0]=true;
        nodes[5][12][0]=true;
        nodes[5][15][0]=true;
        nodes[5][18][0]=true;
        nodes[5][21][0]=true;
        nodes[5][26][0]=true;
        nodes[8][1][0]=true;
        nodes[8][6][0]=true;
        nodes[8][9][0]=true;
        nodes[8][12][0]=true;
        nodes[8][15][0]=true;
        nodes[8][18][0]=true;
        nodes[8][21][0]=true;
        nodes[8][26][0]=true;
        nodes[11][9][0]=true;
        nodes[11][12][0]=true;
        nodes[11][15][0]=true;
        nodes[11][18][0]=true;
        nodes[14][6][0]=true;
        nodes[14][9][0]=true;
        nodes[14][18][0]=true;
        nodes[14][21][0]=true;
        nodes[17][9][0]=true;
        nodes[17][18][0]=true;
        nodes[20][1][0]=true;
        nodes[20][6][0]=true;
        nodes[20][9][0]=true;
        nodes[20][12][0]=true;
        nodes[20][15][0]=true;
        nodes[20][18][0]=true;
        nodes[20][21][0]=true;
        nodes[20][26][0]=true;
        nodes[23][1][0]=true;
        nodes[23][3][0]=true;
        nodes[23][6][0]=true;
        nodes[23][9][0]=true;
        nodes[23][12][0]=true;
        nodes[23][15][0]=true;
        nodes[23][18][0]=true;
        nodes[23][21][0]=true;
        nodes[23][24][0]=true;
        nodes[23][26][0]=true;
        nodes[26][1][0]=true;
        nodes[26][3][0]=true;
        nodes[26][6][0]=true;
        nodes[26][9][0]=true;
        nodes[26][12][0]=true;
        nodes[26][15][0]=true;
        nodes[26][18][0]=true;
        nodes[26][21][0]=true;
        nodes[26][24][0]=true;
        nodes[26][26][0]=true;
        nodes[29][26][0]=true;
        nodes[29][15][0]=true;
        nodes[29][12][0]=true;
        nodes[29][1][0]=true;
        //TURNLEFT
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][1]=false;
            }
        }
        //nodes[1][1][1]=true;
        nodes[1][6][1]=true;
        nodes[1][12][1]=true;
        //nodes[1][15][1]=true;
        nodes[1][21][1]=true;
        nodes[1][26][1]=true;
        //nodes[5][1][1]=true;
        nodes[5][6][1]=true;
        nodes[5][9][1]=true;
        nodes[5][12][1]=true;
        nodes[5][15][1]=true;
        nodes[5][18][1]=true;
        nodes[5][21][1]=true;
        nodes[5][26][1]=true;
        //nodes[8][1][1]=true;
        nodes[8][6][1]=true;
        //nodes[8][9][1]=true;
        nodes[8][12][1]=true;
        //nodes[8][15][1]=true;
        nodes[8][18][1]=true;
        //nodes[8][21][1]=true;
        nodes[8][26][1]=true;
        //nodes[11][9][1]=true;
        nodes[11][12][1]=true;
        nodes[11][15][1]=true;
        nodes[11][18][1]=true;
        nodes[14][6][1]=true;
        nodes[14][9][1]=true;
        //nodes[14][18][1]=true;
        nodes[14][21][1]=true;
        //nodes[17][9][1]=true;
        nodes[17][18][1]=true;
        //nodes[20][1][1]=true;
        nodes[20][6][1]=true;
        nodes[20][9][1]=true;
        nodes[20][12][1]=true;
        //nodes[20][15][1]=true;
        nodes[20][18][1]=true;
        nodes[20][21][1]=true;
        nodes[20][26][1]=true;
        //nodes[23][1][1]=true;
        nodes[23][3][1]=true;
        //nodes[23][6][1]=true;
        nodes[23][9][1]=true;
        nodes[23][12][1]=true;
        nodes[23][15][1]=true;
        nodes[23][18][1]=true;
        nodes[23][21][1]=true;
        //nodes[23][24][1]=true;
        nodes[23][26][1]=true;
        //nodes[26][1][1]=true;
        nodes[26][3][1]=true;
        nodes[26][6][1]=true;
        //nodes[26][9][1]=true;
        nodes[26][12][1]=true;
        //nodes[26][15][1]=true;
        nodes[26][18][1]=true;
        //nodes[26][21][1]=true;
        nodes[26][24][1]=true;
        nodes[26][26][1]=true;
        nodes[29][26][1]=true;
        nodes[29][15][1]=true;
        nodes[29][12][1]=true;
        //nodes[29][1][1]=true;

        //TURN RIGHT
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][2]=false;
            }
        }
        nodes[1][1][2]=true;
        nodes[1][6][2]=true;
        //nodes[1][12][2]=true;
        nodes[1][15][2]=true;
        nodes[1][21][2]=true;
        //nodes[1][26][2]=true;
        nodes[5][1][2]=true;
        nodes[5][6][2]=true;
        nodes[5][9][2]=true;
        nodes[5][12][2]=true;
        nodes[5][15][2]=true;
        nodes[5][18][2]=true;
        nodes[5][21][2]=true;
        //nodes[5][26][2]=true;
        nodes[8][1][2]=true;
        //nodes[8][6][2]=true;
        nodes[8][9][2]=true;
        //nodes[8][12][2]=true;
        nodes[8][15][2]=true;
        //nodes[8][18][2]=true;
        nodes[8][21][2]=true;
        //nodes[8][26][2]=true;
        nodes[11][9][2]=true;
        nodes[11][12][2]=true;
        nodes[11][15][2]=true;
        //nodes[11][18][2]=true;
        nodes[14][6][2]=true;
        //nodes[14][9][2]=true;
        nodes[14][18][2]=true;
        nodes[14][21][2]=true;
        nodes[17][9][2]=true;
        //nodes[17][18][2]=true;
        nodes[20][1][2]=true;
        nodes[20][6][2]=true;
        nodes[20][9][2]=true;
        //nodes[20][12][2]=true;
        nodes[20][15][2]=true;
        nodes[20][18][2]=true;
        nodes[20][21][2]=true;
        //nodes[20][26][2]=true;
        nodes[23][1][2]=true;
        //nodes[23][3][2]=true;
        nodes[23][6][2]=true;
        nodes[23][9][2]=true;
        nodes[23][12][2]=true;
        nodes[23][15][2]=true;
        nodes[23][18][2]=true;
        //nodes[23][21][2]=true;
        nodes[23][24][2]=true;
        //nodes[23][26][2]=true;
        nodes[26][1][2]=true;
        nodes[26][3][2]=true;
        //nodes[26][6][2]=true;
        nodes[26][9][2]=true;
        //nodes[26][12][2]=true;
        nodes[26][15][2]=true;
        //nodes[26][18][2]=true;
        nodes[26][21][2]=true;
        nodes[26][24][2]=true;
        //nodes[26][26][2]=true;
        //nodes[29][26][2]=true;
        nodes[29][15][2]=true;
        nodes[29][12][2]=true;
        nodes[29][1][2]=true;


        //TURNUP
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][3]=false;
            }
        }
        //nodes[1][1][3]=true;
        //nodes[1][6][3]=true;
        //nodes[1][12][3]=true;
        //nodes[1][15][3]=true;
        //nodes[1][21][3]=true;
        //nodes[1][26][3]=true;
        nodes[5][1][3]=true;
        nodes[5][6][3]=true;
        //nodes[5][9][3]=true;
        nodes[5][12][3]=true;
        nodes[5][15][3]=true;
        //nodes[5][18][3]=true;
        nodes[5][21][3]=true;
        nodes[5][26][3]=true;
        nodes[8][1][3]=true;
        nodes[8][6][3]=true;
        nodes[8][9][3]=true;
        //nodes[8][12][3]=true;
        //nodes[8][15][3]=true;
        nodes[8][18][3]=true;
        nodes[8][21][3]=true;
        nodes[8][26][3]=true;
        //nodes[11][9][3]=true;
        nodes[11][12][3]=true;
        nodes[11][15][3]=true;
        //nodes[11][18][3]=true;
        nodes[14][6][3]=true;
        nodes[14][9][3]=true;
        nodes[14][18][3]=true;
        nodes[14][21][3]=true;
        nodes[17][9][3]=true;
        nodes[17][18][3]=true;
        //nodes[20][1][3]=true;
        nodes[20][6][3]=true;
        nodes[20][9][3]=true;
        //nodes[20][12][3]=true;
        //nodes[20][15][3]=true;
        nodes[20][18][3]=true;
        nodes[20][21][3]=true;
        //nodes[20][26][3]=true;
        nodes[23][1][3]=true;
        //nodes[23][3][3]=true;
        nodes[23][6][3]=true;
        //nodes[23][9][3]=true;
        nodes[23][12][3]=true;
        nodes[23][15][3]=true;
        //nodes[23][18][3]=true;
        nodes[23][21][3]=true;
        //nodes[23][24][3]=true;
        nodes[23][26][3]=true;
        //nodes[26][1][3]=true;
        nodes[26][3][3]=true;
        nodes[26][6][3]=true;
        nodes[26][9][3]=true;
        //nodes[26][12][3]=true;
        //nodes[26][15][3]=true;
        nodes[26][18][3]=true;
        nodes[26][21][3]=true;
        nodes[26][24][3]=true;
        //nodes[26][26][3]=true;
        nodes[29][26][3]=true;
        nodes[29][15][3]=true;
        nodes[29][12][3]=true;
        nodes[29][1][3]=true;

        //TURNDOWN
        for (int j = 0; j <ymapsize; j++) {
            for (int i = 0; i <nodes[0].length ; i++) {
                nodes[j][i][4]=false;
            }
        }
        nodes[1][1][4]=true;
        nodes[1][6][4]=true;
        nodes[1][12][4]=true;
        nodes[1][15][4]=true;
        nodes[1][21][4]=true;
        nodes[1][26][4]=true;
        nodes[5][1][4]=true;
        nodes[5][6][4]=true;
        nodes[5][9][4]=true;
        //nodes[5][12][4]=true;
        //nodes[5][15][4]=true;
        nodes[5][18][4]=true;
        nodes[5][21][4]=true;
        nodes[5][26][4]=true;
        //nodes[8][1][4]=true;
        nodes[8][6][4]=true;
        //nodes[8][9][4]=true;
        nodes[8][12][4]=true;
        nodes[8][15][4]=true;
        //nodes[8][18][4]=true;
        nodes[8][21][4]=true;
        //nodes[8][26][4]=true;
        nodes[11][9][4]=true;
        //nodes[11][12][4]=true;
        //nodes[11][15][4]=true;
        nodes[11][18][4]=true;
        nodes[14][6][4]=true;
        nodes[14][9][4]=true;
        nodes[14][18][4]=true;
        nodes[14][21][4]=true;
        nodes[17][9][4]=true;
        nodes[17][18][4]=true;
        nodes[20][1][4]=true;
        nodes[20][6][4]=true;
        //nodes[20][9][4]=true;
        nodes[20][12][4]=true;
        nodes[20][15][4]=true;
        //nodes[20][18][4]=true;
        nodes[20][21][4]=true;
        nodes[20][26][4]=true;
        //[23][1][4]=true;
        nodes[23][3][4]=true;
        nodes[23][6][4]=true;
        nodes[23][9][4]=true;
        //nodes[23][12][4]=true;
        //nodes[23][15][4]=true;
        nodes[23][18][4]=true;
        nodes[23][21][4]=true;
        nodes[23][24][4]=true;
        //nodes[23][26][4]=true;
        nodes[26][1][4]=true;
        //nodes[26][3][4]=true;
        //nodes[26][6][4]=true;
        //nodes[26][9][4]=true;
        nodes[26][12][4]=true;
        nodes[26][15][4]=true;
        //nodes[26][18][4]=true;
        //nodes[26][21][4]=true;
        //nodes[26][24][4]=true;
        nodes[26][26][4]=true;
        //nodes[29][26][4]=true;
        //nodes[29][15][4]=true;
        //nodes[29][12][4]=true;
        //nodes[29][1][4]=true;


        if(getFixedXcoordinate()%multip==0&&y%multip==0) {
            return nodes[YPX][XPX][function];
        }else {
            return false;
        }
    }

    public double getVelX() {
        return velX;
    }
    public double getVelY() {
        return velY;
    }

    public int getFixedXcoordinate(){
        return (-handler.getWidth()/2)+x+420;
    }
    public int getFixedXpixel(){
        return (-handler.getWidth()/60)+(x/30)+14;
    }
    public int setFixedXcoordinate(int desiredX){
        return (desiredX + (handler.getWidth()/2)-(28*15));//notverified
    }
    public int setFixedXpixel(int desiredXpixel){
        return ((handler.getWidth()-2)*(desiredXpixel*30+420))/2;//not verified
    }
}
