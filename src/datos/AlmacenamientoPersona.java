package datos;

import java.util.ArrayList;
import logica.Persona;

public class AlmacenamientoPersona {

    private ArrayList<Persona> listaPersonas;

    public AlmacenamientoPersona() {
        this.listaPersonas = new ArrayList<>();
    }

    public boolean insertar(Persona persona) {
        try {
            if (buscarPorCedula(persona.getCedula()) != null) {
                return false;
            }
            return listaPersonas.add(persona);
        } catch (Exception e) {
            return false;
        }
    }

    public Persona buscarPorCedula(String cedula) {
        for (Persona persona : listaPersonas) {
            if (persona.getCedula().equals(cedula)) {
                return persona;
            }
        }
        return null;
    }

    public ArrayList<Persona> buscarPorNombre(String nombre) {
        ArrayList<Persona> resultado = new ArrayList<>();
        for (Persona persona : listaPersonas) {
            if (persona.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(persona);
            }
        }
        return resultado;
    }

    public Persona buscarPorEmail(String email) {
        for (Persona persona : listaPersonas) {
            if (persona.getEmail().equalsIgnoreCase(email)) {
                return persona;
            }
        }
        return null;
    }

    public boolean modificar(String cedula, Persona personaModificada) {
        try {
            Persona persona = buscarPorCedula(cedula);
            if (persona != null) {
                int indice = listaPersonas.indexOf(persona);
                listaPersonas.set(indice, personaModificada);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminar(String cedula) {
        try {
            Persona persona = buscarPorCedula(cedula);
            if (persona != null) {
                return listaPersonas.remove(persona);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Persona> obtenerTodas() {
        return new ArrayList<>(listaPersonas);
    }

    public int obtenerTamaño() {
        return listaPersonas.size();
    }

    public boolean existePersona(String cedula) {
        return buscarPorCedula(cedula) != null;
    }

    public boolean existeEmail(String email) {
        return buscarPorEmail(email) != null;
    }
}
