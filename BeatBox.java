import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class BeatBox
{
    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList; // ХРАНИМ ФЛАЖКИ В МАССИВЕ ARRAYLIST
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame frame;

    String [] instrumentNames = {"Bass Drum", "Closed Hi-Hat", "Open Hi-Hat", "Acoustic Snare",
    "Crash Cymbal", "Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga",
    "Cowbell", "Vibraslap", "Low-mid Tom", "High Agogo", "Open Hi Conga"};

    int [] instruments = {35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64, 56, 58, 47, 67, 63};
    /* ЭТИ ЧИСЛА ПРЕДСТАВЛЯЮТ СОБОЙ ФАКТИЧЕСКИЕ БАРАБАННЫЕ КЛАВИШИ.
    КАНАЛ БАРАБАНА - ЭТО ЧТО-ТО ВРОДЕ ФОРТЕПИАНО, ТОЛЬКО КАЖДАЯ КЛАВИША НА НЁМ - ОТДЕЛЬНЫЙ БАРАБАН.
    НОМЕР 35 - ЭТО КЛАВИША ДЛЯ BUSS DRUM, А 42 - CLOSED HI-HAT И Т.Д. */

    public static void main(String[] args)
    {
        new BeatBox().buildGUI();
    }

    public void buildGUI()
    {
        frame = new JFrame("BeatBox Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // ПУСТАЯ ГРАНИЦА ПОЗВОЛЯЕТ СОЗДАТЬ ПОЛЯ МЕЖДУ КРАЯМИ ПАНЕЛИ И МЕСТОМ РАЗМЕЩЕНИЯ КОМПОНЕНТОВ

        checkboxList = new ArrayList<>();

        // --------------- БЛОК BUTTON BOX ---------------
        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        JButton start = new JButton("START");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop = new JButton("STOP");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("TEMPO UP");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("TEMPO DOWN");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton serializeIt = new JButton("SERIALIZE");
        serializeIt.addActionListener(new SaveListener());
        buttonBox.add(serializeIt);

        JButton restore = new JButton("RESTORE");
        restore.addActionListener(new LoadListener());
        buttonBox.add(restore);
        // --------------- БЛОК BUTTON BOX ---------------

        // --------------- БЛОК NAME BOX ---------------
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i < 16; i++)
        {
            nameBox.add(new Label(instrumentNames[i]));
        }
        // --------------- БЛОК NAME BOX ---------------

        background.add(BorderLayout.EAST, buttonBox);
        background.add(BorderLayout.WEST, nameBox);
        frame.getContentPane().add(background);

        // --------------- БЛОК MAIN PANEL ---------------
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);

        for (int i = 0; i < 256; i++)
        {
            JCheckBox checkBox = new JCheckBox();
            checkBox.setSelected(false);
            checkboxList.add(checkBox);
            mainPanel.add(checkBox);
        }
        // --------------- БЛОК MAIN PANEL ---------------

        setUpMidi();

        frame.setBounds(50, 50, 50, 50);
        frame.pack();
        frame.setVisible(true);
    } // OUT OF BUILDING GUI

    public void setUpMidi()
    {
        try
        {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        } // OUT OF TRY-CATCH
    } // OUT OF MIDI SETUP

    public void buildTrackAndStart()
    {
        // СОЗДАЁМ МАССИВ ИЗ 16 ЭЛЕМЕНТОВ, ЧТОБЫ ХРАНИТЬ ЗНАЧЕНИЯ ДЛЯ КАЖДОГО ИНСТРУМЕНТА, НА ВСЕ 16 ТАКТОВ
        int [] trackList;

        // ИЗБАВЛЯЕМСЯ ОТ СТАРОЙ ДОРОЖКИ И СОЗДАЁМ НОВУЮ
        sequence.deleteTrack(track);
        track = sequence.createTrack();

        for (int i = 0; i < 16; i++) // ДЛЯ КАЖДОГО ИЗ 16 РЯДОВ...
        {
            trackList = new int[16];
            int key = instruments[i]; // ЗАДАЁМ КЛАВИШУ, КОТОРАЯ ПРЕДСТАВЛЯЕТ ИНСТРУМЕНТ

            for (int j = 0; j < 16; j++) // ДЛЯ КАЖДОГО ТАКТА ТЕКУЩЕГО РЯДА...
            {
                JCheckBox jcb = checkboxList.get(j + (16*i));

                /* УСТАНОВЛЕН ЛИ ФЛАЖОК НА ЭТОМ ТАКТЕ? ЕСЛИ ДА, ТО ПОМЕЩАЕМ ЗНАЧЕНИЕ КЛАВИШИ
                В ТЕКУЩУЮ ЯЧЕЙКУ МАССИВА (ЯЧЕЙКУ, КОТОРАЯ ПРЕДСТАВЛЯЕТ ТАКТ). ЕСЛИ НЕТ,
                ТО ИНСТРУМЕНТ НЕ ДОЛЖЕН ИГРАТЬ В ЭТОМ ТАКТЕ, ПОЭТОМУ ПРИСВОИМ ЕМУ 0. */
                if (jcb.isSelected()) trackList[j] = key;
                else trackList[j] = 0;
            } // OUT OF INNER LOOP

            // ДЛЯ ЭТОГО ИНСТРУМЕНТА И ДЛЯ ВСЕХ 16 ТАКТОВ СОЗДАЁМ СОБЫТИЯ И ДОБАВЛЯЕМ ИХ НА ДОРОЖКУ
            makeTracks(trackList);
            track.add(makeEvent(176, 1, 127, 0, 16));
        } // OUT OF OUTER LOOP

        /* МЫ ВСЕГДА ДОЛЖНЫ БЫТЬ УВЕРЕНЫ, ЧТО СОБЫТИЕ НА ТАКТЕ 16 СУЩЕСТВУЕТ (ОНИ ИДУТ ОТ 0 ДО 15).
        ИНАЧЕ BEAT BOX МОЖЕТ НЕ ПРОЙТИ ВСЕ 16 ТАКТОВ, ПЕРЕД ТЕМ КАК ЗАНОВО НАЧНЁТ ПОСЛЕДОВАТЕЛЬНОСТЬ. */
        track.add(makeEvent(192, 9, 1, 0, 15));
        try
        {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY); // ЗАДАЁМ НЕПРЕРЫВНЫЙ ЦИКЛ
            sequencer.start();
            sequencer.setTempoInBPM(120);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        } // OUT OF TRY-CATCH
    } // OUT OF BUILDING TRACK

    // --------------- БЛОК СЛУШАТЕЛЕЙ ---------------
    public class MyStartListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            buildTrackAndStart();
        }
    } // OUT OF INNER CLASS

    public class MyStopListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            sequencer.stop();
        }
    } // OUT OF INNER CLASS

    public class MyUpTempoListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03)); // УВЕЛИЧИВАЕМ ТЕМП НА 3%
        }
    } // OUT OF INNER CLASS

    public class MyDownTempoListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 0.97)); // УМЕНЬШАЕМ ТЕМП НА 3%
        }
    } // OUT OF INNER CLASS

    public class SaveListener implements ActionListener // СЛУШАТЕЛЬ ДЛЯ СЕРИАЛИЗАЦИИ
    {
        public void actionPerformed(ActionEvent event) // РЕАЛИЗАЦИЯ МЕТОДА ИНТЕРФЕЙСА
        {
            boolean[] checkboxState = new boolean[256]; // СОЗДАЁТСЯ БУЛЕВ МАССИВ ДЛЯ ХРАНЕНИЯ СОСТОЯНИЙ
            for (int i = 0; i < 256 ; i++) // ПРОБЕГАЕМСЯ ПО ЕГО ЭЛЕМЕНТАМ
            {
                JCheckBox check = checkboxList.get(i);
                if (check.isSelected()) checkboxState[i] = true; // ЕСЛИ МАРКЕР УСТАНОВЛЕН - ДУБЛИРУЕМ
            } // OUT OF LOOP

            try
            {
                FileOutputStream fos = new FileOutputStream("checkbox.ser"); // ПОТОК СОЕДИНЕНИЯ
                ObjectOutputStream oos = new ObjectOutputStream(fos); // ЦЕПНОЙ ПОТОК
                oos.writeObject(checkboxState); // ЗАПИСЬ ОБЪЕКТА В ФАЙЛ
                oos.close(); // ЗАКРЫТИЕ ПОТОКА
            }
            catch (Exception exc)
            {
                exc.printStackTrace(); // ПОКАЗАТЬ ПУТЬ ОШИБКИ
            } // OUT OF TRY-CATCH
        } // OUT OF METHOD
    } // OUT OF INNER CLASS

    public class LoadListener implements ActionListener // СЛУШАТЕЛЬ ДЛЯ ДЕСЕРИАЛИЗАЦИИ
    {
        public void actionPerformed(ActionEvent event)
        {
            boolean[] checkboxState = null; // ОБЪЯВЛЕНИЕ МАССИВА
            try
            {
                FileInputStream fis = new FileInputStream("checkbox.ser"); // ПОТОК СОЕДИНЕНИЯ
                ObjectInputStream ois = new ObjectInputStream(fis); // ЦЕПНОЙ ПОТОК
                checkboxState = (boolean[]) ois.readObject(); // ЗАГРУЗКА ОБЪЕКТА
            }
            catch (Exception exception) // ОБРАБОТКА ИСКЛЮЧЕНИЙ
            {
                exception.printStackTrace(); // ВЫВЕСТИ ПУТЬ ОШИБКИ
            } // OUT OF TRY-CATCH

            for (int i = 0; i < 256; i++)
            {
                JCheckBox check = checkboxList.get(i); // ПОМЕСТИТЬ ЧЕКБОКС В ЛОКАЛЬНУЮ ПЕРЕМЕННУЮ
                assert checkboxState != null;
                check.setSelected(checkboxState[i]); // УСТАНОВИТЬ МАРКЕР
            } // OUT OF LOOP
            sequencer.stop(); // ОСТАНОВИТЬ ВОПРОИЗВЕДЕНИЕ
            buildTrackAndStart(); // РЕБИЛД И ЗАПУСК ДОРОЖКИ
        }// OUT OF METHOD
    } // OUT OF INNER CLASS
    // --------------- БЛОК СЛУШАТЕЛЕЙ ---------------

    /* МЕТОД СОЗДАЁТ СОБЫТИЯ ДЛЯ ОДНОГО ИНСТРУМЕНТА ЗА КАЖДЫЙ ПРОХОД ЦИКЛА ДЛЯ ВСЕХ 16 ТАКТОВ.
    МОЖНО ПОЛУЧИТЬ int[] ДЛЯ BUSS DRUM, И КАЖДЫЙ ЭЛЕМЕНТ МАССИВА БУДЕТ СОДЕРЖАТЬ ЛИБО КЛАВИШУ, ЛИБО НОЛЬ.
    ЕСЛИ ЭТО НОЛЬ, ТО ИНСТРУМЕНТ НЕ ДОЛЖЕН ИГРАТЬ НА ТЕКУЩЕМ ТАКТЕ.
    ИНАЧЕ НУЖНО СОЗДАТЬ СОБЫТИЕ И ДОБАВТЬ ЕГО В ДОРОЖКУ. */
    public void makeTracks (int [] list)
    {
        for (int i = 0; i < 16; i++)
        {
            int key = list[i];

            if (key != 0)
            {
                // СОЗДАЁМ СОБЫТИЯ ВКЛЮЧЕНИЯ И ВЫКЛЮЧЕНИЯ, И ДОБАВЛЯЕМ ИХ В ДОРОЖКУ
                track.add(makeEvent(144, 9, key, 100, i));
                track.add(makeEvent(128, 9, key, 100, i + 1));
            } // OUT OF IF
        } // OUT OF LOOP
    } // OUT OF MAKING TRACK

    public MidiEvent makeEvent(int cmd, int chan, int key, int push, int tick)
    {
        MidiEvent event = null;

        try
        {
            ShortMessage message = new ShortMessage();
            message.setMessage(cmd, chan, key, push);
            event = new MidiEvent(message, tick);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        } // OUT OF TRY-CATCH

        return event;
    } // OUT OF MAKING EVENT
} // OUT OF BEAT BOX CLASS
