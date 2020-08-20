package Game.GameStates;


import Display.UI.ClickListlener;
import Display.UI.UIImageButton;
import Display.UI.UIManager;
import Main.Handler;
import Resources.Images;

import java.awt.*;

/**
 * Created by AlexVR on 1/24/2020.
 */
public class MenuState extends State {

    private UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);
        refresh();
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0,0,handler.getWidth(),handler.getHeight());
        g.drawImage(Images.titleScreenBackground[0],0,0,handler.getWidth(),handler.getHeight(),null);
        uiManager.Render(g);

    }


    @Override
    public void refresh() {
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        //Se cambió la entrada de la aplicación
        uiManager.addObjects(new UIImageButton((handler.getWidth() / 2) - (handler.getWidth() /16), (handler.getHeight() /2) - (handler.getHeight() /6), handler.getWidth()/8, handler.getHeight()/24, Images.startGameButton, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                State.setState(handler.getGameState());
            }
        }));
    }
}
