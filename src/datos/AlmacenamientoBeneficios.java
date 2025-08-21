package datos;

import java.util.ArrayList;
import logica.Beneficios;

public class AlmacenamientoBeneficios {

    private ArrayList<Beneficios> listaBeneficios;
    private static AlmacenamientoBeneficios instancia;

    private AlmacenamientoBeneficios() {
        this.listaBeneficios = new ArrayList<>();
    }

    public static synchronized AlmacenamientoBeneficios getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoBeneficios();
        }
        return instancia;
    }

    public boolean insertar(Beneficios beneficio) {
        try {
            if (buscarPorId(beneficio.getIdBeneficio()) != null) {
                return false;
            }
            return listaBeneficios.add(beneficio);
        } catch (Exception e) {
            return false;
        }
    }

    public Beneficios buscarPorId(int idBeneficio) {
        for (Beneficios beneficio : listaBeneficios) {
            if (beneficio.getIdBeneficio() == idBeneficio) {
                return beneficio;
            }
        }
        return null;
    }

    public ArrayList<Beneficios> buscarPorNombre(String nombre) {
        ArrayList<Beneficios> resultado = new ArrayList<>();
        for (Beneficios beneficio : listaBeneficios) {
            if (beneficio.getNomBeneficio().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(beneficio);
            }
        }
        return resultado;
    }

    public ArrayList<Beneficios> buscarPorRangoMonto(double montoMin, double montoMax) {
        ArrayList<Beneficios> resultado = new ArrayList<>();
        for (Beneficios beneficio : listaBeneficios) {
            if (beneficio.getMontoBeneficio() >= montoMin && beneficio.getMontoBeneficio() <= montoMax) {
                resultado.add(beneficio);
            }
        }
        return resultado;
    }

    public boolean modificar(int idBeneficio, Beneficios beneficioModificado) {
        try {
            Beneficios beneficio = buscarPorId(idBeneficio);
            if (beneficio != null) {
                int indice = listaBeneficios.indexOf(beneficio);
                listaBeneficios.set(indice, beneficioModificado);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminar(int idBeneficio) {
        try {
            Beneficios beneficio = buscarPorId(idBeneficio);
            if (beneficio != null) {
                return listaBeneficios.remove(beneficio);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Beneficios> obtenerTodos() {
        return new ArrayList<>(listaBeneficios);
    }

    public int obtenerTamaño() {
        return listaBeneficios.size();
    }

    public boolean existeBeneficio(int idBeneficio) {
        return buscarPorId(idBeneficio) != null;
    }

    public int obtenerSiguienteId() {
        int maxId = 0;
        for (Beneficios beneficio : listaBeneficios) {
            if (beneficio.getIdBeneficio() > maxId) {
                maxId = beneficio.getIdBeneficio();
            }
        }
        return maxId + 1;
    }
}
