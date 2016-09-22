package Control;

import Vista.Almacen;
import Vista.AlmacenDAO;
import Vista.Almacenes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class AlmacenControl{
    AlmacenDAO aDAO = new AlmacenDAO();
    DefaultTableModel dtm = new DefaultTableModel();

    public AlmacenControl() {
    }
    
    public boolean RegistrarAlmacen(String nombre, String direccion, String ciudad, String telefono, String responsable){
        try {
            Almacen a = new Almacen();
            a.setNombre(nombre);
            a.setDireccion(direccion);
            a.setCiudad(ciudad);
            a.setTelefono(telefono);
            a.setResponsable(responsable);
            AlmacenDAO adao = new AlmacenDAO();
            if (adao.Registrar(a)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public void limpiarTabla(JTable tbl) {
        for (int i = 0; i < tbl.getRowCount(); i++) {
            dtm.removeRow(i);
            i -= 1;
        }
    }
    
    public void LlenarTabla(JTable tbl) throws SQLException{
        
        tbl.setModel(dtm);
        
        dtm.addColumn("COD");
        dtm.addColumn("NOMBRE");
        dtm.addColumn("DIRECCION");
        dtm.addColumn("CIUDAD");
        dtm.addColumn("TELEFONO");
        dtm.addColumn("RESPONSABLE");
        
        Object[] columna = new Object[6];
        
        int numFilas = aDAO.listar().size();
        
        for (int i = 0; i < numFilas; i++) {
            columna[0] = aDAO.listar().get(i).getId();
            columna[1] = aDAO.listar().get(i).getNombre();
            columna[2] = aDAO.listar().get(i).getDireccion();
            columna[3] = aDAO.listar().get(i).getCiudad();
            columna[4] = aDAO.listar().get(i).getTelefono();
            columna[5] = aDAO.listar().get(i).getResponsable();
            dtm.addRow(columna);
        }
    }
    
    public Object CargarAlmacenSeleccionado(int id) throws Exception {
        Object[] datos = new Object[6];
        AlmacenDAO adao = new AlmacenDAO();
        for (Almacen a : adao.listar()) {
            if (a.getId() == id) {
                datos[0] = a.getId();
                datos[1] = a.getNombre();
                datos[2] = a.getDireccion();
                datos[3] = a.getCiudad();
                datos[4] = a.getTelefono();
                datos[5] = a.getResponsable();
            }
        }
        return datos;
    }
    
    
    
}
