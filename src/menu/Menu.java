package menu;
import game.Animation;

/**
 * interface for menu.
 * @param <T> task type.
 * @author Lahav Amsalem 204632566
 */
public interface Menu<T> extends Animation {

    /**
     * adds a new selection to the menu.
     * @param key key to wait for.
     * @param message line to print.
     * @param returnVal what to return.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * @return the status of the screen choice.
     */
    T getStatus();

    /**
     * add a sub menu to another menu.
     * @param key - the key that leads to the sub menu.
     * @param message - the message that the user sees on the screen.
     * @param subMenu - the menu itself.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}