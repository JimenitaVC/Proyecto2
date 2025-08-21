package datos;

import java.util.ArrayList;
import logica.Persona;

public class AlmacenamientoPersona {

    private ArrayList<Persona> listaPersonas;

    
    /**
     * Constructor que inicializa la lista de personas.
     */

    public AlmacenamientoPersona() {
        this.listaPersonas = new ArrayList<>();
    }

    
    /**
     * Inserta una nueva persona si no existe una con la misma cédula.
     *
     * @param persona la persona a insertar
     * @return {@code true} si se insertó correctamente, {@code false} si ya existe o ocurrió un error
     */

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

    
    /**
     * Busca una persona por su cédula.
     *
     * @param cedula la cédula de la persona
     * @return la persona encontrada o {@code null} si no existe
     */

    public Persona buscarPorCedula(String cedula) {
        for (Persona persona : listaPersonas) {
            if (persona.getCedula().equals(cedula)) {
                return persona;
            }
        }
        return null;
    }

    
    /**
     * Busca personas cuyo nombre contenga el texto especificado.
     *
     * @param nombre el texto a buscar en el nombre de la persona
     * @return lista de personas que coinciden con el nombre
     */

    public ArrayList<Persona> buscarPorNombre(String nombre) {
        ArrayList<Persona> resultado = new ArrayList<>();
        for (Persona persona : listaPersonas) {
            if (persona.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(persona);
            }
        }
        return resultado;
    }

    
    /**
     * Busca una persona por su correo electrónico.
     *
     * @param email el correo electrónico de la persona
     * @return la persona encontrada o {@code null} si no existe
     */

    public Persona buscarPorEmail(String email) {
        for (Persona persona : listaPersonas) {
            if (persona.getEmail().equalsIgnoreCase(email)) {
                return persona;
            }
        }
        return null;
    }

    
    /**
     * Modifica los datos de una persona existente.
     *
     * @param cedula cédula de la persona a modificar
     * @param personaModificada objeto {@code Persona} con los nuevos datos
     * @return {@code true} si se modificó correctamente, {@code false} si no se encontró o ocurrió un error
     */

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

    
    /**
     * Elimina una persona por su cédula.
     *
     * @param cedula cédula de la persona a eliminar
     * @return {@code true} si se eliminó correctamente, {@code false} si no se encontró o ocurrió un error
     */

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

    
    /**
     * Obtiene todas las personas almacenadas.
     *
     * @return lista de todas las personas
     */

    public ArrayList<Persona> obtenerTodas() {
        return new ArrayList<>(listaPersonas);
    }

    
    /**
     * Obtiene el número total de personas almacenadas.
     *
     * @return cantidad de personas
     */

    public int obtenerTamaño() {
        return listaPersonas.size();
    }

    
    /**
     * Verifica si existe una persona con la cédula especificada.
     *
     * @param cedula cédula de la persona
     * @return {@code true} si existe, {@code false} si no
     */

    public boolean existePersona(String cedula) {
        return buscarPorCedula(cedula) != null;
    }

    
    /**
     * Verifica si existe una persona con el correo electrónico especificado.
     *
     * @param email correo electrónico de la persona
     * @return {@code true} si existe, {@code false} si no
     */

    public boolean existeEmail(String email) {
        return buscarPorEmail(email) != null;
    }
}
