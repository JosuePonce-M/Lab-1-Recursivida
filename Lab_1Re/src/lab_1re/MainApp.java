package lab_1re;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities ;


public class MainApp extends JFrame {

    // botones
    private JButton botonesAsientos[] = new JButton[PalindromoAir.CAPACIDAD];
    private JTextArea consola = new JTextArea();
    private     PalindromoAir aerolinea;

       public MainApp() {
        aerolinea = new PalindromoAir(botonesAsientos, consola);
    }

    // Persona 3: crear la ventana y mostrarla cuando la interfaz este lista.
    public static void main(String[] args) {

    }
}
