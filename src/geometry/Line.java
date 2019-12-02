package geometry;
import java.util.List;

/**
 * This class uses x,y values of two points, creates a line and compares it to other lines.
 * @author Lahav Amsalem 204632566
 */
public class Line {
    private Point start = null;
    private Point end = null;

    /**
     * Constructor #1 creates a line given start and end points.
     * @param start - first point on the line.
     * @param end - second point on the line.
     */
    public Line(Point start, Point end) {
        this.start =  start;
        this.end = end;
    }

    /**
     * Constructor #2 creates a line using x,y values of to points to create end and start points first.
     * @param x1 - x of first point.
     * @param y1 - y of first point.
     * @param x2 - x of second point.
     * @param y2 - y of second point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);
        this.start = point1;
        this.end = point2;
    }

    /**
     * uses the start and end point values to calculate the length of the line.
     * @return length of line
     */
    public double length() {
        //find the difference between start x to end x
        double dx = (this.start.getX() - this.end.getX());
        //find the difference between start y to end y
        double dy = (this.start.getY() - this.end.getY());
        //use sqrt to find the square of difx^2+dify^2
        double length = Math.sqrt((dx * dx) + (dy * dy));
        return length;
    }

    /**
     * uses the start and end point values to find the middle of the line.
     * @return middle point of the line
     */
    public Point middle() {
        //find the middle of the x and y by dividing x and y by 2, and create a point of it.
        double midX = ((this.start.getX() + this.end.getX()) / 2);
        double midY = ((this.start.getY() + this.end.getY()) / 2);
        Point middle = new Point(midX, midY);
        //return the middle point.
        return middle;
    }

    /**
     * compares 2 lines.
     * @param other - second line
     * @return true if the lines are equal, false if not equal
     */
    public boolean equals(Line other) {
        boolean isEqual;
        //to find if the lines are equal, we need to compare every possible pair (point of each line)
        isEqual = (((this.start.getX() == other.start.getX()) && (this.end.getX() == other.end.getX()))
                || ((this.start.getX() == other.end.getX()) && (this.end.getY() == other.start.getY())));
        return isEqual;
    }

    /**
     * function checks if two lines intersect, using intersectionWith function.
     * @param other line
     * @return true if the lines intersect, false if not.
     */
    public boolean isIntersecting(Line other) {
        //if the intersectionwith function returned null, it means there is no intersection
        if (this.intersectionWith(other) == null) {
            //change temp variable to false
            return false;
        }
        return true;
    }

    /**
     * function returns the intersection point of two lines, or null if they don't intersect.
     * @param other line
     * @return point of intersection (or null in case they dont intersect).
     */
    public Point intersectionWith(Line other) {
        //find d (denominator).
        double d = (other.end().getY() - other.start().getY()) * (this.end().getX() - this.start().getX())
                - (other.end().getX() - other.start().getX())  * (this.end().getY() - this.start().getY());
        //find a and b.
        double a = ((other.end().getX() - other.start().getX()) * (this.start().getY() - other.start().getY())
                - (other.end().getY() - other.start().getY()) * (this.start().getX() - other.start().getX())) / d;
        double b = ((this.end().getX() - this.start().getX()) * (this.start().getY() - other.start().getY())
                - (this.end().getY() - this.start().getY()) * (this.start().getX() - other.start().getX())) / d;
        //if d = 0, the lines are parallel, so they dont intersect (unless their ends touch each other).
        if (d == 0) {
            //if the end of the first line is identical to the start of the other, the lines intersect there
            if ((this.end.getX() == other.start.getX()) && (this.end.getY() == other.start.getY())) {
                Point intersection = new Point(this.end.getX(), this.end.getY());
                return intersection;
            }
            //if the start of the first line is identical to the end of the other, the lines intersect there
            if ((this.start.getX() == other.end.getX()) && (this.start.getY() == other.end.getY())) {
                Point intersection = new Point(this.start.getX(), this.start.getY());
                return intersection;
            }
            //if lines are parallel and do not intersect in their ends, return null
            return null;
        }
        //if a is between 0-1 and b is between 0-1, lines intersect.
        if ((a >= 0 && a <= 1) && (b >= 0 && b <= 1)) {
            //find x coordinate of intersection
            double intersectionX = this.start().getX() + a * (this.end().getX()  - this.start().getX());
            //find y coordinate of intersection
            double intersectionY = this.start().getY() + a * (this.end().getY() - this.start().getY());
            //create the intersection point and return it.
            Point intersection = new Point(intersectionX, intersectionY);
            return intersection;
        }
        //defaultly return null (means no intersection was found).
        return null;
    }

    /**
     * checks if the line intersects with a given rectangle and finds the closest intersection point to start point.
     * NOTE: we assume that the user gives the right starting point (and doesn't replace start and end).
     * @param rect - the rectangle we check intersection with.
     * @return closest intersection to the start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //create an intersection point list of this line and the inserted rectangle.
        List<Point> intersectionPts = rect.intersectionPoints(this);
        //if there is a single intersection point, return it.
        if (intersectionPts.size() == 1) {
            return intersectionPts.get(0);
        }
        //if there are two intersection points, the closest point to start point will be returned.
        if (intersectionPts.size() == 2) {
            //calculate the distance between the start point to both intersection points.
            double distance0 = this.start.distance(intersectionPts.get(0));
            double distance1 = this.start.distance(intersectionPts.get(1));
            //compare the distances and return the closer point.
            //if the distances are equal (randomally) return the first point.
            if (distance0 < distance1) {
                return intersectionPts.get(0);
            }
            return intersectionPts.get(1);
        }
        //if there is no intersection at all, return null.
        return null;
    }

    /**
     * @return accessor to start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * @return accessor to end point of the line.
     */
    public Point end() {
        return this.end;
    }

}