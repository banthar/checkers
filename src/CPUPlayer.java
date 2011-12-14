import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CPUPlayer implements Player
{

	Random rand = new Random();
	int level;

	public CPUPlayer(int level)
	{
		this.level = level;
	}

	public Move getMove(Board board)
	{
		switch(level)
		{
			case 0:
				return getRandomMove(board);
			case 1:
				return getMove(board, 2);
			case 2:
				return getMove(board, 6);
			case 3:
				return getMove(board, 6);
			default:
				throw new IllegalStateException();

		}

	}

	private Move getRandomMove(Board board)
	{
		List<Move> moves = board.getMoves();

		if(moves.isEmpty())
			return null;

		return moves.get(rand.nextInt(moves.size()));
	}

	private Move getMove(Board board, int depth)
	{

		List<Move> moves = board.getMoves();
		
		Collections.shuffle(moves);
		
		Move bestMove = null;

		for(Move move : moves)
		{
			Board child = board.clone();
			child.makeMove(move);

			if(depth < 1)
				move.score = -eval(child);
			else
			{
				Move result = getMove(child, depth - 1);
				if(result==null)
					move.score = 1000;
				else
					move.score = -result.score+Utils.sign(result.score);
			}
			
			if(bestMove == null || move.score > bestMove.score)
				bestMove = move;

		}
		
		return bestMove;

	}

	private int eval(Board board)
	{
		
		int player = 0;
		int opponent = 0;
		
		for(int y = 0; y < board.height; y++)
			for(int x = 0; x < board.width; x++)
				if(board.getPlayer(x, y) ==  board.turnHolder)
					player+=board.getPiece(x,y);
				else
					opponent+=board.getPiece(x,y);

		
		if(level<3)
			return player-opponent;
		else
			return (player-opponent)*64/(player+opponent);

	}

	@Override
	public String toString()
	{
		final String[] names = { "Random", "Easy", "Average", "Hard", };

		return names[level];

	}

	public boolean isHuman()
	{
		return false;
	}

}
