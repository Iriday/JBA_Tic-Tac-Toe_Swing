package tictactoe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

public class TicTacToeView extends JFrame {
    private TicTacToeController controller;

    private final Font btnFont = new Font(null, Font.ITALIC, 70);

    private JPanel gameFieldPanel;
    private JLabel stateLabel;
    private JButton startResetBtn;

    private int rows;
    private int cols;

    private boolean gameStarted = false;

    // listeners
    private final ActionListener fieldBtnListener = event -> {
        var btn = event.getSource();
        var buttons = gameFieldPanel.getComponents();

        for (int i = 0; i < buttons.length; i++) {
            if (Objects.equals(btn, buttons[i])) {
                int[] btnCoords = TicTacToeUtils.indexToCoords(i);
                controller.makeMove(btnCoords[0], btnCoords[1]);
            }
        }
    };

    private final ActionListener startResetGameListener = event -> {
        if (!gameStarted) {
            controller.startGame();
            startResetBtn.setText("Reset");
            gameStarted = true;
        } else {
            controller.resetGame();
        }
    };

    public void initialize(TicTacToeController controller) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.controller = controller;
                setTitle("TicTacToe");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(750, 750);
                setLocationRelativeTo(null);

                rows = 3;
                cols = 3;

                add(createGameField(), BorderLayout.CENTER);
                add(createTopPanel(), BorderLayout.NORTH);
                add(createSouthPanel(), BorderLayout.SOUTH);

                setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel createGameField() {
        gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(3, 3, 10, 10));

        for (int i = 3; i > 0; i--) {
            for (char c : new char[]{'A', 'B', 'C'}) {
                var btn = new JButton(" ");

                btn.setName("Button" + c + i);
                btn.addActionListener(fieldBtnListener);
                btn.setFont(btnFont);
                btn.setFocusPainted(false);

                gameFieldPanel.add(btn);
            }
        }

        return gameFieldPanel;
    }

    private JPanel createTopPanel() {
        var panel = new JPanel();
        startResetBtn = new JButton("Start");
        var leftPlayerBtn = new JButton("Player1");
        var rightPlayerBtn = new JButton("Player2");

        startResetBtn.addActionListener(startResetGameListener);
        startResetBtn.setName("ButtonReset");

        panel.add(leftPlayerBtn);
        panel.add(startResetBtn);
        panel.add(rightPlayerBtn);

        return panel;
    }

    private JPanel createSouthPanel() {
        var panel = new JPanel();
        var border = new EmptyBorder(15, 25, 15, 25);
        stateLabel = new JLabel("Game state");

        stateLabel.setFont(new Font(null, Font.ITALIC, 16));
        stateLabel.setName("LabelStatus");

        panel.setBorder(border);
        panel.setLayout(new BorderLayout());
        panel.add(BorderLayout.WEST, stateLabel);

        return panel;
    }

    public void setGameStateMessage(String msg) {
        stateLabel.setText(msg);
    }

    public void redrawFieldSquare(int row, int col, String player) {
        int componentIndex = TicTacToeUtils.coordsToIndex(row, col, rows);
        var btn = gameFieldPanel.getComponent(componentIndex);

        ((JButton) btn).setText(player);
    }

    public void clearField() {
        Arrays
                .stream(gameFieldPanel.getComponents())
                .map(c -> (JButton) c)
                .forEach(btn -> btn.setText(" "));
    }

    public void setFieldButtonsEnabled(boolean enabled) {
        Arrays.stream(gameFieldPanel.getComponents()).forEach(c -> c.setEnabled(enabled));
    }
}
