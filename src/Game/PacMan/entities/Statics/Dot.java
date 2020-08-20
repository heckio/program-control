package Game.PacMan.entities.Statics;

import Main.Handler;
import Resources.Images;

public class Dot extends BaseStatic{
    public Dot(int x, int y, int width, int height, Handler handler) {
        super(x, y, width, height, handler, Images.Dot);
    }


}
