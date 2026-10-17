package lab_1re;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class PalindromoAir {

    // Base comun: usar estos mismos valores en todo el programa.
    public static final int CAPACIDAD = 30;
    public static final double PRECIO_BASE = 100;
    public static final double DESCUENTO = 0.20;

    // Base comun: null significa que el asiento esta libre.
    private Ticket asientos[];
    private JButton botonesAsientos[];
    private JTextArea consola;

    // Este constructor ya prepara los datos compartidos.
    public PalindromoAir(JButton botonesAsientos[], JTextArea consola) {
        asientos = new Ticket[CAPACIDAD];
        this.botonesAsientos = botonesAsientos;
        this.consola = consola;
    }

    // Los return actuales son temporales. Cada persona debe completar sus metodos.
    // Los seis metodos recursivos del PDF deben llevar caso base y paso recursivo, sin ciclos.

    // INICIO PERSONA 1
    // Buscar el primer asiento libre con recursividad. Si no hay, devolver -1.
    public int firstAvailable(int index) {
        if(index<0|| index >= CAPACIDAD){   
        return -1;
        }
        
        if(asientos[index ]== null){
            return index;
        }
        return firstAvailable(index +1);
        
        
    }

    // Persona 1: buscar con recursividad y devolver el indice o -1.
    // Ignorar asientos vacios y diferencias de mayusculas, tildes y espacios.
    public int searchPassenger(String name, int index) {
        return -1;
    }

    // Persona 1: revisar el nombre con recursividad. Puede agregar un auxiliar.
    // Ignorar mayusculas, tildes, espacios y signos. Vacio debe dar false.
    public boolean isPalindromo(String name) {
        return false;
    }

    // Persona 1: validar nombre, revisar espacio y evitar pasajeros repetidos.
    // Aplicar descuento si corresponde, crear Ticket y ocupar el primer libre.
    // Actualizar el asiento y mostrar el resultado. Devolver true o false.
    public boolean sellTicket(String name) {
        return false;
    }

    // FIN PERSONA 1

    // INICIO PERSONA 2
    // Mostrar los datos de cada pasajero con recursividad.
    // Si no hay pasajeros, mostrar "Sin pasajeros registrados".
    public void printPassengers(int index) {

    }

    // Persona 2: sumar con recursividad lo pagado por los tickets vigentes.
    public double income(int index) {
        return 0;
    }

    // Persona 2: liberar los asientos con recursividad y actualizar cada boton.
    public void reset(int index) {

    }

    // Persona 2: buscar al pasajero, guardar su reembolso y liberar el asiento.
    // Actualizar el boton, mostrar el resultado y devolver true o false.
    public boolean cancelTicket(String name) {
        return false;
    }

    // Persona 2: pedir confirmacion. Si acepta, calcular ingresos antes de reset.
    // Mostrar el total y reiniciar los asientos. Si no acepta, no cambiar nada.
    public void dispatch() {

    }

    // FIN PERSONA 2

    // Base comun: este metodo ya agrega mensajes sin borrar los anteriores.
    public void mostrarMensaje(String mensaje) {
        consola.append(mensaje + "\n");
    }

    // Persona 3: actualizar solo el boton de este indice segun su ticket.
    // Libre: verde. Ocupado: rojo. Palindromo: dorado.
    // Si esta ocupado, mostrar codigo, nombre y monto pagado.
    public void actualizarAsiento(int index) {

    }

    // Persona 3: convertir el indice al codigo del asiento: 0 es A1 y 29 es E6.
    public String codigoAsiento(int index) {
        return "";
    }

    // Base comun: ya permite consultar los datos de un asiento.
    public Ticket getTicket(int index) {
        return asientos[index];
    }
}
