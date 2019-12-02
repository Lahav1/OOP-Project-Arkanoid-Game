package levels;
import java.util.List;
import sprites.Block;
import sprites.Sprite;
import sprites.Velocity;

/**
 * This interface includes all the function a "level information" object must implement.
 * @author Lahav Amsalem 204632566
 */
public interface LevelInformation {

    /**
     * function determines the amount of balls that are going to take part.
     * @return amount of balls.
     */
    int numberOfBalls();

    /**
     * function determines the initial velocities of the balls.
     * list size is compatible to the number of balls above.
     * @return list of velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * function determines the speed of the paddle.
     * @return speed of paddle.
     */
    int paddleSpeed();

    /**
     * function determines the width of the paddle.
     * @return width of paddle.
     */
    int paddleWidth();


    /**
     * function determines the name of the level.
     * @return level's name in a string.
     */
    String levelName();


    /**
     * function determines the look of the background.
     * @return level's name in a string.
     */
    Sprite getBackground();

    /**
     * function returns a full list of the blocks in the level, including all the details (size, color, location).
     * @return level's initial block list.
     */
    List<Block> blocks();

    /**
     * function returns the number of blocks we need to remove.
     * @return how many blocks are collidable.
     */
    int numberOfBlocksToRemove();
}
