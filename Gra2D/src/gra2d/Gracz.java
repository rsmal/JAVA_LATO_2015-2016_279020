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
    private static final double PREDKOSC = 0.70;
    private int x;
    private int y;
    private static BufferedImage obraz;
    private long czas;
    private double xRuch;
    private double yRuch;
    private kierunekPoruszania kierunek;
    private kierunekPoruszania kierunekZamawiany;
    private boolean czyMaChodzic;
    
    public Gracz(int x, int y) {
        this.x = x;
        this.y = y;
        czas = 0 ;
        xRuch = 0;
        yRuch = 0;
        kierunek = kierunekPoruszania.LEWO;
        kierunekZamawiany = kierunekZamawiany;
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
    
    public void aktualizuj(long czas,ElementMapy[][] mapa){
        this.czas += czas;
        if (this.czas >= 10) {
            this.czas = this.czas - 10;
            if (czyMaChodzic) {
                //kod do sprawdzenia czy mozna wykonac zamawiany kierunek
                boolean czyZamawianyPossible = false;
                if(kierunekZamawiany == kierunekPoruszania.LEWO){
                    if(xRuch == 0 && mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                        czyZamawianyPossible = true;
                    }
                }else if(kierunekZamawiany == kierunekPoruszania.PRAWO){
                    if(xRuch == 0 && mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                        czyZamawianyPossible = true;
                    }
                }else if(kierunekZamawiany == kierunekPoruszania.GORA){
                    if(yRuch == 0 && mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                        czyZamawianyPossible = true;
                    }
                }else if(kierunekZamawiany == kierunekPoruszania.DOL){
                    if(yRuch == 0 && mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                        czyZamawianyPossible = true;
                    }
                }
                //jezeli mozna to kierunek = kierunekZamawiany
                if(czyZamawianyPossible){
                    kierunek = kierunekZamawiany;
                }
                
                if(kierunek == kierunekPoruszania.LEWO){
                    if(mapa[x][y-1].getElement() == ElementMapy.elementy.SCIANA){
                        return;
                    }
                }else if(kierunek == kierunekPoruszania.PRAWO){
                    if(mapa[x][y+1].getElement() == ElementMapy.elementy.SCIANA){
                        return;
                    }
                }else if(kierunek == kierunekPoruszania.GORA){
                    if(mapa[x-1][y].getElement() == ElementMapy.elementy.SCIANA){
                        return;
                    }
                }else if(kierunek == kierunekPoruszania.DOL){
                    if(mapa[x+1][y].getElement() == ElementMapy.elementy.SCIANA){
                        return;
                    }
                }
                
                if(kierunek == kierunekPoruszania.NIEZNANY){
                    kierunek = kierunekPoruszania.GORA;
                }else{
                    switch (kierunek) {
                        case LEWO:
                            yRuch -= PREDKOSC;
                            break;
                        case PRAWO:
                            yRuch += PREDKOSC;
                            break;
                        case GORA:
                            xRuch -= PREDKOSC;
                            break;
                        case DOL:
                            xRuch += PREDKOSC;
                            break;
                        default:
                    }
                    
                    if (yRuch >= 24) {
                        y++;
                        yRuch = 0;
                       // czyMaChodzic = false;
                    } else if (yRuch <= -24) {
                        y--;
                        yRuch = 0;
                        //czyMaChodzic = false;
                    } else if (xRuch >= 24) {
                        x++;
                        xRuch = 0;
                       // czyMaChodzic = false;
                    } else if (xRuch <= -24) {
                        x--;
                        xRuch = 0;
                       // czyMaChodzic = false;
                    }
                }
                
                
            }
        }
    }
    
    public void setCzyMaChodzic(boolean b){
        czyMaChodzic = b;
    }
    
    public void setKierunek(kierunekPoruszania kierunek){
        this.kierunekZamawiany = kierunek;
//        if(kierunek == kierunekPoruszania.NIEZNANY){
//            this.kierunek = kierunekPoruszania.GORA;
//        }
//        
//        if(kierunek == kierunekPoruszania.LEWO){
//            if(xRuch != 0){
//                return;
//            }else{
//                this.kierunek = kierunek;
//            }
//        }else if(kierunek == kierunekPoruszania.PRAWO){
//            if(xRuch != 0){
//                return;
//            }else{
//                this.kierunek = kierunek;
//            }
//        }else if(kierunek == kierunekPoruszania.GORA){
//            if(yRuch != 0){
//                return;
//            }else{
//                this.kierunek = kierunek;
//            }
//        }else if(kierunek == kierunekPoruszania.DOL){
//            if(yRuch != 0){
//                return;
//            }else{
//                this.kierunek = kierunek;
//            }
//        }
        
        //this.kierunek = kierunek;
    }
}
