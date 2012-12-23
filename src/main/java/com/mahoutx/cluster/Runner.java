/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.cluster;

import com.mahoutx.data.DataCollector;
import org.apache.mahout.common.distance.DistanceMeasure;

/**
 *
 * @author p.bell
 */
public abstract class Runner {

    DistanceMeasure measure;
    DataCollector dataCollector;

    public Runner(DistanceMeasure measure, DataCollector dataCollector) {
        this.measure = measure;
        this.dataCollector = dataCollector;
    }

    public void run() {
    }
}
