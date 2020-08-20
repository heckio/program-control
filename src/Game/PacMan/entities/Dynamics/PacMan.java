package Game.PacMan.entities.Dynamics;

import Game.GameStates.PacManState;
import Game.PacMan.World.MapBuilder;
import Game.PacMan.entities.Statics.BaseStatic;
import Game.PacMan.entities.Statics.BigDot;
import Game.PacMan.entities.Statics.BoundBlock;
import Main.Handler;
import Resources.Animation;
import Resources.Images;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class PacMan extends BaseDynamic{

    protected double velX,velY,speed = 3;
    public String facing = "Left";
    public boolean moving = true,turnFlag = false;
    public Animation leftAnim,rightAnim,upAnim,downAnim,deadanim;
    int turnCooldown = 5;
    public boolean abouttomoveR=false;
    public boolean abouttomoveL=false;
    public boolean abouttomoveU=false;
    public boolean abouttomoveD=false;
    public boolean pacmandead = false;
    public boolean frighton = false;
    int abouttomoveRdelay =60*3;
    int abouttomoveLdelay =60*3;
    int abouttomoveDdelay =60*3;
    int abouttomoveUdelay =60*3;
    int deadtimer=2;
    int xmapsize= Images.map2.getWidth();
    int ymapsize = Images.map2.getHeight();
    public int lives = 3;

    //3D Array for node position and turn ability

    public PacMan(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.pacmanRight[0]);
        leftAnim  = new Animation(128,Images.pacmanLeft  );
        rightAnim = new Animation(128,Images.pacmanRight );
        upAnim    = new Animation(128,Images.pacmanUp    );
        downAnim  = new Animation(128,Images.pacmanDown  );
        deadanim  = new Animation(150,Images.pacmandeath  );

    }

    @Override
    public void tick(){
        System.out.println("Y"+y);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
            if(lives<3){
                lives++;
            }
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {
            if(lives>0){
                handler.getMap().reset();
                pacmandead=true;
            }
        }
        for (BaseStatic block:handler.getMap().getBlocksOnMap()) {
            if(block instanceof BigDot){
                ((BigDot)block).kiki.tick();
            }
        }
        if(pacmandead){
            if(deadtimer<0){
                lives--;
                facing="Right";
                pacmandead=false;
                deadtimer= (2);

            }else{
                //deadanim.tick();
                deadtimer--;
            }

        }
        switch (facing){
            case "Right":
                if(getFixedXcoordinate() == 27*30){
                    setFixedXcoordinate(0);
                }
                x += velX;
                rightAnim.tick();
                break;
            case "Left":
                if(getFixedXcoordinate()==0){
                    setFixedXcoordinate(27*30);
                }
                x-=velX;
                leftAnim.tick();
                break;
            case "Up":
                y-=velY;
                upAnim.tick();
                break;
            case "Down":
                y+=velY;
                downAnim.tick();
                break;
        }
        if (turnCooldown<=0){
            turnFlag= false;
        }
        if (turnFlag){
            turnCooldown--;
        }



        if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)  || handler.getKeyManager().keyJustPressed(KeyEvent.VK_D)) && !turnFlag /*&& checkPreHorizontalCollision("Right")*/){
            abouttomoveR=true;
            abouttomoveD=false;
            abouttomoveU=false;
            abouttomoveL=false;

            abouttomoveRdelay=3*60;
        } else if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_A)) && !turnFlag/*&& checkPreHorizontalCollision("left") && y%30==0*/){
            abouttomoveL=true;
            abouttomoveR=false;
            abouttomoveU=false;
            abouttomoveD=false;
            abouttomoveLdelay=3*60;
        }else if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)  ||handler.getKeyManager().keyJustPressed(KeyEvent.VK_W)) && !turnFlag/*&& checkPreVerticalCollisions("Up") && x%30==0*/){
            abouttomoveU=true;
            abouttomoveD=false;
            abouttomoveR=false;
            abouttomoveL=false;
            abouttomoveUdelay=3*60;
        }else if ((handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)  || handler.getKeyManager().keyJustPressed(KeyEvent.VK_S)) && !turnFlag/*&& checkPreVerticalCollisions("Down") && x%30==0*/){
            abouttomoveD=true;
            abouttomoveR=false;
            abouttomoveU=false;
            abouttomoveL=false;
            abouttomoveDdelay=3*60;
        }
        /*System.out.println((int)Math.ceil((double)x/MapBuilder.pixelMultiplier));
        System.out.print("      ");
        System.out.print((int)Math.ceil((double)y/MapBuilder.pixelMultiplier));
        System.out.print("      ");*/

        if(abouttomoveR){
            if( abouttomoveRdelay>0){
                abouttomoveRdelay--;
                if(checkPreHorizontalCollision("Right") && Node(0) && Node(2)) {
                    facing = "Right";
                    turnFlag = true;
                    turnCooldown = 5;
                    abouttomoveR = false;
                }
            }else{
                abouttomoveRdelay=60*3;
                abouttomoveR=false;
            }
        }
        if(abouttomoveL){
            if( abouttomoveLdelay>0){
                abouttomoveLdelay--;
                if(checkPreHorizontalCollision("Left") && Node(0) && Node(1)) {
                    facing = "Left";
                    turnFlag = true;
                    turnCooldown = 5;
                    abouttomoveL = false;
                }
            }else{
                abouttomoveLdelay=60*3;
                abouttomoveL=false;
            }
        }
        if(abouttomoveU){
            if( abouttomoveUdelay>0){
                abouttomoveUdelay--;
                if(checkPreVerticalCollisions("Up") && Node(0) && Node(3) ) {
                    facing = "Up";//30 30*3 30*6 30*9 30*12 30*15 30*18 30*21 30*26
                    turnFlag = true;
                    turnCooldown = 5;
                    abouttomoveU = false;
                }
            }else{
                abouttomoveUdelay=60*3;
                abouttomoveU=false;
            }
        }
        if(abouttomoveD){
            if( abouttomoveDdelay>0){
                abouttomoveDdelay--;
                if(checkPreVerticalCollisions("Down") && Node(0) && Node(4)  ) {
                    facing = "Down";
                    turnFlag = true;
                    turnCooldown = 5;
                    abouttomoveD = false;
                }
            }else{
                abouttomoveDdelay=60*3;
                abouttomoveD=false;
            }
        }
        if (facing.equals("Right") || facing.equals("Left")){
            checkHorizontalCollision();
        }else{
            checkVerticalCollisions();
        }

    }

    public boolean Node(int function){
        boolean[][][] nodes = new boolean[ymapsize][xmapsize][5];//makes a 2d all false
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


        if(getFixedXcoordinate()%30==0&&y%30==0) {
            int pixelX = getFixedXpixel();
            //int pixelX = (x-(handler.getWidth()/2-(28*30)/2))/MapBuilder.pixelMultiplier;
            int pixelY = y/MapBuilder.pixelMultiplier;
            return nodes[pixelY][pixelX][function];
        }else if (facing=="Up"&&abouttomoveD){
            return true;
        }else if (facing=="Right"&&abouttomoveL){
            return true;
        }else if (facing=="Left"&&abouttomoveR){
            return true;
        }else if (facing=="Down"&&abouttomoveU){
            return true;
        }else{
            return false;
        }
    }

    public void checkVerticalCollisions() {
        PacMan pacman = this;
        ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
        ArrayList<BaseDynamic> enemies = handler.getMap().getEnemiesOnMap();

        boolean pacmanDies = false;
        boolean toUp = moving && facing.equals("Up");

        Rectangle pacmanBounds = toUp ? pacman.getTopBounds() : pacman.getBottomBounds();

        velY = speed;
        for (BaseStatic brick : bricks) {
            if (brick instanceof BoundBlock) {
                Rectangle brickBounds = !toUp ? brick.getTopBounds() : brick.getBottomBounds();
                if (pacmanBounds.intersects(brickBounds)) {
                    velY = 0;
                    if (toUp)
                        pacman.setY(brick.getY() + pacman.getDimension().height);
                    else
                        pacman.setY(brick.getY() - brick.getDimension().height);
                }
            }
        }

        for(BaseDynamic enemy : enemies){
            if(enemy instanceof Ghost){
                Rectangle enemyBounds = !toUp ? enemy.getTopBounds() : enemy.getBottomBounds();
                if (pacmanBounds.intersects(enemyBounds) && !((Ghost) enemy).frightened) {
                    pacmanDies = true;
                    break;
                }else if (((Ghost) enemy).frightened && pacmanBounds.intersects(enemyBounds)){
                    if(((Ghost)enemy).running) {

                        ((Ghost) enemy).goin = true;
                    }
                    break;
                }
            }
        }

        if(pacmanDies) {
            handler.getMap().reset();
            pacmandead=true;
        }
    }


    public boolean checkPreVerticalCollisions(String facing) {
        PacMan pacman = this;
        ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();

        boolean pacmanDies = false;
        boolean toUp = moving && facing.equals("Up");

        Rectangle pacmanBounds = toUp ? pacman.getTopBounds() : pacman.getBottomBounds();

        velY = speed;
        for (BaseStatic brick : bricks) {
            if (brick instanceof BoundBlock) {
                Rectangle brickBounds = !toUp ? brick.getTopBounds() : brick.getBottomBounds();
                if (pacmanBounds.intersects(brickBounds)) {
                    return false;
                }
            }
        }
        return true;

    }



    public void checkHorizontalCollision(){
        PacMan pacman = this;
        ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
        ArrayList<BaseDynamic> enemies = handler.getMap().getEnemiesOnMap();
        velX = speed;
        boolean pacmanDies = false;
        boolean toRight = moving && facing.equals("Right");

        Rectangle pacmanBounds = toRight ? pacman.getRightBounds() : pacman.getLeftBounds();

        for(BaseDynamic enemy : enemies){
            if(enemy instanceof Ghost){
                Rectangle enemyBounds = !toRight ? enemy.getRightBounds() : enemy.getLeftBounds();
                if (pacmanBounds.intersects(enemyBounds)&& !((Ghost) enemy).frightened) {
                    pacmanDies = true;
                    break;
                }else if (((Ghost) enemy).frightened && pacmanBounds.intersects(enemyBounds)){
                    if(((Ghost)enemy).running) {

                        ((Ghost) enemy).goin = true;
                    }
                    break;
                }
            }
        }


        if(pacmanDies) {
            handler.getMap().reset();
            pacmandead = true;
        }else  {

            for (BaseStatic brick : bricks) {//left and right collition check?
                if (brick instanceof BoundBlock) {
                    Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
                    if (pacmanBounds.intersects(brickBounds)) {
                        velX = 0;
                        if (toRight)
                            pacman.setX(brick.getX() - pacman.getDimension().width);
                        else
                            pacman.setX(brick.getX() + brick.getDimension().width);
                    }
                }
            }
        }
    }


    public boolean checkPreHorizontalCollision(String facing){
        PacMan pacman = this;
        ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
        velX = speed;
        boolean toRight = moving && facing.equals("Right");

        Rectangle pacmanBounds = toRight ? pacman.getRightBounds() : pacman.getLeftBounds();

        for (BaseStatic brick : bricks) {
            if (brick instanceof BoundBlock) {
                Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
                if (pacmanBounds.intersects(brickBounds)) {
                    return false;
                }
            }
        }
        return true;
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
    public void setFixedXcoordinate(int desiredX){
        x = desiredX + (handler.getWidth()/2)-(28*15);//notverified
    }


}
