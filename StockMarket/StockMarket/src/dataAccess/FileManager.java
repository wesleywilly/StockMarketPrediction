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
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("[FILE MANAGER] header: " + br.readLine());
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
    
    public static boolean saveDataSet(Acao dataSet, String file_path){
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
    

}
