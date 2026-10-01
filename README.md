# 💅 ERP & CRM - Gestão para Clínicas de Estética

> Sistema robusto para gerenciamento operacional, financeiro e relacionamento com clientes focado no segmento de estética e bem-estar, construído com Java 21, Spring Boot e Spring Security (JWT).

---

## 🎯 Sobre o Projeto

O objetivo deste projeto é resolver dores reais de gestão em clínicas de estética, combinando funcionalidades de **ERP** (controle operacional, estoque e financeiro) e **CRM** (fidelização, histórico de procedimentos e acompanhamento).

### 🚀 Funcionalidades Principais

- **🔒 Autenticação & Segurança (Stateless):**
  - Autenticação JWT (`JSON Web Token`) com encriptação de senhas via `BCrypt`.
  - Proteção de rotas granulares e sessão stateless via `Spring Security`.

- **📦 Módulo de Estoque e Suprimentos:**
  - Gestão de insumos com parâmetro de estoque mínimo para reposição.
  - **Baixa Automática:** Registro de consumo de insumos por profissional/procedimento com dedução automática no saldo total.
  - Validação transacional de saldo disponível em estoque para prevenir inconsistências.

- **👤 Módulo CRM & Clientes (Em expansão):**
  - Ficha de Anamnese digital e histórico de procedimentos realizados.
  - Registro de preferências, fotos de acompanhamento (antes/depois) e restrições/alergias.

- **📅 Módulo ERP Operacional (Em expansão):**
  - Agendamento de horários por profissional e sala/equipamento.
  - Gestão de funcionários e comissionamento.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem & Framework:** Java 21 | Spring Boot 4.x
- **Segurança:** Spring Security | JWT (io.jsonwebtoken) | BCrypt
- **Banco de Dados & Persistência:** MySQL | Spring Data JPA / Hibernate
- **Migrações de Banco:** Flyway Migration (`db/migration`)
- **Documentação & Ferramentas:** Maven | Java Records | Postman | Git

---

## 🏗️ Arquitetura e Estrutura de Pastas

O sistema segue a arquitetura em camadas bem definida, fazendo uso de **Java Records** para DTOs e modelo de domínio desacoplado:

```text
src/main/java/com/clinica/api/
├── controller/         # Endpoints REST (InsumoController, AutenticacaoController, etc.)
├── dto/                # Data Transfer Objects (Records de entrada e saída)
├── domain/
│   └── model/          # Entidades JPA (Insumo, UsoInsumo, Funcionario, etc.)
├── repository/         # Interfaces Spring Data JPA
└── service/            # Regras de negócio, transações e validações de estoque
