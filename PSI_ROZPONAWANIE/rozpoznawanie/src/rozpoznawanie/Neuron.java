package rozpoznawanie;
import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    
        public String name = null;
	
	private ArrayList<Double> weights;
	private int size;
	
	public Neuron(int n) {
		size = n;
		
		weights = new ArrayList<Double>(n);
		
		clearWeights(); //ustawienie wag na 0.0
		initRandomWeights();//nadanie mały, losowych wag
	}
	
	public Neuron(ArrayList<Double> w) {
		weights = w;
	}
	
        //ustawianie wag na 0.0
	public void clearWeights() {
		weights.clear();
		
		for (int i = 0; i < size; i++)
			weights.add(0.0);
	}
	
        //nadanie losowych wag + normalizacja
	public void initRandomWeights() {
		Random rand = new Random();
		
		for (int i = 0; i < weights.size(); i++)
			weights.set(i, rand.nextDouble());
		
		normalize();
	}
	
	public double calculateNet(ArrayList<Integer> input) throws IllegalArgumentException {
		if (input.size() != weights.size())
			throw new IllegalArgumentException("Zła liczba wejść");
		
		double net = 0.0;
		
		for (int i = 0; i < input.size(); i++)
			net += input.get(i) * weights.get(i);
		
		return net;
	}
	
        //normalizacja
	public void normalize() {
		double lenght = 0.0;
		
		for (int i = 0; i < weights.size(); i++)
			lenght += Math.pow(weights.get(i), 2);
		
		lenght = Math.sqrt(lenght);
		
		for (int i = 0; i < weights.size(); i++)
			weights.set(i, weights.get(i) / lenght);
	}
	
	public void learn(ArrayList<Integer> input) throws IllegalArgumentException {
		learn(input, 0.5);
	}
	
	public void learn(ArrayList<Integer> input, double alpha) throws IllegalArgumentException {
		if (input.size() != weights.size())
			throw new IllegalArgumentException("Zła liczba wejść");
		
		for (int i = 0; i < weights.size(); i++) {
			double delta = alpha * (input.get(i) - weights.get(i));
			
			weights.set(i, weights.get(i) + delta);
		}
		
		normalize();
	}
	
	public String dump() {
		String dump = "";
		
		for (double weight : weights)
			dump += weight + "\n";
		
		dump += name + "\n";
		
		return dump;
	}
        
        //wypisanie wag
        public void wypisz() {
            for (int i = 0; i < weights.size(); i++) {
                System.out.println(i + ": " + weights.get(i));
            }
        }
    
}
