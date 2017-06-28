/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SimpleLinearRegression;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
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
    
    public static double evaluate(Instances dataset){
        String[] options = new String[1];
        options[0] = "-U";

        J48 classifier = new J48();
        
        double correct = -1;
        try {
            Evaluation eval = new Evaluation(dataset);
            
            //classifier.setOptions(options);
            
            eval.crossValidateModel(classifier, dataset, 2, new Random());
            
            correct = eval.correct();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return correct;
    }
    
    public static List<AbstractClassifier> generateClassifiers(){
        List<AbstractClassifier> classifiers = new ArrayList<>();
        String[] options;
        
        //Decision Table
        DecisionTable dt = new DecisionTable();
        options = new String[1];
        options[0] = "";
        try{
            dt.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
       //M5Rules
        M5Rules m5r = new M5Rules();
        options = new String[1];
        options[0] = "";
        try{
            m5r.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //J48 Unpruned
        J48 j48u = new J48();
        options = new String[1];
        options[0] = "-U";
        try{
            j48u.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //J48
        J48 j48 = new J48();
        options = new String[1];
        options[0] = "";
        try{
            j48.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //Random Forest
        RandomForest rf = new RandomForest();
        options = new String[1];
        options[0] = "";
        try{
            rf.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //NaiveBayes
        NaiveBayes nb = new NaiveBayes();
        options = new String[1];
        options[0] = "";
        try{
            nb.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        //Simple Linear Regression
        SimpleLinearRegression slr = new SimpleLinearRegression();
        options = new String[1];
        options[0] = "";
        try{
            slr.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //Linear Regression
        LinearRegression lr = new LinearRegression();
        options = new String[1];
        options[0] = "";
        try{
            lr.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //Multilayer Perceptron
        MultilayerPerceptron mlp = new MultilayerPerceptron();
        options = new String[1];
        options[0] = "";
        try{
            mlp.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        //KNN k=1
        IBk nn1 = new IBk(1);
        options = new String[1];
        options[0] = "";
        try{
            nn1.setOptions(options);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        classifiers.add(dt);
        classifiers.add(m5r);
        classifiers.add(j48u);
        classifiers.add(j48);
        classifiers.add(rf);
        classifiers.add(nb);
        classifiers.add(slr);
        classifiers.add(lr);
        classifiers.add(mlp);
        classifiers.add(nn1);
        
        return classifiers;
    }
}
