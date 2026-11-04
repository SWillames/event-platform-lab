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

O projeto adota os princípios do **Domain-Driven Design (DDD)** com contextos delimitados (Bounded Contexts):

```text
event-platform-lab/
├── doc/
│   └── adr/                    # Registos de Decisão Arquitetural (ADRs)
│       └── 001-arquitetura-multimodulo-ddd.md
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── dio/
│       │           └── marketplace/
│       │               ├── registration/   # Módulo de Clientes (MySQL)
│       │               ├── catalog/        # Módulo de Catálogo (MySQL + Mongo + Redis)
│       │               ├── ticketing/      # Módulo de Ingressos (PostgreSQL + Redis)
│       │               └── common/         # Eventos e DTOs Partilhados
│       └── resources/
│           └── application.properties
├── compose.yml                 # Definição dos serviços Docker
├── pom.xml                     # Configurações do Maven
└── README.md                   # Documentação principal
```

---

## 🏛️ Decisões de Arquitetura (ADRs)

As decisões de arquitetura relevantes são documentadas na pasta `doc/adr/`:

- [ADR 001: Arquitetura Multimódulo baseada em Contextos Delimitados (DDD)](doc/adr/001-arquitetura-multimodulo-ddd.md)

---

## 🐳 Infraestrutura (Docker Compose)

A infraestrutura local é gerida através do ficheiro `compose.yml`. Durante o arranque da aplicação Spring Boot em ambiente de desenvolvimento, os containers são inicializados automaticamente.

| Serviço | Imagem | Porta Externa | Descrição |
| :--- | :--- | :--- | :--- |
| `registration-db` | MySQL 9.0 | `3307` | Banco de Clientes |
| `catalog-db` | MySQL 9.0 | `3308` | Banco do Catálogo |
| `mongo-db` | Mongo Latest | `27018` | Banco NoSQL de Metadados |
| `catalog-redis` | Redis Alpine | `6379` | Cache da Vitrine |
| `ticketing-db` | Postgres Latest | `5432` | Banco de Ingressos |
| `ticketing-redis` | Redis Alpine | `6380` | Lock de Assentos |

---

## 📋 Como Executar o Projeto

### Pré-requisitos
- **Java 21** (ou superior) instalado
- **Apache Maven** instalado (ou utilizar o wrapper `./mvnw`)
- **Docker** e **Docker Compose** ativos na máquina

### Passo a Passo

1. **Clonar o repositório:**
   ```bash
   git clone https://github.com/SWillames/event-platform-lab.git
   cd event-platform-lab
   ```

2. **Subir os containers de infraestrutura (opcional se usar o suporte automático do Spring):**
   ```bash
   docker compose up -d
   ```

3. **Compilar e executar a aplicação via Maven:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Aceder à aplicação:**
   - **HAL Explorer (Navegação REST):** `http://localhost:8080`
   - **Health Check (Actuator):** `http://localhost:8080/actuator/health`