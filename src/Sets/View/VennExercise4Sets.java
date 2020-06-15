package Sets.View;

import Sets.Controler.*;
import Sets.Model.*;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

import static Sets.Controler.VennUtils.*;
import static Sets.View.Styles.*;


//https://stackoverflow.com/questions/22217148/get-pixel-color-on-screen-java
@SuppressWarnings("serial")
public class VennExercise4Sets extends JFrame {


	private static final int GUESS_TEXT_AREA_LIMIT = 300;
	private String venn2Url = "/Resources/Images/4VennColoursV2.png";


	private String guessTextAreaDescri =  "Click on Venn Diagram.  To find set  {10}\ntry typing A "
			+ Symbols.intersection + " B " + Symbols.intersection + " C " + Symbols.intersection + " D.";
	private String textArea1Descri = "After clicking \"Check!\", here you will\nfind feedback!";

	private JFrame frame;
	private JPanel panel;
	private JLabel loadingLabel;
	private JButton backButton;
	private JPanel imagePanel;
	private JTextArea textArea1VennDiagram;
	private JTextArea textArea2VennDiagram;
	private JTextArea textArea3VennDiagram;
	private JTextArea textArea4VennDiagram;
	private JTextArea textArea5VennDiagram;
	private JTextArea textArea6VennDiagram;
	private JTextArea textArea7VennDiagram;
	private JTextArea textArea8VennDiagram;
	private JTextArea textArea9VennDiagram;
	private JTextArea textArea10VennDiagram;
	private JTextArea textArea11VennDiagram;
	private JTextArea textArea12VennDiagram;
	private JTextArea textArea13VennDiagram;
	private JTextArea textArea14VennDiagram;
	private JTextArea textArea15VennDiagram;

	private JTextArea universalSetValuesTextArea;

	private static final Color color1 = new Color(239, 192, 146);
	private static final Color color2 = new Color(255, 230, 204);
	private static final Color color3 = new Color(210, 242, 24);
	private static final Color color4 = new Color(145, 210, 94);
	private static final Color color5 = new Color(245, 208, 245);
	private static final Color color6 = new Color(177, 199, 238);
	private static final Color color7 = new Color(198, 255, 198);
	private static final Color color8 = new Color(212, 162, 244);
	private static final Color color9 = new Color(247, 175, 32);
	private static final Color color10 = new Color(247, 237, 22);
	private static final Color color11 = new Color(239, 247, 121);
	private static final Color color12 = new Color(59, 189, 68);
	private static final Color color13 = new Color(82, 205, 156);
	private static final Color color14 = new Color(140, 92, 223);
	private static final Color color15 = new Color(82, 135, 231);
	private Color colorUniversalSet = new Color(195, 195, 195);


	private JTextArea feedbackTextArea;
	private JTextArea guessTextArea;
	private JScrollPane scrollPaneTA1;
	private JScrollPane scrollPaneTA2;

	private JButton unionButton;
	private JButton differenceButton;
	private JButton intersectionButton;
	private JButton easyButton;
	private JButton hardButton;
	private JButton mediumButton;
	private JButton checkButton;
	private JTextArea numbersToFindArea;
	private JLabel explanationLabel;
	private JPanel numbersToFindPanel;

	private ArrayList<MixedSet> sets;
	private ArrayList<MixedSet> setsListOriginal;
	private MixedSet toFindSet;

	private JLabel setNameLabel1;
	private JLabel setNameLabel2;
	private JLabel setNameLabel3;
	private JLabel setNameLabel4;
	private JLabel universalSetNameLabel;


	private boolean labelSizeDecreased = false;

	private ArrayList<JButton> buttonList;
	private ArrayList<JLabel> setNameLabelList;
	private ArrayList<JTextArea> vennValuesTextAreasList;


	private final MouseMotionListener motionListener;
	private final MouseListener mouseExitListener;
	private final MouseListener mouseListener;

	private Dimension dimension;
	// CONSTRUCTOR
	public VennExercise4Sets(Dimension dimension) {
		super();

		this.dimension = dimension;
		JTextField title = new JTextField("spam");
		title.setBounds(80, 40, 225, 20);
		this.setTitle(title.getText());


		sets = new ArrayList<>();
		setsListOriginal = new ArrayList<>();
		toFindSet = new MixedSet(false);
		toFindSet.setName("ClickedAreasSet");
		feedbackTextArea.setText(textArea1Descri);
		guessTextArea.setDocument(new PlainDocumentLimited(GUESS_TEXT_AREA_LIMIT));
		guessTextArea.setText(guessTextAreaDescri);
		feedbackTextArea.setLineWrap(true);
		guessTextArea.setLineWrap(true);
		numbersToFindArea.setLineWrap(true);

		this.create();
		this.showFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//frame.setResizable(false);
		frame.setTitle("Venn Exercises 4 Sets");
		frame.pack();


		mouseListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				mouseClickedAction(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		};
		motionListener = new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseHoverAction(e);
			}
		};
		mouseExitListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

				java.awt.Point p = new java.awt.Point(e.getLocationOnScreen());
				SwingUtilities.convertPointFromScreen(p, e.getComponent());
				if (e.getComponent().contains(p)) {
					return;
				}

				for (int i = 0; i < vennValuesTextAreasList.size(); i++) {
					colourChangeBackOnHover(vennValuesTextAreasList.get(i), numbersToFindArea);
				}

			}
		};

		guessTextArea.setFont(FONT_MEDIUM);
		guessTextArea.setForeground(TEXT_AREA_COLOR);
		feedbackTextArea.setFont(FONT_MEDIUM);
		feedbackTextArea.setForeground(TEXT_AREA_COLOR);


		setNameLabel1.addMouseListener(mouseListener);
		setNameLabel2.addMouseListener(mouseListener);
		setNameLabel3.addMouseListener(mouseListener);
		setNameLabel4.addMouseListener(mouseListener);
		setNameLabel1.addMouseMotionListener(motionListener);
		setNameLabel2.addMouseMotionListener(motionListener);
		setNameLabel3.addMouseMotionListener(motionListener);
		setNameLabel4.addMouseMotionListener(motionListener);
		panel.addMouseMotionListener(motionListener);

		scrollPaneTA1.setPreferredSize(DIAGRAMS_EXERCISE_TEXTAREA_DIM);
		scrollPaneTA2.setPreferredSize(DIAGRAMS_EXERCISE_TEXTAREA_DIM);

		loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loadingLabel.setVerticalAlignment(SwingConstants.CENTER);
		pack();

		feedbackTextArea.setBackground(BACKGROUND_COLOR);
		guessTextArea.setBackground(BACKGROUND_COLOR);
		panel.setBackground(Color.white);

		loadingLabel.setFont(FONT_SMALL);


		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String guess = guessTextArea.getText();

				if (guess == null || guess.equals("") || guess.equals(textArea1Descri)) {
					PopUp.infoBox("Enter the formula to find selected areas", "VennExercise3.Click");
				}

				guess = SyntaxAnalyser.cutSpaces(guess);

				MixedSet A = sets.get(0);
				MixedSet B = sets.get(1);
				MixedSet C = sets.get(2);
				MixedSet D = sets.get(3);
				MixedSet U = UniversalSet.getInstance();

				StringBuilder conversionGuessToSets = new StringBuilder();
				char[] guessArr = guess.toCharArray();
				for (int i = 0; i < guessArr.length; i++) {
					switch (guessArr[i]) {
						case 'A':
							conversionGuessToSets.append(A.toString());
							break;
						case 'B':
							conversionGuessToSets.append(B.toString());
							break;
						case 'C':
							conversionGuessToSets.append(C.toString());
							break;
						case 'D':
							conversionGuessToSets.append(D.toString());
							break;
						case 'U':
							conversionGuessToSets.append(U.toString());
							break;
						case Symbols.union:
							conversionGuessToSets.append(Symbols.union);
							break;
						case Symbols.difference:
							conversionGuessToSets.append(Symbols.difference);
							break;
						case Symbols.intersection:
							conversionGuessToSets.append(Symbols.intersection);
							break;
						case '(':
							conversionGuessToSets.append("(");
							break;
						case ')':
							conversionGuessToSets.append(")");
							break;
						default:
							PopUp.infoBox("Wrong Syntax", "VennExercise.CheckButton");
							return;
					}
				}


				System.out.println("conversionGuessToSets = " + conversionGuessToSets);
				MixedSet guessSet = null;
				try {
					guessSet = Calculate.evaluate(conversionGuessToSets.toString());
				} catch (IncorrectInputSyntaxException ex) {
					ex.printStackTrace();
					return;
				}

				MixedSet universalBase = new MixedSet(false);
				universalBase.add(new IntRange(1, 16));
				UniversalSet universalSet = UniversalSet.getInstance(universalBase, 16, 1, 'N');
				guessSet.intersection(universalSet);

				System.out.println("guessSet.toStringNoName() = " + guessSet.toStringNoName(true));
				System.out.println("answerSet.toString() = " + toFindSet.toString());

				StringBuilder str = null;
				if (SetUtils.doesSetsContainsSameElements(guessSet, toFindSet)) {
					str = new StringBuilder("CORRECT\n");
					feedbackTextArea.setForeground(new Color(0, 144, 25));
					loadingLabel.setForeground(new Color(0, 131, 17));
					loadingLabel.setText("Correct");
				} else {
					feedbackTextArea.setForeground(new Color(231, 22, 0));
					str = new StringBuilder("INCORRECT\n");
					loadingLabel.setForeground(Color.RED);
					loadingLabel.setText("Wrong");
				}

				str.append("Set Found:\n");
				str.append(guessSet.toStringNoName(false));

				feedbackTextArea.setText(str.toString());

			}
		});
		unionButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (guessTextArea.getText().equals(guessTextAreaDescri)) {
					guessTextArea.setText("");
				}
				guessTextArea.requestFocus();
				guessTextArea.insert(String.valueOf(Symbols.union), guessTextArea.getCaretPosition());
			}
		});
		differenceButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (guessTextArea.getText().equals(guessTextAreaDescri)) {
					guessTextArea.setText("");
				}
				guessTextArea.requestFocus();
				guessTextArea.insert(String.valueOf(Symbols.difference), guessTextArea.getCaretPosition());
			}
		});
		intersectionButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (guessTextArea.getText().equals(guessTextAreaDescri)) {
					guessTextArea.setText("");
				}
				guessTextArea.requestFocus();
				guessTextArea.insert(String.valueOf(Symbols.intersection), guessTextArea.getCaretPosition());
			}
		});
		guessTextArea.addFocusListener(new FocusAdapter() {
			/**
			 * Invoked when a component gains the keyboard focus.
			 *
			 * @param e
			 */
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				if (guessTextAreaDescri.equals(guessTextArea.getText())) {
					guessTextArea.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				super.focusGained(e);
				if (guessTextArea.getText().equals("")) {
					guessTextArea.setText(guessTextAreaDescri);
				}
			}
		});


		loadingLabel.setFont(FONT_SMALL);
		numbersToFindArea.setFont(FONT_MEDIUM_BOLD);
		explanationLabel.setFont(VENN_EXPLANATION_LABEL_FONT);
		explanationLabel.setForeground(VENN_EXPLANATION_LABEL_COLOR);
		numbersToFindArea.setForeground(VENN_EXPLANATION_LABEL_COLOR);


		loadingLabel.setMinimumSize(LOADING_LABEL_MIN);
		loadingLabel.setPreferredSize(LOADING_LABEL_PREF);
		loadingLabel.setMaximumSize(LOADING_LABEL_MAX);


		numbersToFindArea.setMinimumSize(NUMBERS_TO_FIND_LABEL_MIN);
		numbersToFindArea.setPreferredSize(NUMBERS_TO_FIND_LABEL_PREF);
		numbersToFindArea.setMaximumSize(NUMBERS_TO_FIND_LABEL_MAX);
		numbersToFindPanel.setPreferredSize(NUMBERS_TO_FIND_PANEL_PREF);
		numbersToFindPanel.setMaximumSize(NUMBERS_TO_FIND_PANEL_MAX);
		numbersToFindPanel.setMinimumSize(NUMBERS_TO_FIND_PANEL_MIN);

		explanationLabel.setMinimumSize(EXPLANATION_LABEL_MIN);
		explanationLabel.setPreferredSize(EXPLANATION_LABEL_PREF);
		explanationLabel.setMaximumSize(EXPLANATION_LABEL_MAX);


		mediumButton.addActionListener( e -> {
				VennUtils.startVennExercises3(panel.getSize());
				frame.dispose();

		});
		backButton.addActionListener( e -> {
				frame.dispose();
				new MainView();

		});

		easyButton.addActionListener(e -> {
				VennUtils.startVennExercises2(panel.getSize());
				frame.dispose();
		});
	}


	private void mouseClickedAction(MouseEvent e) {
		loadingLabel.setForeground(Color.BLACK);
		loadingLabel.setText("LOADING");

		MixedSet universalBase = new MixedSet(false);
		universalBase.add(new IntRange(1, 16));
		UniversalSet.getInstance(universalBase, 16, 1, 'N');

		Point p = e.getLocationOnScreen();
		try {
			MixedSet answ = null;
			System.out.println("");
			System.out.println("(p.x, p.y) = " + p.x + " : " + p.y);
			System.out.println("getPixel(p.x, p.y) = " + getPixel(p.x, p.y));
			System.out.println();

			MixedSet A = sets.get(0);
			MixedSet B = sets.get(1);
			MixedSet C = sets.get(2);
			MixedSet D = sets.get(3);



			Color pixelColor = getPixel(p.x, p.y);
			if (pixelColor.equals(color1)) {
				answ = A.difference(B).difference(C).difference(D);
				System.out.println("1  area clicked");
				setTextAreaValuesInVenn(answ, textArea1VennDiagram);
				answ.setName("Part1");

			} else if (pixelColor.equals(color2)) {
				answ = B.difference(A).difference(D).difference(C);
				System.out.println("2  area clicked");
				setTextAreaValuesInVenn(answ, textArea2VennDiagram);
				answ.setName("Part2");

			} else if (pixelColor.equals(color3)) {
				answ = A.intersection(B).difference(C).difference(D);
				System.out.println("3  area clicked");
				setTextAreaValuesInVenn(answ, textArea3VennDiagram);
				answ.setName("Part3");

			} else if (pixelColor.equals(color4)) {
				answ = D.difference(C).difference(B).difference(A);
				System.out.println("4  area clicked");
				setTextAreaValuesInVenn(answ, textArea4VennDiagram);
				answ.setName("Part4");

			} else if (pixelColor.equals(color5)) {
				System.out.println("5  area clicked");
				answ = A.intersection(D).difference(C).difference(B);
				setTextAreaValuesInVenn(answ, textArea5VennDiagram);
				answ.setName("Part5");

			} else if (pixelColor.equals(color6)) {
				System.out.println("6  area clicked");
				answ = C.intersection(B).difference(A).difference(D);
				setTextAreaValuesInVenn(answ, textArea6VennDiagram);
				answ.setName("Part6");

			} else if (pixelColor.equals(color7)) {
				answ = C.difference(A).difference(B).difference(D);
				System.out.println("7  area clicked");
				setTextAreaValuesInVenn(answ, textArea7VennDiagram);
				answ.setName("Part7");

			} else if (pixelColor.equals(color8)) {
				System.out.println("8  area clicked");
				answ = D.intersection(B).intersection(A).difference(C);
				setTextAreaValuesInVenn(answ, textArea8VennDiagram);
				answ.setName("Part8");

			} else if (pixelColor.equals(color9)) {
				answ = C.intersection(A).intersection(B).difference(D);
				System.out.println("9  area clicked");
				setTextAreaValuesInVenn(answ, textArea9VennDiagram);
				answ.setName("Part9");


			} else if (pixelColor.equals(color10)) {
				System.out.println("10  area clicked");
				answ = A.intersection(B).intersection(C).intersection(D);
				setTextAreaValuesInVenn(answ, textArea10VennDiagram);
				answ.setName("Part10");

			} else if (pixelColor.equals(color11)) {
				answ = B.intersection(D).difference(A).difference(C);
				System.out.println("11  area clicked");
				setTextAreaValuesInVenn(answ, textArea11VennDiagram);
				answ.setName("Part11");

			} else if (pixelColor.equals(color12)) {
				System.out.println("12  area clicked");
				answ = A.intersection(C).difference(B).difference(D);
				setTextAreaValuesInVenn(answ, textArea12VennDiagram);
				answ.setName("Part12");

			} else if (pixelColor.equals(color13)) {
				answ = D.intersection(C).intersection(B).difference(A);
				System.out.println("13  area clicked");
				setTextAreaValuesInVenn(answ, textArea13VennDiagram);
				answ.setName("Part13");
			} else if (pixelColor.equals(color14)) {
				System.out.println("14  area clicked");
				answ = D.intersection(C).intersection(A).difference(B);
				setTextAreaValuesInVenn(answ, textArea14VennDiagram);
				answ.setName("Part14");

			} else if (pixelColor.equals(color15)) {
				answ = D.intersection(C).difference(A).difference(B);
				System.out.println("15  area clicked");
				setTextAreaValuesInVenn(answ, textArea15VennDiagram);
				answ.setName("Part15");


			} else if (pixelColor.equals(colorUniversalSet)) {
				answ = A.union(B).union(C).union(D).complement();
				System.out.println("16  area clicked");
				setTextAreaValuesInVenn(answ, universalSetValuesTextArea);
				answ.setName("U");


			} else {
				loadingLabel.setForeground(Color.RED);
				loadingLabel.setText("FAILED");
				return; // prevent null pointer
			}

			numbersToFindArea.setText(toFindSet.toStringNoName(false));

			int charLimit = 28;
			decreaseTextSizeIfLimitReached(numbersToFindArea, charLimit);


		} catch (AWTException ex) {
			reloadOriginalSetList(sets, setsListOriginal);
			ex.printStackTrace();
		}
		reloadOriginalSetList(sets, setsListOriginal);
		loadingLabel.setText("LOADED");
		loadingLabel.setForeground(new Color(0, 131, 17));


		//=====================================
		// Allows ctrl+z as undo and ctrl+y as redo
		// ADAPTED from:
		// https://stackoverflow.com/questions/12030836/undo-functionality-in-jtextarea
		// Viewed on Feb 18, 2020
		// Answer by: slartidan, who's profile can be found at https://stackoverflow.com/users/476791/slartidan

		UndoManager undoManager = new UndoManager();

		Document doc = guessTextArea.getDocument();
		doc.addUndoableEditListener(ev -> {
			undoManager.addEdit(ev.getEdit());
		});

		InputMap im = guessTextArea.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = guessTextArea.getActionMap();

		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "redo");
		// Java 10
		//im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "Undo");
		//im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()), "Redo");

		am.put("Undo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (undoManager.canUndo()) {
						undoManager.undo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});
		am.put("Redo", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (undoManager.canRedo()) {
						undoManager.redo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});


		//=====================================

	}

	private void mouseHoverAction(MouseEvent e) {

		Point p = e.getLocationOnScreen();

		try {
			Color pixelColour = getPixel(p.x, p.y);
			int distortion = 6;
			Color pixelColourDistortedRight = getPixel(p.x + distortion, p.y + distortion);
			Color pixelColourDistortedLeft = getPixel(p.x - distortion, p.y - distortion);

			if (pixelColour.equals(color1)) {
				colourChangeOnHover(textArea1VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color1) && !pixelColourDistortedLeft.equals(color1)) {
				colourChangeBackOnHover(textArea1VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color2)) {
				colourChangeOnHover(textArea2VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color2) && !pixelColourDistortedLeft.equals(color2)) {
				colourChangeBackOnHover(textArea2VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color3)) {
				colourChangeOnHover(textArea3VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color3) && !pixelColourDistortedLeft.equals(color3)) {
				colourChangeBackOnHover(textArea3VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color4)) {
				colourChangeOnHover(textArea4VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color4) && !pixelColourDistortedLeft.equals(color4)) {
				colourChangeBackOnHover(textArea4VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color5)) {
				colourChangeOnHover(textArea5VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color5) && !pixelColourDistortedLeft.equals(color5)) {
				colourChangeBackOnHover(textArea5VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color6)) {
				colourChangeOnHover(textArea6VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color6) && !pixelColourDistortedLeft.equals(color6)) {
				colourChangeBackOnHover(textArea6VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color7)) {
				colourChangeOnHover(textArea7VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color7) && !pixelColourDistortedLeft.equals(color7)) {
				colourChangeBackOnHover(textArea7VennDiagram, numbersToFindArea);
			}


			if (pixelColour.equals(color8)) {
				colourChangeOnHover(textArea8VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color8) && !pixelColourDistortedLeft.equals(color8)) {
				colourChangeBackOnHover(textArea8VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color9)) {
				colourChangeOnHover(textArea9VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color9) && !pixelColourDistortedLeft.equals(color9)) {
				colourChangeBackOnHover(textArea9VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color10)) {
				colourChangeOnHover(textArea10VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color10) && !pixelColourDistortedLeft.equals(color10)) {
				colourChangeBackOnHover(textArea10VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color11)) {
				colourChangeOnHover(textArea11VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color11) && !pixelColourDistortedLeft.equals(color11)) {
				colourChangeBackOnHover(textArea11VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color12)) {
				colourChangeOnHover(textArea12VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color12) && !pixelColourDistortedLeft.equals(color12)) {
				colourChangeBackOnHover(textArea12VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color13)) {
				colourChangeOnHover(textArea13VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color13) && !pixelColourDistortedLeft.equals(color13)) {
				colourChangeBackOnHover(textArea13VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color14)) {
				colourChangeOnHover(textArea14VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color14) && !pixelColourDistortedLeft.equals(color14)) {
				colourChangeBackOnHover(textArea14VennDiagram, numbersToFindArea);
			}
			if (pixelColour.equals(color15)) {
				colourChangeOnHover(textArea15VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color15) && !pixelColourDistortedLeft.equals(color15)) {
				colourChangeBackOnHover(textArea15VennDiagram, numbersToFindArea);
			}

			if (pixelColour.equals(colorUniversalSet)) {
				colourChangeOnHover(universalSetValuesTextArea);
			} else if (!pixelColourDistortedRight.equals(colorUniversalSet) && !pixelColourDistortedLeft.equals(colorUniversalSet)) {
				colourChangeBackOnHover(universalSetValuesTextArea, numbersToFindArea);
			}

		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}

	private void setTextAreaValuesInVenn(MixedSet answ, JTextArea area) {
		//if (numbersToFindArea.getText().contains(area.getText())) {
		double value;
		Iterator it = answ.iterator();
		value = (double) it.next();
		if (toFindSet.contains(value)) {
			toFindSet = toFindSet.difference(answ);
		} else {
			numbersToFindArea.setText(answ.toStringNoName(false));
			toFindSet = toFindSet.union(answ);
		}

	}

	private void setTextAreaVennTexts() {
		MixedSet A = sets.get(0);
		MixedSet B = sets.get(1);
		MixedSet C = sets.get(2);
		MixedSet D = sets.get(3);

		/*  (A) (B)
		 *  (D) (C)
		 */


		MixedSet universalBase = new MixedSet(false);
		universalBase.add(new IntRange(1, 16));
		UniversalSet.getInstance(universalBase, 16, 1, 'N');


		MixedSet answ1 = null;
		answ1 = A.difference(B).difference(C).difference(D);
		answ1.setName("Part1");
		textArea1VennDiagram.setText(SetUtils.setStringCutEnds(answ1));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);
		System.out.println("sets.toString() = " + sets.toString());

		MixedSet answ2;
		answ2 = B.difference(A).difference(D).difference(C);
		answ2.setName("Part2");
		textArea2VennDiagram.setText(SetUtils.setStringCutEnds(answ2));
		System.out.println("answ2.toString() = " + answ2.toString());
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ3;
		answ3 = A.intersection(B).difference(C).difference(D);
		answ3.setName("Part3");
		textArea3VennDiagram.setText(SetUtils.setStringCutEnds(answ3));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ4;
		answ4 = D.difference(C).difference(B).difference(A);
		answ4.setName("Part4");
		textArea4VennDiagram.setText(SetUtils.setStringCutEnds(answ4));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);



		MixedSet answ5;
		answ5 = A.intersection(D).difference(C).difference(B);
		answ5.setName("Part5");
		textArea5VennDiagram.setText(SetUtils.setStringCutEnds(answ5));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);


		MixedSet answ6;
		answ6 = C.intersection(B).difference(A).difference(D);
		answ6.setName("Part6");
		textArea6VennDiagram.setText(SetUtils.setStringCutEnds(answ6));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ7;
		answ7 = C.difference(A).difference(B).difference(D);
		answ7.setName("Part7");
		textArea7VennDiagram.setText(SetUtils.setStringCutEnds(answ7));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);


		MixedSet answ8;
		answ8 = D.intersection(B).intersection(A).difference(C);
		answ8.setName("Part8");
		textArea8VennDiagram.setText(SetUtils.setStringCutEnds(answ8));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ9;
		answ9 = C.intersection(A).intersection(B).difference(D);
		answ9.setName("Part9");
		textArea9VennDiagram.setText(SetUtils.setStringCutEnds(answ9));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ10;
		answ10 = A.intersection(B).intersection(C).intersection(D);
		answ10.setName("Part10");
		textArea10VennDiagram.setText(SetUtils.setStringCutEnds(answ10));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);


		MixedSet answ11;
		answ11 = B.intersection(D).difference(A).difference(C);
		answ11.setName("Part11");
		textArea11VennDiagram.setText(SetUtils.setStringCutEnds(answ11));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ12;
		answ12 = A.intersection(C).difference(B).difference(D);
		answ12.setName("Part12");
		textArea12VennDiagram.setText(SetUtils.setStringCutEnds(answ12));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ13;
		answ13 = D.intersection(C).intersection(B).difference(A);
		answ13.setName("Part13");
		textArea13VennDiagram.setText(SetUtils.setStringCutEnds(answ13));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ14;
		answ14 = D.intersection(C).intersection(A).difference(B);
		answ14.setName("Part14");
		textArea14VennDiagram.setText(SetUtils.setStringCutEnds(answ14));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ15;
		answ15 = D.intersection(C).difference(A).difference(B);
		answ15.setName("Part15");
		textArea15VennDiagram.setText(SetUtils.setStringCutEnds(answ15));
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);


		MixedSet answ16;
		answ16 = A.union(B).union(C).union(D).complement();
		answ16.setName("U");
		universalSetNameLabel.setText("U");
		universalSetValuesTextArea.setText(SetUtils.setStringCutEnds(answ16));

		reloadOriginalSetList(sets, setsListOriginal);


		for (int i = 0; i < vennValuesTextAreasList.size(); i++) {
			vennValuesTextAreasList.get(i).addMouseListener(mouseListener);
			vennValuesTextAreasList.get(i).addMouseMotionListener(motionListener);
		}

		for (int i = 0; i < setNameLabelList.size(); i++) {
			setNameLabelList.get(i).addMouseListener(mouseListener);
			setNameLabelList.get(i).addMouseMotionListener(motionListener);
		}

		imagePanel.addMouseListener((mouseListener));
		imagePanel.addMouseListener(mouseExitListener);
		imagePanel.addMouseMotionListener(motionListener);


	}


	private VennExercise4Sets create() {
		frame = createFrame();
		frame.setContentPane(panel);
		createContent();
		return this;
	}

	private JFrame createFrame() {
		JFrame frame = new JFrame(getClass().getName());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		if(dimension!= null){
			panel.setPreferredSize(dimension);
		}
		else {
			panel.setPreferredSize(new Dimension(1300, 618));
		}
		return frame;
	}

	private void showFrame() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	private Component createContent() {
		return panel;
	}

	public void setSets(ArrayList<MixedSet> sets) {
		this.sets = sets;
		for (int i = 0; i < sets.size(); i++) {
			MixedSet set = sets.get(i);
			MixedSet deepCopySet = SetUtils.deepCopy(set);
			setsListOriginal.add(deepCopySet);
		}

		setTextAreaVennTexts();

		setNameLabel1.setText("A");
		setNameLabel2.setText("B");
		setNameLabel3.setText("C");
		setNameLabel4.setText("D");

		revalidate();
		repaint();
	}

	private void createUIComponents() {


		final Image image = VennUtils.requestImage(venn2Url);
		imagePanel = new JPanel() {
			//____________________________________________________
			// The following code centers the image in the JPanel
			// Reference
			// https://stackoverflow.com/questions/4533526/how-to-center-align-background-image-in-jpanel
			// Viewed at 23, April, 2020
			// By user trashgod at https://stackoverflow.com/users/230513/trashgod
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
				g2d.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);
				g2d.drawImage(image, 0, 0, null);
			}
			//____________________________________________________
		};

		imagePanel.setLayout(null);


		setNameLabel1 = new JLabel("SET A");
		setNameLabel1.setFont(new Font("Serif", Font.PLAIN, 40));
		imagePanel.add(setNameLabel1);
		setNameLabel1.setLocation(196, 30);
		setNameLabel1.setSize(80, 50);
		setNameLabel1.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

		setNameLabel2 = new JLabel("SET B");
		setNameLabel2.setFont(new Font("Serif", Font.PLAIN, 40));
		imagePanel.add(setNameLabel2);
		setNameLabel2.setLocation(571, 30);
		setNameLabel2.setSize(80, 50);
		setNameLabel2.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

		setNameLabel3 = new JLabel("SET C");
		setNameLabel3.setFont(new Font("Serif", Font.PLAIN, 40));
		imagePanel.add(setNameLabel3);
		setNameLabel3.setLocation(710, 165);
		setNameLabel3.setSize(80, 50);
		setNameLabel3.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

		setNameLabel4 = new JLabel("SET D");
		setNameLabel4.setFont(new Font("Serif", Font.PLAIN, 40));
		imagePanel.add(setNameLabel4);
		setNameLabel4.setLocation(55, 165);
		setNameLabel4.setSize(80, 50);
		setNameLabel4.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

		universalSetNameLabel = new JLabel();
		universalSetNameLabel.setFont(new Font("Serif", Font.PLAIN, 40));
		imagePanel.add(universalSetNameLabel);
		universalSetNameLabel.setLocation(20, 22);
		universalSetNameLabel.setSize(80, 50);
		universalSetNameLabel.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);


		setNameLabelList = new ArrayList<>();
		setNameLabelList.add(setNameLabel1);
		setNameLabelList.add(setNameLabel2);
		setNameLabelList.add(setNameLabel3);
		setNameLabelList.add(setNameLabel4);
		setNameLabelList.add(universalSetNameLabel);

		textArea1VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea1VennDiagram);
		textArea1VennDiagram.setLocation(220, 80);
		textArea1VennDiagram.setSize(150, 80);

		textArea2VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea2VennDiagram);
		textArea2VennDiagram.setLocation(503, 80);
		textArea2VennDiagram.setSize(150, 90);

		textArea3VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea3VennDiagram);
		textArea3VennDiagram.setLocation(394, 178);
		textArea3VennDiagram.setSize(140, 90);

		textArea4VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea4VennDiagram);
		textArea4VennDiagram.setLocation(50, 230);
		textArea4VennDiagram.setSize(110, 100);

		textArea5VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea5VennDiagram);
		textArea5VennDiagram.setLocation(170, 180);
		textArea5VennDiagram.setSize(65, 45);

		textArea6VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea6VennDiagram);
		textArea6VennDiagram.setLocation(600, 180);
		textArea6VennDiagram.setSize(65, 45);

		textArea7VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea7VennDiagram);
		textArea7VennDiagram.setLocation(675, 230);
		textArea7VennDiagram.setSize(110, 100);


		textArea8VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea8VennDiagram);
		textArea8VennDiagram.setLocation(256, 243);
		textArea8VennDiagram.setSize(71, 43);


		textArea9VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea9VennDiagram);
		textArea9VennDiagram.setLocation(500, 243);
		textArea9VennDiagram.setSize(71, 43);


		textArea10VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea10VennDiagram);
		textArea10VennDiagram.setLocation(386, 330);
		textArea10VennDiagram.setSize(70, 45);

		textArea11VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea11VennDiagram);
		textArea11VennDiagram.setLocation(255, 340);
		textArea11VennDiagram.setSize(70, 45);

		textArea12VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea12VennDiagram);
		textArea12VennDiagram.setLocation(510, 340);
		textArea12VennDiagram.setSize(78, 45);


		textArea13VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea13VennDiagram);
		textArea13VennDiagram.setLocation(332, 377);
		textArea13VennDiagram.setSize(50, 30);


		textArea14VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea14VennDiagram);
		textArea14VennDiagram.setLocation(438, 377);
		textArea14VennDiagram.setSize(50, 30);

		textArea15VennDiagram = new JTextArea();
		configTextAreasInVenn(textArea15VennDiagram);
		textArea15VennDiagram.setLocation(386, 445);
		textArea15VennDiagram.setSize(120, 65);

		universalSetValuesTextArea = new JTextArea();
		configTextAreasInVenn(universalSetValuesTextArea);
		universalSetValuesTextArea.setLocation(20, 62);
		universalSetValuesTextArea.setSize(145, 100);

		vennValuesTextAreasList = new ArrayList<>();
		vennValuesTextAreasList.add(universalSetValuesTextArea);
		vennValuesTextAreasList.add(textArea1VennDiagram);
		vennValuesTextAreasList.add(textArea2VennDiagram);
		vennValuesTextAreasList.add(textArea3VennDiagram);
		vennValuesTextAreasList.add(textArea4VennDiagram);
		vennValuesTextAreasList.add(textArea5VennDiagram);
		vennValuesTextAreasList.add(textArea6VennDiagram);
		vennValuesTextAreasList.add(textArea7VennDiagram);
		vennValuesTextAreasList.add(textArea8VennDiagram);
		vennValuesTextAreasList.add(textArea9VennDiagram);
		vennValuesTextAreasList.add(textArea10VennDiagram);
		vennValuesTextAreasList.add(textArea11VennDiagram);
		vennValuesTextAreasList.add(textArea12VennDiagram);
		vennValuesTextAreasList.add(textArea13VennDiagram);
		vennValuesTextAreasList.add(textArea14VennDiagram);
		vennValuesTextAreasList.add(textArea15VennDiagram);

		for (int i = 0; i < vennValuesTextAreasList.size(); i++) {
			VennUtils.centerText(vennValuesTextAreasList.get(i));
		}

		checkButton = new CustomButton(new Color(117, 255, 140),
				new Color(105, 224, 121));
		backButton = new CustomButton(new Color(255, 197, 183),
				new Color(235, 178, 170));

		intersectionButton = new CustomButton(LIGHT_GREEN, DARK_GREEN);
		differenceButton = new CustomButton(LIGHT_GREEN, DARK_GREEN);
		easyButton = new CustomButton(LIGHT_GREEN, DARK_GREEN);
		mediumButton = new CustomButton(LIGHT_GREEN, DARK_GREEN);
		unionButton = new CustomButton(LIGHT_GREEN, DARK_GREEN);
		hardButton = new CustomButton(new Color(117, 255, 140),
				new Color(105, 224, 121));


		buttonList = new ArrayList<>();
		buttonList.add(intersectionButton);
		buttonList.add(checkButton);
		buttonList.add(hardButton);
		buttonList.add(mediumButton);
		buttonList.add(easyButton);
		buttonList.add(unionButton);
		buttonList.add(differenceButton);
		buttonList.add(backButton);


	}

	private void configTextAreasInVenn(JTextArea tArea) {

		tArea.setFont(new Font("Serif", Font.PLAIN, 28));
		tArea.setForeground(NOT_SELECTED_COLOR);
		tArea.setOpaque(false);
		tArea.setEditable(false);
		tArea.setLineWrap(true);
		tArea.setPreferredSize(new Dimension(50, 50));
		imagePanel.add(tArea);
	}

	private void decreaseTextSizeIfLimitReached(JTextArea component, int charLimit) {
		int amountToDecrease = 4;

		if (!labelSizeDecreased && component.getText().length() >= charLimit) {
			int newSize = component.getFont().getSize() - amountToDecrease;
			Font font = new Font("Monospaced", Font.BOLD, newSize);
			component.setFont(font);
			component.repaint();
			labelSizeDecreased = true;
		} else if (labelSizeDecreased && component.getText().length() < charLimit) {
			int newSize = component.getFont().getSize() + amountToDecrease;
			Font font = new Font("Monospaced", Font.BOLD, newSize);
			component.setFont(font);
			component.repaint();
			labelSizeDecreased = false;
		}

	}


}

