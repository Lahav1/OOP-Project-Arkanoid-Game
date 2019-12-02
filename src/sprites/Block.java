package sprites;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import java.util.TreeMap;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import collidables.Collidable;
import game.GameLevel;
import levelsio.BlockOutline;
import listeners.HitListener;
import listeners.HitNotifier;
import levelsio.Fill;

/**
 * This class is used for creation of collidable blocks on the screen.
 * @author Lahav Amsalem 204632566
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle;
    private java.awt.Color color;
    private int hitPoints;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();;
    private BlockOutline stroke;
    private Fill fill;
    private TreeMap<Integer, Fill> fillByHP = null;
    private double width;
    private double height;

    /**
     * Constructor creates a block given x and y coordinates of upper left point.
     * @param x - upper left x.
     * @param y  - upper left y.
     */
    public Block(double x, double y) {
        Point point = new Point(x, y);
        this.rectangle = new Rectangle(point, 0, 0);
        this.fillByHP = new TreeMap<>();
    }

    /**
     * Constructor creates a block given a rectangle.
     * @param r - rectangle.
     */
    public Block(Rectangle r) {
        this.rectangle = r;
        this.fillByHP = new TreeMap<>();
    }

    /**
     * Constructor creates a block given a rectangle and color.
     * @param rectangle - point, height and width
     * @param color  - color of the block.
     */
    public Block(Rectangle rectangle, java.awt.Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.fillByHP = new TreeMap<>();
    }

    /**
     * Constructor creates a block given a rectangle and color.
     * @param rectangle - point, height and width
     * @param f  - fill.
     */
    public Block(Rectangle rectangle, Fill f) {
        this.rectangle = rectangle;
        this.fill = f;
    }

    /**
     * Constructor.
     * @param x - upper left x.
     * @param y  - upper left y.
     * @param w - width.
     * @param h  - height.
     * @param fills - tree map of fills.
     * @param hp - hit points.
     * @param outline - stroke.
     */
    public Block(double x, double y, double w, double h,
                 TreeMap<Integer, Fill> fills, BlockOutline outline, int hp) {
        Point upLeft = new Point(x, y);
        this.rectangle = new Rectangle(upLeft, w, h);
        this.fillByHP = fills;
        this.stroke = outline;
        this.hitPoints = hp;
    }

    /**
     * Constructor.
     * @param x - upper left x.
     * @param y  - upper left y.
     * @param w - width.
     * @param h  - height.
     * @param fills - tree map of fills.
     * @param hp - hit points.
     */
    public Block(double x, double y, double w, double h,
                 TreeMap<Integer, Fill> fills, int hp) {
        Point upLeft = new Point(x, y);
        this.rectangle = new Rectangle(upLeft, w, h);
        this.fillByHP = fills;
        this.hitPoints = hp;
    }

    /**
     * implementation of getCollisionRectangle function from Collidable interface.
     * @return the rectangle that the block is made of.
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * implementation of hit function from Collidable interface.
     * after we already know the ball collides with the block, return an updated velocity.
     * @param hitter - the ball that hitts the block.
     * @param collisionPoint the point of collision.
     * @param currentVelocity - the velocity of the ball when it reaches the block.
     * @return the new updated velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //create the lines of the rectangle
        Line upper = this.rectangle.getUpper();
        Line lower = this.rectangle.getLower();
        Line right = this.rectangle.getRight();
        Line left = this.rectangle.getLeft();
        // dx and dy of current velocity
        double dx = currentVelocity.getDX();
        double dy = currentVelocity.getDY();
         //if the intersection point is on the upper or lower side, change direction of dy
         if ((collisionPoint.isPointOnLineSegment(upper)) || (collisionPoint.isPointOnLineSegment(lower))) {
             dy = -1 * dy;
         }
         if ((collisionPoint.isPointOnLineSegment(right)) || (collisionPoint.isPointOnLineSegment(left))) {
             dx = -1 * dx;
         }
         // notify the listeners that the block was hit.
        this.notifyHit(hitter);
        // decrease the hitpoints of the block
        this.hitPoints = this.hitPoints - 1;
        // return the updated velocity
        return new Velocity(dx, dy);
    }

    /**
     * function uses the color we inserted and the rectangle, and draws the block on a given surface.
     * @param surface the surface where we draw the block
     */
    public void drawOn(DrawSurface surface) {
        int hpLeft = this.getHitPoints();
        Rectangle blockShape = this.rectangle;
        if ((this.fillByHP != null) && (this.fillByHP.containsKey(hpLeft))) {
            fillByHP.get(hpLeft).drawFill(surface, blockShape);
        } else if (this.fill != null) {
            fill.drawFill(surface, blockShape);
        }
        if (this.stroke != null) {
            this.stroke.drawOutline(surface, blockShape);
        }
    }

    /**
     * implementation of sprite interface.
     * @param dt ignored
     */
    public void timePassed(double dt) {
        return;
    }

    /**
     * add the block to the game.
     * @param g - the game we want to add the block to.
     */
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * set the number of hitpoints to block.
     * @param hits - number of hits.
     */
    public void setHitPoints(int hits) {
        this.hitPoints = hits;
    }

    /**
     * remove a block from game (usually, will be called when there are no hitpoints left).
     * @param gameLevel - the game we want to remove the block from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * after a block was hit, the function calls hitevent for every listener in the block's listeners list.
     * @param hitter - the ball that hit the block.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * accessor to block's hitpoints count.
     * @return count of hitpoints of the block.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * add a listener to the block's listeners list.
     * @param hl - the listener we want to add.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * remove a listener to the block's listeners list.
     * @param hl - the listener we want to remove.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * add a fill option for the block to a specified hitpoint.
     * @param hp - the hitpoint we want to match a fill to.
     * @param f - the fill we want to connect the hp to.
     */
    public void matchFillToHitpoint(int hp, Fill f) {
        this.fillByHP.put(hp, f);
    }

    /**
     * add width parameter to the block.
     * @param w - block's width.
     */
    public void setWidth(double w) {
        this.width = w;
    }

    /**
     * add height parameter to the block.
     * @param h - block's height.
     */
    public void setHeight(double h) {
        this.height = h;
    }
}
