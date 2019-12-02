package levelsio;
import java.util.List;

/**
 * classes that implement this interface will parse a string (or a few) into a LevelFactory member,
 * which eventually creates a levelinformation object.
 * @author Lahav Amsalem 204632566
 */
public interface ParseInfo {

    /**
     * function receives a level description list, which includes strings with information about the level.
     * it separates the relevant part (for each type of info) and updates the levelFactory relevant member.
     * @param lvlDescription - the list of description.
     * @param lvlFactory - the level factory object we want to update.
     */
     void parse(List<String> lvlDescription, LevelFactory lvlFactory);
}
