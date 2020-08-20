package Game.PacMan.World;

import Game.PacMan.entities.Dynamics.BaseDynamic;
import Game.PacMan.entities.Dynamics.Bullet;
import Game.PacMan.entities.Dynamics.Ghost;
import Game.PacMan.entities.Dynamics.PacMan;
import Game.PacMan.entities.Statics.*;
import Main.Handler;
import Resources.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Random;

public class MapBuilder {

	public static int pixelMultiplier = 30;//change this for size of blocks
	public static int boundBlock = new Color(0,0,0).getRGB();
	public static int pacman = new Color(255, 255,0).getRGB();
	public static int ghostC = new Color(25, 255,0).getRGB();
	public static int dotC = new Color(255, 10, 0).getRGB();
	public static int bigDotC = new Color(167, 0, 150).getRGB();
	public static int base = new Color(50, 100, 100).getRGB();
	public static int ghostA = new Color(100, 50, 50).getRGB();
	public static int ghostB = new Color(100, 100, 50).getRGB();




	public static Map createMap(BufferedImage mapImage, Handler handler){
		Map mapInCreation = new Map(handler);
		Random fruitR = new Random();
		int fr  = fruitR.nextInt(30);
		int frr =0;
		for (int i = 0; i < mapImage.getWidth(); i++) {
			for (int j = 0; j < mapImage.getHeight(); j++) {
				int currentPixel = mapImage.getRGB(i, j);
				int xPos = handler.getWidth()/2-mapImage.getWidth()*pixelMultiplier/2+i*pixelMultiplier;
				int yPos = j*pixelMultiplier;
				if(currentPixel == boundBlock){
					BaseStatic BoundBlock = new BoundBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler,getSprite(mapImage,i,j));
					mapInCreation.addBlock(BoundBlock);
				}else if(currentPixel == pacman){
					if (mapImage.getRGB(i+1,j)==pacman){
						BaseDynamic PacMan = new PacMan(xPos+pixelMultiplier/2, yPos, pixelMultiplier, pixelMultiplier, handler);
						BaseDynamic Bullet = new Bullet(-30,-30 , 30,30, handler,null);
						mapInCreation.addEnemy(PacMan);
						mapInCreation.addEnemy(Bullet);
						handler.setPacman((Game.PacMan.entities.Dynamics.PacMan) PacMan);

					}
				}else if(currentPixel == ghostC){
					BaseStatic ghostSpawner = new GhostSpawner(xPos-pixelMultiplier/2,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(ghostSpawner);
				}else if(currentPixel == dotC){
					BaseStatic dot = new Dot(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					fr++;
					if(fr%30==0){
						dot.setFruit();
						dot.setFruit(frr);
						if(frr<4) frr++;
						else if(frr==4) frr=0;

						//fr=0;
					}
					mapInCreation.addBlock(dot);
				}else if(currentPixel == bigDotC){
					BaseStatic bigDot = new BigDot(xPos,yPos,pixelMultiplier,pixelMultiplier,handler);
					mapInCreation.addBlock(bigDot);
				}else if(currentPixel == base){
					BaseStatic BoundBlock = new BoundBlock(xPos,yPos,pixelMultiplier,pixelMultiplier,handler,getCocaCola(mapImage,i,j));
					mapInCreation.addBlock(BoundBlock);
				}


			}

		}
		return mapInCreation;
	}

	private static BufferedImage getSprite(BufferedImage mapImage, int i, int j) {

		int currentPixel = boundBlock;
		int leftPixel;
		int rightPixel;
		int upPixel;
		int downPixel;
		boolean leftposibl=false;
		boolean rightposibl=false;
		boolean upposibl=false;
		boolean downposibl=false;
		int onepixel ;
		int twopixel ;
		int threepixel;
		int fourpixel;
		if(i<mapImage.getWidth()-1&&j>0){
			onepixel = mapImage.getRGB(i+1,j-1);
		}else{
			onepixel= pacman;
		}
		if(i>0&&j>0){
			twopixel = mapImage.getRGB(i-1,j-1);
		}else{
			twopixel= pacman;


		}
		if(i>0&&j<mapImage.getWidth()-1){
			threepixel = mapImage.getRGB(i-1,j+1);
		}else{
			threepixel= pacman;
		}
		if(i<mapImage.getWidth()-1&&j<mapImage.getWidth()-1){
			fourpixel = mapImage.getRGB(i+1,j+1);
		}else{
			fourpixel= pacman;

		}
		if(i==0){
			leftposibl=true;
		}
		if(i==mapImage.getWidth()-1){
			rightposibl=true;
		}if(j==0){
			upposibl=true;
		}if(j==mapImage.getHeight()-1){
			downposibl=true;
		}
		if (i>0) {
			leftPixel = mapImage.getRGB(i - 1, j);
		}else{
			leftPixel = pacman;

		}

		if (i<mapImage.getWidth()-1) {
			rightPixel = mapImage.getRGB(i + 1, j);
		}else{
			rightPixel= pacman;

		}

		if (j>0) {
			upPixel = mapImage.getRGB(i, j - 1);
		}else{
			upPixel = pacman;

		}

		if (j<mapImage.getHeight()-1) {
			downPixel = mapImage.getRGB(i, j + 1);
		}else{
			downPixel = pacman;

		}
		if(currentPixel==base && currentPixel==rightPixel && currentPixel==downPixel && currentPixel != leftPixel && currentPixel != upPixel){
			return Images.bound[16];//right down
		}else if (currentPixel==base && currentPixel!=rightPixel && currentPixel==downPixel && currentPixel != leftPixel && currentPixel == upPixel){
			return Images.bound[18];//up down leftside
		}else if (currentPixel==base && currentPixel==rightPixel && currentPixel!=downPixel && currentPixel != leftPixel && currentPixel == upPixel){
			return Images.bound[21];//up right
		}else if (currentPixel==base && currentPixel==rightPixel && currentPixel!=downPixel && currentPixel == leftPixel && currentPixel != upPixel){
			return Images.bound[22];//left right
		}else if (currentPixel==base && currentPixel!=rightPixel && currentPixel!=downPixel && currentPixel == leftPixel && currentPixel == upPixel){
			return Images.bound[17];//up left
		}else if (currentPixel==base && currentPixel!=rightPixel && currentPixel==downPixel && currentPixel == leftPixel && currentPixel != upPixel){
			return Images.bound[19];//left down
		}else if (currentPixel==base && currentPixel!=rightPixel && currentPixel!=downPixel && currentPixel == leftPixel && currentPixel != upPixel){
			return Images.bound[20];//encdcap right
		}else if (currentPixel==base && currentPixel==rightPixel && currentPixel!=downPixel && currentPixel != leftPixel && currentPixel != upPixel){
			return Images.bound[23];//endcapleft
		}else if (currentPixel != leftPixel && currentPixel != upPixel && currentPixel != downPixel && currentPixel == rightPixel){

			return Images.bound[1];
		}else if (currentPixel != leftPixel && currentPixel != upPixel && currentPixel == downPixel && currentPixel != rightPixel){

			return Images.bound[2];
		}else if (currentPixel == leftPixel && currentPixel != upPixel && currentPixel != downPixel && currentPixel != rightPixel){

			return Images.bound[3];
		}else if (currentPixel != leftPixel && currentPixel == upPixel && currentPixel != downPixel && currentPixel != rightPixel){

			return Images.bound[4];
		}else if (leftposibl && currentPixel == upPixel && currentPixel == downPixel && currentPixel != rightPixel ){
			return Images.bound[12];
		}else if (currentPixel == leftPixel && currentPixel == upPixel && currentPixel == rightPixel && currentPixel==downPixel && currentPixel!= onepixel){
			return Images.bound[7];
		}else if (currentPixel == leftPixel && currentPixel == upPixel && currentPixel == rightPixel && currentPixel == downPixel && currentPixel != twopixel ){
			return Images.bound[8];
		}else if (currentPixel == leftPixel && currentPixel == upPixel && currentPixel == downPixel && currentPixel == rightPixel && currentPixel == fourpixel && currentPixel==threepixel){
			return  Images.bound[11];
		}else if (currentPixel == leftPixel && currentPixel == upPixel && currentPixel == rightPixel && currentPixel==downPixel && currentPixel != fourpixel  ){
			return Images.bound[9];
		}else if (rightposibl && currentPixel == upPixel && currentPixel == downPixel && currentPixel != leftPixel ){
			return Images.bound[13];
		}else if (upposibl && currentPixel == leftPixel && currentPixel != downPixel && currentPixel == rightPixel){
			return Images.bound[14];
		}else if (currentPixel == leftPixel && currentPixel != upPixel && downposibl && currentPixel == rightPixel){
			return Images.bound[15];
		}else if (currentPixel != leftPixel && currentPixel == upPixel && currentPixel == downPixel && currentPixel != rightPixel){
			return Images.bound[5];
		}else if (currentPixel == leftPixel && currentPixel != upPixel && currentPixel != downPixel && currentPixel == rightPixel){
			return Images.bound[6];
		}else if (currentPixel != leftPixel && currentPixel == upPixel && currentPixel != downPixel && currentPixel == rightPixel ){
			return Images.bound[7];
		}else if (currentPixel == leftPixel && currentPixel == upPixel && currentPixel != downPixel && currentPixel != rightPixel){
			return Images.bound[8];
		}else if (currentPixel != leftPixel && currentPixel != upPixel && currentPixel == downPixel && currentPixel == rightPixel){
			return Images.bound[9];
		}else if (currentPixel == leftPixel && currentPixel == upPixel && currentPixel == rightPixel && currentPixel==downPixel && currentPixel != threepixel ){
			return Images.bound[10];
		}else if (currentPixel == leftPixel && currentPixel != upPixel && currentPixel == downPixel && currentPixel != rightPixel ){
			return Images.bound[10];
		}else if (currentPixel != leftPixel && currentPixel == upPixel && currentPixel == downPixel && currentPixel == rightPixel){
			return Images.bound[12];
		}else if (currentPixel == leftPixel && currentPixel == upPixel && currentPixel == downPixel && currentPixel != rightPixel){
			return Images.bound[13];
		}else if (currentPixel == leftPixel && currentPixel != upPixel && currentPixel == downPixel && currentPixel == rightPixel){
			return Images.bound[14];
		}else if (currentPixel == leftPixel && currentPixel == upPixel && currentPixel != downPixel && currentPixel == rightPixel){
			return Images.bound[15];
		}else{

			return  Images.bound[0];
		}


	}

	private static BufferedImage getCocaCola(BufferedImage mapImage, int i, int j) {
		int currentPixel = base;
		int leftPixel;
		int rightPixel;
		int upPixel;
		int downPixel;
		boolean leftposibl=false;
		boolean rightposibl=false;
		boolean upposibl=false;
		boolean downposibl=false;
		int onepixel ;
		int twopixel ;
		int threepixel;
		int fourpixel;
		if(i<mapImage.getWidth()-1&&j>0){
			onepixel = mapImage.getRGB(i+1,j-1);
		}else{
			onepixel= pacman;

		}
		if(i>0&&j>0){
			twopixel = mapImage.getRGB(i-1,j-1);
		}else{
			twopixel= pacman;


		}
		if(i>0&&j<mapImage.getWidth()-1){
			threepixel = mapImage.getRGB(i-1,j+1);
		}else{
			threepixel= pacman;
		}
		if(i<mapImage.getWidth()-1&&j<mapImage.getWidth()-1){
			fourpixel = mapImage.getRGB(i+1,j+1);
		}else{
			fourpixel= pacman;

		}
		if(i==0){
			leftposibl=true;
		}
		if(i==mapImage.getWidth()-1){
			rightposibl=true;
		}if(j==0){
			upposibl=true;
		}if(j==mapImage.getHeight()-1){
			downposibl=true;
		}
		if (i>0) {
			leftPixel = mapImage.getRGB(i - 1, j);
		}else{
			leftPixel = pacman;

		}

		if (i<mapImage.getWidth()-1) {
			rightPixel = mapImage.getRGB(i + 1, j);
		}else{
			rightPixel= pacman;

		}

		if (j>0) {
			upPixel = mapImage.getRGB(i, j - 1);
		}else{
			upPixel = pacman;

		}

		if (j<mapImage.getHeight()-1) {
			downPixel = mapImage.getRGB(i, j + 1);
		}else{
			downPixel = pacman;

		}
		if(currentPixel==rightPixel && currentPixel==downPixel && currentPixel != leftPixel && currentPixel != upPixel){
			return Images.bound[16];//right down
		}else if (currentPixel!=rightPixel && currentPixel==downPixel && currentPixel != leftPixel && currentPixel == upPixel){
			return Images.bound[18];//up down leftside
		}else if ( currentPixel==rightPixel && currentPixel!=downPixel && currentPixel != leftPixel && currentPixel == upPixel){
			return Images.bound[21];//up right
		}else if ( currentPixel==rightPixel && currentPixel!=downPixel && currentPixel == leftPixel && currentPixel != upPixel){
			return Images.bound[22];//left right
		}else if ( currentPixel!=rightPixel && currentPixel!=downPixel && currentPixel == leftPixel && currentPixel == upPixel){
			return Images.bound[17];//up left
		}else if (currentPixel!=rightPixel && currentPixel==downPixel && currentPixel == leftPixel && currentPixel != upPixel){
			return Images.bound[19];//left down
		}else if (currentPixel!=rightPixel && currentPixel!=downPixel && currentPixel == leftPixel && currentPixel != upPixel){
			return Images.bound[20];//encdcap right
		}else if (currentPixel==rightPixel && currentPixel!=downPixel && currentPixel != leftPixel && currentPixel != upPixel){
			return Images.bound[23];//endcapleft
		}
		return  Images.bound[0];



	}

}
