package geometry;
import java.util.ArrayList;
import java.util.List;

/**
 * This class uses a point, width and height and creates a rectangle in the requested size, while it's upper left corner
 * is the inserted point.
 * @author Lahav Amsalem 204632566
 */
public class Rectangle {
    private Point upperLeft = null;
    private Point upperRight = null;
    private Point lowerLeft = null;
    private Point lowerRight = null;
    private Line upper = null;
    private Line lower = null;
    private Line right = null;
    private Line left = null;
    private double width = 0;
    private double height = 0;

    /**
     * Constructor creates a rectangle given a point, and size.
     * @param upperLeft upper left corner of the rectangle.
     * @param width - width of the rectangle.
     * @param height - height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft =  upperLeft;
        this.width = width;
        this.height = height;
        //create the rectangle's points and lines for future usage.
        this.createPoints();
        this.createLines();
    }

    /**
     * function finds the other 3 corners of the rectangle using its' upperleft point and sizes.
     */
    public void createPoints() {
        this.upperRight = new Point(this.upperLeft.getX() + this.getWidth(), this.upperLeft.getY());
        this.lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.getHeight());
        this.lowerRight = new Point(this.upperLeft.getX() + this.getWidth(),
                this.upperLeft.getY()  + this.getHeight());
    }

    /**
     * function uses the points of the rectangle's corneres to create 4 lines.
     */
    public void createLines() {
        this.upper = new Line(upperLeft, upperRight);
        this.lower = new Line(lowerLeft, lowerRight);
        this.right = new Line(upperRight, lowerRight);
        this.left = new Line(upperLeft, lowerLeft);

    }

    /**
     * Return a (possibly empty) List of intersection points.
     * @param line specified line.
     * @return a list of intersection points.
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPts = new ArrayList<Point>();
        //for every side of the rectangle, check if it intersects with the line.
        //if it does, add the intersection point to the array.
        if (line.isIntersecting(this.upper)) {
            intersectionPts.add(line.intersectionWith(this.upper));
        }
        if (line.isIntersecting(this.lower)) {
            intersectionPts.add(line.intersectionWith(this.lower));
        }
        if (line.isIntersecting(this.right)) {
            intersectionPts.add(line.intersectionWith(this.right));
        }
        if (line.isIntersecting(this.left)) {
            intersectionPts.add(line.intersectionWith(this.left));
        }
        //return the intersection points list.
        return intersectionPts;
    }

    /**
     * moves the rectantle one step to the left.
     * @param dx - the location change rate of paddle.
     * @param dt - the time change rate of the game.
     */
    public void oneStepLeft(double dx, double dt) {
        this.upperLeft = new Point(this.upperLeft.getX() - dx * dt, this.upperLeft.getY());
        this.createPoints();
        this.createLines();
    }

    /**
     * moves the rectantle one step to the right.
     * @param dx - the location change rate of paddle.
     * @param dt - the time change rate of the game.
     */
    public void oneStepRight(double dx, double dt) {
        this.upperLeft = new Point(this.upperLeft.getX() + dx * dt, this.upperLeft.getY());
        this.createPoints();
        this.createLines();
    }

    /**
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return upper right point of the rectangle.
     */
    public Point getUpperRight() {
        return this.upperRight;
    }

    /**
     * @return lower right point of the rectangle.
     */
    public Point getLowerRight() {
        return this.lowerRight;
    }

    /**
     * @return lower left point of the rectangle.
     */
    public Point getLowerLeft() {
        return this.lowerLeft;
    }

    /**
     * @return upper side of the rectangle.
     */
    public Line getUpper() {
        return this.upper;
    }

    /**
     * @return lower side of the rectangle.
     */
    public Line getLower() {
        return this.lower;
    }

    /**
     * @return right side of the rectangle.
     */
    public Line getRight() {
        return this.right;
    }

    /**
     * @return left side of the rectangle.
     */
    public Line getLeft() {
        return this.left;
    }
}