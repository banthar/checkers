import java.awt.Point;

public class Move
{

	public final Point p0;
	public final Point p1;
	public final boolean jump;
	
	public int score;
	
	public Move()
	{
		p0=null;
		p1=null;
		jump=false;
	}
	
	public Move(Point p0, Point p1, boolean jump)
	{
		this.p0=new Point(p0);
		this.p1=new Point(p1);
		this.jump=jump;
	}
	
	public String toString()
	{
		String s0=(char)('A'+p0.x)+""+p0.y;
		String s1=(char)('A'+p1.x)+""+p1.y;
		
		if(jump)
			return s0+"->"+s1+" ("+score+")";
		else
			return s0+"-"+s1+" ("+score+")";
	}

	public Point getJumpedPiece()
	{
		
		int x=p1.x-Utils.sign(p1.x-p0.x);
		int y=p1.y-Utils.sign(p1.y-p0.y);
		
		return new Point(x,y);
	}
	
}
