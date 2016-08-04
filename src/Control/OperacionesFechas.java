
package Control;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Gaby
 */
public final class OperacionesFechas{
    Connection con = new Conexion().conectar();

    public OperacionesFechas() {       
        diasRestaFecha();
    }
    
    public void calcularFecha(){
        String sql = "SELECT fecha_as FROM tasistencia WHERE id_as = 1";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                Date fecha = rs.getDate("fecha_as");
                ManejadorFechas mf = new ManejadorFechas();
                java.sql.Date ff = mf.sumarFechasDias((java.sql.Date) fecha, 2);
                System.out.println("Fecha :" +ff);
            } else {
                System.out.println("Error con la fecha");
            }
        } catch (SQLException | HeadlessException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void diasRestaFecha(){
        //capturar fecha actual
        String factual = new ManejadorFechas().getFechaActual();
        Date fec_actual = new ManejadorFechas().deStringToDate(factual);
        System.out.println(factual);
        
        //capturar fecha de BD
        String sql = "SELECT fecha_venc_pro_medi FROM tproducto_medicamento WHERE id_pro_medi = 360";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                Date fecha = rs.getDate("fecha_venc_pro_medi");//fecha de vencimiento
                ManejadorFechas mf = new ManejadorFechas();
                int ff = mf.diferenciasDeFechas(fec_actual, fecha);
                System.out.println("Dias : " +ff);
            } else {
                System.out.println("Error con la fecha");
            }
        } catch (SQLException | HeadlessException e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    
    
    public static void main(String[] args) {
        OperacionesFechas cargar_productos = new OperacionesFechas();        
    }
}
