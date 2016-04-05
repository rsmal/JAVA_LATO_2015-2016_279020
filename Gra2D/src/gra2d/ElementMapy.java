
package gra2d;


/// ELEMENTY MAPY ///
public class ElementMapy {

    public enum elementy {
        SCIANA, PUSTE, GWIAZDKI, POTWORY, GRACZ
    }

    private elementy element;

    public ElementMapy(elementy element) {
        this.element = element;
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
    
    
}
