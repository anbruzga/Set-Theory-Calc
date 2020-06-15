package Sets.View;

import Sets.Controler.SetUtils;
import Sets.Controler.VennUtils;
import Sets.Model.MixedSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import static Sets.Controler.VennUtils.*;
import static Sets.View.Styles.NOT_SELECTED_COLOR;
import static Sets.View.Styles.SET_NAME_VENN_DIAGRAM_COLOUR;

//https://stackoverflow.com/questions/22217148/get-pixel-color-on-screen-java
@SuppressWarnings("serial")
public class Diagrams2Sets extends JFrame {

	private String imgLocation = "/Resources/Images/2VennColoursV2.png";

	private String clickedValTADescri = "Click a part of the diagram" +
			" and all the values of it will appear here.";
	private JFrame frame;
	private JPanel panel;
	private JButton closeButton1;
	private JPanel imagePanel;
	private JTextArea textArea1VennDiagram;
	private JTextArea textArea2VennDiagram;
	private JTextArea textArea3VennDiagram;
	private JTextArea textAreaUniversal;


	private static final Color color1 = new Color(239, 228, 176);
	private static final Color color2 = new Color(235, 121, 17);
	private static final Color color3 = new Color(93, 230, 191);
	private static final Color colorUniversalSet = new Color(195, 195, 195);

	private JTextArea clickedValuesTextArea;
	private JScrollPane scrollPaneTA1;

	private final MouseMotionListener motionListener;
	private final MouseListener mouseExitListener;
	private final MouseListener mouseListener;

	private JLabel setNameLabel1;
	private JLabel setNameLabel2;
	private JLabel universlSetNameLabel;


	private ArrayList<MixedSet> sets;
	private ArrayList<MixedSet> setsListOriginal;

	private ArrayList<JLabel> setNameLabelList;
	private ArrayList<JTextArea> setValuesTAList;
	private ArrayList<Color> colorList;
	private ArrayList<MixedSet> listOfSetParts;


	public Diagrams2Sets() {
		super("Venn Diagrams 2 Circles");


		sets = new ArrayList<>();
		setsListOriginal = new ArrayList<>();

		initTextArea();

		initColorsList();

		initFrame();

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
				mouseHoverEventColorChange(e, setValuesTAList, colorList);
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

				for (int i = 0; i < setValuesTAList.size(); i++) {
					colourChangeBackOnHover(setValuesTAList.get(i));
				}
			}
		};

		setListeners();


		closeButton1.addActionListener(e -> {
			frame.dispose();
		});
	}

	private void initTextArea() {
		clickedValuesTextArea.setText(clickedValTADescri);
		clickedValuesTextArea.setLineWrap(true);
		clickedValuesTextArea.setFont(new Font("Monospace", Font.PLAIN, 25));
		clickedValuesTextArea.setForeground(Styles.TEXT_AREA_COLOR);
		scrollPaneTA1.setPreferredSize(new Dimension(800, 80));
	}

	private void initFrame() {
		this.create();
		this.showFrame();
		frame.setTitle("Venn Diagram 2 Sets");
		frame.pack();
	}

	private void setListeners() {
		for (int i = 0; i < setNameLabelList.size(); i++) {
			setNameLabelList.get(i).addMouseListener(mouseListener);
			setNameLabelList.get(i).addMouseMotionListener(motionListener);
		}

		for (int i = 0; i < setValuesTAList.size(); i++) {
			setValuesTAList.get(i).addMouseListener(mouseListener);
			setValuesTAList.get(i).addMouseMotionListener(motionListener);
		}

		panel.addMouseMotionListener(motionListener);
		panel.addMouseListener(mouseListener);
		panel.addMouseListener(mouseExitListener);

		imagePanel.addMouseListener(mouseListener);
		imagePanel.addMouseMotionListener(motionListener);
		imagePanel.addMouseListener(mouseExitListener);
	}

	private void initColorsList() {
		colorList = new ArrayList<Color>();
		colorList.add(color1);
		colorList.add(color2);
		colorList.add(color3);
		colorList.add(colorUniversalSet);
	}


	private void mouseClickedAction(MouseEvent e) {

		Point p = e.getLocationOnScreen();

		try {
			Color pixelColor = getPixel(p.x, p.y);
			MixedSet answ = null;
			for (int i = 0; i < colorList.size(); i++) {
				if (pixelColor.equals(colorList.get(i))) {
					answ = listOfSetParts.get(i);
				}
			}

			if (answ == null) {
				return;
			}

			clickedValuesTextArea.setText(SetUtils.setStringCutEnds(answ));

		} catch (AWTException ex) {
			ex.printStackTrace();
		}

	}

	private void setTextAreaVennTexts() {

		countEachArea();

		for (int i = 0; i < setValuesTAList.size(); i++) {
			String valuesStr = SetUtils.setStringCutEnds(listOfSetParts.get(i));
			setValuesTAList.get(i).setText(valuesStr);
		}
		textAreaUniversal.setText("<...>");

		setVennTextAreasCharLimit(textArea1VennDiagram, 104);
		setVennTextAreasCharLimit(textArea2VennDiagram, 104);
		setVennTextAreasCharLimit(textArea3VennDiagram, 90);

	}

	private void countEachArea() {
		MixedSet answ1 = null;

		answ1 = sets.get(0).difference(sets.get(1));
		answ1.setName("Part1");
		reloadOriginalSetList(sets, setsListOriginal);

		MixedSet answ2;
		answ2 = sets.get(1).difference(sets.get(0));
		answ2.setName("Part2");
		reloadOriginalSetList(sets, setsListOriginal);

		MixedSet answ3;
		answ3 = sets.get(0).intersection(sets.get(1));
		answ3.setName("Part3");
		reloadOriginalSetList(sets, setsListOriginal);

		MixedSet answ4;
		answ4 = sets.get(0).union(sets.get(1)).complement();
		answ4.setName("U");
		reloadOriginalSetList(sets, setsListOriginal);


		listOfSetParts = new ArrayList<>();
		listOfSetParts.add(answ1);
		listOfSetParts.add(answ2);
		listOfSetParts.add(answ3);
		listOfSetParts.add(answ4);

		for (int i = 0; i < listOfSetParts.size(); i++) {
			listOfSetParts.get(i).intersectionWithUniveralSet();
		}
	}


	private Diagrams2Sets create() {
		frame = createFrame();
		frame.setContentPane(panel);
		createContent();
		return this;
	}

	private JFrame createFrame() {
		JFrame frame = new JFrame(getClass().getName());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		return frame;
	}

	private void showFrame() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	private Component createContent() {

		panel.setPreferredSize(new Dimension(843, 675));
		return panel;
	}

	public void setSets(ArrayList<MixedSet> sets) {
		this.sets = sets;
		for (int i = 0; i < sets.size(); i++) {
			MixedSet set = sets.get(i);
			MixedSet deepCopySet = SetUtils.deepCopy(set);
			setsListOriginal.add(deepCopySet);
		}
		setSetNames(this.sets, setNameLabelList);

		setTextAreaVennTexts();
		clickedValuesTextArea.requestFocus();

	}


	private void createUIComponents() {

		paintImagePanel();

		imagePanel.setLayout(null);

		addSetNameLabels();

		addSetValuesTextAreas();

		closeButton1 = new CustomButton(new Color(255, 197, 183),
				new Color(235, 178, 170));

	}

	private void addSetValuesTextAreas() {
		textArea1VennDiagram = new JTextArea();
		textArea1VennDiagram.setLocation(72, 154);
		textArea1VennDiagram.setSize(170, 230);

		textArea2VennDiagram = new JTextArea();
		textArea2VennDiagram.setLocation(554, 154);
		textArea2VennDiagram.setSize(170, 230);

		textArea3VennDiagram = new JTextArea();
		textArea3VennDiagram.setLocation(338, 154);
		textArea3VennDiagram.setSize(145, 230);

		textAreaUniversal = new JTextArea();
		textAreaUniversal.setLocation(20, 45);
		textAreaUniversal.setSize(145, 230);

		setValuesTAList = new ArrayList<>();
		setValuesTAList.add(textArea1VennDiagram);
		setValuesTAList.add(textArea2VennDiagram);
		setValuesTAList.add(textArea3VennDiagram);
		setValuesTAList.add(textAreaUniversal);

		for (int i = 0; i < setValuesTAList.size(); i++) {
			VennUtils.centerText(setValuesTAList.get(i));
			configTextAreasInVenn(setValuesTAList.get(i));
			imagePanel.add(setValuesTAList.get(i));
		}
	}

	private void addSetNameLabels() {
		Dimension labelSize = new Dimension(80, 30);
		setNameLabel1 = new JLabel("SET A");
		setNameLabel1.setLocation(183, 56);

		setNameLabel2 = new JLabel("SET B");
		setNameLabel2.setLocation(479, 56);

		universlSetNameLabel = new JLabel("U");
		universlSetNameLabel.setLocation(15, 13);

		setNameLabelList = new ArrayList<>();
		setNameLabelList.add(setNameLabel1);
		setNameLabelList.add(setNameLabel2);
		setNameLabelList.add(universlSetNameLabel);

		for (int i = 0; i < setNameLabelList.size(); i++) {
			setNameLabelList.get(i).setFont(new Font("Serif", Font.PLAIN, 25));
			setNameLabelList.get(i).setSize(labelSize);
			imagePanel.add(setNameLabelList.get(i));
			setNameLabelList.get(i).setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);
		}
	}

	private void paintImagePanel() {
		final Image image = VennUtils.requestImage(imgLocation);
		imagePanel = new JPanel() {

			//____________________________________________________
			// The following code centers the image in the JPanel
			// Reference
			// https://stackoverflow.com/questions/4533526/how-to-center-align-background-image-in-jpanel
			// Vuewed at 23, April, 2020
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
	}

	private void configTextAreasInVenn(JTextArea tArea) {

		tArea.setFont(new Font("Serif", Font.PLAIN, 25));
		tArea.setForeground(NOT_SELECTED_COLOR);
		tArea.setOpaque(false);
		tArea.setEditable(false);
		tArea.setLineWrap(true);
		tArea.setPreferredSize(new Dimension(100, 100));
	}


}

