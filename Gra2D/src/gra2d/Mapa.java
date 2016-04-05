/// TWORZENIE MAPY /// 
package gra2d;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Mapa {
    private ElementMapy[][] mapa;
    
    public Mapa(String sciezka){
        mapa = new ElementMapy[25][31];
        
        wczytajZPliku(sciezka);
    }
    
    private void wczytajZPliku(String sciezka){
        try {
            Scanner scan = new Scanner(new File(sciezka));
            
            for(int i = 0 ; i < 25 ; i++){
                String linia = scan.nextLine();
                for(int j = 0 ; j < linia.length() ; j++){
                    switch(linia.charAt(j)){
                        case '*':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.SCIANA);
                            break;
                        case '.':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.GWIAZDKI);
                            break;
                        case 'X':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.GRACZ);
                            break;
                        case '@':
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.POTWORY);
                            break;
                        default:
                            mapa[i][j] = new ElementMapy(ElementMapy.elementy.PUSTE);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        for(int i = 0 ; i < 25 ; i++){
            for(int j = 0 ; j < 31; j++){
                System.out.print(mapa[i][j]);
            }
            System.out.println("");
        }
    }
}
