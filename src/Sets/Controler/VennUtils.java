package Sets.Controler;

import Sets.Model.IntRange;
import Sets.Model.MixedSet;
import Sets.Model.UniversalSet;
import Sets.View.Styles;
import Sets.View.VennExercise2Sets;
import Sets.View.VennExercise3Sets;
import Sets.View.VennExercise4Sets;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


public class VennUtils {

	private static final String INSTRUCTIONS_VENN_EXERCISES = "This view allows to exercise Venn Diagrams in the reversed way. \n" +
			"You will see, that each part has a single number. LEFT CLICK a part\n" +
			"in the diagram to ADD it to a set. RIGHT CLICK it to DESELECT it.\n" +
			"Then, type a formula which finds selected parts and click \"CHECK!\"\n" +
			"to get FEEDBACK ";
	public static final  int VENN_EXERCISE_INPUT_LIMIT = 200;


	//https://stackoverflow.com/questions/22217148/get-pixel-color-on-screen-java
	public static Color getPixel(int x, int y) throws AWTException {
		Robot rb = new Robot();
		return rb.getPixelColor(x, y);
	}


	public static void reloadOriginalSetList(ArrayList<MixedSet> sets, ArrayList<MixedSet> setsListOriginal) {
		sets.clear();
		for (int i = 0; i < setsListOriginal.size(); i++) {
			MixedSet set = setsListOriginal.get(i);
			MixedSet deepCopySet = SetUtils.deepCopy(set);
			sets.add(deepCopySet);
		}
	}


	public static void startVennExercises2(Dimension dimension) {
		if (UniversalSet.getInstance() == null) {
			MixedSet base = new MixedSet(false);
			base.add(new IntRange(1, 3));
			UniversalSet.getInstance(base, 3, 1, 'N');
		}

		MixedSet set0 = new MixedSet(false);
		MixedSet set1 = new MixedSet(false);

		set0.add(1);
		set0.add(3);

		set1.add(2);
		set1.add(3);

		ArrayList<MixedSet> setList = new ArrayList<>();

		setList.add(set0);
		setList.add(set1);


		VennExercise2Sets test = new VennExercise2Sets(dimension);

		test.setSets(setList);


	}

	public static void startVennExercises3(Dimension dimension) {
		// Instant start = Instant.now();
		if (UniversalSet.getInstance() == null) {
			MixedSet base = new MixedSet(false);
			base.add(new IntRange(1, 8));
			UniversalSet.getInstance(base, 8, 1, 'N');
		}

		VennExercise3Sets v3 = new VennExercise3Sets(dimension);
		MixedSet set1 = new MixedSet(false);
		MixedSet set2 = new MixedSet(false);
		MixedSet set3 = new MixedSet(false);

		set1.add(1);
		set1.add(2);
		set1.add(4);
		set1.add(5);

		set2.add(2);
		set2.add(3);
		set2.add(5);
		set2.add(6);

		set3.add(6);
		set3.add(7);
		set3.add(4);
		set3.add(5);

		ArrayList<MixedSet> setListForVenn3 = new ArrayList<>();
		setListForVenn3.add(set1);
		setListForVenn3.add(set2);
		setListForVenn3.add(set3);

		v3.setSets(setListForVenn3);

	}

	public static void startVennExercises4(Dimension dimension) {

		if (UniversalSet.getInstance() == null) {
			MixedSet base = new MixedSet(false);
			base.add(new IntRange(1, 16));
			UniversalSet.getInstance(base, 16, 1, 'N');
		}

		VennExercise4Sets v4 = new VennExercise4Sets(dimension);
		MixedSet set1 = new MixedSet(false);
		MixedSet set2 = new MixedSet(false);
		MixedSet set3 = new MixedSet(false);
		MixedSet set4 = new MixedSet(false);

		set1.add(1);
		set1.add(3);
		set1.add(9);
		set1.add(12);
		set1.add(5);
		set1.add(8);
		set1.add(10);
		set1.add(14);

		set2.add(2);
		set2.add(3);
		set2.add(9);
		set2.add(6);
		set2.add(8);
		set2.add(10);
		set2.add(11);
		set2.add(13);

		set3.add(6);
		set3.add(7);
		set3.add(12);
		set3.add(10);
		set3.add(14);
		set3.add(13);
		set3.add(15);
		set3.add(9);

		set4.add(4);
		set4.add(5);
		set4.add(8);
		set4.add(10);
		set4.add(11);
		set4.add(13);
		set4.add(14);
		set4.add(15);

		ArrayList<MixedSet> setListForVenn4 = new ArrayList<>();
		setListForVenn4.add(set1);
		setListForVenn4.add(set2);
		setListForVenn4.add(set3);
		setListForVenn4.add(set4);

		v4.setSets(setListForVenn4);

	}

	public static void setSetNames(ArrayList<MixedSet> sets, ArrayList<JLabel> setNameLabelList) {
		String[] names = {"1st Set", "2nd Set", "3rd Set", "4th Set"};
		for (int i = 0; i < sets.size(); i++) {
			if (sets.get(i).getName() == null || sets.get(i).getName().isEmpty() || sets.get(i).getName().equals("")) {
				setNameLabelList.get(i).setText(names[i]);

			} else {
				setNameLabelList.get(i).setText(sets.get(i).getName());
			}
		}
	}

	public static void mouseHoverEventColorChange(MouseEvent e,
	                                              ArrayList<JTextArea> setValuesTAList,
	                                              ArrayList<Color> colorsList) {
		//Get location of mouse on screen
		Point p = e.getLocationOnScreen();

		// For storing the colours of the pixel
		Color pixelColour;

		// for distorting current mouse point North-East and South-West
		Color pixelColourDistortedRight;
		Color pixelColourDistortedLeft;

		int distortion = 6;
		try {
			// Current mouse location's pixel's color
			pixelColour = getPixel(p.x, p.y);

			// for distorting current mouse point left and right
			// used to stop any colour changing functionality when mouse is on letters
			pixelColourDistortedRight = getPixel(p.x + distortion, p.y + distortion);
			pixelColourDistortedLeft = getPixel(p.x - distortion, p.y - distortion);
		} catch (AWTException ex) {
			ex.printStackTrace();
			return;
		}


		// For each value, setting it to change on hover,
		// or to change back when mouse has left the area
		for (int i = 0; i < setValuesTAList.size(); i++) {
			if (pixelColour.equals(colorsList.get(i))) {
				colourChangeOnHover(setValuesTAList.get(i));
			} else if (!pixelColourDistortedRight.equals(colorsList.get(i)) &&
					!pixelColourDistortedLeft.equals(colorsList.get(i))) {
				colourChangeBackOnHover(setValuesTAList.get(i));
			}
		}
	}


	public static void setVennTextAreasCharLimit(JTextArea textArea, int maxLettersLimit) {
		char[] charArr = textArea.getText().toCharArray();

		int counter = 0;
		if (charArr.length >= maxLettersLimit) {
			StringBuilder vennText = new StringBuilder();
			for (int i = 0; i < charArr.length; i++) {
				vennText.append(charArr[i]);
				// if max limit reached at that symbol
				if (i >= maxLettersLimit && charArr[i] == ',') {
					vennText.append(" <...>");
					break;
				} else if (i > maxLettersLimit) {
					do { // trying to delete chars up until comma
						vennText.deleteCharAt(i);
						i--;
						counter++;
						if (counter > 5) { // if it is a long string, just cut it
							vennText.append("...");
							break;
						}
					} while (vennText.charAt(i) != ',');
					vennText.append("...");
					break;
				}
			}

			textArea.setText(vennText.toString());
		}
	}

	public static Image requestImage(String url) {
		Image image = null;

		try {
			image = ImageIO.read(VennUtils.class.getResource(url));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}


	public static void colourChangeOnHover(JTextArea ta) {
		ta.setForeground(Styles.SELECTED_COLOR);
	}

	public static void colourChangeBackOnHover(JTextArea ta, JLabel toFindLabel) {
		if (!toFindLabel.getText().contains(ta.getText())) {
			ta.setForeground(Styles.NOT_SELECTED_COLOR);
		}
	}

	public static void colourChangeBackOnHover(JTextArea ta) {
		ta.setForeground(Styles.NOT_SELECTED_COLOR);
	}

	public static void colourChangeBackOnHover(JTextArea ta, JTextArea toFindArea) {

		if(ta.getText().matches("[1-6]")) {
			String toFind = new String(toFindArea.getText());

			toFind = toFind.replaceAll("16","")
					.replaceAll("15","")
					.replaceAll("14","")
					.replaceAll("13","")
					.replaceAll("12","")
					.replaceAll("11","")
					.replaceAll("10","");
			if(!toFind.contains(ta.getText())){
				ta.setForeground(Styles.NOT_SELECTED_COLOR);
			}

		}
		else if (!toFindArea.getText().contains(ta.getText())) {
			ta.setForeground(Styles.NOT_SELECTED_COLOR);
		}
	}





	/****************************************************
	 * Reference: https://stackoverflow.com/questions/17161587/how-to-center-align-text-in-jtextarea/49081289
	 * Username: paul
	 * Profile Link: https://stackoverflow.com/users/6315137/paul
	 ****************************************************/

	public static void centerText(JTextArea ta) {
		BufferedImage fake1 = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D fake2 = fake1.createGraphics();
		FontMetrics fm = fake2.getFontMetrics(ta.getFont());

		int lines = ta.getLineCount();
		ArrayList<String> list = new ArrayList<>();
		try {
			for (int i = 0; i < lines; i++) {
				int start = ta.getLineStartOffset(i);
				int end = ta.getLineEndOffset(i);

				String line = ta.getText(start, end - start).replace("\n", "");
				list.add(line.trim());
			}
		} catch (BadLocationException e) {
			System.out.println(e);
		}
		alignLines(list, fm, ta);
	}

	private static void alignLines(ArrayList<String> list, FontMetrics fm, JTextArea ta) {
		String leading = "      ";
		int longest = -1;
		for (String s : list) {
			if (fm.stringWidth(s) > longest)
				longest = fm.stringWidth(s);
		}
		for (int n = 0; n < list.size(); n++) {
			String s = list.get(n);
			if (fm.stringWidth(s) >= longest)
				continue;
			while (fm.stringWidth(s) < longest)
				s = ' ' + s + ' ';
			list.set(n, s);
		}
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			sb.append(leading).append(s).append('\n');
		}
		ta.setText(sb.toString());
	}


	/****************************************************
	 * End of reference
	 ****************************************************/
}
