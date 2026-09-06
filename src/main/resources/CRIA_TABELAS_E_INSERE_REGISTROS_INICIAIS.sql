CREATE TABLE funcionario (
    rowid BIGINT AUTO_INCREMENT PRIMARY KEY,
    nm_funcionario VARCHAR(255)
);

CREATE TABLE agenda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nm_agenda VARCHAR(255),
    periodo_disponivel VARCHAR(20)
);

CREATE TABLE compromisso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cod_funcionario BIGINT NOT NULL,
    cod_agenda BIGINT NOT NULL,
    dt_compromisso DATE NOT NULL,
    hr_compromisso TIME NOT NULL,
    CONSTRAINT fk_compromisso_funcionario FOREIGN KEY (cod_funcionario) REFERENCES funcionario(rowid),
    CONSTRAINT fk_compromisso_agenda FOREIGN KEY (cod_agenda) REFERENCES agenda(id)
);

INSERT INTO funcionario (nm_funcionario)
VALUES
    ('João Santos Silva'),
    ('Maria Silva Santos'),
    ('João Santos Silva'),
    ('Maria Silva Santos'),
    ('João Santos Silva'),
    ('Maria Silva Santos'),
    ('João Santos Silva');

INSERT INTO agenda (nm_agenda, periodo_disponivel)
VALUES
    ('Sala de Reunião', 'AMBOS'),
    ('Consultório Médico A', 'AMBOS'),
    ('Consultório Médico B', 'AMBOS'),
    ('Fisioterapia', 'TARDE'),
    ('Exame Admissional', 'MANHA'),
    ('Exames no Laboratório', 'MANHA'),
    ('Avaliação Psicológica', 'TARDE');