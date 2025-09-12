/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prjlabbd;

import conexao.Conexao;

/**
 *
 * @author felip
 */
public class PrjLabBd {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Conexao c = new Conexao();
        c.getConexao();
    }
    
}
