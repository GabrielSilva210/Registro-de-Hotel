package Principal; // <--- Pacote correto!

public class hospede {
    private int id;
    private String nome;
    private String cpf;
    private quarto quartoOcupado; // Referência ao objeto Quarto (com 'Q' maiúsculo)
    private double valorDiariaPago;

    // Construtor completo (para hóspedes vindos do BD)
    public hospede(int id, String nome, String cpf, quarto quartoOcupado, double valorDiariaPago) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.quartoOcupado = quartoOcupado;
        this.valorDiariaPago = valorDiariaPago;
    }


    public hospede(String nome, String cpf, quarto quartoOcupado, double valorDiariaPago) {
        this(0, nome, cpf, quartoOcupado, valorDiariaPago); // Chama o construtor completo com ID = 0
    }

    // Getters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public quarto getQuartoOcupado() { return quartoOcupado; }
    public double getValorDiariaPago() { return valorDiariaPago; }

    // Setters
    public void setId(int id) { this.id = id; } // Necessário para definir o ID gerado pelo BD
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setQuartoOcupado(quarto quartoOcupado) { this.quartoOcupado = quartoOcupado; }
    public void setValorDiariaPago(double valorDiariaPago) { this.valorDiariaPago = valorDiariaPago; }

    @Override
    public String toString() {
        return "Hóspede{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", quartoOcupado=" + (quartoOcupado != null ? quartoOcupado.getNumeroQuarto() + " (" + quartoOcupado.getTipoQuarto() + ")" : "N/A") +
                ", valorDiariaPago=" + String.format("%.2f", valorDiariaPago) +
                '}';
    }
}