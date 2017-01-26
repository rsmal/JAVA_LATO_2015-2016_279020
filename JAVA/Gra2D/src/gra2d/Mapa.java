/// TWORZENIE MAPY /// 
package gra2d;


import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;



public class Mapa {
    public static final int KAFELKA_WYSOKOSC = 24;
    public static final int KAFELKA_SZEROKOSC = 24;
    public static BufferedImage gwiazdka;
    public static BufferedImage serce;
    
    private ElementMapy[][] mapa;
    private Gracz gracz;
    private List<Potwor> potwory;
    private boolean[][] gwiazdki;
    private int liczbaGwiazdek;
    
    private int aktualnaMapa;
    
    public Mapa(int aktualnaMapa){
        try {
            gwiazdka = ImageIO.read(new File("obrazy/grafiki/gwiazdka.png"));
            serce = ImageIO.read(new File("obrazy/grafiki/serce.png"));
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
        
        liczbaGwiazdek = 0;
        this.aktualnaMapa = aktualnaMapa;
        ElementMapy.wczytajObrazy("mapy/mapa"+this.aktualnaMapa);
        Potwor.wczytajObraz();
        Gracz.wczytajObraz();
        if(aktualnaMapa == 1){
            wczytajZPliku("mapy/mapa"+this.aktualnaMapa,true);
        }else{
            wczytajZPliku("mapy/mapa"+this.aktualnaMapa,false);
        }
    }
    
    private boolean wczytajNastepnaMape(boolean czyStart){
        this.aktualnaMapa = this.aktualnaMapa + 1;
        return wczytajZPliku("mapy/mapa"+(aktualnaMapa),czyStart);
    }
    
    private boolean wczytajZPliku(String sciezka,boolean czyStart){
        try {
            //aktualnaMapa = sciezka;
            Scanner scan = new Scanner(new File(sciezka+"/mapa.txt"));
            
            for (int i = 0; i < gwiazdki.length; i++) {
                for (int j = 0; j < gwiazdki[i].length; j++) {
                    gwiazdki[i][j] = false;
                }
            }
            liczbaGwiazdek = 0;
            potwory = new LinkedList();
            
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
                            liczbaGwiazdek++;
                            break;
                        case 'X':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.PUSTE);
                            if(czyStart){
                                gracz = new Gracz(i,j);
                            }else{
                                gracz.setPozycja(i, j);
                            }
                            break;
                        case '@':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.PUSTE);
                            gwiazdki[i][j] = true;
                            liczbaGwiazdek++;
                            Potwor p = new Potwor(i,j);
                            potwory.add(p);
                            break;
                        default:
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.SCIANA);
                    }
                }
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    
    // pozycja startowa //
    private void wczytajZPlikuPozycjeStartowe(String sciezka){
        try {
            Scanner scan = new Scanner(new File(sciezka+"/mapa.txt"));
            potwory = new LinkedList();
            
            for(int i = 0 ; i < mapa.length ; i++){
                String linia = scan.nextLine();
                for(int j = 0 ; j < mapa[i].length ; j++){
                    switch(linia.charAt(j)){
                        case '*':

                            break;
                        case '.':
                            break;
                        case 'X':
                            gracz.setPozycja(i, j);
                            break;
                        case '@':
                            Potwor p = new Potwor(i,j);
                            potwory.add(p);
                            break;
                        default:
                            
                    }
                }
            }
            scan.close();
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
        for(int i = 0 ; i < gracz.getZycia() ; i++){
            graphics.drawImage(serce, i*KAFELKA_SZEROKOSC, 0,null);
        }
        for(Potwor p : potwory){
            p.rysuj(graphics);
        }
        gracz.rysuj(graphics);
        graphics.drawString("Liczba gwiazdek do zebrania "+String.valueOf(liczbaGwiazdek), 500+10+KAFELKA_SZEROKOSC*gracz.getZycia(), 15);
        graphics.drawString("Liczba punktow "+String.valueOf(gracz.getPunkty()), 10+KAFELKA_SZEROKOSC*gracz.getZycia(), 15);
        
    }
    
    public boolean aktualizuj(long czas){
        for(Potwor p : potwory){
            p.aktualizuj(czas, mapa);
        }
        gracz.aktualizuj(czas, mapa,gwiazdki,this);
        
        
        
        if(liczbaGwiazdek == 0){
            return wczytajNastepnaMape(false);
        }
        
        
        // kolizja //
        for(Potwor potwor : potwory){
   
            if(Math.abs(potwor.getWspolrzednaX()+KAFELKA_WYSOKOSC/2 - gracz.getWspolrzednaX()-KAFELKA_WYSOKOSC/2) < KAFELKA_WYSOKOSC/2 ){
                if(Math.abs(potwor.getWspolrzednaY()+KAFELKA_SZEROKOSC/2-gracz.getWspolrzednaY() - KAFELKA_SZEROKOSC/2) < KAFELKA_SZEROKOSC/2){
                    gracz.ustawZycia(gracz.getZycia()-1);
                    if(gracz.getZycia() < 1){
                        //zakonczenie gry
                        return false;
                    }else{
                       
                        wczytajZPlikuPozycjeStartowe("mapy/mapa"+aktualnaMapa);
                    }
                }
            }
        }
        return true;
    }
    
    public void zmniejszGwiazdki(){
        liczbaGwiazdek--;
    }
    
    public void graczSetCzyMaChodzic(boolean b){
        gracz.setCzyMaChodzic(b);
    }
    
    public void graczSetKierunek(kierunekPoruszania kierunek){
        gracz.setKierunek(kierunek);
    }
    
    public int getPunkty(){
        return gracz.getPunkty();
    }
}
