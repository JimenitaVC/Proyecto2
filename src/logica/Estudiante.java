package logica;

import java.util.Calendar;
/**
 * Representa a un estudiante dentro del sistema académico.
 * Hereda los atributos personales desde la clase {@link Persona} y agrega información específica
 * como el carnet institucional, fechas de ingreso y egreso, y la carrera que cursa.
 * 
 * Esta clase permite gestionar los datos académicos de los estudiantes.
 * 
 * @author Jimena
 * @author Monse
 * @author Yerson
 */

public class Estudiante extends Persona {
    private String carnet;
    private Calendar fechIngreso;
    private Calendar fechEgreso;
    private int carrera; 
     
    /**
     * Constructor vacío inicialización
     */
    
    public Estudiante() {
        super();
        this.fechIngreso = Calendar.getInstance();
        this.fechEgreso = Calendar.getInstance();
    }
     /**
     * Constructor completo que inicializa todos los atributos del estudiante.
     *
     * @param carnet Carnet institucional del estudiante.
     * @param fechIngreso Fecha de ingreso a la institución.
     * @param fechEgreso Fecha de egreso de la institución.
     * @param carrera Identificador de la carrera que cursa.
     * @param cedula Cédula del estudiante.
     * @param nombre Nombre completo del estudiante.
     * @param fechNac Fecha de nacimiento.
     * @param direccion Dirección física del estudiante.
     * @param telefono Número de teléfono.
     * @param email Correo electrónico.
     */
    
    public Estudiante(String carnet, Calendar fechIngreso, Calendar fechEgreso, int carrera, 
                     String cedula, String nombre, Calendar fechNac, String direccion, 
                     String telefono, String email) {
        super(cedula, nombre, fechNac, direccion, telefono, email);
        this.carnet = carnet;
        this.fechIngreso = fechIngreso;
        this.fechEgreso = fechEgreso;
        this.carrera = carrera;
    }
 /**
     * Obtiene el carnet del estudiante.
     *
     * @return carnet institucional.
     */

    public String getCarnet() {
        return carnet;
    }
    /**
     * Establece el carnet del estudiante.
     *
     * @param carnet Nuevo carnet.
     */

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
     /**
     * Obtiene la fecha de ingreso del estudiante.
     *
     * @return fecha de ingreso.
     */

    public Calendar getFechIngreso() {
        return fechIngreso;
    }
    /**
     * Establece la fecha de ingreso del estudiante.
     *
     * @param fechIngreso Nueva fecha de ingreso.
     */

    public void setFechIngreso(Calendar fechIngreso) {
        this.fechIngreso = fechIngreso;
    }
    /**
     * Obtiene la fecha de egreso del estudiante.
     *
     * @return fecha de egreso.
     */

    public Calendar getFechEgreso() {
        return fechEgreso;
    }
     /**
     * Establece la fecha de egreso del estudiante.
     *
     * @param fechEgreso Nueva fecha de egreso.
     */

    public void setFechEgreso(Calendar fechEgreso) {
        this.fechEgreso = fechEgreso;
    }
    /**
     * Obtiene el identificador de la carrera que cursa el estudiante.
     *
     * @return id de la carrera.
     */

    public int getCarrera() {
        return carrera;
    }
    /**
     * Establece el identificador de la carrera que cursa el estudiante.
     *
     * @param carrera Nuevo id de carrera.
     */

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }
     /**
     * Devuelve una representación textual del objeto Estudiante.
     *
     * @return cadena con carnet, cédula, nombre y carrera.
     */

    @Override
    public String toString() {
        return "Estudiante{" + "carnet=" + carnet + ", cedula=" + getCedula() + 
               ", nombre=" + getNombre() + ", carrera=" + carrera + '}';
    }
}