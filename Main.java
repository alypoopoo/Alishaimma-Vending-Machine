import java.util.Scanner;

/**
 * The Main class represents the driver of the vending machine program.
 */
public class Main {
    public static void main(String[] args) {
        RegularVendingMachine vendingMachine = new RegularVendingMachine();
        SpecialVendingMachine specialVendingMachine = new SpecialVendingMachine(vendingMachine);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMainMenu();

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    handleBuyOption(scanner, vendingMachine);
                    break;
                case 2:
                    handlePrepareMealOption(scanner, specialVendingMachine);
                    break;
                case 3:
                    handleMaintenanceOption(scanner, vendingMachine);
                    break;
                case 4:
                    handleExitOption(vendingMachine);
                    return;
                default:
                    System.out.println("ERROR: INPUT NOT A VALID CHOICE!");
            }
        }
    }

    /**
     * Displays the main menu of the vending machine.
     */
    private static void displayMainMenu() {
        System.out.println("========== VENDING MACHINE ==========");
        System.out.println("1. Buy an item");
        System.out.println("2. Prepare a meal");
        System.out.println("3. Maintenance");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Handles the Buy Option of the main menu.
     * 
     * @param scanner        the scanner used to get user input
     * @param vendingMachine the vending machine used
     */
    private static void handleBuyOption(Scanner scanner, RegularVendingMachine vendingMachine) {
        vendingMachine.displayItems();
        System.out.print("Enter the slot number: ");
        int slotNumber = scanner.nextInt();

        if (vendingMachine.isSlotOccupied(slotNumber)) {
            ItemSlot itemSlot = vendingMachine.getSlot(slotNumber);
            Item item = itemSlot.getItem();

            System.out.println("Item selected: " + item.getName());
            System.out.println("Price: PHP" + item.getPrice());
            System.out.println("Quantity: " + itemSlot.getQuantity());

            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();
            int amount = 0;
            double totalPrice = item.getPrice() * quantity;

            System.out.println("Total price: PHP" + totalPrice);
            System.out.print("How many [PHP 1] coins: ");
            int quantity1 = scanner.nextInt();
            amount += quantity1 * 1;

            System.out.print("How many [PHP 5] coins : ");
            int quantity2 = scanner.nextInt();
            amount += quantity2 * 5;

            System.out.print("How many [PHP 10] coins: ");
            int quantity3 = scanner.nextInt();
            amount += quantity3 * 10;

            System.out.print("How many [PHP 20] bills: ");
            int quantity4 = scanner.nextInt();
            amount += quantity4 * 20;

            System.out.print("How many [PHP 50] bills: ");
            int quantity5 = scanner.nextInt();
            amount += quantity5 * 50;

            System.out.print("How many [PHP 100] bills: ");
            int quantity6 = scanner.nextInt();
            amount += quantity6 * 100;

            System.out.print("How many [PHP 200] bills: ");
            int quantity7 = scanner.nextInt();
            amount += quantity7 * 200;

            System.out.print("How many [PHP 500] bills: ");
            int quantity8 = scanner.nextInt();
            amount += quantity8 * 500;

            vendingMachine.replenishMoney(1, quantity1);
            vendingMachine.replenishMoney(5, quantity2);
            vendingMachine.replenishMoney(10, quantity3);
            vendingMachine.replenishMoney(20, quantity4);
            vendingMachine.replenishMoney(50, quantity5);
            vendingMachine.replenishMoney(100, quantity6);
            vendingMachine.replenishMoney(200, quantity7);
            vendingMachine.replenishMoney(500, quantity8);

            vendingMachine.sellItem(slotNumber, quantity, amount, totalPrice);
        } else {
            System.out.println("Slot " + slotNumber + " is empty. Please select a valid slot.");
        }
    }

    /**
     * Handles the Maintenance Option of the main menu.
     * 
     * @param scanner        the scanner used to get user input
     * @param vendingMachine the vending machine used
     */
    private static void handleMaintenanceOption(Scanner scanner, RegularVendingMachine vendingMachine) {
        System.out.println("========== MAINTENANCE ==========");
        System.out.println("1. Restock an item");
        System.out.println("2. Collect money");
        System.out.println("3. View transaction summary");
        System.out.println("4. Replenish Money");
        System.out.println("5. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                vendingMachine.displayItems();
                handleRestockOption(scanner, vendingMachine);
                break;
            case 2:
                handleCollectMoneyOption(vendingMachine);
                break;
            case 3:
                handleTransactionSummaryOption(vendingMachine);
                break;
            case 4:
                handleMoneyOption(scanner, vendingMachine);
                break;
            case 5:
                break;
            default:
                System.out.println("ERROR: INPUT NOT A VALID CHOICE!");
        }
    }

    /**
     * Handles the Restock option of the Maintenance Feature.
     * 
     * @param scanner        the scanner used to get user input
     * @param vendingMachine the vending machine used
     */
    private static void handleRestockOption(Scanner scanner, RegularVendingMachine vendingMachine) {
        System.out.print("Enter the slot number to restock: ");
        int slotNumber = scanner.nextInt();

        if (vendingMachine.isSlotOccupied(slotNumber)) {
            ItemSlot itemSlot = vendingMachine.getSlot(slotNumber);
            Item item = itemSlot.getItem();

            System.out.println("Item selected: " + item.getName());
            System.out.println("Current quantity: " + itemSlot.getQuantity());
            System.out.print("Enter the quantity to add: ");
            int quantity = scanner.nextInt();

            vendingMachine.restock(item, quantity, slotNumber);
            itemSlot = vendingMachine.getSlot(slotNumber); // Get the updated ItemSlot after restocking
            System.out.println("Restock successful!");
            System.out.println("New quantity: " + itemSlot.getQuantity()); // print the updated quantity
        } else {
            System.out.println("Slot " + slotNumber + " is empty. Please select a valid slot.");
        }
    }

    /**
     * Handles the Collect Money Option of the Maintenance Feature.
     * 
     * @param vendingMachine the vending machine used
     */
    private static void handleCollectMoneyOption(RegularVendingMachine vendingMachine) {
        double collectedMoney = vendingMachine.collectMoney();
        System.out.println("Collected money: PHP" + collectedMoney);
    }

    /**
     * Handles the Transaction Summary of the Maintenance Feature.
     * 
     * @param vendingMachine the vending machine used
     */
    private static void handleTransactionSummaryOption(RegularVendingMachine vendingMachine) {
        vendingMachine.displayTransactionSummary();
        System.out.println("=========================================");
    }

    private static void handleMoneyOption(Scanner scanner, RegularVendingMachine vendingMachine) {
        System.out.println("========== REPLENISH MONEY ==========");
        System.out.print("Enter the amount to replenish [PHP 1]: ");
        int quantity1 = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the amount to replenish [PHP 5] : ");
        int quantity2 = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the amount to replenish [PHP 10]: ");
        int quantity3 = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the amount to replenish [PHP 20]: ");
        int quantity4 = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the amount to replenish [PHP 50]: ");
        int quantity5 = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the amount to replenish [PHP 100]: ");
        int quantity6 = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the amount to replenish [PHP 200]: ");
        int quantity7 = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter the amount to replenish [PHP 500]: ");
        int quantity8 = scanner.nextInt();
        scanner.nextLine();

        vendingMachine.replenishMoney(1, quantity1);
        vendingMachine.replenishMoney(5, quantity2);
        vendingMachine.replenishMoney(10, quantity3);
        vendingMachine.replenishMoney(20, quantity4);
        vendingMachine.replenishMoney(50, quantity5);
        vendingMachine.replenishMoney(100, quantity6);
        vendingMachine.replenishMoney(200, quantity7);
        vendingMachine.replenishMoney(500, quantity8);

        System.out.println("Money replenished successfully!");
        System.out.println("Current balance: PHP" + vendingMachine.getBalance());
    }

    /**
     * Handles the exit option of the main menu.
     * 
     * @param vendingMachine the vending machine used
     */
    private static void handleExitOption(RegularVendingMachine vendingMachine) {
        System.out.println("Exiting program...");
        System.out.println("Thank you for using the vending machine!");

    }

    /**
     * Handles the Prepare Meal Option of the main menu for the
     * SpecialVendingMachine.
     *
     * @param scanner        the scanner used to get user input
     * @param vendingMachine the special vending machine used
     */
    private static void handlePrepareMealOption(Scanner scanner, SpecialVendingMachine specialVendingMachine) {
        System.out.println("========== PREPARE A MEAL ==========");
        System.out.println("1. Predefined Meals");
        System.out.println("2. Custom Meal");
        System.out.println("3. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                handlePredefinedMeals(scanner, specialVendingMachine);
                break;
            case 2:
                specialVendingMachine.handleCustomMeal();
                break;
            case 3:
                break;
            default:
                System.out.println("ERROR: INPUT NOT A VALID CHOICE!");
        }
    }

    /**
     * Handles the purchase of the predefined meals of the vending machine's prepare
     * meal portion.
     * 
     * @param scanner        the Scanner instance used to accept input of choice
     *                       from the list of predefined meals
     * @param vendingMachine the Special Vending Machine instance used
     */
    private static void handlePredefinedMeals(Scanner scanner, SpecialVendingMachine vendingMachine) {
        System.out.println("========== PREDEFINED MEALS ==========");
        System.out.println("1. Halo Halo");
        System.out.println("2. Ube Milkshake");
        System.out.println("3. Special Turon");
        System.out.println("4. Banana Milk");
        System.out.println("5. Ube Banana Split");
        System.out.println("6. Jackfruit Delight");
        System.out.println("7. Red Bean Milk Tea");
        System.out.println("8. Back to main menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                vendingMachine.prepareMeal("Halo Halo");
                break;
            case 2:
                vendingMachine.prepareMeal("Ube Milkshake");
                break;
            case 3:
                vendingMachine.prepareMeal("Special Turon");
                break;
            case 4:
                vendingMachine.prepareMeal("Banana Milk");
                break;
            case 5:
                vendingMachine.prepareMeal("Ube Banana Split");
                break;
            case 6:
                vendingMachine.prepareMeal("Jackfruit Delight");
                break;
            case 7:
                vendingMachine.prepareMeal("Red Bean Milk Tea");
                break;
            case 8:
                break;
            default:
                System.out.println("ERROR: INPUT NOT A VALID CHOICE!");
        }
    }

}
