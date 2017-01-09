package hopfield;

public class Neuron {
    // zawiera wagi pomiędzy każdymi neuronami
    public int weightv[];

    // wyniki aktywacji dla neuronu
    public int activation;

    // in---> waga wektoru
    //wagi pomiędzy neuronami są przekazywane w konstruktorze
    public Neuron(int in[])
    {
        weightv = in;
    }

    // ta metoda sprawdzi czy neuron zostanie aktywowany czy pozostanie obojętny
    public int act(boolean x[] )
    {
        int i;
        int a=0;

        for ( i=0;i<x.length;i++ )
            if ( x[i] )
                a+=weightv[i];
        return a;
    }
}
