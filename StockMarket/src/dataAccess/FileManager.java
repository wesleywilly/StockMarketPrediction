/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import dataModel.Acao;
import dataModel.Pregao;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

/**
 *
 * @author wesley
 */
public class FileManager {

    private static final String SEPARATOR = ",";

    public static Acao loadRawData(String empresa, String simbolo, String raw_path) {

        List<Pregao> historico = new ArrayList<>();
        BufferedReader br = null;
        String line;
        boolean done = false;

        try {
            br = new BufferedReader(new FileReader(raw_path));
            br.readLine();
            //System.out.println("[FILE MANAGER] header: " + br.readLine());
            while ((line = br.readLine()) != null) {

                String[] fields = line.split(SEPARATOR);
                if (!fields[1].equals("null")) {
                    historico.add(new Pregao(fields[0],
                            Double.parseDouble(fields[1]),
                            Double.parseDouble(fields[2]),
                            Double.parseDouble(fields[3]),
                            Double.parseDouble(fields[4]),
                            Double.parseDouble(fields[5]),
                            Integer.parseInt(fields[6])));
                }
            }

            done = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (done) {
            return new Acao(empresa, simbolo, historico);
        } else {
            System.out.println("[FILE MANAGER] Error! while loading! =(");
            return null;
        }

    }

    public static boolean saveDataSet(Acao dataSet, String file_path) {
        boolean saved = false;
        FileWriter writeFile = null;

        try {
            writeFile = new FileWriter(file_path);

            //Escreve no arquivo conteudo do Objeto JSON 
            writeFile.write(dataSet.toJSON().toJSONString());
            writeFile.close();
            saved = true;
        } catch (IOException e) {
            System.out.println("[FILE MANAGER] Error while saving file:");
            e.printStackTrace();
        }
        return saved;
    }

    public static void save(Instances dataSet, String file_path) {
        ArffSaver saver = new ArffSaver();
        saver.setInstances(dataSet);
        try {
            saver.setFile(new File(file_path));
            //saver.setDestination(new File("./data/test.arff"));   // **not** necessary in 3.5.4 and later
            saver.writeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void save(List<String> results, String file_path){
        try{
            PrintWriter writer = new PrintWriter(file_path, "UTF-8");
            for(String line: results){
                writer.println(line);
            }
            writer.close();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
