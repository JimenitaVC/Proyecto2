package datos;

import java.util.ArrayList;
import java.util.Calendar;
import logica.PagosMensuales;

public class AlmacenamientoPagosMensuales {

    private ArrayList<PagosMensuales> listaPagosMensuales;
    private static AlmacenamientoPagosMensuales instancia;

    private AlmacenamientoPagosMensuales() {
        this.listaPagosMensuales = new ArrayList<>();
    }

    public static synchronized AlmacenamientoPagosMensuales getInstance() {
        if (instancia == null) {
            instancia = new AlmacenamientoPagosMensuales();
        }
        return instancia;
    }

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

    public PagosMensuales buscarPorId(int idPago) {
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getIdPago() == idPago) {
                return pago;
            }
        }
        return null;
    }

    public ArrayList<PagosMensuales> buscarPorEstudiante(String cedula) {
        ArrayList<PagosMensuales> resultado = new ArrayList<>();
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getEstudiante().toLowerCase().contains(cedula.toLowerCase())) {
                resultado.add(pago);
            }
        }
        return resultado;
    }

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

    public ArrayList<PagosMensuales> buscarPorMes(String mes) {
        ArrayList<PagosMensuales> resultado = new ArrayList<>();
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getMes().toLowerCase().contains(mes.toLowerCase())) {
                resultado.add(pago);
            }
        }
        return resultado;
    }

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

    public ArrayList<PagosMensuales> obtenerTodos() {
        return new ArrayList<>(listaPagosMensuales);
    }

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

    public double calcularTotalPagadoMesAño(String mes, int año) {
        double total = 0.0;
        ArrayList<PagosMensuales> pagosMes = buscarPorMesYAño(mes, año);
        for (PagosMensuales pago : pagosMes) {
            total += pago.getPagoTotal();
        }
        return total;
    }

    public double calcularTotalBeneficios() {
        double total = 0.0;
        for (PagosMensuales pago : listaPagosMensuales) {
            total += pago.getTotalBeneficio();
        }
        return total;
    }

    public double calcularTotalDeduccionesSeguro() {
        double total = 0.0;
        for (PagosMensuales pago : listaPagosMensuales) {
            total += pago.getDeduccionSeguro();
        }
        return total;
    }

    public double calcularTotalDeduccionesRenta() {
        double total = 0.0;
        for (PagosMensuales pago : listaPagosMensuales) {
            total += pago.getDeduccionRenta();
        }
        return total;
    }

    public int obtenerTamaño() {
        return listaPagosMensuales.size();
    }

    public int obtenerSiguienteId() {
        int maxId = 0;
        for (PagosMensuales pago : listaPagosMensuales) {
            if (pago.getIdPago() > maxId) {
                maxId = pago.getIdPago();
            }
        }
        return maxId + 1;
    }

    public boolean eliminarPagosDeEstudiante(String cedula) {
        try {
            listaPagosMensuales.removeIf(pago -> pago.getEstudiante().equals(cedula));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void limpiarTodos() {
        listaPagosMensuales.clear();
    }
}
