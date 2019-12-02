package sprites;
import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import collidables.Collidable;
import collidables.CollisionInfo;
import collidables.GameEnvironment;
import game.GameLevel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import listeners.HitListener;

/**
 * This class is used for constructing a ball, returning it's paramaters and drawing it.
 * @author Lahav Amsalem 204632566
 */
public class Ball implements Sprite {
    private double r = 0;
    private Point center;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment ge;
    private int upperFrame;
    private int lowerFrame;
    private int rightFrame;
    private int leftFrame;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();;

    /**
     * Constructor creates a ball given x,y of center, radius and color.
     *
     * @param center - x,y of the center of the circle.
     * @param r      - radius of the circle.
     * @param color  - color of the circle.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.r = r;
        this.color = color;
        this.ge = ge;
    }

    /**
     * function updates velocity to ball given the velocity itself.
     * @param v the inserted velocity we want to update
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * function updates velocity to ball after creating it, using inserted dx and dy.
     * @param dx the inserted dx.
     * @param dy the inserted dy.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * set a new game environment to the ball.
     * @param newGE - new game environment
     */
    public void setGameEnvironment(GameEnvironment newGE) {
        this.ge = newGE;
    }

    /**
     * function uses the color we inserted and the size and center point, and draws the circle on a given surface.
     * @param surface the surface where we draw the circle
     */
    public void drawOn(DrawSurface surface) {
        //create a color variable and set the drawsurface to this color.
        java.awt.Color drawColor = this.getColor();
        surface.setColor(drawColor);
        //draw a circle on the surface using the ball's details
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
        surface.setColor(Color.BLACK);
        surface.drawCircle(this.getX(), this.getY(), this.getSize());
    }

    /**
     * function for updating a ball's center point.
     * @param newX - new x value
     * @param newY - new y value
     */
    public void updateCenter(double newX, double newY) {
        Point newCenter = new Point(newX, newY);
        this.center = newCenter;
    }

    /**
     * function for updating the frame borders to the ball.
     * @param upper - top frame border
     * @param lower - bottom frame border
     * @param right - right frame border
     * @param left - left frame border
     */
    public void updateFrame(int upper, int lower, int right, int left) {
        this.upperFrame = upper;
        this.lowerFrame = lower;
        this.rightFrame = right;
        this.leftFrame = left;
    }

    /**
     * function adds the velocity's dx and dy to the ball's x and y so it moves one step away.
     * @param dt - the amount of seconds passed since the last call.
     */
    public void moveOneStep(double dt) {
        // if the location is outside the frame, force it inside.
        this.fixLocation();
        //create a new line which represents the ball's trajectory if there were no collidables on it's path
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center, dt));
        //get the closest collision's info of the ball and closest object in trajectory
        CollisionInfo collisionInfo = this.ge.getClosestCollision(trajectory);
        if (collisionInfo != null) {
            //find the closest collision point
            Point collisionPoint = collisionInfo.collisionPoint();
            //x and y values of collision (for convenience)
            double collisionX = collisionInfo.collisionPoint().getX();
            double collisionY = collisionInfo.collisionPoint().getY();
            //find the collidable that the ball is going to collide with.
            Collidable collisionObject = collisionInfo.collisionObject();
            //find the rectangle that the ball is going to collide with.
            Rectangle collisionRectangle = collisionInfo.collisionObject().getCollisionRectangle();
            //when a collision is detected, move ball's center first to avoid falling off the frame.
            //ball collides with left side of block
            if (collisionX == collisionRectangle.getUpperLeft().getX()) {
                Point updated = new Point(collisionX - this.r, this.center.getY());
                this.center = updated;
            }
            //ball collides with right side of block
            if (collisionX == collisionRectangle.getUpperRight().getX()) {
                Point updated = new Point(collisionX + this.r, this.center.getY());
                this.center = updated;
            }
            //ball collides with upper side of block
            if (collisionY == collisionRectangle.getUpperLeft().getY()) {
                Point updated = new Point(this.center.getX(), collisionY - this.r);
                this.center = updated;
            }
            //ball collides with lower side of block
            if (collisionY == collisionRectangle.getLowerLeft().getY()) {
                Point updated = new Point(this.center.getX(), collisionY + this.r);
                this.center = updated;
            }
            // after moving the ball from the "danger zone",
            // call hit function of the collision object to update the velocity
            this.velocity = collisionObject.hit(this, collisionPoint, this.velocity);
        }
        // by default, if there is no collision, just move the ball according to its' velocity.
        this.center = this.velocity.applyToPoint(this.center, dt);
    }

    /**
     * if for some reason the ball getts to an illegal point (mostly inside the frame blocks), fix the location.
     */
    public void fixLocation() {
        double ballx = this.center.getX();
        double bally = this.center.getY();
        // the sizes of frame blocks we will use
        double rightBlockWidth = 25;
        double leftBlockWidth = 25;
        double upperBlockHeight = 45;
        // if the ball is out of the frame, force its location to be inside frame again.
        // if ball went over the right frame, force it back.
        if (this.center.getX() > this.rightFrame - rightBlockWidth) {
            ballx = this.rightFrame - rightBlockWidth - 2 * this.r;
        }
        // if ball went over the left frame, force it back.
        if (this.center.getX() < this.leftFrame + leftBlockWidth) {
            ballx = this.leftFrame + leftBlockWidth + 2 * this.r;
        }
        // if ball went over the upper frame, force it back.
        if (this.center.getY() < this.upperFrame + upperBlockHeight) {
            bally = this.upperFrame + upperBlockHeight + 2 * this.r;
        }
        // if ball went over the lower frame, force it back.
        if (this.center.getY() > this.lowerFrame) {
            bally = this.lowerFrame - this.r;
        }
        // update the center if needed.
        Point updatedCenter = new Point(ballx, bally);
        this.center = updatedCenter;
    }

    /**
     * implementation of sprite interface.
     * function activates moveonestep.
     * @param dt - the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * add the ball to the game.
     * @param g - the game we want to add the ball to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * @return x value of the center.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return y value of the center.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return circle's center.
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * @return radius of the circle.
     */
    public int getSize() {
        return (int) this.r;
    }

    /**
     * @return color of the circle.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * @return accessor to velocity.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * remove a ball from game (usually, will be called when ball goes out of the lower frame).
     * @param gameLevel - the game we want to remove the block from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}
