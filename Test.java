import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test
{
    JFrame frame;
    JPanel panel;
    String [] listEntries = {"альфа", "бета", "гамма", "дельта", "эпсилон", "зета", "ета", "тета"};
    JList list = new JList(listEntries);

    public static void main(String[] args)
    {
        Test gui = new Test();
        gui.go();
    }
    
    public void go()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(250, 250);
        frame.setVisible(true);

        panel = new JPanel();
        JScrollPane scroller = new JScrollPane(list);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        list.setVisibleRowCount(4);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        panel.add(scroller);
        frame.getContentPane().add(BorderLayout.CENTER, panel);

    } // OUT OF GO CLASS
}
