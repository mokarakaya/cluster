/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.cluster;

import com.mahoutx.data.DataCollector;
import com.mahoutx.data.MovielensDataCollector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansClusterer;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.Vector;

/**
 *
 * @author p.bell
 */
public class ClusterRun {

    public static void main(String[] args) throws Exception {
        DataCollector dataCollector = new MovielensDataCollector();
        List<Vector> sampleData = dataCollector.getData();
        for (int i = 2; i < 100; i += 10) {
            Collections.shuffle(sampleData);
            List<Cluster> clusters = getClusters(sampleData,i,new CosineDistanceMeasure());
            List<List<Cluster>> finalClusters = KMeansClusterer.clusterPoints(sampleData, clusters,
                    new CosineDistanceMeasure(), 10, 0.01);
            for (Cluster cluster : finalClusters.get(
                    finalClusters.size() - 1)) {
                System.out.println("Cluster id: " + cluster.getId()
                        + " center: "
                        + cluster.getCenter().asFormatString());

            }
        }
        // DisplayKMeans.main(new String[2]);
        //DisplayKMeans.main(args);

    }

    private static List<Cluster> getClusters(List<Vector> sampleData, int numberOfClusters,
                                DistanceMeasure distanceMeasure) {
        int clusterId = 0;
        List<Cluster> clusters = new ArrayList<Cluster>();
        for (Vector v : sampleData) {
            clusters.add(new Cluster(v, clusterId++,
                    distanceMeasure));
            if (clusterId == numberOfClusters) {
                break;
            }
        }
        return clusters;
    }
}
