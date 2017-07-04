/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

/**
 *
 * @author wesley
 */
public class SingleResult {
    private String dataset;
    private String Algorithm;
    private double correct;
    private double truePositive;
    private double trueNegative;
    private double falsePositive;
    private double falseNegative;

    public SingleResult(String dataset, String Algorithm, double correct, double truePositive, double trueNegative, double falsePositive, double falseNegative) {
        this.dataset = dataset;
        this.Algorithm = Algorithm;
        this.correct = correct;
        this.truePositive = truePositive;
        this.trueNegative = trueNegative;
        this.falsePositive = falsePositive;
        this.falseNegative = falseNegative;
    }
    
    public String toString(){
        return dataset+","+Algorithm+","+correct+","+truePositive+","+trueNegative+","+falsePositive+","+falseNegative;
    }

    public String getDataset() {
        return dataset;
    }

    public String getAlgorithm() {
        return Algorithm;
    }

    public double getCorrect() {
        return correct;
    }

    public double getTruePositive() {
        return truePositive;
    }

    public double getTrueNegative() {
        return trueNegative;
    }

    public double getFalsePositive() {
        return falsePositive;
    }

    public double getFalseNegative() {
        return falseNegative;
    }
    
    
    
}
