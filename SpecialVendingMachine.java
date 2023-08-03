import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class represents a special vending machine
 */
public class SpecialVendingMachine {
    private RegularVendingMachine regularVendingMachine;

    /**
     * This is a constructor for a special vending machine provided the regular
     * vending machine.
     * 
     * @param regularVendingMachine the Regular Vending Machine used
     */
    public SpecialVendingMachine(RegularVendingMachine regularVendingMachine) {
        this.regularVendingMachine = regularVendingMachine;
    }

    /**
     * Handles custom meal of the prepare meals option
     * 
     * @param mealItems array of items selected for customization
     */
    public void prepareCustomMeal(ItemSlot[] mealItems) {
        if (mealItems.length == 0) {
            System.out.println("No ingredients selected. Purchase unsuccessful.");
            return;
        }

        System.out.println("\n===== CUSTOM MEAL =====");
        System.out.println("Selected items for your meal:");
        System.out.println("Item Name      |  Quantity  |  Price  |  Calories");

        Map<String, Integer> itemQuantityMap = new HashMap<>(); // To store the quantity of each item
        double totalPrice = 0;
        double totalCalories = 0;

        for (ItemSlot itemSlot : mealItems) {
            Item item = itemSlot.getItem();
            double price = item.getPrice();
            double calories = item.getCalories();
            totalPrice += price; // total price of customized meal
            totalCalories += calories; // total calories of customized meal

            // Update the quantity for each item in the itemQuantityMap
            String itemName = item.getName();
            itemQuantityMap.put(itemName, itemQuantityMap.getOrDefault(itemName, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : itemQuantityMap.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            double price = mealItems[0].getItem().getPrice();
            double calories = mealItems[0].getItem().getCalories();

            System.out.printf("%-15s |   %-8d |  P%-5.2f |  %-8.2f\n", itemName, quantity, price, calories);
        }

        System.out.println("Total Price: P" + totalPrice);
        System.out.println("Total Calories: " + totalCalories);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("[P]repare now!");
            System.out.println("[B]ack");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();

            if (choice.equals("P")) {
                // Implement the payment function here if needed
                handlePaymentCustomMeal(mealItems, askPaymentAmount());
                break;
            } else if (choice.equals("B")) {
                System.out.println("Going back to the main menu...");
                return;
            } else {
                System.out.println("Invalid choice. Please enter 'P' to prepare the meal or 'B' to go back.");
            }

        }
    }

    /**
     * Getter for the set of predefined meals
     * 
     * @param mealName name of the predefined meal
     * @return predefined meal item
     */
    public ItemSlot[] getPredefinedMealItems(String mealName) {
        List<ItemSlot> mealItems = new ArrayList<>();

        // Logic to get items based on the predefined meal name
        switch (mealName.toLowerCase()) {
            case "halo halo":
                mealItems.add(new ItemSlot(new Item("Red Bean", 5.0, 50.0), 1));
                mealItems.add(new ItemSlot(new Item("Ube Ice Cream", 10.0, 100.0), 1));
                mealItems.add(new ItemSlot(new Item("Leche Flan", 15.0, 150.0), 1));
                mealItems.add(new ItemSlot(new Item("Banana", 5.0, 80.0), 1));
                mealItems.add(new ItemSlot(new Item("Nata de Coco", 15, 20), 1));
                mealItems.add(new ItemSlot(new Item("Kaong", 20, 25), 1));
                mealItems.add(new ItemSlot(new Item("Jackfruit", 10, 30), 1));
                mealItems.add(new ItemSlot(new Item("Jackfruit", 10, 30), 1));
                mealItems.add(new ItemSlot(new Item("Pinipig", 5, 40), 1));
                mealItems.add(new ItemSlot(new Item("Milk", 30, 40), 1));

                break;
            case "ube milkshake":
                mealItems.add(new ItemSlot(new Item("Ube Ice Cream", 10.0, 100.0), 2));
                mealItems.add(new ItemSlot(new Item("Milk", 5.0, 50.0), 4));
                break;
            case "special turon":
                mealItems.add(new ItemSlot(new Item("Banana", 5.0, 80.0), 2));
                mealItems.add(new ItemSlot(new Item("Jackfruit", 10.0, 120.0), 1));
                mealItems.add(new ItemSlot(new Item("Red Bean", 5.0, 50.0), 1));
                break;
            case "banana milk":
                mealItems.add(new ItemSlot(new Item("Banana", 5.0, 80.0), 2));
                mealItems.add(new ItemSlot(new Item("Milk", 5.0, 50.0), 4));
                break;
            case "ube banana split":
                mealItems.add(new ItemSlot(new Item("Ube Ice Cream", 10.0, 100.0), 2));
                mealItems.add(new ItemSlot(new Item("Banana", 5.0, 80.0), 2));
                mealItems.add(new ItemSlot(new Item("Leche Flan", 15.0, 150.0), 1));
                break;
            case "jackfruit delight":
                mealItems.add(new ItemSlot(new Item("Jackfruit", 10.0, 120.0), 2));
                mealItems.add(new ItemSlot(new Item("Milk", 5.0, 50.0), 1));
                mealItems.add(new ItemSlot(new Item("Leche Flan", 15.0, 150.0), 1));
                mealItems.add(new ItemSlot(new Item("Nata de Coco", 15, 20), 1));
                mealItems.add(new ItemSlot(new Item("Kaong", 20, 25), 1));
                break;
            case "red bean milk tea":
                mealItems.add(new ItemSlot(new Item("Red Bean", 5.0, 50.0), 1));
                mealItems.add(new ItemSlot(new Item("Milk", 5.0, 50.0), 3));
                break;
            default:
                System.out.println("ERROR: Invalid meal name!");
                break;
        }

        // Convert the list to an array
        return mealItems.toArray(new ItemSlot[0]);
    }

    /**
     * Handles prepare meal of the predefined meals
     * 
     * @param mealName name of the predefined meal
     */
    public void prepareMeal(String mealName) {
        ItemSlot[] mealItems;

        switch (mealName.toLowerCase()) {
            case "halo halo":
                mealItems = new ItemSlot[] {
                        regularVendingMachine.getSlot(0),
                        regularVendingMachine.getSlot(1),
                        regularVendingMachine.getSlot(2),
                        regularVendingMachine.getSlot(3),
                        regularVendingMachine.getSlot(4),
                        regularVendingMachine.getSlot(5),
                        regularVendingMachine.getSlot(6),
                        regularVendingMachine.getSlot(7),
                        regularVendingMachine.getSlot(8)
                };
                break;
            case "ube milkshake":
                mealItems = new ItemSlot[] {
                        regularVendingMachine.getSlot(1),
                        regularVendingMachine.getSlot(1),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(8)
                };
                break;
            case "special turon":
                mealItems = new ItemSlot[] {
                        regularVendingMachine.getSlot(0),
                        regularVendingMachine.getSlot(3),
                        regularVendingMachine.getSlot(3),
                        regularVendingMachine.getSlot(6),
                };
                break;
            case "banana milk":
                mealItems = new ItemSlot[] {
                        regularVendingMachine.getSlot(3),
                        regularVendingMachine.getSlot(3),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(8)
                };
                break;
            case "ube banana split":
                mealItems = new ItemSlot[] {
                        regularVendingMachine.getSlot(1),
                        regularVendingMachine.getSlot(1),
                        regularVendingMachine.getSlot(3),
                        regularVendingMachine.getSlot(3),
                        regularVendingMachine.getSlot(2)
                };
                break;
            case "jackfruit delight":
                mealItems = new ItemSlot[] {
                        regularVendingMachine.getSlot(6),
                        regularVendingMachine.getSlot(6),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(2),
                        regularVendingMachine.getSlot(4),
                        regularVendingMachine.getSlot(5)
                };
                break;
            case "red bean milk tea":
                mealItems = new ItemSlot[] {
                        regularVendingMachine.getSlot(0),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(8),
                        regularVendingMachine.getSlot(8)
                };
                break;
            default:
                System.out.println("ERROR: Invalid meal name!");
                return;
        }

        prepareCustomMeal(mealItems);
    }

    /**
     * Display items of the regular vending machine
     */
    public void displayItems() {
        regularVendingMachine.displayItems();
    }

    /**
     * Displays transaction summary of the regular vending machine
     */
    public void displayTransactionSummary() {
        regularVendingMachine.displayTransactionSummary();
    }

    /**
     * Handles payment of custom meal
     * 
     * @param mealItems  array of items found in your meal
     * @param amountPaid amount paid by the user
     */
    public void handlePaymentCustomMeal(ItemSlot[] mealItems, double amountPaid) {
        double totalPrice = calculateTotalPrice(mealItems);

        if (amountPaid >= totalPrice) {
            if (isChangePossibleWrapper(amountPaid, totalPrice)) {
                for (ItemSlot itemSlot : mealItems) {
                    Item item = itemSlot.getItem();
                    double price = item.getPrice();

                    int slotNumber = findSlotNumber(itemSlot);
                    regularVendingMachine.sellItem(slotNumber, 1, amountPaid, totalPrice);
                }
                System.out.println("Purchase successful!");

                double change = amountPaid - totalPrice;
                regularVendingMachine.calculateChange(change);
                // Preparation steps of the custom meal
                displayPreparationSteps(Arrays.asList(mealItems));
            } else {
                System.out.println(
                        "Change cannot be provided in the required denominations. Please contact maintenance.");
            }
        } else {
            System.out.println("Insufficient amount. Please enter a valid amount.");
        }
    }

    /**
     * Calculates the total price of the meal
     * 
     * @param mealItems array of items found in your meal
     * @return total price of meal
     */
    public double calculateTotalPrice(ItemSlot[] mealItems) {
        double totalPrice = 0;
        for (ItemSlot itemSlot : mealItems) {
            Item item = itemSlot.getItem();
            double price = item.getPrice();
            totalPrice += price;
        }
        return totalPrice;
    }

    /**
     * Handles purchase of an meal (custom or predefined) and the payment
     * 
     * @param mealItems  array of items found in your meal
     * @param amountPaid amount paid by user
     */
    public void sellItems(ItemSlot[] mealItems, double amountPaid) {
        double totalPrice = calculateTotalPrice(mealItems);

        if (amountPaid >= totalPrice) {
            if (isChangePossibleWrapper(amountPaid, totalPrice)) {
                for (ItemSlot itemSlot : mealItems) {
                    int slotNumber = findSlotNumber(itemSlot);
                    Item item = itemSlot.getItem();
                    int quantity = itemSlot.getQuantity();

                    regularVendingMachine.sellItem(slotNumber, quantity, amountPaid, totalPrice);
                    amountPaid -= totalPrice;
                }
                System.out.println("Purchase successful! Change: ");
            } else {
                System.out.println(
                        "Change cannot be provided in the required denominations. Please contact maintenance.");
            }
        } else {
            System.out.println("Insufficient amount. Please enter a valid amount.");
        }
    }

    /**
     * Calculates total calories of the meal
     * 
     * @param mealItems array of items found in your meal
     * @return total calories of the meal
     */
    public double calculateTotalCalories(ItemSlot[] mealItems) {
        double totalCalories = 0;
        for (ItemSlot itemSlot : mealItems) {
            Item item = itemSlot.getItem();
            double calories = item.getCalories();
            totalCalories += calories;
        }
        return totalCalories;
    }

    /**
     * Overriding is change possible from the regular vending machine
     * 
     * @param amount     amount paid by user
     * @param totalPrice total price of purchase
     * @return true if change is possible, false if not
     */
    public boolean isChangePossibleWrapper(double amount, double totalPrice) {
        return regularVendingMachine.isChangePossible(amount, totalPrice);
    }

    /**
     * Finds the slot number of an item in the special vending machine
     * 
     * @param itemSlot slot of an item
     * @return slot number the item
     */
    public int findSlotNumber(ItemSlot itemSlot) {
        ItemSlot[] slots = regularVendingMachine.getSlots();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == itemSlot) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Handles payment process of the purchase
     * 
     * @return amount paid by user
     */
    private double askPaymentAmount() {
        double amount = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many [PHP 1] coins: ");
        int quantity1 = scanner.nextInt();
        scanner.nextLine();
        amount += quantity1 * 1;

        System.out.print("How many [PHP 5] coins: ");
        int quantity2 = scanner.nextInt();
        scanner.nextLine();
        amount += quantity2 * 5;

        System.out.print("How many [PHP 10] coins: ");
        int quantity3 = scanner.nextInt();
        scanner.nextLine();
        amount += quantity3 * 10;

        System.out.print("How many [PHP 20] bills: ");
        int quantity4 = scanner.nextInt();
        scanner.nextLine();
        amount += quantity4 * 20;

        System.out.print("How many [PHP 50] bills: ");
        int quantity5 = scanner.nextInt();
        scanner.nextLine();
        amount += quantity5 * 50;

        System.out.print("How many [PHP 100] bills: ");
        int quantity6 = scanner.nextInt();
        scanner.nextLine();
        amount += quantity6 * 100;

        System.out.print("How many [PHP 200] bills: ");
        int quantity7 = scanner.nextInt();
        scanner.nextLine();
        amount += quantity7 * 200;

        System.out.print("How many [PHP 500] bills: ");
        int quantity8 = scanner.nextInt();
        scanner.nextLine();
        amount += quantity8 * 500;

        regularVendingMachine.replenishMoney(1, quantity1);
        regularVendingMachine.replenishMoney(5, quantity2);
        regularVendingMachine.replenishMoney(10, quantity3);
        regularVendingMachine.replenishMoney(20, quantity4);
        regularVendingMachine.replenishMoney(50, quantity5);
        regularVendingMachine.replenishMoney(100, quantity6);
        regularVendingMachine.replenishMoney(200, quantity7);
        regularVendingMachine.replenishMoney(500, quantity8);

        return amount;
    }

    /**
     * Handles custom meal (+ items that can be sold individually and alone)
     */
    public void handleCustomMeal() {
        Scanner scanner = new Scanner(System.in);
        List<ItemSlot> selectedItems = new ArrayList<>();

        System.out.println("===== CUSTOM MEAL =====");
        System.out.println("Available items:");
        regularVendingMachine.displayItems();

        while (true) {
            System.out.print("Enter the number of the item you want to add (or 'E' to finish): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("E")) {
                break; // Exit the loop if the user chooses to finish
            }

            try {
                int itemNumber = Integer.parseInt(input);
                ItemSlot itemSlot = regularVendingMachine.getSlot(itemNumber);

                if (itemSlot != null) {
                    selectedItems.add(itemSlot);
                    System.out.println("Added: " + itemSlot.getItem().getName());
                } else {
                    System.out.println("Invalid item number. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number or 'E' to finish.");
            }
        }

        if (selectedItems.isEmpty()) {
            System.out.println("No items selected. Purchase unsuccessful.");
            return;
        }

        String firstItemName = selectedItems.get(0).getItem().getName().toLowerCase();
        boolean cannotBeSoldIndividually = true;
        for (ItemSlot itemSlot : selectedItems) {
            String itemName = itemSlot.getItem().getName().toLowerCase();
            if (!itemName.equals(firstItemName) || (!itemName.equals("red bean")
                    && !itemName.equals("nata de coco") && !itemName.equals("kaong")
                    && !itemName.equals("jackfruit") && !itemName.equals("pinipig"))) {
                cannotBeSoldIndividually = false;
                break;
            }
        }

        if (cannotBeSoldIndividually) {
            System.out.println("The selected item(s): " + firstItemName + " cannot be bought individually.");
            return;
        }

        ItemSlot[] mealItems = selectedItems.toArray(new ItemSlot[0]);
        double totalPrice = calculateTotalPrice(mealItems);
        double totalCalories = calculateTotalCalories(mealItems);

        System.out.println("Selected items for your meal:");
        System.out.println("Item Name      |  Quantity  |  Price  |  Calories");

        for (ItemSlot itemSlot : selectedItems) {
            Item item = itemSlot.getItem();
            double price = item.getPrice();
            double calories = item.getCalories();
            System.out.printf("%-15s |   %-8d |  P%-5.2f |  %-8.2f\n", item.getName(), 1, price, calories);
        }

        System.out.println("Total Price: P" + totalPrice);
        System.out.println("Total Calories: " + totalCalories);

        double amountPaid = askPaymentAmount();
        handlePaymentCustomMeal(mealItems, amountPaid);

    }

    /**
     * Displays preparation steps depending on items used for the meal
     * 
     * @param selectedItems items used to create the meal
     */
    public void displayPreparationSteps(List<ItemSlot> selectedItems) {
        System.out.println("Shaving the refreshing Ice to perfection...\n");

        // Create a map to aggregate quantities of the same items
        Map<String, Integer> itemQuantities = new HashMap<>();

        for (ItemSlot itemSlot : selectedItems) {
            Item item = itemSlot.getItem();
            String itemName = item.getName();
            itemQuantities.put(itemName, itemQuantities.getOrDefault(itemName, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();

            // Perform specific preparation steps based on the item
            switch (itemName.toLowerCase()) {
                case "red bean":
                    System.out.println("Preparing the tender Red Beans with care...\n");
                    break;
                case "ube ice cream":
                    System.out.println("Scooping out the velvety Ube Ice Cream...\n");
                    break;
                case "leche flan":
                    System.out.println("Whisking the Leche Flan to a silky perfection...\n");
                    break;
                case "banana":
                    System.out.println("Peeling and slicing the ripe Banana...\n");
                    break;
                case "nata de coco":
                    System.out.println("Cutting the chewy Nata de Coco into delightful cubes...\n");
                    break;
                case "kaong":
                    System.out.println("Rinsing and picking out the succulent Kaong...\n");
                    break;
                case "jackfruit":
                    System.out.println("Carefully removing the sweet Jackfruit pods...\n");
                    break;
                case "pinipig":
                    System.out.println("Toasting the crunchy Pinipig to a golden delight...\n");
                    break;
                case "milk":
                    System.out.println("Pouring the creamy Milk into a jug...\n");
                    break;
                default:
                    System.out.println("Preparing " + itemName + " with a pinch of magic...\n");
                    break;
            }

        }

        System.out.println("Putting it all together with love and care...\n");
        System.out.println("Meal Done! Enjoy your customized creation!\n");
    }

}
