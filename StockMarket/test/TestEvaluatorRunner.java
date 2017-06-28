
import dataAccess.FileManager;
import dataModel.Acao;
import datamining.EvaluatorRunner;
import preprocessing.Discretizator;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
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
public class TestEvaluatorRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String raw_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/rawdata/VALE.csv";
        String dataSet_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/VALE.json";
        String arff_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/VALE.arff";

        Acao vale = FileManager.loadRawData("Vale S/A", "VALE5", raw_path);
        FileManager.saveDataSet(vale, dataSet_path);

        Instances vale_discretizade = Discretizator.discretize(vale, 14, true);

        try {
            FileManager.save(vale_discretizade, arff_path);

            J48 j48 = new J48();
            J48 j48u = new J48();

            String[] options = new String[1];
            options[0] = "";

            j48.setOptions(options);
            
            options[0] = "-U";
            j48u.setOptions(options);
            
            
            //EvaluatorRunner er1 = new EvaluatorRunner(vale_discretizade, j48);
            //EvaluatorRunner er2 = new EvaluatorRunner(vale_discretizade, j48u);
            
            //Thread t1 = new Thread(er1);
            //Thread t2 = new Thread(er2);
            
            System.out.println("Running..");
            //t1.run();
            //t2.run();
            
            /*
            t1.join();
            System.out.println("T1 - join");
            t2.join();
            System.out.println("T2 - join");
            */
            
            //System.out.println("J48 ="+er1.getEvaluation().pctCorrect());
            
            //System.out.println("J48U ="+er2.getEvaluation().pctCorrect());
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
