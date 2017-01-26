// POBRANE Z INTERNETU ŻEBY PRZYCISKI NIE BYLY OBRYSOWYWANE BEZ MOJEJ WIEDZY ////


package gra2d;
import javax.swing.JComponent;
import javax.swing.RepaintManager;


public class NullRepaintManager extends RepaintManager{
    public static void install(){
        RepaintManager repaintManager = new NullRepaintManager();
        repaintManager.setDoubleBufferingEnabled(false);
        RepaintManager.setCurrentManager(repaintManager);
    }
    @Override
    public void addInvalidComponent(JComponent c) {
        //do nothing
    }

    @Override
    public void addDirtyRegion(JComponent c, int x, int y,int w, int h){
        //do nothing
    }

    @Override
    public void markCompletelyDirty(JComponent c) {
        //do nothing
    }

    @Override
    public void paintDirtyRegions() {
        //do nothing
    }
}
