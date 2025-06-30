package Principal;

public class Main {

    public static void main(String[] args) {

        quarto meuQuarto = new quarto(1, "101", "Standard", 1, 150.00); // <-- Note 'quarto' com 'q' minúsculo, conforme o seu arquivo
        System.out.println("Informações do Quarto: " + meuQuarto);
        System.out.println("Número do Quarto: " + meuQuarto.getNumeroQuarto());
        System.out.println("Preço da Diária: " + meuQuarto.getPrecoDiaria());

        meuQuarto.setPrecoDiaria(165.50);
        System.out.println("Novo preço da Diária: " + meuQuarto.getPrecoDiaria());

    }

}