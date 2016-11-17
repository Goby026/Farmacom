
package Control;

import Modelo.Caja;
import Modelo.CajaDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marce
 */
public class FlujoCajaControl {
    public double getSaldoInicial(String fecha) throws SQLException{
        try {            
            CajaDAO cdao = new CajaDAO();
            for (Caja c : cdao.listar()) {
                if (c.getFecha().equals(fecha)) {
                    return c.getMontoApertura();
                }
            }
            return 0.0;
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public String getEstadoCaja(String fecha) throws Exception{
        try {
            CajaDAO cdao = new CajaDAO();
            for (Caja c : cdao.listar()) {
                if (c.getFecha().equals(fecha)) {
                    if (c.getMontoCierre()==0) {
                        return "CERRADA";
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return "ABIERTA";
    }
}
