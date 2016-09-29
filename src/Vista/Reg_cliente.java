/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.Conexion;
import Vista.Farma_inf;
import Vista.Ventas;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adolfo
 */
public final class Reg_cliente extends javax.swing.JFrame {
    int posx, posy;
    int posx1, posy1;
    int posx2, posy2;
    
    Connection con = new Conexion().conectar();
    DefaultTableModel model;
    String nom,ape,dir,contra,fecha,sexo,telf,dni;
    int perfil,id;
    public void cabecera(){
    
    String[] cabecera = {"ID","NOMBRES","APELLIDOS","DNI","TELF","SEXO","DIRECCION"};
    model = new DefaultTableModel(null, cabecera);
    tbl_cliente.setModel(model);
       tbl_cliente.getColumnModel().getColumn(0).setPreferredWidth(10);
       tbl_cliente.getColumnModel().getColumn(1).setPreferredWidth(90);
       tbl_cliente.getColumnModel().getColumn(2).setPreferredWidth(90);
       tbl_cliente.getColumnModel().getColumn(3).setPreferredWidth(50);
       tbl_cliente.getColumnModel().getColumn(4).setPreferredWidth(80);
       tbl_cliente.getColumnModel().getColumn(5).setPreferredWidth(10);
       tbl_cliente.getColumnModel().getColumn(6).setPreferredWidth(230);
    
    }
    
    public Reg_cliente() {
        //setUndecorated(true);
        initComponents();
        setAlwaysOnTop(true);
        txt_buscar.requestFocus();
        setIconImage(new ImageIcon(getClass().getResource("/IMG/icoono.jpg")).getImage());
        frm_nuevo_cli.setUndecorated(true);
        frm_modificar_clie.setUndecorated(true);
        frm_nuevo_cli.setAlwaysOnTop(true);
        frm_modificar_clie.setAlwaysOnTop(true);
        //lbl_pie.setText(new Farma_inf().pie());
        lbl_pie.setText(new Farma_inf().pie());
        setLocation(200,70);
        cabecera();
        cargarTabla();
        contar_cliente();
        bloquear();
    }
    public void contar_cliente(){
    int c = tbl_cliente.getRowCount();
    lbl_cliente.setText(""+c);
    }
    
     public void cargarTabla(){
        Conexion con = new Conexion();
        Connection cc = con.conectar();
        String[] datos= new String[7];
        String sql = "SELECT id_cli,nom_cli,apell_cli,telf_cli,dni_cli,sexo_cli,direc_cli FROM  tcliente ";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_cli");
                datos[1] = rs.getString("nom_cli");
                datos[2] = rs.getString("apell_cli");
                datos[3] = rs.getString("telf_cli");
                datos[4] = rs.getString("dni_cli");
                datos[5] = rs.getString("sexo_cli");
                datos[6] = rs.getString("direc_cli");
                model.addRow(datos);
            }
            tbl_cliente.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
     public int comprobar(){
        
        if (!txt_nomcli.getText().trim().isEmpty()) {
            txt_nomcli.transferFocus();
            if (!txt_apeclie.getText().trim().isEmpty()) {
                 txt_apeclie.transferFocus();
                if (!txt_telfcli.getText().trim().isEmpty()) {
                    txt_telfcli.transferFocus();
                    if (!txt_dnicli.getText().trim().isEmpty()) {
                        txt_dnicli.transferFocus();
                        if (!txt_dircli.getText().trim().isEmpty()) {
                            txt_dircli.transferFocus();
                                    return 1;
                        
                        } else {
                                    JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE DIRECCION");
                                    txt_dircli.requestFocus();                                    
                                }                             
                        } else {
                            JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE DNI");
                            txt_dnicli.requestFocus();                            
                        }
                    } else {
                        JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE TELEFONO");
                        txt_telfcli.requestFocus();                        
                    }
               
            } else {
                JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE APELLIDOS");
                txt_apeclie.requestFocus();
                          }
        } else {
            JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE NOMBRE");
            txt_nomcli.requestFocus();
            
        }
        return 0;
    }
    
    public int comprobarActualizacion(){
        
        if (!txt_nomcli1.getText().trim().isEmpty()) {
            txt_nomcli1.transferFocus();
            if (!txt_apeclie1.getText().trim().isEmpty()) {
                 txt_apeclie1.transferFocus();
                if (!txt_telfcli1.getText().trim().isEmpty()) {
                    txt_telfcli1.transferFocus();
                    if (!txt_dnicli1.getText().trim().isEmpty()) {
                        txt_dnicli1.transferFocus();
                        if (!txt_dircli1.getText().trim().isEmpty()) {
                            txt_dircli1.transferFocus();
                                    return 1;
                        
                        } else {
                                    JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE DIRECCION");
                                    txt_dircli1.requestFocus();                                    
                                }                             
                        } else {
                            JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE DNI");
                            txt_dnicli1.requestFocus();                            
                        }
                    } else {
                        JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE TELEFONO");
                        txt_telfcli1.requestFocus();                        
                    }
               
            } else {
                JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE APELLIDOS");
                txt_apeclie1.requestFocus();
                          }
        } else {
            JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE NOMBRE");
            txt_nomcli1.requestFocus();
            
        }
        return 0;
    }
     
     
     public void limpiar(){
        txt_nomcli.setText("");
        txt_apeclie.setText("");
        txt_dnicli.setText("");
        txt_telfcli.setText("");
        txt_dircli.setText("");
        txt_nomcli.requestFocus();
   
    }
     
     public int verificarExisteCliente(String dni){
         int opc = 0;
         String sql = "SELECT `dni_cli` FROM `tcliente` WHERE `dni_cli` ="+dni;
         try {
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             if (rs.next()) {
                 opc = 1;
             } else {
                 opc=0;
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
         }
         return opc;
     }
     
    public void Reg_clientes(){
    String nom_cli = txt_nomcli.getText();
    String ape_cli = txt_apeclie.getText();
    String telf_cli = txt_telfcli.getText();
    String dni_cli  =  txt_dnicli.getText();
    String dir1_cli = txt_dircli.getText();
    String sexo_cli = cmb_sexo_cli.getSelectedItem().toString();
    Conexion c = new Conexion();
    Connection cn = c.conectar();
    String sql = "INSERT INTO tcliente(nom_cli,apell_cli,telf_cli,dni_cli,sexo_cli,direc_cli) VALUES ('"+nom_cli+"','"+ape_cli+"','"+telf_cli+"',"+dni_cli+",'"+sexo_cli+"','"+dir1_cli+"')";
      
         try {
            Statement st = cn.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
            //JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO CON EXITO");
                //0=si------1=no------2=cancelar
                limpiarTabla();
                cargarTabla();
                contar_cliente();
       
                int opc = JOptionPane.showOptionDialog(btn_guardarcli, "CLIENTE REGISTRADO, ¿DESEA REGISTRAR MAS CLIENTES?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
                if (opc == 0) {
                    limpiar();

                } else if (opc == 1) {
                    txt_buscar.requestFocus();
                    frm_nuevo_cli.dispose();
                    bloquear();
                    
                }
            }
            
            
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), e.getMessage());
        }
    
    
         
    }

    public void buscarClientes() {
        limpiarTabla();
        String cli = txt_buscar.getText();
        String datos[] = new String[7];
        String sql = "SELECT id_cli, nom_cli, apell_cli,telf_cli,dni_cli,sexo_cli,direc_cli FROM tcliente WHERE nom_cli LIKE '" + cli + "%' OR nom_cli LIKE '%" + cli + "' OR dni_cli LIKE '" + cli + "%' OR dni_cli LIKE '%" + cli + "'";
        try {
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_cli");
                datos[1] = rs.getString("nom_cli");
                datos[2] = rs.getString("apell_cli");
                datos[3] = rs.getString("dni_cli");
                datos[4] = rs.getString("telf_cli");
                datos[5] = rs.getString("sexo_cli");
                datos[6] = rs.getString("direc_cli");
                model.addRow(datos);
            }
            tbl_cliente.setModel(model);
            int filas =  tbl_cliente.getRowCount();
            lbl_cliente.setText("" + filas);
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    
    
    public void actualizarClienteEnBD(){
    int idCliente = Integer.parseInt(txt_idcli.getText());
    String nombres = txt_nomcli1.getText();
    String apellidos = txt_apeclie1.getText();
    String telefono = txt_telfcli1.getText();
    int dni= Integer.parseInt(txt_dnicli1.getText());
    String direccion = txt_dircli1.getText();
    String sexo = cmb_sexo_cli1.getSelectedItem().toString();
    //String pssc_cli = txt_dircli1.getText();
        
    Conexion c = new Conexion();
    Connection cn = c.conectar();
    String sql = "UPDATE TCLIENTE SET nom_cli='"+nombres+"', apell_cli='"+apellidos+"', telf_cli='"+telefono+"', dni_cli="+dni+",direc_cli='"+direccion+"',sexo_cli='"+sexo+"' WHERE id_cli="+idCliente;
        try {
            Statement st = cn.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
                
                limpiarTabla();
                cargarTabla();
                contar_cliente();
                JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "REGISTRO ACTUALIZADO");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), e.getMessage());
        }
    }
    
    
    public void bloquear(){
        btn_modifcar.setEnabled(false);
        btn_eliminar.setEnabled(false);
    }
    public void Desbloquear(){
        btn_modifcar.setEnabled(true);
        btn_eliminar.setEnabled(true);
    }
     public void modificar_cliente(){
        try {
        int fila = tbl_cliente.getSelectedRow();
        id = Integer.parseInt(tbl_cliente.getValueAt(fila, 0).toString());
        nom = tbl_cliente.getValueAt(fila, 1).toString();
        ape = tbl_cliente.getValueAt(fila, 2).toString();
        dni = tbl_cliente.getValueAt(fila, 3).toString();
        telf = tbl_cliente.getValueAt(fila, 4).toString();
        sexo = tbl_cliente.getValueAt(fila, 5).toString();
        dir = tbl_cliente.getValueAt(fila, 6).toString();
        txt_idcli.setText(""+id);
        txt_nomcli1.setText(nom);
        txt_apeclie1.setText(ape);
        txt_dnicli1.setText(""+dni);
        txt_sexo_cli.setText(sexo);
        txt_telfcli1.setText(""+telf);
        txt_dircli1.setText(dir);
             
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e.getMessage());
         }     
         //perfil = Integer.valueOf(tbl_usuario.getValueAt(fila, 6).toString());
        
        
        
        //txt_fecha_ingreso.setDateFormatString(fecha);
        //cbx_perfil.setSelectedIndex(perfil);
        
    }
      public void limpiarTabla() {
        for (int i = 0; i < tbl_cliente.getRowCount(); i++) {
            model.removeRow(i);
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

        frm_nuevo_cli = new javax.swing.JDialog();
        jLabel29 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txt_nomcli = new javax.swing.JTextField();
        txt_apeclie = new javax.swing.JTextField();
        txt_dnicli = new javax.swing.JTextField();
        txt_telfcli = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        DNI = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cmb_sexo_cli = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        txt_dircli = new javax.swing.JTextField();
        btn_guardarcli = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbl_pie_nuevo_cli = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        frm_modificar_clie = new javax.swing.JDialog();
        jLabel30 = new javax.swing.JLabel();
        btn_actualizarcli = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_nomcli1 = new javax.swing.JTextField();
        txt_apeclie1 = new javax.swing.JTextField();
        txt_dnicli1 = new javax.swing.JTextField();
        txt_telfcli1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        DNI1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_dircli1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        cmb_sexo_cli1 = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        txt_idcli = new javax.swing.JTextField();
        txt_sexo_cli = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_cliente = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txt_buscar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btn_eliminar = new javax.swing.JButton();
        btn_modifcar = new javax.swing.JButton();
        btn_nuevo = new javax.swing.JButton();
        lbl_cliente = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        frm_nuevo_cli.setBounds(new java.awt.Rectangle(370, 180, 610, 400));
        frm_nuevo_cli.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel29.setText("SALIR");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel29.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel29MouseDragged(evt);
            }
        });
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel29MousePressed(evt);
            }
        });
        frm_nuevo_cli.getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532072_Add-Male-User.png"))); // NOI18N
        frm_nuevo_cli.getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 69, 69));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)), "DATOS DEL CLIENTE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(204, 0, 0))); // NOI18N
        jPanel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nomcli.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nomcli.setForeground(new java.awt.Color(0, 0, 102));
        txt_nomcli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nomcli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_nomcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nomcliActionPerformed(evt);
            }
        });
        txt_nomcli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nomcliKeyTyped(evt);
            }
        });
        jPanel1.add(txt_nomcli, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 36, 250, -1));

        txt_apeclie.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_apeclie.setForeground(new java.awt.Color(0, 0, 102));
        txt_apeclie.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_apeclie.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_apeclie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_apeclieActionPerformed(evt);
            }
        });
        txt_apeclie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_apeclieKeyTyped(evt);
            }
        });
        jPanel1.add(txt_apeclie, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 250, -1));

        txt_dnicli.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dnicli.setForeground(new java.awt.Color(0, 0, 102));
        txt_dnicli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dnicli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_dnicli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dnicliActionPerformed(evt);
            }
        });
        txt_dnicli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dnicliKeyTyped(evt);
            }
        });
        jPanel1.add(txt_dnicli, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 250, -1));

        txt_telfcli.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_telfcli.setForeground(new java.awt.Color(0, 0, 102));
        txt_telfcli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telfcli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_telfcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telfcliActionPerformed(evt);
            }
        });
        txt_telfcli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telfcliKeyTyped(evt);
            }
        });
        jPanel1.add(txt_telfcli, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 250, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("APELLIDO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 70, -1));

        DNI.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        DNI.setForeground(new java.awt.Color(0, 0, 102));
        DNI.setText("DNI");
        jPanel1.add(DNI, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("TELEFONO");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 80, -1));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("NOMBRE");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("SEXO");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        cmb_sexo_cli.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        cmb_sexo_cli.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "M", "F" }));
        cmb_sexo_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_sexo_cliActionPerformed(evt);
            }
        });
        jPanel1.add(cmb_sexo_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 60, 20));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("DIRECCION");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        txt_dircli.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dircli.setForeground(new java.awt.Color(0, 0, 102));
        txt_dircli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dircli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_dircli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dircliActionPerformed(evt);
            }
        });
        txt_dircli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dircliKeyPressed(evt);
            }
        });
        jPanel1.add(txt_dircli, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 250, 20));

        frm_nuevo_cli.getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 410, 230));

        btn_guardarcli.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_guardarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532190_3floppy_unmount.png"))); // NOI18N
        btn_guardarcli.setText("GUARDAR");
        btn_guardarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarcliActionPerformed(evt);
            }
        });
        frm_nuevo_cli.getContentPane().add(btn_guardarcli, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 140, 47));

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532351_button_cancel.png"))); // NOI18N
        jButton2.setText("CANCELAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        frm_nuevo_cli.getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, 140, 47));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel16.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel16MouseDragged(evt);
            }
        });
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel16MousePressed(evt);
            }
        });
        frm_nuevo_cli.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 58));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("NUEVO CLIENTE");
        frm_nuevo_cli.getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));

        jPanel4.setBackground(new java.awt.Color(51, 102, 255));

        lbl_pie_nuevo_cli.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_pie_nuevo_cli, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(lbl_pie_nuevo_cli)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        frm_nuevo_cli.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 610, 20));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        frm_nuevo_cli.getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 610, 320));

        frm_modificar_clie.setBounds(new java.awt.Rectangle(370, 180, 620, 450));
        frm_modificar_clie.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel30.setText("SALIR");
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel30.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel30MouseDragged(evt);
            }
        });
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel30MousePressed(evt);
            }
        });
        frm_modificar_clie.getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, -1, 30));

        btn_actualizarcli.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btn_actualizarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453410879_gtk-refresh.png"))); // NOI18N
        btn_actualizarcli.setText("ACTUALIZAR");
        btn_actualizarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarcliActionPerformed(evt);
            }
        });
        frm_modificar_clie.getContentPane().add(btn_actualizarcli, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, 160, 47));

        btn_cancelar.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532351_button_cancel.png"))); // NOI18N
        btn_cancelar.setText("CANCELAR");
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        frm_modificar_clie.getContentPane().add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 240, 160, 47));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)), "DATOS DEL CLIENTE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(204, 0, 0))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(0, 0, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nomcli1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nomcli1.setForeground(new java.awt.Color(0, 0, 102));
        txt_nomcli1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nomcli1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_nomcli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nomcli1ActionPerformed(evt);
            }
        });
        txt_nomcli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nomcli1KeyTyped(evt);
            }
        });
        jPanel2.add(txt_nomcli1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 250, -1));

        txt_apeclie1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_apeclie1.setForeground(new java.awt.Color(0, 0, 102));
        txt_apeclie1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_apeclie1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_apeclie1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_apeclie1ActionPerformed(evt);
            }
        });
        txt_apeclie1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_apeclie1KeyTyped(evt);
            }
        });
        jPanel2.add(txt_apeclie1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 250, -1));

        txt_dnicli1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dnicli1.setForeground(new java.awt.Color(0, 0, 102));
        txt_dnicli1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dnicli1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_dnicli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dnicli1ActionPerformed(evt);
            }
        });
        txt_dnicli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dnicli1KeyTyped(evt);
            }
        });
        jPanel2.add(txt_dnicli1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 250, -1));

        txt_telfcli1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_telfcli1.setForeground(new java.awt.Color(0, 0, 102));
        txt_telfcli1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telfcli1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_telfcli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telfcli1ActionPerformed(evt);
            }
        });
        txt_telfcli1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telfcli1KeyTyped(evt);
            }
        });
        jPanel2.add(txt_telfcli1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 250, -1));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("APELLIDO");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        DNI1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        DNI1.setForeground(new java.awt.Color(0, 0, 102));
        DNI1.setText("DNI");
        jPanel2.add(DNI1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("TELEFONO");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("NOMBRE");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 67, -1, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("DIRECCION");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        txt_dircli1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dircli1.setForeground(new java.awt.Color(0, 0, 102));
        txt_dircli1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dircli1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        jPanel2.add(txt_dircli1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 250, -1));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("SEXO");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        cmb_sexo_cli1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "M", "F" }));
        cmb_sexo_cli1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        cmb_sexo_cli1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_sexo_cli1ActionPerformed(evt);
            }
        });
        jPanel2.add(cmb_sexo_cli1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 50, 20));

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("ID");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 50, -1));

        txt_idcli.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_idcli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_idcli.setEnabled(false);
        jPanel2.add(txt_idcli, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 30, -1));

        txt_sexo_cli.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_sexo_cli.setForeground(new java.awt.Color(0, 0, 102));
        txt_sexo_cli.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_sexo_cli.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_sexo_cli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_sexo_cliActionPerformed(evt);
            }
        });
        jPanel2.add(txt_sexo_cli, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 50, 20));

        frm_modificar_clie.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 395, 260));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel19.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel19MouseDragged(evt);
            }
        });
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel19MousePressed(evt);
            }
        });
        frm_modificar_clie.getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -8, 620, 70));

        jPanel5.setBackground(new java.awt.Color(0, 102, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        frm_modificar_clie.getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 620, 20));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255), 2));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("MODIFICAR CLIENTE");
        jPanel8.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449533434_Edit-Male-User.png"))); // NOI18N
        jPanel8.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, -1, 60));

        frm_modificar_clie.getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 620, 370));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel28.setText("SALIR");
        jLabel28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel28.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel28MouseDragged(evt);
            }
        });
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel28MousePressed(evt);
            }
        });
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 10, -1, 30));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel14.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel14MouseDragged(evt);
            }
        });
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel14MousePressed(evt);
            }
        });
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453330342_Login Manager.png"))); // NOI18N
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 43, 42));

        tbl_cliente.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbl_cliente.setForeground(new java.awt.Color(0, 0, 204));
        tbl_cliente.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_cliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_clienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_cliente);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 910, 270));

        jPanel3.setBackground(new java.awt.Color(0, 102, 255));

        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("jLabel23");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(lbl_pie, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lbl_pie)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 940, 20));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPanel6KeyTyped(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_buscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_buscar.setForeground(new java.awt.Color(0, 0, 102));
        txt_buscar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true));
        txt_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_buscarMouseClicked(evt);
            }
        });
        txt_buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_buscarKeyReleased(evt);
            }
        });
        jPanel6.add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 280, 30));

        jLabel5.setFont(new java.awt.Font("Dialog", 2, 14)); // NOI18N
        jLabel5.setText("BUSCAR");
        jPanel6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 120, -1, 30));

        btn_eliminar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449494490_f-cross_256.png"))); // NOI18N
        btn_eliminar.setText("ELIMINAR");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, -1, 55));

        btn_modifcar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_modifcar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449494334_pencil.png"))); // NOI18N
        btn_modifcar.setText("MODIFICAR");
        btn_modifcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifcarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_modifcar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, -1, 55));

        btn_nuevo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449530684_gnome-app-install.png"))); // NOI18N
        btn_nuevo.setText("NUEVO");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jPanel6.add(btn_nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 130, 57));

        lbl_cliente.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbl_cliente.setForeground(new java.awt.Color(0, 0, 204));
        jPanel6.add(lbl_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, 40, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("REGISTROS");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 19, -1, 50));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("REGISTRO DE CLIENTES");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, -1, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 940, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        frm_nuevo_cli.setVisible(true);
        limpiar();

    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_modifcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifcarActionPerformed
        frm_modificar_clie.setVisible(true);
        modificar_cliente();
    }//GEN-LAST:event_btn_modifcarActionPerformed

    private void txt_nomcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomcliActionPerformed
        txt_nomcli.transferFocus();
    }//GEN-LAST:event_txt_nomcliActionPerformed

    private void txt_nomcliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomcliKeyTyped
        int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE SOLO LETRAS");
            txt_nomcli.requestFocus();
            //txt_nombres.requestFocus();
        }
    }//GEN-LAST:event_txt_nomcliKeyTyped

    private void txt_apeclieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_apeclieActionPerformed
        txt_apeclie.transferFocus();
    }//GEN-LAST:event_txt_apeclieActionPerformed

    private void txt_apeclieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apeclieKeyTyped
        int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE SOLO LETRAS");
            txt_apeclie.requestFocus();
            //txt_nombres.requestFocus();
        }
    }//GEN-LAST:event_txt_apeclieKeyTyped

    private void txt_dnicliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dnicliActionPerformed
        txt_dnicli.transferFocus();
    }//GEN-LAST:event_txt_dnicliActionPerformed

    private void txt_dnicliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dnicliKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_dnicli.requestFocus();

        } else {
            if (txt_dnicli.getText().trim().length()==8) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_dnicliKeyTyped

    private void txt_telfcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telfcliActionPerformed
        txt_telfcli.transferFocus();

    }//GEN-LAST:event_txt_telfcliActionPerformed

    private void txt_telfcliKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telfcliKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_telfcli.requestFocus();
        } else {
            if (txt_telfcli.getText().trim().length()==10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_telfcliKeyTyped

    private void cmb_sexo_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_sexo_cliActionPerformed
        cmb_sexo_cli.transferFocus();
    }//GEN-LAST:event_cmb_sexo_cliActionPerformed

    private void txt_dircliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dircliActionPerformed
       
    }//GEN-LAST:event_txt_dircliActionPerformed

    private void btn_guardarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarcliActionPerformed
        
        if (txt_dnicli.getText().trim().length() < 8) {
            JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "EL DNI DEBE TENER 8 DIGITOS");
            txt_dnicli.requestFocus();
        } else {
            if (comprobar() != 0) {
                String dni = txt_dnicli.getText();
                if (verificarExisteCliente(dni) == 0) {
                    Reg_clientes();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(frm_nuevo_cli.getRootPane(), "EXISTE CLIENTE CON EL DNI INGRESADO");
                    txt_nomcli.requestFocus();
                    limpiar();
                }
            } else {
            }
        }
    }//GEN-LAST:event_btn_guardarcliActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        frm_nuevo_cli.dispose();
        txt_buscar.requestFocus();
        bloquear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        System.out.println("Saliendo del formulario");
        txt_buscar.setText("");
        txt_buscar.requestFocus();
        frm_modificar_clie.dispose();
        bloquear();
        
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void txt_apeclie1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_apeclie1ActionPerformed
        txt_apeclie1.transferFocus();
    }//GEN-LAST:event_txt_apeclie1ActionPerformed

    private void cmb_sexo_cli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_sexo_cli1ActionPerformed
        if (cmb_sexo_cli1.getSelectedIndex()==0) {
            txt_sexo_cli.setText("M");
            cmb_sexo_cli1.transferFocus();
        } else {
            txt_sexo_cli.setText("F");
            cmb_sexo_cli1.transferFocus();
        }
    }//GEN-LAST:event_cmb_sexo_cli1ActionPerformed

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel28MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MousePressed
         
    }//GEN-LAST:event_jLabel28MousePressed

    private void jLabel28MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseDragged
      
    }//GEN-LAST:event_jLabel28MouseDragged

    private void jLabel14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_jLabel14MousePressed

    private void jLabel14MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseDragged
       Point point = MouseInfo.getPointerInfo().getLocation();
       this.setLocation(point.x-posx, point.y-posy);
    }//GEN-LAST:event_jLabel14MouseDragged

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        bloquear();
    }//GEN-LAST:event_formMouseClicked

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
       int row = tbl_cliente.getSelectedRow();
        int cod = Integer.parseInt(tbl_cliente.getValueAt(row,0).toString());
        Conexion c = new Conexion();
        Connection cn = c.conectar();
        String sql = "DELETE FROM `tcliente` WHERE `id_cli` = "+cod+"";

        int opc = JOptionPane.showOptionDialog(btn_eliminar, "¿ESTA SEGURO QUE DESEA ELIMINAR ESTE REGISTRO?, SE ELIMINARA TODO EL HISTORIAL DE ESTE CLIENTE", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
        if (opc == 0) {
            try {
                Statement st = cn.createStatement();
                getVentasClienteAndDelDetalles(cod);
                int rs = st.executeUpdate(sql);

                if (rs > 0) {
                    limpiarTabla();
                    cargarTabla();
                    bloquear();
                    txt_buscar.setText("");
                    txt_buscar.requestFocus();

                } else {

                }

            } catch (SQLException | HeadlessException e) {
                JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
            }
            JOptionPane.showMessageDialog(getRootPane(), "REGISTRO ELIMINADO");
        } else
            txt_buscar.requestFocus();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void txt_sexo_cliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_sexo_cliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sexo_cliActionPerformed

    private void jLabel29MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel29MouseDragged

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
       frm_nuevo_cli.dispose();
       txt_buscar.transferFocus();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel29MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel29MousePressed

    private void jLabel30MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseDragged
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel30MouseDragged

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        frm_modificar_clie.dispose();
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel30MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel30MousePressed

    private void jLabel19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MousePressed
        posx2 = evt.getX();
        posy2 = evt.getY();    
    }//GEN-LAST:event_jLabel19MousePressed

    private void jLabel16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MousePressed
        posx1 = evt.getX();
        posy1 = evt.getY();  
    }//GEN-LAST:event_jLabel16MousePressed

    private void jLabel16MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        frm_nuevo_cli.setLocation(point.x-posx1, point.y-posy1);
    }//GEN-LAST:event_jLabel16MouseDragged

    private void jLabel19MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        frm_modificar_clie.setLocation(point.x-posx2, point.y-posy2);
    }//GEN-LAST:event_jLabel19MouseDragged

    private void btn_actualizarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarcliActionPerformed
        if(txt_dnicli1.getText().trim().length()<8){
            JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "DNI DEBE TENER 8 DIGITOS");
            txt_dnicli1.requestFocus();
            
        }else{
        if (comprobarActualizacion()!=0) {
             frm_modificar_clie.dispose();
             actualizarClienteEnBD(); 
             bloquear(); 
             txt_buscar.setText("");
             txt_buscar.requestFocus();
        }else{
            System.out.println("Ingrese al else de actualizar ");
        }
        }
    }//GEN-LAST:event_btn_actualizarcliActionPerformed

    private void txt_nomcli1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomcli1KeyTyped
       int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE SOLO LETRAS");
            txt_nomcli.requestFocus();
            //txt_nombres.requestFocus();
        }
    }//GEN-LAST:event_txt_nomcli1KeyTyped

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        buscarClientes();
    }//GEN-LAST:event_txt_buscarKeyReleased

    private void tbl_clienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_clienteMouseClicked
       int fila = tbl_cliente.getSelectedRow();
        if (fila != -1) {
            Desbloquear();

        }
    }//GEN-LAST:event_tbl_clienteMouseClicked

    private void txt_nomcli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomcli1ActionPerformed
        txt_nomcli1.transferFocus();
    }//GEN-LAST:event_txt_nomcli1ActionPerformed

    private void txt_apeclie1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apeclie1KeyTyped
       int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE SOLO LETRAS");
            txt_apeclie1.requestFocus();
            //txt_nombres.requestFocus();
        }
    }//GEN-LAST:event_txt_apeclie1KeyTyped

    private void txt_telfcli1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telfcli1KeyTyped
       int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_telfcli1.requestFocus();

        } else {
            if (txt_telfcli1.getText().trim().length()==10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_telfcli1KeyTyped

    private void txt_dnicli1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dnicli1KeyTyped
       int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar_clie.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_dnicli1.requestFocus();

        } else {
            if (txt_dnicli1.getText().trim().length()==8) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_dnicli1KeyTyped

    private void txt_telfcli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telfcli1ActionPerformed
       txt_telfcli1.transferFocus();
    }//GEN-LAST:event_txt_telfcli1ActionPerformed

    private void txt_dnicli1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dnicli1ActionPerformed
        cmb_sexo_cli1.requestFocus();
        //txt_dnicli1.transferFocus();
        
    }//GEN-LAST:event_txt_dnicli1ActionPerformed

    private void jPanel6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel6KeyTyped
        txt_buscar.requestFocus();
    }//GEN-LAST:event_jPanel6KeyTyped

    private void txt_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_buscarMouseClicked
        bloquear();
    }//GEN-LAST:event_txt_buscarMouseClicked

    private void txt_dircliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dircliKeyPressed
       
    }//GEN-LAST:event_txt_dircliKeyPressed

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
            java.util.logging.Logger.getLogger(Reg_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reg_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reg_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reg_cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Reg_cliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DNI;
    private javax.swing.JLabel DNI1;
    private javax.swing.JButton btn_actualizarcli;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_guardarcli;
    private javax.swing.JButton btn_modifcar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JComboBox cmb_sexo_cli;
    private javax.swing.JComboBox cmb_sexo_cli1;
    private javax.swing.JDialog frm_modificar_clie;
    public javax.swing.JDialog frm_nuevo_cli;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_cliente;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_pie_nuevo_cli;
    private javax.swing.JTable tbl_cliente;
    private javax.swing.JTextField txt_apeclie;
    private javax.swing.JTextField txt_apeclie1;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_dircli;
    private javax.swing.JTextField txt_dircli1;
    private javax.swing.JTextField txt_dnicli;
    private javax.swing.JTextField txt_dnicli1;
    private javax.swing.JTextField txt_idcli;
    private javax.swing.JTextField txt_nomcli;
    private javax.swing.JTextField txt_nomcli1;
    private javax.swing.JTextField txt_sexo_cli;
    private javax.swing.JTextField txt_telfcli;
    private javax.swing.JTextField txt_telfcli1;
    // End of variables declaration//GEN-END:variables

    public void getVentasClienteAndDelDetalles(int idCliente){
        String sql= "SELECT tventa.id_venta from tcliente INNER JOIN tventa on tcliente.id_cli = tventa.id_cli WHERE tcliente.id_cli ="+idCliente+"";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int venta = rs.getInt("tventa.id_venta");
                
                System.out.println(venta);
                borrarDetallesCliente(venta);
                borrarVentasCliente(venta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void borrarVentasCliente(int idVenta) {
        String sql = "DELETE FROM `tventa` WHERE `id_venta`=" + idVenta;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs>0) {
                System.out.println("se borró la venta "+idVenta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void borrarDetallesCliente(int idVenta){
        String sql= "DELETE FROM `tdetalleventa` WHERE `id_venta` =  "+idVenta;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs>0) {
                System.out.println("Se borro el detalle de la venta "+idVenta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}
