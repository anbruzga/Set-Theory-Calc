package Sets.View;

import Sets.Model.TableData;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class StatsView extends JFrame {
    private JPanel panel1;
    private JTable table;
    private JFrame frame;
    private Font fontBig = new Font("Monospaced", Font.PLAIN, 25);
    private Font fontMedium = new Font("Monospaced", Font.PLAIN, 19);
    private Font fontMediumBold = new Font("Monospaced", Font.BOLD, 19);
    private Font fontSmall = new Font("Monospaced", Font.PLAIN, 15);
    public StatsView() {
        super();
        frame = this;
        frame.setTitle("My Stats");
        frame.setContentPane(panel1);


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    private void createUIComponents() {

        String fullPath =  ExerciseView.getSavedDataPath();
        System.out.println("fullPath = " + fullPath);
        String headers[] = {"Exercise", "Did Succeed?", "No Failed",
                "Accuracy (%), ", "Gave Up?"};
        TableData dataTable = null;
        try {
            FileInputStream fis = new FileInputStream(fullPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            dataTable = (TableData) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object[][] data;
        if (dataTable == null) {
            data = new Object[][]{{"No Data", "No Data", "No Data", "No Data", "No Data"}};
        } else {
            int rows = dataTable.getRows();
            int cols = headers.length;
            // Object[][] data = new Object[4][rows];
            data = new Object[rows][cols];
            for (int j = 0; j < rows; j++) {
                data[j][0] = dataTable.getLine(j).getExercise();
                data[j][1] = dataTable.getLine(j).isSuccess();
                data[j][2] = dataTable.getLine(j).getNoOfFails();
                data[j][3] = dataTable.getLine(j).getPercentage();
                data[j][4] = dataTable.getLine(j).isAnswerCheckBeforeSuccess();
            }
        }

        table = new JTable(data, headers);
        table.setDefaultEditor(Object.class, null);

        table.setFont(fontMedium);
        table.validate();
        table.repaint();
    }
}
