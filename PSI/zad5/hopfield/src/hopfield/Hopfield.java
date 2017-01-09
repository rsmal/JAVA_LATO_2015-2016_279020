package hopfield;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Hopfield extends JFrame implements ActionListener{
    
    // liczba neuronow w sieci
    public static final int NETWORK_SIZE = 4;

    // wagi dla 4 połączonych neuronów (daje nam to 4x4)
    JTextField weightMatrix[][] =
            new JTextField[NETWORK_SIZE][NETWORK_SIZE];

    // wzorecz wejsciowy jaki będzie użyty do trenowania sieci
    JComboBox input[] = new JComboBox[NETWORK_SIZE];

    // wyjscie dla calej sieci
    JTextField output[] = new JTextField[NETWORK_SIZE];

// BUTTONY
    JButton btnClear = new JButton("Wyczysc");

    JButton btnTrain = new JButton("Trenuj");

    JButton btnRun = new JButton("Uruchom");

    // tworzenie jframe wraz z komponentami
    public Hopfield()
    {
        setTitle("Hopfield");

        JPanel connections = new JPanel();
        connections.setLayout(
                new GridLayout(NETWORK_SIZE,NETWORK_SIZE) );
        for ( int row=0;row<NETWORK_SIZE;row++ ) {
            for ( int col=0;col<NETWORK_SIZE;col++ ) {
                weightMatrix[row][col] = new JTextField(3);
                weightMatrix[row][col].setText("0");
                connections.add(weightMatrix[row][col]);
            }
        }

        Container content = getContentPane();

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        content.setLayout(gridbag);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 1.0;

        // wagi macierzy wizualizacja
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.NORTHWEST;
        content.add(
                new JLabel("Wagi neuronow dla sieci Hopfielda:"),c);

        // wagi macierzy
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        content.add(connections,c);
        c.gridwidth = 1;

        // wejsciowy wzorzec wizualizacja
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        content.add(
                new JLabel("Kliknij \"Trenuj\" aby trenowac zgodnie z biezacym wzorcem:"),c);

        // wejsciowy wzorzec

        String options[] = { "0","1"};

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        for ( int i=0;i<NETWORK_SIZE;i++ ) {
            input[i] = new JComboBox(options);
            inputPanel.add(input[i]);

        }

        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        c.anchor = GridBagConstraints.CENTER;
        content.add(inputPanel,c);

        // wyjsciowy wzorzec wizualizacja
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        content.add(
                new JLabel("Kliknij \"Uruchom\" aby zobaczyc wyjsciowy wzorzec:"),c);

        // wyjsciowy wzorzec

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout());
        for ( int i=0;i<NETWORK_SIZE;i++ ) {
            output[i] = new JTextField(3);
            output[i].setEditable(false);
            outputPanel.add(output[i]);
        }
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        c.anchor = GridBagConstraints.CENTER;
        content.add(outputPanel,c);

        // buttony

        JPanel buttonPanel = new JPanel();
        btnClear = new JButton("Wyczysc");
        btnTrain = new JButton("Trenuj");
        btnRun = new JButton("Uruchom");
        btnClear.addActionListener(this);
        btnTrain.addActionListener(this);
        btnRun.addActionListener(this);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(btnClear);
        buttonPanel.add(btnTrain);
        buttonPanel.add(btnRun);
        content.add(buttonPanel,c);

        pack();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();
        setLocation(
                (int)(d.width-this.getSize().getWidth())/2,
                (int)(d.height-this.getSize().getHeight())/2 );
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);


    }

    // obsluga buttonow
    public void actionPerformed(ActionEvent e)
    {
        if ( e.getSource()==btnRun )
            run();
        else if ( e.getSource()==btnClear )
            clear();
        else if ( e.getSource()==btnTrain )
            train();
    }

    // wywolywana metoda, gdy siec biegnie w strone przeciwną jak wejście
    protected void run()
    {
        boolean pattern[] = new boolean[NETWORK_SIZE];
        int wt[][] = new int[NETWORK_SIZE][NETWORK_SIZE];

        for ( int row=0;row<NETWORK_SIZE;row++ )
            for ( int col=0;col<NETWORK_SIZE;col++ )
                wt[row][col]=Integer.parseInt(weightMatrix[row][col].getText());
        for ( int row=0;row<NETWORK_SIZE;row++ ) {
            int i = input[row].getSelectedIndex();
            if ( i==0 )
                pattern[row] = false;
            else
                pattern[row] = true;
        }

        Layer net = new Layer(wt);
        net.activation(pattern);

        for ( int row=0;row<NETWORK_SIZE;row++ ) {
            if ( net.output[row] )
                output[row].setText("1");
            else
                output[row].setText("0");
            if ( net.output[row]==pattern[row] )
                output[row].setBackground(java.awt.Color.green);
            else
                output[row].setBackground(java.awt.Color.red);
        }

    }


    protected void clear()
    {
        for ( int row=0;row<NETWORK_SIZE;row++ )
            for ( int col=0;col<NETWORK_SIZE;col++ )
                weightMatrix[row][col].setText("0");
    }

    // wywolanie funkcji trenującej
    protected void train()
    {
        int work[][] = new int[NETWORK_SIZE][NETWORK_SIZE];
        int bi[] = new int[NETWORK_SIZE];

        for ( int x=0;x<NETWORK_SIZE;x++ ) {
            if ( input[x].getSelectedIndex()==0 )
                bi[x] = -1;
            else
                bi[x] = 1;
        }

        for ( int row=0;row<NETWORK_SIZE;row++ )
            for ( int col=0;col<NETWORK_SIZE;col++ ) {
                work[row][col] = bi[row]*bi[col];
            }

        for ( int x=0;x<NETWORK_SIZE;x++ )
            work[x][x] -=1;

        for ( int row=0;row<NETWORK_SIZE;row++ )
            for ( int col=0;col<NETWORK_SIZE;col++ ) {
                int i = Integer.parseInt(weightMatrix[row][col].getText());
                weightMatrix[row][col].setText( "" + (i+work[row][col]));
            }

    }

    public static void main(String[] args) {
        JFrame f = new Hopfield();
        f.show();
    }
    
}
