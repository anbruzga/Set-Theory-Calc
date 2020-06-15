package Sets.View;

import Sets.Controler.IncorrectInputSyntaxException;
import Sets.Controler.SyntaxAnalyser;
import Sets.Model.MixedSet;
import Sets.Model.PlainDocumentLimited;
import com.fathzer.soft.javaluator.DoubleEvaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

public class FunctionView extends JFrame {
	private static final int DOMAIN_INPUT_TEXT_AREA_LIMIT = 400;
	private static final int CODOMAIN_INPUT_TEXT_AREA_LIMIT = 400;
	private static final int FORMULA_TEXT_AREA_LIMIT = 300;
	private JPanel panel1;
	private JTextArea domainTextField;
	private JTextArea codomainTextField;
	private JTextArea functionTextField;
	private JButton checkButton;
	private JLabel answerLabel;
	private JButton clearButton;
	private JButton backButton;
	private JLabel topDescriptionLabel;
	private JPanel invisiblePanel1;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private JPanel invisiblePanel2;
	private JScrollPane scrollPaneLabel;
	private JFrame frame;
	private String domainStr = "Enter a domain values, for ex. \"{2, 4, 6, 8}\".";
	private String codomainStr = "Enter a codomain values, for ex. \"{5, 17, 37, 64 }\".";
	private String functionStr = "Enter a function formula. For ex. \"f(x) = x * x + 1\". " +
			"Cubic root example: f(x) = x^(1/3)." +
			"In this exercise, extracting roots never gives signed values." +
			" ";

	private char functionChar;
	private char variableChar;

	private static Font fontBig = new Font("Monospaced", Font.PLAIN, 25);
	private static Font fontMedium = new Font("Monospaced", Font.PLAIN, 19);
	private static Font fontMediumBold = new Font("Monospaced", Font.BOLD, 19);
	private static Font fontSmall = new Font("Monospaced", Font.PLAIN, 15);

	private Color lightGreenBtn = new Color(196, 255, 199);
	private Color darkGreenBtn = new Color(177, 235, 179);
	private Color foreTextColor = new Color(160, 0, 28);

	private Color darkGreenText = new Color(53, 156, 0);
	private Color lightGreenText = new Color(0, 215, 9);

	private Color darktRedText = new Color(193, 2, 0);
	private Color lightRedText = new Color(255, 7, 0);

	private int timesClicked = 0;

	public FunctionView() {
		super();
		frame = this;
		frame.setContentPane(panel1);
		frame.setTitle("Functions-Relations");
		frame.setPreferredSize(new Dimension(630, 500));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);


		pack();
		setLocationRelativeTo(null);
		setVisible(true);

		domainTextField.setDocument(new PlainDocumentLimited(DOMAIN_INPUT_TEXT_AREA_LIMIT));
		codomainTextField.setDocument(new PlainDocumentLimited(CODOMAIN_INPUT_TEXT_AREA_LIMIT));
		functionTextField.setDocument(new PlainDocumentLimited(FORMULA_TEXT_AREA_LIMIT));

		domainTextField.setText(domainStr);
		codomainTextField.setText(codomainStr);
		functionTextField.setText(functionStr);

		domainTextField.setName("domain");
		codomainTextField.setName("codomain");
		functionTextField.setName("function");

		invisiblePanel1.requestFocus();

		domainTextField.setFont(fontMedium);
		codomainTextField.setFont(fontMedium);
		functionTextField.setFont(fontMedium);

		domainTextField.setForeground(foreTextColor);
		codomainTextField.setForeground(foreTextColor);
		functionTextField.setForeground(foreTextColor);

		answerLabel.setFont(fontMediumBold);
		answerLabel.setForeground(foreTextColor);

		domainTextField.setBackground(new Color(240, 240, 240));
		codomainTextField.setBackground(new Color(240, 240, 240));
		functionTextField.setBackground(new Color(240, 240, 240));
		scrollPaneLabel.setBackground(Color.white);
		answerLabel.setBackground(Color.white);

		topDescriptionLabel.setFont(fontMedium);

		Dimension textAreaSize = domainTextField.getPreferredSize();
		scrollPane1.setPreferredSize(textAreaSize);
		scrollPane2.setPreferredSize(textAreaSize);
		scrollPane3.setPreferredSize(textAreaSize);
		scrollPaneLabel.setPreferredSize(textAreaSize);


		domainTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				if (domainTextField.getText().equals(domainStr)) {
					domainTextField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				if (domainTextField.getText() == null || domainTextField.getText().equals("")) {
					domainTextField.setText(domainStr);
				}
			}
		});
		codomainTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				if (codomainTextField.getText().equals(codomainStr)) {
					codomainTextField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				if (codomainTextField.getText() == null || codomainTextField.getText().equals("")) {
					codomainTextField.setText(codomainStr);
				}
			}
		});
		functionTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				if (functionTextField.getText().equals(functionStr)) {
					functionTextField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);
				if (functionTextField.getText() == null || functionTextField.getText().equals("")) {
					functionTextField.setText(functionStr);
				}
			}
		});
		checkButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				timesClicked++;
				functionChar = functionTextField.getText().charAt(0);
				variableChar = functionTextField.getText().charAt(2);

				try {
					if (!setSyntaxCheck(domainTextField) || !setSyntaxCheck(codomainTextField)) {
						return;
					}
				} catch (IncorrectInputSyntaxException ex) {
					ex.printStackTrace();
				}

				DoubleEvaluator evaluator = new DoubleEvaluator();


				try {
					SyntaxAnalyser.rangeToUniversalSet("[-500,500]");
				} catch (IncorrectInputSyntaxException ex) {
					ex.printStackTrace();
					return;
				}
				MixedSet codomainSet = stringToSetNoInnerSetsOnlyDoubles(codomainTextField.getText());
				MixedSet domainSet = stringToSetNoInnerSetsOnlyDoubles(domainTextField.getText());

				String func = SyntaxAnalyser.cutSpaces(functionTextField.getText());
				String funcPt2 = func.substring(func.indexOf('=') + 1);
				char[] replacedXArr = funcPt2.toCharArray();

				MixedSet functionSet = new MixedSet(true);
				Iterator it = domainSet.iterator();
				boolean setIsFunction = true;
				while (it.hasNext()) {
					double itNext = (double) it.next();
					StringBuilder replacedXStr = new StringBuilder();
					for (int i = 0; i < replacedXArr.length; i++) {
						if (replacedXArr[i] != variableChar) {
							replacedXStr.append(replacedXArr[i]);
						} else {
							replacedXStr.append(itNext);
						}
					}

					double val;
					double val2;


					if (!functionSyntaxCheck(functionTextField)) {
						return;
					}


					try {
						val = evaluator.evaluate(replacedXStr.toString());
						functionSet.add(val);
					} catch (Exception ex) {
						PopUp.infoBox("Please check formula syntax", "FunctionsView");
						return;
					}

					if (!codomainSet.contains(val)) {
						if (timesClicked % 2 == 0) {
							answerLabel.setForeground(darktRedText);
						} else {
							answerLabel.setForeground(lightRedText);
						}

						//  answerLabel.setText("NOT A FUNCTION. SET FOUND: "
						//          + functionSet.toStringNoName(false));
						answerLabel.setText("NOT A FUNCTION");

						setIsFunction = false;
					}
				}

				if (setIsFunction) {
					answerLabel.setText("IT IS A FUNCTION");
					if (timesClicked % 2 == 0) {
						answerLabel.setForeground(darkGreenText);
					} else {
						answerLabel.setForeground(lightGreenText);
					}
				}
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
				resetTextAreas();
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
				new MainView();
				frame.dispose();
			}
		});
		domainTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_TAB) {
					codomainTextField.requestFocus();
				} else {
					super.keyTyped(e);
				}
			}
		});

		codomainTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_TAB) {
					functionTextField.requestFocus();
				} else {
					super.keyTyped(e);
				}

			}
		});
		functionTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_TAB) {
					checkButton.requestFocus();
				} else {
					super.keyTyped(e);
				}
			}
		});
	}

	private boolean setSyntaxCheck(JTextArea ta) throws IncorrectInputSyntaxException {
		if (ta.getText().equals(codomainStr) || ta.getText().equals(domainStr) || ta.getText().equals(functionStr)) {
			PopUp.infoBox("Enter some values as domain, codomain and formula", "FunctionsView");
			return false;
		}

		if (!SyntaxAnalyser.IsRosterNotation(ta.getText())) {
			PopUp.infoBox("The notation of of " + ta.getName() +
					" is not suitable, check your syntax!", "FunctionsView");
			return false;
		}
		// can throw exception
		SyntaxAnalyser.checkSyntaxSanity(ta.getText());

		return true;
	}

	private void resetTextAreas() {
		codomainTextField.setText(codomainStr);
		domainTextField.setText(domainStr);
		functionTextField.setText(functionStr);
		answerLabel.setText(" ");
	}

	//Checks if formula has good syntax
	private boolean functionSyntaxCheck(JTextArea ta) {
		String func = SyntaxAnalyser.cutSpaces(ta.getText());

		// easiest way to check for sqrt is to remove if any
		func = func.replaceAll("sqrt", "");
		if (func.contains("\\D\\(\\D\\)=")) {
			PopUp.infoBox("The notation of of " + ta.getName() +
					" is not suitable, check your syntax!", "FunctionsView");
			return false;
		}

		functionChar = func.charAt(0);
		variableChar = func.charAt(2);


		func = func.replaceAll("\\D\\(\\D\\)=", "");

		char[] chars = func.toCharArray();

		for (char c : chars) {
			if (c == functionChar || c == variableChar) {
				continue;
			}
			switch (c) {
				case '+':
				case '-':
				case '/':
				case '*':
				case '(':
				case ')':
				case '^':
					break;
				default:
					if (!Character.isDigit(c)) {
						PopUp.infoBox("The notation of of " + ta.getName() +
								" is not suitable, check your syntax!", "FunctionsView");
						return false;
					}
			}
		}

		return true;
	}

	private MixedSet stringToSetNoInnerSetsOnlyDoubles(String expr) {

		expr = expr.substring(1, expr.length() - 1);
		String splitted[] = expr.split("[,]");
		stringContainsIntsAndDoubles(splitted);
		MixedSet set = new MixedSet(false);
		for (String numStr : splitted) {
			set.add(Double.valueOf(numStr));
		}
		return set;
	}

	private boolean stringContainsIntsAndDoubles(String[] splittedExpr) {

		for (int i = 0; i < splittedExpr.length; i++) {
			String s = splittedExpr[i];
			if (!s.matches("(-?\\d+(\\.\\d+)?)(?:\\s(-?\\d+(\\.\\d+)?))")) {
				return false;
			}
		}

		return true;
	}

	private void createUIComponents() {
		checkButton = new CustomButton(new Color(117, 255, 140),
				new Color(105, 224, 121));
		backButton = new CustomButton(new Color(255, 197, 183),
				new Color(235, 178, 170));
		clearButton = new CustomButton(new Color(117, 255, 140),
				new Color(105, 224, 121));

		checkButton.setFont(fontSmall);
		backButton.setFont(fontSmall);
		clearButton.setFont(fontSmall);


		functionTextField = new NoTabTextArea();
		codomainTextField = new NoTabTextArea();
		domainTextField = new NoTabTextArea();
	}


}
