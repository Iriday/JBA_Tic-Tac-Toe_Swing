package tictactoe;

public enum Mode {
    HUMAN("Human"), ROBOT("Robot");

    final String mode;

    Mode(String mode) {
        this.mode = mode;
    }
}
