package tictactoe;

import javax.swing.*;

import java.util.List;

import static tictactoe.Mode.*;

public class TicTacToeController {
    private static final int ROBOT_MOVE_DELAY = 500;
    private final TicTacToeView view;
    private final TicTacToeModel model;
    private volatile boolean makingMove;
    private Mode[] mode;
    private SwingWorker<int[], int[]> robotMoveWorker;


    public TicTacToeController() {
        this.view = new TicTacToeView();
        this.model = new TicTacToeModel();

        view.initialize(this);
        view.setGameStateMessage(model.getGameState().message);
        view.setFieldButtonsEnabled(false);
    }

    public void startGame(Mode[] mode) {
        makingMove = true;
        this.mode = mode;

        view.setFieldButtonsEnabled(true);
        view.setGameStateMessage(GameState.IN_PROGRESS.message);
        view.setPlayerButtonsEnabled(false);

        if (mode[0] == ROBOT && mode[1] == ROBOT) {
            robotMakeMoves(false);
        } else if (mode[0] == ROBOT) {
            robotMakeMoves(true);
        } else {
            makingMove = false;
        }
    }

    public void makeMove(int row, int col) {
        if (makingMove) {
            return;
        }
        makingMove = true;

        if (model.makeMove(row, col)) {
            view.redrawFieldSquare(row, col, model.getGameField()[row][col]);
            var gameState = model.getGameState();
            view.setGameStateMessage(gameState.message);

            if (model.isGameFinished()) {
                view.setFieldButtonsEnabled(false);
            }
            if (mode[0] == ROBOT || mode[1] == ROBOT) {
                robotMakeMoves(true);
            } else {
                makingMove = false;
            }
        } else {
            makingMove = false;
        }
    }

    public void resetGame() {
        makingMove = true;

        if (robotMoveWorker != null) {
            robotMoveWorker.cancel(true);
        }

        model.resetGame();
        view.clearField();
        view.setGameStateMessage(model.getGameState().message);
        view.setPlayerButtonsEnabled(true);
        view.setFieldButtonsEnabled(false);

        makingMove = false;
    }

    private void robotMakeMoves(boolean singleMove) {
        robotMoveWorker = new SwingWorker<>() {
            @Override
            protected int[] doInBackground() {
                while (!model.isGameFinished()) {
                    try {
                        Thread.sleep(ROBOT_MOVE_DELAY);
                    } catch (InterruptedException e) {
                        System.out.println("TicTacToeController.doInBackground  sleep interrupted");
                        return null;
                    }
                    var cords = model.makeRandomMove();

                    publish(cords);

                    if (singleMove) {
                        makingMove = false;
                        break;
                    }
                }

                return null;
            }

            @Override
            protected void process(List<int[]> chunks) {
                int[] coords = chunks.get(0);

                view.redrawFieldSquare(coords[0], coords[1], model.getGameField()[coords[0]][coords[1]]);

                var gameState = model.getGameState();
                view.setGameStateMessage(gameState.message);

                if (model.isGameFinished()) {
                    view.setFieldButtonsEnabled(false);
                }
            }
        };

        try {
            robotMoveWorker.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new TicTacToeController();
    }
}
