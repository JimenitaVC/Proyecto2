package logica;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class PagosMensuales {

    private int idPago;
    private Calendar fechaCreacion;
    private String mes;
    private Calendar fechaPago;
    private String estudiante;
    private double totalBeneficio;
    private double deduccionSeguro;
    private double deduccionRenta;
    private double pagoTotal;

    public PagosMensuales() {
        this.fechaCreacion = Calendar.getInstance();
        this.fechaPago = Calendar.getInstance();
    }

    public PagosMensuales(int idPago, Calendar fechaCreacion, String mes, Calendar fechaPago,
            String estudiante, double totalBeneficio) {
        this.idPago = idPago;
        this.fechaCreacion = fechaCreacion;
        this.mes = mes;
        this.fechaPago = fechaPago;
        this.estudiante = estudiante;
        this.totalBeneficio = totalBeneficio;
        calcularDeducciones();
    }

    public PagosMensuales(String mes, Calendar fechaPago, String estudiante, double totalBeneficio) {
        this.fechaCreacion = Calendar.getInstance();
        this.mes = mes;
        this.fechaPago = fechaPago;
        this.estudiante = estudiante;
        this.totalBeneficio = totalBeneficio;
        calcularDeducciones();
    }

    public void calcularDeducciones() {
        this.deduccionSeguro = this.totalBeneficio * 0.10;
        this.deduccionRenta = this.totalBeneficio * 0.05;
        this.pagoTotal = this.totalBeneficio - (this.deduccionSeguro + this.deduccionRenta);
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Calendar getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Calendar fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public double getTotalBeneficio() {
        return totalBeneficio;
    }

    public void setTotalBeneficio(double totalBeneficio) {
        this.totalBeneficio = totalBeneficio;
        calcularDeducciones();
    }

    public double getDeduccionSeguro() {
        return deduccionSeguro;
    }

    public void setDeduccionSeguro(double deduccionSeguro) {
        this.deduccionSeguro = deduccionSeguro;
    }

    public double getDeduccionRenta() {
        return deduccionRenta;
    }

    public void setDeduccionRenta(double deduccionRenta) {
        this.deduccionRenta = deduccionRenta;
    }

    public double getPagoTotal() {
        return pagoTotal;
    }

    public void setPagoTotal(double pagoTotal) {
        this.pagoTotal = pagoTotal;
    }

    @Deprecated
    public double getPagoNeto() {
        return getPagoTotal();
    }

    public String getFechaCreacionFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaCreacion.getTime());
    }

    public String getFechaPagoFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaPago.getTime());
    }

    @Override
    public String toString() {
        return "PagosMensuales{"
                + "idPago=" + idPago
                + ", fechaCreacion=" + getFechaCreacionFormateada()
                + ", mes=" + mes
                + ", fechaPago=" + getFechaPagoFormateada()
                + ", estudiante=" + estudiante
                + ", totalBeneficio=" + totalBeneficio
                + ", deduccionSeguro=" + deduccionSeguro
                + ", deduccionRenta=" + deduccionRenta
                + ", pagoNeto=" + pagoTotal
                + '}';
    }
}
