/*Nome Banco de Dados: db_granja */

CREATE TABLE caminhoes(
 	cdCaminhao 				SERIAL 	     NOT NULL,
 	modelo     				VARCHAR(30)  NOT NULL,
 	marca 	   				VARCHAR(30)  NOT NULL,
	placa      				CHAR(7)      NOT NULL,
	cor        				VARCHAR(20)  NOT NULL,
	quilometrosRodados  	NUMERIC(8,1) NOT NULL,
	capacidadeMaximaPorcos	INTEGER		 NOT NULL,
	CONSTRAINT pk_caminhoes
		PRIMARY KEY(cdCaminhao)
);

CREATE TABLE motoristas(
	cdMotorista 	SERIAL      NOT NULL,
	nome 			VARCHAR(40) NOT NULL,
	cpf				CHAR(14)	NOT NULL,
	categoriaCnh	VARCHAR(2)  NOT NULL,
	dataNascimento  DATE		NOT NULL,
	dataContratacao DATE		NOT NULL,
	CONSTRAINT pk_motoristas
		PRIMARY KEY(cdMotorista)
);

CREATE TABLE galpoes(
	cdGalpao		 SERIAL 		NOT NULL,
	nome		 	 VARCHAR(40)	NOT NULL,
	metrosQuadrados  NUMERIC(7,2) 	NOT NULL,
	capacidadeMaxima INTEGER		NOT NULL,
	numeroBaias		 INTEGER 		NOT NULL,
	refrigerado		 BOOLEAN		NOT NULL,
	maternidade		 BOOLEAN		NOT NULL,
	limiteDiario	 INTEGER		NOT NULL,
	quantidadeAtual	 INTEGER		NOT NULL,
	CONSTRAINT pk_galpoes
		PRIMARY KEY(cdGalpao),
	UNIQUE(nome)
);

CREATE TABLE recebimentosporcos(
	cdRecebimentoPorcos	SERIAL  NOT NULL,
	cdCaminhao			INTEGER NOT NULL,
	cdMotorista			INTEGER NOT NULL,
	cdGalpao			INTEGER NOT NULL,
	data				DATE    NOT NULL,
	quantidadePorcos    INTEGER NOT NULL,
	filhotes			BOOLEAN NOT NULL,
	CONSTRAINT pk_recebimentosporcos
		PRIMARY KEY(cdRecebimentoPorcos),
	CONSTRAINT fk_recebimentoporcos_caminhoes
		FOREIGN KEY (cdCaminhao)
		REFERENCES caminhoes(cdCaminhao),
	CONSTRAINT fk_recebimentoporcos_motoristas
		FOREIGN KEY (cdMotorista)
		REFERENCES motoristas(cdMotorista),
	CONSTRAINT fk_recebimentoporcos_galpoes
		FOREIGN KEY (cdGalpao)
		REFERENCES galpoes(cdGalpao)
);

CREATE TABLE usuarios(
	idUsuario INTEGER      NOT NULL,
	senha     VARCHAR(64)  NOT NULL,
	CONSTRAINT pk_usuarios 
		PRIMARY KEY(idUsuario)
);

INSERT INTO usuarios(idUsuario, senha) VALUES(1, '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918');
INSERT INTO usuarios(idUsuario, senha) VALUES(2, '8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92');

INSERT INTO caminhoes(modelo, marca, placa, cor, quilometrosRodados, capacidadeMaximaPorcos) VALUES ('p310', 'Scania', 'THJ1A28', 'Vermelho', 428110.0, 120);
INSERT INTO caminhoes(modelo, marca, placa, cor, quilometrosRodados, capacidadeMaximaPorcos) VALUES ('Constellation', 'Volkswagen', 'QRI8C21', 'Branco', 215240.0, 80);
INSERT INTO caminhoes(modelo, marca, placa, cor, quilometrosRodados, capacidadeMaximaPorcos) VALUES ('Atego 2426', 'Mercedes-Benz', 'MTI2T42', 'Prata', 85240.0, 120);
INSERT INTO caminhoes(modelo, marca, placa, cor, quilometrosRodados, capacidadeMaximaPorcos) VALUES ('1620', 'Mercedes-Benz', 'MSA1B11', 'Branco', 516720.0, 90);
INSERT INTO caminhoes(modelo, marca, placa, cor, quilometrosRodados, capacidadeMaximaPorcos) VALUES ('1113', 'Mercedes-Benz', 'STA7G33', 'Azul', 726410.0, 60);

INSERT INTO motoristas(nome, cpf, categoriaCnh, dataNascimento, dataContratacao) VALUES ('Gabriel Guimarães Massolari', '293.164.450-19', 'AD', '2002-08-28', '2015-06-16');
INSERT INTO motoristas(nome, cpf, categoriaCnh, dataNascimento, dataContratacao) VALUES ('João Ferreira', '493.114.150-14', 'AD', '1988-02-03', '2018-03-11');
INSERT INTO motoristas(nome, cpf, categoriaCnh, dataNascimento, dataContratacao) VALUES ('Tião Silva', '381.101.500-12', 'BD', '1969-11-27', '2014-04-22');
INSERT INTO motoristas(nome, cpf, categoriaCnh, dataNascimento, dataContratacao) VALUES ('Francisco Dias', '808.948.200-79', 'BD', '1966-03-11', '2015-03-11');
INSERT INTO motoristas(nome, cpf, categoriaCnh, dataNascimento, dataContratacao) VALUES ('Lucas Nascimento', '043.566.930-35', 'AD', '1992-07-16', '2019-08-20');

INSERT INTO galpoes(nome, metrosQuadrados, capacidadeMaxima, numeroBaias, refrigerado, maternidade, limiteDiario, quantidadeAtual) VALUES ('A1', 1250.00, 340, 25, true, false, 90, 0);
INSERT INTO galpoes(nome, metrosQuadrados, capacidadeMaxima, numeroBaias, refrigerado, maternidade, limiteDiario, quantidadeAtual) VALUES ('A2', 1850.00, 560, 30, true, false, 120, 0);
INSERT INTO galpoes(nome, metrosQuadrados, capacidadeMaxima, numeroBaias, refrigerado, maternidade, limiteDiario, quantidadeAtual) VALUES ('B1', 1000.00, 250, 23, true, true, 60, 0);

/*Inserção dados históricos de recebimentos*/
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 3, 2, '2021-01-03', 100, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 2, 2, '2021-02-08', 20, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 3, 3, '2021-02-09', 30, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 3, '2021-02-10', 40, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 2, 1, '2021-02-12', 90, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 1, 1, '2021-02-18', 70, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 2, '2021-02-19', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 4, 2, '2021-02-26', 115, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 2, '2021-03-01', 95, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 4, 3, '2021-03-12', 60, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 1, 3, '2021-03-25', 45, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 2, '2021-04-06', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 3, 2, '2021-04-19', 120, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 4, 2, '2021-04-27', 60, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 5, 1, '2021-05-02', 50, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 2, 1, '2021-05-18', 70, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 4, 1, '2021-05-22', 90, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 2, '2021-06-15', 40, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 1, 2, '2021-06-15', 35, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 2, 2, '2021-06-15', 30, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 1, '2021-06-16', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 1, '2021-06-23', 60, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 4, 1, '2021-06-28', 90, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 3, 2, '2021-07-03', 100, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 2, 2, '2021-07-08', 20, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 3, 3, '2021-07-09', 30, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 3, '2021-07-10', 40, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 2, 1, '2021-07-12', 90, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 1, 1, '2021-07-18', 70, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 2, '2021-07-19', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 4, 2, '2021-07-26', 115, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 2, '2021-08-01', 95, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 4, 3, '2021-08-12', 60, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 1, 1, '2021-08-18', 70, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 2, '2021-08-19', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 4, 2, '2021-08-26', 115, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 2, '2021-09-01', 95, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 1, 1, '2021-09-05', 90, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 1, 1, '2021-09-19', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 2, '2021-09-20', 80, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 3, 2, '2021-09-25', 120, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 4, 2, '2021-09-28', 60, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 5, 2, '2021-10-07', 90, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 3, 1, '2021-10-22', 90, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 1, '2021-10-28', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 4, 3, '2021-11-04', 60, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 5, 1, '2021-11-08', 50, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 2, 1, '2021-11-22', 70, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 4, 1, '2021-12-01', 90, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 4, 2, '2021-12-04', 120, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 2, '2021-12-15', 40, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 1, 2, '2021-12-15', 40, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 2, 2, '2021-12-15', 40, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 1, '2021-12-16', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 1, '2021-12-23', 60, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 4, 1, '2021-12-28', 90, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 3, 2, '2022-01-03', 100, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 2, 2, '2022-02-08', 20, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 3, 3, '2022-02-09', 30, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 3, '2022-02-10', 40, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 2, 1, '2022-02-12', 90, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 1, 1, '2022-02-18', 70, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 2, '2022-02-19', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 4, 2, '2022-02-26', 115, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 5, 2, '2022-03-01', 95, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 4, 3, '2022-03-12', 60, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 1, 3, '2022-03-25', 45, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (2, 2, 2, '2022-04-06', 80, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (3, 3, 2, '2022-04-19', 120, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 4, 2, '2022-04-27', 60, true);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (5, 5, 1, '2022-05-02', 50, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (4, 2, 1, '2022-05-18', 70, false);
INSERT INTO recebimentosporcos(cdCaminhao, cdMotorista, cdGalpao, data, quantidadePorcos, filhotes) VALUES (1, 4, 1, '2022-05-22', 90, true);