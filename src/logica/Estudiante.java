/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.time.LocalDate;
public class Estudiante {
      private String carnet;
    private LocalDate fechaIngreso;
    private LocalDate fechaEgreso;
    private int idCarrera;

    public Estudiante(String carnet, LocalDate fechaIngreso, LocalDate fechaEgreso, int idCarrera) {
        this.carnet = carnet;
        this.fechaIngreso = fechaIngreso;
        this.fechaEgreso = fechaEgreso;
        this.idCarrera = idCarrera;
    }
        public Estudiante() {
        this.carnet = "";
        this.fechaIngreso = null;
        this.fechaEgreso = null;
        this.idCarrera = 0;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }
}
