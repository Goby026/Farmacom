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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gaby
 */
public final class Flujo_caja extends javax.swing.JFrame {

    int posx = 0, posy = 0;
    Connection con = new Conexion().conectar();
    DefaultTableModel model_diario;
    MyiReportVisor mrv;
    HashMap parametros = new HashMap();

    public Flujo_caja() {
        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        cargarTitulos();
//        Color c = new Color(0,0,1,0.06f);
//        panel_principal.setBackground(c);
//        setBackground(c);
        lbl_pie.setText(new Farma_inf().pie());
    }

    public void cargarTitulos() {
        String titulos[] = {"VENTA", "FECHA", "HORA", "TRABAJADOR"};
        model_diario = new DefaultTableModel(null, titulos);
        tbl_diario.setModel(model_diario);
        String fecha = new ManejadorFechas().getFechaActualMySQL();
        cargarTabla(fecha);
        //flujoDia(fecha);
    }

    public void cargarTabla(String fecha) {
        limpiarTabla();
        String datos[] = new String[4];
        String sql = "SELECT tventa.id_venta, tventa.fecha_venta, tventa.hora_venta, tusuario.nom_usu FROM `tventa` INNER JOIN tusuario on tventa.id_usu = tusuario.id_usu WHERE `fecha_venta`= '" + fecha + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_venta");
                datos[1] = rs.getString("fecha_venta");
                datos[2] = rs.getString("hora_venta");
                datos[3] = rs.getString("tusuario.nom_usu");
                model_diario.addRow(datos);
            }
            tbl_diario.setModel(model_diario);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    public double calcularIngresos(String fecha) {
        double ing = 0.0;
        String sql = "SELECT SUM(tdetalleventa.sub_total) FROM tventa INNER JOIN tdetalleventa on tventa.id_venta = tdetalleventa.id_venta WHERE tventa.fecha_venta = '" + fecha + "' ";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                ing = ing + (rs.getDouble("SUM(tdetalleventa.sub_total)"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return new Farma_inf().Redondear(ing);
    }

//    public int flujoDia(String fecActual){
//        int opc = 0;
//        double ingresos = 0.0;
//        String datos[] = new String[4];
//        String sql = "SELECT `id`, `fecha`, `hora_venta`, `id_usu`, `id_cli` FROM `venta`";
//        try {
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            while (rs.next()) {
//                if (rs.getString("fecha").equals(fecActual)) {
//                    opc = opc+1;
//                    int filas = tbl_diario.getRowCount();
//                    txt_ventas_diario.setText(""+filas);
//                } else {
//                }
//                txt_ventas_diario.setText(""+opc);
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
//        }
//        
//        return opc;
//    }
    public double getGastos(String fecha) {
        double opc = 0.0;
        String sql = "SELECT SUM(`monto`) FROM `tgastos` WHERE `fecha_gasto` = '" + fecha + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                opc = rs.getDouble("SUM(`monto`)");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return new Farma_inf().Redondear(opc);
    }

    public void getBalance() {
        double ingresos = Double.parseDouble(txt_ingresos_diario.getText());
        double gastos = Double.parseDouble(txt_gastos_diario.getText());
        double balance = new Farma_inf().Redondear(ingresos - gastos);
        txt_balance_diario.setText("" + balance);
    }

    public void limpiarTabla() {
        for (int i = 0; i < tbl_diario.getRowCount(); i++) {
            model_diario.removeRow(i);
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

        panel_titulo = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        lbl_salir = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panel_principal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_diario = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_ventas_diario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_ingresos_diario = new javax.swing.JTextField();
        txt_gastos_diario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_balance_diario = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btn_revisar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jdc_fecha = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_titulo.setBackground(new java.awt.Color(0, 0, 102));
        panel_titulo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panel_tituloMouseDragged(evt);
            }
        });
        panel_titulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel_tituloMousePressed(evt);
            }
        });
        panel_titulo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("FLUJO DE CAJA");
        panel_titulo.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

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
        panel_titulo.add(lbl_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, -1, -1));

        getContentPane().add(panel_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 70));

        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 102));

        panel_principal.setBackground(new java.awt.Color(255, 255, 255));
        panel_principal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 204), 2));
        panel_principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_diario.setForeground(new java.awt.Color(0, 102, 255));
        tbl_diario.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_diario);

        panel_principal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 530, 320));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("FECHA");
        panel_principal.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 19, -1, 30));

        txt_ventas_diario.setEditable(false);
        txt_ventas_diario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel_principal.add(txt_ventas_diario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 110, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("INGRESOS");
        panel_principal.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        txt_ingresos_diario.setEditable(false);
        txt_ingresos_diario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panel_principal.add(txt_ingresos_diario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 110, -1));

        txt_gastos_diario.setEditable(false);
        txt_gastos_diario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_gastos_diario.setForeground(new java.awt.Color(204, 0, 0));
        panel_principal.add(txt_gastos_diario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 110, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("GASTOS");
        panel_principal.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        txt_balance_diario.setEditable(false);
        txt_balance_diario.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        panel_principal.add(txt_balance_diario, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 283, 140, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("BALANCE");
        panel_principal.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        btn_revisar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_revisar.setText("REVISAR");
        btn_revisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_revisarActionPerformed(evt);
            }
        });
        panel_principal.add(btn_revisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 120, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("TOTAL VENTAS");
        panel_principal.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jdc_fecha.setForeground(new java.awt.Color(0, 0, 153));
        jdc_fecha.setDateFormatString("yyyy/MM/dd");
        jdc_fecha.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        panel_principal.add(jdc_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 19, 160, 30));

        jLabel3.setBackground(new java.awt.Color(0, 0, 102));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("VENTAS EL DÍA DE HOY");
        panel_principal.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, -1));

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setText("CREAR REPORTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panel_principal.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, -1, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("ver gastos");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        panel_principal.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, -1, -1));

        jTabbedPane1.addTab("DIARIO", panel_principal);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 850, 420));

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_pie.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("jLabel7");
        jPanel1.add(lbl_pie, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 760, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 850, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_revisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_revisarActionPerformed
        String fecha = new ManejadorFechas().getFechaActualMySQL();
        //lbl_getFecha.setText(fecha);
        try {
            //String formato = jdc_fecha.getDateFormatString();
            Date date = jdc_fecha.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ff = String.valueOf(sdf.format(date));
            cargarTabla(ff);
            int filas = tbl_diario.getRowCount();
            txt_ventas_diario.setText("" + filas);
            txt_ingresos_diario.setText("" + calcularIngresos(ff));
            txt_gastos_diario.setText("" + getGastos(ff));
            getBalance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), "Fecha no valida", "Error..!!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_revisarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (!txt_ventas_diario.getText().trim().isEmpty()) {
            Date date = jdc_fecha.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String ff = String.valueOf(sdf.format(date));
            double total = Double.parseDouble(txt_balance_diario.getText());
            parametros.put("total", total);
            parametros.put("fecha", ff);
            mrv = new MyiReportVisor(System.getProperty("user.dir") + "\\reportes\\ventadiaria.jrxml", parametros);
            mrv.exportarAPdf();
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "Seleccione una fecha, a continuacion presione REVISAR");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void panel_tituloMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_tituloMousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_panel_tituloMousePressed

    private void panel_tituloMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel_tituloMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x - posx, point.y - posy);
    }//GEN-LAST:event_panel_tituloMouseDragged

    private void lbl_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_salirMouseClicked
        dispose();
        //new Administracion().setVisible(true);
    }//GEN-LAST:event_lbl_salirMouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        new Gastos().setVisible(true);
    }//GEN-LAST:event_jLabel7MouseClicked

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
            java.util.logging.Logger.getLogger(Flujo_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Flujo_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Flujo_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Flujo_caja.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Flujo_caja().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_revisar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdc_fecha;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_salir;
    private javax.swing.JPanel panel_principal;
    private javax.swing.JPanel panel_titulo;
    private javax.swing.JTable tbl_diario;
    private javax.swing.JTextField txt_balance_diario;
    private javax.swing.JTextField txt_gastos_diario;
    private javax.swing.JTextField txt_ingresos_diario;
    private javax.swing.JTextField txt_ventas_diario;
    // End of variables declaration//GEN-END:variables
}
