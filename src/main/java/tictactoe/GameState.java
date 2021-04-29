package tictactoe;

public enum GameState {
    NOT_STARTED("Game is not started"),
    IN_PROGRESS("Game in progress"),
    X_WINS("X wins"),
    O_WINS("O wins"),
    DRAW("Draw");


    public final String message;

    GameState(String message) {
        this.message = message;
    }
}
