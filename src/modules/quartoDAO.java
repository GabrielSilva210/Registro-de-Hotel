package modules; // <--- Pacote correto!

import Principal.conexaoDB;
import Principal.quarto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class quartoDAO {

    public quarto salvar(quarto q) { // Retorna o quarto com o ID gerado pelo BD
        String sql = "INSERT INTO Quarto (numero_quarto, tipo_quarto, andar, preco_diaria) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = conexaoDB.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, q.getNumeroQuarto());
            stmt.setString(2, q.getTipoQuarto());
            stmt.setInt(3, q.getAndar());
            stmt.setDouble(4, q.getPrecoDiaria());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    q.setId(rs.getInt(1)); // Define o ID gerado no objeto Quarto
                }
            }
            System.out.println("Quarto " + q.getNumeroQuarto() + " salvo com ID " + q.getId());
            return q;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar quarto: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            conexaoDB.closeConnection(conn);
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public quarto buscarPorNumero(String numeroQuarto) {
        String sql = "SELECT * FROM Quarto WHERE numero_quarto = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        quarto q = null;

        try {
            conn = conexaoDB.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, numeroQuarto);
            rs = stmt.executeQuery();

            if (rs.next()) {
                q = new quarto( // <--- Instanciando Quarto com 'Q' maiúsculo
                        rs.getInt("id"),
                        rs.getString("numero_quarto"),
                        rs.getString("tipo_quarto"),
                        rs.getInt("andar"),
                        rs.getDouble("preco_diaria")
                );
            }
            return q;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar quarto por número: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            conexaoDB.closeConnection(conn);
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    // Adicione outros métodos se precisar (ex: buscarPorId, listarTodos, atualizar, excluir)
}