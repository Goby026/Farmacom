/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.Conexion;
import Control.ManejadorFechas;
import Control.MyiReportVisor;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gaby
 */
public final class Stock extends javax.swing.JFrame {

    int posx, posy;
    Connection con = new Conexion().conectar();
    DefaultTableModel table1;
    HashMap parametros = new HashMap();
    MyiReportVisor mrv;

    public Stock() {
        //setUndecorated(true);
        initComponents();
        //setAlwaysOnTop(true);
        txt_buscar.requestFocus();
        this.getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        tbl_productos.setBackground(Color.WHITE);
        String fechaActual;
        cargarTitulos();
        cargarProductos();
        llenarComboLaboratorios();
    }

    public void cargarTitulos() {
        String[] titulos = {"CÓDIGO", "NOMBRE", "CONCENTRACIÓN", "PRESENTACIÓN", "STOCK", "PRECIO"};
        table1 = new DefaultTableModel(null, titulos);
        tbl_productos.setModel(table1);
        lbl_fecha.setText("" + new Fechas().fechaCadena());
        lbl_pie.setText(new Farma_inf().pie());
        tbl_productos.getColumnModel().getColumn(0).setPreferredWidth(10);
        tbl_productos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbl_productos.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_productos.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbl_productos.getColumnModel().getColumn(4).setPreferredWidth(50);
        tbl_productos.getColumnModel().getColumn(5).setPreferredWidth(50);
    }

    public void cargarProductos() {
        String datos[] = new String[6];
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`, `concentracion_pro_medi`, `presentacion_pro_medi`,`stock_pro_medi`, prec_venta FROM `tproducto_medicamento` WHERE stock_pro_medi<=40";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("stock_pro_medi");
                datos[5] = rs.getString("prec_venta");
                table1.addRow(datos);
            }
            tbl_productos.setModel(table1);
            int filas = tbl_productos.getRowCount();
            txt_registros.setText("" + filas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void llenarComboLaboratorios() {
        String datos[] = new String[1];
        String sql = "select nombre from laboratorio";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("nombre");
                cmbLaboratorio.addItem(datos[0]);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void buscarProductos() {
        limpiarTabla();
        String art = txt_buscar.getText();
        String datos[] = new String[6];
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`, `concentracion_pro_medi`, `presentacion_pro_medi`,`stock_pro_medi` FROM `tproducto_medicamento`, prec_venta WHERE nom_pro_medi LIKE '" + art + "%' OR nom_pro_medi LIKE '%" + art + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("stock_pro_medi");
                datos[5] = rs.getString("prec_venta");
                table1.addRow(datos);
            }
            tbl_productos.setModel(table1);
            int filas = tbl_productos.getRowCount();
            txt_registros.setText("" + filas);
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void mostrarProductosPorLaboratorio(String nomLabo) {
        limpiarTabla();
        String datos[] = new String[6];
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`, `concentracion_pro_medi`, `presentacion_pro_medi`,`stock_pro_medi`,prec_venta FROM `tproducto_medicamento` WHERE proveedor = '" + nomLabo + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("stock_pro_medi");
                datos[5] = rs.getString("prec_venta");
                table1.addRow(datos);
                tbl_productos.setModel(table1);
                int filas = tbl_productos.getRowCount();
                txt_registros.setText("" + filas);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void limpiarTabla() {
        for (int i = 0; i < tbl_productos.getRowCount(); i++) {
            table1.removeRow(i);
            i -= 1;
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

        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_productos = new javax.swing.JTable();
        txt_buscar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();
        cmbLaboratorio = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbl_fecha = new javax.swing.JLabel();
        btn_reporte = new javax.swing.JButton();
        txt_registros = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("STOCK");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel8.setText("SALIR");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 10, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 60));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 880, 10));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 880, 10));

        tbl_productos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbl_productos.setForeground(new java.awt.Color(0, 102, 255));
        tbl_productos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_productos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 860, 270));

        txt_buscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_buscar.setForeground(new java.awt.Color(0, 102, 255));
        txt_buscar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_buscar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });
        getContentPane().add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 290, -1));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("LABORATORIO");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, -1, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("LISTA DE STOCK POR PRODUCTOS");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jPanel1.setBackground(new java.awt.Color(0, 153, 204));

        lbl_pie.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("CADENA DE BOTICAS FARMACOM                                    HOSPITAL REGIONAL ### EL TAMBO - HUANCAYO - PERU");
        jPanel1.add(lbl_pie);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 880, 30));

        cmbLaboratorio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cmbLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLaboratorioActionPerformed(evt);
            }
        });
        getContentPane().add(cmbLaboratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 120, 140, -1));

        jLabel10.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("BUSCAR:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("STOCK DE PRODUCTOS");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbl_fecha.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_fecha.setForeground(new java.awt.Color(0, 0, 102));
        lbl_fecha.setText("Viernes, 24 de Diciembre del 2015");
        jPanel2.add(lbl_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 160, -1));

        btn_reporte.setText("REPORTE DE EXISTENCIAS");
        btn_reporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporteActionPerformed(evt);
            }
        });
        jPanel2.add(btn_reporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 60, -1, -1));

        txt_registros.setEditable(false);
        txt_registros.setBackground(new java.awt.Color(153, 153, 153));
        txt_registros.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_registros.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_registros.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_registros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_registrosKeyReleased(evt);
            }
        });
        jPanel2.add(txt_registros, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 30, 80, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("REGISTROS");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 880, 410));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_registrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_registrosKeyReleased

    }//GEN-LAST:event_txt_registrosKeyReleased

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        buscarProductos();
    }//GEN-LAST:event_txt_buscarKeyReleased

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x - posx, point.y - posy);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void cmbLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLaboratorioActionPerformed
        String nomLabo = cmbLaboratorio.getSelectedItem().toString();
        mostrarProductosPorLaboratorio(nomLabo);
    }//GEN-LAST:event_cmbLaboratorioActionPerformed

    private void btn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteActionPerformed
        int numFilas = tbl_productos.getRowCount();
        double valor = 0.0;
        for (int i = 0; i < numFilas; i++) {
            valor += Double.parseDouble(tbl_productos.getValueAt(i, 5).toString());
        }
        String nomLabo = cmbLaboratorio.getSelectedItem().toString();
        parametros.put("valor", valor);
        parametros.put("nomLabo", nomLabo);
        mrv = new MyiReportVisor(System.getProperty("user.dir") + "\\reportes\\stock.jrxml", parametros);
        mrv.exportarAPdf();
    }//GEN-LAST:event_btn_reporteActionPerformed

    public double valorizacion() {
        double valor = 0.0;
        double precVenta = 0.0;
        int idProd = 0;
        String nombre = null;

        int numFilas = tbl_productos.getRowCount();

        for (int i = 0; i < numFilas; i++) {
            idProd = Integer.parseInt(tbl_productos.getValueAt(i, 0).toString());
            nombre = tbl_productos.getValueAt(i, 1).toString();
            precVenta = new Farma_inf().getPrecioProducto(nombre, idProd);
            valor += precVenta;
        }
        return valor;
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
            java.util.logging.Logger.getLogger(Stock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Stock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Stock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Stock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Stock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_reporte;
    private javax.swing.JComboBox cmbLaboratorio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbl_fecha;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JTable tbl_productos;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_registros;
    // End of variables declaration//GEN-END:variables
}
