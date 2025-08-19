/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.math.BigDecimal;
public class Beneficios {
     private int idBeneficio;
    private String nomBeneficio;
    private String descripcion;
    private BigDecimal montoBeneficio;

    public Beneficios(int idBeneficio, String nomBeneficio, String descripcion, BigDecimal montoBeneficio) {
        this.idBeneficio = idBeneficio;
        this.nomBeneficio = nomBeneficio;
        this.descripcion = descripcion;
        this.montoBeneficio = montoBeneficio;
    }
        public Beneficios() {
        this.idBeneficio = 0;
        this.nomBeneficio = "";
        this.descripcion = "";
        this.montoBeneficio = null;
    }

    public int getIdBeneficio() {
        return idBeneficio;
    }

    public void setIdBeneficio(int idBeneficio) {
        this.idBeneficio = idBeneficio;
    }

    public String getNomBeneficio() {
        return nomBeneficio;
    }

    public void setNomBeneficio(String nomBeneficio) {
        this.nomBeneficio = nomBeneficio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMontoBeneficio() {
        return montoBeneficio;
    }

    public void setMontoBeneficio(BigDecimal montoBeneficio) {
        this.montoBeneficio = montoBeneficio;
    }
}
