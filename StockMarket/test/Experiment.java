
import dataAccess.FileManager;
import dataModel.Acao;
import datamining.Algorithm;
import datamining.EvaluatorRunner;
import experiment.ExperimentResults;
import experiment.SingleResult;
import java.util.ArrayList;
import java.util.List;
import preprocessing.Discretizator;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wesley
 */
public class Experiment {
    
    private static final String COMPANY = "Vale S/A";
    private static final String QUOTE = "VALE5";
    private static final String DATASET_DIR = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/";
    private static final String EXPERIMENTS_DIR = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/experiments/";
    private static final String RAW_PATH = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/rawdata/VALE.csv";
    
    private static final int MAX_WINDOWS = 15;
    private static final int MAX_TEST = 35;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Throwable {
        
        String experiment_folder = String.valueOf(System.currentTimeMillis())+"/";
        
        System.out.print("Loading Raw data...");
        Acao vale = FileManager.loadRawData(COMPANY, QUOTE, RAW_PATH);
        System.out.println(" Done!");
        
        System.out.print("Saving Dataset...");
        FileManager.saveDataSet(vale, DATASET_DIR+QUOTE+".json");
        System.out.println(" Done!");
        
        System.out.print("Generating arff datasets...");
        List<Instances> instances = new ArrayList<>();
        for(int i = 0; i<MAX_WINDOWS;i++){
            instances.add(Discretizator.discretize(vale, i+1));
            FileManager.save(instances.get(i), EXPERIMENTS_DIR+experiment_folder+QUOTE+"("+(i+1)+").arff");
        }
        System.out.println(" Done!");
        
        
        
        try{
            System.out.print("Setting Machine Learning Algorithms...");
            List<Algorithm> classifiers = setClassifiers();
            System.out.println(" Done!");
            
            System.out.print("Starting test...");
            ExperimentResults result = new ExperimentResults();
            
            for(Instances dataset: instances){
                for(Algorithm classifier: classifiers){
                    List<Thread> threads = new ArrayList<>();
                    List<EvaluatorRunner> evaluators = new ArrayList<>();
                    
                    
                    for(int it=0;it<MAX_TEST;it++){
                        EvaluatorRunner er = new EvaluatorRunner(dataset, classifier);
                        evaluators.add(er);
                        threads.add(new Thread(er));
                    }
                    for(Thread thread: threads)
                        thread.run();
                    
                    for(Thread thread: threads)
                        thread.join();
                                
                                
                    for(EvaluatorRunner er: evaluators){
                        SingleResult sr = new SingleResult(er.getDataset().relationName(), 
                                er.getClassifier().getName(),
                                er.getEvaluation().pctCorrect(),
                                er.getEvaluation().truePositiveRate(0),
                                er.getEvaluation().truePositiveRate(1),
                                er.getEvaluation().trueNegativeRate(0),
                                er.getEvaluation().trueNegativeRate(1));
                        
                        
                        result.addResult(sr);
                    }
                    
                }
                
            }
            
            result.toCSV(ExperimentResults.ACCURACY,EXPERIMENTS_DIR+experiment_folder+"accuracy.csv");
        }catch(Exception e){
            e.printStackTrace();
        }
        
        System.out.println(" Done!");
    }
    
    private static List<Algorithm> setClassifiers() throws Exception, Throwable{
        List<Algorithm> classifiers = new ArrayList<>();
               for(int i = 0; i<Algorithm.MAX_CLASSIFIER_TYPES;i++){
                   classifiers.add(new Algorithm(i));
               }
        
        return classifiers;
    }
    
    
}
