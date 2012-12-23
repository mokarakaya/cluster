/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.tester;

import com.mahoutx.cluster.KMeansRunner;
import com.mahoutx.cluster.Runner;
import com.mahoutx.data.JesterDataCollector;
import org.apache.mahout.common.distance.CosineDistanceMeasure;

/**
 *
 * @author p.bell
 */
public class Tester {
    public static void main(String[] args)
    {
        Runner runner=new KMeansRunner(new CosineDistanceMeasure(), new JesterDataCollector());
        runner.run();
        
    }
}
