package Sets.View;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomButton extends JButton {
    private Color pressedColor;
    private Color normalColor;

    public CustomButton (Color normalColor, Color pressedColor) {
        super();

        this.setBackground(normalColor);
        setFocusPainted(false);
        this.pressedColor = pressedColor;
        this.normalColor = normalColor;

        setContentAreaFilled(false);
        setOpaque(true);
        //setBounds(x_pos, y_pos, 30, 25);
        setBorder(new RoundedBorder(5)); //10 is the radius


        addChangeListener(evt -> {
            if (getModel().isPressed()) {
                setBackground(pressedColor);
            } else {
                setBackground(normalColor);
            }
        });

        addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
              setBackground(pressedColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(normalColor);
            }
        });
    }

    public CustomButton (Color normalColor, Color pressedColor, int roundRadius) {
        super();

        this.setBackground(normalColor);
        setFocusPainted(false);
        this.pressedColor = pressedColor;
        this.normalColor = normalColor;

        setContentAreaFilled(false);
        setOpaque(true);
        //setBounds(x_pos, y_pos, 30, 25);
        setBorder(new RoundedBorder(roundRadius)); //10 is the radius


        addChangeListener(evt -> {
            if (getModel().isPressed()) {
                setBackground(pressedColor);
            } else {
                setBackground(normalColor);
            }
        });


        addFocusListener(new FocusListener(){

            @Override
            public void focusGained(FocusEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(normalColor);
            }
        });
    }

   // https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java
    private static class RoundedBorder implements Border {

        private int radius;


        RoundedBorder(int radius) {
            this.radius = radius;
        }


        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }


        public boolean isBorderOpaque() {
            return true;
        }


        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

}