package Sets.View;

import Sets.Controler.Calculator;
import Sets.Controler.VennUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class MainView {
	private JButton vennDiagramsExercisesButton;
	private JPanel panel1;
	private JButton exercisesButton;
	private JButton setsCalculatorButton;
	private JButton backToBooleanLogicButton;
	private JButton functionsButton;
	private JButton manualButton;
	private JPanel panelInitialFocus;
	private JLabel titleLabel;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	private JScrollPane scrollPane4;
	private JScrollPane scrollPane5;
	private JScrollPane scrollPane6;
	private JScrollPane scrollPaneTitle;


	public static void main(String [] args){
		new MainView();
	}

	public MainView() {
		JFrame frame = new JFrame();
		frame.setContentPane(panel1);
		frame.setTitle("Sets and Venn Diagrams Menu");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double sizeCoefficient = 0.75;
		frame.setSize((int) (screenSize.width * sizeCoefficient), (int) (screenSize.height * sizeCoefficient));
		panelInitialFocus.setFocusable(true);
		panelInitialFocus.requestFocus();

		titleLabel.setForeground(new Color(120, 71, 132));

		ArrayList<JScrollPane> scrollPaneList = new ArrayList<>();
		scrollPaneList.add(scrollPane1);
		scrollPaneList.add(scrollPane2);
		scrollPaneList.add(scrollPane3);
		scrollPaneList.add(scrollPane4);
		scrollPaneList.add(scrollPane5);
		scrollPaneList.add(scrollPane6);
		double coefficient = 0.3;
		Dimension panelSize = new Dimension((int) (screenSize.width * sizeCoefficient * coefficient),
				(int) (screenSize.height * sizeCoefficient * coefficient));
		for (int i = 0; i < scrollPaneList.size(); i++) {
			scrollPaneList.get(i).setPreferredSize(panelSize);
			scrollPaneList.get(i).setBackground(panel1.getBackground());
		}

		double coefficientLabel = 0.1;
		Dimension panelSizeLabel = new Dimension((int) (screenSize.width * sizeCoefficient),
				(int) (screenSize.height * sizeCoefficient * coefficientLabel));
		scrollPaneTitle.setPreferredSize(panelSizeLabel);

		scrollPaneTitle.setBackground(new Color(171, 116, 54));
		titleLabel.setBackground(new Color(171, 116, 54));

		frame.setLocationRelativeTo(null);

		setsCalculatorButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Calculator();
			}
		});
		exercisesButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new ExerciseView();
			}
		});
		backToBooleanLogicButton.addActionListener(new ActionListener() {
			/**
			 * Invoked when an action occurs.
			 *
			 * @param e the event to be processed
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				//frame.dispose();
				//Tableau.Model.Tableau tab = new Tableau.Model.Tableau();
				//new Tableau.Controler.Controler(tab);
			}
		});
		vennDiagramsExercisesButton.addActionListener(e -> {
			VennUtils.startVennExercises2(null);
			frame.dispose();

		});

		functionsButton.addActionListener(e -> {
			new FunctionView();
			frame.dispose();
		});


		manualButton.addActionListener(e -> {
			try {
				String path = "";
				Path tempOutput = null;
				String tempFile = "SetsManual";
				tempOutput = Files.createTempFile(tempFile, ".pdf");
				tempOutput.toFile().deleteOnExit();
				InputStream is = getClass().getResourceAsStream("/SetsManual.pdf");
				Files.copy(is,tempOutput, StandardCopyOption.REPLACE_EXISTING);
				if(Desktop.isDesktopSupported())
				{
					Desktop dTop = Desktop.getDesktop();
					if(dTop.isSupported(Desktop.Action.OPEN))
					{
						dTop.open(tempOutput.toFile());
					}
				}
			} catch (IOException ex) {}

		});
	}

	private void createUIComponents() {
		Color color1 = new Color(154, 208, 106);
		Color color2 = new Color(81, 190, 190);
		Color color3 = new Color(191, 178, 97);
		Color color4 = new Color(191, 75, 78);
		Color color5 = new Color(178, 111, 190);
		Color color6 = new Color(118, 115, 191);

		Color color1darker = new Color(123, 168, 84);
		Color color2darker = new Color(61, 133, 133);
		Color color3darker = new Color(133, 123, 69);
		Color color4darker = new Color(133, 47, 50);
		Color color5darker = new Color(120, 71, 132);
		Color color6darker = new Color(82, 79, 133);

		int roundRadius = 0;
		backToBooleanLogicButton = new CustomButton(color1, color1darker, roundRadius);
		exercisesButton = new CustomButton(color2, color2darker, roundRadius);
		functionsButton = new CustomButton(color3, color3darker, roundRadius);
		setsCalculatorButton = new CustomButton(color4, color4darker, roundRadius);
		manualButton = new CustomButton(color5, color5darker, roundRadius);
		vennDiagramsExercisesButton = new CustomButton(color6, color6darker, roundRadius);

		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.add(backToBooleanLogicButton);
		buttons.add(exercisesButton);
		buttons.add(functionsButton);
		buttons.add(setsCalculatorButton);
		buttons.add(manualButton);
		buttons.add(vennDiagramsExercisesButton);
		Font buttonsFont = new Font("ARIAL", Font.PLAIN, 25);
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).setFont(buttonsFont);
			buttons.get(i).setForeground(new Color(71, 10, 87));

		}

		backToBooleanLogicButton.setText("<html>Back To<br/>Boolean Logic</html>");
		vennDiagramsExercisesButton.setText("<html>Practice<br />Venn Diagrams</html>");
	}
}
