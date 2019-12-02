package levelsio;
import levels.LevelInformation;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.util.TreeMap;


/**
 * in charge of turning a levels description text file into a list of level info objects which can be
 * passed to the runlevel function of the gameflow and create an actual game.
 * @author Lahav Amsalem 204632566
 */
public class LevelSpecificationReader {
    private List<LevelInformation> lvlInfoList;

    /**
     * function reveives a java io reader connected to a level description text file, parsing it,
     * and returnsa list of level information objects.
     * @param reader - io file reader.
     * @return list of level information.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        // create an empty level info list
        List<LevelInformation> levelInfoList = new ArrayList<LevelInformation>();
        // create a new buffered reader of the java io reader passed to the function.
        BufferedReader buffered = new BufferedReader(reader);
        // call the function turnFileToStringList which turns the file into a list of lines.
        List<String> lineList = turnFileToStringList(buffered);
        // call the function fullLevelListToSubLists which separates the full lists to sub lists,
        // each sub list in listOfSublists consists a description of a single level.
        List<List<String>> listOfSublists = LevelSpecificationReader.fullLevelListToSubLists(lineList);
        // turn the list of sub lists into a list of maps (each level has a map with it's description).
        List<TreeMap<String, ParseInfo>> mapList = LevelSpecificationReader.fromListsToMaps(listOfSublists);
        // turn the list of sub lists into a list of maps (each level has a map with it's description).
        levelInfoList = LevelSpecificationReader.fromListsToLevelInfo(listOfSublists);
        //return the level info list.
        this.lvlInfoList = levelInfoList;
        return levelInfoList;
    }

    /**
     * receive a buffered reader of a text file and turn it into a list of strings.
     * each string is a line in the file.
     * comment and spacer lines are ignored.
     * @param buffered - buffered reader.
     * @return list of lines.
     */
    public static List<String> turnFileToStringList(BufferedReader buffered) {
        // create an empty list of lines.
        List<String> lineList = new ArrayList<>();
        // throw io exception if something goes wrong with reading the file.
        try {
            // read the first line.
            String line = buffered.readLine();
            // while line is not null, read the whole file.
            while (line != null) {
                // trim the line.
                line = line.trim();
                // if line begins with "#", it is a comment. ignore it.
                // if line is empty, also ignore.
                if ((!line.startsWith("#")) && (!line.equals(""))) {
                    lineList.add(line);
                }
                // move to next line.
                line = buffered.readLine();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        // return the new string list consisting the lines.
        return lineList;
    }

    /**
     * receive a list of strings which are all of the game levels descriptions,
     * and separate it into a list of lists, so each list is a single level's description.
     * the received list is composed in a way that each single level's description is written inside a
     * "START_LEVEL" - "END-LEVEL" block.
     * @param fullLevelDescriptionLines - a list of all of the game levels descriptions.
     * @return a list of sub-lists. each sub list is a single level's description.
     */
    public static List<List<String>> fullLevelListToSubLists(List<String> fullLevelDescriptionLines) {
        // create an empty list of sub-lists.
        List<List<String>> listOfSublists = new ArrayList<>();
        // scan the full game levels description
        for (int i = 0; i < fullLevelDescriptionLines.size() - 1; i++) {
            // if the line is "START_LEVEL" -> new sub list.
            if (fullLevelDescriptionLines.get(i).equals("START_LEVEL")) {
                // move to next line (we don't want to add "START_LEVEL" to the sublist.
                i++;
                // create a new single level description string list.
                List<String> singleNewLevel = new ArrayList<>();
                // stop when we reach "END_LEVEL" line.
                while (!fullLevelDescriptionLines.get(i).equals("END_LEVEL")) {
                    // add each line between "START_LEVEL" - "END-LEVEL".
                    singleNewLevel.add(fullLevelDescriptionLines.get(i));
                    // move ahead without adding "END_LEVEL".
                    // next line we should be reading is "START_LEVEL".
                    i++;
                }
                // add the sub list to the sublists list.
                listOfSublists.add(singleNewLevel);
            }
        }
        // return the separated list.
        return listOfSublists;
    }

    /**
     * function receives a list of levels descriptions, and returns a tree map of the descriptions.
     * @param listOfLevelsDescription - a single level's description.
     * @return a map bases on the sub list.
     */
    public static List<TreeMap<String, ParseInfo>> fromListsToMaps(List<List<String>> listOfLevelsDescription) {
        List<TreeMap<String, ParseInfo>> mapList = new ArrayList<>();
        for (List<String> singleLevelDescription : listOfLevelsDescription) {
            LevelFactory factory = new LevelFactory(singleLevelDescription);
            TreeMap<String, ParseInfo> map = factory.createMap();
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * function receives a list of levels descriptions, and returns a list of level information.
     * @param listOfLevelsDescription - a single level's description.
     * @return a level information list.
     */
    public static List<LevelInformation> fromListsToLevelInfo(List<List<String>> listOfLevelsDescription) {
        List<LevelInformation> list = new ArrayList<>();
        for (List<String> singleLevelDescription : listOfLevelsDescription) {
            LevelFactory factory = new LevelFactory(singleLevelDescription);
            LevelInformation levelInfo = factory.createLevelInformation();
            list.add(levelInfo);
        }
        return list;
    }
    }
