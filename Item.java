/**
 * This class represents the items placed in item slot of the vending machine
 */
public class Item {
    private String name;
    private double price;
    private double calories;

    /**
     * This is a constructor of Item given the name, price and calories
     * 
     * @param name     the name of the item
     * @param price    the price of the item (PHP)
     * @param calories the calories found in item
     */
    public Item(String name, double price, double calories) {
        this.name = name;
        this.price = price;
        this.calories = calories;
    }

    /**
     * Gets the name of an item
     * 
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of an item
     * 
     * @param name the name of an item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of an item
     * 
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of an item
     * 
     * @param price the price of the item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the calories of an item
     * 
     * @return the calories of the item
     */
    public double getCalories() {
        return calories;
    }

    /**
     * Sets the calories of an item
     * 
     * @param calories the calories of the item
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }
}