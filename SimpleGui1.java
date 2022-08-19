import javax.swing.*;

public class SimpleGui1
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        JButton button = new JButton("CLICK ME");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // завершение программы при закрытии окна

        frame.getContentPane().add(button); // добавляем кнопку на панель фрейма
        frame.setSize(300, 300); // размер фрейма в пикселях
        frame.setVisible(true); // делаем фрейм видимым
    }
}
