package print;

public class Document
{
    private String documentName;
    private int name;
    private int numPages;
    private boolean doubleSided;
    private Printer sentPrinter;

    public Document(int name, String documentName, int numPages, boolean doubleSided)
    {
        this.name = name;
        this.documentName = documentName;
        this.numPages = numPages;
        this.doubleSided = doubleSided;
    }

    public void sendToPrinter(Printer printer)
    {
        printer.enqueue(this);
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getDocumentName()
    {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public int getNumPages()
    {
        return numPages;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public boolean isDoubleSided() {
        return doubleSided;
    }

    public void setDoubleSided(boolean doubleSided) {
        this.doubleSided = doubleSided;
    }

    public Printer getSentPrinter()
    {
        return sentPrinter;
    }

    public void setSentPrinter(Printer sentPrinter) {
        this.sentPrinter = sentPrinter;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentName='" + documentName + '\'' +
                ", name=" + name +
                ", numPages=" + numPages +
                ", doubleSided=" + doubleSided +
                ", sentPrinter=" + sentPrinter +
                '}';
    }
}
