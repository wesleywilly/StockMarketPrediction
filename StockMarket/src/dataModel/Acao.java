package dataModel;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author wesley
 */
public class Acao {
    
    private static final String EMPRESA = "empresa";
    private static final String SIMBOLO = "simbolo";
    private static final String HISTORICO = "historico";
    
    private String empresa;
    private String simbolo;
    private List<Pregao> historico;

    public Acao(String empresa, String simbolo, List<Pregao> historico) {
        this.empresa = empresa;
        this.simbolo = simbolo;
        this.historico = historico;
    }

    public Acao(JSONObject jCommodity) {
        this.empresa = (String) jCommodity.get(EMPRESA);
        this.simbolo = (String) jCommodity.get(SIMBOLO);
        JSONArrayToHistorico((JSONArray)jCommodity.get(HISTORICO));
    }
    
    public JSONObject toJSON(){
        JSONObject jCommodity = new JSONObject();
        jCommodity.put(EMPRESA, empresa);
        jCommodity.put(SIMBOLO, simbolo);
        jCommodity.put(HISTORICO, historicoToJSONArray());
        return jCommodity;
    }
    
    
    private void JSONArrayToHistorico(JSONArray jHistorico){
        historico = new ArrayList<>();
        for(Object object: jHistorico){
            historico.add(new Pregao((JSONObject)object));
        }
    }
    
    private JSONArray historicoToJSONArray(){
        JSONArray jHistorico = new JSONArray();
        for(Pregao pregao: historico){
            jHistorico.add(pregao.toJSON());
        }
        return jHistorico;
    }
    

    public String getEmpresa() {
        return empresa;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public List<Pregao> getHistorico() {
        return historico;
    }

    public void setHistorico(List<Pregao> historico) {
        this.historico = historico;
    }
    
    
    
    
    
}
