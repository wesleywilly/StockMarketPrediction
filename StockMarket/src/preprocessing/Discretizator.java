/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessing;

import dataModel.*;
import java.util.ArrayList;
import java.util.List;
import weka.core.*;

/**
 *
 * @author wesley
 */
public class Discretizator {

    private static final String V_ALTA = "A";
    private static final String V_BAIXA = "B";
    private static final String V_IGUAL = "I";

    private static final String V_ALTO = "A";
    private static final String V_MEDIO = "M";
    private static final String V_BAIXO = "B";

    private static final int ABERTURA = 0;
    private static final int BAIXA = 1;
    private static final int ALTA = 2;
    private static final int VOLUME = 3;
    private static final int FECHAMENTO = 4;

    /**
     * Transform numeric values to nominal using mean and standard deviation
     *
     * @param original
     * @return Weka Data Set
     */
    public static Instances discretize(Acao original) {
        //Creating Dataset
        Instances dataset = generateEmptyNominalDataSet(original.getSimbolo(), original.getHistorico());

        //Getting Means and Standard Deviation of all data
        List<double[]> msd = getMeansAndStandardDeviation(original.getHistorico());

        //Discretizing and inserting instances
        for (int i = 0; i < original.getHistorico().size(); i++) {
            double[] values = new double[5];

            //Abertura
            if (original.getHistorico().get(i).getAbertura() > msd.get(0)[ABERTURA] + msd.get(1)[ABERTURA]) {
                values[ABERTURA] = dataset.attribute(ABERTURA).indexOfValue(V_ALTO);
            } else if (original.getHistorico().get(i).getAbertura() < msd.get(0)[ABERTURA] - msd.get(1)[ABERTURA]) {
                values[ABERTURA] = dataset.attribute(ABERTURA).indexOfValue(V_BAIXO);
            } else {
                values[ABERTURA] = dataset.attribute(ABERTURA).indexOfValue(V_MEDIO);
            }

            //Baixa
            if (original.getHistorico().get(i).getBaixa() > msd.get(0)[BAIXA] + msd.get(1)[BAIXA]) {
                values[BAIXA] = dataset.attribute(BAIXA).indexOfValue(V_ALTO);
            } else if (original.getHistorico().get(i).getBaixa() < msd.get(0)[BAIXA] - msd.get(1)[BAIXA]) {
                values[BAIXA] = dataset.attribute(BAIXA).indexOfValue(V_BAIXO);
            } else {
                values[BAIXA] = dataset.attribute(BAIXA).indexOfValue(V_MEDIO);
            }

            //Alta
            if (original.getHistorico().get(i).getAlta() > msd.get(0)[ALTA] + msd.get(1)[ALTA]) {
                values[ALTA] = dataset.attribute(ALTA).indexOfValue(V_ALTO);
            } else if (original.getHistorico().get(i).getAlta() < msd.get(0)[ALTA] - msd.get(1)[ALTA]) {
                values[ALTA] = dataset.attribute(ALTA).indexOfValue(V_BAIXO);
            } else {
                values[ALTA] = dataset.attribute(ALTA).indexOfValue(V_MEDIO);
            }

            //Volume
            if (original.getHistorico().get(i).getVolume() > msd.get(0)[VOLUME] + msd.get(1)[VOLUME]) {
                values[VOLUME] = dataset.attribute(VOLUME).indexOfValue(V_ALTO);
            } else if (original.getHistorico().get(i).getVolume() < msd.get(0)[VOLUME] - msd.get(1)[VOLUME]) {
                values[VOLUME] = dataset.attribute(VOLUME).indexOfValue(V_BAIXO);
            } else {
                values[VOLUME] = dataset.attribute(VOLUME).indexOfValue(V_MEDIO);
            }

            //Fechamento
            if (i > 0) {
                if (original.getHistorico().get(i).getFechamento() > original.getHistorico().get(i - 1).getFechamento()) {
                    values[FECHAMENTO] = dataset.attribute(FECHAMENTO).indexOfValue(V_ALTA);
                } else if (original.getHistorico().get(i).getFechamento() < original.getHistorico().get(i - 1).getFechamento()) {
                    values[FECHAMENTO] = dataset.attribute(FECHAMENTO).indexOfValue(V_BAIXA);
                } else {
                    values[FECHAMENTO] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
                }
            } else {
                values[FECHAMENTO] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
            }

            dataset.add(new DenseInstance(1.0, values));

        }

        return dataset;
    }

    /**
     * Transform numeric values to nominal using mean and standard deviation
     *
     * @param original - Original dataset
     * @param name - Dataset name
     * @param window - get days before actual day and put as attributes
     * @return
     */
    public static Instances discretize(Acao original, int window) {
        //Creating Dataset
        Instances dataset = generateEmptyNominalDataSet(original.getSimbolo(), original.getHistorico(), window);

        //Getting Means and Standard Deviation of all data
        List<double[]> msd = getMeansAndStandardDeviation(original.getHistorico());

        //Discretizing and inserting instances
        for (int i = window; i < original.getHistorico().size(); i++) {
            double[] values = new double[5 * (window + 1)];

            //Abertura
            for (int j = 0; j <= window; j++) {

                if (original.getHistorico().get(i-(window-j)).getAbertura() > msd.get(0)[ABERTURA] + msd.get(1)[ABERTURA]) {
                    values[ABERTURA+(5*j)] = dataset.attribute(ABERTURA).indexOfValue(V_ALTO);
                } else if (original.getHistorico().get(i-(window-j)).getAbertura() < msd.get(0)[ABERTURA] - msd.get(1)[ABERTURA]) {
                    values[ABERTURA+(5*j)] = dataset.attribute(ABERTURA).indexOfValue(V_BAIXO);
                } else {
                    values[ABERTURA+(5*j)] = dataset.attribute(ABERTURA).indexOfValue(V_MEDIO);
                }

                //Baixa
                if (original.getHistorico().get(i-(window-j)).getBaixa() > msd.get(0)[BAIXA] + msd.get(1)[BAIXA]) {
                    values[BAIXA+(5*j)] = dataset.attribute(BAIXA).indexOfValue(V_ALTO);
                } else if (original.getHistorico().get(i-(window-j)).getBaixa() < msd.get(0)[BAIXA] - msd.get(1)[BAIXA]) {
                    values[BAIXA+(5*j)] = dataset.attribute(BAIXA).indexOfValue(V_BAIXO);
                } else {
                    values[BAIXA+(5*j)] = dataset.attribute(BAIXA).indexOfValue(V_MEDIO);
                }

                //Alta
                if (original.getHistorico().get(i-(window-j)).getAlta() > msd.get(0)[ALTA] + msd.get(1)[ALTA]) {
                    values[ALTA+(5*j)] = dataset.attribute(ALTA).indexOfValue(V_ALTO);
                } else if (original.getHistorico().get(i-(window-j)).getAlta() < msd.get(0)[ALTA] - msd.get(1)[ALTA]) {
                    values[ALTA+(5*j)] = dataset.attribute(ALTA).indexOfValue(V_BAIXO);
                } else {
                    values[ALTA+(5*j)] = dataset.attribute(ALTA).indexOfValue(V_MEDIO);
                }

                //Volume
                if (original.getHistorico().get(i-(window-j)).getVolume() > msd.get(0)[VOLUME] + msd.get(1)[VOLUME]) {
                    values[VOLUME+(5*j)] = dataset.attribute(VOLUME).indexOfValue(V_ALTO);
                } else if (original.getHistorico().get(i-(window-j)).getVolume() < msd.get(0)[VOLUME] - msd.get(1)[VOLUME]) {
                    values[VOLUME+(5*j)] = dataset.attribute(VOLUME).indexOfValue(V_BAIXO);
                } else {
                    values[VOLUME+(5*j)] = dataset.attribute(VOLUME).indexOfValue(V_MEDIO);
                }

                //Fechamento
                if (i > 0) {
                    if (original.getHistorico().get(i-(window-j)).getFechamento() > original.getHistorico().get(i - 1).getFechamento()) {
                        values[FECHAMENTO+(5*j)] = dataset.attribute(FECHAMENTO).indexOfValue(V_ALTA);
                    } else if (original.getHistorico().get(i-(window-j)).getFechamento() < original.getHistorico().get(i - 1).getFechamento()) {
                        values[FECHAMENTO+(5*j)] = dataset.attribute(FECHAMENTO).indexOfValue(V_BAIXA);
                    } else {
                        values[FECHAMENTO+(5*j)] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
                    }
                } else {
                    values[FECHAMENTO+(5*j)] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
                }

            }
            dataset.add(new DenseInstance(1.0, values));

        }

        return dataset;

    }

    private static Instances generateEmptyNominalDataSet(String name, List<Pregao> historico) {
        //Creating Dataset

        //Attributes labels
        ArrayList<String> attLabels = new ArrayList<>();
        attLabels.add(V_ALTO);
        attLabels.add(V_MEDIO);
        attLabels.add(V_BAIXO);

        //Class labels
        ArrayList<String> classLabels = new ArrayList<>();
        classLabels.add(V_ALTA);
        classLabels.add(V_IGUAL);
        classLabels.add(V_BAIXA);

        //Atributes
        Attribute abertura = new Attribute("Abertura", attLabels);
        Attribute baixa = new Attribute("Baixa", attLabels);
        Attribute alta = new Attribute("Alta", attLabels);
        Attribute volume = new Attribute("Volume", attLabels);
        Attribute fechamento = new Attribute("Fechamento", classLabels);

        //Header
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(abertura);
        attributes.add(baixa);
        attributes.add(alta);
        attributes.add(volume);
        attributes.add(fechamento);

        //Dataset
        Instances dataset = new Instances(name, attributes, 0);

        //Defining class attribute
        dataset.setClassIndex(4);

        return dataset;
    }

    private static Instances generateEmptyNominalDataSet(String name, List<Pregao> historico, int window) {
        if (window == 0) {
            return generateEmptyNominalDataSet(name, historico);
        } else if (window > 0) {

            //Creating Dataset
            //Attributes labels
            ArrayList<String> attLabels = new ArrayList<>();
            attLabels.add(V_ALTO);
            attLabels.add(V_MEDIO);
            attLabels.add(V_BAIXO);

            //Class labels
            ArrayList<String> classLabels = new ArrayList<>();
            classLabels.add(V_ALTA);
            classLabels.add(V_IGUAL);
            classLabels.add(V_BAIXA);

            //Atributes
            Attribute[] abertura = new Attribute[window+1];
            Attribute[] baixa = new Attribute[window+1];
            Attribute[] alta = new Attribute[window+1];
            Attribute[] volume = new Attribute[window+1];
            Attribute[] fechamento = new Attribute[window+1];

            //Header
            ArrayList<Attribute> attributes = new ArrayList<>();

            for (int i = window; i >= 0; i--) {
                abertura[window - i] = new Attribute("Abertura(" + (i * (-1)) + ")", attLabels);
                baixa[window - i] = new Attribute("Baixa(" + (i * (-1)) + ")", attLabels);
                alta[window - i] = new Attribute("Alta(" + (i * (-1)) + ")", attLabels);
                volume[window - i] = new Attribute("Volume(" + (i * (-1)) + ")", attLabels);
                fechamento[window - i] = new Attribute("Fechamento(" + (i * (-1)) + ")", classLabels);

                attributes.add(abertura[window - i]);
                attributes.add(baixa[window - i]);
                attributes.add(alta[window - i]);
                attributes.add(volume[window - i]);
                attributes.add(fechamento[window - i]);

            }

            //Dataset
            Instances dataset = new Instances(name, attributes, 0);

            //Defining class attribute
            dataset.setClassIndex(FECHAMENTO + (5 * window));

            return dataset;
        } else {
            System.out.println("[DISCRETIZATOR] Invalid window value. it should be positive! ... Returning null...");
            return null;
        }
    }

    private static List<double[]> getMeansAndStandardDeviation(List<Pregao> historico) {
        //Getting means
        double[] means = new double[4];
        for (double num : means) {
            num = 0;
        }

        for (int i = 0; i < historico.size(); i++) {
            //Abertura
            means[0] += historico.get(i).getAbertura();
            //Baixa
            means[1] += historico.get(i).getBaixa();
            //Alta
            means[2] += historico.get(i).getAlta();
            //Volume
            means[3] += historico.get(i).getVolume();
        }

        for (double num : means) {
            num = num / historico.size();
        }

        //Getting standard Deviations
        double[] sds = new double[4];

        for (double num : sds) {
            num = 0;
        }

        for (int i = 0; i < historico.size(); i++) {
            //Abertura
            sds[0] += Math.pow(historico.get(i).getAbertura() - means[0], 2);
            //Baixa
            sds[1] += Math.pow(historico.get(i).getBaixa() - means[1], 2);
            //Alta
            sds[2] += Math.pow(historico.get(i).getAlta() - means[2], 2);
            //Volume
            sds[3] += Math.pow(historico.get(i).getVolume() - means[3], 2);
        }

        for (double num : sds) {
            num = Math.sqrt(num / historico.size());
        }

        //Returning results..
        List<double[]> result = new ArrayList<>();
        result.add(means);
        result.add(sds);

        return result;
    }
}
