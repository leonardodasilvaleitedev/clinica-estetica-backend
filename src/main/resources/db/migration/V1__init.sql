CREATE TABLE clients (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        cpf VARCHAR(14) UNIQUE,
                        phone VARCHAR(20) NOT NULL,
                        email VARCHAR(100),
                        birth_date DATE,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE employees (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          cpf VARCHAR(14) NOT NULL UNIQUE,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          phone VARCHAR(20),
                          password_hash VARCHAR(255) NOT NULL,
                          role VARCHAR(50) NOT NULL,
                          commission_percentage DECIMAL(5,2) NOT NULL DEFAULT 0.00,
                          active BOOLEAN NOT NULL DEFAULT TRUE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE services (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         description TEXT,
                         duration_minutes INT NOT NULL,
                         base_price DECIMAL(10,2) NOT NULL,
                         active BOOLEAN NOT NULL DEFAULT TRUE,
                         CONSTRAINT chk_positive_duration CHECK (duration_minutes > 0),
                         CONSTRAINT chk_positive_price CHECK (base_price >= 0)
) ENGINE=InnoDB;

CREATE TABLE appointments (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             client_id BIGINT NOT NULL,
                             employee_id BIGINT NOT NULL,
                             service_id BIGINT NOT NULL,
                             start_time DATETIME NOT NULL,
                             end_time DATETIME NOT NULL,
                             status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
                             price_charged DECIMAL(10,2) NOT NULL,
                             notes TEXT,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             CONSTRAINT fk_appointment_client FOREIGN KEY (client_id) REFERENCES clients(id),
                             CONSTRAINT fk_appointment_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
                             CONSTRAINT fk_appointment_service FOREIGN KEY (service_id) REFERENCES services(id),
                             CONSTRAINT chk_valid_schedule CHECK (end_time > start_time)
) ENGINE=InnoDB;



CREATE TABLE employee_service (
                                  employee_id BIGINT NOT NULL,
                                  service_id BIGINT NOT NULL,
                                  PRIMARY KEY (employee_id, service_id),
                                  CONSTRAINT fk_es_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
                                  CONSTRAINT fk_es_service FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE financial_transactions (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            description VARCHAR(255) NOT NULL,
                            amount DECIMAL(10,2) NOT NULL,
                            type VARCHAR(50) NOT NULL,
                            payment_method VARCHAR(50),
                            transaction_date DATE NOT NULL,
                            payment_date DATETIME,
                            appointment_id BIGINT,
                            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT fk_transaction_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
) ENGINE=InnoDB;