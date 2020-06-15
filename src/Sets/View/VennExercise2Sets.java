package Sets.View;

import Sets.Controler.*;
import Sets.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static Sets.Controler.VennUtils.*;
import static Sets.View.Styles.*;


//https://stackoverflow.com/questions/22217148/get-pixel-color-on-screen-java
@SuppressWarnings("serial")
public class VennExercise2Sets extends JFrame {


	private static final int GUESS_TEXT_AREA_LIMIT = 300;
	private String venn2Url = "/Resources/Images/2VennColoursV2.png";

	private String guessTextAreaDescri = "Click on Venn Diagram. To find set  {1, 2, 3}\ntry typing A " + Symbols.union + " B";
	private String feedbackTextAreaDescri = "After clicking \"Check!\", here you will\nfind feedback!";

	private JFrame frame;
	private JPanel panel;
	private JLabel loadingLabel;
	private JButton backButton;
	private JPanel imagePanel;
	private JTextArea textArea1VennDiagram;
	private JTextArea textArea2VennDiagram;
	private JTextArea textArea3VennDiagram;
	private JTextArea UniversalSetNameTextArea;
	private JTextArea universalSetValuesTextArea;

	private static final Color color1 = new Color(239, 228, 176);
	private static final Color color2 = new Color(235, 121, 17);
	private static final Color color3 = new Color(93, 230, 191);
	private static final Color colorUniversalSet = new Color(195, 195, 195);

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
	private JLabel numbersToFindLabel;
	private JLabel explanationLabel;
	private JPanel numbersToFindPanel;

	private ArrayList<MixedSet> sets;
	private ArrayList<MixedSet> setsListOriginal;
	private MixedSet toFindSet;


	private JLabel setNameLabel1;
	private JLabel setNameLabel2;

	private ArrayList<JButton> buttonList;
	private ArrayList<JLabel> setNameLabelList;
	private ArrayList<JTextArea> setValuesTextAreaList;
	private final MouseMotionListener motionListener;
	private final MouseListener mouseExitListener;
	private final MouseListener mouseListener;

	private Dimension dimension;

	// CONSTRUCTOR
	public VennExercise2Sets(Dimension dimension) {
		super();

		if (dimension!= null){
			this.dimension = dimension;
		}

		guessTextArea.setDocument(new PlainDocumentLimited(GUESS_TEXT_AREA_LIMIT));
		sets = new ArrayList<>();
		setsListOriginal = new ArrayList<>();
		toFindSet = new MixedSet(false);
		toFindSet.setName("ClickedAreasSet");
		feedbackTextArea.setText(feedbackTextAreaDescri);
		guessTextArea.setText(guessTextAreaDescri);
		feedbackTextArea.setLineWrap(true);
		guessTextArea.setLineWrap(true);



		this.create();
		this.showFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setTitle("Venn Exercises 2 Sets");
		//frame.setResizable(false);
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

				colourChangeBackOnHover(textArea1VennDiagram, numbersToFindLabel);
				colourChangeBackOnHover(textArea2VennDiagram, numbersToFindLabel);
				colourChangeBackOnHover(textArea3VennDiagram, numbersToFindLabel);
				colourChangeBackOnHover(universalSetValuesTextArea, numbersToFindLabel);
			}
		};

		guessTextArea.setFont(FONT_MEDIUM);
		guessTextArea.setForeground(TEXT_AREA_COLOR);
		feedbackTextArea.setFont(FONT_MEDIUM);
		feedbackTextArea.setForeground(TEXT_AREA_COLOR);


		setNameLabel1.addMouseListener(mouseListener);
		setNameLabel2.addMouseListener(mouseListener);
		setNameLabel1.addMouseMotionListener(motionListener);
		setNameLabel2.addMouseMotionListener(motionListener);
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


		checkButton.addActionListener(e -> {
			String guess = guessTextArea.getText();

			if (guess == null || guess.equals("") || guess.equals(feedbackTextAreaDescri)) {
				PopUp.infoBox("Enter the formula to find selected areas", "VennExercise3.Click");
			}

			guess = SyntaxAnalyser.cutSpaces(guess);

			MixedSet A = sets.get(0);
			MixedSet B = sets.get(1);
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
						PopUp.infoBox("Wrong Syntax. For {1, 4} try entering \n A "
								+ Symbols.union + " U", "VennExercise.CheckButton");
						return;
				}
			}

			MixedSet guessSet = null;
			try {
				guessSet = Calculate.evaluate(conversionGuessToSets.toString());
			} catch (IncorrectInputSyntaxException ex) {
				ex.printStackTrace();
				return;
			}
			MixedSet universalBase = new MixedSet(false);
			universalBase.add(new IntRange(1, 4));
			UniversalSet universalSet = UniversalSet.getInstance(universalBase, 4, 1, 'N');
			guessSet.intersection(universalSet);


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
		numbersToFindLabel.setFont(FONT_MEDIUM_BOLD);
		explanationLabel.setFont(VENN_EXPLANATION_LABEL_FONT);
		explanationLabel.setForeground(VENN_EXPLANATION_LABEL_COLOR);
		numbersToFindLabel.setForeground(VENN_EXPLANATION_LABEL_COLOR);

		loadingLabel.setMinimumSize(LOADING_LABEL_MIN);
		loadingLabel.setPreferredSize(LOADING_LABEL_PREF);
		loadingLabel.setMaximumSize(LOADING_LABEL_MAX);

		numbersToFindLabel.setMinimumSize(NUMBERS_TO_FIND_LABEL_MIN);
		numbersToFindLabel.setPreferredSize(NUMBERS_TO_FIND_LABEL_PREF);
		numbersToFindLabel.setMaximumSize(NUMBERS_TO_FIND_LABEL_MAX);
		numbersToFindPanel.setPreferredSize(NUMBERS_TO_FIND_PANEL_PREF);
		numbersToFindPanel.setMaximumSize(NUMBERS_TO_FIND_PANEL_MAX);
		numbersToFindPanel.setMinimumSize(NUMBERS_TO_FIND_PANEL_MIN);

		explanationLabel.setMinimumSize(EXPLANATION_LABEL_MIN);
		explanationLabel.setPreferredSize(EXPLANATION_LABEL_PREF);
		explanationLabel.setMaximumSize(EXPLANATION_LABEL_MAX);


		backButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainView();
			}
		});

		hardButton.addActionListener(e -> {
				VennUtils.startVennExercises4(panel.getSize());
				frame.dispose();

		});

		mediumButton.addActionListener(e -> {
			VennUtils.startVennExercises3(panel.getSize());
			frame.dispose();
		});

	} // CONSTRUCTOR ENDS


	private void mouseClickedAction(MouseEvent e) {
		loadingLabel.setForeground(Color.BLACK);
		loadingLabel.setText("LOADING");


		//String testVal = "[0,4]";
		//SyntaxAnalyser.rangeToUniversalSet(testVal);

		MixedSet universalBase = new MixedSet(false);
		universalBase.add(new IntRange(1, 4));
		UniversalSet.getInstance(universalBase, 4, 1, 'N');

		Point p = e.getLocationOnScreen();
		try {
			MixedSet answ = null;
			System.out.println("");
			System.out.println("(p.x, p.y) = " + p.x + " : " + p.y);
			System.out.println("getPixel(p.x, p.y) = " + getPixel(p.x, p.y));
			System.out.println();

			Color pixelColor = getPixel(p.x, p.y);
			if (pixelColor.equals(color1)) {
				answ = sets.get(0).difference(sets.get(1));
				System.out.println("1  area clicked");
				setTextAreaValuesInVenn(answ, textArea1VennDiagram);
				answ.setName("Part1");
			} else if (pixelColor.equals(color2)) {
				System.out.println("2  area clicked");
				answ = sets.get(1).difference(sets.get(0));
				setTextAreaValuesInVenn(answ, textArea2VennDiagram);
				answ.setName("Part2");
			} else if (pixelColor.equals(color3)) {
				System.out.println("3  area clicked");
				answ = sets.get(0).intersection(sets.get(1));
				setTextAreaValuesInVenn(answ, textArea3VennDiagram);
				answ.setName("Part3");
			} else if (pixelColor.equals(colorUniversalSet)) {
				System.out.println("4  area clicked");
				answ = sets.get(0).union(sets.get(1)).complement();
				setTextAreaValuesInVenn(answ, universalSetValuesTextArea);
				answ.setName("U");
			} else {
				loadingLabel.setForeground(Color.RED);
				loadingLabel.setText("FAILED");
				return; // prevent null pointer
			}

			numbersToFindLabel.setText(toFindSet.toStringNoName(false));


		} catch (AWTException ex) {
			ex.printStackTrace();
		}
		reloadOriginalSetList(sets, setsListOriginal);
		loadingLabel.setText("LOADED");
		loadingLabel.setForeground(new Color(0, 131, 17));


	}

	private void mouseHoverAction(MouseEvent e) {

		Point p = e.getLocationOnScreen();

		try {
			Color pixelColour = getPixel(p.x, p.y);
			int distortion  = 6;
			Color pixelColourDistortedRight = getPixel(p.x+distortion, p.y+distortion);
			Color pixelColourDistortedLeft = getPixel(p.x-distortion, p.y-distortion);

			if (pixelColour.equals(color1) ) {
				colourChangeOnHover(textArea1VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color1) &&  !pixelColourDistortedLeft.equals(color1) ){
				colourChangeBackOnHover(textArea1VennDiagram, numbersToFindLabel);
			}
			if (pixelColour.equals(color2)) {
				colourChangeOnHover(textArea2VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color2) &&  !pixelColourDistortedLeft.equals(color2) ){
				colourChangeBackOnHover(textArea2VennDiagram, numbersToFindLabel);
			}
			if (pixelColour.equals(color3)) {
				colourChangeOnHover(textArea3VennDiagram);
			} else if (!pixelColourDistortedRight.equals(color3) &&  !pixelColourDistortedLeft.equals(color3) ){
				colourChangeBackOnHover(textArea3VennDiagram, numbersToFindLabel);
			}
			if (pixelColour.equals(colorUniversalSet)) {
				colourChangeOnHover(universalSetValuesTextArea);
			} else if (!pixelColourDistortedRight.equals(colorUniversalSet) &&  !pixelColourDistortedLeft.equals(colorUniversalSet) ){
				colourChangeBackOnHover(universalSetValuesTextArea, numbersToFindLabel);
			}

		} catch (AWTException ex) {
			ex.printStackTrace();
		}
	}


	private void setTextAreaValuesInVenn(MixedSet answ, JTextArea area) {
		if (numbersToFindLabel.getText().contains(area.getText())) {
			toFindSet = toFindSet.difference(answ);
		} else {
			numbersToFindLabel.setText(answ.toStringNoName(false));
			toFindSet = toFindSet.union(answ);
		}
	}


	private void setTextAreaVennTexts() {
		MixedSet answ1 = null;

		answ1 = sets.get(0).difference(sets.get(1));
		answ1.setName("Part1");
		textArea1VennDiagram.setText(SetUtils.setStringCutEnds(answ1));
		reloadOriginalSetList(sets, setsListOriginal);

		MixedSet answ2;
		answ2 = sets.get(1).difference(sets.get(0));
		answ2.setName("Part2");
		textArea2VennDiagram.setText(SetUtils.setStringCutEnds(answ2));
		reloadOriginalSetList(sets, setsListOriginal);

		MixedSet answ3;
		answ3 = sets.get(0).intersection(sets.get(1));
		answ3.setName("Part3");
		textArea3VennDiagram.setText(SetUtils.setStringCutEnds(answ3));
		reloadOriginalSetList(sets, setsListOriginal);

		MixedSet universalBase = new MixedSet(false);
		universalBase.add(new IntRange(1, 4));
		UniversalSet universalSet = UniversalSet.getInstance(universalBase, 4, 1, 'N');

		MixedSet answ4;
		answ4 = sets.get(0).union(sets.get(1)).complement();
		answ4.setName("U");
		UniversalSetNameTextArea.setText("U");
		universalSetValuesTextArea.setText(SetUtils.setStringCutEnds(answ4));


		textArea1VennDiagram.addMouseListener(mouseListener);
		textArea2VennDiagram.addMouseListener(mouseListener);
		textArea3VennDiagram.addMouseListener(mouseListener);
		UniversalSetNameTextArea.addMouseListener(mouseListener);
		universalSetValuesTextArea.addMouseListener(mouseListener);

		textArea1VennDiagram.addMouseMotionListener(motionListener);
		textArea2VennDiagram.addMouseMotionListener(motionListener);
		textArea3VennDiagram.addMouseMotionListener(motionListener);
		UniversalSetNameTextArea.addMouseMotionListener(motionListener);
		universalSetValuesTextArea.addMouseMotionListener(motionListener);

		setVennTextAreasCharLimit(textArea1VennDiagram, 104);
		setVennTextAreasCharLimit(textArea2VennDiagram, 104);
		setVennTextAreasCharLimit(textArea3VennDiagram, 90);
		//adjustVennTextAreasFont(textArea4VennDiagram, 1);


		//panel.addMouseListener(mouseListener);
		imagePanel.addMouseListener((mouseListener));
		imagePanel.addMouseMotionListener(motionListener);
		imagePanel.addMouseListener(mouseExitListener);
		reloadOriginalSetList(sets, setsListOriginal);


	}


	private VennExercise2Sets create() {
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
			panel.setPreferredSize(new Dimension(1303, 620));
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
		setNameLabel1.setFont(new Font("Serif", Font.PLAIN, 50));
		imagePanel.add(setNameLabel1);
		setNameLabel1.setLocation(183, 56);
		setNameLabel1.setSize(80, 50);
		setNameLabel1.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

		setNameLabel2 = new JLabel("SET B");
		setNameLabel2.setFont(new Font("Serif", Font.PLAIN, 50));
		imagePanel.add(setNameLabel2);
		setNameLabel2.setLocation(479, 56);
		setNameLabel2.setSize(80, 50);
		setNameLabel2.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

		UniversalSetNameTextArea = new JTextArea();
		configTextAreasInVennStyle(UniversalSetNameTextArea);
		imagePanel.add(UniversalSetNameTextArea);
		UniversalSetNameTextArea.setLocation(20, 6);
		UniversalSetNameTextArea.setSize(145, 100);
		UniversalSetNameTextArea.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);


		textArea1VennDiagram = new JTextArea();
		configTextAreasInVennStyle(textArea1VennDiagram);
		imagePanel.add(textArea1VennDiagram);
		textArea1VennDiagram.setLocation(72, 224);
		textArea1VennDiagram.setSize(170, 230);

		textArea2VennDiagram = new JTextArea();
		configTextAreasInVennStyle(textArea2VennDiagram);
		imagePanel.add(textArea2VennDiagram);
		textArea2VennDiagram.setLocation(594, 224);
		textArea2VennDiagram.setSize(170, 230);

		textArea3VennDiagram = new JTextArea();
		configTextAreasInVennStyle(textArea3VennDiagram);
		imagePanel.add(textArea3VennDiagram);
		textArea3VennDiagram.setLocation(358, 224);
		textArea3VennDiagram.setSize(145, 230);



		universalSetValuesTextArea = new JTextArea();
		configTextAreasInVennStyle(universalSetValuesTextArea);
		imagePanel.add(universalSetValuesTextArea);
		universalSetValuesTextArea.setLocation(20, 62);
		universalSetValuesTextArea.setSize(145, 100);
		universalSetValuesTextArea.setForeground(NOT_SELECTED_COLOR);

		VennUtils.centerText(UniversalSetNameTextArea);
		VennUtils.centerText(universalSetValuesTextArea);
		VennUtils.centerText(textArea1VennDiagram);
		VennUtils.centerText(textArea2VennDiagram);
		VennUtils.centerText(textArea3VennDiagram);


		checkButton = new CustomButton(new Color(117, 255, 140),
				new Color(105, 224, 121));
		backButton = new CustomButton(new Color(255, 197, 183),
				new Color(235, 178, 170));

		intersectionButton = new CustomButton(Styles.LIGHT_GREEN, Styles.DARK_GREEN);
		differenceButton = new CustomButton(Styles.LIGHT_GREEN, Styles.DARK_GREEN);
		easyButton = new CustomButton(new Color(117, 255, 140),
				new Color(105, 224, 121));
		hardButton = new CustomButton(Styles.LIGHT_GREEN, Styles.DARK_GREEN);
		unionButton = new CustomButton(Styles.LIGHT_GREEN, Styles.DARK_GREEN);
		mediumButton = new CustomButton(Styles.LIGHT_GREEN, Styles.DARK_GREEN);


		buttonList = new ArrayList<>();
		buttonList.add(intersectionButton);
		buttonList.add(checkButton);
		buttonList.add(hardButton);
		buttonList.add(mediumButton);
		buttonList.add(easyButton);
		buttonList.add(unionButton);
		buttonList.add(differenceButton);
		buttonList.add(backButton);

		for (int i = 0; i < buttonList.size(); i++) {
			//buttonList.get(i).setFont(FONT_MEDIUM);
			//buttonList.get(i).repaint();
		}

		//easyButton.setFont(FONT_MEDIUM_BOLD);


	}

	private void configTextAreasInVennStyle(JTextArea tArea) {
		tArea.setFont(new Font("Serif", Font.PLAIN, 50));
		tArea.setForeground(NOT_SELECTED_COLOR);
		tArea.setOpaque(false);
		tArea.setEditable(false);
		tArea.setLineWrap(true);
		tArea.setPreferredSize(new Dimension(100, 100));
	}


}

