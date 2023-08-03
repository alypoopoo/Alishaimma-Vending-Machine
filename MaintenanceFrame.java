import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represent the Maintenance Frame in the Main Frame for
 * the GUI of the vending machine application.
 */
public class MaintenanceFrame extends JFrame {
    private RegularVendingMachine vendingMachine;

    /**
     * Constructs the Maintenance Frame for the vending machine's GUI.
     * 
     * @param vendingMachine the RegularVendingMachine instance used for the
     *                       application.
     */
    public MaintenanceFrame(RegularVendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;

        setTitle("Alishaimma Vending Machine: Maintenance");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel maintenanceLabel = new JLabel("Maintenance Features");
        maintenanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        maintenanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton restockButton = new JButton("Restock an Item");
        JButton collectMoneyButton = new JButton("Collect Money");
        JButton transactionSummaryButton = new JButton("View Transaction Summary");
        JButton replenishMoneyButton = new JButton("Replenish Money");
        JButton backToMainMenuButton = new JButton("Back to Main Menu");

        restockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRestockOption();
            }
        });

        collectMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCollectMoneyOption();
            }
        });

        transactionSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTransactionSummary();
            }
        });

        replenishMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleReplenishMoneyOption();
            }
        });

        backToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the MaintenanceFrame when the "Back to Main Menu" button is clicked
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(restockButton);
        buttonPanel.add(collectMoneyButton);
        buttonPanel.add(transactionSummaryButton);
        buttonPanel.add(replenishMoneyButton);
        buttonPanel.add(backToMainMenuButton);

        mainPanel.add(maintenanceLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    /**
     * Handles the restock feature of the Maintence Frame.
     */
    private void handleRestockOption() {
        // Update the available items with current quantities
        StringBuilder itemsText = new StringBuilder();
        itemsText.append("*===========================================================*\n");
        itemsText.append("│                     AVAILABLE ITEMS                       │\n");
        itemsText.append("*============================================================*\n");
        itemsText.append("| Slot  |      Item      |  Quantity  |  Price  |  Calories |\n");
        itemsText.append("=============================================================\n");

        for (int slotNumber = 0; slotNumber < 9; slotNumber++) {
            ItemSlot itemSlot = vendingMachine.getSlot(slotNumber);
            if (itemSlot != null) {
                Item item = itemSlot.getItem();
                itemsText.append(String.format("|  %-3d  |  %-13s |  %-9d |  P%-5.2f |  %-8.2f  |\n",
                        slotNumber, item.getName(), itemSlot.getQuantity(), item.getPrice(), item.getCalories()));
            }
        }

        itemsText.append("=============================================================\n");

        JTextArea textArea = new JTextArea(itemsText.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Available Items", JOptionPane.INFORMATION_MESSAGE);

        String slotNumberInput = JOptionPane.showInputDialog("Enter the slot number to restock:");
        String quantityInput = JOptionPane.showInputDialog("Enter the quantity to add:");

        try {
            int slotNumber = Integer.parseInt(slotNumberInput);
            int quantityToAdd = Integer.parseInt(quantityInput);

            if (slotNumber >= 0 && slotNumber < 9) {
                ItemSlot slot = vendingMachine.getSlot(slotNumber);
                if (slot != null) {
                    int currentQuantity = slot.getQuantity();
                    int maxCapacity = 10;

                    if (currentQuantity + quantityToAdd <= maxCapacity) {
                        Item item = slot.getItem();
                        vendingMachine.restock(item, quantityToAdd, slotNumber);
                        slot = vendingMachine.getSlot(slotNumber);

                        String successMessage = "Restock successful!\n"
                                + "Item: " + item.getName() + "\n"
                                + "Current quantity: " + slot.getQuantity() + "\n"
                                + "Added quantity: " + quantityToAdd + "\n"
                                + "New quantity: " + slot.getQuantity();
                        JOptionPane.showMessageDialog(this, successMessage, "Restock Successful",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // When slot is full
                        String errorMessage = "Slot " + slotNumber + " is full. Cannot add more items.";
                        JOptionPane.showMessageDialog(this, errorMessage, "Restock Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // when slot is empty
                    String errorMessage = "Slot " + slotNumber + " is empty. Please select a valid slot.";
                    JOptionPane.showMessageDialog(this, errorMessage, "Restock Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // when slot is invalid
                String errorMessage = "Invalid slot number. Please select a valid slot.";
                JOptionPane.showMessageDialog(this, errorMessage, "Restock Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            String errorMessage = "Invalid input. Please enter valid numbers for slot number and quantity.";
            JOptionPane.showMessageDialog(this, errorMessage, "Restock Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the collect money feature of the Maintenance Frame.
     */
    private void handleCollectMoneyOption() {
        double collectedMoney = vendingMachine.collectMoney();
        JOptionPane.showMessageDialog(this, "Collected Money: PHP " + collectedMoney, "Collect Money",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays transaction summary for the Maintenance Frame GUI
     */
    private void viewTransactionSummary() {
        StringBuilder transactionSummaryBuilder = new StringBuilder();
        transactionSummaryBuilder.append("*=====================================*\n");
        transactionSummaryBuilder.append("│          TRANSACTION SUMMARY        │\n");
        transactionSummaryBuilder.append("*=====================================*\n");
        transactionSummaryBuilder.append("| Item Name      | Quantity Sold       |\n");
        transactionSummaryBuilder.append("=======================================\n");
        for (Transaction transaction : vendingMachine.getTransactions()) {
            Item item = transaction.getItem();
            int quantity = transaction.getQuantity();
            transactionSummaryBuilder.append(String.format("| %-15s | %-18d |\n", item.getName(), quantity));
        }
        transactionSummaryBuilder.append("=======================================\n");
        double totalSales = vendingMachine.getTotalSales();
        transactionSummaryBuilder.append("Total Amount Collected: " + totalSales + "\n");

        JTextArea textArea = new JTextArea(transactionSummaryBuilder.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Transaction Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles replenish money option of the Maintenance Frame for the GUI
     */
    private void handleReplenishMoneyOption() {
        String[] denominations = { "PHP 1", "PHP 5", "PHP 10", "PHP 20", "PHP 50", "PHP 100", "PHP 200", "PHP 500" };
        double[] amounts = { 1, 5, 10, 20, 50, 100, 200, 500 };

        for (int i = 0; i < denominations.length; i++) {
            String input = JOptionPane.showInputDialog("Enter the quantity to replenish [" + denominations[i] + "]:");
            try {
                int quantity = Integer.parseInt(input);
                if (quantity > 0) {
                    vendingMachine.replenishMoney(amounts[i], quantity);
                }
            } catch (NumberFormatException e) {
                // Do nothing if input is not more than 0 and a non integer value
            }
        }

        JOptionPane.showMessageDialog(this, "Money replenished successfully!", "Replenish Money",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
