CREATE TABLE pessoa (
    id  bigint NOT NULL AUTO_INCREMENT,
    nome varchar(50) NOT NULL,
    identificador varchar(50) NOT NULL,
    data_nascimento datetime NOT NULL,
    tipo_identificador varchar(50) NOT NULL,
    valor_min_mensal decimal(18,4) NOT NULL,
    valor_max_emprestimo decimal(18,4) NOT NULL,
    PRIMARY KEY (id)
) DEFAULT CHARSET=UTF8MB4;

CREATE TABLE emprestimo (
    id  bigint NOT NULL AUTO_INCREMENT,
    id_pessoa bigint NOT NULL,
    valor_emprestimo decimal(18,4) NOT NULL,
    numero_parcelas integer NOT NULL
    status_pagamento varchar(50) NOT NULL,
    data_criacao datetime NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa(id)
) DEFAULT CHARSET=UTF8MB4;