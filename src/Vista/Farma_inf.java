package Vista;

import Control.Conexion;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Gaby
 */
public class Farma_inf extends javax.swing.JFrame {

    Connection con = new Conexion().conectar();

    public Double cambio() {
        double c = 0.0;
        String sql = "SELECT cambio FROM tconfiguracion WHERE id_config = 1";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                c = rs.getDouble("cambio");
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "No se pudo obtener el cambio");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return c;
    }

    public Double getSueldoMinimoVital() {
        double s = 0.0;
        String sql = "SELECT `salario` FROM tconfiguracion WHERE id_config = 1";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                s = rs.getDouble("salario");
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "No se pudo obtener el sueldo básico");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return s;
    }

    public Double igv() {
        double v = 0.0;
        String sql = "SELECT igv FROM tconfiguracion WHERE id_config = 1";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                v = rs.getDouble("igv");
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "No se pudo obtener el igv");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return v;
    }

    public int getStock(int idproducto) {
        int stock = 0;
        String sql = "SELECT `stock_pro_medi` FROM `tproducto_medicamento` WHERE id_pro_medi = " + idproducto + "";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                stock = rs.getInt("stock_pro_medi");
            } else {
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return stock;
    }

    public int setNewStock(int idprod, int cantidad) {
        int Stock = getStock(idprod);
        int newStock = Stock + cantidad;
        String sql = "UPDATE `tproducto_medicamento` SET `stock_pro_medi`= " + newStock + " WHERE `id_pro_medi` = " + idprod + " ";
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return newStock;
    }

    public int setRestarStock(int idprod, int cantidad) {
        int Stock = getStock(idprod);
        int newStock = 0;
        if (Stock >= cantidad) {
            newStock = Stock - cantidad;
            String sql = "UPDATE `tproducto_medicamento` SET `stock_pro_medi`= " + newStock + " WHERE `id_pro_medi` = " + idprod + " ";
            try {
                Statement st = con.createStatement();
                int rs = st.executeUpdate(sql);
                return newStock;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("La cantidad excede al stock disponible");
        }
        return newStock;
    }

    public double updatePrecio(int idprod, double newPrecio) {
        String sql = "UPDATE `tproducto_medicamento` SET `prec_venta`= " + newPrecio + " WHERE `id_pro_medi` = " + idprod + " ";
        try {
            Statement st = con.createStatement();
            int rs = st.executeUpdate(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }
        return newPrecio;
    }

    public String pie() {
        String cadena = "CADENA DE BOTICAS ";
        String nom_far = null;
        String dir_far = null;
        int tel_far = 0;
        String nom_resp = null;
        String sql = "SELECT nom_empresa, dir_empresa, telf_empresa, nom_responsable_empresa FROM tconfiguracion WHERE id_config = 1";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                nom_far = rs.getString("nom_empresa");
                dir_far = rs.getString("dir_empresa");
                tel_far = rs.getInt("telf_empresa");
                nom_resp = rs.getString("nom_responsable_empresa");

                cadena = cadena + nom_far + "           Dirección: " + dir_far + "          Teléfono: " + tel_far + "           De " + nom_resp;
            } else {
                JOptionPane.showMessageDialog(getRootPane(), "No se pudo obtener el cambio");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
        }

        return cadena;
    }

    public double Redondear(double numero) {
        return Math.rint(numero * 100) / 100;
    }

    public void alertaProductos() {
        String sql = "SELECT `id_pro_medi`, `nom_pro_medi`,`concentracion_pro_medi`, `presentacion_pro_medi`, `provee_labo_pro_medi`,`fec_ingreso_prod` ,`fecha_venc_pro_medi`, `stock_pro_medi` FROM tproducto_medicamento WHERE TIMESTAMPDIFF(DAY,CURDATE(),`fecha_venc_pro_medi`)<= 30";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                JOptionPane.showMessageDialog(getRootPane(), "EXISTEN PRODUCTOS QUE CADUCARAN EN MENOS DE  30  DIAS, REVISE EL MODULO DE CADUCIDAD DE PRODUCTOS");
            } else {
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getNombreProducto(int id) {
        String nombre = null;
        String sql = "SELECT `nom_pro_medi` FROM `tproducto_medicamento` WHERE `id_pro_medi`= " + id;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                nombre = rs.getString("nom_pro_medi");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nombre;
    }

    public double getPrecioProducto(String nombre, int codProd) {
        double precio = 0;
        String sql = "SELECT prec_venta FROM `tproducto_medicamento` WHERE `nom_pro_medi`= '" + nombre + "' AND id_pro_medi = " + codProd + "";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                precio = Double.parseDouble(rs.getString("prec_venta"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return precio;
    }

    public String getPresentacionProducto(int id) {
        String presentacion = null;
        String sql = "SELECT presentacion_pro_medi FROM tproducto_medicamento WHERE id_pro_medi = " + id;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                presentacion = rs.getString("presentacion_pro_medi");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return presentacion;
    }

    public String getLoteProducto(int id) {
        String lote = null;
        String sql = "SELECT lote FROM tproducto_medicamento WHERE id_pro_medi = " + id;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                lote = rs.getString("lote");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lote;
    }

    public String getFechaVencimientoProducto(int id) {
        String fecVencimiento = null;
        String sql = "SELECT fecha_venc_pro_medi FROM tproducto_medicamento WHERE id_pro_medi = " + id;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                fecVencimiento = rs.getString("fecha_venc_pro_medi");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fecVencimiento;
    }

    public int getIdProducto(String nombre) {
        int id = 0;
        String sql = "SELECT id_pro_medi FROM `tproducto_medicamento` WHERE `nom_pro_medi`= '" + nombre + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = Integer.parseInt(rs.getString("id_pro_medi"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public int getIdProveedor(String nombre) {
        int id = 0;
        String sql = "SELECT `id_provee` FROM `tproveedor` WHERE `nom_provee` = '" + nombre + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt("id_provee");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public String getNomProveedor(String nomProd) {
        String nombre = null;
        String sql = "SELECT provee_labo_pro_medi FROM tproducto_medicamento WHERE nom_pro_medi = '" + nomProd + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                nombre = rs.getString("provee_labo_pro_medi");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nombre;
    }

    public int getIdTrabajador(String nombre) {
        int id = 0;
        String sql = "SELECT `id_usu` FROM tusuario WHERE `nom_usu` = '" + nombre + "'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                id = rs.getInt("id_usu");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public int getFraccion(int idProd) {
        int fraccion = 0;
        try {
            String sql = "SELECT `fraccion_pro_medi` FROM tproducto_medicamento WHERE `id_pro_medi` = '" + idProd + "'";
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                if (rs.next()) {
                    fraccion = rs.getInt("fraccion_pro_medi");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
        }
        return fraccion;
    }

}
