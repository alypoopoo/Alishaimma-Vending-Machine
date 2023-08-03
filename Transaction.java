/**
 * This class represents the transactions of the vending machine
 */
public class Transaction {
    private Item item;
    private int quantity;

    /**
     * This is the constructor of a transaction given the item and the quantity
     * 
     * @param item     the item purchased by users
     * @param quantity the quantity of the item purchased
     */
    public Transaction(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    /**
     * Gets the item of the transaction
     * 
     * @return the item purchased
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gets the quantity of the item purchased
     * 
     * @return the quantity of the purchased item
     */
    public int getQuantity() {
        return quantity;
    }
}
