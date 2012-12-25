/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.cluster;

import com.mahoutx.data.DataCollector;
import com.mahoutx.evaluate.Evaluator;
import com.mahoutx.model.GenCluster;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.mahout.clustering.kmeans.KMeansClusterer;
import org.apache.mahout.clustering.meanshift.MeanShiftCanopy;
import org.apache.mahout.clustering.meanshift.MeanShiftCanopyClusterer;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.Vector;

/**
 *
 * @author p.bell
 */
public class MeanShiftCanopyRunner extends Runner {

    public MeanShiftCanopyRunner(DistanceMeasure measure, DataCollector dataCollector) {
        super(measure, dataCollector);
    }

    @Override
    public void run() {
        try {
            List<Vector> sampleData = dataCollector.getData();
            FileWriter fileWriter = new FileWriter(new File(this.dataCollector.getClass().getSimpleName()
                    + this.measure.getClass().getSimpleName()
                    + MeanShiftCanopyRunner.class.getSimpleName()));
            for (double i = 0.6; i <= 1; i += 0.02) {
                System.out.println("#ofClusters:" + i);
                Collections.shuffle(sampleData);
                List<MeanShiftCanopy> finalClusters = MeanShiftCanopyClusterer.clusterPoints(sampleData, this.measure, 0.01, 0.01, i, 10);
                fileWriter.append(i + ";" + finalClusters.size() + ";");
                List<GenCluster> genCluster = convertToGenCluster(finalClusters);
                Evaluator evaluator = new Evaluator();
                evaluator.evaluateCluster(genCluster,
                        this.measure, sampleData, fileWriter);
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error in MeanShiftCanopyRunner " + e.getMessage());
        }
    }

    private List<GenCluster> convertToGenCluster(List<MeanShiftCanopy> clusters) {
        List<GenCluster> genClusters = new ArrayList<GenCluster>();
        for (MeanShiftCanopy cluster : clusters) {
            GenCluster genCluster = new GenCluster(cluster.getId(), cluster.getCenter());
            genClusters.add(genCluster);
        }
        return genClusters;
    }
}
