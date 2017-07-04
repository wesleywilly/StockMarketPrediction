
import smile.Network;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wesley
 */
public class BayesNet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // WIKI: https://dslpitt.org/genie/wiki/JSMILE_and_Smile.NET
        //Tutorial1: https://dslpitt.org/genie/wiki/Java_Tutorial_1:_Creating_a_Bayesian_Network
        
        Network net = new Network();
        net.addNode(Network.NodeType.Cpt, "Succexx");
        
        
    }
    
}
