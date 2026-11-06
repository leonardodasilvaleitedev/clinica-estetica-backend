CREATE TABLE supplies (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          brand VARCHAR(255),
                          unit_of_measure VARCHAR(50),
                          stock_quantity DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                          minimum_stock DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE supply_usages (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               employee_id BIGINT NOT NULL,
                               supply_id BIGINT NOT NULL,
                               quantity_used DECIMAL(10,2) NOT NULL,
                               used_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                               notes TEXT,
                               CONSTRAINT fk_usage_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
                               CONSTRAINT fk_usage_supply FOREIGN KEY (supply_id) REFERENCES supplies(id)
);