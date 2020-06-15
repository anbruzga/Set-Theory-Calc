package Sets.Controler;

import Sets.View.PopUp;

public class IncorrectInputSyntaxException extends Exception {

	public IncorrectInputSyntaxException(String errorMessage, String callerIdentification) {
		super(errorMessage);
		PopUp.infoBox(errorMessage, callerIdentification); // comment for running automated tests

	}
}
