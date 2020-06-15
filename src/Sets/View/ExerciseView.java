package Sets.View;

import Sets.Controler.*;
import Sets.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class ExerciseView {

	private final static int INPUT_TEXT_AREA_LIMIT = 350;
	private JPanel panel1;
	private JButton backButton;
	private JButton checkButton;
	private JScrollPane feedbackScrollPane;
	private JButton vennDiagramButton;
	private JButton simpleButton;

	private JButton hardButton;
	private JButton averageButton;
	private JTextArea inputTextArea;
	private JButton showAnswerButton;
	private JButton myStatsButton;
	private JList exercisesList;

	private JTextArea feedbackTextArea;
	private JScrollPane exercisesScrollPane;
	private JScrollPane inputScrollPane;
	private JLabel feedbackLabel;
	private JLabel typeAnswerLabel;
	private JLabel selectExerciseLabel;
	private JLabel universalSetLabel;
	private JPanel contentPanel;
	private String beginingOfPath;
	private static String path;
	private ArrayList<JButton> buttonList;

	private Font fontBig = new Font("Monospace", Font.PLAIN, 25);
	private Font fontMedium = new Font("Monospace", Font.PLAIN, 19);
	private Font fontMediumBold = new Font("Monospace", Font.BOLD, 19);
	private Font fontSmall = new Font("Monospace", Font.PLAIN, 15);

	private TableData tableData;

	public ExerciseView() {
		JFrame frame = new JFrame("Sets Exercises");
		frame.setContentPane(panel1);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);


		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double sizeCoefficient = 0.75;
		frame.setSize((int) (screenSize.width * sizeCoefficient), (int) (screenSize.height * sizeCoefficient));

		populateExercises(1);

		exercisesList.setFont(fontMedium);
		inputTextArea.setFont(fontBig);
		feedbackTextArea.setFont(fontBig);

		deserialiseStatsData();

		UniversalSet.setType('Z');
		String testVal = "[-99,99]";
		String setName = "U";
		try {
			SyntaxAnalyser.rangeToUniversalSet(testVal);
		} catch (IncorrectInputSyntaxException e) {
			System.out.println("This should never happen");
			e.printStackTrace();
		}


		//checkButton.


      /*
        feedbackTextArea.setBorder(BorderFactory.createCompoundBorder(border,

        Border border = BorderFactory.createLineBorder((new Color(117, 255, 140)));
        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        exercisesList.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
       */
		//Border border = BorderFactory.createLineBorder((new Color(117, 255, 140)));

		//  inputTextArea.setBorder(BorderFactory.createCompoundBorder(border,
		//          BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		feedbackTextArea.setBackground(new Color(235, 231, 236));
		exercisesList.setBackground(new Color(235, 231, 236));
		inputTextArea.setBackground(new Color(240, 240, 240));
		panel1.setBackground(Color.white);

		inputTextArea.setLineWrap(true);
		feedbackTextArea.setLineWrap(true);

		inputTextArea.setDocument(new PlainDocumentLimited(INPUT_TEXT_AREA_LIMIT));

		frame.setLocationRelativeTo(null);

		//LISTENERS
		{
			backButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					new MainView();
				}
			});


			checkButton.addActionListener(e -> {


				String exercise;
				try {
					exercise = exercisesList.getSelectedValue().toString();
				} catch (NullPointerException ex) {
					PopUp.infoBox("Select an exercise from the list!", "ExerciseView.checkButtonClick");
					return;
				}

				if (inputTextArea.getText().isEmpty()) {
					PopUp.infoBox("Type in your answer for the selected exercise", "ExerciseView.checkButtonClick");
					return;
				}

				int numberPart = exercise.indexOf(".") + 2;
				exercise = exercise.substring(numberPart);


				MixedSet answerSet;
				SetComprehension.setIntersectWithUnviersalSet(false);
				System.out.println("exercise = " + exercise);
				MixedSet guessSet = null;
				boolean compareString = false;
				try {
					answerSet = Calculate.evaluate(exercise);
					if (answerSet.toStringNoIntersectionWithUniversalSet().contains("(") // for cartesian
					||answerSet.toStringNoIntersectionWithUniversalSet().contains("{true}")
					|| answerSet.toStringNoIntersectionWithUniversalSet().contains("{false}")) {
						compareString = true;
					}
					else{
						guessSet = Calculate.evaluate(inputTextArea.getText());
					}
				} catch (IncorrectInputSyntaxException ex) {
					ex.printStackTrace();
					SetComprehension.setIntersectWithUnviersalSet(true);
					return;
				}
				SetComprehension.setIntersectWithUnviersalSet(true);


				String guessStr =  removeSpaces(inputTextArea.getText());
				if(guessStr.equalsIgnoreCase("false")){
					guessStr = "false";
				}
				else if(guessStr.equalsIgnoreCase("true")){
					guessStr = "true";
				}

				String answerStr = removeSpaces(answerSet.toStringNoName(true));

				if (answerStr.equals("{false}")) {
					answerStr = "false";
				} else if (answerStr.equals("{true}")) {
					answerStr = "true";
				}

				SingleTableLine tableLine;
				if ((compareString && removeSpaces(guessStr).equals(removeSpaces(answerStr)))
						|| (guessSet != null && guessSet.equals(answerSet)) ) {
					feedbackTextArea.setForeground(new Color(0, 144, 25));
					feedbackTextArea.setText(getPositiveFeedback());
					tableLine = new SingleTableLine(exercise, true, 0, false);
				} else {
					feedbackTextArea.setForeground(new Color(174, 0, 35));
					feedbackTextArea.setText(getNegativeFeedback());
					tableLine = new SingleTableLine(exercise, false, 1, false);
				}
				tableData.add(tableLine);
				serialiseStatsData();

			});


			showAnswerButton.addActionListener(e -> {
				String exercise;
				try {
					exercise = exercisesList.getSelectedValue().toString();
				} catch (NullPointerException ex) {
					PopUp.infoBox("Select an exercise from the list!", "ExerciseView.checkButtonClick");
					return;
				}

				boolean confirmBoxResult = PopUp.confirmBox(
						"Are you sure you want to give up?",
						"ExerciseView.checkButtonClick");

				if (confirmBoxResult) {

					int numberPart = exercise.indexOf(".") + 2;
					exercise = exercise.substring(numberPart);
					SingleTableLine tableLine = new SingleTableLine(exercise, false, 0, true);
					tableData.add(tableLine);
					serialiseStatsData();

					MixedSet set;
					try {
						set = Calculate.evaluate(exercise);
					} catch (IncorrectInputSyntaxException ex) {
						ex.printStackTrace();
						return;

					}
					String answer = set.toStringNoName(true);
					if (answer.equals("{false}")) {
						answer = "false";
					} else if (answer.equals("{true}")) {
						answer = "true";
					}

					feedbackTextArea.setText(answer);
					feedbackTextArea.setForeground(Color.black);
				} else return;
			});

			vennDiagramButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {


					String exercise;
					try {
						exercise = exercisesList.getSelectedValue().toString();
					} catch (NullPointerException ex) {
						PopUp.infoBox("Select an exercise from the list!", "ExerciseView.checkButtonClick");
						return;
					}

					int numberPart = exercise.indexOf(".") + 2;
					exercise = exercise.substring(numberPart);


					ArrayList<MixedSet> setsList = null;
					try {
						setsList = Calculate.strExprToListOfSets(exercise);
					} catch (IncorrectInputSyntaxException ex) {
						ex.printStackTrace();
						return;
					}

					if (setsList != null) {
						VennDiagramOpener.openVennDiagrams(setsList);
					}
				}
			});
			simpleButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					populateExercises(1);
				}
			});
			averageButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					populateExercises(2);
				}
			});

			hardButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					populateExercises(3);
				}
			});


		}
		myStatsButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				new StatsView();
			}
		});
	}

	/* 1 - easy
	 *  2 - medium
	 *  3 - hard
	 */
	private void populateExercises(int level) {
		DefaultListModel listModel;
		listModel = new DefaultListModel();
		String path = new String();
		switch (level) {
			case 1:
				path = "/Resources/SetData/SetExercises1.txt";
				break;
			case 2:
				path = "/Resources/SetData/SetExercises2.txt";
				break;
			case 3:
				path = "/Resources/SetData/SetExercises3.txt";
				break;
			default:
				return;
		}

		String content;

		try {
			InputStream is = ExerciseView.class.getResourceAsStream(path);
			InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
			BufferedReader reader = new BufferedReader(isReader);
			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				//System.out.println(str);
				str = str.substring(0, str.length() - 1);
				listModel.addElement(str);
			}

		} catch (IOException e) {
			PopUp.infoBox("Exercise list not found", "ExerciseView.getContent");
			e.printStackTrace();
		}

		exercisesList.setModel(listModel);
	}

	private String getPositiveFeedback() {
		String[] feedback = {"Correct!", "Doing Well!", "That's Right!", "Well Done!",
				"Perfect!"};

		Random random = new Random();
		int randomNum = random.nextInt(feedback.length);
		return feedback[randomNum];
	}


	private String getNegativeFeedback() {
		String[] feedback = {"Look for a Mistake!", "Incorrect!",
				"Try Again!", "Mistake!", "Wrong!"};

		Random random = new Random();
		int randomNum = random.nextInt(feedback.length);
		return feedback[randomNum];
	}


	private String removeSpaces(String str) {
		char[] arr = str.toCharArray();
		StringBuilder noSpaces = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != ' ') {
				noSpaces.append(arr[i]);
			}
		}
		return noSpaces.toString();
	}


	public static String getSavedDataPath() {
		return path;
	}

	public void initPath() {
		if (new File("H:").exists()) {
			this.beginingOfPath = "H:/";
		} else {
			this.beginingOfPath = "";
		}
		try {
			Files.createDirectories(Paths.get(this.beginingOfPath + "LogicAppData"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		this.beginingOfPath = this.beginingOfPath + "LogicAppData/";
		this.path = beginingOfPath + "tabledata.ser";
	}


	private void deserialiseStatsData() {
		initPath();
		String defaultCharacterEncoding = System.getProperty("file.encoding");
		System.out.println("defaultCharacterEncoding = " + defaultCharacterEncoding);
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tableData = (TableData) ois.readObject();
			fis.close();
			ois.close();

		} catch (FileNotFoundException ex) {
			tableData = new TableData();
			ex.printStackTrace();
			return;
		} catch (Exception e) {
			tableData = new TableData();
			e.printStackTrace();
			return;
		}
	}

	private void serialiseStatsData() {
		initPath();
		try {
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tableData);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	private void createUIComponents() {
		checkButton = new CustomButton(new Color(117, 255, 140),
				new Color(105, 224, 121));
		backButton = new CustomButton(new Color(255, 197, 183),
				new Color(235, 178, 170));


		Color lightGreen = new Color(196, 255, 199);
		Color darkGreen = new Color(177, 235, 179);
		simpleButton = new CustomButton(lightGreen, darkGreen);
		hardButton = new CustomButton(lightGreen, darkGreen);
		averageButton = new CustomButton(lightGreen, darkGreen);
		myStatsButton = new CustomButton(lightGreen, darkGreen);
		vennDiagramButton = new CustomButton(lightGreen, darkGreen);
		showAnswerButton = new CustomButton(lightGreen, darkGreen);

		buttonList = new ArrayList<>();
		buttonList.add(vennDiagramButton);
		buttonList.add(simpleButton);
		buttonList.add(hardButton);
		buttonList.add(averageButton);
		buttonList.add(showAnswerButton);
		buttonList.add(myStatsButton);
		buttonList.add(checkButton);
		buttonList.add(backButton);

	}

  /*  private void createUIComponents() {
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        listModel.addElement("item 1");
        listModel.addElement("item 2");

        listModel.addElement("new item");
        exercisesList = new JList(listModel);
        exercisesList.repaint();
    }*/
}
