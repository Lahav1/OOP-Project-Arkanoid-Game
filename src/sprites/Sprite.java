package sprites;
import biuoop.DrawSurface;

/**
 * interface for sprite objects in the game, which are all the objects that take part in the animation drawing.
 * @author Lahav Amsalem 204632566
 */
public interface Sprite {
    /**
     * calls the original drawon function of the object, depended on it's type.
     * @param d the draw surface where we want to draw the objects.
     */
    void drawOn(DrawSurface d);
    /**
     * notifies the sprite that time has passed.
     * @param dt - the amount of seconds passed since the last call.
     */
    void timePassed(double dt);
}