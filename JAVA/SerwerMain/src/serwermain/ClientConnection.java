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
            String password = "ALA MA CHOMIKA";
            String test = (String) input.readObject();
            if(!password.equals(test)){
                System.out.println("Klient id="+id+" AUTHORIZATION FAILED " +  "Data: "+new Date().toString());
                throw new Exception("AUTHORIZATION FAILED");
            }
            System.out.println("Klient id="+id+" AUTHORIZATION SUCCESS " +  "Data: "+new Date().toString());
            while(uruchomiony){
                String message = (String) input.readObject();
                
                System.out.println("Klient id="+id+"  wiadomosc: " + message + "   Data: "+new Date().toString());
                if(message.equals("koniec")){
                    break;
                }else if(message.equals("wyslij na serwer")){
                    //output.writeObject("Nazwa uzytkownika");
                    String haslo = (String) input.readObject();
                    if (haslo.equals("1234567890")) {
                        output.writeObject("OK");
                        System.out.println("Klient id="+id+"  Autoryzacja OK   Data: "+new Date().toString());
                        String nazwaUzytkownika = (String) input.readObject();
                        //output.writeObject("Liczba punktow");
                        int punkty = (int) input.readObject();
                        serwer.dodajStatystyke(nazwaUzytkownika, punkty);
                    }else{
                        output.writeObject("Bledne haslo!");
                        System.out.println("Klient id="+id+"  Autoryzacja ERROR   Data: "+new Date().toString());
                    }
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
