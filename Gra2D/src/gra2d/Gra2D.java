
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
    
    private JLabel nazwaGracza1;
    private JLabel punktyGracza1;
    private JLabel nazwaGracza2;
    private JLabel punktyGracza2;
    private JLabel nazwaGracza3;
    private JLabel punktyGracza3;
    private JLabel nazwaGracza4;
    private JLabel punktyGracza4;
    private JLabel nazwaGracza5;
    private JLabel punktyGracza5;
    private JLabel nazwaGracza6;
    private JLabel punktyGracza6;
    private JLabel nazwaGracza7;
    private JLabel punktyGracza7;
    
    
    private stanyGry stan;
    
    private Mapa mapa;
    
    @Override
    public void inicjalizuj(){
        super.inicjalizuj();
        NullRepaintManager.install(); // manager sluzacy do inicjalizowania funkcji na beczynnosc odnoszacych sie do ryswania.
    
        glowna = zaladujObraz("obrazy/tlo2.png");
        wczytaj = zaladujObraz("obrazy/tlo2.png");
        nowaGra = zaladujObraz("obrazy/tlo2.png");
        statystyki = zaladujObraz("obrazy/statystyka.png");
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
        
        przyciskWyslijNaSerwer = stworzPrzycisk("obrazy/wyslij.png","Wyslij  na  serwer" , 0.9f);
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
        nowaGraSpace.setLocation(340, 100+65);
        
        wczytajSpace.setVisible(true);
        wczytajSpace.setSize(przyciskWczytaj.getPreferredSize());
        wczytajSpace.setLocation(320, 50+50+50+65+50+30);
        
        statystykiSpace.setVisible(true);
        statystykiSpace.setSize(przyciskStatystyki.getPreferredSize());
        statystykiSpace.setLocation(340, 50+50+65+50+30);
        autorzySpace.setVisible(true);
        autorzySpace.setSize(przyciskAutorzy.getPreferredSize());
        autorzySpace.setLocation(340, 50+65+50+30+50+30+50);
        wyjscieSpace.setVisible(true);
        wyjscieSpace.setSize(przyciskWyjscie.getPreferredSize());
        wyjscieSpace.setLocation(340, 35+75+35+75+35+75+35+75);
        
        
        
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
        wyslijNaSerwerspace.setLocation(165, 50+65+50+30+50+30+50+15);
        
        
        nazwaGracza = new JTextField();
        nazwaGracza.setBounds(150, 250, 200, 70);
        nazwaGracza.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza.setHorizontalAlignment(JTextField.CENTER);
        nazwaGracza.setVisible(false);
        
        liczbaPunktow = new JTextField();
        liczbaPunktow.setBounds(150, 150, 200, 70);
        liczbaPunktow.setFont(new Font("Serif",Font.PLAIN,28));
        liczbaPunktow.setHorizontalAlignment(JTextField.CENTER);
        liczbaPunktow.setEditable(false);
        liczbaPunktow.setVisible(false);

        nazwaGracza1 = new JLabel("Statystyki");
        nazwaGracza1.setBounds(150, 100, 200, 50);
        nazwaGracza1.setForeground(Color.yellow);
        nazwaGracza1.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza1.setVisible(false);
        nazwaGracza2 = new JLabel("Statystyki");
        nazwaGracza2.setBounds(150, 150, 200, 50);
        nazwaGracza2.setForeground(Color.yellow);
        nazwaGracza2.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza2.setVisible(false);
        nazwaGracza3 = new JLabel("Statystyki");
        nazwaGracza3.setBounds(150, 200, 200, 50);
        nazwaGracza3.setForeground(Color.yellow);
        nazwaGracza3.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza3.setVisible(false);
        nazwaGracza4 = new JLabel("Statystyki");
        nazwaGracza4.setBounds(150, 250, 200, 50);
        nazwaGracza4.setForeground(Color.yellow);
        nazwaGracza4.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza4.setVisible(false);
        nazwaGracza5 = new JLabel("Ala ma kota");
        nazwaGracza5.setBounds(150, 300, 200, 50);
        nazwaGracza5.setForeground(Color.yellow);
        nazwaGracza5.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza5.setVisible(false);
        nazwaGracza6 = new JLabel("Statystyki");
        nazwaGracza6.setBounds(150, 350, 200, 50);
        nazwaGracza6.setForeground(Color.yellow);
        nazwaGracza6.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza6.setVisible(false);
        nazwaGracza7 = new JLabel("Statystyki");
        nazwaGracza7.setBounds(150, 400, 200, 50);
        nazwaGracza7.setForeground(Color.yellow);
        nazwaGracza7.setFont(new Font("Serif",Font.PLAIN,28));
        nazwaGracza7.setVisible(false);
        
        punktyGracza1 = new JLabel("Statystyki");
        punktyGracza1.setBounds(300, 100, 200, 50);
        punktyGracza1.setForeground(Color.yellow);
        punktyGracza1.setFont(new Font("Serif",Font.PLAIN,28));
        punktyGracza1.setVisible(false);
        punktyGracza2 = new JLabel("Statystyki");
        punktyGracza2.setBounds(300, 150, 200, 50);
        punktyGracza2.setForeground(Color.yellow);
        punktyGracza2.setFont(new Font("Serif",Font.PLAIN,28));
        punktyGracza2.setVisible(false);
        punktyGracza3 = new JLabel("Statystyki");
        punktyGracza3.setBounds(300, 200, 200, 50);
        punktyGracza3.setForeground(Color.yellow);
        punktyGracza3.setFont(new Font("Serif",Font.PLAIN,28));
        punktyGracza3.setVisible(false);
        punktyGracza4 = new JLabel("Statystyki");
        punktyGracza4.setBounds(300, 250, 200, 50);
        punktyGracza4.setForeground(Color.yellow);
        punktyGracza4.setFont(new Font("Serif",Font.PLAIN,28));
        punktyGracza4.setVisible(false);
        punktyGracza5 = new JLabel("Statystyki");
        punktyGracza5.setBounds(300, 300, 200, 50);
        punktyGracza5.setForeground(Color.yellow);
        punktyGracza5.setFont(new Font("Serif",Font.PLAIN,28));
        punktyGracza5.setVisible(false);
        punktyGracza6 = new JLabel("Statystyki");
        punktyGracza6.setBounds(300, 350, 200, 50);
        punktyGracza6.setForeground(Color.yellow);
        punktyGracza6.setFont(new Font("Serif",Font.PLAIN,28));
        punktyGracza6.setVisible(false);
        punktyGracza7 = new JLabel("Statystyki");
        punktyGracza7.setBounds(300, 400, 200, 50);
        punktyGracza7.setForeground(Color.yellow);
        punktyGracza7.setFont(new Font("Serif",Font.PLAIN,28));
        punktyGracza7.setVisible(false);
                
        
        
        ramkaGlowna.getLayeredPane().add(nazwaGracza1,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(nazwaGracza2,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(nazwaGracza3,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(nazwaGracza4,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(nazwaGracza5,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(nazwaGracza6,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(nazwaGracza7,JLayeredPane.MODAL_LAYER);
        
        ramkaGlowna.getLayeredPane().add(punktyGracza1,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(punktyGracza2,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(punktyGracza3,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(punktyGracza4,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(punktyGracza5,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(punktyGracza6,JLayeredPane.MODAL_LAYER);
        ramkaGlowna.getLayeredPane().add(punktyGracza7,JLayeredPane.MODAL_LAYER);
        
        
        
        
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
        przyciskWczytaj.setVisible(false);
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
                    liczbaPunktow.setText("Punkty "+String.valueOf(mapa.getPunkty()));
                    nazwaGracza.setVisible(true);
                    przyciskWyslijNaSerwer.setVisible(true);
                    przyciskWroc.setVisible(true);
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
        przyciskWczytaj.setVisible(false);
        przyciskStatystyki.setVisible(b);
        przyciskAutorzy.setVisible(b);
        przyciskWyjscie.setVisible(b);
    }
    
    private void pokazStatystyki(boolean b)
    {
        nazwaGracza1.setVisible(b);
        nazwaGracza2.setVisible(b);
        nazwaGracza3.setVisible(b);
        nazwaGracza4.setVisible(b);
        nazwaGracza5.setVisible(b);
        nazwaGracza6.setVisible(b);
        nazwaGracza7.setVisible(b);
        punktyGracza1.setVisible(b);
        punktyGracza2.setVisible(b);
        punktyGracza3.setVisible(b);
        punktyGracza4.setVisible(b);
        punktyGracza5.setVisible(b);
        punktyGracza6.setVisible(b);
        punktyGracza7.setVisible(b);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == przyciskNowaGra){
            mapa = new Mapa(1);
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
            String stat = "";
            try {
                Socket socket = new Socket("localhost", 1166);

                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

                //output.writeObject("wyslij na serwer");

                output.writeObject("pobierz statystyki");
                stat = (String) input.readObject();
               
                //statystyki = (String) input.readObject();
                   
                output.writeObject("koniec");
                socket.close();
                input.close();
                output.close();
            } catch (Exception ex) {
                System.out.println("Blad polaczenia z serwerem");
                ex.printStackTrace();
            }
            
            System.out.println(stat);
            
            Scanner scan = new Scanner(stat);
            
            List<Statystyka> lista = new LinkedList();
            while(scan.hasNext()){
                lista.add(new Statystyka(scan.nextLine(),Integer.parseInt(scan.nextLine())));
            }
            
            Collections.sort(lista);
            
            nazwaGracza1.setText("Brak");
            punktyGracza1.setText("0");
            nazwaGracza2.setText("Brak");
            punktyGracza2.setText("0");
            nazwaGracza3.setText("Brak");
            punktyGracza3.setText("0");
            nazwaGracza4.setText("Brak");
            punktyGracza4.setText("0");
            nazwaGracza5.setText("Brak");
            punktyGracza5.setText("0");
            nazwaGracza6.setText("Brak");
            punktyGracza6.setText("0");
            nazwaGracza7.setText("Brak");
            punktyGracza7.setText("0");

            try{
                nazwaGracza1.setText(lista.get(0).getNazwa());
                punktyGracza1.setText(String.valueOf(lista.get(0).getPunkty()));
                nazwaGracza2.setText(lista.get(1).getNazwa());
                punktyGracza2.setText(String.valueOf(lista.get(1).getPunkty()));
                nazwaGracza3.setText(lista.get(2).getNazwa());
                punktyGracza3.setText(String.valueOf(lista.get(2).getPunkty()));
                nazwaGracza4.setText(lista.get(3).getNazwa());
                punktyGracza4.setText(String.valueOf(lista.get(3).getPunkty()));
                nazwaGracza5.setText(lista.get(4).getNazwa());
                punktyGracza5.setText(String.valueOf(lista.get(4).getPunkty()));
                nazwaGracza6.setText(lista.get(5).getNazwa());
                punktyGracza6.setText(String.valueOf(lista.get(5).getPunkty()));
                nazwaGracza7.setText(lista.get(6).getNazwa());
                punktyGracza7.setText(String.valueOf(lista.get(6).getPunkty()));
            }catch(Exception ex){
                
            }
            
            pokazPrzyciskiMenu(false);
            pokazStatystyki(true);
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
            pokazPrzyciskiMenu(false);
            pokazStatystyki(false);
            przyciskWroc.setVisible(false);
            stop();
            ramkaGlowna.requestFocus();
        }else if(e.getSource() == przyciskWroc){
            pokazPrzyciskiMenu(true);
            pokazStatystyki(false);
            stan = stanyGry.GLOWNE;
            przyciskWroc.setVisible(false);
            przyciskZapis1.setVisible(false);
            przyciskZapis2.setVisible(false);
            przyciskZapis3.setVisible(false);
            nazwaGracza.setVisible(false);
            liczbaPunktow.setVisible(false);
            przyciskWyslijNaSerwer.setVisible(false);
            ramkaGlowna.requestFocus();
        } else if (e.getSource() == przyciskWyslijNaSerwer) {
            String nazwa;
            if (nazwaGracza.getText().equals("")) {
                nazwa = "nieznany";
            } else {
                nazwa = nazwaGracza.getText();
            }
            int punkty = Integer.parseInt(liczbaPunktow.getText().split(" ")[1]);

            try {
                Socket socket = new Socket("localhost", 1166);

                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

                output.writeObject("wyslij na serwer");
                output.writeObject(nazwa);
                output.writeObject(punkty);

                //output.writeObject("pobierz statystyki");
                
               
                //statystyki = (String) input.readObject();
                   
                output.writeObject("koniec");
                socket.close();
                input.close();
                output.close();
            } catch (Exception ex) {
                System.out.println("Blad polaczenia z serwerem");
                ex.printStackTrace();
            }
            
            nazwaGracza.setVisible(false);
            liczbaPunktow.setVisible(false);
            przyciskWyslijNaSerwer.setVisible(false);
            przyciskWroc.setVisible(false);
            pokazPrzyciskiMenu(true);
            stan = stanyGry.GLOWNE;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                mapa.graczSetKierunek(kierunekPoruszania.LEWO);
                break;
            case KeyEvent.VK_RIGHT:
                mapa.graczSetKierunek(kierunekPoruszania.PRAWO);
                break;
            case KeyEvent.VK_UP:
                mapa.graczSetKierunek(kierunekPoruszania.GORA);
                break;
            case KeyEvent.VK_DOWN:
                mapa.graczSetKierunek(kierunekPoruszania.DOL);
                break;
            case KeyEvent.VK_ESCAPE:
                stan = stanyGry.GLOWNE;
                pokazPrzyciskiMenu(true);
                pokazStatystyki(false);
            default:
                break;
        }
        mapa.graczSetCzyMaChodzic(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
