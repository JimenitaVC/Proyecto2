package datos;

import java.util.ArrayList;
import logica.BeneficiosEstudiantes;

public class AlmacenamientoBeneficiosEstudiantes {

    private ArrayList<BeneficiosEstudiantes> listaBeneficiosEstudiantes;
    private static AlmacenamientoBeneficiosEstudiantes instancia;

    // Constructor
    public AlmacenamientoBeneficiosEstudiantes() {
        this.listaBeneficiosEstudiantes = new ArrayList<>();
    }

    public static AlmacenamientoBeneficiosEstudiantes getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoBeneficiosEstudiantes();
        }
        return instancia;
    }

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

    public ArrayList<BeneficiosEstudiantes> buscarPorCedula(String cedula) {
        ArrayList<BeneficiosEstudiantes> resultados = new ArrayList<>();
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getCedula().toLowerCase().contains(cedula.toLowerCase())) {
                resultados.add(be);
            }
        }
        return resultados;
    }

    public ArrayList<BeneficiosEstudiantes> buscarPorIdBeneficio(int idBeneficio) {
        ArrayList<BeneficiosEstudiantes> resultados = new ArrayList<>();
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getIdBeneficio() == idBeneficio) {
                resultados.add(be);
            }
        }
        return resultados;
    }

    public ArrayList<BeneficiosEstudiantes> buscarPorNombreEstudiante(String nombreEstudiante) {
        ArrayList<BeneficiosEstudiantes> resultados = new ArrayList<>();
        return resultados;
    }

    public boolean asignarBeneficio(String cedula, int idBeneficio) {
        BeneficiosEstudiantes beneficioEstudiante = new BeneficiosEstudiantes(cedula, idBeneficio);
        return insertar(beneficioEstudiante);
    }

    public boolean quitarBeneficio(String cedula, int idBeneficio) {
        return eliminar(cedula, idBeneficio);
    }

    public ArrayList<Integer> obtenerBeneficiosDeEstudiante(String cedula) {
        ArrayList<Integer> beneficiosEstudiante = new ArrayList<>();
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getCedula().equals(cedula)) {
                beneficiosEstudiante.add(be.getIdBeneficio());
            }
        }
        return beneficiosEstudiante;
    }

    public ArrayList<String> obtenerEstudiantesConBeneficio(int idBeneficio) {
        ArrayList<String> estudiantesConBeneficio = new ArrayList<>();
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getIdBeneficio() == idBeneficio) {
                estudiantesConBeneficio.add(be.getCedula());
            }
        }
        return estudiantesConBeneficio;
    }

    public boolean existeAsignacion(String cedula, int idBeneficio) {
        for (BeneficiosEstudiantes be : listaBeneficiosEstudiantes) {
            if (be.getCedula().equals(cedula) && be.getIdBeneficio() == idBeneficio) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<BeneficiosEstudiantes> obtenerTodas() {
        return new ArrayList<>(listaBeneficiosEstudiantes);
    }

    public boolean eliminarAsignacionesDeEstudiante(String cedula) {
        try {
            listaBeneficiosEstudiantes.removeIf(be -> be.getCedula().equals(cedula));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarAsignacionesDeBeneficio(int idBeneficio) {
        try {
            listaBeneficiosEstudiantes.removeIf(be -> be.getIdBeneficio() == idBeneficio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int obtenerTamaño() {
        return listaBeneficiosEstudiantes.size();
    }
}
