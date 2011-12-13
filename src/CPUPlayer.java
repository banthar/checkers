import java.util.List;
import java.util.Random;

public class CPUPlayer
{

	Random rand = new Random();
	
	Move getMove(Board board)
	{
		List<Move> moves=board.getMoves();
		
		return moves.get(rand.nextInt(moves.size()));
	}
	
}
