package levelsio;
import java.util.ArrayList;
import java.util.List;

/**
 * class for parsing level's blocks layout.
 * @author Lahav Amsalem 204632566
 */
public class ParseBlocksLayout implements ParseInfo {
    private List<String> blocksLayout;

    /**
     * Constructor parsed information object.
     * @param description - a list of strings that describe the level information.
     *                         our goal is to parse this information to create a level.
     * @param factory - a factory we want to update.
     */
    public ParseBlocksLayout(List<String> description, LevelFactory factory) {
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
            // look for the "START BLOCKS" line.
            if (currentLine.equals("START_BLOCKS")) {
                List<String> layoutList = new ArrayList<>();
                i++;
                currentLine = lvlDescription.get(i);
                // stop when we reach "END_LEVEL" line.
                while (!currentLine.equals("END_BLOCKS")) {
                    // add each line between "START_LEVEL" - "END-LEVEL".
                    layoutList.add(currentLine);
                    // move ahead without adding "END_BLOCKS".
                    i++;
                    currentLine = lvlDescription.get(i);
                }
                // update level factory with the layout list.
                lvlFactory.setBlocksLayout(layoutList);
                this.blocksLayout = layoutList;
            }
        }
    }
}

