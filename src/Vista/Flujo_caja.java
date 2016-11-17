/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.Conexion;
import Control.FlujoCajaControl;
import Control.ManejadorFechas;
import Control.MyiReportVisor;
import Modelo.Caja;
import Modelo.CajaDAO;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public final class Flujo_caja extends javax.swing.JFrame {

    int posx = 0, posy = 0;
    Connection con = new Conexion().conectar();
    DefaultTableModel model_diario;
    MyiReportVisor mrv;
    HashMap parametros = new HashMap();
    String fechaActual = new ManejadorFechas().getFechaActualMySQL();

    public Flujo_caja() throws Exception {
        try {
            setUndecorated(true);
            initComponents();
            setLocationRelativeTo(null);
            cargarTitulos();
//        Color c = new Color(0,0,1,0.06f);
//        panel_principal.setBackground(c);
//        setBackground(c);
            lblFecha.setText(new ManejadorFechas().getFechaActual());
            lblFecha2.setText(new ManejadorFechas().getFechaActual());
            lbl_pie.setText(new Farma_inf().pie());
            txtMontoApertura.setText("" + new FlujoCajaControl().getSaldoInicial(fechaActual));
            lblEstado.setText(new FlujoCajaControl().getEstadoCaja(fechaActual));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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
        panelApertura = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtSaldoInicial = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaObservaciones = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        btnAperturar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        panelCierre = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMontoVisa = new javax.swing.JTextField();
        txtMontoVentas = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtMontoApertura = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtCompras = new javax.swing.JTextField();
        txtGastos = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtSaldoContabilizado = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lblFecha2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        panelFlujoDiario = new javax.swing.JPanel();
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
        txtVisa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        panel_titulo.add(lbl_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, -1, -1));

        getContentPane().add(panel_titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 70));

        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 102));

        panelApertura.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setText("CAJA");
        panelApertura.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, -1, -1));

        txtSaldoInicial.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        panelApertura.add(txtSaldoInicial, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 130, -1));

        jLabel18.setText("OBSERVACIONES");
        panelApertura.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, -1, -1));

        txaObservaciones.setColumns(20);
        txaObservaciones.setLineWrap(true);
        txaObservaciones.setRows(5);
        jScrollPane3.setViewportView(txaObservaciones);

        panelApertura.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 520, 130));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel19.setText("SALDO INICIAL");
        panelApertura.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, -1, -1));

        lblFecha.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblFecha.setText("_______________");
        panelApertura.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setText("FECHA");
        panelApertura.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        jTextField7.setEditable(false);
        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.setText("CAJA PRINCIPAL");
        panelApertura.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 150, -1));

        btnAperturar.setBackground(new java.awt.Color(51, 153, 0));
        btnAperturar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAperturar.setForeground(new java.awt.Color(255, 255, 255));
        btnAperturar.setText("APERTURAR");
        btnAperturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAperturarActionPerformed(evt);
            }
        });
        panelApertura.add(btnAperturar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, -1, -1));

        btnCancelar.setBackground(new java.awt.Color(255, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btnCancelar.setText("CANCELAR");
        panelApertura.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 370, -1, -1));

        jLabel25.setText("ESTADO CAJA:");
        panelApertura.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel26.setText("________________");
        panelApertura.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        jTabbedPane1.addTab("APERTURA", panelApertura);

        panelCierre.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("ENTRADAS"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setText("VENTAS");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("VISA");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, -1));

        txtMontoVisa.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMontoVisa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtMontoVisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, 120, -1));

        txtMontoVentas.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMontoVentas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtMontoVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 120, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("SALDO INICIAL");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, -1, -1));

        txtMontoApertura.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtMontoApertura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtMontoApertura, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 120, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, -1, -1));

        panelCierre.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 420, 160));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("SALIDAS"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("COMPRAS");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("GASTOS");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, -1));

        txtCompras.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCompras.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(txtCompras, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 120, -1));

        txtGastos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtGastos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(txtGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, 120, -1));

        panelCierre.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 420, 130));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setText("OBSERVACIONES");
        panelCierre.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 270, -1, -1));

        txtSaldoContabilizado.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtSaldoContabilizado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        panelCierre.add(txtSaldoContabilizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, 250, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setText("SALDO CONTABILIZADO");
        panelCierre.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setText("_____");
        panelCierre.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 230, 260, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("DESCUADRE");
        panelCierre.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        panelCierre.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 520, 130));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("REPORTE DE CIERRE");
        panelCierre.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setText("CERRAR CAJA");
        panelCierre.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 440, -1, -1));

        lblFecha2.setText("____________");
        panelCierre.add(lblFecha2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jLabel22.setText("FECHA:");
        panelCierre.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel23.setText("ESTADO CAJA:");
        panelCierre.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        lblEstado.setText("________________");
        panelCierre.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 100, -1));

        jTabbedPane1.addTab("CIERRE", panelCierre);

        panelFlujoDiario.setBackground(new java.awt.Color(255, 255, 255));
        panelFlujoDiario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 204), 2));
        panelFlujoDiario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        panelFlujoDiario.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, 590, 400));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("FECHA");
        panelFlujoDiario.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 30));

        txt_ventas_diario.setEditable(false);
        txt_ventas_diario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panelFlujoDiario.add(txt_ventas_diario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 110, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("INGRESOS");
        panelFlujoDiario.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        txt_ingresos_diario.setEditable(false);
        txt_ingresos_diario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        panelFlujoDiario.add(txt_ingresos_diario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 110, -1));

        txt_gastos_diario.setEditable(false);
        txt_gastos_diario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_gastos_diario.setForeground(new java.awt.Color(204, 0, 0));
        panelFlujoDiario.add(txt_gastos_diario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, 110, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("GASTOS");
        panelFlujoDiario.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        txt_balance_diario.setEditable(false);
        txt_balance_diario.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        panelFlujoDiario.add(txt_balance_diario, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 140, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("DESCUADRE");
        panelFlujoDiario.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, -1, -1));

        btn_revisar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_revisar.setText("REVISAR");
        btn_revisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_revisarActionPerformed(evt);
            }
        });
        panelFlujoDiario.add(btn_revisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 120, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("TOTAL VENTAS");
        panelFlujoDiario.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jdc_fecha.setForeground(new java.awt.Color(0, 0, 153));
        jdc_fecha.setDateFormatString("yyyy/MM/dd");
        jdc_fecha.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        panelFlujoDiario.add(jdc_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 160, 30));

        jLabel3.setBackground(new java.awt.Color(0, 0, 102));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("VENTAS DEL DÍA");
        panelFlujoDiario.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, -1, -1));

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setText("CREAR REPORTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelFlujoDiario.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("ver gastos");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        panelFlujoDiario.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, -1, -1));

        txtVisa.setEditable(false);
        txtVisa.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtVisa.setForeground(new java.awt.Color(204, 0, 0));
        panelFlujoDiario.add(txtVisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 110, -1));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("VISA");
        panelFlujoDiario.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jTabbedPane1.addTab("FLUJO DIARIO", panelFlujoDiario);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 880, 510));

        jPanel1.setBackground(new java.awt.Color(0, 102, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_pie.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("jLabel7");
        jPanel1.add(lbl_pie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 760, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, 880, 30));

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

    private void btnAperturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAperturarActionPerformed
        if (!txtSaldoInicial.getText().trim().isEmpty()) {
            try {
                int flag = 0;
                CajaDAO cdao = new CajaDAO();
                for (Caja c : cdao.listar()) {
                    if (c.getFecha().equals(new ManejadorFechas().getFechaActualMySQL())) {
                        flag++;
                    }
                }
                System.out.println(flag);
                if (flag > 0) {
                    JOptionPane.showMessageDialog(getRootPane(), "YA SE APERTURO LA CAJA EL DIA DE HOY.");
                } else {
                    Caja c = new Caja();
                    c.setMontoApertura(Double.parseDouble(txtSaldoInicial.getText()));
                    c.setMontoCierre(0);
                    c.setFecha(new ManejadorFechas().getFechaActualMySQL());
                    c.setHora(new ManejadorFechas().getHoraActual());
                    c.setObservacion(txaObservaciones.getText());
                    if (cdao.Registrar(c)) {
                        JOptionPane.showMessageDialog(getRootPane(), "SE APERTURO LA CAJA CON S/." + txtSaldoInicial.getText());
                    } else {
                        JOptionPane.showMessageDialog(getRootPane(), "ERROR EN REGISTRO DE APERTURA");
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE UN MONTO INICIAL PARA APERTURAR LA CAJA");
        }
    }//GEN-LAST:event_btnAperturarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            System.out.println(new FlujoCajaControl().getSaldoInicial("2016-11-15"));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
                try {
                    new Flujo_caja().setVisible(true);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAperturar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btn_revisar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField7;
    private com.toedter.calendar.JDateChooser jdc_fecha;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFecha2;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_salir;
    private javax.swing.JPanel panelApertura;
    private javax.swing.JPanel panelCierre;
    private javax.swing.JPanel panelFlujoDiario;
    private javax.swing.JPanel panel_titulo;
    private javax.swing.JTable tbl_diario;
    private javax.swing.JTextArea txaObservaciones;
    private javax.swing.JTextField txtCompras;
    private javax.swing.JTextField txtGastos;
    private javax.swing.JTextField txtMontoApertura;
    private javax.swing.JTextField txtMontoVentas;
    private javax.swing.JTextField txtMontoVisa;
    private javax.swing.JTextField txtSaldoContabilizado;
    private javax.swing.JTextField txtSaldoInicial;
    private javax.swing.JTextField txtVisa;
    private javax.swing.JTextField txt_balance_diario;
    private javax.swing.JTextField txt_gastos_diario;
    private javax.swing.JTextField txt_ingresos_diario;
    private javax.swing.JTextField txt_ventas_diario;
    // End of variables declaration//GEN-END:variables
}
