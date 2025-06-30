package Principal; // <--- Pacote correto!

public class quarto { // <--- Começa com letra maiúscula por convenção
    private int id;
    private String numeroQuarto;
    private String tipoQuarto;
    private int andar;
    private double precoDiaria;

    // Construtor completo (para quartos vindos do BD)
    public quarto(int id, String numeroQuarto, String tipoQuarto, int andar, double precoDiaria) {
        this.id = id;
        this.numeroQuarto = numeroQuarto;
        this.tipoQuarto = tipoQuarto;
        this.andar = andar;
        this.precoDiaria = precoDiaria;
    }

    // Construtor para novos quartos (sem ID inicial, o BD irá gerar)
    public quarto(String numeroQuarto, String tipoQuarto, int andar, double precoDiaria) {
        this(0, numeroQuarto, tipoQuarto, andar, precoDiaria); // Chama o construtor completo com ID = 0
    }

    // Getters
    public int getId() { return id; }
    public String getNumeroQuarto() { return numeroQuarto; }
    public String getTipoQuarto() { return tipoQuarto; }
    public int getAndar() { return andar; }
    public double getPrecoDiaria() { return precoDiaria; }

    // Setters
    public void setId(int id) { this.id = id; } // Necessário para definir o ID gerado pelo BD
    public void setNumeroQuarto(String numeroQuarto) { this.numeroQuarto = numeroQuarto; }
    public void setTipoQuarto(String tipoQuarto) { this.tipoQuarto = tipoQuarto; }
    public void setAndar(int andar) { this.andar = andar; }
    public void setPrecoDiaria(double precoDiaria) { this.precoDiaria = precoDiaria; }

    @Override
    public String toString() {
        return "Quarto{" +
                "id=" + id +
                ", numeroQuarto='" + numeroQuarto + '\'' +
                ", tipoQuarto='" + tipoQuarto + '\'' +
                ", andar=" + andar +
                ", precoDiaria=" + String.format("%.2f", precoDiaria) + // Formata o valor
                '}';
    }
}