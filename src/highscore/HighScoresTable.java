package highscore;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a table of top scores.
 * @author Lahav Amsalem 204632566
 */
public class HighScoresTable {
    // table actually holds a list of score info.
    private List<ScoreInfo> scoreList;
    // size limit for the list.
    private int listSize;

    /**
     * Constructor HighScoresTable creates a list of high scores.
     *
     * @param size - list size.
     */
    public HighScoresTable(int size) {
        this.scoreList = new ArrayList<ScoreInfo>();
        this.listSize = size;
    }

    /**
     * Constructor HighScoresTable creates a list of high scores.
     * this time with no size parameter (use default value of 10).
     */
    public HighScoresTable() {
        this.scoreList = new ArrayList<ScoreInfo>();
        this.listSize = 10;
    }

    /**
     * function adds a new score to the list.
     *
     * @param score - new score info of the game that just ended.
     */
    public void add(ScoreInfo score) {
        // check what is the ranking of the new score.
        int newScoreRank = this.getRank(score.getScore());
        // if the rank is smaller than the size of the list, it should be in it.
        if (newScoreRank <= this.listSize) {
            // enter the new score and sort the table to keep it sorted everytime.
            this.scoreList.add(score);
            this.sortByScore();
        }
        // after we added the new score, check if the list is larger than the size it was allowed to be.
        //if it is, remove last place.
        if (this.scoreList.size() > this.listSize) {
            this.scoreList.remove(this.listSize);
        }
    }

    /**
     * returns the size of the list.
     *
     * @return list size.
     */
    public int size() {
        return this.listSize;
    }

    /**
     * returns the list of the scores.
     *
     * @return list size.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoreList;
    }

    /**
     * return the rank of the current score: where will it
     * be on the list if added?
     * Rank 1 means the score will be highest on the list.
     * Rank `size` means the score will be lowest.
     * Rank > `size` means the score is too low and will not enter the list.
     *
     * @param score - the new score.
     * @return list size.
     */
    public int getRank(int score) {
        for (int i = 0; i < this.listSize; i++) {
            if (i < this.scoreList.size()) {
                if (score <= this.scoreList.get(i).getScore()) {
                    return i + 1;
                }
            }
        }
        // if the score is lower than the whole list, return the place after last place.
        return 1;
    }

    /**
     * function clears all of the scores from the list.
     */
    public void clear() {
        for (int i = 0; i < this.scoreList.size(); i++) {
            this.scoreList.remove(i);
        }
    }

    /**
     * function uses bubble sort to sort the list by score.
     */
    public void sortByScore() {
        // use bubble sort to sort the list from highest to lowest.
        for (int i = 0; i < this.scoreList.size() - 1; i++) {
            for (int j = 0; j < this.scoreList.size() - i - 1; j++) {
                ScoreInfo current = this.scoreList.get(j);
                ScoreInfo next = this.scoreList.get(j + 1);
                if (current.getScore() < next.getScore()) {
                    // create a temp scoreinfo to copy the current score into before replacing it.
                    ScoreInfo temp = current;
                    // switch places (put next in current's place, and temp in next's place.
                    this.scoreList.set(j, next);
                    this.scoreList.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * function loads a highscore table from a file.
     *
     * @param filename name of the file.
     * @throws IOException if load from file throws
     */
    public void load(File filename) throws IOException {
        // clear the score list.
        this.scoreList.clear();
        // use loadfromfile function to read the list from the file (hs is just a temp table).
        HighScoresTable hs = loadFromFile(filename);
        // update the scorelist of the table to be the list from the file.
        this.scoreList = hs.getHighScores();
        // default size of list is 10.
        this.listSize = 10;
    }


    /**
     * function saves a highscore table on a file.
     *
     * @param filename name of the file.
     * @throws IOException e exception if there was a problem with IO.
     */
    public void save(File filename) throws IOException {
        // create a new output stream.
        ObjectOutputStream os = null;
        try {
            // wrap the new stream with object writing stream.
            os = new ObjectOutputStream(new FileOutputStream(filename));
            // write the scorelist to the file.
            os.writeObject(this.scoreList);
            // catch possible exceptions.
        } catch (IOException e) {
            System.out.println("Something went wrong while writing!");
        } finally {
            // close the stream.
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * function loads a highscore table from a file.
     * // Read a table from file and return it.
     * // If the file does not exist, or there is a problem with
     * // reading it, an empty table is returned.
     *
     * @param filename name of the file.
     * @return high score table.
     */
    public static HighScoresTable loadFromFile(File filename) {
        // create a new input stream.
        ObjectInputStream is = null;
        // create a new temp table.
        HighScoresTable table = new HighScoresTable();
        try {
            // wrap is with object reading stream (serialized).
            try {
                is = new ObjectInputStream(new FileInputStream(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<ScoreInfo> listFromFile = null;
            try {
                listFromFile = (List<ScoreInfo>) is.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // if the list we received was not null, make it the temp table's list.
            if (listFromFile != null) {
                table.scoreList = listFromFile;
            }
        } finally {
            // close the stream.
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                    System.out.println(e3.toString());
                }
            }
        }
        // return the temp table so we can use its' members to update out main table.
        return table;
    }
}

