
import dataAccess.FileManager;
import dataModel.Acao;
import datamining.*;
import java.util.ArrayList;
import java.util.List;
import preprocessing.Discretizator;
import smile.Network;
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
public class ExperimentBayesNet {
    private static final String COMPANY = "Vale S/A";
    private static final String QUOTE = "VALE5";
    private static final String DATASET_DIR = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/";
    private static final String EXPERIMENTS_DIR = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/experiments/";
    private static final String RAW_PATH = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/rawdata/VALE.csv";
    
    private static final String DATASETS = "datasets/";
    private static final String NETS = "nets/";
    private static final String RESULTS = "results/";
    
    private static final int MAX_WINDOWS = 15;
    private static final int MAX_TEST = 35;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
        
        System.out.print("Setting Bayesian Nets...");
        List<BayesNet> nets = new ArrayList<>();
        
        for(int i =0 ;i<instances.size();i++){
            nets.add(new BayesNet(instances.get(i), 0));
        }
        
            
    }
    
    
}
