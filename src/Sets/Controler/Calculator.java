package Sets.Controler;

import Sets.Model.*;
import Sets.View.CustomButton;
import Sets.View.MainView;
import Sets.Model.PlainDocumentLimited;
import Sets.View.PopUp;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Calculator {

	private static final int UNIVERSAL_SET_TEXTFIELD_LIMIT = 15;
	private static final int INPUT_TEXT_AREA_LIMIT = 1000;


	private JPanel panel1;
	private JButton calculateButton;
	private JTextArea outputTextArea;
	private JButton backButton;
	private JTextArea inputTextArea;
	private JButton vennDiagramsFromStorageButton;
	private JButton unionButton;
	private JButton belongsButton;
	private JButton differenceButton;
	private JButton productButton;
	private JButton intersectionButton;
	private JButton exampleButton;
	private JButton clearButton;
	private JButton saveToStorageButton;
	private JButton getFromStorageButton;
	private JScrollPane storageScrollPane;
	private JPanel storagePanel;
	private JButton complementButton;
	private JTextField universalSetTextField;
	private JButton infinityButton;
	private JButton deleteButton1;
	private JButton powerButton;
	private JLabel cardinalityLabel;
	private JButton weakSubsetButton;
	private JButton properSubsetButton;
	private JButton outputToStorageButton;
	private JButton tranferOutputToInputButton;
	private JRadioButton qRadioButton;
	private JRadioButton nRadioButton;
	private JRadioButton zRadioButton;
	private JButton showVennFromInputButton;
	private JScrollPane outputScrollPane;
	private JScrollPane inputScrollPane;
	private Storage storage = new Storage();
	private java.util.List<JCheckBox> checkBoxes = new ArrayList<>();

	private UndoManager undoManager;


	public Calculator() {
		JFrame frame = new JFrame();
		frame.setTitle("Sets Calculator");
		frame.setContentPane(panel1);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		inputTextArea.setLineWrap(true);
		outputTextArea.setLineWrap(true);

		storagePanel.setPreferredSize(storageScrollPane.getSize());
		storagePanel.setLayout(new GridLayout(10, 1));
//        storageTextArea.setLineWrap(true);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double sizeCoefficient = 0.85;
		//	frame.setPreferredSize((new Dimension ((int) (screenSize.width * sizeCoefficient), (int) (screenSize.height * sizeCoefficient))));
		frame.setSize((int) (screenSize.width * sizeCoefficient), (int) (screenSize.height * sizeCoefficient));
		frame.setLocationRelativeTo(null);


		outputScrollPane.setPreferredSize(new Dimension((int) (screenSize.width * sizeCoefficient / 3 * 2), (int) (screenSize.height * sizeCoefficient / 8)));
		inputScrollPane.setPreferredSize(new Dimension((int) (screenSize.width * sizeCoefficient / 3 * 2), (int) (screenSize.height * sizeCoefficient / 8)));

		zRadioButton.setSelected(true);

		universalSetTextField.setDocument(new PlainDocumentLimited(UNIVERSAL_SET_TEXTFIELD_LIMIT));
		universalSetTextField.setText("[-99, 100)");
		inputTextArea.setDocument(new PlainDocumentLimited(INPUT_TEXT_AREA_LIMIT));

		// LISTENERS_____________
		{
			calculateButton.addActionListener(new ActionListener() {
				/**
				 * Invoked when an action occurs.
				 *
				 * @param e the event to be processed
				 */
				@Override
				public void actionPerformed(ActionEvent e) {

					LostValuesToUInteresection.createFirst();

					try {
						setupUniversalSet();
					} catch (IncorrectInputSyntaxException ex) {
						ex.printStackTrace();
						return;
					}
					System.out.println("UniversalSet.getType() 1= " + UniversalSet.getType());
					String expression = inputTextArea.getText();

					if (expression == null || expression.equals("")) {
						PopUp.infoBox("Incorrect Input", "Calculator OnClick");
						return;
					}
					MixedSet answer = null;
					try {
						answer = Calculate.evaluate(expression);
					} catch (IncorrectInputSyntaxException ex) {
						ex.printStackTrace();
						return;
					}
					System.out.println("UniversalSet.getType() 2= " + UniversalSet.getType());
					if (answer == null) {
						return;
					}
					switch (answer.toString()) {
						case "{true}":
							outputTextArea.setText("true");
							cardinalityLabel.setText("NaN");
							break;
						case "{false}":
							outputTextArea.setText("false");
							cardinalityLabel.setText("NaN");
							break;
						default:
							outputTextArea.setText(answer.toString());
							cardinalityLabel.setText(Integer.toString(answer.getMultiplicity()));
							break;
					}
					inputTextArea.requestFocus();
					showLostValuesToUIntersection();
				}



			});


			vennDiagramsFromStorageButton.addActionListener(new ActionListener() {
				/**
				 * Invoked when an action occurs.
				 *
				 * @param e the event to be processed
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					LostValuesToUInteresection.createFirst();

					ArrayList<MixedSet> setList = new ArrayList<>();

					try {
						setupUniversalSet();
					} catch (IncorrectInputSyntaxException ex) {
						ex.printStackTrace();
						return;
					}

					for (JCheckBox c : checkBoxes) {
						if (c.isSelected()) {
							MixedSet set = null;
							try {
								set = Calculate.evaluate(c.getText());
							} catch (IncorrectInputSyntaxException ex) {
								ex.printStackTrace();
								return;
							}
							setList.add(set);

						}
					}
					if (setList.isEmpty()) {
						PopUp.infoBox("To use this feature, add some sets to storage and\n" +
								"tick the ones that should be displayed", "Venn Diagrams Storage Button");
						inputTextArea.requestFocus();
					} else {
						VennDiagramOpener.openVennDiagrams(setList);
					}
					showLostValuesToUIntersection();

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
					inputTextArea.insert(String.valueOf(Symbols.union), inputTextArea.getCaretPosition());
					inputTextArea.requestFocus();
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
					inputTextArea.insert(String.valueOf(Symbols.intersection), inputTextArea.getCaretPosition());
					inputTextArea.requestFocus();
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
					inputTextArea.insert(String.valueOf(Symbols.difference), inputTextArea.getCaretPosition());
					inputTextArea.requestFocus();
				}
			});
			productButton.addActionListener(new ActionListener() {
				/**
				 * Invoked when an action occurs.
				 *
				 * @param e the event to be processed
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					inputTextArea.insert(String.valueOf(Symbols.product),
							inputTextArea.getCaretPosition());
					inputTextArea.requestFocus();
				}
			});


			exampleButton.addActionListener(new ActionListener() {
				/**
				 * Invoked when an action occurs.
				 *
				 * @param e the event to be processed
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					inputTextArea.setText(storage.getExample());
					inputTextArea.requestFocus();
				}
			});
			clearButton.addActionListener(new ActionListener() {
				/**
				 * Invoked when an action occurs.
				 *
				 * @param e the event to be processed
				 */
				@Override
				public void actionPerformed(ActionEvent e) {
					inputTextArea.setText("");
					inputTextArea.requestFocus();
				}
			});
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
		}
		getFromStorageButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean inputChanged = false;
				for (int i = 0; i < checkBoxes.size(); i++) {
					if (checkBoxes.get(i).isSelected()) {
						inputTextArea.insert("   " + (checkBoxes.get(i).getText()),
								inputTextArea.getCaretPosition());
						inputChanged = true;
					}
				}
				if (!inputChanged){
						PopUp.infoBox("This button puts the selected sets into input area.\n" +
										"There must be some selected sets in storage.",
								"Get From Storage Button");
						return;
				}
				inputTextArea.requestFocus();
			}
		});

		saveToStorageButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = inputTextArea.getText();
				if (input.isEmpty()){
					PopUp.infoBox("Write down some sets in the input area\n" +
							"and click this button to save them to storage", "Save to Storage Button");
					return;
				}

				SetComprehension.setIntersectWithUnviersalSet(false);

				try {
					setupUniversalSet();
				} catch (IncorrectInputSyntaxException ex) {
					SetComprehension.setIntersectWithUnviersalSet(true);
					ex.printStackTrace();
					return;
				}



                ArrayList<MixedSet> setsList = null;
                try {
                    setsList = Calculate.strExprToListOfSets(input);
                } catch (IncorrectInputSyntaxException ex) {
	                SetComprehension.setIntersectWithUnviersalSet(true);
	                ex.printStackTrace();
                    return;
                }

                //for (int i = 0; i < setsList.size(); i++) {
					//can set the names here
				//}


				for (int i = 0; i < setsList.size(); i++) {
					String setToStr = setsList.get(i).toStringNoIntersectionWithUniversalSet();
					JCheckBox cb = new JCheckBox(setToStr);
					if (checkBoxes.size() < 10) {
						storage.addSet(setsList.get(i));
						checkBoxes.add(cb);
						storagePanel.add(cb);
					} else {
						PopUp.infoBox("The storage is full. Consider deleting some to add", "Save to Storage Button");
						SetComprehension.setIntersectWithUnviersalSet(true);
						break;
					}
				}

				SetComprehension.setIntersectWithUnviersalSet(true);
				storagePanel.revalidate();
				storagePanel.repaint();

				inputTextArea.requestFocus();
			}


		});
		deleteButton1.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("checkBoxes.size() = " + checkBoxes.size());

				int removed = 0;
				for (int i = 0; i < checkBoxes.size(); i++) {
					if (checkBoxes.get(i).isSelected()) {
						storagePanel.remove(i - removed);
						removed++;
					}
				}

				if (removed == 0) {
					PopUp.infoBox("To use this feature, add some sets to storage and\n" +
							"tick the ones that should be deleted", "Venn Diagrams Storage Button");
					inputTextArea.requestFocus();
				}


				checkBoxes = checkBoxes.stream()
						.filter(Calculator::isNotSelected)
						.collect(Collectors.toList());



				storagePanel.revalidate();
				storagePanel.repaint();

				inputTextArea.requestFocus();
			}
		});
		powerButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputTextArea.getSelectedText() != null) {
					StringBuilder selectedText = new StringBuilder(inputTextArea.getSelectedText());
					String fullText = inputTextArea.getText();
					fullText = fullText.replace(selectedText.toString(), "P(" + selectedText.toString() + ")");
					inputTextArea.setText(fullText);
				} else {
					inputTextArea.insert("P(set)", inputTextArea.getCaretPosition());
				}

				inputTextArea.requestFocus();
			}
		});

		belongsButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTextArea.insert(String.valueOf(Symbols.belongsTo), inputTextArea.getCaretPosition());
				inputTextArea.requestFocus();
			}
		});
		complementButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTextArea.insert("\'", inputTextArea.getCaretPosition());
				inputTextArea.requestFocus();
			}
		});
		properSubsetButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTextArea.insert(String.valueOf(Symbols.properSubset), inputTextArea.getCaretPosition());
				inputTextArea.requestFocus();
			}
		});
		weakSubsetButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTextArea.insert(String.valueOf(Symbols.weakSubset), inputTextArea.getCaretPosition());
				inputTextArea.requestFocus();
			}
		});
		outputToStorageButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

                String input = inputTextArea.getText();
                if(input == null || input.isEmpty()){
                    PopUp.infoBox("The output is empty.","Output to Storage Button");
                    return;
                }


				try {
					setupUniversalSet();
				} catch (IncorrectInputSyntaxException ex) {
					ex.printStackTrace();
					return;
				}



				MixedSet answSet;
				try {
					answSet = SyntaxAnalyser.rosterToSet(outputTextArea.getText());
				} catch (IncorrectInputSyntaxException ex) {
					System.out.println("You should never see this");
					ex.printStackTrace();
					return;
				}


				//  sets.get(i).setName(NameRandomised.getRandName());
             //   if(storage.getSetId(answSet) != -1){
             //       PopUp.infoBox("Set is already added","Output to Storage Button");
             //       return;
             //   }

				storage.addSet(answSet);
				JCheckBox cb = new JCheckBox(outputTextArea.getText());
				if (checkBoxes.size() < 10) {
					checkBoxes.add(cb);
					storagePanel.add(cb);
				} else {
					PopUp.infoBox("The storage is full. Consider deleting some to add", "Save to Storage Button");
					SetComprehension.setIntersectWithUnviersalSet(true);
				}



				storagePanel.revalidate();
				storagePanel.repaint();

				inputTextArea.requestFocus();
			}
		});

		//=====================================
		// Allows ctrl+z as undo and ctrl+y as redo
		// ADAPTED from:
		// https://stackoverflow.com/questions/12030836/undo-functionality-in-jtextarea
		// Viewed on Feb 18, 2020
		// Answer by: slartidan, who's profile can be found at https://stackoverflow.com/users/476791/slartidan
		undoManager = new UndoManager();
		Document doc = inputTextArea.getDocument();
		doc.addUndoableEditListener(e -> {
			undoManager.addEdit(e.getEdit());
		});

		InputMap im = inputTextArea.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = inputTextArea.getActionMap();

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
        //==================================================


		tranferOutputToInputButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if (outputTextArea.getText().isEmpty()){
					PopUp.infoBox("This button puts the output into input area.\n" +
							"There must be some set in the output area.",
							"Transfer Output To Input Button");
					return;
				}

				String previousText = inputTextArea.getText();
				inputTextArea.setText(outputTextArea.getText());
				if (!inputTextArea.getText().contains(outputTextArea.getText())) {
					inputTextArea.setText(previousText);
				}
			}
		});

        showVennFromInputButton.addActionListener(e -> {
	        LostValuesToUInteresection.createFirst();

            try {
                setupUniversalSet();
            } catch (IncorrectInputSyntaxException ex) {
                ex.printStackTrace();
                inputTextArea.requestFocus();
                return;
            }
            String input = inputTextArea.getText();
            if(input == null || input.isEmpty()){
                PopUp.infoBox("To use this feature, write down some" +
                        " sets or expression in input area", "showVennFromInputButton Button");
                return;
            }
            ArrayList<MixedSet> setList = null;
            try {
                setList = Calculate.strExprToListOfSets(input);
            } catch (IncorrectInputSyntaxException ex) {
                ex.printStackTrace();
                return;
            }

            if(! (setList == null || setList.isEmpty()))
                VennDiagramOpener.openVennDiagrams(setList);
            else {
                PopUp.infoBox("To use this feature, add some sets to storage and\n" +
                                    "tick the ones that should be displayed", "Venn Diagrams Storage Button");
            }

            showLostValuesToUIntersection();

        });
    }

    private static boolean isNotSelected(JCheckBox cb){
		return !cb.isSelected();
    }

	private void showLostValuesToUIntersection() {
		if(LostValuesToUInteresection.getSet().getMultiplicity() > 0) {
		/*	String lostSetVals = LostValuesToUInteresection.getSetToString();
			char[] lostArr = lostSetVals.toCharArray();
			StringBuilder strBuilder = new StringBuilder();
			boolean appendNewLine = false;
			for (int i = 0; i < lostArr.length; i++) {
				if (appendNewLine && lostArr[i] == ' '){
					appendNewLine = false;
					strBuilder.append("\n");
					i++;
				}
				strBuilder.append(lostArr[i]);
				if(i % 200 == 0 && i != 0) {
					appendNewLine = true;
				}
			}

				PopUp.infoBox("These values were excluded " +
						"due to selected universal set limits:\n" + strBuilder.toString(), "Attention!");
		*/

			PopUp.infoBox("Some values were excluded " +
					"due to selected universal set limits.", "Attention!");

			LostValuesToUInteresection.resetValues();
		}
	}

	private void createUIComponents() {
		Color lightGreen = new Color(196, 255, 199);
		Color darkGreen = new Color(177, 235, 179);


		calculateButton = new CustomButton(new Color(117, 255, 140),
				new Color(105, 224, 121));
		backButton = new CustomButton(new Color(255, 197, 183),
				new Color(235, 178, 170));

		showVennFromInputButton = new CustomButton(lightGreen, darkGreen);
		exampleButton = new CustomButton(lightGreen, darkGreen);
		clearButton = new CustomButton(lightGreen, darkGreen);
		tranferOutputToInputButton = new CustomButton(lightGreen, darkGreen);

		vennDiagramsFromStorageButton = new CustomButton(lightGreen, darkGreen);
		deleteButton1 = new CustomButton(lightGreen, darkGreen);

		getFromStorageButton = new CustomButton(lightGreen, darkGreen);
		outputToStorageButton = new CustomButton(lightGreen, darkGreen);
		saveToStorageButton = new CustomButton(lightGreen, darkGreen);

		unionButton = new CustomButton(lightGreen, darkGreen);
		intersectionButton = new CustomButton(lightGreen, darkGreen);
		differenceButton = new CustomButton(lightGreen, darkGreen);
		powerButton = new CustomButton(lightGreen, darkGreen);
		complementButton = new CustomButton(lightGreen, darkGreen);
		belongsButton = new CustomButton(lightGreen, darkGreen);
		productButton = new CustomButton(lightGreen, darkGreen);
		weakSubsetButton = new CustomButton(lightGreen, darkGreen);
		properSubsetButton = new CustomButton(lightGreen, darkGreen);


	}

	//=====================================

	private void setupUniversalSet() throws IncorrectInputSyntaxException {

		if (nRadioButton.isSelected()) {
			UniversalSet.setType('N');
		} else if (zRadioButton.isSelected()) {
			UniversalSet.setType('Z');
		} else if (qRadioButton.isSelected()) {
			UniversalSet.setType('Q');
		}

		String universalSetString = universalSetTextField.getText();

		//if (universalSetString == null || universalSetString.isEmpty()){
		//
		// }
		SyntaxAnalyser.rangeToUniversalSet(universalSetString);

	}


}
