/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author felip
 */
public class Cliente {
    private int id;
    private String nome;
    private String endereco;
    private String email;
    
    public int getId(){
        return id;
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getEndereco(){
        return endereco;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setId(int i){
        id = i;
    } 
    
    public void setNome(String n){
        nome = n;
    }
    
    public void setEndereco(String e){
        endereco = e;
    }
    
    public void setEmail(String em){
        email = em;
    }
}
