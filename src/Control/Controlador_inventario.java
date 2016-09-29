
package Control;

import Vista.Kardex;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import farmacia.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Goby
 */
public class Controlador_inventario implements ActionListener, KeyListener {

    Kardex inv = new Kardex();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==inv.txtCodigo) {
            inv.txtProducto.requestFocus();
        }
    }
 
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
