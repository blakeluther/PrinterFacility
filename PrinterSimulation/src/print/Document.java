package print;

public class Document
{
    private String documentName;
    private int id;
    private int numPages;
    private boolean doubleSided;
    private Printer sentPrinter;

    /**
     * Constructs a new Document object
     * @param id - int representing Employee ID
     * @param documentName - Name of the document
     * @param numPages - Number of pages for this document
     * @param doubleSided - boolean - true or false
     */
    public Document(int id, String documentName, int numPages, boolean doubleSided)
    {
        this.id = id;
        this.documentName = documentName;
        this.numPages = numPages;
        this.doubleSided = doubleSided;
    }

    /**
     * Sends a Document to a printer
     * Enqueues the doc to the printer
     * @param printer - Printer to add the Document to
     */
    public void sendToPrinter(Printer printer)
    {
        printer.enqueue(this);
    }

    /**
     * Gets the employee ID referenced by the document
     * @return int that represents a ID number
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the employee ID to a new ID
     * @param id - a new int / employee ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the document
     * @return - String that is the document Name
     */
    public String getDocumentName()
    {
        return documentName;
    }

    /**
     * Sets a new Document name
     * @param documentName - String with new document name
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Returns the number of pages for the Document
     * @return - int
     */
    public int getNumPages()
    {
        return numPages;
    }

    /**
     * Changes the number of pages needed to print the document
     * @param numPages int
     */
    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    /**
     * Returns whether or not the documnet is to be double-sided or not
     * @return boolean
     */
    public boolean isDoubleSided() {
        return doubleSided;
    }

    /**
     * Changes if the document is double-sided
     * @param doubleSided - boolean
     */
    public void setDoubleSided(boolean doubleSided) {
        this.doubleSided = doubleSided;
    }

    /**
     * Gets the printer that this document was sent to
     * @return Printer object
     */
    public Printer getSentPrinter()
    {
        return sentPrinter;
    }

    /**
     * Changes the printer the document is sent too
     * @param sentPrinter new Printer object
     */
    public void setSentPrinter(Printer sentPrinter) {
        this.sentPrinter = sentPrinter;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentName='" + documentName + '\'' +
                ", name=" + id +
                ", numPages=" + numPages +
                ", doubleSided=" + doubleSided +
                ", sentPrinter=" + sentPrinter +
                '}';
    }
}
