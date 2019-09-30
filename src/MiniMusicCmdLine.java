import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicCmdLine {

    static JFrame f = new JFrame("Мой первый музыкальный клип.");
    static MyDrawPanel mdp;

    public static MidiEvent makeEvent (int comd, int chan, int one, int two,  int tick) {
        MidiEvent midiEvent = null;
        try {
            ShortMessage shortMessage = new ShortMessage();
            shortMessage.setMessage(comd, chan, one, two);
            midiEvent = new MidiEvent(shortMessage,  tick);
        } catch (Exception e) {
            return midiEvent;
        }
        return midiEvent;
    }

    public void setUpGui(){
        mdp = new MyDrawPanel();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(mdp);
        f.setBounds(30, 30, 300, 300);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        MiniMusicCmdLine mini = new MiniMusicCmdLine();
        mini.play();
    }

    public void play() {
        setUpGui();

        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();
            player.addControllerEventListener(mdp, new int[] {127});
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            int r = 0;
            for (int i = 0; i < 60; i += 4) {
                r = (int) ((Math.random() * 50) + 1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i + 2));
            }

            player.setSequence(sequence);
            player.setTempoInBPM(120);
            player.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class MyDrawPanel extends JPanel implements ControllerEventListener {

        boolean msg = false;

        @Override
        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        public void paintComponent (Graphics g) {
            if (msg) {
                Graphics2D g2d = (Graphics2D) g;

                int red = (int) (Math.random() * 255);
                int green = (int) (Math.random() * 255);
                int blue = (int) (Math.random() * 255);

                g.setColor(new Color(red, green, blue));

                int height = (int) ((Math.random() * 120) + 10);
                int width = (int) ((Math.random() * 120) + 10);
                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);

                g.fillRect(x, y, height, width);
                msg = false;
            }
        }
    }
}
