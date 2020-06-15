package Sets.Model;

import Sets.View.PopUp;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

// This is used to set limit for user input for JTextField to prevent overflow
///https://stackoverflow.com/questions/10136794/limiting-the-number-of-characters-in-a-jtextfield
public class PlainDocumentLimited extends PlainDocument {
	private int limit;

	public PlainDocumentLimited(int limit) {
		super();
		this.limit = limit;
	}

	public  PlainDocumentLimited(int limit, boolean upper) {
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
		else{
			PopUp.infoBox("This input area is limited to " + limit + " characters", "PlainDocumentLimited");
		}
	}
}
