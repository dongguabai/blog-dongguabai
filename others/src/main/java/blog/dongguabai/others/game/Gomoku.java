package blog.dongguabai.others.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author dongguabai
 * @date 2024-01-24 20:50
 */
public class Gomoku extends JFrame {
    private static final int SIZE = 15;
    private static final int DOT_SIZE = 30;
    private int[][] board = new int[SIZE][SIZE];
    private boolean isBlackTurn = true;

    public Gomoku() {
        setSize(SIZE * DOT_SIZE, SIZE * DOT_SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / DOT_SIZE;
                int y = e.getY() / DOT_SIZE;
                if (board[x][y] == 0) {
                    board[x][y] = isBlackTurn ? 1 : 2;
                    if (checkWin(isBlackTurn ? 1 : 2)) {
                        JOptionPane.showMessageDialog(null, (isBlackTurn ? "Black" : "Red") + " wins!");
                    }
                    isBlackTurn = !isBlackTurn;
                    repaint();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 1) {
                    g.fillOval(i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE);
                } else if (board[i][j] == 2) {
                    g.setColor(Color.RED);
                    g.fillOval(i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE);
                    g.setColor(Color.BLACK);
                }
                g.drawRect(i * DOT_SIZE, j * DOT_SIZE, DOT_SIZE, DOT_SIZE);
            }
        }
    }

    private boolean checkWin(int player) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (checkLine(i, j, 1, 0, player) ||
                        checkLine(i, j, 0, 1, player) ||
                        checkLine(i, j, 1, 1, player) ||
                        checkLine(i, j, 1, -1, player)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkLine(int x, int y, int dx, int dy, int player) {
        for (int i = 0; i < 5; i++) {
            if (x + i * dx < 0 || x + i * dx >= SIZE ||
                    y + i * dy < 0 || y + i * dy >= SIZE ||
                    board[x + i * dx][y + i * dy] != player) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new Gomoku();
    }
}