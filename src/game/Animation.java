package game;
import biuoop.DrawSurface;

/**
 * interface for animation.
 * @author Lahav Amsalem 204632566
 */
public interface Animation {

    /**
     * function that allows flexibility in specific game (/levels) logic and drawings.
     * @param d - drawsurface.
     * @param dt - the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * when running = false, it means the player lost all lives or finished the level.
     * we want to stop at this point.
     * not running -> should stop.
     * @return true or false.
     */
    boolean shouldStop();
}
