package menu;
import biuoop.KeyboardSensor;
import game.AnimationRunner;

/**
 * This class is used for creation of the start game task.
 * @param <T> task type.
 * @author Lahav Amsalem 204632566
 */
public class SubMenuTask<T> extends MenuAnimation implements Task<Void> {
    private AnimationRunner runner;

    /**
     * Constructor creates a menu animation object.
     * @param r - runner.
     * @param k - keyboard.
     * @param menuTitle - title.
     */
    public SubMenuTask(AnimationRunner r, KeyboardSensor k, String menuTitle) {
        super(k, menuTitle);
        this.runner = r;
    }

    /**
     * used for running the specific task.
     * @return null
     */
    public Void run() {
        // create a while loop which runs the animation by user's choice.
        while (true) {
            this.runner.run(this);
            if (this.getStatus().run() == null) {
                break;
            }
            // keep running the next screen.
            if (this.getStatus() != null) {
                this.getStatus().run();
            }
        }
        // reset tasks and return null.
        this.resetTasks();
        return null;
    }
}
