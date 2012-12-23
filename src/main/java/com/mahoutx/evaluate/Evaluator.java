/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.evaluate;

import com.mahoutx.model.GenCluster;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.DistanceMeasureCluster;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

/**
 *
 * @author p.bell
 */
public class Evaluator {

    public void evaluateCluster(List<GenCluster> clusters, DistanceMeasure measure,
            List<Vector> sampleData, FileWriter fileWriter) throws IOException, Exception {
        double totalInterCluster = 0;
        double totalIntraCluster = 0;
        int count = 0;
        for (int i = 0; i < clusters.size(); i++) {
            totalIntraCluster += getIntraClusterDistance(clusters,sampleData, measure, clusters.get(i));
            for (int j = i + 1; j < clusters.size(); j++) {
                double d = measure.distance(clusters.get(i).getCenter(),
                        clusters.get(j).getCenter());
                totalInterCluster += d;
                count++;
            }
        }
        double avgIntraCluster = totalIntraCluster / clusters.size();
        double avgInterCluster = totalInterCluster / count;
        fileWriter.append(avgIntraCluster + ";" + avgInterCluster + "\n");
    }

    private double getIntraClusterDistance(List<GenCluster> clusters,List<Vector> sampleData, DistanceMeasure measure, GenCluster cluster) throws Exception {
        double totalDistance = 0;
        int count=0;
        for (Vector vector : sampleData) {
            double distance= getDistance(vector,  clusters, measure, cluster);
           
            if(distance!=Double.MAX_VALUE )
            {
                totalDistance+=distance;
                count++;
            }
        }
        //if(count!=cluster.getNumPoints())
          //  throw new Exception("error in getInnerClusterDistance");
        return totalDistance /count;
    }

    protected double getDistance(Vector point,
            Iterable<GenCluster> clusters, DistanceMeasure measure, GenCluster targetCluster) {
        GenCluster closestCluster = null;
        double closestDistance = Double.MAX_VALUE;
        for (GenCluster cluster : clusters) {
            double distance = measure.distance(cluster.getCenter(), point);
            if (( !(""+distance).equals("NaN"))&&(closestCluster == null || closestDistance > distance)) {
                closestCluster = cluster;
                closestDistance = distance;
            }
        }
        if (closestCluster!=null && targetCluster.getId() == closestCluster.getId()) {
            return closestDistance;
        }
        return Double.MAX_VALUE;
    }
}
