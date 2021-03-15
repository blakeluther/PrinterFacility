# PrinterFacility

## Overview
Printer Facility simulates a printer network that allows you to send documents to any printer on the network. This program is implemented using Java Swing, and the GUI allows you to add documents, look at the queues of the the printers on the network, start the printing of these printers, find any documents in a printer queue that were sent by a particular Employee ID number and to refill the paper and toner in said printers.

## Usage
Run the main method in EntrySystem.java. It will open a user interface using Java Swing. There are six buttons to click on; "Add a New Printer", "Add Document", "Check Queue",
"Start Printer", "Replenish Toner/Paper", "Exit Printer Simulation."

At the start of the program, there will be no available printers to add a Document to, so you must create a new Printer using the "Add a New Printer" button. At this point, you can use a singular Printer to send all the documents to using "Add Document." Press "Start Printer" to start the 'printing' of these documents. It is visual based so each time the Printer prints a Document, it will create a pop-up to the screen. If the Printer runs out of Paper or toner, warning or a error pop up and note the error at hand.

- "Add a New Printer" - Allows you to add a new Printer to the network. Will ask for the name of the Printer, and the amount of paper it can hold
- "Add Document" - This button will ask for identying information about the new Document to create, and the printer, in which you want the Document to be printed at. **Note: Only the printers you add will be available to print to.**
- "Check Queue" - Allows the user to check the current queue of any Printer on the Network. Will display a text pop-up with the current queue.
- "Start Printer" - Starts the 'printing' of the Documents in the queue. Will display each time a Document is printed, and will display the warning and erros pop-ups if either the paper or printer is running low or empty.
- "Replenish Toner/Paper" - Refills the toner, paper or both at any Printer on the network.
- Exit Printer Simulation" - Ends the program. **Note: The current state of the program (the created printers on the network, and any documents in their queues) will not be saved.**

## Installation Requirements/ Dependencies
### Requirements
- IntelliJ or equivalent - I used 2020.3.1
- Java SDK - was written using Oracle Open JDK 15.0.1

## Contributing
Pull requests are welcome.

## License
[MIT](https://choosealicense.com/licenses/mit/)
