package levelsio;
import sprites.Block;
import java.util.Map;

/**
 * This class is used to create a blockfactory object which holds 2 members:
 * map of symbols to space width, and map from symbol to block creator.
 * @author Lahav Amsalem 204632566
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructor.
     * @param s - map - string of symbol to integer of spacer width.
     * @param b - map - string of symbol to BlockCreator.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> s,  Map<String, BlockCreator> b) {
        this.spacerWidths = s;
        this.blockCreators = b;
    }


    /**
     * returns true if 's' is a valid space symbol.
     * @param s - string.
     * @return true/false.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * returns true if 's' is a valid block symbol.
     * @param s - string.
     * @return true/false.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);

    }

    /**
     * Return a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     * @param s - string.
     * @param xpos - x.
     * @param ypos - y.
     * @return block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s - string.
     * @return space width value.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * returns the blocks symbol map.
     * @return map.
     */
    public Map<String, BlockCreator> getBlocksMap() {
        return this.blockCreators;
    }
}
