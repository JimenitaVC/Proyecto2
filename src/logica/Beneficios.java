package logica;

public class Beneficios {
    private int idBeneficio;
    private String nomBeneficio;
    private String descripcion;
    private double montoBeneficio;
    
    public Beneficios() {
    }
    
    public Beneficios(int idBeneficio, String nomBeneficio, String descripcion, double montoBeneficio) {
        this.idBeneficio = idBeneficio;
        this.nomBeneficio = nomBeneficio;
        this.descripcion = descripcion;
        this.montoBeneficio = montoBeneficio;
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

    public double getMontoBeneficio() {
        return montoBeneficio;
    }

    public void setMontoBeneficio(double montoBeneficio) {
        this.montoBeneficio = montoBeneficio;
    }

    @Override
    public String toString() {
        return "Beneficios{" + "idBeneficio=" + idBeneficio + ", nomBeneficio=" + nomBeneficio + 
               ", descripcion=" + descripcion + ", montoBeneficio=" + montoBeneficio + '}';
    }
}