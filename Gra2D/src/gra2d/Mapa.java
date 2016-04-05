/// TWORZENIE MAPY /// 
package gra2d;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;



public class Mapa {
    public static final int KAFELKA_WYSOKOSC = 24;
    public static final int KAFELKA_SZEROKOSC = 24;
    public static BufferedImage gwiazdka;
    
    private ElementMapy[][] mapa;
    private Gracz gracz;
    private List<Potwor> potwory;
    private boolean[][] gwiazdki;
    
    public Mapa(String katalog){
        try {
            gwiazdka = ImageIO.read(new File("obrazy/grafiki/gwiazdka.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mapa = new ElementMapy[24][33];
        potwory = new LinkedList();
        gwiazdki = new boolean[24][33];
        
        for(int i = 0 ; i < gwiazdki.length; i++){
            for(int j = 0 ; j < gwiazdki[i].length ; j++){
                gwiazdki[i][j] = false;
            }
        }
        
        ElementMapy.wczytajObrazy(katalog);
        Potwor.wczytajObraz();
        Gracz.wczytajObraz();
        wczytajZPliku(katalog);
    }
    
    private void wczytajZPliku(String sciezka){
        try {
            Scanner scan = new Scanner(new File(sciezka+"/mapa.txt"));
            
            for(int i = 0 ; i < mapa.length ; i++){
                String linia = scan.nextLine();
                for(int j = 0 ; j < mapa[i].length ; j++){
                    switch(linia.charAt(j)){
                        case '*':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.SCIANA);
                            break;
                        case '.':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.PUSTE);
                            gwiazdki[i][j] = true;
                            break;
                        case 'X':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.PUSTE);
                            gracz = new Gracz(i,j);
                            break;
                        case '@':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.PUSTE);
                            gwiazdki[i][j] = true;
                            Potwor p = new Potwor(i,j);
                            potwory.add(p);
                            break;
                        default:
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.SCIANA);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void rysuj(Graphics2D graphics){
        for(int i = 0 ; i < mapa.length ; i++){
            for(int j = 0 ; j < mapa[i].length ; j++){
                mapa[i][j].rysuj(graphics, j * KAFELKA_SZEROKOSC, i * KAFELKA_WYSOKOSC);
            }
        }
        for(int i = 0 ; i < mapa.length ; i++){
            for(int j = 0 ; j < mapa[i].length ; j++){
                if(gwiazdki[i][j] == true){
                    graphics.drawImage(gwiazdka, j*KAFELKA_SZEROKOSC, i*KAFELKA_WYSOKOSC,null);
                }
            }
        }
        for(Potwor p : potwory){
            p.rysuj(graphics);
        }
        gracz.rysuj(graphics);
    }
    
    public void aktualizuj(long czas){
        for(Potwor p : potwory){
            p.aktualizuj(czas, mapa);
        }
        gracz.aktualizuj(czas);
    }
}
