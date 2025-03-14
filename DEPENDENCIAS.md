# Documentação das Dependências

A aplicação utiliza diversas bibliotecas e frameworks para garantir performance, escalabilidade e boas práticas. Abaixo está a descrição de cada uma delas:

## Frameworks Principais

- **Spring Boot 3.4.3** - Framework para desenvolvimento rápido de aplicações Java.
- **Spring Data JPA** - Facilita a persistência de dados utilizando ORM.
- **Spring Data MongoDB** - Integração com MongoDB.
- **Spring AMQP** - Comunicação com RabbitMQ.
- **Spring Cloud OpenFeign** - Cliente HTTP para comunicação entre serviços.

## Banco de Dados

- **OracleDB** - Banco de dados relacional principal.
- **MongoDB** - Banco NoSQL utilizado para armazenar documentos.
- **Flyway** - Gerenciamento de versionamento de banco de dados.

## Testes

- **JUnit** - Framework de testes unitários.
- **Spring Boot Starter Test** - Conjunto de dependências para testes com Spring.

## Outros

- **MapStruct** - Geração automática de mapeamentos entre objetos.
- **Logback + Logstash** - Gerenciamento e formatação de logs.
- **Micrometer** - Monitoramento e métricas.
- **Jackson Databind** - Serialização e desserialização de JSON.

## Como as Bibliotecas São Utilizadas

| Biblioteca | Finalidade |
|------------|-----------|
| Spring Boot | Framework principal |
| Spring Data JPA | ORM para banco relacional |
| Spring Data MongoDB | Integração com MongoDB |
| Spring AMQP | Comunicação com RabbitMQ |
| Spring Cloud OpenFeign | Cliente HTTP para APIs |
| Flyway | Controle de versões do banco |
| Logback + Logstash | Logs estruturados |
| Micrometer | Monitoramento |
| JUnit | Testes unitários |
| MapStruct | Conversão entre DTOs e entidades |

Para mais detalhes, consulte a documentação oficial de cada biblioteca.

