package sprites;
import biuoop.DrawSurface;
import java.awt.Color;
import geometry.Point;
import geometry.Rectangle;
/**
 * This class is used for creating and drawing a score indicator for the game.
 * @author Lahav Amsalem 204632566
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    private Rectangle rectangle;
    private java.awt.Color color;

    /**
     * Constructor creates a score indicator.
     * @param scoreCount - the counter of the score.
     */
    public ScoreIndicator(Counter scoreCount) {
        this.score = scoreCount;
        // the score bar is always the same so automatically create it
        this.rectangle = new Rectangle(new Point(0, 0), 800, 20);
        this.color = java.awt.Color.white;
    }

    /**
     * function uses the details of the score indicator bar to draw it and print the counter over it.
     * @param d - the drawsurface.
     */
    public void drawOn(DrawSurface d) {
        //create a color variable and set the drawsurface to this color.
        java.awt.Color barColor = this.color;
        // find the rectangle's upper left point coordinates.
        int x = (int) this.rectangle.getUpperLeft().getX();
        int y = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();
        //set the color of the drawsurface to the bar's color.
        d.setColor(barColor);
        //fill the rectangle with the details.
        d.fillRectangle(x, y, width, height);
        //place the text as close as possible to middle of the block.
        //locate the text close to the middle of the bar.
        int textX = (int) this.rectangle.getUpper().middle().getX() - 50;
        int textY = (int) this.rectangle.getLeft().middle().getY() + 5;
        //create a string which presents the score.
        String text = "Score: " + Integer.toString(this.score.getValue());
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
