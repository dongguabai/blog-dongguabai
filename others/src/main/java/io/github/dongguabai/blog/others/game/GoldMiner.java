package io.github.dongguabai.blog.others.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author dongguabai
 * @date 2024-01-24 21:24
 *
 * "黄金矿工"是一款非常受欢迎的休闲游戏。在游戏中，玩家扮演一名矿工，使用一个挂钩来抓取地下的黄金和其他宝藏。挂钩会在屏幕上来回摆动，玩家需要在正确的时机点击屏幕，使挂钩向下抓取。抓取的目标可以是黄金、石头、钻石等各种物品，每种物品的价值和重量都不同。
 *
 * 玩家需要在限定的时间内尽可能地获取更多的金钱。游戏中还有各种道具可以帮助玩家，例如可以增加挂钩抓取速度的道具，或者可以炸掉无价值物品的道具等。
 *
 * 这个游戏需要玩家有良好的时机控制和策略规划能力。通过不断地玩游戏，玩家可以提高自己的技巧，获取更多的金钱，从而达到更高的分数。
 */
public class GoldMiner extends JFrame {
    private int score = 0;
    private JLabel scoreLabel;
    private JButton button;
    private Hook hook;

    public GoldMiner() {
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        scoreLabel = new JLabel("Score: 0");
        add(scoreLabel, BorderLayout.NORTH);

        button = new JButton("Mine Gold");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score += 10;
                scoreLabel.setText("Score: " + score);
            }
        });
        add(button, BorderLayout.SOUTH);

        hook = new Hook();
        add(hook, BorderLayout.CENTER);

        Timer timer = new Timer(10, e -> hook.move());
        timer.start();

        setVisible(true);
    }

    public static void main(String[] args) {
        new GoldMiner();
    }

    class Hook extends JPanel {
        private int x = 0;
        private int direction = 1;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawLine(getWidth() / 2, 0, getWidth() / 2 + x, getHeight() / 2);
            g.fillOval(getWidth() / 2 + x - 10, getHeight() / 2 - 10, 20, 20);
        }

        public void move() {
            x += direction;
            if (x > getWidth() / 2) {
                direction = -1;
            } else if (x < -getWidth() / 2) {
                direction = 1;
            }
            repaint();
        }
    }
}