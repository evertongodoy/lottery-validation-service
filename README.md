# Lottery Validation Service

Microserviço para validação de loterias da Caixa com cadastro de usuários.

## Arquitetura

Este projeto foi desenvolvido seguindo os princípios de **Clean Architecture** com implementação **Onion**.

### Estrutura de Camadas

```
Domain (núcleo) → Application → Infrastructure
```

- **Domain**: Entidades de negócio, enums e exceções
- **Application**: Casos de uso, ports (interfaces) e DTOs
- **Infrastructure**: Adaptadores (REST, Persistence), configurações

### Princípios SOLID

O projeto segue os princípios SOLID:
- **S**ingle Responsibility Principle
- **O**pen/Closed Principle
- **L**iskov Substitution Principle
- **I**nterface Segregation Principle
- **D**ependency Inversion Principle

## Tecnologias

- Java 21
- Spring Boot 4.0.0
- Spring Data MongoDB
- SpringDoc OpenAPI (Swagger)
- MongoDB 8.2
- Docker & Docker Compose
- Maven

## Pré-requisitos

- Java 21 ou superior
- Maven 3.6+
- Docker e Docker Compose

## Configuração e Execução

### 1. Iniciar MongoDB com Docker Compose

```bash
cd compose
docker-compose up -d
```

### 2. Compilar o projeto

```bash
mvn clean install
```

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

Ou com perfil específico:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Endpoints

### Criar Usuário

**POST** `/lottery-validation/api/v1/users`

**Request Body:**
```json
{
  "name": "João Silva",
  "role": "USER",
  "subject": "12345678900",
  "cellphone": "+5511999999999"
}
```

**Response (201 Created):**
```json
{
  "uuid": "123e4567-e89b-12d3-a456-426614174000",
  "name": "João Silva",
  "subject": "12345678900",
  "cellphone": "+5511999999999",
  "createdAt": "2025-12-15T10:30:00",
  "role": "USER"
}
```

## Documentação API (Swagger)

Após iniciar a aplicação, acesse:

- Swagger UI: http://localhost:8080/lottery-validation/swagger-ui.html
- API Docs: http://localhost:8080/lottery-validation/api-docs

## Estrutura do Projeto

```
lottery-validation-service/
├── compose/                          # Docker Compose
├── src/main/java/com/lottery/validation/
│   ├── domain/                       # Camada de Domínio
│   │   ├── entities/                 # Entidades de negócio
│   │   ├── enums/                    # Enumerações
│   │   └── exceptions/               # Exceções de negócio
│   ├── application/                  # Camada de Aplicação
│   │   ├── usecases/                 # Casos de uso
│   │   ├── ports/                    # Interfaces (input/output)
│   │   └── dto/                      # Data Transfer Objects
│   └── infrastructure/               # Camada de Infraestrutura
│       ├── adapters/
│       │   ├── input/rest/          # Controllers, Requests, Responses
│       │   └── output/persistence/  # Repositories, Entities MongoDB
│       └── config/                   # Configurações
└── src/main/resources/              # Arquivos de configuração
```

## Camadas e Responsabilidades

### Domain Layer
- **Entities**: Classes de domínio puras (User)
- **Enums**: Enumerações (UserRole)
- **Exceptions**: Exceções de negócio

### Application Layer
- **Use Cases**: Lógica de aplicação
- **Ports**: Interfaces para entrada e saída
- **DTOs**: Objetos de transferência entre camadas

### Infrastructure Layer
- **REST Adapters**: Controllers, Requests, Responses, Mappers
- **Persistence Adapters**: Repositories, Entities MongoDB, Mappers
- **Config**: Configurações do Spring

## Regras de Negócio

- O campo `subject` é único no sistema
- Validação de formato de telefone
- Geração automática de UUID e data de criação

## Profiles

- **dev**: Desenvolvimento local
- **prod**: Produção

## Licença

Apache 2.0
