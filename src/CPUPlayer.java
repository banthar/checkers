import java.util.List;
import java.util.Random;

public class CPUPlayer implements Player
{

	Random rand = new Random();
	int level;
	
	public CPUPlayer(int level)
	{
		this.level=level;
	}

	Move getMove(Board board)
	{
		List<Move> moves=board.getMoves();
		
		return moves.get(rand.nextInt(moves.size()));
	}

	@Override
	public String toString()
	{
		final String[] names={
				"Easy",
				"Average",
				"Hard",
				"Very Hard",
		};
		
		return names[level];
		
	}
	
}
