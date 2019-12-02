package game;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * This class is used for creation of the animation loop part, to make it more flexible.
 * @author Lahav Amsalem 204632566
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    private double dt;

    /**
     * Constructor creates an animation runner.
     * @param gui1 - gui.
     * @param framesPerSecond1 - how many frames the animation will show in one sec.
     * @param sleeper1 - sleeper.
     */
    public AnimationRunner(GUI gui1, int framesPerSecond1, Sleeper sleeper1) {
        this.gui = gui1;
        this.framesPerSecond = framesPerSecond1;
        this.sleeper = sleeper1;
        this.dt = 1.0 / (double) this.framesPerSecond;
    }

    /**
     * accessor to gui.
     * @return gui
     */
    public GUI getGUI() {
        return this.gui;
    }

    /**
     * function prepares and runs the animation loop.
     * @param animation - the animation we want the runner to run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        //when running = false -> shouldstop = true.
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, this.dt);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}