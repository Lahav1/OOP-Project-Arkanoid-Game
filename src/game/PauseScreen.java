package game;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * This class is used for creation of the pause screen animation controlled by keyboard.
 * @author Lahav Amsalem 204632566
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Constructor creates a pause screen animation object.
     * @param k - keyboard.
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    /**
     * pause screen logic and drawings.
     * @param d - drawsurface.
     * @param dt ignored.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // fill the background colors and the frames
        d.setColor(new Color(28, 63, 146));
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(new Color(243, 24, 50));
        d.fillRectangle(25, 25, 750, 550);
        d.fillRectangle(30, 30, 740, 540);
        d.setColor(Color.black);
        d.fillRectangle(30, 30, 740, 540);
        d.setColor(Color.white);
        d.fillCircle(390, 200, 80);
        d.drawText(300, d.getHeight() / 2 + 50, "Paused", 50);
        d.drawText(200, d.getHeight() / 2 + 105, "Press space key to continue.", 32);
        d.setColor(Color.black);
        d.fillCircle(390, 200, 72);
        d.setColor(Color.white);
        d.fillRectangle(360, 160, 20, 80);
        d.fillRectangle(400, 160, 20, 80);
    }

    /**
     * return true if the animation should stop and false if not.
     * @return should stop or not.
     */
    public boolean shouldStop() {
        return this.stop; }
}
