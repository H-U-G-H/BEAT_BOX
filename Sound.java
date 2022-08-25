import javax.sound.midi.*;

public class Sound
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
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            for (int i = 5; i < 61; i++)
            {
                track.add(makeEvent(144, 1, i, 100, i));
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
