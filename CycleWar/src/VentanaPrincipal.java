import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaPrincipal extends JFrame {

    // Colores por defecto
    private Color colorJugador1 = Color.RED;
    private Color colorJugador2 = Color.BLUE;

    public VentanaPrincipal() {
        setTitle("Menú Principal");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar ventana
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

        // Panel central para elegir colores
        JPanel panelCentral = new JPanel(new GridLayout(2, 1, 10, 10));

        // --- Selección Jugador 1 ---
        JPanel panelJ1 = new JPanel();
        panelJ1.setBorder(BorderFactory.createTitledBorder("Color Jugador 1"));

        JButton rojo1 = new JButton("Rojo");
        JButton azul1 = new JButton("Azul");
        JButton amarillo1 = new JButton("Amarillo");
        JButton verde1 = new JButton("Verde");

        rojo1.addActionListener(e -> colorJugador1 = Color.RED);
        azul1.addActionListener(e -> colorJugador1 = Color.BLUE);
        amarillo1.addActionListener(e -> colorJugador1 = Color.YELLOW);
        verde1.addActionListener(e -> colorJugador1 = Color.GREEN);

        panelJ1.add(rojo1);
        panelJ1.add(azul1);
        panelJ1.add(amarillo1);
        panelJ1.add(verde1);

        // --- Selección Jugador 2 ---
        JPanel panelJ2 = new JPanel();
        panelJ2.setBorder(BorderFactory.createTitledBorder("Color Jugador 2"));

        JButton rojo2 = new JButton("Rojo");
        JButton azul2 = new JButton("Azul");
        JButton amarillo2 = new JButton("Amarillo");
        JButton verde2 = new JButton("Verde");

        rojo2.addActionListener(e -> colorJugador2 = Color.RED);
        azul2.addActionListener(e -> colorJugador2 = Color.BLUE);
        amarillo2.addActionListener(e -> colorJugador2 = Color.YELLOW);
        verde2.addActionListener(e -> colorJugador2 = Color.GREEN);

        panelJ2.add(rojo2);
        panelJ2.add(azul2);
        panelJ2.add(amarillo2);
        panelJ2.add(verde2);

        panelCentral.add(panelJ1);
        panelCentral.add(panelJ2);

        add(panelCentral, BorderLayout.CENTER);

        setVisible(true);
    }

    // Método que abre la ventana del juego
    private void abrirJuego() {
        JFrame frameJuego = new JFrame("Juego de Motos Tron - 2 Jugadores");
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
