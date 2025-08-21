package datos;

import java.util.ArrayList;
import java.util.Calendar;
import logica.PagosMensuales;

public class AlmacenamientoPagosMensuales {

    private ArrayList<PagosMensuales> listaPagosMensuales;
    private static AlmacenamientoPagosMensuales instancia;

    
    /**
     * Constructor privado AlmacenamientoPagosMensuales
     */

    private AlmacenamientoPagosMensuales() {
        this.listaPagosMensuales = new ArrayList<>();
    }

    
    /**
     * Obtiene la instancia única de {@code AlmacenamientoPagosMensuales}.
     *
     * @return instancia única
     */

    public static synchronized AlmacenamientoPagosMensuales getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoPagosMensuales();
        }
        return instancia;
    }

    
    /**
     * Inserta un nuevo pago mensual si no existe uno para el mismo estudiante, mes y año.
     *
     * @param pago el pago a insertar
     * @return {@code true} si se insertó correctamente, {@code false} si ya existe o ocurrió un error
     */

    public boolean insertar(PagosMensuales pago) {
        try {
            int siguienteId = obtenerSiguienteId();
            pago.setIdPago(siguienteId);
            Calendar fechaPago = pago.getFechaPago();
            int año = fechaPago.get(Calendar.YEAR);
            if (existePagoEstudianteMesAño(pago.getEstudiante(), pago.getMes(), año)) {
                return false;
            }
            return listaPagosMensuales.add(pago);
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Modifica un pago mensual existente.
     *
     * @param idPago ID del pago a modificar
     * @param pagoModificado objeto {@code PagosMensuales} con los nuevos datos
     * @return {@code true} si se modificó correctamente, {@code false} si no se encontró o ocurrió un error
     */

    public boolean modificar(int idPago, PagosMensuales pagoModificado) {
        try {
            PagosMensuales pagoOriginal = buscarPorId(idPago);
            if (pagoOriginal == null) {
                return false;
            }
            pagoModificado.setIdPago(idPago);
            pagoModificado.setFechaCreacion(pagoOriginal.getFechaCreacion());

            Calendar fechaPago = pagoModificado.getFechaPago();
            int año = fechaPago.get(Calendar.YEAR);

            if (!pagoOriginal.getEstudiante().equals(pagoModificado.getEstudiante())
                    || !pagoOriginal.getMes().equals(pagoModificado.getMes())
                    || pagoOriginal.getFechaPago().get(Calendar.YEAR) != año) {

                if (existePagoEstudianteMesAño(pagoModificado.getEstudiante(),
                        pagoModificado.getMes(), año)) {
                    return false;
                }
            }

            int indice = listaPagosMensuales.indexOf(pagoOriginal);
            if (indice != -1) {
                listaPagosMensuales.set(indice, pagoModificado);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Elimina un pago mensual por su ID.
     *
     * @param idPago ID del pago a eliminar
     * @return {@code true} si se eliminó correctamente, {@code false} si no se encontró o ocurrió un error
     */

    public boolean eliminar(int idPago) {
        try {
            PagosMensuales pago = buscarPorId(idPago);
            if (pago != null) {
                return listaPagosMensuales.remove(pago);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Busca un pago mensual por su ID.
     *
     * @param idPago ID del pago
     * @return el pago encontrado o {@code null} si no existe
     */

    public PagosMensuales buscarPorId(int idPago) {
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getIdPago() == idPago) {
                return pago;
            }
        }
        return null;
    }

    
    /**
     * Busca pagos realizados por un estudiante según su cédula.
     *
     * @param cedula cédula del estudiante
     * @return lista de pagos encontrados
     */

    public ArrayList<PagosMensuales> buscarPorEstudiante(String cedula) {
        ArrayList<PagosMensuales> resultado = new ArrayList<>();
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getEstudiante().toLowerCase().contains(cedula.toLowerCase())) {
                resultado.add(pago);
            }
        }
        return resultado;
    }

    
    /**
     * Busca pagos por mes y año.
     *
     * @param mes nombre del mes
     * @param año año del pago
     * @return lista de pagos encontrados
     */

    public ArrayList<PagosMensuales> buscarPorMesYAño(String mes, int año) {
        ArrayList<PagosMensuales> resultado = new ArrayList<>();
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getMes().equalsIgnoreCase(mes)
                    && pago.getFechaPago().get(Calendar.YEAR) == año) {
                resultado.add(pago);
            }
        }
        return resultado;
    }

    
    /**
     * Busca pagos por nombre del mes.
     *
     * @param mes nombre del mes
     * @return lista de pagos encontrados
     */

    public ArrayList<PagosMensuales> buscarPorMes(String mes) {
        ArrayList<PagosMensuales> resultado = new ArrayList<>();
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getMes().toLowerCase().contains(mes.toLowerCase())) {
                resultado.add(pago);
            }
        }
        return resultado;
    }

    
    /**
     * Verifica si existe un pago para un estudiante en un mes y año específicos.
     *
     * @param cedula cédula del estudiante
     * @param mes nombre del mes
     * @param año año del pago
     * @return {@code true} si existe, {@code false} si no
     */

    public boolean existePagoEstudianteMesAño(String cedula, String mes, int año) {
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getEstudiante().equals(cedula)
                    && pago.getMes().equalsIgnoreCase(mes)
                    && pago.getFechaPago().get(Calendar.YEAR) == año) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * Obtiene todos los pagos mensuales almacenados.
     *
     * @return lista de pagos
     */

    public ArrayList<PagosMensuales> obtenerTodos() {
        return new ArrayList<>(listaPagosMensuales);
    }

    
    /**
     * Filtra pagos según cédula, mes y año.
     *
     * @param cedula cédula del estudiante
     * @param mes nombre del mes
     * @param año año del pago
     * @return lista de pagos que cumplen con los filtros
     */

    public ArrayList<PagosMensuales> filtrarPagos(String cedula, String mes, Integer año) {
        ArrayList<PagosMensuales> resultado = new ArrayList<>();
        for (PagosMensuales pago : listaPagosMensuales) {
            boolean cumpleFiltro = true;

            if (cedula != null && !cedula.trim().isEmpty()) {
                if (!pago.getEstudiante().toLowerCase().contains(cedula.toLowerCase())) {
                    cumpleFiltro = false;
                }
            }

            if (mes != null && !mes.trim().isEmpty()) {
                if (!pago.getMes().toLowerCase().contains(mes.toLowerCase())) {
                    cumpleFiltro = false;
                }
            }

            if (año != null) {
                if (pago.getFechaPago().get(Calendar.YEAR) != año) {
                    cumpleFiltro = false;
                }
            }

            if (cumpleFiltro) {
                resultado.add(pago);
            }
        }
        return resultado;
    }

    
    /**
     * Calcula el total pagado en un mes y año específicos.
     *
     * @param mes nombre del mes
     * @param año año del pago
     * @return monto total pagado
     */

    public double calcularTotalPagadoMesAño(String mes, int año) {
        double total = 0.0;
        ArrayList<PagosMensuales> pagosMes = buscarPorMesYAño(mes, año);
        for (PagosMensuales pago : pagosMes) {
            total += pago.getPagoTotal();
        }
        return total;
    }

    
    /**
     * Calcula el total de beneficios aplicados en todos los pagos.
     *
     * @return monto total de beneficios
     */

    public double calcularTotalBeneficios() {
        double total = 0.0;
        for (PagosMensuales pago : listaPagosMensuales) {
            total += pago.getTotalBeneficio();
        }
        return total;
    }

    
    /**
     * Calcula el total de deducciones por seguro en todos los pagos.
     *
     * @return monto total de deducciones por seguro
     */

    public double calcularTotalDeduccionesSeguro() {
        double total = 0.0;
        for (PagosMensuales pago : listaPagosMensuales) {
            total += pago.getDeduccionSeguro();
        }
        return total;
    }

    
    /**
     * Calcula el total de deducciones por renta en todos los pagos.
     *
     * @return monto total de deducciones por renta
     */

    public double calcularTotalDeduccionesRenta() {
        double total = 0.0;
        for (PagosMensuales pago : listaPagosMensuales) {
            total += pago.getDeduccionRenta();
        }
        return total;
    }

    
    /**
     * Obtiene el número total de pagos almacenados.
     *
     * @return cantidad de pagos
     */

    public int obtenerTamaño() {
        return listaPagosMensuales.size();
    }

    
    /**
     * Obtiene el siguiente ID disponible para un nuevo pago.
     *
     * @return siguiente ID disponible
     */

    public int obtenerSiguienteId() {
        int maxId = 0;
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getIdPago() > maxId) {
                maxId = pago.getIdPago();
            }
        }
        return maxId + 1;
    }

    
    /**
     * Elimina todos los pagos realizados por un estudiante.
     *
     * @param cedula cédula del estudiante
     * @return {@code true} si se eliminaron correctamente, {@code false} si ocurrió un error
     */

    public boolean eliminarPagosDeEstudiante(String cedula) {
        try {
            listaPagosMensuales.removeIf(pago -> pago.getEstudiante().equals(cedula));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
    /**
     * Elimina todos los pagos almacenados.
     */

    public void limpiarTodos() {
        listaPagosMensuales.clear();
    }
}
