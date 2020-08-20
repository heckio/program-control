package Resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Created by AlexVR on 1/24/2020.
 */
public class Images {


    public static BufferedImage[] titleScreenBackground;
    public static BufferedImage pauseBackground;
    public static BufferedImage selectionBackground;
    public static BufferedImage galagaCopyright;
    public static BufferedImage galagaSelect;
    public static BufferedImage muteIcon;
    public static BufferedImage galagaPlayerLaser;
    public static BufferedImage[] startGameButton;
    public static BufferedImage[] galagaLogo;
    public static BufferedImage[] pauseResumeButton;
    public static BufferedImage[] pauseToTitleButton;
    public static BufferedImage[] pauseOptionsButton;
    public static BufferedImage[] galagaPlayer;
    public static BufferedImage[] galagaPlayerDeath;
    public static BufferedImage[] galagaEnemyDeath;
    public static BufferedImage[] galagaEnemyBee;
    public static BufferedImage[] frightened;
    public static BufferedImage[] eaten;
    public static BufferedImage[] fruits;

    public static BufferedImage[] joysticksprite;
    
    public static BufferedImage[] title;
    public static BufferedImage map1;
    public static BufferedImage map2;
    public static BufferedImage Dot;
    public static BufferedImage [] pacmandeath;//to be done
    public static BufferedImage[] ghostB;
    public static BufferedImage[] ghostC;
    public static BufferedImage[] ghostP;
    public static BufferedImage[] ghostI;
    public static BufferedImage[] pacmanDots;
    public static BufferedImage[] pacmanRight;
    public static BufferedImage[] pacmanLeft;
    public static BufferedImage[] pacmanUp;
    public static BufferedImage[] pacmanDown;
    public static BufferedImage[] bound;
    public static BufferedImage intro;
    public static BufferedImage start;
    public static BufferedImage score;
    public static BufferedImage bullet;

    public static BufferedImage[] ghost500;
    
    
    
    public static BufferedImage galagaImageSheet;
    public SpriteSheet galagaSpriteSheet;

    public static BufferedImage pacmanImageSheet;
    public SpriteSheet pacmanSpriteSheet;

    public Images() {
        titleScreenBackground= new BufferedImage[2];
        startGameButton = new BufferedImage[3];
        pauseResumeButton = new BufferedImage[2];
        pauseToTitleButton = new BufferedImage[2];
        pauseOptionsButton = new BufferedImage[2];
        galagaLogo = new BufferedImage[3];
        galagaPlayer = new BufferedImage[8];//not full yet, only has second to last image on sprite sheet
        galagaPlayerDeath = new BufferedImage[8];
        galagaEnemyDeath = new BufferedImage[5];
        galagaEnemyBee = new BufferedImage[8];
        pacmandeath = new BufferedImage[19];
        fruits = new BufferedImage[8];
        joysticksprite = new BufferedImage[5];
        ghost500 = new BufferedImage[2];

        pacmanDots = new BufferedImage[2];
        pacmanRight = new BufferedImage[3];
        pacmanLeft = new BufferedImage[3];
        pacmanUp = new BufferedImage[3];
        pacmanDown = new BufferedImage[3];
        ghostB = new BufferedImage[8];
        ghostP = new BufferedImage[8];
        ghostC = new BufferedImage[8];
        ghostI = new BufferedImage[8];
        bound = new BufferedImage[24];
        title = new BufferedImage[3];
        frightened = new BufferedImage[4];
        eaten = new BufferedImage[4];

        try {

            startGameButton[0]= ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Start/pushstart.png"));
            startGameButton[1]= ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Start/pushstartG.png"));
            startGameButton[2]= ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Start/pushstartY.png"));

            titleScreenBackground[1] = ImageIO.read(getClass().getResourceAsStream("/UI/Backgrounds/Title.png"));
            titleScreenBackground[0 ] = ImageIO.read(getClass().getResourceAsStream("/UI/Backgrounds/TitleI.png"));

            title[0] = ImageIO.read(getClass().getResourceAsStream("/UI/SpriteSheets/PacMan/gameselect.png"));
            title[1] = ImageIO.read(getClass().getResourceAsStream("/UI/SpriteSheets/PacMan/gameselected.png"));
            title[2] = ImageIO.read(getClass().getResourceAsStream("/UI/SpriteSheets/PacMan/gameselecth.png"));


            pauseBackground = ImageIO.read(getClass().getResourceAsStream("/UI/Backgrounds/Pause.png"));

            selectionBackground = ImageIO.read(getClass().getResourceAsStream("/UI/Backgrounds/Selection.png"));

            galagaCopyright = ImageIO.read(getClass().getResourceAsStream("/UI/Misc/Copyright.png"));

            galagaSelect = ImageIO.read(getClass().getResourceAsStream("/UI/Misc/galaga_select.png"));

            muteIcon = ImageIO.read(getClass().getResourceAsStream("/UI/Misc/mute.png"));

            galagaLogo[0] = ImageIO.read(getClass().getResourceAsStream("/UI/Misc/galaga_logo.png"));
            galagaLogo[1] = ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Selection/Galaga/hover_galaga_logo.png"));
            galagaLogo[2] = ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Selection/Galaga/pressed_galaga_logo.png"));

            pauseResumeButton[0] = ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Pause/Resume/NormalHoverResume.png"));
            pauseResumeButton[1] = ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Pause/Resume/PressedResume.png"));

            pauseToTitleButton[0] = ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Pause/ToTitle/NormalHoverToTitleButton.png"));
            pauseToTitleButton[1] = ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Pause/ToTitle/PressedToTitleButton.png"));

            pauseOptionsButton[0] = ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Pause/ToOptions/NormalHoverToOptionsButton.png"));
            pauseOptionsButton[1] = ImageIO.read(getClass().getResourceAsStream("/UI/Buttons/Pause/ToOptions/PressedToOptionsButton.png"));

            galagaImageSheet = ImageIO.read(getClass().getResourceAsStream("/UI/SpriteSheets/Galaga/Galaga.png"));
            galagaSpriteSheet = new SpriteSheet(galagaImageSheet);

            galagaPlayer[0] = galagaSpriteSheet.crop(160,55,15,16);

            galagaPlayerDeath[0] = galagaSpriteSheet.crop(209,48,32,32);
            galagaPlayerDeath[1] = galagaSpriteSheet.crop(209,48,32,32);
            galagaPlayerDeath[2] = galagaSpriteSheet.crop(247,48,32,32);
            galagaPlayerDeath[3] = galagaSpriteSheet.crop(247,48,32,32);
            galagaPlayerDeath[4] = galagaSpriteSheet.crop(288,47,32,32);
            galagaPlayerDeath[5] = galagaSpriteSheet.crop(288,47,32,32);
            galagaPlayerDeath[6] = galagaSpriteSheet.crop(327,47,32,32);
            galagaPlayerDeath[7] = galagaSpriteSheet.crop(327,47,32,32);

            galagaEnemyDeath[0] = galagaSpriteSheet.crop(201,191,32,32);
            galagaEnemyDeath[1] = galagaSpriteSheet.crop(223,191,32,32);
            galagaEnemyDeath[2] = galagaSpriteSheet.crop(247,191,32,32);
            galagaEnemyDeath[3] = galagaSpriteSheet.crop(280,191,32,32);
            galagaEnemyDeath[4] = galagaSpriteSheet.crop(320,191,32,32);

            galagaEnemyBee[0] = galagaSpriteSheet.crop(188,178,9,10);
            galagaEnemyBee[1] = galagaSpriteSheet.crop(162,178,13,10);
            galagaEnemyBee[2] = galagaSpriteSheet.crop(139,177,11,12);
            galagaEnemyBee[3] = galagaSpriteSheet.crop(113,176,14,13);
            galagaEnemyBee[4] = galagaSpriteSheet.crop(90,177,13,13);
            galagaEnemyBee[5] = galagaSpriteSheet.crop(65,176,13,14);
            galagaEnemyBee[6] = galagaSpriteSheet.crop(42,178,12,11);
            galagaEnemyBee[7] = galagaSpriteSheet.crop(19,177,10,13);


            galagaPlayerLaser = galagaSpriteSheet.crop(365 ,219,3,8);

            pacmanImageSheet = ImageIO.read(getClass().getResourceAsStream("/UI/SpriteSheets/PacMan/spritetest.png"));
            pacmanSpriteSheet = new SpriteSheet(pacmanImageSheet);
            map1 = ImageIO.read(getClass().getResourceAsStream("/UI/Backgrounds/PacManMaps/map1.png"));
            map2 = ImageIO.read(getClass().getResourceAsStream("/UI/Backgrounds/PacManMaps/map2.png"));//edwin
            //Se le añadió las imágenes de cada ghost.

            ghost500 [0]= pacmanSpriteSheet.crop(488,144,15,15);
            ghost500 [1]= pacmanSpriteSheet.crop(488,144,15,15);

            bullet = pacmanSpriteSheet.crop(554,193,17  ,17);


            ghostI[0] = pacmanSpriteSheet.crop(456+1,64+(2*16)+1,14,14);
            ghostI[1] = pacmanSpriteSheet.crop(456+(1*16)+1,64+(2*16)+1,14,14);//animation for ghost sprite
            ghostI[2] = pacmanSpriteSheet.crop(456+(2*16)+1,64+(2*16)+1,14,14);
            ghostI[3] = pacmanSpriteSheet.crop(456+(3*16)+1,64+(2*16)+1,14,14);
            ghostI[4] = pacmanSpriteSheet.crop(456+(4*16)+1,64+(2*16)+1,14,14);
            ghostI[5] = pacmanSpriteSheet.crop(456+(5*16)+1,64+(2*16)+1,14,14);
            ghostI[6] = pacmanSpriteSheet.crop(456+(6*16)+1,64+(2*16)+1,14,14);
            ghostI[7] = pacmanSpriteSheet.crop(456+(7*16)+1,64+(2*16)+1,14,14);

            ghostP[0] = pacmanSpriteSheet.crop(456+1,64+(1*16)+1,14,14);
            ghostP[1] = pacmanSpriteSheet.crop(456+(1*16)+1,64+(1*16)+1,14,14);//animation for ghost sprite
            ghostP[2] = pacmanSpriteSheet.crop(456+(2*16)+1,64+(1*16)+1,14,14);
            ghostP[3] = pacmanSpriteSheet.crop(456+(3*16)+1,64+(1*16)+1,14,14);
            ghostP[4] = pacmanSpriteSheet.crop(456+(4*16)+1,64+(1*16)+1,14,14);
            ghostP[5] = pacmanSpriteSheet.crop(456+(5*16)+1,64+(1*16)+1,14,14);
            ghostP[6] = pacmanSpriteSheet.crop(456+(6*16)+1,64+(1*16)+1,14,14);
            ghostP[7] = pacmanSpriteSheet.crop(456+(7*16)+1,64+(1*16)+1,14,14);

            ghostC[0] = pacmanSpriteSheet.crop(456+1,64+(3*16)+1,14,14);
            ghostC[1] = pacmanSpriteSheet.crop(456+(1*16)+1,64+(3*16)+1,14,14);//animation for ghost sprite
            ghostC[2] = pacmanSpriteSheet.crop(456+(2*16)+1,64+(3*16)+1,14,14);
            ghostC[3] = pacmanSpriteSheet.crop(456+(3*16)+1,64+(3*16)+1,14,14);
            ghostC[4] = pacmanSpriteSheet.crop(456+(4*16)+1,64+(3*16)+1,14,14);
            ghostC[5] = pacmanSpriteSheet.crop(456+(5*16)+1,64+(3*16)+1,14,14);
            ghostC[6] = pacmanSpriteSheet.crop(456+(6*16)+1,64+(3*16)+1,14,14);
            ghostC[7] = pacmanSpriteSheet.crop(456+(7*16)+1,64+(3*16)+1,14,14);

            ghostB[0] = pacmanSpriteSheet.crop(456+1,64+1,14,14);
            ghostB[1] = pacmanSpriteSheet.crop(456+(1*16)+1,64+1,14,14);//animation for ghost sprite
            ghostB[2] = pacmanSpriteSheet.crop(456+(2*16)+1,64+1,14,14);
            ghostB[3] = pacmanSpriteSheet.crop(456+(3*16)+1,64+1,14,14);
            ghostB[4] = pacmanSpriteSheet.crop(456+(4*16)+1,64+1,14,14);
            ghostB[5] = pacmanSpriteSheet.crop(456+(5*16)+1,64+1,14,14);
            ghostB[6] = pacmanSpriteSheet.crop(456+(6*16)+1,64+1,14,14);
            ghostB[7] = pacmanSpriteSheet.crop(456+(7*16)+1,64+1,14,14);
            //Se le añadió las imágenes de cuando se asustan los ghost

            frightened[2] = pacmanSpriteSheet.crop(585,64+1,14,14);
            frightened[3] = pacmanSpriteSheet.crop(601,64+1,14,14);
            frightened[0] = pacmanSpriteSheet.crop(617,64+1,14,14);
            frightened[1] = pacmanSpriteSheet.crop(633,64+1,14,14);
            //Se le añadió las imágenes de cuando se comen

            eaten[2] = pacmanSpriteSheet.crop(585,64+1+16,14,14);
            eaten[3] = pacmanSpriteSheet.crop(601,64+1+16,14,14);
            eaten[0] = pacmanSpriteSheet.crop(617,64+1+16,14,14);
            eaten[1] = pacmanSpriteSheet.crop(633,64+1+16,14,14);
            
            pacmanDots[0] = pacmanSpriteSheet.crop(643,18,14,14);
            Dot = pacmanSpriteSheet.crop(623,18,16,16);
            pacmanDots[1] = pacmanSpriteSheet.crop(339,110,16,16);


            //OLD MAP PARTS
            /*bound[0] = pacmanSpriteSheet.crop(603,18,16,16);//single
            bound[1] = pacmanSpriteSheet.crop(615,37,16,16);//right open
            bound[2] = pacmanSpriteSheet.crop(635,37,16,16);//down open
            bound[3] = pacmanSpriteSheet.crop(655,37,16,16);//left open
            bound[4] = pacmanSpriteSheet.crop(655,57,16,16);//up open
            bound[5] = pacmanSpriteSheet.crop(655,75,16,16);//up/down
            bound[6] = pacmanSpriteSheet.crop(656,116,16,16);//left/Right
            bound[7] = pacmanSpriteSheet.crop(656,136,16,16);//up/Right
            bound[8] = pacmanSpriteSheet.crop(655,174,16,16);//up/left
            bound[9] = pacmanSpriteSheet.crop(655,155,16,16);//down/Right
            bound[10] = pacmanSpriteSheet.crop(655,192,16,16);//down/left
            bound[11] = pacmanSpriteSheet.crop(664,232,16,16);//all blck
            bound[12] = pacmanSpriteSheet.crop(479,191,16,16);//left
            bound[13] = pacmanSpriteSheet.crop(494,191,16,16);//right
            bound[14] = pacmanSpriteSheet.crop(479,208,16,16);//top
            bound[15] = pacmanSpriteSheet.crop(479,223,16,16);//bottom*/

            bound[0] = pacmanSpriteSheet.crop(603,18,16,16);//single
            bound[1] = pacmanSpriteSheet.crop(615,37,16,16);//right open
            bound[2] = pacmanSpriteSheet.crop(635,37,16,16);//down open
            bound[3] = pacmanSpriteSheet.crop(655,37,16,16);//left open
            bound[4] = pacmanSpriteSheet.crop(655,57,16,16);//up open
            bound[5] = pacmanSpriteSheet.crop(0,32,8,8);//up/down //not used
            bound[6] = pacmanSpriteSheet.crop(656,116,16,16);//left/Right not
            bound[7] = pacmanSpriteSheet.crop(56,152,8,8);//up/Right
            bound[8] = pacmanSpriteSheet.crop(64,152,8,8);//up/left
            bound[9] = pacmanSpriteSheet.crop(176,120,8,8);//down/Right
            bound[10] = pacmanSpriteSheet.crop(40,72,8,8);//down/left
            bound[11] = pacmanSpriteSheet.crop(664,232,16,16);//all blck
            bound[12] = pacmanSpriteSheet.crop(56  ,136,8,8);//left
            bound[13] = pacmanSpriteSheet.crop(64,136,8,8);//right
            bound[14] = pacmanSpriteSheet.crop(88,144,8,8);//top
            bound[15] = pacmanSpriteSheet.crop(88,152,8,8);//bottom
            bound[16] = pacmanSpriteSheet.crop(80,96,8,8);//down right
            bound[17] = pacmanSpriteSheet.crop(136,128,8,8);//up left
            bound[18] = pacmanSpriteSheet.crop(136,120,8,8);//up down
            bound[19] = pacmanSpriteSheet.crop(136,96,8,8);//left down
            bound[20] = pacmanSpriteSheet.crop(96,96,8,8);//right cap
            bound[21] = pacmanSpriteSheet.crop(80,128,8,8);//up right
            bound[22] = pacmanSpriteSheet.crop(89,128,8,8);//left right
            bound[23] = pacmanSpriteSheet.crop(120,96,8,8);//right cap

            //Se le añadió las imágenenes de PacMan cuando va hacia la derecha.

            pacmanRight[0] = pacmanSpriteSheet.crop(473,1,13,13);
            pacmanRight[1] = pacmanSpriteSheet.crop(489,1,13,13);
            pacmanRight[2] = pacmanSpriteSheet.crop(457,1,13,13);

            pacmanLeft[0] = pacmanSpriteSheet.crop(473,17,13,13);
            pacmanLeft[1] = pacmanSpriteSheet.crop(489,1 ,13,13);
            pacmanLeft[2] = pacmanSpriteSheet.crop(457,17,13,13);
    
            //pacmandeath[0] = pacmanSpriteSheet.crop(489,1,13,13);
            pacmandeath[0] = pacmanSpriteSheet.crop(489+(16*1),1,13,13);
            pacmandeath[1] = pacmanSpriteSheet.crop(489+(16*2),1,13,13);
            pacmandeath[2] = pacmanSpriteSheet.crop(489+(16*3),1,13,13);
            pacmandeath[3] = pacmanSpriteSheet.crop(489+(16*4),1,13,13);
            pacmandeath[4] = pacmanSpriteSheet.crop(489+(16*5),1,13,13);
            pacmandeath[5] = pacmanSpriteSheet.crop(489+(16*6),1,13,13);
            pacmandeath[6] = pacmanSpriteSheet.crop(489+(16*7),1,13,13);
            pacmandeath[7] = pacmanSpriteSheet.crop(489+(16*8),1,13,13);
            pacmandeath[8] = pacmanSpriteSheet.crop(489+(16*9),1,13,13);
            pacmandeath[9] = pacmanSpriteSheet.crop(489+(16*10),1,13,13);
            pacmandeath[10] = pacmanSpriteSheet.crop(489+(16*11),1,13,13);
            pacmandeath[11] = pacmanSpriteSheet.crop(663,19,13,13);
            pacmandeath[12] = pacmanSpriteSheet.crop(489+(16*11),1,13,13);
            pacmandeath[13] = pacmanSpriteSheet.crop(339,110,16,16);
            pacmandeath[14] = pacmanSpriteSheet.crop(339,110,16,16);
            pacmandeath[15] = pacmanSpriteSheet.crop(339,110,16,16);
            pacmandeath[16] = pacmanSpriteSheet.crop(339,110,16,16);
            pacmandeath[17] = pacmanSpriteSheet.crop(339,110,16,16);
            pacmandeath[18] = pacmanSpriteSheet.crop(339,110,16,16);


            //Se le añadió las imágenes de las frutas.

            fruits[0] = pacmanSpriteSheet.crop(489+(16*0),49,13,13);
            fruits[1] = pacmanSpriteSheet.crop(489+(16*1),49,13,13);
            fruits[2] = pacmanSpriteSheet.crop(489+(16*2),49,13,13);
            fruits[3] = pacmanSpriteSheet.crop(489+(16*3),49,13,13);
            fruits[4] = pacmanSpriteSheet.crop(489+(16*4),49,13,13);
            fruits[5] = pacmanSpriteSheet.crop(489+(16*5),49,13,13);
            fruits[6] = pacmanSpriteSheet.crop(489+(16*6),49,13,13);
            fruits[7] = pacmanSpriteSheet.crop(489+(16*7),49,13,13);
            //Se le añadió las imágenes del joystick.

            joysticksprite[0] = pacmanSpriteSheet.crop(519 ,225,22,22);
            joysticksprite[1] = pacmanSpriteSheet.crop(543 ,225,22,22);
            joysticksprite[2] = pacmanSpriteSheet.crop(567 ,225,22,22);
            joysticksprite[3] = pacmanSpriteSheet.crop(590 ,225,22,22);
            joysticksprite[4] = pacmanSpriteSheet.crop(613 ,225,22,22);




            pacmanUp[0] = pacmanSpriteSheet.crop(473,33,13,13);
            pacmanUp[1] = pacmanSpriteSheet.crop(489,1 ,13,13);
            pacmanUp[2] = pacmanSpriteSheet.crop(457,33,13,13);


            pacmanDown[0] = pacmanSpriteSheet.crop(473,49,13,13);
            pacmanDown[1] = pacmanSpriteSheet.crop(489,1 ,13,13);
            pacmanDown[2] = pacmanSpriteSheet.crop(457,49,13,13);

            intro = ImageIO.read(getClass().getResourceAsStream("/UI/SpriteSheets/PacMan/intro.png"));
            start = ImageIO.read(getClass().getResourceAsStream("/UI/Backgrounds/startScreen.png"));
            score = ImageIO.read(getClass().getResourceAsStream("/UI/Backgrounds/Scorescreen.png"));



        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
