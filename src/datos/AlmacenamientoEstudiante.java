package datos;

import java.util.ArrayList;
import logica.Estudiante;

public class AlmacenamientoEstudiante {

    private ArrayList<Estudiante> listaEstudiantes;
    private static AlmacenamientoEstudiante instancia;

    
    /**
     * Constructor privado para implementar el patrón Singleton.
     */

    private AlmacenamientoEstudiante() {
        this.listaEstudiantes = new ArrayList<>();
    }

    
    /**
     * Obtiene la instancia única de {@code AlmacenamientoEstudiante}.
     *
     * @return instancia única
     */

    public static AlmacenamientoEstudiante getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoEstudiante();
        }
        return instancia;
    }

    
    /**
     * Inserta un nuevo estudiante si no existe uno con la misma cédula o carnet.
     *
     * @param estudiante el estudiante a insertar
     * @return {@code true} si se insertó correctamente, {@code false} si ya existe o ocurrió un error
     */

    public boolean insertar(Estudiante estudiante) {
        try {
            if (buscarPorCedula(estudiante.getCedula()) != null) {
                return false;
            }
            if (buscarPorCarnet(estudiante.getCarnet()) != null) {
                return false;
            }
            return listaEstudiantes.add(estudiante);
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Busca un estudiante por su cédula.
     *
     * @param cedula la cédula del estudiante
     * @return el estudiante encontrado o {@code null} si no existe
     */

    public Estudiante buscarPorCedula(String cedula) {
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getCedula().equals(cedula)) {
                return estudiante;
            }
        }
        return null;
    }

    
    /**
     * Busca un estudiante por su carnet.
     *
     * @param carnet el carnet del estudiante
     * @return el estudiante encontrado o {@code null} si no existe
     */

    public Estudiante buscarPorCarnet(String carnet) {
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getCarnet().equals(carnet)) {
                return estudiante;
            }
        }
        return null;
    }

    
/**      * Busca estudiantes cuyo nombre contenga el texto especificado.
     *
     * @param nombre el texto a buscar en el nombre del estudiante
     * @return lista de estudiantes que coinciden con el nombre
     */

    public ArrayList<Estudiante> buscarPorNombre(String nombre) {
        ArrayList<Estudiante> resultado = new ArrayList<>();
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(estudiante);
            }
        }
        return resultado;
    }

    
    /**
     * Modifica los datos de un estudiante existente.
     *
     * @param cedula cédula del estudiante a modificar
     * @param estudianteModificado objeto {@code Estudiante} con los nuevos datos
     * @return {@code true} si se modificó correctamente, {@code false} si no se encontró o ocurrió un error
     */

    public boolean modificar(String cedula, Estudiante estudianteModificado) {
        try {
            Estudiante estudiante = buscarPorCedula(cedula);
            if (estudiante != null) {
                if (!estudiante.getCarnet().equals(estudianteModificado.getCarnet())) {
                    if (buscarPorCarnet(estudianteModificado.getCarnet()) != null) {
                        return false;
                    }
                }

                int indice = listaEstudiantes.indexOf(estudiante);
                listaEstudiantes.set(indice, estudianteModificado);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Elimina un estudiante por su cédula.
     *
     * @param cedula cédula del estudiante a eliminar
     * @return {@code true} si se eliminó correctamente, {@code false} si no se encontró o ocurrió un error
     */

    public boolean eliminar(String cedula) {
        try {
            Estudiante estudiante = buscarPorCedula(cedula);
            if (estudiante != null) {
                return listaEstudiantes.remove(estudiante);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Obtiene todos los estudiantes almacenados.
     *
     * @return lista de todos los estudiantes
     */

    public ArrayList<Estudiante> obtenerTodos() {
        return new ArrayList<>(listaEstudiantes);
    }

    
    /**
     * Obtiene el número total de estudiantes almacenados.
     *
     * @return cantidad de estudiantes
     */

    public int obtenerTamaño() {
        return listaEstudiantes.size();
    }

    
    /**
     * Verifica si existe un estudiante con la cédula especificada.
     *
     * @param cedula cédula del estudiante
     * @return {@code true} si existe, {@code false} si no
     */

    public boolean existeEstudiante(String cedula) {
        return buscarPorCedula(cedula) != null;
    }
}
