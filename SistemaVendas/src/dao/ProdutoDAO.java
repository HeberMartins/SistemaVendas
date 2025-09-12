/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import beans.Produto;

import conexao.Conexao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

/**
 *
 * @author felip
 */
public class ProdutoDAO {

    private Conexao conexao;
    private Connection conn;

    public ProdutoDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public void inserir(Produto produto) {
        String sql = "INSERT INTO Produto (nome, descricao, preco_venda, estoque) VALUES(?,?,?)";

        try {
            PreparedStatement stat = this.conn.prepareStatement(sql);

            stat.setString(1, produto.getNome());
            stat.setString(2, produto.getDescricao());
            stat.setDouble(3, produto.getPreco());
            stat.setInt(4, produto.getEstoque());

            stat.execute();

            System.out.println("Inserção feita com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir pessoa: " + ex.getMessage());
        }
    }

    public Produto getProduto(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            // 1º parâmetro é o SQL
            // 2º parâmetro é o tipo do ResultSet -
            // ResultSet scroll, ou seja, o cursor se move para frente ou para trás.
            // Este tipo de ResultSet é sensível às alterações feitas no banco de dados, ou seja, as modificações feitas no banco de dados são refletidas no ResultSet.
            // 3º parâmetro é sobre os parâmetros de concorrência – pode ser "read only" ou atualizável

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery(); // obtenho o retorno da consulta e armazeno no ResultSet
            Produto p = new Produto();
            // Primeiramente, vamos posicionar o retorno da consulta (ResultSet) na primeira posição da consulta
            // Em alguns casos, a consulta terá mais de um resultado de retorno
            rs.first();
            p.setId(id);
            p.setNome(rs.getString("nome"));
            p.setDescricao(rs.getString("descricao"));
            p.setPreco(rs.getDouble("preco_venda"));
            p.setEstoque(rs.getInt("estoque"));
            return p;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar produto: " + ex.getMessage());
            return null;
        }
    }

    public void editar(Produto produto) {
        try {
            String sql = "UPDATE pessoa set nome=?, endereco=?, email=? WHERE id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getId());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar produto: " + ex.getMessage());
        }
    }

    public void excluir(int id) {
        try {
            String sql = "delete from produto WHERE id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir produto: " + ex.getMessage());
        }
    }
}
