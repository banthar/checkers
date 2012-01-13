import javax.swing.JApplet;

public class CheckersApplet extends JApplet
{

	private static final long serialVersionUID = 7076766047708629733L;
	private final GamePanel gamePanel;
	
	public CheckersApplet()
	{
		gamePanel = new GamePanel(new Board());
		setContentPane(gamePanel);
	}
}
