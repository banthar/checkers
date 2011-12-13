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

	Point movePiece;
	Point movePosition;
	Point moveOffset;

	List<Move> possibleMoves;

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

					if(move != null)
						board.makeMove(move);

					moveOffset = null;
					movePiece = null;
					movePosition = null;
					possibleMoves = null;

					repaint();

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

	private boolean isHumanMove()
	{
		return true;
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
		this.board = board;
		repaint();

	}

}
