package menu;
import game.AnimationRunner;
import game.KeyPressStoppableAnimation;

/**
 * This class is used for creation of the high score task.
 * @author Lahav Amsalem 204632566
 */
public class HighScoresTask implements Task<Void> {
    // animation runner
    private AnimationRunner runner;
    // keyboard sensor.
    private KeyPressStoppableAnimation hsAnimation;

    /**
     * Constructor creates a highscore task.
     * @param r - animation runner.
     * @param hs - highscore animation.
     */
    public HighScoresTask(AnimationRunner r, KeyPressStoppableAnimation hs) {
        this.runner = r;
        this.hsAnimation = hs;
    }

    /**
     * used for running the specific task.
     * @return null.
     */
    public Void run() {
        this.runner.run(this.hsAnimation);
        return null;
    }
}
