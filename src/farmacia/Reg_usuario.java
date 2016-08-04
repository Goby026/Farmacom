/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package farmacia;

import Control.Conexion;
import Vista.Farma_inf;
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
public final class Reg_usuario extends javax.swing.JFrame {
    
    Connection con = new Conexion().conectar();
    int posx, posy;
    int posx1, posy1;
    int posx2, posy2;
    int posx3, posy3;
    DefaultTableModel model;
    String nom,ape,dir,contra,fecha,telf,dni,perfil;
    int id;
    
    public Reg_usuario() {
        // setUndecorated(true);
         initComponents();
         frm_nuevo.setUndecorated(true);
         frm_modificar.setUndecorated(true);
         frm_fecha.setUndecorated(true);
         txt_buscar.requestFocus();
         lbl_pie.setText(new Farma_inf().pie());
         setLocation(180,80);
         cabecera();
         cargarTabla();
         contar_usuario();
         bloquear();
    }
 public void contar_usuario(){
    int c = tbl_usuario.getRowCount();
    lbl_registros.setText(""+c);
    }
 public void limpiarTabla() {
        for (int i = 0; i < tbl_usuario.getRowCount(); i++) {
            model.removeRow(i);
            i -= 1;
        }
    }
 
    
    public void cabecera(){
    
       String[] cabecera = {"ID","NOMBRE","APELLIDOS","DNI","TELF","DIRECCION","FECHA INGRESO","PERFIL","CONTRASEÑA"};
       model = new DefaultTableModel(null,cabecera);
       tbl_usuario.setModel(model);
       tbl_usuario.getColumnModel().getColumn(0).setPreferredWidth(1);
       tbl_usuario.getColumnModel().getColumn(1).setPreferredWidth(20);
       tbl_usuario.getColumnModel().getColumn(2).setPreferredWidth(20);
       tbl_usuario.getColumnModel().getColumn(3).setPreferredWidth(10);
       tbl_usuario.getColumnModel().getColumn(4).setPreferredWidth(10);
       tbl_usuario.getColumnModel().getColumn(5).setPreferredWidth(160);
       tbl_usuario.getColumnModel().getColumn(6).setPreferredWidth(20);
       tbl_usuario.getColumnModel().getColumn(7).setPreferredWidth(40);
       tbl_usuario.getColumnModel().getColumn(8).setPreferredWidth(15);
    
    
    }
    
    public void cargarTabla(){
        Conexion con = new Conexion();
        Connection cc = con.conectar();
        String[] datos= new String[9];
        String sql = "SELECT id_usu,nom_usu,apell_usu,dni_usu,telf_usu,direc_usu,fec_ingreso_usu,descrip_perfil,pass_usu FROM tusuario INNER JOIN tperfil on tusuario.id_perfil = tperfil.id_perfil";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_usu");
                datos[1] = rs.getString("nom_usu");
                datos[2] = rs.getString("apell_usu");
                datos[3] = rs.getString("dni_usu");
                datos[4] = rs.getString("telf_usu");
                datos[5] = rs.getString("direc_usu");
                datos[6] = rs.getString("fec_ingreso_usu");
                datos[7] = rs.getString("descrip_perfil");
                datos[8] = rs.getString("pass_usu");
                model.addRow(datos);
            }
            tbl_usuario.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    public int comprobar(){
        
        if (!txt_nombres1.getText().trim().isEmpty()) {
            txt_nombres1.transferFocus();
            if (!txt_apellidos1.getText().trim().isEmpty()) {
                 txt_apellidos1.transferFocus();
                if (!txt_dni1.getText().trim().isEmpty()) {
                    txt_dni1.transferFocus();
                    if (!txt_telf1.getText().trim().isEmpty()) {
                        txt_telf1.transferFocus();
                        if (!txt_dir1.getText().trim().isEmpty()) {
                            txt_dir1.transferFocus();
                            if (txt_fecha_ingreso1.getDate() != null) {
                                txt_fecha_ingreso1.transferFocus();
                                if (!txt_contraseña1.getText().trim().isEmpty()) {
                                    return 1;
                                } else {
                                    JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE CONTRASEÑA");
                                    txt_contraseña1.requestFocus();                                    
                                }
                            } else {
                                JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE FECHA DE INGRESO");
                                txt_fecha_ingreso1.requestFocus();                                
                            }
                        } else {
                            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE DIRECCION");
                            txt_dir1.requestFocus();                            
                        }
                    } else {
                        JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE TELEFONO");
                        txt_telf1.requestFocus();                        
                    }
                } else {
                    JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE DNI");
                    txt_dni1.requestFocus();
                    
                }
            } else {
                JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE APELLIDOS");
                txt_apellidos1.requestFocus();
                          }
        } else {
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE NOMBRE");
            txt_nombres1.requestFocus();
            
        }
        return 0;
    }
    
    
    public int comprobarActualizacion(){
        
        if (!txt_nombres.getText().trim().isEmpty()) {
            txt_nombres.transferFocus();
            if (!txt_apellidos.getText().trim().isEmpty()) {
                 txt_apellidos.transferFocus();
                if (!txt_telf.getText().trim().isEmpty()) {
                    txt_telf.transferFocus();
                    if (!txt_dni.getText().trim().isEmpty()) {
                        txt_dni.transferFocus();
                        if (!txt_dir.getText().trim().isEmpty()) {
                            txt_dir.transferFocus();
                                    return 1;
                        
                        } else {
                                    JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE DIRECCION");
                                    txt_dir.requestFocus();                                    
                                }                             
                        } else {
                            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE DNI");
                            txt_dni.requestFocus();                            
                        }
                    } else {
                        JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE TELEFONO");
                        txt_telf.requestFocus();                        
                    }
               
            } else {
                JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE APELLIDOS");
                txt_apellidos.requestFocus();
                          }
        } else {
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE NOMBRE");
            txt_nombres.requestFocus();
            
        }
        return 0;
    }
    public void limpiar(){
        txt_nombres1.setText("");
        txt_apellidos1.setText("");
        txt_dni1.setText("");
        txt_telf1.setText("");
        txt_dir1.setText("");
        txt_fecha_ingreso1.setDateFormatString("");
        txt_contraseña1.setText("");
        txt_nombres1.requestFocus();
    }
    
    public void Reg_usuario(){
    String nom1 = txt_nombres1.getText();
    String ape1 = txt_apellidos1.getText();
    String dni1 = txt_dni1.getText();
    String telf1= txt_telf1.getText();
    String dir1 = txt_dir1.getText();
    SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");  
    Date dia = txt_fecha_ingreso1.getDate();
    String fecha1 = formateador.format(dia);
    int indice = cbx_perfil1.getSelectedIndex()+1;
    String pss = txt_contraseña1.getText();
    
    Conexion c = new Conexion();
    Connection cn = c.conectar();
    String sql = "INSERT INTO `tusuario`(`nom_usu`, `apell_usu`, `pass_usu`, `dni_usu`, `telf_usu`, `direc_usu`, `fec_ingreso_usu`, `id_perfil`) VALUES ('"+nom1+"','"+ape1+"','"+pss+"',"+dni1+",'"+telf1+"','"+dir1+"','"+fecha1+"',"+indice+")";
        try {
            Statement st = cn.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {
            //JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO CON EXITO");
                //0=si------1=no------2=cancelar
                limpiarTabla();
                cargarTabla();
                contar_usuario();
       
                int opc = JOptionPane.showOptionDialog(btn_guardar, "USUARIO REGISTRADO, ¿DESEA REGISTRAR MAS USUARIOS?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
                if (opc == 0) {
                    limpiar();

                } else if (opc == 1) {
                    frm_nuevo.dispose();
                    txt_buscar.requestFocus();
                    bloquear();
                }
            }
            
            
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
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
    public void buscarUsuario() {
        Conexion con = new Conexion();
        Connection cc = con.conectar();
        limpiarTabla();
        String pro = txt_buscar.getText();
        String datos[] = new String[9];
        String sql = "SELECT `id_usu`, `nom_usu`, `apell_usu`, `pass_usu`, `dni_usu`, `telf_usu`, `direc_usu`, `fec_ingreso_usu`, `descrip_perfil` FROM tusuario INNER JOIN tperfil on tusuario.id_perfil = tperfil.id_perfil WHERE  nom_usu LIKE  '" + pro + "%' OR nom_usu LIKE '%" + pro + "' OR dni_usu LIKE '" + pro + "%' OR dni_usu LIKE '%" + pro + "'";
        try{
             Statement st = cc.createStatement();
             ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("id_usu");
                datos[1] = rs.getString("nom_usu");
                datos[2] = rs.getString("apell_usu");
                datos[3] = rs.getString("dni_usu");
                datos[4] = rs.getString("telf_usu");
                datos[5] = rs.getString("direc_usu");
                datos[6] = rs.getString("fec_ingreso_usu");
                datos[7] = rs.getString("descrip_perfil");
                datos[8] = rs.getString("pass_usu");
                model.addRow(datos);
            }
            tbl_usuario.setModel(model);
            int filas =  tbl_usuario.getRowCount();
            lbl_registros.setText("" + filas);
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    
    }
    
    public int getPerfil(String perfil){
        String sql = "SELECT `id_perfil` FROM `tperfil` WHERE `descrip_perfil` = '"+perfil+"'";
        int idperfil=0;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                idperfil = rs.getInt("id_perfil");
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return idperfil;
    }
    
    
    public void actualizarUsuarioEnBD(){
        int idUsuario = Integer.parseInt(txt_id.getText());
        String nombres = txt_nombres.getText();
        String apellidos = txt_apellidos.getText();
        String dni = txt_dni.getText();
        String telefono = txt_telf.getText();
        String direccion = txt_dir.getText();
        String formato = jdc_fecha.getDateFormatString();
        Date date = jdc_fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        String fecha = sdf.format(date);
        String perfil = cbx_perfil.getSelectedItem().toString();
        int idperfil = getPerfil(perfil);
        String pass = txt_contraseña.getText();
        String sql = "UPDATE `tusuario` SET `nom_usu`='"+nombres+"',`apell_usu`='"+apellidos+"',`pass_usu`='"+pass+"',`dni_usu`='"+dni+"',`telf_usu`='"+telefono+"',`direc_usu`='"+direccion+"',`fec_ingreso_usu`='"+fecha+"',`id_perfil`="+idperfil+" WHERE id_usu=" + idUsuario;
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
            if (rs > 0) {

                limpiarTabla();
                cargarTabla();
                contar_usuario();
                JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "REGISTRO ACTUALIZADO");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "Indique una fecha");
        }
    }
    
    
    public void modificar_usuario(){
        try {
            int fila = tbl_usuario.getSelectedRow();
            id = Integer.parseInt(tbl_usuario.getValueAt(fila, 0).toString());
            nom = tbl_usuario.getValueAt(fila, 1).toString();
            ape = tbl_usuario.getValueAt(fila, 2).toString();
            dni = tbl_usuario.getValueAt(fila, 3).toString();
            telf = tbl_usuario.getValueAt(fila, 4).toString();
            dir = tbl_usuario.getValueAt(fila, 5).toString();
            String fecha = tbl_usuario.getValueAt(fila, 6).toString();
            perfil = tbl_usuario.getValueAt(fila, 7).toString();
            contra = tbl_usuario.getValueAt(fila, 8).toString();
            txt_perfil.setText(perfil);
            txt_id.setText("" + id);
            txt_nombres.setText(nom);
            txt_apellidos.setText(ape);
            txt_dni.setText("" + dni);
            txt_telf.setText("" + telf);
            txt_dir.setText(dir);
            txt_contraseña.setText(contra);
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(fecha);
            jdc_fecha.setDate(date);   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

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
        jLabel30 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_nombres = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        txt_dni = new javax.swing.JTextField();
        txt_telf = new javax.swing.JTextField();
        txt_dir = new javax.swing.JTextField();
        txt_contraseña = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cbx_perfil = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        txt_perfil = new javax.swing.JTextField();
        jdc_fecha = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btn_actualizar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        frm_nuevo = new javax.swing.JDialog();
        jLabel29 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txt_nombres1 = new javax.swing.JTextField();
        txt_apellidos1 = new javax.swing.JTextField();
        txt_dni1 = new javax.swing.JTextField();
        txt_telf1 = new javax.swing.JTextField();
        txt_dir1 = new javax.swing.JTextField();
        txt_contraseña1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        cbx_perfil1 = new javax.swing.JComboBox();
        txt_fecha_ingreso1 = new com.toedter.calendar.JDateChooser();
        btn_guardar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        frm_fecha = new javax.swing.JDialog();
        jLabel32 = new javax.swing.JLabel();
        cal_usu = new com.toedter.calendar.JCalendar();
        jLabel31 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_usuario = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btn_eliminar = new javax.swing.JButton();
        btn_modifcar = new javax.swing.JButton();
        btn_nuevo = new javax.swing.JButton();
        lbl_registros = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txt_buscar = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();

        frm_modificar.setBounds(new java.awt.Rectangle(450, 10, 660, 560));
        frm_modificar.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        frm_modificar.getContentPane().add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 70, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 1, true), "DATOS DEL USUARIO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(153, 0, 0))); // NOI18N
        jPanel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("NOMBRES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 70, -1, -1));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("APELLIDOS");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 110, 80, -1));

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("DNI");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 40, -1));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("TELF. (CEL)");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 189, -1, -1));

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("DIRECCION");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 234, -1, -1));

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("CONTRASEÑA");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 365, -1, -1));

        txt_nombres.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nombres.setForeground(new java.awt.Color(0, 51, 255));
        txt_nombres.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombres.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_nombres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombresActionPerformed(evt);
            }
        });
        txt_nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombresKeyTyped(evt);
            }
        });
        jPanel1.add(txt_nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 281, -1));

        txt_apellidos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_apellidos.setForeground(new java.awt.Color(0, 51, 255));
        txt_apellidos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_apellidos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_apellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_apellidosActionPerformed(evt);
            }
        });
        txt_apellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_apellidosKeyTyped(evt);
            }
        });
        jPanel1.add(txt_apellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 281, -1));

        txt_dni.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dni.setForeground(new java.awt.Color(0, 51, 255));
        txt_dni.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_dni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dniActionPerformed(evt);
            }
        });
        txt_dni.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dniKeyTyped(evt);
            }
        });
        jPanel1.add(txt_dni, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 281, -1));

        txt_telf.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_telf.setForeground(new java.awt.Color(0, 51, 255));
        txt_telf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_telf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telfActionPerformed(evt);
            }
        });
        jPanel1.add(txt_telf, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 281, -1));

        txt_dir.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dir.setForeground(new java.awt.Color(0, 51, 255));
        txt_dir.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dir.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_dir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dirActionPerformed(evt);
            }
        });
        jPanel1.add(txt_dir, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 281, -1));

        txt_contraseña.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_contraseña.setForeground(new java.awt.Color(0, 51, 255));
        txt_contraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_contraseña.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        jPanel1.add(txt_contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 281, -1));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("FECHA DE INGRESO");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 272, -1, -1));

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("PERFIL");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 324, -1, -1));

        cbx_perfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ADMINISTRADOR", "TRABAJADOR" }));
        cbx_perfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_perfilActionPerformed(evt);
            }
        });
        jPanel1.add(cbx_perfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 132, -1));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("ID");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 32, -1, -1));

        txt_id.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_id.setEnabled(false);
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });
        jPanel1.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 40, -1));

        txt_perfil.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_perfil.setForeground(new java.awt.Color(0, 51, 255));
        txt_perfil.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_perfil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 255)));
        txt_perfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_perfilActionPerformed(evt);
            }
        });
        jPanel1.add(txt_perfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 138, -1));

        jdc_fecha.setForeground(new java.awt.Color(0, 51, 204));
        jdc_fecha.setDateFormatString("yyyy/MM/dd");
        jdc_fecha.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel1.add(jdc_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, 140, -1));

        frm_modificar.getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 470, 400));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449533434_Edit-Male-User.png"))); // NOI18N
        frm_modificar.getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 59, 72));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("MODIFICAR USUARIO");
        frm_modificar.getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel27.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel27MouseDragged(evt);
            }
        });
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel27MousePressed(evt);
            }
        });
        frm_modificar.getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, -1));

        jPanel8.setBackground(new java.awt.Color(0, 102, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 660, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        frm_modificar.getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 660, 20));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 2, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_actualizar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453410879_gtk-refresh.png"))); // NOI18N
        btn_actualizar.setText("ACTUALIZAR");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jPanel5.add(btn_actualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 150, -1));

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532351_button_cancel.png"))); // NOI18N
        jButton2.setText("CANCELAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, 150, -1));

        frm_modificar.getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 660, 480));

        frm_nuevo.setBounds(new java.awt.Rectangle(440, 10, 610, 560));
        frm_nuevo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        frm_nuevo.getContentPane().add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 70, -1));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel25.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel25MouseDragged(evt);
            }
        });
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel25MousePressed(evt);
            }
        });
        frm_nuevo.getContentPane().add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, -1));

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

        frm_nuevo.getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 610, 20));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)), "DATOS DEL USUARIO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(204, 0, 0))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("NOMBRES");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 52, -1, -1));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("APELLIDOS");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 97, -1, -1));

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setText("DNI");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 143, -1, -1));

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 102));
        jLabel19.setText("TELF. (CEL)");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 184, -1, -1));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setText("DIRECCION");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 222, -1, -1));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("CONTRASEÑA");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 368, -1, -1));

        txt_nombres1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_nombres1.setForeground(new java.awt.Color(0, 51, 255));
        txt_nombres1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombres1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_nombres1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombres1ActionPerformed(evt);
            }
        });
        txt_nombres1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombres1KeyTyped(evt);
            }
        });
        jPanel2.add(txt_nombres1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 50, 242, 20));

        txt_apellidos1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_apellidos1.setForeground(new java.awt.Color(0, 51, 255));
        txt_apellidos1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_apellidos1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_apellidos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_apellidos1ActionPerformed(evt);
            }
        });
        txt_apellidos1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_apellidos1KeyTyped(evt);
            }
        });
        jPanel2.add(txt_apellidos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 95, 242, -1));

        txt_dni1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dni1.setForeground(new java.awt.Color(0, 51, 255));
        txt_dni1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dni1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_dni1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dni1ActionPerformed(evt);
            }
        });
        txt_dni1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dni1KeyTyped(evt);
            }
        });
        jPanel2.add(txt_dni1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 141, 242, -1));

        txt_telf1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_telf1.setForeground(new java.awt.Color(0, 51, 255));
        txt_telf1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telf1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_telf1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telf1ActionPerformed(evt);
            }
        });
        txt_telf1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telf1KeyTyped(evt);
            }
        });
        jPanel2.add(txt_telf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 182, 242, -1));

        txt_dir1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_dir1.setForeground(new java.awt.Color(0, 51, 255));
        txt_dir1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_dir1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        txt_dir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dir1ActionPerformed(evt);
            }
        });
        jPanel2.add(txt_dir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 220, 242, -1));

        txt_contraseña1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_contraseña1.setForeground(new java.awt.Color(0, 51, 255));
        txt_contraseña1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_contraseña1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_contraseña1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_contraseña1ActionPerformed(evt);
            }
        });
        jPanel2.add(txt_contraseña1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 366, 242, -1));

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 102));
        jLabel22.setText("FECHA DE INGRESO");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 273, -1, -1));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 102));
        jLabel23.setText("PERFIL");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 317, -1, -1));

        cbx_perfil1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        cbx_perfil1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ADMINISTRADOR", "TRABAJADOR" }));
        cbx_perfil1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 255)));
        cbx_perfil1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_perfil1ActionPerformed(evt);
            }
        });
        jPanel2.add(cbx_perfil1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 313, 240, -1));

        txt_fecha_ingreso1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));
        txt_fecha_ingreso1.setForeground(new java.awt.Color(0, 51, 255));
        txt_fecha_ingreso1.setToolTipText("");
        txt_fecha_ingreso1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jPanel2.add(txt_fecha_ingreso1, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 270, 242, -1));

        jPanel4.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 420, 400));

        btn_guardar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532190_3floppy_unmount.png"))); // NOI18N
        btn_guardar.setText("GUARDAR");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jPanel4.add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 130, 140, -1));

        jButton3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532351_button_cancel.png"))); // NOI18N
        jButton3.setText("CANCELAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 180, 140, -1));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449532072_Add-Male-User.png"))); // NOI18N
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 58, 59));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("NUEVO USUARIO");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 130, -1));

        frm_nuevo.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 610, 480));

        frm_fecha.setBackground(new java.awt.Color(204, 204, 255));
        frm_fecha.setBounds(new java.awt.Rectangle(900, 430, 400, 250));
        frm_fecha.setResizable(false);
        frm_fecha.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/logout.png"))); // NOI18N
        jLabel32.setText("SALIR");
        jLabel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });
        frm_fecha.getContentPane().add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 70, -1));

        cal_usu.setBackground(new java.awt.Color(255, 255, 255));
        cal_usu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        cal_usu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cal_usuMouseClicked(evt);
            }
        });
        frm_fecha.getContentPane().add(cal_usu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 63, 400, 150));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel31.setText("jLabel31");
        jLabel31.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel31MouseDragged(evt);
            }
        });
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel31MousePressed(evt);
            }
        });
        frm_fecha.getContentPane().add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, -1));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 2, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(0, 0, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SELECCIONAR");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 130, 20));

        frm_fecha.getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 400, 180));

        jPanel10.setBackground(new java.awt.Color(0, 102, 255));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        frm_fecha.getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 400, 10));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
        );

        frm_fecha.getContentPane().add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 400, 30));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 10, -1, -1));

        tbl_usuario.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tbl_usuario.setForeground(new java.awt.Color(0, 51, 255));
        tbl_usuario.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_usuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_usuario);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 218, 1080, 300));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Banner.png"))); // NOI18N
        jLabel24.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel24MouseDragged(evt);
            }
        });
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel24MousePressed(evt);
            }
        });
        getContentPane().add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_eliminar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449494490_f-cross_256.png"))); // NOI18N
        btn_eliminar.setText("ELIMINAR");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 160, 60));

        btn_modifcar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_modifcar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449494334_pencil.png"))); // NOI18N
        btn_modifcar.setText("MODIFICAR");
        btn_modifcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modifcarActionPerformed(evt);
            }
        });
        jPanel6.add(btn_modifcar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, 170, 60));

        btn_nuevo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1449530684_gnome-app-install.png"))); // NOI18N
        btn_nuevo.setText("NUEVO");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jPanel6.add(btn_nuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 150, 60));

        lbl_registros.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        lbl_registros.setForeground(new java.awt.Color(0, 0, 204));
        lbl_registros.setText(".");
        jPanel6.add(lbl_registros, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 10, 40, 40));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setText("REGISTROS");
        jPanel6.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 20, -1, -1));

        jLabel33.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        jLabel33.setText("BUSCAR");
        jPanel6.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, -1, 30));

        txt_buscar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txt_buscar.setForeground(new java.awt.Color(0, 51, 255));
        txt_buscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
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
        jPanel6.add(txt_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 100, 340, 30));

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/1453329666_people.png"))); // NOI18N
        jPanel6.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, 42));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("REGISTRO DE USUARIOS");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, -1, -1));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1100, 470));

        jPanel7.setBackground(new java.awt.Color(0, 102, 255));

        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("jLabel34");
        lbl_pie.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(159, Short.MAX_VALUE)
                .addComponent(lbl_pie, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(lbl_pie)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 1100, 20));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed

        int row = tbl_usuario.getSelectedRow();
        int cod = Integer.parseInt(tbl_usuario.getValueAt(row,0).toString());
        Conexion c = new Conexion();
        Connection cn = c.conectar();
        String sql = "DELETE FROM `tusuario` WHERE `id_usu` = "+cod+"";

        int opc = JOptionPane.showOptionDialog(btn_eliminar, "¿ESTA SEGURO QUE DESEA ELIMINAR ESTE REGISTRO?", "showOptionDialog", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"SI", "NO"}, "SI");
        if (opc == 0) {
            try {
                Statement st = cn.createStatement();
                int rs = st.executeUpdate(sql);

                if (rs > 0) {
                    limpiarTabla();
                    cargarTabla();
                    bloquear();

                } else {

                }

            } catch (SQLException | HeadlessException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
            JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO");
        }

    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void tbl_usuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_usuarioMouseClicked
         int fila = tbl_usuario.getSelectedRow();
        if (fila != -1) {
            Desbloquear();

        }

    }//GEN-LAST:event_tbl_usuarioMouseClicked

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        frm_nuevo.setVisible(true);
        txt_nombres1.setText("");
        txt_apellidos1.setText("");
        txt_dni1.setText("");
        txt_telf1.setText("");
        txt_dir1.setText("");
        txt_fecha_ingreso1.setDate(null);
        txt_contraseña1.setText("");
        txt_buscar.setText("");
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_modifcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modifcarActionPerformed
        frm_modificar.setVisible(true);
        modificar_usuario();
        txt_nombres.requestFocus();
    }//GEN-LAST:event_btn_modifcarActionPerformed

    private void cbx_perfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_perfilActionPerformed
        if (cbx_perfil.getSelectedIndex()==0) {
            txt_perfil.setText("ADMINISTRADOR");
        } else {
            txt_perfil.setText("TRABAJADOR");
        }
    }//GEN-LAST:event_cbx_perfilActionPerformed

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed

    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_perfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_perfilActionPerformed

    }//GEN-LAST:event_txt_perfilActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        try {
            if (comprobarActualizacion() != 0) {
                if (txt_dni.getText().trim().length() < 8) {
                    JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "EL DNI DEBE TENER 8 DIGITOS");
                    txt_dni.requestFocus();       
               
               }else{
                    frm_modificar.dispose();
                    actualizarUsuarioEnBD();
                    bloquear();
                    txt_buscar.setText("");
                    txt_buscar.requestFocus();
            
            }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), "SELECCIONE UNA FECHA");
        }
        
            
        
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        frm_modificar.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_nombres1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombres1ActionPerformed
        txt_nombres1.transferFocus();
    }//GEN-LAST:event_txt_nombres1ActionPerformed

    private void txt_nombres1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombres1KeyTyped
        int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE SOLO LETRAS");
            txt_nombres.requestFocus();
            //txt_nombres.requestFocus();
        }else{

        }
    }//GEN-LAST:event_txt_nombres1KeyTyped

    private void txt_apellidos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_apellidos1ActionPerformed
        txt_apellidos1.transferFocus();
    }//GEN-LAST:event_txt_apellidos1ActionPerformed

    private void txt_apellidos1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apellidos1KeyTyped
        int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE SOLO LETRAS");
            txt_apellidos.requestFocus();
        }
    }//GEN-LAST:event_txt_apellidos1KeyTyped

    private void txt_dni1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dni1ActionPerformed
        txt_dni1.transferFocus();
    }//GEN-LAST:event_txt_dni1ActionPerformed

    private void txt_dni1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dni1KeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_dni1.requestFocus();

        } else {
            if (txt_dni1.getText().trim().length()==8) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_dni1KeyTyped

    private void txt_telf1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telf1ActionPerformed
        txt_telf1.transferFocus();
    }//GEN-LAST:event_txt_telf1ActionPerformed

    private void txt_telf1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telf1KeyTyped
       int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_telf1.requestFocus();

        } else {
            if (txt_telf1.getText().trim().length()==10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_telf1KeyTyped

    private void txt_dir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dir1ActionPerformed
        txt_dir1.transferFocus();
    }//GEN-LAST:event_txt_dir1ActionPerformed

    private void txt_contraseña1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_contraseña1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_contraseña1ActionPerformed

    private void cbx_perfil1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_perfil1ActionPerformed
        
    }//GEN-LAST:event_cbx_perfil1ActionPerformed

         public int verificarExisteUsuario(String dni){
         int opc = 0;
         String sql = "SELECT `dni_usu` FROM `tusuario` WHERE `dni_usu` = "+dni;
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
    
    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
      
        if (comprobar() != 0) {
            if (txt_dni1.getText().trim().length() < 8) {
                JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "EL DNI DEBE TENER 8 DIGITOS");
                txt_dni1.requestFocus();
                
            } else {
                String doc = txt_dni1.getText();
                if (verificarExisteUsuario(doc) == 0) {
                    Reg_usuario();
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(frm_nuevo.getRootPane(), "EXISTE USUARIO CON EL DNI INGRESADO");
                    limpiar();
                }
            }
        }
      
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        frm_nuevo.dispose();
        txt_buscar.requestFocus();
        bloquear();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void cal_usuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cal_usuMouseClicked

    }//GEN-LAST:event_cal_usuMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        Date dia = cal_usu.getDate();
        String f = formateador.format(dia);
//        txt_fecha.setText(f);
        frm_fecha.dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel24MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MousePressed
       posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_jLabel24MousePressed

    private void jLabel24MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x-posx, point.y-posy);
    }//GEN-LAST:event_jLabel24MouseDragged

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel25MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MousePressed
        posx1 = evt.getX();
        posy1 = evt.getY();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel25MousePressed

    private void jLabel25MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        frm_nuevo.setLocation(point.x-posx1, point.y-posy1);
    }//GEN-LAST:event_jLabel25MouseDragged

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
       frm_nuevo.dispose();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel27MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MousePressed
        posx2 = evt.getX();
        posy2 = evt.getY();    
    }//GEN-LAST:event_jLabel27MousePressed

    private void jLabel27MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        frm_modificar.setLocation(point.x-posx2, point.y-posy2);
    }//GEN-LAST:event_jLabel27MouseDragged

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
      bloquear();
    }//GEN-LAST:event_formMouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
       frm_modificar.dispose();
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
       frm_fecha.dispose();
    }//GEN-LAST:event_jLabel32MouseClicked

    private void jLabel31MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MousePressed
        posx3 = evt.getX();
        posy3 = evt.getY();
    }//GEN-LAST:event_jLabel31MousePressed

    private void jLabel31MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        frm_fecha.setLocation(point.x-posx3, point.y-posy3);
    }//GEN-LAST:event_jLabel31MouseDragged

    private void txt_buscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscarKeyReleased
        buscarUsuario();
    }//GEN-LAST:event_txt_buscarKeyReleased

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        bloquear();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void txt_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_buscarMouseClicked
        bloquear();
    }//GEN-LAST:event_txt_buscarMouseClicked

    private void txt_dniKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dniKeyTyped
         int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE SOLO NUMEROS");
            txt_dni.requestFocus();

        } else {
            if (txt_dni.getText().trim().length()==8) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_dniKeyTyped

    private void txt_nombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombresKeyTyped
             int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE SOLO LETRAS");
            txt_nombres.requestFocus();
        }
    }//GEN-LAST:event_txt_nombresKeyTyped

    private void txt_apellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apellidosKeyTyped
         int tecla = (int)evt.getKeyChar();
        if(tecla>47 && tecla<58){
            evt.setKeyChar((char)KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(frm_modificar.getRootPane(), "INGRESE SOLO LETRAS");
            txt_apellidos.requestFocus();
        }
    }//GEN-LAST:event_txt_apellidosKeyTyped

    private void txt_nombresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombresActionPerformed
        txt_nombres.transferFocus();
    }//GEN-LAST:event_txt_nombresActionPerformed

    private void txt_apellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_apellidosActionPerformed
       txt_apellidos.transferFocus();
    }//GEN-LAST:event_txt_apellidosActionPerformed

    private void txt_dniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dniActionPerformed
       txt_dni.transferFocus();
    }//GEN-LAST:event_txt_dniActionPerformed

    private void txt_telfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telfActionPerformed
      txt_telf.transferFocus();
    }//GEN-LAST:event_txt_telfActionPerformed

    private void txt_dirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dirActionPerformed
        txt_dir.transferFocus();
    }//GEN-LAST:event_txt_dirActionPerformed

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
            java.util.logging.Logger.getLogger(Reg_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reg_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reg_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reg_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Reg_usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_eliminar;
    public javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_modifcar;
    private javax.swing.JButton btn_nuevo;
    private com.toedter.calendar.JCalendar cal_usu;
    private javax.swing.JComboBox cbx_perfil;
    private javax.swing.JComboBox cbx_perfil1;
    private javax.swing.JDialog frm_fecha;
    private javax.swing.JDialog frm_modificar;
    private javax.swing.JDialog frm_nuevo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdc_fecha;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_registros;
    public javax.swing.JTable tbl_usuario;
    private javax.swing.JTextField txt_apellidos;
    private javax.swing.JTextField txt_apellidos1;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_contraseña;
    private javax.swing.JTextField txt_contraseña1;
    private javax.swing.JTextField txt_dir;
    private javax.swing.JTextField txt_dir1;
    private javax.swing.JTextField txt_dni;
    private javax.swing.JTextField txt_dni1;
    private com.toedter.calendar.JDateChooser txt_fecha_ingreso1;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nombres;
    private javax.swing.JTextField txt_nombres1;
    private javax.swing.JTextField txt_perfil;
    private javax.swing.JTextField txt_telf;
    private javax.swing.JTextField txt_telf1;
    // End of variables declaration//GEN-END:variables
}
