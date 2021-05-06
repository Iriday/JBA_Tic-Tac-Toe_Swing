package tictactoe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

import static tictactoe.Mode.*;

public class TicTacToeView extends JFrame {
    private static final String START = "Start";
    private static final String RESET = "Reset";

    private TicTacToeController controller;
    private Mode[] mode;

    private final Font btnFont = new Font(null, Font.ITALIC, 70);

    private JPanel gameFieldPanel;
    private JLabel stateLabel;
    private JButton startResetBtn;
    private JButton leftPlayerBtn;
    private JButton rightPlayerBtn;

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
            controller.startGame(mode);
            startResetBtn.setText(RESET);
        } else {
            controller.resetGame();
            startResetBtn.setText(START);
        }

        gameStarted = !gameStarted;
    };

    public void initialize(TicTacToeController controller) {
        try {
            SwingUtilities.invokeAndWait(() -> {
                this.controller = controller;
                setTitle("TicTacToe");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(750, 750);
                setLocationRelativeTo(null);

                mode = new Mode[]{HUMAN, HUMAN};

                rows = 3;
                cols = 3;

                setJMenuBar(createMenuBar());

                add(createGameField(), BorderLayout.CENTER);
                add(createTopPanel(), BorderLayout.NORTH);
                add(createSouthPanel(), BorderLayout.SOUTH);

                setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JMenuBar createMenuBar() {
        var menuBar = new JMenuBar();
        var gameMenu = new JMenu("Game");
        var humanVsHuman = new JMenuItem("Human vs. Human");
        var humanVsRobot = new JMenuItem("Human vs. Robot");
        var robotVsHuman = new JMenuItem("Robot vs. Human");
        var robotVsRobot = new JMenuItem("Robot vs. Robot");
        var exit = new JMenuItem("Exit");

        gameMenu.setName("MenuGame");
        humanVsHuman.setName("MenuHumanHuman");
        humanVsRobot.setName("MenuHumanRobot");
        robotVsHuman.setName("MenuRobotHuman");
        robotVsRobot.setName("MenuRobotRobot");
        exit.setName("MenuExit");

        gameMenu.setMnemonic('g');
        humanVsHuman.setMnemonic('1');
        humanVsRobot.setMnemonic('2');
        robotVsHuman.setMnemonic('3');
        robotVsRobot.setMnemonic('4');

        humanVsHuman.addActionListener(e -> resetAndStartNewGame(HUMAN, HUMAN));
        humanVsRobot.addActionListener(e -> resetAndStartNewGame(HUMAN, ROBOT));
        robotVsHuman.addActionListener(e -> resetAndStartNewGame(ROBOT, HUMAN));
        robotVsRobot.addActionListener(e -> resetAndStartNewGame(ROBOT, ROBOT));
        exit.addActionListener(e -> System.exit(0));

        gameMenu.add(humanVsHuman);
        gameMenu.add(humanVsRobot);
        gameMenu.add(robotVsHuman);
        gameMenu.add(robotVsRobot);
        gameMenu.addSeparator();
        gameMenu.add(exit);

        menuBar.add(gameMenu);

        return menuBar;
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
        startResetBtn = new JButton(START);
        leftPlayerBtn = new JButton(mode[0].name);
        rightPlayerBtn = new JButton(mode[1].name);

        startResetBtn.addActionListener(startResetGameListener);
        startResetBtn.setName("ButtonStartReset");

        leftPlayerBtn.addActionListener(event -> {
            mode[0] = mode[0] == HUMAN ? ROBOT : HUMAN;
            leftPlayerBtn.setText(mode[0].name);
        });
        leftPlayerBtn.setName("ButtonPlayer1");

        rightPlayerBtn.addActionListener(event -> {
            mode[1] = mode[1] == HUMAN ? ROBOT : HUMAN;
            rightPlayerBtn.setText(mode[1].name);
        });
        rightPlayerBtn.setName("ButtonPlayer2");

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

    private void resetAndStartNewGame(Mode player1, Mode player2) {
        mode[0] = player1;
        mode[1] = player2;

        controller.resetGame();
        leftPlayerBtn.setText(mode[0].name);
        rightPlayerBtn.setText(mode[1].name);
        controller.startGame(mode);

        if (!gameStarted) {
            gameStarted = true;
            startResetBtn.setText(RESET);
        }
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

    public void setPlayerButtonsEnabled(boolean enabled) {
        leftPlayerBtn.setEnabled(enabled);
        rightPlayerBtn.setEnabled(enabled);
    }
}
