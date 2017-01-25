package hebb;

public class Hebb {

    public static void main(String[] args) {
        int training[][] =new int[][]{{1,1,0,0,1},
                {1,1,0,0,1},
                {1,1,1,0,0},
                {1,1,0,0,1},
                {1,1,0,0,1},
                {1,1,1,0,0},
                {1,1,1,1,1}};
        Net net = new Net(5,training);
        net.training();
        net.testing(new int[]{1,1,0,0,0});
        net.testing(new int[]{0,0,1,0,0});
    }
    
}

class Net {

    double n=0.1;
    int training[][] =new int[5][];
    Neuron neurons[]=new Neuron[5];

    public Net(int noOfNeurons,int trainingdata[][]){
        neurons=new Neuron[noOfNeurons];
        for(int j=0;j<neurons.length;j++){
            neurons[j]=new Neuron();
        }
        training=trainingdata;
    }

    public void training(){
        for(int i=0;i<training.length;i++){
            int inputs[]= training[i];
            double output=getNeuralNetOutput(neurons,inputs);
            //Update all the neurons
            for(int j=0;j<neurons.length;j++){
                neurons[j].updateWeight(output*n*inputs[j]);
            }
        }
    }

    public double getNeuralNetOutput(Neuron[] neurons,int inputs[]){
        //get all the outputs and add them together
        double output=0;
        for(int j=0;j<neurons.length;j++){
            output+=neurons[j].getOutput(inputs[j]);
        }
        return output;
    }

    public void testing (int[] inputs){
        System.out.println(getNeuralNetOutput(neurons,inputs));
    }

    class Neuron{
        double w;

        public Neuron(){
            w=1;
        }

        public double getOutput(int x){
            return x*w;
        }

        public void updateWeight(double update){
            w+=update;
        }
    }
}
