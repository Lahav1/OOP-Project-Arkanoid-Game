package sprites;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import java.awt.Color;

/**
 * This class is used for drawing the level's name.
 * @author Lahav Amsalem 204632566
 */
public class LevelsName implements Sprite {
    private String name;
    private Rectangle rectangle;

    /**
     * Constructor creates a lives indicator.
     * @param lvlname - the level's name.
     */
    public LevelsName(String lvlname) {
        this.name = lvlname;
        // the score bar is always the same so automatically create it
        this.rectangle = new Rectangle(new Point(0, 0), 800, 20);
    }

    /**
     * function uses the details of the score indicator bar to draw it and print the counter over it.
     * @param d - the drawsurface.
     */
    public void drawOn(DrawSurface d) {
        //place the text as close as possible to middle of the block.
        //locate the text close to the middle of the bar.
        int textX = (int) this.rectangle.getUpper().middle().getX() + 160;
        int textY = (int) this.rectangle.getLeft().middle().getY() + 5;
        //create a string which presents the score.
        String text = "Level Name: " + this.name;
        //draw the text.
        d.setColor(Color.black);
        d.drawText(textX, textY, text, 14);
    }

    /**
     * implementation of Sprite interface.
     * no use.
     * @param dt ignored
     */
    public void timePassed(double dt) {
        return;
    }

    /**
     * accessor to the rectangle.
     * @return the rectangle.
     */
    public Rectangle getRectangle() {
        return this.rectangle;
    }

}
