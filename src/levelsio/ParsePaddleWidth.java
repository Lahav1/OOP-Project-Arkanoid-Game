package levelsio;
import java.util.List;

/**
 * class for parsing level's paddle width.
 * @author Lahav Amsalem 204632566
 */
public class ParsePaddleWidth implements ParseInfo {
    private double width;

    /**
     * Constructor parsed information object.
     * @param description - a list of strings that describe the level information.
     *                         our goal is to parse this information to create a level.
     * @param factory - a factory we want to update.
     */
    public ParsePaddleWidth(List<String> description, LevelFactory factory) {
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
            // check if a paddle width line exists.
            if (currentLine.contains("paddle_width:")) {
                // leave the paddle width number only.
                currentLine = currentLine.replace("paddle_width:", "");
                currentLine = currentLine.trim();
                // cast the string number to an integer.
                double paddleWidth = Integer.parseInt(currentLine);
                // update the paddle's width.
                lvlFactory.setPaddleWidth(paddleWidth);
                this.width = paddleWidth;
            }
        }
    }
}

