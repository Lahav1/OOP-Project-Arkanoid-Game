package levelsio;
import sprites.Velocity;
import java.util.ArrayList;
import java.util.List;

/**
 * class for parsing level's balls number and list of velocities.
 * @author Lahav Amsalem 204632566
 */
public class ParseBalls implements ParseInfo {
    private int numOfBalls;
    private List<Velocity> velocityList;

    /**
     * Constructor parsed information object.
     * @param description - a list of strings that describe the level information.
     *                         our goal is to parse this information to create a level.
     * @param factory - a factory we want to update.
     */
    public ParseBalls(List<String> description, LevelFactory factory) {
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
            // check if a ball velocities line exists.
            if (currentLine.contains("ball_velocities:")) {
                // leave the ball velocities only.
                currentLine = currentLine.replace("ball_velocities:", "");
                currentLine = currentLine.trim();
                // split the trimmed current line to array of angel,speed pairs.
                String[] angleSpeedPairs = currentLine.split(" ");
                // create an empty velocity list.
                List<Velocity> vList = new ArrayList<>();
                // create a velocity for each pair using fromAngleAndSpeed function.
                for (String pair : angleSpeedPairs) {
                    String[] splittedPair = pair.split(",");
                    double angle = Double.parseDouble(splittedPair[0]);
                    double speed = Double.parseDouble(splittedPair[1]);
                    Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
                    // add the new velocity to the list.
                    vList.add(v);
                }
                // number of balls in the level equals to the size of the velocities list.
                int numberOfBalls = vList.size();
                // update lvl factory with the number of balls and the list of their velocities.
                lvlFactory.setNumOfBalls(numberOfBalls);
                this.numOfBalls = numberOfBalls;
                lvlFactory.setBallVelocities(vList);
                this.velocityList = vList;
            }
        }
    }
}

