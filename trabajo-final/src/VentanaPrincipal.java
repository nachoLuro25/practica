import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private Color colorJugador1 = Color.RED;
    private Color colorJugador2 = Color.BLUE;

    private Image fondoMenu;

    public VentanaPrincipal() {
        setTitle("CYCLE WARS - Menú Principal");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        fondoMenu = new ImageIcon(getClass().getResource("/assets/fondoMenu.png")).getImage();

        JPanel panelConFondo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondoMenu, 0, 0, getWidth(), getHeight(), this);
            }
        };

        Font fuenteRetro = new Font("Consolas", Font.BOLD, 22);
        Color colorTexto = Color.CYAN;
        Color colorFondo = new Color(0, 0, 0, 180);

        JButton jugarBtn = new JButton("INICIAR JUEGO");
        jugarBtn.setBounds(600, 300, 220, 50);
        jugarBtn.setFont(fuenteRetro);
        jugarBtn.setForeground(colorTexto);
        jugarBtn.setBackground(colorFondo);
        jugarBtn.addActionListener(e -> abrirSeleccionJugadores());
        panelConFondo.add(jugarBtn);

        JButton configBtn = new JButton("CONFIGURACIÓN");
        configBtn.setBounds(600, 370, 220, 50);
        configBtn.setFont(fuenteRetro);
        configBtn.setForeground(colorTexto);
        configBtn.setBackground(colorFondo);
        configBtn.addActionListener(e -> abrirConfiguracion());
        panelConFondo.add(configBtn);

        JButton ayudaBtn = new JButton("¿CÓMO JUGAR?");
        ayudaBtn.setBounds(600, 440, 220, 50);
        ayudaBtn.setFont(fuenteRetro);
        ayudaBtn.setForeground(colorTexto);
        ayudaBtn.setBackground(colorFondo);
        ayudaBtn.addActionListener(e -> mostrarAyuda());
        panelConFondo.add(ayudaBtn);

        JButton salirBtn = new JButton("SALIR");
        salirBtn.setBounds(600, 510, 220, 50);
        salirBtn.setFont(fuenteRetro);
        salirBtn.setForeground(colorTexto);
        salirBtn.setBackground(colorFondo);
        salirBtn.addActionListener(e -> System.exit(0));
        panelConFondo.add(salirBtn);

        setContentPane(panelConFondo);
        setVisible(true);
    }

    // Ventana de selección de jugadores
    private void abrirSeleccionJugadores() {
        JFrame frameSeleccion = new JFrame("Seleccionar Colores - Cycle Wars");
        frameSeleccion.setSize(1200, 550);
        frameSeleccion.setLocationRelativeTo(this);
        frameSeleccion.setLayout(new BorderLayout());

        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 20, 20));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Color.BLACK);

        // Jugador 1
        JPanel panelJ1 = new JPanel(new BorderLayout());
        panelJ1.setBackground(Color.BLACK);

        JLabel labelJ1 = new JLabel("JUGADOR 1", SwingConstants.CENTER);
        labelJ1.setFont(new Font("Consolas", Font.BOLD, 26));
        labelJ1.setForeground(Color.CYAN);

        JPanel opcionesJ1 = new JPanel(new GridLayout(2, 2, 10, 10));
        opcionesJ1.setBackground(Color.BLACK);
        opcionesJ1.add(crearBotonColor("/assets/perfilRojo.png", Color.RED, true));
        opcionesJ1.add(crearBotonColor("/assets/perfilAzul.png", Color.BLUE, true));
        opcionesJ1.add(crearBotonColor("/assets/perfilAmarillo.png", Color.YELLOW, true));
        opcionesJ1.add(crearBotonColor("/assets/perfilVerde.png", Color.GREEN, true));

        panelJ1.add(labelJ1, BorderLayout.NORTH);
        panelJ1.add(opcionesJ1, BorderLayout.CENTER);

        // Jugador 2
        JPanel panelJ2 = new JPanel(new BorderLayout());
        panelJ2.setBackground(Color.BLACK);

        JLabel labelJ2 = new JLabel("JUGADOR 2", SwingConstants.CENTER);
        labelJ2.setFont(new Font("Consolas", Font.BOLD, 26));
        labelJ2.setForeground(Color.CYAN);

        JPanel opcionesJ2 = new JPanel(new GridLayout(2, 2, 10, 10));
        opcionesJ2.setBackground(Color.BLACK);
        opcionesJ2.add(crearBotonColor("/assets/perfilRojo.png", Color.RED, false));
        opcionesJ2.add(crearBotonColor("/assets/perfilAzul.png", Color.BLUE, false));
        opcionesJ2.add(crearBotonColor("/assets/perfilAmarillo.png", Color.YELLOW, false));
        opcionesJ2.add(crearBotonColor("/assets/perfilVerde.png", Color.GREEN, false));

        panelJ2.add(labelJ2, BorderLayout.NORTH);
        panelJ2.add(opcionesJ2, BorderLayout.CENTER);

        panelPrincipal.add(panelJ1);
        panelPrincipal.add(panelJ2);

        // Botón comenzar
        JButton comenzarBtn = new JButton("COMENZAR");
        comenzarBtn.setFont(new Font("Consolas", Font.BOLD, 20));
        comenzarBtn.addActionListener(e -> {
            frameSeleccion.dispose();
            abrirJuego();
        });

        frameSeleccion.add(panelPrincipal, BorderLayout.CENTER);
        frameSeleccion.add(comenzarBtn, BorderLayout.SOUTH);
        frameSeleccion.getContentPane().setBackground(Color.BLACK);
        frameSeleccion.setVisible(true);
    }

    // Crea un botón con imagen escalada y selección visual + hover
    private JButton crearBotonColor(String rutaImagen, Color color, boolean esJugador1) {
        JButton btn;
        if (rutaImagen != null) {
            ImageIcon icon = new ImageIcon(getClass().getResource(rutaImagen));
            Image img = icon.getImage().getScaledInstance(270, 250, Image.SCALE_SMOOTH);
            btn = new JButton(new ImageIcon(img));
            btn.setBackground(Color.BLACK);
            btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        } else {
            btn = new JButton();
            btn.setBackground(color);
            btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
        }

        // Acción al hacer clic (seleccionar personaje)
        btn.addActionListener(e -> {
            if (esJugador1) {
                colorJugador1 = color;
                resetearSeleccion(true, btn);
            } else {
                colorJugador2 = color;
                resetearSeleccion(false, btn);
            }
            btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 4));
            btn.putClientProperty("seleccionado", true);
        });

        // Hover con MouseListener
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (btn.getClientProperty("seleccionado") == null) {
                    btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (btn.getClientProperty("seleccionado") == null) {
                    btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
                }
            }
        });

        return btn;
    }

    // Quita selección de los otros botones y mantiene el nuevo
    private void resetearSeleccion(boolean esJugador1, JButton botonSeleccionado) {
        Container parent = botonSeleccionado.getParent();
        JPanel panel = (JPanel) parent; // Grid con 4 botones
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton btn) {
                btn.putClientProperty("seleccionado", null);
                btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
            }
        }
    }

    private void abrirConfiguracion() {
        JFrame frameConfig = new JFrame("Configuración - Cycle Wars");
        frameConfig.setSize(400, 200);
        frameConfig.setLocationRelativeTo(this);
        frameConfig.setLayout(new BorderLayout());

        JLabel lblBrillo = new JLabel("Brillo del juego", JLabel.CENTER);
        JSlider sliderBrillo = new JSlider(0, 100, 50);
        sliderBrillo.setMajorTickSpacing(25);
        sliderBrillo.setPaintTicks(true);
        sliderBrillo.setPaintLabels(true);

        frameConfig.add(lblBrillo, BorderLayout.NORTH);
        frameConfig.add(sliderBrillo, BorderLayout.CENTER);

        frameConfig.setVisible(true);
    }

    private void mostrarAyuda() {
        String mensaje = """
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

    private void abrirJuego() {
        JFrame frameJuego = new JFrame("Juego de Motos Tron - 2 Jugadores con Habilidades");
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
