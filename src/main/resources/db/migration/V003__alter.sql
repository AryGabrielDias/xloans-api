ALTER TABLE pessoa CHANGE COLUMN valor_min_mensal valor_min_mensal double precision(18,4);

ALTER TABLE pessoa CHANGE COLUMN valor_max_emprestimo valor_max_emprestimo double precision(18,4);

ALTER TABLE emprestimo CHANGE COLUMN valor_emprestimo valor_emprestimo double precision(18,4);