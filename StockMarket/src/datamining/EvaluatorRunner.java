/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.util.Random;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 *
 * @author wesley
 */
public class EvaluatorRunner implements Runnable{
    private Instances dataset;
    private Algorithm classifier;
    private Evaluation evaluation;

    public EvaluatorRunner(Instances dataset, Algorithm classifier) {
        this.dataset = dataset;
        this.classifier = classifier;
    }

    public Evaluation getEvaluation(){
        return evaluation;
    }
    
    @Override
    public void run() {
        if(classifier != null){
            try{
                evaluation = new Evaluation(dataset);
                evaluation.crossValidateModel(classifier.getClassifier(), dataset, 10, new Random(System.currentTimeMillis()));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }

    public Algorithm getClassifier() {
        return classifier;
    }

    public Instances getDataset() {
        return dataset;
    }
    
    
    
}
