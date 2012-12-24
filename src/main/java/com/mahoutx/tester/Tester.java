/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.tester;

import com.mahoutx.cluster.DirichletRunner;
import com.mahoutx.cluster.KMeansRunner;
import com.mahoutx.cluster.MeanShiftCanopyRunner;
import com.mahoutx.cluster.Runner;
import com.mahoutx.data.JesterDataCollector;
import com.mahoutx.data.MovielensDataCollector;
import org.apache.mahout.common.distance.CosineDistanceMeasure;

/**
 *
 * @author p.bell
 */
public class Tester {
    public static void main(String[] args)
    {
        
        Runner runner=new MeanShiftCanopyRunner(new CosineDistanceMeasure(), new MovielensDataCollector());
        runner.run();
        System.out.println("MeanShiftCanopyRunner finishes");
        //runner=new KMeansRunner(new CosineDistanceMeasure(), new MovielensDataCollector());
        //runner.run();
        System.out.println("KMeansRunner finishes");
        runner=new DirichletRunner(null, new MovielensDataCollector());
        runner.run();
        
    }
}
