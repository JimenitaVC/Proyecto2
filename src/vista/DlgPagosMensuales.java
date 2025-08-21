package vista;

import datos.AlmacenamientoEstudiante;
import datos.AlmacenamientoBeneficios;
import datos.AlmacenamientoBeneficiosEstudiantes;
import datos.AlmacenamientoPagosMensuales;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import logica.Estudiante;
import logica.Beneficios;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logica.PagosMensuales;

/**
 * Diálogo para la gestión de pagos mensuales de estudiantes.
 * Permite registrar pagos, aplicar beneficios y generar reportes por mes.
 * Se conecta con las clases de almacenamiento para obtener y mostrar datos.
 * 
 * @author monse
 * @author Jimena
 * @author Yerson
 */
public class DlgPagosMensuales extends javax.swing.JDialog {

    private ResultSet TablaBD;
    private DefaultTableModel TablaJT;
    private AlmacenamientoPagosMensuales almacenamientoPagosMensuales;
    private AlmacenamientoEstudiante almacenamientoEstudiantes;
    private AlmacenamientoBeneficios almacenamientoBeneficios;
    private AlmacenamientoBeneficiosEstudiantes almacenamientoBeneficiosEstudiantes;
    private boolean modoEdicion = false;
    private int idPagoOriginal = -1;
    private DecimalFormat formatoMoneda = new DecimalFormat("#,###.00");

    /**
     * Creates new form DlgPagosMensuales
     * Constructor del diálogo. Inicializa componentes, carga datos y configura la interfaz.
     * @param parent Ventana padre.
     * @param modal Indica si el diálogo es modal.
     */
    public DlgPagosMensuales(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        almacenamientoPagosMensuales = AlmacenamientoPagosMensuales.getInstance();
        almacenamientoEstudiantes = AlmacenamientoEstudiante.getInstance();
        almacenamientoBeneficios = AlmacenamientoBeneficios.getInstance();
        almacenamientoBeneficiosEstudiantes = AlmacenamientoBeneficiosEstudiantes.getInstance();

        TablaJT = (DefaultTableModel) JtPagos.getModel();
        configurarComponentes();
        configurarComboMesesReporte();
        cargarComboBoxes();
        actualizarTabla();
        configurarListenerTabla();
        habilitarBotones(true, false);

        txtFecha_Hora_actual.setText(LocalDate.now().toString());
        txtFecha_Hora_actual.setEditable(false);

        limpiarCampos();
    }
    /**
 * Configura los componentes visuales del formulario.
 * Inicializa los combos de meses, beneficios y deducciones.
 * Desactiva campos que no deben ser editables directamente.
 */

    private void configurarComponentes() {
        cmbMeses.removeAllItems();
        cmbMeses.addItem("Seleccionar...");
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String mes : meses) {
            cmbMeses.addItem(mes);
        }
        cmbMonto_Beneficio_Estudiante.removeAllItems();
        cmbMonto_Beneficio_Estudiante.addItem("Seleccionar estudiante primero...");
        cmbSeguro_Menos10.removeAllItems();
        cmbSeguro_Menos10.addItem("10%");
        cmbSeguro_Menos10.setEnabled(false);
        cmbDeduccion_Menos5.removeAllItems();
        cmbDeduccion_Menos5.addItem("5%");
        cmbDeduccion_Menos5.setEnabled(false);
        txtTotal_Pagar.setEditable(false);
    }
    /**
 * Configura el combo box utilizado para generar reportes por mes.
 * Carga los nombres de los meses en orden.
 */

    private void configurarComboMesesReporte() {
        cmbMeses_reporte.removeAllItems();
        cmbMeses_reporte.addItem("Seleccionar mes...");
        String[] mesesReporte = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (String mes : mesesReporte) {
            cmbMeses_reporte.addItem(mes);
        }
    }
    /**
 * Habilita o deshabilita los botones según el modo de operación.
 * También desactiva la edición del campo de ID de pago.
 *
 * @param insertar true para habilitar el modo de inserción.
 * @param actualizar true para habilitar el modo de actualización.
 */

    private void habilitarBotones(boolean insertar, boolean actualizar) {
        txtId_Pago.setEditable(false);
        txtId_Pago.setFocusable(false);
    }
    /**
 * Carga las cédulas de estudiantes en el combo box.
 * También configura los listeners para cargar beneficios y calcular el total automáticamente.
 */

    private void cargarComboBoxes() {
        cmbCedula_Estudiante.removeAllItems();
        cmbCedula_Estudiante.addItem("Seleccionar...");

        for (Estudiante est : almacenamientoEstudiantes.obtenerTodos()) {
            cmbCedula_Estudiante.addItem(est.getCedula() + " - " + est.getNombre());
        }
        cmbCedula_Estudiante.addActionListener(e -> cargarBeneficiosDelEstudiante());
        cmbMonto_Beneficio_Estudiante.addActionListener(e -> calcularTotal());
    }
    /**
 * Carga los beneficios asignados al estudiante seleccionado en el combo box.
 * Si el estudiante no tiene beneficios, se muestra un mensaje indicativo.
 * Si ocurre un error, se muestra un mensaje de error en consola y en el combo.
 */

    private void cargarBeneficiosDelEstudiante() {
        cmbMonto_Beneficio_Estudiante.removeAllItems();

        if (cmbCedula_Estudiante.getSelectedIndex() == 0) {
            cmbMonto_Beneficio_Estudiante.addItem("Seleccionar estudiante primero...");
            return;
        }
        try {
            String estudianteSeleccionado = cmbCedula_Estudiante.getSelectedItem().toString();
            String cedula = estudianteSeleccionado.split(" - ")[0];
            ArrayList<Integer> beneficiosDelEstudiante = almacenamientoBeneficiosEstudiantes.obtenerBeneficiosDeEstudiante(cedula);
            if (beneficiosDelEstudiante.isEmpty()) {
                cmbMonto_Beneficio_Estudiante.addItem("No tiene beneficios asignados");
                cmbMonto_Beneficio_Estudiante.setEnabled(false);
            } else {
                cmbMonto_Beneficio_Estudiante.addItem("Seleccionar beneficio...");
                cmbMonto_Beneficio_Estudiante.setEnabled(true);
                for (Integer idBeneficio : beneficiosDelEstudiante) {
                    Beneficios beneficio = almacenamientoBeneficios.buscarPorId(idBeneficio);
                    if (beneficio != null) {
                        String itemBeneficio = beneficio.getNomBeneficio() + " - "
                                + beneficio.getMontoBeneficio();
                        cmbMonto_Beneficio_Estudiante.addItem(itemBeneficio);
                    }
                }
            }
        } catch (Exception ex) {
            cmbMonto_Beneficio_Estudiante.addItem("Error al cargar beneficios");
            cmbMonto_Beneficio_Estudiante.setEnabled(false);
            System.out.println("Error al cargar beneficios: " + ex.getMessage());
        }
    }
    /**
 * Actualiza la tabla de pagos mensuales con todos los registros disponibles.
 * Utiliza el método mostrarEnTabla para renderizar los datos.
 */

    private void actualizarTabla() {
        mostrarEnTabla(almacenamientoPagosMensuales.obtenerTodos());
    }
    /**
 * Muestra una lista de pagos mensuales en la tabla.
 * Formatea los montos con separadores de miles y dos decimales.
 *
 * @param lista Lista de objetos PagosMensuales a mostrar.
 */

    private void mostrarEnTabla(ArrayList<PagosMensuales> lista) {
        TablaJT.setRowCount(0);
        for (PagosMensuales pago : lista) {
            TablaJT.addRow(new Object[]{
                pago.getIdPago(),
                pago.getFechaCreacionFormateada(),
                pago.getMes(),
                pago.getFechaPagoFormateada(),
                pago.getEstudiante(),
                formatoMoneda.format(pago.getTotalBeneficio()),
                formatoMoneda.format(pago.getDeduccionSeguro()),
                formatoMoneda.format(pago.getDeduccionRenta()),
                formatoMoneda.format(pago.getPagoTotal())
            });
        }
    }
    /**
 * Limpia todos los campos del formulario y prepara el siguiente ID de pago.
 * Restablece los combos, fechas y campos de texto a su estado inicial.
 * También desactiva el modo edición y limpia la selección de la tabla.
 */

    private void limpiarCampos() {
        int siguienteId = 1;
        ArrayList<PagosMensuales> lista = almacenamientoPagosMensuales.obtenerTodos();
        if (!lista.isEmpty()) {
            int maxId = lista.stream().mapToInt(PagosMensuales::getIdPago).max().orElse(0);
            siguienteId = maxId + 1;
        }

        txtId_Pago.setText(String.valueOf(siguienteId));
        txtFecha_Hora_actual.setText(LocalDate.now().toString());
        cmbMeses.setSelectedIndex(0);
        dtFecha_Pago.setDate(null);
        cmbCedula_Estudiante.setSelectedIndex(0);
        cmbMonto_Beneficio_Estudiante.removeAllItems();
        cmbMonto_Beneficio_Estudiante.addItem("Seleccionar estudiante primero...");
        txtTotal_Pagar.setText("");

        idPagoOriginal = -1;
        JtPagos.clearSelection();
        habilitarBotones(true, false);
    }
    /**
 * Configura el listener para detectar la selección de una fila en la tabla de pagos.
 * Al seleccionar una fila, se carga el pago correspondiente en el formulario.
 * También se habilita el botón de actualización.
 */

    private void configurarListenerTabla() {
        JtPagos.getSelectionModel().addListSelectionListener(e -> {
            int fila = JtPagos.getSelectedRow();
            if (fila != -1 && fila < TablaJT.getRowCount()) {
                try {
                    int idPago = Integer.parseInt(TablaJT.getValueAt(fila, 0).toString());
                    PagosMensuales pago = almacenamientoPagosMensuales.buscarPorId(idPago);

                    if (pago != null) {
                        cargarDatosEnFormulario(pago);
                        habilitarBotones(false, true);
                    }
                } catch (Exception ex) {
                    System.out.println("Error al cargar datos de la fila: " + ex.getMessage());
                }
            }
        });
    }
    /**
 * Carga los datos de un objeto PagosMensuales en el formulario.
 * Asigna valores a los campos de texto, combos y fecha según el pago seleccionado.
 *
 * @param pago Objeto PagosMensuales con los datos a mostrar.
 */

    private void cargarDatosEnFormulario(PagosMensuales pago) {
        txtId_Pago.setText(String.valueOf(pago.getIdPago()));
        txtFecha_Hora_actual.setText(pago.getFechaCreacionFormateada());
        cmbMeses.setSelectedItem(pago.getMes());
        Date fechaPago = pago.getFechaPago().getTime();
        dtFecha_Pago.setDate(fechaPago);

        String cedula = pago.getEstudiante();
        for (int i = 0; i < cmbCedula_Estudiante.getItemCount(); i++) {
            String item = cmbCedula_Estudiante.getItemAt(i).toString();
            if (item.startsWith(cedula)) {
                cmbCedula_Estudiante.setSelectedIndex(i);
                break;
            }
        }

        String beneficioEstudiante = obtenerNombreBeneficioPorMonto(pago.getTotalBeneficio(), pago.getEstudiante());
        if (!beneficioEstudiante.isEmpty()) {
            for (int i = 0; i < cmbMonto_Beneficio_Estudiante.getItemCount(); i++) {
                String item = cmbMonto_Beneficio_Estudiante.getItemAt(i).toString();
                if (item.contains(beneficioEstudiante)) {
                    cmbMonto_Beneficio_Estudiante.setSelectedIndex(i);
                    break;
                }
            }
        }

        txtTotal_Pagar.setText(formatoMoneda.format(pago.getPagoTotal()));
        idPagoOriginal = pago.getIdPago();
    }
    /**
 * Busca el nombre del beneficio asignado a un estudiante según el monto recibido.
 * Compara el monto con los beneficios registrados para la cédula indicada.
 *
 * @param monto Monto del beneficio aplicado.
 * @param cedula Cédula del estudiante.
 * @return Nombre del beneficio si se encuentra, o cadena vacía si no hay coincidencia.
 */

    private String obtenerNombreBeneficioPorMonto(double monto, String cedula) {
        try {
            ArrayList<Integer> beneficiosDelEstudiante = almacenamientoBeneficiosEstudiantes.obtenerBeneficiosDeEstudiante(cedula);

            for (Integer idBeneficio : beneficiosDelEstudiante) {
                Beneficios beneficio = almacenamientoBeneficios.buscarPorId(idBeneficio);
                if (beneficio != null && beneficio.getMontoBeneficio() == monto) {
                    return beneficio.getNomBeneficio();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar beneficio: " + e.getMessage());
        }
        return "";
    }
    /**
 * Calcula el total a pagar aplicando deducciones al beneficio seleccionado.
 * Se descuentan el 10% por seguro y el 5% por renta del monto del beneficio.
 * Si no hay beneficio válido seleccionado, se muestra "0.00" como total.
 * En caso de error, también se muestra "0.00" y se imprime el mensaje en consola.
 */

    private void calcularTotal() {
        try {
            if (cmbMonto_Beneficio_Estudiante.getSelectedIndex() > 0
                    && !cmbMonto_Beneficio_Estudiante.getSelectedItem().toString().contains("Seleccionar")
                    && !cmbMonto_Beneficio_Estudiante.getSelectedItem().toString().contains("No tiene")) {

                String beneficioSeleccionado = cmbMonto_Beneficio_Estudiante.getSelectedItem().toString();
                String[] partes = beneficioSeleccionado.split(" - ");
                if (partes.length >= 2) {
                    double montoBeneficio = Double.parseDouble(partes[1]);
                    double seguro = montoBeneficio * 0.10; 
                    double renta = montoBeneficio * 0.05;  
                    double pagoNeto = montoBeneficio - (seguro + renta); 
                    txtTotal_Pagar.setText(formatoMoneda.format(pagoNeto));
                }
            } else {
                txtTotal_Pagar.setText("0.00"); 
            }
        } catch (Exception e) {
            txtTotal_Pagar.setText("0.00");
            System.out.println("Error al calcular total: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        dtFecha_Pago = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        lblMonto = new javax.swing.JLabel();
        cmbCedula_Estudiante = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cmbDeduccion_Menos5 = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        cmbMonto_Beneficio_Estudiante = new javax.swing.JComboBox<>();
        cmbSeguro_Menos10 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtTotal_Pagar = new javax.swing.JTextField();
        txtId_Pago = new javax.swing.JTextField();
        txtFecha_Hora_actual = new javax.swing.JTextField();
        cmbMeses = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtPagos = new javax.swing.JTable();
        lblBuscar = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        cmbMeses_reporte = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtAño_reporte = new javax.swing.JTextField();
        btnGenerar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnGuardar.setText("Generar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel11.setText("idPago");

        jLabel12.setText("fechCreacion");

        jLabel13.setText("mes");

        jLabel14.setText("fechaPago");

        lblMonto.setText("Cedula");

        cmbCedula_Estudiante.setToolTipText("");

        jLabel16.setText("totalBeneficios");

        cmbDeduccion_Menos5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "5%" }));
        cmbDeduccion_Menos5.setToolTipText("");

        jLabel17.setText("deducSeguro");

        cmbMonto_Beneficio_Estudiante.setToolTipText("");

        cmbSeguro_Menos10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "10%" }));
        cmbSeguro_Menos10.setToolTipText("");

        jLabel18.setText("deducRenta");

        jLabel19.setText("pagoNeto");

        txtFecha_Hora_actual.setEditable(false);

        cmbMeses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre", " " }));
        cmbMeses.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFecha_Hora_actual, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMeses, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtId_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(28, 28, 28)
                        .addComponent(dtFecha_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMonto)
                        .addGap(47, 47, 47)
                        .addComponent(cmbCedula_Estudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cmbDeduccion_Menos5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmbSeguro_Menos10, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotal_Pagar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbMonto_Beneficio_Estudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(cmbMonto_Beneficio_Estudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel19)
                                    .addComponent(txtTotal_Pagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbSeguro_Menos10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17))
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbDeduccion_Menos5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtId_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtFecha_Hora_actual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbMeses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(27, 27, 27)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMonto)
                                    .addComponent(cmbCedula_Estudiante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(dtFecha_Pago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 159, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28))))
        );

        jLabel2.setText("Generar pago mensual");

        jLabel3.setText("Mostrar pago de un mes");

        jLabel10.setText("Todos los registros de pagos");

        JtPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "idPago", "fechCreacion", "mes", "fechaPago", "estudiante", "totalBeneficios", "deducSeguro", "deducRenta", "pagoNeto"
            }
        ));
        jScrollPane2.setViewportView(JtPagos);

        lblBuscar.setText("Buscar:");

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setText("mes");

        cmbMeses_reporte.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio ", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre ", "Diciembre", " " }));
        cmbMeses_reporte.setToolTipText("");
        cmbMeses_reporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMeses_reporteActionPerformed(evt);
            }
        });

        jLabel20.setText("Año");

        btnGenerar.setText("Generar");
        btnGenerar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbMeses_reporte, 0, 110, Short.MAX_VALUE)
                    .addComponent(txtAño_reporte))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(113, Short.MAX_VALUE)
                .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMeses_reporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtAño_reporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(281, 281, 281)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 402, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176))
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBuscar))
                        .addGap(176, 176, 176)))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
 * Acción del botón "Guardar".
 * Valida los campos del formulario y registra un nuevo pago mensual.
 * Verifica que no exista un pago duplicado para el mismo estudiante en el mismo mes/año.
 * Muestra mensajes de éxito o error según el resultado.
 *
 * @param evt Evento de acción generado por el botón.
 */

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling xcode here:
        try {
            if (cmbCedula_Estudiante.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un estudiante", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cmbMeses.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un mes", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dtFecha_Pago.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha de pago", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cmbMonto_Beneficio_Estudiante.getSelectedIndex() == 0
                    || cmbMonto_Beneficio_Estudiante.getSelectedItem().toString().contains("Seleccionar")
                    || cmbMonto_Beneficio_Estudiante.getSelectedItem().toString().contains("No tiene")
                    || cmbMonto_Beneficio_Estudiante.getSelectedItem().toString().contains("Error")) {
                JOptionPane.showMessageDialog(this, "Seleccione un beneficio válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String estudianteSeleccionado = cmbCedula_Estudiante.getSelectedItem().toString();
            String cedula = estudianteSeleccionado.split(" - ")[0];
            String mes = cmbMeses.getSelectedItem().toString();

            Calendar fechaPago = Calendar.getInstance();
            fechaPago.setTime(dtFecha_Pago.getDate());

            String beneficioSeleccionado = cmbMonto_Beneficio_Estudiante.getSelectedItem().toString();
            String[] partes = beneficioSeleccionado.split(" - ");
            String montoStr = partes[partes.length - 1].replace(",", "");
            double monto = Double.parseDouble(montoStr);

            PagosMensuales nuevoPago = new PagosMensuales(mes, fechaPago, cedula, monto);

            AlmacenamientoPagosMensuales almacenamientoPagos = AlmacenamientoPagosMensuales.getInstance();

            if (almacenamientoPagos.insertar(nuevoPago)) {
                JOptionPane.showMessageDialog(this, "Pago registrado con éxito");
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Ya existe un pago para este estudiante en este mes/año", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed
    /**
 * Evento que se dispara al liberar una tecla en el campo de búsqueda.
 * Filtra los pagos mensuales por cédula del estudiante, mes o año de la fecha de pago.
 * Si el campo está vacío, se muestran todos los pagos.
 *
 * @param evt Evento de teclado generado por el campo de búsqueda.
 */

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased
        String datoBusqueda = txtBusqueda.getText().trim().toLowerCase(); 
        ArrayList<PagosMensuales> resultados = new ArrayList<>();

        if (datoBusqueda.isEmpty()) {
            resultados = almacenamientoPagosMensuales.obtenerTodos();
        } else {
            Integer año = null;
            try {
                año = Integer.parseInt(datoBusqueda);
            } catch (NumberFormatException e) {
                // no es número, ignorar
            }

            for (PagosMensuales pago : almacenamientoPagosMensuales.obtenerTodos()) {
                boolean coincide = false;

                if (pago.getEstudiante().toLowerCase().contains(datoBusqueda)) {
                    coincide = true;
                }
                else if (pago.getMes().toLowerCase().contains(datoBusqueda)) {
                    coincide = true;
                } 
                else if (año != null && pago.getFechaPago().get(Calendar.YEAR) == año) {
                    coincide = true;
                }

                if (coincide) {
                    resultados.add(pago);
                }
            }
        }

        mostrarEnTabla(resultados);
    }//GEN-LAST:event_txtBusquedaKeyReleased
    /**
 * Acción del botón "Generar".
 * Valida el año y el mes seleccionados, y genera un reporte con los pagos mensuales realizados.
 * Calcula totales de beneficios, deducciones y pagos netos.
 * Muestra un resumen en un cuadro de diálogo o un mensaje si no hay resultados.
 *
 * @param evt Evento de acción generado por el botón.
 */

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        try {
            String textoAno = txtAño_reporte.getText().trim();
            if (textoAno.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un año", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int año;
            try {
                año = Integer.parseInt(textoAno);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El año debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (año < 2000 || año > 2100) {
                JOptionPane.showMessageDialog(this, "El año debe estar entre 2000 y 2100", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cmbMeses_reporte.getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un mes", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String mesSeleccionado = cmbMeses_reporte.getSelectedItem().toString();

            AlmacenamientoPagosMensuales almacenamiento = AlmacenamientoPagosMensuales.getInstance();
            double totalPagado = 0.0;
            double totalBeneficios = 0.0;
            double totalDeduccionesSeguro = 0.0;
            double totalDeduccionesRenta = 0.0;
            int cantidadPagos = 0;

            for (PagosMensuales pago : almacenamiento.obtenerTodos()) {
                int añoPago = pago.getFechaPago().get(Calendar.YEAR);
                String mesPago = pago.getMes();

                if (añoPago == año && mesPago.equalsIgnoreCase(mesSeleccionado)) {
                    totalPagado += pago.getPagoTotal(); 
                    totalBeneficios += pago.getTotalBeneficio();
                    totalDeduccionesSeguro += pago.getDeduccionSeguro();
                    totalDeduccionesRenta += pago.getDeduccionRenta();
                    cantidadPagos++;
                }
            }

            if (cantidadPagos == 0) {
                JOptionPane.showMessageDialog(this,
                        String.format("No se encontraron pagos para %s de %d", mesSeleccionado, año),
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                String mensaje = String.format(
                        "Reporte de %s %d:\n\n"
                        + "Cantidad de pagos: %d\n"
                        + "Total pagado: ₡%s",
                        mesSeleccionado, año, cantidadPagos, formatoMoneda.format(totalPagado)
                );

                JOptionPane.showMessageDialog(this,
                        mensaje,
                        "Total del Mes",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al generar el reporte: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnGenerarActionPerformed
    
    private void cmbMeses_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMeses_reporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMeses_reporteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DlgPagosMensuales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgPagosMensuales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgPagosMensuales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgPagosMensuales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgPagosMensuales dialog = new DlgPagosMensuales(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JtPagos;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbCedula_Estudiante;
    private javax.swing.JComboBox<String> cmbDeduccion_Menos5;
    private javax.swing.JComboBox<String> cmbMeses;
    private javax.swing.JComboBox<String> cmbMeses_reporte;
    private javax.swing.JComboBox<String> cmbMonto_Beneficio_Estudiante;
    private javax.swing.JComboBox<String> cmbSeguro_Menos10;
    private com.toedter.calendar.JDateChooser dtFecha_Pago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JTextField txtAño_reporte;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtFecha_Hora_actual;
    private javax.swing.JTextField txtId_Pago;
    private javax.swing.JTextField txtTotal_Pagar;
    // End of variables declaration//GEN-END:variables
}
