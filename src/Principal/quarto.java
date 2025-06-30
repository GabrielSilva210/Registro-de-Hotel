package Principal;

public class quarto {

    private final int id;

    private final String numeroQuarto;

    private final String tipoQuarto;

    private final int andar;

    private double precoDiaria;

    public quarto(int id, String numeroQuarto, String tipoQuarto, int andar, double precoDiaria) {

        this.id = id;

        this.numeroQuarto = numeroQuarto;

        this.tipoQuarto = tipoQuarto;

        this.andar = andar;

        this.precoDiaria = precoDiaria;

    }

    public String getNumeroQuarto() { return numeroQuarto; }

    public double getPrecoDiaria() { return precoDiaria; }

    public void setPrecoDiaria(double precoDiaria) { this.precoDiaria = precoDiaria; }

    @Override

    public String toString() {

        return "Quarto{" +

                "id=" + id +

                ", numeroQuarto='" + numeroQuarto + '\'' +

                ", tipoQuarto='" + tipoQuarto + '\'' +

                ", andar=" + andar +

                ", precoDiaria=" + String.format("%.2f", precoDiaria) +

                '}';
    }
}