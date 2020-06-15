package Sets.View;


import javax.swing.*;


public class PopUp {

    /****************************************************
     * Reference: https://stackoverflow.com/questions/7080205/popup-message-boxes
     * Username: Troyseph
     * Profile Link: https://stackoverflow.com/users/921224/troyseph
     ****************************************************/
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar,
                JOptionPane.INFORMATION_MESSAGE);
    }
    // To Call:
    // PopUp.infoBox("YOUR INFORMATION HERE", "TITLE BAR MESSAGE");
    /********************************************************/

    public static boolean confirmBox(String infoMessage, String titleBar) {
        int dialogResult = JOptionPane.showConfirmDialog(null, infoMessage,
                "confirmBox: " + titleBar,
                JOptionPane.YES_NO_OPTION);

        if(dialogResult == 0 ){ // yes option
            return true;
        }
        else return false; // no option
    }
}


