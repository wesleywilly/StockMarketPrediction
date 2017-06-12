
import dataAccess.FileManager;
import dataModel.*;

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
        String raw_path = "/home/wesley/Projects/StockMarket/StockMarket/rawdata/VALE.csv";
        String dataSet_path = "/home/wesley/Projects/StockMarket/StockMarket/rawdata/VALE.json";
        
        Acao vale = FileManager.loadRawData("Vale S/A", "VALE5", raw_path);
        FileManager.saveDataSet(vale, dataSet_path);
        
    }
    
}
