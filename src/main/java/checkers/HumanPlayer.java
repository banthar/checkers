package checkers;

public class HumanPlayer implements Player {
    public boolean isHuman() {
        return true;
    }

    public Move getMove(Board board) {
        throw new IllegalStateException();
    }

    public String toString() {
        return "Human";
    }
}
