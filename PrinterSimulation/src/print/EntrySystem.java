/**
 * As a Student at Union College, I am part of a community that values intellectual effort, curosity
 * and discovery. I understand that in order to truly claim my educational and academic achievements, I
 * am obligated to act with academic integrity. Therefore, I affirm that I will carry out my academic
 * endeavors with full academic honesty, and I rely on my fellow students to do the same"
 *
 * Signed,
 *
 * Blake Luther, Ian Sulley, Parsa Keyvani
 *
 * 3/14/2021
 */

package print;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EntrySystem {

    private static ArrayList<Printer> availablePrinters = new ArrayList<>();
    private static String[] printerNames = new String[10];
    private static HashMap<String, Printer> printerHashMap = new HashMap<>();
    private static int numPrinters = 0;

    public static void main(String[] args) {
        show();
    }

    /**
     * This displays the initial screen that has the options for the entire network.
     */
    private static void show() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int x = 6;
        int y = 1;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(x, y));
        String[] buttonNames = new String[]{"Add A New Printer", "Add Document", "Check Queue", "Start Printer"
                , "Replenish Toner/Paper", "Exit Printer Simulation"};

        JButton button1 = new JButton(buttonNames[0]);
        button1.setPreferredSize(new Dimension(300, 100));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPrinter();
            }
        });
        panel.add(button1);

        JButton button2 = new JButton(buttonNames[1]);
        button2.setPreferredSize(new Dimension(300, 100));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDocument();
            }
        });
        panel.add(button2);

        JButton button3 = new JButton(buttonNames[2]);
        button3.setPreferredSize(new Dimension(300, 100));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkQueue();
            }
        });
        panel.add(button3);

        JButton button4 = new JButton(buttonNames[3]);
        button4.setPreferredSize(new Dimension(300, 100));
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startPrinter();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        panel.add(button4);

        JButton button5 = new JButton(buttonNames[4]);
        button5.setPreferredSize(new Dimension(300, 100));
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replenish();
            }
        });
        panel.add(button5);

        JButton button6 = new JButton(buttonNames[5]);
        button6.setPreferredSize(new Dimension(300, 100));
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(button6);

        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        container.add(panel);
        JScrollPane scrollPane = new JScrollPane(container);
        f.getContentPane().add(scrollPane);

        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    /**
     * This method adds functionality to the "Add a New Printer" button.
     * Asks user for identifying information, and adds the new printer to the network to be used.
     */
    private static void addPrinter()
    {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter New Printer Name"));
        JTextField field1 = new JTextField();
        panel.add(field1);
        panel.add(new JLabel("Enter Capacity of Paper Tray"));
        JTextField field2 = new JTextField();
        panel.add(field2);
        int result = JOptionPane.showConfirmDialog(null, panel, "Set Up Printer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            String name = field1.getText();
            int numPages = Integer.parseInt(field2.getText());
            Printer newPrinter = new Printer(name, numPages);
            availablePrinters.add(newPrinter);
            printerHashMap.put(newPrinter.getName(), newPrinter);
            if (numPrinters == printerNames.length - 1)
            {
                printerNames = Arrays.copyOf(printerNames, printerNames.length * 2);
            }
            printerNames[numPrinters] = newPrinter.getName();
            numPrinters++;
            JOptionPane.showMessageDialog(null, newPrinter.getName() + " was added to the Printer Network",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * This method adds functionality to the "Add Document" button
     * Asks user for details about the document to be printed
     * Creates a new Document object and adds the document to the respective printer's queue
     */
    private static void addDocument() {
        JComboBox<String> combo = new JComboBox<>(printerNames);
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();
        JTextField field4 = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Employee ID"));
        panel.add(field1);
        panel.add(new JLabel("Document Name"));
        panel.add(field2);
        panel.add(new JLabel("Number of Pages"));
        panel.add(field3);
        panel.add(new JLabel("Double Sides- Y/N"));
        panel.add(field4);
        panel.add(new JLabel("Printer Location"));
        panel.add(combo);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add a Document", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            int eID = Integer.parseInt(field1.getText());
            String document = field2.getText();
            int numPages = Integer.parseInt(field3.getText());
            boolean doubleSided = false;
            if (field4.getText().equals("Y"))
                doubleSided = true;
            Printer printer = printerHashMap.get(combo.getSelectedItem());
            Document docToAdd = new Document(eID, document, numPages, doubleSided);
            docToAdd.sendToPrinter(printer);
            String string = "Document sent successfully to " + printer.getName();
            JOptionPane.showMessageDialog(null, string, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Adds functionality to the "Check Queue" button
     * Asks the user for the printer they want to look at
     * Displays the current queue for the printer
     */
    public static void checkQueue() {
        JPanel panel = new JPanel(new GridLayout(printerNames.length, 1));
        JButton[] buttons = new JButton[printerNames.length];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(printerNames[i]);
            buttons[i].setSize(200, 100);
            buttons[i].setActionCommand(printerNames[i]);
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String choice = e.getActionCommand();
                    Printer printer = printerHashMap.get(choice);
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(1, 1));
                    String text = printer.outputQueue();
                    JOptionPane.showConfirmDialog(null, text, printer.getName() + " Current Queue", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
            });
            panel.add(buttons[i]);
        }
        JOptionPane.showConfirmDialog(null, panel, "Printers", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Adds functionality to the "Start Printer" button
     * Asks the user what printer they want to start printing from
     * Display a success message for successful start
     * @throws InterruptedException
     */
    private static void startPrinter() throws InterruptedException {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JComboBox<String> printerList = new JComboBox<>(printerNames);
        panel.add(new JLabel("Printer Location"));
        panel.add(printerList);
        frame.add(panel);
        int result = JOptionPane.showConfirmDialog(null, panel, "Printer Activity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            Printer printer = printerHashMap.get(printerList.getSelectedItem());
            printer.startPrinting();
            System.out.println("Printing Successfully Started");
        }
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Adds functionality to the "Replenish Toner/ Paper" button
     * Asks the user for what printer they want to refill
     * And for paper, toner or both
     * And refills the selected option at the printer.
     */
    private static void replenish()
    {
        JPanel panel = new JPanel(new GridLayout(0,1));
        JComboBox<String> printers = new JComboBox<>(printerNames);
        panel.add(new JLabel("Select Printer"));
        panel.add(printers);
        int result = JOptionPane.showConfirmDialog(null, panel, "Pick One", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            Printer printerSelected = printerHashMap.get(printers.getSelectedItem());
            JPanel panel1 = new JPanel(new GridLayout(0,1));
            panel1.add(new JLabel("Select One"));
            String[] options = new String[]{"Paper", "Toner", "Fill Both"};
            JComboBox<String> choices = new JComboBox<>(options);
            panel1.add(choices);
            int result1 = JOptionPane.showConfirmDialog(null, panel1, "Pick One", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (result1 == JOptionPane.OK_OPTION)
            {
                JPanel panel3 = new JPanel(new GridLayout(0,1));
                JLabel j1;
                String toFill = (String) choices.getSelectedItem();
                if (toFill.equals("Paper"))
                {
                    printerSelected.refillPaper();
                    j1 = new JLabel("Paper Filled");
                    panel3.add(j1);
                    JOptionPane.showConfirmDialog(null, panel3, "Status", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else if (toFill.equals("Toner"))
                {
                    printerSelected.fillToner();
                    j1 = new JLabel("Toner Filled");
                    panel3.add(j1);
                    JOptionPane.showConfirmDialog(null, panel3, "Status", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    printerSelected.fillToner();
                    printerSelected.refillPaper();
                    j1 = new JLabel("Paper & Toner Filled");
                    panel3.add(j1);
                    JOptionPane.showConfirmDialog(null, panel3, "Status", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}