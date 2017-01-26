
package gra2d;


public class Statystyka implements Comparable<Statystyka>{
    String nazwa;
    int punkty;

    public Statystyka(String nazwa, int punkty) {
        this.nazwa = nazwa;
        this.punkty = punkty;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getPunkty() {
        return punkty;
    }

    public void setPunkty(int punkty) {
        this.punkty = punkty;
    }

    @Override
    public int compareTo(Statystyka o) {
       if(this.punkty > o.punkty){
           return -1;
       }else if(this.punkty == o.punkty){
           return 0;
       }else{
           return 1;
       }
    }
    
    
}
