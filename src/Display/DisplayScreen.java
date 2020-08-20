package Display;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by AlexVR on 1/24/2020.
 */

public class DisplayScreen {

    private JFrame frame;
    private Canvas canvas;

    private static String iconURL;

    private String title;
    private int width, height;

    public DisplayScreen(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        iconURL = "res/UI/icon.png";
        createDisplay();
    }

    private void createDisplay(){
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBackground(Color.black);

        try {
            frame.setIconImage(ImageIO.read(new File(iconURL)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.setBackground(Color.black);

        frame.add(canvas);
        frame.pack();
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public JFrame getFrame(){
        return frame;
    }

    public String stringInputPopUp(String Promp, String initialBoxValue){
        return JOptionPane.showInputDialog(getFrame(), Promp, initialBoxValue);
    }

    public int doubleInputPopUp(String Promp){
        return JOptionPane.showConfirmDialog(getFrame(), Promp);
    }

    public boolean flipFullScreen(){
        JFrame app = getFrame();
        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.getFullScreenWindow() == null){
            app.dispose();
            app.setUndecorated(true);
            gd.setFullScreenWindow(app);
            app.setVisible(true);
            return true;
        }else{ // back to windowed mode
            app.dispose();
            app.setUndecorated(false);
            gd.setFullScreenWindow(null);
            app.setVisible(true);
            return false;
        }
    }

}
