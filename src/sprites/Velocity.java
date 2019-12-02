package sprites;
import geometry.Point;

/**
 * This class is used for setting a velocity to a bouncing ball.
 * @author Lahav Amsalem 204632566
 */
public class Velocity {
    private double dx = 0;
    private double dy = 0;

    /**
     * Constructor creates a point given x and y values.
     * @param dx - constant change in x.
     * @param dy - constant change in y.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * function uses speed and angle to calculate dx and dy and creates a new velocity, which is returned from function.
     * @param angle the inserted angle
     * @param speed the inserted speed
     * @return v - the velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double angleRad = Math.toRadians(angle);
        //find dx and dy using the sin and cos of the angle, multiplied by speed
        double dx = speed * Math.sin(angleRad);
        double dy = -1 * speed * Math.cos(angleRad);
        //create a velocity using new dx and dy, and then return it
        Velocity v = new Velocity(dx, dy);
        return v;
    }

    /**
     * function creates a new temp point with the added dx and dy and returns it.
     * @param p - point.
     * @param dt - the amount of seconds passed since the last call.
     * @return an updated point after adding dx and dy.
     */
    public Point applyToPoint(Point p, double dt) {
        Point updatedLocation = new Point(p.getX() + this.dx * dt, p.getY() + this.dy * dt);
        return updatedLocation;
    }

    /**
     * function updates new velocity using new given dx and dy.
     * @param newDX - new dx value
     * @param newDY - new dy value
     */
    public void update(double newDX, double newDY) {
        this.dx = newDX;
        this.dy = newDY;
    }

    /**
     * @return accessor to dx.
     */
    public double getDX() {
        return this.dx;
    }

    /**
     * @return accessor to dy.
     */
    public double getDY() {
        return this.dy;
    }
}
