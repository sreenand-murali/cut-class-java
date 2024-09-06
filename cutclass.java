import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AttendanceTracker extends JFrame {
    private JTextField classEnteredField, classTotalField, percentageField, toCutField;
    private JLabel resultLabel, cutResultLabel;
    private JButton cutPercentageButton;
    private int classEntered, classTotal, percentage, toCut;
    private boolean showCut;

    public AttendanceTracker() {
        setTitle("Attendance Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));

        add(new JLabel("CLASS RATIO:"));
        JPanel classRatioPanel = new JPanel(new GridLayout(1, 3));
        classEnteredField = new JTextField("0");
        classTotalField = new JTextField("0");
        classRatioPanel.add(classEnteredField);
        classRatioPanel.add(new JLabel("/"));
        classRatioPanel.add(classTotalField);
        add(classRatioPanel);


        JPanel targetPanel = new JPanel(new GridLayout(1, 3));
        targetPanel.add(new JLabel("TARGET:"));
        percentageField = new JTextField("75");
        targetPanel.add(percentageField);
        targetPanel.add(new JLabel("%"));
        add(targetPanel);

       
        JButton calculateButton = new JButton("Calculate");
        add(calculateButton);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAttendance();
            }
        });

       
        resultLabel = new JLabel(" ");
        add(resultLabel);

 
        JButton cutButton = new JButton("Cut Class");
        add(cutButton);
        cutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleCutClass();
            }
        });

      
        toCutField = new JTextField("0");
        add(toCutField);
        toCutField.setVisible(false);

        cutPercentageButton = new JButton("Calculate Cut Percentage");
        add(cutPercentageButton);
        cutPercentageButton.setVisible(false);
        cutPercentageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateCutPercentage();
            }
        });

        cutResultLabel = new JLabel(" ");
        add(cutResultLabel);
        cutResultLabel.setVisible(false);

        setVisible(true);
    }

    private void calculateAttendance() {
        classEntered = Integer.parseInt(classEnteredField.getText());
        classTotal = Integer.parseInt(classTotalField.getText());
        percentage = Integer.parseInt(percentageField.getText());

        int mainPercentage = Math.round(100 * classEntered / classTotal);
        int moreClass = (int) Math.ceil((100.0 * classEntered - percentage * classTotal) / (percentage - 100));
        moreClass = moreClass >= 0 ? moreClass : 0;

        int possibleCut = (int) Math.floor((100.0 * classEntered - classTotal * percentage) / percentage);
        possibleCut = possibleCut >= 0 ? possibleCut : 0;

        String color = mainPercentage >= percentage ? "Green" : "Red";
        resultLabel.setText("<html>Attendance: <span style='color:" + color + "'>" + mainPercentage + "%</span><br>" +
                "Enter " + moreClass + " more classes<br>" +
                "You can cut " + possibleCut + " classes</html>");
    }

    private void toggleCutClass() {
        showCut = !showCut;
        toCutField.setVisible(showCut);
        cutPercentageButton.setVisible(showCut);
        cutResultLabel.setVisible(false); 
    }

    private void calculateCutPercentage() {
        toCut = Integer.parseInt(toCutField.getText());
        int cutPercentage = Math.round(100 * classEntered / (classTotal + toCut));

        String color = cutPercentage >= percentage ? "Green" : "Red";
        cutResultLabel.setText("<html>After cutting, attendance will be: <span style='color:" + color + "'>" + cutPercentage + "%</span></html>");
        cutResultLabel.setVisible(true);
    }

    public static void main(String[] args) {
        new AttendanceTracker();
    }
}
