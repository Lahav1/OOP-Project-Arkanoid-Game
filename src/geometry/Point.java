package geometry;

/**
 * This class takes x,y value a point, creates it and compares it to other points.
 * @author Lahav Amsalem 204632566
 */
public class Point {
    //each point will contain x and y cordinates.
    private double x;
    private double y;

    /**
     * Constructor creates a point given x and y values.
     * @param x - x cordinate.
     * @param y - y cordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * function takes another point and returns the distance between them.
     * @param other - a second point to compare.
     * @return distance - the calculated distance between the two points
     */
    public double distance(Point other) {
        double dx = this.x - other.getX();
        double dy = this.y - other.getY();
        //use sqrt to find the square of dx^2+dy^2
        double distance = Math.round(Math.sqrt((dx * dx) + (dy * dy)));
        return distance;
    }

    /**
     * check if a point lays on a line segment by calculating the sum of distances of point from line edges.
     * @param line the line we want to check if the point lays on.
     * @return if point is contained in line.
     */
    public boolean isPointOnLineSegment(Line line) {
        //create points of start and end of the line
        Point lineStart = line.start();
        Point lineEnd = line.end();
        //compare sum of distances of point from line edges to the length of line
        return ((this.distance(lineStart) + this.distance(lineEnd)) == lineStart.distance(lineEnd));

    }

    /**
     * function takes another point and returns if the point equals to the first point or not.
     * @param other - a second point to compare.
     * @return isEquals - equal or not
     */
    public boolean equals(Point other) {
        //temp variable
        boolean isEquals;
        //if one of the points is null automatically return false
        if (other == null) {
            return false;
        }
        //compare the two lines
        isEquals = ((this.x == other.getX()) && (this.y == other.getY()));
        //return the boolean outcome
        return isEquals;
    }

    /** function updates x,y values for existing point.
     * @param newX - new x value.
     * @param newY - new y value.
     */
    public void updatePoint(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }

    /**
     * @return x value of the point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * return the y value of the point.
     * @return y value of the point.
     */
    public double getY() {
        return this.y;
    }
}



