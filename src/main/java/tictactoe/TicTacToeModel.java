package tictactoe;

import java.util.Arrays;
import java.util.Objects;

import static tictactoe.GameState.*;

public class TicTacToeModel {

    private final int rows;
    private final int cols;
    private final String oPlayer;
    private final String xPlayer;

    private String[][] gameField;
    private boolean playerSwitcher;

    public TicTacToeModel() {
        rows = 3;
        cols = 3;
        oPlayer = "O";
        xPlayer = "X";
        gameField = generateGameField(rows, cols);
        playerSwitcher = false;
    }

    public String[][] getGameField() {
        return gameField;
    }

    /***
     * Make a move and place O or X on the field at the specified coords.
     * Coords start from 0.
     *
     * @return true if the cell is empty and the move was performed, otherwise false.
     */
    public boolean makeMove(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            throw new IllegalArgumentException("Coordinates out of range");
        }

        if (isWinner(gameField, oPlayer) || isWinner(gameField, xPlayer)) {
            return false;
        }

        if (gameField[row][col] != null) {
            return false;
        } else {
            gameField[row][col] = playerSwitcher ? oPlayer : xPlayer;
            playerSwitcher = !playerSwitcher;
            return true;
        }
    }

    public GameState getGameState() {

        if (isFieldEmpty(gameField)) {
            return NOT_STARTED;
        } else if (isWinner(gameField, oPlayer)) {
            return O_WINS;
        } else if (isWinner(gameField, xPlayer)) {
            return X_WINS;
        } else if (isFieldFull(gameField)) {
            return DRAW;
        } else {
            return IN_PROGRESS;
        }
    }

    public void resetGame() {
        gameField = generateGameField(rows, cols);
        playerSwitcher = false;
    }

    private static String[][] generateGameField(int rows, int cols) {
        return new String[rows][cols];
    }

    private static boolean isFieldEmpty(String[][] gameField) {
        return Arrays
                .stream(gameField)
                .flatMap(Arrays::stream)
                .allMatch(Objects::isNull);
    }

    private static boolean isFieldFull(String[][] gameField) {
        return Arrays
                .stream(gameField)
                .flatMap(Arrays::stream)
                .allMatch(Objects::nonNull);
    }

    private static boolean isWinner(String[][] gField, String player) {
        // check rows and cols
        for (int i = 0; i < gField.length; i++) {
            if (player.equals(gField[i][0]) && player.equals(gField[i][1]) && player.equals(gField[i][2]) ||
                    player.equals(gField[0][i]) && player.equals(gField[1][i]) && player.equals(gField[2][i])) {
                return true;
            }
        }
        // check diagonals
        return player.equals(gField[0][0]) && player.equals(gField[1][1]) && player.equals(gField[2][2]) ||
                player.equals(gField[0][2]) && player.equals(gField[1][1]) && player.equals(gField[2][0]);
    }
}
