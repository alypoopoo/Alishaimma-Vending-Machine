/**
 * This class represents the item slot of the vending machine
 */
public class ItemSlot {
    private Item item;
    private int quantity;

    /**
     * This is a constructor of ItemSlot given the item and the quantity
     * 
     * @param item     item in the item slot
     * @param quantity quantity of that item in the slot
     */
    public ItemSlot(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    /**
     * Gets the item of an item slot
     * 
     * @return the item associated with the slot
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the item of a slot
     * 
     * @param item the item of the slot
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Gets the quantity of an item of the item slot
     * 
     * @return the quantity of the item of the slot
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of an item of a slot
     * 
     * @param quantity the quantity of an item of a slot
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Decreases the quantity of an item of a slot given the amount to be deducted
     * If the resulting quantity is less than zero, it is set to zero since quantity
     * should be non-negative
     * 
     * @param quantity the quantity to be deducted
     */
    public void decreaseItemQuantity(int quantity) {
        this.quantity -= quantity;
        if (this.quantity < 0) {
            this.quantity = 0;
        }
    }

    /**
     * Adds the quantity of an item of a slot given the amount to be added
     * If quantity to be added is less than 10, it proceeds with adding since
     * maximum
     * capacity of a slot is 10
     * 
     * @param quantity the quantity to be added
     */
    public void addItemQuantity(int quantity) {
        if (this.quantity < 10)
            this.quantity += quantity;
    }
}
