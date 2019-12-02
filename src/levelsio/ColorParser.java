package levelsio;
import java.awt.Color;

/**
 * This class is used to return a color objects by parsing a text string.
 * @author Lahav Amsalem 204632566
 */
public class ColorParser {

    /**
     * This class is used to return a color objects by parsing a text string.
     * possible strings- "color(black/blue/cyan/gray/lightGray/green/orange/pink/red/white/yellow)
     * or "color(rgb(x, y, z))".
     * @param s - the description of the color
     * @return the specific color object.
     */
    public static java.awt.Color colorFromString(String s) {
        // turn the whole string to lower case.
        s = s.toLowerCase();
        // if the string is supposed to be in an rgb format, remove the uneccessary parts first.
        if (s.startsWith("color(rgb(")) {
            s = s.replace("color(rgb(", "");
            s = s.replace("))", "");
            // split the remaining string into an array of number strings (splitted by ",").
            String[] rgbComponents = s.split(",");
            // if there are more/less components than 3, the format does not support it.
            if (rgbComponents.length != 3) {
                return null;
            }
            // if the array size is ok, parse the strings into integers.
            int x = Integer.parseInt(rgbComponents[0]);
            int y = Integer.parseInt(rgbComponents[1]);
            int z = Integer.parseInt(rgbComponents[2]);
            // create a new color using the integers, and return it.
            Color newColor = new Color(x, y, z);
            return newColor;
        }
        // if the string is not an rgb, check if the color is on the package's basic color's list.
        // if it does, return the suitable color.
        // remove the "color()" part so s will consist the color's name only.
        s = s.replace("color(", "");
        s = s.replace(")", "");
        if (s.equals("black")) {
            return Color.BLACK;
        }
        if (s.equals("blue")) {
            return Color.BLUE;
        }
        if (s.equals("cyan")) {
            return Color.CYAN;
        }
        if (s.equals("gray")) {
            return Color.GRAY;
        }
        if (s.equals("darkgray")) {
            return Color.darkGray;
        }
        if (s.equals("lightgray")) {
            return Color.LIGHT_GRAY;
        }
        if (s.equals("orange")) {
            return Color.ORANGE;
        }
        if (s.equals("pink")) {
            return Color.PINK;
        }
        if (s.equals("red")) {
            return Color.RED;
        }
        if (s.equals("white")) {
            return Color.WHITE;
        }
        if (s.equals("yellow")) {
            return Color.YELLOW;
        }
        if (s.equals("green")) {
            return Color.green;
        }
        // if none of the options has been returned, the string is not legal and null should be returned.
        return null;
    }
}
