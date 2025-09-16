package dao;

import beans.Nota;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mheber
 */
public class NotaDAO {

    private Conexao conexao;
    private Connection conn;

    public void inserir(Nota nota) {
        String sql = "INSERT INTO cabecalho_nota(data_venda, cliente_id, valor_total) VALUES(?,?,?)";

        try {
            PreparedStatement start = this.conn.prepareStatement(sql);

            start.setString(1, nota.getData());
            start.setInt(2, nota.getCliente());
            start.setDouble(3, nota.getValor());
            start.execute();

            System.out.println("Inserção feita com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir cliente: " + ex.getMessage());
        }
    }

    public Nota getNota(int id) {
        String sql = "SELECT * FROM cabecalho_nota WHERE id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(
                    sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Nota c = new Nota();

            rs.first();
            c.setId(id);
            c.setData(rs.getString("data"));
            c.setCliente(rs.getInt("Cliente"));
            c.setValor(rs.getDouble("Valor"));
            return c;
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar nota: " + ex.getMessage());
            return null;
        }
    }

    public void editar(Nota nota) {
        try {
            String sql = "UPDATE cabecalho_nota set data_venda=?, cleinte_id=?, valor_total=? WHERE id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nota.getData());
            stmt.setInt(2, nota.getCliente());
            stmt.setDouble(3, nota.getValor());
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar nota: " + ex.getMessage());
        }
    }

    public void excluir(int id) {
        try {
            String sql = "delete from cabecalho_nota WHERE id=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir nota: " + ex.getMessage());
        }
    }
}
