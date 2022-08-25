import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer
{
    static JFrame frame = new JFrame("МОЙ ПЕРВЫЙ МУЗЫКАЛЬНЫЙ КЛИП");
    static DrawPanel panel;

    public static void main(String[] args)
    {
        MiniMusicPlayer mini = new MiniMusicPlayer();
        mini.go();
    } // OUT OF MAIN METHOD

    public void setUpGui()
    {
        panel = new DrawPanel();
        frame.setContentPane(panel);
        frame.setBounds(30, 30, 300, 300);
        frame.setVisible(true);
    } // OUT OF SETUP METHOD

    public void go()
    {
        setUpGui();

        try
        {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(panel, new int[] {127});
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            int r = 0;

            for (int i = 0; i < 60; i += 4)
            {
                r = (int)((Math.random() * 50) + 1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i + 2));
            } // OUT OF LOOP
            sequencer.setSequence(sequence);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        } // OUT OF TRY-CATCH
    } // OUT OF GO METHOD

    public MidiEvent makeEvent (int comd, int chan, int one, int two, int tick)
    {
        MidiEvent event = null;

        try
        {
            ShortMessage message = new ShortMessage();
            message.setMessage(comd, chan, one, two);
            event = new MidiEvent(message, tick);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }

        return event;
    } // OUT OF METHOD

    class DrawPanel extends JPanel implements ControllerEventListener
    {
        boolean msg = false;

        public void controlChange(ShortMessage event)
        {
            msg = true;
            repaint();
        }

        public void paintComponent (Graphics g)
        {
            if (msg)
            {
                Graphics2D g2d = (Graphics2D) g;

                int red = (int) (Math.random() * 250);
                int green = (int) (Math.random() * 250);
                int blue = (int) (Math.random() * 250);

                g.setColor(new Color(red, green, blue));

                int height = (int) ((Math.random() * 120) + 10);
                int width = (int) ((Math.random()* 120) + 10);
                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);

                g.fillRect(x, y, height, width);

                msg = false;
            } //OUT OF IF
        } // OUT OF METHOD
    } // OUT OF INNER CLASS
}
