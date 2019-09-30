import javax.sound.midi.*;

public class MiniMusicCmdLine {
    public static void main(String[] args) {
        MiniMusicCmdLine mini = new MiniMusicCmdLine();
        if (args.length < 2) {                                  // Если забыли ввести аргументы в командной строке
            System.out.println("Не забудьте ввести аргументы: инструмент и ноты!");
        } else {
            int intriment = Integer.parseInt(args[0]);          // Преобразуем троки в числа
            int note = Integer.parseInt(args[1]);
            mini.play(intriment, note);
        }
    }
    public void play(int instrument, int note) {                //Метод создает и проигрывает звук

        try {
            Sequencer player = MidiSystem.getSequencer();       // Запрашиваем синтезатор
            player.open();                                      // Открываем его, так как он не открыт в начале
            Sequence sequence = new Sequence(Sequence.PPQ, 4); // Создаем последовательноесть
            Track track = sequence.createTrack();               // Создаем трек из последовательности

            MidiEvent event = null;

            ShortMessage first = new ShortMessage();            // Создаем сообщение
            first.setMessage(192, 1, instrument, 0); // Заполняем сообщение значениями
            MidiEvent changeInstrument = new MidiEvent(first, 1); // Создаем МИДИ инструкции
            track.add(changeInstrument);                        // Добавляем МИДИ инструкции в трек

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, note, 100);
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, note, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);

            player.setSequence(sequence);               // Указываем проигрываемую последовательность в синтезаторе
            player.start();                             // Запускаем синтезатор

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
