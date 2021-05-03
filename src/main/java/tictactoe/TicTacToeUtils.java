package tictactoe;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class TicTacToeUtils {
    private static final Random random = new Random();

    public static int[] indexToCoords(int index) {
        int row = index < 3 ? 0 : index < 6 ? 1 : 2;
        int col = index % 3;

        return new int[]{row, col};
    }

    public static int coordsToIndex(int row, int col, int rows) {
        return row * rows + col;
    }

    public static int[] findRandomEmptyCell(Object[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Matrix length = 0");
        }
        if (Arrays.stream(matrix).flatMap(Arrays::stream).allMatch(Objects::nonNull)) {

            throw new IllegalArgumentException("Matrix is full");
        }

        int rows = matrix.length;
        int cols = matrix[0].length;

        while (true) {
            int randRow = random.nextInt(rows);
            int randCol = random.nextInt(cols);

            if (matrix[randRow][randCol] == null) {
                return new int[]{randRow, randCol};
            }
        }
    }
}
