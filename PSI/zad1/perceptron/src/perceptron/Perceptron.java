/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perceptron;

import java.math.*;
import java.text.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author Joseph
 */
public class Perceptron {
    
    static int MAX_ITER = 100;
    static double LEARNING_RATE = 0.1;
    static int NUM_INSTANCES = 100;
    static int theta = 0;
    
    public static void main(String[] args) {
        Random liczba = new Random();
        double[] x = new double [NUM_INSTANCES];
        double[] y = new double [NUM_INSTANCES];
        double[] z = new double [NUM_INSTANCES];
        int[] outputs = new int [NUM_INSTANCES];        
        
        // 50 rand punktow klasy 1
        for(int i = 0; i < NUM_INSTANCES/2; i++) {
            x[i] = liczba.nextInt(5) + 5;
            y[i] = liczba.nextInt(4) + 4;
            //z[i] = liczba.nextInt(7) + 2;
            z[i] = 0;
            outputs[i] = 1;
            System.out.println(x[i] + "\t" + y[i] + "\t" + z[i] + "\t" + outputs[i]);
        }
        
        // 50 rand punktow klasy 2
        for(int i = 50; i < NUM_INSTANCES; i++) {
            x[i] = liczba.nextInt(4) - 1;
            y[i] = liczba.nextInt(6) - 4;
            //z[i] = liczba.nextInt(8) - 3;
            z[i] = 0;
            outputs[i] = 0;
            System.out.println(x[i] + "\t" + y[i] + "\t" + z[i] + "\t" + outputs[i]);            
        }          
            
        double[] weights = new double[4]; //wagi, 3 dla zmiennych i 1 dla bias
        double localError, globalError;
        int i, p, iteration, output;
        
        weights[0] = liczba.nextInt(1); //w1
        weights[1] = liczba.nextInt(1); //w2
        weights[2] = liczba.nextInt(1); //w3
        weights[3] = liczba.nextInt(1); //bias
        
        iteration = 0;
        do {
            iteration++;
            globalError = 0;
            
            for (p = 0; p < NUM_INSTANCES; p++) {
                
                output = calculateOutput(theta, weights, x[p], y[p], z[p]);
                
                localError = outputs[p] - output;
                
                weights[0] += LEARNING_RATE * localError * x[p];
                weights[1] += LEARNING_RATE * localError * y[p];
                weights[2] += LEARNING_RATE * localError * z[p];
                weights[3] += LEARNING_RATE * localError;
                
                globalError += (localError * localError);
            }
            System.out.println("Iteracja " + iteration + " : RMSE = " + Math.sqrt(globalError/NUM_INSTANCES));
        } while (globalError != 0 && iteration <= MAX_ITER);
        
        System.out.println("\n==================\nDecision boundary equation:");
        System.out.println(weights[0] + "*x + " + weights[1] + "*y + " + weights[2] + "*z + " + weights[3] + " = 0");
        
        
        for(int j = 0; j < 10; j++) {
            double x1 = liczba.nextInt(20) - 10;
            double y1 = liczba.nextInt(20) - 10;
            //double z1 = liczba.nextInt(20) - 10;
            double z1 = 0;
            
            output = calculateOutput(theta, weights, x1, y1, z1);
            System.out.println("\nNew random point:");
            System.out.println("x = " + x1 + ", y = " + y1 + ", z = " + z1);
            System.out.println("class = " + output);
        }        
    }      
    
    static int calculateOutput(int theta, double weights[], double x, double y, double z) {
        double sum = x * weights[0] + y * weights[1] + z * weights[2] + weights[3];
        return (sum >= theta) ? 1 : 0;
    }
}