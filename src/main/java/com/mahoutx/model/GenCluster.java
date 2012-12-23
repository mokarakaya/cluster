/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mahoutx.model;

import org.apache.mahout.math.Vector;

/**
 *
 * @author p.bell
 */
public class GenCluster {

    private Vector center;
    private int id;

    public GenCluster(int id, Vector center) {
        this.id = id;
        this.center = center;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector getCenter() {
        return this.center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }
}
