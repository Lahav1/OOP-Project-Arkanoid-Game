package menu;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/**
 * This class is used for creation of the menu screen animation controlled by keyboard.
 * @author Lahav Amsalem 204632566
 */
public class MenuAnimation implements Menu<Task<Void>> {
    private KeyboardSensor keyboard;
    private Task<Void> status;
    private boolean stop;
    private List<TaskInfo> taskList;
    private String title;
    private Image logo;

    /**
     * Constructor creates a menu animation object.
     * @param k - keyboard.
     * @param menuTitle - title of the menu.
     */
    public MenuAnimation(KeyboardSensor k, String menuTitle) {
        this.keyboard = k;
        this.title = menuTitle;
        this.taskList = new ArrayList<TaskInfo>();
        this.stop = false;
        this.logo = null;
    }

    /**
     * pause screen logic and drawings.
     * @param d - drawsurface.
     * @param dt ignored.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        // fill the background colors and the frames
        d.setColor(new Color(28, 63, 146));
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(new Color(243, 24, 50));
        d.fillRectangle(25, 25, 750, 550);
        d.fillRectangle(30, 30, 740, 540);
        d.setColor(Color.BLACK);
        d.fillRectangle(30, 30, 740, 540);

        // draw the building
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle(80, 420, 104, 150);
        d.setColor(Color.WHITE);
        for (int i = 0; i < 6; i++) {
            d.fillRectangle(87 + i * 16, 430, 10, 20);
        }
        for (int i = 0; i < 6; i++) {
            d.fillRectangle(87 + i * 16, 460, 10, 20);
        }
        for (int i = 0; i < 6; i++) {
            d.fillRectangle(87 + i * 16, 490, 10, 20);
        }
        for (int i = 0; i < 6; i++) {
            d.fillRectangle(87 + i * 16, 520, 10, 20);
        }
        for (int i = 0; i < 6; i++) {
            d.fillRectangle(87 + i * 16, 550, 10, 16);
        }
        d.setColor(Color.DARK_GRAY);
        d.fillRectangle(112, 370, 40, 50);
        d.setColor(Color.darkGray);
        d.fillRectangle(127, 200, 10, 170);
        d.setColor(Color.orange);
        d.fillCircle(132, 190, 10);
        d.setColor(Color.RED);
        d.fillCircle(132, 190, 7);
        d.setColor(Color.white);
        d.fillCircle(132, 190, 3);
        d.setColor(Color.WHITE);

        // draw the game logo
        try {
            this.logo = ImageIO.read(new File("resources/background_images/arkanoid.jpg"));
        } catch (IOException e) {
            System.out.println("File does not exist");
        }
        if (this.logo != null) {
            d.drawImage(200, 150, this.logo);
        }
        // print the title of the menu.
        d.setColor(new Color(28, 63, 146));
        d.drawText(215, 400, this.title, 35);
        // print each task's message.
        d.setColor(Color.white);
        for (int i = 0; i < this.taskList.size(); i++) {
            d.drawText(250, 450 + i * 40, this.taskList.get(i).getMessage(), 25);
        }
        // check if a relevant key was pressed and change the status accordingly.
        for (TaskInfo t : this.taskList) {
            if (this.keyboard.isPressed(t.getKey())) {
                this.status = t.getReturnVal();
                this.stop = true;
            }
        }
    }

    /**
     * return true if the animation should stop and false if not.
     * @return should stop or not.
     */
    public boolean shouldStop() {
        if (this.stop) {
            this.stop = false;
            return true;
        }
        return false;
    }

    /**
     * adds a new selection to the menu.
     * @param key key to wait for.
     * @param message line to print.
     * @param returnVal what to return.
     */
    public void addSelection(String key, String message, Task<Void> returnVal) {
        TaskInfo task = new TaskInfo(key, message, returnVal);
        this.taskList.add(task);
    }

    /**
     * @return the status of the screen choice.
     */
    public Task<Void> getStatus() {
        return this.status;
    }

    /**
     * remove all the tasks from list.
     */
    public void resetTasks() {
        this.taskList.clear();
    }

    /**
     * add a sub menu to the main menu.
     * @param key - the key that leads to the sub menu.
     * @param message - the message that the user sees on the screen.
     * @param subMenu - the menu itself.
     */
    public void addSubMenu(String key, String message, Menu<Task<Void>> subMenu) {
        this.addSelection(key, message, subMenu.getStatus());
    }
}
