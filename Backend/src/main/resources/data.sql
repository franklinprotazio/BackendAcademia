CREATE TABLE tb_academia
(
    id_academia IDENTITY NOT NULL PRIMARY KEY,
    nome_academia VARCHAR(50) NOT NULL
    
);

CREATE TABLE tb_turma
(
    id_turma IDENTITY NOT NULL PRIMARY KEY,
    curso VARCHAR(50) NOT NULL,
    horario VARCHAR(50) NOT NULL,
    nome_professor VARCHAR(50) NOT NULL,
    academia_id INT NOT NULL,

        CONSTRAINT FK_TB_ACADEMIA
        FOREIGN KEY (academia_id)
        REFERENCES tb_academia(id_academia)
);


CREATE TABLE tb_aluno
(
    id_aluno IDENTITY NOT NULL PRIMARY KEY,
    data_matricula TIMESTAMP WITH TIME ZONE NOT NULL,
    nome_aluno VARCHAR(50) NOT NULL,
    turma_id INT NOT NULL,
    academia_id INT NOT NULL,

        CONSTRAINT FK_TB_TURMA_ALUNO
        FOREIGN KEY (turma_id)
        REFERENCES tb_turma(id_turma),
        
         CONSTRAINT FK_TB_ACADEMIA_ALUNO
        FOREIGN KEY (academia_id)
        REFERENCES tb_academia(id_academia)
);
