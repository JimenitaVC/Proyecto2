package logica;
/**
 * Representa la relación entre un estudiante y un beneficio asignado.
 * Contiene la cédula del estudiante y el identificador del beneficio recibido.
 * 
 * Esta clase es útil para registrar qué beneficios han sido asignados a cada estudiante.
 * 
 * @author Jimena
 * @author Monse
 * @author Yerson
 */


public class BeneficiosEstudiantes {
    private String cedula; 
    private int idBeneficio; 
        /**
     * Constructor vacío inicialización
     */
    
    public BeneficiosEstudiantes() {
    }
     /**
     * Constructor con todos los atributos.
     *
     * @param cedula Cédula del estudiante.
     * @param idBeneficio Identificador del beneficio asignado.
     */
    
    public BeneficiosEstudiantes(String cedula, int idBeneficio) {
        this.cedula = cedula;
        this.idBeneficio = idBeneficio;
    }
     /**
     * Obtiene la cédula del estudiante.
     *
     * @return cédula del estudiante.
     */
    
    public String getCedula() {
        return cedula;
    }
     /**
     * Establece la cédula del estudiante.
     *
     * @param cedula Nueva cédula.
     */
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
     /**
     * Obtiene el identificador del beneficio asignado.
     *
     * @return id del beneficio.
     */
    
    public int getIdBeneficio() {
        return idBeneficio;
    }
    /**
     * Establece el identificador del beneficio asignado.
     *
     * @param idBeneficio Nuevo identificador.
     */
    
    public void setIdBeneficio(int idBeneficio) {
        this.idBeneficio = idBeneficio;
    }
     /**
     * Devuelve una representación en texto del objeto BeneficiosEstudiantes.
     *
     * @return cadena con los valores de los atributos.
     */
    
    @Override
    public String toString() {
        return "BeneficiosEstudiantes{" + "cedula=" + cedula + ", idBeneficio=" + idBeneficio + '}';
    }
}