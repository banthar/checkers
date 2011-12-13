import javax.swing.JApplet;
import javax.swing.UIManager;

public class CheckersApplet extends JApplet
{

	private final GamePanel gamePanel;
	
	public CheckersApplet()
	{
		gamePanel = new GamePanel(new Board());
		setContentPane(gamePanel);
	}
}
