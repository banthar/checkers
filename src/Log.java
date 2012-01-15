import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class Log extends JTextArea implements BoardListener
{

	
	public Log()
	{
		super(8,0);
		setEditable(false);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
	}

	@Override
	public void onMove(Board board, Move move)
	{
		append(move+"\n");
		setCaretPosition(getText().length());
	}

	@Override
	public void onNewGame(Board board)
	{
		setText("");
	}
	
}
