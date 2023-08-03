import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent the Custom Meal Frame of the Prepare Meal Frame for
 * the GUI of the vending machine application.
 */
public class CustomMealFrame extends JFrame {
    private RegularVendingMachine vendingMachine;
    private List<ItemButton> itemButtons; // List to store the item buttons
    private List<ItemButton> selectedItems; // List to store the selected items
    private DefaultTableModel tableModel; // Table model to hold selected items
    private JTable selectedItemsTable; // Table to display selected items
    private Map<String, Integer> itemQuantities; // Map to store the quantity of each item

    private JLabel totalPriceLabel; // Label for total price
    private JLabel totalCaloriesLabel; // Label for total calories

    /**
     * Constructs the Custom Meal Frame for the vending machine's GUI.
     * 
     * @param vendingMachine the Regular Vending Machine instance used
     */
    public CustomMealFrame(RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
        this.itemButtons = new ArrayList<>();
        this.selectedItems = new ArrayList<>();
        this.itemQuantities = new HashMap<>();

        setTitle("Alishaimma Vending Machine - Custom Meal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        displayItems();
        setupSelectedItemsTable();
        setupTotalLabels();

        pack();
        setLocationRelativeTo(null);

    }

    /**
     * Displays each item in a button panel
     */
    private void displayItems() {
        JPanel itemButtonsPanel = new JPanel(new GridLayout(3, 3, 10, 10));

        for (int i = 0; i < 9; i++) {
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
                        handleItemSelection(itemButton);
                    }
                });

                itemButtons.add(itemButton);
                itemButtonsPanel.add(itemButton);
            }
        }

        add(itemButtonsPanel, BorderLayout.CENTER);
    }

    /**
     * Handles the selected item and updates the table that shows selected items
     * 
     * @param itemButton the ItemButton instance used
     */
    private void handleItemSelection(ItemButton itemButton) {
        String itemName = itemButton.getItemName();

        // Check if the item is already selected
        if (itemQuantities.containsKey(itemName)) {
            // Increment the quantity if the same one is selected multiple times
            int quantity = itemQuantities.get(itemName);
            itemQuantities.put(itemName, quantity + 1);
        } else {
            // Do not increment since only pressed once
            itemQuantities.put(itemName, 1);
            selectedItems.add(itemButton);
        }
        updateSelectedItemsTable();
    }

    /**
     * Initializes the table that shows selected items
     */
    private void setupSelectedItemsTable() {
        tableModel = new DefaultTableModel(new Object[] { "Item", "Price", "Calories", "Quantity" }, 0);
        selectedItemsTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(selectedItemsTable);
        add(tableScrollPane, BorderLayout.EAST);
    }

    /**
     * Inializes table that shows total price and total calories
     */
    private void setupTotalLabels() {
        JPanel totalPanel = new JPanel(new GridLayout(3, 1));
        totalPriceLabel = new JLabel("Total Price: PHP 0.0");
        totalCaloriesLabel = new JLabel("Total Calories: 0.0");
        totalPanel.add(totalPriceLabel);
        totalPanel.add(totalCaloriesLabel);

        JButton resetButton = new JButton("Reset"); // reset button in case user wants to redo selected items
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetCart();
            }
        });
        totalPanel.add(resetButton);

        JButton checkoutButton = new JButton("Checkout"); // Checkout button to proceed with customization
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkout();
            }
        });
        totalPanel.add(checkoutButton);

        add(totalPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the table that shows all selected items
     */
    private void updateSelectedItemsTable() {
        tableModel.setRowCount(0);

        double totalPrice = 0;
        double totalCalories = 0;

        for (ItemButton itemButton : selectedItems) {
            String itemName = itemButton.getItemName();
            double price = itemButton.getPrice();
            double calories = itemButton.getCalories();
            int quantity = itemQuantities.getOrDefault(itemName, 0);

            totalPrice += price * quantity;
            totalCalories += calories * quantity;

            Object[] rowData = { itemName, price, calories, quantity };
            tableModel.addRow(rowData);
        }

        totalPriceLabel.setText("Total Price: PHP " + totalPrice);
        totalCaloriesLabel.setText("Total Calories: " + totalCalories);
    }

    /**
     * Handles reset of selected items list
     */
    public void resetCart() {
        selectedItems.clear();
        itemQuantities.clear();
        for (ItemButton itemButton : itemButtons) {
            itemButton.setBackground(null); // Reset the background color of item buttons

        }
        updateSelectedItemsTable();
    }

    /**
     * Handles checkout of customization meal
     */
    private void checkout() {
        // Check if no items are selected
        if (selectedItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items selected. Purchase unsuccessful.", "Checkout Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String firstItemName = selectedItems.get(0).getItemName().toLowerCase();
        boolean cannotBeSoldIndividually = true;
        for (ItemButton itemButton : selectedItems) {
            String itemName = itemButton.getItemName().toLowerCase();
            // Items that cannot be sold individually: red bean, nata de coco, kaong,
            // jackfruit, pinipig
            if (!itemName.equals(firstItemName) || (!itemName.equals("red bean")
                    && !itemName.equals("nata de coco") && !itemName.equals("kaong")
                    && !itemName.equals("jackfruit") && !itemName.equals("pinipig"))) {
                cannotBeSoldIndividually = false;
                break;
            }
        }

        if (cannotBeSoldIndividually) {
            JOptionPane.showMessageDialog(this,
                    "The selected item(s): " + firstItemName + " cannot be bought individually.", "Purchase Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate the total price of selected items
        double totalPrice = 0;
        for (ItemButton itemButton : selectedItems) {
            String itemName = itemButton.getItemName();
            double price = itemButton.getPrice();
            int quantity = itemQuantities.getOrDefault(itemName, 0);
            totalPrice += price * quantity;
        }

        // Payment process of the checkout
        double amountPaid = askPaymentAmount();
        if (amountPaid < totalPrice) {
            JOptionPane.showMessageDialog(this, "Insufficient amount. Please enter a valid amount.", "Payment Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!vendingMachine.isChangePossible(amountPaid, totalPrice)) {
            JOptionPane.showMessageDialog(this,
                    "Change cannot be provided in the required denominations. Please contact maintenance.",
                    "Purchase Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate and display the change
        double change = amountPaid - totalPrice;
        JOptionPane.showMessageDialog(this,
                "Payment successful!\nTotal Price: PHP " + totalPrice + "\nChange: PHP " + change,
                "Payment Successful", JOptionPane.INFORMATION_MESSAGE);

        updateSelectedItemsTable();

        for (ItemButton itemButton : selectedItems) {
            String itemName = itemButton.getItemName();
            int quantity = itemQuantities.getOrDefault(itemName, 0);

            int slotNumber = -1;
            for (int i = 0; i < 9; i++) { // find slot number based on item name
                ItemSlot slot = vendingMachine.getSlot(i);
                if (slot != null && slot.getItem().getName().equals(itemName)) {
                    slotNumber = i;
                    break;
                }
            }

            if (slotNumber != -1) {
                // handles sellItem method so that variables are updated (quantity, total sales,
                // etc)
                vendingMachine.sellItem(slotNumber, quantity, amountPaid, totalPrice);
            } else {
                System.out.println("Item not found in the vending machine: " + itemName);
            }
        }

        resetCart(); // Reset the cart after successful payment
        dispose();
    }

    /**
     * Asks for the payment in specific denominations using the GUI
     * 
     * @return amount paid
     */
    private double askPaymentAmount() {

        double amount = 0;
        String input;

        input = JOptionPane.showInputDialog(this, "Enter the number of [PHP 1] coins:");
        int quantity1 = Integer.parseInt(input);
        amount += quantity1 * 1;

        input = JOptionPane.showInputDialog(this, "Enter the number of [PHP 5] coins:");
        int quantity2 = Integer.parseInt(input);
        amount += quantity2 * 5;

        input = JOptionPane.showInputDialog(this, "Enter the number of [PHP 10] coins:");
        int quantity3 = Integer.parseInt(input);
        amount += quantity3 * 10;

        input = JOptionPane.showInputDialog(this, "Enter the number of [PHP 20] bills:");
        int quantity4 = Integer.parseInt(input);
        amount += quantity4 * 20;

        input = JOptionPane.showInputDialog(this, "Enter the number of [PHP 50] bills:");
        int quantity5 = Integer.parseInt(input);
        amount += quantity5 * 50;

        input = JOptionPane.showInputDialog(this, "Enter the number of [PHP 100] bills:");
        int quantity6 = Integer.parseInt(input);
        amount += quantity6 * 100;

        input = JOptionPane.showInputDialog(this, "Enter the number of [PHP 200] bills:");
        int quantity7 = Integer.parseInt(input);
        amount += quantity7 * 200;

        input = JOptionPane.showInputDialog(this, "Enter the number of [PHP 500] bills:");
        int quantity8 = Integer.parseInt(input);
        amount += quantity8 * 500;

        return amount;
    }

}
