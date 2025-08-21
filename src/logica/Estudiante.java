package logica;

import java.util.Calendar;

public class Estudiante extends Persona {
    private String carnet;
    private Calendar fechIngreso;
    private Calendar fechEgreso;
    private int carrera; 
    
    public Estudiante() {
        super();
        this.fechIngreso = Calendar.getInstance();
        this.fechEgreso = Calendar.getInstance();
    }
    
    public Estudiante(String carnet, Calendar fechIngreso, Calendar fechEgreso, int carrera, 
                     String cedula, String nombre, Calendar fechNac, String direccion, 
                     String telefono, String email) {
        super(cedula, nombre, fechNac, direccion, telefono, email);
        this.carnet = carnet;
        this.fechIngreso = fechIngreso;
        this.fechEgreso = fechEgreso;
        this.carrera = carrera;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public Calendar getFechIngreso() {
        return fechIngreso;
    }

    public void setFechIngreso(Calendar fechIngreso) {
        this.fechIngreso = fechIngreso;
    }

    public Calendar getFechEgreso() {
        return fechEgreso;
    }

    public void setFechEgreso(Calendar fechEgreso) {
        this.fechEgreso = fechEgreso;
    }

    public int getCarrera() {
        return carrera;
    }

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "carnet=" + carnet + ", cedula=" + getCedula() + 
               ", nombre=" + getNombre() + ", carrera=" + carrera + '}';
    }
}