package serwermain;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class ClientConnection extends Thread{
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private int id;
    private Serwer serwer;
    
    public ClientConnection(Socket socket,int id,Serwer serwer){
        try{
            this.socket = socket;
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.input = new ObjectInputStream(socket.getInputStream());
            this.id = id;
            this.serwer = serwer;
            
        }catch(IOException ex){
            //ex.printStackTrace();
        }
        
        System.out.println("Polaczono z klientem id="+id+"  Data: " + new Date().toString());
    }
    
    @Override
    public void run(){
        boolean uruchomiony = true;
        
        try{
            while(uruchomiony){
                String message = (String) input.readObject();
                
                System.out.println("Klient id="+id+"  wiadomosc: " + message + "   Data: "+new Date().toString());
                if(message.equals("koniec")){
                    break;
                }else if(message.equals("wyslij na serwer")){
                    //output.writeObject("Nazwa uzytkownika");
                    String nazwaUzytkownika = (String) input.readObject();
                    //output.writeObject("Liczba punktow");
                    int punkty = (int) input.readObject();
                    serwer.dodajStatystyke(nazwaUzytkownika, punkty);
                }else if(message.equals("pobierz statystyki")){
                    output.writeObject(serwer.pobierzStatystyki());
                }
            }
        }catch(Exception ex){
            
        }finally{
            System.out.println("Rozlaczono z klientem id=" +id );
        }
    }
    
    public void close(){
        try{
            socket.close();
            input.close();
            output.close();
        }catch(Exception ex){
            
        }
    }
}
