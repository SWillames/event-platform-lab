# ADR 001: Arquitetura Multimódulo baseada em Contextos Delimitados (DDD)

- **Status:** Aceito
- **Data:** 2026-11-03

## Contexto e Problema

A aplicação precisa gerir domínios com responsabilidades e padrões de consistência diferentes:
1. Clientes (`registration`)
2. Eventos e metadados (`catalog`)
3. Venda e reserva de ingressos (`ticketing`)

Manter todas essas responsabilidades acopladas em uma única estrutura ou banco de dados único dificulta a manutenção, o desacoplamento de regras de negócio e a evolução independente das partes do sistema.

## Decisão Escolhida

Organizar o projeto em módulos internos independentes (`registration`, `catalog`, `ticketing`, `common`) seguindo o padrão de Contextos Delimitados (Bounded Contexts) do Domain-Driven Design (DDD).

Cada módulo possui:
- Seu próprio modelo de domínio e regras de negócio.
- Sua própria camada de persistência e fonte de dados.
- Interfaces transparentes para integração por eventos.

## Consequências

### Positivas:
- **Desacoplamento:** Alterações no módulo de clientes não impactam diretamente a lógica de reserva de bilhetes.
- **Isolamento de Dados:** Cada módulo gere apenas a sua base de dados.
- **Facilidade de Testes:** É possível testar e validar cada fluxo de negócio isoladamente.

### Negativas / Riscos:
- Necessidade de sincronizar dados entre módulos (resolvido via publicação de eventos internos).
- Maior complexidade na configuração inicial de múltiplos Data Sources no Spring Boot.