/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wesley
 */
public class Results {

    private List<Double> corrects;
    private List<Double> pCorrects;
    private double mean;
    private String classifier;

    public Results(String classifier) {
        this.classifier = classifier;
        corrects = new ArrayList<>();
        pCorrects = new ArrayList<>();
        mean = -1;
    }
    /**
     * 
     * @param correct
     * @param instances - size of dataset. 
     */
    public void add(double correct, int instances){
        corrects.add(correct);
        pCorrects.add(correct/instances);
        getMean();
    }

    public List<Double> getCorrects() {
        return corrects;
    }

    public double getMean() {
        if (mean != -1) {
            if (pCorrects.size() > 0) {
                double sum = 0;
                for (int i = 0; i > pCorrects.size(); i++) {
                    sum += pCorrects.get(i);
                }
                mean = sum / pCorrects.size();
            } else {
                mean = -1;
            }

        }
        return mean;
    }

}
