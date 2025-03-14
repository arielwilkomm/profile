# Configuração e Instalação

## Pré-requisitos

- **Java 17** instalado
- **Maven** instalado
- **Docker** (opcional, para rodar dependências)

## Instalação

1. Clone o repositório:

   ```sh
   git clone https://github.com/seu-usuario/profile.git
   cd profile
   ```

2. Configure o banco de dados Oracle e MongoDB:

    - Atualize `application.yml` com as credenciais corretas
    - Se estiver usando Docker, inicie os serviços:
      ```sh
      docker-compose up -d
      ```

3. Execute a aplicação:

   ```sh
   mvn spring-boot:run
   ```

## Docker

A aplicação pode ser executada em containers utilizando `docker-compose`. Abaixo estão os serviços configurados:

```yaml
version: '3'
services:
  oracle19c:
    image: container-registry.oracle.com/database/enterprise:19.3.0.0
    expose:
      - 1521
    ports:
      - 1521:1521
      - 5500:5500
    environment:
      ORACLE_PWD: system
      ORACLE_SID: xe
    restart: always
  rabbitmq:
    container_name: rabbitmq
    image: rabbitmq:management
    restart: always
    expose:
     - "5672"
    ports:
     - "15672:15672"
     - "5672:5672"
    networks:
      - default
  mongodb:
    container_name: mongodb
    image: mongo
    restart: always
    expose:
     - "27017"
    ports:
     - "27017:27017"
    networks:
      - default
```

Para subir os containers, execute:

```sh
docker-compose up -d
```

Para verificar os logs:

```sh
docker-compose logs -f
```

4. Acesse a API:

    - **Swagger UI**: `http://localhost:8080/swagger-ui.html`
    - **Swagger UI YAML**: `http://localhost:8080/v3/api-docs.yaml`
    - **Swagger UI JSON**: `http://localhost:8080/v3/api-docs`
    - **Atuadores**: `http://localhost:8080/actuator`

## Variáveis de Ambiente

| Variável        | Descrição               | Valor Padrão                          |
| --------------- | ----------------------- | ------------------------------------- |
| `DB_URL`        | URL do banco Oracle     | `jdbc:oracle:thin:@//localdb:1521/xe` |
| `DB_USERNAME`   | Usuário do banco Oracle | `PROFILE`                             |
| `DB_PASSWORD`   | Senha do banco Oracle   | `PROFILE`                             |
| `MONGODB_URI`   | URL do MongoDB          | `mongodb://localhost:27017/profile`   |
| `RABBITMQ_HOST` | Host do RabbitMQ        | `localhost`                           |
| `RABBITMQ_PORT` | Porta do RabbitMQ       | `5672`                                |
| `RABBITMQ_USER` | Usuário do RabbitMQ     | `guest`                               |
| `RABBITMQ_PASS` | Senha do RabbitMQ       | `guest`                               |

## Testes

Para rodar os testes unitários:

```sh
mvn test
```

