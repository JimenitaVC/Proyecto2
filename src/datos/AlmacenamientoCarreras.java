package datos;

import java.util.ArrayList;
import logica.Carreras;

public class AlmacenamientoCarreras {

    private ArrayList<Carreras> listaCarreras;
    private static AlmacenamientoCarreras instancia;

    public AlmacenamientoCarreras() {
        this.listaCarreras = new ArrayList<>();
    }

    
    /**
     * Constructor que inicializa la lista de carreras.
     */

    public static AlmacenamientoCarreras getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoCarreras();
        }
        return instancia;
    }

    
    /**
     * Inicializa datos de prueba con algunas carreras predefinidas.
     */

    private void inicializarDatosPrueba() {
        insertar(new Carreras(1, "Ingeniería en Sistemas", "Licenciatura"));
        insertar(new Carreras(2, "Administración de Empresas", "Licenciatura"));
        insertar(new Carreras(3, "Derecho", "Licenciatura"));
    }

    
    /**
     * Inserta una nueva carrera si no existe una con el mismo ID.
     *
     * @param carrera la carrera a insertar
     * @return {@code true} si se insertó correctamente, {@code false} si ya existe o ocurrió un error
     */

    public boolean insertar(Carreras carrera) {
        try {
            if (buscarPorId(carrera.getIdCarrera()) != null) {
                return false;
            }
            return listaCarreras.add(carrera);
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Busca una carrera por su ID.
     *
     * @param idCarrera el ID de la carrera
     * @return la carrera encontrada o {@code null} si no existe
     */

    public Carreras buscarPorId(int idCarrera) {
        for (Carreras carrera : listaCarreras) {
            if (carrera.getIdCarrera() == idCarrera) {
                return carrera;
            }
        }
        return null;
    }

    
    /**
     * Busca carreras cuyo nombre contenga el texto especificado.
     *
     * @param nombre el texto a buscar en el nombre de la carrera
     * @return lista de carreras que coinciden con el nombre
     */

    public ArrayList<Carreras> buscarPorNombre(String nombre) {
        ArrayList<Carreras> resultado = new ArrayList<>();
        for (Carreras carrera : listaCarreras) {
            if (carrera.getNomCarrera().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(carrera);
            }
        }
        return resultado;
    }

    
    /**
     * Busca carreras por el grado académico.
     *
     * @param grado el grado académico (por ejemplo, "Licenciatura")
     * @return lista de carreras que coinciden con el grado
     */

    public ArrayList<Carreras> buscarPorGrado(String grado) {
        ArrayList<Carreras> resultado = new ArrayList<>();
        for (Carreras carrera : listaCarreras) {
            if (carrera.getGrado().equalsIgnoreCase(grado)) {
                resultado.add(carrera);
            }
        }
        return resultado;
    }

    
    /**
     * Modifica una carrera existente con nuevos datos.
     *
     * @param idCarrera ID de la carrera a modificar
     * @param carreraModificada objeto {@code Carreras} con los nuevos datos
     * @return {@code true} si se modificó correctamente, {@code false} si no se encontró o ocurrió un error
     */

    public boolean modificar(int idCarrera, Carreras carreraModificada) {
        try {
            Carreras carrera = buscarPorId(idCarrera);
            if (carrera != null) {
                int indice = listaCarreras.indexOf(carrera);
                listaCarreras.set(indice, carreraModificada);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Elimina una carrera por su ID.
     *
     * @param idCarrera ID de la carrera a eliminar
     * @return {@code true} si se eliminó correctamente, {@code false} si no se encontró o ocurrió un error
     */

    public boolean eliminar(int idCarrera) {
        try {
            Carreras carrera = buscarPorId(idCarrera);
            if (carrera != null) {
                return listaCarreras.remove(carrera);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Obtiene todas las carreras almacenadas.
     *
     * @return lista de todas las carreras
     */

    public ArrayList<Carreras> obtenerTodas() {
        return new ArrayList<>(listaCarreras);
    }

    
    /**
     * Obtiene el número total de carreras almacenadas.
     *
     * @return cantidad de carreras
     */

    public int obtenerTamaño() {
        return listaCarreras.size();
    }

    
    /**
     * Verifica si existe una carrera con el ID especificado.
     *
     * @param idCarrera ID de la carrera
     * @return {@code true} si existe, {@code false} si no
     */

    public boolean existeCarrera(int idCarrera) {
        return buscarPorId(idCarrera) != null;
    }

    
    /**
     * Obtiene el siguiente ID disponible para una nueva carrera.
     *
     * @return siguiente ID disponible
     */

    public int obtenerSiguienteId() {
        int maxId = 0;
        for (Carreras carrera : listaCarreras) {
            if (carrera.getIdCarrera() > maxId) {
                maxId = carrera.getIdCarrera();
            }
        }
        return maxId + 1;
    }
}
