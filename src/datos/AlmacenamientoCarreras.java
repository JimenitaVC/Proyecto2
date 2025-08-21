package datos;

import java.util.ArrayList;
import logica.Carreras;

public class AlmacenamientoCarreras {

    private ArrayList<Carreras> listaCarreras;
    private static AlmacenamientoCarreras instancia;

    public AlmacenamientoCarreras() {
        this.listaCarreras = new ArrayList<>();
    }

    public static AlmacenamientoCarreras getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoCarreras();
        }
        return instancia;
    }

    private void inicializarDatosPrueba() {
        insertar(new Carreras(1, "Ingeniería en Sistemas", "Licenciatura"));
        insertar(new Carreras(2, "Administración de Empresas", "Licenciatura"));
        insertar(new Carreras(3, "Derecho", "Licenciatura"));
    }

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

    public Carreras buscarPorId(int idCarrera) {
        for (Carreras carrera : listaCarreras) {
            if (carrera.getIdCarrera() == idCarrera) {
                return carrera;
            }
        }
        return null;
    }

    public ArrayList<Carreras> buscarPorNombre(String nombre) {
        ArrayList<Carreras> resultado = new ArrayList<>();
        for (Carreras carrera : listaCarreras) {
            if (carrera.getNomCarrera().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(carrera);
            }
        }
        return resultado;
    }

    public ArrayList<Carreras> buscarPorGrado(String grado) {
        ArrayList<Carreras> resultado = new ArrayList<>();
        for (Carreras carrera : listaCarreras) {
            if (carrera.getGrado().equalsIgnoreCase(grado)) {
                resultado.add(carrera);
            }
        }
        return resultado;
    }

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

    public ArrayList<Carreras> obtenerTodas() {
        return new ArrayList<>(listaCarreras);
    }

    public int obtenerTamaño() {
        return listaCarreras.size();
    }

    public boolean existeCarrera(int idCarrera) {
        return buscarPorId(idCarrera) != null;
    }

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
