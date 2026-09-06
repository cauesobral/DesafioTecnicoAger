CREATE TABLE agenda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE compromisso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE funcionario (
    rowid BIGINT AUTO_INCREMENT PRIMARY KEY,
    nm_funcionario VARCHAR(255)
);

INSERT INTO funcionario (nm_funcionario)
VALUES
    ('João Santos Silva'),
    ('Maria Silva Santos'),
    ('João Silva Santos'),
    ('Maria Santos Silva'),
    ('João Santos Silva'),
    ('Maria Silva Santos'),
    ('João Silva Santos'),
    ('Maria Santos Silva'),
    ('João Santos Silva'),
    ('Maria Silva Santos'),
    ('João Silva Santos'),
    ('Maria Santos Silva');
