
package Vista;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Marce
 */
public class CustomPanel extends JPanel {
    private URL url = getClass().getResource("/img/doc1.jpg");
    Image imagen = new ImageIcon(url).getImage();
    
    @Override
    public void paint(Graphics g){
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(),this);
        //this.setOpaque(false);
        super.paint(g);
    }
}
