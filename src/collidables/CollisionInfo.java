package collidables;
import geometry.Line;
import geometry.Point;

/**
 * This class is used to save info of collisions that occure.
 * @author Lahav Amsalem 204632566
 */
public class CollisionInfo {
    private Line trajectory = null;
    private Collidable collidable = null;

    /**
     * Constructor creates a collision information record for a new potential collision that has occured.
     * @param trajectory - the current path of the ball.
     * @param c - the collidable we want to check colliion with.
     */
    public CollisionInfo(Line trajectory, Collidable c) {
        this.trajectory = trajectory;
        this.collidable = c;
    }

    /**
     * finds the intersection point of a collidable and a ball's path line.
     * if there is no collision, it will return null.
     * @return the point of the potential collision
     */
    public Point collisionPoint() {
        return this.trajectory.closestIntersectionToStartOfLine(this.collidable.getCollisionRectangle());
    }

    /**
     * @return the shape of the object the ball has collided with.
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}