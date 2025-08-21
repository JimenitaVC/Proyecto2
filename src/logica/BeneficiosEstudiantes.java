package logica;

public class BeneficiosEstudiantes {
    private String cedula; 
    private int idBeneficio; 
    
    public BeneficiosEstudiantes() {
    }
    
    public BeneficiosEstudiantes(String cedula, int idBeneficio) {
        this.cedula = cedula;
        this.idBeneficio = idBeneficio;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getIdBeneficio() {
        return idBeneficio;
    }

    public void setIdBeneficio(int idBeneficio) {
        this.idBeneficio = idBeneficio;
    }

    @Override
    public String toString() {
        return "BeneficiosEstudiantes{" + "cedula=" + cedula + ", idBeneficio=" + idBeneficio + '}';
    }
}