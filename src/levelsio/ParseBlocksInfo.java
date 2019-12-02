package levelsio;
import sprites.Block;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * class for parsing level's balls number and list of velocities.
 * @author Lahav Amsalem 204632566
 */
public class ParseBlocksInfo implements ParseInfo {
    private double startX;
    private double startY;
    private double rowHeight;
    private double numOfBlocks;
    private String filePath;
    private BlocksFromSymbolsFactory blocksFactory;
    private List<Block> blockList;
    private List<String> layout;

    /**
     * Constructor parsed information object.
     * @param description - a list of strings that describe the level information.
     *                         our goal is to parse this information to create a level.
     * @param factory - a factory we want to update.
     * @param blayout - blocks layout.
     */
    public ParseBlocksInfo(List<String> description, LevelFactory factory, List<String> blayout) {
        this.layout = blayout;
        this.parse(description, factory);
    }

    /**
     * function receives a level description list, which includes strings with information about the level.
     * it separates the relevant part (for each type of info) and updates the levelFactory relevant member.
     *
     * @param lvlDescription - the list of description.
     * @param lvlFactory     - the level factory object we want to update.
     */
    public void parse(List<String> lvlDescription, LevelFactory lvlFactory) {
        // scan the whole description.
        for (int i = 0; i < lvlDescription.size() - 1; i++) {
            String currentLine = lvlDescription.get(i);
            // create a new map between strings to doubles.
            TreeMap<String, Double> info = new TreeMap<>();
            // check if a block start x line exists.
            if (currentLine.contains("blocks_start_x:")) {
                currentLine = currentLine.replace("blocks_start_x:", "");
                currentLine = currentLine.trim();
                double startx = Double.parseDouble(currentLine);
                info.put("blocks_start_x", startx);
                this.startX = startx;
            }
            // check if a block start y line exists.
            if (currentLine.contains("blocks_start_y:")) {
                currentLine = currentLine.replace("blocks_start_y:", "");
                currentLine = currentLine.trim();
                double starty = Double.parseDouble(currentLine);
                info.put("blocks_start_y", starty);
                this.startY = starty;
            }
            // check if a row height line exists.
            if (currentLine.contains("row_height:")) {
                currentLine = currentLine.replace("row_height:", "");
                currentLine = currentLine.trim();
                double height = Double.parseDouble(currentLine);
                info.put("row_height", height);
                this.rowHeight = height;
            }
            // check if a num blocks line exists.
            if (currentLine.contains("num_blocks:")) {
                currentLine = currentLine.replace("num_blocks:", "");
                currentLine = currentLine.trim();
                double numBlocks = Double.parseDouble(currentLine);
                info.put("num_blocks", numBlocks);
                this.numOfBlocks = numBlocks;
            }
            // check if a num blocks line exists.
            if (currentLine.contains("block_definitions:")) {
                currentLine = currentLine.replace("block_definitions:", "");
                currentLine = currentLine.trim();
                // we have the clean path to the file.
                String path = currentLine;
                // update the level factory with the path.
                lvlFactory.setBlocksDescriptionPath(path);
                this.filePath = path;
                // create a block factory from the blocks description file.
                File blockfile = new File("resources/" + this.filePath);
                Reader blockfilereader = null;
                try {
                    blockfilereader = new InputStreamReader(new FileInputStream(blockfile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                this.blocksFactory = BlocksDefinitionReader.fromReader(blockfilereader);
                lvlFactory.setBlocksFactory(this.blocksFactory);
            }
            // update the level factory with the blocks info treemap.
            lvlFactory.setBlocksInfo(info);
        }
        this.blockList = createBlockList();
        lvlFactory.setBlockList(this.blockList);
    }


    /**
     * use all of the information to create the final level's block list.
     * @return blocks list.
     */
    public List<Block> createBlockList() {
        List<Block> list = new ArrayList<>();
        int locateX = (int) this.startX;
        int locateY = (int) this.startY;
        for (String line : this.layout) {
            String[] splitLine = line.split("");
            for (int j = 0; j < line.length(); j++) {
                String symbol = splitLine[j];
                if (this.blocksFactory.isBlockSymbol(symbol)) {
                    Block b = this.blocksFactory.getBlocksMap().get(symbol).create(locateX, locateY);
                    list.add(b);
                    int width = (int) this.blocksFactory.getBlocksMap().get(symbol).getWidth();
                    locateX = locateX + width;
                }
                if (this.blocksFactory.isSpaceSymbol(symbol)) {
                    int width = this.blocksFactory.getSpaceWidth(symbol);
                    locateX = locateX + width;
                }
            }
            locateX = (int) this.startX;
            locateY = locateY + (int) this.rowHeight;
        }
        return list;
    }

    /**
     * get the blocks factory object.
     * @return blocks factory.
     */
    public BlocksFromSymbolsFactory getBlocksFactory() {
        return this.blocksFactory;
    }


}

