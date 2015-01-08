package checkers;

import javax.swing.*;
import java.awt.*;

public class Clock extends JPanel {
    private static final long serialVersionUID = 8988209987063047869L;
    private final JLabel label;
    private final Timer timer;
    private int time = 0;

    public Clock() {
        label = new JLabel();
        add(label);
        label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
        timer = new Timer(1000, e -> {
            time++;
            update();
        });

        update();
    }

    private void update() {
        label.setText(String.format("%02d:%02d", time / 60, time % 60));

        if (timer.isRunning()) {
            label.setForeground(Color.RED);
        } else {
            label.setForeground(Color.BLACK);
        }
    }

    public void stop() {
        timer.stop();
        update();
    }

    public void reset() {
        time = 0;
        update();
        stop();
    }

    public void start() {
        timer.start();
        update();
    }

    @Override
    protected void finalize() throws Throwable {
        timer.stop();
    }
}
