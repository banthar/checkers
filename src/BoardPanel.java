import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;

public class BoardPanel extends JPanel
{

	private Board board;

	private Point movePiece;
	private Point movePosition;
	private Point moveOffset;

	List<Move> possibleMoves;

	private final Player[] controllers = new Player[] { new HumanPlayer(), new HumanPlayer() };

	private Thread aiThread = null;

	public BoardPanel(Board b)
	{

		setBoard(b);

		final int size = 32;
		setPreferredSize(new Dimension(size * Board.width, size * Board.height));
		setSize(getPreferredSize());
		setBackground(Color.red);

		addMouseListener(new MouseAdapter()
		{

			@Override
			public void mousePressed(MouseEvent e)
			{
				if(e.getButton() == MouseEvent.BUTTON1 && isHumanMove())
				{
					int x = e.getX() / 32;
					int y = e.getY() / 32;

					if(board.inBounds(x, y) && board.getPlayer(x, y) == board.turnHolder)
					{

						movePiece = new Point(x, y);
						movePosition = e.getPoint();
						moveOffset = new Point(x * 32 - movePosition.x, y * 32 - movePosition.y);

						possibleMoves = board.getMoves(movePiece);

						repaint();
					}

				}

			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if(e.getButton() == MouseEvent.BUTTON1 && isHumanMove() && movePiece != null)
				{
					int x = (e.getX() + moveOffset.x + 16) / 32;
					int y = (e.getY() + moveOffset.y + 16) / 32;

					Move move = board.getMove(movePiece, new Point(x, y));

					makeMove(move);

				}
			}

		});

		addMouseMotionListener(new MouseAdapter()
		{

			@Override
			public void mouseDragged(MouseEvent e)
			{

				if(movePosition != null)
				{
					movePosition.setLocation(e.getX(), e.getY());
					repaint();
				}
			}
		});
	}

	private void reqestMove()
	{

		if(isHumanMove() || aiThread != null || board.getMoves().isEmpty())
			return;

		final Player player = getController();

		aiThread = new Thread(new Runnable()
		{

			public void run()
			{
				Move move = player.getMove(board);
				System.out.println(move);
				aiThread = null;

				try
				{
					animateMove(move);
				}
				catch(InterruptedException e)
				{
				}
				makeMove(move);

			}
		});

		aiThread.start();

	}

	private boolean isHumanMove()
	{
		return getController().isHuman();
	}

	private void animateMove(Move move) throws InterruptedException
	{
		
		movePiece = move.p0;
		moveOffset = new Point(0,0);
		movePosition = new Point();
		
		int frames=30;
		
		for(int i=0;i<frames;i++)
		{
			movePosition.x=(move.p0.x*(frames-i)+move.p1.x*i)*32/frames;
			movePosition.y=(move.p0.y*(frames-i)+move.p1.y*i)*32/frames;
			repaint();
			Thread.sleep(1000/60);
		}
		
	}

	private void makeMove(Move move)
	{

		if(move != null)
			board.makeMove(move);

		moveOffset = null;
		movePiece = null;
		movePosition = null;
		possibleMoves = null;

		repaint();

		reqestMove();

	}

	@Override
	public void paint(Graphics g)
	{

		if(g instanceof Graphics2D)
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final Color evenColor = new Color(0xfece9e);
		final Color oddColor = new Color(0xd08c47);

		for(int y = 0; y < Board.height; y++)
		{
			for(int x = 0; x < Board.width; x++)
			{
				if(((x ^ y) & 1) == 0)
					g.setColor(evenColor);
				else
					g.setColor(oddColor);

				g.fillRect(x * 32, y * 32, 32, 32);

			}
		}

		for(int y = 0; y < Board.height; y++)
		{
			for(int x = 0; x < Board.width; x++)
			{
				if(movePiece == null || movePiece.x != x || movePiece.y != y)
					drawPiece(g, x * 32, y * 32, board.get(x, y));
			}
		}

		if(movePiece != null)
		{

			if(g instanceof Graphics2D)
			{

				((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));

				if(possibleMoves!=null)
					for(Move m : possibleMoves)
					{
						drawPiece(g, m.p1.x * 32, m.p1.y * 32, board.get(movePiece.x, movePiece.y));
	
					}

				((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

			}

			drawPiece(g, movePosition.x + moveOffset.x, movePosition.y + moveOffset.y, board.get(movePiece.x, movePiece.y));

		}

	}

	private void drawPiece(Graphics g, Color fg, Color bg, int piece)
	{

		final int m = 2;
		final int p = 6;

		g.setColor(bg);
		g.fillOval(m, m, 31 - m * 2, 31 - m * 2);
		g.setColor(fg);
		g.drawOval(m, m, 31 - m * 2, 31 - m * 2);
		g.drawOval(p, p, 31 - p * 2, 31 - p * 2);

		if(piece == 2)
		{
			g.drawLine(12, 16, 19, 16);
			g.drawLine(12, 15, 19, 15);
			g.drawLine(15, 12, 15, 19);
			g.drawLine(16, 12, 16, 19);
		}
	}

	private void drawPiece(Graphics g, int x, int y, int piece)
	{

		g.translate(x, y);

		switch(Utils.sign(piece))
		{
			case 0:
				break;
			case 1:
				drawPiece(g, Color.BLACK, Color.WHITE, Math.abs(piece));
				break;
			case -1:
				drawPiece(g, Color.WHITE, Color.BLACK, Math.abs(piece));
				break;
		}

		g.translate(-x, -y);

	}

	public void setBoard(Board board)
	{
		if(aiThread != null)
		{
			aiThread.interrupt();
			aiThread = null;
		}

		this.board = board;
		repaint();
		reqestMove();
	}

	private Player getController()
	{
		return controllers[(board.turnHolder + 1) / 2];
	}

	public void setController(int player, Player controller)
	{
		controllers[(player + 1) / 2] = controller;
		reqestMove();
	}

}
