package logica;

import java.util.Calendar;
import java.text.SimpleDateFormat;
/**
 * Representa el registro de un pago mensual realizado a un estudiante.
 * Incluye información sobre fechas, deducciones aplicadas y el monto final a pagar.
 * 
 * Esta clase permite calcular automáticamente las deducciones por seguro y renta
 * a partir del beneficio total asignado.
 * 
 * @author Jimena
 * @author Monse
 * @author Yerson
 */

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
      /**
     * Constructor vacío inicialización
     */
    
    public PagosMensuales() {
        this.fechaCreacion = Calendar.getInstance();
        this.fechaPago = Calendar.getInstance();
    }
     /**
     * Constructor completo con todos los atributos.
     *
     * @param idPago Identificador del pago.
     * @param fechaCreacion Fecha de creación del registro.
     * @param mes Mes correspondiente al pago.
     * @param fechaPago Fecha en que se realizó el pago.
     * @param estudiante Identificador del estudiante.
     * @param totalBeneficio Monto total del beneficio.
     */

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
     /**
     * Constructor alternativo sin ID ni fecha de creación explícita.
     *
     * @param mes Mes correspondiente al pago.
     * @param fechaPago Fecha en que se realizó el pago.
     * @param estudiante Identificador del estudiante.
     * @param totalBeneficio Monto total del beneficio.
     */

    public PagosMensuales(String mes, Calendar fechaPago, String estudiante, double totalBeneficio) {
        this.fechaCreacion = Calendar.getInstance();
        this.mes = mes;
        this.fechaPago = fechaPago;
        this.estudiante = estudiante;
        this.totalBeneficio = totalBeneficio;
        calcularDeducciones();
    }
     /**
     * Calcula las deducciones por seguro y renta, y actualiza el monto final a pagar.
     */

    public void calcularDeducciones() {
        this.deduccionSeguro = this.totalBeneficio * 0.10;
        this.deduccionRenta = this.totalBeneficio * 0.05;
        this.pagoTotal = this.totalBeneficio - (this.deduccionSeguro + this.deduccionRenta);
    }
     /** @return Identificador del pago. */

    public int getIdPago() {
        return idPago;
    }
    /** @param idPago Nuevo identificador del pago. */

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }
     /** @return Fecha de creación del registro. */

    public Calendar getFechaCreacion() {
        return fechaCreacion;
    }
     /** @param fechaCreacion Nueva fecha de creación. */

    public void setFechaCreacion(Calendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
     /** @return Mes del pago. */

    public String getMes() {
        return mes;
    }
        /** @param mes Nuevo mes del pago. */

    public void setMes(String mes) {
        this.mes = mes;
    }
        /** @return Fecha en que se realizó el pago. */

    public Calendar getFechaPago() {
        return fechaPago;
    }
        /** @param fechaPago Nueva fecha de pago. */

    public void setFechaPago(Calendar fechaPago) {
        this.fechaPago = fechaPago;
    }
     /** @return Identificador del estudiante. */

    public String getEstudiante() {
        return estudiante;
    }
    /** @param estudiante Nuevo identificador del estudiante. */

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }
      /** @return Monto total del beneficio. */

    public double getTotalBeneficio() {
        return totalBeneficio;
    }
    /**
     * Establece el monto total del beneficio y recalcula las deducciones.
     *
     * @param totalBeneficio Nuevo monto del beneficio.
     */

    public void setTotalBeneficio(double totalBeneficio) {
        this.totalBeneficio = totalBeneficio;
        calcularDeducciones();
    }
        /** @return Deducción por seguro. */

    public double getDeduccionSeguro() {
        return deduccionSeguro;
    }
     /** @param deduccionSeguro Nuevo valor de deducción por seguro. */

    public void setDeduccionSeguro(double deduccionSeguro) {
        this.deduccionSeguro = deduccionSeguro;
    }
     /** @return Deducción por renta. */

    public double getDeduccionRenta() {
        return deduccionRenta;
    }
      /** @param deduccionRenta Nuevo valor de deducción por renta. */

    public void setDeduccionRenta(double deduccionRenta) {
        this.deduccionRenta = deduccionRenta;
    }
    /** @return Monto final a pagar. */

    public double getPagoTotal() {
        return pagoTotal;
    }
     /** @param pagoTotal Nuevo monto final a pagar. */

    public void setPagoTotal(double pagoTotal) {
        this.pagoTotal = pagoTotal;
    }
     /**
     * Método obsoleto. Usa {@link #getPagoTotal()} en su lugar.
     *
     * @return Monto final a pagar.
     */

    @Deprecated
    public double getPagoNeto() {
        return getPagoTotal();
    }
      /**
     * Devuelve la fecha de creación formateada como dd/MM/yyyy.
     *
     * @return Fecha de creación en formato legible.
     */

    public String getFechaCreacionFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaCreacion.getTime());
    }
     /**
     * Devuelve la fecha de pago formateada como dd/MM/yyyy.
     *
     * @return Fecha de pago en formato legible.
     */

    public String getFechaPagoFormateada() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaPago.getTime());
    }
      /**
     * Devuelve una representación textual del objeto PagosMensuales.
     *
     * @return Cadena con los valores principales del pago.
     */

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
