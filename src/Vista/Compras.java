/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.Conexion;
import Control.ManejadorFechas;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Goby
 */
public final class Compras extends javax.swing.JFrame {

    int posx, posy;
    DefaultTableModel model_provee;
    DefaultTableModel model_produc;
    DefaultTableModel model_produc_add;

    Connection con = new Conexion().conectar();

    public Compras() {
        //setUndecorated(true);
        setAlwaysOnTop(true);
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        frm_buscar_provee.setAlwaysOnTop(true);
        frm_buscar_prod.setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        lbl_fecha.setText("" + new Fechas().fechaCadena());
        titulosAddProduct();
        getIgv();
    }

    public void titulosAddProduct() {
        String cabeza[] = {"CÓDIGO", "NOMBRE", "CANT.", "PRECIO COMPRA", "SUB-TOTAL", "STOCK ACTUAL", "% UTILIDAD", "PREC. UNIDAD"};
        model_produc_add = new DefaultTableModel(null, cabeza);
        tbl_productos_agregados.setModel(model_produc_add);
        tbl_productos_agregados.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbl_productos_agregados.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbl_productos_agregados.getColumnModel().getColumn(2).setPreferredWidth(50);
        tbl_productos_agregados.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbl_productos_agregados.getColumnModel().getColumn(4).setPreferredWidth(50);
        tbl_productos_agregados.getColumnModel().getColumn(5).setPreferredWidth(50);
        tbl_productos_agregados.getColumnModel().getColumn(6).setPreferredWidth(50);
        tbl_productos_agregados.getColumnModel().getColumn(7).setPreferredWidth(50);
        lbl_pie.setText(new Farma_inf().pie());
    }

    public void cargarFormProveedor() {
        String titulos[] = {"RUC", "RAZON SOCIAL", "DIRECCÍON", "TELÉFONO"};
        model_provee = new DefaultTableModel(null, titulos);
        tbl_provee.setModel(model_provee);

        String sql = "SELECT ruc_provee, nom_provee, dir_provee, telf_provee FROM tproveedor";
        try {
            String datos[] = new String[4];
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("ruc_provee");
                datos[1] = rs.getString("nom_provee");
                datos[2] = rs.getString("dir_provee");
                datos[3] = rs.getString("telf_provee");
                model_provee.addRow(datos);
                tbl_provee.setModel(model_provee);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void buscarProveedor(String nomProvee) {
        limpiarTablaProveedor();
        String sql = "SELECT ruc_provee, nom_provee, dir_provee, telf_provee FROM tproveedor WHERE nom_provee LIKE '" + nomProvee + "%' OR nom_provee LIKE '%" + nomProvee + "'";
        try {
            String datos[] = new String[4];
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("ruc_provee");
                datos[1] = rs.getString("nom_provee");
                datos[2] = rs.getString("dir_provee");
                datos[3] = rs.getString("telf_provee");
                model_provee.addRow(datos);
                tbl_provee.setModel(model_provee);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void getDatosProvee() {
        int fila = tbl_provee.getSelectedRow();
        String ruc = tbl_provee.getValueAt(fila, 0).toString();
        String razon = tbl_provee.getValueAt(fila, 1).toString();
        String dir = tbl_provee.getValueAt(fila, 2).toString();
        String telf = tbl_provee.getValueAt(fila, 3).toString();

        txt_ruc.setText("" + ruc);
        txt_nom_raz.setText(razon);
        txt_direccion.setText(dir);
        txt_celular.setText("" + telf);

        frm_buscar_provee.dispose();
    }

    public void cargarFormProductos() {
        String titulos[] = {"CÓDIGO", "NOMBRE", "CONCENTRACIÓN", "PRESENTACIÓN", "LABORATORIO","PRECIO", "P. BLISTER", "STOCK"};
        model_produc = new DefaultTableModel(null, titulos);
        //tbl_buscar_prod.setModel(model_produc);

        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`, `concentracion_pro_medi`, `presentacion_pro_medi`, `proveedor`,`prec_venta`,`precio_blister`, `stock_pro_medi` FROM `tproducto_medicamento`";
        try {
            String datos[] = new String[8];
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_pro_medi");
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("proveedor");
                datos[5] = rs.getString("prec_venta");
                datos[6] = rs.getString("precio_blister");
                datos[7] = rs.getString("stock_pro_medi");
                model_produc.addRow(datos);
                tbl_buscar_prod.setModel(model_produc);
            }
            tbl_buscar_prod.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl_buscar_prod.getColumnModel().getColumn(1).setPreferredWidth(250);
            tbl_buscar_prod.getColumnModel().getColumn(2).setPreferredWidth(50);
            tbl_buscar_prod.getColumnModel().getColumn(3).setPreferredWidth(120);
            tbl_buscar_prod.getColumnModel().getColumn(4).setPreferredWidth(120);
            tbl_buscar_prod.getColumnModel().getColumn(5).setPreferredWidth(50);
            tbl_buscar_prod.getColumnModel().getColumn(6).setPreferredWidth(50);
            tbl_buscar_prod.getColumnModel().getColumn(7).setPreferredWidth(50);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void buscarProductos() {
        limpiarTabla();
        String art = txt_buscar_prod.getText();
        String datos[] = new String[8];
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`, `concentracion_pro_medi`, `presentacion_pro_medi`, `proveedor`,`prec_venta`,`precio_blister`, `stock_pro_medi` FROM `tproducto_medicamento` WHERE nom_pro_medi LIKE '" + art + "%' OR nom_pro_medi LIKE '%" + art + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_pro_medi");
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("proveedor");
                datos[5] = rs.getString("prec_venta");
                datos[6] = rs.getString("precio_blister");
                datos[7] = rs.getString("stock_pro_medi");
                model_produc.addRow(datos);
                tbl_buscar_prod.setModel(model_produc);
            }
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void getProducto(int fila) {
        int cod_prod = Integer.parseInt(tbl_buscar_prod.getValueAt(fila, 0).toString());
        String nom_prod = tbl_buscar_prod.getValueAt(fila, 1).toString();
        String labo = tbl_buscar_prod.getValueAt(fila, 4).toString();
        String precio = tbl_buscar_prod.getValueAt(fila, 5).toString();
        String stock_prod = tbl_buscar_prod.getValueAt(fila, 7).toString();
        txtPresentacion.setText(new Farma_inf().getPresentacionProducto(cod_prod));
        txtLote.setText(new Farma_inf().getLoteProducto(cod_prod));
        txtFecVenc.setText(new Farma_inf().getFechaVencimientoProducto(cod_prod));
        txt_codigo.setText("" + cod_prod);
        txt_nom_prod.setText(nom_prod);
        txt_stock.setText(stock_prod);
        txtPrecio.setText(precio);
        txtLaboratorio.setText(labo);
    }

    public void addProducto(int cod) {
        //"CÓDIGO","NOMBRE","CANTIDAD","P. UNITARIO","PRECIO COMPRA","SUBTOTAL"
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`,`stock_pro_medi` FROM `tproducto_medicamento` WHERE `id_pro_medi` = " + cod + " ";
        try {
            String datos[] = new String[8];
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                datos[0] = rs.getString("id_pro_medi");
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = txt_cantidad.getText();
                datos[3] = txt_precio_compra.getText();
                double subtotal = Double.parseDouble(txt_cantidad.getText()) * Double.parseDouble(txt_precio_compra.getText());
                datos[4] = txt_precio_compra.getText();
                datos[5] = rs.getString("stock_pro_medi");
                datos[6] = txtUtilidad.getText();
                datos[7] = txtPrecVentaUnidad.getText();
                model_produc_add.addRow(datos);
                tbl_productos_agregados.setModel(model_produc_add);
            } else {
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void limpiarTablaProveedor() {
        for (int i = 0; i < tbl_provee.getRowCount(); i++) {
            model_provee.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarTabla() {
        for (int i = 0; i < tbl_buscar_prod.getRowCount(); i++) {
            model_produc.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarAdd() {
        txt_precio_compra.setText("");
        txt_nom_prod.setText("");
        txt_codigo.setText("");
        txt_stock.setText("");
        txt_cantidad.setText("");
        txtPrecVentaUnidad.setText("");
        txtUtilidad.setText("");
        txtPresentacion.setText("");
        txtLote.setText("");
        txtFecVenc.setText("");
        txtPrecio.setText("");
        txtLaboratorio.setText("");
    }

    public void contarAdd() {
        int n = tbl_productos_agregados.getRowCount();
        lbl_num_productos.setText("" + n);
    }

    public void getIgv() {
        String sql = "SELECT `igv` FROM `tconfiguracion` where id_config=1";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                lbl_igv.setText(rs.getString("igv"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void calculos() {
        double subtotal = 0.0, igva = 0.0, total = 0.0;
        double igv = Double.parseDouble(lbl_igv.getText());
        int row = tbl_productos_agregados.getRowCount();
        for (int i = 0; i < row; i++) {
            subtotal = subtotal + Double.parseDouble(tbl_productos_agregados.getValueAt(i, 4).toString());
            igva = igv * subtotal;
        }

        txt_subtotal.setText("" + new Farma_inf().Redondear(subtotal));
        txt_igv.setText("" + new Farma_inf().Redondear(igva));
        txt_total.setText("" + new Farma_inf().Redondear(subtotal + igva));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frm_buscar_provee = new javax.swing.JDialog();
        jLabel27 = new javax.swing.JLabel();
        txtBuscarProveedor = new javax.swing.JTextField();
        btn_add_provee = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_provee = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        frm_buscar_prod = new javax.swing.JDialog();
        jLabel28 = new javax.swing.JLabel();
        txt_buscar_prod = new javax.swing.JTextField();
        btn_agregar_prod = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_buscar_prod = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lbl_salir = new javax.swing.JLabel();
        lbl_logo1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_fecha = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_celular = new javax.swing.JTextField();
        txt_ruc = new javax.swing.JTextField();
        txt_nom_raz = new javax.swing.JTextField();
        txt_direccion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cmb_boleta = new javax.swing.JComboBox();
        txt_serie = new javax.swing.JTextField();
        cmb_form_pago = new javax.swing.JComboBox();
        jdc_fecha = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_productos_agregados = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txt_precio_compra = new javax.swing.JTextField();
        txt_cantidad = new javax.swing.JTextField();
        btn_buscar = new javax.swing.JButton();
        btn_agregar = new javax.swing.JButton();
        btn_quitar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        txt_numero = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtPrecVentaUnidad = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbl_igv = new javax.swing.JLabel();
        lbl_num_productos = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        btn_comprar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txt_igv = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        txtUtilidad = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_stock = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();
        txt_nom_prod = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtFecVenc = new javax.swing.JTextField();
        txtPresentacion = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtLote = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtLaboratorio = new javax.swing.JTextField();

        frm_buscar_provee.setBounds(new java.awt.Rectangle(600, 50, 555, 535));
        frm_buscar_provee.setResizable(false);
        frm_buscar_provee.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        jLabel27.setText("jLabel27");
        frm_buscar_provee.getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 60));

        txtBuscarProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtBuscarProveedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 1, true));
        txtBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarProveedorActionPerformed(evt);
            }
        });
        txtBuscarProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarProveedorKeyReleased(evt);
            }
        });
        frm_buscar_provee.getContentPane().add(txtBuscarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 290, 30));

        btn_add_provee.setBackground(new java.awt.Color(0, 204, 51));
        btn_add_provee.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_add_provee.setForeground(new java.awt.Color(255, 255, 255));
        btn_add_provee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add.png"))); // NOI18N
        btn_add_provee.setText("AGREGAR");
        btn_add_provee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_add_proveeActionPerformed(evt);
            }
        });
        frm_buscar_provee.getContentPane().add(btn_add_provee, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, 220, -1));

        tbl_provee.setForeground(new java.awt.Color(0, 51, 255));
        tbl_provee.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_provee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbl_proveeKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_provee);

        frm_buscar_provee.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 520, 320));

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setBackground(new java.awt.Color(255, 204, 51));
        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setText("REGISTRAR NUEVO PROVEEDOR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 380, -1, -1));

        jButton3.setBackground(new java.awt.Color(0, 102, 102));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453101714_gtk-refresh.png"))); // NOI18N
        jButton3.setText("REFRESCAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, -1));

        frm_buscar_provee.getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 540, 420));

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));
        frm_buscar_provee.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 540, 20));

        frm_buscar_prod.setBounds(new java.awt.Rectangle(200, 50, 860, 500));
        frm_buscar_prod.setResizable(false);
        frm_buscar_prod.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        jLabel28.setText("jLabel27");
        frm_buscar_prod.getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 60));

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

        btn_agregar_prod.setBackground(new java.awt.Color(51, 204, 0));
        btn_agregar_prod.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        btn_agregar_prod.setForeground(new java.awt.Color(255, 255, 255));
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
        tbl_buscar_prod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbl_buscar_prodKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_buscar_prod);

        frm_buscar_prod.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 850, 330));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setText("BUSCAR");
        frm_buscar_prod.getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
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
        getContentPane().add(lbl_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, -1, -1));

        lbl_logo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        lbl_logo1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lbl_logo1MouseDragged(evt);
            }
        });
        lbl_logo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_logo1MousePressed(evt);
            }
        });
        getContentPane().add(lbl_logo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 60));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("TELÉFONO/CELULAR");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        lbl_fecha.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_fecha.setForeground(new java.awt.Color(0, 0, 102));
        lbl_fecha.setText("Viernes, 24 de Diciembre del 2015");
        getContentPane().add(lbl_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 130, 170, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("RUC");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("NOMBRE, RAZON SOCIAL");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("DIRECCIÓN");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        txt_celular.setEditable(false);
        txt_celular.setBackground(new java.awt.Color(255, 255, 153));
        txt_celular.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_celular.setForeground(new java.awt.Color(0, 51, 255));
        txt_celular.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_celular.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        getContentPane().add(txt_celular, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 130, -1));

        txt_ruc.setEditable(false);
        txt_ruc.setBackground(new java.awt.Color(255, 255, 153));
        txt_ruc.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_ruc.setForeground(new java.awt.Color(0, 51, 255));
        txt_ruc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ruc.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        getContentPane().add(txt_ruc, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 130, -1));

        txt_nom_raz.setEditable(false);
        txt_nom_raz.setBackground(new java.awt.Color(255, 255, 153));
        txt_nom_raz.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nom_raz.setForeground(new java.awt.Color(0, 51, 255));
        txt_nom_raz.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nom_raz.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        getContentPane().add(txt_nom_raz, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 130, -1));

        txt_direccion.setEditable(false);
        txt_direccion.setBackground(new java.awt.Color(255, 255, 153));
        txt_direccion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_direccion.setForeground(new java.awt.Color(0, 51, 255));
        txt_direccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_direccion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        getContentPane().add(txt_direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 260, -1));

        jButton1.setBackground(new java.awt.Color(0, 204, 0));
        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        jButton1.setText("BUSCAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 130, -1));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 0, 0));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/customer.png"))); // NOI18N
        jLabel13.setText("PROVEEDOR");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cmb_boleta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cmb_boleta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BOLETA", "FACTURA", "GUÍA DE REMISIÓN" }));
        cmb_boleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_boletaActionPerformed(evt);
            }
        });
        jPanel2.add(cmb_boleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 120, 140, 30));

        txt_serie.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_serie.setForeground(new java.awt.Color(0, 102, 204));
        txt_serie.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_serie.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_serie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_serieActionPerformed(evt);
            }
        });
        txt_serie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_serieKeyTyped(evt);
            }
        });
        jPanel2.add(txt_serie, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, 100, -1));

        cmb_form_pago.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cmb_form_pago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CONTADO", "CRÉDITO" }));
        jPanel2.add(cmb_form_pago, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 190, 140, -1));

        jdc_fecha.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel2.add(jdc_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 230, 140, 30));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("FECHA");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 230, -1, -1));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("FORMA DE PAGO");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, -1, -1));

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("N°");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, -1, -1));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("TIPO DE DOCUMENTO");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 102));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel2.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 20, 170));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 0, 0));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/code.png"))); // NOI18N
        jLabel21.setText("DOCUMENTO");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, -1, -1));

        tbl_productos_agregados.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbl_productos_agregados.setForeground(new java.awt.Color(0, 51, 255));
        tbl_productos_agregados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_productos_agregados);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 840, 160));

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("UNIDADES");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, 70, -1));

        jLabel30.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 102));
        jLabel30.setText("unidad");
        jPanel2.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 360, -1, -1));

        txt_precio_compra.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_precio_compra.setForeground(new java.awt.Color(0, 51, 255));
        txt_precio_compra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_precio_compra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txt_precio_compra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_precio_compraActionPerformed(evt);
            }
        });
        txt_precio_compra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_precio_compraKeyTyped(evt);
            }
        });
        jPanel2.add(txt_precio_compra, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 270, 100, -1));

        txt_cantidad.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_cantidad.setForeground(new java.awt.Color(0, 51, 255));
        txt_cantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cantidad.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txt_cantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cantidadActionPerformed(evt);
            }
        });
        txt_cantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cantidadKeyTyped(evt);
            }
        });
        jPanel2.add(txt_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 300, 100, -1));

        btn_buscar.setBackground(new java.awt.Color(0, 204, 0));
        btn_buscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_buscar.setForeground(new java.awt.Color(255, 255, 255));
        btn_buscar.setMnemonic('b');
        btn_buscar.setText("BUSCAR");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 270, 110, -1));

        btn_agregar.setBackground(new java.awt.Color(255, 153, 0));
        btn_agregar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_agregar.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar.setText("AGREGAR");
        btn_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 390, 220, -1));

        btn_quitar.setBackground(new java.awt.Color(255, 0, 0));
        btn_quitar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_quitar.setForeground(new java.awt.Color(255, 255, 255));
        btn_quitar.setMnemonic('q');
        btn_quitar.setText("QUITAR");
        btn_quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quitarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_quitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 610, 110, -1));

        jSeparator2.setForeground(new java.awt.Color(0, 0, 102));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 90, 870, -1));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 0, 0));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/modificar.png"))); // NOI18N
        jLabel25.setText("TABLA DE PRODUCTOS AGREGADOS");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 600, -1, -1));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1460976811_self1.png"))); // NOI18N
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, -1, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("COMPRAS");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(204, 0, 0));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/etiqueta.png"))); // NOI18N
        jLabel19.setText("PRODUCTO");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, 30));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 102));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 860, 10));

        jLabel31.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 102));
        jLabel31.setText("SERIE");
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, -1, -1));

        txt_numero.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_numero.setForeground(new java.awt.Color(0, 102, 204));
        txt_numero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_numero.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_numero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_numeroActionPerformed(evt);
            }
        });
        txt_numero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_numeroKeyTyped(evt);
            }
        });
        jPanel2.add(txt_numero, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 160, 140, -1));

        jLabel32.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 102));
        jLabel32.setText("PRECIO COMPRA");
        jPanel2.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 270, -1, -1));

        txtPrecVentaUnidad.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtPrecVentaUnidad.setForeground(new java.awt.Color(0, 51, 255));
        txtPrecVentaUnidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecVentaUnidad.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtPrecVentaUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecVentaUnidadActionPerformed(evt);
            }
        });
        jPanel2.add(txtPrecVentaUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 330, 100, -1));

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_pie.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("jLabel17");
        jPanel1.add(lbl_pie, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 30));

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 750, 860, 30));

        jLabel24.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("IGV ACTUAL:");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 690, -1, -1));

        lbl_igv.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_igv.setText("........");
        jPanel2.add(lbl_igv, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 690, -1, -1));

        lbl_num_productos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_num_productos.setText("........");
        jPanel2.add(lbl_num_productos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 660, -1, -1));

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 102));
        jLabel29.setText("PRODUCTOS AGREGADOS");
        jPanel2.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 660, -1, -1));

        btn_comprar.setBackground(new java.awt.Color(0, 153, 204));
        btn_comprar.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btn_comprar.setForeground(new java.awt.Color(255, 255, 255));
        btn_comprar.setText("COMPRAR");
        btn_comprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_comprarActionPerformed(evt);
            }
        });
        jPanel2.add(btn_comprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 700, 530, 40));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("SUBTOTAL");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 660, -1, -1));

        txt_subtotal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_subtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_subtotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel2.add(txt_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 660, 100, -1));

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("IGV");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 660, -1, -1));

        txt_igv.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_igv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_igv.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel2.add(txt_igv, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 660, 90, -1));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("TOTAL");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 660, -1, -1));

        txt_total.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_total.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_total.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel2.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 660, 120, -1));

        txtUtilidad.setEditable(false);
        txtUtilidad.setBackground(new java.awt.Color(255, 102, 0));
        txtUtilidad.setFont(new java.awt.Font("SansSerif", 1, 15)); // NOI18N
        txtUtilidad.setForeground(new java.awt.Color(255, 255, 255));
        txtUtilidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUtilidad.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtUtilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUtilidadActionPerformed(evt);
            }
        });
        txtUtilidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUtilidadKeyTyped(evt);
            }
        });
        jPanel2.add(txtUtilidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 360, 100, -1));

        jLabel33.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 102));
        jLabel33.setText("% UTILIDAD");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 360, -1, -1));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("STOCK");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        txt_stock.setEditable(false);
        txt_stock.setBackground(new java.awt.Color(255, 255, 153));
        txt_stock.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_stock.setForeground(new java.awt.Color(0, 51, 255));
        txt_stock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_stock.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        jPanel2.add(txt_stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 50, -1));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("F. VENC");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, -1, -1));

        txt_codigo.setEditable(false);
        txt_codigo.setBackground(new java.awt.Color(255, 255, 153));
        txt_codigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_codigo.setForeground(new java.awt.Color(0, 51, 255));
        txt_codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_codigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        jPanel2.add(txt_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 50, -1));

        txt_nom_prod.setEditable(false);
        txt_nom_prod.setBackground(new java.awt.Color(255, 255, 153));
        txt_nom_prod.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nom_prod.setForeground(new java.awt.Color(0, 51, 255));
        txt_nom_prod.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nom_prod.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        jPanel2.add(txt_nom_prod, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 310, 340, -1));

        jLabel34.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 102));
        jLabel34.setText("PRESENTACIÓN");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, -1, -1));

        txtFecVenc.setEditable(false);
        txtFecVenc.setBackground(new java.awt.Color(255, 255, 153));
        txtFecVenc.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtFecVenc.setForeground(new java.awt.Color(0, 51, 255));
        txtFecVenc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecVenc.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        jPanel2.add(txtFecVenc, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 100, -1));

        txtPresentacion.setEditable(false);
        txtPresentacion.setBackground(new java.awt.Color(255, 255, 153));
        txtPresentacion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtPresentacion.setForeground(new java.awt.Color(0, 51, 255));
        txtPresentacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPresentacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        jPanel2.add(txtPresentacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, 170, -1));

        jLabel35.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(0, 0, 102));
        jLabel35.setText("LABORATORIO");
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, -1, -1));

        txtLote.setEditable(false);
        txtLote.setBackground(new java.awt.Color(255, 255, 153));
        txtLote.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtLote.setForeground(new java.awt.Color(0, 51, 255));
        txtLote.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLote.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        jPanel2.add(txtLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 70, -1));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("CÓDIGO");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, -1));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("NOMBRE");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jLabel36.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 102));
        jLabel36.setText("PRECIO VENTA");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 330, -1, -1));

        jButton4.setText("HISTORIAL DE COMPRAS");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 610, 230, -1));

        jLabel37.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 102));
        jLabel37.setText("LOTE");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, -1, -1));

        txtPrecio.setEditable(false);
        txtPrecio.setBackground(new java.awt.Color(255, 255, 153));
        txtPrecio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtPrecio.setForeground(new java.awt.Color(0, 51, 255));
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        jPanel2.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 400, 50, -1));

        jLabel38.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 102));
        jLabel38.setText("PRECIO");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, -1));

        txtLaboratorio.setEditable(false);
        txtLaboratorio.setBackground(new java.awt.Color(255, 255, 153));
        txtLaboratorio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtLaboratorio.setForeground(new java.awt.Color(0, 51, 255));
        txtLaboratorio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLaboratorio.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        jPanel2.add(txtLaboratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 170, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 860, 790));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_logo1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logo1MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x - posx, point.y - posy);
    }//GEN-LAST:event_lbl_logo1MouseDragged

    private void lbl_logo1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logo1MousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_lbl_logo1MousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frm_buscar_provee.setVisible(true);
        frm_buscar_provee.setAlwaysOnTop(true);
        txtBuscarProveedor.requestFocus();
        cargarFormProveedor();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_add_proveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_add_proveeActionPerformed
        int fila = tbl_provee.getSelectedRow();
        if (fila >= 0) {
            getDatosProvee();
            txt_serie.requestFocus();
        } else {
            JOptionPane.showMessageDialog(frm_buscar_provee.getRootPane(), "SELECCIONE PROVEEDOR");
        }

    }//GEN-LAST:event_btn_add_proveeActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        frm_buscar_prod.setVisible(true);
        frm_buscar_prod.setAlwaysOnTop(true);
        txt_buscar_prod.setText("");
        cargarFormProductos();
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void txt_buscar_prodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_prodKeyReleased
        buscarProductos();
    }//GEN-LAST:event_txt_buscar_prodKeyReleased

    private void btn_agregar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_prodActionPerformed
        int fila = tbl_buscar_prod.getSelectedRow();
        if (fila >= 0) {
            getProducto(fila);
            txt_precio_compra.requestFocus();
            frm_buscar_prod.dispose();
        } else {
            JOptionPane.showMessageDialog(frm_buscar_prod.getRootPane(), "SELECCIONAR UN PRODUCTO");
        }
    }//GEN-LAST:event_btn_agregar_prodActionPerformed

    private void btn_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregarActionPerformed
        try {
            int c = Integer.parseInt(txt_codigo.getText());
            if (!txt_nom_prod.getText().trim().isEmpty()) {
                if (!txt_precio_compra.getText().trim().isEmpty()) {
                    if (!txt_cantidad.getText().trim().isEmpty()) {
                        if (!txtPrecVentaUnidad.getText().trim().isEmpty()) {
                            /*int cantidad = Integer.parseInt(txt_cantidad.getText());
                             Double precio = Double.parseDouble(txt_precio.getText());*/
                            addProducto(c);
                            limpiarAdd();
                            calculos();
                            contarAdd();
                        } else {
                            JOptionPane.showMessageDialog(frm_buscar_prod.getRootPane(), "INDIQUE PORCENTAJE DE UTILIDAD");
                            txtPrecVentaUnidad.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frm_buscar_prod.getRootPane(), "INDIQUE CANTIDAD");
                        txt_cantidad.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(frm_buscar_prod.getRootPane(), "INDIQUE PRECIO VENTA");
                    txt_precio_compra.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UN PRODUCTO");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }//GEN-LAST:event_btn_agregarActionPerformed

    private void lbl_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_salirMouseClicked
        dispose();
    }//GEN-LAST:event_lbl_salirMouseClicked

    private void btn_comprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_comprarActionPerformed
        try {
            int filas = tbl_productos_agregados.getRowCount();
            if (!txt_nom_raz.getText().trim().isEmpty()) {
                if (!txt_serie.getText().trim().isEmpty()) {
                    if (filas < 0) {
                        JOptionPane.showMessageDialog(getRootPane(), "AGREGUE PRODUCTOS A COMPRAR");
                    } else {
                        registrarCompra();
                        registrarDetalleCompra();
                        int opc = JOptionPane.showOptionDialog(getRootPane(), "COMPRA REALIZADA, ¿DESEA REALIZAR MAS COMPRAS?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
                        if (opc == 0) {
                            dispose();
                            new Compras().setVisible(true);
                        } else {
                            dispose();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "INDIQUE NUMERO DE DOCUMENTO");
                    txt_serie.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "INDIQUE PROVEEDOR");
                txt_nom_raz.requestFocus();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), "INDIQUE PRECIO, PRECIO U,CANTIDAD Y AGREGUE A LA LISTA DE PRODUCTOS");
        }


    }//GEN-LAST:event_btn_comprarActionPerformed

    private void btn_quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quitarActionPerformed
        int fila = tbl_productos_agregados.getSelectedRow();
        if (fila >= 0) {
            model_produc_add.removeRow(fila);
            calculos();
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UN PRODUCTO A QUITAR");
        }
    }//GEN-LAST:event_btn_quitarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Reg_proveedor().frm_prov_nuevo.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_precio_compraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_precio_compraActionPerformed
        txt_cantidad.requestFocus();
    }//GEN-LAST:event_txt_precio_compraActionPerformed

    private void txt_serieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_serieActionPerformed
        txt_numero.requestFocus();
    }//GEN-LAST:event_txt_serieActionPerformed

    private void txt_serieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serieKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE SOLO NUMEROS");
            txt_serie.requestFocus();
        }
    }//GEN-LAST:event_txt_serieKeyTyped

    private void txt_numeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_numeroKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE SOLO NUMEROS");
            txt_numero.requestFocus();
        }
    }//GEN-LAST:event_txt_numeroKeyTyped

    private void txt_precio_compraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_precio_compraKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE SOLO NUMEROS");
            txt_precio_compra.requestFocus();
        }
    }//GEN-LAST:event_txt_precio_compraKeyTyped

    private void txt_cantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidadKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE SOLO NUMEROS");
            txt_cantidad.requestFocus();
        }
    }//GEN-LAST:event_txt_cantidadKeyTyped

    private void cmb_boletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_boletaActionPerformed
        txt_serie.requestFocus();
    }//GEN-LAST:event_cmb_boletaActionPerformed

    private void txtBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarProveedorActionPerformed
        tbl_provee.requestFocus();
        tbl_provee.getSelectionModel().setSelectionInterval(0, 0);
    }//GEN-LAST:event_txtBuscarProveedorActionPerformed

    private void tbl_proveeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_proveeKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            tbl_provee.changeSelection(tbl_provee.getSelectedRow() - 1, 0, false, false);
            btn_add_provee.doClick();
        }
    }//GEN-LAST:event_tbl_proveeKeyTyped

    private void txt_buscar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscar_prodActionPerformed
        tbl_buscar_prod.requestFocus();
        tbl_buscar_prod.getSelectionModel().setSelectionInterval(0, 0);
    }//GEN-LAST:event_txt_buscar_prodActionPerformed

    private void txtPrecVentaUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecVentaUnidadActionPerformed
        if (!txt_precio_compra.getText().trim().isEmpty()) {
            if (!txt_cantidad.getText().trim().isEmpty()) {
                if (!txtPrecVentaUnidad.getText().trim().isEmpty()) {
//                    double precio = Double.parseDouble(txt_precio_compra.getText());
//                    double cantidad = Double.parseDouble(txt_cantidad.getText());
//                    double porcentaje = Double.parseDouble(txtUtilidad.getText());
//                    double precUniBruto = precio / cantidad;
//                    double utilidad = (precio / cantidad) * porcentaje;
//                    double total = precUniBruto + utilidad;
//                    txtPrecVenta.setText("" + new Farma_inf().Redondear(total));
//                    txtPrecVenta.requestFocus();
                    double precio = Double.parseDouble(txt_precio_compra.getText());
                    double cantidad = Double.parseDouble(txt_cantidad.getText());
                    double precioIngresado = Double.parseDouble(txtPrecVentaUnidad.getText());
                    double precioBruto = precio / cantidad;
                    double resta = precioIngresado - precioBruto;
                    double porcentaje = new Farma_inf().Redondear((resta * 100) / precioBruto);
                    txtUtilidad.setText("" + porcentaje);
                    txtUtilidad.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "INGRESE PORCENTAJE DE UTILIDAD");
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "INGRESE CANTIDAD");
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE PRECIO DE COMPRA");
        }
        
    }//GEN-LAST:event_txtPrecVentaUnidadActionPerformed

    private void txt_cantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cantidadActionPerformed
        txtPrecVentaUnidad.requestFocus();
    }//GEN-LAST:event_txt_cantidadActionPerformed

    private void txtUtilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUtilidadActionPerformed
        btn_agregar.doClick();
    }//GEN-LAST:event_txtUtilidadActionPerformed

    private void txtUtilidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUtilidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUtilidadKeyTyped

    private void txt_numeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_numeroActionPerformed
        txt_precio_compra.requestFocus();
    }//GEN-LAST:event_txt_numeroActionPerformed

    private void txtBuscarProveedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProveedorKeyReleased
        String nomProvee = txtBuscarProveedor.getText();
        buscarProveedor(nomProvee);
    }//GEN-LAST:event_txtBuscarProveedorKeyReleased

    private void tbl_buscar_prodKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_buscar_prodKeyTyped
//        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
//            tbl_buscar_prod.changeSelection(tbl_buscar_prod.getSelectedRow() - 1, 0, false, false);
//            btn_agregar_prod.doClick();
//        }

        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            int ultimaFila = tbl_buscar_prod.getRowCount() - 1;
            //System.out.println(ultimaFila);
            int filaSeleccionada = tbl_buscar_prod.getSelectedRow() - 1;
            //System.out.println("La fila seleccionada es:"+filaSeleccionada);
            if (tbl_buscar_prod.getSelectedRow() - 1 == -1) {
                //System.out.println("entro al if y selecciono la fila "+ filaSeleccionada);
                tbl_buscar_prod.changeSelection(ultimaFila, 0, false, false);
                btn_agregar_prod.doClick();
            } else {
                tbl_buscar_prod.changeSelection(tbl_buscar_prod.getSelectedRow() - 1, 0, false, false);
                btn_agregar_prod.doClick();
            }

        }
    }//GEN-LAST:event_tbl_buscar_prodKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        limpiarTablaProveedor();
        cargarFormProveedor();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
        new ReporteDeCompras().setVisible(true);
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
            java.util.logging.Logger.getLogger(Compras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Compras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add_provee;
    private javax.swing.JButton btn_agregar;
    private javax.swing.JButton btn_agregar_prod;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_comprar;
    private javax.swing.JButton btn_quitar;
    private javax.swing.JComboBox cmb_boleta;
    private javax.swing.JComboBox cmb_form_pago;
    private javax.swing.JDialog frm_buscar_prod;
    private javax.swing.JDialog frm_buscar_provee;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private com.toedter.calendar.JDateChooser jdc_fecha;
    private javax.swing.JLabel lbl_fecha;
    private javax.swing.JLabel lbl_igv;
    private javax.swing.JLabel lbl_logo1;
    private javax.swing.JLabel lbl_num_productos;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_salir;
    private javax.swing.JTable tbl_buscar_prod;
    private javax.swing.JTable tbl_productos_agregados;
    private javax.swing.JTable tbl_provee;
    private javax.swing.JTextField txtBuscarProveedor;
    private javax.swing.JTextField txtFecVenc;
    private javax.swing.JTextField txtLaboratorio;
    private javax.swing.JTextField txtLote;
    private javax.swing.JTextField txtPrecVentaUnidad;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPresentacion;
    private javax.swing.JTextField txtUtilidad;
    private javax.swing.JTextField txt_buscar_prod;
    private javax.swing.JTextField txt_cantidad;
    private javax.swing.JTextField txt_celular;
    private javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_igv;
    private javax.swing.JTextField txt_nom_prod;
    private javax.swing.JTextField txt_nom_raz;
    private javax.swing.JTextField txt_numero;
    private javax.swing.JTextField txt_precio_compra;
    private javax.swing.JTextField txt_ruc;
    private javax.swing.JTextField txt_serie;
    private javax.swing.JTextField txt_stock;
    private javax.swing.JTextField txt_subtotal;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables

    public void registrarCompra() {
        String nom_provee = txt_nom_raz.getText();
        String tipoDoc = cmb_boleta.getSelectedItem().toString();
        String numSerie = txt_serie.getText();
        String numDoc = txt_numero.getText();
        String formaPago = cmb_form_pago.getSelectedItem().toString();
        String fec = new ManejadorFechas().getFechaActualMySQL();
        String hora = new ManejadorFechas().getHoraActual();
        int filas = tbl_productos_agregados.getRowCount();
        double total = Double.parseDouble(txt_total.getText());
        String sql = "INSERT INTO "
                + "`tcompras`(`nom_provee`, `tipo_doc`,`num_serie` ,`num_doc_compra`, `forma_pago`, `fecha_compra`, `hora_compra`,estado , `total_compra`,`concepto`) "
                + "VALUES ('" + nom_provee + "','" + tipoDoc + "','" + numSerie + "','" + numDoc + "','" + formaPago + "', '" + fec + "', '" + hora + "',0," + total + ",'' )";
        try {
            Statement st = con.createStatement();
            int res = st.executeUpdate(sql);
            if (res > 0) {
                System.out.println("Compra Registrada");
            } else {
                System.out.println("Error al registrar la compra");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    public void registrarDetalleCompra() {
        int filas = tbl_productos_agregados.getRowCount();
        int idcompra = getNumCompra();
        try {
            Statement st = con.createStatement();
            for (int i = 0; i < filas; i++) {
                int idprod = Integer.parseInt(tbl_productos_agregados.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(tbl_productos_agregados.getValueAt(i, 2).toString());
                double precioU = Double.parseDouble(tbl_productos_agregados.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(tbl_productos_agregados.getValueAt(i, 4).toString());
                double igv = Double.parseDouble(txt_igv.getText());
                new Farma_inf().setNewStock(idprod, cantidad);
                new Farma_inf().updatePrecio(idprod, Double.parseDouble(tbl_productos_agregados.getValueAt(i, 7).toString()));
                String sql = "INSERT INTO `tdetalle_compra`(`id_compra`, `id_pro_medi`, `precio`, `cantidad`, `sub_total`, `descuento`, `igv`) VALUES (" + idcompra + "," + idprod + "," + precioU + "," + cantidad + "," + subtotal + ",0," + igv + "  )";
                int res = st.executeUpdate(sql);
                if (res > 0) {
                    System.out.println("Detalle registrado");
                }
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public int getNumCompra() {
        int id = 0;
        String sql = "SELECT MAX(`id_compra`) FROM `tcompras`";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = Integer.parseInt(rs.getString("MAX(`id_compra`)"));
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return id;
    }

//    public void updatePrecioVenta(int idProd, double precio){
//        String sql = "UPDATE `tproducto_medicamento` SET prec_uni_pro_medi`="+precio+" WHERE `id_pro_medi` = "+idProd;
//        try {
//            Statement st = con.createStatement();
//            int rs = st.executeUpdate(sql);
//            if (rs>0) {
//                System.out.println("Se actualizo el precio de venta del producto "+idProd);                
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
