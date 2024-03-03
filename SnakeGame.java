import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements KeyListener, ActionListener {
    private static final int BOX_SIZE = 20;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int INITIAL_SNAKE_LENGTH = 3;
    private static final int DELAY = 100;

    private ArrayList<Point> snake;
    private Point food;
    private char direction;
    private boolean gameOver;
    private Timer timer;

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        snake = new ArrayList<>();
        for (int i = 0; i < INITIAL_SNAKE_LENGTH; i++) {
            snake.add(new Point(WIDTH / 2 - BOX_SIZE * i, HEIGHT / 2));
        }
        generateFood();
        direction = 'R';
        gameOver = false;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    private void generateFood() {
        Random random = new Random();
        int x = random.nextInt(WIDTH / BOX_SIZE) * BOX_SIZE;
        int y = random.nextInt(HEIGHT / BOX_SIZE) * BOX_SIZE;
        food = new Point(x, y);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameOver) {
            g.setColor(Color.red);
            g.fillRect(food.x, food.y, BOX_SIZE, BOX_SIZE);
            g.setColor(Color.green);
            for (Point point : snake) {
                g.fillRect(point.x, point.y, BOX_SIZE, BOX_SIZE);
            }
        } else {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Game Over!", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }
    private void move() {
        Point head = snake.get(0);
        int dx = 0, dy = 0;
        switch (direction) {
            case 'U':
                dy = -BOX_SIZE;
                break;
            case 'D':
                dy = BOX_SIZE;
                break;
            case 'L':
                dx = -BOX_SIZE;
                break;
            case 'R':
                dx = BOX_SIZE;
                break;
        }
        Point newHead = new Point(head.x + dx, head.y + dy);
        if (newHead.equals(food)) {
            snake.add(0, newHead);
            generateFood();
        } else {
            snake.add(0, newHead);
            snake.remove(snake.size() - 1);
        }
        checkCollision();
    }
    private void checkCollision() {
        Point head = snake.get(0);
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            gameOver = true;
        }
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver = true;
                break;
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            move();
            repaint();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && direction != 'R') {
            direction = 'L';
        } else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && direction != 'L') {
            direction = 'R';
        } else if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && direction != 'D') {
            direction = 'U';
        } else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && direction != 'U') {
            direction = 'D';
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SnakeGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
