import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Clock extends JPanel
{
	
	private final JLabel label;
	private final Timer timer;
	private int time=0;
	
	public Clock()
	{
		
		label = new JLabel();
		add(label);
		label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		timer = new Timer(1000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				update();
				time++;
			}
		});
		
		update();
		
	}
	
	private void update()
	{
		label.setText(String.format("%02d:%02d", time/60,time%60));
	}
	
	public void stop()
	{
		timer.stop();
	}
	
	public void reset()
	{
		time=0;
		update();
	}
	
	public void start()
	{
		timer.start();
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		timer.stop();
	}
	
}	
