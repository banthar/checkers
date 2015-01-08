package checkers;

public interface BoardListener {
    public void onMove(Board board, Move move);
    public void onNewGame(Board board);
}