package farmacia;

import Control.Conexion;
import Control.MyiReportVisor;
import Vista.Farma_inf;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Grover
 */
public class Kardex extends javax.swing.JFrame {

    Connection con = new Conexion().conectar();
    DefaultTableModel modelVentas, modelCompras, model_produc;
    HashMap parametros = new HashMap();
    MyiReportVisor mrv;

    public Kardex() {
        initComponents();
        //evento para capturar el valor de una celda jtable con doble click
        tblEntradas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent Mouse_evt) {
                JTable table = (JTable) Mouse_evt.getSource();
                Point point = Mouse_evt.getPoint();
                int row = table.rowAtPoint(point);
                if (Mouse_evt.getClickCount() == 2) {
                    System.out.println("" + tblEntradas.getValueAt(tblEntradas.getSelectedRow(), 1));
                }
            }
        });
        cabeceras();
        setLocationRelativeTo(null);
    }

    public void cabeceras() {
        String titulos[] = {"FECHA", "DETALLE", "CANTIDAD", "PRECIO", "SUB-TOTAL"};
        modelVentas = new DefaultTableModel(null, titulos);
        modelCompras = new DefaultTableModel(null, titulos);
        tblSalidas.setModel(modelVentas);
        tblEntradas.setModel(modelCompras);
    }

    public void cargarVentas(String nomProd) {
        limpiarTablaVentas();
        String sql = "SELECT tventa.fecha_venta, tproducto_medicamento.nom_pro_medi, tdetalleventa.cantidad, tproducto_medicamento.prec_venta, tdetalleventa.sub_total FROM `tventa` INNER JOIN tdetalleventa ON tventa.id_venta = tdetalleventa.id_venta INNER JOIN tproducto_medicamento ON tdetalleventa.id_pro_medi = tproducto_medicamento.id_pro_medi WHERE tproducto_medicamento.nom_pro_medi = '" + nomProd + "'";
        String datos[] = new String[5];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("tventa.fecha_venta");
                datos[1] = rs.getString("tproducto_medicamento.nom_pro_medi");
                datos[2] = rs.getString("tdetalleventa.cantidad");
                datos[3] = rs.getString("tproducto_medicamento.prec_venta");
                datos[4] = rs.getString("tdetalleventa.sub_total");
                modelVentas.addRow(datos);
            }
            tblSalidas.setModel(modelVentas);
            sumaDeSalidas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sumaDeEntradas() {
        int cantidad = 0;
        double precio = 0.0, subtotal = 0.0;
        int numFilas = tblEntradas.getRowCount();
        for (int i = 0; i < numFilas; i++) {
            cantidad += Integer.parseInt(tblEntradas.getValueAt(i, 2).toString());
            precio += Double.parseDouble(tblEntradas.getValueAt(i, 3).toString());
            subtotal += Double.parseDouble(tblEntradas.getValueAt(i, 4).toString());
        }
        lblCantidadEntradas.setText("" + cantidad);
        lblPrecioEntradas.setText("" + precio);
        lblTotalEntradas.setText("" + subtotal);
    }

    public void sumaDeSalidas() {
        int cantidad = 0;
        double precio = 0.0, subtotal = 0.0;
        int numFilas = tblSalidas.getRowCount();
        for (int i = 0; i < numFilas; i++) {
            cantidad += Integer.parseInt(tblSalidas.getValueAt(i, 2).toString());
            precio += Double.parseDouble(tblSalidas.getValueAt(i, 3).toString());
            subtotal += Double.parseDouble(tblSalidas.getValueAt(i, 4).toString());
        }
        lblCantidadSalidas.setText("" + cantidad);
        lblPrecioSalidas.setText("" + precio);
        lblTotalSalidas.setText("" + subtotal);
    }

    public void cargarCompras(String nomProd) {
        limpiarTablaCompras();
        String sql = "SELECT tcompras.fecha_compra, tproducto_medicamento.nom_pro_medi, tdetalle_compra.cantidad, tproducto_medicamento.prec_venta, tdetalle_compra.sub_total FROM `tcompras` INNER JOIN tdetalle_compra on tcompras.id_compra = tdetalle_compra.id_compra INNER JOIN tproducto_medicamento on tdetalle_compra.id_pro_medi = tproducto_medicamento.id_pro_medi WHERE tproducto_medicamento.nom_pro_medi = '" + nomProd + "'";
        String datos[] = new String[5];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("tcompras.fecha_compra");
                datos[1] = rs.getString("tproducto_medicamento.nom_pro_medi");
                datos[2] = rs.getString("tdetalle_compra.cantidad");
                datos[3] = rs.getString("tproducto_medicamento.prec_venta");
                datos[4] = rs.getString("tdetalle_compra.sub_total");
                modelCompras.addRow(datos);
            }
            tblEntradas.setModel(modelCompras);
            sumaDeEntradas();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void limpiarTablaVentas() {
        for (int i = 0; i < tblSalidas.getRowCount(); i++) {
            modelVentas.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarTablaCompras() {
        for (int i = 0; i < tblEntradas.getRowCount(); i++) {
            modelCompras.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarTabla() {
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
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtProducto = new javax.swing.JTextField();
        btnMostrar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProveedor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        btnAgregarProducto = new javax.swing.JButton();
        jdcHasta = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jdcDesde = new com.toedter.calendar.JDateChooser();
        cmbTodo = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEntradas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSalidas = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        btnCrearReporte = new javax.swing.JButton();
        lblTotalEntradas = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblCantidadEntradas = new javax.swing.JLabel();
        lblPrecioEntradas = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblCantidadSalidas = new javax.swing.JLabel();
        lblPrecioSalidas = new javax.swing.JLabel();
        lblTotalSalidas = new javax.swing.JLabel();

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
        setTitle("Lista de Medicamentos");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATOS DE PRODUCTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 1, 14))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setText("STOCK");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new java.awt.Color(255, 255, 153));
        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 70, -1));

        txtProducto.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtProducto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txtProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 201, -1));

        btnMostrar.setBackground(new java.awt.Color(51, 153, 0));
        btnMostrar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnMostrar.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrar.setText("MOSTRAR");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnMostrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 110, 30));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setText("CÓDIGO");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("PRODUCTO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        txtProveedor.setEditable(false);
        txtProveedor.setBackground(new java.awt.Color(255, 255, 153));
        txtProveedor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txtProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 200, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setText("PROVEEDOR");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        txtStock.setEditable(false);
        txtStock.setBackground(new java.awt.Color(255, 255, 153));
        txtStock.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtStock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 70, -1));

        btnAgregarProducto.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btnAgregarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/buscar.png"))); // NOI18N
        btnAgregarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarProductoActionPerformed(evt);
            }
        });
        jPanel1.add(btnAgregarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 40, -1));

        jdcHasta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel1.add(jdcHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 140, 30));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel1.setText("HASTA");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, -1, -1));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setText("DESDE");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, -1, -1));

        jdcDesde.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel1.add(jdcDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, 140, 30));

        cmbTodo.setText("TODO");
        jPanel1.add(cmbTodo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, -1, -1));

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("PARÁMETROS DE BÚSQUEDA");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 180, 20));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 290, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 720, 180));

        tblEntradas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblEntradas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 710, 150));

        jPanel2.setBackground(new java.awt.Color(204, 102, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SALIDAS - VENTAS");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 0, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 730, 20));

        jPanel3.setBackground(new java.awt.Color(0, 102, 204));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("KARDEX");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 730, -1));

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("ENTRADAS - COMPRAS");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, -1, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 730, 20));

        tblSalidas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblSalidas);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 710, 140));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 60));

        btnCrearReporte.setText("CREAR REPORTE");
        btnCrearReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearReporteActionPerformed(evt);
            }
        });
        getContentPane().add(btnCrearReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 690, -1, -1));

        lblTotalEntradas.setText("TOTALES");
        getContentPane().add(lblTotalEntradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 470, -1, -1));

        jLabel8.setText("TOTALES");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        lblCantidadEntradas.setText("TOTALES");
        getContentPane().add(lblCantidadEntradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 470, -1, -1));

        lblPrecioEntradas.setText("TOTALES");
        getContentPane().add(lblPrecioEntradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 470, -1, -1));

        jLabel14.setText("TOTALES");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 670, -1, -1));

        lblCantidadSalidas.setText("TOTALES");
        getContentPane().add(lblCantidadSalidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 670, -1, -1));

        lblPrecioSalidas.setText("TOTALES");
        getContentPane().add(lblPrecioSalidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 670, -1, -1));

        lblTotalSalidas.setText("TOTALES");
        getContentPane().add(lblTotalSalidas, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 670, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        if (!txtProducto.getText().trim().isEmpty()) {
            String producto = txtProducto.getText();
            cargarVentas(producto);
            cargarCompras(producto);
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "INDIQUE UN PRODUCTO");
        }

//        if (!txtProducto.getText().trim().isEmpty() || !txtCodigo.getText().trim().isEmpty()) {
//            String producto = "";
//            int codigo = 0;
//
//            
//        } else {
//            JOptionPane.showMessageDialog(getRootPane(), "INDIQUE UN PRODUCTO O CODIGO");
//        }

    }//GEN-LAST:event_btnMostrarActionPerformed

    private void txt_buscar_prodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscar_prodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscar_prodActionPerformed

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

    private void btnAgregarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarProductoActionPerformed
        frm_buscar_prod.setVisible(true);
        cargarFormProductos();
    }//GEN-LAST:event_btnAgregarProductoActionPerformed

    private void btnCrearReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearReporteActionPerformed
        int idProd = Integer.parseInt(txtCodigo.getText());
        parametros.put("idProd", idProd);
        mrv = new MyiReportVisor(System.getProperty("user.dir") + "\\reportes\\kardex.jrxml", parametros);
        mrv.exportarAPdf();
    }//GEN-LAST:event_btnCrearReporteActionPerformed

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
            java.util.logging.Logger.getLogger(Kardex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kardex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kardex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kardex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kardex().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnCrearReporte;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton btn_agregar_prod;
    private javax.swing.JCheckBox cmbTodo;
    private javax.swing.JDialog frm_buscar_prod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator6;
    private com.toedter.calendar.JDateChooser jdcDesde;
    private com.toedter.calendar.JDateChooser jdcHasta;
    private javax.swing.JLabel lblCantidadEntradas;
    private javax.swing.JLabel lblCantidadSalidas;
    private javax.swing.JLabel lblPrecioEntradas;
    private javax.swing.JLabel lblPrecioSalidas;
    private javax.swing.JLabel lblTotalEntradas;
    private javax.swing.JLabel lblTotalSalidas;
    private javax.swing.JTable tblEntradas;
    private javax.swing.JTable tblSalidas;
    private javax.swing.JTable tbl_buscar_prod;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtProducto;
    private javax.swing.JTextField txtProveedor;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txt_buscar_prod;
    // End of variables declaration//GEN-END:variables
public void buscarProductos() {
        limpiarTabla();
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

    public void getProducto(int fila) {
        String codProd = tbl_buscar_prod.getValueAt(fila, 0).toString();
        String nom_prod = tbl_buscar_prod.getValueAt(fila, 1).toString();
        int stock = Integer.parseInt(tbl_buscar_prod.getValueAt(fila, 5).toString());
        String proveedor = new Farma_inf().getNomProveedor(nom_prod);
        txtProducto.setText(nom_prod);
        txtCodigo.setText(codProd);
        txtStock.setText("" + stock);
        txtProveedor.setText(proveedor);
    }
}
