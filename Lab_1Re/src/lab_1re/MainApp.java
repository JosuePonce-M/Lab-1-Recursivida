
package lab_1re;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities ;
import java.awt.GridLayout;
import javax.swing.BorderFactory;


public class MainApp extends JFrame {

    // botones
   private JButton botonesAsientos[] = new JButton[PalindromoAir.CAPACIDAD];
    private JTextArea consola = new JTextArea();
    private PalindromoAir aerolinea ;
    // cantodad columnas del avion 
     private static final int COLUMNAS = 6;
       public MainApp() {
             setTitle("PalindromoAir - Sistema de venta de boletos | Vuelo PA-030");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()) ;
      // Las cuatro zonas de la ventana
        add(crearPanelNorte(), BorderLayout.NORTH);
        add(crearPanelAsientos(), BorderLayout.CENTER);
        add(crearPanelControles(), BorderLayout.EAST);
        add(crearPanelConsola(), BorderLayout.SOUTH);

        
        aerolinea = new PalindromoAir(botonesAsientos, consola ) ;
    }
   // Zona de arriba
    private JPanel crearPanelNorte() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW); 
        return panel;
    }
     // Zona del centro
  private JPanel crearPanelAsientos() {
        JPanel panel = new JPanel(new GridLayout(0, COLUMNAS, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        crearBotonAsiento(0, panel);
        return panel;
    }
      private void crearBotonAsiento(int index, JPanel panel) {
        if (index >= botonesAsientos.length) { // caso base: ya se crearon los 30
            return;
        }
        char fila = (char) ('A' + index / COLUMNAS);   // A, B, C, D, E
        int columna = index % COLUMNAS + 1;            // 1 a 6

        JButton boton = new JButton(fila + "" + columna);
        boton.setOpaque(true);
        boton.setBackground(new Color(200, 230, 200)); // verde: disponible
        boton.setFocusPainted(false);

        botonesAsientos[index] = boton;
        panel.add(boton);

        crearBotonAsiento(index + 1, panel);       
    }

     // Zona de la derecha
    private JPanel crearPanelControles() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN); // color temporal
        panel.setPreferredSize(new Dimension(300, 0));
        return panel;
    }
 // Zona de abajo
    private JPanel crearPanelConsola() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.PINK); // color temporal
        panel.setPreferredSize(new Dimension(0, 150));
        return panel;
    }
    
    public static void main(String[] args) {
   
    }
}
