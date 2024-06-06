Projeto Contas a Pagar

> Descri��o

   Este projeto � uma aplica��o Spring Boot para gerenciamento de contas a pagar, que inclui funcionalidades como cria��o, 
   atualiza��o, consulta e importa��o de contas via CSV. Utiliza Docker e Docker-Compose para a configura��o do ambiente de desenvolvimento.

> Tecnologias Utilizadas

   Java 17, Spring Boot, Spring Data JPA, Flyway, PostgreSQL, Docker e Docker-Compose.

> Configura��o do Ambiente

Subir aplica��o: docker-compose up --build

> Configura��o do Postman

Para autenticar as chamadas no Postman, use as seguintes credenciais:
Usu�rio: user
Senha: password

> Chamadas das APIs:

1. Criar Conta
   M�todo: POST
   URL: http://localhost:8080/api/conta
   Body:
   {
   "descricao": "Conta de luz",
   "valor": 150.00,
   "dataVencimento": "2024-07-01",
   "situacao": "PENDENTE"
   }

2. Atualizar Conta
   M�todo: PUT
   URL: http://localhost:8080/api/conta/{id}
   Body:
   {
   "descricao": "Conta de luz atualizada",
   "valor": 150.00,
   "dataVencimento": "2024-07-15",
   "situacao": "PAGO"
   }

3. Alterar situa��o da conta
   M�todo: PUT
   URL: http://localhost:8080/api/conta/{id}/situacao
   Body:
   {
   "dataPagamento": "2024-06-06",
   "situacao": "PAGO"
   }

4. Importar CSV
   M�todo: POST
   URL: http://localhost:8080/api/conta/import
   Headers:
   Content-Type: multipart/form-data
   Body:
   Escolher um arquivo CSV.

   sugest�o/formato csv:
   dataVencimento;dataPagamento;valor;descricao;situacao
   30/06/2024;;2500;Aluguel;Pendente
   29/06/2024;01/06/2024;1500;Conta de luz;Pago
   29/06/2024;01/06/2024;1500;Conta de agua;Pago
   29/06/2024;01/06/2024;1500;Conta de telefone;Pago
   29/06/2024;;1500;internet;Pendente
   29/06/2024;02/06/2024;1500;fatura Bradesco;Pago
   29/06/2024;02/06/2024;1500;fatura Visa;Pago
   29/06/2024;02/06/2024;1500;fatura Mastercard;Pago
   29/06/2024;03/06/2024;1500;Aluguel tia;Pago
   29/06/2024;03/06/2024;1500;Aluguel pai;Pago
   29/06/2024;04/06/2024;1500;Previdencia;Pago
   29/06/2024;;1500;outra conta;Pendente
   29/06/2024;05/06/2024;1500;conta teste;Pago
   29/06/2024;06/06/2024;1500;conta teste 1;Pago
   29/06/2024;06/06/2024;1500;conta teste 2;Pago

5. Consultar Contas
   M�todo: GET
   URL: http://localhost:8080/api/conta?page=0&size=10

6. Consultar Conta filtrando por id
      M�todo: GET
      URL: http://localhost:8080/api/conta/{id}


7.  Consultar Contas a pagar (pendente) com filtro de data de vencimento e descri��o.
   M�todo: GET
   URL: http://localhost:8080/api/conta/pendente?dataInicio=2024-06-01&dataFim=2024-06-30?descricao=Aluguel

8. Consultar Total Pago por per�odo
    M�todo: GET
    URL: http://localhost:8080/api/conta/total-pago?dataInicio=2024-06-01&dataFim=2024-06-30 

