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

    private static final int YEAR = 240;

    /**
     * Transform numeric values to nominal using mean and standard deviation
     *
     * @param original
     * @return Weka Data Set
     */
    private static Instances discretize(Acao original) {
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

    public static Instances discretize(Acao original, int window) {
        //Creating Dataset
        Instances dataset = generateEmptyNominalDataSet(original.getSimbolo() + "(" + window + ")", original.getHistorico(), window);

        for (int i = window + 1; i < original.getHistorico().size(); i++) {
            double[] values = new double[5 * (window + 1) - 4];
            boolean equal = false;

            //Window
            for (int j = 0; j <= window; j++) {
                if (j == window) {
                    if (original.getHistorico().get(i - (window - j)).getFechamento() > original.getHistorico().get(i - (window - j)).getAbertura()) {
                        values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_ALTA);
                    } else if (original.getHistorico().get(i - (window - j)).getFechamento() < original.getHistorico().get(i - (window - j)).getAbertura()) {
                        values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_BAIXA);
                    } else {
                        equal = true;
                    }
                } else {

                    if (i > 0) {

                        //Abertura
                        if (original.getHistorico().get(i - (window - j)).getAbertura() > original.getHistorico().get(i - (window - j) - 1).getAbertura()) {
                            values[ABERTURA + (5 * j)] = dataset.attribute(ABERTURA).indexOfValue(V_ALTA);
                        } else if (original.getHistorico().get(i - (window - j)).getAbertura() < original.getHistorico().get(i - (window - j) - 1).getAbertura()) {
                            values[ABERTURA + (5 * j)] = dataset.attribute(ABERTURA).indexOfValue(V_BAIXA);
                        } else {
                            values[ABERTURA + (5 * j)] = dataset.attribute(ABERTURA).indexOfValue(V_IGUAL);
                        }

                        //Baixa
                        if (original.getHistorico().get(i - (window - j)).getBaixa() > original.getHistorico().get(i - (window - j) - 1).getBaixa()) {
                            values[BAIXA + (5 * j)] = dataset.attribute(BAIXA).indexOfValue(V_ALTA);
                        } else if (original.getHistorico().get(i - (window - j)).getBaixa() < original.getHistorico().get(i - (window - j) - 1).getBaixa()) {
                            values[BAIXA + (5 * j)] = dataset.attribute(BAIXA).indexOfValue(V_BAIXA);
                        } else {
                            values[BAIXA + (5 * j)] = dataset.attribute(BAIXA).indexOfValue(V_IGUAL);
                        }

                        //Alta
                        if (original.getHistorico().get(i - (window - j)).getAlta() > original.getHistorico().get(i - (window - j) - 1).getAlta()) {
                            values[ALTA + (5 * j)] = dataset.attribute(ALTA).indexOfValue(V_ALTA);
                        } else if (original.getHistorico().get(i - (window - j)).getAlta() < original.getHistorico().get(i - (window - j) - 1).getAlta()) {
                            values[ALTA + (5 * j)] = dataset.attribute(ALTA).indexOfValue(V_BAIXA);
                        } else {
                            values[ALTA + (5 * j)] = dataset.attribute(ALTA).indexOfValue(V_IGUAL);
                        }

                        //Volume
                        if (original.getHistorico().get(i - (window - j)).getVolume() > original.getHistorico().get(i - (window - j) - 1).getVolume()) {
                            values[VOLUME + (5 * j)] = dataset.attribute(VOLUME).indexOfValue(V_ALTA);
                        } else if (original.getHistorico().get(i - (window - j)).getVolume() < original.getHistorico().get(i - (window - j) - 1).getVolume()) {
                            values[VOLUME + (5 * j)] = dataset.attribute(VOLUME).indexOfValue(V_BAIXA);
                        } else {
                            values[VOLUME + (5 * j)] = dataset.attribute(VOLUME).indexOfValue(V_IGUAL);
                        }

                        //Fechamento
                        if (original.getHistorico().get(i - (window - j)).getFechamento() > original.getHistorico().get(i - (window - j)).getAbertura()) {
                            values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_ALTA);
                        } else if (original.getHistorico().get(i - (window - j)).getFechamento() < original.getHistorico().get(i - (window - j)).getAbertura()) {
                            values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_BAIXA);
                        } else {
                            values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
                        }
                    }
                }
            }
            if (!equal) {
                dataset.add(new DenseInstance(1.0, values));
            }
        }
        return dataset;
    }

    /**
     *
     * @param original
     * @param window
     * @param use_windows_for_means_and_standard_deviation
     * @param equal_instances
     * @return
     */
    public static Instances discretize(Acao original, int window, boolean use_windows_for_means_and_standard_deviation, boolean equal_instances) {
        //Creating Dataset
        Instances dataset = generateEmptyNominalDataSet(original.getSimbolo() + "(" + window + ")", original.getHistorico(), window, equal_instances);

        //Getting Means and Standard Deviation of all data
        List<double[]> msd;// = getMeansAndStandardDeviation(original.getHistorico());

        //Discretizing and inserting instances
        for (int i = window + 1; i < original.getHistorico().size(); i++) {
            double[] values = new double[5 * (window + 1) - 4];
            if (use_windows_for_means_and_standard_deviation) {
                msd = getMeansAndStandardDeviation(original.getHistorico().subList(i - window, i));
            } else {
                //Getting Means and Standard Deviation of last year
                if (i > YEAR) {
                    msd = getMeansAndStandardDeviation(original.getHistorico().subList(i - YEAR, i));
                } else {
                    msd = getMeansAndStandardDeviation(original.getHistorico().subList(0, i));
                }
            }
            boolean equal = false;
            //Window
            for (int j = 0; j <= window; j++) {
                equal = false;
                if (j == window) {
                    //Fechamento
                    if (i > 0) {
                        if (original.getHistorico().get(i - (window - j)).getFechamento() > original.getHistorico().get(i - (window - j) - 1).getFechamento()) {
                            values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_ALTA);
                        } else if (original.getHistorico().get(i - (window - j)).getFechamento() < original.getHistorico().get(i - (window - j) - 1).getFechamento()) {
                            values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_BAIXA);
                        } else {
                            if (equal_instances) {
                                values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
                            } else {
                                values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_BAIXA);
                                equal = true;
                            }

                        }
                    } else {
                        if (equal_instances) {
                            values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
                        } else {
                            values[FECHAMENTO + (5 * j) - 4] = dataset.attribute(FECHAMENTO).indexOfValue(V_BAIXA);
                            equal = true;
                        }
                    }

                } else {
                    //Abertura
                    if (original.getHistorico().get(i - (window - j)).getAbertura() > msd.get(0)[ABERTURA] + msd.get(1)[ABERTURA]) {
                        values[ABERTURA + (5 * j)] = dataset.attribute(ABERTURA).indexOfValue(V_ALTO);
                    } else if (original.getHistorico().get(i - (window - j)).getAbertura() < msd.get(0)[ABERTURA] - msd.get(1)[ABERTURA]) {
                        values[ABERTURA + (5 * j)] = dataset.attribute(ABERTURA).indexOfValue(V_BAIXO);
                    } else {
                        values[ABERTURA + (5 * j)] = dataset.attribute(ABERTURA).indexOfValue(V_MEDIO);
                    }

                    //Baixa
                    if (original.getHistorico().get(i - (window - j)).getBaixa() > msd.get(0)[BAIXA] + msd.get(1)[BAIXA]) {
                        values[BAIXA + (5 * j)] = dataset.attribute(BAIXA).indexOfValue(V_ALTO);
                    } else if (original.getHistorico().get(i - (window - j)).getBaixa() < msd.get(0)[BAIXA] - msd.get(1)[BAIXA]) {
                        values[BAIXA + (5 * j)] = dataset.attribute(BAIXA).indexOfValue(V_BAIXO);
                    } else {
                        values[BAIXA + (5 * j)] = dataset.attribute(BAIXA).indexOfValue(V_MEDIO);
                    }

                    //Alta
                    if (original.getHistorico().get(i - (window - j)).getAlta() > msd.get(0)[ALTA] + msd.get(1)[ALTA]) {
                        values[ALTA + (5 * j)] = dataset.attribute(ALTA).indexOfValue(V_ALTO);
                    } else if (original.getHistorico().get(i - (window - j)).getAlta() < msd.get(0)[ALTA] - msd.get(1)[ALTA]) {
                        values[ALTA + (5 * j)] = dataset.attribute(ALTA).indexOfValue(V_BAIXO);
                    } else {
                        values[ALTA + (5 * j)] = dataset.attribute(ALTA).indexOfValue(V_MEDIO);
                    }

                    //Volume
                    if (original.getHistorico().get(i - (window - j)).getVolume() > msd.get(0)[VOLUME] + msd.get(1)[VOLUME]) {
                        values[VOLUME + (5 * j)] = dataset.attribute(VOLUME).indexOfValue(V_ALTO);
                    } else if (original.getHistorico().get(i - (window - j)).getVolume() < msd.get(0)[VOLUME] - msd.get(1)[VOLUME]) {
                        values[VOLUME + (5 * j)] = dataset.attribute(VOLUME).indexOfValue(V_BAIXO);
                    } else {
                        values[VOLUME + (5 * j)] = dataset.attribute(VOLUME).indexOfValue(V_MEDIO);
                    }

                    //Fechamento
                    if (i > 0) {
                        if (original.getHistorico().get(i - (window - j)).getFechamento() > original.getHistorico().get(i - (window - j) - 1).getFechamento()) {
                            values[FECHAMENTO + (5 * j)] = dataset.attribute(FECHAMENTO).indexOfValue(V_ALTA);
                        } else if (original.getHistorico().get(i - (window - j)).getFechamento() < original.getHistorico().get(i - (window - j) - 1).getFechamento()) {
                            values[FECHAMENTO + (5 * j)] = dataset.attribute(FECHAMENTO).indexOfValue(V_BAIXA);
                        } else {
                            values[FECHAMENTO + (5 * j)] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
                            equal = true;
                        }
                    } else {
                        values[FECHAMENTO + (5 * j)] = dataset.attribute(FECHAMENTO).indexOfValue(V_IGUAL);
                        equal = true;
                    }
                }
            }
            if (equal_instances) {
                dataset.add(new DenseInstance(1.0, values));
            } else if (!equal) {
                dataset.add(new DenseInstance(1.0, values));
            }

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
        if (window > 0) {
            //Creating Dataset
            //Attributes labels
            ArrayList<String> labels = new ArrayList<>();
            labels.add(V_ALTA);
            labels.add(V_BAIXA);
            labels.add(V_IGUAL);
            
            //Attributes labels
            ArrayList<String> class_labels = new ArrayList<>();
            class_labels.add(V_ALTA);
            class_labels.add(V_BAIXA);
           

            //Atributes
            Attribute[] abertura = new Attribute[window];
            Attribute[] baixa = new Attribute[window];
            Attribute[] alta = new Attribute[window];
            Attribute[] volume = new Attribute[window];
            Attribute[] fechamento = new Attribute[window + 1];

            //Header
            ArrayList<Attribute> attributes = new ArrayList<>();

            for (int i = window; i >= 0; i--) {
                if (i > 0) {
                    abertura[window - i] = new Attribute("Abertura" + (i * (-1)), labels);
                    baixa[window - i] = new Attribute("Baixa" + (i * (-1)), labels);
                    alta[window - i] = new Attribute("Alta" + (i * (-1)), labels);
                    volume[window - i] = new Attribute("Volume" + (i * (-1)), labels);
                    fechamento[window - i] = new Attribute("Fechamento" + (i * (-1)), labels);

                    attributes.add(abertura[window - i]);
                    attributes.add(baixa[window - i]);
                    attributes.add(alta[window - i]);
                    attributes.add(volume[window - i]);
                    attributes.add(fechamento[window - i]);
                } else {
                    fechamento[window - i] = new Attribute("Fechamento", class_labels);
                    attributes.add(fechamento[window - i]);
                }
            }

            //Dataset
            Instances dataset = new Instances(name, attributes, 0);

            //Defining class attribute
            dataset.setClassIndex(FECHAMENTO + (5 * window) - 4);

            return dataset;

        } else {
            System.out.println("[DISCRETIZATOR] Invalid window value. it should be positive! ... Returning null...");
            return null;
        }
    }

    private static Instances generateEmptyNominalDataSet(String name, List<Pregao> historico, int window, boolean equal_instances) {
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
            if (equal_instances) {
                classLabels.add(V_IGUAL);
            }

            classLabels.add(V_BAIXA);

            //Atributes
            Attribute[] abertura = new Attribute[window];
            Attribute[] baixa = new Attribute[window];
            Attribute[] alta = new Attribute[window];
            Attribute[] volume = new Attribute[window];
            Attribute[] fechamento = new Attribute[window + 1];

            //Header
            ArrayList<Attribute> attributes = new ArrayList<>();

            for (int i = window; i >= 0; i--) {
                if (i > 0) {
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
                } else {
                    fechamento[window - i] = new Attribute("Fechamento", classLabels);
                    attributes.add(fechamento[window - i]);
                }
            }

            //Dataset
            Instances dataset = new Instances(name, attributes, 0);

            //Defining class attribute
            dataset.setClassIndex(FECHAMENTO + (5 * window) - 4);

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

        for (int i = 0; i < means.length; i++) {
            means[i] = means[i] / historico.size();
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
