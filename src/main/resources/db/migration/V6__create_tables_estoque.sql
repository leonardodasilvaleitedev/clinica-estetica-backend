-- Criar tabela de insumos (produtos/materiais do estoque)
CREATE TABLE IF NOT EXISTS insumos (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       nome VARCHAR(100) NOT NULL,
    marca VARCHAR(100),
    quantidade_estoque DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    estoque_minimo DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    unidade_medida VARCHAR(20) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
    );

-- Criar tabela para registrar o uso/baixa dos insumos
CREATE TABLE IF NOT EXISTS usos_insumos (
                                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                            insumo_id BIGINT NOT NULL,
                                            funcionario_id BIGINT NOT NULL,
                                            quantidade_usada DECIMAL(10,2) NOT NULL,
    data_uso DATETIME NOT NULL,
    observacao VARCHAR(255),

    CONSTRAINT fk_usos_insumos_insumo
    FOREIGN KEY (insumo_id) REFERENCES insumos(id),

    CONSTRAINT fk_usos_insumos_funcionario
    FOREIGN KEY (funcionario_id) REFERENCES funcionarios(id)
    );