package clientmain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client extends Thread{
    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    String serverAdress;
    int numerPortu;
    
    public Client(String serverAdress,int numerPortu){
        this.serverAdress = serverAdress;
        this.numerPortu = numerPortu;
    }
    
    @Override
    public void run(){
        try{
            socket = new Socket(serverAdress,numerPortu);
            
            System.out.println("Polaczono z serwerem");
            
            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            
            Scanner scan = new Scanner(System.in);
            
            while(true){
                //String message = scan.nextLine();
                //output.writeObject(message);
                output.writeObject("wyslij na serwer");
                output.writeObject("ala");
                output.writeObject(500);
                output.writeObject("wyslij na serwer");
                output.writeObject("Kasia");
                output.writeObject(25);
                output.writeObject("wyslij na serwer");
                output.writeObject("ANNA");
                output.writeObject(3);
                
                output.writeObject("pobierz statystyki");
                System.out.println(input.readObject());
                output.writeObject("koniec");
            }
            
        }catch(Exception ex){
            System.out.println("Blad polaczenia z serwerem");
        }finally{
            try {
                socket.close();
                input.close();
                output.close();
            } catch (IOException ex) {
            }
        }
    }
}
