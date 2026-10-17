package lab_1re;
//ANGILI RELINA PALACIOS GOMEZ 62611135
//JOSUE PONCE MARADIAGA 22611140
//JORGE LOPEZ RIVERA 22551004
public class Ticket {

    private String nombre;
    private double montoOriginal;
    private double montoPagado;
    private boolean palindromo;

    public Ticket(String nombre, double montoOriginal, double montoPagado, boolean palindromo) {
       this.nombre = nombre;
        this.montoOriginal = montoOriginal;
        this.montoPagado = montoPagado;
        this.palindromo = palindromo;
         
    }

   
    public String getNombre() {
        return nombre;
    }

    public double getMontoOriginal() {
        return montoOriginal;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public boolean isPalindromo() {
        return palindromo;
    }
}
