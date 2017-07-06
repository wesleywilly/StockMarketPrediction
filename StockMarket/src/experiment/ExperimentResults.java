/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiment;

import dataAccess.FileManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wesley
 */
public class ExperimentResults {

    public static final int ACCURACY = 0;
    public static final int TP = 1;
    public static final int TN = 2;
    public static final int FP = 3;
    public static final int FN = 4;

    private List<Result> results;

    public ExperimentResults() {
        results = new ArrayList<>();
    }

    public void addResult(SingleResult sResult) {
        boolean found = false;
        for (int i = 0; i < results.size() && !found; i++) {
            if (results.get(i).addResult(sResult)) {
                found = true;
            }
        }
        if (!found) {
            results.add(new Result(sResult));
        }
    }

    public List<Result> getResults() {
        return results;
    }

    public void toCSV(String file_path) {
        //Acuracy

        List<String> lines = new ArrayList<>();
        //Header
        lines.add("DataSet,Algorithm,Corrects(%),TP(%),TN(%),FP(%),FN(%)");

        for (Result result : results) {
            lines.add(result.getDataset() + ","
                    + result.getAlgorithm() + ","
                    + result.getCorrectsMean() + ","
                    + result.getTruePositivesMean() + ","
                    + result.getTrueNegativesMean() + ","
                    + result.getFalsePositivesMean() + ","
                    + result.getFalseNegativesMean());
        }

        FileManager.save(lines, file_path);

    }

    public void toCSV(int type, String file_path) {
        List<String> lines = new ArrayList<>();
        List<String> algoritms = getAlgorithms();

        //Header
        String header = "DataSet";

        for (String algorithm : algoritms) {
            header = header + "," + algorithm;
        }

        //Body
        String dataset = "";
        String line = header;
        for (int i = 0; i < results.size(); i++) {
            double value;
            switch (type) {
                case ACCURACY:
                    value = results.get(i).getCorrectsMean();
                    break;
                case TP:
                    value = results.get(i).getTruePositivesMean();
                    break;
                case TN:
                    value = results.get(i).getTrueNegativesMean();
                    break;
                case FP:
                    value = results.get(i).getFalsePositivesMean();
                    break;
                case FN:
                    value = results.get(i).getFalseNegativesMean();
                    break;
                default:
                    value = results.get(i).getCorrectsMean();
                    break;
            }

            if (results.get(i).getDataset().equals(dataset)) {
                line = line + "," + value;
            } else {
                lines.add(line);
                dataset = results.get(i).getDataset();
                line = dataset + "," + value;

            }
        }
        lines.add(line);
        FileManager.save(lines, file_path);
    }

    private List<String> getAlgorithms() {
        List<String> algorithms = new ArrayList<>();

        for (Result result : results) {
            boolean found = false;

            for (int i = 0; i < algorithms.size() && !found; i++) {
                if (result.getAlgorithm().equals(algorithms.get(i))) {
                    found = true;
                }

            }
            if (!found) {
                algorithms.add(result.getAlgorithm());
            }

        }
        return algorithms;
    }

}
