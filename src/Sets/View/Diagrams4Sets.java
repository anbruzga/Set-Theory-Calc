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

@SuppressWarnings("serial")
public class Diagrams4Sets extends JFrame {

	private String venn4Location = "/Resources/Images/4VennColoursV2.png";

	private String clickedValTADescri = "Click a part of the " +
			"diagram and all the values of it will appear here.";
	private String textArea1Descri = "This TextArea displays what is in currently selected area.";
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
	private JTextArea textArea8VennDiagram;
	private JTextArea textArea9VennDiagram;
	private JTextArea textArea10VennDiagram;
	private JTextArea textArea11VennDiagram;
	private JTextArea textArea12VennDiagram;
	private JTextArea textArea13VennDiagram;
	private JTextArea textArea14VennDiagram;
	private JTextArea textArea15VennDiagram;
	private JTextArea textAreaUniversal;

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

	private JTextArea clickedValuesTextArea;
	private JScrollPane scrollPaneTA1;

	private final MouseMotionListener motionListener;
	private final MouseListener mouseExitListener;
	private final MouseListener mouseListener;

	private ArrayList<MixedSet> sets;
	private ArrayList<MixedSet> setsListOriginal;
	private ArrayList<JLabel> setNameLabelList;
	private ArrayList<JTextArea> setValuesTAList;
	private ArrayList<Color> colorList;
	private ArrayList<MixedSet> listOfSetParts;
	private MixedSet tArea2Set;

	private JLabel setNameLabel1;
	private JLabel setNameLabel2;
	private JLabel setNameLabel3;
	private JLabel setNameLabel4;
	private JLabel universalSetNameLabel;

	public Diagrams4Sets() {
		super("Venn Diagrams 4 Sets");
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
		frame.setTitle("Venn Diagram 2 Sets");
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
        colorList.add(color8);
        colorList.add(color9);
        colorList.add(color10);
        colorList.add(color11);
        colorList.add(color12);
        colorList.add(color13);
        colorList.add(color14);
        colorList.add(color15);
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

        setVennTextAreasCharLimit(textArea1VennDiagram, 70);
        setVennTextAreasCharLimit(textArea2VennDiagram, 70);
        setVennTextAreasCharLimit(textArea3VennDiagram, 65);
        setVennTextAreasCharLimit(textArea4VennDiagram, 62);
        setVennTextAreasCharLimit(textArea5VennDiagram, 10);
        setVennTextAreasCharLimit(textArea6VennDiagram, 10);
        setVennTextAreasCharLimit(textArea7VennDiagram, 62);
        setVennTextAreasCharLimit(textArea8VennDiagram, 11);
        setVennTextAreasCharLimit(textArea9VennDiagram, 11);
        setVennTextAreasCharLimit(textArea10VennDiagram, 30);
        setVennTextAreasCharLimit(textArea11VennDiagram, 11);
        setVennTextAreasCharLimit(textArea12VennDiagram, 11);
        setVennTextAreasCharLimit(textArea13VennDiagram, 10);
        setVennTextAreasCharLimit(textArea14VennDiagram, 10);
        setVennTextAreasCharLimit(textArea15VennDiagram, 29);
    }

	private void countEachArea() {
		MixedSet A = sets.get(0);
		MixedSet B = sets.get(1);
		MixedSet C = sets.get(2);
		MixedSet D = sets.get(3);

		/*  (A) (B)
		 *  (D) (C)
		 */

		MixedSet answ1 = null;
		answ1 = A.difference(B).difference(C).difference(D);
		answ1.setName("Part1");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ2;
		answ2 = B.difference(A).difference(D).difference(C);
		answ2.setName("Part2");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ3;
		answ3 = A.intersection(B).difference(C).difference(D);
		answ3.setName("Part3");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ4;
		answ4 = D.difference(C).difference(B).difference(A);
		answ4.setName("Part4");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);


		MixedSet answ5;
		answ5 = A.intersection(D).difference(C).difference(B);
		answ5.setName("Part5");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ6;
		answ6 = C.intersection(D).difference(A).difference(B);
		answ6.setName("Part6");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ7;
		answ7 = C.difference(A).difference(B).difference(D);
		answ7.setName("Part7");
        reloadOriginalSetList(sets, setsListOriginal);
        A = sets.get(0);
        B = sets.get(1);
        C = sets.get(2);
        D = sets.get(3);

		MixedSet answ8;
		answ8 = C.intersection(A).intersection(B).difference(D);
		answ8.setName("Part8");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ9;
		answ9 = C.intersection(A).intersection(B).difference(D);
		answ9.setName("Part9");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ10;
		answ10 = A.intersection(B).intersection(C).intersection(D);
		answ10.setName("Part10");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		MixedSet answ11;
		answ11 = B.intersection(D).difference(A).difference(C);
		answ11.setName("Part11");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ12;
		answ12 = A.intersection(C).difference(B).difference(D);
		answ12.setName("Part12");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ13;
		answ13 = D.intersection(C).intersection(B).difference(A);
		answ13.setName("Part13");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ14;
		answ14 = D.intersection(C).intersection(A).difference(B);
		answ14.setName("Part14");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ15;
		answ15 = D.intersection(C).difference(A).difference(B);
		answ15.setName("Part15");
		reloadOriginalSetList(sets, setsListOriginal);
		A = sets.get(0);
		B = sets.get(1);
		C = sets.get(2);
		D = sets.get(3);

		MixedSet answ16;
		answ16 = A.union(B).union(C).union(D).complement();
		answ16.setName("U");
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
        listOfSetParts.add(answ9);
        listOfSetParts.add(answ10);
        listOfSetParts.add(answ11);
        listOfSetParts.add(answ12);
        listOfSetParts.add(answ13);
        listOfSetParts.add(answ14);
        listOfSetParts.add(answ15);
        listOfSetParts.add(answ16);

        for (int i = 0; i < listOfSetParts.size(); i++) {
            listOfSetParts.get(i).intersectionWithUniveralSet();
        }

	}


	private Diagrams4Sets create() {
		frame = createFrame();
		frame.setContentPane(panel);
		createContent();
		return this;
	}

	private JFrame createFrame() {
		JFrame frame = new JFrame(getClass().getName());
		//  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

    private void paintImagePanel() {
        final Image image = VennUtils.requestImage(venn4Location);
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
    private void addSetNameLabels() {
        setNameLabel1 = new JLabel("SET A");
        setNameLabel1.setLocation(196, 30);

        setNameLabel2 = new JLabel("SET B");
        setNameLabel2.setLocation(571, 30);

        setNameLabel3 = new JLabel("SET C");
        setNameLabel3.setLocation(710, 165);

        setNameLabel4 = new JLabel("SET D");
        setNameLabel4.setLocation(55, 165);

        universalSetNameLabel = new JLabel("U");
        universalSetNameLabel.setLocation(15, 13);

        setNameLabelList = new ArrayList<>();
        setNameLabelList.add(setNameLabel1);
        setNameLabelList.add(setNameLabel2);
        setNameLabelList.add(setNameLabel3);
        setNameLabelList.add(setNameLabel4);
        setNameLabelList.add(universalSetNameLabel);

        Dimension labelSize = new Dimension(80, 30);
        for (int i = 0; i < setNameLabelList.size(); i++) {
            setNameLabelList.get(i).setFont(new Font("Serif", Font.PLAIN, 20));
            setNameLabelList.get(i).setSize(labelSize);
            imagePanel.add(setNameLabelList.get(i));
            setNameLabelList.get(i).setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);
        }
    }
	private void createUIComponents() {

        paintImagePanel();

        imagePanel.setLayout(null);

        addSetNameLabels();

        addSetValuesTextAreas();

		imagePanel.setLayout(null);

		closeButton1 =  new CustomButton(new Color(255, 197, 183),
				new Color(235, 178, 170));

	}

    private void addSetValuesTextAreas() {

        textArea1VennDiagram = new JTextArea();
        textArea1VennDiagram.setLocation(175, 60);
        textArea1VennDiagram.setSize(150, 80);

        textArea2VennDiagram = new JTextArea();
        textArea2VennDiagram.setLocation(503, 50);
        textArea2VennDiagram.setSize(150, 90);

        textArea3VennDiagram = new JTextArea();
        textArea3VennDiagram.setLocation(352, 165);
        textArea3VennDiagram.setSize(140, 90);

        textArea4VennDiagram = new JTextArea();
        textArea4VennDiagram.setLocation(38, 230);
        textArea4VennDiagram.setSize(110, 100);

        textArea5VennDiagram = new JTextArea();
        textArea5VennDiagram.setLocation(164, 180);
        textArea5VennDiagram.setSize(65, 45);

        textArea6VennDiagram = new JTextArea();
        textArea6VennDiagram.setLocation(587, 180);
        textArea6VennDiagram.setSize(65, 45);

        textArea7VennDiagram = new JTextArea();
        textArea7VennDiagram.setLocation(660, 230);
        textArea7VennDiagram.setSize(110, 100);


        textArea8VennDiagram = new JTextArea();
        textArea8VennDiagram.setLocation(241, 243);
        textArea8VennDiagram.setSize(71, 43);


        textArea9VennDiagram = new JTextArea();
        textArea9VennDiagram.setLocation(491, 243);
        textArea9VennDiagram.setSize(71, 43);


        textArea10VennDiagram = new JTextArea();
        textArea10VennDiagram.setFont(new Font("Serif", Font.BOLD, 10));
        textArea10VennDiagram.setLocation(375, 330);
        textArea10VennDiagram.setSize(70, 45);

        textArea11VennDiagram = new JTextArea();
        textArea11VennDiagram.setLocation(245, 340);
        textArea11VennDiagram.setSize(70, 45);

        textArea12VennDiagram = new JTextArea();
        textArea12VennDiagram.setLocation(505, 340);
        textArea12VennDiagram.setSize(78, 45);


        textArea13VennDiagram = new JTextArea();
        textArea13VennDiagram.setLocation(329, 383);
        textArea13VennDiagram.setFont(new Font("Serif", Font.BOLD, 10));
        textArea13VennDiagram.setSize(50, 30);


        textArea14VennDiagram = new JTextArea();
        textArea14VennDiagram.setLocation(433, 383);
        textArea14VennDiagram.setFont(new Font("Serif", Font.BOLD, 10));
        textArea14VennDiagram.setSize(50, 30);

        textArea15VennDiagram = new JTextArea();
        textArea15VennDiagram.setLocation(350, 445);
        textArea15VennDiagram.setSize(120, 65);

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
        setValuesTAList.add(textArea8VennDiagram);
        setValuesTAList.add(textArea9VennDiagram);
        setValuesTAList.add(textArea10VennDiagram);
        setValuesTAList.add(textArea11VennDiagram);
        setValuesTAList.add(textArea12VennDiagram);
        setValuesTAList.add(textArea13VennDiagram);
        setValuesTAList.add(textArea14VennDiagram);
        setValuesTAList.add(textArea15VennDiagram);
        setValuesTAList.add(textAreaUniversal);

        for (int i = 0; i < setValuesTAList.size(); i++) {
            VennUtils.centerText(setValuesTAList.get(i));
            configTextAreasInVenn(setValuesTAList.get(i));
            imagePanel.add(setValuesTAList.get(i));
        }
    }

	private void configTextAreasInVenn(JTextArea tArea) {
		tArea.setBackground(colorUniversalSet);
		tArea.setFont(new Font("Serif", Font.BOLD, 15));
		tArea.setOpaque(false);
        tArea.setForeground(NOT_SELECTED_COLOR);
		tArea.setEditable(false);
		tArea.setLineWrap(true);
		imagePanel.add(tArea);
	}


}

