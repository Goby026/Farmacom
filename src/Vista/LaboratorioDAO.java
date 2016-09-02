package Vista;

import Control.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LaboratorioDAO {
    Connection con = new Conexion().conectar();
    public boolean Registrar(Laboratorio l) throws SQLException{
        try {
            PreparedStatement pst = con.prepareStatement("insert into laboratorio(nombre,direccion,telefono) values (?,?,?)");
            pst.setString(1, l.getNombre());
            pst.setString(2, l.getDireccion());
            pst.setString(3, l.getTelefono());
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
    
    public boolean modificar(Laboratorio l) throws Exception{
        try {
            PreparedStatement pst = con.prepareStatement("update laboratorio set nombre=?, direccion=?, telefono=? where idlaboratorio = ?");
            pst.setString(1, l.getNombre());
            pst.setString(2, l.getDireccion());
            pst.setString(3, l.getTelefono());
            pst.setInt(4, l.getId());
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
    
    public boolean eliminar(Laboratorio l) throws SQLException{
        try {
            PreparedStatement pst = con.prepareStatement("delete from laboratorio where idlaboratorio = ?");
            pst.setInt(1, l.getId());
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
    
    public List<Laboratorio> listar() throws SQLException{
        List<Laboratorio> lista = new ArrayList<>();
        try {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM laboratorio");
            ResultSet res = pst.executeQuery();
            while (res.next()) {
                Laboratorio l = new Laboratorio();
                l.setId(res.getInt("idlaboratorio"));
                l.setNombre(res.getString("nombre"));
                l.setDireccion(res.getString("direccion"));
                l.setTelefono(res.getString("telefono"));
                lista.add(l);
            }
            pst.close();
            res.close();
        } catch (Exception e) {
            throw e;
        }
        
        return lista;
    }
}
