package logica;

import java.util.Calendar;
/**
 * Clase abstracta que representa una persona dentro del sistema.
 * Contiene información básica como cédula, nombre, fecha de nacimiento,
 * dirección, teléfono y correo electrónico.
 * 
 * Esta clase puede ser extendida por otras como {@code Estudiante} o {@code Profesor}.
 * 
 * @author Jimena
 * @author Monse
 * @author Yerson
 */

public abstract class Persona {
    private String cedula;
    private String nombre;
    private Calendar fechNac;
    private String direccion;
    private String telefono;
    private String email;
    
    /**
     * Constructor vacío inicialización.
     */
    
    public Persona() {
        this.fechNac = Calendar.getInstance();
    }
     /**
     * Constructor completo para inicializar todos los atributos.
     *
     * @param cedula Cédula de identidad.
     * @param nombre Nombre completo.
     * @param fechNac Fecha de nacimiento.
     * @param direccion Dirección física.
     * @param telefono Número de teléfono.
     * @param email Correo electrónico.
     */

    public Persona(String cedula, String nombre, Calendar fechNac, String direccion, String telefono, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechNac = fechNac;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
     /** @return Cédula de la persona. */

    public String getCedula() {
        return cedula;
    }
    /** @param cedula Nueva cédula. */

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    /** @return Nombre completo. */

    public String getNombre() {
        return nombre;
    }
    /** @param nombre Nuevo nombre. */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /** @return Fecha de nacimiento. */

    public Calendar getFechNac() {
        return fechNac;
    }
     /** @param fechNac Nueva fecha de nacimiento. */

    public void setFechNac(Calendar fechNac) {
        this.fechNac = fechNac;
    }
     /** @return Dirección física. */

    public String getDireccion() {
        return direccion;
    }
     /** @param direccion Nueva dirección. */

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
     /** @return Número de teléfono. */

    public String getTelefono() {
        return telefono;
    }
     /** @param telefono Nuevo número de teléfono. */

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
      /** @return Correo electrónico. */

    public String getEmail() {
        return email;
    }
     /** @param email Nuevo correo electrónico. */

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Devuelve una representación textual de la persona.
     *
     * @return Cadena con los datos principales.
     */

    @Override
    public String toString() {
        return "Persona{" + "cedula=" + cedula + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + '}';
    }
}
