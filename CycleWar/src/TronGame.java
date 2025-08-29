import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.io.File;

public class TronGame extends JPanel implements ActionListener, KeyListener {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int UNIT = 20;
    private static final int DELAY = 100;

    private LinkedList<Point> player1Trail = new LinkedList<>();
    private LinkedList<Point> player2Trail = new LinkedList<>();

    private Point player1;
    private Point player2;

    private char dir1 = 'R';
    private char dir2 = 'L';

    private Timer timer;
    private boolean running = false;
    private String winner = null;

    private Color player1Color;
    private Color player2Color;

    private Image imgPlayer1;
    private Image imgPlayer2;
    private int motoSize = 40; // tamaño de la imagen de la moto

    public TronGame(Color p1Color, Color p2Color) {
        this.player1Color = p1Color;
        this.player2Color = p2Color;

        // Cargar imágenes según el color inicial
        updateImages();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        startGame();
    }

    // Actualiza las imágenes según los colores elegidos
    private void updateImages() {
        String basePath = System.getProperty("user.dir") + File.separator + "assets" + File.separator;

        // Jugador 1
        if (player1Color == Color.RED || player1Color == Color.BLUE) {
            imgPlayer1 = new ImageIcon(basePath + "Red.png").getImage();
        } else {
            imgPlayer1 = new ImageIcon(basePath + "Yellow.png").getImage();
        }

        // Jugador 2
        if (player2Color == Color.RED || player2Color == Color.BLUE) {
            imgPlayer2 = new ImageIcon(basePath + "Red.png").getImage();
        } else {
            imgPlayer2 = new ImageIcon(basePath + "Yellow.png").getImage();
        }
    }

    public void startGame() {
        if (timer != null) {
            timer.stop();
        }
        player1 = new Point(WIDTH / 4, HEIGHT / 2);
        player2 = new Point(3 * WIDTH / 4, HEIGHT / 2);
        player1Trail.clear();
        player2Trail.clear();
        player1Trail.add(new Point(player1));
        player2Trail.add(new Point(player2));
        dir1 = 'R';
        dir2 = 'L';
        winner = null;
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Dibuja la estela del jugador 1
            g.setColor(player1Color);
            for (Point p : player1Trail) {
                g.fillRect(p.x, p.y, UNIT, UNIT);
            }

            // Dibuja la estela del jugador 2
            g.setColor(player2Color);
            for (Point p : player2Trail) {
                g.fillRect(p.x, p.y, UNIT, UNIT);
            }

            // Dibuja las motos con imágenes al frente de la estela
            g.drawImage(imgPlayer1, player1.x, player1.y, motoSize, motoSize, this);
            g.drawImage(imgPlayer2, player2.x, player2.y, motoSize, motoSize, this);

        } else {
            if (winner != null) {
                g.setColor(Color.white);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics fm = getFontMetrics(g.getFont());
                String message = winner + " gana! Presiona R para reiniciar.";
                g.drawString(message, (WIDTH - fm.stringWidth(message)) / 2, HEIGHT / 2);
            }
        }
    }

    public void move() {
        if (!running) return;
        switch (dir1) {
            case 'U': player1.y -= UNIT; break;
            case 'D': player1.y += UNIT; break;
            case 'L': player1.x -= UNIT; break;
            case 'R': player1.x += UNIT; break;
        }
        player1Trail.add(new Point(player1));
        switch (dir2) {
            case 'U': player2.y -= UNIT; break;
            case 'D': player2.y += UNIT; break;
            case 'L': player2.x -= UNIT; break;
            case 'R': player2.x += UNIT; break;
        }
        player2Trail.add(new Point(player2));
    }

    public void checkCollisions() {
        if (player1.x < 0 || player1.x >= WIDTH || player1.y < 0 || player1.y >= HEIGHT) {
            running = false; winner = "Jugador 2"; }
        else if (player2.x < 0 || player2.x >= WIDTH || player2.y < 0 || player2.y >= HEIGHT) {
            running = false; winner = "Jugador 1"; }

        for (Point p : player1Trail) {
            if (p.equals(player1) && p != player1Trail.getLast()) {
                running = false; winner = "Jugador 2"; break;
            }
        }
        for (Point p : player2Trail) {
            if (p.equals(player2) && p != player2Trail.getLast()) {
                running = false; winner = "Jugador 1"; break;
            }
        }
        for (Point p : player1Trail) {
            if (p.equals(player2)) {
                running = false; winner = "Jugador 1"; break;
            }
        }
        for (Point p : player2Trail) {
            if (p.equals(player1)) {
                running = false; winner = "Jugador 2"; break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkCollisions();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: if (dir1 != 'D') dir1 = 'U'; break;
            case KeyEvent.VK_S: if (dir1 != 'U') dir1 = 'D'; break;
            case KeyEvent.VK_A: if (dir1 != 'R') dir1 = 'L'; break;
            case KeyEvent.VK_D: if (dir1 != 'L') dir1 = 'R'; break;
            case KeyEvent.VK_UP: if (dir2 != 'D') dir2 = 'U'; break;
            case KeyEvent.VK_DOWN: if (dir2 != 'U') dir2 = 'D'; break;
            case KeyEvent.VK_LEFT: if (dir2 != 'R') dir2 = 'L'; break;
            case KeyEvent.VK_RIGHT: if (dir2 != 'L') dir2 = 'R'; break;
            case KeyEvent.VK_R:
                if (!running) startGame();
                updateImages(); // actualizar imágenes según los colores elegidos antes de reiniciar
                break;
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Juego de Motos Tron - 2 Jugadores");
        frame.add(new TronGame(Color.RED, Color.YELLOW));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
