
package lab_1re;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

        // Se crea despues de los botones para que ya existan
        aerolinea = new PalindromoAir(botonesAsientos, consola);

        // Cada boton avisa a esta misma clase cuando lo presionan
        btnSell.addActionListener(this);
        btnCancel.addActionListener(this);
        btnSearch.addActionListener(this);
        btnPrint.addActionListener(this);
        btnIncome.addActionListener(this);
        btnDispatch.addActionListener(this);
        campoNombre.addActionListener(this); // Enter en el campo
    }

    // Zona de arriba (temporal, luego van los contadores)
    private JPanel crearPanelNorte() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.YELLOW);
        return panel;
    }

    // Zona del centro: 5 filas y 6 columnas de asientos
    private JPanel crearPanelAsientos() {
        JPanel panel = new JPanel(new GridLayout(5, PalindromoAir.COLUMNAS, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        crearBotonAsiento(0, panel);
        return panel;
    }

    // Recursivo: crea un boton y se llama con el siguiente indice
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

    // Convierte el indice en codigo: 0 es A1, 29 es E6
    private String codigoDelAsiento(int index) {
        String letras = "ABCDE";
        int fila = index / PalindromoAir.COLUMNAS;
        int columna = index % PalindromoAir.COLUMNAS + 1;
        return "" + letras.charAt(fila) + columna;
    }

    // Zona de la derecha: campo de nombre y botones
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

    // Se ejecuta cada vez que se presiona un boton o Enter en el campo
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();

        // Estos tres no necesitan nombre
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

        // Los demas si necesitan nombre: validar que no este vacio
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