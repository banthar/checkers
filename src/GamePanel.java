import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GamePanel extends JPanel
{

	private static class BorderPanel extends JPanel
	{

		JPanel board;

		public BorderPanel(JPanel board)
		{
			this.board = board;
			add(board);
			setLayout(null);
			board.setLocation(20, 20);
			setBackground(Color.WHITE);

			setPreferredSize(new Dimension(board.getWidth() + 40, board.getHeight() + 40));
		}

		public void paint(Graphics g)
		{

			super.paint(g);

			if(g instanceof Graphics2D)
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			for(int x = 0; x < Board.width; x++)
			{
				g.drawString(Integer.toString(x), x * 32 + 32, 16);
				g.drawString(Integer.toString(x), x * 32 + 32, board.getHeight() + 32);
			}

			for(int y = 0; y < Board.height; y++)
			{
				g.drawString(Character.toString((char)(y + 'A')), 8, y * 32 + 32 + 8);
				g.drawString(Character.toString((char)(y + 'A')), board.getWidth() + 24, y * 32 + 32 + 8);
			}

			g.setColor(Color.BLACK);
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

		}

	}

	private static class PlayerInfoPanel extends JPanel
	{

		final String playerName;

		public PlayerInfoPanel(int player)
		{

			if(player == 1)
				playerName = "White";
			else if(player == -1)
				playerName = "Black";
			else
				throw new IllegalArgumentException();

			setBorder(BorderFactory.createTitledBorder(playerName + ":"));

			JComboBox<Player> playerController = new JComboBox<>();

			for(Player p : avaliablePlayers)
			{
				playerController.addItem(p);
			}

			add(playerController);

		}
	}

	private static final List<Player> avaliablePlayers = Arrays
			.asList(new Player[] { new HumanPlayer(), new CPUPlayer(0), new CPUPlayer(1), new CPUPlayer(2) });

	private final BoardPanel boardPanel;

	public GamePanel(Board board)
	{

		setLayout(new BorderLayout(8, 0));

		boardPanel = new BoardPanel(board);
		add(new BorderPanel(boardPanel), BorderLayout.CENTER);

		JComponent buttonPanel = new JPanel();

		JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				boardPanel.setBoard(new Board());
			}
		});
		buttonPanel.add(newGame);

		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		buttonPanel.add(quit);

		add(buttonPanel, BorderLayout.NORTH);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(0, 1));
		rightPanel.add(new PlayerInfoPanel(1));
		rightPanel.add(new PlayerInfoPanel(-1));

		add(rightPanel, BorderLayout.EAST);

	}

}
