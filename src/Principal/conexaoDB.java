package Principal; // Este pacote deve corresponder ao caminho src/Principal/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexaoDB {
    // Altere "hoteldb" se o nome do seu banco de dados no Workbench for diferente
    private static final String URL = "jdbc:mysql://localhost:3306/hoteldb";
    private static final String USER = "root"; // Seu usuário do MySQL Workbench
    private static final String PASSWORD = "sua_senha"; // <-- COLOQUE SUA SENHA AQUI!

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // System.out.println("Conexão com o banco de dados estabelecida com sucesso!"); // Linha para depuração, pode remover
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC do MySQL não encontrado. Certifique-se de que o JAR está no classpath (adicionado como biblioteca).");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                // System.out.println("Conexão com o banco de dados fechada."); // Linha para depuração, pode remover
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}