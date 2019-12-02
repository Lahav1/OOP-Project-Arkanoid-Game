package levelsio;
import sprites.Block;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * creates an object for a single block type, and holds it's info together.
 * @author Lahav Amsalem 204632566
 */
public class SingleBlockFactory implements BlockCreator {
    private String symbol;
    private int width;
    private int height;
    private int hp;
    private BlockOutline outline;
    private TreeMap<Integer, Fill> fills;

    /**
     * Constructor of a blockcreator object from info.
     * The objects holds all the needed information and has a creation method.
     * @param s - the symbol of the block we want to create.
     * @param infoMap - map of the info from the level, before parsing it..
     */
    public SingleBlockFactory(String s, TreeMap<String, TreeMap<String, String>> infoMap) {
        this.symbol = s;
        TreeMap<String, String> info = infoMap.get(s);
        this.width = Integer.parseInt(info.get("width"));
        this.height = Integer.parseInt(info.get("height"));
        this.hp = Integer.parseInt(info.get("hit_points"));
        if (info.containsKey("stroke")) {
            String c = info.get("stroke");
            Color strokeColor = ColorParser.colorFromString(info.get("stroke"));
            this.outline = new BlockOutline(strokeColor);
        }
        this.fills = new TreeMap<Integer, Fill>();
        if (this.hp == 1) {
            if (info.containsKey("fill")) {
                String fillString = info.get("fill");
                Fill fill = parseBlockFill(fillString);
                fills.put(1, fill);
            } else if (info.containsKey("fill-1")) {
                String fillString = info.get("fill-1");
                Fill fill = parseBlockFill(fillString);
                fills.put(1, fill);
            }
        }
        if (this.hp > 1) {
            int j = 0;
            if ((info.containsKey("fill")) && (!info.containsKey("fill-1"))) {
                j = 1;
                String fillString = info.get("fill");
                Fill fill = parseBlockFill(fillString);
                fills.put(1, fill);
            }
            for (int i = j; i < this.hp; i++) {
                int k = i + 1;
                String fillKey = "fill-" + Integer.toString(k);
                Fill fillValue = parseBlockFill(info.get(fillKey));
                this.fills.put(k, fillValue);
            }
        }
    }


    /**
     * function turns a string into a fill.
     * @param s - block's fill description.
     * @return Fill objects of the suitable type.
     */
    public Fill parseBlockFill(String s) {
        // if the given background is a color, use colorparser class to parse it and create a fill.
        if (s.startsWith("color")) {
            Color c = ColorParser.colorFromString(s);
            Fill background = new FillColor(c);
            // set background to lvlfactory.
            return background;
        }
        // if the given background is a n image, use try reading it and create a fill from it.
        if (s.startsWith("image")) {
            // remove unnecessary parts from path.
            s = s.replace("image(", "");
            s = s.replace(")", "");
            // create a null image.
            Image image = null;
            // try reading the file.
            try {
                image = ImageIO.read(new File("resources/" + s));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Fill background = new FillImage(image);
            return background;
        }
        return null;
    }

    /**
     * creates a block in a spcified location.
     * @param xpos - x coordinate.
     * @param ypos - y coordinate.
     * @return a new block.
     */
    public Block create(int xpos, int ypos) {
        Block block;
        // a stroke is not a must-have for a block.
        if (this.outline != null) {
            block = new Block(xpos, ypos, this.width, this.height, this.fills, this.outline, this.hp);
        } else {
            block = new Block(xpos, ypos, this.width, this.height, this.fills, this.hp);
        }
        return block;
    }

    /**
     * @return block's width.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @return block's width.
     */
    public int getHeight() {
        return this.height;
    }

}
