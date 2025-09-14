/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Cliente;

import conexao.Conexao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

/**
 *
 * @author felip / mheber
 */
public class ClienteDAO {

    private Conexao conexao;
    private Connection conn;

    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, endereco, email) VALUES(?,?,?)";

        try {
            PreparedStatement start = this.conn.prepareStatement(sql);

            start.setString(1, cliente.getNome());
            start.setString(2, cliente.getEndereco());
            start.setString(3, cliente.getEmail());

            start.execute();

            System.out.println("Inserção feita com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir cliente: " + ex.getMessage());
        }
    }

    public Cliente getCliente(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(); 
            Cliente c = new Cliente();

            rs.first();
            c.setId(id);
            c.setNome(rs.getString("nome"));
            c.setEndereco(rs.getString("endereco"));
            c.setEmail(rs.getString("String"));
            return c;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar cliente: " + ex.getMessage());
            return null;
        }
    }
    
        public void editar(Cliente cliente) {
        try {
            String sql = "UPDATE clientes set nome=?, endereco=?, email=? WHERE id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getEmail());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar cliente: " + ex.getMessage());
        }
    }
        
        public void excluir(int id) {
        try {
            String sql = "delete from clientes WHERE id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir cliente: " + ex.getMessage());
        }
    }
}
