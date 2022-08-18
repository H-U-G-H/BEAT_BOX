import javax.sound.midi.*;

public class MiniMiniMusicApp
{
    public static void main(String[] args)
    {
        MiniMiniMusicApp mini = new MiniMiniMusicApp();
        mini.play();
    } //OUT OF MAIN

    public void play()
    {
        try {
            Sequencer player = MidiSystem.getSequencer(); // получаем синтезатор
            player.open(); // открываем его, чтобы начать использовать

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack(); // трек содержится внутри последовательности

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, 44, 100);
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, 44, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            player.setSequence(seq); // передаём последовательность синтезатору (вставляем CD)
            player.start(); // запускаем синтезатор
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } // OUT OF TRY-CATCH
    } // OUT OF METHOD
} // OUT OF CLASS
