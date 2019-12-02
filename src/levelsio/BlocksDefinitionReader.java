package levelsio;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

/**
 * functions in this class turn a text file with a level's blocks description
 * into a BlocksFromSymbolsFactory.
 * @author Lahav Amsalem 204632566
 */
public class BlocksDefinitionReader {

    /**
     * creates a block in a spcified location.
     * @param reader - file reader.
     * @return a new block.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        // create empty treemaps for spacers and block creators.
        Map<String, Integer> spacers = new TreeMap<>();
        Map<String, BlockCreator> blockCreatorsMap = new TreeMap<>();
        // create a new buffered reader from given reader.
        BufferedReader buffered = new BufferedReader(reader);
        // turn the text file into a list of strings.
        List<String> stringList = turnFileToStringList(buffered);
        // create a map with the list of strings.
        spacers = createSpacersMap(stringList);
        // create a defaults map with the list of strings.
        TreeMap<String, String> deafults = createDefaultsMap(stringList);
        // create a temp blocks map which contains a symbol and a long, unseparated line of description.
        // then create a map which suits a symbol to another map with organized info.
        TreeMap<String, String> blocksTemp = createTempBlocksMap(stringList);
        TreeMap<String, TreeMap<String, String>> blocksNotParsed = createBlocksMap(blocksTemp);
        // use a function to add fields that exist in defaults and don't exist in a block's definition.
        mergeBlocksAndDefaults(deafults, blocksNotParsed);
        // use a function to turn each symbol in the blocksNotParsed map into a SingleBlockFactory object,
        // which is from a BlockCreator type, and holds all the parsed info of the block type.
        blockCreatorsMap = createBlockCreators(blocksNotParsed);
        // create a blocks from symbols factory and return it.
        BlocksFromSymbolsFactory blocksFactory = new BlocksFromSymbolsFactory(spacers, blockCreatorsMap);
        return blocksFactory;
    }

    /**
     * receive a buffered reader of a text file and turn it into a list of strings.
     * each string is a line in the file.
     * comment and spacer lines are ignored.
     *
     * @param buffered - buffered reader.
     * @return list of lines.
     */
    public static List<String> turnFileToStringList(BufferedReader buffered) {
        // create an empty list of lines.
        List<String> list = new ArrayList<>();
        // throw io exception if something goes wrong with reading the file.
        try {
            // read the first line.
            String line = buffered.readLine();
            // while line is not null, read the whole file.
            while (line != null) {
                // trim the line.
                line = line.trim();
                // ignore comments and empty lines.
                if ((!line.startsWith("#")) && (!line.equals(""))) {
                    list.add(line);
                }
                // move to next line.
                line = buffered.readLine();
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        // return the new string list consisting the lines.
        return list;
    }

    /**
     * receive a simplified list of strings and turn it into a structure of 1-3 maps.
     *
     * @param list - the list of the level's blocks description.
     * @return map of defaults.
     */
    public static TreeMap<String, String> createDefaultsMap(List<String> list) {
        TreeMap<String, String> defaults = new TreeMap<>();
        for (String line : list) {
            if (line.startsWith("default")) {
                line = line.replace("default", "");
                line = line.trim();
                String[] splitLine = line.split(" ");
                for (String pair : splitLine) {
                    String[] splitPair = pair.split(":");
                    String key = splitPair[0];
                    String value = splitPair[1];
                    defaults.put(key, value);
                }
            }
        }
        return defaults;
    }

    /**
     * receive a simplified list of strings and turn it into a structure of 1-3 maps.
     * @param list - the list of the level's blocks description.
     * @return map of spacers.
     */
    public static TreeMap<String, Integer> createSpacersMap(List<String> list) {
        TreeMap<String, Integer> spacers = new TreeMap<>();
        for (String line : list) {
            if (line.startsWith("sdef")) {
                line = line.replace("sdef", "");
                line = line.trim();
                String[] splitLine = line.split(" ");
                String key = splitLine[0];
                key = key.replace("symbol:", "");
                key = key.trim();
                String valueString = splitLine[1];
                valueString = valueString.replace("width:", "");
                int value = Integer.parseInt(valueString.trim());
                spacers.put(key, value);
                }
            }
        return spacers;
    }

    /**
     * receive a simplified list of strings and turn it into a structure of 1-3 maps.
     * @param list - the list of the level's blocks description.
     * @return temp map of blocks.
     */
    public static TreeMap<String, String> createTempBlocksMap(List<String> list) {
        TreeMap<String, String> blocks = new TreeMap<>();
        for (String line : list) {
            if (line.startsWith("bdef")) {
                line = line.replace("bdef", "");
                line = line.trim();
                String[] splitLine = line.split(" ");
                String key = splitLine[0];
                line = line.replace(key, "");
                key = key.replace("symbol:", "");
                key = key.trim();
                line = line.trim();
                blocks.put(key, line);
            }
        }
        return blocks;
    }

    /**
     * receives a temp blocks map and turns it into a detailed map of strings and maps.
     * @param tempMap - map with symbols and long lines.
     * @return map with possible 3 keys - defaults, blocks and spacers.
     *         each key holds a string->string map with blocks description.
     */
    public static TreeMap<String, TreeMap<String, String>> createBlocksMap(TreeMap<String, String> tempMap) {
        TreeMap<String, TreeMap<String, String>> blocksMap = new TreeMap<>();
        Set<String> keySet = tempMap.keySet();
        for (String key : keySet) {
            TreeMap<String, String> blocksDetails = new TreeMap<>();
            String description = tempMap.get(key);
            String[] splitLine = description.split(" ");
            for (String pair : splitLine) {
                String[] splitPair = pair.split(":");
                String parameter = splitPair[0];
                String value = splitPair[1];
                blocksDetails.put(parameter, value);
            }
            blocksMap.put(key, blocksDetails);
        }
        return blocksMap;
    }

    /**
     * makes a final map of the blocks using the defaults map to if the block has no parameter of itself's,
     * or the specified parameters.
     * @param defaults - map of the defaults.
     * @param blocks - the list of the level's blocks description.
     */
    public static void mergeBlocksAndDefaults(
    TreeMap<String, String> defaults, TreeMap<String, TreeMap<String, String>> blocks) {
        // create an empty blocks tree map.
        Set<String> defaultsKeySet = defaults.keySet();
        Set<String> blocksKeySet = blocks.keySet();
        for (String blockKey : blocksKeySet) {
            for (String defaultsKey : defaultsKeySet) {
                if (!blocks.get(blockKey).containsKey(defaultsKey)) {
                    blocks.get(blockKey).put(defaultsKey, defaults.get(defaultsKey));
                }
            }
        }
    }

    /**
     * receives a final blocks info map made of strings only.
     * creates a BlockCreator object of each block type, and returns a map from string to blockcreator.
     * @param blocks - map of strings to maps.
     * @return map of strings to BlockCreator objects.
     */
    public static TreeMap<String, BlockCreator> createBlockCreators(TreeMap<String, TreeMap<String, String>> blocks) {
        TreeMap<String, BlockCreator> creators = new TreeMap<>();
        Set<String> symbols = blocks.keySet();
        for (String symbol : symbols) {
            BlockCreator factory = new SingleBlockFactory(symbol, blocks);
            creators.put(symbol, factory);
        }
        return creators;
    }

}