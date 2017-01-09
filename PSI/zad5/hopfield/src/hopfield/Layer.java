package hopfield;

public class Layer {
    // tablica neuronow
    protected Neuron neuron[] = new Neuron[4];

    // wyjscie na neuronach
    protected boolean output[] = new boolean[4];

    // ilosc neuronow w warstwie, a w naszym przypadkow tez w sieci (mamy tylko 1 warstwe w sieci)
    protected int neurons;

    // stała do mnożenia przez górną granice funkcji (nie korzystamy z tego, wiec ==1)
    public static final double lambda = 1.0;

    // wielkosc tablicy--> w naszym przypadku 4x4
    // zawiera wagi pomiędzy każdymi neuronami
    Layer(int weights[][])
    {
        neurons = weights[0].length;

        neuron = new Neuron[neurons];
        output = new boolean[neurons];

        for ( int i=0;i<neurons;i++ )
            neuron[i]=new Neuron(weights[i]);
    }

    // metoda threshold sprawdza czy siec będzie podatna na podany wzorzec
    public boolean threshold(int k)
    {
        double kk = k * lambda;
        double a = Math.exp( kk );
        double b = Math.exp( -kk );
        double tanh = (a-b)/(a+b);
        return(tanh>=0);
    }

    // aktywacja neuronów  i uruchomienie naszej funkcji
    void activation(boolean pattern[])
    {
        int i,j;
        for ( i=0;i<4;i++ ) {
            neuron[i].activation = neuron[i].act(pattern);
            output[i] = threshold(neuron[i].activation);
        }
    }
}
