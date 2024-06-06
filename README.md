# Projeto Contas a Pagar

> Descrição

   Este projeto é uma aplicação Spring Boot para gerenciamento de contas a pagar, que inclui funcionalidades como criação, 
   atualização, consulta e importação de contas via CSV. Utiliza Docker e Docker-Compose para a configuração do ambiente de desenvolvimento.

> Tecnologias Utilizadas

   Java 17, Spring Boot, Spring Data JPA, Flyway, PostgreSQL, Docker e Docker-Compose.

> Configuração do Ambiente

Subir aplicação: docker-compose up --build

> Configuração do Postman

Para autenticar as chamadas no Postman, use as seguintes credenciais:
```
   Usuário: user
   Senha: password
```

> Chamadas das APIs:

1. Criar Conta
   Método: POST
   ```
   URL: http://localhost:8080/api/conta
   Body:
   {
   	"descricao": "Conta de luz",
       	"valor": 150.00,
   	"dataVencimento": "2024-07-01",
   	"situacao": "PENDENTE"
   }
   ```

3. Atualizar Conta
   Método: PUT
   
```
   URL: http://localhost:8080/api/conta/{id}
   Body:
      {
         "descricao": "Conta de luz atualizada",
         "valor": 150.00,
         "dataVencimento": "2024-07-15",
         "situacao": "PAGO"
      }
```
5. Alterar situação da conta
   Método: PUT
```
   URL: http://localhost:8080/api/conta/{id}/situacao
   Body:
      {
         "dataPagamento": "2024-06-06",
         "situacao": "PAGO"
      }
```
6. Importar CSV
   Método: POST
```
   URL: http://localhost:8080/api/conta/import
   Headers:
   Content-Type: multipart/form-data
   Body:
   Escolher um arquivo CSV.
```
 sugestão dados/formato csv:
``` 
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
```
7. Consultar Contas
   Método: GET
```
   URL: http://localhost:8080/api/conta?page=0&size=10
```

8. Consultar Conta filtrando por id
      Método: GET
```
      URL: http://localhost:8080/api/conta/{id}
 ```


9.  Consultar Contas a pagar (pendente) com filtro de data de vencimento e descrição.
   Método: GET
```
   URL: http://localhost:8080/api/conta/pendente?dataInicio=2024-06-01&dataFim=2024-06-30?descricao=Aluguel
```

11. Consultar Total Pago por período
    Método: GET
    ```
    URL: http://localhost:8080/api/conta/total-pago?dataInicio=2024-06-01&dataFim=2024-06-30
    ```

