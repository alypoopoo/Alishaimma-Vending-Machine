import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This class represents the Buy Frame used in the Main Frame for
 * the GUI of the vending machine application.
 */
public class BuyFrame extends JFrame {
    private RegularVendingMachine vendingMachine;

    /**
     * Constucts the Buy Frame for the vending machine's GUI.
     * 
     * @param vendingMachine the RegularVendingMachine instance used for the
     *                       application.
     */
    public BuyFrame(RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;

        setTitle("Alishaimma Vending Machine - Buy an Item");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 3, 10, 10)); // 3 rows, 3 columns, with 10px horizontal and vertical gaps

        displayItems();

        pack(); // Automatically adjust the size of the frame to fit all components
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Displays and creates the buttons for the items in the vending machine.
     */
    private void displayItems() {
        for (int i = 0; i < 9; i++) { // 9 since that is total slot count
            ItemSlot slot = vendingMachine.getSlot(i);

            if (slot != null) {
                Item item = slot.getItem();
                int quantityAvailable = slot.getQuantity();
                double price = item.getPrice();
                double calories = item.getCalories();

                String itemDetails = "Item: " + item.getName() + "\n"
                        + "Price: PHP " + price + "\n"
                        + "Quantity: " + quantityAvailable + "\n"
                        + "Calories: " + calories + "\n";

                ItemButton itemButton = new ItemButton(itemDetails, item.getName(), price, quantityAvailable, calories);

                itemButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleBuyAction(itemButton);
                    }
                });

                add(itemButton);
            }
        }
    }

    /**
     * Handles the buy action when the user choose the "Buy an Item" option.
     * 
     * @param itemButton ItemButton representing the item choice of purchase
     */
    private void handleBuyAction(ItemButton itemButton) {
        String itemName = itemButton.getItemName();
        double price = itemButton.getPrice();
        int quantityAvailable = itemButton.getQuantity();
        double calories = itemButton.getCalories();

        int slotNumber = -1;

        for (int i = 0; i < 9; i++) { // 9 since that is total slot count
            ItemSlot slot = vendingMachine.getSlot(i);
            if (slot != null && slot.getItem().getName().equals(itemName)) {
                slotNumber = i;
                break;
            }
        }

        if (slotNumber == -1) {
            JOptionPane.showMessageDialog(this, "Item " + itemName + " not found in any slot.", "Purchase Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (quantityAvailable == 0) {
            JOptionPane.showMessageDialog(this, "Slot " + slotNumber + " is empty. Please select another item.",
                    "Purchase Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantityToBuy = Integer.parseInt(JOptionPane.showInputDialog(this,
                "Enter the quantity to buy (1-" + quantityAvailable + "):", "Quantity", JOptionPane.PLAIN_MESSAGE));
        if (quantityToBuy <= 0 || quantityToBuy > quantityAvailable) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a valid quantity.", "Purchase Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalPrice = price * quantityToBuy;

        // Accept amount in specific denominations
        int quantity1 = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of [PHP 1] coins:",
                "Payment", JOptionPane.PLAIN_MESSAGE));
        int quantity2 = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of [PHP 5] coins:",
                "Payment", JOptionPane.PLAIN_MESSAGE));
        int quantity3 = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of [PHP 10] coins:",
                "Payment", JOptionPane.PLAIN_MESSAGE));
        int quantity4 = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of [PHP 20] bills:",
                "Payment", JOptionPane.PLAIN_MESSAGE));
        int quantity5 = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of [PHP 50] bills:",
                "Payment", JOptionPane.PLAIN_MESSAGE));
        int quantity6 = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of [PHP 100] bills:",
                "Payment", JOptionPane.PLAIN_MESSAGE));
        int quantity7 = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of [PHP 200] bills:",
                "Payment", JOptionPane.PLAIN_MESSAGE));
        int quantity8 = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the number of [PHP 500] bills:",
                "Payment", JOptionPane.PLAIN_MESSAGE));

        int amountPaid = quantity1 * 1 + quantity2 * 5 + quantity3 * 10 + quantity4 * 20 +
                quantity5 * 50 + quantity6 * 100 + quantity7 * 200 + quantity8 * 500;

        if (amountPaid < totalPrice) {
            JOptionPane.showMessageDialog(this, "Insufficient amount. Please enter a valid amount.", "Purchase Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!vendingMachine.isChangePossible(amountPaid, totalPrice)) {
            JOptionPane.showMessageDialog(this,
                    "Change cannot be provided in the required denominations. Please contact maintenance.",
                    "Purchase Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Perform the purchase and update the vending machine values (quantity, total
        // sales, etc.)
        vendingMachine.sellItem(slotNumber, quantityToBuy, amountPaid, totalPrice);
        int newQuantity = vendingMachine.getSlot(slotNumber).getQuantity();

        String successMessage = "Purchase successful!\n"
                + "Item: " + itemName + "\n"
                + "Quantity: " + quantityToBuy + "\n"
                + "Total Price: PHP " + totalPrice + "\n"
                + "Change: PHP " + (amountPaid - totalPrice);
        JOptionPane.showMessageDialog(this, successMessage, "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);

        // Update the item button text with the updated quantity
        String updatedItemDetails = "Item: " + itemName + "\n"
                + "Price: PHP " + price + "\n"
                + "Quantity: " + newQuantity + "\n"
                + "Calories: " + calories + "\n";
        itemButton.setText(updatedItemDetails);

        dispose();
    }
}

/**
 * This class represent the ItemButton and the details associated with it.
 */
class ItemButton extends JButton {
    private String itemName;
    private double price;
    private int quantity;
    private double calories;

    /**
     * Constructs the ItemButton and its proper formatting.
     * 
     * @param text     details to be displayed in item button
     * @param itemName item name of the item in the item slot
     * @param price    price of the item in PHP
     * @param quantity quantity of the item in the item slot
     * @param calories calories of the item in the item slot
     */
    public ItemButton(String text, String itemName, double price, int quantity, double calories) {
        super("<html>" + text.replaceAll("\n", "<br>") + "</html>");
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.calories = calories;

        setPreferredSize(new Dimension(115, 80));
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Getter method for the item's name.
     * 
     * @return name of the item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Getter method for the item's price.
     * 
     * @return price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter method for the item's quantity.
     * 
     * @return quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Getter method for the item's calories.
     * 
     * @return calories of the item
     */
    public double getCalories() {
        return calories;
    }

}
