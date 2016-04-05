
package gra2d;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


/// ELEMENTY MAPY ///
public class ElementMapy {
    private static BufferedImage sciana;
    private static BufferedImage puste;

    public enum elementy {
        SCIANA, PUSTE
    }

    private elementy element;

    public ElementMapy(elementy element) {
        this.element = element;
    }
    
    public static void wczytajObrazy(String katalog){
        try {
            File plik = new File(katalog + "/sciana.png");
            sciana = ImageIO.read(plik);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            File plik = new File(katalog + "/puste.png");
            puste = ImageIO.read(plik);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public elementy getElement() {
        return element;
    }

    public void setElement(elementy element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return element.toString();
    }
    
    public void rysuj(Graphics2D graphics,int x,int y){
        if(element == elementy.SCIANA){
            graphics.drawImage(sciana, x, y, null);
        }else{
            graphics.drawImage(puste, x, y, null);
        }
    }
}
