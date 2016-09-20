/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.Conexion;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Goby
 */
public final class gestionVentas extends javax.swing.JFrame {

    DefaultTableModel model_produc;
    DefaultTableModel modelVenta, modelDetalles;
    Connection con = new Conexion().conectar();

    public gestionVentas() {
        initComponents();
        //setLocationRelativeTo(null);
        setResizable(false);       // que no se le pueda cambiar el tamanio
        //Centrar la ventana de autentificacion en la pantalla
        Dimension tamFrame = this.getSize();//para obtener las dimensiones del frame
        Dimension tamPantalla = Toolkit.getDefaultToolkit().getScreenSize();      //para obtener el tamanio de la pantalla
        setLocation((tamPantalla.width - tamFrame.width) / 2, (tamPantalla.height - tamFrame.height) / 2);  //para posicionar
        setVisible(true);// Hacer visible al frame
        titulos();
        bloquear();
    }

    public void bloquear() {
        btnAnularVenta.setEnabled(false);
        btnModificar.setEnabled(false);
    }

    //Metodo para obtener la fecha en formato MySQL
    public static Date Fecha(JDateChooser jDatefecha) {
        Date date = jDatefecha.getDate();
        long d = date.getTime();
        java.sql.Date fecha = new java.sql.Date(d);

        return fecha;

    }

    public void titulos() {
        String titulos[] = {"VENTA", "FECHA", "HORA", "ESTADO", "TRABAJADOR"};
        String titulosDetalles[] = {"ID", "COD", "PRODUCTO", "CANTIDAD", "PRECIO", "SUBTOTAL"};
        modelVenta = new DefaultTableModel(null, titulos);
        modelDetalles = new DefaultTableModel(null, titulosDetalles);
        tbl_ventas.setModel(modelVenta);
        tbl_detalles.setModel(modelDetalles);
    }

    public void cargarVentas() {
        limpiarTablaVentas();
        String sql = "SELECT tventa.id_venta, tventa.fecha_venta, tventa.hora_venta, tventa.estado ,tusuario.nom_usu, tusuario.apell_usu FROM `tventa` INNER JOIN tusuario on tventa.id_usu = tusuario.id_usu ";
        String datos[] = new String[5];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("tventa.id_venta");
                datos[1] = rs.getString("tventa.fecha_venta");
                datos[2] = rs.getString("tventa.hora_venta");
                int estado = rs.getInt("tventa.estado");
                if (estado == 0) {
                    datos[3] = "ACTIVO";
                } else {
                    datos[3] = "ANULADO";
                }
                datos[4] = rs.getString("tusuario.nom_usu");
                modelVenta.addRow(datos);
            }
            tbl_ventas.setModel(modelVenta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    public void cargarVentasConId(int id) {
        limpiarTablaVentas();
        String sql = "SELECT tventa.id_venta, tventa.fecha_venta, tventa.hora_venta, tventa.estado ,tusuario.nom_usu, tusuario.apell_usu FROM `tventa` INNER JOIN tusuario on tventa.id_usu = tusuario.id_usu WHERE tventa.id_venta = " + id;
        String datos[] = new String[5];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("tventa.id_venta");
                datos[1] = rs.getString("tventa.fecha_venta");
                datos[2] = rs.getString("tventa.hora_venta");
                int estado = rs.getInt("tventa.estado");
                if (estado == 0) {
                    datos[3] = "ACTIVO";
                } else {
                    datos[3] = "ANULADO";
                }
                datos[4] = rs.getString("tusuario.nom_usu") + " " + rs.getString("tusuario.apell_usu");
                modelVenta.addRow(datos);
            }
            tbl_ventas.setModel(modelVenta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    public void cargarRangoVentas() {
        limpiarTablaVentas();
        Date inicio = jdc_inicio.getDate();
        Date fin = jdc_fin.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fec_inicio = sdf.format(inicio);
        String fec_fin = sdf.format(fin);
        String sql = "SELECT tventa.id_venta, tventa.fecha_venta, tventa.hora_venta, tventa.estado ,tusuario.nom_usu, tusuario.apell_usu FROM `tventa` INNER JOIN tusuario on tventa.id_usu = tusuario.id_usu WHERE `fecha_venta` between '" + fec_inicio + "' and '" + fec_fin + "';";
        String datos[] = new String[5];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("tventa.id_venta");
                datos[1] = rs.getString("tventa.fecha_venta");
                datos[2] = rs.getString("tventa.hora_venta");
                int estado = rs.getInt("tventa.estado");
                if (estado == 0) {
                    datos[3] = "ACTIVO";
                } else {
                    datos[3] = "ANULADO";
                }
                datos[4] = rs.getString("tusuario.nom_usu") + " " + rs.getString("tusuario.apell_usu");
                modelVenta.addRow(datos);
            }
            tbl_ventas.setModel(modelVenta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    public void cargarDetalleVenta(int id) {
        limpiarTablaDetalles();
        String sql = "SELECT tdetalleventa.id ,tproducto_medicamento.id_pro_medi ,tproducto_medicamento.nom_pro_medi, tdetalleventa.cantidad, tproducto_medicamento.prec_venta, tdetalleventa.sub_total FROM `tdetalleventa` INNER JOIN tproducto_medicamento ON tdetalleventa.id_pro_medi = tproducto_medicamento.id_pro_medi INNER JOIN tventa on tdetalleventa.id_venta = tventa.id_venta WHERE tventa.id_venta = " + id + "";
        String datos[] = new String[6];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("tdetalleventa.id");
                datos[1] = rs.getString("tproducto_medicamento.id_pro_medi");
                datos[2] = rs.getString("tproducto_medicamento.nom_pro_medi");
                datos[3] = rs.getString("tdetalleventa.cantidad");
                datos[4] = rs.getString("tproducto_medicamento.prec_venta");
                datos[5] = rs.getString("tdetalleventa.sub_total");
                modelDetalles.addRow(datos);
            }
            tbl_detalles.setModel(modelDetalles);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }

    public void buscarProductos() {
        limpiarTablaBuscarProducto();
        String art = txt_buscar_prod.getText();
        String datos[] = new String[6];
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`, `concentracion_pro_medi`, `presentacion_pro_medi`, `fraccion_pro_medi`, `stock_pro_medi` FROM `tproducto_medicamento` WHERE nom_pro_medi LIKE '" + art + "%' OR nom_pro_medi LIKE '%" + art + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_pro_medi");
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("fraccion_pro_medi");
                datos[5] = rs.getString("stock_pro_medi");
                model_produc.addRow(datos);
                tbl_buscar_prod.setModel(model_produc);
            }
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void cargarFormProductos() {
        String titulos[] = {"CÓDIGO", "NOMBRE", "CONCENTRACIÓN", "PRESENTACIÓN", "FRACCIÓN", "STOCK"};
        model_produc = new DefaultTableModel(null, titulos);
        tbl_buscar_prod.setModel(model_produc);

        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`, `concentracion_pro_medi`, `presentacion_pro_medi`, `fraccion_pro_medi`, `stock_pro_medi` FROM `tproducto_medicamento`";
        try {
            String datos[] = new String[6];
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_pro_medi");
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("fraccion_pro_medi");
                datos[5] = rs.getString("stock_pro_medi");
                model_produc.addRow(datos);
                tbl_buscar_prod.setModel(model_produc);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void llenarComboTrabajadores() {
        String sql = "SELECT `nom_usu` FROM `tusuario`";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cmbTrabajador1.addItem(rs.getString("nom_usu"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void limpiarTablaVentas() {
        for (int i = 0; i < tbl_ventas.getRowCount(); i++) {
            modelVenta.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarTablaDetalles() {
        for (int i = 0; i < tbl_detalles.getRowCount(); i++) {
            modelDetalles.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarTablaBuscarProducto() {
        for (int i = 0; i < tbl_buscar_prod.getRowCount(); i++) {
            model_produc.removeRow(i);
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

        frm_buscar_prod = new javax.swing.JDialog();
        jLabel28 = new javax.swing.JLabel();
        txt_buscar_prod = new javax.swing.JTextField();
        btn_agregar_prod = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_buscar_prod = new javax.swing.JTable();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jdc_inicio = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jdc_fin = new com.toedter.calendar.JDateChooser();
        btn_listar = new javax.swing.JButton();
        txt_num_venta = new javax.swing.JTextField();
        lblVenta = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_detalles = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnEliminar2 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtSubtotal2 = new javax.swing.JTextField();
        txtIdProducto2 = new javax.swing.JTextField();
        txtProducto2 = new javax.swing.JTextField();
        txtCantidad2 = new javax.swing.JTextField();
        txtPrecioUnitario2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_ventas = new javax.swing.JTable();
        btnAnularVenta = new javax.swing.JButton();
        btnModificar1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtVenta1 = new javax.swing.JTextField();
        txtHora1 = new javax.swing.JTextField();
        cmbTrabajador1 = new javax.swing.JComboBox();
        cmbEstado1 = new javax.swing.JComboBox();
        jdcFecha1 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbMostrar = new javax.swing.JCheckBox();

        frm_buscar_prod.setBounds(new java.awt.Rectangle(200, 50, 740, 500));
        frm_buscar_prod.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        jLabel28.setText("jLabel27");
        frm_buscar_prod.getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 60));

        txt_buscar_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscar_prodActionPerformed(evt);
            }
        });
        txt_buscar_prod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscar_prodKeyReleased(evt);
            }
        });
        frm_buscar_prod.getContentPane().add(txt_buscar_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 310, 30));

        btn_agregar_prod.setText("AGREGAR");
        btn_agregar_prod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_prodActionPerformed(evt);
            }
        });
        frm_buscar_prod.getContentPane().add(btn_agregar_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 70, 230, -1));

        tbl_buscar_prod.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tbl_buscar_prod);

        frm_buscar_prod.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 730, 330));
        frm_buscar_prod.getContentPane().add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 510, 10));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("BUSCAR");
        frm_buscar_prod.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jdc_inicio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getContentPane().add(jdc_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 160, 30));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setText("INICIO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel3.setText("FIN");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 100, -1, -1));

        jdc_fin.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getContentPane().add(jdc_fin, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 70, 160, 30));

        btn_listar.setText("LISTAR");
        btn_listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_listarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_listar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 70, -1, 30));

        txt_num_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_num_ventaKeyReleased(evt);
            }
        });
        getContentPane().add(txt_num_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 70, 80, 30));

        lblVenta.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblVenta.setText("______");
        getContentPane().add(lblVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 340, 50, -1));

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("GESTIÓN VENTAS");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTOS VENDIDOS"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_detalles.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_detalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_detallesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_detalles);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 650, 170));

        btnModificar.setBackground(new java.awt.Color(255, 102, 0));
        btnModificar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnModificar.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel2.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 120, 120, 70));

        btnEliminar2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnEliminar2.setText("ELIMINAR");
        btnEliminar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar2ActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, -1, -1));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setText("SUBTOTAL");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 170, -1, -1));

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel18.setText("N° PRODUCTO");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, -1, -1));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel19.setText("PRODUCTO");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, -1, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel20.setText("CANTIDAD");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, -1, -1));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setText("P. UNITARIO");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, -1, -1));

        txtSubtotal2.setEditable(false);
        txtSubtotal2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.add(txtSubtotal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 170, 100, -1));

        txtIdProducto2.setEditable(false);
        txtIdProducto2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.add(txtIdProducto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 50, 100, -1));
        jPanel2.add(txtProducto2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 80, 100, -1));
        jPanel2.add(txtCantidad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 110, 100, -1));

        txtPrecioUnitario2.setEditable(false);
        txtPrecioUnitario2.setBackground(new java.awt.Color(255, 255, 153));
        jPanel2.add(txtPrecioUnitario2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 140, 100, -1));

        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 80, 30, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 1090, 260));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("LISTA DE VENTAS REALIZADAS"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_ventas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ventasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_ventas);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 650, 140));

        btnAnularVenta.setBackground(new java.awt.Color(255, 0, 0));
        btnAnularVenta.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnAnularVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnAnularVenta.setText("ANULAR VENTA");
        btnAnularVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularVentaActionPerformed(evt);
            }
        });
        jPanel3.add(btnAnularVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, 140, -1));

        btnModificar1.setBackground(new java.awt.Color(255, 102, 0));
        btnModificar1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnModificar1.setForeground(new java.awt.Color(255, 255, 255));
        btnModificar1.setText("MODIFICAR");
        btnModificar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnModificar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(961, 89, 120, 70));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setText("TRABAJADOR");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, -1, -1));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setText("N° VENTA");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, -1, -1));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setText("FECHA");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 50, -1, -1));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel15.setText("HORA");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, -1, -1));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setText("ESTADO");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, -1, -1));

        txtVenta1.setEditable(false);
        txtVenta1.setBackground(new java.awt.Color(255, 255, 153));
        jPanel3.add(txtVenta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 20, 100, -1));
        jPanel3.add(txtHora1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 80, 100, -1));

        jPanel3.add(cmbTrabajador1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 140, 100, -1));

        cmbEstado1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ACTIVO", "ANULADO" }));
        jPanel3.add(cmbEstado1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 110, 100, -1));
        jPanel3.add(jdcFecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 50, 120, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 1090, 210));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setText("N° BOLETA");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 70, -1, -1));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setText("VENTA N°");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 340, -1, -1));

        cmbMostrar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbMostrar.setText("TODAS LAS VENTAS");
        cmbMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMostrarActionPerformed(evt);
            }
        });
        getContentPane().add(cmbMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_listarActionPerformed
        try {
            cargarRangoVentas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Indique un rango de fechas");
        }

    }//GEN-LAST:event_btn_listarActionPerformed

    private void tbl_ventasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ventasMouseClicked
        int fila = tbl_ventas.getSelectedRow();
        if (fila >= 0) {
            try {
                llenarComboTrabajadores();
                int id = Integer.parseInt(tbl_ventas.getValueAt(fila, 0).toString());
                txtVenta1.setText("" + id);//seteo el numero de venta en el txt de venta para modificar
                String fecha = tbl_ventas.getValueAt(fila, 1).toString();//capturo la fecha de la tabla
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//defino un formato
                Date fechaDate = sdf.parse(fecha);//asigno el formato a la fecha que capturé
                jdcFecha1.setDate(fechaDate);//seteo la fecha con el formato en el JDateChooser
                txtHora1.setText(tbl_ventas.getValueAt(fila, 2).toString());//seteo la hora en un txt para modificar
                String estado = tbl_ventas.getValueAt(fila, 3).toString();
                if (estado.equals("ACTIVO")) {
                    cmbEstado1.setSelectedIndex(0);
                } else {
                    cmbEstado1.setSelectedIndex(1);
                }
                String nomUsuario = tbl_ventas.getValueAt(fila, 4).toString();
                int idUsuario = new Farma_inf().getIdTrabajador(nomUsuario);
                cmbTrabajador1.setSelectedIndex(idUsuario - 1);

                btnAnularVenta.setEnabled(true);
                lblVenta.setText("" + id);
                cargarDetalleVenta(id);
            } catch (ParseException ex) {
                Logger.getLogger(gestionVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        }

    }//GEN-LAST:event_tbl_ventasMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        limpiarTablaVentas();
        limpiarTablaDetalles();
        bloquear();
    }//GEN-LAST:event_formMouseClicked

    private void btnAnularVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularVentaActionPerformed
        //consulta: UPDATE `farmacom`.`tdetalleventa` SET `sub_total` = '0' WHERE `tdetalleventa`.`id` = 2;

        if (tbl_ventas.getRowCount() > 0) {
            //lectura de datos
//            Date fec = Fecha(jdc_fin);
//            lbl_fecha.setText("" + fec);
            int numDetallesFilas = tbl_detalles.getRowCount();
            int fila = tbl_ventas.getSelectedRow();
            String estado = tbl_ventas.getValueAt(fila, 3).toString();
            int idVenta = Integer.parseInt(tbl_ventas.getValueAt(fila, 0).toString());

            if (estado.equals("ACTIVO")) {
                String concepto = JOptionPane.showInputDialog("INGRESE CONCEPTO DE ANULACIÓN");
                setConcepto(idVenta, concepto);
                
                String sql = "UPDATE `farmacom1`.`tdetalleventa` SET `sub_total` = '0' WHERE `tdetalleventa`.`id_venta` =" + idVenta + " ";
                //Ejecucion de instrucciones
                try {
                    Statement st = con.createStatement();
                    for (int i = 0; i < numDetallesFilas; i++) {
                        int idprod = Integer.parseInt(tbl_detalles.getValueAt(i, 1).toString());
                        int cantidad = Integer.parseInt(tbl_detalles.getValueAt(i, 3).toString());

                        sumarStock(idprod, cantidad);

                        int rs = st.executeUpdate(sql);
                        if (rs > 0) {
                            //JOptionPane.showMessageDialog(getRootPane(), "SE ANULO LA VENTA");
                            System.out.println("Se actualizo el monto de la venta " + idVenta);
                            setEstado_anulado(idVenta);
                            cargarRangoVentas();
                            cargarDetalleVenta(idVenta);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                JOptionPane.showMessageDialog(getRootPane(), "SE ANULÓ LA VENTA");
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "LA VENTA SELECCIONADA YA FUÉ ANULADA");
            }

        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UNA VENTA");
        }
    }//GEN-LAST:event_btnAnularVentaActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        int fila = tbl_detalles.getSelectedRow();
        int id = Integer.parseInt(tbl_detalles.getValueAt(fila, 0).toString());
        String producto = txtProducto2.getText();
        int idProd = new Farma_inf().getIdProducto(producto);
        int cantidad = Integer.parseInt(txtCantidad2.getText());
        double subTotal = Double.parseDouble(txtSubtotal2.getText());
        //UPDATE `farmacom`.`tdetalleventa` SET `id_pro_medi` = '12' WHERE `tdetalleventa`.`id` = 8;
        String sql = "UPDATE `farmacom1`.`tdetalleventa` SET `id_pro_medi` ='"+idProd+"',`cantidad`="+cantidad+",`sub_total`="+subTotal+"  WHERE `tdetalleventa`.`id` = "+id;
        try {
            Statement st =con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs>0) {
                JOptionPane.showMessageDialog(getRootPane(), "SE ACTUALIZÓ EL PRODUCTO DE LA VENTA");
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        
    }//GEN-LAST:event_btnModificarActionPerformed

    private void tbl_detallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_detallesMouseClicked
        int fila = tbl_detalles.getSelectedRow();
        if (fila >= 0) {
            int idProd = Integer.parseInt(tbl_detalles.getValueAt(fila, 1).toString());
            String producto = tbl_detalles.getValueAt(fila, 2).toString();
            int cantidad = Integer.parseInt(tbl_detalles.getValueAt(fila, 3).toString());
            Double precUnitario = Double.parseDouble(tbl_detalles.getValueAt(fila, 4).toString());
            Double monto = Double.parseDouble(tbl_detalles.getValueAt(fila, 5).toString());
            if (monto > 0) {
                txtIdProducto2.setText("" + idProd);
                txtProducto2.setText(producto);
                txtCantidad2.setText("" + cantidad);
                txtPrecioUnitario2.setText("" + precUnitario);
                txtSubtotal2.setText("" + monto);

            } else {
                JOptionPane.showMessageDialog(getRootPane(), "La venta de este producto fué anulada, no se podran realizar modificaciones");
            }

        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UN PRODUCTO VENDIDO");
        }
        btnModificar.setEnabled(true);
    }//GEN-LAST:event_tbl_detallesMouseClicked

    private void cmbMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMostrarActionPerformed
        if (cmbMostrar.isSelected()) {
            cargarVentas();
            jdc_inicio.setEnabled(false);
            jdc_fin.setEnabled(false);
            btn_listar.setEnabled(false);
            txt_num_venta.setEnabled(false);
        } else {
            limpiarTablaVentas();
            limpiarTablaDetalles();
            jdc_inicio.setEnabled(true);
            jdc_fin.setEnabled(true);
            btn_listar.setEnabled(true);
            txt_num_venta.setEnabled(true);
        }
    }//GEN-LAST:event_cmbMostrarActionPerformed

    private void txt_num_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_num_ventaKeyReleased
        int id = Integer.parseInt(txt_num_venta.getText());
        try {
            if (!txt_num_venta.getText().trim().isEmpty()) {
                cargarVentasConId(id);
            } else {
                limpiarTablaVentas();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }//GEN-LAST:event_txt_num_ventaKeyReleased

    private void btnEliminar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar2ActionPerformed
        int fila = tbl_detalles.getSelectedRow();
        int id = Integer.parseInt(tbl_detalles.getValueAt(fila, 0).toString());
        eliminarDetalleDeUnaVenta(id);
        txtIdProducto2.setText("");
        txtProducto2.setText("");
        txtCantidad2.setText("");
        txtPrecioUnitario2.setText("");
        txtSubtotal2.setText("");
        
    }//GEN-LAST:event_btnEliminar2ActionPerformed

    private void txt_buscar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscar_prodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscar_prodActionPerformed

    private void txt_buscar_prodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_prodKeyReleased
        buscarProductos();
    }//GEN-LAST:event_txt_buscar_prodKeyReleased

    public void getProducto(int fila) {
        int codProd = Integer.parseInt(tbl_buscar_prod.getValueAt(fila, 0).toString());
        String nomProd = tbl_buscar_prod.getValueAt(fila, 1).toString();
        double precProd = new Farma_inf().getPrecioProducto(nomProd,codProd);
        txtIdProducto2.setText(""+codProd);
        txtProducto2.setText(nomProd);
        txtPrecioUnitario2.setText(""+precProd);
    }

    private void btn_agregar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_prodActionPerformed
        int fila = tbl_buscar_prod.getSelectedRow();
        if (fila >= 0) {
            getProducto(fila);
            frm_buscar_prod.dispose();
        } else {
            JOptionPane.showMessageDialog(frm_buscar_prod.getRootPane(), "SELECCIONAR UN PRODUCTO");
        }
    }//GEN-LAST:event_btn_agregar_prodActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        frm_buscar_prod.setVisible(true);
        frm_buscar_prod.setAlwaysOnTop(true);
        cargarFormProductos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnModificar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar1ActionPerformed
        int numVenta = Integer.parseInt(txtVenta1.getText());
        int estado = 0;

        Date fecha = jdcFecha1.getDate();//capturo la fecha del jdatechooser
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");//creo un formato para la fecha Date que capture
        String fechaDate = sdf.format(fecha);//le paso el formato a la fecha que capture
        String hora = txtHora1.getText();
        String status = cmbEstado1.getSelectedItem().toString();
        if (cmbEstado1.getSelectedIndex() == 0) {
            estado = 0;
        } else {
            estado = 1;
        }
        String trabajador = cmbTrabajador1.getSelectedItem().toString();
        int idTrabajador = new Farma_inf().getIdTrabajador(trabajador);

        String sql = "UPDATE `tventa` SET `fecha_venta`='" + fechaDate + "',`hora_venta`='" + hora + "',`estado`='" + estado + "',`id_usu`=" + idTrabajador + " WHERE `id_venta` = " + numVenta;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                JOptionPane.showMessageDialog(getRootPane(), "SE ACTUALIZO LA VENTA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }//GEN-LAST:event_btnModificar1ActionPerformed

    private void sumarStock(int idProd, int cantidad) {
        int stock = new Farma_inf().getStock(idProd) + cantidad;
        String sql = "UPDATE `tproducto_medicamento` SET `stock_pro_medi`= " + stock + " WHERE `id_pro_medi`=" + idProd + "";
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Se actualizo el stock del producto " + idProd);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setEstado_anulado(int idVenta) {
        String sql = "UPDATE `tventa` SET `estado`= '1' WHERE `id_venta` = " + idVenta + "";
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Estado de la venta " + idVenta + " ANULADO");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void setConcepto(int idVenta, String concepto) {
        String sql = "UPDATE `tventa` SET `concepto_anul`= '" + concepto + "' WHERE `id_venta` = " + idVenta + "";
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("concepto de la venta " + idVenta + " actualizado");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            java.util.logging.Logger.getLogger(gestionVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gestionVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gestionVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gestionVentas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gestionVentas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnularVenta;
    private javax.swing.JButton btnEliminar2;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificar1;
    private javax.swing.JButton btn_agregar_prod;
    private javax.swing.JButton btn_listar;
    private javax.swing.JComboBox cmbEstado1;
    private javax.swing.JCheckBox cmbMostrar;
    private javax.swing.JComboBox cmbTrabajador1;
    private javax.swing.JDialog frm_buscar_prod;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator6;
    private com.toedter.calendar.JDateChooser jdcFecha1;
    private com.toedter.calendar.JDateChooser jdc_fin;
    private com.toedter.calendar.JDateChooser jdc_inicio;
    private javax.swing.JLabel lblVenta;
    private javax.swing.JTable tbl_buscar_prod;
    private javax.swing.JTable tbl_detalles;
    private javax.swing.JTable tbl_ventas;
    private javax.swing.JTextField txtCantidad2;
    private javax.swing.JTextField txtHora1;
    private javax.swing.JTextField txtIdProducto2;
    private javax.swing.JTextField txtPrecioUnitario2;
    private javax.swing.JTextField txtProducto2;
    private javax.swing.JTextField txtSubtotal2;
    private javax.swing.JTextField txtVenta1;
    private javax.swing.JTextField txt_buscar_prod;
    private javax.swing.JTextField txt_num_venta;
    // End of variables declaration//GEN-END:variables

//metodo para elminar producto del detalle de una venta
    public void eliminarDetalleDeUnaVenta(int id) {
        limpiarTablaDetalles();
        String sql = "DELETE FROM `tdetalleventa` WHERE `id` = " + id;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Se borró el detalle :" + id);
                cargarDetalleVenta(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //metodos para eliminar una venta

    public void eliminarDetalleVenta(int numVenta) {
        String sql = "DELETE FROM `tdetalleventa` WHERE `id_venta` = " + numVenta;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Se borraron los detalles de la venta n°: " + numVenta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void eliminarVenta(int numVenta) {
        String sql = "DELETE FROM `tventa` WHERE `id_venta`= " + numVenta;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Se borró la venta n°: " + numVenta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
