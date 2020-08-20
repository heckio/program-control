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

public class Bullet extends BaseDynamic{
    protected double velX=3 ,velY=3 ,speed = 3;
    public Animation bulletanim;
    boolean chasing_Ed;
    public boolean moving = true,turnFlag = false;
    int turnCooldown = 5;
    public String facing = "Up";
    int multip = MapBuilder.pixelMultiplier;
    int xmapsize= Images.map2.getWidth();
    int ymapsize = Images.map2.getHeight();
    public boolean[][][] nodes = new boolean[ymapsize][xmapsize][5];//makes a 2d all false
    public boolean[][] Visited = new boolean[ymapsize][xmapsize];//makes a 2d all false
    public int[][][] Queue = new int[ymapsize][xmapsize][3];//
    public boolean[][] nodos = new boolean[ymapsize][ymapsize];//
    public int prevX;
    public int prevY;


    public Bullet(int x, int y, int width, int height, Handler handler,String name) {
        super(x, y, width, height, handler, Images.bullet);
        bulletanim  = new Animation(128, Images.pacmanDots);

    }
        public void tick () {

            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE) && handler.getPacManState().fruitsOnMap>7) {
                System.out.println("teste");
                chasing_Ed = true;
                x=handler.getPacman().getX();
                y=handler.getPacman().getY();
                facing=handler.getPacman().facing;
            }
            if (chasing_Ed) {




                velX = 6;
                velY = 6;
                switch (facing) {
                    case "Right":
                        if (getFixedXcoordinate() == 27 * 30) {
                            x = setFixedXcoordinate(0);
                        }
                        x += velX;
                        bulletanim.tick();
                        break;
                    case "Left":
                        if (getFixedXcoordinate() == 0) {
                            x = setFixedXcoordinate(27 * 30);
                        }
                        x -= velX;
                        bulletanim.tick();
                        break;
                    case "Up":
                        y -= velY;
                        bulletanim.tick();
                        break;
                    case "Down":
                        y += velY;
                        bulletanim.tick();
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



                int TpixelX = 12;//TARGET PIXELS
                int TpixelY =11;
                int d=100;
                for (BaseDynamic entity : handler.getMap().getEnemiesOnMap()) {
                    if (entity instanceof Ghost) {
                        if(Pithagoras(x-entity.x,y-entity.y)<d){
                            d= (int) Pithagoras(x-entity.x,y-entity.y);
                            TpixelX = ((Ghost) entity).getFixedXpixel();
                            TpixelY = entity.getY()/multip;

                        }



                        if(this.getBounds().intersects(entity.getBounds())){
                            ((Ghost) entity).frighton=true;
                            ((Ghost) entity).goin=true;
                            x=-50;
                            y =-50;
                            chasing_Ed=false;
                        }


                    }
                }
                int PixelX = getFixedXpixel();
                int PixelY = y / multip;
                if (Node(0) && TpixelX != 0) {
                    facing = Get_next_dir(PixelX, PixelY, TpixelX, TpixelY);
                }
            }
        }
        public void checkVerticalCollisions () {
            Bullet ghost = this;
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




        }

        public boolean checkPreVerticalCollisions (String facing){
            Bullet ghost = this;
            ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();

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

        public void checkHorizontalCollision () {
            Bullet ghost = this;
            ArrayList<BaseStatic> bricks = handler.getMap().getBlocksOnMap();
            ArrayList<BaseDynamic> enemies = handler.getMap().getEnemiesOnMap();
            velX = speed;
            boolean ghostDies = false;
            boolean toRight = moving && facing.equals("Right");

            Rectangle ghostBounds = toRight ? ghost.getRightBounds() : ghost.getLeftBounds();


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


        public boolean checkPreHorizontalCollision (String facing){
            Bullet ghost = this;
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


        public String Get_next_dir ( int pixelX, int pixelY, int tpixelX, int tpixelY){

            /*for (int j = 0; j <ymapsize; j++) {
                for (int i = 0; i <xmapsize ; i++) {
                    int currentPixel = Images.map2.getRGB(i, j);
                    if(currentPixel != MapBuilder.boundBlock || currentPixel != MapBuilder.base ) nodos[i][j] = true;
                }
            }*/
            for (int j = 0; j < ymapsize; j++) {//set all paths nodes to true
                for (int i = 0; i < Visited[0].length; i++) {
                    int currenPixel = Images.map2.getRGB(i, j);
                    nodos[j][i] = (currenPixel != MapBuilder.boundBlock) && (currenPixel != MapBuilder.base);
                }
            }
            for (int j = 0; j < ymapsize; j++) {//set all visited pixels to false
                for (int i = 0; i < Visited[0].length; i++) {
                    Visited[j][i] = false;
                }
            }
            for (int j = 0; j < ymapsize; j++) {//set all disntances to infinite
                for (int i = 0; i < Queue[0].length; i++) {
                    Queue[j][i][0] = 99999;
                }
            }
            Queue[pixelY][pixelX][0] = 0;//sets initial point 0 distance
            checker(pixelX, pixelY, tpixelX, tpixelY);
            BacktrackX(tpixelX, tpixelY, pixelX, pixelY);
            String dir = "Left";
            if (prevX > pixelX) {
                dir = "Right";
            } else if (prevX < pixelX) {
                dir = "Left";
            }
            if (prevY > pixelY) {
                dir = "Down";
            } else if (prevY < pixelY) {
                dir = "Up";
            }
            return dir;
        }


        public void checker ( int pixelX, int pixelY, int tpixelX, int tpixelY){

            if (pixelX > 1) {
                if (nodos[pixelY][pixelX - 1] ) {//left
                    if (1 + Queue[pixelY][pixelX][0] < Queue[pixelY][pixelX - 1][0]) {
                        Queue[pixelY][pixelX - 1][0] = 1 + Queue[pixelY][pixelX][0];
                        Queue[pixelY][pixelX - 1][1] = pixelX;
                        Queue[pixelY][pixelX - 1][2] = pixelY;
                        Visited[pixelY][pixelX] = true;
                        checker(pixelX - 1, pixelY, tpixelX, tpixelY);
                    }
                }
            }
            if (pixelX < xmapsize - 1) {//right
                if (nodos[pixelY][pixelX + 1]) {
                    if (1 + Queue[pixelY][pixelX][0] < Queue[pixelY][pixelX + 1][0]) {
                        Queue[pixelY][pixelX + 1][0] = 1 + Queue[pixelY][pixelX][0];
                        Queue[pixelY][pixelX + 1][1] = pixelX;
                        Queue[pixelY][pixelX + 1][2] = pixelY;
                        Visited[pixelY][pixelX] = true;
                        checker(pixelX + 1, pixelY, tpixelX, tpixelY);
                    }

                }
            }
            if (pixelY > 1) {//up
                if (nodos[pixelY - 1][pixelX]) {
                    if (1 + Queue[pixelY][pixelX][0] < Queue[pixelY - 1][pixelX][0]) {
                        Queue[pixelY - 1][pixelX][0] = 1 + Queue[pixelY][pixelX][0];
                        Queue[pixelY - 1][pixelX][1] = pixelX;
                        Queue[pixelY - 1][pixelX][2] = pixelY;
                        Visited[pixelY][pixelX] = true;
                        checker(pixelX, pixelY - 1, tpixelX, tpixelY);
                    }
                }
            }
            if (pixelY < 30 && nodos[pixelY][pixelX]) {//dn
                if (nodos[pixelY + 1][pixelX]) {
                    if (1 + Queue[pixelY][pixelX][0] < Queue[pixelY + 1][pixelX][0]) {
                        Queue[pixelY + 1][pixelX][0] = 1 + Queue[pixelY][pixelX][0];
                        Queue[pixelY + 1][pixelX][1] = pixelX;
                        Queue[pixelY + 1][pixelX][2] = pixelY;
                        Visited[pixelY][pixelX] = true;
                        checker(pixelX, pixelY + 1, tpixelX, tpixelY);
                    }
                }
            }
        }

        //go back through the shortest path and find the pixel of the nearest nodes
        public void BacktrackX ( int pixlX, int pixlY, int startX, int startY){
            int[] prevcoor = new int[2];
            //int[] coor = new int[2];
            prevcoor[0] = Queue[pixlY][pixlX][1];
            prevcoor[1] = Queue[pixlY][pixlX][2];
            if (prevcoor[0] == startX && prevcoor[1] == startY) {
                prevX = pixlX;
                prevY = pixlY;
                //return coor;
            } else {
                BacktrackX(prevcoor[0], prevcoor[1], startX, startY);
                //break;
            }
            //return coor;
        }

        //find the distance of the next turning pont (Node)
        public int Distance (String direction,int InitialpixelX, int InitialpixelY)
        {//assuming you know that there is a node there
            int dis = 0;
            switch (direction) {
                case "Right":
                    while (!Nodes(0, InitialpixelX + 1, InitialpixelY + 0) && InitialpixelX + 1 < xmapsize - 1) {
                        InitialpixelX++;
                        dis++;
                    }
                    break;
                case "Left":
                    while (!Nodes(0, InitialpixelX - 1, InitialpixelY + 0) && InitialpixelX - 1 > 1) {
                        InitialpixelX--;
                        dis++;
                    }
                    break;
                case "Up":
                    while (!Nodes(0, InitialpixelX + 0, InitialpixelY - 1) && InitialpixelY - 1 > 1) {
                        InitialpixelY--;
                        dis++;
                    }
                    break;
                case "Down":
                    while (!Nodes(0, InitialpixelX + 0, InitialpixelY + 1) && InitialpixelY + 1 < 28) {
                        InitialpixelY++;
                        dis++;
                    }
                    break;
            }
            return dis;
        }


        ////END
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

        public boolean Node ( int function){
            //Z-axis(function) 0 = position ,1 = TURNLEFT , 2 = TURNRIGHT, 3 = TURNUP, 4 = TURNDOWN FOR WHAT?
            for (int j = 0; j < ymapsize; j++) {
                for (int i = 0; i < nodes[0].length; i++) {
                    nodes[j][i][0] = false;
                }
            }
            //POSITIONS
            nodes[1][1][0] = true;
            nodes[1][6][0] = true;
            nodes[1][12][0] = true;
            nodes[1][15][0] = true;
            nodes[1][21][0] = true;
            nodes[1][26][0] = true;
            nodes[5][1][0] = true;
            nodes[5][6][0] = true;
            nodes[5][9][0] = true;
            nodes[5][12][0] = true;
            nodes[5][15][0] = true;
            nodes[5][18][0] = true;
            nodes[5][21][0] = true;
            nodes[5][26][0] = true;
            nodes[8][1][0] = true;
            nodes[8][6][0] = true;
            nodes[8][9][0] = true;
            nodes[8][12][0] = true;
            nodes[8][15][0] = true;
            nodes[8][18][0] = true;
            nodes[8][21][0] = true;
            nodes[8][26][0] = true;
            nodes[11][9][0] = true;
            nodes[11][12][0] = true;
            nodes[11][15][0] = true;
            nodes[11][18][0] = true;
            nodes[14][6][0] = true;
            nodes[14][9][0] = true;
            nodes[14][18][0] = true;
            nodes[14][21][0] = true;
            nodes[17][9][0] = true;
            nodes[17][18][0] = true;
            nodes[20][1][0] = true;
            nodes[20][6][0] = true;
            nodes[20][9][0] = true;
            nodes[20][12][0] = true;
            nodes[20][15][0] = true;
            nodes[20][18][0] = true;
            nodes[20][21][0] = true;
            nodes[20][26][0] = true;
            nodes[23][1][0] = true;
            nodes[23][3][0] = true;
            nodes[23][6][0] = true;
            nodes[23][9][0] = true;
            nodes[23][12][0] = true;
            nodes[23][15][0] = true;
            nodes[23][18][0] = true;
            nodes[23][21][0] = true;
            nodes[23][24][0] = true;
            nodes[23][26][0] = true;
            nodes[26][1][0] = true;
            nodes[26][3][0] = true;
            nodes[26][6][0] = true;
            nodes[26][9][0] = true;
            nodes[26][12][0] = true;
            nodes[26][15][0] = true;
            nodes[26][18][0] = true;
            nodes[26][21][0] = true;
            nodes[26][24][0] = true;
            nodes[26][26][0] = true;
            nodes[29][26][0] = true;
            nodes[29][15][0] = true;
            nodes[29][12][0] = true;
            nodes[29][1][0] = true;
            //TURNLEFT
            for (int j = 0; j < ymapsize; j++) {
                for (int i = 0; i < nodes[0].length; i++) {
                    nodes[j][i][1] = false;
                }
            }
            //nodes[1][1][1]=true;
            nodes[1][6][1] = true;
            nodes[1][12][1] = true;
            //nodes[1][15][1]=true;
            nodes[1][21][1] = true;
            nodes[1][26][1] = true;
            //nodes[5][1][1]=true;
            nodes[5][6][1] = true;
            nodes[5][9][1] = true;
            nodes[5][12][1] = true;
            nodes[5][15][1] = true;
            nodes[5][18][1] = true;
            nodes[5][21][1] = true;
            nodes[5][26][1] = true;
            //nodes[8][1][1]=true;
            nodes[8][6][1] = true;
            //nodes[8][9][1]=true;
            nodes[8][12][1] = true;
            //nodes[8][15][1]=true;
            nodes[8][18][1] = true;
            //nodes[8][21][1]=true;
            nodes[8][26][1] = true;
            //nodes[11][9][1]=true;
            nodes[11][12][1] = true;
            nodes[11][15][1] = true;
            nodes[11][18][1] = true;
            nodes[14][6][1] = true;
            nodes[14][9][1] = true;
            //nodes[14][18][1]=true;
            nodes[14][21][1] = true;
            //nodes[17][9][1]=true;
            nodes[17][18][1] = true;
            //nodes[20][1][1]=true;
            nodes[20][6][1] = true;
            nodes[20][9][1] = true;
            nodes[20][12][1] = true;
            //nodes[20][15][1]=true;
            nodes[20][18][1] = true;
            nodes[20][21][1] = true;
            nodes[20][26][1] = true;
            //nodes[23][1][1]=true;
            nodes[23][3][1] = true;
            //nodes[23][6][1]=true;
            nodes[23][9][1] = true;
            nodes[23][12][1] = true;
            nodes[23][15][1] = true;
            nodes[23][18][1] = true;
            nodes[23][21][1] = true;
            //nodes[23][24][1]=true;
            nodes[23][26][1] = true;
            //nodes[26][1][1]=true;
            nodes[26][3][1] = true;
            nodes[26][6][1] = true;
            //nodes[26][9][1]=true;
            nodes[26][12][1] = true;
            //nodes[26][15][1]=true;
            nodes[26][18][1] = true;
            //nodes[26][21][1]=true;
            nodes[26][24][1] = true;
            nodes[26][26][1] = true;
            nodes[29][26][1] = true;
            nodes[29][15][1] = true;
            nodes[29][12][1] = true;
            //nodes[29][1][1]=true;

            //TURN RIGHT
            for (int j = 0; j < ymapsize; j++) {
                for (int i = 0; i < nodes[0].length; i++) {
                    nodes[j][i][2] = false;
                }
            }
            nodes[1][1][2] = true;
            nodes[1][6][2] = true;
            //nodes[1][12][2]=true;
            nodes[1][15][2] = true;
            nodes[1][21][2] = true;
            //nodes[1][26][2]=true;
            nodes[5][1][2] = true;
            nodes[5][6][2] = true;
            nodes[5][9][2] = true;
            nodes[5][12][2] = true;
            nodes[5][15][2] = true;
            nodes[5][18][2] = true;
            nodes[5][21][2] = true;
            //nodes[5][26][2]=true;
            nodes[8][1][2] = true;
            //nodes[8][6][2]=true;
            nodes[8][9][2] = true;
            //nodes[8][12][2]=true;
            nodes[8][15][2] = true;
            //nodes[8][18][2]=true;
            nodes[8][21][2] = true;
            //nodes[8][26][2]=true;
            nodes[11][9][2] = true;
            nodes[11][12][2] = true;
            nodes[11][15][2] = true;
            //nodes[11][18][2]=true;
            nodes[14][6][2] = true;
            //nodes[14][9][2]=true;
            nodes[14][18][2] = true;
            nodes[14][21][2] = true;
            nodes[17][9][2] = true;
            //nodes[17][18][2]=true;
            nodes[20][1][2] = true;
            nodes[20][6][2] = true;
            nodes[20][9][2] = true;
            //nodes[20][12][2]=true;
            nodes[20][15][2] = true;
            nodes[20][18][2] = true;
            nodes[20][21][2] = true;
            //nodes[20][26][2]=true;
            nodes[23][1][2] = true;
            //nodes[23][3][2]=true;
            nodes[23][6][2] = true;
            nodes[23][9][2] = true;
            nodes[23][12][2] = true;
            nodes[23][15][2] = true;
            nodes[23][18][2] = true;
            //nodes[23][21][2]=true;
            nodes[23][24][2] = true;
            //nodes[23][26][2]=true;
            nodes[26][1][2] = true;
            nodes[26][3][2] = true;
            //nodes[26][6][2]=true;
            nodes[26][9][2] = true;
            //nodes[26][12][2]=true;
            nodes[26][15][2] = true;
            //nodes[26][18][2]=true;
            nodes[26][21][2] = true;
            nodes[26][24][2] = true;
            //nodes[26][26][2]=true;
            //nodes[29][26][2]=true;
            nodes[29][15][2] = true;
            nodes[29][12][2] = true;
            nodes[29][1][2] = true;


            //TURNUP
            for (int j = 0; j < ymapsize; j++) {
                for (int i = 0; i < nodes[0].length; i++) {
                    nodes[j][i][3] = false;
                }
            }
            //nodes[1][1][3]=true;
            //nodes[1][6][3]=true;
            //nodes[1][12][3]=true;
            //nodes[1][15][3]=true;
            //nodes[1][21][3]=true;
            //nodes[1][26][3]=true;
            nodes[5][1][3] = true;
            nodes[5][6][3] = true;
            //nodes[5][9][3]=true;
            nodes[5][12][3] = true;
            nodes[5][15][3] = true;
            //nodes[5][18][3]=true;
            nodes[5][21][3] = true;
            nodes[5][26][3] = true;
            nodes[8][1][3] = true;
            nodes[8][6][3] = true;
            nodes[8][9][3] = true;
            //nodes[8][12][3]=true;
            //nodes[8][15][3]=true;
            nodes[8][18][3] = true;
            nodes[8][21][3] = true;
            nodes[8][26][3] = true;
            //nodes[11][9][3]=true;
            nodes[11][12][3] = true;
            nodes[11][15][3] = true;
            //nodes[11][18][3]=true;
            nodes[14][6][3] = true;
            nodes[14][9][3] = true;
            nodes[14][18][3] = true;
            nodes[14][21][3] = true;
            nodes[17][9][3] = true;
            nodes[17][18][3] = true;
            //nodes[20][1][3]=true;
            nodes[20][6][3] = true;
            nodes[20][9][3] = true;
            //nodes[20][12][3]=true;
            //nodes[20][15][3]=true;
            nodes[20][18][3] = true;
            nodes[20][21][3] = true;
            //nodes[20][26][3]=true;
            nodes[23][1][3] = true;
            //nodes[23][3][3]=true;
            nodes[23][6][3] = true;
            //nodes[23][9][3]=true;
            nodes[23][12][3] = true;
            nodes[23][15][3] = true;
            //nodes[23][18][3]=true;
            nodes[23][21][3] = true;
            //nodes[23][24][3]=true;
            nodes[23][26][3] = true;
            //nodes[26][1][3]=true;
            nodes[26][3][3] = true;
            nodes[26][6][3] = true;
            nodes[26][9][3] = true;
            //nodes[26][12][3]=true;
            //nodes[26][15][3]=true;
            nodes[26][18][3] = true;
            nodes[26][21][3] = true;
            nodes[26][24][3] = true;
            //nodes[26][26][3]=true;
            nodes[29][26][3] = true;
            nodes[29][15][3] = true;
            nodes[29][12][3] = true;
            nodes[29][1][3] = true;

            //TURNDOWN
            for (int j = 0; j < ymapsize; j++) {
                for (int i = 0; i < nodes[0].length; i++) {
                    nodes[j][i][4] = false;
                }
            }
            nodes[1][1][4] = true;
            nodes[1][6][4] = true;
            nodes[1][12][4] = true;
            nodes[1][15][4] = true;
            nodes[1][21][4] = true;
            nodes[1][26][4] = true;
            nodes[5][1][4] = true;
            nodes[5][6][4] = true;
            nodes[5][9][4] = true;
            //nodes[5][12][4]=true;
            //nodes[5][15][4]=true;
            nodes[5][18][4] = true;
            nodes[5][21][4] = true;
            nodes[5][26][4] = true;
            //nodes[8][1][4]=true;
            nodes[8][6][4] = true;
            //nodes[8][9][4]=true;
            nodes[8][12][4] = true;
            nodes[8][15][4] = true;
            //nodes[8][18][4]=true;
            nodes[8][21][4] = true;
            //nodes[8][26][4]=true;
            nodes[11][9][4] = true;
            //nodes[11][12][4]=true;
            //nodes[11][15][4]=true;
            nodes[11][18][4] = true;
            nodes[14][6][4] = true;
            nodes[14][9][4] = true;
            nodes[14][18][4] = true;
            nodes[14][21][4] = true;
            nodes[17][9][4] = true;
            nodes[17][18][4] = true;
            nodes[20][1][4] = true;
            nodes[20][6][4] = true;
            //nodes[20][9][4]=true;
            nodes[20][12][4] = true;
            nodes[20][15][4] = true;
            //nodes[20][18][4]=true;
            nodes[20][21][4] = true;
            nodes[20][26][4] = true;
            //[23][1][4]=true;
            nodes[23][3][4] = true;
            nodes[23][6][4] = true;
            nodes[23][9][4] = true;
            //nodes[23][12][4]=true;
            //nodes[23][15][4]=true;
            nodes[23][18][4] = true;
            nodes[23][21][4] = true;
            nodes[23][24][4] = true;
            //nodes[23][26][4]=true;
            nodes[26][1][4] = true;
            //nodes[26][3][4]=true;
            //nodes[26][6][4]=true;
            //nodes[26][9][4]=true;
            nodes[26][12][4] = true;
            nodes[26][15][4] = true;
            //nodes[26][18][4]=true;
            //nodes[26][21][4]=true;
            //nodes[26][24][4]=true;
            nodes[26][26][4] = true;
            //nodes[29][26][4]=true;
            //nodes[29][15][4]=true;
            //nodes[29][12][4]=true;
            //nodes[29][1][4]=true;


            if (getFixedXcoordinate() % multip == 0 && y % multip == 0) {
                int pixelX = (x - (handler.getWidth() / 2 - (28 * 30) / 2)) / MapBuilder.pixelMultiplier;
                int pixelY = y / MapBuilder.pixelMultiplier;
                return nodes[pixelY][pixelX][function];
            } else {
                return false;
            }
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
    public double Pithagoras(int a, int b){
        double c = Math.sqrt(  Math.pow((double)a, 2)  +  Math.pow((double)b, 2)  );
        return c;
    }


}