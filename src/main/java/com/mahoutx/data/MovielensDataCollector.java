/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

/**
 *
 * @author p.bell
 */
public class MovielensDataCollector implements DataCollector {

    private int numberOfUsers = 944;
    private int numberOfItems = 1683;

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
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/slopeonetest?autoReconnect=true", "root", "root");
            String sql = "select * from slopeonetest.movielensdatatime order by userId";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rSet = stmt.executeQuery();
            while (rSet.next()) {
                int userId = rSet.getInt("userId");
                int itemId = rSet.getInt("itemId");
                double rating = rSet.getDouble("rating");
                vectors.get(userId).setQuick(itemId, rating);
            }

        } catch (Exception e) {
            System.out.println("MovielensDataCollector Error while reading data: " + e.getMessage());
        }
        return vectors;
    }
}
