package game;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import geometry.Point;
import geometry.Rectangle;
import collidables.Collidable;
import collidables.GameEnvironment;
import levelsio.Fill;
import levelsio.FillColor;
import sprites.Block;
import sprites.Ball;
import sprites.Paddle;
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.ScoreIndicator;
import sprites.LivesIndicator;
import sprites.LevelsName;
import listeners.HitListener;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import listeners.PrintingHitListener;
import sprites.Counter;
import sprites.Velocity;
import levels.LevelInformation;

/**
 * This class is used to create the sprite and collidable collections, and create the animation loop.
 * @author Lahav Amsalem 204632566
 */
public class GameLevel implements Animation {
    // screen frame
    private int upper;
    private int lower;
    private int left;
    private int right;
    // collections of sprites and collidables.
    private SpriteCollection sprites;
    private GameEnvironment environment;
    // level-specific counters.
    private Counter blockCounter;
    private Counter ballCounter;
    // whole-game counters.
    private Counter scoreCounter;
    private Counter lifeCounter;
    // indicators we follow through game.
    private LivesIndicator lifeIndi;
    private ScoreIndicator scoreIndi;
    // animation runner
    private AnimationRunner runner;
    // indicates if the game has stopped (caused by victory/loss)
    private boolean running;
    // keyboard sensor to allow player use keyboard.
    private KeyboardSensor keyboard;
    // level information
    private LevelInformation levelInfo;


    /**
     * Constructor creates a gamelevel given a gamelevel info.
     * @param info - all the information we need to create the level.
     * @param kboard - keyboard sensor.
     * @param aRunner - animation runner.
     * @param scoreInd - score indicator.
     * @param lifeInd - life indicator.
     * @param scoreC - score counter.
     * @param livesC - lives counter.
     */
    public GameLevel(LevelInformation info, KeyboardSensor kboard, AnimationRunner aRunner,
                     ScoreIndicator scoreInd, LivesIndicator lifeInd, Counter scoreC, Counter livesC) {
        this.levelInfo = info;
        this.keyboard = kboard;
        this.runner = aRunner;
        this.scoreIndi = scoreInd;
        this.lifeIndi = lifeInd;
        this.scoreCounter = scoreC;
        this.lifeCounter = livesC;
    }

    /**
     * function initializes the game, creating the gui, balls, collidables.
     */
    public void initialize() {
        // create a runner using a function
        this.createScreenBorders();
        // create a keyboard sensor.
        this.keyboard = this.runner.getGUI().getKeyboardSensor();
        //create the environment for the game (game environment and sprites collection)
        this.createEnvironment();
        // BACKGROUND CREATION //
        this.createBackground();
        // create the counters for the game.
        this.createCounters();
        // LISTENERS CREATION //
        //create a message printing listener.
        HitListener phl = new PrintingHitListener();
        //create a new block remover listener.
        HitListener blockrmv = new BlockRemover(this, blockCounter);
        //create a new ball remover listener.
        HitListener ballrmv = new BallRemover(this, ballCounter);
        //create a new score counter listener.
        HitListener scorelstn = new ScoreTrackingListener(this.scoreCounter);
        // BLOCKS CREATION //
        this.createBlocks(phl, blockrmv, scorelstn);
        // SIDE BLOCKS CREATION //
        this.createSideBlocks();
        // DEATH BLOCK CREATION //
        this.createDeathBlock(ballrmv);
        // LEVEL'S NAME //
        LevelsName name = new LevelsName(this.levelInfo.levelName());
        // add the whole-game indicators to the sprites list.
        this.sprites.addSprite(scoreIndi);
        this.sprites.addSprite(lifeIndi);
        this.sprites.addSprite(name);
    }

    /**
     * function starts a single turn of the player.
     */
    public void playOneTurn() {
        // set running to be true
        this.running = true;
        // create 2 balls and add them to the game using function.
        this.createBalls();
        //create the paddle and place it in the bottom of the screen, middle of line.
        Paddle pad = this.createPaddle();
        //add paddle to game.
        pad.addToGame(this);
        // countdown before turn starts
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        // use our runner to run the current animation -- which is one turn of the game
        this.runner.run(this);
        //finally remove pad before the next turn.
        pad.removeFromGame(this);
    }

    /**
     * function allows player keep trying until he has no lives left.
     */
    public void run() {
        //while player has lives left and did not clear the level, give him another turn
        while ((this.lifeCounter.getValue() != 0) && (this.blockCounter.getValue() != 0)) {
            playOneTurn();
        }
    }

    /**
     * function creates the animation runner and its' constructor's materials.
     */
    public void createScreenBorders() {
        // set the screen's borders.
        this.upper = 0;
        this.lower = 600;
        this.left = 0;
        this.right = 800;
    }

    /**
     * function creates the environemnts of the game.
     */
    public void createEnvironment() {
        //create a list of collidable objects and a list of sprite objects
        List<Collidable> collidables = new ArrayList<Collidable>();
        List<Sprite> spriteList = new ArrayList<Sprite>();
        //update the game's environment to be the collidables list.
        this.environment = new GameEnvironment(collidables);
        //update the game's sprites collection to be the list of sprites.
        this.sprites = new SpriteCollection(spriteList);
    }

    /**
     * function creates the counters for the LEVEL ONLY.
     */
    public void createCounters() {
        // COUNTERS CREATION //
        //create a new counter for the blocks.
        this.blockCounter = new Counter();
        //create a new counter for the blocks.
        this.ballCounter = new Counter();
    }

    /**
     * function creates the balls according to the levelinfo velocity list.
     */
    public void createBalls() {
        // add the required amount of balls according to the levelinfo.
        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            Ball ball = new Ball(new Point(400, 580), 5, java.awt.Color.WHITE);
            // let the ball know the frame limits.
            ball.updateFrame(this.upper, this.lower, this.right, this.left);
            // let the ball know the collidables.
            ball.setGameEnvironment(this.environment);
            // use the velocity from the velocity list of levelinfo.
            Velocity v = this.levelInfo.initialBallVelocities().get(i);
            ball.setVelocity(v);
            // add the ball to the game.
            ball.addToGame(this);
            // increase the ball counter by 1.
            this.ballCounter.increase(1);
        }
    }

    /**
     * function uses levelinfo to create blocks.
     * @param phl - printing hit listener.
     * @param blockrmv - block remover.
     * @param scorelstn - score tracking listener.
     */
    public void createBlocks(HitListener phl, HitListener blockrmv, HitListener scorelstn) {
        // add all the blocks according to the levelinfo block list.
        for (Block block : this.levelInfo.blocks()) {
            // add the block to the game
            block.addToGame(this);
            // add all the relevant listeners to the blocks.
            block.addHitListener(phl);
            block.addHitListener(blockrmv);
            block.addHitListener(scorelstn);
            // increase the blocks counter by 1.
            this.blockCounter.increase(1);
        }
    }

    /**
     * function uses levelinfo to create the paddle.
     * @return the paddle
     */
    public Paddle createPaddle() {
        int width = this.levelInfo.paddleWidth();
        int speed = this.levelInfo.paddleSpeed();
        // optimize the paddle location to the middle of the screen.
        // it should be placed a half of its' width from the center of the screen (which its' x coordinate is 400)
        double paddleX = 400 - (width / 2);
        double paddleY = 585;
        // create the paddle using the details we received from level info.
        Paddle pad = new Paddle(new Rectangle(new Point(paddleX, paddleY), width, 12),
                Color.ORANGE, speed);
        // update the keyboard sensor to pad.
        pad.updateKeyboardSensor(this.keyboard);
        // update the screen frame to pad.
        pad.updateFrame(this.upper, this.lower, this.right, this.left);
        return pad;
    }

    /**
     * function uses levelinfo to create the side blocks.
     */
    public void createSideBlocks() {
        Fill fill = new FillColor(Color.darkGray);
        Block topB = new Block(new Rectangle(new Point(0, 0), 800, 45), fill);
        Block rightB = new Block(new Rectangle(new Point(775, 25), 25, 576), fill);
        Block leftB = new Block(new Rectangle(new Point(0, 25), 25, 576), fill);
        //add each screen-side block to game and set 1 hitpoint.
        topB.addToGame(this);
        topB.setHitPoints(1);
        rightB.addToGame(this);
        rightB.setHitPoints(1);
        leftB.addToGame(this);
        leftB.setHitPoints(1);
    }

    /**
     * function creates the death block in the bottom of the screen, so when a ball touches it, the ball is removed.
     * @param ballrmv - the ball remover listener.
     */
    public void createDeathBlock(HitListener ballrmv) {
        Block death = new Block(new Rectangle(new Point(0, 600), 800, 10), Color.DARK_GRAY);
        death.addHitListener(ballrmv);
        death.addToGame(this);
        death.setHitPoints(100);
    }


    /**
     * function creates the indicators of the game (score and life).
     */
    public void createBackground() {
        Sprite bg = this.levelInfo.getBackground();
        this.sprites.addSprite(bg);
    }

    /**
     * function activates addcollidable function from gameenvironment class and adds collidable 'c' to the list.
     *
     * @param c the collidable we want to add.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * function activates addsprite function from spritescollection class and adds sprite 's' to the list.
     *
     * @param s the sprite we want to add.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * remove a collidable from game when it is no longer relevant.
     *
     * @param c - the collidable we want to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * remove a sprite from game when it is no longer relevant.
     *
     * @param s - the sprite we want to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * specific game logic and drawings.
     * @param d - drawsurface
     * @param dt - the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // operate timepassed function on each sprite
        this.sprites.notifyAllTimePassed(dt);
        // draw all objects on drawsurface
        this.sprites.drawAllOn(d);
        // cover the outline of the sideblocks with dark gray.
        d.setColor(Color.DARK_GRAY);
        d.drawRectangle(775, 25, 25, 576);
        d.drawRectangle(0, 25, 25, 576);
        // if the player has no lives left or he cleared the level, make the loop stop running.
        if ((this.blockCounter.getValue() == 0) || (this.ballCounter.getValue() == 0)) {
            this.running = false;
        }
        //if the user pressed "p", pause the game and show the pausescreen animation.
        //when user presses space key, we go back to the game animation.
        if (this.keyboard.isPressed("p")) {
            PauseScreen pause = new PauseScreen(this.keyboard);
            Animation pauseStoppable = new KeyPressStoppableAnimation(this.keyboard,
                    KeyboardSensor.SPACE_KEY, pause);
            this.runner.run(pauseStoppable);
        }
    }

    /**
     * when running = false, it means the player lost all lives or finished the level.
     * we want to stop at this point.
     * not running -> should stop.
     * @return true if running, false if not.
     */
    public boolean shouldStop() {
        return !this.running;
    }

    /**
     * @return number of blocks left in this level.
     */
    public int getLevelBlocksCount() {
        return this.blockCounter.getValue();
    }

    /**
     * @return number of lives left in this level.
     */
    public int getLevelLivesCount() {
        return this.lifeCounter.getValue();
    }

    /**
     * @return number of balls left in this level.
     */
    public int getLevelBallsCount() {
        return this.ballCounter.getValue();
    }
}