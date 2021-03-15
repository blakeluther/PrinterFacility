package print;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Printer
{
    private String name;
    private double tonerLevel = 100;
    private int maxCapacity;
    private int paperLeft;
    private int size = 0;
    private Node front, rear;

    /**
     * Constructs a Printer class
     * front and rear nodes are intialized to null because the queue is empty at time of creation
     * @param name - name of the Printer object
     * @param maxCapacity - number of pieces of paper a printer can hold
     */
    public Printer(String name, int maxCapacity)
    {
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.paperLeft = maxCapacity;
        this.front = null;
        this.rear = null;
    }

    /**
     * Starts the printing of the queue of this Printer objecy
     * Create new JFrame, and dequeues the first Document in the queue if not null
     * Makes sure the Printer has enough paper and toner before printing, if not it will create a warning popup
     * Also, will enqueue the dequeued Document
     * Calculates time needed to print Document and waits that time
     * Creates popup when printing for document is done
     */
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
                JOptionPane.showMessageDialog(frame, front.getDocumentName() + " was printed to " + this.name + " by ID " +
                                front.getId(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);

            } else {
                enqueue(front);
                ok = false;
            }
        }
    }

    /**
     * Works with the isPrinterReadyToPrint(Document doc) method to ensure the printer has enough ink to print
     * If the amount used is greater than zero, it will allow the Document to be printed
     * If Toner goes beneath 10%, it will create a pop-up warning to warn of low toner
     * @param numPages - Number of pages in the document to be printed
     * @return boolean
     */
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

    /**
     * Checks if the Document that is to be printed can be printed due to the number of pages it has
     * If the Document is listed as double-sided, it will reduce the number of pages by half.
     * Calculates the number of pages needed, and checks if there is enough paper in the printer
     * If there is it will return true, but if the paper is lower than 30, it will create a "Low on Paper" warning
     * If there is not enough paper, it will return false, and not allow the Printer to print the next Document
     * @param numPages - Number of pages in the Document
     * @param doubleSided - boolean that dentoes if the Document is to be printed on one side or both sides.
     * @return boolean - true/ false
     */
    private boolean checkPaper(int numPages, boolean doubleSided)
    {
        JFrame frame = new JFrame();
        if (doubleSided)
        {
            if (numPages%2 == 1)
            {
                int pages = ((numPages - 1) / 2) + 1;
                if (paperLeft - pages > 0)
                {
                    paperLeft-=pages;
                    if (paperLeft < 30)
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
                if (paperLeft - pages > 0)
                {
                    paperLeft-=numPages;
                    if (paperLeft < 30)
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
            if (paperLeft - numPages > 0)
            {
                paperLeft-=numPages;
                if (paperLeft < 30)
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

    /**
     * Refills the Paper Tray to the maximum capacity indicted earlier
     */
    public void refillPaper()
    {
        paperLeft = maxCapacity;
    }

    /**
     * Refills the toner to 100% capacity
     */
    public void fillToner()
    {
        tonerLevel = 100;
    }

    /**
     * Checks if the Document next in the queue has enough paper and toner to print
     * If both are good, then will allow the Document to be printed
     * If not, it will run an out of paper/ toner and prompt the user to refill the Printer
     * @param document - Document object at the front of the queue
     * @return boolean
     */
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
                JOptionPane.showMessageDialog(frame, "Printer " + name + " Out of Paper",
                        "ERROR - Paper", JOptionPane.ERROR_MESSAGE);
            } else if (!checkToner(numPages))
            {
                JOptionPane.showMessageDialog(frame, "Printer " + name + " Low on Toner",
                        "ERROR - Toner", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }

    /**
     * Builds a string of the contents of the queue of the Printer
     * @return String
     */
    public String outputQueue()
    {
        StringBuilder queue = new StringBuilder("");
        queue.append("Position").append(" | ").append("Doc Name").append(" | ").append("Employee ID").append("\n");
        int count = 1;
        Node current = front;
        while (current != null)
        {
            String docID = current.getData().getDocumentName();
            int eID = current.getData().getId();
            queue.append(count).append(" | ").append(docID).append(" | ").append(eID).append("\n");
            current = current.next;
            count++;
        }
        return queue.toString();
    }

    /**
     * Gets the next document in the queue, and reduces size of the queue
     * @return Document object representing the object on top
     */
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

    /**
     * Adds a new Document object to the queue, and readjusts the rear and .next of the previous rear of the queue
     * @param document - Document object to be added
     */
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

    /**
     * Calculates the time required to print the document based upon the number of pages
     * @param document - Document to print
     * @return double representing the time needed
     */
    private double getTimeRequired(Document document)
    {

        int numPages = document.getNumPages();
        double timeNeeded = (numPages * 0.6);
        return timeNeeded;
    }

    /**
     * Gets the Document at the front of the queue
     * @return Document object
     */
    public Document getFront() {
        if (isEmpty())
            throw new NoSuchElementException();
        else
            return front.getData();
    }

    /**
     * Returns the name of the printer
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the size of the queue of the printer
     * @return Int
     */
    public int getLength()
    {
        return size;
    }

    /**
     * Checks if the queue is empty by checking the numerical value of size
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Class that stores the data and forms the queue of the Printer
     */
    public class Node
    {
        private Document data;
        private Node next;

        /**
         * Constructs a new Node
         * @param data - Document object
         */
        public Node(Document data)
        {
            this(data, null);
        }

        /**
         * Constructs a new Node
         * @param data Document object
         * @param nextEntry Node that references the object behind/ next to it
         */
        Node (Document data, Node nextEntry)
        {
            data = data;
            next = nextEntry;
        }

        /**
         * Retuns the Document object inside a Node object
         * @return
         */
        public Document getData()
        {
            return data;
        }

        /**
         * Sets a new Document object to a Node
         * @param newDocument new Document object
         */
        private void setData(Document newDocument)
        {
            data = newDocument;
        }

        /**
         * Gets the Node behind this node
         * @return Node
         */
        public Node getNext()
        {
            return next;
        }

        /**
         * Changes the Node that refernces the next Node in the linked list/ queue
         * @param nextEntry Node
         */
        private void setNext(Node nextEntry)
        {
            next = nextEntry;
        }
    }
}