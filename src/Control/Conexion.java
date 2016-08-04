package Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Adolfo
 */
public class Conexion {
    Connection cn = null;
    
    public Connection conectar(){
        try {
          Class.forName("com.mysql.jdbc.Driver");
          cn=DriverManager.getConnection("jdbc:mysql://localhost/farmacom1","root","");
          //cn=DriverManager.getConnection("jdbc:mysql://localhost/farmacom","root","wasufofo15");
          //JOptionPane.showMessageDialog(null, "CONEXION EXITOSA");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null,"Error"+e);
        }
        return cn;
    }
}
