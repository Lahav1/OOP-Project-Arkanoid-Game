package levelsio;
import geometry.Rectangle;
import biuoop.DrawSurface;
import java.awt.Image;


/**
 * This class is used to create a filling for the block using a given image.
 * @author Lahav Amsalem 204632566
 */
public class FillImage implements Fill {
    private Image image;

    /**
     * Constructor a filling of a block using a given image.
     *
     * @param img - image we want to fill the block with.
     */
    public FillImage(Image img) {
        this.image = img;
    }

    /**
     * function fills a rectangle.
     *
     * @param d   - draw surface.
     * @param rec - block's rectangle.
     */
    public void drawFill(DrawSurface d, Rectangle rec) {
        int x = (int) rec.getUpperLeft().getX();
        int y = (int) rec.getUpperLeft().getY();
        d.drawImage(x, y, this.image);
    }
}




