
import dataAccess.FileManager;
import dataModel.Acao;
import datamining.Evaluator;
import preprocessing.Discretizator;
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
public class testWindow {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String raw_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/rawdata/VALE.csv";
        String dataSet_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/VALE.json";
        String arff_path = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/VALE.arff";

        Acao vale = FileManager.loadRawData("Vale S/A", "VALE5", raw_path);
        FileManager.saveDataSet(vale, dataSet_path);

        int max_window = 15;
        int max_test = 35;

        double[][][] result = new double[max_window][max_test][2];
        
        double[] mean = new double[max_window];
        for(double num: mean)
            num=0;
        
        for (int i = 0; i < max_window; i++) {
            for (int j = 0; j < max_test; j++) {

                Instances vale_discretizade = Discretizator.discretize(vale, (i+1), true);

                FileManager.save(vale_discretizade, arff_path.substring(0,arff_path.length()-4)+"w("+(i+1)+").arff");

                Evaluator.evaluate(vale_discretizade);
                
                result[i][j][0] = Evaluator.evaluate(vale_discretizade); 
                result[i][j][1] = vale_discretizade.size();
                
                mean[i] +=  (result[i][j][0]/result[i][j][1])*100;
            }
        }

        System.out.println("\n MEANS:");
        for(int i=0;i<max_window;i++){
           mean[i] = mean[i]/max_test; 
           
            System.out.println("Window ["+(i+1)+"] = "+mean[i]);
        }

    }

}
