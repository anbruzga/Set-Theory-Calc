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
public class Diagrams3Sets extends JFrame {

	private String venn2Url = "/Resources/Images/3VennColoursV1.png";

	private String clickedValTADescri = "Click a part of the " +
			"diagram and all the values of it will appear here.";
	private JFrame frame;
	private JPanel panel;
	private JButton closeButton1;
	private JPanel imagePanel;

	private JTextArea textArea1VennDiagram;
	private JTextArea textArea2VennDiagram;
	private JTextArea textArea3VennDiagram;
	private JTextArea textArea4VennDiagram;
	private JTextArea textArea5VennDiagram;
	private JTextArea textArea6VennDiagram;
	private JTextArea textArea7VennDiagram;
	private JTextArea textAreaUniversal;

	private final Color color1 = new Color(227, 160, 53);
	private final Color color2 = new Color(110, 227, 106);
	private final Color color3 = new Color(239, 247, 121);
	private final Color color4 = new Color(28, 155, 58);
	private final Color color5 = new Color(177, 172, 233);
	// for some reason RGB of pixel where mouse clicks does not match even thou Red Green and Blue are same.
	// This is workaround:
	private final Color color6 = new Color(-14816792);
	private final Color color7 = new Color(227, 215, 12);
	private Color colorUniversalSet = new Color(195, 195, 195);


	private JTextArea clickedValuesTextArea;
	private JScrollPane scrollPaneTA1;

	private final MouseMotionListener motionListener;
	private final MouseListener mouseExitListener;
	private final MouseListener mouseListener;

	private MixedSet tArea2Set;

	private JLabel setNameLabel1;
	private JLabel setNameLabel2;
	private JLabel setNameLabel3;
	private JLabel universalSetNameLabel;

	private ArrayList<MixedSet> sets;
	private ArrayList<MixedSet> setsListOriginal;

	private ArrayList<JLabel> setNameLabelList;
	private ArrayList<JTextArea> setValuesTAList;
	private ArrayList<Color> colorList;
	private ArrayList<MixedSet> listOfSetParts;

	public Diagrams3Sets() {
		super("Venn Diagrams 3 Sets");
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

	private void initFrame() {
		this.create();
		this.showFrame();
		//frame.setResizable(false);
		frame.setTitle("Venn Diagram 3 Sets");
		frame.pack();
	}

	private void setListeners() {
		for (int i = 0; i < setNameLabelList.size(); i++) {
			setNameLabelList.get(i).addMouseListener(mouseListener);
			setNameLabelList.get(i).addMouseMotionListener(motionListener);
            System.out.println("i = " + i);
		}

		for (int i = 0; i < setValuesTAList.size(); i++) {
			setValuesTAList.get(i).addMouseListener(mouseListener);
			setValuesTAList.get(i).addMouseMotionListener(motionListener);
            System.out.println("i2 = " + i);
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
		colorList.add(color4);
		colorList.add(color5);
		colorList.add(color6);
		colorList.add(color7);
		colorList.add(colorUniversalSet);
	}

	private void initTextArea() {
		clickedValuesTextArea.setText(clickedValTADescri);
		clickedValuesTextArea.setLineWrap(true);
		clickedValuesTextArea.setFont(new Font("Monospace", Font.PLAIN, 25));
		clickedValuesTextArea.setForeground(Styles.TEXT_AREA_COLOR);
		scrollPaneTA1.setPreferredSize(new Dimension(800, 80));
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
            System.out.println("i3 = " + i);
        }

        textAreaUniversal.setText("<...>");

		setVennTextAreasCharLimit(textArea1VennDiagram, 42);
		setVennTextAreasCharLimit(textArea2VennDiagram, 8);
		setVennTextAreasCharLimit(textArea3VennDiagram, 42);
		setVennTextAreasCharLimit(textArea4VennDiagram, 19);
		setVennTextAreasCharLimit(textArea5VennDiagram, 23);
		setVennTextAreasCharLimit(textArea6VennDiagram, 19);
		setVennTextAreasCharLimit(textArea7VennDiagram, 41);

	}

	private void countEachArea() {

		MixedSet A = sets.get(0);
		MixedSet B = sets.get(1);
		MixedSet C = sets.get(2);

		/*(A) (B)
		 *  (C)
		 */

		MixedSet answ1 = null;
		answ1 = A.difference(B).difference(C);
		answ1.setName("Part1");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);


		MixedSet answ2;
		answ2 = B.intersection(A).difference(C);
		answ2.setName("Part2");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);


		MixedSet answ3;
		answ3 = B.difference(A).difference(C);
		answ3.setName("Part3");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);


		MixedSet answ4;
		answ4 = A.intersection(C).difference(B);
		answ4.setName("Part4");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);


		MixedSet answ5;
		answ5 = A.intersection(C).intersection(B);
		answ5.setName("Part5");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);

		MixedSet answ6;
		answ6 = B.intersection(C).difference(A);
		answ6.setName("Part6");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);

		MixedSet answ7;
		answ7 = C.difference(B).difference(A);
		answ7.setName("Part7");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);

		MixedSet answ8;
		answ8 = A.union(B).union(C).complement();
		answ8.setName("U");
		reloadOriginalSetList(sets, setsListOriginal);


		listOfSetParts = new ArrayList<>();
		listOfSetParts.add(answ1);
		listOfSetParts.add(answ2);
		listOfSetParts.add(answ3);
		listOfSetParts.add(answ4);
        listOfSetParts.add(answ5);
        listOfSetParts.add(answ6);
        listOfSetParts.add(answ7);
        listOfSetParts.add(answ8);

		for (int i = 0; i < listOfSetParts.size(); i++) {
			listOfSetParts.get(i).intersectionWithUniveralSet();
		}
	}



	private Diagrams3Sets create() {
		frame = createFrame();
		frame.setContentPane(panel);
		createContent();
		return this;
	}

	private JFrame createFrame() {
		JFrame frame = new JFrame(getClass().getName());
		// frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

	}

	private void createUIComponents() {
		paintImagePanel();

		imagePanel.setLayout(null);

		addSetNameLabels();

		addSetValuesTextAreas();

		closeButton1 =  new CustomButton(new Color(255, 197, 183),
				new Color(235, 178, 170));

	}

	private void addSetValuesTextAreas() {
		textArea1VennDiagram = new JTextArea();
		textArea1VennDiagram.setLocation(76, 94);
		textArea1VennDiagram.setSize(200, 95);

		textArea2VennDiagram = new JTextArea();
		textArea2VennDiagram.setLocation(342, 112);
		textArea2VennDiagram.setSize(143, 40);

		textArea3VennDiagram = new JTextArea();
		textArea3VennDiagram.setLocation(530, 94);
		textArea3VennDiagram.setSize(200, 95);

		textArea4VennDiagram = new JTextArea();
		textArea4VennDiagram.setLocation(195, 265);
		textArea4VennDiagram.setSize(105, 100);

		textArea5VennDiagram = new JTextArea();
		textArea5VennDiagram.setLocation(324, 195);
		textArea5VennDiagram.setSize(180, 70);

		textArea6VennDiagram = new JTextArea();
		textArea6VennDiagram.setLocation(512, 265);
		textArea6VennDiagram.setSize(112, 100);

		textArea7VennDiagram = new JTextArea();
		textArea7VennDiagram.setLocation(318, 400);
		textArea7VennDiagram.setSize(200, 110);

		textAreaUniversal = new JTextArea();
		textAreaUniversal.setLocation(20, 45);
		textAreaUniversal.setSize(145, 230);

		setValuesTAList = new ArrayList<>();
		setValuesTAList.add(textArea1VennDiagram);
		setValuesTAList.add(textArea2VennDiagram);
		setValuesTAList.add(textArea3VennDiagram);
        setValuesTAList.add(textArea4VennDiagram);
        setValuesTAList.add(textArea5VennDiagram);
        setValuesTAList.add(textArea6VennDiagram);
        setValuesTAList.add(textArea7VennDiagram);
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
		setNameLabel1.setLocation(183, 25);


		setNameLabel2 = new JLabel("SET B");
		setNameLabel2.setLocation(550, 25);

		setNameLabel3 = new JLabel("SET C");
		setNameLabel3.setLocation(376, 506);

		universalSetNameLabel = new JLabel("U");
		universalSetNameLabel.setLocation(15, 13);

		setNameLabelList = new ArrayList<>();
		setNameLabelList.add(setNameLabel1);
		setNameLabelList.add(setNameLabel2);
		setNameLabelList.add(setNameLabel3);
		setNameLabelList.add(universalSetNameLabel);

		for (int i = 0; i < setNameLabelList.size(); i++) {
			setNameLabelList.get(i).setFont(new Font("Serif", Font.PLAIN, 25));
			setNameLabelList.get(i).setSize(labelSize);
			imagePanel.add(setNameLabelList.get(i));
			setNameLabelList.get(i).setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);
		}
	}

	private void paintImagePanel() {
		final Image image = VennUtils.requestImage(venn2Url);
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


