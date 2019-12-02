import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import game.AnimationRunner;
import game.GameFlow;
import game.KeyPressStoppableAnimation;
import highscore.HighScoresAnimation;
import highscore.HighScoresTable;
import levels.LevelInformation;
import menu.MenuAnimation;
import menu.StartGameTask;
import menu.QuitGameTask;
import menu.HighScoresTask;
import menu.SubMenuTask;
import menu.Task;
import levelsio.LevelSpecificationReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a new game, initializes it and runs it.
 * @author Lahav Amsalem 204632566
 */
public class Ass6Game {

    /**
     * main function of the game proccess.
     *
     * @param args for specific level order choice.
     */
    public static void main(String[] args) {
        // if there are no args, use the default path. else, use the path from args.
        String path;
        if (args.length == 0) {
             path = "level_sets.txt";
        } else {
             path = args[0];
        }
        // create new gui and keyboard sensor.
        GUI gui = new GUI("Arkanoid", 800, 600);
        // create a sleeper.
        Sleeper sleeper = new Sleeper();
        // use 60 frames ps.
        int framesPerSecond = 60;
        //create an animation runner.
        AnimationRunner ar = new AnimationRunner(gui, framesPerSecond, sleeper);
        // create a keyboard sensor using te gui.
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        // create a new high score table.
        HighScoresTable hs = new HighScoresTable();
        // create a new file to manage the all-time high scores.
        File hsFile = new File("highscores");
        // if there is a file in the certain path, load it. else, create a new file.
        if (hsFile.exists()) {
            try {
                hs.load(hsFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // endless animation loop.
        // will stop only if "q" is pressed.
        while (true) {
            // create a gameflow.
            GameFlow flow = new GameFlow(ar, keyboard, hs, hsFile);
            // create a high score animation screen.
            HighScoresAnimation hsScreen = new HighScoresAnimation(keyboard, hs);
            KeyPressStoppableAnimation hsScreenStoppable = new KeyPressStoppableAnimation(keyboard,
                    KeyboardSensor.SPACE_KEY, hsScreen);
            // create a new main menu.
            MenuAnimation menu = new MenuAnimation(keyboard, "Main Menu");
            // create a sub menu task and add it to the main menu.
            SubMenuTask<Void> subMenu = new SubMenuTask<Void>(ar, keyboard, "Select mode:");
            HighScoresTask hsTask = new HighScoresTask(ar, hsScreenStoppable);
            menu.addSelection("s", "s - Start Game", subMenu);
            createLevelListFromSetsFile(subMenu, ar, flow, path);
            // create a new high score task and add it to the main menu.
            menu.addSelection("h", "h - High Scores Table", hsTask);
            // create a new quit game task and add it to the main menu.
            QuitGameTask quitTask = new QuitGameTask(keyboard, ar);
            menu.addSelection("q", "q - Quit Game", quitTask);
            // run the main menu
            ar.run(menu);
            // run the player's choice.
            Task<Void> task = menu.getStatus();
            task.run();
            // reset the task list.
            menu.resetTasks();
        }
    }

    /**
     * create a level information objects list from a description file.
     * @param filePath - a string with the file's path.
     * @return list of level information objects.
     */
    private static List<LevelInformation> createLevelList(String filePath) {
        List<LevelInformation> levels = new ArrayList<>();
        InputStream is = null;
        is = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
        Reader levelfilereader = new InputStreamReader(is);
        BufferedReader buffered = new BufferedReader(levelfilereader);
        LevelSpecificationReader levelReader = new LevelSpecificationReader();
        // create an empty level list.
        levels = levelReader.fromReader(buffered);
        return levels;
    }

    /**
     * receive a description text file of level sets and a sub menu, parse the description,
     * and add each set of levels to the sub menu.
     * @param submenu - sub menu.
     * @param runner - animation runner.
     * @param flow - game flow.
     * @param path - file path.
     * */
    private static void createLevelListFromSetsFile(
            SubMenuTask submenu, AnimationRunner runner, GameFlow flow, String path) {
        InputStream is = null;
        is = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        Reader setsFileReader = new InputStreamReader(is);
        BufferedReader buffered = new BufferedReader(setsFileReader);
        LevelSpecificationReader levelReader = new LevelSpecificationReader();
        List<List<String>> setsList = new ArrayList<>();
        // turn level sets file into a list of lines.
        List<String> lineList = LevelSpecificationReader.turnFileToStringList(buffered);
        // create a list of odd number lines.
        List<String> oddLines = new ArrayList<>();
        for (String line : lineList) {
            if (lineList.indexOf(line) % 2 != 0) {
                oddLines.add(line);
            }
        }
        // create a list of even number lines.
        List<String> evenLines = new ArrayList<>();
        for (String line : lineList) {
            if (lineList.indexOf(line) % 2 == 0) {
                evenLines.add(line);
            }
        }
        // turn each file path from odd lines to a list of level information objects.
        List<List<LevelInformation>> listOfLevelSets = new ArrayList<>();
        for (String line : oddLines) {
            List<LevelInformation> levels = createLevelList(line);
            listOfLevelSets.add(levels);
        }
        // create a selection in the menu for each level set.
        for (String line : evenLines) {
            // specified index (should be the same in both lists).
            int index = evenLines.indexOf(line);
            // split the line into 2 strings.
            String[] splitLine = line.split(":");
            // separate to symbol and message.
            String symbol = splitLine[0];
            String message = symbol + " - " + splitLine[1];
            // find the level information list in the second list.
            List<LevelInformation> levels = listOfLevelSets.get(index);
            // create a task with the info and add it as a selection to sub menu.
            Task task = new StartGameTask(runner, flow, levels);
            submenu.addSelection(symbol, message, task);
        }
    }
}

