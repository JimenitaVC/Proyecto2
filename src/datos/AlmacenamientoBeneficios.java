package datos;

import java.util.ArrayList;
import logica.Beneficios;

public class AlmacenamientoBeneficios {

    private ArrayList<Beneficios> listaBeneficios;
    private static AlmacenamientoBeneficios instancia;

    
    /**
     * Constructor privado para implementar el patrón Singleton.
     */

    private AlmacenamientoBeneficios() {
        this.listaBeneficios = new ArrayList<>();
    }

    
    /**
     * Obtiene la instancia única de {@code AlmacenamientoBeneficios}.
     *
     * @return instancia única de {@code AlmacenamientoBeneficios}
     */

    public static synchronized AlmacenamientoBeneficios getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoBeneficios();
        }
        return instancia;
    }

    
    /**
     * Inserta un nuevo beneficio si no existe uno con el mismo ID.
     *
     * @param beneficio el beneficio a insertar
     * @return {@code true} si se insertó correctamente, {@code false} si ya existe o ocurrió un error
     */

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

    
    /**
     * Busca un beneficio por su ID.
     *
     * @param idBeneficio el ID del beneficio
     * @return el beneficio encontrado o {@code null} si no existe
     */

    public Beneficios buscarPorId(int idBeneficio) {
        for (Beneficios beneficio : listaBeneficios) {
            if (beneficio.getIdBeneficio() == idBeneficio) {
                return beneficio;
            }
        }
        return null;
    }

        
    /**
     * Busca beneficios cuyo nombre contenga el texto especificado.
     *
     * @param nombre el texto a buscar en el nombre del beneficio
     * @return lista de beneficios que coinciden con el nombre
     */

    public ArrayList<Beneficios> buscarPorNombre(String nombre) {
        ArrayList<Beneficios> resultado = new ArrayList<>();
        for (Beneficios beneficio : listaBeneficios) {
            if (beneficio.getNomBeneficio().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(beneficio);
            }
        }
        return resultado;
    }

    
    /**
     * Busca beneficios cuyo monto esté dentro del rango especificado.
     *
     * @param montoMin monto mínimo
     * @param montoMax monto máximo
     * @return lista de beneficios dentro del rango
     */

    public ArrayList<Beneficios> buscarPorRangoMonto(double montoMin, double montoMax) {
        ArrayList<Beneficios> resultado = new ArrayList<>();
        for (Beneficios beneficio : listaBeneficios) {
            if (beneficio.getMontoBeneficio() >= montoMin && beneficio.getMontoBeneficio() <= montoMax) {
                resultado.add(beneficio);
            }
        }
        return resultado;
    }

    
    /**
     * Modifica un beneficio existente con nuevos datos.
     *
     * @param idBeneficio ID del beneficio a modificar
     * @param beneficioModificado objeto {@code Beneficios} con los nuevos datos
     * @return {@code true} si se modificó correctamente, {@code false} si no se encontró o ocurrió un error
     */

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

    
    /**
     * Elimina un beneficio por su ID.
     *
     * @param idBeneficio ID del beneficio a eliminar
     * @return {@code true} si se eliminó correctamente, {@code false} si no se encontró o ocurrió un error
     */

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

    
    /**
     * Obtiene todos los beneficios almacenados.
     *
     * @return lista de todos los beneficios
     */

    public ArrayList<Beneficios> obtenerTodos() {
        return new ArrayList<>(listaBeneficios);
    }

    
    /**
     * Obtiene el número total de beneficios almacenados.
     *
     * @return cantidad de beneficios
     */

    public int obtenerTamaño() {
        return listaBeneficios.size();
    }

    
    /**
     * Verifica si existe un beneficio con el ID especificado.
     *
     * @param idBeneficio ID del beneficio
     * @return {@code true} si existe, {@code false} si no
     */

    public boolean existeBeneficio(int idBeneficio) {
        return buscarPorId(idBeneficio) != null;
    }

    
    /**
     * Obtiene el siguiente ID disponible para un nuevo beneficio.
     *
     * @return siguiente ID disponible
     */

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
