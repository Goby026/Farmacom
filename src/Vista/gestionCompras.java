/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public final class gestionCompras extends javax.swing.JFrame {

    Connection con = new Conexion().conectar();
    DefaultTableModel modelCompras, modelDetalleCompra, model_produc;

    public gestionCompras() {
        initComponents();
        setLocationRelativeTo(null);
        titulos();
        bloquearControlesCompra();
        bloquearControlesDetalleCompra();
    }

    public void titulos() {
        String titulos[] = {"N° COMPRA", "PROVEEDOR", "DOCUMENTO", "N° SERIE", "N°", "FORMA DE PAGO", "FECHA", "HORA", "ESTADO"};
        String titulos2[] = {"ID","COD. PROD", "PRODUCTO", "CANTIDAD", "PRECIO", "SUBTOTAL"};
        modelCompras = new DefaultTableModel(null, titulos);
        modelDetalleCompra = new DefaultTableModel(null, titulos2);
        tblCompras.setModel(modelCompras);
        tblDetalleCompras.setModel(modelDetalleCompra);
        
        tblDetalleCompras.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblDetalleCompras.getColumnModel().getColumn(1).setPreferredWidth(10);
        tblDetalleCompras.getColumnModel().getColumn(2).setPreferredWidth(200);
        tblDetalleCompras.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblDetalleCompras.getColumnModel().getColumn(4).setPreferredWidth(50);
        tblDetalleCompras.getColumnModel().getColumn(5).setPreferredWidth(50);
    }

    public void cargarRangoCompras() {
        limpiarTablaCompras();
        Date inicio = jdc_inicio.getDate();
        Date fin = jdc_fin.getDate();
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

    public void cargarTablaCompras() {
        limpiarTablaCompras();
        String datos[] = new String[9];
        String sql = "SELECT `id_compra`, `nom_provee`, `tipo_doc`, `num_serie`, `num_doc_compra`, `forma_pago`, `fecha_compra`, `hora_compra`, estado FROM `tcompras`";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
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
            System.out.println(e.getMessage());
        }
    }
    
    public void cargarTablaComprasConId(int id) {
        limpiarTablaCompras();
        String datos[] = new String[9];
        String sql = "SELECT `id_compra`, `nom_provee`, `tipo_doc`, `num_serie`, `num_doc_compra`, `forma_pago`, `fecha_compra`, `hora_compra`, estado FROM `tcompras` WHERE id_compra ="+id;
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
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
            System.out.println(e.getMessage());
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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
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
    
    public void limpiarControlesCompra(){
        txtNumCompra.setText("");
        txtSerie.setText("");
        txtNum.setText("");
        txtHora.setText("");
    }
    
    public void bloquearControlesCompra(){
        txtNumCompra.setEnabled(false);
        cmbDocumento.setEnabled(false);
        cmbProveedor.setEnabled(false);
        txtSerie.setEnabled(false);
        txtNum.setEnabled(false);
        cmbFormaPago.setEnabled(false);
        txtHora.setEnabled(false);
        jdcFecha.setEnabled(false);
        cmbEstado.setEnabled(false);
        btnAnularCompra.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnModificarCompra.setEnabled(false);
    }
    public void desbloquearControlesCompra(){
        txtNumCompra.setEnabled(true);
        cmbDocumento.setEnabled(true);
        cmbProveedor.setEnabled(true);
        txtSerie.setEnabled(true);
        txtNum.setEnabled(true);
        cmbFormaPago.setEnabled(true);
        txtHora.setEnabled(true);
        jdcFecha.setEnabled(true);
        cmbEstado.setEnabled(true);
        btnAnularCompra.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnModificarCompra.setEnabled(true);
    }
    
    public void bloquearControlesDetalleCompra(){
        txtIdProd.setEnabled(false);
        txtNomProd.setEnabled(false);
        txtCantidad.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtSubtotal.setEnabled(false);
        btnEliminarDetalle.setEnabled(false);
        btnBuscarProd.setEnabled(false);
        btnModificarDetalleCompra.setEnabled(false);
    }
    
    public void desbloquearControlesDetalleCompra(){
        txtIdProd.setEnabled(true);
        txtNomProd.setEnabled(true);
        txtCantidad.setEnabled(true);
        txtPrecio.setEnabled(true);
        txtSubtotal.setEnabled(true);
        btnEliminarDetalle.setEnabled(true);
        btnBuscarProd.setEnabled(true);
        btnModificarDetalleCompra.setEnabled(true);
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
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCompras = new javax.swing.JTable();
        btnAnularCompra = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtNumCompra = new javax.swing.JTextField();
        txtSerie = new javax.swing.JTextField();
        txtNum = new javax.swing.JTextField();
        cmbFormaPago = new javax.swing.JComboBox();
        cmbProveedor = new javax.swing.JComboBox();
        cmbDocumento = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jdcFecha = new com.toedter.calendar.JDateChooser();
        jLabel25 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox();
        btnModificarCompra = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDetalleCompras = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        btnEliminarDetalle = new javax.swing.JButton();
        txtSubtotal = new javax.swing.JTextField();
        txtIdProd = new javax.swing.JTextField();
        txtNomProd = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        btnBuscarProd = new javax.swing.JButton();
        btnModificarDetalleCompra = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jdc_inicio = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jdc_fin = new com.toedter.calendar.JDateChooser();
        btn_listar = new javax.swing.JButton();
        txtCompraNum = new javax.swing.JTextField();
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

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setText("BUSCAR");
        frm_buscar_prod.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 102, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTIÓN COMPRAS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 240, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1310, 60));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LISTA DE COMPRAS REALIZADAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tblCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComprasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCompras);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 870, 190));

        btnAnularCompra.setBackground(new java.awt.Color(255, 0, 0));
        btnAnularCompra.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnAnularCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnAnularCompra.setText("ANULAR COMPRA");
        btnAnularCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnularCompraActionPerformed(evt);
            }
        });
        jPanel2.add(btnAnularCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, -1, -1));

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setText("DOCUMENTO");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 60, -1, -1));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setText("PROVEEDOR");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 110, -1, -1));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel15.setText("N°");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 200, -1, -1));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setText("FORMA DE PAGO");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 20, -1, -1));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setText("SERIE");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 170, -1, -1));

        jLabel24.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel24.setText("N° COMPRA");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 30, -1, -1));

        txtNumCompra.setEditable(false);
        txtNumCompra.setBackground(new java.awt.Color(255, 255, 153));
        txtNumCompra.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtNumCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 30, 60, -1));

        txtSerie.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtSerie.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtSerie, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 170, 70, -1));

        txtNum.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 200, 70, -1));

        cmbFormaPago.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbFormaPago.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CONTADO", "CRÉDITO" }));
        jPanel2.add(cmbFormaPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 40, 120, -1));

        cmbProveedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jPanel2.add(cmbProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 130, 160, -1));

        cmbDocumento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbDocumento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "BOLETA", "FACTURA", "GUÍA DE REMISIÓN" }));
        jPanel2.add(cmbDocumento, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 80, 160, -1));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setText("HORA");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 70, -1, -1));

        txtHora.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtHora.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel2.add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 90, 120, -1));

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel18.setText("ESTADO");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 170, -1, -1));
        jPanel2.add(jdcFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 140, 120, -1));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel25.setText("FECHA");
        jPanel2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 120, -1, -1));

        cmbEstado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ACTIVO", "ANULADO" }));
        jPanel2.add(cmbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 190, 120, -1));

        btnModificarCompra.setBackground(new java.awt.Color(0, 153, 0));
        btnModificarCompra.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnModificarCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarCompra.setText("MODIFICAR");
        btnModificarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarCompraActionPerformed(evt);
            }
        });
        jPanel2.add(btnModificarCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 240, 230, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 1290, 290));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PRODUCTOS COMPRADOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tblDetalleCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetalleComprasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDetalleCompras);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 880, 160));

        jButton3.setText("MODIFICAR");
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel19.setText("N° PRODUCTO");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 30, -1, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel20.setText("NOM. PRODUCTO");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 60, -1, -1));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setText("CANTIDAD");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 110, -1, -1));

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel22.setText("PRECIO");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 140, -1, -1));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel23.setText("SUBTOTAL");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 170, -1, -1));

        btnEliminarDetalle.setText("ELIMINAR");
        btnEliminarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarDetalleActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminarDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        txtSubtotal.setBackground(new java.awt.Color(255, 255, 153));
        txtSubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(txtSubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 170, 90, -1));

        txtIdProd.setEditable(false);
        txtIdProd.setBackground(new java.awt.Color(255, 255, 153));
        txtIdProd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(txtIdProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 30, 90, -1));

        txtNomProd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(txtNomProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 80, 220, -1));

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 110, 90, -1));

        txtPrecio.setBackground(new java.awt.Color(255, 255, 153));
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel3.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 140, 90, -1));

        btnBuscarProd.setText("...");
        btnBuscarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarProdActionPerformed(evt);
            }
        });
        jPanel3.add(btnBuscarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 70, -1, -1));

        btnModificarDetalleCompra.setBackground(new java.awt.Color(0, 153, 0));
        btnModificarDetalleCompra.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnModificarDetalleCompra.setForeground(new java.awt.Color(255, 255, 255));
        btnModificarDetalleCompra.setText("MODIFICAR");
        btnModificarDetalleCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDetalleCompraActionPerformed(evt);
            }
        });
        jPanel3.add(btnModificarDetalleCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 200, 220, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 1290, 250));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("N° COMPRA");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 70, -1, -1));

        jLabel2.setText("INICIO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, -1, -1));

        jdc_inicio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jdc_inicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jdc_inicioMouseClicked(evt);
            }
        });
        getContentPane().add(jdc_inicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 70, 160, 30));

        jLabel3.setText("FIN");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 100, -1, -1));

        jdc_fin.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getContentPane().add(jdc_fin, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 160, 30));

        btn_listar.setText("LISTAR");
        btn_listar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_listarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_listar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 70, -1, 30));

        txtCompraNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCompraNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCompraNumKeyReleased(evt);
            }
        });
        getContentPane().add(txtCompraNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 70, 80, 30));

        cmbMostrar.setText("TODAS LA COMPRAS");
        cmbMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMostrarActionPerformed(evt);
            }
        });
        getContentPane().add(cmbMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jdc_inicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jdc_inicioMouseClicked

    }//GEN-LAST:event_jdc_inicioMouseClicked

    private void btn_listarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_listarActionPerformed
        try {
            cargarRangoCompras();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Indique un rango de fechas");
        }
    }//GEN-LAST:event_btn_listarActionPerformed

    private void setConcepto(int idCompra, String concepto) {
        String sql = "UPDATE `tcompras` SET `concepto`= '" + concepto + "' WHERE `id_compra` = " + idCompra + "";
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("concepto de la compra " + idCompra + " actualizado");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void restarStock(int idProd, int cantidad) {
        int stock = new Farma_inf().getStock(idProd) - cantidad;
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

    private void setEstado_anulado(int idCompra) {
        String sql = "UPDATE `tcompras` SET `estado`= '1' WHERE `id_compra` = " + idCompra + "";
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Estado de la compra " + idCompra + " ANULADO");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void btnAnularCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularCompraActionPerformed
        if (tblCompras.getRowCount() >= 0) {
            int numDetallesFilas = tblDetalleCompras.getRowCount();
            int fila = tblCompras.getSelectedRow();
            String estado = tblCompras.getValueAt(fila, 8).toString();
            int idCompra = Integer.parseInt(tblCompras.getValueAt(fila, 0).toString());

            if (estado.equals("ACTIVO")) {
                String concepto = JOptionPane.showInputDialog("INGRESE CONCEPTO DE ANULACIÓN");
                setConcepto(idCompra, concepto);
                String sql = "UPDATE tdetalle_compra SET sub_total = 0.0 WHERE tdetalle_compra.id_compra =" + idCompra;
                //Ejecucion de instrucciones
                try {
                    Statement st = con.createStatement();
                    for (int i = 0; i < numDetallesFilas; i++) {
                        int idprod = Integer.parseInt(tblDetalleCompras.getValueAt(i, 0).toString());
                        int cantidad = Integer.parseInt(tblDetalleCompras.getValueAt(i, 3).toString());

                        restarStock(idprod, cantidad);

                        int rs = st.executeUpdate(sql);
                        if (rs > 0) {
                            //JOptionPane.showMessageDialog(getRootPane(), "SE ANULO LA VENTA");
                            System.out.println("Se actualizo el monto de la compra " + idCompra);
                            setEstado_anulado(idCompra);
                            cargarCompraConId(idCompra);
                            cargarDetalleCompra(idCompra);
                            limpiarControlesCompra();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                JOptionPane.showMessageDialog(getRootPane(), "SE ANULÓ LA COMPRA");
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "LA COMPRA SELECCIONADA YA FUÉ ANULADA");
            }

        } else {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UNA COMPRA");
        }
    }//GEN-LAST:event_btnAnularCompraActionPerformed

    private void cmbMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMostrarActionPerformed
        if (cmbMostrar.isSelected()) {
            cargarTablaCompras();
            jdc_inicio.setEnabled(false);
            jdc_fin.setEnabled(false);
            btn_listar.setEnabled(false);
            txtCompraNum.setEnabled(false);
        } else {
            limpiarTablaCompras();
            limpiarTablaDetalleCompras();
            jdc_inicio.setEnabled(true);
            jdc_fin.setEnabled(true);
            btn_listar.setEnabled(true);
            txtCompraNum.setEnabled(true);
        }
    }//GEN-LAST:event_cmbMostrarActionPerformed

    public void getNomProveedor() {
        cmbProveedor.removeAllItems();
        String sql = "SELECT nom_provee FROM `tproveedor` order by id_provee";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cmbProveedor.addItem(rs.getString("nom_provee"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void tblComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComprasMouseClicked
        int fila = tblCompras.getSelectedRow();
        if (fila >= 0) {
            try {
                desbloquearControlesCompra();
                int id = Integer.parseInt(tblCompras.getValueAt(fila, 0).toString());
                txtNumCompra.setText("" + id);//seteo el numero de Compra en el txt de Compra para modificar
                String doc = tblCompras.getValueAt(fila, 2).toString();
                if (doc.equals("BOLETA") || doc.equals("Boleta")) {
                    cmbDocumento.setSelectedIndex(0);
                } else if (doc.equals("FACTURA") || doc.equals("Factura")) {
                    cmbDocumento.setSelectedIndex(1);
                } else if (doc.equals("GUÍA DE REMISIÓN") || doc.equals("Guía de remisión")) {
                    cmbDocumento.setSelectedIndex(2);
                }
                getNomProveedor();
                String nomProveedor = tblCompras.getValueAt(fila, 1).toString();
                cmbProveedor.setSelectedItem(nomProveedor);
                String numSerie = tblCompras.getValueAt(fila, 3).toString();
                String num = tblCompras.getValueAt(fila, 4).toString();
                txtSerie.setText(numSerie);
                txtNum.setText(num);
                String formaPago = tblCompras.getValueAt(fila, 5).toString();
                cmbFormaPago.setSelectedItem(formaPago);
                String fecha = tblCompras.getValueAt(fila, 6).toString();//capturo la fecha de la tabla
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//defino un formato
                Date fechaDate = sdf.parse(fecha);//asigno el formato a la fecha que capturé
                jdcFecha.setDate(fechaDate);//seteo la fecha con el formato en el JDateChooser
                txtHora.setText(tblCompras.getValueAt(fila, 7).toString());//seteo la hora en un txt para modificar
                String estado = tblCompras.getValueAt(fila, 8).toString();
                if (estado.equals("ACTIVO")) {
                    cmbEstado.setSelectedIndex(0);
                } else {
                    cmbEstado.setSelectedIndex(1);
                }
                btnAnularCompra.setEnabled(true);
                cargarDetalleCompra(id);
            } catch (ParseException ex) {
                Logger.getLogger(gestionVentas.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
        }
    }//GEN-LAST:event_tblComprasMouseClicked

    private void tblDetalleComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetalleComprasMouseClicked
        int fila = tblDetalleCompras.getSelectedRow();
        if (fila >= 0) {
            desbloquearControlesDetalleCompra();
            int idProd = Integer.parseInt(tblDetalleCompras.getValueAt(fila, 1).toString());
            String nomProd = tblDetalleCompras.getValueAt(fila, 2).toString();
            int cantidad = Integer.parseInt(tblDetalleCompras.getValueAt(fila, 3).toString());
            double precio = Double.parseDouble(tblDetalleCompras.getValueAt(fila, 4).toString());
            double subTotal = Double.parseDouble(tblDetalleCompras.getValueAt(fila, 5).toString());
            txtIdProd.setText("" + idProd);
            txtNomProd.setText(nomProd);
            txtCantidad.setText("" + cantidad);
            txtPrecio.setText("" + precio);
            txtSubtotal.setText("" + subTotal);
        } else {
        }
    }//GEN-LAST:event_tblDetalleComprasMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int opc = JOptionPane.showOptionDialog(btnEliminar, "¿ESTA SEGURO QUE DESEA ELIMINAR ESTE REGISTRO?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
        if (opc == 0) {
            int fila = tblCompras.getSelectedRow();//capturo la fila seleccionada
            int numCompra = Integer.parseInt(tblCompras.getValueAt(fila, 0).toString());//capturo el primer elemento de la fila seleccionada
            eliminarDetalleCompra(numCompra);
            eliminarCompra(numCompra);
            JOptionPane.showMessageDialog(getRootPane(), "SE ELIMINÓ LA COMPRA N°: " + numCompra);
        } else {

        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarCompraActionPerformed
        int numCompra = Integer.parseInt(txtNumCompra.getText());
        int estado = 0;

        String documento = cmbDocumento.getSelectedItem().toString();
        String proveedor = cmbProveedor.getSelectedItem().toString();
        String serie = txtSerie.getText();
        String numDocCompra = txtNumCompra.getText();
        String formPago = cmbFormaPago.getSelectedItem().toString();
        String hora = txtHora.getText();
        Date fecha = jdcFecha.getDate();//capturo la fecha del jdatechooser
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");//creo un formato para la fecha Date que capture
        String fechaDate = sdf.format(fecha);//le paso el formato a la fecha que capture
        
        String status = cmbEstado.getSelectedItem().toString();
        if (cmbEstado.getSelectedIndex() == 0) {
            estado = 0;
        } else {
            estado = 1;
        }

        String sql = "UPDATE `tcompras` SET `nom_provee`='"+proveedor+"',`tipo_doc`='"+documento+"',`num_serie`='"+serie+"',`num_doc_compra`='"+numDocCompra+"',`forma_pago`='"+formPago+"',`fecha_compra`='"+fechaDate+"',`hora_compra`='"+hora+"',`estado`='"+estado+"' WHERE `id_compra` = " + numCompra;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                JOptionPane.showMessageDialog(getRootPane(), "SE ACTUALIZO LA COMPRA");
                cargarTablaCompras();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }//GEN-LAST:event_btnModificarCompraActionPerformed

    private void btnEliminarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarDetalleActionPerformed
        int fila = tblDetalleCompras.getSelectedRow();
        int id = Integer.parseInt(tblDetalleCompras.getValueAt(fila, 0).toString());
        eliminarDetalleDeUnaCompra(id);
    }//GEN-LAST:event_btnEliminarDetalleActionPerformed

    private void btnModificarDetalleCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDetalleCompraActionPerformed
        int fila = tblDetalleCompras.getSelectedRow();
        int id = Integer.parseInt(tblDetalleCompras.getValueAt(fila, 0).toString());
        String producto = txtNomProd.getText();
        int idProd = new Farma_inf().getIdProducto(producto);
        int cantidad = Integer.parseInt(txtCantidad.getText());
        double precio = Double.parseDouble(txtPrecio.getText());
        double subTotal = Double.parseDouble(txtSubtotal.getText());
        //UPDATE `farmacom`.`tdetalleventa` SET `id_pro_medi` = '12' WHERE `tdetalleventa`.`id` = 8;
        String sql = "UPDATE tdetalle_compra SET `id_pro_medi` ="+idProd+",precio = "+precio+",`cantidad`="+cantidad+",`sub_total`="+subTotal+"  WHERE `tdetalle_compra`.`id` = "+id;
        try {
            Statement st =con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs>0) {
                JOptionPane.showMessageDialog(getRootPane(), "SE ACTUALIZÓ EL PRODUCTO DE LA COMPRA");
                int filaCompra = tblCompras.getSelectedRow();
                cargarDetalleCompra(Integer.parseInt(tblCompras.getValueAt(filaCompra, 0).toString()));
            } else {
            }
            st.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }finally{
            try {
                con.close();                
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnModificarDetalleCompraActionPerformed

    private void txt_buscar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscar_prodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscar_prodActionPerformed

    
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
    
    public void getProducto(int fila) {
        int codProd = Integer.parseInt(tbl_buscar_prod.getValueAt(fila, 0).toString());
        String nomProd = tbl_buscar_prod.getValueAt(fila, 1).toString();
        //double precProd = new Farma_inf().getPrecioProducto(nomProd,codProd);
        
        txtIdProd.setText(""+codProd);
        txtNomProd.setText(nomProd);
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtSubtotal.setText("");
    }
    private void txt_buscar_prodKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscar_prodKeyReleased
        buscarProductos();
    }//GEN-LAST:event_txt_buscar_prodKeyReleased

    private void btn_agregar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_prodActionPerformed
        int fila = tbl_buscar_prod.getSelectedRow();
        if (fila >= 0) {
            getProducto(fila);
            frm_buscar_prod.dispose();
        } else {
            JOptionPane.showMessageDialog(frm_buscar_prod.getRootPane(), "SELECCIONAR UN PRODUCTO");
        }
    }//GEN-LAST:event_btn_agregar_prodActionPerformed

    private void btnBuscarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarProdActionPerformed
        frm_buscar_prod.setVisible(true);
        frm_buscar_prod.setAlwaysOnTop(true);
        cargarFormProductos();
    }//GEN-LAST:event_btnBuscarProdActionPerformed

    
    public void cargarCompraConId(int id) {
        limpiarTablaCompras();
        String sql = "SELECT `id_compra`, `nom_provee`, `tipo_doc`, `num_serie`, `num_doc_compra`, `forma_pago`, `fecha_compra`, `hora_compra`, estado FROM `tcompras` WHERE id_compra = "+id;
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
    private void txtCompraNumKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCompraNumKeyReleased
        int id = 0;
        try {
            
            if (!txtCompraNum.getText().trim().isEmpty()) {
                id = Integer.parseInt(txtCompraNum.getText());
                cargarCompraConId(id);
            } else {
                limpiarTablaCompras();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_txtCompraNumKeyReleased

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
            java.util.logging.Logger.getLogger(gestionCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gestionCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gestionCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gestionCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gestionCompras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnularCompra;
    private javax.swing.JButton btnBuscarProd;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarDetalle;
    private javax.swing.JButton btnModificarCompra;
    private javax.swing.JButton btnModificarDetalleCompra;
    private javax.swing.JButton btn_agregar_prod;
    private javax.swing.JButton btn_listar;
    private javax.swing.JComboBox cmbDocumento;
    private javax.swing.JComboBox cmbEstado;
    private javax.swing.JComboBox cmbFormaPago;
    private javax.swing.JCheckBox cmbMostrar;
    private javax.swing.JComboBox cmbProveedor;
    private javax.swing.JDialog frm_buscar_prod;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator6;
    private com.toedter.calendar.JDateChooser jdcFecha;
    private com.toedter.calendar.JDateChooser jdc_fin;
    private com.toedter.calendar.JDateChooser jdc_inicio;
    private javax.swing.JTable tblCompras;
    private javax.swing.JTable tblDetalleCompras;
    private javax.swing.JTable tbl_buscar_prod;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCompraNum;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtIdProd;
    private javax.swing.JTextField txtNomProd;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtNumCompra;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txt_buscar_prod;
    // End of variables declaration//GEN-END:variables

    //metodo para elminar producto del detalle de una compra
    public void eliminarDetalleDeUnaCompra(int id) {
        String sql = "DELETE FROM `tdetalle_compra` WHERE `id` = " + id;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Se borró el detalle :" + id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void eliminarDetalleCompra(int numCompra) {
        String sql = "DELETE FROM `tdetalle_compra` WHERE `id_compra` = " + numCompra;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Se borraron los detalles de la compra n°: " + numCompra);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void eliminarCompra(int numCompra) {
        String sql = "DELETE FROM `tcompras` WHERE `id_compra`= " + numCompra;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Se borró la venta n°: " + numCompra);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
