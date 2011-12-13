import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GamePanel extends JPanel
{

	private static class BorderPanel extends JPanel
	{

		JPanel board;
		
		public BorderPanel(JPanel board)
		{
			this.board=board;
			add(board);
			setLayout(null);
			board.setLocation(20,20);
			setBackground(Color.WHITE);

			setPreferredSize(new Dimension(board.getWidth()+40,board.getHeight()+40));
		}
		
		public void paint(Graphics g)
		{
			
			super.paint(g);
			
			if(g instanceof Graphics2D)
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			for(int x=0;x<Board.width;x++)
			{
				g.drawString(Integer.toString(x), x*32+32, 16);
				g.drawString(Integer.toString(x), x*32+32, board.getHeight()+32);
			}
			
			for(int y=0;y<Board.height;y++)
			{
				g.drawString(Character.toString((char)(y+'A')), 8,y*32+32+8);
				g.drawString(Character.toString((char)(y+'A')), board.getWidth()+24, y*32+32+8);
			}

			
		}
	}
	
	private final BoardPanel boardPanel;
	
	public GamePanel(Board board)
	{
		
		setLayout(new BorderLayout());

		
		boardPanel = new BoardPanel(board);
		add(new BorderPanel(boardPanel),BorderLayout.CENTER);
		
		JComponent buttonPanel = new JPanel();
		
		JButton newGame=new JButton("New Game");
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boardPanel.setBoard(new Board());
			}
		});
		buttonPanel.add(newGame);

		JButton quit=new JButton("Quit");
		quit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		buttonPanel.add(quit);
		
		add(buttonPanel,BorderLayout.NORTH);
		
		
	}
	
}
