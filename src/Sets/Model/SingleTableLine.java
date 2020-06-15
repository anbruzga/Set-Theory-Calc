package Sets.Model;

import Sets.View.PopUp;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class SingleTableLine implements Serializable {
	private static final long serialVersionUID = -1240697744316073936L;
	private String exercise;
	private boolean success;
	private int noOfFails = 0;
	private int percentage;
	private boolean answerCheckBeforeSuccess;


	public boolean isAnswerCheckBeforeSuccess() {
		return answerCheckBeforeSuccess;
	}

	public void setAnswerCheckBeforeSuccess(boolean answerCheckBeforeSuccess) {
		this.answerCheckBeforeSuccess = answerCheckBeforeSuccess;
	}

	public SingleTableLine(String exercise, boolean success, int noOfFails, boolean answerCheckBeforeSuccess) {
		this.exercise = exercise;
		this.success = success;
		this.noOfFails = noOfFails;
		this.answerCheckBeforeSuccess = answerCheckBeforeSuccess;
	}

	public String getExercise() {
		//String encoding = System.getProperty("file.encoding");
      //  PopUp.infoBox(exercise + encoding, "");
        return exercise;
	}

	public void setExercise(String exercise) {
		byte arr[] = new byte[0];
		arr = exercise.getBytes(StandardCharsets.UTF_8);
		String s = new String(arr, StandardCharsets.UTF_8);
		this.exercise = s;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getNoOfFails() {
		return noOfFails;
	}

	public void setNoOfFails(int noOfFails) {
		this.noOfFails = noOfFails;
	}

	public int getPercentage() {
		if (success) {
			// 1 success
			// 4 not
			// 5 tries = 100
			// 1 try = 100/5
			percentage = 100 / (noOfFails + 1);
		} else percentage = 0 / 2; // gives NaN

		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public void addFail() {
		noOfFails++;
	}

	@Override
	public String toString() {
		return "SingleTableLine{" +
				"exercise='" + exercise + '\'' +
				", success=" + success +
				", noOfFails=" + noOfFails +
				'}';
	}
}
