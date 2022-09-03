import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextAreaTest implements ActionListener
{
    JTextArea area;
    public static void main(String[] args)
    {
        TextAreaTest gui = new TextAreaTest();
        gui.go();
    }
    
    public void go()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 250);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        JPanel panel1 = new JPanel();

        JCheckBox checkBox = new JCheckBox("Goes to 11");
        JButton button = new JButton("Just click it");
        button.addActionListener(this);
        area = new JTextArea(10, 20);
        area.setLineWrap(true); // ПЕРЕНОС СТРОК (TRUE)

        JScrollPane scroller = new JScrollPane(area);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scroller);
        panel1.add(checkBox);
        frame.getContentPane().add(BorderLayout.NORTH, panel1);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, button);
    } // OUT OF GO CLASS

    public void actionPerformed(ActionEvent event)
    {
        area.append("button clicked \n");
    }
}
