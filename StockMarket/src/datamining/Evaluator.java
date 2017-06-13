/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.*;

/**
 *  Evaluate classifiers
 * - Conjunctive Rule;
 * - J48;
 * - Random Forest;
 * - Multilayer Perceptron;
 * - Bayes Net;
 * - Naive Bayes;
 * - KNN;
 * - TAM;
 * 
 * @author wesley
 */
public class Evaluator {
    public static void evaluate(Instances dataset){
        String[] options = new String[1];
        options[0] = "-U";

        J48 classifier = new J48();
        
        try {
            Evaluation eval = new Evaluation(dataset);
            
            classifier.setOptions(options);
            
            eval.crossValidateModel(classifier, dataset, 2, new Random());
            
            System.out.println(eval.toSummaryString("\nResults\n\n", false));
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
