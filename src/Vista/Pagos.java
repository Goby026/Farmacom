
package Vista;

import javax.swing.table.DefaultTableModel;
import Control.Conexion;
import Control.ManejadorFechas;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public final class Pagos extends javax.swing.JFrame {

    int posx, posy;
    DefaultTableModel trabajadores;
    Connection con = new Conexion().conectar();
    String nombres, apellidos, perfil, direccion;
    int dni=0, cod=0;
    
    
    public Pagos() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/IMG/icoono.jpg")).getImage());
        this.getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        txt_concepto.setEnabled(false);
        txt_monto_descuento.setEnabled(false);
        txt_monto.setEditable(false);
        txt_cuenta.setEnabled(false);
        cargarTrabajadores();
    }
    
    
    public void cargarTrabajadores(){
        String []cabeza= {"CODIGO", "NOMBRES", "APELLIDOS","DNI","DIRECCION","PERFIL"};
        trabajadores = new DefaultTableModel(null, cabeza);
        tbl_trabajadores.setModel(trabajadores);
        lbl_fecha.setText(""+ new Fechas().fechaCadena());
        cargarTablaTrabajadores();
        btn_seleccionar.setEnabled(false);
    }
    
    public void cargarTablaTrabajadores(){
        String sql = "SELECT `id_usu`, `nom_usu`, `apell_usu`,`dni_usu`, `direc_usu` , `descrip_perfil` FROM `tusuario` INNER JOIN tperfil ON tusuario.id_perfil = tperfil.id_perfil";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String []datos = new String[6];
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_usu"));
                datos[1] = rs.getString("nom_usu");
                datos[2] = rs.getString("apell_usu");
                datos[3] = rs.getString("dni_usu");
                datos[4] = rs.getString("direc_usu");
                datos[5] = rs.getString("descrip_perfil");
                trabajadores.addRow(datos);
                tbl_trabajadores.setModel(trabajadores);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    public int getTardanza(int id, int mes){
        int min = 0;
        String sql = "select SUM(min_tarde_as) from tasistencia where fecha_as between CONCAT(YEAR(NOW()),'-"+mes+"-1') and CONCAT(YEAR(NOW()),'-"+mes+"-31') and id_usu = "+id+"";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                min = rs.getInt("SUM(min_tarde_as)");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return min;
    }
    
    private int getFaltas(int id, int mes){
        int faltas = 0;
        String sql = "select SUM(faltas_as) from tasistencia where fecha_as between CONCAT(YEAR(NOW()),'-"+mes+"-1') and CONCAT(YEAR(NOW()),'-"+mes+"-31') and id_usu = "+id+"";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                faltas = Integer.parseInt(rs.getString("SUM(faltas_as)"));
            } else {
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return faltas;
    }
    
    public void obtenerTrabajador(int fila){
        try {
            cod = Integer.parseInt(tbl_trabajadores.getValueAt(fila, 0).toString());
            nombres = tbl_trabajadores.getValueAt(fila,1).toString();
            apellidos = tbl_trabajadores.getValueAt(fila,2).toString();
            dni = Integer.parseInt(tbl_trabajadores.getValueAt(fila,3).toString());
            direccion = String.valueOf(tbl_trabajadores.getValueAt(fila,4));
            txt_codigo.setText(""+cod);
            txt_nombres.setText(nombres);
            txt_apellidos.setText(apellidos);
            txt_dni.setText("" + dni);
            txt_direccion.setText(direccion);
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    public void sumarDescuentoTotal(){
        double monto,descuento;
        monto = Double.parseDouble(txt_monto_descuento.getText());
        descuento = Double.parseDouble(lbl_descuento.getText());
        descuento = monto+descuento;
        lbl_descuento.setText(""+descuento);
        txt_monto.setText(""+(new Farma_inf().getSueldoMinimoVital()- descuento));
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frm_buscar = new javax.swing.JDialog();
        jLabel22 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_trabajadores = new javax.swing.JTable();
        btn_seleccionar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        frm_cantidad = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        btn_ok = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        lbl_fecha = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_nombres = new javax.swing.JTextField();
        txt_dni = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        txt_monto = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        txt_direccion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btn_buscar = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        btn_pagos = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        panel_principal = new javax.swing.JPanel();
        jmc_meses = new com.toedter.calendar.JMonthChooser();
        jLabel25 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        lbl_descuento = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_monto_descuento = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_faltas = new javax.swing.JTextField();
        txt_min_tardanza = new javax.swing.JTextField();
        cbx_otros = new javax.swing.JCheckBox();
        txt_concepto = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_cuenta = new javax.swing.JTextField();
        cmb_pago = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        btn_sumar_descuento = new javax.swing.JButton();

        frm_buscar.setBackground(new java.awt.Color(0, 102, 255));
        frm_buscar.setBounds(new java.awt.Rectangle(300, 50, 590, 460));
        frm_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                frm_buscarMouseClicked(evt);
            }
        });
        frm_buscar.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel22.setText("SALIR");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        frm_buscar.getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        frm_buscar.getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, -1));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("LISTA DE TRABAJADORES");
        frm_buscar.getContentPane().add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        tbl_trabajadores.setForeground(new java.awt.Color(0, 0, 204));
        tbl_trabajadores.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_trabajadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_trabajadoresMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_trabajadoresMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_trabajadores);

        frm_buscar.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 590, 240));

        btn_seleccionar.setBackground(new java.awt.Color(0, 153, 255));
        btn_seleccionar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_seleccionar.setForeground(new java.awt.Color(255, 255, 255));
        btn_seleccionar.setText("SELECCIONAR");
        btn_seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seleccionarActionPerformed(evt);
            }
        });
        frm_buscar.getContentPane().add(btn_seleccionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 220, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 2, true));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 606, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );

        frm_buscar.getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 610, 350));

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        frm_buscar.getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 610, 20));

        frm_cantidad.setBounds(new java.awt.Rectangle(450, 100, 225, 275));
        frm_cantidad.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton1.setText("9");
        frm_cantidad.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 40, 40));

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton2.setText("1");
        frm_cantidad.getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 40, 40));

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton3.setText("2");
        frm_cantidad.getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 40, 40));

        jButton4.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton4.setText("3");
        frm_cantidad.getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 40, 40));

        jButton5.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton5.setText("7");
        frm_cantidad.getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 40, 40));

        jButton6.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton6.setText("8");
        frm_cantidad.getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 40, 40));

        jButton7.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton7.setText("<");
        frm_cantidad.getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 50, 40));

        jButton8.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton8.setText("4");
        frm_cantidad.getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 40, 40));

        jButton9.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton9.setText("5");
        frm_cantidad.getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 40, 40));

        jButton10.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton10.setText("6");
        frm_cantidad.getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 40, 40));

        jButton11.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jButton11.setText("0");
        frm_cantidad.getContentPane().add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 140, 40));

        btn_ok.setBackground(new java.awt.Color(51, 255, 51));
        btn_ok.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        btn_ok.setForeground(new java.awt.Color(255, 255, 255));
        btn_ok.setText("ok");
        frm_cantidad.getContentPane().add(btn_ok, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 50, 140));

        jTextField1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        frm_cantidad.getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel14.setText("SALIR");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
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
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 60));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 560, 610, 10));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("DIRECCION");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        lbl_fecha.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_fecha.setForeground(new java.awt.Color(0, 0, 102));
        lbl_fecha.setText("FECHA:----------------------------------");
        getContentPane().add(lbl_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, 180, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 0, 0));
        jLabel4.setText("MONTO A PAGAR");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, -1, -1));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("CODIGO");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, -1, -1));

        txt_nombres.setEditable(false);
        txt_nombres.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nombres.setForeground(new java.awt.Color(0, 51, 204));
        txt_nombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombres.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        getContentPane().add(txt_nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 230, -1));

        txt_dni.setEditable(false);
        txt_dni.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dni.setForeground(new java.awt.Color(0, 51, 204));
        txt_dni.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dni.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        getContentPane().add(txt_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, 110, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 610, 10));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 0));
        jLabel8.setText("DATOS DE EMPLEADO");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        txt_monto.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        txt_monto.setForeground(new java.awt.Color(0, 0, 102));
        txt_monto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_monto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        getContentPane().add(txt_monto, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 590, 150, -1));

        txt_apellidos.setEditable(false);
        txt_apellidos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_apellidos.setForeground(new java.awt.Color(0, 51, 204));
        txt_apellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_apellidos.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        getContentPane().add(txt_apellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, 230, -1));

        txt_direccion.setEditable(false);
        txt_direccion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_direccion.setForeground(new java.awt.Color(0, 51, 204));
        txt_direccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_direccion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        getContentPane().add(txt_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 440, -1));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("NOMBRES");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        btn_buscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_buscar.setText("BUSCAR");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, -1));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 610, 10));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 0, 0));
        jLabel20.setText("CONFORMACION DE PAGO");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setForeground(new java.awt.Color(0, 102, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 610, -1));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("APELLIDOS");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        btn_pagos.setBackground(new java.awt.Color(0, 153, 204));
        btn_pagos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_pagos.setForeground(new java.awt.Color(255, 255, 255));
        btn_pagos.setText("REGISTRAR PAGO");
        btn_pagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pagosActionPerformed(evt);
            }
        });
        getContentPane().add(btn_pagos, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 590, 250, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("DNI");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 160, -1, -1));

        txt_codigo.setEditable(false);
        txt_codigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_codigo.setForeground(new java.awt.Color(0, 51, 204));
        txt_codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_codigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        getContentPane().add(txt_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 130, 110, -1));

        panel_principal.setBackground(new java.awt.Color(255, 255, 255));
        panel_principal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 2, true));
        panel_principal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel_principal.add(jmc_meses, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 160, -1, -1));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("MES");
        panel_principal.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        jButton12.setBackground(new java.awt.Color(0, 204, 51));
        jButton12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("REVISAR");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        panel_principal.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 270, -1));
        panel_principal.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 610, 10));

        jLabel26.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 102));
        jLabel26.setText("S/.");
        panel_principal.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, -1, -1));

        lbl_descuento.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lbl_descuento.setForeground(new java.awt.Color(0, 0, 102));
        lbl_descuento.setText("..");
        panel_principal.add(lbl_descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 340, 80, -1));

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("TOTAL DESCUENTO:");
        panel_principal.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, -1, -1));

        txt_monto_descuento.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_monto_descuento.setForeground(new java.awt.Color(0, 51, 204));
        txt_monto_descuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_monto_descuento.setText("0");
        txt_monto_descuento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        txt_monto_descuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_monto_descuentoKeyTyped(evt);
            }
        });
        panel_principal.add(txt_monto_descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 150, -1));

        jLabel24.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("MONTO");
        panel_principal.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 300, -1, -1));

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("FALTAS");
        panel_principal.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 240, -1, -1));

        txt_faltas.setEditable(false);
        txt_faltas.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_faltas.setForeground(new java.awt.Color(0, 51, 204));
        txt_faltas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_faltas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        panel_principal.add(txt_faltas, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 240, 150, -1));

        txt_min_tardanza.setEditable(false);
        txt_min_tardanza.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_min_tardanza.setForeground(new java.awt.Color(0, 51, 204));
        txt_min_tardanza.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_min_tardanza.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        panel_principal.add(txt_min_tardanza, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 150, -1));

        cbx_otros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_otrosActionPerformed(evt);
            }
        });
        panel_principal.add(cbx_otros, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, -1, -1));

        txt_concepto.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_concepto.setForeground(new java.awt.Color(0, 51, 204));
        txt_concepto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_concepto.setText("SIN CONCEPTO");
        txt_concepto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        txt_concepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_conceptoActionPerformed(evt);
            }
        });
        txt_concepto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_conceptoKeyTyped(evt);
            }
        });
        panel_principal.add(txt_concepto, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 150, -1));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("CONCEPTO");
        panel_principal.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, -1, -1));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("OTROS:");
        panel_principal.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("MIN. TARDANZA");
        panel_principal.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 0, 0));
        jLabel12.setText("DESCUENTOS");
        panel_principal.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("FORMA DE PAGO");
        panel_principal.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 420, -1, -1));

        txt_cuenta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_cuenta.setForeground(new java.awt.Color(0, 102, 255));
        txt_cuenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cuenta.setText("0");
        txt_cuenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        panel_principal.add(txt_cuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 460, 360, -1));

        cmb_pago.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cmb_pago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CONTADO", "DEPOSITO", "CHEQUE" }));
        cmb_pago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_pagoActionPerformed(evt);
            }
        });
        panel_principal.add(cmb_pago, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 410, 150, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("n° cuenta");
        panel_principal.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 460, -1, -1));

        btn_sumar_descuento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1461037554_7.png"))); // NOI18N
        btn_sumar_descuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sumar_descuentoActionPerformed(evt);
            }
        });
        panel_principal.add(btn_sumar_descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 296, 30, 30));

        getContentPane().add(panel_principal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 610, 570));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        frm_buscar.dispose();
    }//GEN-LAST:event_jLabel22MouseClicked

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        frm_buscar.setVisible(true);
        frm_buscar.setAlwaysOnTop(true);
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void tbl_trabajadoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_trabajadoresMouseClicked
        int filas= tbl_trabajadores.getSelectedRow()+1;
        if (filas>0) {
            btn_seleccionar.setEnabled(true);            
        }else{
            
        }
    }//GEN-LAST:event_tbl_trabajadoresMouseClicked

    private void tbl_trabajadoresMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_trabajadoresMousePressed
        
    }//GEN-LAST:event_tbl_trabajadoresMousePressed

    private void frm_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_frm_buscarMouseClicked
        btn_seleccionar.setEnabled(false);
    }//GEN-LAST:event_frm_buscarMouseClicked

    private void btn_seleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seleccionarActionPerformed
        int fila = tbl_trabajadores.getSelectedRow();
        
        obtenerTrabajador(fila);
        frm_buscar.dispose();
    }//GEN-LAST:event_btn_seleccionarActionPerformed

    private void cbx_otrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_otrosActionPerformed
        if (cbx_otros.isSelected()) {
            txt_concepto.setEnabled(true);
            txt_monto_descuento.setEnabled(true);
            txt_concepto.requestFocus();
        } else {
            txt_concepto.setEnabled(false);
            txt_monto_descuento.setEnabled(false);
        }
    }//GEN-LAST:event_cbx_otrosActionPerformed

    private void cmb_pagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_pagoActionPerformed
        if (cmb_pago.getSelectedIndex()==1) {
            txt_cuenta.setEnabled(true);
            txt_cuenta.requestFocus();
        } else {
            txt_cuenta.setEnabled(false);
        }
    }//GEN-LAST:event_cmb_pagoActionPerformed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x-posx, point.y-posy);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void btn_pagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pagosActionPerformed
        if (!txt_nombres.getText().trim().isEmpty()) {
            if (!txt_min_tardanza.getText().trim().isEmpty()) {
                int cod = Integer.parseInt(txt_codigo.getText());
                int mes = jmc_meses.getMonth() + 1;
                int estado = verificarSiSePago(cod, mes);
                if (estado == 1) {
                    JOptionPane.showMessageDialog(getRootPane(), "Ya se realizó el pago del mes seleccionado");
                } else {
                    registrarPago();
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Presione el boton REVISAR");
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "Seleccione un usuario");
        }
        
        
    }//GEN-LAST:event_btn_pagosActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        if (!txt_nombres.getText().trim().isEmpty()) {
            int cod = Integer.parseInt(txt_codigo.getText());
            int mes = jmc_meses.getMonth() + 1;
            int min = getTardanza(cod, mes);
            int faltas = getFaltas(cod, mes);
            txt_min_tardanza.setText("" + min);
            txt_faltas.setText("" + faltas);
            calcularMontoDescuentos();
            calcularTotalPagar();
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UN USUARIO");
        }
        
    }//GEN-LAST:event_jButton12ActionPerformed

    private void txt_conceptoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_conceptoKeyTyped
        int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE SOLO LETRAS");
            txt_concepto.requestFocus();
        }
    }//GEN-LAST:event_txt_conceptoKeyTyped

    private void txt_conceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_conceptoActionPerformed
        txt_monto_descuento.requestFocus();
    }//GEN-LAST:event_txt_conceptoActionPerformed

    private void txt_monto_descuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_monto_descuentoKeyTyped
      int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE SOLO NUMEROS");
            txt_monto_descuento.requestFocus();
        }else{
            
        
        }
    }//GEN-LAST:event_txt_monto_descuentoKeyTyped

    private void btn_sumar_descuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sumar_descuentoActionPerformed
        sumarDescuentoTotal();
        cbx_otros.setEnabled(true);
        cbx_otros.setSelected(false);
        btn_sumar_descuento.setEnabled(false);
        txt_concepto.setEnabled(false);
        txt_monto_descuento.setEnabled(false);
        
        
    }//GEN-LAST:event_btn_sumar_descuentoActionPerformed

   
    
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
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pagos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_ok;
    private javax.swing.JButton btn_pagos;
    private javax.swing.JButton btn_seleccionar;
    private javax.swing.JButton btn_sumar_descuento;
    private javax.swing.JCheckBox cbx_otros;
    private javax.swing.JComboBox cmb_pago;
    private javax.swing.JDialog frm_buscar;
    private javax.swing.JDialog frm_cantidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField jTextField1;
    private com.toedter.calendar.JMonthChooser jmc_meses;
    private javax.swing.JLabel lbl_descuento;
    private javax.swing.JLabel lbl_fecha;
    private javax.swing.JPanel panel_principal;
    private javax.swing.JTable tbl_trabajadores;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_concepto;
    private javax.swing.JTextField txt_cuenta;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_dni;
    private javax.swing.JTextField txt_faltas;
    private javax.swing.JTextField txt_min_tardanza;
    private javax.swing.JTextField txt_monto;
    private javax.swing.JTextField txt_monto_descuento;
    private javax.swing.JTextField txt_nombres;
    // End of variables declaration//GEN-END:variables

    
    private void calcularMontoDescuentos(){
        double sueldo = new Farma_inf().getSueldoMinimoVital();
        //calculo de descuento por falta
        double descPorFalta = sueldo /24;
        //calculo de descuento por minuto
        double descPorMinuto = sueldo /11520;
        
        int minTarde = Integer.parseInt(txt_min_tardanza.getText());
        int faltas = Integer.parseInt(txt_faltas.getText());
        
        double  td = (descPorFalta*faltas)+(descPorMinuto*minTarde);
        double totalDescuento = Redondear(td);
        lbl_descuento.setText(""+totalDescuento);
    }
    
    private void calcularTotalPagar(){
        double descuento = Double.parseDouble(lbl_descuento.getText());
        double sueldo = new Farma_inf().getSueldoMinimoVital();
        double t = sueldo - descuento;
        double total = Redondear(t);
        txt_monto.setText(""+total);
    }
    
    public double Redondear(double numero) {
        return Math.rint(numero * 100) / 100;
    }
    
    private void registrarGasto(int idUsu){
        try {
        String nom_usu = new Users().cargarNomUsuario(idUsu);
        String ape_usu = new Users().cargarApeUsuario(idUsu);
        String descripcion = "Pago de :"+nom_usu+" "+ape_usu;
        double monto = Double.parseDouble(txt_monto.getText());
        String fecha = new ManejadorFechas().getFechaActualMySQL();
        String sql = "INSERT INTO `tgastos`(`descripcion_gasto`, `monto`, `fecha_gasto`) VALUES ('"+descripcion+"',"+monto+",'"+fecha+"')";
        Statement st = con.createStatement();
        int rs = st.executeUpdate(sql);
            if (rs>0) {
                System.out.println("Se registro el gasto");
            } else {
                System.out.println("Error de registro del gasto");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    private void registrarPago() {
        try {
            int codigo = Integer.parseInt(txt_codigo.getText());
            String fecha = new ManejadorFechas().getFechaActualMySQL();
            double descuento = Double.parseDouble(lbl_descuento.getText());
            String forma_pago = cmb_pago.getSelectedItem().toString();
            double monto = Double.parseDouble(txt_monto.getText());
            String sql = "INSERT INTO `tpagos`(`id_empleado`,`fecha_pago` ,`descuento`, `forma_pago`, `monto`,estado) VALUES (" + codigo + ",'"+fecha+"'," + descuento + ",'" + forma_pago + "'," + monto + ",1 )";
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            registrarGasto(codigo);
            if (rs>0) {
                int g = JOptionPane.showOptionDialog(btn_pagos, "Registro exitoso, ¿desea realizar otro pago?", "showOptionDialog", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No", "Cancelar"}, "Si");
                if (g==0) {
                    dispose();
                    new Pagos().setVisible(true);
                } else {
                    dispose();
                }
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    public int verificarSiSePago(int idUsu, int mes){
        int res=0;
        String sql = "SELECT estado FROM tpagos  where fecha_pago between CONCAT(YEAR(NOW()),'-"+mes+"-1') and CONCAT(YEAR(NOW()),'-"+mes+"-31') and id_empleado = "+idUsu+" ORDER BY estado DESC LIMIT 1";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                res = 1;
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return res;
    }
}
