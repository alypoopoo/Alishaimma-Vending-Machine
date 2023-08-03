import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This class represents the Main Frame used in the GUI of the vending machine.
 * It connects all other sub-frames created.
 */
public class MainFrame {
    private JFrame mainFrame;
    private RegularVendingMachine vendingMachine;
    private SpecialVendingMachine specialVendingMachine;

    /**
     * Constructs the Main Frame of the vending machine's GUI.
     */
    public MainFrame() {
        this.mainFrame = new JFrame("Alishaimma Vending Machine");

        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.setSize(600, 800);

        // Initialize the vending machines
        this.vendingMachine = new RegularVendingMachine();
        this.specialVendingMachine = new SpecialVendingMachine(vendingMachine);

        initializeHeader();
        initializeMainMenu();

        this.mainFrame.setVisible(true);
    }

    /**
     * Initializes the greeting elements of the Pinoy Vending Machine
     * though the use of a JLabel.
     */
    private void initializeHeader() {
        JLabel pvmHeading = new JLabel();
        pvmHeading.setText("Welcome to Alishaimma Vending Machine!");
        pvmHeading.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font to Arial and size to 20
        pvmHeading.setHorizontalAlignment(JLabel.CENTER); // Centers the label

        this.mainFrame.add(pvmHeading, BorderLayout.NORTH);
    }

    /**
     * Initializes the Main Menu choices of the Pinoy Vending Machine using buttons
     * in a JPanel
     * for each option: buy button, customize button, maintenance button, and exit
     * button.
     */
    private void initializeMainMenu() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        JButton buyButton = new JButton("Buy an Item");
        JButton customizeButton = new JButton("Prepare a Meal");
        JButton maintenanceButton = new JButton("Maintenance");
        JButton exitButton = new JButton("Exit");

        // Centers all the button in the panel
        buyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        customizeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        maintenanceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBuyOption();
            }
        });

        customizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePrepareMealOption();
            }
        });

        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMaintenanceOption();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExitOption();
            }
        });

        // Adds the buttons to the panel
        buttonPanel.add(buyButton);
        buttonPanel.add(customizeButton);
        buttonPanel.add(maintenanceButton);
        buttonPanel.add(exitButton);

        // Adds the panel to the center of the main frame
        this.mainFrame.add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Method linked to Buy Button to handle Buy Frame and its operations.
     */
    private void handleBuyOption() {
        BuyFrame buyFrame = new BuyFrame(vendingMachine);
        buyFrame.setVisible(true);
    }

    /**
     * Method linked to Maintenance Button to handle Maintenance Frame and its
     * operations.
     */
    private void handleMaintenanceOption() {
        MaintenanceFrame maintenanceFrame = new MaintenanceFrame(vendingMachine);
        maintenanceFrame.setVisible(true);
    }

    /**
     * Method linked to Prepare a Meal Button to handle Customize Frame and its
     * operations.
     */
    private void handlePrepareMealOption() {
        PrepareMealFrame prepareMealFrame = new PrepareMealFrame(specialVendingMachine, vendingMachine);
        prepareMealFrame.setVisible(true);
    }

    /**
     * Method linked to Exit Button to exit application.
     */
    private void handleExitOption() {
        this.mainFrame.dispose();
        System.exit(0);
    }
}