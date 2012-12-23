/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.cluster;

import com.mahoutx.data.DataCollector;
import com.mahoutx.data.MovielensDataCollector;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.dirichlet.UncommonDistributions;
import org.apache.mahout.clustering.evaluation.ClusterEvaluator;
import org.apache.mahout.clustering.kmeans.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansClusterer;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;

/**
 *
 * @author p.bell
 */
public class KMeansRunner extends Runner {

    public KMeansRunner(DistanceMeasure measure, DataCollector dataCollector) {
        super(measure, dataCollector);
    }

    @Override
    public void run() {

        try {
            List<Vector> sampleData = dataCollector.getData();
            FileWriter fileWriter = new FileWriter(new File(KMeansClusterer.class.getSimpleName()
                    + this.measure.getClass().getSimpleName()));
            for (int i = 10; i <= 100; i += 10) {
                System.out.println("#ofClusters:" + i);
                Collections.shuffle(sampleData);
                List<Cluster> clusters = getClusters(sampleData, i, this.measure);
                List<List<Cluster>> finalClusters = KMeansClusterer.clusterPoints(sampleData, clusters,
                        this.measure, 10, 0.01);
                fileWriter.append(i + ";");
                Evaluator evaluator = new Evaluator();
                evaluator.evaluateCluster(finalClusters.get(finalClusters.size() - 2),
                        this.measure, sampleData, fileWriter);
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error in KMeansRunner " + e.getMessage());
        }
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
