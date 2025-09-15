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
import java.util.ArrayList;
import java.util.List;

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
        String sql = "INSERT INTO Produto (nome, descricao, preco_venda, estoque) VALUES(?,?,?,?)";

        try {
            PreparedStatement stat = this.conn.prepareStatement(sql);

            stat.setString(1, produto.getNome());
            stat.setString(2, produto.getDescricao());
            stat.setDouble(3, produto.getPreco());
            stat.setInt(4, produto.getEstoque());

            stat.execute();

            System.out.println("Inserção feita com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir produto: " + ex.getMessage());
        }
    }

    public Produto getProduto(int id) {;
        String sql = "SELECT * FROM produto WHERE id_P = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Produto p = new Produto();

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

    public List<Produto> getProdutos() {
        String sql = "SELECT * FROM produto";
        List<Produto> listarProduto = new ArrayList<>();

        try (PreparedStatement stmt = this.conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();

                p.setId(rs.getInt("id_P"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco_venda"));
                p.setEstoque(rs.getInt("estoque"));

                listarProduto.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao carregar lista de produtos: " + e.getMessage());
        }

        return listarProduto;
    }

    public void editar(Produto produto) {
        try {
            String sql = "UPDATE produto set nome=?, descricao=?, preco=?, estoque=? WHERE id_P=?";

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
        String sql = "DELETE FROM Produto WHERE id_P=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int linhas = stmt.executeUpdate();
            System.out.println("Linhas excluídas: " + linhas);
        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
        }
    }
}
