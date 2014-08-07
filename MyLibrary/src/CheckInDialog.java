import java.lang.reflect.Array;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.totalbeginner.*;
import org.totalbeginner.tutorial.*;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.internal.Library;

public class CheckInDialog extends Dialog {

	protected Object result;
	protected Shell shlCheckIn;
	protected BookLibrary bookLibrary;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CheckInDialog(Shell parent, int style, BookLibrary theLibrary) {
		super(parent, style);
		bookLibrary = theLibrary;
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlCheckIn.open();
		shlCheckIn.layout();
		Display display = getParent().getDisplay();
		while (!shlCheckIn.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shlCheckIn = new Shell(getParent(), getStyle());
		shlCheckIn.setSize(450, 300);
		shlCheckIn.setText("Check In");

		final ArrayList<Book> checkedOutBooks = new ArrayList<Book>();
		final ArrayList<Person> checkedOutPeople = new ArrayList<Person>();

		Label label = new Label(shlCheckIn, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setBounds(10, 79, 424, 2);
		
		Label lblCheckInA = new Label(shlCheckIn, SWT.NONE);
		lblCheckInA.setBounds(10, 10, 81, 13);
		lblCheckInA.setText("Check In a Book:");
		
		final Combo cboCheckInBook = new Combo(shlCheckIn, SWT.NONE);
		cboCheckInBook.setBounds(10, 37, 300, 21);
		
		Label lblCheckInBy = new Label(shlCheckIn, SWT.NONE);
		lblCheckInBy.setText("Check In by Person:");
		lblCheckInBy.setBounds(10, 97, 107, 13);
		
		final Combo cboPersonBook = new Combo(shlCheckIn, SWT.NONE);
		cboPersonBook.setBounds(10, 174, 300, 21);
		
		final Combo cboPerson = new Combo(shlCheckIn, SWT.NONE);
		cboPerson.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// List the books this person has checked out
				cboPersonBook.removeAll();
				Person p1 = bookLibrary.getPeople().get(cboPerson.getSelectionIndex());
				for (Book b : bookLibrary.getBooksForPerson(p1)) {
					cboPersonBook.add(b.toString());
				}
			}
		});
		cboPerson.setBounds(10, 124, 300, 21);
		
		// Initialize dialog
		for (Book b : bookLibrary.getBooks()) {
			if (b.getPerson() != null) {
				checkedOutBooks.add(b);
				cboCheckInBook.add(b.toString());
			}
		}
		
		for (Person p : bookLibrary.getPeople()) {
			if (bookLibrary.getBooksForPerson(p).size() > 0) {
				checkedOutPeople.add(p);
				cboPerson.add(p.toString());
			}
		}
		
		Button btnCheckInBook = new Button(shlCheckIn, SWT.NONE);
		btnCheckInBook.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Check if ready to do Check In
				if (cboCheckInBook.getSelectionIndex() < 0) {
					MessageBox errMsg = new MessageBox(shlCheckIn, SWT.OK);
					errMsg.setText("Error");
					errMsg.setMessage("You must select a book to check in.");
					errMsg.open();
				}
				else {
					Book b1 = checkedOutBooks.get(cboCheckInBook.getSelectionIndex());
					bookLibrary.checkIn(b1);
					shlCheckIn.dispose();
				}
			}
		});
		btnCheckInBook.setBounds(342, 35, 68, 23);
		btnCheckInBook.setText("Check In");
		
		Button btnCheckInPerson = new Button(shlCheckIn, SWT.NONE);
		btnCheckInPerson.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (cboPerson.getSelectionIndex() < 0) {
					MessageBox errMsg = new MessageBox(shlCheckIn, SWT.OK);
					errMsg.setText("Error");
					errMsg.setMessage("You must select a person who has books checked out.");
					errMsg.open();
				}
				else {
					Person p = checkedOutPeople.get(cboPerson.getSelectionIndex());
					if (cboPersonBook.getSelectionIndex() < 0) {
						MessageBox errMsg = new MessageBox(shlCheckIn, SWT.OK);
						errMsg.setText("Error");
						errMsg.setMessage("You must select one of " + p.getName() + "'s books to check in.");
						errMsg.open();
					}
					else {
						Book b = bookLibrary.getBooksForPerson(p).get(cboPersonBook.getSelectionIndex());
						bookLibrary.checkIn(b);
						shlCheckIn.dispose();
					}
				}
			}
		});
		btnCheckInPerson.setBounds(342, 172, 68, 23);
		btnCheckInPerson.setText("Check In");
		
		Button btnCancel = new Button(shlCheckIn, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlCheckIn.dispose();
			}
		});
		btnCancel.setBounds(203, 229, 68, 23);
		btnCancel.setText("Cancel");

	}
}
