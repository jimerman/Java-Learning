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


public class CheckOutDialog extends Dialog {

	protected Object result;
	protected Shell shlCheckOut;
	protected BookLibrary bookLibrary;
	protected Display display;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public CheckOutDialog(Shell parent, int style, BookLibrary theLibrary) {
		super(parent, style);
		setText("Check Out");
		bookLibrary = theLibrary;
		display = getParent().getDisplay();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlCheckOut.open();
		shlCheckOut.layout();
		display = getParent().getDisplay();
		while (!shlCheckOut.isDisposed()) {
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
		shlCheckOut = new Shell();
		shlCheckOut.setSize(392, 204);
		shlCheckOut.setText("Check Out");
		
		Label lblSelectABook = new Label(shlCheckOut, SWT.NONE);
		lblSelectABook.setBounds(10, 10, 78, 13);
		lblSelectABook.setText("Select a book:");
		
		final Combo cboBooks = new Combo(shlCheckOut, SWT.NONE);
		cboBooks.setBounds(20, 29, 315, 21);
		
		Label lblSelectAPerson = new Label(shlCheckOut, SWT.NONE);
		lblSelectAPerson.setText("Select a person:");
		lblSelectAPerson.setBounds(10, 63, 78, 13);
		
		final Combo cboPeople = new Combo(shlCheckOut, SWT.NONE);
		cboPeople.setBounds(20, 82, 315, 21);
		
		Button btnCheckOut = new Button(shlCheckOut, SWT.NONE);
		btnCheckOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (cboBooks.getSelectionIndex() < 0) {
					MessageBox errMsg = new MessageBox(shlCheckOut, SWT.OK);
					errMsg.setText("Error");
					errMsg.setMessage("You must select a book to check out.");
					errMsg.open();
				}
				else {
					if (cboPeople.getSelectionIndex() < 0) {
						MessageBox errMsg = new MessageBox(shlCheckOut, SWT.OK);
						errMsg.setText("Error");
						errMsg.setMessage("You must select a person to whom to check out.");
						errMsg.open();
					}
					else {
						Book b1 = bookLibrary.getAvailableBooks().get(cboBooks.getSelectionIndex());
						Person p1 = bookLibrary.getPeople().get(cboPeople.getSelectionIndex());
						if (!bookLibrary.checkOut(b1, p1)) {
							MessageBox errMsg = new MessageBox(shlCheckOut, SWT.OK);
							errMsg.setText("Error");
							errMsg.setMessage("Check Out failed, perhaps the person already has checked out their maximum.");
							errMsg.open();
						}
						shlCheckOut.dispose();
					}
				}
			}
		});
		btnCheckOut.setBounds(62, 133, 68, 23);
		btnCheckOut.setText("Check Out");
		
		Button btnCancel = new Button(shlCheckOut, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlCheckOut.dispose();
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
