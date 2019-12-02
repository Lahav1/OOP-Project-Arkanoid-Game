package highscore;

import java.io.Serializable;

/**
 * This class contains information about the score a player received in a full game round.
 * @author Lahav Amsalem 204632566
 */
public class ScoreInfo implements Serializable {
    private String pname;
    private int pscore;

    /**
     * Constructor ScoreInfo creates a file with information of a new score after a game ended.
     * @param name - player's name.
     * @param score - player's score.
     */
    public ScoreInfo(String name, int score) {
        this.pname = name;
        this.pscore = score;
    }

    /**
     * @return accessor to players name.
     */
    public String getName() {
        return this.pname;
    }

    /**
     * @return player's score.
     */
    public int getScore() {
        return this.pscore;
    }
}
