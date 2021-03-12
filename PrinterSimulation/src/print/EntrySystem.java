package print;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class EntrySystem {
    private static ArrayList<Printer> availablePrinters;
    private static String[] printerNames;
    private static HashMap<String, Printer> printerHashMap;

    public static void main(String[] args) {
        initializePrinters();
        show();
    }

    private static void initializePrinters() {
        availablePrinters = new ArrayList<>();
        Printer p1 = new Printer("Site-West", 50);
        Printer p2 = new Printer("Site-North", 50);
        Printer p3 = new Printer("Site-East", 50);
        Printer p4 = new Printer("Site-Office", 50);
        Printer mobile = new Printer("Mobile", 25);

        availablePrinters.add(p1);
        availablePrinters.add(p2);
        availablePrinters.add(p3);
        availablePrinters.add(p4);
        availablePrinters.add(mobile);

        printerHashMap = new HashMap<>();
        printerHashMap.put(p1.getName(), p1);
        printerHashMap.put(p2.getName(), p2);
        printerHashMap.put(p3.getName(), p3);
        printerHashMap.put(p4.getName(), p4);
        printerHashMap.put(mobile.getName(), mobile);

        printerNames = new String[availablePrinters.size()];
        for (int index = 0; index < availablePrinters.size(); index++) {
            String name = availablePrinters.get(index).getName();
            printerNames[index] = name;
        }
    }

    private static void addDocument() {
        JComboBox<String> combo = new JComboBox<>(printerNames);
        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();
        JTextField field4 = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Employee ID"));
        panel.add(field1);
        panel.add(new JLabel("Document ID"));
        panel.add(field2);
        panel.add(new JLabel("Number of Pages"));
        panel.add(field3);
        panel.add(new JLabel("Double Sides- Y/N"));
        panel.add(field4);
        panel.add(new JLabel("Printer Location"));
        panel.add(combo);
        int result = JOptionPane.showConfirmDialog(null, panel, "Printer Activity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
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
            String string = String.format("Document sent successfully to " + printer);
            System.out.println(string);
        }
    }

    private static void show() {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int x = 6;
        int y = 1;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(x, y));
        String[] buttonNames = new String[]{"Add Document", "Check Queue", "Start Printer", "Find Documents in Queue Using" +
                " Employee ID", "Replenish Toner/Paper", "Exit Printer Simulation"};

        JButton button1 = new JButton(buttonNames[0]);
        button1.setPreferredSize(new Dimension(300, 100));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDocument();
            }
        });
        panel.add(button1);

        JButton button2 = new JButton(buttonNames[1]);
        button2.setPreferredSize(new Dimension(300, 100));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkQueue();
            }
        });
        panel.add(button2);

        JButton button3 = new JButton(buttonNames[2]);
        button3.setPreferredSize(new Dimension(300, 100));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startPrinter();
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        });
        panel.add(button3);

        JButton button4 = new JButton(buttonNames[3]);
        button4.setPreferredSize(new Dimension(300, 100));
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPositionInQueue();
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

    private static void checkPositionInQueue()
    {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JComboBox<String> printerList = new JComboBox<>(printerNames);
        panel.add(new JLabel("Location Finder"));
        panel.add(printerList);
        int result = JOptionPane.showConfirmDialog(null, panel, "Printer Activity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION)
        {
            Printer printerSelected = printerHashMap.get(printerList.getSelectedItem());
            JPanel panel1 = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Enter employee ID"));
            JTextField field1 = new JTextField();
            panel1.add(field1);
            int result1 = JOptionPane.showConfirmDialog(null, panel1, "Enter Employee ID", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result1 == JOptionPane.OK_OPTION)
            {
                int eID = Integer.parseInt(field1.getText());
                JPanel panel2 = new JPanel();
                JLabel t1;
                String text = printerSelected.lookForEmployeeID(eID);
                t1 = new JLabel(text);
                panel.add(t1);
                JOptionPane.showConfirmDialog(null, panel2, "Document Queue for " + eID + " at " + printerSelected.getName(), JOptionPane.DEFAULT_OPTION);
            }
        }
    }

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