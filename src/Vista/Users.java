
package Vista;

import Control.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author Goby
 */
public class Users extends javax.swing.JFrame{
    Connection con = new Conexion().conectar();
    
    
    public String cargarNomUsuario(int id){
        String nom = null;        
        String sql = "SELECT `nom_usu` FROM `tusuario` WHERE `id_usu` = "+id+" ";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                nom = rs.getString("nom_usu");                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return nom;
    }
    
    public String cargarApeUsuario(int id){       
        String ape = null;
        String sql = "SELECT `nom_usu`, `apell_usu` FROM `tusuario` WHERE `id_usu` = "+id+"";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                ape = rs.getString("apell_usu");            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return ape;
    }
    
    public int obtenerDni(String nombre, String apellido){
        int dni=0;
        String sql = "SELECT `dni_usu` FROM `tusuario` WHERE `	nom_usu` = "+nombre+" AND `apell_usu`="+apellido+" ";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                dni = rs.getInt("dni_usu");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return dni;
    }
    
    public int getCustomerId(String nombre){
        int id=0;
        try {
            String sql = "SELECT id_cli FROM tcliente WHERE nom_cli = '"+nombre+"' ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id= Integer.parseInt(rs.getString("id_cli"));
            } else {
                System.out.println("Error en la consulta");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return id;
    }
    
    public String getCustomerName(int codigo){
        String nombre = null;
        try {
            String sql = "SELECT nom_cli FROM tcliente WHERE id_cli = "+codigo+" ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                nombre= rs.getString("nom_cli");
            } else {
                System.out.println("Error en la consulta");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return nombre;
    }
    
    public String getCustomerLastName(int codigo){
        String apellido = null;
        try {
            String sql = "SELECT apell_cli FROM tcliente WHERE id_cli = "+codigo+" ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                apellido= rs.getString("apell_cli");
            } else {
                System.out.println("Error en la consulta");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return apellido;
    }
    
    
//    public int numId(int cod){
//        String n= cargarNomUsuario(cod);
//        String a = cargarApeUsuario(cod);
//        int id = 0;
//        String sql = "SELECT `id_usu` FROM `tusuario` WHERE `nom_usu` = '"+n+"' AND `apell_usu` = '"+a+"'";
//        try {
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            if (rs.next()) {
//                id = rs.getInt("id_usu");
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
//        }
//        return id;
//    }
}
