
package clientmain;

public class ClientMain {

    
    public static void main(String[] args) {
        Client c = new Client("localhost",1166);
        c.start();
    }
    
}
