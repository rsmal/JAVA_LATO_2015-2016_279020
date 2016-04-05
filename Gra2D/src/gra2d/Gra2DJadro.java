package gra2d;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;


public abstract class Gra2DJadro implements Runnable{
    public static final int SZEROKOSC_GRY = 800;
    public static final int WYSOKOSC_GRY = 600;
    private static final int BUFORY = 2;
    private boolean czyUruchomiony;
    protected JFrame ramkaGlowna;
  
    
    @Override
    public void run() {
        try{
            inicjalizuj();
            petlaGlowna();
            System.exit(0);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void inicjalizuj(){
        czyUruchomiony = true;
        ramkaGlowna = new JFrame("Gra2D");
        
        //ustawienia ramki
        ramkaGlowna.setVisible(true);
        int ekranSzerokosc = Toolkit.getDefaultToolkit().getScreenSize().width;
        int ekranWysokosc = Toolkit.getDefaultToolkit().getScreenSize().height;
        // UStawienie ramki na środku ekranu
        ramkaGlowna.setBounds(ekranSzerokosc/2 - SZEROKOSC_GRY/2, ekranWysokosc/2-WYSOKOSC_GRY/2, SZEROKOSC_GRY, WYSOKOSC_GRY);
        // UStawienia przycisków ramki
        ramkaGlowna.setIgnoreRepaint(true);
        ramkaGlowna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ramkaGlowna.setResizable(false);
        
     
        
        //tworzenie buforow dla ramki
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    ramkaGlowna.createBufferStrategy(BUFORY);
                }
            });
        }
        catch (InterruptedException e) {
            // ignorowane
        }
        catch (InvocationTargetException  e) {
            // ignorowane
        }
    }
    
    public void petlaGlowna(){
        long czasStartu  = System.currentTimeMillis();
        long czasBierzacy = czasStartu;
        
        while(czyUruchomiony){
            long uplywCzasu = System.currentTimeMillis() - czasBierzacy;
            czasBierzacy += uplywCzasu;
            
            aktualizuj(uplywCzasu);
            
            Graphics2D g = pobierzPowierzchnie();
            rysuj(g);
            g.dispose();
            rysujOkno();
        }
    }
    
    
    public void stop(){
        Graphics2D g = pobierzPowierzchnie();
        rysuj(g);
        g.dispose();
        rysujOkno();
    // Sleep do przycisku wyjdz // 
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Gra2DJadro.class.getName()).log(Level.SEVERE, null, ex);
        }
        czyUruchomiony = false;
    }
    
    public abstract void aktualizuj(long czas);
    public abstract void rysuj(Graphics2D graphics);

    
    private Graphics2D pobierzPowierzchnie() {
        BufferStrategy strategia = ramkaGlowna.getBufferStrategy();
        Graphics2D powierzchnia = null;
        
        int i = 0;
        //petla po to aby przy zmianie rozmiaru okna graphics2D nie było null
        while(powierzchnia == null && i < 1000000){
            try {
                i++;
                powierzchnia = (Graphics2D) strategia.getDrawGraphics();
            } catch (Exception e) {
                //do nothing
            }
        }
        Insets inset = ramkaGlowna.getInsets();
        powierzchnia.translate(inset.left, inset.top);
        
        return powierzchnia;
    }

    private void rysujOkno() {
        BufferStrategy strategia = ramkaGlowna.getBufferStrategy();
        if(!strategia.contentsLost()){
            strategia.show();
        }
        // Operacja Sync, dzialajaca w niektorych systemach
        // (w systemie Linux naprawia problem z kolejka zdarzen).
        Toolkit.getDefaultToolkit().sync();
    }

   
    

    
}
