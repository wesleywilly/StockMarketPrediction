/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamining;

import java.util.ArrayList;
import java.util.List;
import smile.Network;
import weka.core.Instances;

/**
 *
 * @author wesley
 */
public class BayesNet {

    private static final int DAILY_NAIVE = 0;

    private Network net;
    private String classAttribute;
    private List<List<String>> attributes;

    public BayesNet(Instances dataset, int type) {
        net = new Network();

        //Nomes dos atributos
        classAttribute = dataset.attribute(dataset.numAttributes() - 1).name();

        attributes = new ArrayList<>();
        for (int i = 0; i < dataset.numAttributes() - 1; i++) {
            String attribute = dataset.attribute(i).name();

            boolean groupFound = false;

            for (int j = 0; j < attributes.size() && !groupFound; j++) {
                String groupName = attributes.get(j).get(0).substring(0, attributes.get(j).get(0).indexOf("M"));
                if (attribute.contains(groupName)) {
                    attributes.get(j).add(attribute);
                    groupFound = true;
                }
            }

            if (!groupFound) {
                List<String> attributeGroup = new ArrayList<>();
                attributeGroup.add(attribute);
                attributes.add(attributeGroup);
            }

        }

        //Nó pai
        net.addNode(Network.NodeType.Cpt, classAttribute);
        for (int i = 0; i < dataset.attribute(classAttribute).numValues(); i++) {
            net.addOutcome(classAttribute, dataset.attribute(classAttribute).value(i));
        }
        net.deleteOutcome(classAttribute, 0);
        net.deleteOutcome(classAttribute, 0);

        //Nós filhos
        for (List<String> group : attributes) {
            for (String attribute : group) {
                net.addNode(Network.NodeType.Cpt, attribute);
                for (int i = 0; i < dataset.attribute(attribute).numValues(); i++) {
                    net.addOutcome(attribute, dataset.attribute(attribute).value(i));
                }
                net.deleteOutcome(attribute, 0);
                net.deleteOutcome(attribute, 0);
            }
        }
        
        //Arcos
        if(type >= 0){
            //Daily Naive
            List<String> lastGroup = attributes.get(attributes.size()-1);
            for(int i=0; i<lastGroup.size();i++){
                if((i+1)<lastGroup.size()){
                    net.addArc(lastGroup.get(i), lastGroup.get(i));
                }
                for(int j=0; j<attributes.size()-1;j++){
                    net.addArc(attributes.get(j).get(i), lastGroup.get(i));
                }
            }
            net.addArc(lastGroup.get(lastGroup.size()-1), classAttribute);
            
        }
        
        /* PROBABILIDADES */
        
        /*
        for(String attribute: net.getAllNodeIds()){
            List<Double> probabilities = new ArrayList<>();
            
            for(String parent: net.getParentIds(attribute)){
                for(int i=0; i<net.getOutcomeCount(parent);i++){
                    for(int j=0;j<net.getOutcomeCount(attribute);j++){
                        
                        int child_index = 0;
                        int parent_index = 0;
                        
                        for(int k=0; k<dataset.numAttributes();k++){
                            if(dataset.attribute(k).name().equals(attribute)){
                                child_index = k;
                            }
                            if(dataset.attribute(k).name().equals(parent)){
                                parent_index = k;
                            }
                        }
                        
                        
                        double p=0;
                        for(int l =0 ; l<dataset.numInstances();l++){
                            
                            
                            
                            if(dataset.instance(l).value(parent_index) == i 
                                    && dataset.instance(l).value(child_index) == j){
                                p+=1;
                            }
                        }
                        p = p/dataset.numInstances();
                        probabilities.add(p);
                        
                    }
                }
            }
            double[] definition = new double[probabilities.size()];
            for(int i =0 ; i<probabilities.size();i++){
                definition[i] = probabilities.get(i);
            }
            net.setNodeDefinition(attribute, definition);
        }*/
        
      net.writeFile("DNB-"+dataset.relationName()+".xdsl");
        
    }
    
    

}
