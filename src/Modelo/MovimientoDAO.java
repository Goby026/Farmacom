
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
public class MovimientoDAO {
    Connection con = new Conexion().conectar();
    public boolean Registrar(Movimiento m) throws SQLException{
        try {
            PreparedStatement pst = con.prepareStatement("insert into tmovimientos(producto,cantidad,concepto,tipo,fecha) values (?,?,?,?,?)");
            pst.setString(1, m.getProducto());
            pst.setInt(2, m.getCantidad());
            pst.setString(3, m.getConcepto());
            pst.setString(4, m.getTipo());
            pst.setString(5, m.getFecha());
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
    
    public boolean modificar(Movimiento m) throws Exception{
        try {
            PreparedStatement pst = con.prepareStatement("update tmovimientos set producto=?, cantidad=?,concepto=?, tipo=? ,fecha=? where idtmovimientos = ?");
            pst.setString(1, m.getProducto());
            pst.setInt(2, m.getCantidad());
            pst.setString(3, m.getConcepto());
            pst.setString(4, m.getTipo());
            pst.setString(5, m.getFecha());
            pst.setInt(6, m.getIdMovimiento());
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
    
    public boolean eliminar(Movimiento m) throws SQLException{
        try {
            PreparedStatement pst = con.prepareStatement("delete from tmovimientos where idtmovimientos = ?");
            pst.setInt(1, m.getIdMovimiento());
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
    
    public List<Movimiento> listar() throws SQLException{
        List<Movimiento> lista = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM tmovimientos");
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Movimiento m = new Movimiento();
                m.setIdMovimiento(res.getInt("idtmovimientos"));
                m.setProducto(res.getString("producto"));
                m.setCantidad(res.getInt("cantidad"));
                m.setConcepto(res.getString("concepto"));
                m.setTipo(res.getString("tipo"));
                m.setFecha(res.getString("fecha"));                
                lista.add(m);
            }
            pst.close();
            res.close();
        } catch (Exception e) {
            throw e;
        }
        
        return lista;
    }
}
