import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Iterator;
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
    private int motoSize = 40;

    // Variables para habilidades especiales
    private boolean player1AbilityUsed = false;
    private boolean player2AbilityUsed = false;

    // Habilidad Azul - Velocidad
    private boolean player1SpeedBoost = false;
    private boolean player2SpeedBoost = false;
    private int player1SpeedTimer = 0;
    private int player2SpeedTimer = 0;
    private Timer speedTimer1;
    private Timer speedTimer2;

    // Habilidad Amarillo - Invisibilidad
    private boolean player1Invisible = false;
    private boolean player2Invisible = false;
    private int player1InvisibleTimer = 0;
    private int player2InvisibleTimer = 0;
    private Timer invisTimer1;
    private Timer invisTimer2;

    // Habilidad Verde - Controles invertidos
    private boolean player1ControlsInverted = false;
    private boolean player2ControlsInverted = false;
    private int player1InvertTimer = 0;
    private int player2InvertTimer = 0;
    private Timer invertTimer1;
    private Timer invertTimer2;

    public TronGame(Color p1Color, Color p2Color) {
        this.player1Color = p1Color;
        this.player2Color = p2Color;

        updateImages();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);
        startGame();
    }

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
        // Detener todos los timers de habilidades
        stopAllAbilityTimers();

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

        // Resetear habilidades
        resetAbilities();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void stopAllAbilityTimers() {
        if (speedTimer1 != null) speedTimer1.stop();
        if (speedTimer2 != null) speedTimer2.stop();
        if (invisTimer1 != null) invisTimer1.stop();
        if (invisTimer2 != null) invisTimer2.stop();
        if (invertTimer1 != null) invertTimer1.stop();
        if (invertTimer2 != null) invertTimer2.stop();
    }

    private void resetAbilities() {
        player1AbilityUsed = false;
        player2AbilityUsed = false;
        player1SpeedBoost = false;
        player2SpeedBoost = false;
        player1Invisible = false;
        player2Invisible = false;
        player1ControlsInverted = false;
        player2ControlsInverted = false;
        player1SpeedTimer = 0;
        player2SpeedTimer = 0;
        player1InvisibleTimer = 0;
        player2InvisibleTimer = 0;
        player1InvertTimer = 0;
        player2InvertTimer = 0;
    }

    // Activar habilidad del jugador 1
    private void activatePlayer1Ability() {
        if (player1AbilityUsed || !running) return;

        player1AbilityUsed = true;

        if (player1Color == Color.BLUE) {
            // Habilidad Azul - Velocidad (MODIFICADO: 3 segundos)
            player1SpeedBoost = true;
            player1SpeedTimer = 30; // 3 segundos a 100ms = 30 ticks
            speedTimer1 = new Timer(DELAY, e -> {
                player1SpeedTimer--;
                if (player1SpeedTimer <= 0) {
                    player1SpeedBoost = false;
                    speedTimer1.stop();
                }
            });
            speedTimer1.start();

        } else if (player1Color == Color.YELLOW) {
            // Habilidad Amarillo - Invisibilidad
            player1Invisible = true;
            player1InvisibleTimer = 30; // 3 segundos
            invisTimer1 = new Timer(DELAY, e -> {
                player1InvisibleTimer--;
                if (player1InvisibleTimer <= 0) {
                    player1Invisible = false;
                    invisTimer1.stop();
                }
            });
            invisTimer1.start();

        } else if (player1Color == Color.RED) {
            // Habilidad Rojo - Explosión (MODIFICADO: 5 bloques de rango)
            explodeTrails(player1, player1Trail);

        } else if (player1Color == Color.GREEN) {
            // Habilidad Verde - Invertir controles del oponente
            player2ControlsInverted = true;
            player2InvertTimer = 30; // 3 segundos
            invertTimer2 = new Timer(DELAY, e -> {
                player2InvertTimer--;
                if (player2InvertTimer <= 0) {
                    player2ControlsInverted = false;
                    invertTimer2.stop();
                }
            });
            invertTimer2.start();
        }
    }

    // Activar habilidad del jugador 2
    private void activatePlayer2Ability() {
        if (player2AbilityUsed || !running) return;

        player2AbilityUsed = true;

        if (player2Color == Color.BLUE) {
            // Habilidad Azul - Velocidad (MODIFICADO: 3 segundos)
            player2SpeedBoost = true;
            player2SpeedTimer = 30; // 3 segundos
            speedTimer2 = new Timer(DELAY, e -> {
                player2SpeedTimer--;
                if (player2SpeedTimer <= 0) {
                    player2SpeedBoost = false;
                    speedTimer2.stop();
                }
            });
            speedTimer2.start();

        } else if (player2Color == Color.YELLOW) {
            // Habilidad Amarillo - Invisibilidad
            player2Invisible = true;
            player2InvisibleTimer = 30; // 3 segundos
            invisTimer2 = new Timer(DELAY, e -> {
                player2InvisibleTimer--;
                if (player2InvisibleTimer <= 0) {
                    player2Invisible = false;
                    invisTimer2.stop();
                }
            });
            invisTimer2.start();

        } else if (player2Color == Color.RED) {
            // Habilidad Rojo - Explosión (MODIFICADO: 5 bloques de rango)
            explodeTrails(player2, player2Trail);

        } else if (player2Color == Color.GREEN) {
            // Habilidad Verde - Invertir controles del oponente
            player1ControlsInverted = true;
            player1InvertTimer = 30; // 3 segundos
            invertTimer1 = new Timer(DELAY, e -> {
                player1InvertTimer--;
                if (player1InvertTimer <= 0) {
                    player1ControlsInverted = false;
                    invertTimer1.stop();
                }
            });
            invertTimer1.start();
        }
    }

    // Explosión que destruye estelas cercanas (MODIFICADO: rango de 5 bloques)
    private void explodeTrails(Point center, LinkedList<Point> excludeTrail) {
        int explosionRange = UNIT * 5; // MODIFICADO: 5 bloques de rango (era 3)

        // Destruir estelas del jugador 1
        if (excludeTrail != player1Trail) {
            Iterator<Point> iter1 = player1Trail.iterator();
            while (iter1.hasNext()) {
                Point p = iter1.next();
                if (Math.abs(p.x - center.x) <= explosionRange &&
                        Math.abs(p.y - center.y) <= explosionRange) {
                    iter1.remove();
                }
            }
        }

        // Destruir estelas del jugador 2
        if (excludeTrail != player2Trail) {
            Iterator<Point> iter2 = player2Trail.iterator();
            while (iter2.hasNext()) {
                Point p = iter2.next();
                if (Math.abs(p.x - center.x) <= explosionRange &&
                        Math.abs(p.y - center.y) <= explosionRange) {
                    iter2.remove();
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // Dibuja la estela del jugador 1 (solo si no es invisible)
            if (!player1Invisible) {
                g.setColor(player1Color);
                for (Point p : player1Trail) {
                    g.fillRect(p.x, p.y, UNIT, UNIT);
                }
            }

            // Dibuja la estela del jugador 2 (solo si no es invisible)
            if (!player2Invisible) {
                g.setColor(player2Color);
                for (Point p : player2Trail) {
                    g.fillRect(p.x, p.y, UNIT, UNIT);
                }
            }

            // Dibuja las motos (con efecto de invisibilidad)
            if (!player1Invisible) {
                g.drawImage(imgPlayer1, player1.x, player1.y, motoSize, motoSize, this);
            } else {
                // Efecto de transparencia para jugador invisible
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2d.drawImage(imgPlayer1, player1.x, player1.y, motoSize, motoSize, this);
                g2d.dispose();
            }

            if (!player2Invisible) {
                g.drawImage(imgPlayer2, player2.x, player2.y, motoSize, motoSize, this);
            } else {
                // Efecto de transparencia para jugador invisible
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                g2d.drawImage(imgPlayer2, player2.x, player2.y, motoSize, motoSize, this);
                g2d.dispose();
            }

            // Indicadores de habilidades
            drawAbilityStatus(g);

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

    private void drawAbilityStatus(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 14));

        // Estado del Jugador 1
        g.setColor(player1Color);
        String p1Status = "J1: ";
        if (player1AbilityUsed) {
            p1Status += "USADA";
        } else {
            p1Status += getAbilityName(player1Color) + " (C)";
        }
        g.drawString(p1Status, 10, 30);

        // Efectos activos del jugador 1
        if (player1SpeedBoost) {
            g.drawString("VELOCIDAD: " + (player1SpeedTimer/10 + 1) + "s", 10, 50);
        }
        if (player1Invisible) {
            g.drawString("INVISIBLE: " + (player1InvisibleTimer/10 + 1) + "s", 10, 50);
        }
        if (player1ControlsInverted) {
            g.drawString("CONTROLES INVERTIDOS: " + (player1InvertTimer/10 + 1) + "s", 10, 50);
        }

        // Estado del Jugador 2
        g.setColor(player2Color);
        String p2Status = "J2: ";
        if (player2AbilityUsed) {
            p2Status += "USADA";
        } else {
            p2Status += getAbilityName(player2Color) + " (M)";
        }
        g.drawString(p2Status, WIDTH - 200, 30);

        // Efectos activos del jugador 2
        if (player2SpeedBoost) {
            g.drawString("VELOCIDAD: " + (player2SpeedTimer/10 + 1) + "s", WIDTH - 200, 50);
        }
        if (player2Invisible) {
            g.drawString("INVISIBLE: " + (player2InvisibleTimer/10 + 1) + "s", WIDTH - 200, 50);
        }
        if (player2ControlsInverted) {
            g.drawString("CONTROLES INVERTIDOS: " + (player2InvertTimer/10 + 1) + "s", WIDTH - 200, 50);
        }
    }

    private String getAbilityName(Color color) {
        if (color == Color.BLUE) return "VELOCIDAD";
        if (color == Color.YELLOW) return "INVISIBILIDAD";
        if (color == Color.RED) return "EXPLOSIÓN";
        if (color == Color.GREEN) return "CONFUSIÓN";
        return "DESCONOCIDA";
    }

    public void move() {
        if (!running) return;

        // Movimiento del jugador 1 (con velocidad aumentada si tiene boost)
        int moves1 = player1SpeedBoost ? 2 : 1;
        for (int i = 0; i < moves1; i++) {
            switch (dir1) {
                case 'U': player1.y -= UNIT; break;
                case 'D': player1.y += UNIT; break;
                case 'L': player1.x -= UNIT; break;
                case 'R': player1.x += UNIT; break;
            }
            player1Trail.add(new Point(player1));
        }

        // Movimiento del jugador 2 (con velocidad aumentada si tiene boost)
        int moves2 = player2SpeedBoost ? 2 : 1;
        for (int i = 0; i < moves2; i++) {
            switch (dir2) {
                case 'U': player2.y -= UNIT; break;
                case 'D': player2.y += UNIT; break;
                case 'L': player2.x -= UNIT; break;
                case 'R': player2.x += UNIT; break;
            }
            player2Trail.add(new Point(player2));
        }
    }

    public void checkCollisions() {
        // Colisiones con bordes
        if (player1.x < 0 || player1.x >= WIDTH || player1.y < 0 || player1.y >= HEIGHT) {
            running = false; winner = "Jugador 2";
        } else if (player2.x < 0 || player2.x >= WIDTH || player2.y < 0 || player2.y >= HEIGHT) {
            running = false; winner = "Jugador 1";
        }

        // Colisiones con estela propia (solo si no es invisible)
        if (!player1Invisible) {
            for (Point p : player1Trail) {
                if (p.equals(player1) && p != player1Trail.getLast()) {
                    running = false; winner = "Jugador 2"; break;
                }
            }
        }

        if (!player2Invisible) {
            for (Point p : player2Trail) {
                if (p.equals(player2) && p != player2Trail.getLast()) {
                    running = false; winner = "Jugador 1"; break;
                }
            }
        }

        // Colisiones entre jugadores (solo si no son invisibles)
        if (!player1Invisible) {
            for (Point p : player2Trail) {
                if (p.equals(player1)) {
                    running = false; winner = "Jugador 2"; break;
                }
            }
        }

        if (!player2Invisible) {
            for (Point p : player1Trail) {
                if (p.equals(player2)) {
                    running = false; winner = "Jugador 1"; break;
                }
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
            // Controles Jugador 1 (con posible inversión)
            case KeyEvent.VK_W:
                if (player1ControlsInverted) {
                    if (dir1 != 'U') dir1 = 'D';
                } else {
                    if (dir1 != 'D') dir1 = 'U';
                }
                break;
            case KeyEvent.VK_S:
                if (player1ControlsInverted) {
                    if (dir1 != 'D') dir1 = 'U';
                } else {
                    if (dir1 != 'U') dir1 = 'D';
                }
                break;
            case KeyEvent.VK_A:
                if (player1ControlsInverted) {
                    if (dir1 != 'L') dir1 = 'R';
                } else {
                    if (dir1 != 'R') dir1 = 'L';
                }
                break;
            case KeyEvent.VK_D:
                if (player1ControlsInverted) {
                    if (dir1 != 'R') dir1 = 'L';
                } else {
                    if (dir1 != 'L') dir1 = 'R';
                }
                break;

            // Controles Jugador 2 (con posible inversión)
            case KeyEvent.VK_UP:
                if (player2ControlsInverted) {
                    if (dir2 != 'U') dir2 = 'D';
                } else {
                    if (dir2 != 'D') dir2 = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (player2ControlsInverted) {
                    if (dir2 != 'D') dir2 = 'U';
                } else {
                    if (dir2 != 'U') dir2 = 'D';
                }
                break;
            case KeyEvent.VK_LEFT:
                if (player2ControlsInverted) {
                    if (dir2 != 'L') dir2 = 'R';
                } else {
                    if (dir2 != 'R') dir2 = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (player2ControlsInverted) {
                    if (dir2 != 'R') dir2 = 'L';
                } else {
                    if (dir2 != 'L') dir2 = 'R';
                }
                break;

            // Habilidades especiales
            case KeyEvent.VK_C:
                activatePlayer1Ability();
                break;
            case KeyEvent.VK_M:
                activatePlayer2Ability();
                break;

            case KeyEvent.VK_R:
                if (!running) startGame();
                updateImages();
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