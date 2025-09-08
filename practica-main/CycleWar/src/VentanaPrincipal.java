import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    // Colores por defecto
    private Color colorJugador1 = Color.RED;
    private Color colorJugador2 = Color.BLUE;

    // Imagen de fondo
    private Image fondoMenu;
    private float brillo = 1.0f; // brillo inicial

    public VentanaPrincipal() {
        setTitle("CYCLE WARS - Menú Principal");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Cargar la imagen de fondo
        fondoMenu = new ImageIcon(getClass().getResource("/assets/fondoMenu.png")).getImage();

        // Panel con fondo dibujado y control de brillo
        JPanel panelConFondo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.drawImage(fondoMenu, 0, 0, getWidth(), getHeight(), this);

                // aplicar brillo (oscurece la pantalla si < 1.0f)
                if (brillo < 1.0f) {
                    g2d.setColor(new Color(0, 0, 0, (int) ((1 - brillo) * 255)));
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
                g2d.dispose();
            }
        };

        // Estilo retro
        Font fuenteRetro = new Font("Consolas", Font.BOLD, 22);
        Color colorTexto = Color.CYAN;
        Color colorFondo = new Color(0, 0, 0, 180);

        // Botón INICIAR JUEGO
        JButton jugarBtn = new JButton("INICIAR JUEGO");
        jugarBtn.setBounds(600, 300, 220, 50);
        jugarBtn.setFont(fuenteRetro);
        jugarBtn.setForeground(colorTexto);
        jugarBtn.setBackground(colorFondo);
        jugarBtn.addActionListener(e -> abrirSeleccionColores());
        panelConFondo.add(jugarBtn);

        // Botón CONFIGURACIÓN
        JButton configBtn = new JButton("CONFIGURACIÓN");
        configBtn.setBounds(600, 370, 220, 50);
        configBtn.setFont(fuenteRetro);
        configBtn.setForeground(colorTexto);
        configBtn.setBackground(colorFondo);
        configBtn.addActionListener(e -> abrirConfiguracion());
        panelConFondo.add(configBtn);

        // Botón ¿CÓMO JUGAR?
        JButton ayudaBtn = new JButton("¿CÓMO JUGAR?");
        ayudaBtn.setBounds(600, 440, 220, 50);
        ayudaBtn.setFont(fuenteRetro);
        ayudaBtn.setForeground(colorTexto);
        ayudaBtn.setBackground(colorFondo);
        ayudaBtn.addActionListener(e -> mostrarAyuda());
        panelConFondo.add(ayudaBtn);

        // Botón SALIR
        JButton salirBtn = new JButton("SALIR");
        salirBtn.setBounds(600, 510, 220, 50);
        salirBtn.setFont(fuenteRetro);
        salirBtn.setForeground(colorTexto);
        salirBtn.setBackground(colorFondo);
        salirBtn.addActionListener(e -> System.exit(0));
        panelConFondo.add(salirBtn);

        // Usar panel con fondo como contenido
        setContentPane(panelConFondo);
        setVisible(true);
    }

    // --- Pantalla de selección de colores al iniciar juego ---
    private void abrirSeleccionColores() {
        JFrame frameColores = new JFrame("Seleccionar Colores - Cycle Wars");
        frameColores.setSize(800, 500);
        frameColores.setLocationRelativeTo(this);
        frameColores.setLayout(new BorderLayout());

        JPanel panelColores = new JPanel(new GridLayout(2, 1, 10, 10));

        // --- Jugador 1 ---
        JPanel panelJ1 = new JPanel();
        panelJ1.setBorder(BorderFactory.createTitledBorder("Color Jugador 1 - Controles: WASD + C"));
        panelJ1.add(createColorButton("Rojo", Color.RED, true));
        panelJ1.add(createColorButton("Azul", Color.BLUE, true));
        panelJ1.add(createColorButton("Amarillo", Color.YELLOW, true));
        panelJ1.add(createColorButton("Verde", Color.GREEN, true));

        // --- Jugador 2 ---
        JPanel panelJ2 = new JPanel();
        panelJ2.setBorder(BorderFactory.createTitledBorder("Color Jugador 2 - Controles: Flechas + M"));
        panelJ2.add(createColorButton("Rojo", Color.RED, false));
        panelJ2.add(createColorButton("Azul", Color.BLUE, false));
        panelJ2.add(createColorButton("Amarillo", Color.YELLOW, false));
        panelJ2.add(createColorButton("Verde", Color.GREEN, false));

        panelColores.add(panelJ1);
        panelColores.add(panelJ2);

        // Botón para comenzar el juego
        JButton comenzarBtn = new JButton("COMENZAR");
        comenzarBtn.setFont(new Font("Consolas", Font.BOLD, 20));
        comenzarBtn.addActionListener(e -> {
            frameColores.dispose();
            abrirJuego();
        });

        frameColores.add(panelColores, BorderLayout.CENTER);
        frameColores.add(comenzarBtn, BorderLayout.SOUTH);
        frameColores.setVisible(true);
    }

    // --- Configuración: solo control de brillo ---
    private void abrirConfiguracion() {
        JFrame frameConfig = new JFrame("Configuración - Brillo");
        frameConfig.setSize(400, 200);
        frameConfig.setLocationRelativeTo(this);

        JSlider sliderBrillo = new JSlider(0, 100, (int) (brillo * 100));
        sliderBrillo.setMajorTickSpacing(25);
        sliderBrillo.setPaintTicks(true);
        sliderBrillo.setPaintLabels(true);

        sliderBrillo.addChangeListener(e -> {
            brillo = sliderBrillo.getValue() / 100f;
            repaint(); // actualiza el fondo
        });

        frameConfig.add(new JLabel(" Ajustar brillo:"), BorderLayout.NORTH);
        frameConfig.add(sliderBrillo, BorderLayout.CENTER);
        frameConfig.setVisible(true);
    }

    // --- Muestra la ayuda (controles + habilidades) ---
    private void mostrarAyuda() {
        String mensaje = """
        🎮 ¿CÓMO JUGAR CYCLE WARS?

        📋 CONTROLES:
        • Jugador 1: WASD para moverse, C para habilidad
        • Jugador 2: Flechas para moverse, M para habilidad
        • R para reiniciar partida

        🚀 Objetivo:
        Encierra a tu rival con tu estela de luz.
        Usa tu habilidad especial en el momento justo.

        🟦 HABILIDADES ESPECIALES (una vez por partida):

        🔵 AZUL - VELOCIDAD SUPERIOR:
        • Duración: 5 segundos
        • Efecto: Velocidad doble
        • Estrategia: Ideal para escapar o alcanzar

        🟡 AMARILLO - INVISIBILIDAD:
        • Duración: 3 segundos
        • Efecto: Atravesar estelas
        • Estrategia: Perfecto en zonas congestionadas

        🔴 ROJO - EXPLOSIÓN:
        • Instantáneo
        • Efecto: Destruye estelas cercanas
        • Estrategia: Crea espacio libre

        🟢 VERDE - CONFUSIÓN:
        • Duración: 3 segundos
        • Efecto: Invierte controles del rival
        • Estrategia: Desorientar al enemigo
        """;

        JTextArea area = new JTextArea(mensaje);
        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 14));
        area.setBackground(Color.BLACK);
        area.setForeground(Color.CYAN);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 500));

        JOptionPane.showMessageDialog(this, scroll, "¿Cómo jugar?", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- Botones de colores ---
    private JButton createColorButton(String text, Color color, boolean isPlayer1) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        if (color == Color.YELLOW || color == Color.GREEN) {
            button.setForeground(Color.BLACK);
        } else {
            button.setForeground(Color.WHITE);
        }

        button.addActionListener(e -> {
            if (isPlayer1) {
                colorJugador1 = color;
            } else {
                colorJugador2 = color;
            }
        });
        return button;
    }

    // --- Abre la ventana real del juego ---
    private void abrirJuego() {
        JFrame frameJuego = new JFrame("Juego de Motos Tron - Cycle Wars");
        frameJuego.add(new TronGame(colorJugador1, colorJugador2));
        frameJuego.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameJuego.pack();
        frameJuego.setLocationRelativeTo(null);
        frameJuego.setVisible(true);
    }

    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}
