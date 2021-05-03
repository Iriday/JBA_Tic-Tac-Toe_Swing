package tictactoe;

public class TicTacToeController {
    private final TicTacToeView view;
    private final TicTacToeModel model;

    public TicTacToeController() {
        this.view = new TicTacToeView();
        this.model = new TicTacToeModel();

        view.initialize(this);
        view.setGameStateMessage(model.getGameState().message);
        view.setFieldButtonsEnabled(false);
    }

    public void startGame() {
        view.setFieldButtonsEnabled(true);
        view.setGameStateMessage(model.getGameState().message);
    }

    public void makeMove(int row, int col) {
        if (model.makeMove(row, col)) {
            view.redrawFieldSquare(row, col, model.getGameField()[row][col]);
            var gameState = model.getGameState();
            view.setGameStateMessage(gameState.message);

            switch (gameState) {
                case DRAW, X_WINS, O_WINS:
                    view.setFieldButtonsEnabled(false);
            }
        }
    }

    public void resetGame() {
        model.resetGame();
        view.clearField();
        view.setGameStateMessage(model.getGameState().message);
        view.setFieldButtonsEnabled(true);
    }

    public static void main(String[] args) {
        new TicTacToeController();
    }
}
