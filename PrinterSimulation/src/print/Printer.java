package print;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Printer
{
    private String name;
    private double tonerLevel = 100;
    private int maxCapacity;
    private int numberPagesPrinted;
    private int size = 0;
    private Node front, rear;

    public static void main(String[] args) {
        Printer test = new Printer("Test", 100);
        Document test1 = new Document(1,"Txt.txt", 12, true);
        test1.sendToPrinter(test);
        System.out.println(test.lookForEmployeeID(1));
        test.toC();
    }
    public Printer(String name, int paperLeft)
    {
        this.name = name;
        this.maxCapacity = paperLeft;
        this.front = null;
        this.rear = null;
    }

    public void startPrinting() {
        JFrame frame = new JFrame();
        boolean ok = true;
        while (ok && front != null)
        {
            Document front = dequeue();
            if (isPrinterReadyToPrint(front))
            {
                double timeNeeded = getTimeRequired(front);
                int seconds = (int)timeNeeded;
                int milli = (int)((timeNeeded - seconds) * 1000);
                try
                {
                    Thread.sleep(milli);
                } catch (InterruptedException e)
                {
                    Thread.currentThread().interrupt();
                }
                JOptionPane.showMessageDialog(frame, front.getDocumentName() + " Was Printed to " + this.name + " by " +
                                front.getName(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } else {
                enqueue(front);
                ok = false;
            }
        }
    }

    private boolean checkToner(int numPages)
    {
        JFrame frame = new JFrame();
        double tonerUsed = numPages * .25;
        if (tonerLevel - tonerUsed > 0)
        {
            tonerLevel-=tonerUsed;
            if (tonerLevel < 10)
            {
                JOptionPane.showMessageDialog(frame, "Toner Warning",
                        "Toner Low at " + name, JOptionPane.WARNING_MESSAGE);
            }
            return true;
        }
        return false;
    }

    public void fillToner()
    {
        tonerLevel = 100;
    }

    private boolean checkPaper(int numPages, boolean doubleSided)
    {
        JFrame frame = new JFrame();
        if (doubleSided)
        {
            if (numPages%2 == 1)
            {
                int pages = ((numPages - 1) / 2) + 1;
                if (maxCapacity - pages > 0)
                {
                    maxCapacity-=pages;
                    if (maxCapacity < 30)
                    {
                        JOptionPane.showMessageDialog(frame, "Low On Paper Warning",
                                "Low Paper at " + name, JOptionPane.WARNING_MESSAGE);
                    }
                    return true;
                } else
                {
                    return false;
                }
            } else
            {
                int pages = numPages / 2;
                if (maxCapacity - pages > 0)
                {
                    maxCapacity-=numPages;
                    if (maxCapacity < 30)
                    {
                        JOptionPane.showMessageDialog(frame, "Low On Paper Warning",
                                "Low Paper at " + name, JOptionPane.WARNING_MESSAGE);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (maxCapacity - numPages > 0)
            {
                maxCapacity-=numPages;
                if (maxCapacity < 30)
                {
                    JOptionPane.showMessageDialog(frame, "Low On Paper Warning",
                            "Low Paper at " + name, JOptionPane.WARNING_MESSAGE);
                }
                return true;
            } else
            {
                return false;
            }
        }
    }

    public void refillPaper()
    {
        maxCapacity = 100;
    }

    private boolean isPrinterReadyToPrint(Document document)
    {
        int numPages = document.getNumPages();
        boolean doubleSided = document.isDoubleSided();
        if (checkPaper(numPages, doubleSided) && checkToner(numPages))
        {
            return true;
        } else
        {
            JFrame frame = new JFrame();
            if (!checkPaper(numPages, doubleSided))
            {
                JOptionPane.showMessageDialog(frame, name + " Low on Printer",
                        "Not enough Paper to Print Document - Replenish", JOptionPane.ERROR_MESSAGE);
            } else if (!checkToner(numPages))
            {
                JOptionPane.showMessageDialog(frame, name + " Low on Printer",
                        "Not enough Toner to Print Document - Replenish", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }

    public String outputQueue()
    {
        StringBuilder queue = new StringBuilder("");
        queue.append("Position").append(" | ").append("Doc Name").append(" | ").append("Employee ID").append("\n");
        int count = 1;
        Node current = front;
        while (current != null)
        {
            String docID = current.getData().getDocumentName();
            int eID = current.getData().getName();
            queue.append(count).append(" | ").append(docID).append(" | ").append(eID).append("\n");
            current = current.next;
            count++;
        }
        return queue.toString();
    }

    public String lookForEmployeeID(int employeeID)
    {
        StringBuilder queueText = new StringBuilder("");
        queueText.append("Position").append(" | ").append("Document Name").append("\n");
        int count = 1;
        Node current = front;
        while (current != null)
        {
            if (current.getData().getName() == employeeID)
            {
                String documentID = current.getData().getDocumentName();
                queueText.append(count).append(" | ").append(documentID).append("\n");
                current = current.next;
            }
        }
        return queueText.toString();
    }

    public Document dequeue() {
        Document onTop = front.getData();
        front = front.next;
        if (isEmpty())
        {
            rear = null;
        }
        size--;
        return onTop;
    }


    public void enqueue(Document document)
    {
        Node oldRear = rear;
        rear = new Node(document);
        rear.data = document;
        rear.next = null;
        if (isEmpty())
        {
            front = rear;
        } else
        {
            oldRear.next = rear;
        }
        size++;
    }

    private double getTimeRequired(Document document)
    {

        int numPages = document.getNumPages();
        double timeNeeded = (numPages * 0.6);
        return timeNeeded;
    }

    public Document getFront() {
        if (isEmpty())
            throw new NoSuchElementException();
        else
            return front.getData();
    }

    public String getName()
    {
        return name;
    }

    public int getLength()
    {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public Component toC() {
        JLabel j = new JLabel("");
        StringBuilder txt = new StringBuilder("<html>");
        txt.append("Position").append(" | ").append("Name");
        int count = 1;
        while (front.next != null)
        {
            txt.append("<br/>").append(count).append(" | ").append(front.getData().getDocumentName());
            count++;
        }
        txt.append("</html>");
        return j;
    }

    public void clear()
    {
        front = null;
        rear = null;
    }


    public class Node
    {
        private Document data;
        private Node next;

        public Node(Document data)
        {
            this(data, null);
        }

        Node (Document dataB, Node nextEntry)
        {
            data = dataB;
            next = nextEntry;
        }

        public Document getData()
        {
            return data;
        }

        private void setData(Document newDocument)
        {
            data = newDocument;
        }

        public Node getNext()
        {
            return next;
        }

        private void setNext(Node nextEntry)
        {
            next = nextEntry;
        }
    }
}