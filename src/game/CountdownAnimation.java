package game;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;
import java.awt.Color;

/**
 * The CountdownAnimation will display the given gameScreen, for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will appear on the screen for (numOfSeconds / countFrom)
 * secods, before it is replaced with the next one.
 * @author Lahav Amsalem 204632566
 */
public class CountdownAnimation implements Animation {
    private boolean stop;
    private double numOfSec;
    private int startCount;
    private int currentCount;
    private SpriteCollection screenSprites;

    /**
     * Constructor creates a countdown.
     * @param numOfSeconds - the number of seconds we want the countdown to last.
     * @param countFrom - the number we want to start the countdown from.
     * @param gameScreen - the objects we want to see behind the countdown animation.
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen) {
        this.screenSprites = gameScreen;
        this.numOfSec = numOfSeconds;
        this.startCount = countFrom;
        //create a member of the actual counting and initialize it with the countfrom value.
        this.currentCount = countFrom;
    }

    /**
     * pause screen logic and drawings.
     * @param d - drawsurface.
     * @param dt ignored.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.screenSprites.drawAllOn(d);
        // cover the outline of the sideblocks with dark gray.
        d.setColor(Color.DARK_GRAY);
        d.drawRectangle(775, 25, 25, 576);
        d.drawRectangle(0, 25, 25, 576);
        d.setColor(Color.BLACK);
        d.fillCircle(d.getWidth() / 2, d.getHeight() / 2 - 50, 40);
        d.setColor(Color.WHITE);
        d.drawText(d.getWidth() / 2 - 18, d.getHeight() / 2 - 25,
                Integer.toString((int) this.currentCount), 70);
        if (this.startCount != this.currentCount) {
            Sleeper sleeper = new Sleeper();
            sleeper.sleepFor((int) (1000 * (this.numOfSec / this.startCount)));
        }
        if (this.currentCount < 1) {
            this.stop = true;
        }
        this.currentCount--;
    }

    /**
     * return true if the animation should stop and false if not.
     * @return should stop or not.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
