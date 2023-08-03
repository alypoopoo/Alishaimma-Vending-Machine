import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a regular vending machine
 */
public class RegularVendingMachine {
    private ItemSlot[] slots;
    private double balance;
    private List<Transaction> transactions;
    private double totalSales;
    private Money money;

    /**
     * This is the constructor of a regular vending machine object with that
     * contains slots for items,
     * machine's balance, a list of transactions, and total sales.
     */
    public RegularVendingMachine() {
        slots = new ItemSlot[9];
        balance = 0.0;
        transactions = new ArrayList<>();
        totalSales = 0.0;
        money = new Money();

        slots[0] = new ItemSlot(new Item("Red Bean", 5, 50), 10);
        slots[1] = new ItemSlot(new Item("Ube Ice Cream", 65, 200), 10);
        slots[2] = new ItemSlot(new Item("Leche Flan", 8, 80), 10);
        slots[3] = new ItemSlot(new Item("Banana", 12, 30), 10);
        slots[4] = new ItemSlot(new Item("Nata de Coco", 15, 20), 10);
        slots[5] = new ItemSlot(new Item("Kaong", 20, 25), 10);
        slots[6] = new ItemSlot(new Item("Jackfruit", 10, 30), 10);
        slots[7] = new ItemSlot(new Item("Pinipig", 5, 40), 10);
        slots[8] = new ItemSlot(new Item("Milk", 30, 40), 10);
    }

    /**
     * Gets the item slot of an item in the vending machine
     * 
     * @param slotNumber the slot number of the desired item
     * @return the slot of the desired item or null is slotNumber does not exist
     */
    public ItemSlot getSlot(int slotNumber) {
        if (slotNumber >= 0 && slotNumber < slots.length) {
            return slots[slotNumber];
        } else {
            System.out.println("Invalid slot number. Please select a valid slot.");
            return null;
        }
    }

    /**
     * Gets the balance of the vending machine
     * 
     * @return the balance of the machine when called
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the transactions of the vending machine
     * 
     * @return the transactions of the machine when called
     */
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    /**
     * Restocks an item by increasing its quantity.
     * If the slot as been emptied, a new item can be stocked.
     * 
     * @param item       the item to be added in slot
     * @param quantity   quantity to be added
     * @param slotNumber slot number of item to be restocked
     */
    public void restock(Item item, int quantity, int slotNumber) {
        if (slotNumber >= 0 && slotNumber < slots.length) {
            ItemSlot slot = slots[slotNumber];
            if (slot == null) {
                slot = new ItemSlot(item, quantity);
                slots[slotNumber] = slot;
            } else {
                slot.addItemQuantity(quantity);
            }
        }
    }

    /**
     * Checks if the slot number given is occupied by an item and valid.
     * 
     * @param slotNumber the slot number inputted by chooser
     * @return the slot of the slot number (if valid), or false (if not found)
     */
    public boolean isSlotOccupied(int slotNumber) {
        if (slotNumber >= 0 && slotNumber < slots.length) {
            return slots[slotNumber] != null;
        } else {
            return false;
        }
    }

    /**
     * Checks if able to produce change in the right denominations
     * considering the quantity of denomination in the machine
     * 
     * @param amount     the amount entered by the user as their payment
     * @param totalPrice the total price of their purchase
     * @return true if providing change is possible, false if not possible
     */
    public boolean isChangePossible(double amount, double totalPrice) {
        int remainingAmount = (int) ((amount - totalPrice) * 100);
        int[] availableQuantities = Arrays.copyOf(money.getQuantities(), money.getQuantities().length);
        int[] denominations = money.getDenominations();

        for (int i = denominations.length - 1; i >= 0; i--) {
            int denomination = denominations[i];
            int numDenominationNeeded = remainingAmount / (denomination * 100);
            if (numDenominationNeeded > availableQuantities[i]) {
                numDenominationNeeded = availableQuantities[i];
            }
            remainingAmount -= numDenominationNeeded * (denomination * 100);
        }

        return remainingAmount == 0 && remainingAmount % 100 == 0;
    }

    /**
     * Calculates the change and shows a breakdown of it.
     * 
     * @param change the expected change of the user
     */
    public void calculateChange(double change) {
        int[] denominations = { 500, 100, 50, 20, 10, 5, 1 };
        int[] changeCount = new int[denominations.length];

        for (int i = 0; i < denominations.length; i++) {
            if (change >= denominations[i]) {
                changeCount[i] = (int) (change / denominations[i]);
                change -= changeCount[i] * denominations[i];

                // Deduct the quantity of denominations from the machine
                replenishMoney(denominations[i], -changeCount[i]);
            }
        }

        // Print the change provided
        System.out.println("Change provided:");
        for (int i = 0; i < denominations.length; i++) {
            if (changeCount[i] > 0) {
                System.out.println(changeCount[i] + " x PHP" + denominations[i] + ".0");
            }
        }
    }

    /**
     * Adds transaction to transaction list whenever someone uses the vending
     * machine
     * 
     * @param item     the item purchased
     * @param quantity the quantity of the purchased item
     */
    private void addTransaction(Item item, int quantity) {
        Transaction transaction = new Transaction(item, quantity);
        transactions.add(transaction);
    }

    /**
     * Handles the process of a user's purchase. Sells an item from the specified
     * slot number,
     * given the quantity, amount provided by user, and total price of purchase.
     * 
     * @param slotNumber the slot number of the desired item
     * @param quantity   the quantity of the desired item
     * @param amount     the amount payed by user
     * @param totalPrice the expected amount payed by the user (total amount due of
     *                   user's purchase)
     */
    public void sellItem(int slotNumber, int quantity, double amount, double totalPrice) {
        ItemSlot slot = slots[slotNumber];
        Item item = slot.getItem();

        if (slot.getQuantity() >= quantity) {
            if (amount >= totalPrice) {
                if (isChangePossible(amount, totalPrice)) {
                    slot.decreaseItemQuantity(quantity);
                    totalSales += totalPrice;
                    calculateChange(amount - totalPrice);
                    addTransaction(item, quantity);
                    System.out.println("Purchase successful!");
                } else {
                    System.out.println(
                            "Change cannot be provided in the required denominations. Please contact maintenance.");
                }
            } else {
                System.out.println("Insufficient amount. Please enter a valid amount.");
            }
        } else {
            System.out.println("Insufficient quantity. Purchase unsuccessful.");
        }
    }

    /**
     * Replenished money in the vending machine given the denomination and the
     * quantity of it.
     * 
     * @param denomination the denominatin of choice from the valid denominations
     * @param quantity     the quantity of the denomination to be replenished
     */
    public void replenishMoney(double denomination, int quantity) {
        if (denomination > 0 && quantity > 0) {
            int[] denominations = money.getDenominations();
            int[] quantities = money.getQuantities();

            for (int i = 0; i < denominations.length; i++) {
                if (denominations[i] == denomination) {
                    quantities[i] += quantity;
                    balance += denomination * quantity;
                    break;
                }
            }
        }
    }

    /**
     * Collects money from the machine and resets it to 0 (meaning machine balance
     * is empty)
     * 
     * @return the collected amount from the machine
     */
    public double collectMoney() {
        double collectedAmount = totalSales;
        totalSales = 0.0;
        return collectedAmount;
    }

    /**
     * Displays Items of the regular vending machine including its slot number, name
     * of item,
     * quantity available, price and calories.
     */
    public void displayItems() {
        System.out.println("*===========================================================*");
        System.out.println("│                     AVAILABLE ITEMS                       │");
        System.out.println("*============================================================*");
        System.out.println("| Slot  |      Item      |  Quantity  |  Price  |  Calories |");
        System.out.println("=============================================================");
        for (int i = 0; i < slots.length; i++) {
            ItemSlot slot = slots[i];
            if (slot != null) {
                Item item = slot.getItem();
                int quantity = slot.getQuantity();
                double price = item.getPrice();
                double calories = item.getCalories();
                System.out.printf("|  %-4d |  %-13s |   %-8d |  P%-5.2f |  %-8.2f |\n", i, item.getName(), quantity,
                        price, calories);
            } else {
                System.out.printf("|  %-4d |  %-13s |   %-8d |         |          |\n", i, "EMPTY", 0);
            }
        }
        System.out.println("=============================================================");
    }

    /**
     * Displays a summary of transaction.
     * This basically shows the item name and how many of it has been purchased.
     */
    public void displayTransactionSummary() {
        System.out.println("*=====================================*");
        System.out.println("│          TRANSACTION SUMMARY        │");
        System.out.println("*=====================================*");
        System.out.println("| Item Name      | Quantity Sold       |");
        System.out.println("=======================================");
        for (Transaction transaction : transactions) {
            Item item = transaction.getItem();
            int quantity = transaction.getQuantity();
            System.out.printf("| %-15s | %-18d |\n", item.getName(), quantity);
        }
        System.out.println("=======================================");
        double totalSales = getTotalSales();
        System.out.println("Total Amount Collected: " + totalSales);
    }

    /**
     * Gets the total sales made using the machine
     * 
     * @return the total sales amount of the machine when called
     */
    protected double getTotalSales() {
        return totalSales;
    }

    /**
     * Gets the item slots of the machine
     * 
     * @return the slots of the machine
     */
    protected ItemSlot[] getSlots() {
        return slots;
    }
}