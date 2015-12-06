CREATE TABLE IF NOT EXISTS  produto(codigo int primary key, codigo_barras varchar(250), fsc_code varchar(250), descricao varchar(500));
CREATE TABLE IF NOT EXISTS  pallete(codigo int primary key, identificacao varchar(250));
CREATE TABLE IF NOT EXISTS  produto_pallete(codigo int primary key,codigo_produto int not null,codigo_pallete int not null);
ALTER TABLE produto_pallete ADD CONSTRAINT PK_PRODUTO PRIMARY KEY (codigo_produto,codigo_pallete);
ALTER TABLE produto_pallete ADD CONSTRAINT FK_PROD FOREIGN KEY (codigo_produto) REFERENCES PRODUTO(CODIGO);
ALTER TABLE produto_pallete ADD CONSTRAINT FK_PALLETE FOREIGN KEY (codigo_pallete) REFERENCES pallete(CODIGO);;