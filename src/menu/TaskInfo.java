package menu;

/**
 * This class is used for folding the information of each task in the menu task list.
 * @author Lahav Amsalem 204632566
 */
public class TaskInfo {
    private String key;
    private String message;
    private Task<Void> returnVal;

    /**
     * Constructor creates a menu animation object.
     * @param k - key.
     * @param m - message.
     * @param r - return val.
     */
    public TaskInfo(String k, String m, Task<Void> r) {
        this.key = k;
        this.message = m;
        this.returnVal = r;
    }

    /**
     * @return key.
     */
    public String getKey() {
        return this.key;
    }

    /**
     * @return message.
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @return task.
     */
    public Task<Void> getReturnVal() {
        return this.returnVal;
    }
}


