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
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Gaby
 */
public final class Caducidad_productos extends javax.swing.JFrame {

    int posx, posy;
    Conexion con = new Conexion();
    Connection cc = con.conectar();
    DefaultTableModel table1;
    HashMap parametros = new HashMap();
    MyiReportVisor mrv;
    
    public Caducidad_productos() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        String []cabecera={"CÓDIGO","NOMBRE","CONCENTRACIÓN","PRESENTACIÓN","LABORATORIO","FECHA DE COMPRA","FECHA DE CADUCIDAD","STOCK"};
        table1 = new DefaultTableModel(null, cabecera);
        tbl_productos.setModel(table1);
        tbl_productos.getColumnModel().getColumn(0).setPreferredWidth(10);
        tbl_productos.getColumnModel().getColumn(1).setPreferredWidth(130);
        tbl_productos.getColumnModel().getColumn(2).setPreferredWidth(60);
        tbl_productos.getColumnModel().getColumn(3).setPreferredWidth(100);
        tbl_productos.getColumnModel().getColumn(4).setPreferredWidth(60);
        tbl_productos.getColumnModel().getColumn(5).setPreferredWidth(40);
        tbl_productos.getColumnModel().getColumn(6).setPreferredWidth(40);
        tbl_productos.getColumnModel().getColumn(7).setPreferredWidth(20);
        cargarProductos();
        lbl_pie.setText(new Farma_inf().pie());
    }
    
    public void cargarProductos() {
        String datos[] = new String[8];
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`,`concentracion_pro_medi`, `presentacion_pro_medi`, `provee_labo_pro_medi`,`fec_ingreso_prod` ,`fecha_venc_pro_medi`, `stock_pro_medi` FROM `tproducto_medicamento`";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("provee_labo_pro_medi");
                datos[5] = String.valueOf(rs.getObject("fec_ingreso_prod"));
                datos[6] = rs.getString("fecha_venc_pro_medi");
                //datos[6] = String.valueOf(caduca());
                datos[7] = String.valueOf(rs.getInt("stock_pro_medi"));
                table1.addRow(datos);
            }
            tbl_productos.setModel(table1);
            int filas = tbl_productos.getRowCount();            
            
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
    
    public void caduca(int dias){
        limpiarTabla();
        //capturar fecha de BD
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`,`concentracion_pro_medi`, `presentacion_pro_medi`, `provee_labo_pro_medi`,`fec_ingreso_prod` ,`fecha_venc_pro_medi`, `stock_pro_medi` FROM tproducto_medicamento WHERE TIMESTAMPDIFF(DAY,CURDATE(),`fecha_venc_pro_medi`)<="+dias;
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String []datos = new String[8];
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("provee_labo_pro_medi");
                datos[5] = String.valueOf(rs.getObject("fec_ingreso_prod"));
                datos[6] = rs.getString("fecha_venc_pro_medi");
                //datos[6] = String.valueOf(caduca());
                datos[7] = String.valueOf(rs.getInt("stock_pro_medi"));
                table1.addRow(datos);
            }
        } catch (SQLException | HeadlessException e) {
            System.out.println(e.getMessage());
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

        lbl_salir = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_productos = new javax.swing.JTable();
        lbl_logo = new javax.swing.JLabel();
        cmb_dias = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_reporte = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_salir.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lbl_salir.setForeground(new java.awt.Color(255, 255, 255));
        lbl_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        lbl_salir.setText("SALIR");
        lbl_salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_salirMouseClicked(evt);
            }
        });
        getContentPane().add(lbl_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 10, 70, -1));

        tbl_productos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbl_productos.setForeground(new java.awt.Color(0, 51, 255));
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 197, 1180, 400));

        lbl_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        lbl_logo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lbl_logoMouseDragged(evt);
            }
        });
        lbl_logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_logoMousePressed(evt);
            }
        });
        getContentPane().add(lbl_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        cmb_dias.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cmb_dias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "30 DIAS", "3 MESES", "6 MESES" }));
        cmb_dias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_diasActionPerformed(evt);
            }
        });
        getContentPane().add(cmb_dias, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, 180, -1));

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_pie.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("jLabel3");
        jPanel1.add(lbl_pie, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 1200, 30));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("PRODUCTOS PROXIMOS A CADUCAR");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1460975095_Shipping3.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 70, 70));

        btn_reporte.setText("CREAR REPORTE");
        btn_reporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reporteActionPerformed(evt);
            }
        });
        jPanel2.add(btn_reporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 100, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1200, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_logoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x-posx, point.y-posy);
    }//GEN-LAST:event_lbl_logoMouseDragged

    private void lbl_logoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_lbl_logoMousePressed

    private void cmb_diasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_diasActionPerformed
        if (cmb_dias.getSelectedIndex()==0) {
            caduca(30);
        } else if(cmb_dias.getSelectedIndex()==1){
            caduca(90);
        }else if(cmb_dias.getSelectedIndex()==2){
            caduca(180);
        }
    }//GEN-LAST:event_cmb_diasActionPerformed

    private void lbl_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_salirMouseClicked
        dispose();
    }//GEN-LAST:event_lbl_salirMouseClicked

    private void btn_reporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reporteActionPerformed
        int dias = 30;
        if (cmb_dias.getSelectedIndex() == 0) {
            dias = 30;
        } else if(cmb_dias.getSelectedIndex() == 1){
            dias = 90;
        }else if(cmb_dias.getSelectedIndex() == 2){
            dias = 180;
        }
        parametros.put("dias", dias);
        mrv = new MyiReportVisor(System.getProperty("user.dir") + "\\reportes\\vencimientos.jrxml", parametros);
        mrv.exportarAPdf();
    }//GEN-LAST:event_btn_reporteActionPerformed

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
            java.util.logging.Logger.getLogger(Caducidad_productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Caducidad_productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Caducidad_productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Caducidad_productos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Caducidad_productos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_reporte;
    private javax.swing.JComboBox cmb_dias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_salir;
    private javax.swing.JTable tbl_productos;
    // End of variables declaration//GEN-END:variables
}
