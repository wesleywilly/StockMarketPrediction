/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONObject;

/**
 *
 * @author wesley
 */
public class Pregao {

    private static final String DATA = "data";
    private static final String ABERTURA = "abertura";
    private static final String FECHAMENTO = "fechamento";
    private static final String FECHAMENTO_AJUSTADO = "fechamento_ajustado";
    private static final String ALTA = "alta";
    private static final String BAIXA = "baixa";
    private static final String VOLUME = "volume";

    private String data;
    private double abertura;
    private double alta;
    private double baixa;
    private double fechamento;
    private double fechamentoAjustado;
    private long volume;

    public Pregao(String data, double abertura, double alta, double baixa, double fechamento,double fechamentoAjustado, long volume) {
        this.data = data;
        this.abertura = abertura;
        this.alta = alta;
        this.baixa = baixa;
        this.fechamento = fechamento;
        this.fechamentoAjustado = fechamentoAjustado;
        this.volume = volume;
    }

    public Pregao(JSONObject jPregao) {
        if (!jPregao.isEmpty()) {
            this.data = (String) jPregao.get(DATA);
            this.abertura = (double) jPregao.get(ABERTURA);
            this.fechamento = (double) jPregao.get(FECHAMENTO);
            this.fechamentoAjustado = (double) jPregao.get(FECHAMENTO_AJUSTADO);
            this.alta = (double) jPregao.get(ALTA);
            this.baixa = (double) jPregao.get(BAIXA);
            this.volume = (long) jPregao.get(VOLUME);
        } else {
            System.out.println("[PREGAO] Objeto nulo!");
        }
    }

    public JSONObject toJSON() {
        JSONObject jPregao = new JSONObject();
        jPregao.put(DATA, data);
        jPregao.put(ABERTURA, abertura);
        jPregao.put(FECHAMENTO, fechamento);
        jPregao.put(FECHAMENTO_AJUSTADO, fechamentoAjustado);
        jPregao.put(ALTA, alta);
        jPregao.put(BAIXA, baixa);
        jPregao.put(VOLUME, volume);

        return jPregao;
    }

    public double getAbertura() {
        return abertura;
    }

    public double getFechamento() {
        return fechamento;
    }

    public String getData() {
        return data;
    }

    public double getAlta() {
        return alta;
    }

    public double getFechamentoAjustado() {
        return fechamentoAjustado;
    }

    public double getBaixa() {
        return baixa;
    }

    public long getVolume() {
        return volume;
    }

}
