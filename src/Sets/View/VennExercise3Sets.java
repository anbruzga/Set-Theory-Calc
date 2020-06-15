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

import static Sets.Controler.VennUtils.*;
import static Sets.View.Styles.*;
import static Sets.View.Styles.DIAGRAMS_EXERCISE_TEXTAREA_DIM;


//https://stackoverflow.com/questions/22217148/get-pixel-color-on-screen-java
@SuppressWarnings("serial")
public class VennExercise3Sets extends JFrame {


	private static final int GUESS_TEXT_AREA_LIMIT = 300;
	private String venn2Url = "/Resources/Images/3VennColoursV1.png";


    private String guessTextAreaDescri = "Click on Venn Diagram. To find set  {5}\ntry typing A "
            + Symbols.intersection + " B " + Symbols.intersection + "C";
    private String feedbackTextAreaDescri = "After clicking \"Check!\", here you will\nfind feedback!";

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

    private JTextArea universalSetValuesTextArea;

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
    private JLabel setNameLabel3;
    private JLabel universalSetNameLabel;

    private ArrayList<JButton> buttonList;
    private ArrayList<JLabel> setNameLabelList;
    private ArrayList<JTextArea> vennValuesTextAreasList;

    private final MouseMotionListener motionListener;
    private final MouseListener mouseExitListener;
    private final MouseListener mouseListener;

    private Dimension dimension;

    // CONSTRUCTOR
    public VennExercise3Sets(Dimension dimension) {
        super();

        this.dimension = dimension;
        sets = new ArrayList<>();
        setsListOriginal = new ArrayList<>();
        toFindSet = new MixedSet(false);
        toFindSet.setName("ClickedAreasSet");
        guessTextArea.setDocument(new PlainDocumentLimited(GUESS_TEXT_AREA_LIMIT));
        feedbackTextArea.setText(feedbackTextAreaDescri);
        guessTextArea.setText(guessTextAreaDescri);
        feedbackTextArea.setLineWrap(true);
        guessTextArea.setLineWrap(true);

        this.create();
        this.showFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setTitle("Venn Exercises 3 Sets");
        frame.pack();



        mouseListener = new MouseListener() {
            //ReentrantLock lock = new ReentrantLock();

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
                    colourChangeBackOnHover(vennValuesTextAreasList.get(i), numbersToFindLabel);
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
        setNameLabel1.addMouseMotionListener(motionListener);
        setNameLabel2.addMouseMotionListener(motionListener);
        setNameLabel3.addMouseMotionListener(motionListener);
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

            if (guess == null || guess.equals("") || guess.equals(feedbackTextAreaDescri)){
                PopUp.infoBox("Enter the formula to find selected areas","VennExercise3.Click");
            }

            guess = SyntaxAnalyser.cutSpaces(guess);

            MixedSet A = sets.get(0);
            MixedSet B = sets.get(1);
            MixedSet C = sets.get(2);
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
            System.out.println("conversionGuessToSets = " + conversionGuessToSets);
            try {
                guessSet =  Calculate.evaluate(conversionGuessToSets.toString());
            } catch (IncorrectInputSyntaxException ex) {
                ex.printStackTrace();
                return;
            }
            MixedSet universalBase = new MixedSet(false);
            universalBase.add(new IntRange(1, 8));
            UniversalSet universalSet = UniversalSet.getInstance(universalBase, 8, 1, 'N');
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
                if(guessTextArea.getText().equals(guessTextAreaDescri)){
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
                if(guessTextArea.getText().equals(guessTextAreaDescri)){
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
                if(guessTextArea.getText().equals(guessTextAreaDescri)){
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
        easyButton.addActionListener(e -> {

                VennUtils.startVennExercises2(panel.getSize());
                frame.dispose();

        });

        hardButton.addActionListener(e ->{
                VennUtils.startVennExercises4(panel.getSize());
                frame.dispose();

        });
    } // CONSTRUCTOR ENDS



    private void mouseClickedAction(MouseEvent e) {
        loadingLabel.setForeground(Color.BLACK);
        loadingLabel.setText("LOADING");

        MixedSet universalBase = new MixedSet(false);
        universalBase.add(new IntRange(1, 8));
        UniversalSet.getInstance(universalBase, 8, 1, 'N');

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


            Color pixelColor = getPixel(p.x, p.y);

            System.out.println("pixelColor.getRGB() = " + pixelColor.getRGB());
            System.out.println("color6 = " + color6.getRGB());

            if (pixelColor.equals(color1)) {
                answ = A.difference(B).difference(C);
                System.out.println("1  area clicked");
                setTextAreaValuesInVenn(answ, textArea1VennDiagram);
                answ.setName("Part1");

            } else if (pixelColor.equals(color2)) {
                answ = B.intersection(A).difference(C);
                System.out.println("2  area clicked");
                setTextAreaValuesInVenn(answ, textArea2VennDiagram);
                answ.setName("Part2");

            } else if (pixelColor.equals(color3)) {
                answ = B.difference(A).difference(C);
                System.out.println("3  area clicked");
                setTextAreaValuesInVenn(answ, textArea3VennDiagram);
                answ.setName("Part3");

            } else if (pixelColor.equals(color4)) {
                answ = A.intersection(C).difference(B);
                System.out.println("4  area clicked");
                setTextAreaValuesInVenn(answ, textArea4VennDiagram);
                answ.setName("Part4");

            } else if (pixelColor.equals(color5)) {
                System.out.println("5  area clicked");
                answ = A.intersection(C).intersection(B);
                setTextAreaValuesInVenn(answ, textArea5VennDiagram);
                answ.setName("Part5");

            } else if (pixelColor.getRGB() == (color6.getRGB())) {
                System.out.println("6  area clicked");
                answ = B.intersection(C).difference(A);
                setTextAreaValuesInVenn(answ, textArea6VennDiagram);
                answ.setName("Part6");

            } else if (pixelColor.equals(color7)) {
                answ = C.difference(B).difference(A);
                System.out.println("7  area clicked");
                setTextAreaValuesInVenn(answ, textArea7VennDiagram);
                answ.setName("Part7");

            } else if (pixelColor.equals(colorUniversalSet)) {
                answ = A.union(B).union(C).complement();
                System.out.println("8  area clicked");
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
            if (pixelColour.equals(color4)) {
                colourChangeOnHover(textArea4VennDiagram);
            } else if (!pixelColourDistortedRight.equals(color4) &&  !pixelColourDistortedLeft.equals(color4) ){
                colourChangeBackOnHover(textArea4VennDiagram, numbersToFindLabel);
            }
            if (pixelColour.equals(color5)) {
                colourChangeOnHover(textArea5VennDiagram);
            } else if (!pixelColourDistortedRight.equals(color5) &&  !pixelColourDistortedLeft.equals(color5) ){
                colourChangeBackOnHover(textArea5VennDiagram, numbersToFindLabel);
            }
            if (pixelColour.equals(color6)) {
                colourChangeOnHover(textArea6VennDiagram);
            } else if (!pixelColourDistortedRight.equals(color6) &&  !pixelColourDistortedLeft.equals(color6) ){
                colourChangeBackOnHover(textArea6VennDiagram, numbersToFindLabel);
            }
            if (pixelColour.equals(color7)) {
                colourChangeOnHover(textArea7VennDiagram);
            } else if (!pixelColourDistortedRight.equals(color7) &&  !pixelColourDistortedLeft.equals(color7) ){
                colourChangeBackOnHover(textArea7VennDiagram, numbersToFindLabel);
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
        if(numbersToFindLabel.getText().contains(area.getText())){
            toFindSet = toFindSet.difference(answ);
        }
        else {
            numbersToFindLabel.setText(answ.toStringNoName(false));
            toFindSet = toFindSet.union(answ);
        }
    }



    private void setTextAreaVennValues() {
        MixedSet A = sets.get(0);
        MixedSet B = sets.get(1);
        MixedSet C = sets.get(2);

        /*(A)  (B)
         *  (C)
         */

        MixedSet answ1;

        answ1 = A.difference(B).difference(C);
        answ1.setName("Part1");
        textArea1VennDiagram.setText(SetUtils.setStringCutEnds(answ1));
        reloadOriginalSetList(sets, setsListOriginal);
        A = sets.get(0);
        B = sets.get(1);
        C = sets.get(2);



        MixedSet answ2;
        answ2 = B.intersection(A);
        answ2.difference(C);
        answ2.setName("Part2");
        textArea2VennDiagram.setText(SetUtils.setStringCutEnds(answ2));
        reloadOriginalSetList(sets, setsListOriginal);
        A = sets.get(0);
        B = sets.get(1);
        C = sets.get(2);

        MixedSet answ3;
        answ3 = B.difference(A);
        answ3.difference(C);
        answ3.setName("Part3");
        textArea3VennDiagram.setText(SetUtils.setStringCutEnds(answ3));
        reloadOriginalSetList(sets, setsListOriginal);
        A = sets.get(0);
        B = sets.get(1);
        C = sets.get(2);

        MixedSet answ4;
        answ4 = A.intersection(C);
        answ4.difference(B);
        answ4.setName("Part4");
        textArea4VennDiagram.setText(SetUtils.setStringCutEnds(answ4));
        reloadOriginalSetList(sets, setsListOriginal);
        A = sets.get(0);
        B = sets.get(1);
        C = sets.get(2);

        MixedSet answ5;
        answ5 = A.intersection(C);
        answ5.intersection(B);
        answ5.setName("Part5");
        textArea5VennDiagram.setText(SetUtils.setStringCutEnds(answ5));
        reloadOriginalSetList(sets, setsListOriginal);
        A = sets.get(0);
        B = sets.get(1);
        C = sets.get(2);

        MixedSet answ6;
        answ6 = B.intersection(C);
        answ6.difference(A);
        answ6.setName("Part6");
        textArea6VennDiagram.setText(SetUtils.setStringCutEnds(answ6));
        reloadOriginalSetList(sets, setsListOriginal);
        A = sets.get(0);
        B = sets.get(1);
        C = sets.get(2);

        MixedSet answ7;
        answ7 = C.difference(B);
        answ7.difference(A);
        answ7.setName("Part7");
        textArea7VennDiagram.setText(SetUtils.setStringCutEnds(answ7));
        reloadOriginalSetList(sets, setsListOriginal);
        A = sets.get(0);
        B = sets.get(1);
        C = sets.get(2);


        MixedSet universalBase = new MixedSet(false);
        universalBase.add(new IntRange(1, 8));
        UniversalSet universalSet = UniversalSet.getInstance(universalBase, 8, 1, 'N');

        MixedSet answ8;
        answ8 = A.union(B).union(C).complement();
        answ8.setName("U");
        universalSetNameLabel.setText("U");
        universalSetValuesTextArea.setText(SetUtils.setStringCutEnds(answ8));


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
        reloadOriginalSetList(sets, setsListOriginal);


    }


    private VennExercise3Sets create() {
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
            panel.setPreferredSize(new Dimension(1297, 618));
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

        setTextAreaVennValues();

        setNameLabel1.setText("A");
        setNameLabel2.setText("B");
        setNameLabel3.setText("C");

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
        setNameLabel1.setFont(new Font("Serif", Font.PLAIN, 50));
        imagePanel.add(setNameLabel1);
        setNameLabel1.setLocation(183, 32);
        setNameLabel1.setSize(80, 50);
        setNameLabel1.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

        setNameLabel2 = new JLabel("SET B");
        setNameLabel2.setFont(new Font("Serif", Font.PLAIN, 50));
        imagePanel.add(setNameLabel2);
        setNameLabel2.setLocation(550, 32);
        setNameLabel2.setSize(80, 50);
        setNameLabel2.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

        setNameLabel3 = new JLabel("SET C");
        setNameLabel3.setFont(new Font("Serif", Font.PLAIN, 50));
        imagePanel.add(setNameLabel3);
        setNameLabel3.setLocation(378, 487);
        setNameLabel3.setSize(80, 50);
        setNameLabel3.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

        universalSetNameLabel = new JLabel();
        universalSetNameLabel.setFont(new Font("Serif", Font.PLAIN, 40));
        imagePanel.add(universalSetNameLabel);
        universalSetNameLabel.setLocation(20, 7);
        universalSetNameLabel.setSize(80, 50);
        universalSetNameLabel.setForeground(SET_NAME_VENN_DIAGRAM_COLOUR);

        setNameLabelList = new ArrayList<>();
        setNameLabelList.add(setNameLabel1);
        setNameLabelList.add(setNameLabel2);
        setNameLabelList.add(setNameLabel3);
        setNameLabelList.add(universalSetNameLabel);

        textArea1VennDiagram = new JTextArea();
        configTextAreasInVennStyle(textArea1VennDiagram);
        textArea1VennDiagram.setLocation(110, 94);
        textArea1VennDiagram.setSize(50, 105);

        textArea2VennDiagram = new JTextArea();
        configTextAreasInVennStyle(textArea2VennDiagram);
        textArea2VennDiagram.setLocation(378, 102);
        textArea2VennDiagram.setSize(143, 40);

        textArea3VennDiagram = new JTextArea();
        configTextAreasInVennStyle(textArea3VennDiagram);
        textArea3VennDiagram.setLocation(560, 94);
        textArea3VennDiagram.setSize(200, 95);

        textArea4VennDiagram = new JTextArea();
        configTextAreasInVennStyle(textArea4VennDiagram);
        textArea4VennDiagram.setLocation(215, 265);
        textArea4VennDiagram.setSize(105, 100);

        textArea5VennDiagram = new JTextArea();
        configTextAreasInVennStyle(textArea5VennDiagram);
        textArea5VennDiagram.setLocation(378, 203);
        textArea5VennDiagram.setSize(180, 70);

        textArea6VennDiagram = new JTextArea();
        configTextAreasInVennStyle(textArea6VennDiagram);
        textArea6VennDiagram.setLocation(532, 265);
        textArea6VennDiagram.setSize(112, 100);

        textArea7VennDiagram = new JTextArea();
        configTextAreasInVennStyle(textArea7VennDiagram);
        textArea7VennDiagram.setLocation(378, 400);
        textArea7VennDiagram.setSize(200, 110);


        universalSetValuesTextArea = new JTextArea();
        configTextAreasInVennStyle(universalSetValuesTextArea);
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
        hardButton = new CustomButton(LIGHT_GREEN, DARK_GREEN);
        unionButton = new CustomButton(LIGHT_GREEN, DARK_GREEN);
        mediumButton = new CustomButton(new Color(117, 255, 140),
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

    private void configTextAreasInVennStyle(JTextArea tArea) {
        tArea.setFont(new Font("Serif", Font.PLAIN, 40));
        tArea.setForeground(NOT_SELECTED_COLOR);
        tArea.setOpaque(false);
        tArea.setEditable(false);
        tArea.setLineWrap(true);
        tArea.setPreferredSize(new Dimension(50, 50));
        imagePanel.add(tArea);
    }


}

