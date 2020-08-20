package Display.UI;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by AlexVR on 1/24/2020.
 */
public class UIImageButton extends UIObject{
    private BufferedImage[] images;
    private ClickListlener clicker;

    public UIImageButton(float x, float y, int width, int height, BufferedImage[] images, ClickListlener clicker ) {
        super(x, y, width, height);
        this.images = new BufferedImage[3];
        if (images.length==1){
            this.images[0]=images[0];
            this.images[1]=images[0];
            this.images[2]=images[0];
        }
        else if (images.length==2){
            this.images[0] = images[0];
            this.images[1] = images[0];
            this.images[2] = images[1];
        }else {
            this.images = images;
        }
        this.clicker=clicker;
    }

    public UIImageButton(float x, float y, int width, int height, BufferedImage image, ClickListlener clicker ) {
        super(x, y, width, height);
        this.images = new BufferedImage[3];
        this.images[0]=image;
        this.images[1]=image;
        this.images[2]=image;
        this.clicker=clicker;
    }


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if(active){
            if(images.length==3) {
                g.drawImage(images[2], (int) x, (int) y, width, heigth, null);
            }
        }
        else if(hovering){
            g.drawImage(images[1],(int)x,(int)y,width, heigth,null);
        }else{
            g.drawImage(images[0],(int)x,(int)y,width, heigth,null);

        }
    }


    @Override
    public void onClick()
    {
        clicker.onClick();
    }
}
