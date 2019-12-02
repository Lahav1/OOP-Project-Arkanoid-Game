package levelsio;
import sprites.Background;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.Color;
import java.awt.Image;

/**
 * class for parsing background.
 * @author Lahav Amsalem 204632566
 */
public class ParseBackground implements ParseInfo {
    private Fill fill;
    private Background background;

    /**
     * Constructor parsed information object.
     * @param description - a list of strings that describe the level information.
     *                         our goal is to parse this information to create a level.
     * @param factory - a factory we want to update.
     */
    public ParseBackground(List<String> description, LevelFactory factory) {
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
            // check if a level name line exists.
            if (currentLine.contains("background:")) {
                // leave the level name only.
                currentLine = currentLine.replace("background:", "");
                currentLine = currentLine.trim();
            }
            // if the given background is a color, use colorparser class to parse it and create a fill.
            if (currentLine.startsWith("color")) {
                Color c = ColorParser.colorFromString(currentLine);
                Fill backgroundFill = new FillColor(c);
                // set background to lvlfactory.
                this.fill = backgroundFill;
                this.background = new Background(backgroundFill);
                lvlFactory.setBackground(this.background);
            }
            // if the given background is a n image, use try reading it and create a fill from it.
            if (currentLine.startsWith("image")) {
                // remove unnecessary parts from path.
                currentLine = currentLine.replace("image(", "");
                currentLine = currentLine.replace(")", "");
                // create a null image.
                Image image = null;
                // try reading the file.
                try {
                    image = ImageIO.read(new File("resources/" + currentLine));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Fill backgroundFill = new FillImage(image);
                // set background to lvlfactory.
                this.fill = backgroundFill;
                this.background = new Background(backgroundFill);
                lvlFactory.setBackground(this.background);
                return;
            }
        }
    }
}

