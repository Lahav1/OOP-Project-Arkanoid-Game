package highscore;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Animation;
import java.awt.Color;

/**
 * This class is used for creation of the high scores display screen.
 * @author Lahav Amsalem 204632566
 */
public class HighScoresAnimation implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private HighScoresTable table;

    /**
     * Constructor creates a highscores screen.
     * @param k - keyboard sensor.
     * @param hs the counter that is in charge of counting the scores of the whole game.
     */
    public HighScoresAnimation(KeyboardSensor k, HighScoresTable hs) {
        this.keyboard = k;
        this.stop = false;
        this.table = hs;
    }

    /**
     * pause screen logic and drawings.
     * @param d - drawsurface.
     * @param dt ignored.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(new Color(28, 63, 146));
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(new Color(243, 24, 50));
        d.fillRectangle(30, 30, 740, 540);
        d.fillRectangle(25, 25, 750, 550);
        d.setColor(Color.BLACK);
        d.fillRectangle(30, 30, 740, 540);
        d.setColor(Color.WHITE);
        d.drawText(275, 100, "High Scores", 36);
        d.setColor(new Color(215, 215, 215));
        d.drawText(70, 150, "Name: ", 27);
        d.drawText(600, 150, "Score:", 27);
        d.drawText(200, 550, "Press space key to return to the main menu.", 20);
        // draw separation lines.
        d.setColor(new Color(243, 24, 50));
        d.fillRectangle(70, 160, 610, 2);
        d.fillRectangle(580, 120, 2, 370);
        d.setColor(Color.white);
        for (int i = 0; i < this.table.getHighScores().size(); i++) {
            d.drawText(70, 170 + 30 * (i + 1), this.table.getHighScores().get(i).getName(), 20);
        }
        for (int i = 0; i < this.table.getHighScores().size(); i++) {
            d.drawText(600, 170 + 30 * (i + 1),
                    Integer.toString(this.table.getHighScores().get(i).getScore()), 20);
        }
        if (this.keyboard.isPressed(KeyboardSensor.UP_KEY)) {
            this.stop = true;
        }
    }

    /**
     * return true if the animation should stop and false if not.
     * @return should stop or not.
     */
    public boolean shouldStop() {
        return this.stop; }
}
