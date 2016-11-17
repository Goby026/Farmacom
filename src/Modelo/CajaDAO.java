
package Modelo;

import Control.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marce
 */
public class CajaDAO {
    
    Connection con = new Conexion().conectar();
    public boolean Registrar(Caja c) throws SQLException{
        try {
            PreparedStatement pst = con.prepareStatement("insert into tcaja(abrir_caja,cerra_caja,fecha_caja,hora_caja,observacion) values (?,?,?,?,?)");
            pst.setDouble(1, c.getMontoApertura());
            pst.setDouble(2, c.getMontoCierre());
            pst.setString(3, c.getFecha());
            pst.setString(4, c.getHora());
            pst.setString(5, c.getObservacion());
            int res = pst.executeUpdate();
            if (res>0) {
                return true;
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
    
    public boolean modificar(Caja c) throws Exception{
        try {
            PreparedStatement pst = con.prepareStatement("update tcaja set abrir_caja=?, cerra_caja=?,fecha_caja=?, hora_caja=? ,observacion=? where id_caja = ?");
            pst.setDouble(1, c.getMontoApertura());
            pst.setDouble(2, c.getMontoCierre());
            pst.setString(3, c.getFecha());
            pst.setString(4, c.getHora());
            pst.setString(5, c.getObservacion());
            pst.setInt(6, c.getIdCaja());
            int res = pst.executeUpdate();
            if (res>0) {
                return true;
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
    
    public boolean eliminar(Caja c) throws SQLException{
        try {
            PreparedStatement pst = con.prepareStatement("delete from tcaja where id_caja = ?");
            pst.setInt(1, c.getIdCaja());
            int res = pst.executeUpdate();
            if (res>0) {
                return true;
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }
        return false;
    }
    
    public List<Caja> listar() throws SQLException{
        List<Caja> lista = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM tcaja");
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Caja c = new Caja();
                c.setIdCaja(res.getInt("id_caja"));
                c.setMontoApertura(res.getDouble("abrir_caja"));
                c.setMontoCierre(res.getDouble("cerra_caja"));
                c.setFecha(res.getString("fecha_caja"));
                c.setHora(res.getString("hora_caja"));
                c.setObservacion(res.getString("observacion"));
                lista.add(c);
            }
            pst.close();
            res.close();
        } catch (Exception e) {
            throw e;
        }
        
        return lista;
    }
}
