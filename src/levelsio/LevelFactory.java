package levelsio;
import levels.LevelInformation;
import sprites.Background;
import sprites.Sprite;
import sprites.Velocity;
import sprites.Block;

import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class is used to create a level factory object.
 * @author Lahav Amsalem 204632566
 */
public class LevelFactory {
    private List<String> description;
    private String name;
    private Background background;
    private List<Velocity> ballVelocities;
    private TreeMap<String, Double> blocksInfo;
    private BlocksFromSymbolsFactory blocksFactory;
    private List<String> blocksLayout;
    private String blocksDescriptionPath;
    private List<Block> blockList;
    private double paddleSpeed;
    private double paddleWidth;
    private int numOfBalls;


    /**
     * Constructor creates a level factory object given a level description list.
     * @param levelDescription - a list of strings that describe the level information.
     *                         our goal is to parse this information to create a level.
     */
    public LevelFactory(List<String> levelDescription) {
        this.description = levelDescription;
        // initialize all the members with null.
        this.name = null;
        this.background = null;
        this.ballVelocities = new ArrayList<>();
        this.blockList = new ArrayList<>();
    }

    /**
     * set the level's name.
     * @param n - level's name.
     */
    public void setLevelName(String n) {
        this.name = n;
    }

    /**
     * set the level's background.
     * @param bg - background.
     */
    public void setBackground(Background bg) {
        this.background = bg;
    }

    /**
     * set the level's balls + velocities.
     * @param vList - list of velocities.
     */
    public void setBallVelocities(List<Velocity> vList) {
        this.ballVelocities = vList;
    }

    /**
     * set the level's block list.
     * @param blocks - list of blocks.
     */
    public void setBlockList(List<Block> blocks) {
        this.blockList = blocks;
    }

    /**
     * set the level's paddle speed.
     * @param pSpeed - speed of the paddle.
     */
    public void setPaddleSpeed(double pSpeed) {
        this.paddleSpeed = pSpeed;
    }

    /**
     * set the level's paddle width.
     * @param pWidth - width of the paddle.
     */
    public void setPaddleWidth(double pWidth) {
        this.paddleWidth = pWidth;
    }

    /**
     * set the level's number of balls.
     * @param ballsNum - number of balls.
     */
    public void setNumOfBalls(int ballsNum) {
        this.numOfBalls = ballsNum;
    }

    /**
     * set the level's blocks info.
     * @param info - information about the blocks.
     */
    public void setBlocksInfo(TreeMap<String, Double> info) {
        this.blocksInfo = info;
    }

    /**
     * set the level's blocks layout.
     * @param layout - scheme of balls layout (using a string).
     */
    public void setBlocksLayout(List<String> layout) {
        this.blocksLayout = layout;
    }

    /**
     * set the level's blocks description file's path.
     * @param path - file's path.
     */
    public void setBlocksDescriptionPath(String path) {
        this.blocksDescriptionPath = path;
    }

    /**
     * set the level's blocks factory.
     * @param factory - factory.
     */
    public void setBlocksFactory(BlocksFromSymbolsFactory factory) {
        this.blocksFactory = factory;
    }

    /**
     * create a map from the level factory.
     * @return a treemap with all the information of the level.
     */
    public TreeMap<String, ParseInfo> createMap() {
        TreeMap<String, ParseInfo> map = new TreeMap<>();
        map.put("Name", new ParseLevelName(description, this));
        map.put("Background", new ParseBackground(description, this));
        map.put("Balls", new ParseBalls(description, this));
        map.put("Blocks Layout", new ParseBlocksLayout(description, this));
        map.put("Blocks Info", new ParseBlocksInfo(description, this, this.blocksLayout));
        map.put("Paddle Speed", new ParsePaddleSpeed(description, this));
        map.put("Paddle Width", new ParsePaddleWidth(description, this));
        return map;
    }

    /**
     * create a level information with the info of the level factory.
     * @return level factory.
     */
    public LevelInformation createLevelInformation() {
        TreeMap<String, ParseInfo> map = createMap();
        int ballsNumForInfo = this.numOfBalls;
        List<Velocity> vListForInfo = this.ballVelocities;
        String nameForInfo = this.name;
        int paddleSpeedForInfo = (int) this.paddleSpeed;
        int paddleWidthForInfo = (int) this.paddleWidth;
        Sprite bgForInfo = this.background;
        List<Block> blockListForInfo = this.blockList;
        int numOfBlocksForInfo =  blockListForInfo.size();
        return new LevelInformation() {
            @Override
            public int numberOfBalls() {
                return ballsNumForInfo;
            }

            @Override
            public List<Velocity> initialBallVelocities() {
                return vListForInfo;
            }

            @Override
            public int paddleSpeed() {
                return paddleSpeedForInfo;
            }

            @Override
            public int paddleWidth() {
                return paddleWidthForInfo;
            }

            @Override
            public String levelName() {
                return nameForInfo;
            }

            @Override
            public Sprite getBackground() {
                return bgForInfo;
            }

            @Override
            public List<Block> blocks() {
                return blockListForInfo;
            }

            @Override
            public int numberOfBlocksToRemove() {
                return numOfBlocksForInfo;
            }
        };
    }
}
