# 💅 ERP & CRM - Gestão para Clínicas de Estética

> Sistema completo para gerenciamento operacional, financeiro e relacionamento com clientes focado no segmento de estética e bem-estar.

---

## 🎯 Sobre o Projeto

O objetivo deste projeto é resolver dores reais de gestão em clínicas de estética, combinando funcionalidades de **ERP** (controle operacional e financeiro) e **CRM** (fidelização, histórico de procedimentos e ficha de anamnese).

### 🚀 Principais Funcionalidades

- **Módulo CRM (Gestão de Clientes):**
  - Ficha de Anamnese digital e histórico de procedimentos realizados.
  - Registro de preferências, fotos de acompanhamento (antes/depois) e alergias.
  - Funil de atendimento e acompanhamento pós-procedimento.

- **Módulo ERP (Gestão Operacional):**
  - **Agendamento Inteligente:** Controle de horários por profissional e sala/equipamento.
  - **Estoque & Suprimentos:** Baixa automática de insumos (ex: aplicação de toxina botulínica/preenchedores).
  - **Módulo Financeiro:** Fluxo de caixa, comissionamento de profissionais e controle de pacotes/sessões.

---

## 🛠️ Tecnologias Utilizadas

- **Backend:** Java 21 / Spring Boot (Spring Data JPA, Spring Security, Validation)
- **Banco de Dados:** MySQL / H2
- **Documentação da API:** Swagger / OpenAPI
- **Gerenciamento de Dependências:** Maven

---

## 🏗️ Arquitetura e Modelagem

O sistema foi desenhado seguindo as boas práticas do DDD (Domain-Driven Design) e arquitetura em camadas (Controller, Service, Repository, DTOs).

```text
src/main/java/com/clinica/estetica/
├── controller/    # Endpoints REST
├── dto/           # Data Transfer Objects
├── model/         # Entidades de Domínio (Cliente, Agendamento, Anamnese, Estoque)
├── repository/    # Interfaces Spring Data JPA
└── service/       # Regras de Negócio e Validações
