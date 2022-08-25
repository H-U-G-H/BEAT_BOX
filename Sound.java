import javax.sound.midi.*;

public class Sound implements ControllerEventListener
{
    public static void main(String[] args)
    {
        Sound sound = new Sound();
        sound.play();
    }

    public void play()
    {
        try
        {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            int[] events = {127};
            player.addControllerEventListener(this, events);

            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            for (int i = 5; i < 60; i++)
            {
                track.add(makeEvent(144, 1, i, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, i, 100, ++i));
            }

            player.setSequence(sequence);
            player.setTempoInBPM(220);
            player.start();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void controlChange(ShortMessage event)
    {
        System.out.println("ЛЯ");
    }

    public static MidiEvent makeEvent (int cmd, int chan, int one, int two, int tick)
    {
        MidiEvent event = null;

        try
        {
            ShortMessage message = new ShortMessage();
            message.setMessage(cmd, chan, one, two);
            event = new MidiEvent(message, tick);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }

        return event;
    }
}
