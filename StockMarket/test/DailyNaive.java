
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.net.ParentSet;
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
public class DailyNaive extends weka.classifiers.bayes.net.search.SearchAlgorithm {
    
    private static final int numChilds = 4;
    
    public void buildStructure(BayesNet bayesNet, Instances instances){
        
        int numAttributes = instances.numAttributes(); //numero de atributos
        int numDays = (int)(numAttributes-1)/5;
        
        int iDay = 0;
        int iChild = 0;
        for(int iAttributes = 0; iAttributes< numAttributes; iAttributes++){
            ParentSet parentSet  = new ParentSet();
            
            if(iChild<numChilds){
                parentSet.addParent(iAttributes+numChilds-iChild, instances);
            }else if(iChild == numChilds){
                if(iDay == numDays-1){
                    parentSet.addParent(iAttributes+1, instances);
                }else{
                    parentSet.addParent(iAttributes+numChilds+1, instances);
                }
            }
            
            bayesNet.getParentSet(iAttributes).copy(parentSet);
            
            //Step
            if(iChild >= numChilds){
                iChild =0;
                if(iDay >= numDays){
                    iDay = 0;
                }else{
                    iDay++;
                }
            }else{
                iChild++;
            }
        }
        
        
        
        
        //bayesNet.getParentSet(iAttribute); //pega o conjunto de parentes de um determinado atributo
        
        
        
        
    }
    
    public void search (BayesNet bayesNet, Instances instances){
        
    }
    
}
