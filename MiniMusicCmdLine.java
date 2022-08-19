import javax.sound.midi.*;

public class MiniMusicCmdLine
{
    public static void main(String[] args)
    {
        MiniMusicCmdLine mini = new MiniMusicCmdLine();
        if (args.length < 2)
        {
            System.out.println("Не забудьте аргументы для инструмента и ноты");
        }
        else
        {
            int instrument = Integer.parseInt(args[0]);
            int note = Integer.parseInt(args[1]);
            mini.play(instrument, note);
        }
    } //OUT OF MAIN

    public void play(int instrument, int note)
    {
        try {
            Sequencer player = MidiSystem.getSequencer(); // получаем синтезатор
            player.open(); // открываем его, чтобы начать использовать
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack(); // трек содержится внутри последовательности

            MidiEvent event = null;

            ShortMessage first = new ShortMessage();
            first.setMessage(192, 1, instrument, 0); //меняем инструмент в первом канале
            MidiEvent changeInstrument = new MidiEvent(first, 1); // вставляем в первый такт
            track.add(changeInstrument);

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, 44, 100); // начинаем проигрывать ноту
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, 44, 100); // заканчиваем проигрывать ноту
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            player.setSequence(seq); // передаём последовательность синтезатору (вставляем CD)
            player.start(); // запускаем синтезатор
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } // OUT OF TRY-CATCH
    } // OUT OF METHOD
}
