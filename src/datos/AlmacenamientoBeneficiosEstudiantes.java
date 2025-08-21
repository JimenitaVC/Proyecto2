package datos;

import java.util.ArrayList;
import logica.BeneficiosEstudiantes;

public class AlmacenamientoBeneficiosEstudiantes {

    private ArrayList<BeneficiosEstudiantes> listaBeneficiosEstudiantes;
    private static AlmacenamientoBeneficiosEstudiantes instancia;

    
    /**
     * Constructor que inicializa la lista de beneficios asignados a estudiantes.
     */

    public AlmacenamientoBeneficiosEstudiantes() {
        this.listaBeneficiosEstudiantes = new ArrayList<>();
    }

    
    /**
     * Obtiene la instancia única de {@code AlmacenamientoBeneficiosEstudiantes}.
     *
     * @return instancia única
     */

    public static AlmacenamientoBeneficiosEstudiantes getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoBeneficiosEstudiantes();
        }
        return instancia;
    }

    
    /**
     * Inserta una nueva asignación de beneficio a un estudiante si no existe previamente.
     *
     * @param beneficioEstudiante asignación a insertar
     * @return {@code true} si se insertó correctamente, {@code false} si ya existe o ocurrió un error
     */

    public boolean insertar(BeneficiosEstudiantes beneficioEstudiante) {
        try {
            if (existeAsignacion(beneficioEstudiante.getCedula(), beneficioEstudiante.getIdBeneficio())) {
                return false;
            }
            return listaBeneficiosEstudiantes.add(beneficioEstudiante);
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Modifica una asignación existente de beneficio a estudiante.
     *
     * @param cedulaOriginal cédula original del estudiante
     * @param idBeneficioOriginal ID original del beneficio
     * @param beneficioModificado nueva asignación
     * @return {@code true} si se modificó correctamente, {@code false} si no se encontró o ocurrió un error
     */

    public boolean modificar(String cedulaOriginal, int idBeneficioOriginal, BeneficiosEstudiantes beneficioModificado) {
        try {
            BeneficiosEstudiantes asignacionOriginal = null;
            for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
                if (be.getCedula().equals(cedulaOriginal) && be.getIdBeneficio() == idBeneficioOriginal) {
                    asignacionOriginal = be;
                    break;
                }
            }

            if (asignacionOriginal == null) {
                return false;
            }

            if (!cedulaOriginal.equals(beneficioModificado.getCedula())
                    || idBeneficioOriginal != beneficioModificado.getIdBeneficio()) {
                if (existeAsignacion(beneficioModificado.getCedula(), beneficioModificado.getIdBeneficio())) {
                    return false;
                }
            }

            asignacionOriginal.setCedula(beneficioModificado.getCedula());
            asignacionOriginal.setIdBeneficio(beneficioModificado.getIdBeneficio());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Elimina una asignación de beneficio a estudiante.
     *
     * @param cedula cédula del estudiante
     * @param idBeneficio ID del beneficio
     * @return {@code true} si se eliminó correctamente, {@code false} si no se encontró o ocurrió un error
     */

    public boolean eliminar(String cedula, int idBeneficio) {
        try {
            BeneficiosEstudiantes beneficioAQuitar = null;
            for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
                if (be.getCedula().equals(cedula) && be.getIdBeneficio() == idBeneficio) {
                    beneficioAQuitar = be;
                    break;
                }
            }

            if (beneficioAQuitar != null) {
                return listaBeneficiosEstudiantes.remove(beneficioAQuitar);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Busca asignaciones por cédula del estudiante.
     *
     * @param cedula cédula del estudiante
     * @return lista de asignaciones que coinciden
     */

    public ArrayList<BeneficiosEstudiantes> buscarPorCedula(String cedula) {
        ArrayList<BeneficiosEstudiantes> resultados = new ArrayList<>();
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getCedula().toLowerCase().contains(cedula.toLowerCase())) {
                resultados.add(be);
            }
        }
        return resultados;
    }

    
    /**
     * Busca asignaciones por ID de beneficio.
     *
     * @param idBeneficio ID del beneficio
     * @return lista de asignaciones que coinciden
     */

    public ArrayList<BeneficiosEstudiantes> buscarPorIdBeneficio(int idBeneficio) {
        ArrayList<BeneficiosEstudiantes> resultados = new ArrayList<>();
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getIdBeneficio() == idBeneficio) {
                resultados.add(be);
            }
        }
        return resultados;
    }

    
    /**
     * Busca asignaciones por nombre del estudiante.
     * (Método aún no implementado)
     *
     * @param nombreEstudiante nombre del estudiante
     * @return lista vacía
     */

    public ArrayList<BeneficiosEstudiantes> buscarPorNombreEstudiante(String nombreEstudiante) {
        ArrayList<BeneficiosEstudiantes> resultados = new ArrayList<>();
        return resultados;
    }

    
    /**
     * Asigna un beneficio a un estudiante.
     *
     * @param cedula cédula del estudiante
     * @param idBeneficio ID del beneficio
     * @return {@code true} si se asignó correctamente, {@code false} si ya existía
     */

    public boolean asignarBeneficio(String cedula, int idBeneficio) {
        BeneficiosEstudiantes beneficioEstudiante = new BeneficiosEstudiantes(cedula, idBeneficio);
        return insertar(beneficioEstudiante);
    }

    
    /**
     * Quita un beneficio asignado a un estudiante.
     *
     * @param cedula cédula del estudiante
     * @param idBeneficio ID del beneficio
     * @return {@code true} si se eliminó correctamente, {@code false} si no se encontró
     */

    public boolean quitarBeneficio(String cedula, int idBeneficio) {
        return eliminar(cedula, idBeneficio);
    }

    
    /**
     * Obtiene los IDs de beneficios asignados a un estudiante.
     *
     * @param cedula cédula del estudiante
     * @return lista de IDs de beneficios
     */

    public ArrayList<Integer> obtenerBeneficiosDeEstudiante(String cedula) {
        ArrayList<Integer> beneficiosEstudiante = new ArrayList<>();
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getCedula().equals(cedula)) {
                beneficiosEstudiante.add(be.getIdBeneficio());
            }
        }
        return beneficiosEstudiante;
    }

    
    /**
     * Obtiene las cédulas de estudiantes que tienen asignado un beneficio específico.
     *
     * @param idBeneficio ID del beneficio
     * @return lista de cédulas
     */

    public ArrayList<String> obtenerEstudiantesConBeneficio(int idBeneficio) {
        ArrayList<String> estudiantesConBeneficio = new ArrayList<>();
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getIdBeneficio() == idBeneficio) {
                estudiantesConBeneficio.add(be.getCedula());
            }
        }
        return estudiantesConBeneficio;
    }

    
    /**
     * Verifica si existe una asignación específica de beneficio a estudiante.
     *
     * @param cedula cédula del estudiante
     * @param idBeneficio ID del beneficio
     * @return {@code true} si existe, {@code false} si no
     */

    public boolean existeAsignacion(String cedula, int idBeneficio) {
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getCedula().equals(cedula) && be.getIdBeneficio() == idBeneficio) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Obtiene todas las asignaciones de beneficios a estudiantes.
     *
     * @return lista de asignaciones
     */

    public ArrayList<BeneficiosEstudiantes> obtenerTodas() {
        return new ArrayList<>(listaBeneficiosEstudiantes);
    }

    
    /**
     * Elimina todas las asignaciones de beneficios de un estudiante.
     *
     * @param cedula cédula del estudiante
     * @return {@code true} si se eliminaron correctamente, {@code false} si ocurrió un error
     */

    public boolean eliminarAsignacionesDeEstudiante(String cedula) {
        try {
            listaBeneficiosEstudiantes.removeIf(be -> be.getCedula().equals(cedula));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Elimina todas las asignaciones de un beneficio específico.
     *
     * @param idBeneficio ID del beneficio
     * @return {@code true} si se eliminaron correctamente, {@code false} si ocurrió un error
     */

    public boolean eliminarAsignacionesDeBeneficio(int idBeneficio) {
        try {
            listaBeneficiosEstudiantes.removeIf(be -> be.getIdBeneficio() == idBeneficio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Obtiene el número total de asignaciones almacenadas.
     *
     * @return cantidad de asignaciones
     */

    public int obtenerTamaño() {
        return listaBeneficiosEstudiantes.size();
    }
}
