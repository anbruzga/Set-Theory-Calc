package Sets.View;

import java.awt.*;

public class Styles {

	private Styles() {
	}

	protected final static Font FONT_LARGE = new Font("Monospace", Font.PLAIN, 25);

	final static Font FONT_MEDIUM = new Font("Monospaced", Font.PLAIN, 19);
	final static Font FONT_MEDIUM_BOLD = new Font("Monospaced", Font.BOLD, 19);
	final static Font FONT_SMALL = new Font("Monospaced", Font.PLAIN, 15);

	public final static Color NOT_SELECTED_COLOR = new Color(73, 15, 15);
	public final static Color SELECTED_COLOR = new Color(255, 0, 26);
	final static Color SET_NAME_VENN_DIAGRAM_COLOUR = new Color(67, 0, 14);


	final static Color LIGHT_GREEN = new Color(196, 255, 199);
	final static Color DARK_GREEN = new Color(177, 235, 179);
	final static Color BACKGROUND_COLOR = new Color(240, 240, 240);
	final static Color TEXT_AREA_COLOR = new Color(200, 0, 131);
	final static Font VENN_EXPLANATION_LABEL_FONT = new Font("Serif", Font.PLAIN, 14);
	final static Dimension DIAGRAMS_EXERCISE_TEXTAREA_DIM = new Dimension(150, 55);
	final static Color VENN_EXPLANATION_LABEL_COLOR = new Color(209, 93, 141);


	final static Dimension LOADING_LABEL_MIN = new Dimension(60, 40);
	final static Dimension LOADING_LABEL_PREF = new Dimension(115, 50);
	final static Dimension LOADING_LABEL_MAX = new Dimension(125, 40);


	private final static int width = 350;
	private final static int height = 50;

	final static Dimension NUMBERS_TO_FIND_LABEL_MIN = new Dimension(width, 40);
	final static Dimension NUMBERS_TO_FIND_LABEL_PREF = new Dimension(width, height);
	final static Dimension NUMBERS_TO_FIND_LABEL_MAX = new Dimension(height, width);

	final static Dimension NUMBERS_TO_FIND_PANEL_MIN = new Dimension(40, 40);
	final static Dimension NUMBERS_TO_FIND_PANEL_PREF = new Dimension(width, height);
	final static Dimension NUMBERS_TO_FIND_PANEL_MAX = new Dimension(height, width);

	final static Dimension EXPLANATION_LABEL_MIN = new Dimension(width, 25);
	final static Dimension EXPLANATION_LABEL_PREF = new Dimension(width, 25);
	final static Dimension EXPLANATION_LABEL_MAX = new Dimension(width, 25);


}