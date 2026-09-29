CREATE TABLE clientes (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          cpf VARCHAR(14) UNIQUE,
                          telefone VARCHAR(20) NOT NULL,
                          email VARCHAR(100),
                          data_nascimento DATE,
                          criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE funcionarios (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              nome VARCHAR(100) NOT NULL,
                              cpf VARCHAR(14) NOT NULL UNIQUE,
                              email VARCHAR(100) NOT NULL UNIQUE,
                              telefone VARCHAR(20),
                              senha_hash VARCHAR(255) NOT NULL,
                              cargo VARCHAR(50) NOT NULL DEFAULT 'PROFISSIONAL',
                              percentual_comissao DECIMAL(5,2) NOT NULL DEFAULT 0.00,
                              ativo BOOLEAN NOT NULL DEFAULT TRUE,
                              criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE servicos (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          descricao TEXT,
                          duracao_minutos INT NOT NULL,
                          preco_base DECIMAL(10,2) NOT NULL,
                          ativo BOOLEAN NOT NULL DEFAULT TRUE,
                          CONSTRAINT chk_duracao_positiva CHECK (duracao_minutos > 0),
                          CONSTRAINT chk_preco_positivo CHECK (preco_base >= 0)
) ENGINE=InnoDB;

CREATE TABLE funcionario_servico (
                                     funcionario_id BIGINT NOT NULL,
                                     servico_id BIGINT NOT NULL,
                                     PRIMARY KEY (funcionario_id, servico_id),
                                     CONSTRAINT fk_fs_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id) ON DELETE CASCADE,
                                     CONSTRAINT fk_fs_servico FOREIGN KEY (servico_id) REFERENCES servicos(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE agendamentos (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              cliente_id BIGINT NOT NULL,
                              funcionario_id BIGINT NOT NULL,
                              servico_id BIGINT NOT NULL,
                              data_hora_inicio DATETIME NOT NULL,
                              data_hora_fim DATETIME NOT NULL,
                              status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
                              valor_cobrado DECIMAL(10,2) NOT NULL,
                              observacoes TEXT,
                              criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              CONSTRAINT fk_agendamento_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id),
                              CONSTRAINT fk_agendamento_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id),
                              CONSTRAINT fk_agendamento_servico FOREIGN KEY (servico_id) REFERENCES servicos(id),
                              CONSTRAINT chk_horario_valido CHECK (data_hora_fim > data_hora_inicio)
) ENGINE=InnoDB;

CREATE TABLE transacoes_financeiras (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        agendamento_id BIGINT NULL,
                                        tipo VARCHAR(20) NOT NULL,
                                        descricao VARCHAR(255) NOT NULL,
                                        valor DECIMAL(10,2) NOT NULL,
                                        forma_pagamento VARCHAR(30) NOT NULL,
                                        data_pagamento DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        CONSTRAINT fk_transacao_agendamento FOREIGN KEY (agendamento_id) REFERENCES agendamentos(id) ON DELETE SET NULL,
                                        CONSTRAINT chk_valor_positivo CHECK (valor > 0)
) ENGINE=InnoDB;