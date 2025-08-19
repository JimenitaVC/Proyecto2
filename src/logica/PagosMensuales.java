/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
public class PagosMensuales {
    private int idPago;
    private LocalDateTime fechCreacion;
    private Month mes;
    private LocalDate fechaPago;
    private int cedula;
    private BigDecimal totalBeneficios;
    private BigDecimal deducSeguro;
    private BigDecimal deducRenta;
    private BigDecimal pagoNeto;

    public PagosMensuales(int idPago, LocalDateTime fechCreacion, Month mes, LocalDate fechaPago, int cedula, BigDecimal totalBeneficios, BigDecimal deducSeguro, BigDecimal deducRenta, BigDecimal pagoNeto) {
        this.idPago = idPago;
        this.fechCreacion = fechCreacion;
        this.mes = mes;
        this.fechaPago = fechaPago;
        this.cedula = cedula;
        this.totalBeneficios = totalBeneficios;
        this.deducSeguro = deducSeguro;
        this.deducRenta = deducRenta;
        this.pagoNeto = pagoNeto;
    }
        public PagosMensuales() {
        this.idPago = 0;
        this.fechCreacion = null;
        this.mes = null;
        this.fechaPago = null;
        this.cedula = 0;
        this.totalBeneficios = null;
        this.deducSeguro = null;
        this.deducRenta = null;
        this.pagoNeto = null;
        
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public LocalDateTime getFechCreacion() {
        return fechCreacion;
    }

    public void setFechCreacion(LocalDateTime fechCreacion) {
        this.fechCreacion = fechCreacion;
    }

    public Month getMes() {
        return mes;
    }

    public void setMes(Month mes) {
        this.mes = mes;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public BigDecimal getTotalBeneficios() {
        return totalBeneficios;
    }

    public void setTotalBeneficios(BigDecimal totalBeneficios) {
        this.totalBeneficios = totalBeneficios;
    }

    public BigDecimal getDeducSeguro() {
        return deducSeguro;
    }

    public void setDeducSeguro(BigDecimal deducSeguro) {
        this.deducSeguro = deducSeguro;
    }

    public BigDecimal getDeducRenta() {
        return deducRenta;
    }

    public void setDeducRenta(BigDecimal deducRenta) {
        this.deducRenta = deducRenta;
    }

    public BigDecimal getPagoNeto() {
        return pagoNeto;
    }

    public void setPagoNeto(BigDecimal pagoNeto) {
        this.pagoNeto = pagoNeto;
    }
}
