
package lab_1re;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout; 
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainApp extends JFrame implements ActionListener {

    // Botones de asientos, consola y la aerolinea
    private JButton botonesAsientos[] = new JButton[PalindromoAir.CAPACIDAD];
    private JTextArea consola = new JTextArea();
    private PalindromoAir aerolinea;

    // Campo de nombre y botones de accion
    private JTextField campoNombre = new JTextField();
    private JButton btnSell = new JButton("Sell Ticket");
    private JButton btnCancel = new JButton("Cancel Ticket");
    private JButton btnSearch = new JButton("Search Passenger");
    private JButton btnPrint = new JButton("Print Passengers");
    private JButton btnIncome = new JButton("View Income");
    private JButton btnDispatch = new JButton("Dispatch");

    public MainApp() {
        setTitle("PalindromoAir - Sistema de venta de boletos | Vuelo PA-030");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(crearPanelNorte(), BorderLayout.NORTH);
        add(crearPanelAsientos(), BorderLayout.CENTER);
        add(crearPanelControles(), BorderLayout.EAST);
        add(crearPanelConsola(), BorderLayout.SOUTH);

       
        aerolinea = new PalindromoAir(botonesAsientos, consola);

      
        btnSell.addActionListener(this);
        btnCancel.addActionListener(this);
        btnSearch.addActionListener(this);
        btnPrint.addActionListener(this);
        btnIncome.addActionListener(this);
        btnDispatch.addActionListener(this);
        campoNombre.addActionListener(this); 
    }

    // Zona de arriba 
    private JPanel crearPanelNorte() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        return panel;
    }

    
    private JPanel crearPanelAsientos() {
        JPanel cuadricula = new JPanel(new GridLayout(5, PalindromoAir.COLUMNAS, 8, 8));
        cuadricula.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        crearBotonAsiento(0, cuadricula);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(cuadricula, BorderLayout.CENTER);
        panel.add(crearLeyenda(), BorderLayout.SOUTH);
        return panel;
    }

    // Recursivo: 
    private void crearBotonAsiento(int index, JPanel panel) {
        if (index >= botonesAsientos.length) { // caso base
            return;
        }
        JButton boton = new JButton();
        boton.setOpaque(true);
        boton.setBackground(new Color(200, 230, 200)); // verde: disponible
        botonesAsientos[index] = boton;
        boton.setText(codigoDelAsiento(index));
        panel.add(boton);

        crearBotonAsiento(index + 1, panel); // paso recursivo
    }

    
    private String codigoDelAsiento(int index) {
        String letras = "ABCDE";
        int fila = index / PalindromoAir.COLUMNAS;
        int columna = index % PalindromoAir.COLUMNAS + 1;
        return "" + letras.charAt(fila) + columna;
    }

    
    private JPanel crearLeyenda() {
        JPanel leyenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        leyenda.add(crearItemLeyenda(new Color(200, 230, 200), "Disponible"));
        leyenda.add(crearItemLeyenda(new Color(250, 200, 200), "Ocupado"));
        leyenda.add(crearItemLeyenda(new Color(250, 220, 150), "Palíndromo (-20%)"));
        return leyenda;
    }

   
    private JPanel crearItemLeyenda(Color color, String texto) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel cuadro = new JLabel("    ");
        cuadro.setOpaque(true);
        cuadro.setBackground(color);
        cuadro.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        item.add(cuadro);
        item.add(new JLabel(texto));
        return item;
    }

    
    private JPanel crearPanelControles() {
        JPanel interno = new JPanel(new GridLayout(8, 1, 8, 8));
        interno.add(new JLabel("Nombre del pasajero"));
        interno.add(campoNombre);
        interno.add(btnSell);
        interno.add(btnCancel);
        interno.add(btnSearch);
        interno.add(btnPrint);
        interno.add(btnIncome);
        interno.add(btnDispatch);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setPreferredSize(new Dimension(300, 0));
        panel.add(interno, BorderLayout.NORTH);
        return panel;
    }

    // Zona de abajo: consola de mensajes
    private JPanel crearPanelConsola() {
        consola.setEditable(false);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 150));
        panel.add(new JScrollPane(consola), BorderLayout.CENTER);
        return panel;
    }

    
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

      
        if (origen == btnPrint) {
            aerolinea.printPassengers(0);
            return;
        }
        if (origen == btnIncome) {
            aerolinea.mostrarMensaje("Ingresos actuales: $"
                    + String.format("%.2f", aerolinea.income(0)));
            return;
        }
        if (origen == btnDispatch) {
            aerolinea.dispatch();
            return;
        }

       
        String nombre = campoNombre.getText().trim();
        if (nombre.equals("")) {
            aerolinea.mostrarMensaje("Ingrese un nombre valido.");
            JOptionPane.showMessageDialog(this, "Ingrese un nombre valido.");
            return;
        }

        if (origen == btnSell || origen == campoNombre) {
            if (aerolinea.sellTicket(nombre)) {
                campoNombre.setText("");
            }
        } else if (origen == btnCancel) {
            if (aerolinea.cancelTicket(nombre)) {
                campoNombre.setText("");
            }
        } else if (origen == btnSearch) {
            int posicion = aerolinea.searchPassenger(nombre, 0);
            if (posicion == -1) {
                aerolinea.mostrarMensaje("No se encontro al pasajero.");
            } else {
                aerolinea.mostrarMensaje(nombre + " esta en el asiento "
                        + aerolinea.codigoAsiento(posicion) + ".");
            }
        }
    }

    public static void main(String[] args) {
        MainApp ventana = new MainApp();
        ventana.setVisible(true);
    }
}