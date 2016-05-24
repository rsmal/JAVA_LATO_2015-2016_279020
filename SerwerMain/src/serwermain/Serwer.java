package serwermain;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Serwer extends Thread{
    private int numerPortu;
    private List<ClientConnection> connections;
    private boolean uruchomiony;
    private static int uniqueID = 0;
    
    public Serwer(int numerPortu){
        this.numerPortu = numerPortu;
        this.connections = new LinkedList();
    }
    
    @Override
    public void run(){
        uruchomiony = true;
        ServerSocket serwer = null;
        try{
            serwer = new ServerSocket(numerPortu);
            System.out.println("Serwer jest uruchomiony na porcie " + numerPortu);
            
            while(uruchomiony){
                Socket socket = serwer.accept();
                
                if(!uruchomiony){
                    break;
                }
                
                uniqueID++;
                ClientConnection connection = new ClientConnection(socket,uniqueID,this);
                //System.out.println("Polaczono z klientem");
                connections.add(connection);
                connection.start();
            }
            
            serwer.close();
            for(ClientConnection con : connections){
                con.close();
            }
        }catch(IOException ex){
            System.out.println("Blad utworzenia gniazdka");
        }finally{
            System.out.println("Serwer wylaczony");
            if(serwer != null){
                try {
                    serwer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    public void close(){
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            
        }
        uruchomiony = false;
        try{
            new Socket("localhost",numerPortu);
        }catch(IOException ex){
            //ex.printStackTrace();
            //System.out.println("Blad falszywego gniazdka");
        }
    }
    
    
    
     /// baza danych ///
    public synchronized String pobierzStatystyki(){
        Connection c = null;
        Statement stat = null;
        String wynik = "";
        
        try {
            Class.forName("org.sqlite.JDBC");
            
            c = DriverManager.getConnection("jdbc:sqlite:statystyki.db");
            
            stat = c.createStatement();
            
            String comenda = "SELECT * FROM STATYSTYKI";
            
            ResultSet result = stat.executeQuery(comenda);
            
            while(result.next()){
                wynik += result.getString("UZYTKOWNIK");
                wynik += "\n";
                wynik += result.getInt("PUNKTY");
                wynik += "\n";
            }
            stat.close();
            c.close();
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return wynik;
    }
    
    public synchronized void dodajStatystyke(String nazwa,int punkty){
        Connection c = null;
        Statement stat = null;
        
        try {
            Class.forName("org.sqlite.JDBC");
            
            c = DriverManager.getConnection("jdbc:sqlite:statystyki.db");
            
            stat = c.createStatement();
            
            String comenda;
            /*comenda = "DROP TABLE STATYSTYKI";
            try{
                stat.executeUpdate(comenda);
            }catch(Exception ex){
                
            }*/
            try{
                comenda = "CREATE TABLE STATYSTYKI("
                        + "UZYTKOWNIK TEXT,"
                        + "PUNKTY INTEGER"
                        + ");";

                stat.executeUpdate(comenda);
            }catch(Exception ex){
                //ex.printStackTrace();
            }
            
            comenda = "INSERT INTO STATYSTYKI VALUES ("
                    + "'"+ nazwa + "',"
                    + punkty
                    + ");";
            
            stat.executeUpdate(comenda);
            
            
            try{
                comenda = "CREATE TABLE LOGG("
                        + "UZYTKOWNIK TEXT,"
                        + "DATA TEXT"
                        + ");";

                stat.executeUpdate(comenda);
            }catch(Exception ex){
                //ex.printStackTrace();
            }
            
            comenda = "INSERT INTO LOGG VALUES ("
                    + "'"+ nazwa + "',"
                    + "'"+ new Date() +"'"
                    + ");";
            
            stat.executeUpdate(comenda);
            
            
            stat.close();
            c.close();
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public synchronized void drukujLoggi(){
        Connection c = null;
        Statement stat = null;
        
        try {
            Class.forName("org.sqlite.JDBC");
            
            c = DriverManager.getConnection("jdbc:sqlite:statystyki.db");
            
            stat = c.createStatement();
            
            String comenda = "SELECT * FROM LOGG";
            
            ResultSet result = stat.executeQuery(comenda);
            
            while(result.next()){
                System.out.println(result.getString("UZYTKOWNIK") + " !!!!! "+result.getString("DATA"));
            }
            stat.close();
            c.close();
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public synchronized void czyscTabele() {
        Connection c = null;
        Statement stat = null;
        
        try {
            Class.forName("org.sqlite.JDBC");
            
            c = DriverManager.getConnection("jdbc:sqlite:statystyki.db");
            
            stat = c.createStatement();
            
            String comenda;
            comenda = "DROP TABLE STATYSTYKI";
            try{
                stat.executeUpdate(comenda);
            }catch(Exception ex){
                
            }
            try{
                comenda = "CREATE TABLE STATYSTYKI("
                        + "UZYTKOWNIK TEXT,"
                        + "PUNKTY INTEGER"
                        + ");";

                stat.executeUpdate(comenda);
            }catch(Exception ex){
                //ex.printStackTrace();
            }

            comenda = "DROP TABLE LOGG";
            try{
                stat.executeUpdate(comenda);
            }catch(Exception ex){
                
            }
            try{
                comenda = "CREATE TABLE LOGG("
                        + "UZYTKOWNIK TEXT,"
                        + "DATA TEXT"
                        + ");";

                stat.executeUpdate(comenda);
            }catch(Exception ex){
                //ex.printStackTrace();
            }
            
            stat.close();
            c.close();
            System.out.println("Tabela wyczyszczona");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
