package checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;

public class GamePanel extends JPanel {
    private static final long serialVersionUID = 5209868672877405279L;
    private static final List<Player> avaliablePlayers = Arrays.asList(new HumanPlayer(),
            //new RandomPlayer(),
            //new MinMaxPlayer(1),
            //new MinMaxPlayer(3),
            new MinMaxPlayer(5));
    private final BoardController controller;

    public GamePanel(Board board) {
        BoardView view = new BoardView(board);
        controller = new BoardController(view);

        BorderLayout borderLayout = new BorderLayout(8, 8);
        setLayout(borderLayout);

        JPanel filler = new JPanel();
        filler.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.CENTER;
        filler.add(new BorderPanel(view), constraints);

        add(filler, BorderLayout.CENTER);

        JComponent buttonPanel = new JPanel();

        JButton newGame = new JButton("New Game");
        newGame.addActionListener(e -> controller.newGame());
        buttonPanel.add(newGame);

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> System.exit(0));
        buttonPanel.add(quit);

        add(buttonPanel, BorderLayout.NORTH);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 1));
        rightPanel.add(new PlayerInfoPanel(-1));
        rightPanel.add(new PlayerInfoPanel(1));

        add(rightPanel, BorderLayout.EAST);

        Log log = new Log();

        JScrollPane logScroll = new JScrollPane(log);
        logScroll.setBorder(BorderFactory.createTitledBorder("Log:"));
        add(logScroll, BorderLayout.SOUTH);
        controller.addBoardListener(log);
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

    }

    private static class BorderPanel extends JPanel {
        private static final long serialVersionUID = 4355351078893761543L;
        JPanel board;

        public BorderPanel(JPanel board) {
            this.board = board;
            add(board);
            setLayout(null);
            board.setLocation(20, 20);
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(board.getWidth() + 40, board.getHeight() + 40));
        }

        public void paint(Graphics g) {
            super.paint(g);

            if (g instanceof Graphics2D) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }

            for (int x = 0; x < Board.width; x++) {
                g.drawString(Integer.toString(x + 1), x * 32 + 32, 16);
                g.drawString(Integer.toString(x + 1), x * 32 + 32, board.getHeight() + 32);
            }

            for (int y = 0; y < Board.height; y++) {
                g.drawString(Character.toString((char) (y + 'A')), 8, y * 32 + 32 + 8);
                g.drawString(Character.toString((char) (y + 'A')), board.getWidth() + 24, y * 32 + 32 + 8);
            }

            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    private class PlayerInfoPanel extends JPanel implements BoardListener {
        private static final long serialVersionUID = -2691532936608582978L;
        final String playerName;
        final Clock clock;
        final int player;

        public PlayerInfoPanel(final int player) {
            this.player = player;
            setLayout(new GridBagLayout());

            if (player == 1) {
                playerName = "White";
            } else if (player == -1) {
                playerName = "Black";
            } else {
                throw new IllegalArgumentException();
            }

            setBorder(BorderFactory.createTitledBorder(playerName + ":"));

            JComboBox playerController = new JComboBox();

            avaliablePlayers.forEach(playerController::addItem);
            playerController.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    controller.setPlayer(player, (Player) e.getItem());
            });

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.VERTICAL;
            constraints.anchor = GridBagConstraints.NORTH;
            constraints.insets = new Insets(4, 4, 4, 4);
            add(playerController, constraints);

            clock = new Clock();
            constraints.gridy = 1;
            constraints.weighty = 1.0;

            add(clock, constraints);

            controller.addBoardListener(this);

            if (controller.getBoard().turnHolder == player) {
                clock.start();
            }
        }

        @Override
        public void onMove(Board board, Move move) {
            if (board.turnHolder != player) {
                clock.start();
            } else {
                clock.stop();
            }
        }

        @Override
        public void onNewGame(Board board) {
            clock.reset();
            if (board.turnHolder == player) {
                clock.start();
            }
        }
    }
}
