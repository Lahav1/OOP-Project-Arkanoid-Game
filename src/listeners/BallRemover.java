package listeners;
import sprites.Ball;
import sprites.Block;
import sprites.Counter;
import game.GameLevel;

/**
 * This class is used to create a listener which removes a ball when it went off the screen.
 * @author Lahav Amsalem 204632566
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Constructor creates a ball remover.
     * @param gameLevel - the game we want to count its' balls.
     * @param remainingBalls  - amount of remaining balls.
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * implementation of hitEvent function from HitListener interface.
     * this listener removes the ball when it finds out it's out of frame.
     * @param beingHit the blocks that is being hit.
     * @param hitter - the specific ball that has hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //if the block has it's last hitpoint and it was hit, it will be removed from the game.
        //remove the hitting ball from the game.
        hitter.removeFromGame(this.gameLevel);
        //decrease the count of the blocks since we removed the block.
        this.remainingBalls.decrease(1);
        }

}
