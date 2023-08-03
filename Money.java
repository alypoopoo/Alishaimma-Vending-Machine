/**
 * This class represent the money of the users and the money in the vending
 * machine
 */
public class Money {
    private int[] denominations;
    private int[] quantities;

    /**
     * This is the constructor of a money object with the valid money denominations
     * and quantity
     * 
     */
    public Money() {
        denominations = new int[] { 1, 5, 10, 20, 50, 100, 200, 500 };
        quantities = new int[denominations.length];
    }

    /**
     * Gets the array of valid denominations
     * 
     * @return the array of denominations
     */
    public int[] getDenominations() {
        return denominations;
    }

    /**
     * Gets the corresponding quantities of the denominations array
     * 
     * @return the array of quantites
     */
    public int[] getQuantities() {
        return quantities;
    }

}
