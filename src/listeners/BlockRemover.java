package listeners;
import sprites.Ball;
import sprites.Block;
import sprites.Counter;
import game.GameLevel;

/**
 * This class is used to create a listener which removes a block when it got hit by the ball.
 * @author Lahav Amsalem 204632566
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Constructor creates a block remover.
     * @param gameLevel - the game we want to count its' blocks.
     * @param remainingBlocks  - amount of remaining blocks.
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * implementation of hitEvent function from HitListener interface.
     * this listener removes the block when it recognizes a hit of a block with his last hit point.
     * @param beingHit the blocks that is being hit.
     * @param hitter - the specific ball that has hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //if the block has it's last hitpoint and it was hit, it will be removed from the game.
        if (beingHit.getHitPoints() == 1) {
            //before removing the block we will remove the listener from the block.
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.gameLevel);
            //decrease the count of the blocks since we removed the block.
            this.remainingBlocks.decrease(1);
        }
    }
}
