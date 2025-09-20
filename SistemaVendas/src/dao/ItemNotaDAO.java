package dao;

import beans.ItemNota;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author mhebe
 */
public class ItemNotaDAO {
    private Conexao conexao;
    private Connection conn;

    public ItemNotaDAO() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }

    public void inserirItem(ItemNota item) {
        String sql = "INSERT INTO itens_nota(nota_id, produto_id, quantidade, preco_unitario_venda) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, item.getNotaId());
            stmt.setInt(2, item.getProdutoId());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir item da nota: " + e.getMessage());
        }
    }
}
