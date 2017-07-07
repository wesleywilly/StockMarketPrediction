
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
    private static final String COMPANY = "Vale S/A";
    private static final String QUOTE = "VALE5";
    private static final String DATASET_DIR = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/dataset/";
    private static final String EXPERIMENTS_DIR = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/experiments/";
    private static final String RAW_PATH = "/home/wesley/git_repository/StockMarketPrediction/StockMarket/rawdata/VALE.csv";
    
    private static final String DATASETS = "datasets/";
    private static final String NETS = "nets/";
    private static final String RESULTS = "results/";
    
    private static final int MAX_WINDOWS = 15;
    private static final int MAX_TEST = 35;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        
    }
    
    private static Network generate(){
        // WIKI: https://dslpitt.org/genie/wiki/JSMILE_and_Smile.NET
        //Tutorial1: https://dslpitt.org/genie/wiki/Java_Tutorial_1:_Creating_a_Bayesian_Network

        Network net = new Network();

        //Nó pai
        net.addNode(Network.NodeType.Cpt, "Fechamento");
        net.setOutcomeId("Fechamento", 0, "Baixa");
        net.setOutcomeId("Fechamento", 1, "Alta");

        //Nós filhos
        net.addNode(Network.NodeType.Cpt, "Abertura");
        net.setOutcomeId("Abertura", 0, "Baixa");
        net.setOutcomeId("Abertura", 1, "Igual");
        net.addOutcome("Abertura", "Alta");

        net.addNode(Network.NodeType.Cpt, "Minima");
        net.setOutcomeId("Minima", 0, "Baixa");
        net.setOutcomeId("Minima", 1, "Igual");
        net.addOutcome("Minima", "Alta");

        net.addNode(Network.NodeType.Cpt, "Maxima");
        net.setOutcomeId("Maxima", 0, "Baixa");
        net.setOutcomeId("Maxima", 1, "Igual");
        net.addOutcome("Maxima", "Alta");

        net.addNode(Network.NodeType.Cpt, "Volume");
        net.setOutcomeId("Volume", 0, "Baixa");
        net.setOutcomeId("Volume", 1, "Igual");
        net.addOutcome("Volume", "Alta");

        //Arestas
        net.addArc("Fechamento", "Abertura");
        net.addArc("Fechamento", "Minima");
        net.addArc("Fechamento", "Maxima");
        net.addArc("Fechamento", "Volume");

        //Definindo probabilidades dos valores no nó pai
        double[] definitionFechamento = {0.2, 0.8};
        net.setNodeDefinition("Fechamento", definitionFechamento);

        //Definindo probabilidades dos valores nos nós filhos
        /*
            SEQUÊNCIA
        
            P(<Nó Filho> = State0 | <Nó Pai> = State0) = p1 |
            P(<Nó Filho> = State1 | <Nó Pai> = State0) = p2 |   a soma das probabilidades(p1 + p2 + ... +pn) precisa ser = 1
            [...]                                           |
            P(<Nó Filho> = Staten | <Nó Pai> = State0) = pn |
        
            P(<Nó Filho> = State0 | <Nó Pai> = State1) = q1 |
            P(<Nó Filho> = State1 | <Nó Pai> = State1) = q2 |
            [...]                                           |
            P(<Nó Filho> = Staten | <Nó Pai> = State1) = qn |
        
            [...]
         */
        double[] definitionAbertura = {0.3, 0.3, 0.4, 0.4, 0.4, 0.2};
        net.setNodeDefinition("Abertura", definitionAbertura);

        double[] definitionMinima = {0.3, 0.3, 0.4, 0.4, 0.4, 0.2};
        net.setNodeDefinition("Minima", definitionMinima);

        double[] definitionMaxima = {0.3, 0.3, 0.4, 0.4, 0.4, 0.2};
        net.setNodeDefinition("Maxima", definitionMaxima);

        double[] definitionVolume = {0.3, 0.3, 0.4, 0.4, 0.4, 0.2};
        net.setNodeDefinition("Volume", definitionVolume);

        //Definindo atributos visuais
        net.setNodePosition("Fechamento", ((30 + 50) * 3), ((30 + 50) * 3), 50, 30);
        net.setNodePosition("Abertura", ((30 + 50) * 1), 90, 50, 30);
        net.setNodePosition("Minima", ((30 + 50) * 2), 90, 50, 30);
        net.setNodePosition("Maxima", ((30 + 50) * 4), 90, 50, 30);
        net.setNodePosition("Volume", ((30 + 50) * 5), 90, 50, 30);

        try {
            net.writeFile("test.xdsl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return net;
    }
    
}
