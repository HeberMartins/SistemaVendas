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
public class Cabecalho {
    private int id;
    private String data;
    private int clienteId;
    private double valor;
    
    public int getId(){
        return id;
    }
    
    public String getData(){
        return data;
    }
    
    public int getCliente(){
        return clienteId;
    }
    
    public double getValor(){
        return valor;
    }
    
    public void setId(int i){
        id = i;
    }
    
    public void setData(String d){
        data = d;
    }
    
    public void setCliente(int cId){
        clienteId = cId;
    }
    
    public void setValor(double v){
        valor = v;
    }
}
