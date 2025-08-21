package logica;
/**
 * Representa una carrera académica dentro del sistema.
 * Contiene información como el identificador, nombre y grado académico asociado.
 * 
 * Esta clase puede utilizarse para registrar y gestionar las carreras disponibles
 * en una institución educativa.
 * 
 * @author Jimena
 * @author Monse
 * @author Yerson
 */

public class Carreras {
    private int idCarrera;
    private String nomCarrera;
    private String grado; 
        /**
     * Constructor vacío constructor por defecto.
     */

    public Carreras() {
    }
    /**
     * Constructor con todos los atributos de la carrera.
     *
     * @param idCarrera Identificador único de la carrera.
     * @param nomCarrera Nombre de la carrera.
     * @param grado Grado académico que otorga la carrera.
     */
    
    public Carreras(int idCarrera, String nomCarrera, String grado) {
        this.idCarrera = idCarrera;
        this.nomCarrera = nomCarrera;
        this.grado = grado;
    }
 /**
     * Obtiene el identificador de la carrera.
     *
     * @return id de la carrera.
     */
    
    public int getIdCarrera() {
        return idCarrera;
    }
    /**
     * Establece el identificador de la carrera.
     *
     * @param idCarrera Nuevo identificador.
     */
    
    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }
    /**
     * Obtiene el nombre de la carrera.
     *
     * @return nombre de la carrera.
     */
    
    public String getNomCarrera() {
        return nomCarrera;
    }
     /**
     * Establece el nombre de la carrera.
     *
     * @param nomCarrera Nuevo nombre.
     */
    
    public void setNomCarrera(String nomCarrera) {
        this.nomCarrera = nomCarrera;
    }
     /**
     * Obtiene el grado académico de la carrera.
     *
     * @return grado académico.
     */

    public String getGrado() {
        return grado;
    }
     /**
     * Establece el grado académico de la carrera.
     *
     * @param grado Nuevo grado académico.
     */

    public void setGrado(String grado) {
        this.grado = grado;
    }
    /**
     * Devuelve una representación textual del objeto Carreras.
     *
     * @return cadena con los valores de los atributos.
     */

    @Override
    public String toString() {
        return "Carreras{" + "idCarrera=" + idCarrera + ", nomCarrera=" + nomCarrera + ", grado=" + grado + '}';
    }
}