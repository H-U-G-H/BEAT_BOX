import java.awt.*;
import javax.swing.*;

class MyDrawPanel extends JPanel // создаём личный виджет
{
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
        g2d.fillOval(90, 90, 100, 100);
    }
}
