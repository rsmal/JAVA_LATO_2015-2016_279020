package serwermain;
import java.util.Scanner;

public class SerwerMain {

   
    public static void main(String[] args) {
        try{
            Scanner scan = new Scanner(System.in);
            Serwer s = new Serwer(1166);
            s.start();
            while(true){
                String input = scan.next();
                if(input.startsWith("exit")){
                    s.close();
                    break;
                }else if(input.startsWith("cleanTable")){
                    s.czyscTabele();
                }
            }
        }catch(Exception ex){
            
        }
    }
    
}
