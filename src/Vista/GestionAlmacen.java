/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.GestionAlmacenControl;
import Control.ManejadorFechas;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gaby
 */
public class GestionAlmacen extends javax.swing.JInternalFrame {

    DefaultTableModel modelProductoAdd;

    public GestionAlmacen() throws Exception {
        initComponents();
        new GestionAlmacenControl().cargarComboAlmacenes(cmbAlmacen);
        txtFecha2.setText(new ManejadorFechas().getFechaActual());
        titulosAdd();
    }

    public void titulosAdd() {
        String[] titulos = {"ALMACEN", "PRODUCTO", "CANTIDAD"};
        modelProductoAdd = new DefaultTableModel(null, titulos);
        tblAgregadosParaMover.setModel(modelProductoAdd);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtNumMovimiento = new javax.swing.JTextField();
        cmbAlmacen = new javax.swing.JComboBox();
        txtProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducto = new javax.swing.JTable();
        txtUnidades = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtFecha2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblAgregadosParaMover = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        txaConcepto = new javax.swing.JTextArea();
        btnAgregar = new javax.swing.JButton();
        cbxTodo = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtIngresoProducto = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtIngresoProducto1 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        txtFecha1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setText("N° MOVIMIENTO");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        txtNumMovimiento.setEditable(false);
        txtNumMovimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtNumMovimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 50, -1));

        jPanel2.add(cmbAlmacen, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 300, -1));

        txtProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProductoKeyReleased(evt);
            }
        });
        jPanel2.add(txtProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 390, -1));

        tblProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblProducto);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 720, 130));

        txtUnidades.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtUnidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 140, -1));

        jPanel5.setBackground(new java.awt.Color(102, 102, 102));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("PRODUCTO");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, -1, -1));

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 390, 20));

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("UNIDADES");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, -1));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 250, 20));

        jPanel7.setBackground(new java.awt.Color(102, 102, 102));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("NOMBRE DE ALMACEN DESTINO");
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 250, 20));

        jPanel8.setBackground(new java.awt.Color(102, 102, 102));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("CONCEPTO");
        jPanel8.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, -1));

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 250, 20));

        jPanel14.setBackground(new java.awt.Color(102, 102, 102));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("FECHA");
        jPanel14.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel2.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 60, 20));
        jPanel2.add(txtFecha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 100, -1));

        jButton4.setText("MOVER PRODUCTOS");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 580, 300, -1));

        tblAgregadosParaMover.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tblAgregadosParaMover);

        jPanel2.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 720, 110));

        txaConcepto.setColumns(20);
        txaConcepto.setLineWrap(true);
        txaConcepto.setRows(5);
        jScrollPane8.setViewportView(txaConcepto);

        jPanel2.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 310, 300, 100));

        btnAgregar.setText("AGREGAR");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel2.add(btnAgregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 420, 300, -1));

        cbxTodo.setText("TODO");
        cbxTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTodoActionPerformed(evt);
            }
        });
        jPanel2.add(cbxTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 280, -1, -1));

        jTabbedPane1.addTab("MOVIMIENTOS", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(51, 102, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("PRODUCTO");
        jPanel10.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel3.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 90, 20));
        jPanel3.add(txtIngresoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 330, 20));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 720, 150));

        jPanel11.setBackground(new java.awt.Color(51, 102, 255));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("FECHA");
        jPanel11.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel3.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 60, 20));
        jPanel3.add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, 100, -1));

        jButton1.setText("SELECCIONAR");
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, 220, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable2);

        jPanel3.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 720, 150));

        jPanel15.setBackground(new java.awt.Color(51, 102, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("CONCEPTO");
        jPanel15.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        jPanel3.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 150, 20));

        jPanel16.setBackground(new java.awt.Color(51, 102, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("UNIDADES");
        jPanel16.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        jPanel3.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 150, 20));
        jPanel3.add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 100, -1));

        jButton5.setText("REGISTRAR INGRESO");
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 590, -1, -1));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane9.setViewportView(jTextArea2);

        jPanel3.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, -1, -1));

        jTabbedPane1.addTab("INGRESOS", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(204, 51, 0));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("PRODUCTO");
        jPanel12.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel4.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 90, 20));
        jPanel4.add(txtIngresoProducto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 350, 20));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        jPanel4.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 670, 130));

        jPanel13.setBackground(new java.awt.Color(204, 51, 0));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("FECHA");
        jPanel13.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jPanel4.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 60, 20));
        jPanel4.add(txtFecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 100, -1));

        jButton3.setText("SELECCIONAR");
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 170, -1));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable4);

        jPanel4.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 500, 150));

        jPanel17.setBackground(new java.awt.Color(204, 51, 0));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("CONCEPTO");
        jPanel17.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        jPanel4.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 150, 20));
        jPanel4.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 520, -1));

        jPanel18.setBackground(new java.awt.Color(204, 51, 0));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("UNIDADES");
        jPanel18.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        jPanel4.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 150, 20));
        jPanel4.add(jTextField12, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 100, -1));

        jButton6.setText("REGISTRAR SALIDA");
        jPanel4.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 400, 150, -1));

        jTabbedPane1.addTab("SALIDAS", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 760, 650));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("MOVIMIENTOS - INGRESOS - SALIDAS");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProductoKeyReleased
        try {
            String prod = txtProducto.getText();
            new GestionAlmacenControl().buscarProductos(tblProducto, prod);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_txtProductoKeyReleased

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        int fila = tblProducto.getSelectedRow();
        if (fila >= 0) {
            if (!txtUnidades.getText().trim().isEmpty()) {
                try {
                    //Object[] datos = new Object[4];
//                    Almacen a = new Almacen();
//                    a.setNombre(cmbAlmacen.getSelectedItem().toString());
//                    int idProd = Integer.parseInt(tblProducto.getValueAt(fila, 0).toString());                    
                    String almacen = cmbAlmacen.getSelectedItem().toString();
                    String producto = tblProducto.getValueAt(fila, 1).toString();
                    int cantidad = Integer.parseInt(txtUnidades.getText());
                    Object[] datos = {almacen, producto, cantidad};
                    modelProductoAdd.addRow(datos);
                    tblAgregadosParaMover.setModel(modelProductoAdd);
//                    datos[0] = new GestionAlmacenControl().getIdAlmacen(a);
//                    datos[1] = Integer.parseInt(tblProducto.getValueAt(fila, 0).toString());
//                    datos[2] = txtUnidades.getText();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "INGRESE UNA CANTIDAD");
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UN PRODUCTO");
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int numFilas = tblAgregadosParaMover.getRowCount();
        if (numFilas >= 0) {
            try {
                new GestionAlmacenControl().registrarProductoAlmacen(tblAgregadosParaMover);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "AGREGUE POR LO MENOS UN MOVIMIENTO DE PRODUCTOS");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void cbxTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTodoActionPerformed
        int fila = tblProducto.getSelectedRow();
        if (fila >= 0) {
            if (cbxTodo.isSelected()) {
                txtUnidades.setText(tblProducto.getValueAt(fila, 3).toString());
            } else {
                txtUnidades.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UN PRODUCTO");
            cbxTodo.setSelected(false);
        }

    }//GEN-LAST:event_cbxTodoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JCheckBox cbxTodo;
    private javax.swing.JComboBox cmbAlmacen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTable tblAgregadosParaMover;
    private javax.swing.JTable tblProducto;
    private javax.swing.JTextArea txaConcepto;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtFecha1;
    private javax.swing.JTextField txtFecha2;
    private javax.swing.JTextField txtIngresoProducto;
    private javax.swing.JTextField txtIngresoProducto1;
    private javax.swing.JTextField txtNumMovimiento;
    private javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtUnidades;
    // End of variables declaration//GEN-END:variables
}
