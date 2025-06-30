package Principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class registro {

    private static final String URL = "jdbc:mysql://localhost:3306/hoteldb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "@Volkadas2800,";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Gerenciamento de Hóspedes ---");
            System.out.println("1. Registrar Novo Hóspede");
            System.out.println("2. Ver Lista de Hóspedes");
            System.out.println("3. Remover Hóspede por CPF");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Por favor, digite um número.");
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    registrarNovoHospedeComQuarto(scanner);
                    break;
                case 2:
                    System.out.println("\n--- Lista de Hóspedes ---");
                    listarHospedes();
                    break;
                case 3:
                    System.out.println("\n--- Remover Hóspede ---");
                    System.out.print("Digite o CPF do hóspede a ser excluído: ");
                    String cpfExcluir = scanner.nextLine();
                    excluirHospede(cpfExcluir);
                    break;
                case 0:
                    System.out.println("Saindo do programa. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    public static void registrarNovoHospedeComQuarto(Scanner scanner) {
        System.out.println("\n--- Registrar Novo Hóspede ---");
        System.out.print("Nome do Hóspede: ");
        String nome = scanner.nextLine();
        System.out.print("CPF do Hóspede: ");
        String cpf = scanner.nextLine();

        System.out.print("Tipo do Quarto (Ex: Standard, Deluxe): ");
        String tipoQuarto = scanner.nextLine();

        System.out.print("Andar do Quarto: ");
        int andar = -1;
        try {
            andar = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Andar inválido. Usando 0.");
            andar = 0;
        }

        System.out.print("Número do Quarto (Ex: 101): ");
        String numeroQuartoInput = scanner.nextLine();

        System.out.print("Valor da Diária que o Hóspede irá pagar (Ex: 150.00): ");
        double valorDiariaPago = -1;
        try {
            valorDiariaPago = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor da diária inválido. Usando 0.00.");
            valorDiariaPago = 0.0;
        }

        int quartoId = -1;

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String sqlBuscarQuarto = "SELECT id, preco_diaria FROM quarto WHERE numero_quarto = ?";
            try (PreparedStatement stmtBuscarQuarto = conexao.prepareStatement(sqlBuscarQuarto)) {
                stmtBuscarQuarto.setString(1, numeroQuartoInput);
                try (ResultSet rs = stmtBuscarQuarto.executeQuery()) {
                    if (rs.next()) {
                        quartoId = rs.getInt("id");
                        double precoBaseQuartoExistente = rs.getDouble("preco_diaria");
                        System.out.println("Quarto '" + numeroQuartoInput + "' encontrado (ID: " + quartoId + ", Preço Base Registrado: " + precoBaseQuartoExistente + ").");
                    }
                }
            }

            if (quartoId == -1) {
                System.out.println("Quarto '" + numeroQuartoInput + "' não encontrado no sistema. Cadastrando novo quarto...");

                String sqlInsertQuarto = "INSERT INTO quarto (numero_quarto, tipo_quarto, andar, preco_diaria) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmtInsertQuarto = conexao.prepareStatement(sqlInsertQuarto, Statement.RETURN_GENERATED_KEYS)) {
                    stmtInsertQuarto.setString(1, numeroQuartoInput);
                    stmtInsertQuarto.setString(2, tipoQuarto);
                    stmtInsertQuarto.setInt(3, andar);
                    stmtInsertQuarto.setDouble(4, valorDiariaPago);

                    int linhasAfetadasQuarto = stmtInsertQuarto.executeUpdate();
                    if (linhasAfetadasQuarto > 0) {
                        try (ResultSet rsGeneratedKeys = stmtInsertQuarto.getGeneratedKeys()) {
                            if (rsGeneratedKeys.next()) {
                                quartoId = rsGeneratedKeys.getInt(1);
                                System.out.println("Quarto '" + numeroQuartoInput + "' cadastrado com sucesso (ID: " + quartoId + ").");
                            }
                        }
                    } else {
                        System.out.println("Erro ao cadastrar novo quarto. Nenhuma linha afetada. Registro de hóspede cancelado.");
                        return;
                    }
                }
            }

            if (quartoId != -1) {
                String sqlInsertHospede = "INSERT INTO hospede (nome, cpf, quarto_id, valor_diaria_pago) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmtInsertHospede = conexao.prepareStatement(sqlInsertHospede)) {
                    stmtInsertHospede.setString(1, nome);
                    stmtInsertHospede.setString(2, cpf);
                    stmtInsertHospede.setInt(3, quartoId);
                    stmtInsertHospede.setDouble(4, valorDiariaPago);

                    int linhasAfetadasHospede = stmtInsertHospede.executeUpdate();
                    if (linhasAfetadasHospede > 0) {
                        System.out.println("Hóspede '" + nome + "' registrado com sucesso no Quarto " + numeroQuartoInput + "!");
                    } else {
                        System.out.println("Erro ao registrar hóspede '" + nome + "'. Nenhuma linha afetada.");
                    }
                }
            } else {
                System.out.println("Não foi possível obter um ID de quarto válido. Registro de hóspede cancelado.");
            }

        } catch (SQLException e) {
            System.err.println("Erro SQL durante o registro do hóspede/quarto: " + e.getMessage());
            // e.printStackTrace();
        }
    }

    public static void listarHospedes() {
        String sql = "SELECT h.id, h.nome, h.cpf, h.valor_diaria_pago, " +
                "q.numero_quarto, q.tipo_quarto, q.andar " +
                "FROM hospede h LEFT JOIN quarto q ON h.quarto_id = q.id";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhum hóspede encontrado no banco de dados.");
                return;
            }

            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-15s %-18s %-15s %-15s %-10s%n", "ID", "Nome", "CPF", "Valor Pago", "Num. Quarto", "Tipo Quarto", "Andar");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                double valorDiariaPago = rs.getDouble("valor_diaria_pago");

                String numeroQuartoExibido = rs.getString("numero_quarto");
                String tipoQuarto = rs.getString("tipo_quarto");
                int andar = rs.getInt("andar");

                if (rs.wasNull()) {
                    numeroQuartoExibido = "N/A";
                    tipoQuarto = "N/A";
                    andar = 0;
                }

                System.out.printf("%-5d %-20s %-15s %-18.2f %-15s %-15s %-10d%n",
                        id, nome, cpf, valorDiariaPago, numeroQuartoExibido, tipoQuarto, andar);
            }
            System.out.println("-----------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.err.println("Erro SQL ao listar hóspedes: " + e.getMessage());
            // e.printStackTrace();
        }
    }

    public static void excluirHospede(String cpf) {
        String sql = "DELETE FROM hospede WHERE cpf = ?";

        try (Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Hóspede com CPF '" + cpf + "' excluído com sucesso!");
            } else {
                System.out.println("Nenhum hóspede encontrado com CPF '" + cpf + "' para exclusão.");
            }

        } catch (SQLException e) {
            System.err.println("Erro SQL ao excluir hóspede: " + e.getMessage());
            // e.printStackTrace();
        }
    }
}
