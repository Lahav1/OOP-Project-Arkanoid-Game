package sprites;

import biuoop.DrawSurface;
import geometry.Rectangle;
import geometry.Point;
import levelsio.Fill;

/**
 * This class holds a background object.
 * @author Lahav Amsalem 204632566
 */
public class Background implements Sprite {
    private Fill fill;
    private Rectangle rectangle;

    /**
     * Constructor creates a background.
     * @param f - can be image or color.
     */
    public Background(Fill f) {
        this.fill = f;
        this.rectangle = new Rectangle(new Point(0, 0), 800, 600);
    }

    /**
     * function uses the fill and rectangle to draw the background.
     * @param surface the surface where we draw the block
     */
    public void drawOn(DrawSurface surface) {
        this.fill.drawFill(surface, this.rectangle);
    }

    /**
     * notifies the sprite that time has passed.
     * @param dt ignored.
     */
    public void timePassed(double dt) {
        return;
    }

}
