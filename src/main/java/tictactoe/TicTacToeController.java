package tictactoe;

public class TicTacToeController {
    private final TicTacToeView view;
    private final TicTacToeModel model;

    public TicTacToeController() {
        this.view = new TicTacToeView();
        this.model = new TicTacToeModel();

        view.initialize(this);
        view.setGameStateMessage(model.getGameState().message);
    }

    public void makeMove(int row, int col) {
        if (model.makeMove(row, col)) {
            view.redrawFieldSquare(row, col, model.getGameField()[row][col]);
            view.setGameStateMessage(model.getGameState().message);
        }
    }

    public void resetGame() {
        model.resetGame();
        view.clearField();
        view.setGameStateMessage(model.getGameState().message);
    }

    public static void main(String[] args) {
        new TicTacToeController();
    }
}
