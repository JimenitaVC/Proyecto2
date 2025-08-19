/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;


public class BeneficiosEstudiantes {
  private int cedula;
    private int idBeneficio;

    public BeneficiosEstudiantes(int cedula, int idBeneficio) {
        this.cedula = cedula;
        this.idBeneficio = idBeneficio;
    }
        public BeneficiosEstudiantes() {
        this.cedula = 0;
        this.idBeneficio = 0;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public int getIdBeneficio() {
        return idBeneficio;
    }

    public void setIdBeneficio(int idBeneficio) {
        this.idBeneficio = idBeneficio;
    }  
}
