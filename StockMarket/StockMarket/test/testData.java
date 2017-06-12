
import dataModel.Acao;
import dataModel.Pregao;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wesley
 */
public class testData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Random r = new Random();
        List<Pregao> lp = new ArrayList<>();
        
        for(int i=0;i<10;i++){
            lp.add(new Pregao("d",r.nextDouble()*10,r.nextDouble()*10,r.nextDouble()*10,r.nextDouble()*10,r.nextDouble()*10,r.nextInt(1000000)));
        }

        Acao c = new Acao("Biomm S.A.", "BIOM3.SA", lp);
        
        System.out.println(c.toJSON());
        
        
        
    }
    
}
