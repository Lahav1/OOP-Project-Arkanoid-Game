package sprites;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;

/**
 * This class holds a list of all of the sprite objects on the screen.
 * @author Lahav Amsalem 204632566
 */
public class SpriteCollection {
    //a list that includes all the sprites
    private List<Sprite> spriteList = new ArrayList<Sprite>();

    /**
     * Constructor creates a collection of sprite objects.
     * @param spriteList list of sprites.
     */
    public SpriteCollection(List<Sprite> spriteList) {
        this.spriteList = spriteList;
    }

    /**
     * function adds a new sprite to the sprite list.
     * @param s a new sprite we want to add to the game.
     */
    public void addSprite(Sprite s) {
        spriteList.add(s);
    }

    /**
     * function notifies all sprites that time has passed.
     * @param dt the dt we pass to every single timepassed function.
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < spriteList.size(); i++) {
            spriteList.get(i).timePassed(dt);
        }
    }

    /**
     * function draws all the sprites.
     * @param d - the draw surface we want to draw the sprites on.
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < spriteList.size(); i++) {
            spriteList.get(i).drawOn(d);
        }
    }

    /**
     * remove a sprite from the sprites list.
     * @param s - the sprite we want to remove.
     */
    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }
}