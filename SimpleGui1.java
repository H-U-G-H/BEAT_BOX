import javax.swing.*;
import java.awt.event.*; // здесь хранятся ActionListener и ActionEvent

public class SimpleGui1 implements ActionListener
{
    JButton button;

    public static void main(String[] args)
    {
        SimpleGui1 gui = new SimpleGui1();
        gui.go();
    }
    public void go()
    {
        JFrame frame = new JFrame();
        button = new JButton("CLICK ME");

        button.addActionListener(this); // аргумент ДОЛЖЕН реализовывать Action Listener

        frame.getContentPane().add(button); // добавляем кнопку на панель фрейма
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // завершение программы при закрытии окна
        frame.setSize(300, 300); // размер фрейма в пикселях
        frame.setVisible(true); // делаем фрейм видимым
    }
        public void actionPerformed(ActionEvent event)
        {
            button.setText("I'VE BEEN CLICKED");
        }
}
