import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InnerButton
{
    JFrame frame;
    JButton button;

    public static void main(String[] args)
    {
        InnerButton gui = new InnerButton();
        gui.go();
    }

    public void go()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button = new JButton("NO");
        button.addActionListener(new ButtonListener());
        frame.getContentPane().add(BorderLayout.SOUTH, button);
        frame.setSize(200, 100);
        frame.setVisible(true);
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if (button.getText().equals("NO"))
            {
                button.setText("YES");
            }
            else
            {
                button.setText("NO");
            } // OUT OF IF-ELSE
        } // OUT OF METHOD
    }// OUT OF INNER CLASS
} // OUT OF OUTER CLASS
