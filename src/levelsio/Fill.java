package levelsio;
import biuoop.DrawSurface;
import geometry.Rectangle;

/**
 * includes functions which are related to the block's background.
 * @author Lahav Amsalem 204632566
 */
public interface Fill {

    /**
     * function fills a rectangle.
     * @param d - draw surface.
     * @param rec - block's rectangle.
     */
    void drawFill(DrawSurface d, Rectangle rec);
}
