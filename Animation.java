import javax.swing.*;
import java.awt.*;

public class Animation
{
    int x = 70;
    int y = 70;

    public static void main(String[] args)
    {
        Animation animation = new Animation();
        animation.go();
    }

    public void go()
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);

        DrawPanel panel = new DrawPanel();
        frame.getContentPane().add(panel);

        for (int i = 0; i < 130; i++)
        {
            x++;
            y++;
            panel.repaint();

            try
            {
                Thread.sleep(50);
            }
            catch (Exception exception) {}
        }
    }

    class DrawPanel extends JPanel
    {
        public void paintComponent (Graphics g)
        {
            g.setColor(Color.white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.green);
            g.fillOval(x, y, 40, 40);
        }
    }
}
