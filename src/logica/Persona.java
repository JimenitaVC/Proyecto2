/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.util.Calendar;
public class Persona {
     private int cedula;
    private String nombre;
    private Calendar fechNac;
    private String direccion;
    private int telefono;
    private String email;

    public Persona(int cedula, String nombre, Calendar fechNac, String direccion, int telefono, String email) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.fechNac = fechNac;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    public Persona() {
        this.cedula = 0;
        this.nombre = "";
        this.fechNac = null;
        this.direccion = "";
        this.telefono = 0;
        this.email = "";
        
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
