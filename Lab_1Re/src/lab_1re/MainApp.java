package lab_1re;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

// Persona 3: completar la ventana y los eventos de los botones.
public class MainApp extends JFrame {

    // Crear los 30 botones y colocarlos en 5 filas y 6 columnas.
    private JButton botonesAsientos[] = new JButton[PalindromoAir.CAPACIDAD];
    private JTextArea consola = new JTextArea();
    private     PalindromoAir aerolinea;

    // Agregar campo de nombre, consola visible y botones de las seis acciones.
    // Conectar todos los eventos con esta misma aerolinea.
    // Mostrar los avisos y validar que el campo de nombre no este vacio.
    public MainApp() {
        aerolinea = new PalindromoAir(botonesAsientos, consola);
    }

    // Persona 3: crear la ventana y mostrarla cuando la interfaz este lista.
    public static void main(String[] args) {

    }
}
