/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gra2d;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Robo
 */
public class Gracz {
    private int x;
    private int y;
    private static BufferedImage obraz;
    private long czas;
    private double xRuch;
    private double yRuch;
    private kierunekPoruszania kierunek;
    private boolean czyMaChodzic;
    
    public Gracz(int x, int y) {
        this.x = x;
        this.y = y;
        czas = 0 ;
        xRuch = 0;
        yRuch = 0;
        kierunek = kierunekPoruszania.NIEZNANY;
        czyMaChodzic = false;
    }
    
    public static void wczytajObraz(){
        try {
            obraz = ImageIO.read(new File("obrazy/grafiki/pacman.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void rysuj(Graphics2D graphics){
        graphics.drawImage(obraz, (int) (y * Mapa.KAFELKA_SZEROKOSC + yRuch), (int) (x * Mapa.KAFELKA_WYSOKOSC + xRuch), null);
    }
    
    public void aktualizuj(long czas){
        
    }
    
    public void setCzyMaChodzic(boolean b){
        czyMaChodzic = b;
    }
    
    public void setKierunek(kierunekPoruszania kierunek){
        this.kierunek = kierunek;
    }
}
