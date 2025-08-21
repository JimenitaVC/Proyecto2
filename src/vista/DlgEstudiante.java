package vista;

import datos.AlmacenamientoCarreras;
import datos.AlmacenamientoEstudiante;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import logica.Carreras;
import logica.Estudiante;

/**
 *
 * @author monse
 */
public class DlgEstudiante extends javax.swing.JDialog {

    private ResultSet TablaBD;
    private DefaultTableModel TablaJT;
    private AlmacenamientoEstudiante almacenamientoEstudiante;
    private AlmacenamientoCarreras almacenamientoCarreras;
    private boolean modoEdicion = false;
    private String cedulaOriginal = "";

    /**
     * Creates new form DlgEstudiante
     */
    public DlgEstudiante(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        almacenamientoEstudiante = AlmacenamientoEstudiante.getInstance();
        almacenamientoCarreras = AlmacenamientoCarreras.getInstance();

        TablaJT = (DefaultTableModel) JtEstudiantes.getModel();
        cargarCarrerasEnCombo();
        actualizarTabla();
        configurarListenerTabla();
        habilitarBotones(true, false);
    }

    private void cargarCarrerasEnCombo() {
        try {
            cmbCarrera.removeAllItems();
            cmbCarrera.addItem("Seleccione una carrera");

            ArrayList<Carreras> listaCarreras = almacenamientoCarreras.obtenerTodas();
            for (Carreras carrera : listaCarreras) {
                cmbCarrera.addItem(carrera.getIdCarrera() + " - " + carrera.getNomCarrera());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar carreras: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarListenerTabla() {
        JtEstudiantes.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                if (!evt.getValueIsAdjusting()) {
                    int filaSeleccionada = JtEstudiantes.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        cargarDatosEnCampos(filaSeleccionada);
                        modoEdicion = true;
                        habilitarBotones(false, true);
                    }
                }
            }
        });
    }

    private void cargarDatosEnCampos(int fila) {
        try {
            String cedula = (String) TablaJT.getValueAt(fila, 0);
            Estudiante estudiante = almacenamientoEstudiante.buscarPorCedula(cedula);

            if (estudiante != null) {
                txtCedula.setText(estudiante.getCedula());
                txtNombre.setText(estudiante.getNombre());
                if (estudiante.getFechNac() != null) {
                    dtFecha_Nacimiento.setDate(estudiante.getFechNac().getTime());
                }
                txtDireccion.setText(estudiante.getDireccion());
                txtTelefono.setText(estudiante.getTelefono());
                txtEmail.setText(estudiante.getEmail());
                txtCarnet.setText(estudiante.getCarnet());
                if (estudiante.getFechIngreso() != null) {
                    dtFechIngreso.setDate(estudiante.getFechIngreso().getTime());
                }
                if (estudiante.getFechEgreso() != null) {
                    dtfechEgreso.setDate(estudiante.getFechEgreso().getTime());
                }

                int carreraId = estudiante.getCarrera();
                for (int i = 0; i < cmbCarrera.getItemCount(); i++) {
                    String item = cmbCarrera.getItemAt(i).toString();
                    if (item.startsWith(carreraId + " - ")) {
                        cmbCarrera.setSelectedIndex(i);
                        break;
                    }
                }

                Carreras carrera = almacenamientoCarreras.buscarPorId(carreraId);
                if (carrera != null) {
                    txtNombreCarrera.setText(carrera.getNomCarrera());
                }

                cedulaOriginal = cedula;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void habilitarBotones(boolean insertar, boolean actualizar) {
        btnInsertar.setEnabled(insertar);
        btnActualizar.setEnabled(actualizar);
        txtCedula.setEnabled(insertar);
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtCarnet.setText("");
        txtNombreCarrera.setText("");

        dtFecha_Nacimiento.setDate(null);
        dtFechIngreso.setDate(null);
        dtfechEgreso.setDate(null);

        cmbCarrera.setSelectedIndex(0);

        txtCedula.requestFocus();
        modoEdicion = false;
        cedulaOriginal = "";
        habilitarBotones(true, false);
        JtEstudiantes.clearSelection();
    }

    private void agregarFilaEstudiante(Estudiante estudiante) {
        Object[] fila = new Object[10];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        fila[0] = estudiante.getCedula();
        fila[1] = estudiante.getNombre();
        fila[2] = estudiante.getFechNac() != null ? sdf.format(estudiante.getFechNac().getTime()) : "";
        fila[3] = estudiante.getDireccion();
        fila[4] = estudiante.getTelefono();
        fila[5] = estudiante.getEmail();
        fila[6] = estudiante.getCarnet();
        fila[7] = estudiante.getFechIngreso() != null ? sdf.format(estudiante.getFechIngreso().getTime()) : "";
        fila[8] = estudiante.getFechEgreso() != null ? sdf.format(estudiante.getFechEgreso().getTime()) : "";

        Carreras carrera = almacenamientoCarreras.buscarPorId(estudiante.getCarrera());
        fila[9] = carrera != null ? carrera.getNomCarrera() : "Carrera no encontrada";

        TablaJT.addRow(fila);
    }

    private void actualizarTabla() {
        try {
            TablaJT.setRowCount(0);
            ArrayList<Estudiante> listaEstudiantes = almacenamientoEstudiante.obtenerTodos();

            for (Estudiante estudiante : listaEstudiantes) {
                Object[] fila = new Object[10];

                fila[0] = estudiante.getCedula();
                fila[1] = estudiante.getNombre();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                fila[2] = estudiante.getFechNac() != null ? sdf.format(estudiante.getFechNac().getTime()) : "";

                fila[3] = estudiante.getDireccion();
                fila[4] = estudiante.getTelefono();
                fila[5] = estudiante.getEmail();
                fila[6] = estudiante.getCarnet();

                fila[7] = estudiante.getFechIngreso() != null ? sdf.format(estudiante.getFechIngreso().getTime()) : "";

                fila[8] = estudiante.getFechEgreso() != null ? sdf.format(estudiante.getFechEgreso().getTime()) : "";

                Carreras carrera = almacenamientoCarreras.buscarPorId(estudiante.getCarrera());
                fila[9] = carrera != null ? carrera.getNomCarrera() : "Carrera no encontrada";

                TablaJT.addRow(fila);
            }

            JtEstudiantes.revalidate();
            JtEstudiantes.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar la tabla: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        if (txtCedula.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La cédula es obligatoria",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtCedula.requestFocus();
            return false;
        }

        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return false;
        }

        if (txtCarnet.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carnet es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtCarnet.requestFocus();
            return false;
        }

        if (cmbCarrera.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una carrera",
                    "Error", JOptionPane.ERROR_MESSAGE);
            cmbCarrera.requestFocus();
            return false;
        }

        if (dtFecha_Nacimiento.getDate() == null) {
            JOptionPane.showMessageDialog(this, "La fecha de nacimiento es obligatoria",
                    "Error", JOptionPane.ERROR_MESSAGE);
            dtFecha_Nacimiento.requestFocus();
            return false;
        }

        if (dtFechIngreso.getDate() == null) {
            JOptionPane.showMessageDialog(this, "La fecha de ingreso es obligatoria",
                    "Error", JOptionPane.ERROR_MESSAGE);
            dtFechIngreso.requestFocus();
            return false;
        }

        String email = txtEmail.getText().trim();
        if (!email.isEmpty() && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "El formato del email no es válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        return true;
    }

    private int extraerIdCarrera(String itemCombo) {
        try {
            if (itemCombo != null && itemCombo.contains(" - ")) {
                return Integer.parseInt(itemCombo.split(" - ")[0]);
            }
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    // MÉTODOS DE LOS BOTONES
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblBuscar = new javax.swing.JLabel();
        btnInsertar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        txtBusqueda = new javax.swing.JTextField();
        lblCarnet = new javax.swing.JLabel();
        lblFechaIngreso = new javax.swing.JLabel();
        lblFechaEgreso = new javax.swing.JLabel();
        lblCarrera = new javax.swing.JLabel();
        dtFechIngreso = new com.toedter.calendar.JDateChooser();
        dtfechEgreso = new com.toedter.calendar.JDateChooser();
        txtNombreCarrera = new javax.swing.JTextField();
        cmbCarrera = new javax.swing.JComboBox<>();
        txtCarnet = new javax.swing.JTextField();
        lblCarnet2 = new javax.swing.JLabel();
        lblCarnet3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        lblCarnet1 = new javax.swing.JLabel();
        dtFecha_Nacimiento = new com.toedter.calendar.JDateChooser();
        txtCedula = new javax.swing.JTextField();
        lblCarnet4 = new javax.swing.JLabel();
        lblCarnet5 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        lblFechaIngreso1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtEstudiantes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblBuscar.setText("Buscar:");

        btnInsertar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/insertar.png"))); // NOI18N
        btnInsertar.setText("Insertar");
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/editar.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        txtBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBusquedaKeyReleased(evt);
            }
        });

        lblCarnet.setText("Carnet:");

        lblFechaIngreso.setText("fechIngreso:");

        lblFechaEgreso.setText("fechEgreso:");

        lblCarrera.setText("Carrera:");

        txtNombreCarrera.setEditable(false);

        lblCarnet2.setText("Fecha_N:");

        lblCarnet3.setText("Direccion:");

        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        lblCarnet1.setText("Cedula:");

        lblCarnet4.setText("Nombre:");

        lblCarnet5.setText("Telefono:");

        lblFechaIngreso1.setText("Email:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFechaIngreso1)
                    .addComponent(lblCarnet5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtfechEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtFechIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCarnet, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCarnet2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCarnet3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCarnet4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCarnet1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtFecha_Nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(186, 186, 186)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblCarrera)
                                .addGap(221, 221, 221))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lblFechaEgreso)
                                .addGap(221, 221, 221))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblCarnet)
                                    .addComponent(lblFechaIngreso))
                                .addGap(221, 221, 221))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103)
                        .addComponent(btnInsertar)
                        .addGap(18, 18, 18)
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminar)
                        .addGap(16, 16, 16))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnActualizar)
                    .addComponent(btnInsertar)
                    .addComponent(lblBuscar)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCarnet1)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCarnet)
                    .addComponent(txtCarnet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCarnet4)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaIngreso))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCarnet2)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblFechaEgreso)
                                .addComponent(dtFecha_Nacimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dtFechIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dtfechEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCarnet3)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCarrera)
                    .addComponent(cmbCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCarnet5)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaIngreso1)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67))
        );

        JtEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cedula", "Nombre", "Fecha_N", "Direccion", "Telefono", "Email", "Carnet", "FechIngreso", "FechEgreso", "Carrera"
            }
        ));
        jScrollPane2.setViewportView(JtEstudiantes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        try {
            if (!modoEdicion || cedulaOriginal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante de la tabla para actualizar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!validarCampos()) {
                return;
            }
            Calendar fechNac = Calendar.getInstance();
            fechNac.setTime(dtFecha_Nacimiento.getDate());

            Calendar fechIngreso = Calendar.getInstance();
            fechIngreso.setTime(dtFechIngreso.getDate());

            Calendar fechEgreso = null;
            if (dtfechEgreso.getDate() != null) {
                fechEgreso = Calendar.getInstance();
                fechEgreso.setTime(dtfechEgreso.getDate());
            }

            int carreraId = extraerIdCarrera(cmbCarrera.getSelectedItem().toString());

            Estudiante estudianteActualizado = new Estudiante(
                    txtCarnet.getText().trim(),
                    fechIngreso,
                    fechEgreso,
                    carreraId,
                    txtCedula.getText().trim(),
                    txtNombre.getText().trim(),
                    fechNac,
                    txtDireccion.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtEmail.getText().trim()
            );

            boolean actualizado = almacenamientoEstudiante.modificar(cedulaOriginal, estudianteActualizado);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Estudiante actualizado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: No se pudo actualizar el estudiante. Verifique que la cédula exista.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        try {
            if (!modoEdicion || cedulaOriginal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un estudiante de la tabla para eliminar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar este estudiante?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (opcion == JOptionPane.YES_OPTION) {
                boolean eliminado = almacenamientoEstudiante.eliminar(cedulaOriginal);

                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Estudiante eliminado exitosamente",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Error: No se pudo eliminar el estudiante.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        // TODO add your handling code here:
        try {
            if (!validarCampos()) {
                return;
            }
            Calendar fechNac = Calendar.getInstance();
            fechNac.setTime(dtFecha_Nacimiento.getDate());

            Calendar fechIngreso = Calendar.getInstance();
            fechIngreso.setTime(dtFechIngreso.getDate());

            Calendar fechEgreso = null;
            if (dtfechEgreso.getDate() != null) {
                fechEgreso = Calendar.getInstance();
                fechEgreso.setTime(dtfechEgreso.getDate());
            }

            int carreraId = extraerIdCarrera(cmbCarrera.getSelectedItem().toString());

            Estudiante nuevoEstudiante = new Estudiante(
                    txtCarnet.getText().trim(),
                    fechIngreso,
                    fechEgreso,
                    carreraId,
                    txtCedula.getText().trim(),
                    txtNombre.getText().trim(),
                    fechNac,
                    txtDireccion.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtEmail.getText().trim()
            );

            boolean guardado = almacenamientoEstudiante.insertar(nuevoEstudiante);

            if (guardado) {
                JOptionPane.showMessageDialog(this, "Estudiante guardado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error: Ya existe un estudiante con esa cédula o carnet",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void txtBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBusquedaKeyReleased

        String datoBusqueda = txtBusqueda.getText().trim();

        try {
            TablaJT.setRowCount(0);

            if (datoBusqueda.isEmpty()) {
                ArrayList<Estudiante> todos = almacenamientoEstudiante.obtenerTodos();
                for (Estudiante est : todos) {
                    agregarFilaEstudiante(est);
                }
            } else {
                ArrayList<Estudiante> resultados = new ArrayList<>();

                Estudiante estCedula = almacenamientoEstudiante.buscarPorCedula(datoBusqueda);
                if (estCedula != null) {
                    resultados.add(estCedula);
                }

                Estudiante estCarnet = almacenamientoEstudiante.buscarPorCarnet(datoBusqueda);
                if (estCarnet != null && !resultados.contains(estCarnet)) {
                    resultados.add(estCarnet);
                }

                ArrayList<Estudiante> estNombre = almacenamientoEstudiante.buscarPorNombre(datoBusqueda);
                for (Estudiante est : estNombre) {
                    if (!resultados.contains(est)) {
                        resultados.add(est);
                    }
                }

                for (Estudiante est : resultados) {
                    agregarFilaEstudiante(est);
                }
            }
            JtEstudiantes.revalidate();
            JtEstudiantes.repaint();

        } catch (Exception ex) {
            System.out.println("Error al realizar la búsqueda: " + ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_txtBusquedaKeyReleased

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

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
            java.util.logging.Logger.getLogger(DlgEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DlgEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DlgEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DlgEstudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgEstudiante dialog = new DlgEstudiante(new javax.swing.JFrame(), true);
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
    private javax.swing.JTable JtEstudiantes;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JComboBox<String> cmbCarrera;
    private com.toedter.calendar.JDateChooser dtFechIngreso;
    private com.toedter.calendar.JDateChooser dtFecha_Nacimiento;
    private com.toedter.calendar.JDateChooser dtfechEgreso;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCarnet;
    private javax.swing.JLabel lblCarnet1;
    private javax.swing.JLabel lblCarnet2;
    private javax.swing.JLabel lblCarnet3;
    private javax.swing.JLabel lblCarnet4;
    private javax.swing.JLabel lblCarnet5;
    private javax.swing.JLabel lblCarrera;
    private javax.swing.JLabel lblFechaEgreso;
    private javax.swing.JLabel lblFechaIngreso;
    private javax.swing.JLabel lblFechaIngreso1;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCarnet;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCarrera;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
