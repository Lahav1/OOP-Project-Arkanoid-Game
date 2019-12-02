package listeners;

/**
 * interface for collidable objects type that are able to notify they were hit by the ball.
 * @author Lahav Amsalem 204632566
 */

public interface HitNotifier {

    /**
     * function that adds a listener to a notifier's listeners list.
     * @param hl the listener we want to add.
     */
    void addHitListener(HitListener hl);

    /**
     * function that removes a listener to a notifier's listeners list.
     * @param hl the listener we want to remove.
     */
    void removeHitListener(HitListener hl);
}
