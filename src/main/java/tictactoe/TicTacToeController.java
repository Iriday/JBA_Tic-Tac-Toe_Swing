package tictactoe;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class TicTacToeController {
    private TicTacToeView view;
    private final TicTacToeModel model;

    public TicTacToeController() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> this.view = new TicTacToeView());
        this.model = new TicTacToeModel();

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

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        new TicTacToeController();
    }
}
