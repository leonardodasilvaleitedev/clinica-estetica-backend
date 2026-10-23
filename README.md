# 💅 Clínica de Estética - API RESTful

API Backend robusta desenvolvida para a gestão completa de uma clínica de estética, cobrindo o gerenciamento de clientes, controle de estoque de produtos e agendamento de serviços.

---

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3** (Spring Data JPA, Spring Web, Bean Validation)
* **MySQL 8** (Banco de dados relacional)
* **Flyway** (Versionamento e migrações do banco de dados)
* **Lombok** (Produtividade e redução de código boilerplate)
* **Maven** (Gerenciamento de dependências e build)

---

## 📌 Módulos e Funcionalidades

### 👤 1. Gestão de Clientes (`/api/clientes`)
- Cadastro de clientes com validações de dados obrigatórios.
- Listagem e busca por ID.
- Registro de observações médicas e histórico do cliente.

### 📦 2. Controle de Estoque (`/api/produtos`)
- Cadastro e atualização de produtos em estoque.
- Controle de quantidades e alertas para reposição de materiais da clínica.

### 📅 3. Módulo de Agendamentos (`/api/agendamentos`)
- Agendamento de procedimentos estéticos vinculando Cliente, Serviço e Profissional.
- Regra de negócio para validação de conflitos de horários.
- **Endpoints:**
  - `POST /api/agendamentos` — Cria um novo agendamento.
  - `GET /api/agendamentos` — Lista todos os agendamentos registrados.
  - `GET /api/agendamentos/{id}` — Busca os detalhes de um agendamento específico.
  - `PATCH /api/agendamentos/{id}/status` — Atualiza o status do agendamento (`PENDENTE`, `CONFIRMADO`, `CANCELADO`, etc.).
 
### 💰 4. Módulo de Transações Financeiras (`/api/transacoes`)
* Controlo completo do fluxo de caixa da clínica com registo de Receitas e Despesas.
* Gestão de formas de pagamento, datas de transação/pagamento e cálculo de comissões para esteticistas.
* Endpoints:
  * `POST /api/transacoes` — Regista uma nova transação financeira (Receita ou Despesa).
  * `GET /api/transacoes` — Lista todas as transações financeiras registadas no sistema.

### 📦 Módulo de Estoque e Insumos (`/api/insumos`)
* **Cadastro de Insumos**: Controle de produtos e materiais utilizados nos atendimentos (nome, marca, unidade de medida, quantidade em estoque e estoque mínimo).
* **Registro de Uso (`/api/insumos/usos`)**: Registro manual ou automático do consumo de insumos por funcionário.
* **Baixa Automática**: Integração com o fluxo de conclusão de agendamentos, garantindo que o estoque seja abatido em tempo real de forma transacional (`@Transactional`).
* **Baixa Automática e Dinâmica:** Integração com o fluxo de conclusão de agendamentos (`PATCH /api/agendamentos/{id}/concluir`), permitindo o envio de uma lista flexível de insumos personalizados ou extras consumidos no atendimento, garantindo que o estoque seja abatido em tempo real de forma segura e transacional (`@Transactional`).

### 🔄 Fluxo de Conclusão de Atendimento
* **Endpoint**: `PATCH /api/agendamentos/{id}/concluir`
* **Comportamento Automatizado**: 

  1- Altera o status do agendamento para `CONCLUIDO`.

  2- Gera automaticamente a **Transação Financeira de Receita** correspondente ao valor cobrado.

  3- Dispara a baixa automática do estoque dos insumos associados.

---

## ⚙️ Como Executar o Projeto Localmente

### Pré-requisitos
- Java 21 instalado
- MySQL instalado e rodando
- Git

### Passos:

**Clonar o repositório:**
   ```bash
   git clone https://github.com/leonardodasilvaleitedev/clinica-estetica-backend.git
   cd clinica-estetica-backend
   ```

✒️ Autor

Desenvolvido por Leonardo - Desenvolvedor Backend Java / Spring Boot.
