package checkers;

import javax.swing.*;

public class CheckersWindow extends JFrame {
    private static final long serialVersionUID = -6494085506802879161L;

    public CheckersWindow() {
        GamePanel gamePanel = new GamePanel(new Board());
        setContentPane(gamePanel);
        setResizable(false);
        setTitle("Checkers");
        pack();
    }
}
