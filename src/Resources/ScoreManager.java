package Resources;

import Main.Handler;

/**
 * Created by AlexVR on 1/24/2020.
 */

public class ScoreManager {

    Handler handler;

    //Galaga
    private int galagaHighScore=0;
    private int galagaCurrentScore=0;

    //Pacman
    private int pacmanHighScore=0;
    private int pacmanCurrentScore=0;

    public ScoreManager(Handler handler) {
        this.handler = handler;
    }

    public int getGalagaHighScore() {
        return galagaHighScore;
    }

    public void setGalagaHighScore(int galagaHighScore) {
        this.galagaHighScore = galagaHighScore;
    }

    public int getGalagaCurrentScore() {
        return galagaCurrentScore;
    }

    public void setGalagaCurrentScore(int galagaCurrentScore) {
        this.galagaCurrentScore = galagaCurrentScore;
    }

    public void addGalagaCurrentScore(int galagaCurrentScore) {
        this.galagaCurrentScore += galagaCurrentScore;
    }

    public void removeGalagaCurrentScore(int galagaCurrentScore) {
        this.galagaCurrentScore -= galagaCurrentScore;
    }

    public int getPacmanHighScore() {
        return pacmanHighScore;
    }

    public void setPacmanHighScore(int pacmanHighScore) {
        this.pacmanHighScore = pacmanHighScore;
    }

    public int getPacmanCurrentScore() {
        return pacmanCurrentScore;
    }

    public void setPacmanCurrentScore(int pacmanCurrentScore) {
        this.pacmanCurrentScore = pacmanCurrentScore;
    }

    public void addPacmanCurrentScore(int pacmanCurrentScore) {
        this.pacmanCurrentScore += pacmanCurrentScore;
    }

    public void removePacmanCurrentScore(int pacmanCurrentScore) {
        this.pacmanCurrentScore -= pacmanCurrentScore;
    }

}
