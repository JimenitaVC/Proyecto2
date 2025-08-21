package logica;

public class Carreras {
    private int idCarrera;
    private String nomCarrera;
    private String grado; 
    
    public Carreras() {
    }
    
    public Carreras(int idCarrera, String nomCarrera, String grado) {
        this.idCarrera = idCarrera;
        this.nomCarrera = nomCarrera;
        this.grado = grado;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNomCarrera() {
        return nomCarrera;
    }

    public void setNomCarrera(String nomCarrera) {
        this.nomCarrera = nomCarrera;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    @Override
    public String toString() {
        return "Carreras{" + "idCarrera=" + idCarrera + ", nomCarrera=" + nomCarrera + ", grado=" + grado + '}';
    }
}