
package gra2d;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gra2D extends Gra2DJadro implements ActionListener,KeyListener{

    public static void main(String[] args) {
        new Gra2D().run();
    }

    
    
    enum stanyGry{
        GLOWNE, NOWAGRA, WCZYTAJ, STATYSTYKI, AUTORZY, WYJSCIE, NIEZNANY,KONIEC_GRY
    }
    
    private BufferedImage glowna;
    private BufferedImage nowaGra;
    private BufferedImage wczytaj;
    private BufferedImage statystyki;
    private BufferedImage autorzy;
    private BufferedImage wyjscie;
    
    
    private JButton przyciskNowaGra;
    private JButton przyciskWczytaj;
    private JButton przyciskStatystyki;
    private JButton przyciskAutorzy;
    private JButton przyciskWyjscie;
    private JPanel nowaGraSpace;
    private JPanel wczytajSpace;
    private JPanel statystykiSpace;
    private JPanel autorzySpace;
    private JPanel wyjscieSpace;
    
    private JButton przyciskWroc;
    private JPanel wrocSpace;
    
    private JButton przyciskZapis1;
    private JPanel zapis1Space;
    
    private JButton przyciskZapis2;
    private JPanel zapis2Space;
    
    private JButton przyciskZapis3;
    private JPanel zapis3Space;
    
    private JButton przyciskWyslijNaSerwer;
    private JPanel wyslijNaSerwerspace;
    private JTextField nazwaGracza;
    private JTextField liczbaPunktow;
    
    private stanyGry stan;
    
    private Mapa mapa;
    
    @Override
    public void inicjalizuj(){
        super.inicjalizuj();
        NullRepaintManager.install(); // manager sluzacy do inicjalizowania funkcji na beczynnosc odnoszacych sie do ryswania.
    
        glowna = zaladujObraz("obrazy/tlo2.png");
        wczytaj = zaladujObraz("obrazy/tlo2.png");
        nowaGra = zaladujObraz("obrazy/tlo2.png");
        statystyki = zaladujObraz("obrazy/tlo2.png");
        autorzy = zaladujObraz("obrazy/autor.png");
        wyjscie = zaladujObraz("obrazy/gra_wyjscie.png");
       
        
        
        przyciskNowaGra = stworzPrzycisk("obrazy/nowa.png", "Nowa gra", 0.9f);
        przyciskNowaGra.setFocusable(true);
        przyciskWczytaj = stworzPrzycisk("obrazy/wczytaj.png", "Wczytaj", 0.9f);
        przyciskWczytaj.setFocusable(true);
        przyciskStatystyki = stworzPrzycisk("obrazy/statystyki.png", "Statystyki", 0.9f);
        przyciskStatystyki.setFocusable(true);
        przyciskAutorzy = stworzPrzycisk("obrazy/autorzy.png", "Autorzy", 0.9f);
        przyciskAutorzy.setFocusable(true);
        przyciskWyjscie = stworzPrzycisk("obrazy/wyjscie.png", "Wyjscie", 0.9f);
        przyciskWyjscie.setFocusable(true);
        przyciskWroc = stworzPrzycisk("obrazy/wroc.png", "Wyjscie", 0.9f);
        przyciskWroc.setFocusable(true);
        
        przyciskZapis1 = stworzPrzycisk("obrazy/zapis1.png", "Wyjscie", 0.9f);
        przyciskZapis1.setFocusable(true);
        
        przyciskZapis2 = stworzPrzycisk("obrazy/zapis2.png", "Wyjscie", 0.9f);
        przyciskZapis2.setFocusable(true);
        
        przyciskZapis3 = stworzPrzycisk("obrazy/zapis3.png", "Wyjscie", 0.9f);
        przyciskZapis3.setFocusable(true);
        
        przyciskWyslijNaSerwer = stworzPrzycisk("obrazy/autorzy.png","Wyslij  na  serwer" , 0.9f);
        przyciskWyslijNaSerwer.setFocusable(true);
        
        nowaGraSpace = new JPanel();
        nowaGraSpace.setOpaque(false);
        nowaGraSpace.add(przyciskNowaGra);
        wczytajSpace = new JPanel();
        wczytajSpace.setOpaque(false);
        wczytajSpace.add(przyciskWczytaj);
        statystykiSpace = new JPanel();
        statystykiSpace.setOpaque(false);
        statystykiSpace.add(przyciskStatystyki);
        autorzySpace = new JPanel();
        autorzySpace.setOpaque(false);
        autorzySpace.add(przyciskAutorzy);
        wyjscieSpace = new JPanel();
        wyjscieSpace.setOpaque(false);
        wyjscieSpace.add(przyciskWyjscie);
        wrocSpace = new JPanel();
        wrocSpace.setOpaque(false);
        wrocSpace.add(przyciskWroc);
        
        zapis1Space = new JPanel();
        zapis1Space.setOpaque(false);
        zapis1Space.add(przyciskZapis1);
        
        zapis2Space = new JPanel();
        zapis2Space.setOpaque(false);
        zapis2Space.add(przyciskZapis2);
        
        zapis3Space = new JPanel();
        zapis3Space.setOpaque(false);
        zapis3Space.add(przyciskZapis3);
        
        
        wyslijNaSerwerspace = new JPanel ();
        wyslijNaSerwerspace.setOpaque(false);
        wyslijNaSerwerspace.add(przyciskWyslijNaSerwer);
     
        Container contentPane = ramkaGlowna.getContentPane();

        //sprawdzenie czy panel jest przeźroczysty
        if (contentPane instanceof JComponent) {
            ((JComponent) contentPane).setOpaque(false);
        }
        
        nowaGraSpace.setVisible(true);
        nowaGraSpace.setSize(przyciskNowaGra.getPreferredSize());
        nowaGraSpace.setLocation(320, 50+65);
        wczytajSpace.setVisible(true);
        wczytajSpace.setSize(przyciskWczytaj.getPreferredSize());
        wczytajSpace.setLocation(320, 50+65+50+30);
        statystykiSpace.setVisible(true);
        statystykiSpace.setSize(przyciskWczytaj.getPreferredSize());
        statystykiSpace.setLocation(320, 50+65+50+30+50+30);
        autorzySpace.setVisible(true);
        autorzySpace.setSize(przyciskAutorzy.getPreferredSize());
        autorzySpace.setLocation(320, 50+65+50+30+50+30+50+30);
        wyjscieSpace.setVisible(true);
        wyjscieSpace.setSize(przyciskWyjscie.getPreferredSize());
        wyjscieSpace.setLocation(320, 35+75+35+75+35+75+35+75+30);
        wrocSpace.setVisible(true);
        wrocSpace.setSize(przyciskWroc.getPreferredSize());
        wrocSpace.setLocation(340, 35+75+35+75+35+75+35+75+10);
        zapis1Space.setVisible(true);
        zapis1Space.setSize(przyciskZapis1.getPreferredSize());
        zapis1Space.setLocation(310, 35+75+10);
        
        zapis2Space.setVisible(true);
        zapis2Space.setSize(przyciskZapis2.getPreferredSize());
        zapis2Space.setLocation(310, 35+75+20+75);
        
        zapis3Space.setVisible(true);
        zapis3Space.setSize(przyciskZapis3.getPreferredSize());
        zapis3Space.setLocation(310, 25+75+20+75+20+75);
        
        wyslijNaSerwerspace.setVisible(true);
        wyslijNaSerwerspace.setSize(przyciskWyslijNaSerwer.getPreferredSize());
        wyslijNaSerwerspace.setLocation(320, 50+65+50+30+50+30+50+30);
        
        
        nazwaGracza = new JTextField();
        nazwaGracza.setBounds(310, 250, 200, 70);
        nazwaGracza.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza.setHorizontalAlignment(JTextField.CENTER);
        //nazwaGracza.setEditable(false);
        nazwaGracza.setVisible(false);
        
        liczbaPunktow = new JTextField();
        liczbaPunktow.setBounds(310, 100, 200, 70);
        liczbaPunktow.setFont(new Font("Serif",Font.PLAIN,28));
        liczbaPunktow.setHorizontalAlignment(JTextField.CENTER);
        liczbaPunktow.setEditable(false);
        liczbaPunktow.setVisible(false);

        
        
        ramkaGlowna.getLayeredPane().add(nowaGraSpace,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(wczytajSpace,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(statystykiSpace,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(autorzySpace,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(wyjscieSpace,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(wrocSpace,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(zapis1Space,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(zapis2Space,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(zapis3Space,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(wyslijNaSerwerspace,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(nazwaGracza,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(liczbaPunktow,JLayeredPane.MODAL_LAYER);
        
        przyciskNowaGra.setVisible(true);
        przyciskWczytaj.setVisible(true);
        przyciskStatystyki.setVisible(true);
        przyciskAutorzy.setVisible(true);
        przyciskWyjscie.setVisible(true);
        przyciskWroc.setVisible(false);
        przyciskZapis1.setVisible(false);
        przyciskZapis2.setVisible(false);
        przyciskZapis3.setVisible(false);
        przyciskWyslijNaSerwer.setVisible(false);
        
        stan = stanyGry.GLOWNE;
        
        ramkaGlowna.addKeyListener(this);
    }

    @Override
    public void aktualizuj(long czas) {
        switch (stan) {
            case GLOWNE:
                break;
            case NOWAGRA:
                if(mapa.aktualizuj(czas) == false){
                    stan = stanyGry.KONIEC_GRY;
                    liczbaPunktow.setVisible(true);
                    liczbaPunktow.setText(String.valueOf(mapa.getPunkty()));
                    nazwaGracza.setVisible(true);
                    przyciskWyslijNaSerwer.setVisible(true);
                }
                break;
            case WCZYTAJ:
                break;
            case AUTORZY:
                break;
            case STATYSTYKI:
                break;
            case WYJSCIE:
                break;
            case KONIEC_GRY:
                break;
            default:
                break;
        }
    }

    @Override
    public void rysuj(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0-ramkaGlowna.getInsets().left, 0 - ramkaGlowna.getInsets().top, 2000, 2000);
        
        switch(stan){
            case GLOWNE:
                graphics.drawImage(glowna, -ramkaGlowna.getInsets().left, -ramkaGlowna.getInsets().top, null);
                break;
            case NOWAGRA:
                graphics.drawImage(nowaGra, -ramkaGlowna.getInsets().left, -ramkaGlowna.getInsets().top, null);
                mapa.rysuj(graphics);
                break;
            case WCZYTAJ:
                graphics.drawImage(wczytaj, -ramkaGlowna.getInsets().left, -ramkaGlowna.getInsets().top, null);
                break;
            case AUTORZY:
                graphics.drawImage(autorzy, -ramkaGlowna.getInsets().left, -ramkaGlowna.getInsets().top, null);
                break;
            case STATYSTYKI:
                graphics.drawImage(statystyki, -ramkaGlowna.getInsets().left, -ramkaGlowna.getInsets().top, null);
                break;
            case WYJSCIE:
                graphics.drawImage(wyjscie, -ramkaGlowna.getInsets().left, -ramkaGlowna.getInsets().top, null);
                break;
            case KONIEC_GRY:
                graphics.drawImage(statystyki, -ramkaGlowna.getInsets().left, -ramkaGlowna.getInsets().top, null);
                break;
            default:
                break;
        }
        
        ramkaGlowna.getLayeredPane().paintComponents(graphics); // obrysowanie przycisków
    }
    
    private JButton stworzPrzycisk(String sciezka, String toolTip, float przezroczystosc){
        ImageIcon iconRollover = new ImageIcon(sciezka);
        int w = iconRollover.getIconWidth();
        int h = iconRollover.getIconHeight();
        
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
        
        Image image = stworzKompatybilnyObraz(w,h,Transparency.TRANSLUCENT);
        
        Graphics2D g = (Graphics2D) image.getGraphics();
        
        Composite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, przezroczystosc);
        
        g.setComposite(alpha);
        g.drawImage(iconRollover.getImage(), 0, 0, null);
        g.dispose();
        
        ImageIcon iconDefault = new ImageIcon(image);
        
        image = stworzKompatybilnyObraz(w, h, Transparency.TRANSLUCENT);
        g = (Graphics2D) image.getGraphics();
        g.setComposite(alpha);
        g.drawImage(iconRollover.getImage(), 3, 3, null);
        g.dispose();
        
        ImageIcon iconPressed = new ImageIcon(image);
        
        JButton button = new JButton();
        button.addActionListener(this);
        button.setIgnoreRepaint(true);
        button.setFocusable(false);
        button.setToolTipText(toolTip);
        button.setBorder(null);
        
        button.setContentAreaFilled(false);
        button.setCursor(cursor);
        button.setIcon(iconDefault);
        button.setRolloverIcon(iconRollover);
        button.setPressedIcon(iconPressed);
        
        return button;
    }
    
    private Image stworzKompatybilnyObraz(int w,int h,int przezroczystosc){
        if(ramkaGlowna != null){
            GraphicsConfiguration gc = ramkaGlowna.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, przezroczystosc);
        }else{
            return null;
        }
    }
    
    private static BufferedImage zaladujObraz(String sciezka){
        File pllik = new File(sciezka);
        BufferedImage obraz = null;
        try{
            obraz = ImageIO.read(pllik);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return obraz;
    }
    
    private void pokazPrzyciskiMenu(boolean b){
        przyciskNowaGra.setVisible(b);
        przyciskWczytaj.setVisible(b);
        przyciskStatystyki.setVisible(b);
        przyciskAutorzy.setVisible(b);
        przyciskWyjscie.setVisible(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == przyciskNowaGra){
            mapa = new Mapa(3);
            pokazPrzyciskiMenu(false);
            stan = stanyGry.NOWAGRA;
            przyciskWroc.setVisible(false);
            ramkaGlowna.requestFocus();
        }else if(e.getSource() == przyciskWczytaj){
            pokazPrzyciskiMenu(false);
            stan = stanyGry.WCZYTAJ;
            przyciskZapis1.setVisible(true);
            przyciskZapis2.setVisible(true);
            przyciskZapis3.setVisible(true);
            przyciskWroc.setVisible(true);
            ramkaGlowna.requestFocus();
        }else if(e.getSource() == przyciskStatystyki){
            pokazPrzyciskiMenu(false);
            stan = stanyGry.STATYSTYKI;
            przyciskWroc.setVisible(true);
            ramkaGlowna.requestFocus();
        }else if(e.getSource() == przyciskAutorzy){
            pokazPrzyciskiMenu(false);
            stan = stanyGry.AUTORZY;
            przyciskWroc.setVisible(true);
            ramkaGlowna.requestFocus();
        }else if(e.getSource() == przyciskWyjscie){
            pokazPrzyciskiMenu(false);
            stan = stanyGry.WYJSCIE;
            stop();
            ramkaGlowna.requestFocus();
        }else if(e.getSource() == przyciskWroc){
            pokazPrzyciskiMenu(true);
            stan = stanyGry.GLOWNE;
            przyciskWroc.setVisible(false);
            przyciskZapis1.setVisible(false);
            przyciskZapis2.setVisible(false);
            przyciskZapis3.setVisible(false);
            ramkaGlowna.requestFocus();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            mapa.graczSetKierunek(kierunekPoruszania.LEWO);
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            mapa.graczSetKierunek(kierunekPoruszania.PRAWO);
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            mapa.graczSetKierunek(kierunekPoruszania.GORA);
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            mapa.graczSetKierunek(kierunekPoruszania.DOL);
        }
        mapa.graczSetCzyMaChodzic(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
