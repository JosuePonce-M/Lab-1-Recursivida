package lab_1re;
import java.text.Normalizer;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;
import java.util.Scanner;

public class PalindromoAir {

    public static final int CAPACIDAD = 30;
    public static final double PRECIO_BASE = 100;
    public static final double DESCUENTO = 0.20;
    public static final int COLUMNAS = 6;

    private Ticket asientos[];
    private JButton botonesAsientos[];
    private JTextArea consola;

    public PalindromoAir(JButton botonesAsientos[], JTextArea consola) {
        asientos = new Ticket[CAPACIDAD];
        this.botonesAsientos = botonesAsientos;
        this.consola = consola;
    }

   
    public PalindromoAir(Ticket asientos[]) {
        this.asientos = asientos;
    }

    // INICIO PERSONA 1

    // Persona 1: buscar el primer asiento libre con recursividad.
    public int firstAvailable(int index) {
        if (index < 0 || index >= CAPACIDAD) { 
        }
        if (asientos[index] == null) {      
            return index;
        }
        return firstAvailable(index + 1);     
    }

    
    // Persona 1: buscar al pasajero con recursividad.
    public int searchPassenger(String name, int index) {
        if (index < 0 || index >= CAPACIDAD) {
            return -1;
        }
        if (asientos[index] != null) {
            String nombreNormalizadoActual = normalizarTexto(asientos[index].getNombre());
            String nombreBuscado = normalizarTexto(name);

            if (nombreNormalizadoActual.equals(nombreBuscado)) {
                return index;
            }
        }
        return searchPassenger(name, index + 1);
    }

    
    // Deja el texto en minusculas, sin tildes, sin espacios ni signos
    private String normalizarTexto(String texto) {
        if (texto == null) return "";
        String sinMayusculas = texto.toLowerCase();
        String sinTildes = Normalizer.normalize(sinMayusculas, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String soloLetrasYNumeros = sinTildes.replaceAll("[^a-z0-9]", "");
        return soloLetrasYNumeros;
    }

    // Persona 1: revisar si el nombre es palindromo.
    public boolean isPalindromo(String name) {
        String texto = normalizarTexto(name);  
        if (texto.equals("")) {               
            return false;
        }
        return compararExtremos(texto, 0, texto.length() - 1);
    }

    
    private boolean compararExtremos(String texto, int inicio, int fin) {
        if (inicio >= fin) {                   // caso base
            return true;
        }
        if (texto.charAt(inicio) != texto.charAt(fin)) { // caso base
            return false;
        }
        return compararExtremos(texto, inicio + 1, fin - 1); // paso recursivo
    }

    // Persona 1: validar los datos y vender el boleto.
    public boolean sellTicket(String name) {
        // 1. 
        if (normalizarTexto(name).equals("")) {
            mostrarMensaje("Ingrese un nombre válido.");
            return false;
        }

        // 2. Debe haber asientos disponibles
        int posicion = firstAvailable(0);
        if (posicion == -1) {
            mostrarMensaje("Avión lleno. No se puede vender el boleto.");
            return false;
        }

        // 3. El pasajero no puede estar ya registrado
        if (searchPassenger(name, 0) != -1) {
            mostrarMensaje("El pasajero ya tiene un asiento asignado.");
            return false;
        }

        // 4. 
        boolean palindromo = isPalindromo(name);

        // 5. Calcular el monto final
        double montoPagado = PRECIO_BASE;
        if (palindromo) {
            montoPagado = PRECIO_BASE * (1 - DESCUENTO);
        }

        // 6. Crear el ticket y asignarlo al primer asiento libre
        Ticket ticket = new Ticket(name, PRECIO_BASE, montoPagado, palindromo);
        asientos[posicion] = ticket;

        // 7. Pintar el asiento 
        actualizarAsiento(posicion);

        // 8. Mensaje de confirmacion
        String monto = String.format("%.2f", montoPagado);
        if (palindromo) {
            long porcentaje = Math.round(DESCUENTO * 100);
            mostrarMensaje("Vendido: " + name + " en " + codigoAsiento(posicion)
                    + ", pagó $" + monto + " (palíndromo, " + porcentaje
                    + "% de descuento).");
        } else {
            mostrarMensaje("Vendido: " + name + " en " + codigoAsiento(posicion)
                    + ", pagó $" + monto + ".");
        }
        return true;
    }

    // FIN PERSONA 1

    // INICIO PERSONA 2

    // Persona 2: mostrar los pasajeros registrados.
    public void printPassengers(int index) {
        printPassengers(index, false);
    }

    private void printPassengers(int index, boolean hayPasajeros) {
        if(index < 0 || index >= asientos.length) {
            if(!hayPasajeros) {
                mostrarMensaje("Sin pasajeros registrados");
            }
            return;
        }

        if(asientos[index] != null) {
            Ticket boleto = asientos[index];
            String palindromo = "No";
            if(boleto.isPalindromo()) {
                palindromo = "Si";
            }
            
            mostrarMensaje(obtenerCodigo(index) + " - " + boleto.getNombre()
                    + " - Pagado: $" + String.format("%.2f", boleto.getMontoPagado())
                    + " - Original: $" + String.format("%.2f", boleto.getMontoOriginal())
                    + " - Palindromo: " + palindromo);
            hayPasajeros = true;
        }
        printPassengers(index + 1, hayPasajeros);
    }

    // Persona 2: sumar los pagos con recursividad.
    public double income(int index) {
        if(index < 0 || index >= asientos.length) {
            return 0;
        }
        if(asientos[index] == null) {
            return income(index + 1);
        }
        double pago = asientos[index].getMontoPagado();
        return pago + income(index + 1);
    }

    // Persona 2: 
    public void reset(int index) {
        if(index < 0 || index >= asientos.length) {
            return;
        }
        asientos[index] = null;
        pintarLibre(index);
        reset(index + 1);
    }

    // Persona 2: cancelar el boleto y mostrar el reembolso.
    public boolean cancelTicket(String name) {
        String nombre = limpiarParaCancelar(name);
        if(nombre.equals("")) {
            mostrarMensaje("Ingrese un nombre valido.");
            return false;
        }

        int posicion = buscarParaCancelar(nombre, 0);
        if(posicion == -1) {
            mostrarMensaje("No se encontro al pasajero. No se pudo cancelar.");
            return false;
        }

        String pasajero = asientos[posicion].getNombre();
        double reembolso = asientos[posicion].getMontoPagado();
        asientos[posicion] = null;
        pintarLibre(posicion);
       
        mostrarMensaje("Cancelado: " + pasajero + ". Reembolso de $"
                + String.format("%.2f", reembolso) + ".");
        return true;
    }

    // Persona 2: confirmar antes de despachar.
    public void dispatch() {
        boolean confirmar = false;
        if(consola == null) {
            System.out.println("Despachar el vuelo? Escriba S para confirmar:");
            Scanner s = new Scanner(System.in);
            String respuesta = s.nextLine();
            respuesta = respuesta.toUpperCase();
            if(respuesta.equals("S")) {
                confirmar = true;
            }
        } else {
            int opcion = JOptionPane.showConfirmDialog(null, "Despachar el vuelo?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if(opcion == JOptionPane.YES_OPTION) {
                confirmar = true;
            }
        }

        if(confirmar) {
            double total = income(0);
            reset(0);
            // CAMBIO: 
            mostrarMensaje("Ingresos totales: $" + String.format("%.2f", total)
                    + ". Vuelo despachado, asientos reiniciados.");
        } else {
            mostrarMensaje("cancelado.");
        }
    }

    // Persona 2: 
    private int buscarParaCancelar(String nombre, int index) {
        if(index >= asientos.length) {
            return -1;
        }
        if(asientos[index] != null) {
            String registrado = limpiarParaCancelar(asientos[index].getNombre());
            if(registrado.equals(nombre)) {
                return index;
            }
        }
        return buscarParaCancelar(nombre, index + 1);
    }

    // Persona 2: 
    private String limpiarParaCancelar(String nombre) {
        if(nombre == null) {
            return "";
        }

        nombre = nombre.toLowerCase();
        String limpio = "";

        for(int i=0; i<nombre.length(); i++) {
            char letra = nombre.charAt(i);

            switch(letra) {
                case 'á':
                    letra = 'a';
                    break;
                case 'é':
                    letra = 'e';
                    break;
                case 'í':
                    letra = 'i';
                    break;
                case 'ó':
                    letra = 'o';
                    break;
                case 'ú':
                case 'ü':
                    letra = 'u';
                    break;
                case 'ñ':
                    letra = 'n';
                    break;
            }

            if(letra >= 'a' && letra <= 'z') {
                limpio = limpio + letra;
            }
        }

        return limpio;
    }

    // Persona 2: obtener el codigo sin usar el metodo de persona 3.
    private String obtenerCodigo(int index) {
        String letras = "ABCDE";
        int fila = index / 6;
        int columna = index % 6 + 1;
        String codigo = "" + letras.charAt(fila) + columna;
        return codigo;
    }

    // Persona 2: actualizar el boton si ya existe la ventana.
   
    private void pintarLibre(int index) {
        if(botonesAsientos != null && index < botonesAsientos.length) {
            actualizarAsiento(index);
        }
    }

    // FIN PERSONA 2

    
    public void mostrarMensaje(String mensaje) {
        if(consola != null) {
            consola.append(mensaje + "\n");
        } else {
            System.out.println(mensaje);
        }
    }

    // Persona 3: mostrar codigo, nombre, monto y color del asiento.
    public void actualizarAsiento(int index) {
        if (botonesAsientos == null || botonesAsientos[index] == null) {
            return;
        }
        JButton boton = botonesAsientos[index];
        Ticket ticket = asientos[index];

        if (ticket == null) {
            boton.setText(codigoAsiento(index));
            boton.setBackground(new Color(200, 230, 200)); // verde
        } else {
           
            boton.setText("<html>" + codigoAsiento(index) + "<br>"
                    + ticket.getNombre() + "<br>$"
                    + String.format("%.2f", ticket.getMontoPagado()) + "</html>");
            if (ticket.isPalindromo()) {
                boton.setBackground(new Color(250, 220, 150)); // dorado
            } else {
                boton.setBackground(new Color(250, 200, 200)); // rojo
            }
        }
    }

    // Persona 3: 
    public String codigoAsiento(int index) {
        String letras = "ABCDE";
        int fila = index / COLUMNAS;
        int columna = index % COLUMNAS + 1;
        return "" + letras.charAt(fila) + columna;
    }

   
    public Ticket getTicket(int index) {
        return asientos[index];
    }
}