import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represent the Prepare Meal Frame in the Main Frame for
 * the GUI of the vending machine application.
 */
public class PrepareMealFrame extends JFrame {
    private SpecialVendingMachine specialVendingMachine;
    private RegularVendingMachine regularVendingMachine;

    /**
     * Constructs the Prepare Meal Frame for the vending machine's GUI.
     * 
     * @param specialVendingMachine the Special Vending Machine instance used
     * @param regularVendingMachine the Regular Vending Machine instance used
     */
    public PrepareMealFrame(SpecialVendingMachine specialVendingMachine, RegularVendingMachine regularVendingMachine) {
        this.regularVendingMachine = regularVendingMachine;
        this.specialVendingMachine = specialVendingMachine;
        initialize();
    }

    /**
     * Initializes the JPanel with the buttons for the options (Predefined Meal,
     * Custom Meal, and Exit).
     */
    private void initialize() {
        setTitle("Prepare a Meal");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 300);

        JPanel panel = new JPanel();
        add(panel);

        panel.setLayout(new GridLayout(0, 1));

        JLabel titleLabel = new JLabel("===== PREPARE A MEAL =====");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel);

        // Buttons for the options:
        JButton predefinedMealsButton = new JButton("1. Predefined Meals");
        JButton customMealButton = new JButton("2. Custom Meal");
        JButton backButton = new JButton("3. Back to Main Menu");

        panel.add(predefinedMealsButton); // Add predefinedMealsButton to the panel
        panel.add(customMealButton); // Add customMealButton to the panel
        panel.add(backButton); // Add backButton to the panel

        predefinedMealsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                predefinedMeals();
            }
        });

        customMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customMeal();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    /**
     * Creates method to access Predefined Meal Frame
     */
    private void predefinedMeals() {
        PredefinedMealFrame predefinedMealFrame = new PredefinedMealFrame(specialVendingMachine, regularVendingMachine);
        predefinedMealFrame.setVisible(true);
    }

    /**
     * Creates method to access Custom Meal Frame
     */
    private void customMeal() {
        CustomMealFrame customMealFrame = new CustomMealFrame(regularVendingMachine);
        customMealFrame.setVisible(true);

    }
}
