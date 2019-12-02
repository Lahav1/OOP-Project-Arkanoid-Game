package listeners;
import sprites.Ball;
import sprites.Block;

/**
 * This class is used to create a listener which prints a message with details of a block when it got hit by the ball.
 * @author Lahav Amsalem 204632566
 */
public class PrintingHitListener implements HitListener {

    /**
     * implementation of hitEvent function from HitListener interface.
     * this listener prints a message of the block that was hit and it's hitpoints count before hit.
     * @param beingHit the blocks that is being hit.
     * @param hitter - the specific ball that has hit the block.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block with " + beingHit.getHitPoints() + " points was hit.");
    }
}
