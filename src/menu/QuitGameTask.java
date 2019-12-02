package menu;
import biuoop.KeyboardSensor;
import game.AnimationRunner;

/**
 * This class is used for creation of the quit game task.
 * @author Lahav Amsalem 204632566
 */
public class QuitGameTask implements Task<Void> {
    // animation runner
    private AnimationRunner runner;

    /**
     * Constructor creates a quit game task.
     * @param k - keyboard sensor.
     * @param r - animation runner.
     */
    public QuitGameTask(KeyboardSensor k, AnimationRunner r) {
        this.runner = r;
    }


    /**
     * used for running the specific task.
     * @return null.
     */
    public Void run() {
        System.exit(1);
        return null;
    }
}
