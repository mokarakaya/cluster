/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.data;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

/**
 *
 * @author p.bell
 */
public class JesterDataCollector implements DataCollector {

    private int numberOfUsers = 5000;
    private int numberOfItems = 101;

    public List<Vector> getData() {

        ArrayList<Vector> vectors = new ArrayList<Vector>();
        double[] ratings = new double[numberOfItems];
        for (int i = 0; i < numberOfItems; i++) {
            ratings[i] = 0;
        }
        for (int i = 0; i < numberOfUsers; i++) {
            vectors.add(new DenseVector(ratings.clone()));
        }
        try {
            Scanner f = new Scanner(new File("C:/book-crossing/Jester.csv"));
            while (f.hasNextLine()) {
                String line = f.nextLine();
                int userId = Integer.parseInt(line.substring(0, line.indexOf(";")));
                line = line.substring(line.indexOf(";") + 1);
                int itemId = Integer.parseInt(line.substring(0, line.indexOf(";")));
                line = line.substring(line.indexOf(";") + 1);
                double rating = new Double(line);
                vectors.get(userId).setQuick(itemId, rating);
            }
        } catch (Exception e) {
            System.out.println("JesterDataCollector Error while reading data: " + e.getMessage());
        }
        return vectors;
    }
}
