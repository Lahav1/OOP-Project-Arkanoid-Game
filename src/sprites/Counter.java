package sprites;

/**
 * This class is used to help us count objects.
 * @author Lahav Amsalem 204632566
 */
public class Counter {
    //only member is the int value of the count.
    private int value;

    /**
     * increase the count by number.
     * @param number - the number we want to add.
     */
    public void increase(int number) {
        this.value = this.value + number;
    }

    /**
     * decrease the count by number.
     * @param number - the number we want to minus.
     */
    public void decrease(int number) {
        this.value = this.value - number;
    }

    /**
     * accessor to count value.
     * @return the value of the count.
     */
    public int getValue() {
        return this.value;
    }
}
