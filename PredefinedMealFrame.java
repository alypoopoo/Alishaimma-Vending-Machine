import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represent the Predefined Meal Frame of the Prepare Meal Frame for
 * the GUI of the vending machine application.
 */
public class PredefinedMealFrame extends JFrame {

    private SpecialVendingMachine vendingMachine;
    private RegularVendingMachine regularVendingMachine;

    /**
     * Constructs the Predefined Meal Frame of the vending machine's GUI
     * 
     * @param vendingMachine the Special vending Machine instance
     */
    public PredefinedMealFrame(SpecialVendingMachine vendingMachine, RegularVendingMachine regularVendingMachine) {
        this.vendingMachine = vendingMachine;
        this.regularVendingMachine = regularVendingMachine;
        initialize();
    }

    /**
     * Initializes the scroll panel of list of predefined meals
     */
    private void initialize() {
        setTitle("Predefined Meals");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] predefinedMeals = {
                "Halo Halo",
                "Ube Milkshake",
                "Special Turon",
                "Banana Milk",
                "Ube Banana Split",
                "Jackfruit Delight",
                "Red Bean Milk Tea"
        };

        JList<String> mealsList = new JList<>(predefinedMeals);
        mealsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mainPanel.add(new JScrollPane(mealsList), BorderLayout.CENTER);

        JButton prepareButton = new JButton("Prepare");
        prepareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = mealsList.getSelectedIndex();
                if (selectedIndex >= 0 && selectedIndex < predefinedMeals.length) {
                    String selectedMeal = predefinedMeals[selectedIndex];
                    handlePredefinedMeal(selectedMeal);
                } else {
                    JOptionPane.showMessageDialog(PredefinedMealFrame.this,
                            "Please select a valid meal from the list.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(prepareButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    /**
     * Handles the purchase of a predefined meal
     * 
     * @param selectedMeal the specific predefined meal chosen
     */
    private void handlePredefinedMeal(String selectedMeal) {
        ItemSlot[] mealItems = vendingMachine.getPredefinedMealItems(selectedMeal);
        double totalPrice = calculateTotalPrice(mealItems);
        double totalCalories = calculateTotalCalories(mealItems);
    
        // Display the items used to make the predefined meal
        StringBuilder summaryText = new StringBuilder();
        summaryText.append("===== MEAL SUMMARY =====\n");
        summaryText.append("Selected items for your meal:\n");
        summaryText.append("Item Name      |  Quantity  |  Price  |  Calories\n");
    
        for (ItemSlot itemSlot : mealItems) {
            Item item = itemSlot.getItem();
            int quantity = itemSlot.getQuantity();
            double price = item.getPrice();
            double calories = item.getCalories();
            summaryText.append(String.format("%-15s |   %-8d |  P%-5.2f |  %-8.2f\n",
                    item.getName(), quantity, price, calories));
        }
    
        summaryText.append("Total Price: PHP ").append(totalPrice).append("\n");
        summaryText.append("Total Calories: ").append(totalCalories).append("\n");
    
        JOptionPane.showMessageDialog(this, summaryText.toString(), "Meal Summary", JOptionPane.INFORMATION_MESSAGE);
        this.toFront();
    
        // Payment process of checkout
        double amountPaid = askPaymentAmount();
        vendingMachine.handlePaymentCustomMeal(mealItems, amountPaid);
    
        // handles sellItem method so that variables are updated (quantity, total sales, etc)
        for (ItemSlot itemSlot : mealItems) {
            Item item = itemSlot.getItem();
            String itemName = item.getName();
            int quantity = itemSlot.getQuantity();
    
            int slotNumber = -1;
            for (int i = 0; i < 9; i++) { // find slot number based on item name
                ItemSlot slot = regularVendingMachine.getSlot(i);
                if (slot != null && slot.getItem().getName().equals(itemName)) {
                    slotNumber = i;
                    break;
                }
            }
    
            if (slotNumber != -1) {
                // handles sellItem method so that variables are updated (quantity, total sales, etc)
                regularVendingMachine.sellItem(slotNumber, quantity, amountPaid, totalPrice);
            } else {
                System.out.println("Item not found in the vending machine: " + itemName);
            }
        }
    
        StringBuilder preparationStepsText = new StringBuilder();
        preparationStepsText.append("===== PREPARATION STEPS =====\n");
    
        // Preparation steps based on items used
        for (ItemSlot itemSlot : mealItems) {
            Item item = itemSlot.getItem();
            String itemName = item.getName();
    
            switch (itemName.toLowerCase()) {
                case "red bean":
                    preparationStepsText.append("Preparing the tender Red Beans with care...\n");
                    break;
                case "ube ice cream":
                    preparationStepsText.append("Scooping out the velvety Ube Ice Cream...\n");
                    break;
                case "leche flan":
                    preparationStepsText.append("Whisking the Leche Flan to a silky perfection...\n");
                    break;
                case "banana":
                    preparationStepsText.append("Peeling and slicing the ripe Banana...\n");
                    break;
                case "nata de coco":
                    preparationStepsText.append("Cutting the chewy Nata de Coco into delightful cubes...\n");
                    break;
                case "kaong":
                    preparationStepsText.append("Rinsing and picking out the succulent Kaong...\n");
                    break;
                case "jackfruit":
                    preparationStepsText.append("Carefully removing the sweet Jackfruit pods...\n");
                    break;
                case "pinipig":
                    preparationStepsText.append("Toasting the crunchy Pinipig to a golden delight...\n");
                    break;
                case "milk":
                    preparationStepsText.append("Pouring the creamy Milk into a jug...\n");
                    break;
                default:
                    preparationStepsText.append("Preparing ").append(itemName).append(" with a pinch of magic...\n");
                    break;
            }
        }
    
        preparationStepsText.append("Putting it all together with love and care...\n");
        preparationStepsText.append("Meal Done! Enjoy your creation!\n");
    
        JOptionPane.showMessageDialog(this, preparationStepsText.toString(), "Preparation Steps",
                JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showMessageDialog(this, "Meal preparation is complete. Enjoy your creation!", "Done",
                JOptionPane.INFORMATION_MESSAGE);
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

    /**
     * Calculates total price of items in chosen predfined meal
     * 
     * @param items items in chosen predfined meal
     * @return total price of predefined meal purchased
     */
    private double calculateTotalPrice(ItemSlot[] items) {
        double totalPrice = 0;
        for (ItemSlot itemSlot : items) {
            Item item = itemSlot.getItem();
            double price = item.getPrice();
            int quantity = itemSlot.getQuantity();
            totalPrice += price * quantity;
        }
        return totalPrice;
    }

    /**
     * Calculates total calories of items in chosen predfined meal
     * 
     * @param items items in chosen predfined meal
     * @return total calories of predefined meal purchased
     */
    private double calculateTotalCalories(ItemSlot[] items) {
        double totalCalories = 0;
        for (ItemSlot itemSlot : items) {
            Item item = itemSlot.getItem();
            double calories = item.getCalories();
            int quantity = itemSlot.getQuantity();
            totalCalories += calories * quantity;
        }
        return totalCalories;
    }
}
