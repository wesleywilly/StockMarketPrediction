/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wesley
 */
public class Result {
    private String dataset;
    private String Algorithm;
    private List<Double> corrects;
    private List<Double> truePositives;
    private List<Double> trueNegatives;
    private List<Double> falsePositives;
    private List<Double> falseNegatives;

    public Result(String dataset, String Algorithm) {
        this.dataset = dataset;
        this.Algorithm = Algorithm;
        
        corrects = new ArrayList<>();
        truePositives = new ArrayList<>();
        trueNegatives = new ArrayList<>();
        falsePositives = new ArrayList<>();
        falseNegatives = new ArrayList<>();
                
    }
    
    public Result(SingleResult sResult) {
        this.dataset = sResult.getDataset();
        this.Algorithm = sResult.getAlgorithm();
        
        corrects = new ArrayList<>();
        truePositives = new ArrayList<>();
        trueNegatives = new ArrayList<>();
        falsePositives = new ArrayList<>();
        falseNegatives = new ArrayList<>();
        
        addResult(sResult);
                
    }
    
    
    
    
    
    public boolean addResult(SingleResult sResult){
        if(sResult.getDataset().equals(dataset) && sResult.getAlgorithm().equals(Algorithm)){
            corrects.add(sResult.getCorrect());
            truePositives.add(sResult.getTruePositive());
            trueNegatives.add(sResult.getTrueNegative());
            falsePositives.add(sResult.getFalsePositive());
            falseNegatives.add(sResult.getFalseNegative());
            return true;
        }else{
            return false;
        }
    }

    public String getDataset() {
        return dataset;
    }

    public String getAlgorithm() {
        return Algorithm;
    }

    public double getCorrectsMean() {
        if(corrects.size()>0){
        double sum = 0;
        for(Double num: corrects){
            sum += num;
        }
        return sum/corrects.size();
        }else{
            return 0;
        }
        
    }

    public double getTruePositivesMean() {
        if(truePositives.size()>0){
        double sum = 0;
        for(Double num: truePositives){
            sum += num;
        }
        return sum/truePositives.size();
        }else{
            return 0;
        }
    }

    public double getTrueNegativesMean() {
        if(trueNegatives.size()>0){
        double sum = 0;
        for(Double num: trueNegatives){
            sum += num;
        }
        return sum/trueNegatives.size();
        }else{
            return 0;
        }
    }

    public double getFalsePositivesMean() {
        if(falsePositives.size()>0){
        double sum = 0;
        for(Double num: falsePositives){
            sum += num;
        }
        return sum/falsePositives.size();
        }else{
            return 0;
        }
    }

    public double getFalseNegativesMean() {
        if(falseNegatives.size()>0){
        double sum = 0;
        for(Double num: falseNegatives){
            sum += num;
        }
        return sum/falseNegatives.size();
        }else{
            return 0;
        }
    }
    
    
    
    
}
