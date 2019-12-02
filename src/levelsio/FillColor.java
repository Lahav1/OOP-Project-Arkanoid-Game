package levelsio;
import biuoop.DrawSurface;
import geometry.Rectangle;

import java.awt.Color;

/**
 * This class is used to create a filling for the block using a given color.
 * @author Lahav Amsalem 204632566
 */
public class FillColor implements Fill {
    private Color color;

    /**
     * Constructor a filling of a block using a given color.
     * @param c - the color we want to fill the block with.
     */
    public FillColor(Color c) {
        this.color = c;
    }

    /**
     * function fills a rectangle.
     * @param d - draw surface.
     * @param rec - block's rectangle.
     */
    public void drawFill(DrawSurface d, Rectangle rec) {
        int x = (int) rec.getUpperLeft().getX();
        int y = (int) rec.getUpperLeft().getY();
        int width = (int) rec.getWidth();
        int height = (int) rec.getHeight();
        d.setColor(this.color);
        d.fillRectangle(x, y, width, height);    }
}
