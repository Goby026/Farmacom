/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmacia;
import Control.Conexion;
import Control.ManejadorFechas;
import Vista.Farma_inf;
import Vista.Laboratorio;
import Vista.LaboratorioDAO;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

/**
 *
 * @author Gaby
 */
public final class Reg_producto extends javax.swing.JFrame {

    int posx, posy;
    Conexion con = new Conexion();
    Connection cc = con.conectar();
    DefaultTableModel table1;
    int id,Fraccion,Stock;
    double PrecUnitario,ProdCompleto;
    String Codigo, Nombre,Concentracion,Formato,FormatoSimplificado,Presentacion,FecVencimiento,NumRegSanitario,Proveedor;
    
    public Reg_producto() {
       // setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        frm_nuevo.setTitle("Registrar producto");
        frm_modificar.setTitle("Modificar producto");
        frm_nuevo.getContentPane().setBackground(Color.WHITE);
        frm_modificar.getContentPane().setBackground(Color.WHITE);
        this.getContentPane().setBackground(Color.WHITE);
        txt_buscar.requestFocus();
        llbl_pie.setText(new Farma_inf().pie());
        bloquear();
        cargarTituloTabla1();
        cargarProductos();
    }
    
    public void cargarTituloTabla1() {
        String[] cabecera1 = {"ID", "CODIGO","NOMBRE", "CONCENTRACION","PRESENTACIÓN","LABORATORIO","LOTE","P. VENTA","STOCK"};
        table1 = new DefaultTableModel(null, cabecera1);
        tbl_productos.setModel(table1);
        tbl_productos.getColumnModel().getColumn(0).setPreferredWidth(10);
        tbl_productos.getColumnModel().getColumn(1).setPreferredWidth(10);
        tbl_productos.getColumnModel().getColumn(2).setPreferredWidth(200);
        tbl_productos.getColumnModel().getColumn(3).setPreferredWidth(50);
        tbl_productos.getColumnModel().getColumn(4).setPreferredWidth(50);
        tbl_productos.getColumnModel().getColumn(5).setPreferredWidth(50);
        tbl_productos.getColumnModel().getColumn(6).setPreferredWidth(50);
        tbl_productos.getColumnModel().getColumn(7).setPreferredWidth(50);
        tbl_productos.getColumnModel().getColumn(8).setPreferredWidth(20);
     }
    
    public void bloquear(){
        btn_modify.setEnabled(false);
        btn_delete.setEnabled(false);
    }
    
    public void desbloquear(){
        btn_modify.setEnabled(true);
        btn_delete.setEnabled(true);
    }
    
    public void cargarProductos() {
        String datos[] = new String[9];
        String sql = "SELECT `id_pro_medi`, cod_prod, `nom_pro_medi` , concentracion_pro_medi ,`presentacion_pro_medi`,proveedor ,lote ,`prec_venta`, `stock_pro_medi` FROM `tproducto_medicamento`";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("cod_prod");
                datos[2] = rs.getString("nom_pro_medi");
                datos[3] = rs.getString("concentracion_pro_medi");
                datos[4] = rs.getString("presentacion_pro_medi");
                datos[5] = rs.getString("proveedor");
                datos[6] = rs.getString("lote");
                datos[7] = rs.getString("prec_venta");
                datos[8] = String.valueOf(rs.getInt("stock_pro_medi"));
                
                table1.addRow(datos);
            }
            tbl_productos.setModel(table1);
            int filas = tbl_productos.getRowCount();
            txt_registros.setText("" + filas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void cargarProductosText(String prod) {
        
        String datos[] = new String[9];
        String sql = "SELECT `id_pro_medi`, cod_prod, `nom_pro_medi` , concentracion_pro_medi ,`presentacion_pro_medi`, `lote`,`prec_venta`, `provee_labo_pro_medi`, `stock_pro_medi` FROM `tproducto_medicamento` WHERE nom_pro_medi LIKE '" + prod + "%' OR nom_pro_medi LIKE '%" + prod + "'";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("cod_prod");
                datos[2] = rs.getString("nom_pro_medi");
                datos[3] = rs.getString("concentracion_pro_medi");
                datos[4] = rs.getString("presentacion_pro_medi");
                datos[5] = rs.getString("prec_venta");
                datos[6] = rs.getString("provee_labo_pro_medi");
                datos[7] = String.valueOf(rs.getInt("stock_pro_medi"));
                datos[8] = rs.getString("lote");
                table1.addRow(datos);
            }
            tbl_productos.setModel(table1);
            int filas = tbl_productos.getRowCount();
            txt_registros.setText("" + filas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    public int comprobar() {
        if (!txtNewProd.getText().trim().isEmpty()) {
            txtNewProd.transferFocus();
            if (!txtNewNombre.getText().trim().isEmpty()) {
                txtNewNombre.transferFocus();
                if (!txtNewConcentracion.getText().trim().isEmpty()) {
                    txtNewConcentracion.transferFocus(); 
                            if (!txtNewPresentacion.getText().trim().isEmpty()) {
                                txtNewPresentacion.transferFocus();
                                    if (!txtPrecioBlister.getText().trim().isEmpty()) {
                                        txtPrecioBlister.transferFocus();
                                        if (!txtLote.getText().trim().isEmpty()) {
                                                txtLote.transferFocus();
                                                 if (!txtNewPrecVenta.getText().trim().isEmpty()) {
                                                    txtNewPrecVenta.transferFocus();
                                                if (!txtNewStock.getText().trim().isEmpty()) {
                                                    txtNewStock.transferFocus();
                                                    return 1;
                                                } else {
                                                    JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE STOCK");
                                                    txtNewStock.requestFocus();
                                                }
                                        } else {
                                            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE PRECIO VENTA");
                                            txtNewPrecVenta.requestFocus();
                                        }
                                              } else {
                                        JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE LOTE");
                                        txtLote.requestFocus();
                                    }
                                    } else {
                                        JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE PRECIO DE BLISTER");
                                        txtPrecioBlister.requestFocus();
                                    }
                            } else {
                                JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE PRESENTACION");
                                txtNewPresentacion.requestFocus();
                            }
                } else {
                    JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE CONCENTRACION");
                    txtNewConcentracion.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE NOMBRE");
                txtNewNombre.requestFocus();
            }
            }else {
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE CODIGO DE PRODUCTO");
            txtNewProd.requestFocus();
                    }
        return 0;
    }

     public int comprobarActualizacion() {
        if (!txtModCodigo.getText().trim().isEmpty()) {
            txtModCodigo.transferFocus();
            if (!txtModNombre.getText().trim().isEmpty()) {
                txtModNombre.transferFocus();
                if (!txtModConcentracion.getText().trim().isEmpty()) {
                    txtModConcentracion.transferFocus();
                            if (!txtModPresentacion.getText().trim().isEmpty()) {
                                txtModPresentacion.transferFocus();
                                    if (!txtModPrecioBlister.getText().trim().isEmpty()) {
                                        txtModPrecioBlister.transferFocus();
                                         if (!txtModLote.getText().trim().isEmpty()) {
                                                   txtModLote.transferFocus();
                                        if (!txtModPrecVenta.getText().trim().isEmpty()) {
                                            txtModPrecVenta.transferFocus();
                                                if (!txtModStock.getText().trim().isEmpty()) {
                                                    txtModStock.transferFocus();
                                                    return 1;
                                                } else {
                                                    JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE STOCK");
                                                    txtModStock.requestFocus();
                                                }
                                        } else {
                                            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE PRECIO VENTA");
                                            txtModPrecVenta.requestFocus();
                                        }
                                        } else {
                                        JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE LOTE");
                                        txtModLote.requestFocus();
                                    }
                                    } else {
                                        JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE REGISTRO SANITARIO");
                                        txtModPrecioBlister.requestFocus();
                                    }
                                
                            } else {
                                JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE PRESENTACION");
                                txtModPresentacion.requestFocus();
                            }

                } else {
                    JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE CONCENTRACION");
                    txtModConcentracion.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE NOMBRE");
                txtModNombre.requestFocus();
            }
            }else {
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE CODIGO DE PRODUCTO");
            txtModCodigo.requestFocus();
                    }
        return 0;
    }

    
    
    public void buscarProductos() {
        limpiarTabla();
        String art = txt_buscar.getText();
        String datos[] = new String[9];
        String sql = "SELECT `id_pro_medi`, cod_prod, `nom_pro_medi`,concentracion_pro_medi, `presentacion_pro_medi`,lote, `prec_venta`, `proveedor`, `stock_pro_medi` FROM `tproducto_medicamento` WHERE nom_pro_medi LIKE '" + art + "%' OR nom_pro_medi LIKE '%" + art + "' OR cod_prod LIKE '" + art + "%' OR cod_prod LIKE '%" + art + "'";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("cod_prod");
                datos[2] = rs.getString("nom_pro_medi");
                datos[3] = rs.getString("concentracion_pro_medi");
                datos[4] = rs.getString("presentacion_pro_medi");
                datos[5] = rs.getString("proveedor");
                datos[6] = rs.getString("lote");
                datos[7] = String.valueOf(rs.getDouble("prec_venta"));
                datos[8] = rs.getString("stock_pro_medi");
                table1.addRow(datos);
            }
            tbl_productos.setModel(table1);
            int filas = tbl_productos.getRowCount();
            //txt_registros.setText("" + filas);
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    public void limpiarTabla() {
        for (int i = 0; i < tbl_productos.getRowCount(); i++) {
            table1.removeRow(i);
            i -= 1;
        }
    }
    public void contar_productos(){
    int c = tbl_productos.getRowCount();
    txt_registros.setText(""+c);
    }
     public void limpiar(){
        txtNewProd.setText("");
        txtNewNombre.setText("");
        txtNewConcentracion.setText("");
        //txtNewFormato.setText("");
        //txtNewFormSimplificado.setText("");
        txtNewPresentacion.setText("");
        //txtNewFraccion.setText("");
        txtPrecioBlister.setText("");
        txtNewPrecVenta.setText("");
        txtNewStock.setText("");
        txtLote.setText("");
        txtNewProd.requestFocus();
       
     }
    
    public void modificar_producto() {
        int fila = tbl_productos.getSelectedRow();
        int codigo = Integer.parseInt(tbl_productos.getValueAt(fila, 0).toString());
        String sql = "SELECT `id_pro_medi`, `cod_prod`, `nom_pro_medi`, `concentracion_pro_medi`, lote,`nom_form_farm_pro_medi`, `nom_form_farm_simplif_pro_medi`, `presentacion_pro_medi`, `proveedor`, `fecha_venc_pro_medi`, `fec_ingreso_prod`, `num_reg_sanit_pro_medi`, `provee_labo_pro_medi`, prec_venta, precio_blister, `stock_pro_medi` FROM `tproducto_medicamento` WHERE id_pro_medi =" + codigo;

        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                txt_id.setText(rs.getString("id_pro_medi"));
                txtModCodigo.setText(rs.getString("cod_prod"));
                txtModNombre.setText(rs.getString("nom_pro_medi"));
                txtModConcentracion.setText(rs.getString("concentracion_pro_medi"));
               // txtModFormato.setText(rs.getString("nom_form_farm_pro_medi"));
                //txtModFormatoSimplificado.setText(rs.getString("nom_form_farm_simplif_pro_medi"));
                txtModPresentacion.setText(rs.getString("presentacion_pro_medi"));
                
                //txtModFraccion.setText(rs.getString("fraccion_pro_medi"));
                String Fec = rs.getString("fecha_venc_pro_medi");
                Date date = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(Fec);
                txtModFecVencimiento.setDate(date);                
                txtModProveedor.setText(rs.getString("provee_labo_pro_medi"));
                txtModPrecVenta.setText(rs.getString("prec_venta"));
                txtModPrecioBlister.setText(rs.getString("precio_blister"));
                txtModStock.setText(rs.getString("stock_pro_medi"));
                txtModLote.setText(rs.getString("lote"));
            }
         
//            id = Integer.parseInt(tbl_productos.getValueAt(fila, 0).toString());
//            Codigo = tbl_productos.getValueAt(fila, 1).toString();
//            Nombre = tbl_productos.getValueAt(fila, 2).toString();
//            Concentracion = tbl_productos.getValueAt(fila, 3).toString();
//            Formato = tbl_productos.getValueAt(fila, 4).toString();
//            FormatoSimplificado = tbl_productos.getValueAt(fila, 5).toString();
//            Presentacion = tbl_productos.getValueAt(fila, 6).toString();
//            Fraccion = Integer.parseInt(tbl_productos.getValueAt(fila, 7).toString());
//            String Fec = tbl_productos.getValueAt(fila, 8).toString();
//            NumRegSanitario = tbl_productos.getValueAt(fila, 9).toString();
//            Proveedor = tbl_productos.getValueAt(fila, 10).toString();
////                cmb_provee.setSelectedIndex(idProvee);
//            PrecUnitario = Double.parseDouble(tbl_productos.getValueAt(fila, 11).toString());
//            ProdCompleto = Double.parseDouble(tbl_productos.getValueAt(fila, 12).toString());
//            Stock = Integer.parseInt(tbl_productos.getValueAt(fila,13).toString());
            

        } catch (Exception e) {
           JOptionPane.showMessageDialog(getRootPane(), e.fillInStackTrace());
        }
    }
public void actualizarProductoEnBD(){
        int idprod = Integer.parseInt(txt_id.getText());
        String codigo = txtModCodigo.getText();
        String nombre = txtModNombre.getText();
        String concentracion = txtModConcentracion.getText();
       // String formato = txtModFormato.getText();
        String lote = txtModLote.getText();
        String presentacion = txtModPresentacion.getText();
        Date date = txtModFecVencimiento.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(date);
        double  prec_blister = Double.parseDouble(txtModPrecioBlister.getText());
        String prove = txtModProveedor.getText();
        String labo = cmbModificarLaboratorio.getSelectedItem().toString();
        //String reg_san = txtModNumRegSanitario.getText();
        double prec_uni = Double.parseDouble(txtModPrecVenta.getText());
        int stock = Integer.parseInt(txtModStock.getText());
       

        String sql = "UPDATE `tproducto_medicamento` SET `cod_prod`= '"+codigo+"',`nom_pro_medi`='"+nombre+"',`concentracion_pro_medi`='"+concentracion+"',`presentacion_pro_medi`='"+presentacion+"', proveedor = '"+labo+"',`fecha_venc_pro_medi`= '"+fecha+"',`precio_blister`='"+prec_blister+"',lote='"+lote+"',`provee_labo_pro_medi`='"+prove+"',`prec_venta`="+prec_uni+",`stock_pro_medi`="+stock+" WHERE  id_pro_medi = " + idprod;
        try {
            Statement st = cc.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {

                limpiarTabla();
                cargarProductos();
                contar_productos();
                JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "REGISTRO ACTUALIZADO");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), e.getMessage());
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

        frm_modificar = new javax.swing.JDialog();
        jLabel47 = new javax.swing.JLabel();
        lbl_logo2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtModFecVencimiento = new com.toedter.calendar.JDateChooser();
        cmb_provee = new javax.swing.JComboBox();
        txtModProveedor = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtModStock = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtModPrecVenta = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtModPrecioBlister = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtModPresentacion = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtModConcentracion = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtModNombre = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtModCodigo = new javax.swing.JTextField();
        btnModCancelar = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtModLote = new javax.swing.JTextField();
        cmbModificarLaboratorio = new javax.swing.JComboBox();
        jLabel28 = new javax.swing.JLabel();
        btnModActualizar = new javax.swing.JButton();
        frm_nuevo = new javax.swing.JDialog();
        jLabel46 = new javax.swing.JLabel();
        lbl_logo1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnNewRegistrar = new javax.swing.JButton();
        btnNewCancelar = new javax.swing.JButton();
        txt_fecha = new com.toedter.calendar.JDateChooser();
        cmb_proveedor = new javax.swing.JComboBox();
        txtNewStock = new javax.swing.JTextField();
        txtNewPrecVenta = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtPrecioBlister = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtNewPresentacion = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        txtNewConcentracion = new javax.swing.JTextField();
        txtNewNombre = new javax.swing.JTextField();
        txtNewProd = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txtLote = new javax.swing.JTextField();
        cmb_laboratorio = new javax.swing.JComboBox();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        lbl_logo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btn_delete = new javax.swing.JButton();
        btn_modify = new javax.swing.JButton();
        btn_new = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_registros = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_productos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lbl_icono = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        llbl_pie = new javax.swing.JLabel();

        frm_modificar.setBounds(new java.awt.Rectangle(500, 100, 717, 535));
        frm_modificar.setResizable(false);
        frm_modificar.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel47.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel47.setText("SALIR");
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel47.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel47MouseDragged(evt);
            }
        });
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel47MousePressed(evt);
            }
        });
        frm_modificar.getContentPane().add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, -1, 30));

        lbl_logo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        frm_modificar.getContentPane().add(lbl_logo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 60));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(txtModFecVencimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, 260, 30));

        cmb_provee.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NULL" }));
        cmb_provee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_proveeActionPerformed(evt);
            }
        });
        jPanel4.add(cmb_provee, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 110, -1));

        txtModProveedor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModProveedor.setForeground(new java.awt.Color(0, 102, 255));
        txtModProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModProveedor.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        jPanel4.add(txtModProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 130, -1));

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 102));
        jLabel27.setText("STOCK");
        jPanel4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, -1, -1));

        txtModStock.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModStock.setForeground(new java.awt.Color(0, 102, 255));
        txtModStock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModStock.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtModStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtModStockKeyTyped(evt);
            }
        });
        jPanel4.add(txtModStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 390, 255, -1));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("PRECIO VENTA");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, -1, -1));

        txtModPrecVenta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModPrecVenta.setForeground(new java.awt.Color(0, 102, 255));
        txtModPrecVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModPrecVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtModPrecVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModPrecVentaActionPerformed(evt);
            }
        });
        txtModPrecVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtModPrecVentaKeyTyped(evt);
            }
        });
        jPanel4.add(txtModPrecVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 330, 255, -1));

        jLabel24.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("LABORATORIO");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, -1, -1));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("LOTE");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, -1, -1));

        txtModPrecioBlister.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModPrecioBlister.setForeground(new java.awt.Color(0, 102, 255));
        txtModPrecioBlister.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModPrecioBlister.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtModPrecioBlister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModPrecioBlisterActionPerformed(evt);
            }
        });
        jPanel4.add(txtModPrecioBlister, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 255, -1));

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("FECHA DE VENCIMIENTO");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, -1, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("PRESENTACION");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, -1));

        txtModPresentacion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModPresentacion.setForeground(new java.awt.Color(0, 102, 255));
        txtModPresentacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModPresentacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtModPresentacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModPresentacionActionPerformed(evt);
            }
        });
        jPanel4.add(txtModPresentacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 255, -1));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("CONCENTRACION");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        txtModConcentracion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModConcentracion.setForeground(new java.awt.Color(0, 102, 255));
        txtModConcentracion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModConcentracion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtModConcentracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModConcentracionActionPerformed(evt);
            }
        });
        jPanel4.add(txtModConcentracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 255, -1));

        jLabel30.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 102));
        jLabel30.setText("NOMBRE DE PRODUCTO");
        jPanel4.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        txtModNombre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModNombre.setForeground(new java.awt.Color(0, 102, 255));
        txtModNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModNombre.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtModNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModNombreActionPerformed(evt);
            }
        });
        jPanel4.add(txtModNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 255, -1));

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 0, 102));
        jLabel29.setText("ID");
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

        txt_id.setEditable(false);
        txt_id.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_id.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_id.setEnabled(false);
        jPanel4.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 80, -1));

        jLabel42.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 0, 102));
        jLabel42.setText("CODIGO DE PRODUCTO");
        jPanel4.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, -1, -1));

        txtModCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModCodigo.setForeground(new java.awt.Color(0, 102, 255));
        txtModCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModCodigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtModCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModCodigoActionPerformed(evt);
            }
        });
        jPanel4.add(txtModCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 255, -1));

        btnModCancelar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnModCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/delete.png"))); // NOI18N
        btnModCancelar.setText("CANCELAR");
        btnModCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnModCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, 150, 50));

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 0, 102));
        jLabel26.setText("PRECIO BLISTER");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, -1, -1));

        txtModLote.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtModLote.setForeground(new java.awt.Color(0, 102, 255));
        txtModLote.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModLote.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtModLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtModLoteActionPerformed(evt);
            }
        });
        jPanel4.add(txtModLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 255, -1));

        cmbModificarLaboratorio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NULL" }));
        cmbModificarLaboratorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbModificarLaboratorioActionPerformed(evt);
            }
        });
        jPanel4.add(cmbModificarLaboratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, 250, -1));

        jLabel28.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 0, 102));
        jLabel28.setText("PROVEEDOR");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, -1, -1));

        btnModActualizar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnModActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        btnModActualizar.setText("ACTUALIZAR");
        btnModActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModActualizarActionPerformed(evt);
            }
        });
        jPanel4.add(btnModActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 150, 50));

        frm_modificar.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 710, 450));

        frm_nuevo.setBackground(new java.awt.Color(255, 255, 255));
        frm_nuevo.setBounds(new java.awt.Rectangle(500, 100, 735, 500));
        frm_nuevo.setResizable(false);
        frm_nuevo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel46.setText("SALIR");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel46.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel46MouseDragged(evt);
            }
        });
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel46MousePressed(evt);
            }
        });
        frm_nuevo.getContentPane().add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 10, -1, 30));

        lbl_logo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        frm_nuevo.getContentPane().add(lbl_logo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 60));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNewRegistrar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnNewRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/save.png"))); // NOI18N
        btnNewRegistrar.setText("REGISTRAR");
        btnNewRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewRegistrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnNewRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 90, 150, 40));

        btnNewCancelar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnNewCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/delete.png"))); // NOI18N
        btnNewCancelar.setText("CANCELAR");
        btnNewCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(btnNewCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 140, 150, 40));

        txt_fecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 204)));
        txt_fecha.setDateFormatString("yyyy-MM-dd");
        jPanel2.add(txt_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, 260, 30));

        jPanel2.add(cmb_proveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 230, 260, -1));

        txtNewStock.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNewStock.setForeground(new java.awt.Color(0, 51, 255));
        txtNewStock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNewStock.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtNewStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewStockActionPerformed(evt);
            }
        });
        txtNewStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewStockKeyTyped(evt);
            }
        });
        jPanel2.add(txtNewStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 255, -1));

        txtNewPrecVenta.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNewPrecVenta.setForeground(new java.awt.Color(0, 51, 255));
        txtNewPrecVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNewPrecVenta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtNewPrecVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPrecVentaActionPerformed(evt);
            }
        });
        txtNewPrecVenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewPrecVentaKeyTyped(evt);
            }
        });
        jPanel2.add(txtNewPrecVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 310, 255, -1));

        jLabel41.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 102));
        jLabel41.setText("STOCK");
        jPanel2.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, -1, -1));

        jLabel39.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 102));
        jLabel39.setText("PRECIO VENTA");
        jPanel2.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, 20));

        jLabel38.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 102));
        jLabel38.setText("LOTE");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, -1, -1));

        txtPrecioBlister.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtPrecioBlister.setForeground(new java.awt.Color(0, 51, 255));
        txtPrecioBlister.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecioBlister.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtPrecioBlister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioBlisterActionPerformed(evt);
            }
        });
        jPanel2.add(txtPrecioBlister, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 340, 255, -1));

        jLabel37.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 102));
        jLabel37.setText("PRECIO BLISTER");
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, -1, -1));

        jLabel36.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 102));
        jLabel36.setText("FECHA DE VENCIMIENTO");
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, -1));

        jLabel34.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 102));
        jLabel34.setText("PRESENTACION");
        jPanel2.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, -1, -1));

        txtNewPresentacion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNewPresentacion.setForeground(new java.awt.Color(0, 51, 255));
        txtNewPresentacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNewPresentacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtNewPresentacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewPresentacionActionPerformed(evt);
            }
        });
        txtNewPresentacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewPresentacionKeyTyped(evt);
            }
        });
        jPanel2.add(txtNewPresentacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, 255, -1));

        jLabel31.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 0, 102));
        jLabel31.setText("CODIGO DE PRODUCTO");
        jPanel2.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, -1, -1));

        jLabel33.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 102));
        jLabel33.setText("NOMBRE DE PRODUCTO");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, -1));

        jLabel43.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 102));
        jLabel43.setText("CONCENTRACION");
        jPanel2.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        txtNewConcentracion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNewConcentracion.setForeground(new java.awt.Color(0, 51, 255));
        txtNewConcentracion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNewConcentracion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtNewConcentracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewConcentracionActionPerformed(evt);
            }
        });
        txtNewConcentracion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewConcentracionKeyTyped(evt);
            }
        });
        jPanel2.add(txtNewConcentracion, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 255, -1));

        txtNewNombre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNewNombre.setForeground(new java.awt.Color(0, 51, 255));
        txtNewNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNewNombre.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtNewNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewNombreActionPerformed(evt);
            }
        });
        txtNewNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewNombreKeyTyped(evt);
            }
        });
        jPanel2.add(txtNewNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 255, -1));

        txtNewProd.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNewProd.setForeground(new java.awt.Color(0, 51, 255));
        txtNewProd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNewProd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtNewProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNewProdActionPerformed(evt);
            }
        });
        txtNewProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNewProdKeyTyped(evt);
            }
        });
        jPanel2.add(txtNewProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 255, -1));

        jLabel40.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 102));
        jLabel40.setText("LABORATORIO");
        jPanel2.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, 30));

        txtLote.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtLote.setForeground(new java.awt.Color(0, 51, 255));
        txtLote.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLote.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 1, true));
        txtLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoteActionPerformed(evt);
            }
        });
        jPanel2.add(txtLote, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 200, 255, -1));

        jPanel2.add(cmb_laboratorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 260, -1));

        jLabel44.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 102));
        jLabel44.setText("PROVEEDOR");
        jPanel2.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, -1, 30));

        frm_nuevo.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 720, 420));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PRODUCTOS");
        setBounds(new java.awt.Rectangle(50, 50, 0, 0));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel45.setText("SALIR");
        jLabel45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel45.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel45MouseDragged(evt);
            }
        });
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel45MousePressed(evt);
            }
        });
        getContentPane().add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, -1, 30));

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
        getContentPane().add(lbl_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_delete.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/delete.png"))); // NOI18N
        btn_delete.setText("ELIMINAR");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel1.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 190, 50));

        btn_modify.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_modify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/modify.png"))); // NOI18N
        btn_modify.setText("MODIFICAR");
        btn_modify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifyActionPerformed(evt);
            }
        });
        jPanel1.add(btn_modify, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 170, 50));

        btn_new.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_new.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/new.png"))); // NOI18N
        btn_new.setText("NUEVO");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });
        jPanel1.add(btn_new, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 150, 50));

        jLabel15.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("BUSCAR:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 90, -1, -1));

        txt_buscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_buscar.setForeground(new java.awt.Color(0, 0, 153));
        txt_buscar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_buscarMouseClicked(evt);
            }
        });
        txt_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_buscarActionPerformed(evt);
            }
        });
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });
        jPanel1.add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 81, 410, 30));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setText("REGISTROS:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 20, -1, -1));

        txt_registros.setEditable(false);
        txt_registros.setBackground(new java.awt.Color(204, 204, 255));
        txt_registros.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_registros.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_registros.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel1.add(txt_registros, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 20, 60, -1));

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
        tbl_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_productosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_productos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 1250, 390));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("REGISTROS DE PRODUCTOS");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        lbl_icono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1460857905_Shipping1.png"))); // NOI18N
        jPanel1.add(lbl_icono, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 70, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1270, 540));

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        llbl_pie.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        llbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        llbl_pie.setText("jLabel2");
        jPanel3.add(llbl_pie, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 0, 1260, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1270, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_modifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifyActionPerformed
        try {
            frm_modificar.setVisible(true);
            modificar_producto();
            cargarLaboratorio(cmbModificarLaboratorio);
            cargarproveedorModificar();
        } catch (SQLException ex) {
            Logger.getLogger(Reg_producto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_modifyActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        try {
            frm_nuevo.setVisible(true);
            cargarproveedor();
            cargarLaboratorio(cmb_laboratorio);
            txtNewProd.requestFocus();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }//GEN-LAST:event_btn_newActionPerformed

    private void tbl_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_productosMouseClicked
        int fila = tbl_productos.getSelectedRow()+1;
        if (fila>0) {
            desbloquear();
        }else{
            bloquear();
        }
    }//GEN-LAST:event_tbl_productosMouseClicked

    private void txt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscarActionPerformed
        
    }//GEN-LAST:event_txt_buscarActionPerformed

    private void txt_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_buscarMouseClicked
        tbl_productos.clearSelection();
        bloquear();
    }//GEN-LAST:event_txt_buscarMouseClicked

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        buscarProductos();
    }//GEN-LAST:event_txt_buscarKeyReleased

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        tbl_productos.clearSelection();
        bloquear();
    }//GEN-LAST:event_formMouseClicked

    private void lbl_logoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_lbl_logoMousePressed

    private void lbl_logoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x-posx, point.y-posy);
    }//GEN-LAST:event_lbl_logoMouseDragged

    private void btnModCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModCancelarActionPerformed
        frm_modificar.dispose();
    }//GEN-LAST:event_btnModCancelarActionPerformed

    private void btnNewCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewCancelarActionPerformed
        frm_nuevo.dispose();
    }//GEN-LAST:event_btnNewCancelarActionPerformed

    private void jLabel46MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel46MouseDragged

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        frm_nuevo.dispose();
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel46MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel46MousePressed

    private void jLabel47MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel47MouseDragged

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
       frm_modificar.dispose();
    }//GEN-LAST:event_jLabel47MouseClicked

    private void jLabel47MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel47MousePressed

    private void jLabel45MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel45MousePressed

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel45MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel45MouseDragged

    private void txtNewProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewProdActionPerformed
      txtNewProd.transferFocus();
    }//GEN-LAST:event_txtNewProdActionPerformed

    private void txtNewNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewNombreActionPerformed
        txtNewConcentracion.requestFocus();
    }//GEN-LAST:event_txtNewNombreActionPerformed

    private void txtNewConcentracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewConcentracionActionPerformed
       txtNewConcentracion.transferFocus();
    }//GEN-LAST:event_txtNewConcentracionActionPerformed

    private void txtNewPresentacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPresentacionActionPerformed
       txtLote.requestFocus();
    }//GEN-LAST:event_txtNewPresentacionActionPerformed

    private void txtPrecioBlisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioBlisterActionPerformed
       txtNewStock.requestFocus();
             
    }//GEN-LAST:event_txtPrecioBlisterActionPerformed

    private void txtNewPrecVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewPrecVentaActionPerformed
      txtNewPrecVenta.transferFocus();
    }//GEN-LAST:event_txtNewPrecVentaActionPerformed

    private void txtNewProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewProdKeyTyped
        
    }//GEN-LAST:event_txtNewProdKeyTyped

    private void txtNewNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewNombreKeyTyped
       int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE SOLO LETRAS");
            txtNewNombre.requestFocus();
            //txt_nombres.requestFocus();
        }
    }//GEN-LAST:event_txtNewNombreKeyTyped

    private void txtNewConcentracionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewConcentracionKeyTyped
      
    }//GEN-LAST:event_txtNewConcentracionKeyTyped

    private void txtNewPresentacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewPresentacionKeyTyped
       
    }//GEN-LAST:event_txtNewPresentacionKeyTyped

    private void txtNewPrecVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewPrecVentaKeyTyped
      int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE SOLO NUMEROS");
            txtNewPrecVenta.requestFocus();
        }
    }//GEN-LAST:event_txtNewPrecVentaKeyTyped

    private void txtNewStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNewStockKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE SOLO NUMEROS");
            txtNewStock.requestFocus();
        }
    }//GEN-LAST:event_txtNewStockKeyTyped

    private void btnNewRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewRegistrarActionPerformed
        if (comprobar()!=0) {
             registrarProducto();
             limpiar();
        }else{
            //comprobar();
        }
    }//GEN-LAST:event_btnNewRegistrarActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        String prod = txt_buscar.getText();
        int fila = tbl_productos.getSelectedRow();
        int id = Integer.parseInt(tbl_productos.getValueAt(fila, 0).toString());
        String sql = "DELETE FROM `tproducto_medicamento` WHERE id_pro_medi ="+id;
        try {
            int opc = JOptionPane.showOptionDialog(btn_delete, "¿ELIMINAR PRODUCTO ? SE PERDERAN LOS DATOS ASOCIADOS AL PRODUCTO", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
            if (opc == 0) {
                    eliminarDetalleCompra(id);
                    eliminarDetalleVenta(id);
                Statement st = cc.createStatement();
                int rs = st.executeUpdate(sql);
                if (rs > 0) {
                    
                    limpiarTabla();
                    //cargarProductos();
                    cargarProductosText(prod);
                    bloquear();
                    JOptionPane.showMessageDialog(getRootPane(), "REGISTRO ELIMINADO");
                    //txt_buscar.setText("");
                    txt_buscar.requestFocus();
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
       

    }//GEN-LAST:event_btn_deleteActionPerformed

    public void eliminarDetalleCompra(int idproducto){
        String sql = "DELETE FROM `tdetalle_compra` WHERE `id_pro_medi`="+idproducto;
        try {
            Statement st = cc.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs>0) {
                System.out.println("detalle de compra eliminado");
           
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
   public void eliminarDetalleVenta(int idproducto){
        String sql = "DELETE FROM `tdetalleventa` WHERE  `id_pro_medi` ="+idproducto;
        try {
            Statement st = cc.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs>0) {
                System.out.println("detalle de venta eliminado");
           
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        
        
    }
    
    
    
    
    
    
    private void btnModActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModActualizarActionPerformed
        try {
            if (comprobarActualizacion() != 0) {
                frm_modificar.dispose();
                actualizarProductoEnBD();
                bloquear();
                txt_buscar.setText("");
                txt_buscar.requestFocus();
            } else {
                System.out.println("Ingrese al else de actualizar ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }//GEN-LAST:event_btnModActualizarActionPerformed

    private void cmb_proveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_proveeActionPerformed
        if (cmb_provee.getSelectedIndex()>0) {
            String proveedor = cmb_provee.getSelectedItem().toString();
        txtModProveedor.setText(proveedor);
        } else {
        }
        
        
    }//GEN-LAST:event_cmb_proveeActionPerformed

    private void txtModPrecVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModPrecVentaActionPerformed
        txtModStock.requestFocus();
    }//GEN-LAST:event_txtModPrecVentaActionPerformed

    private void txtModPrecVentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModPrecVentaKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE SOLO NUMEROS");
            txtModPrecVenta.requestFocus();
        }
    }//GEN-LAST:event_txtModPrecVentaKeyTyped

    private void txtModStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModStockKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE SOLO NUMEROS");
            txtModStock.requestFocus();
        }
    }//GEN-LAST:event_txtModStockKeyTyped

    private void txtModCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModCodigoActionPerformed
        txtModNombre.requestFocus();
    }//GEN-LAST:event_txtModCodigoActionPerformed

    private void txtModNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModNombreActionPerformed
        txtModConcentracion.requestFocus();
    }//GEN-LAST:event_txtModNombreActionPerformed

    private void txtModConcentracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModConcentracionActionPerformed
        txtModPresentacion.requestFocus();
    }//GEN-LAST:event_txtModConcentracionActionPerformed

    private void txtModPresentacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModPresentacionActionPerformed
        txtModPrecioBlister.requestFocus();
    }//GEN-LAST:event_txtModPresentacionActionPerformed

    private void txtModPrecioBlisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModPrecioBlisterActionPerformed
        txtModPrecVenta.requestFocus();
    }//GEN-LAST:event_txtModPrecioBlisterActionPerformed

    private void txtLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoteActionPerformed
      txtNewPrecVenta.requestFocus();
    }//GEN-LAST:event_txtLoteActionPerformed

    private void txtModLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtModLoteActionPerformed

    private void txtNewStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNewStockActionPerformed
        btnNewRegistrar.doClick();
    }//GEN-LAST:event_txtNewStockActionPerformed

    private void cmbModificarLaboratorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbModificarLaboratorioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbModificarLaboratorioActionPerformed

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
            java.util.logging.Logger.getLogger(Reg_producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reg_producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reg_producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reg_producto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reg_producto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnModActualizar;
    private javax.swing.JButton btnModCancelar;
    private javax.swing.JButton btnNewCancelar;
    private javax.swing.JButton btnNewRegistrar;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_modify;
    private javax.swing.JButton btn_new;
    private javax.swing.JComboBox cmbModificarLaboratorio;
    private javax.swing.JComboBox cmb_laboratorio;
    private javax.swing.JComboBox cmb_provee;
    private javax.swing.JComboBox cmb_proveedor;
    private javax.swing.JDialog frm_modificar;
    private javax.swing.JDialog frm_nuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
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
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_icono;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_logo1;
    private javax.swing.JLabel lbl_logo2;
    private javax.swing.JLabel llbl_pie;
    private javax.swing.JTable tbl_productos;
    private javax.swing.JTextField txtLote;
    private javax.swing.JTextField txtModCodigo;
    private javax.swing.JTextField txtModConcentracion;
    private com.toedter.calendar.JDateChooser txtModFecVencimiento;
    private javax.swing.JTextField txtModLote;
    private javax.swing.JTextField txtModNombre;
    private javax.swing.JTextField txtModPrecVenta;
    private javax.swing.JTextField txtModPrecioBlister;
    private javax.swing.JTextField txtModPresentacion;
    private javax.swing.JTextField txtModProveedor;
    private javax.swing.JTextField txtModStock;
    private javax.swing.JTextField txtNewConcentracion;
    private javax.swing.JTextField txtNewNombre;
    private javax.swing.JTextField txtNewPrecVenta;
    private javax.swing.JTextField txtNewPresentacion;
    private javax.swing.JTextField txtNewProd;
    private javax.swing.JTextField txtNewStock;
    private javax.swing.JTextField txtPrecioBlister;
    private javax.swing.JTextField txt_buscar;
    private com.toedter.calendar.JDateChooser txt_fecha;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_registros;
    // End of variables declaration//GEN-END:variables

    public void registrarProducto() {        
        String idprod =txtNewProd.getText();
        String nomProd = txtNewNombre.getText();
        String concentracion = txtNewConcentracion.getText();
        String presentacion = txtNewPresentacion.getText();
        String labo = cmb_laboratorio.getSelectedItem().toString();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        Date dia = txt_fecha.getDate();
        String fecha_ingreso = new ManejadorFechas().getFechaActualMySQL();
        String fecha1 = formateador.format(dia);
        String lote = txtLote.getText();
        String proveedor = cmb_proveedor.getSelectedItem().toString();
        double precUni = Double.parseDouble(txtNewPrecVenta.getText());
        double prec_blister = Double.parseDouble(txtPrecioBlister.getText());
        int stock = Integer.parseInt(txtNewStock.getText());
        String sql = "INSERT INTO `tproducto_medicamento`(`cod_prod`, `nom_pro_medi`, `concentracion_pro_medi`, `presentacion_pro_medi`,proveedor,lote, `fecha_venc_pro_medi`, `fec_ingreso_prod`, `num_reg_sanit_pro_medi`,`provee_labo_pro_medi`, `prec_venta`,`precio_blister`, `stock_pro_medi`, `id_contac`, `id_categoria`) VALUES ('"+idprod+"','"+nomProd+"','"+concentracion+"','"+presentacion+"','"+labo+"','"+lote+"','"+fecha1+"','"+fecha_ingreso+"','0','"+proveedor+"','"+precUni+"','"+prec_blister+"','"+stock+"',1,6)";
        try {
            Statement st = cc.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
            //JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO CON EXITO");
                //0=si------1=no------2=cancelar
                limpiarTabla();
                cargarProductos();
                contar_productos();
                int opc = JOptionPane.showOptionDialog(btnNewRegistrar, "PRODUCTO REGISTRADO, ¿DESEA REGISTRAR MAS PRODUCTOS?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
                if (opc == 0) {
                    limpiar();
                } else if (opc == 1) {
                    frm_nuevo.dispose();
                    txt_buscar.requestFocus();
                    bloquear();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public void cargarproveedor() {
        String nombre = null;
        String sql = "SELECT `nom_provee`  FROM `tproveedor`";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombre = rs.getString("nom_provee");
                cmb_proveedor.addItem(nombre);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    public void cargarLaboratorio(JComboBox cmb) throws SQLException{
        LaboratorioDAO ldao = new LaboratorioDAO();
        for (Laboratorio l : ldao.listar()) {
            cmb.addItem(l.getNombre());
        }
    }
    
    public void cargarproveedorModificar() {
        String nombre = null;
        String sql = "SELECT `nom_provee`  FROM `tproveedor`";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombre = rs.getString("nom_provee");
                cmb_provee.addItem(nombre);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    
}
