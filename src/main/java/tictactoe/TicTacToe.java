package tictactoe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TicTacToe extends JFrame {
    public TicTacToe() {
        initialize();
    }

    private void initialize() {
        setTitle("TicTacToe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 750);
        setLocationRelativeTo(null);

        add(createGameField());
        add(createSouthPanel(), BorderLayout.SOUTH);

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

    private JPanel createSouthPanel() {
        var panel = new JPanel();
        var stateLabel = new JLabel("Game state");
        var resetBtn = new JButton("Reset");
        var border = new EmptyBorder(15, 25, 15, 25);

        stateLabel.setFont(new Font(null, Font.ITALIC, 16));

        panel.setBorder(border);
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.WEST, stateLabel);
        panel.add(BorderLayout.EAST, resetBtn);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
