import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    // Colores por defecto
    private Color colorJugador1 = Color.RED;
    private Color colorJugador2 = Color.BLUE;

    public VentanaPrincipal() {
        setTitle("Menú Principal - Tron Con Habilidades");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior con botones JUGAR y SALIR
        JPanel panelSuperior = new JPanel();
        JButton jugarBtn = new JButton("JUGAR");
        JButton salirBtn = new JButton("SALIR");

        jugarBtn.setFont(new Font("Arial", Font.BOLD, 18));
        salirBtn.setFont(new Font("Arial", Font.BOLD, 18));

        jugarBtn.addActionListener(e -> abrirJuego());
        salirBtn.addActionListener(e -> System.exit(0));

        panelSuperior.add(jugarBtn);
        panelSuperior.add(salirBtn);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel central principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel para selección de colores
        JPanel panelColores = new JPanel(new GridLayout(2, 1, 10, 10));

        // --- Selección Jugador 1 ---
        JPanel panelJ1 = new JPanel();
        panelJ1.setBorder(BorderFactory.createTitledBorder("Color Jugador 1 - Controles: WASD + C para habilidad"));

        JButton rojo1 = createColorButton("Rojo", Color.RED, true);
        JButton azul1 = createColorButton("Azul", Color.BLUE, true);
        JButton amarillo1 = createColorButton("Amarillo", Color.YELLOW, true);
        JButton verde1 = createColorButton("Verde", Color.GREEN, true);

        panelJ1.add(rojo1);
        panelJ1.add(azul1);
        panelJ1.add(amarillo1);
        panelJ1.add(verde1);

        // --- Selección Jugador 2 ---
        JPanel panelJ2 = new JPanel();
        panelJ2.setBorder(BorderFactory.createTitledBorder("Color Jugador 2 - Controles: Flechas + M para habilidad"));

        JButton rojo2 = createColorButton("Rojo", Color.RED, false);
        JButton azul2 = createColorButton("Azul", Color.BLUE, false);
        JButton amarillo2 = createColorButton("Amarillo", Color.YELLOW, false);
        JButton verde2 = createColorButton("Verde", Color.GREEN, false);

        panelJ2.add(rojo2);
        panelJ2.add(azul2);
        panelJ2.add(amarillo2);
        panelJ2.add(verde2);

        panelColores.add(panelJ1);
        panelColores.add(panelJ2);

        // Panel de información de habilidades
        JPanel panelHabilidades = createAbilitiesInfoPanel();

        panelPrincipal.add(panelColores, BorderLayout.NORTH);
        panelPrincipal.add(panelHabilidades, BorderLayout.CENTER);

        add(panelPrincipal, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createColorButton(String text, Color color, boolean isPlayer1) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setOpaque(true);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        // Cambiar color del texto según el fondo
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

    private JPanel createAbilitiesInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Habilidades Especiales (Una vez por partida)"));

        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 14));
        infoArea.setBackground(getBackground());

        String info = """
                🔵 AZUL - VELOCIDAD SUPERIOR:
                   • Duración: 5 segundos
                   • Efecto: Velocidad doble de movimiento
                   • Estrategia: Ideal para alcanzar al oponente o escapar
                
                🟡 AMARILLO - INVISIBILIDAD:
                   • Duración: 3 segundos  
                   • Efecto: Modo fantasma - atraviesa estelas sin colisionar
                   • Estrategia: Perfecto para escapar en zonas congestionadas
                
                🔴 ROJO - EXPLOSIÓN:
                   • Duración: Instantáneo
                   • Efecto: Destruye estelas en un rango de 1 bloque alrededor
                   • Estrategia: Crea espacios libres o bloquea rutas del enemigo
                
                🟢 VERDE - CONFUSIÓN:
                   • Duración: 3 segundos
                   • Efecto: Invierte los controles del oponente
                   • Estrategia: Desorienta al rival en momentos críticos
                
                📋 CONTROLES:
                   • Jugador 1: WASD para moverse, C para habilidad
                   • Jugador 2: Flechas para moverse, M para habilidad  
                   • R para reiniciar partida
                """;

        infoArea.setText(info);

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setPreferredSize(new Dimension(0, 300));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Método que abre la ventana del juego
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