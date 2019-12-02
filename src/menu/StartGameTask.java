package menu;
import java.util.List;
import game.AnimationRunner;
import game.GameFlow;
import levels.LevelInformation;

/**
 * This class is used for creation of the start game task.
 * @author Lahav Amsalem 204632566
 */
public class StartGameTask implements Task<Void> {
    // animation runner
    private AnimationRunner runner;
    // keyboard sensor.
    private GameFlow game;
    private List<LevelInformation> lvls;

    /**
     * Constructor creates a start game task.
     * @param r - animation runner.
     * @param g - gameflow.
     * @param list - list of levels.
     */
    public StartGameTask(AnimationRunner r, GameFlow g, List<LevelInformation> list) {
        this.runner = r;
        this.lvls = list;
        this.game = g;
    }

    /**
     * used for running the specific task.
     * @return null
     */
    public Void run() {
        this.game.runLevels(this.lvls);
        return null;
    }
}
