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
public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    
    public int getId(){
        return id;
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getDescricao(){
        return descricao;
    }
    
    public double getPreco(){
        return preco;
    }
    
    public int getEstoque(){
        return estoque;
    }
    
    public void setId(int i){
        id = i;
    } 
    
    public void setNome(String n){
        nome = n;
    }
    
    public void setDescricao(String d){
        descricao = d;
    }
    
    public void setPreco(double p){
        preco = p;
    }
    
    public void setEstoque(int e){
        estoque = e;
    }
}
