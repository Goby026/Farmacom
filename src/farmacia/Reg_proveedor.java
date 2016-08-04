/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmacia;

import Control.Conexion;
import Vista.Farma_inf;
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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Adolfo
 */
public final class Reg_proveedor extends javax.swing.JFrame {

    int posx, posy;
    int posx1, posy1;
    int posx2, posy2;
    int posx3, posy3;
    int posx4, posy4;
    DefaultTableModel model;
    DefaultTableModel model1;
    String nom,ape,dir,email,contacto,contac,nom_contac,telf_contac;
    String ruc,telf;
    int id;
    Connection cc = new Conexion().conectar();
    private int cabecera;
    
   
    
    public Reg_proveedor() {
       
         //setUndecorated(true);
         initComponents();
         setAlwaysOnTop(true);
         txt_buscar.requestFocus();
         frm_prov_nuevo.setUndecorated(true);
         frm_prov_modi.setUndecorated(true);
         frm_prov_nuevo.setAlwaysOnTop(true);
         frm_prov_modi.setAlwaysOnTop(true);
         setLocationRelativeTo(this);
         lbl_pie.setText(new Farma_inf().pie());
         cabecera();
         cargarTabla();
         bloquear();
        
    }
    
   
    public void limpiarTabla() {
        for (int i = 0; i < tbl_proveedor.getRowCount(); i++) {
            model.removeRow(i);
            i -= 1;
        }
    }

    public void cabecera(){
    
       String[] cabecera = {"ID","NOMBRE","RUC","TELF","DIRECCION","EMAIL","CONTACTO","TELF-CONTACTO"};
       model = new DefaultTableModel(null,cabecera);
       tbl_proveedor.setModel(model);
       tbl_proveedor.getColumnModel().getColumn(0).setPreferredWidth(10);
       tbl_proveedor.getColumnModel().getColumn(1).setPreferredWidth(120);
       tbl_proveedor.getColumnModel().getColumn(2).setPreferredWidth(10);
       tbl_proveedor.getColumnModel().getColumn(3).setPreferredWidth(5);
       tbl_proveedor.getColumnModel().getColumn(4).setPreferredWidth(130);
       tbl_proveedor.getColumnModel().getColumn(5).setPreferredWidth(120);
       tbl_proveedor.getColumnModel().getColumn(6).setPreferredWidth(50);
       tbl_proveedor.getColumnModel().getColumn(7).setPreferredWidth(20);
    
    }
   
   
    public void cargarTabla(){
     Conexion con = new Conexion();
        Connection cc = con.conectar();
        String[] datos= new String[8];
        String sql = "SELECT tproveedor.id_provee , tproveedor.nom_provee, tproveedor.ruc_provee, tproveedor.telf_provee, tproveedor.dir_provee, tproveedor.email_provee, tproveedor.nom_contac, tproveedor.telf_contac FROM tproveedor";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_provee");
                datos[1] = rs.getString("nom_provee");
                datos[2] = rs.getString("ruc_provee");
                datos[3] = rs.getString("telf_provee");
                datos[4] = rs.getString("dir_provee");
                datos[5] = rs.getString("email_provee");
                datos[6] = rs.getString("nom_contac");
                datos[7] = rs.getString("telf_contac");
                model.addRow(datos);
            }
            tbl_proveedor.setModel(model);
            int filas =  tbl_proveedor.getRowCount();
            lbl_registros.setText("" + filas);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    
     public int comprobar(){
        if (!txt_nom_prove.getText().trim().isEmpty()) {
            txt_nom_prove.transferFocus();
            if (!txt_ruc_prove.getText().trim().isEmpty()) {
                 txt_ruc_prove.transferFocus();
                if (!txt_telf_prove.getText().trim().isEmpty()) {
                    txt_telf_prove.transferFocus();
                        if (!txt_dir_prove.getText().trim().isEmpty()) {
                            txt_dir_prove.transferFocus();
                                if (!txt_email_prove.getText().trim().isEmpty()) {
                                    txt_email_prove.transferFocus();
                                    if (!txt_nomcontac_prove.getText().trim().isEmpty()) {
                                            txt_nomcontac_prove.transferFocus();
                                                if (!txt_telfcontac_prove.getText().trim().isEmpty()) {
                                                        txt_telfcontac_prove.transferFocus();
                                                         return 1;
                                            } else {
                                                JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE TELEFONO DE CONTACTO");
                                                txt_telfcontac_prove.requestFocus();
                                            }                           
                                       } else {
                                    JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE NOMBRE DE CONTACTO");
                                    txt_nomcontac_prove.requestFocus();
                                    }           
                                } else {
                                    JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE EMAIL");
                                    txt_email_prove.requestFocus();
                                }
                                
                        }else {
                            JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE DIRECCION");
                            txt_dir_prove.requestFocus();                            
                        }
                    } else {
                        JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE TELEFONO");
                        txt_telf_prove.requestFocus();                        
                    }
                
            } else {
                JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE RUC");
                txt_ruc_prove.requestFocus();
                          }
        }else {
            JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE NOMBRE");
            txt_nom_prove.requestFocus();
            
                 }
        return 0;
    }
     
    public void buscarClientes() {
        limpiarTabla();
        String pro = txt_buscar.getText();
        String datos[] = new String[8];
        String sql = "SELECT id_provee, nom_provee, ruc_provee, telf_provee, dir_provee, email_provee, nom_contac, telf_contac FROM tproveedor WHERE nom_provee LIKE  '" + pro + "%' OR nom_provee LIKE '%" + pro + "' OR ruc_provee LIKE '" + pro + "%' OR ruc_provee LIKE '%" + pro + "'";
        try {
             Statement st = cc.createStatement();
             ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_provee");
                datos[1] = rs.getString("nom_provee");
                datos[2] = rs.getString("ruc_provee");
                datos[3] = rs.getString("telf_provee");
                datos[4] = rs.getString("dir_provee");
                datos[5] = rs.getString("email_provee");
                datos[6] = rs.getString("nom_contac");
                datos[7] = rs.getString("telf_contac");
                model.addRow(datos);
            }
            tbl_proveedor.setModel(model);
            int filas =  tbl_proveedor.getRowCount();
            lbl_registros.setText("" + filas);
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    } 
     public int comprobarActualizacion(){
        if (!txt_nom_prove1.getText().trim().isEmpty()) {
            txt_nom_prove1.transferFocus();
            if (!txt_ruc_prove1.getText().trim().isEmpty()) {
                 txt_ruc_prove1.transferFocus();
                if (!txt_telf_prove1.getText().trim().isEmpty()) {
                    txt_telf_prove1.transferFocus();
                        if (!txt_dir_prove1.getText().trim().isEmpty()) {
                            txt_dir_prove1.transferFocus();
                                if (!txt_email_prove1.getText().trim().isEmpty()) {
                                    txt_email_prove1.transferFocus();
                                    if (!txt_nom_contacto1.getText().trim().isEmpty()) {
                                            txt_nom_contacto1.transferFocus();
                                                if (!txt_telf_contacto1.getText().trim().isEmpty()) {
                                                        txt_telf_contacto1.transferFocus();
                                                         return 1;
                                            } else {
                                                JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE TELEFONO DE CONTACTO");
                                                txt_telf_contacto1.requestFocus();
                                            }                           
                                       } else {
                                    JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE NOMBRE DE CONTACTO");
                                    txt_nom_contacto1.requestFocus();
                                    }           
                                } else {
                                    JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE EMAIL");
                                    txt_email_prove1.requestFocus();
                                }
                                
                        }else {
                            JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE DIRECCION");
                            txt_dir_prove1.requestFocus();                            
                        }
                    } else {
                        JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE TELEFONO");
                        txt_telf_prove1.requestFocus();                        
                    }
                
            } else {
                JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE RUC");
                txt_ruc_prove1.requestFocus();
                          }
        }else {
            JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE NOMBRE");
            txt_nom_prove1.requestFocus();
            
                 }
        return 0;
    }
     
    public void limpiar(){
        txt_nom_prove.setText("");
        txt_ruc_prove.setText("");
        txt_telf_prove.setText("");
        txt_dir_prove.setText("");
        txt_email_prove.setText("");
        txt_nomcontac_prove.setText("");
        txt_telfcontac_prove.setText("");
        txt_nom_prove.requestFocus();
    }
    
    public void limpiarJTextFields(javax.swing.JTextField ... camposALimpiar){
        for(javax.swing.JTextField jtf : camposALimpiar){
            jtf.setText("");
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
    
    
     public void modificar_proveedor(){
        
         
        try {
        
        int fila = tbl_proveedor.getSelectedRow();
        id = Integer.parseInt(tbl_proveedor.getValueAt(fila, 0).toString());
        nom = tbl_proveedor.getValueAt(fila, 1).toString();
        ruc = (String) tbl_proveedor.getValueAt(fila, 2);
        telf = tbl_proveedor.getValueAt(fila, 3).toString();
        dir = tbl_proveedor.getValueAt(fila, 4).toString();        
        email = tbl_proveedor.getValueAt(fila, 5).toString();
        nom_contac = tbl_proveedor.getValueAt(fila, 6).toString();        
        telf_contac = tbl_proveedor.getValueAt(fila, 7).toString();
        txt_ed_provee.setText(""+id);
        txt_nom_prove1.setText(nom);
        txt_ruc_prove1.setText(""+ruc);
        txt_telf_prove1.setText(""+telf);
        txt_dir_prove1.setText(dir);
        txt_email_prove1.setText(""+email);
        txt_nom_contacto1.setText(nom_contac);
        txt_telf_contacto1.setText(telf_contac);
       
        
        //lbl_prueba.setText("");*/
        } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e.getMessage());
        
        }
        
    }
     
    public int getId(String provee){
        int id_provee = 0;
        String sql = "SELECT id_provee FROM tproveedor WHERE tproveedor.nom_provee =  '"+provee+"'";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id_provee = rs.getInt("id_provee");
                                  
            } else {
            }
        } catch (Exception e) {
        }
     return id_provee;   
    }
    public void Registrar_proveedor(){
    String nom1 = txt_nom_prove.getText();
    String ruc1 = txt_ruc_prove.getText();
    String telf1 = txt_telf_prove.getText();
    String dir1 = txt_dir_prove.getText();
    String email1 = txt_email_prove.getText();
    String nom_contac = txt_nomcontac_prove.getText();
    String telf_contac = txt_telfcontac_prove.getText();
    
    
    Conexion c = new Conexion();
    Connection cn = c.conectar();
    String sql = "INSERT INTO tproveedor (nom_provee, ruc_provee, telf_provee,dir_provee, email_provee,nom_contac,telf_contac) VALUES ('"+nom1+"','"+ruc1+"','"+telf1+"','"+dir1+"','"+email1+"','"+nom_contac+"','"+telf_contac+"')";
        try {
            Statement st = cn.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
            //JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO CON EXITO");
                //0=si------1=no------2=cancelar
                limpiarTabla();
                cargarTabla();
               
       
                int opc = JOptionPane.showOptionDialog(btn_guardar, "PROVEEDOR REGISTRADO, ¿DESEA REGISTRAR MAS PROVEEDORES?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
                if (opc == 0) {
                    limpiar();

                } else if (opc == 1) {
                    frm_prov_nuevo.dispose();
                    btn_modifcar.setEnabled(false);
                    btn_eliminar.setEnabled(false);
                    txt_buscar.requestFocus();
                    bloquear();
                }
            }
            
            
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    
    
    }
    
    public void actualizarProveedorEnBD(){
    int codProveedor = Integer.parseInt(txt_ed_provee.getText());
    String nombre = txt_nom_prove1.getText();
    String ruc = txt_ruc_prove1.getText();
    String telefono = txt_telf_prove1.getText();
    String direccion = txt_dir_prove1.getText();
    String email = txt_email_prove1.getText();
    String nom_contac = txt_nom_contacto1.getText();
    String telf_contac = txt_telf_contacto1.getText();
    Conexion c = new Conexion();
    Connection cn = c.conectar();
    String sql = "UPDATE tproveedor set nom_provee='"+nombre+"',"
            + " ruc_provee='"+ruc+"', telf_provee='"+telefono+"', dir_provee='"+direccion+"', email_provee='"+email+"', nom_contac='"+nom_contac+"',telf_contac='"+telf_contac+"'  WHERE id_provee="+codProveedor;
        try {
            Statement st = cn.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
            //JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO CON EXITO");
                //0=si------1=no------2=cancelar
                limpiarTabla();
                cargarTabla();  
                limpiarJTextFields(txt_nom_prove1, txt_ruc_prove1,txt_telf_prove1,txt_dir_prove1,txt_email_prove1,txt_nom_contacto1,txt_telf_contacto1);
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        frm_prov_nuevo = new javax.swing.JDialog();
        jLabel29 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_nom_prove = new javax.swing.JTextField();
        txt_ruc_prove = new javax.swing.JTextField();
        txt_telf_prove = new javax.swing.JTextField();
        txt_dir_prove = new javax.swing.JTextField();
        txt_email_prove = new javax.swing.JTextField();
        txt_nomcontac_prove = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txt_telfcontac_prove = new javax.swing.JTextField();
        btn_guardar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        frm_prov_modi = new javax.swing.JDialog();
        jLabel30 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txt_nom_prove1 = new javax.swing.JTextField();
        txt_ruc_prove1 = new javax.swing.JTextField();
        txt_telf_prove1 = new javax.swing.JTextField();
        txt_dir_prove1 = new javax.swing.JTextField();
        txt_email_prove1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_ed_provee = new javax.swing.JTextField();
        txt_nom_contacto1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        txt_telf_contacto1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btn_guardar1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_nuevo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_proveedor = new javax.swing.JTable();
        txt_buscar = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        btn_eliminar = new javax.swing.JButton();
        btn_modifcar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lbl_registros = new javax.swing.JLabel();

        frm_prov_nuevo.setBounds(new java.awt.Rectangle(400, 210, 593, 490));
        frm_prov_nuevo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel29.setText("SALIR");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });
        frm_prov_nuevo.getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, -1, -1));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel5MouseDragged(evt);
            }
        });
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel5MousePressed(evt);
            }
        });
        frm_prov_nuevo.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 590, -1));

        jPanel4.setBackground(new java.awt.Color(0, 102, 255));
        frm_prov_nuevo.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 590, 20));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 204), 2, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453510435_provider.png"))); // NOI18N
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 70, 50));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("NUEVO PROVEEDOR");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 168, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)), " DATOS DEL PROVEEDOR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(204, 0, 0))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("NOMBRE");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("RUC");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 102));
        jLabel19.setText("TELF. (CEL)");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("DIRECCION");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("E-MAIL");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        txt_nom_prove.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nom_prove.setForeground(new java.awt.Color(0, 51, 204));
        txt_nom_prove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nom_prove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_nom_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nom_proveActionPerformed(evt);
            }
        });
        txt_nom_prove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nom_proveKeyTyped(evt);
            }
        });
        jPanel2.add(txt_nom_prove, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 200, -1));

        txt_ruc_prove.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_ruc_prove.setForeground(new java.awt.Color(0, 51, 204));
        txt_ruc_prove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ruc_prove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_ruc_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ruc_proveActionPerformed(evt);
            }
        });
        txt_ruc_prove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ruc_proveKeyTyped(evt);
            }
        });
        jPanel2.add(txt_ruc_prove, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 200, -1));

        txt_telf_prove.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_telf_prove.setForeground(new java.awt.Color(0, 51, 204));
        txt_telf_prove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telf_prove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_telf_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telf_proveActionPerformed(evt);
            }
        });
        txt_telf_prove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telf_proveKeyTyped(evt);
            }
        });
        jPanel2.add(txt_telf_prove, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 130, 200, -1));

        txt_dir_prove.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dir_prove.setForeground(new java.awt.Color(0, 51, 204));
        txt_dir_prove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dir_prove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_dir_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dir_proveActionPerformed(evt);
            }
        });
        jPanel2.add(txt_dir_prove, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 200, -1));

        txt_email_prove.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_email_prove.setForeground(new java.awt.Color(0, 51, 204));
        txt_email_prove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_email_prove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_email_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_email_proveActionPerformed(evt);
            }
        });
        jPanel2.add(txt_email_prove, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 200, -1));

        txt_nomcontac_prove.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nomcontac_prove.setForeground(new java.awt.Color(0, 51, 204));
        txt_nomcontac_prove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nomcontac_prove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_nomcontac_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nomcontac_proveActionPerformed(evt);
            }
        });
        txt_nomcontac_prove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nomcontac_proveKeyTyped(evt);
            }
        });
        jPanel2.add(txt_nomcontac_prove, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, 200, -1));

        jLabel33.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 0, 102));
        jLabel33.setText("TELF. CONTACTO");
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jLabel38.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 102));
        jLabel38.setText("NOM. CONTACTO");
        jPanel2.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        txt_telfcontac_prove.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_telfcontac_prove.setForeground(new java.awt.Color(0, 51, 204));
        txt_telfcontac_prove.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telfcontac_prove.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_telfcontac_prove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telfcontac_proveActionPerformed(evt);
            }
        });
        txt_telfcontac_prove.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telfcontac_proveKeyTyped(evt);
            }
        });
        jPanel2.add(txt_telfcontac_prove, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 290, 200, -1));

        jPanel5.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 400, 340));

        btn_guardar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532190_3floppy_unmount.png"))); // NOI18N
        btn_guardar.setText("GUARDAR");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jPanel5.add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 140, -1));

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532351_button_cancel.png"))); // NOI18N
        jButton3.setText("CANCELAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, 140, -1));

        frm_prov_nuevo.getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 590, 410));

        frm_prov_modi.setBackground(new java.awt.Color(255, 255, 255));
        frm_prov_modi.setBounds(new java.awt.Rectangle(400, 200, 670, 520));
        frm_prov_modi.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel30.setText("SALIR");
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });
        frm_prov_modi.getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 70, 30));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel6MouseDragged(evt);
            }
        });
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel6MousePressed(evt);
            }
        });
        frm_prov_modi.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, -1));

        jPanel10.setBackground(new java.awt.Color(0, 102, 255));
        frm_prov_modi.getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 670, 20));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453510435_provider.png"))); // NOI18N
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 0, 60, 60));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true), "DATOS DE PROVEEDOR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(204, 0, 51))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("NOMBRE");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 79, -1, -1));

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("RUC");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 119, -1, -1));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("TELF. (CEL)");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 159, -1, -1));

        jLabel24.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 102));
        jLabel24.setText("DIRECCION");
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 199, -1, -1));

        jLabel25.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 0, 102));
        jLabel25.setText("E-MAIL");
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 239, -1, -1));

        txt_nom_prove1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nom_prove1.setForeground(new java.awt.Color(0, 51, 255));
        txt_nom_prove1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nom_prove1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_nom_prove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nom_prove1ActionPerformed(evt);
            }
        });
        txt_nom_prove1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nom_prove1KeyTyped(evt);
            }
        });
        jPanel3.add(txt_nom_prove1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 242, -1));

        txt_ruc_prove1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_ruc_prove1.setForeground(new java.awt.Color(0, 51, 255));
        txt_ruc_prove1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_ruc_prove1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_ruc_prove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ruc_prove1ActionPerformed(evt);
            }
        });
        txt_ruc_prove1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_ruc_prove1KeyTyped(evt);
            }
        });
        jPanel3.add(txt_ruc_prove1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 120, 242, -1));

        txt_telf_prove1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_telf_prove1.setForeground(new java.awt.Color(0, 51, 255));
        txt_telf_prove1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telf_prove1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_telf_prove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telf_prove1ActionPerformed(evt);
            }
        });
        txt_telf_prove1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telf_prove1KeyTyped(evt);
            }
        });
        jPanel3.add(txt_telf_prove1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 242, -1));

        txt_dir_prove1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dir_prove1.setForeground(new java.awt.Color(0, 51, 255));
        txt_dir_prove1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dir_prove1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_dir_prove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dir_prove1ActionPerformed(evt);
            }
        });
        jPanel3.add(txt_dir_prove1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 242, -1));

        txt_email_prove1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_email_prove1.setForeground(new java.awt.Color(0, 51, 255));
        txt_email_prove1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_email_prove1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_email_prove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_email_prove1ActionPerformed(evt);
            }
        });
        jPanel3.add(txt_email_prove1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 242, -1));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("ID");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 32, -1, -1));

        txt_ed_provee.setEditable(false);
        txt_ed_provee.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_ed_provee.setEnabled(false);
        txt_ed_provee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ed_proveeActionPerformed(evt);
            }
        });
        jPanel3.add(txt_ed_provee, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 43, -1));

        txt_nom_contacto1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nom_contacto1.setForeground(new java.awt.Color(0, 51, 255));
        txt_nom_contacto1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nom_contacto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_nom_contacto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nom_contacto1ActionPerformed(evt);
            }
        });
        txt_nom_contacto1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nom_contacto1KeyTyped(evt);
            }
        });
        jPanel3.add(txt_nom_contacto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 242, -1));

        jLabel39.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(0, 0, 102));
        jLabel39.setText("NOMBRE CONTACTO ");
        jPanel3.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        jLabel40.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 102));
        jLabel40.setText("TELF. CONTACTO");
        jPanel3.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        txt_telf_contacto1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_telf_contacto1.setForeground(new java.awt.Color(0, 51, 255));
        txt_telf_contacto1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telf_contacto1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_telf_contacto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telf_contacto1ActionPerformed(evt);
            }
        });
        txt_telf_contacto1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telf_contacto1KeyTyped(evt);
            }
        });
        jPanel3.add(txt_telf_contacto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 242, -1));

        jPanel9.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 460, 360));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("MODIFICAR PROVEEDOR");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 180, -1));

        btn_guardar1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_guardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453410879_gtk-refresh.png"))); // NOI18N
        btn_guardar1.setText("ACTUALIZAR");
        btn_guardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardar1ActionPerformed(evt);
            }
        });
        jPanel9.add(btn_guardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 150, -1));

        jButton4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532351_button_cancel.png"))); // NOI18N
        jButton4.setText("CANCELAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 150, -1));

        frm_prov_modi.getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 670, 440));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel28.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel28.setText("SALIR");
        jLabel28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 10, 80, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
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
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1230, 60));

        btn_nuevo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449530684_gnome-app-install.png"))); // NOI18N
        btn_nuevo.setText("NUEVO");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btn_nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 130, 50));

        tbl_proveedor.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbl_proveedor.setForeground(new java.awt.Color(0, 0, 255));
        tbl_proveedor.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_proveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_proveedorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_proveedor);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 1180, 290));

        txt_buscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_buscar.setForeground(new java.awt.Color(0, 51, 255));
        txt_buscar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 51), 1, true));
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
        getContentPane().add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 150, 460, 30));

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_pie.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("jLabel10");
        jPanel1.add(lbl_pie, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 1230, 30));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel6.setForeground(new java.awt.Color(0, 102, 255));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("REGISTRO DE PROVEEDOR");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(309, 19, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453510435_provider.png"))); // NOI18N
        jPanel6.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(559, 7, -1, -1));

        jLabel34.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        jLabel34.setText("BUSCAR");
        jPanel6.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 80, -1, 50));

        btn_eliminar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449494490_f-cross_256.png"))); // NOI18N
        btn_eliminar.setText("ELIMINAR");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 160, 50));

        btn_modifcar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_modifcar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449494334_pencil.png"))); // NOI18N
        btn_modifcar.setText("MODIFICAR");
        btn_modifcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifcarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_modifcar, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, -1, 50));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setText("REGISTROS");
        jPanel6.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 20, -1, 30));

        lbl_registros.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        lbl_registros.setForeground(new java.awt.Color(0, 0, 204));
        jPanel6.add(lbl_registros, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 20, 80, 30));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1230, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        frm_prov_nuevo.setVisible(true);
        limpiar();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_modifcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifcarActionPerformed
        frm_prov_modi.setVisible(true);
        txt_nom_prove1.requestFocus();
        modificar_proveedor();
    }//GEN-LAST:event_btn_modifcarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed

        int row = tbl_proveedor.getSelectedRow();
        int cod = Integer.parseInt(tbl_proveedor.getValueAt(row,0).toString());
        Conexion c = new Conexion();
        Connection cn = c.conectar();
        String sql = "DELETE FROM `tproveedor` WHERE `id_provee` = "+cod+"";

        int opc = JOptionPane.showOptionDialog(btn_eliminar, "¿ESTA SEGURO QUE DESEA ELIMINAR ESTE REGISTRO?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
        if (opc == 0) {
            try {
                Statement st = cn.createStatement();
                int rs = st.executeUpdate(sql);

                if (rs > 0) {
                    limpiarTabla();
                    cargarTabla();
                    bloquear();
                    txt_buscar.requestFocus();

                } else {
                    
                }

            } catch (SQLException | HeadlessException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO");
        }
            else
             txt_buscar.requestFocus();
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel28MouseClicked

    private void txt_nom_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nom_proveActionPerformed
        txt_nom_prove.transferFocus();
    }//GEN-LAST:event_txt_nom_proveActionPerformed

    private void txt_nom_proveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nom_proveKeyTyped
        int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE SOLO LETRAS");
           // txt_nombres.requestFocus();
            //txt_nombres.requestFocus();
        }else{

        }
    }//GEN-LAST:event_txt_nom_proveKeyTyped

    private void txt_ruc_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ruc_proveActionPerformed
        txt_ruc_prove.transferFocus();
    }//GEN-LAST:event_txt_ruc_proveActionPerformed

    private void txt_ruc_proveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruc_proveKeyTyped
       int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_ruc_prove.requestFocus();

        } else {
            if (txt_ruc_prove.getText().trim().length()==11) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_ruc_proveKeyTyped

    private void txt_telf_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telf_proveActionPerformed
        txt_telf_prove.transferFocus();
    }//GEN-LAST:event_txt_telf_proveActionPerformed

    private void txt_telf_proveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telf_proveKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_telf_prove.requestFocus();

        } else {
            
            if (txt_telf_prove.getText().trim().length()==10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_telf_proveKeyTyped

    private void txt_dir_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dir_proveActionPerformed
        txt_dir_prove.transferFocus();
    }//GEN-LAST:event_txt_dir_proveActionPerformed

    private void txt_email_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_email_proveActionPerformed
        txt_email_prove.transferFocus();
    }//GEN-LAST:event_txt_email_proveActionPerformed

         public int verificarExisteProveedor(String ruc){
         int opc = 0;
         String sql = "SELECT `ruc_provee` FROM `tproveedor` WHERE `ruc_provee` = "+ruc;
         try {
             Statement st = cc.createStatement();
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
    
    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        if (comprobar()!=0) {
            String ruc = txt_ruc_prove.getText();
            if (verificarExisteProveedor(ruc)==0) {
                Registrar_proveedor();
                limpiar();
            } else {
                JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "EXISTE PROVEEDOR CON EL RUC INGRESADO");
                limpiar();
            }
            
        }else{
            //comprobar();
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        frm_prov_nuevo.dispose();
        txt_buscar.requestFocus();
        btn_modifcar.setEnabled(false);
        btn_eliminar.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        posx= evt.getX();
        posy= evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x-posx, point.y-posy);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        frm_prov_nuevo.dispose();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MousePressed
        posx1 = evt.getX();
        posy1 = evt.getY();
    }//GEN-LAST:event_jLabel5MousePressed

    private void jLabel5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        frm_prov_nuevo.setLocation(point.x-posx1, point.y-posy1);
    }//GEN-LAST:event_jLabel5MouseDragged

    private void tbl_proveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_proveedorMouseClicked
       int fila = tbl_proveedor.getSelectedRow();
        if (fila != -1) {
            Desbloquear();

        }
    }//GEN-LAST:event_tbl_proveedorMouseClicked

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        buscarClientes();
    }//GEN-LAST:event_txt_buscarKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        frm_prov_modi.dispose();
        txt_buscar.setText("");
        txt_buscar.requestFocus();
        bloquear();
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btn_guardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardar1ActionPerformed
        if (comprobarActualizacion()!=0) {
            frm_prov_modi.dispose();
            actualizarProveedorEnBD();
            JOptionPane.showMessageDialog(getRootPane(),"REGISTRO ACTUALIZADO");
            btn_modifcar.setEnabled(false);
            btn_eliminar.setEnabled(false);
            txt_buscar.setText("");
            txt_buscar.requestFocus();
            bloquear();
            
            
        }else{
            //comprobar();
        }
    }//GEN-LAST:event_btn_guardar1ActionPerformed

    private void txt_ed_proveeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ed_proveeActionPerformed

    }//GEN-LAST:event_txt_ed_proveeActionPerformed

    private void txt_email_prove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_email_prove1ActionPerformed
       txt_email_prove1.transferFocus();
    }//GEN-LAST:event_txt_email_prove1ActionPerformed

    private void txt_dir_prove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dir_prove1ActionPerformed
        txt_dir_prove1.transferFocus();
    }//GEN-LAST:event_txt_dir_prove1ActionPerformed

    private void txt_telf_prove1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telf_prove1KeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_telf_prove1.requestFocus();

        } else {
            if (txt_telf_prove1.getText().trim().length()==10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_telf_prove1KeyTyped

    private void txt_telf_prove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telf_prove1ActionPerformed
       txt_telf_prove1.transferFocus();
    }//GEN-LAST:event_txt_telf_prove1ActionPerformed

    private void txt_ruc_prove1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ruc_prove1KeyTyped
       int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_ruc_prove1.requestFocus();

        } else {
            if (txt_ruc_prove1.getText().trim().length()==11) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_ruc_prove1KeyTyped

    private void txt_ruc_prove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ruc_prove1ActionPerformed
       txt_ruc_prove1.transferFocus();
    }//GEN-LAST:event_txt_ruc_prove1ActionPerformed

    private void txt_nom_prove1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nom_prove1KeyTyped
       int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE SOLO LETRAS");
            txt_nom_prove1.requestFocus();
            //txt_nombres.requestFocus();
        }else{

        }
    }//GEN-LAST:event_txt_nom_prove1KeyTyped

    private void txt_nom_prove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nom_prove1ActionPerformed
        txt_nom_prove1.transferFocus();
    }//GEN-LAST:event_txt_nom_prove1ActionPerformed

    private void jLabel6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MousePressed
        posx2 = evt.getX();
        posy2 = evt.getY();
    }//GEN-LAST:event_jLabel6MousePressed

    private void jLabel6MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        frm_prov_modi.setLocation(point.x-posx2, point.y-posy2);
    }//GEN-LAST:event_jLabel6MouseDragged

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        frm_prov_modi.dispose();
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        bloquear();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void txt_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_buscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_buscarActionPerformed

    private void txt_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_buscarMouseClicked
        
        btn_modifcar.setEnabled(false);
        btn_eliminar.setEnabled(false);
    }//GEN-LAST:event_txt_buscarMouseClicked

    private void txt_nomcontac_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nomcontac_proveActionPerformed
       txt_nomcontac_prove.transferFocus();
    }//GEN-LAST:event_txt_nomcontac_proveActionPerformed

    private void txt_telfcontac_proveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telfcontac_proveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telfcontac_proveActionPerformed

    private void txt_nomcontac_proveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nomcontac_proveKeyTyped
         int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE SOLO LETRAS");
            txt_nomcontac_prove.requestFocus();
            //txt_nombres.requestFocus();
        }else{

        }
    }//GEN-LAST:event_txt_nomcontac_proveKeyTyped

    private void txt_telfcontac_proveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telfcontac_proveKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_nuevo.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_telfcontac_prove.requestFocus();

        } else {
            if (txt_telfcontac_prove.getText().trim().length()==10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_telfcontac_proveKeyTyped

    private void txt_nom_contacto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nom_contacto1ActionPerformed
       txt_nom_contacto1.transferFocus();
    }//GEN-LAST:event_txt_nom_contacto1ActionPerformed

    private void txt_telf_contacto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telf_contacto1ActionPerformed
       
    }//GEN-LAST:event_txt_telf_contacto1ActionPerformed

    private void txt_nom_contacto1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nom_contacto1KeyTyped
          int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE SOLO LETRAS");
            txt_nom_contacto1.requestFocus();
            //txt_nombres.requestFocus();
        }else{

        }
    }//GEN-LAST:event_txt_nom_contacto1KeyTyped

    private void txt_telf_contacto1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telf_contacto1KeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_prov_modi.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_telfcontac_prove.requestFocus();

        } else {
            if (txt_telf_contacto1.getText().trim().length()==10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_telf_contacto1KeyTyped

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
            java.util.logging.Logger.getLogger(Reg_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reg_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reg_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reg_proveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reg_proveedor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_eliminar;
    public javax.swing.JButton btn_guardar;
    public javax.swing.JButton btn_guardar1;
    private javax.swing.JButton btn_modifcar;
    private javax.swing.JButton btn_nuevo;
    public javax.swing.JDialog frm_prov_modi;
    public javax.swing.JDialog frm_prov_nuevo;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_registros;
    private javax.swing.JTable tbl_proveedor;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_dir_prove;
    private javax.swing.JTextField txt_dir_prove1;
    private javax.swing.JTextField txt_ed_provee;
    private javax.swing.JTextField txt_email_prove;
    private javax.swing.JTextField txt_email_prove1;
    private javax.swing.JTextField txt_nom_contacto1;
    private javax.swing.JTextField txt_nom_prove;
    private javax.swing.JTextField txt_nom_prove1;
    private javax.swing.JTextField txt_nomcontac_prove;
    private javax.swing.JTextField txt_ruc_prove;
    private javax.swing.JTextField txt_ruc_prove1;
    private javax.swing.JTextField txt_telf_contacto1;
    private javax.swing.JTextField txt_telf_prove;
    private javax.swing.JTextField txt_telf_prove1;
    private javax.swing.JTextField txt_telfcontac_prove;
    // End of variables declaration//GEN-END:variables

    public Object frm_prov_nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
