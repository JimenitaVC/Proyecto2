package datos;

import java.util.ArrayList;
import logica.Estudiante;

public class AlmacenamientoEstudiante {

    private ArrayList<Estudiante> listaEstudiantes;
    private static AlmacenamientoEstudiante instancia;

    private AlmacenamientoEstudiante() {
        this.listaEstudiantes = new ArrayList<>();
    }

    public static AlmacenamientoEstudiante getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoEstudiante();
        }
        return instancia;
    }

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

    public Estudiante buscarPorCedula(String cedula) {
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getCedula().equals(cedula)) {
                return estudiante;
            }
        }
        return null;
    }

    public Estudiante buscarPorCarnet(String carnet) {
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getCarnet().equals(carnet)) {
                return estudiante;
            }
        }
        return null;
    }

    public ArrayList<Estudiante> buscarPorNombre(String nombre) {
        ArrayList<Estudiante> resultado = new ArrayList<>();
        for (Estudiante estudiante : listaEstudiantes) {
            if (estudiante.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(estudiante);
            }
        }
        return resultado;
    }

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

    public ArrayList<Estudiante> obtenerTodos() {
        return new ArrayList<>(listaEstudiantes);
    }

    public int obtenerTamaño() {
        return listaEstudiantes.size();
    }

    public boolean existeEstudiante(String cedula) {
        return buscarPorCedula(cedula) != null;
    }
}
