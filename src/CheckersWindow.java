import javax.swing.JFrame;

public class CheckersWindow extends JFrame
{

	private final GamePanel gamePanel;

	public CheckersWindow()
	{
		gamePanel = new GamePanel(new Board());
		setContentPane(gamePanel);
		setResizable(false);
		pack();

	}

	
}
