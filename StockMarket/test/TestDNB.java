
import dataAccess.FileManager;
import dataModel.Acao;
import java.util.ArrayList;
import java.util.List;
import preprocessing.Discretizator;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.BayesNetGenerator;
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
public class TestDNB {
    private static final String COMPANY = "Vale S/A";
    private static final String QUOTE = "VALE5";
    private static final String DATASET_DIR = "./dataset/";
    private static final String EXPERIMENTS_DIR = "./experiments/";
    private static final String RAW_PATH = "./rawdata/VALE.csv";
    
    private static final String DATASETS = "datasets/";
    private static final String NETS = "nets/";
    private static final String RESULTS = "results/";
    
    private static final int MAX_WINDOWS = 30;
    private static final int MAX_TEST = 35;
    
    public static void main(String[] args) throws Throwable {
        String experiment_folder = String.valueOf(System.currentTimeMillis())+"/";
        System.out.println("Experiment: "+experiment_folder);
        
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
            FileManager.save(instances.get(i), EXPERIMENTS_DIR+experiment_folder+DATASETS+QUOTE+"("+(i+1)+").arff");
        }
        System.out.println(" Done!");
        
        System.out.print("Training Baysian nets...");
        
        List<BayesNet> bayesNets = new ArrayList<>();
        for(Instances xpSource: instances){
            BayesNet bayesNet = new BayesNet();
            DailyNaive dnb = new DailyNaive();
            dnb.buildStructure(bayesNet, xpSource);   
        }
        
        
        
        System.out.println(" Done!");
    }
}
