package collidables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import geometry.Line;
import geometry.Point;

/**
 * This class holds a list of all of the collidable objects on the screen.
 * @author Lahav Amsalem 204632566
 */
public class GameEnvironment {
    //a list that includes all of the objects that the ball can collide with
    private List<Collidable> collidableList = new ArrayList<Collidable>();


    /**
     * Constructor creates a game environment using a list of collidables.
     * @param collidableList list of colliables.
     */
    public GameEnvironment(List<Collidable> collidableList) {
        this.collidableList = collidableList;
    }

    /**
     * function adds a new collidable to the collidable list.
     * @param c a new collidable we want to add to the game.
     */
    public void addCollidable(Collidable c) {
        collidableList.add(c);
    }

    /**
     * function takes the current ball's trajectory and checks if it collides with any collidable.
     * it summs the possible collisions and finds the closest collision to the ball, because the rest don't matter.
     * @param trajectory - the current path of the ball.
     * @return the info of the collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //create a list of possible collisions and a parallel list of distance of collision from ball.
        List<CollisionInfo> possibleCollisions = new ArrayList<CollisionInfo>();
        List<Double> collisionDistances = new ArrayList<Double>();
        //add every collisioninfo and distance of a collidable with the given trajectory to both lists.
        for (int i = 0; i < this.collidableList.size(); i++) {
            //create a new temporary collision info for the trajectory with each collidable.
            CollisionInfo tempCollision = new CollisionInfo(trajectory, collidableList.get(i));
            //find temp collision point
            Point tempCollisionPoint = tempCollision.collisionPoint();
            //if a possible collision found, procceed
            if (tempCollisionPoint != null) {
                //add the collision to the possible collision list
                possibleCollisions.add(tempCollision);
                //create a temporary distance between current point of collision and the ball
                double tempDistance = trajectory.start().distance(tempCollisionPoint);
                //add the distance in the same index
                collisionDistances.add(tempDistance);
            }
        }
        //if there are no possible collisions, return null.
        if (possibleCollisions.isEmpty()) {
            return null;
        }
        //find the minimum distance's value in the list.
        double minDistance = Collections.min(collisionDistances);
        //find the index of min distance in the distances list.
        int minDistanceIndex = collisionDistances.indexOf(minDistance);
        //return the collisioninfo with the suitable index.
        return possibleCollisions.get(minDistanceIndex);
    }

    /**
     * remove a collidable from the collidables list.
     * @param c - the collidable we want to remove.
     */
    public void removeCollidable(Collidable c) {
        this.collidableList.remove(c);
    }
}