package menu;

/**
 * interface for menu.
 * @param <T> task type.
 * @author Lahav Amsalem 204632566
 */
public interface Task<T> {

    /**
     * used for running the specific task.
     * @return task type.
     */
    T run();
}
