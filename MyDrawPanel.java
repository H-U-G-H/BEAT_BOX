import java.awt.*;
import javax.swing.*;

public class MyDrawPanel extends JPanel // создаём личный виджет
{
    public static void main(String[] args)
    {
        MyDrawPanel panel = new MyDrawPanel();
        panel.go();
    }

    public void go()
    {
        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) // Graphics2D замаскированный под Graphics
    {
        Graphics2D g2d = (Graphics2D) g;

        int red = (int) (Math.random() * 255); // рандомная насыщенность красного
        int green = (int) (Math.random() * 255); // рандомная насыщенность зеленого
        int blue = (int) (Math.random() * 255); // рандомная насыщенность синего
        Color startColor = new Color(red, green, blue);

        red = (int) (Math.random() * 255); // рандомная насыщенность красного
        green = (int) (Math.random() * 255); // рандомная насыщенность зеленого
        blue = (int) (Math.random() * 255); // рандомная насыщенность синего
        Color endColor = new Color(red, green, blue);

        GradientPaint gradient = new GradientPaint(70, 70, startColor, 150, 150, endColor);
        g2d.setPaint(gradient);
        g2d.fillOval(70, 70, 100, 100);
    }
}
