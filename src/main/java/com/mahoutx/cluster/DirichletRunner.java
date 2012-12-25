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
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.dirichlet.DirichletClusterer;
import org.apache.mahout.clustering.dirichlet.models.GaussianClusterDistribution;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

/**
 *
 * @author p.bell
 */
public class DirichletRunner extends Runner {
    
    public DirichletRunner(DistanceMeasure measure, DataCollector dataCollector) {
        super(measure, dataCollector);
    }
    
    @Override
    public void run() {
        try {
            List<Vector> sampleData = dataCollector.getData();
            List<VectorWritable> sampleDataWritable = convertToWritable(sampleData);
            FileWriter fileWriter = new FileWriter(new File(this.dataCollector.getClass().getSimpleName()
                    + DirichletRunner.class.getSimpleName()));
            for (int i = 10; i <= 100; i += 10) {
                System.out.println("#ofClusters:" + i);
                Collections.shuffle(sampleDataWritable);
                DirichletClusterer dc =
                        new DirichletClusterer(
                        sampleDataWritable,
                        new GaussianClusterDistribution(
                        new VectorWritable(new DenseVector(2178))),
                        1.0, i, 1, 1);
                List<Cluster[]> finalClusters = dc.cluster(10);
                fileWriter.append(i + ";");
                Evaluator evaluator = new Evaluator();
                List<GenCluster> genCluster =
                        convertToGenCluster(finalClusters.get(finalClusters.size()-1));
                evaluator.evaluateCluster(genCluster,
                        this.measure, sampleData, fileWriter);
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error in KMeansRunner " + e.getMessage());
        }
    }
    
    private List<VectorWritable> convertToWritable(List<Vector> vectors) {
        List<VectorWritable> vectorWritable = new ArrayList<VectorWritable>();
        for (Vector vector : vectors) {
            vectorWritable.add(new VectorWritable(vector));
        }
        return vectorWritable;
    }
     private List<GenCluster> convertToGenCluster(Cluster[] clusters) {
        List<GenCluster> genClusters = new ArrayList<GenCluster>();
        for (int i=0;i<clusters.length;i++) {
            GenCluster genCluster = new GenCluster(clusters[i].getId(),clusters[i].getCenter());
            genClusters.add(genCluster);
        }
        return genClusters;
    }
}
