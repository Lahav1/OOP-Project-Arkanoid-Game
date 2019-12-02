package sprites;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import collidables.Collidable;
import game.GameLevel;

/**
 * This class is used for creation of a collidable moving keyboard-controlled paddle on the screen.
 * @author Lahav Amsalem 204632566
 */
public class Paddle implements Collidable, Sprite {
    private Rectangle rectangle;
    private java.awt.Color color;
    private biuoop.KeyboardSensor keyboard;
    private int upperFrame;
    private int lowerFrame;
    private int rightFrame;
    private int leftFrame;
    private Line region1;
    private Line region2;
    private Line region3;
    private Line region4;
    private Line region5;
    private double speed;

    /**
     * Constructor creates a paddle, given a rectangle and color.
     * @param rectangle - point, height and width
     * @param color  - color of the block.
     * @param padSpeed  - not actual speed but the "dx" of paddle.
     */
    public Paddle(Rectangle rectangle, java.awt.Color color, double padSpeed) {
        this.rectangle = rectangle;
        this.color = color;
        this.speed = padSpeed;
        this.updateKeyboardSensor(keyboard);
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
     * function for updating the frame borders to the ball.
     * @param keysensor - keyboard sensor
     */
    public void updateKeyboardSensor(biuoop.KeyboardSensor keysensor) {
        this.keyboard = keysensor;
    }

    /**
     * implementation of getCollisionRectangle function from Collidable interface.
     * @return the rectangle that the block is made of.
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }


    /**
     * function uses the color we inserted and the rectangle, and draws the paddle on a given surface.
     * @param surface the surface where we draw the block
     */
    public void drawOn(DrawSurface surface) {
        //create a color variable and set the drawsurface to this color.
        java.awt.Color paddleColor = this.color;
        int x = (int) this.rectangle.getUpperLeft().getX();
        int y = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();
        //draw a circle on the surface using the ball's details
        surface.setColor(color);
        surface.fillRectangle(x, y, width, height);
        surface.setColor(Color.BLACK);
        surface.drawRectangle(x, y, width, height);
    }

    /**
     * add the block to the game.
     * @param g - the game we want to add paddle to.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * calls the onestepleft function in rectangle class, which changes the upperleft point and then updates the rest
     * of the points and also updates the lines.
     * @param dx - the location change rate of paddle.
     * @param dt - the time change rate.
     */
    public void moveLeft(double dx, double dt) {
        this.rectangle.oneStepLeft(dx, dt);
    }

    /**
     * calls the onestepright function in rectangle class, which changes the upperleft point and then updates the rest
     * of the points and also updates the lines.
     * @param dx - the location change rate of paddle.
     * @param dt - the time change rate.
     */
    public void moveRight(double dx, double dt) {
        this.rectangle.oneStepRight(dx, dt);
    }

    /**
     * implementation of sprite interface.
     * if the left key was pressed, call the moveleft function to move the paddle to the left.
     * if the right key was pressed, call the moveright function to move the paddle to the right.
     * @param dt - the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        // if the left key was pressed, call the moveleft function to move the paddle to the left.
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            if (this.rectangle.getUpperLeft().getX() - this.speed * dt > (this.leftFrame + 25)) {
                this.moveLeft(this.speed, dt);
            }
        }
        // if the right key was pressed, call the moveright function to move the paddle to the right.
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            if (this.rectangle.getUpperRight().getX() + this.speed * dt < (this.rightFrame - 25)) {
                this.moveRight(this.speed, dt);
            }
        }
        return;
    }

    /**
     *function that helps to set the regions of the paddle's upper line.
     */
    public void setRegions() {
        //length of each region is the length of whole upper line / 5.
        double regionLength = this.getCollisionRectangle().getUpper().length() / 5;
        Line upperLine = this.getCollisionRectangle().getUpper();
        //find the points by adding (add 0.00001 to start the new region a little bit after the previous)
        Point first = upperLine.start();
        Point second = new Point((first.getX() + regionLength) + 0.00001, first.getY());
        Point third = new Point((first.getX() + 2 * regionLength) + 0.00001, first.getY());
        Point fourth = new Point((first.getX() + 3 * regionLength) + 0.00001, first.getY());
        Point fifth = new Point((first.getX() + 4 * regionLength) + 0.00001, first.getY());
        Point sixth = upperLine.end();
        //create the lines which represent the regions
        this.region1 = new Line(first, second);
        this.region2 = new Line(second, third);
        this.region3 = new Line(third, fourth);
        this.region4 = new Line(fourth, fifth);
        this.region5 = new Line(fifth, sixth);
    }

    /**
     * implementation of hit function from Collidable interface.
     * after we already know the ball collides with the paddle, return an updated velocity.
     * @param hitter the ball that hitts the paddle.
     * @param collisionPoint the point of collision.
     * @param currentVelocity - the velocity of the ball when it reaches the block.
     * @return the new updated velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity newV = new Velocity(currentVelocity.getDX(), currentVelocity.getDY());
        this.setRegions();
        double ballSpeed = Math.sqrt((currentVelocity.getDX() * currentVelocity.getDX())
                                 + (currentVelocity.getDY() * currentVelocity.getDY()));
        //change the ball's direction in competence to the region it has hit, using fromangleandspeed function.
        if (collisionPoint.isPointOnLineSegment(this.region1)) {
            newV = newV.fromAngleAndSpeed(300, ballSpeed);
            return newV;
        }
        if (collisionPoint.isPointOnLineSegment(this.region2)) {
            newV = newV.fromAngleAndSpeed(330, ballSpeed);
            return newV;
        }
        //if the ball hit the middle region, change it's vertical direction
        if (collisionPoint.isPointOnLineSegment(this.region3)) {
            newV.update(currentVelocity.getDX(), (currentVelocity.getDY() * -1));
            return newV;
        }
        if (collisionPoint.isPointOnLineSegment(this.region4)) {
            newV = newV.fromAngleAndSpeed(30, ballSpeed);
            return newV;
        }
        if (collisionPoint.isPointOnLineSegment(this.region5)) {
            newV = newV.fromAngleAndSpeed(60, ballSpeed);
            return newV;
        }
        if ((collisionPoint.isPointOnLineSegment(this.rectangle.getRight()))
                || (collisionPoint.isPointOnLineSegment(this.rectangle.getRight()))) {
            newV.update(currentVelocity.getDX() * -1, (currentVelocity.getDY()));
            return newV;
        }
        //return the velocity after it was updated
        return newV;
    }

    /**
     * remove the paddle from game (usually, will be called when the player lost 1 life).
     * @param gameLevel - the game we want to remove the paddle from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

}
