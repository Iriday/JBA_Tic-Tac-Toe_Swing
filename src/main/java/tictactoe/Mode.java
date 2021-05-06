package tictactoe;

public enum Mode {
    HUMAN("Human"), ROBOT("Robot");

    final String name;

    Mode(String name) {
        this.name = name;
    }
}
