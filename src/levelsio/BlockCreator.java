package levelsio;
import sprites.Block;

/**
 * block creator interface.
 * @author Lahav Amsalem 204632566
 */
public interface BlockCreator {

    /**
     * creates a block in a spcified location.
     * @param xpos - x coordinate.
     * @param ypos - y coordinate.
     * @return a new block.
     */
    Block create(int xpos, int ypos);

    /**
     * @return block's width.
     */
    int getWidth();

    /**
     * @return block's width.
     */
    int getHeight();
}
