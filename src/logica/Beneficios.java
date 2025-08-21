package logica;
/**
 * Representa un beneficio que puede ser asignado a un usuario.
 * Contiene información como el identificador, nombre, descripción y monto asociado.
 * 
 * Esta clase es útil para modelar los beneficios dentro del sistema de asignación.
 * 
 * @author Jimena
 * @author Monse
 * @author Yerson
 */


public class Beneficios {
    private int idBeneficio;
    private String nomBeneficio;
    private String descripcion;
    private double montoBeneficio;
    
     /**
     * Constructor vacío inicialización.
     */
    public Beneficios() {
    }
    /**
     * Constructor con todos los atributos del beneficio.
     *
     * @param idBeneficio Identificador único del beneficio.
     * @param nomBeneficio Nombre del beneficio.
     * @param descripcion Descripción del beneficio.
     * @param montoBeneficio Monto económico del beneficio.
     */
    
    public Beneficios(int idBeneficio, String nomBeneficio, String descripcion, double montoBeneficio) {
        this.idBeneficio = idBeneficio;
        this.nomBeneficio = nomBeneficio;
        this.descripcion = descripcion;
        this.montoBeneficio = montoBeneficio;
    }

    /**
     * Obtiene el identificador del beneficio.
     *
     * @return id del beneficio.
     */
    public int getIdBeneficio() {
        return idBeneficio;
    }

    /**
     * Establece el identificador del beneficio.
     *
     * @param idBeneficio Nuevo identificador.
     */
    public void setIdBeneficio(int idBeneficio) {
        this.idBeneficio = idBeneficio;
    }
    /**
     * Obtiene el nombre del beneficio.
     *
     * @return nombre del beneficio.
     */
    
    public String getNomBeneficio() {
        return nomBeneficio;
    }
    /**
     * Establece el nombre del beneficio.
     *
     * @param nomBeneficio Nuevo nombre.
     */
    
    public void setNomBeneficio(String nomBeneficio) {
        this.nomBeneficio = nomBeneficio;
    }
    /**
     * Obtiene la descripción del beneficio.
     *
     * @return descripción del beneficio.
     */
    
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Establece la descripción del beneficio.
     *
     * @param descripcion Nueva descripción.
     */
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
     /**
     * Obtiene el monto económico del beneficio.
     *
     * @return monto del beneficio.
     */
    
    public double getMontoBeneficio() {
        return montoBeneficio;
    }
     /**
     * Establece el monto económico del beneficio.
     *
     * @param montoBeneficio Nuevo monto.
     */
    
    public void setMontoBeneficio(double montoBeneficio) {
        this.montoBeneficio = montoBeneficio;
    }
    /**
     * Devuelve una representación en texto del objeto Beneficios.
     *
     * @return cadena con los valores de los atributos.
     */
    
    @Override
    public String toString() {
        return "Beneficios{" + "idBeneficio=" + idBeneficio + ", nomBeneficio=" + nomBeneficio + 
               ", descripcion=" + descripcion + ", montoBeneficio=" + montoBeneficio + '}';
    }
}