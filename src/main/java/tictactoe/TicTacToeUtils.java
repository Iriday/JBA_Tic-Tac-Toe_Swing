package tictactoe;

public class TicTacToeUtils {

    public static int[] indexToCoords(int index) {
        int row = index < 3 ? 0 : index < 6 ? 1 : 2;
        int col = index % 3;

        return new int[]{row, col};
    }

    public static int coordsToIndex(int row, int col, int rows) {
        return row * rows + col;
    }
}
