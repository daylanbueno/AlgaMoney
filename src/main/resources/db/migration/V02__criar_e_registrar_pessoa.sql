CREATE TABLE IF NOT EXISTS  pessoa (
 codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
 nome varchar(50) NOT NULL,
 logradouro varchar(50),
 numero varchar(5),
 complemento varchar(20),
 bairro varchar(30),
 cep varchar(15),
 cidade varchar(30),
 estado varchar(30),
 ativo BOOLEAN  NOT NULL
)ENGINE =InnoDB DEFAULT CHARSET=utf8;

INSERT INTO algamoneyapi.pessoa (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo) values ('João Silva', 'Rua do Abacaxi', '10', 'SEM','lago norte','112233','braslia','DF',true)


