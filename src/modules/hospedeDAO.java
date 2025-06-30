package modules;

import Principal.conexaoDB;
import Principal.hospede;
import Principal.quarto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class hospedeDAO {

    public hospede salvar(hospede h) {
        String sql = "INSERT INTO Hospede (nome, cpf, quarto_id, valor_diaria_pago) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = conexaoDB.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, h.getNome());
            stmt.setString(2, h.getCpf());
            if (h.getQuartoOcupado() != null && h.getQuartoOcupado().getId() != 0) {
                stmt.setInt(3, h.getQuartoOcupado().getId()); // Pega o ID do quarto salvo
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER); // Se o quarto não tem ID (ainda não salvo)
            }
            stmt.setDouble(4, h.getValorDiariaPago());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    h.setId(rs.getInt(1)); // Define o ID gerado no objeto Hospede
                }
            }
            System.out.println("Hóspede " + h.getNome() + " salvo com ID " + h.getId());
            return h;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar hóspede: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            conexaoDB.closeConnection(conn);
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<hospede> listarTodos() {
        String sql = "SELECT h.id, h.nome, h.cpf, h.valor_diaria_pago, " +
                "q.id AS quarto_id, q.numero_quarto, q.tipo_quarto, q.andar, q.preco_diaria " +
                "FROM Hospede h LEFT JOIN Quarto q ON h.quarto_id = q.id";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<hospede> hospedes = new ArrayList<>();
        quartoDAO quartoDAO = new quartoDAO(); // Necessário para criar objetos Quarto

        try {
            conn = conexaoDB.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                quarto quartoOcupado = null;
                int quartoId = rs.getInt("quarto_id");
                if (!rs.wasNull()) { // Verifica se quarto_id não é NULL (ou seja, tem um quarto associado)
                    quartoOcupado = new quarto( // <--- Instanciando Quarto com 'Q' maiúsculo
                            quartoId,
                            rs.getString("numero_quarto"),
                            rs.getString("tipo_quarto"),
                            rs.getInt("andar"),
                            rs.getDouble("preco_diaria")
                    );
                }

                hospede h = new hospede(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        quartoOcupado,
                        rs.getDouble("valor_diaria_pago")
                );
                h.setId(rs.getInt("id")); // Define o ID do Hospede
                hospedes.add(h);
            }
            return hospedes;
        } catch (SQLException e) {
            System.err.println("Erro ao listar hóspedes: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            conexaoDB.closeConnection(conn);
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    // Adicione outros métodos se precisar (ex: buscarPorCpf, atualizar, excluir)
}
