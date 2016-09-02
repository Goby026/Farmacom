package Vista;

import Control.Conexion;
import Control.ManejadorFechas;
import Control.MyiReportVisor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gaby
 */
public class ReporteDeCompras extends javax.swing.JFrame {

    MyiReportVisor mrv;
    HashMap parametros = new HashMap();
    Connection con = new Conexion().conectar();
    DefaultTableModel modelCompras, modelDetalleCompra, model_produc;

    public ReporteDeCompras() {
        initComponents();
        setLocationRelativeTo(null);
        titulos();
    }

    public void titulos() {
        String titulos[] = {"N° COMPRA", "PROVEEDOR", "DOCUMENTO", "N° SERIE", "N°", "FORMA DE PAGO", "FECHA", "HORA", "ESTADO"};
        String titulos2[] = {"ID", "COD. PROD", "PRODUCTO", "CANTIDAD", "PRECIO", "SUBTOTAL"};
        modelCompras = new DefaultTableModel(null, titulos);
        modelDetalleCompra = new DefaultTableModel(null, titulos2);
        tblCompras.setModel(modelCompras);
        tblDetalleCompras.setModel(modelDetalleCompra);
    }

    public void limpiarTablaCompras() {
        for (int i = 0; i < tblCompras.getRowCount(); i++) {
            modelCompras.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarTablaDetalleCompras() {
        for (int i = 0; i < tblDetalleCompras.getRowCount(); i++) {
            modelDetalleCompra.removeRow(i);
            i -= 1;
        }
    }

    public void cargarRangoCompras() {
        limpiarTablaCompras();
        Date inicio = jdcDesde.getDate();
        Date fin = jdcHasta.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fec_inicio = sdf.format(inicio);
        String fec_fin = sdf.format(fin);
        String sql = "SELECT `id_compra`, `nom_provee`, `tipo_doc`, `num_serie`, `num_doc_compra`, `forma_pago`, `fecha_compra`, `hora_compra`,estado FROM `tcompras` WHERE `fecha_compra` between '" + fec_inicio + "' and '" + fec_fin + "';";
        String datos[] = new String[9];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_compra");
                datos[1] = rs.getString("nom_provee");
                datos[2] = rs.getString("tipo_doc");
                datos[3] = rs.getString("num_serie");
                datos[4] = rs.getString("num_doc_compra");
                datos[5] = rs.getString("forma_pago");
                datos[6] = rs.getString("fecha_compra");
                datos[7] = rs.getString("hora_compra");
                int estado = rs.getInt("estado");
                if (estado == 0) {
                    datos[8] = "ACTIVO";
                } else {
                    datos[8] = "ANULADO";
                }
                modelCompras.addRow(datos);
            }
            tblCompras.setModel(modelCompras);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    public void cargarDetalleCompra(int id) {
        limpiarTablaDetalleCompras();
        String sql = "SELECT tdetalle_compra.id, tproducto_medicamento.id_pro_medi, tproducto_medicamento.nom_pro_medi, tdetalle_compra.cantidad, tdetalle_compra.precio, tdetalle_compra.sub_total FROM tcompras INNER JOIN tdetalle_compra ON tcompras.id_compra = tdetalle_compra.id_compra INNER JOIN tproducto_medicamento ON tproducto_medicamento.id_pro_medi = tdetalle_compra.id_pro_medi WHERE tcompras.id_compra= " + id + "";
        String datos[] = new String[6];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("tdetalle_compra.id");
                datos[1] = rs.getString("tproducto_medicamento.id_pro_medi");
                datos[2] = rs.getString("tproducto_medicamento.nom_pro_medi");
                datos[3] = rs.getString("tdetalle_compra.cantidad");
                datos[4] = rs.getString("tdetalle_compra.precio");
                datos[5] = rs.getString("tdetalle_compra.sub_total");
                modelDetalleCompra.addRow(datos);
            }
            tblDetalleCompras.setModel(modelDetalleCompra);

            tblDetalleCompras.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblDetalleCompras.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblDetalleCompras.getColumnModel().getColumn(2).setPreferredWidth(250);
            tblDetalleCompras.getColumnModel().getColumn(3).setPreferredWidth(50);
            tblDetalleCompras.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblDetalleCompras.getColumnModel().getColumn(5).setPreferredWidth(50);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnMostrar = new javax.swing.JButton();
        jdcHasta = new com.toedter.calendar.JDateChooser();
        jdcDesde = new com.toedter.calendar.JDateChooser();
        btnReporte = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalleCompras = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cmbComprasDelDia = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REPORTES DE COMPRAS");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REPORTES DE COMPRAS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 60));

        tblCompras.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCompras);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 950, 210));

        jLabel2.setText("DESDE");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel3.setText("DETALLES DE COMPRA");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, -1, -1));

        jLabel4.setText("HASTA");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        btnMostrar.setBackground(new java.awt.Color(51, 153, 0));
        btnMostrar.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrar.setText("MOSTRAR");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        getContentPane().add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 180, -1));
        getContentPane().add(jdcHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 160, -1));
        getContentPane().add(jdcDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 160, -1));

        btnReporte.setBackground(new java.awt.Color(204, 102, 0));
        btnReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453245109_distributor-report.png"))); // NOI18N
        btnReporte.setText("CREAR REPORTE");
        btnReporte.setEnabled(false);
        btnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteActionPerformed(evt);
            }
        });
        getContentPane().add(btnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 566, 190, 40));

        tblDetalleCompras.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDetalleCompras);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 950, 210));

        jButton2.setText("VER DETALLE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 280, 130, -1));

        jLabel6.setText("CREAR REPORTE");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, -1, -1));

        jLabel7.setText("1° Seleccione un rango de fechas");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jLabel8.setText("2° Seleccione una compra de la");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        jLabel9.setText("ó presionar ALT + N");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, -1, -1));

        jLabel10.setText("compra seleccionada antes.");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

        jLabel11.setText("tabla superior");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jLabel12.setText("seleccionada pulse sobre");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, -1));

        jLabel13.setText("3° Pulse el boton VER DETALLE");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, -1, -1));

        jLabel14.setText("para visualizar los detalles de la");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        cmbComprasDelDia.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cmbComprasDelDia.setText("COMPRAS DEL DIA");
        cmbComprasDelDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbComprasDelDiaActionPerformed(evt);
            }
        });
        getContentPane().add(cmbComprasDelDia, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 102, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("SELECCIONE PERIODO");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 220, 20));

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        lblTotal.setText("......");
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 530, 150, -1));

        jLabel16.setText("TOTAL GRABADO:");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 530, -1, -1));

        jButton3.setMnemonic('n');
        jButton3.setText("NUEVO REPORTE");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 160, -1));

        jLabel15.setText("4° Si desea un reporte de la compra");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, -1));

        jLabel17.setText("5° Para crear un nuevo reporte ");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, -1, -1));

        jLabel18.setText("clic en el boton NUEVO REPORTE");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        if (jdcDesde.getDate() != null) {
            if (jdcHasta.getDate() != null) {
                cargarRangoCompras();
                limpiarTablaDetalleCompras();
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "INDIQUE FECHA FINAL");
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "INDIQUE FECHA DE INICIO");

        }

    }//GEN-LAST:event_btnMostrarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila = tblCompras.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = Integer.parseInt(tblCompras.getValueAt(fila, 0).toString());
                cargarDetalleCompra(id);
                lblTotal.setText("" + sumarTotalDetalles());
                btnReporte.setEnabled(true);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());                
                btnReporte.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UNA COMPRA");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            this.dispose();
            new ReporteDeCompras().setVisible(true);
            con.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cmbComprasDelDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbComprasDelDiaActionPerformed
        if (cmbComprasDelDia.isSelected()) {
            String fecha = new ManejadorFechas().getFechaActualMySQL();
            cargarComprasDelDia(fecha);
            limpiarTablaDetalleCompras();
        } else {
            limpiarTablaCompras();
            cmbComprasDelDia.setSelected(false);
            limpiarTablaDetalleCompras();
        }
    }//GEN-LAST:event_cmbComprasDelDiaActionPerformed

    private void btnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteActionPerformed
        int fila = tblCompras.getSelectedRow();
        if (fila>=0) {
            int id = Integer.parseInt(tblCompras.getValueAt(fila, 0).toString());//tambien es el numero de compra
            String provee = String.valueOf(tblCompras.getValueAt(fila, 1).toString());
            String doc = String.valueOf(tblCompras.getValueAt(fila, 2).toString());
            String fec = String.valueOf(tblCompras.getValueAt(fila, 6).toString());
            String hor = String.valueOf(tblCompras.getValueAt(fila, 7).toString());
            double total = Double.parseDouble(lblTotal.getText());
            parametros.put("numCompra", id);
            parametros.put("doc", doc);
            parametros.put("fec", fec);
            parametros.put("hor", hor);
            parametros.put("provee", provee);
            parametros.put("total", total);
            parametros.put("id", id);
            mrv = new MyiReportVisor(System.getProperty("user.dir") + "\\reportes\\reporteCompras.jrxml", parametros);
            mrv.exportarAPdf();
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UNA COMPRA");
        }
    }//GEN-LAST:event_btnReporteActionPerformed

    public double sumarTotalDetalles() {
        int numFilas = tblDetalleCompras.getRowCount();
        double total = 0.0;
        for (int i = 0; i < numFilas; i++) {
            //sumar toda la columna 5
            total += Double.parseDouble(tblDetalleCompras.getValueAt(i, 5).toString());
        }
        return total;
    }

    public void cargarComprasDelDia(String fecha) {
        limpiarTablaCompras();
        String sql = "SELECT `id_compra`, `nom_provee`, `tipo_doc`, `num_serie`, `num_doc_compra`, `forma_pago`, `fecha_compra`, `hora_compra`,estado FROM `tcompras` WHERE `fecha_compra` = '" + fecha + "'";
        String datos[] = new String[9];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_compra");
                datos[1] = rs.getString("nom_provee");
                datos[2] = rs.getString("tipo_doc");
                datos[3] = rs.getString("num_serie");
                datos[4] = rs.getString("num_doc_compra");
                datos[5] = rs.getString("forma_pago");
                datos[6] = rs.getString("fecha_compra");
                datos[7] = rs.getString("hora_compra");
                int estado = rs.getInt("estado");
                if (estado == 0) {
                    datos[8] = "ACTIVO";
                } else {
                    datos[8] = "ANULADO";
                }
                modelCompras.addRow(datos);
            }
            tblCompras.setModel(modelCompras);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

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
            java.util.logging.Logger.getLogger(ReporteDeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteDeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteDeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteDeCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReporteDeCompras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton btnReporte;
    private javax.swing.JCheckBox cmbComprasDelDia;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser jdcDesde;
    private com.toedter.calendar.JDateChooser jdcHasta;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTable tblDetalleCompras;
    // End of variables declaration//GEN-END:variables
}
