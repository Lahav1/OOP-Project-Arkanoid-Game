package levelsio;
import biuoop.DrawSurface;
import geometry.Rectangle;
import java.awt.Color;

/**
 * This class is used to create an outline for the block using a given color.
 * @author Lahav Amsalem 204632566
 */
public class BlockOutline {
    private Color color;

    /**
     * Constructor creates an outline of a block using a given color.
     * @param c - the game we want to count its' blocks.
     */
    public BlockOutline(Color c) {
        this.color = c;
    }

    /**
     * function fills a rectangle.
     *
     * @param d   - draw surface.
     * @param rec - block's rectangle.
     */
    public void drawOutline(DrawSurface d, Rectangle rec) {
        int x = (int) rec.getUpperLeft().getX();
        int y = (int) rec.getUpperLeft().getY();
        int width = (int) rec.getWidth();
        int height = (int) rec.getHeight();
        d.setColor(this.color);
        d.drawRectangle(x, y, width, height);
    }
}
