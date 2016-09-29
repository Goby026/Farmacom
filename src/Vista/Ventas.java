package Vista;

import Control.Conexion;
import Control.MyiReportVisor;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Gaby
 */
public final class Ventas extends javax.swing.JFrame {

    int posx, posy;
    int posa, posb;
    Conexion con = new Conexion();
    Connection cc = con.conectar();
    DefaultTableModel table1;
    DefaultTableModel table2;
    DefaultTableModel table_cliente;

    String codigo, nombre, presentacion;
    int cantidad;
    String hora = hora();

    MyiReportVisor mrv;
    HashMap parametros = new HashMap();

    public Ventas() {
        //setUndecorated(true);
        initComponents();
        setAlwaysOnTop(true);
        frm_buscar.setAlwaysOnTop(true);
        //frm_buscar.setUndecorated(true);
        //frm_buscar.getRootPane().setOpaque(false);
        //frm_buscar.setOpacity(0.95f);
        this.getContentPane().setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        //frm_buscar.getContentPane().setBackground(Color.darkGray);
        //frm_buscar.setLocationRelativeTo(null);
        cargarTituloTabla1();
        cargarTituloTabla2();
        cargarProductos();
        btn_quitar.setEnabled(false);
        loadDatos();

    }

    public void loadDatos() {
        txt_monto_recibido.setText("" + 0);
        txt_cliente.setText("NULL");
        txt_hora.setText(hora);
        txt_fecha.setText(fecha());
        lbl_serie.setText("" + obtenerNumeroBoleta());
//        txt_usuario.setText(new Users().cargarNomUsuario(id)+" "+new Users().cargarApeUsuario(id));
//        txt_codigo.setText(""+new Users().numId(id));
        String time_venta = new Time(System.currentTimeMillis()).toString();
        lbl_pie.setText(new Farma_inf().pie());
        txt_cambio.setText("" + new Farma_inf().cambio());
        cargarNumPedido();
    }

    public void cargarTituloTabla1() {
        String[] cabecera1 = {"CÓDIGO", "NOMBRE", "PRESENTACIÓN", "CANT. UNID.", "PRECIO VENTA", "IMPORTE", "DCTO"};
        table1 = new DefaultTableModel(null, cabecera1);
        //TableColumnModel columnModel = tbl_venta.getColumnModel();
        //columnModel.getColumn(1).setPreferredWidth(2);
        tbl_venta.setModel(table1);
        tbl_venta.getColumnModel().getColumn(0).setPreferredWidth(10);
        tbl_venta.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbl_venta.getColumnModel().getColumn(2).setPreferredWidth(60);
        tbl_venta.getColumnModel().getColumn(3).setPreferredWidth(60);
        tbl_venta.getColumnModel().getColumn(4).setPreferredWidth(60);
        tbl_venta.getColumnModel().getColumn(5).setPreferredWidth(60);
        tbl_venta.getColumnModel().getColumn(6).setPreferredWidth(60);

    }

    public void cargarTituloTabla2() {
        String[] cabecera = {"CÓDIGO", "NOMBRE", "CONCENTRACION", "PRESENTACION", "LABORATORIO", "STOCK", "PRECIO", "P. BLISTER"};
        table2 = new DefaultTableModel(null, cabecera);
        tbl_productos.setModel(table2);
    }

    public void cargarTituloClientes() {
        String[] cabecera = {"CÓDIGO", "NOMBRE", "APELLIDO", "TELEFONO", "DNI", "DIRECCION", "E-MAIL"};
        table_cliente = new DefaultTableModel(null, cabecera);
        tbl_clientes_ventas.setModel(table_cliente);
        String sql = "SELECT `id_cli`, `nom_cli`, `apell_cli`, `telf_cli`, `dni_cli`, `direc_cli`, `email_contac` FROM `tcliente` ORDER BY id_cli DESC";
        try {
            String[] datos = new String[7];
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_cli"));
                datos[1] = rs.getString("nom_cli");
                datos[2] = rs.getString("apell_cli");
                datos[3] = rs.getString("telf_cli");
                datos[4] = rs.getString("dni_cli");
                datos[5] = rs.getString("direc_cli");
                datos[6] = rs.getString("email_contac");
                table_cliente.addRow(datos);
            }
            tbl_clientes_ventas.setModel(table_cliente);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    public void cargarProductos() {
        String datos[] = new String[8];
        String sql = "select id_pro_medi, nom_pro_medi, concentracion_pro_medi, presentacion_pro_medi, provee_labo_pro_medi, stock_pro_medi,prec_venta, precio_blister from tproducto_medicamento";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("provee_labo_pro_medi");
                datos[5] = String.valueOf(rs.getInt("stock_pro_medi"));
                datos[6] = String.valueOf(rs.getDouble("prec_venta"));
                datos[7] = String.valueOf(rs.getDouble("precio_blister"));
                table2.addRow(datos);
            }
            tbl_productos.setModel(table2);
            tbl_productos.getColumnModel().getColumn(0).setPreferredWidth(10);
            tbl_productos.getColumnModel().getColumn(1).setPreferredWidth(200);
            tbl_productos.getColumnModel().getColumn(2).setPreferredWidth(20);
            tbl_productos.getColumnModel().getColumn(3).setPreferredWidth(20);
            tbl_productos.getColumnModel().getColumn(4).setPreferredWidth(20);
            tbl_productos.getColumnModel().getColumn(5).setPreferredWidth(20);
            tbl_productos.getColumnModel().getColumn(6).setPreferredWidth(20);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    public void cargarNumPedido() {
        String sql = "SELECT MAX(`id_venta`) FROM `tventa`";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                txt_num_pedido.setText("" + (rs.getInt("MAX(`id_venta`)") + 1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    public void buscarProductos() {
        limpiarTabla();
        String art = txt_articulo.getText();
        String datos[] = new String[8];
        String sql = "select id_pro_medi, nom_pro_medi, concentracion_pro_medi, presentacion_pro_medi, provee_labo_pro_medi, stock_pro_medi,prec_venta, precio_blister from tproducto_medicamento WHERE nom_pro_medi LIKE '" + art + "%' OR nom_pro_medi LIKE '%" + art + "'";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("concentracion_pro_medi");
                datos[3] = rs.getString("presentacion_pro_medi");
                datos[4] = rs.getString("provee_labo_pro_medi");
                datos[5] = String.valueOf(rs.getInt("stock_pro_medi"));
                datos[6] = String.valueOf(rs.getDouble("prec_venta"));
                datos[7] = String.valueOf(rs.getDouble("precio_blister"));
                table2.addRow(datos);
            }
            tbl_productos.setModel(table2);
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    public void limpiarTabla() {
        for (int i = 0; i < tbl_productos.getRowCount(); i++) {
            table2.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarTablaCliente() {
        for (int i = 0; i < tbl_clientes_ventas.getRowCount(); i++) {
            table_cliente.removeRow(i);
            i -= 1;
        }
    }

    public void limpiarTablaVenta() {
        for (int i = 0; i < tbl_venta.getRowCount(); i++) {
            table1.removeRow(i);
            i -= 1;
        }
    }

    public String hora() {
        Calendar c = new GregorianCalendar();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int seg = c.get(Calendar.SECOND);
        String h = hour + ":" + min + ":" + seg;
        return h;
    }

    public String fecha() {
        String mes = "";
        Calendar c = new GregorianCalendar();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case 0:
                mes = "01";
                break;
            case 1:
                mes = "02";
                break;
            case 2:
                mes = "03";
                break;
            case 3:
                mes = "04";
                break;
            case 4:
                mes = "05";
                break;
            case 5:
                mes = "06";
                break;
            case 6:
                mes = "07";
                break;
            case 7:
                mes = "08";
                break;
            case 8:
                mes = "09";
                break;
            case 9:
                mes = "10";
                break;
            case 10:
                mes = "11";
                break;
            case 11:
                mes = "12";
                break;
        }
        int year = c.get(Calendar.YEAR);
        String f = year + "-" + mes + "-" + dia;
        return f;
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_productos = new javax.swing.JTable();
        lbl_producto8 = new javax.swing.JLabel();
        lbl_logo2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbl_producto11 = new javax.swing.JLabel();
        lbl_producto12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txt_articulo = new javax.swing.JTextField();
        lbl_producto7 = new javax.swing.JLabel();
        btn_add = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();
        lbl_producto9 = new javax.swing.JLabel();
        txt_cantidad1 = new javax.swing.JTextField();
        txtDescuento = new javax.swing.JTextField();
        lbl_producto10 = new javax.swing.JLabel();
        cmbDescuento = new javax.swing.JCheckBox();
        cmbBlister = new javax.swing.JCheckBox();
        frm_cliente = new javax.swing.JDialog();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_clientes_ventas = new javax.swing.JTable();
        btn_agregar_cliente = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_venta = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_num_pedido = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lbl_producto = new javax.swing.JLabel();
        txt_producto = new javax.swing.JTextField();
        btn_buscar_cliente = new javax.swing.JButton();
        txt_cliente = new javax.swing.JTextField();
        btn_agregar1 = new javax.swing.JButton();
        lbl_producto14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_hora = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_igv = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_descuento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_usuario = new javax.swing.JTextField();
        txt_cambio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_total_pagar = new javax.swing.JTextField();
        btn_realizar_venta = new javax.swing.JButton();
        lbl_producto6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_monto_recibido = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_vuelto = new javax.swing.JTextField();
        btn_quitar = new javax.swing.JButton();
        lbl_fondo = new javax.swing.JLabel();
        lbl_logo = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_serie = new javax.swing.JLabel();
        lbl_producto15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_codigo = new javax.swing.JTextField();

        frm_buscar.setTitle("BUSCAR PRODUCTO");
        frm_buscar.setBounds(new java.awt.Rectangle(100, 100, 1020, 555));
        frm_buscar.setResizable(false);
        frm_buscar.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_productos = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbl_productos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbl_productos.setForeground(new java.awt.Color(0, 102, 204));
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
        tbl_productos.getTableHeader().setReorderingAllowed(false);
        tbl_productos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_productosMouseClicked(evt);
            }
        });
        tbl_productos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_productosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tbl_productosKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_productos);

        frm_buscar.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 145, 990, 270));

        lbl_producto8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lbl_producto8.setForeground(new java.awt.Color(0, 0, 153));
        lbl_producto8.setText("DETALLES");
        frm_buscar.getContentPane().add(lbl_producto8, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 120, -1, -1));

        lbl_logo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        lbl_logo2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lbl_logo2MouseDragged(evt);
            }
        });
        lbl_logo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbl_logo2MousePressed(evt);
            }
        });
        frm_buscar.getContentPane().add(lbl_logo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, -1));

        jPanel3.setBackground(new java.awt.Color(0, 153, 204));
        jPanel3.setForeground(new java.awt.Color(0, 102, 204));

        lbl_producto11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_producto11.setForeground(new java.awt.Color(255, 255, 255));
        lbl_producto11.setText("CADENA DE BOTICAS FARMACOM");

        lbl_producto12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_producto12.setForeground(new java.awt.Color(255, 255, 255));
        lbl_producto12.setText("HOSPITAL REGIONAL ### EL TAMBO - HUANCAYO - PERU");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lbl_producto11)
                .addGap(110, 110, 110)
                .addComponent(lbl_producto12)
                .addGap(0, 302, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_producto11)
                    .addComponent(lbl_producto12)))
        );

        frm_buscar.getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 1020, -1));

        jPanel4.setBackground(new java.awt.Color(153, 153, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_articulo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_articulo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txt_articulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_articuloKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_articuloKeyTyped(evt);
            }
        });
        jPanel4.add(txt_articulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 310, 25));

        lbl_producto7.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl_producto7.setForeground(new java.awt.Color(0, 0, 102));
        lbl_producto7.setText("BUSCAR");
        jPanel4.add(lbl_producto7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 20));

        btn_add.setBackground(new java.awt.Color(0, 255, 0));
        btn_add.setForeground(new java.awt.Color(255, 255, 255));
        btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add.png"))); // NOI18N
        btn_add.setText("AGREGAR");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });
        jPanel4.add(btn_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 370, 140, 40));

        btn_salir.setBackground(new java.awt.Color(255, 0, 0));
        btn_salir.setForeground(new java.awt.Color(255, 255, 255));
        btn_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/quitar2.png"))); // NOI18N
        btn_salir.setMnemonic('s');
        btn_salir.setText("SALIR");
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        jPanel4.add(btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 410, 140, 30));

        lbl_producto9.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        lbl_producto9.setForeground(new java.awt.Color(0, 0, 102));
        lbl_producto9.setText("CANTIDAD");
        jPanel4.add(lbl_producto9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 390, -1, -1));

        txt_cantidad1.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
        txt_cantidad1.setForeground(new java.awt.Color(0, 51, 204));
        txt_cantidad1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cantidad1.setText("0");
        txt_cantidad1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txt_cantidad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cantidad1ActionPerformed(evt);
            }
        });
        txt_cantidad1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cantidad1KeyReleased(evt);
            }
        });
        jPanel4.add(txt_cantidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 370, 160, 70));

        txtDescuento.setEditable(false);
        txtDescuento.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txtDescuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescuento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txtDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescuentoActionPerformed(evt);
            }
        });
        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyReleased(evt);
            }
        });
        jPanel4.add(txtDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, 90, 25));

        lbl_producto10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lbl_producto10.setForeground(new java.awt.Color(0, 0, 153));
        lbl_producto10.setText("¿Descuento S/ 0.5?");
        jPanel4.add(lbl_producto10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 160, -1));

        cmbDescuento.setBackground(new java.awt.Color(153, 153, 255));
        cmbDescuento.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        cmbDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDescuentoActionPerformed(evt);
            }
        });
        jPanel4.add(cmbDescuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, -1, 20));

        cmbBlister.setBackground(new java.awt.Color(153, 153, 255));
        cmbBlister.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cmbBlister.setText("BLISTER");
        cmbBlister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBlisterActionPerformed(evt);
            }
        });
        jPanel4.add(cmbBlister, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 40, -1, 20));

        frm_buscar.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1020, 450));

        frm_cliente.setBounds(new java.awt.Rectangle(450, 50, 734, 530));
        frm_cliente.setResizable(false);
        frm_cliente.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Banner.png"))); // NOI18N
        frm_cliente.getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 60));

        tbl_clientes_ventas.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbl_clientes_ventas.setForeground(new java.awt.Color(0, 51, 255));
        tbl_clientes_ventas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tbl_clientes_ventas);

        frm_cliente.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 700, 320));

        btn_agregar_cliente.setBackground(new java.awt.Color(0, 204, 204));
        btn_agregar_cliente.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btn_agregar_cliente.setForeground(new java.awt.Color(255, 255, 255));
        btn_agregar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add.png"))); // NOI18N
        btn_agregar_cliente.setText("AGREGAR CLIENTE");
        btn_agregar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar_clienteActionPerformed(evt);
            }
        });
        frm_cliente.getContentPane().add(btn_agregar_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 450, 190, -1));

        jButton1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453103738_customers.png"))); // NOI18N
        jButton1.setText("REGISTRAR NUEVO CLIENTE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        frm_cliente.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        jPanel5.setBackground(new java.awt.Color(0, 51, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        frm_cliente.getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 720, 20));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 2, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453101714_gtk-refresh.png"))); // NOI18N
        jButton2.setText("ACTUALIZAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 10, -1, 30));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 153));
        jLabel16.setText("LISTA DE CLIENTES");
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, 30));

        frm_cliente.getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 720, 420));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VENTAS");
        setBackground(new java.awt.Color(153, 255, 255));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_venta = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tbl_venta.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tbl_venta.setForeground(new java.awt.Color(0, 102, 255));
        tbl_venta.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_venta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ventaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_venta);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 250, 790, 181));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 204));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/modificar.png"))); // NOI18N
        jLabel1.setText("VENTA N°");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 70, -1, -1));

        txt_num_pedido.setEditable(false);
        txt_num_pedido.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_num_pedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_num_pedido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(txt_num_pedido, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 70, 90, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DETALLES", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 12))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 0, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_producto.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_producto.setForeground(new java.awt.Color(0, 153, 204));
        lbl_producto.setText("PRODUCTO");
        jPanel1.add(lbl_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 33, -1, -1));

        txt_producto.setEditable(false);
        txt_producto.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_producto.setForeground(new java.awt.Color(0, 51, 204));
        txt_producto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_producto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        txt_producto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_productoMouseClicked(evt);
            }
        });
        jPanel1.add(txt_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 30, 390, 25));

        btn_buscar_cliente.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_buscar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/customer.png"))); // NOI18N
        btn_buscar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_clienteActionPerformed(evt);
            }
        });
        jPanel1.add(btn_buscar_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, 25));

        txt_cliente.setEditable(false);
        txt_cliente.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_cliente.setForeground(new java.awt.Color(0, 51, 204));
        txt_cliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cliente.setText("NULL");
        txt_cliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        jPanel1.add(txt_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 390, 25));

        btn_agregar1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_agregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add.png"))); // NOI18N
        btn_agregar1.setMnemonic('p');
        btn_agregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_agregar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btn_agregar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(511, 30, -1, 25));

        lbl_producto14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_producto14.setForeground(new java.awt.Color(0, 153, 204));
        lbl_producto14.setText("CLIENTE");
        jPanel1.add(lbl_producto14, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, 580, 120));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 204));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/alarm.png"))); // NOI18N
        jLabel2.setText("HORA");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 110, -1, -1));

        txt_hora.setEditable(false);
        txt_hora.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_hora.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_hora.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(txt_hora, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 110, 90, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 204));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fecha.png"))); // NOI18N
        jLabel3.setText("FECHA");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 150, -1, -1));

        txt_fecha.setEditable(false);
        txt_fecha.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_fecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_fecha.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(txt_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 150, 90, -1));

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setForeground(new java.awt.Color(0, 102, 204));

        lbl_pie.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("HOSPITAL REGIONAL ### EL TAMBO - HUANCAYO - PERU");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_pie, javax.swing.GroupLayout.PREFERRED_SIZE, 981, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbl_pie))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, 1130, -1));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/dollar.png"))); // NOI18N
        jLabel4.setText("SUB-TOTAL");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 480, -1, -1));

        txt_igv.setEditable(false);
        txt_igv.setBackground(new java.awt.Color(153, 255, 255));
        txt_igv.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txt_igv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_igv.setText("0");
        txt_igv.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(txt_igv, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, 84, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/calculator.png"))); // NOI18N
        jLabel5.setText("IGV");
        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 510, -1, -1));

        txt_subtotal.setEditable(false);
        txt_subtotal.setBackground(new java.awt.Color(153, 255, 255));
        txt_subtotal.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txt_subtotal.setForeground(new java.awt.Color(0, 51, 204));
        txt_subtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_subtotal.setText("0");
        txt_subtotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(txt_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, 84, -1));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/etiqueta.png"))); // NOI18N
        jLabel6.setText("DESCUENTO");
        jLabel6.setToolTipText("");
        jLabel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 540, -1, -1));

        txt_descuento.setBackground(new java.awt.Color(153, 255, 255));
        txt_descuento.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txt_descuento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_descuento.setText("0");
        txt_descuento.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(txt_descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 540, 84, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel7.setText("SALIR");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 10, -1, 20));

        txt_usuario.setEditable(false);
        txt_usuario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_usuario.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        getContentPane().add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 310, -1));

        txt_cambio.setEditable(false);
        txt_cambio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_cambio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cambio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(txt_cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 190, 90, -1));

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cash.png"))); // NOI18N
        jLabel10.setText("TOTAL A PAGAR");
        jLabel10.setToolTipText("");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 510, -1, -1));

        txt_total_pagar.setEditable(false);
        txt_total_pagar.setBackground(new java.awt.Color(153, 255, 255));
        txt_total_pagar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txt_total_pagar.setForeground(new java.awt.Color(255, 0, 0));
        txt_total_pagar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_total_pagar.setText("0");
        txt_total_pagar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(txt_total_pagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 510, 84, -1));

        btn_realizar_venta.setBackground(new java.awt.Color(255, 102, 0));
        btn_realizar_venta.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btn_realizar_venta.setForeground(new java.awt.Color(255, 255, 255));
        btn_realizar_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/compra.png"))); // NOI18N
        btn_realizar_venta.setText("REALIZAR VENTA");
        btn_realizar_venta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_realizar_venta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_realizar_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_realizar_ventaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_realizar_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(919, 474, 180, 90));

        lbl_producto6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_producto6.setForeground(new java.awt.Color(0, 0, 102));
        lbl_producto6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/find_add.png"))); // NOI18N
        lbl_producto6.setText("LISTA DE PEDIDOS:");
        getContentPane().add(lbl_producto6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, -1, -1));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pago.png"))); // NOI18N
        jLabel11.setText("MONTO RECIBIDO");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 480, -1, -1));

        txt_monto_recibido.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txt_monto_recibido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_monto_recibido.setText("0");
        txt_monto_recibido.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txt_monto_recibido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_monto_recibidoActionPerformed(evt);
            }
        });
        txt_monto_recibido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_monto_recibidoKeyReleased(evt);
            }
        });
        getContentPane().add(txt_monto_recibido, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 480, 84, -1));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/dollar.png"))); // NOI18N
        jLabel12.setText("VUELTO");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 540, -1, -1));

        txt_vuelto.setEditable(false);
        txt_vuelto.setBackground(new java.awt.Color(153, 255, 255));
        txt_vuelto.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        txt_vuelto.setForeground(new java.awt.Color(0, 153, 0));
        txt_vuelto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_vuelto.setText("0");
        txt_vuelto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        getContentPane().add(txt_vuelto, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 540, 84, -1));

        btn_quitar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_quitar.setForeground(new java.awt.Color(0, 0, 102));
        btn_quitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/quitar.png"))); // NOI18N
        btn_quitar.setText("QUITAR PEDIDO");
        btn_quitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_quitarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_quitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, -1, 25));

        lbl_fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/doctor.jpg"))); // NOI18N
        getContentPane().add(lbl_fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 320));

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
        getContentPane().add(lbl_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1130, 60));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/employee.png"))); // NOI18N
        jLabel9.setText("USUARIO");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, -1, 20));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 153, 204));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/money.png"))); // NOI18N
        jLabel13.setText("CAMBIO");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 190, -1, -1));

        lbl_serie.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_serie.setForeground(new java.awt.Color(0, 153, 204));
        lbl_serie.setText("..............");
        getContentPane().add(lbl_serie, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 230, 60, 20));

        lbl_producto15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        lbl_producto15.setForeground(new java.awt.Color(0, 153, 204));
        lbl_producto15.setText("SERIE ACTUAL DE BOLETA");
        getContentPane().add(lbl_producto15, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 230, 200, 20));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/code.png"))); // NOI18N
        jLabel14.setText("CÓDIGO");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, -1, 20));

        txt_codigo.setEditable(false);
        txt_codigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_codigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_codigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 1, true));
        getContentPane().add(txt_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 70, 80, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_buscar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_clienteActionPerformed
        frm_cliente.setVisible(true);
        frm_cliente.setAlwaysOnTop(true);
        this.setAlwaysOnTop(false);
        cargarTituloClientes();
        //String sql = "SELECT `id_cli`, `nom_cli`, `apell_cli`, `telf_cli`, `dni_cli`, `direc_cli`, `email_contac` FROM `tcliente` WHERE 1";
    }//GEN-LAST:event_btn_buscar_clienteActionPerformed

    private void btn_realizar_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_realizar_ventaActionPerformed
        //0=si------1=no------2=cancelar

        int filas = tbl_venta.getRowCount();

        if (!txt_monto_recibido.getText().trim().equals("0") && !txt_monto_recibido.getText().trim().isEmpty()) {
            if (filas > 0) {
                int g = JOptionPane.showOptionDialog(getRootPane(), "¿Desea registrar la venta?, no se podra modificar a partir de este punto.", "showOptionDialog", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No", "Cancelar"}, "Si");
                if (g == 0) {
                    registrarVenta();
                    registrarBoleta(Double.parseDouble(txt_total_pagar.getText()));
                    registrarDetalleVenta();
                    restarStock();
                    System.out.println("resto el stock");
                    //0=si------1=no------
                    int opc = JOptionPane.showOptionDialog(getRootPane(), "Se registró la venta, ¿Desea Nota de Pedido?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Si", "No"}, "Si");
                    if (opc == 0) {
                        String sDirectorioTrabajo = System.getProperty("user.dir");
                        System.out.println("El directorio de trabajo es " + sDirectorioTrabajo);
                        int id_venta = Integer.parseInt(txt_num_pedido.getText());
//                    parametros.put("id_venta", id_venta);
//                    mrv = new MyiReportVisor(System.getProperty("user.dir")+"\\src\\Reportes\\boucher.jrxml", parametros);
//                    mrv.exportarAPdf();
                        int pedido = Integer.parseInt(txt_num_pedido.getText());
                        double total = Double.parseDouble(txt_total_pagar.getText());
                        String cliente = txt_cliente.getText();
                        String vendedor = txt_usuario.getText();
                        String fecha = txt_fecha.getText();
                        String hora = txt_hora.getText();
                        double efectivo = Double.parseDouble(txt_monto_recibido.getText());
                        double cambio = Double.parseDouble(txt_vuelto.getText());
                        parametros.put("fecha", fecha);
                        parametros.put("hora", hora);
                        parametros.put("pedido", pedido);
                        parametros.put("total", total);
                        parametros.put("efectivo", efectivo);
                        parametros.put("cambio", cambio);
                        parametros.put("cliente", cliente);
                        parametros.put("vendedor", vendedor);
                        parametros.put("id_venta", id_venta);
                        mrv = new MyiReportVisor(sDirectorioTrabajo + "\\reportes\\boucher.jrxml", parametros, getPageSize());
                        mrv.setNombreArchivo("voucherVenta");
                        try {
                            mrv.exportarAPdfConCopia();
                        } catch (IOException ex) {
                            Logger.getLogger(Ventas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        mrv.dispose();
                        limpiarTablaVenta();
                        loadDatos();
                        operacionesVenta();

                    } else if (opc == 1) {
                        limpiarTablaVenta();
                        loadDatos();
                        operacionesVenta();
                    }
                } else if (g == 1) {
                    txt_monto_recibido.requestFocus();
                }
            } else {
                this.setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(getRootPane(), "Realize un pedido!!");
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE EFECTIVO");
            txt_monto_recibido.requestFocus();
        }


    }//GEN-LAST:event_btn_realizar_ventaActionPerformed

    public void registrarDetalleVenta() {
        int filas, id_venta, id_comprobante;
        Double subtotal = 0.0, descuento, igvs;

        filas = tbl_venta.getRowCount();
        id_venta = Integer.parseInt(txt_num_pedido.getText());
        id_comprobante = Integer.parseInt(lbl_serie.getText());

        igvs = new Farma_inf().igv();

        for (int i = 0; i < filas; i++) {
            try {
                int id_pro_medi = Integer.parseInt(tbl_venta.getValueAt(i, 0).toString());
                double cant = Double.parseDouble(tbl_venta.getValueAt(i, 3).toString());
                subtotal += Double.parseDouble(tbl_venta.getValueAt(i, 5).toString());
                //int fraccion = new Farma_inf().getFraccion(id_pro_medi);
                descuento = Double.parseDouble(tbl_venta.getValueAt(i, 6).toString());
                double pre_unitario = Double.parseDouble(tbl_venta.getValueAt(i, 4).toString());
//                if (fraccion == 1) {
//                    subtotal = cant * pre_unitario;
//                } else {
//                    subtotal = (cant / 10) * pre_unitario;
//                }
//                cmbBlister.isSelected();                
                String sql = "INSERT INTO `tdetalleventa`(`id_venta`, `id_pro_medi`, `id_comprobante`, `cantidad`, `sub_total`, `descuento`, `igv`) VALUES (" + id_venta + " ," + id_pro_medi + "," + id_comprobante + "," + cant + "," + subtotal + "," + descuento + "," + igvs + ")";
                Statement st = cc.createStatement();
                int rs = st.executeUpdate(sql);
                if (rs > 0) {
                    System.out.print("Detalles registrados");
                }
            } catch (NumberFormatException | SQLException e) {
                JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
            }
        }
    }

//    public int getNumId(){
//        int opc=0;
//        String sql = "SELECT id_venta FROM tventa order by `id_venta` DESC limit 1";
//        try {
//            Statement st = cc.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return opc;
//    }
    public void registrarVenta() {
        //int idVenta = getNumId();
        int id_cli = new Users().getCustomerId(txt_cliente.getText());
        String fecha_venta = txt_fecha.getText();
        String time_venta = new Time(System.currentTimeMillis()).toString();
        int id_usu = Integer.parseInt(txt_codigo.getText());

        String sql = "INSERT INTO `tventa`(`fecha_venta`,`hora_venta`,`estado`,`concepto_anul`, `id_usu`, `id_cli`) VALUES ('" + fecha_venta + "','" + time_venta + "',0,'null'," + id_usu + "," + id_cli + ")";
        try {
            Statement st = cc.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Venta Registrada");
            } else {
                System.out.println("Error de registro");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    public void registrarBoleta(Double total) {
        //int id_boleta=0;
        String sql = "INSERT INTO `tcomprobante`(`total_comprobante`) VALUES (" + total + ")";
        try {
            Statement st = cc.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                System.out.println("Boleta registrada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    public int obtenerNumeroBoleta() {
        int num = 0;
        String sql = "SELECT MAX(`id_comprobante`) FROM tcomprobante";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                num = rs.getInt(1);
                lbl_serie.setText("" + num);
                return num;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return -1;
    }

    public void operacionesVenta() {
        Double subtotal = 0.0, dcto = 0.0, igv = new Farma_inf().igv(), total_pagar = 0.0, vuelto = 0.0;
        Double monto = Double.parseDouble(txt_monto_recibido.getText());
        int filas = tbl_venta.getRowCount();
        for (int i = 0; i < filas; i++) {
            subtotal = subtotal + Double.parseDouble(tbl_venta.getValueAt(i, 5).toString());
            dcto += Double.parseDouble(tbl_venta.getValueAt(i, 6).toString());
        }
        igv = subtotal * igv;
        double igva = new Farma_inf().Redondear(igv);
        total_pagar = subtotal + igv;
        double tp = new Farma_inf().Redondear(total_pagar);
        vuelto = monto - total_pagar;
        double v = new Farma_inf().Redondear(vuelto);
        double stotal = new Farma_inf().Redondear(subtotal);
        txt_subtotal.setText("" + stotal);
        txt_igv.setText("" + igva);
        txt_descuento.setText("" + new Farma_inf().Redondear(dcto));
        txt_total_pagar.setText("" + tp);
        txt_vuelto.setText("" + v);
    }

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        double prec_venta = 0.0, precVentaBD = 0.0, importe = 0.0, sub_total = 0.0, igv = 0.0, total = 0.0, dcto = 0.0;
        try {
            int fila = tbl_productos.getSelectedRow();
            if (fila >= 0) {
                if (!txt_cantidad1.getText().trim().isEmpty()) {
                    int stock = Integer.parseInt(tbl_productos.getValueAt(fila, 5).toString());
                    cantidad = Integer.parseInt(txt_cantidad1.getText());
                    if (cantidad <= stock) {
                        codigo = tbl_productos.getValueAt(fila, 0).toString();
                        //lbl_test.setText("" + fila);
                        nombre = tbl_productos.getValueAt(fila, 1).toString();
                        presentacion = tbl_productos.getValueAt(fila, 3).toString();
                        precVentaBD = Double.parseDouble(tbl_productos.getValueAt(fila, 6).toString());
                        prec_venta = Double.parseDouble(txtDescuento.getText());
                        if (!cmbBlister.isSelected()) {
                            importe = new Farma_inf().Redondear(prec_venta * cantidad);
                            
                        } else {
                            cantidad = 10 * Integer.parseInt(txt_cantidad1.getText());
                            importe = Double.parseDouble(tbl_productos.getValueAt(fila, 7).toString()) * Integer.parseInt(txt_cantidad1.getText());
                            precVentaBD = Double.parseDouble(tbl_productos.getValueAt(fila, 7).toString());
                        }

//                        int fraccion = new Farma_inf().getFraccion(Integer.parseInt(codigo));
//                        if (fraccion == 1) {
//                            
//                        } else {
//                            
//                        }
                        //sub_total = sub_total + importe;
                        //igv = 0.19*sub_total;
                        //total = sub_total+igv;
                        //table1 = (DefaultTableModel) tbl_venta.getModel();
                        if (cmbDescuento.isSelected()) {
                            importe = new Farma_inf().Redondear(Double.parseDouble(txtDescuento.getText())*Integer.parseInt(txt_cantidad1.getText()));
                        }
                        dcto = new Farma_inf().Redondear(precVentaBD - prec_venta);
                        Object datos[] = {codigo, nombre, presentacion, cantidad, prec_venta, importe, dcto};
                        table1.addRow(datos);
                        tbl_venta.setModel(table1);
                        operacionesVenta();
                        JOptionPane.showMessageDialog(frm_buscar.getRootPane(), "Se agregó el producto");
                        frm_buscar.dispose();
//                    txt_cantidad1.setText("1");
//                    txt_articulo.setText("");
                        txt_monto_recibido.setText("0");
                        txt_monto_recibido.requestFocus();
                        cmbDescuento.setSelected(false);
                        cmbBlister.setSelected(false);
//                    txt_monto_recibido.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frm_buscar.getRootPane(), "NO SE CUENTA CON LAS EXISTENCIAS NECESARIAS");
                    }
                } else {
                    JOptionPane.showMessageDialog(frm_buscar.getRootPane(), "INGRESE CANTIDAD");
                    //System.out.println("Indique una cantidad");
                    txt_cantidad1.requestFocus();
                }
            } else {
                System.out.println("Selecciona un producto");
            }
        } catch (NumberFormatException | HeadlessException e) {
            System.out.println(e.getMessage());
            System.out.println("Error no se de que");
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void txt_cantidad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cantidad1ActionPerformed
        //btn_add.addKeyListener(new PresionarTecla());
        //btn_add.requestFocus();
        //btn_add.addKeyListener(new PresionarTecla());
        btn_add.doClick();
    }//GEN-LAST:event_txt_cantidad1ActionPerformed

    private void btn_quitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_quitarActionPerformed

        int fila = tbl_venta.getSelectedRow();
        table1 = (DefaultTableModel) tbl_venta.getModel();
        table1.removeRow(fila);
        operacionesVenta();
    }//GEN-LAST:event_btn_quitarActionPerformed

    private void tbl_ventaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ventaMouseClicked
        int filas = tbl_venta.getSelectedRow();
        if (filas >= 0) {
            btn_quitar.setEnabled(true);
        } else {
            btn_quitar.setEnabled(false);
        }

    }//GEN-LAST:event_tbl_ventaMouseClicked

    private void txt_articuloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_articuloKeyReleased
        buscarProductos();
    }//GEN-LAST:event_txt_articuloKeyReleased

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        frm_buscar.dispose();
        frm_buscar.setAlwaysOnTop(false);
        this.setVisible(true);
        txt_monto_recibido.requestFocus();
    }//GEN-LAST:event_btn_salirActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void lbl_logoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_lbl_logoMousePressed

    private void lbl_logoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x - posx, point.y - posy);

    }//GEN-LAST:event_lbl_logoMouseDragged

    private void lbl_logo2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logo2MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        frm_buscar.setLocation(point.x - posa, point.y - posb);
    }//GEN-LAST:event_lbl_logo2MouseDragged

    private void lbl_logo2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logo2MousePressed
        posa = evt.getX();
        posb = evt.getY();
    }//GEN-LAST:event_lbl_logo2MousePressed

    private void btn_agregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar1ActionPerformed
//      this.setAlwaysOnTop(false);
        txt_articulo.setText("");
        txt_monto_recibido.setText("0");
        txt_articulo.requestFocus();
        frm_buscar.setVisible(true);
        frm_buscar.setAlwaysOnTop(true);
//      frm_buscar.setAlwaysOnTop(true);

    }//GEN-LAST:event_btn_agregar1ActionPerformed

    private void btn_agregar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_agregar_clienteActionPerformed
        int fila = tbl_clientes_ventas.getSelectedRow();
        if (fila >= 0) {
            String nom = tbl_clientes_ventas.getValueAt(fila, 1).toString();
            txt_cliente.setText(nom);
            frm_cliente.dispose();
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "Seleccione un cliente");
        }
    }//GEN-LAST:event_btn_agregar_clienteActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        btn_quitar.setEnabled(false);
        tbl_venta.clearSelection();
    }//GEN-LAST:event_formMouseClicked

    private void tbl_productosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_productosMouseClicked
        double precio = 0.0;
        txt_cantidad1.setText("");
        int fila = tbl_productos.getSelectedRow();
        if (!cmbBlister.isSelected()) {
            precio = Double.parseDouble(tbl_productos.getValueAt(fila, 6).toString());
        } else {
            precio = Double.parseDouble(tbl_productos.getValueAt(fila, 7).toString());
        }
        txtDescuento.setText("" + precio);
        //txt_cantidad1.requestFocus();
    }//GEN-LAST:event_tbl_productosMouseClicked

    private void txt_productoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_productoMouseClicked
        txt_articulo.setText("");
        txt_monto_recibido.setText("0");
        frm_buscar.setVisible(true);
        frm_buscar.setAlwaysOnTop(true);
    }//GEN-LAST:event_txt_productoMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Reg_cliente().frm_nuevo_cli.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_monto_recibidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_monto_recibidoActionPerformed
        double montoRecibido = Double.parseDouble(txt_monto_recibido.getText());
        double totalPagar = Double.parseDouble(txt_total_pagar.getText());
        if (montoRecibido >= totalPagar) {
            btn_realizar_venta.doClick();
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "EL VUELTO NO PUEDE SER NEGATIVO");
        }

    }//GEN-LAST:event_txt_monto_recibidoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiarTablaCliente();
        cargarTituloClientes();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tbl_productosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_productosKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            int ultimaFila = tbl_productos.getRowCount() - 1;
            //System.out.println(ultimaFila);
            int filaSeleccionada = tbl_productos.getSelectedRow() - 1;
            //System.out.println("La fila seleccionada es:"+filaSeleccionada);
            if (tbl_productos.getSelectedRow() - 1 == -1) {
                //System.out.println("entro al if y selecciono la fila "+ filaSeleccionada);
                tbl_productos.changeSelection(ultimaFila, 0, false, false);
                txt_cantidad1.requestFocus();
                txt_cantidad1.setText("");
            } else {
                tbl_productos.changeSelection(tbl_productos.getSelectedRow() - 1, 0, false, false);
                txt_cantidad1.requestFocus();
                txt_cantidad1.setText("");
            }

        }
    }//GEN-LAST:event_tbl_productosKeyTyped

//    public class PresionarTecla extends KeyAdapter {
//
//        public void keyPressed(KeyEvent ke) {
//            if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
//                btn_addActionPerformed(null);
//            }
//        }
//    }

    private void txt_cantidad1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantidad1KeyReleased

        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123) {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_buscar.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_cantidad1.requestFocus();
        } else {
            if (txt_cantidad1.getText().trim().length() == 10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_cantidad1KeyReleased

    private void txtDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescuentoActionPerformed
        txt_cantidad1.requestFocus();
    }//GEN-LAST:event_txtDescuentoActionPerformed

    private void txtDescuentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescuentoKeyReleased

    private void txt_monto_recibidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_monto_recibidoKeyReleased
        try {
            if (!txt_monto_recibido.getText().trim().isEmpty()) {
                Double total_pagar = Double.parseDouble(txt_total_pagar.getText());
                Double monto = Double.parseDouble(txt_monto_recibido.getText());
                double vuelto = new Farma_inf().Redondear(monto - total_pagar);
                txt_vuelto.setText("" + vuelto);
            } else {
                txt_vuelto.setText("0.0");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_txt_monto_recibidoKeyReleased

    private void txt_articuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_articuloKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (!txt_articulo.getText().trim().isEmpty()) {
                tbl_productos.requestFocus();
                tbl_productos.getSelectionModel().setSelectionInterval(0, 0);
            } else {
                txt_articulo.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_articuloKeyTyped

    private void tbl_productosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_productosKeyReleased
        int filaSeleccionada = tbl_productos.getSelectedRow();
        //System.out.println("La fila seleccionada es:"+filaSeleccionada);
        double prec = Double.parseDouble(tbl_productos.getValueAt(filaSeleccionada, 6).toString());
        txtDescuento.setText("" + prec);
    }//GEN-LAST:event_tbl_productosKeyReleased

    private void cmbDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDescuentoActionPerformed
        int fila = tbl_productos.getSelectedRow();

        if (fila >= 0) {
            double precio = Double.parseDouble(tbl_productos.getValueAt(fila, 6).toString());
            double dcto = 0.0;
            if (cmbBlister.isSelected()) {
                precio = Double.parseDouble(tbl_productos.getValueAt(fila, 7).toString());
            }
            if (cmbDescuento.isSelected()) {
                dcto = 0.5;
                precio -= dcto;
                txtDescuento.setText("" + new Farma_inf().Redondear(precio));
                txt_cantidad1.requestFocus();
            } else {
                dcto = 0.0;
                precio -= dcto;
                txtDescuento.setText("" + new Farma_inf().Redondear(precio));
                txt_cantidad1.requestFocus();
            }

        } else {
            JOptionPane.showMessageDialog(frm_buscar.getRootPane(), "SELECCIONE UN PRODUCTO");
            cmbDescuento.setSelected(false);
        }

//        if (cmbDescuento.isSelected()) {
//            if (!txtDescuento.getText().trim().isEmpty()) {
//                dcto = 0.5;
//                precio -= dcto;
//                txtDescuento.setText("" + new Farma_inf().Redondear(precio));
//                txt_cantidad1.requestFocus();
//            } else {
//                JOptionPane.showMessageDialog(frm_buscar.getRootPane(), "SELECCIONE UN PRODUCTO");
//            }
//        } else {
//            txtDescuento.setText("" + precio);
//            txt_cantidad1.requestFocus();
//        }

    }//GEN-LAST:event_cmbDescuentoActionPerformed

    private void cmbBlisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBlisterActionPerformed
        double precio = 0.0;
        int fila = tbl_productos.getSelectedRow();
        if (fila >= 0) {
            if (!cmbBlister.isSelected()) {
                precio = Double.parseDouble(tbl_productos.getValueAt(fila, 6).toString());
            } else {
                precio = Double.parseDouble(tbl_productos.getValueAt(fila, 7).toString());
            }
        } else {
            JOptionPane.showMessageDialog(frm_buscar.getRootPane(), "SELECCIONE UN PRODUCTO");
            cmbBlister.setSelected(false);
        }
        txtDescuento.setText("" + precio);
    }//GEN-LAST:event_cmbBlisterActionPerformed

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
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btn_add;
    private javax.swing.JButton btn_agregar1;
    private javax.swing.JButton btn_agregar_cliente;
    private javax.swing.JButton btn_buscar_cliente;
    private javax.swing.JButton btn_quitar;
    private javax.swing.JButton btn_realizar_venta;
    public javax.swing.JButton btn_salir;
    private javax.swing.JCheckBox cmbBlister;
    private javax.swing.JCheckBox cmbDescuento;
    private javax.swing.JDialog frm_buscar;
    public javax.swing.JDialog frm_cliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_fondo;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_logo2;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_producto;
    private javax.swing.JLabel lbl_producto10;
    private javax.swing.JLabel lbl_producto11;
    private javax.swing.JLabel lbl_producto12;
    private javax.swing.JLabel lbl_producto14;
    private javax.swing.JLabel lbl_producto15;
    private javax.swing.JLabel lbl_producto6;
    private javax.swing.JLabel lbl_producto7;
    private javax.swing.JLabel lbl_producto8;
    private javax.swing.JLabel lbl_producto9;
    private javax.swing.JLabel lbl_serie;
    private javax.swing.JTable tbl_clientes_ventas;
    private javax.swing.JTable tbl_productos;
    public javax.swing.JTable tbl_venta;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txt_articulo;
    private javax.swing.JTextField txt_cambio;
    private javax.swing.JTextField txt_cantidad1;
    private javax.swing.JTextField txt_cliente;
    public javax.swing.JTextField txt_codigo;
    private javax.swing.JTextField txt_descuento;
    private javax.swing.JTextField txt_fecha;
    private javax.swing.JTextField txt_hora;
    private javax.swing.JTextField txt_igv;
    private javax.swing.JTextField txt_monto_recibido;
    private javax.swing.JTextField txt_num_pedido;
    private javax.swing.JTextField txt_producto;
    private javax.swing.JTextField txt_subtotal;
    private javax.swing.JTextField txt_total_pagar;
    public javax.swing.JTextField txt_usuario;
    private javax.swing.JTextField txt_vuelto;
    // End of variables declaration//GEN-END:variables

    private void restarStock() {
        int numFilas = tbl_venta.getRowCount();
        try {
            for (int i = 0; i < numFilas; i++) {
                int id = Integer.parseInt(tbl_venta.getValueAt(i, 0).toString());
                System.out.println("id: " + id);
                int cantidad = Integer.parseInt(tbl_venta.getValueAt(i, 3).toString());
                System.out.println("cantidad: " + cantidad);
                int stock = new Farma_inf().getStock(id);
                System.out.println("stock: " + stock);
                int newStock = stock - cantidad;
                System.out.println("stock: " + newStock);

                String sql = "UPDATE `tproducto_medicamento` SET `stock_pro_medi`=" + newStock + " WHERE `id_pro_medi` = " + id + " ";
                Statement st = cc.createStatement();
                int rs = st.executeUpdate(sql);
                if (rs > 0) {
                    System.out.println("Se actualizo en stock del producto :" + id);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }

    private int getPageSize() {
        int filas = tbl_venta.getRowCount();
        System.out.println("cantidad de filas: " + filas);
        int rowCount = 2;//FILAS DE GRACIA
        int fontSize = 7;//TAMAÑO DE LETRA DEL DETAIL
        int rowSize = fontSize + 2;//TAMAÑO DE LA FILA
        int caracteresPorLinea = 16;// CANTIDAD DE CARACTERES PARA QUE PASE A LA SIGTE LINEA
        for (int i = 0; i < filas; i++) {
            String descripcionDeProducto = tbl_venta.getValueAt(i, 1).toString();
            rowCount += (1 + (int) (descripcionDeProducto.length() / caracteresPorLinea));
        }
        int cabecera = 156;
        int piePagina = 217;
        int pageSize = (rowCount * rowSize) + cabecera + piePagina;
        System.out.println("Cantidad de Filas finale:" + rowCount);
        System.out.println("pageSize:" + pageSize);
        return pageSize;
    }

}
