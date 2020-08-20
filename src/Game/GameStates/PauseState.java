package Game.GameStates;

import Display.UI.UIImageButton;
import Display.UI.UIManager;
import Main.Handler;
import Resources.Images;

import java.awt.*;

/**
 * Created by AlexVR on 1/24/2020.
 */
public class PauseState extends State {

    private int count = 0;
    private UIManager uiManager;

    public PauseState(Handler handler) {
        super(handler);
        refresh();

    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pausebutt && count>=30){
            count=0;

            State.setState(handler.getGameState());
        }


    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.pauseBackground,0,0,handler.getWidth(),handler.getHeight(),null);
        uiManager.Render(g);

    }

    @Override
    public void refresh() {
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(56, 223, 128, 64, Images.pauseResumeButton, () -> {
            handler.getMouseManager().setUimanager(null);
            if (handler.getLastState() == null){
                State.setState(handler.getMenuState());
            }else {
                State.setState(handler.getLastState());
            }
        }));

        uiManager.addObjects(new UIImageButton(56, 223+(64+16), 128, 64, Images.pauseOptionsButton, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getMenuState());
        }));

        uiManager.addObjects(new UIImageButton(56, (223+(64+16))+(64+16), 128, 64, Images.pauseToTitleButton, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getMenuState());
        }));
    }
}
