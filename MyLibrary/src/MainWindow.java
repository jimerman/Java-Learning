import java.util.ArrayList;
import java.util.jar.JarInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.totalbeginner.*;
import org.totalbeginner.tutorial.*;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;

import java.awt.TextArea;

import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class MainWindow {

	public static final String SAVE_FILE = "C:\\Users\\jay.imerman\\workspace\\MyLibrary\\bin\\Library.sav"; 
	private BookLibrary theLibrary;
	protected Shell shell;
	private Text txtOutput;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void saveFile() throws Exception {
		// Save library
/*		JAXBContext context = JAXBContext.newInstance(BookLibrary.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
*/		
		PrintWriter outFile = new PrintWriter(MainWindow.SAVE_FILE, "UTF-8");
		outFile.println(theLibrary.getName());

		for (Person p : theLibrary.getPeople()) {
			outFile.println("Person:" + p.getName() + "|" + p.getMaxBooks());
		}
		
		for (Book b : theLibrary.getBooks()) {
			if (b.getPerson() == null) {
				outFile.println("Book:" + b.getTitle() + "|" + b.getAuthor() + "|");
			}
			else {
				outFile.println("Book:" + b.getTitle() + "|" + b.getAuthor() + "|" + b.getPerson().getName());
			}
		}
		
		outFile.close();
		//m.marshal(theLibrary, outFile);
	}
	
	protected void loadFile() throws Exception {
		// Load library
		String tempLine;
		theLibrary = null;
		BufferedReader inFile = new BufferedReader(new FileReader(MainWindow.SAVE_FILE));
		while ((tempLine = inFile.readLine()) != null) {
			if (theLibrary == null) {
				theLibrary = new BookLibrary(tempLine);			
			}
			else {
				if (tempLine.startsWith("Person:")) {
					String[] ps = tempLine.substring(7).split("\\|");
					Person p = new Person(ps[0], Integer.parseInt(ps[1]));
					theLibrary.addPerson(p);
				}
				if (tempLine.startsWith("Book:")) {
					String[] bs = tempLine.substring(5).split("\\|");
					Book b = new Book(bs[0]);
					b.setAuthor(bs[1]);
					if (bs.length > 2) {
						// Lookup person by name
						Person p = theLibrary.getPerson(bs[2]);
						if (p != null) {
							b.setPerson(p);
						}
					}
					theLibrary.addBook(b);
				}
			}
		}
		inFile.close();
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				try {
					saveFile();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		shell.setSize(450, 300);
		shell.setText("My Library");
		
		try {
			loadFile();
		} catch (Exception e) {
			theLibrary = new BookLibrary("New Library");
		}


		
		final Label lblLibrary = new Label(shell, SWT.NONE);
		lblLibrary.setBounds(10, 15, 203, 13);
		lblLibrary.setText("Library: " + theLibrary.getName());
		
		Button btnEditLibraryName = new Button(shell, SWT.NONE);
		btnEditLibraryName.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String newName = JOptionPane.showInputDialog("Enter the new name:");
				if (newName != null) {
					theLibrary.setName(newName);
					lblLibrary.setText("Library: " + theLibrary.getName());					
				}
			}
		});
		btnEditLibraryName.setBounds(219, 10, 112, 23);
		btnEditLibraryName.setText("Edit Library Name");
		
		final Label lblBooks = new Label(shell, SWT.NONE);
		lblBooks.setBounds(10, 44, 203, 13);
		lblBooks.setText("Books: " + theLibrary.getAvailableBooks().size() + "/" + theLibrary.getBooks().size());
		
		final Label lblPeople = new Label(shell, SWT.NONE);
		lblPeople.setBounds(10, 76, 203, 13);
		lblPeople.setText("People: " + theLibrary.getPeople().size());
		
		Button btnNewBook = new Button(shell, SWT.NONE);
		btnNewBook.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String title = JOptionPane.showInputDialog("Enter the book title: ");
				if (title != null) {
					if (title.length() > 0) {
						Book b1 = new Book(title);
						title = JOptionPane.showInputDialog("Enter the author: ");
						if (title != null) {
							b1.setAuthor(title);
						}
						theLibrary.addBook(b1);
						lblBooks.setText("Books: " + theLibrary.getAvailableBooks().size() + "/" + theLibrary.getBooks().size());
					}
				}
			}
		});
		btnNewBook.setBounds(219, 39, 68, 23);
		btnNewBook.setText("New Book");
		
		Button btnNewPerson = new Button(shell, SWT.NONE);
		btnNewPerson.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = JOptionPane.showInputDialog("Enter the person's name: ");
				if (name != null) {
					if (name.length() > 0) {
						Person p1 = new Person(name);
						theLibrary.addPerson(p1);
						lblPeople.setText("People: " + theLibrary.getPeople().size());
					}
				}
			}
		});
		btnNewPerson.setBounds(219, 71, 68, 23);
		btnNewPerson.setText("New Person");
		
		Button btnCheckOut = new Button(shell, SWT.NONE);
		btnCheckOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					CheckOutDialog window = new CheckOutDialog(shell, SWT.DIALOG_TRIM, theLibrary);
					window.open();
					Display display = Display.getDefault();
					while (!window.shlCheckOut.isDisposed()) {
						display.sleep();						
					}
					lblBooks.setText("Books: " + theLibrary.getAvailableBooks().size() + "/" + theLibrary.getBooks().size());
				} catch (Exception eCheckOut) {
					eCheckOut.printStackTrace();
				}
			}
		});
		
		btnCheckOut.setBounds(27, 111, 68, 23);
		btnCheckOut.setText("Check Out");
		
		Button btnCheckIn = new Button(shell, SWT.NONE);
		btnCheckIn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					CheckInDialog window = new CheckInDialog(shell, SWT.DIALOG_TRIM, theLibrary);
					window.open();
					Display display = Display.getDefault();
					while (!window.shlCheckIn.isDisposed()) {
						display.sleep();
					}
					lblBooks.setText("Books: " + theLibrary.getAvailableBooks().size() + "/" + theLibrary.getBooks().size());
				} catch (Exception eCheckIn) {
					eCheckIn.printStackTrace();
				}
			}
		});
		btnCheckIn.setBounds(156, 111, 68, 23);
		btnCheckIn.setText("Check In");
		
		Button btnStatus = new Button(shell, SWT.NONE);
		btnStatus.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtOutput.setText(theLibrary.getStatus());
			}
		});
		btnStatus.setBounds(278, 111, 68, 23);
		btnStatus.setText("Status");
		
		txtOutput = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtOutput.setEditable(false);
		txtOutput.setBounds(10, 140, 422, 106);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmHelp_1 = new MenuItem(menu, SWT.CASCADE);
		mntmHelp_1.setText("Help");
		
		Menu menu_1 = new Menu(mntmHelp_1);
		mntmHelp_1.setMenu(menu_1);
		
		MenuItem mntmAbout = new MenuItem(menu_1, SWT.NONE);
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setText("About MyLibrary");
				msg.setMessage("Copyright (c) 2014, Jay Imerman");
				msg.open();
			}
		});
		mntmAbout.setText("About");
		
		Label label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 0, 422, 2);

	}
}
