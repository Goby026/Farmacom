
package Vista;

import Control.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlmacenProductoDAO {    
    public boolean Registrar(AlmacenProducto ap) throws SQLException{
        Connection con = new Conexion().conectar();
        try {
            PreparedStatement pst = con.prepareStatement("insert into almacenproducto(idalmacen,idproducto,cantidad) values (?,?,?)");
            pst.setInt(1, ap.getIdAlmacen());
            pst.setInt(2, ap.getIdProducto());
            pst.setInt(3, ap.getCantidad());            
            int res = pst.executeUpdate();
            if (res>0) {
                return true;
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }finally{
            con.close();
        }
        return false;
    }
    
    public boolean modificar(AlmacenProducto ap) throws Exception{
        Connection con = new Conexion().conectar();
        try {
            PreparedStatement pst = con.prepareStatement("update almacenproducto set idalmacen=?, idproducto=?,cantidad=? where idalmacenProducto = ?");
            pst.setInt(1, ap.getIdAlmacen());
            pst.setInt(2, ap.getIdProducto());
            pst.setInt(3, ap.getCantidad());
            pst.setInt(4, ap.getIdAlmacenProducto());
            int res = pst.executeUpdate();
            if (res>0) {
                return true;
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }finally{
            con.close();
        }
        return false;
    }
    
    public boolean eliminar(AlmacenProducto ap) throws SQLException{
        Connection con = new Conexion().conectar();
        try {
            PreparedStatement pst = con.prepareStatement("delete from almacenproducto where idalmacenProducto = ?");
            pst.setInt(1, ap.getIdAlmacenProducto());
            int res = pst.executeUpdate();
            if (res>0) {
                return true;
            }
            pst.close();
        } catch (Exception e) {
            throw e;
        }finally{
            con.close();
        }
        return false;
    }
    
    public List<AlmacenProducto> listar() throws SQLException{
        Connection con = new Conexion().conectar();
        List<AlmacenProducto> lista = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM almacenproducto");
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                AlmacenProducto ap = new AlmacenProducto();
                ap.setIdAlmacenProducto(res.getInt("idalmacenProducto"));
                ap.setIdAlmacen(res.getInt("idalmacen"));
                ap.setIdProducto(res.getInt("idProducto"));
                ap.setCantidad(res.getInt("cantidad"));
                lista.add(ap);
            }
            pst.close();
            res.close();
        } catch (Exception e) {
            throw e;
        }finally{
            con.close();
        }
        
        return lista;
    }
}
