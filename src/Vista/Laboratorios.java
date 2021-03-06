/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gaby
 */
public class Laboratorios extends javax.swing.JFrame {

    DefaultTableModel modelo;

    public Laboratorios() throws SQLException {
        initComponents();
        setLocationRelativeTo(null);
        cargarTabla(tblLaboratorio, 50, 100, 200);
    }

    public void cargarTabla(JTable tabla, int small, int large, int xl) throws SQLException {
        modelo = new DefaultTableModel();
        tabla.setModel(modelo);
        LaboratorioDAO ved = new LaboratorioDAO();

        modelo.addColumn("ID");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("DIRECCION");
        modelo.addColumn("TELEFONO");

        Object[] columna = new Object[4];

        int numeroRegistros = ved.listar().size();

        for (int i = 0; i < numeroRegistros; i++) {
            columna[0] = ved.listar().get(i).getId();
            columna[1] = ved.listar().get(i).getNombre();
            columna[2] = ved.listar().get(i).getDireccion();
            columna[3] = ved.listar().get(i).getTelefono();
            modelo.addRow(columna);
        }

        tabla.getColumnModel().getColumn(0).setPreferredWidth(small);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(large);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(xl);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(small);
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaboratorio = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LABORATORIOS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 60));

        tblLaboratorio.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblLaboratorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLaboratorioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLaboratorio);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, 330));

        jLabel2.setText("TELEFONO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jLabel4.setText("DIRECCION");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        txtTelefono.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(0, 102, 204));
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.setText("0");
        txtTelefono.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        getContentPane().add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 250, -1));

        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(0, 102, 204));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        getContentPane().add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 250, -1));

        txtDireccion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(0, 102, 204));
        txtDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDireccion.setText("0");
        txtDireccion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        getContentPane().add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 250, -1));

        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 250, -1));

        btnNuevo.setText("LIMPIAR");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 250, -1));

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 250, -1));

        btnModificar.setText("MODIFICAR");
        btnModificar.setEnabled(false);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 250, -1));

        jLabel5.setText("NOMBRE");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel6.setText("LISTA DE LABORATORIOS");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("INSTRUCTIVO"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel8.setText("- PULSE EL BOTON \"LIMPIAR\" PARA BORRAR TODOS LOS CAMPOS.");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel3.setText("- SELECCIONE UN LABORATORIO DE LA TABLA PARA MODIFICAR ALGUN CAMPO Y PULSE EL BOTON \"MODIFICAR\" ");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel7.setText("PARA GUARDAR LOS CAMBIOS");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel9.setText("- RELLENE TODOS LOS CAMPOS Y PULSE EL BOTON \"GUARDAR\" PARA REGISTRAR UN NUEVO LABORATORIO");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 720, 130));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiar();
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        tblLaboratorio.clearSelection();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (!txtNombre.getText().trim().isEmpty()) {
                String nombre = txtNombre.getText();
                String direccion = txtDireccion.getText();
                String telefono = txtTelefono.getText();
                Laboratorio lab = new Laboratorio();
                lab.setNombre(nombre);
                lab.setDireccion(direccion);
                lab.setTelefono(telefono);
                LaboratorioDAO ldao = new LaboratorioDAO();
                if (ldao.Registrar(lab)) {
                    JOptionPane.showMessageDialog(getRootPane(), "LABORATORIO REGISTRADO");
                    limpiarTabla();
                    cargarTabla(tblLaboratorio, 50, 100, 200);
                    limpiar();
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "INDIQUE NOMBRE DE LABORATORIO");
                txtNombre.requestFocus();
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblLaboratorioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLaboratorioMouseClicked
        int fila = tblLaboratorio.getSelectedRow();
        if (fila >= 0) {
            btnModificar.setEnabled(true);
            btnEliminar.setEnabled(true);
            txtNombre.setText(tblLaboratorio.getValueAt(fila, 1).toString());
            txtDireccion.setText(tblLaboratorio.getValueAt(fila, 2).toString());
            txtTelefono.setText(tblLaboratorio.getValueAt(fila, 3).toString());
        } else {
            btnModificar.setEnabled(false);
            btnEliminar.setEnabled(false);
        }

    }//GEN-LAST:event_tblLaboratorioMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        try {
            int fila = tblLaboratorio.getSelectedRow();
            int id = Integer.parseInt(tblLaboratorio.getValueAt(fila, 0).toString());
            LaboratorioDAO ldao = new LaboratorioDAO();
            Laboratorio l = new Laboratorio();
            l.setNombre(txtNombre.getText());
            l.setDireccion(txtDireccion.getText());
            l.setTelefono(txtTelefono.getText());
            l.setId(id);
            if (ldao.modificar(l)) {
                JOptionPane.showMessageDialog(getRootPane(), "SE MODIFICARON LOS DATOS");
                limpiar();
                limpiarTabla();
                cargarTabla(tblLaboratorio, 50, 100, 200);
                btnEliminar.setEnabled(false);                
                btnModificar.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "ERROR");
            }

        } catch (Exception ex) {
            ex.getMessage();
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        txtDireccion.requestFocus();
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        txtTelefono.requestFocus();
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        btnGuardar.doClick();
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = tblLaboratorio.getSelectedRow();
        int id = Integer.parseInt(tblLaboratorio.getValueAt(fila, 0).toString());
        if (fila >= 0) {
            try {
                LaboratorioDAO ldao = new LaboratorioDAO();
                Laboratorio l = new Laboratorio();
                l.setId(id);
                if (ldao.eliminar(l)) {
                    JOptionPane.showMessageDialog(getRootPane(), "LABORATORIO ELIMINADO");
                    limpiar();
                    limpiarTabla();
                    cargarTabla(tblLaboratorio, 50, 100, 200);
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "ERROR");
                }
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }else{
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UN LABORATORIO");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

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
            java.util.logging.Logger.getLogger(Laboratorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laboratorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laboratorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laboratorios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Laboratorios().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Laboratorios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLaboratorio;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    public void limpiar() {
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtNombre.requestFocus();
    }

    public void limpiarTabla() {
        for (int i = 0; i < tblLaboratorio.getRowCount(); i++) {
            modelo.removeRow(i);
            i -= 1;
        }
    }
}
