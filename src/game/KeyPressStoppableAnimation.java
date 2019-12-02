package game;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * This class is used for decorating a key press stoppable animation, such as pause/end screen and
 * a highscores table.
 * @author Lahav Amsalem 204632566
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor keyboardSensor;
    private String pressedKey;
    private Animation animationScreen;
    private boolean stop;
    private boolean isAlreadyPressed;


    /**
     * Constructor creates a key press stoppable animation.
     * @param sensor - keyboard sensor.
     * @param key - the key that was pressed
     * @param animation - the animation we want to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboardSensor = sensor;
        this.pressedKey = key;
        this.animationScreen = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    /**
     * organizes a single frame of the animation.
     * @param d - drawsurface.
     * @param dt ignored.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // do the graphics part of the screen using the animation's dooneframe function.
        this.animationScreen.doOneFrame(d, dt);
        // use isalreadypressed to determine if the keypress happened during the runtime of the animation.
        // if the specific key was pressed, exit the animation.
        if (this.keyboardSensor.isPressed(this.pressedKey)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * return true if the animation should stop and false if not.
     * @return should stop or not.
     */
    public boolean shouldStop() {
        return this.stop; }
}