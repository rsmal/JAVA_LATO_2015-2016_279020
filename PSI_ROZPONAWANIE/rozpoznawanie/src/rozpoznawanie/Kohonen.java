package rozpoznawanie;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rozpoznawanie.DigitizeCfg;
import rozpoznawanie.Pair;

public class Kohonen {
    
        private HashMap<String, ArrayList<ArrayList<Integer>>> inputs;
	private ArrayList<Neuron> neurons;
	
	public Kohonen(HashMap<String, ArrayList<ArrayList<Integer>>> i) {
		inputs = i;
		
		createNetwork(inputs.size());
	}
	
	public Kohonen(ArrayList<Pair<String, ArrayList<Double>>> dump) {
		neurons = new ArrayList<Neuron>();
		
		for (Pair<String, ArrayList<Double>> character : dump) {
			Neuron neuron = new Neuron(character.getRight());
			neuron.name = character.getLeft();
			
			neurons.add(neuron);
		}
	}
	
	public void createNetwork(int n) {
		neurons = new ArrayList<Neuron>();
		
		for (int i = 0; i < n; i++)
			neurons.add(new Neuron(DigitizeCfg.TOTAL));
	}
	
	public Neuron findWinner(ArrayList<Integer> input) {
		Neuron win = null;
		
		for (Neuron neuron : neurons)
			if (neuron.name == null && (win == null || neuron.calculateNet(input) > win.calculateNet(input)))
				win = neuron;
		
		return win;
	}
	
	public void learn() {
		for (Map.Entry<String, ArrayList<ArrayList<Integer>>> character : inputs.entrySet()) {
			String name = character.getKey();
			ArrayList<ArrayList<Integer>> charInputs = character.getValue();
			
			ArrayList<Integer> first = charInputs.get(0);
			charInputs.remove(0);
			
			Neuron winner = findWinner(first);
			winner.name = name;
			
			for (ArrayList<Integer> in : charInputs)
				winner.learn(in);
		}
	}
	
	public String recognize(ArrayList<Integer> input) {
		Neuron winner = null;
		
		for (Neuron neuron : neurons)
			if (winner == null || neuron.calculateNet(input) > winner.calculateNet(input))
				winner = neuron;
		
		return winner.name;
	}
	
	public void save(BufferedWriter out) throws IOException {
		out.write(DigitizeCfg.COLUMN + "\n" + DigitizeCfg.ROW + "\n" + neurons.size() + "\n");
		
		for (Neuron neuron : neurons)
			out.write(neuron.dump());
	}
    
}
