/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

/**
 *
 * @author wesley
 */
public class Algorithm {
    
    public static final int J48U = 0;
    public static final int J48 = 1;
    public static final int RF = 2;
    public static final int NB = 3;
    
    public static final int MAX_CLASSIFIER_TYPES = 4;
    
    private String name;
    private AbstractClassifier classifier;

    public Algorithm(int type)throws Exception, Throwable{
        String[] options;
        if(type>=0 && type<4){
        switch(type){
            case J48U:
                name = "J48 Unpruned";
                classifier = new J48();
                options = new String[1];
                options[0] = "-U";
                classifier.setOptions(options);
                break;
                
            case J48:
                name = "J48";
                classifier = new J48();
                options = new String[1];
                options[0] = "";
                classifier.setOptions(options);
                break;
                
            case RF:
                name = "Random Forest";
                classifier = new RandomForest();
                options = new String[1];
                options[0] = "";
                classifier.setOptions(options);
                break;
            
            case NB:
                name = "Naive Bayes";
                classifier = new NaiveBayes();
                options = new String[1];
                options[0] = "";
                classifier.setOptions(options);
                break;
        }
        }else{
            throw new Throwable("Classifier Type out of range!");
        }
    }

    public String getName() {
        return name;
    }

    public AbstractClassifier getClassifier() {
        return classifier;
    }
    
    
    
    
    
    
}
