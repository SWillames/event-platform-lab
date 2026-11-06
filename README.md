# Marketplace - Plataforma de Eventos e Venda de Ingressos

Este projeto consiste numa aplicação baseada numa arquitetura de microsserviços/módulos desacoplados desenvolvida em Java com Spring Boot, Maven e Docker. O sistema gere desde o registo de clientes até ao catálogo flexível de eventos e a reserva de lugares em tempo real com prevenção de *overbooking*.

## 🚀 Objetivo do Sistema

1. **Gestão de Clientes (`registration`):** Registo e manutenção de dados de clientes e endereços em banco relacional.
2. **Catálogo de Eventos (`catalog`):** Gestão de eventos com suporte a metadados flexíveis NoSQL, vitrine otimizada e cache em memória.
3. **Reserva de Ingressos (`ticketing`):** Comunicação reativa entre módulos por eventos internos e travamento temporário de assentos para evitar compras simultâneas da mesma cadeira.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.x (Spring Data JPA, Spring Data REST, Spring Data MongoDB, Spring Data Redis)
- **Gestor de Dependências:** Apache Maven (`pom.xml`)
- **Bancos de Dados:**
  - **MySQL 9.0:** Dados relacionais de clientes e eventos básicos.
  - **MongoDB:** Metadados dinâmicos e flexíveis de eventos.
  - **PostgreSQL:** Banco isolado para o módulo de ingressos.
  - **Redis:** Cache da vitrine de eventos e mecanismo de trava (*lock*) de assentos com TTL.
- **Contentorização:** Docker e Docker Compose (`spring-boot-docker-compose`)

---

## 📐 Arquitetura do Projeto

O projeto adota os princípios do **Domain-Driven Design (DDD)** e da **Clean Architecture** com contextos delimitados (Bounded Contexts):

```text
event-platform-lab/
├── doc/
│   └── adr/                    # Registos de Decisão Arquitetural (ADRs)
│       └── 001-arquitetura-multimodulo-ddd.md
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── swsoftware/
│       │           └── marketplace/
│       │               ├── registration/   # Módulo de Clientes (MySQL)
│       │               │   ├── domain/
│       │               │   │   ├── Customer.java               # Entidade do Domínio
│       │               │   │   ├── CustomerId.java             # Value Object
│       │               │   │   └── CustomerRepository.java     # Contrato de Negócio
│       │               │   └── infrastructure/
│       │               │       └── persistence/
│       │               │           ├── AddressEntity.java      # Tabela 'address'
│       │               │           ├── CustomerEntity.java     # Tabela 'customer'
│       │               │           ├── CustomerEntityRepository.java # JPA / Spring Data REST
│       │               │           ├── CustomerMapper.java     # Conversor (Domínio <-> JPA)
│       │               │           └── CustomerRepositoryImpl.java   # Conexão Domínio <-> JPA
│       │               ├── catalog/        # Módulo de Catálogo
│       │               ├── ticketing/      # Módulo de Ingressos
│       │               └── common/         # Eventos e DTOs Partilhados
│       └── resources/
│           └── application.properties
├── compose.yaml                 # Definição dos serviços Docker
├── pom.xml                     # Configurações do Maven
└── README.md                   # Documentação principal
```

---

## 💡 Decisão de Arquitetura: Separação de Repositórios no Módulo `registration`

Dentro do módulo `registration`, aplicamos o **Princípio da Inversão de Dependência (DIP)** para manter o núcleo do negócio (`domain`) totalmente desacoplado de frameworks e da base de dados relacional:

1. **`CustomerRepository` (Domínio):** Interface puramente Java em `registration.domain`. Define operações com objetos do negócio (`Customer` e `CustomerId`), permitindo testar as regras sem dependência do Spring Boot ou MySQL.
2. **`CustomerEntityRepository` (Infraestrutura):** Interface em `registration.infrastructure.persistence` que estende `JpaRepository` e é anotada com `@RepositoryRestResource` para gerir tabelas no MySQL e expor a API REST.
3. **`CustomerRepositoryImpl` & `CustomerMapper` (Ponte):** O `CustomerRepositoryImpl` implementa a interface do domínio. Ele utiliza o `CustomerMapper` para converter `Customer` $\leftrightarrow$ `CustomerEntity` e delega a gravação no banco ao `CustomerEntityRepository`.

---

## 🏛️ Decisões de Arquitetura (ADRs)

As decisões de arquitetura relevantes são documentadas na pasta `doc/adr/`:

- [ADR 001: Arquitetura Multimódulo baseada em Contextos Delimitados (DDD)](doc/adr/001-arquitetura-multimodulo-ddd.md)

---

## 🐳 Infraestrutura (Docker Compose)

A infraestrutura local é gerida através do ficheiro `compose.yaml`. Durante o arranque da aplicação Spring Boot em ambiente de desenvolvimento, os contêineres são inicializados automaticamente via `spring-boot-docker-compose`.

| Serviço | Imagem | Porta Externa | Descrição |
| :--- | :--- | :--- | :--- |
| `registration-db` | MySQL 9.0 | `3307` | Banco de Clientes |
| `catalog-db` | MySQL 9.0 | `3308` | Banco do Catálogo |
| `mongo-db` | Mongo Latest | `27018` | Banco NoSQL de Metadados |
| `catalog-redis` | Redis Alpine | `6379` | Cache da Vitrine |
| `ticketing-db` | Postgres Latest | `5432` | Banco de Ingressos |
| `ticketing-redis` | Redis Alpine | `6380` | Lock de Assentos |

---

## 📋 Como Executar e Testar o Projeto

### Pré-requisitos
- **Java 21** (ou superior) instalado
- **Docker** e **Docker Compose** ativos na máquina

### Passo a Passo de Execução

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/SWillames/event-platform-lab.git
   cd event-platform-lab
   ```

2. **Compilar e executar a aplicação via Maven Wrapper:**
   *(O Spring Boot iniciará os contêineres Docker automaticamente)*
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Testar os Endpoints REST do Módulo `registration`:**
   - **Cadastrar Cliente com Endereço (HTTP POST):**
     ```bash
     curl -i -X POST http://localhost:8080/customers \
       -H "Content-Type: application/json" \
       -d '{
         "id": "c138b139-4d69-4d40-8488-bc1c2d0fbb32",
         "name": "Sérgio Willames",
         "email": "sergio@example.com",
         "address": {
           "street": "Rua Principal",
           "number": "100",
           "neighborhood": "Centro",
           "city": "Floriano",
           "state": "PI",
           "zipCode": "64800-000"
         }
       }'
     ```
   - Cadastrar Evento (catalog - HTTP POST):
     ```bash
     curl -i -X POST http://localhost:8080/api/events \
      -H "Content-Type: application/json" \
      -d '{
         "name": "DevConference 2026",
         "description": "Maior evento de tecnologia e       arquitetura de software",
         "price": 150.00,
         "totalCapacity": 500
     }'
     ```