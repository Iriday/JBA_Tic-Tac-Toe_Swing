package tictactoe;

import javax.swing.*;
import java.awt.*;

public class TicTacToe extends JFrame {
    public TicTacToe() {
        super("TicTacToe");
        initialize();
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);

        add(createGameField());

        setVisible(true);
    }

    private JPanel createGameField() {
        var gameField = new JPanel();
        gameField.setLayout(new GridLayout(3, 3, 10, 10));

        for (int i = 3; i > 0; i--) {
            for (char c : new char[]{'A', 'B', 'C'}) {
                var btn = new JButton(c + "" + i);
                btn.setName("Button" + c + i);
                gameField.add(btn);
            }
        }

        return gameField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
