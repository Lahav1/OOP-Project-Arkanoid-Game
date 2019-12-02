package collidables;
import geometry.Point;
import geometry.Rectangle;
import sprites.Ball;
import sprites.Velocity;

/**
 * interface for collidable objects in the game.
 * @author Lahav Amsalem 204632566
 */
public interface Collidable {

    /**
     * @return the collision object.
     */
    Rectangle getCollisionRectangle();
    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * @param collisionPoint the point of the collision.
     * @param currentVelocity the previous velocity of the ball before the collision.
     * @param hitter the ball that hit the collidable.
     * @return  the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}