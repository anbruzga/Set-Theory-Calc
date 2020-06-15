package Sets.View;


import Sets.Controler.TransferFocus;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NoTabTextArea extends JTextArea {

    public NoTabTextArea(){
        super();
        setFocusCycleRoot(true);
        this.addKeyListener(new NoTabKeyListener(this));
    }

    public NoTabTextArea(String name){
        super(name);
        TransferFocus.patch(this);
        this.addKeyListener(new NoTabKeyListener(this));
    }

    public class NoTabKeyListener implements KeyListener {
        JTextArea a;
        NoTabKeyListener(JTextArea textArea){
            a = textArea;
        }
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_TAB) {
                System.out.println(e.getModifiersEx());
                if (e.getModifiersEx() > 0) a.transferFocusBackward();
                else a.transferFocus();
                e.consume();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}