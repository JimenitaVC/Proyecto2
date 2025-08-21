package logica;

import java.util.Calendar;

public abstract class Persona {
    private String cedula;
    private String nombre;
    private Calendar fechNac;
    private String direccion;
    private String telefono;
    private String email;
    
    public Persona() {
        this.fechNac = Calendar.getInstance();
    }
    
    public Persona(String cedula, String nombre, Calendar fechNac, String direccion, String telefono, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechNac = fechNac;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Calendar getFechNac() {
        return fechNac;
    }

    public void setFechNac(Calendar fechNac) {
        this.fechNac = fechNac;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Persona{" + "cedula=" + cedula + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + '}';
    }
}
