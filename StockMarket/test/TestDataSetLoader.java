
import dataAccess.FileManager;
import dataModel.*;
import datamining.Evaluator;
import preprocessing.Discretizator;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wesley
 */
public class TestDataSetLoader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String raw_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/rawdata/VALE.csv";
        String dataSet_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/VALE.json";
        String arff_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/VALE.arff";
        
        Acao vale = FileManager.loadRawData("Vale S/A", "VALE5", raw_path);
        FileManager.saveDataSet(vale, dataSet_path);
        
        Instances vale_discretizade = Discretizator.discretize(vale,5,true);
        
        FileManager.save(vale_discretizade,arff_path);
        
        int size = vale_discretizade.size();
        double correct = Evaluator.evaluate(vale_discretizade);
        double pcorrect = (correct/size)*100;
        
        System.out.println("Instances = "+size+"\n"
                            + "Hits = "+correct+"\n"
                            + "% = "+pcorrect+"\n");
        
    }
    
}
