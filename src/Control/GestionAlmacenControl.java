package Control;

import Modelo.Almacen;
import Modelo.AlmacenDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GestionAlmacenControl {

    Connection cc = new Conexion().conectar();
    DefaultTableModel model;

    public GestionAlmacenControl() {
    }

    public void cargarComboAlmacenes(JComboBox cmb) throws Exception {
        AlmacenDAO adao = new AlmacenDAO();
        for (Almacen a : adao.listar()) {
            cmb.addItem(a.getNombre());
        }
    }

    public void titulosTablaProductos(JTable tbl) {
        String titulos[] = {"ID", "NOMBRE", "ALMACEN", "STOCK"};
        model = new DefaultTableModel(null, titulos);
        tbl.setModel(model);
    }

    public void buscarProductos(JTable tbl, String prod) throws Exception {
        titulosTablaProductos(tbl);
        limpiarTabla(tbl);
        String datos[] = new String[4];
        String sql = "select tproducto_medicamento.id_pro_medi, tproducto_medicamento.nom_pro_medi, almacen.nombre, almacenproducto.cantidad\n"
                + "from tproducto_medicamento\n"
                + "inner join almacenproducto on tproducto_medicamento.id_pro_medi = almacenproducto.idproducto\n"
                + "inner join almacen on almacenproducto.idalmacen = almacen.idalmacen\n"
                + "where tproducto_medicamento.nom_pro_medi LIKE '" + prod + "%' OR nom_pro_medi LIKE '%" + prod + "'";
        try {
            Statement st = cc.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id_pro_medi"));
                datos[1] = rs.getString("nom_pro_medi");
                datos[2] = rs.getString("nombre");
                datos[3] = rs.getString("cantidad");
                model.addRow(datos);
            }
            tbl.setModel(model);
            //tbl_productos.setModel(new DefaultTableModel());
        } catch (Exception e) {
            throw e;
        }
    }

    public void limpiarTabla(JTable tbl) {
        for (int i = 0; i < tbl.getRowCount(); i++) {
            model.removeRow(i);
            i -= 1;
        }
    }

    public int getIdAlmacen(Almacen al) throws SQLException {
        AlmacenDAO adao = new AlmacenDAO();
        for (Almacen a : adao.listar()) {
            if (a.getNombre().equals(al.getNombre())) {
                return a.getId();
            }
        }
        return 0;
    }

    //comprobar si existe el almacen en la tabla
//    public boolean existeProductoEnAlmacen(AlmacenProducto aprod) throws Exception {
//        try {
//            AlmacenProductoDAO apdao = new AlmacenProductoDAO();
//            for (AlmacenProducto ap : apdao.listar()) {
//                if (ap.getIdAlmacen()==aprod.getIdAlmacen() && ap.getIdProducto() == aprod.getIdProducto()) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//        return false;
//    }

//    private void restarStock() throws Exception {
//        int numFilas = tbl.getRowCount();
//        try {
//            for (int i = 0; i < numFilas; i++) {                
//                int cantidad = Integer.parseInt(tbl.getValueAt(i, 3).toString());
//                System.out.println("cantidad: " + cantidad);
//                int stock = new Farma_inf().getStock(id);
//                System.out.println("stock: " + stock);
//                int newStock = stock - cantidad;
//                System.out.println("stock: " + newStock);
//
//                String sql = "UPDATE `tproducto_medicamento` SET `stock_pro_medi`=" + newStock + " WHERE `id_pro_medi` = " + id + " ";
//                Statement st = cc.createStatement();
//                int rs = st.executeUpdate(sql);
//                if (rs > 0) {
//                    System.out.println("Se actualizo en stock del producto :" + id);
//                }
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }
}
