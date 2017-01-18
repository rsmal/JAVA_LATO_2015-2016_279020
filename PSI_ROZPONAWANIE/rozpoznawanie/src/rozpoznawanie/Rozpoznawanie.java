/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rozpoznawanie;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import rozpoznawanie.DigitizeCfg;
import rozpoznawanie.Pair;
import rozpoznawanie.Kohonen;

public class Rozpoznawanie extends JFrame {
    
    private static final long serialVersionUID = 1L;
    private JPanel mainPanel;
    private JLabel infoLabel;
    private JTextField characterName;
    private Canvas canvas;
    private Thumbnail characterThumbnail;

    private HashMap<String, ArrayList<ArrayList<Integer>>> characters;
	
    Kohonen kohonen;    
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
				Rozpoznawanie frame = new Rozpoznawanie();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
    }
    
    public Rozpoznawanie() {
		super("Kohonen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 350);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPanel);
		
		characters = new HashMap<String, ArrayList<ArrayList<Integer>>>();
		
		characterThumbnail = new Thumbnail();
		mainPanel.add(characterThumbnail, BorderLayout.WEST);
		
		canvas = new Canvas();
		mainPanel.add(canvas);
		
		canvas.setThumbnail(characterThumbnail);
		
		JPanel panel = new JPanel();
		mainPanel.add(panel, BorderLayout.SOUTH);
		
		final JButton saveButton = new JButton("Zapisz");
		final JButton recognizeButton = new JButton("Rozpoznaj");
		final JButton addButton = new JButton("Dodaj");
		final JButton learnButton = new JButton("Naucz");
		
		final JButton loadButton = new JButton("Wczytaj");
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int ret = fileChooser.showOpenDialog(canvas);
				
				if(ret == JFileChooser.APPROVE_OPTION) {
					File openFile = fileChooser.getSelectedFile();
					
					try {
						Scanner scanner = new Scanner(openFile);
						
						int column = Integer.parseInt(scanner.nextLine());
						int row = Integer.parseInt(scanner.nextLine());
						int neuronsCount = Integer.parseInt(scanner.nextLine());
						
						DigitizeCfg.COLUMN = column;
						DigitizeCfg.ROW = row;
						
						characterThumbnail.repaint();
						
						ArrayList<Pair<String, ArrayList<Double>>> dump = new ArrayList<Pair<String,ArrayList<Double>>>();
						
						for (int i = 0; i < neuronsCount; i++) {
							ArrayList<Double> weights = new ArrayList<Double>();
							
							for (int j = 0; j < row*column; j++) {
								double weg = Double.parseDouble(scanner.nextLine());
								
								weights.add(weg);
							}
							
							String name = scanner.nextLine();
							
							Pair<String, ArrayList<Double>> pair = new Pair<String, ArrayList<Double>>(name, weights);
							
							dump.add(pair);
						}
						
						kohonen = new Kohonen(dump);
					} catch (IOException e) {
						 JOptionPane.showMessageDialog(canvas, "Nie można otworzyć");
						
						e.printStackTrace();
					}
				}
				
				recognizeButton.setEnabled(true);
				learnButton.setEnabled(false);
				addButton.setEnabled(false);
				saveButton.setEnabled(true);
				characterName.setText("");
				characterName.setEnabled(false);
			}
		});
		//loadButton.setToolTipText("Open configuration");
		panel.add(loadButton);
		
		saveButton.setEnabled(false);
		//saveButton.setToolTipText("Save configuration");
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				int ret = fileChooser.showSaveDialog(canvas);
				
				if(ret == JFileChooser.APPROVE_OPTION) {
					File saveFile = fileChooser.getSelectedFile();
					
					try {
						saveFile.createNewFile();
						String path = saveFile.getPath();
						
						FileWriter fileWrite = new FileWriter(path);
						BufferedWriter writer = new BufferedWriter(fileWrite);
						
						kohonen.save(writer);
						
						writer.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(canvas, "Nie można zapisać");
					}
				}
			}
		});
		panel.add(saveButton);
		
		panel.add(new JSeparator());
		
		recognizeButton.setEnabled(false);
		//recognizeButton.setToolTipText("Rozpoznaj znak");
		recognizeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Integer> input = canvas.getInput();
				canvas.findCharacter();
				
				String character = kohonen.recognize(input);
				
				infoLabel.setText("Rozpoznano " + character);
			}
			
		});
		panel.add(recognizeButton);
		
		JButton clearButton = new JButton("Wyczyść");
		clearButton.setToolTipText("Wyczyść");
		clearButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				infoLabel.setText("\n");
				
				canvas.clear();
			}
			
		});
		panel.add(clearButton);
		
		panel.add(new JSeparator());
		
		characterName = new JTextField();
		//characterName.setToolTipText("Type character name");
		characterName.setFont(new Font("Dialog", Font.PLAIN, 16));
		panel.add(characterName);
		characterName.setColumns(1);
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> input = canvas.getInput();
				canvas.findCharacter();
				
				String name = characterName.getText();
				
				if (!characters.containsKey(name))
					characters.put(name, new ArrayList<ArrayList<Integer>>());
				
				characters.get(name).add(input);
				
				infoLabel.setText("Dodano " + name);
			}
		});
		//addButton.setToolTipText("Add character");
		panel.add(addButton);
		
		learnButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				kohonen = new Kohonen(characters);
				
				kohonen.learn();
				
				recognizeButton.setEnabled(true);
				learnButton.setEnabled(false);
				addButton.setEnabled(false);
				saveButton.setEnabled(true);
				characterName.setText("");
				characterName.setEnabled(false);
				
				infoLabel.setText("Nauczono");
				canvas.clear();
			}
		});
		panel.add(learnButton);
		
		infoLabel = new JLabel("Click load button to load characters' data");
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainPanel.add(infoLabel, BorderLayout.NORTH);
	}
    
}
