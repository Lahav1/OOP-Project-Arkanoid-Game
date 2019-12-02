package game;
import biuoop.KeyboardSensor;
import biuoop.DialogManager;
import java.io.IOException;
import java.util.List;
import java.io.File;
import highscore.HighScoresAnimation;
import highscore.ScoreInfo;
import sprites.Counter;
import sprites.LivesIndicator;
import sprites.ScoreIndicator;
import levels.LevelInformation;
import highscore.HighScoresTable;

/**
 * This class is in charge of managing the whole game and moving thorugh the different levels.
 * @author Lahav Amsalem 204632566
 */
public class GameFlow {
    // whole-game counters.
    private Counter scoreCounter;
    private Counter lifeCounter;
    // indicators we follow through game.
    private LivesIndicator lifeIndi;
    private ScoreIndicator scoreIndi;
    // animation runner
    private AnimationRunner runner;
    // keyboard sensor.
    private KeyboardSensor keyboard;
    // did the player win?
    private boolean victory;
    // high scores table
    private HighScoresTable hsTable;
    // file of highscores
    private File hsFile;

    /**
     * Constructor creates a new gameflow object.
     * @param ar - animation runner.
     * @param ks - keyboard sensor.
     * @param hs - highscore table.
     * @param f - file.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, HighScoresTable hs, File f) {
        this.runner = ar;
        this.keyboard = ks;
        this.hsTable = hs;
        this.hsFile = f;
    }

    /**
     * the function uses a levels list to build the game and is in charge of the game logics.
     * @param levels - list of levels we want our game to be made of.
     */
    public void runLevels(List<LevelInformation> levels) {
        // initialize the victory boolean to "true", it will stay so if the lives won't end.
        this.victory = true;
        // create the counters and the indicators.
        this.createCounters();
        this.createIndicators();
        // for each level in the list, create a gamelevel and run it until loss/level clear/victory at whole game.
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboard, this.runner, this.scoreIndi, this.lifeIndi,
                                             this.scoreCounter, this.lifeCounter);
            level.initialize();
            //if level has more blocks, and player has more lives, give him another turn.
            while ((level.getLevelBlocksCount() > 0) && (level.getLevelLivesCount() > 0)) {
                level.playOneTurn();
                //if the player cleared the level, he gets another 100 points.
                if (level.getLevelBlocksCount() == 0) {
                    this.scoreCounter.increase(100);
                    break;
                }
                //if the player lost both balls, decrease his lives by 1.
                if (level.getLevelBallsCount() == 0) {
                    this.lifeCounter.decrease(1);
                }
            }
            // if there are no more lives, it means loss.
            // change victory to false and break the loop.
            if (this.lifeCounter.getValue() == 0) {
                this.victory = false;
                break;
            }
        }
        // after finishing the while loop, we check if the player won or lost (using boolean victory).
        if (victory) {
            // if player won, create a victory screen and display his score.
            // space key will finish the animation.
            EndScreenWin end = new EndScreenWin(this.keyboard, this.scoreCounter);
            Animation endStopable = new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, end);
            this.runner.run(endStopable);
        } else {
            // if player lost, create a game over screen and display his score.
            // space key will finish the animation.
            EndScreenLoss end = new EndScreenLoss(this.keyboard, this.scoreCounter);
            Animation endStopable = new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, end);
            this.runner.run(endStopable);
        }
        // open a dialog to allow player insert his name.
        DialogManager dialog = this.runner.getGUI().getDialogManager();
        String name = dialog.showQuestionDialog("Name", "What is your name?", "");
        // if the player's score was high enough, add him to the highscores table.
        this.hsTable.add(new ScoreInfo(name, this.scoreCounter.getValue()));
        // save the new table to the file.
        try {
            this.hsTable.save(this.hsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // create a high score animation screen.
        HighScoresAnimation hsScreen = new HighScoresAnimation(this.keyboard, this.hsTable);
        Animation hsScreenStopable = new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY,
                 hsScreen);
        // open high scores animation screen.
        this.runner.run(hsScreenStopable);
//        //close the gui after we exited the while loop.
//        this.runner.getGUI().close();
    }


    /**
     * function creates the counters for the WHOLE GAME.
     */
    public void createCounters() {
        // COUNTERS CREATION //
        //create a new counter for the player's score.
        this.scoreCounter = new Counter();
        //create a new counter for the player's lives.
        this.lifeCounter = new Counter();
    }

    /**
     * function creates the indicators of the game (score and life).
     */
    public void createIndicators() {
        //create a score indicator.
        this.scoreIndi = new ScoreIndicator(this.scoreCounter);
        //create a lives indicator.
        this.lifeIndi = new LivesIndicator(this.lifeCounter, 7);
    }
}
