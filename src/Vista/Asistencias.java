/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.table.DefaultTableModel;
import Control.Conexion;
import Control.ManejadorFechas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import javax.swing.JOptionPane;

/**
 *
 * @author Gaby
 */
public final class Asistencias extends javax.swing.JFrame implements Runnable{

    int posx, posy, bandera = -1;
    
    String hora, minutos, segundos, ampm;
    
    Calendar calendario;
    Thread h1;
    
    DefaultTableModel model;
    
    Connection con = new Conexion().conectar();
    
    public Asistencias() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        txtCodigo.setEnabled(false);
        lbl_pie.setText(new Farma_inf().pie());
        setLocationRelativeTo(null);
        cabecera();
        h1 = new Thread(this);
        h1.start();
        setVisible(true);
        cargarAsistencia();
        cargarAnuncio();
    }
    
    public void calcular() {
        calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();        
        
        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";

        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY) - 12;
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            hora = calendario.get(Calendar.HOUR_OF_DAY) > 9 ? "" + calendario.get(Calendar.HOUR_OF_DAY) : "0" + calendario.get(Calendar.HOUR_OF_DAY);
        }
        minutos = calendario.get(Calendar.MINUTE) > 9 ? "" + calendario.get(Calendar.MINUTE) : "0" + calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND) > 9 ? "" + calendario.get(Calendar.SECOND) : "0" + calendario.get(Calendar.SECOND);
    }
    
    public String obtener_hora() {
        String hora = "";
        calcular();
        hora = (this.hora + ":" + minutos + ":" + segundos + " " + ampm);
        return hora;
    }
    
    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == h1) {
            calcular();
            lbl_hora.setText(hora + ":" + minutos + ":" + segundos + " " + ampm);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
    
    public void cabecera(){
        String [] titulos = {"# Reg","Nombre","Apellido","Tipo","Hora","Min Retraso", "Faltas","FECHA"};
        model = new DefaultTableModel(null, titulos);
        tbl_asistencia.setModel(model);
        btn_marcar.setEnabled(false);
    }
    
    
    
    public void registrarAsistencia(int dni){
        try {
            String hh = new ManejadorFechas().getHoraActual();
            String fecha = new ManejadorFechas().getFechaActualMySQL();
            String nom = getNombre(dni);
            int id_usu = getId(dni);
            String tipoMarcado = null;
            if (cmb_entrada.isSelected()) {
                tipoMarcado = "ENTRADA";
            }else{
                tipoMarcado = "SALIDA";
            }
            int tardanza = getMinRetraso();
            int falta = calcFaltas(Integer.parseInt(hora));
            
            String obs = txtObservaciones.getText();
            String sql = "INSERT INTO `tasistencia`(`hora_as`, `fecha_as`, `tipo_as`, `min_tarde_as`, `faltas_as`, `observ_as`, `id_usu`) VALUES ('"+hh+"','"+fecha+"','"+tipoMarcado+"','"+tardanza+"','"+falta+"','"+obs+"','"+id_usu+"')";
            Statement st = con.createStatement();
            int res = st.executeUpdate(sql);
            
            if (res > 0) {
                if (cmb_entrada.isSelected()) {
                    JOptionPane.showMessageDialog(getRootPane(), "Buenos dias " + nom);
                } else {
                    JOptionPane.showMessageDialog(getRootPane(), "Hasta luego " + nom);
                }
            } else {
            }
            
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    
    
    public void mostrarDetalles(){
        lbl_fecha.setText(""+ new ManejadorFechas().getFechaActual());
        lbl_entrada.setText("" + new ManejadorFechas().getHoraActual());
        int dni = Integer.parseInt(txtCodigo.getText());
        String nom = getNombre(dni)+" "+ getApellido(dni) ;
        lbl_nombres.setText(nom);
        //int tardanza = calcMinRetraso();
        int id = getId(dni);
        lbl_min_tarde.setText(""+setMinRetraso(id));
        int hor = calendario.get(Calendar.HOUR_OF_DAY);
        lbl_faltas.setText(""+calcFaltas(hor));
    }
    
    public int calcFaltas(int hora){
        int faltas = 0;
        if (hora>=9) {
                    faltas = faltas + 1;
                }
        return faltas;
    }
    
    public int setMinRetraso(int id){
        int min_ret = 0;
        String sql = "SELECT SUM(min_tarde_as) FROM tasistencia WHERE id_usu = "+id+" ";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                min_ret = rs.getInt("SUM(min_tarde_as)");
            } else {
            }
        } catch (Exception e) {
        }
        return min_ret;
    }
    
    
    public int getMinRetraso(){
        int mm=0;
        int hor = calendario.get(Calendar.HOUR_OF_DAY);
        int min = calendario.get(Calendar.MINUTE);        
        try {
            if (hor >= 8) {
                if (min > 0) {
                    mm = min;
                }
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return mm;
    }
    
    
    
    public String getNombre(int dni){
        String s=null;
        try {
            String sql = "SELECT nom_usu FROM tusuario WHERE `dni_usu` = '"+dni+"' ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                s= rs.getString("nom_usu");
            } else {
                System.out.println("Error en la consulta");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return s;
    }
    
    public String getApellido(int dni){
        String s="";
        try {
            String sql = "SELECT apell_usu FROM tusuario WHERE `dni_usu` = '"+dni+"' ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                s= rs.getString("apell_usu");
            } else {
                s="error";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return s;
    }
    
    public int getId(int dni){
        int s=0;
        try {
            String sql = "SELECT id_usu FROM tusuario WHERE dni_usu = '"+dni+"' ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                s= rs.getInt("id_usu");
            } else {
                System.out.println("Error en la consulta");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return s;
    }
    
    public void cargarAsistencia(){
        String sql = "SELECT id_as,nom_usu,apell_usu,tipo_as,hora_as,min_tarde_as,faltas_as,fecha_as  FROM tasistencia INNER JOIN tusuario ON tasistencia.id_usu = tusuario.id_usu ORDER BY id_as DESC";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[8];
            while (rs.next()) {                
                datos[0]=rs.getString("id_as");
                datos[1]=rs.getString("nom_usu");
                datos[2]=rs.getString("apell_usu");
                datos[3]=rs.getString("tipo_as");
                datos[4]=rs.getString("hora_as");
                datos[5]=rs.getString("min_tarde_as");
                datos[6]=rs.getString("faltas_as");
                datos[7]=rs.getString("fecha_as");
                model.addRow(datos);
                tbl_asistencia.setModel(model);
            }
            //TAMAÑOS DE LAS CELDAS
            tbl_asistencia.getColumnModel().getColumn(0).setPreferredWidth(40);
            tbl_asistencia.getColumnModel().getColumn(1).setPreferredWidth(70);
            tbl_asistencia.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbl_asistencia.getColumnModel().getColumn(3).setPreferredWidth(80);
            tbl_asistencia.getColumnModel().getColumn(4).setPreferredWidth(70);
            tbl_asistencia.getColumnModel().getColumn(5).setPreferredWidth(90);
            tbl_asistencia.getColumnModel().getColumn(6).setPreferredWidth(60);
            tbl_asistencia.getColumnModel().getColumn(7).setPreferredWidth(90);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    public void cargarAnuncio(){
        String sql = "SELECT anuncio FROM tconfiguracion";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                txa_avisos.setText(rs.getString("anuncio"));
            } else {
                txa_avisos.setText("No existen avisos pendientes");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
    }
    
    
    public void limpiarTabla() {
        for (int i = 0; i < tbl_asistencia.getRowCount(); i++) {
            model.removeRow(i);
            i -= 1;
        }
    }
    
    
    public int tipoMarcado(){
        int tipo=0;
        
        if (cmb_entrada.isSelected()) {
            tipo = 1;
        } else {
            tipo = 2;
        }
        return tipo;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_salir = new javax.swing.JLabel();
        lbl_logo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lbl_pie = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_marcar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        txtCodigo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_asistencia = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txa_avisos = new javax.swing.JTextArea();
        lbl_min_tarde = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_fecha = new javax.swing.JLabel();
        lbl_nombres = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        txtObservaciones = new javax.swing.JTextField();
        lbl_faltas = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_entrada = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_hora = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cmb_entrada = new javax.swing.JCheckBox();
        cmb_salida = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
        getContentPane().add(lbl_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, -1, -1));

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
        getContentPane().add(lbl_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 890, 60));

        jPanel2.setBackground(new java.awt.Color(0, 102, 204));
        jPanel2.setForeground(new java.awt.Color(0, 102, 204));

        lbl_pie.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lbl_pie.setForeground(new java.awt.Color(255, 255, 255));
        lbl_pie.setText("jLabel1");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(lbl_pie, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lbl_pie)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 710, 890, 20));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("CÓDIGO / DNI");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, -1, -1));

        btn_marcar.setBackground(new java.awt.Color(0, 102, 204));
        btn_marcar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_marcar.setForeground(new java.awt.Color(255, 255, 255));
        btn_marcar.setText("MARCAR");
        btn_marcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_marcarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_marcar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 370, -1));
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 830, 10));
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 328, 830, 0));

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 36)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 1, true));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        getContentPane().add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 370, 50));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 2, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_asistencia.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tbl_asistencia.setForeground(new java.awt.Color(0, 51, 255));
        tbl_asistencia.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_asistencia);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 610, 310));

        txa_avisos.setEditable(false);
        txa_avisos.setColumns(20);
        txa_avisos.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        txa_avisos.setLineWrap(true);
        txa_avisos.setRows(5);
        txa_avisos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)), "AVISOS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 0, 14), new java.awt.Color(0, 0, 102))); // NOI18N
        jScrollPane2.setViewportView(txa_avisos);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 330, 240, 310));

        lbl_min_tarde.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        lbl_min_tarde.setForeground(new java.awt.Color(0, 102, 204));
        jPanel4.add(lbl_min_tarde, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, 110, 20));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/employee.png"))); // NOI18N
        jLabel4.setText("Nombres y apellidos:");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, -1, -1));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/alarm.png"))); // NOI18N
        jLabel13.setText("Tardanza acumulada:");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 170, -1));

        lbl_fecha.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        lbl_fecha.setForeground(new java.awt.Color(0, 102, 204));
        jPanel4.add(lbl_fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 210, 100, 20));

        lbl_nombres.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        lbl_nombres.setForeground(new java.awt.Color(204, 0, 0));
        jPanel4.add(lbl_nombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, 190, 20));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 880, 10));

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setText("OBSERVACIONES:");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        txtObservaciones.setEditable(false);
        txtObservaciones.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtObservaciones.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtObservaciones.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtObservaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacionesActionPerformed(evt);
            }
        });
        jPanel4.add(txtObservaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 750, 30));

        lbl_faltas.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        lbl_faltas.setForeground(new java.awt.Color(0, 102, 204));
        jPanel4.add(lbl_faltas, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 240, 150, 20));

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/modificar.png"))); // NOI18N
        jLabel14.setText("Faltas:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 240, 70, -1));

        lbl_entrada.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        lbl_entrada.setForeground(new java.awt.Color(204, 0, 0));
        jPanel4.add(lbl_entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 210, 150, 20));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fecha.png"))); // NOI18N
        jLabel16.setText("Fecha:");
        jPanel4.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 2, true), "HORA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("SansSerif", 1, 14))); // NOI18N

        lbl_hora.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        lbl_hora.setForeground(new java.awt.Color(204, 0, 0));
        lbl_hora.setText("08:11 p.m.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(lbl_hora)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_hora)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 102), 2, true));

        cmb_entrada.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        cmb_entrada.setForeground(new java.awt.Color(0, 102, 0));
        cmb_entrada.setText("ENTRADA");
        cmb_entrada.setBorder(null);
        cmb_entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_entradaActionPerformed(evt);
            }
        });

        cmb_salida.setFont(new java.awt.Font("SansSerif", 2, 14)); // NOI18N
        cmb_salida.setForeground(new java.awt.Color(255, 0, 0));
        cmb_salida.setText("SALIDA");
        cmb_salida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_salidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmb_entrada)
                .addGap(18, 18, 18)
                .addComponent(cmb_salida)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_entrada, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_salida, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, -1, -1));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/code.png"))); // NOI18N
        jLabel9.setText("Entrada a las: ");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 120, -1));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("SELECCIONE EL TIPO DE MARCADO:");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 250, -1));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 890, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_logoMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMouseDragged
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.setLocation(point.x-posx, point.y-posy);
    }//GEN-LAST:event_lbl_logoMouseDragged

    private void lbl_logoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_logoMousePressed
        posx = evt.getX();
        posy = evt.getY();
    }//GEN-LAST:event_lbl_logoMousePressed

    private void btn_marcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_marcarActionPerformed
        //cambiar la relacion tusuario y tasistencia
        if (!txtCodigo.getText().trim().isEmpty()) {
            int dni = Integer.parseInt(txtCodigo.getText());
            String ape = getApellido(dni);
            //lbl_ddd.setText(ape);
            if (ape.trim().equals("error")) {
                JOptionPane.showMessageDialog(getRootPane(), "Usuario incorrecto");
                txtCodigo.setText("");
                txtCodigo.requestFocus();
            } else {
                //JOptionPane.showMessageDialog(null, "Bien");
                try {
                    registrarAsistencia(dni);
                    mostrarDetalles();
                    limpiarTabla();
                    cargarAsistencia();
                    txtCodigo.setText("");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(getRootPane(), "Ingrese codigo");
            txtCodigo.requestFocus();
        }
        
        
        
        
    }//GEN-LAST:event_btn_marcarActionPerformed

    private void lbl_salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_salirMouseClicked
        dispose();
    }//GEN-LAST:event_lbl_salirMouseClicked

    private void cmb_entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_entradaActionPerformed
        if (cmb_entrada.isSelected()) {
            btn_marcar.setEnabled(true);
            btn_marcar.setBackground(Color.green);
            cmb_salida.setEnabled(false);
            txtCodigo.setEnabled(true);
            txtCodigo.requestFocus();
        }else{
            btn_marcar.setEnabled(false);
            cmb_salida.setEnabled(true);
            txtCodigo.setEnabled(false);
            
        }
    }//GEN-LAST:event_cmb_entradaActionPerformed

    private void cmb_salidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_salidaActionPerformed
        if (cmb_salida.isSelected()) {
            btn_marcar.setEnabled(true);
            btn_marcar.setBackground(Color.red);
            cmb_entrada.setEnabled(false);
            txtCodigo.setEnabled(true);
            txtCodigo.requestFocus();
        }else{
            btn_marcar.setEnabled(false);
            cmb_entrada.setEnabled(true);
            txtCodigo.setEnabled(false);
        }
    }//GEN-LAST:event_cmb_salidaActionPerformed

    private void txtObservacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacionesActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        int tecla = (int) evt.getKeyChar();
        if (tecla > 64 && tecla < 91 || tecla > 96 && tecla < 123)  {
            evt.setKeyChar((char) KeyEvent.VK_CLEAR);
            JOptionPane.showMessageDialog(getRootPane(), "INGRESE SOLO NUMEROS");
            txtCodigo.requestFocus();
        } else {
            if (txtCodigo.getText().trim().length()==8) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            if (!txtCodigo.getText().trim().isEmpty()) {
                int dni = Integer.parseInt(txtCodigo.getText());
                String ape = getApellido(dni);
                //lbl_ddd.setText(ape);
                if (ape.trim().equals("error")) {
                    JOptionPane.showMessageDialog(getRootPane(), "Usuario incorrecto");
                    txtCodigo.setText("");
                    txtCodigo.requestFocus();
                } else {
                    //JOptionPane.showMessageDialog(null, "Bien");
                    try {
                        registrarAsistencia(dni);
                        mostrarDetalles();
                        limpiarTabla();
                        cargarAsistencia();
                        txtCodigo.setText("");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "Ingrese codigo");
                txtCodigo.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

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
            java.util.logging.Logger.getLogger(Asistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Asistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Asistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Asistencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Asistencias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_marcar;
    private javax.swing.JCheckBox cmb_entrada;
    private javax.swing.JCheckBox cmb_salida;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lbl_entrada;
    private javax.swing.JLabel lbl_faltas;
    private javax.swing.JLabel lbl_fecha;
    private javax.swing.JLabel lbl_hora;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_min_tarde;
    private javax.swing.JLabel lbl_nombres;
    private javax.swing.JLabel lbl_pie;
    private javax.swing.JLabel lbl_salir;
    private javax.swing.JTable tbl_asistencia;
    private javax.swing.JTextArea txa_avisos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtObservaciones;
    // End of variables declaration//GEN-END:variables
}
