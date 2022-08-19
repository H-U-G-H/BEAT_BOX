import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class TwoButtons // OUTER CLASS
{
    JFrame frame;
    JLabel label;

    public static void main(String[] args)
    {
        TwoButtons gui = new TwoButtons();
        gui.go();
    }

    public void go()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton labelButton = new JButton("CHANGE LABEL");
        labelButton.addActionListener(new LabelListener());

        JButton colorButton = new JButton("CHANGE COLOR");
        colorButton.addActionListener(new ColorListener());

        label = new JLabel("I AM A LABEL");
        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(BorderLayout.SOUTH, colorButton);
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.getContentPane().add(BorderLayout.EAST, labelButton);
        frame.getContentPane().add(BorderLayout.WEST, label);

        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    class LabelListener implements ActionListener // INNER CLASS
    {
        public void actionPerformed(ActionEvent event)
        {
            label.setText("OUCH!");
        }
    } // OUT OF OUTER CLASS

    class ColorListener implements ActionListener // INNER CLASS
    {
        public void actionPerformed(ActionEvent event)
        {
            frame.repaint();
        }
    } // OUT OF INNER CLASS
} // OUT OF OUTER CLASS
