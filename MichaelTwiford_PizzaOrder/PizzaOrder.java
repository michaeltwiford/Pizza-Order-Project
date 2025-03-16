/**Pizza Order Project
 * @author Michael Twiford
 * @version Spring 2025
 * CSCI 1130
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class PizzaOrder extends JFrame implements ActionListener, ItemListener {
    //Declaring all of my varibles that I will use throughout the program
    JPanel titlePanel, submitPanel, receiptPanel, sizePanel, toppPanel, northPanel,
            pizzaPanel;
    JLabel titleLabel;
    JButton submit, clear;
    JTextArea receipt;
    JRadioButton small, medium, large;
    JCheckBox pepp, sausage, chicken, bPeppers, bOlives;
    JScrollPane scroll;
    String receiptText = "";
    ButtonGroup sizeGroup;
    Image pizzaImage;
    DisplayPanel display;
    boolean smallChosen, mediumChosen, largeChosen;
    boolean peppChosen, sausChosen, chikChosen, bPeppChosen, bOlivesChosen;

    //Declaring variables that are constants
    private final double PEPP_PRICE = 3.99;
    private final double SAUS_PRICE = 3.99;
    private final double CHIK_PRICE = 3.99;
    private final double BPEPP_PRICE = 1.99;
    private final double BOLIVES_PRICE = 1.99;
    private final double SMALL_PRICE = 8.00;
    private final double MEDIUM_PRICE = 10.00;
    private final double LARGE_PRICE = 12.00;
    public static void main(String[] args) {
        PizzaOrder frame = new PizzaOrder();
        frame.setSize(new Dimension(600, 600));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.createGUI();
        frame.setVisible(true);
    }

    private void createGUI() {
        //adding JPanels to my GUI
        setUpNorthPanel();
        setUpSubmit();
        setUpReceipt();
        setUpToppings();
        setUpPizzaImage();

        //adding methods that respond to user actions
        registerSizes();
        registerTopp();
        registerSubmit();
    }

    public void actionPerformed(ActionEvent e) {
        //Method that handles the user clicking submit or clear JButtons
        if (e.getSource() == submit) {
            buildReceiptString();
            receipt.setText(receiptText);
            receiptText = "";
        }
        else if(e.getSource() == clear){
            receipt.setText("");
            pepp.setSelected(false);
            sausage.setSelected(false);
            chicken.setSelected(false);
            bPeppers.setSelected(false);
            bOlives.setSelected(false);

            //JRadio buttons will be deselected when user clicks "clear" button
            sizeGroup.clearSelection();
            smallChosen = false;
            mediumChosen = false;
            largeChosen = false;
        }
    }

    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();

        /*This handles changes to the checkboxes and radiobuttons for pizza sizes
        and toppings being deselected
        */
        if (e.getStateChange() == ItemEvent.DESELECTED) {
            if (source == pepp) {
                peppChosen = false;
            }
            if (source == sausage) {
                sausChosen = false;
            }
            if (source == chicken) {
                chikChosen = false;
            }
            if (source == bPeppers) {
                bPeppChosen = false;
            }
            if (source == bOlives) {
                bOlivesChosen = false;
            }
            if (source == small) {
                smallChosen = false;
            }
            if (source == medium) {
                mediumChosen = false;
            }
            if (source == large) {
                largeChosen = false;
            }
            return;
        }

        //using if/else if statements for pizza sizes being selected
        if (source == small) {
            smallChosen = true;
        } else if (source == medium) {
            mediumChosen = true;
        } else if (source == large) {
            largeChosen = true;
        }

        /*using if statments for toppings chosen so that user can
        check unlimited number of checkboxes
        */
        if (source == pepp && pepp.isSelected()) {
            peppChosen = true;
        }
        if (source == sausage && sausage.isSelected()) {
            sausChosen = true;
        }
        if (source == chicken && chicken.isSelected()) {
            chikChosen = true;
        }
        if (source == bPeppers && bPeppers.isSelected()) {
            bPeppChosen = true;
        }
        if (source == bOlives && bOlives.isSelected()) {
            bOlivesChosen = true;
        }
    }

    public void buildReceiptString() {
        /*this method builds the receipt textbox by checking which options are
        selected and calculating a grand total for the pizza price
        */
        double runningTotal = 0.0;

        receiptText = "Itemized Receipt:\n\n";

        /*Using a string.format method so that my double value "runningTotal" 
        will always print with two decimal places. 
        */
        if (smallChosen) {
            receiptText += "Small: $" + String.format("%.2f", SMALL_PRICE) + "\n";
            runningTotal += SMALL_PRICE;
        }
        if (mediumChosen) {
            receiptText += "Medium: $" + String.format("%.2f", MEDIUM_PRICE) + "\n";
            runningTotal += MEDIUM_PRICE;
        }
        if (largeChosen) {
            receiptText += "Large: $" + String.format("%.2f", LARGE_PRICE) + "\n";
            runningTotal += LARGE_PRICE;
        }
        if (peppChosen) {
            receiptText += "Pepperoni: $" + String.format("%.2f", PEPP_PRICE) + "\n";
            runningTotal += PEPP_PRICE;
        }
        if (sausChosen) {
            receiptText += "Sausage: $" + String.format("%.2f", SAUS_PRICE) + "\n";
            runningTotal += SAUS_PRICE;
        }
        if (chikChosen) {
            receiptText += "Chicken: $" + String.format("%.2f", CHIK_PRICE) + "\n";
            runningTotal += CHIK_PRICE;
        }
        if (bPeppChosen) {
            receiptText += "Bell Peppers: $" + String.format("%.2f", BPEPP_PRICE) + "\n";
            runningTotal += BPEPP_PRICE;
        }
        if (bOlivesChosen) {
            receiptText += "Black Olives: $" + String.format("%.2f", BOLIVES_PRICE) + "\n";
            runningTotal += BOLIVES_PRICE;
        }

        receiptText += "\nGrand Total: $" + String.format("%.2f", runningTotal);
    }

    public void setUpTitle() {
        //setting up my Pizza Order title JPanel
        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());

        titleLabel = new JLabel("Pizza Order");
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(53, 252, 3));
    }

    public void setUpSizes() {
        //setting up radio buttons for selecting pizza size
        sizePanel = new JPanel(new GridLayout(1, 3));
        small = new JRadioButton("Small");
        medium = new JRadioButton("Medium");
        large = new JRadioButton("Large");

        /*this groups the radio buttons together so that only one
        size can be selected
        */
        sizeGroup = new ButtonGroup();
        sizeGroup.add(small);
        sizeGroup.add(medium);
        sizeGroup.add(large);

        sizePanel.add(small);
        sizePanel.add(medium);
        sizePanel.add(large);
    }

    //registering pizza sizes to the Item Listener
    public void registerSizes() {
        small.addItemListener(this);
        medium.addItemListener(this);
        large.addItemListener(this);
    }

    /*This is grouping the titlepanel and sizepanel together so that
    I can have them both at the top of the window of my program
    */
    public void setUpNorthPanel() {
        northPanel = new JPanel(new GridLayout(2,1));

        setUpTitle();
        northPanel.add(titlePanel);

        setUpSizes();
        northPanel.add(sizePanel);

        getContentPane().add(northPanel, BorderLayout.NORTH);
    }

    //adding a display panel to display my pizza image
    class DisplayPanel extends JPanel {
        DisplayPanel() {

            String path = "images/pizza.jpg";
            File file = new File(path);
            try {
                pizzaImage = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*using paintComponent to draw the pizza image as a
        square image
        */ 
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (pizzaImage != null) {
                int imageSize = Math.min(getWidth(), getHeight());
                int x = (getWidth() - imageSize) / 2;
                int y = (getHeight() - imageSize) / 2;

                g.drawImage(pizzaImage, x, y, imageSize, imageSize, this);
            }

        }
    }

    //this is adding the pizza image to the center of the window
    public void setUpPizzaImage() {
        display = new DisplayPanel();
        getContentPane().add(display, BorderLayout.CENTER);
    }

    //adding JCheckboxes for my toppings
    public void setUpToppings() {
        toppPanel = new JPanel(new GridLayout(5, 1));
        pepp = new JCheckBox("Pepperoni");
        sausage = new JCheckBox("Sausage");
        chicken = new JCheckBox("Chicken");
        bPeppers = new JCheckBox("Bell Peppers");
        bOlives = new JCheckBox("Black Olives");

        toppPanel.add(pepp);
        toppPanel.add(sausage);
        toppPanel.add(chicken);
        toppPanel.add(bPeppers);
        toppPanel.add(bOlives);

        getContentPane().add(toppPanel, BorderLayout.WEST);
    }

    //registering the JCheckboxes to the Item Listener
    public void registerTopp() {
        pepp.addItemListener(this);
        sausage.addItemListener(this);
        chicken.addItemListener(this);
        bPeppers.addItemListener(this);
        bOlives.addItemListener(this);
    }

    //setting up my JTextArea that will display the itemized receipt
    public void setUpReceipt() {
        receiptPanel = new JPanel();
        receipt = new JTextArea(28, 25);
        receipt.setLineWrap(true);
        receipt.setWrapStyleWord(true);

        scroll = new JScrollPane(receipt);

        receiptPanel.setLayout(new FlowLayout());
        receiptPanel.add(scroll);

        getContentPane().add(receiptPanel, BorderLayout.EAST);
    }

    //setting up the submit and clear JButtons
    public void setUpSubmit() {
        submitPanel = new JPanel();
        submitPanel.setLayout(new FlowLayout());
        submitPanel.setBackground(new Color(247, 255, 8));

        submit = new JButton("Submit");
        clear = new JButton("Clear");

        submitPanel.add(submit);
        submitPanel.add(clear);

        getContentPane().add(submitPanel, BorderLayout.SOUTH);
    }

    //adding the submit and clear JButtons to the Action Listener
    public void registerSubmit() {
        submit.addActionListener(this);
        clear.addActionListener(this);
    }
}