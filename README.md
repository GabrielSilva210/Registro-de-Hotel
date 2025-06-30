# 🏨 Sistema de Registro de Hóspedes com MySQL

Este projeto Java é um sistema simples de gerenciamento de hóspedes com integração a um banco de dados MySQL. Ele permite **registrar**, **listar** e **remover hóspedes**, vinculando cada hóspede a um quarto.

---

## 📌 Funcionalidades

- ✅ Registrar novo hóspede com dados validados
- ✅ Cadastrar ou atualizar dados do quarto vinculado
- ✅ Listar todos os hóspedes com detalhes do quarto
- ✅ Remover hóspede pelo CPF

---

## 💻 Tecnologias utilizadas

- Java (JDK 17+)
- JDBC (Java Database Connectivity)
- MySQL
- IntelliJ IDEA (opcional)

---

## 🗃️ Estrutura dos arquivos
Hospedes/
│
├── src/Principal/
│ ├── Main.java # Classe para testar o objeto Quarto
│ ├── quarto.java # Classe que representa o Quarto
│ └── registro.java # Lógica principal de cadastro, listagem e exclusão

---

## 🛠️ Como executar

1. **Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
````

Configure o banco de dados MySQL:
Crie um banco chamado hoteldb e execute os seguintes comandos:
```
USE hoteldb;
SELECT * FROM hospede;

CREATE TABLE quarto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_quarto VARCHAR(10) NOT NULL UNIQUE,
    tipo_quarto VARCHAR(50),
    andar INT,
    preco_diaria DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'Disponível'
);

CREATE TABLE hospede (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    quarto_id INT,
    valor_diaria_pago DECIMAL(10,2),
    FOREIGN KEY (quarto_id) REFERENCES quarto(id)
);

CREATE TABLE pagamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT,
    data_pagamento DATE,
    valor_pago DECIMAL(10,2),
    forma_pagamento VARCHAR(50),
    FOREIGN KEY (id_reserva) REFERENCES reserva(id)
);

INSERT INTO quarto (numero_quarto, tipo_quarto, andar, preco_diaria)
VALUES
('101', 'Simples', 1, 100.00),
('150', 'Duplo', 2, 150.00),
('180', 'Luxo', 3, 250.00);

SELECT * FROM quarto
WHERE CAST(numero_quarto AS UNSIGNED) BETWEEN 100 AND 200;
```
Atualize as credenciais de acesso ao banco no arquivo registro.java:
```
private static final String URL = "jdbc:mysql://localhost:3306/hoteldb?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "SUA_SENHA_AQUI";
```

📌 Observações
O projeto usa LEFT JOIN para exibir dados de hóspedes com ou sem quartos.

Todos os campos são validados para garantir a consistência dos dados.

Se o quarto não existir, será criado automaticamente com os dados informados.

👨‍💻 Autor
Gabriel Silva – Desenvolvedor Backend em formação pelo programa Oracle ONE.
Linkedin - [https://www.linkedin.com/in/gabriel-gon%C3%A7alves-2337b526a/?originalSubdomain=br].



