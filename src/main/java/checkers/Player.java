package checkers;

public interface Player {
    public boolean isHuman();
    Move getMove(Board board);
}
