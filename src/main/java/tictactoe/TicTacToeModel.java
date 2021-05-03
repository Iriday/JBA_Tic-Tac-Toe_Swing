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
     * @return true if the cell was empty and the move was performed, otherwise false.
     */
    public boolean makeMove(int row, int col) {
        if (row < 0 || col < 0 || row >= rows || col >= cols) {
            throw new IllegalArgumentException("Coordinates out of range");
        }

        if (isGameFinished()) {
            return false;
        }

        if (gameField[row][col] != null) {
            return false;
        }

        move(row, col);
        return true;
    }

    /***
     * @return coords if move was performed, otherwise null.
     */
    public int[] makeRandomMove() {
        if (isGameFinished()) {
            return null;
        }

        int[] coords = TicTacToeUtils.findRandomEmptyCell(gameField);

        move(coords[0], coords[1]);
        return coords;
    }

    private void move(int row, int col) {
        gameField[row][col] = playerSwitcher ? oPlayer : xPlayer;
        playerSwitcher = !playerSwitcher;
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

    public boolean isGameFinished() {
        return isWinner(gameField, oPlayer) || isWinner(gameField, xPlayer) || isFieldFull(gameField);
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
