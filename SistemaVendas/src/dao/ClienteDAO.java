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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author felip / mheber
 */
public class ClienteDAO {

    private Conexao conexao;
    private Connection conn;

    public ClienteDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

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

// Substitua seu método getCliente por este em ClienteDAO.java
    public Cliente getCliente(int id) {
        String sql = "SELECT * FROM clientes WHERE id_C = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id_C")); 
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setEmail(rs.getString("email")); 

                return c; 
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao consultar cliente: " + ex.getMessage());
            return null;
        }

        return null; 
    }

    public void editar(Cliente cliente) {
        try {
            String sql = "UPDATE clientes set nome=?, endereco=?, email=? WHERE id_C = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getEmail());
            stmt.setInt(4, cliente.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar cliente: " + ex.getMessage());
        }
    }

    public void excluir(int id) {
        try {
            String sql = "delete from clientes WHERE id_C = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir cliente: " + ex.getMessage());
        }
    }

    public List<Cliente> getClientes() {
        String sql = "SELECT * FROM clientes";

        List<Cliente> listaClientes = new ArrayList<>();

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();

                c.setId(rs.getInt("id_C"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setEmail(rs.getString("email"));
                listaClientes.add(c);

            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar lisa de clientes" + e.getMessage());
            return null;
        }
        return listaClientes;
    }

}
