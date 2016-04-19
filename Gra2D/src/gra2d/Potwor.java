package gra2d;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

enum kierunekPoruszania{
    LEWO, PRAWO, DOL, GORA, NIEZNANY
}

public class Potwor {
    private static final double PREDKOSC = 0.40;
    private static BufferedImage obraz;
    private int x;
    private int y;
    private long czas;
    private double xRuch;
    private double yRuch;
    private kierunekPoruszania kierunek;

    public Potwor(int x, int y) {
        this.x = x;
        this.y = y;
        czas = 0 ;
        xRuch = 0;
        yRuch = 0;
        kierunek = kierunekPoruszania.NIEZNANY;
    }
    
    public static void wczytajObraz(){
        try {
            obraz = ImageIO.read(new File("obrazy/grafiki/potwor_1.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void rysuj(Graphics2D graphics){
        graphics.drawImage(obraz, (int) (y * Mapa.KAFELKA_SZEROKOSC + yRuch), (int) (x * Mapa.KAFELKA_WYSOKOSC + xRuch), null);
    }
    
    public void aktualizuj(long czas, ElementMapy[][] mapa){
        this.czas += czas;
        if (this.czas >= 10) {
            this.czas = this.czas - 10;

            if (kierunek == kierunekPoruszania.NIEZNANY) {
                Random losowa = new Random();
                
                int numer = losowa.nextInt(4);
                
                switch(numer){
                    case 0:
                        if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else{
                            kierunek = kierunekPoruszania.NIEZNANY;
                        }
                        break;
                    case 1:
                        if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else{
                            kierunek = kierunekPoruszania.NIEZNANY;
                        }
                        break;
                    case 2:
                        if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else{
                            kierunek = kierunekPoruszania.NIEZNANY;
                        }
                        break;
                    case 3:
                        if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else{
                            kierunek = kierunekPoruszania.NIEZNANY;
                        }
                        break;
                    default:
                        
                }
            } else {
                switch(kierunek){
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
                
                Random random = new Random();
                
                if(yRuch >= 24){
                    y++;
                    yRuch = 0;
                    
                    int pomoc = random.nextInt(100);
                    
                    if(pomoc > 70){
                        if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else{
                            kierunek = kierunekPoruszania.LEWO;
                        }
                    }else if(pomoc > 30){
                        if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else{
                            kierunek = kierunekPoruszania.LEWO;
                        }
                    }else{
                        if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else{
                            kierunek = kierunekPoruszania.LEWO;
                        }
                    }
                }else if(yRuch <= -24){
                    y--;
                    yRuch = 0;
                    
                    int pomoc = random.nextInt(100);
                    
                    if(pomoc > 70){
                        if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else{
                            kierunek = kierunekPoruszania.PRAWO;
                        }
                    }else if(pomoc > 30){
                        if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else{
                            kierunek = kierunekPoruszania.PRAWO;
                        }
                    }else{
                        if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else{
                            kierunek = kierunekPoruszania.PRAWO;
                        }
                    }
                }else if(xRuch >= 24){
                    x++;
                    xRuch = 0;
                    
                    int pomoc = random.nextInt(100);
                    
                    if(pomoc > 70){
                        if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else{
                            kierunek = kierunekPoruszania.GORA;
                        }
                    }else if(pomoc > 30){
                        if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else{
                            kierunek = kierunekPoruszania.GORA;
                        }
                    }else{
                        if(mapa[x+1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.DOL;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else{
                            kierunek = kierunekPoruszania.GORA;
                        }
                    }
                }else if(xRuch <= -24){
                    x--;
                    xRuch = 0;
                    
                    int pomoc = random.nextInt(100);
                    
                    if(pomoc > 70){
                        if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else{
                            kierunek = kierunekPoruszania.DOL;
                        }
                    }else if(pomoc > 30){
                        if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else{
                            kierunek = kierunekPoruszania.DOL;
                        }
                    }else{
                        if(mapa[x-1][y].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.GORA;
                        }else if(mapa[x][y+1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.PRAWO;
                        }else if(mapa[x][y-1].getElement() == ElementMapy.elementy.PUSTE){
                            kierunek = kierunekPoruszania.LEWO;
                        }else{
                            kierunek = kierunekPoruszania.DOL;
                        }
                    }
                }
            }
        }
    }
    public int getWspolrzednaX(){
        return (int) (x * Mapa.KAFELKA_WYSOKOSC + xRuch);
    }
    
    public int getWspolrzednaY(){
        return (int) (y * Mapa.KAFELKA_SZEROKOSC + yRuch);
    }
}
