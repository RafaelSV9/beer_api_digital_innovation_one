# Beer Stock API (Spring Boot) — TDD com JUnit e Mockito

Projeto laboratório para praticar **testes unitários** em uma API REST de gerenciamento de estoques de cerveja.
Inclui exemplos de:
- JUnit 5 + Mockito (testes de serviço e web);
- TDD para funcionalidades de **incremento** e **decremento** de estoque;
- MockMvc para testes de controlador;
- Jacoco para relatório de cobertura;
- H2 em memória para execução local simples.

## Requisitos
- Java 17+
- Maven 3.9+

## Rodando os testes
```bash
mvn clean test
# Relatório de cobertura: target/site/jacoco/index.html
```

## Subindo a API
```bash
mvn spring-boot:run
# API em http://localhost:8080
# H2 console: http://localhost:8080/h2-console
```

## Endpoints principais
- **POST** `/api/v1/beers` — cria cerveja
- **GET** `/api/v1/beers` — lista todas
- **GET** `/api/v1/beers/byName?name=IPA` — busca por nome
- **DELETE** `/api/v1/beers/{id}` — exclui por id
- **PATCH** `/api/v1/beers/{id}/increment?quantity=10` — incrementa estoque (TDD)
- **PATCH** `/api/v1/beers/{id}/decrement?quantity=5` — decrementa estoque (TDD)

### Exemplo de payload
```json
{
  "name": "IPA",
  "brand": "BrewCo",
  "max": 50,
  "quantity": 10
}
```

## Estratégia de Testes
- **BeerServiceTest**: cobre regras de negócio (duplicidade de nome, busca, exclusão, incremento e decremento com validações).
- **BeerControllerTest**: testa a camada web com `@WebMvcTest` e `MockBean` do serviço.
- Exceções específicas: `BeerAlreadyRegisteredException`, `BeerNotFoundException`, `BeerStockExceededException`, `BeerStockInsufficientException`.

## TDD na prática
1. Escreva um teste que falha (ex.: `increment_shouldThrow_whenExceedsMax`).  
2. Implemente o mínimo na `BeerService` para o teste passar.  
3. Refatore mantendo os testes verdes.

## Dicas
- Adapte os nomes de pacote para seu GitHub se quiser.
- Para facilitar avaliação no portfólio, rode `mvn test` e suba o badge/print do JaCoCo no README.
- Se quiser trocar para DTO com Bean Validation, adicione `spring-boot-starter-validation` e anote os campos.

Feito com ❤️ para estudos.
