import java.awt.Dialog;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.totalbeginner.*;
import org.totalbeginner.tutorial.*;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CheckOutWindow {

	protected Shell shlCheckOut;
	protected BookLibrary bookLibrary;
	
	public CheckOutWindow(BookLibrary theLibrary) {
		this.bookLibrary = theLibrary;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CheckOutWindow window = new CheckOutWindow(null);
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
		shlCheckOut.open();
		shlCheckOut.layout();
		while (!shlCheckOut.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCheckOut = new Shell();
		shlCheckOut.setSize(392, 204);
		shlCheckOut.setText("Check Out");
		
		Label lblSelectABook = new Label(shlCheckOut, SWT.NONE);
		lblSelectABook.setBounds(10, 10, 78, 13);
		lblSelectABook.setText("Select a book:");
		
		Combo cboBooks = new Combo(shlCheckOut, SWT.NONE);
		cboBooks.setBounds(20, 29, 315, 21);
		
		Label lblSelectAPerson = new Label(shlCheckOut, SWT.NONE);
		lblSelectAPerson.setText("Select a person:");
		lblSelectAPerson.setBounds(10, 63, 78, 13);
		
		Combo cboPeople = new Combo(shlCheckOut, SWT.NONE);
		cboPeople.setBounds(20, 82, 315, 21);
		
		Button btnCheckOut = new Button(shlCheckOut, SWT.NONE);
		btnCheckOut.setBounds(62, 133, 68, 23);
		btnCheckOut.setText("Check Out");
		
		Button btnCancel = new Button(shlCheckOut, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnCancel.setBounds(283, 133, 68, 23);
		btnCancel.setText("Cancel");

		// Build the combo boxes
		for (Book b : this.bookLibrary.getAvailableBooks()) {
			cboBooks.add(b.toString());
		}
		
		for (Person p : this.bookLibrary.getPeople()) {
			cboPeople.add(p.toString());
		}

	}
}
