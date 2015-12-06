CREATE TABLE IF NOT EXISTS  produto(codigo bigint auto_increment primary key, codigo_barras varchar(250), fsc_code varchar(250), descricao varchar(500));
CREATE TABLE IF NOT EXISTS  pallete(codigo bigint auto_increment primary key, identificacao varchar(250));
CREATE TABLE IF NOT EXISTS  produto_pallete(codigo bigint auto_increment primary key,codigo_produto int not null,codigo_pallete int not null);
ALTER TABLE produto_pallete ADD CONSTRAINT FK_PROD FOREIGN KEY (codigo_produto) REFERENCES PRODUTO(CODIGO);
ALTER TABLE produto_pallete ADD CONSTRAINT FK_PALLETE FOREIGN KEY (codigo_pallete) REFERENCES pallete(CODIGO);

ALTER TABLE pallete ADD CONSTRAINT identificacao_pallete_unique UNIQUE(identificacao);
ALTER TABLE produto ADD CONSTRAINT codigo_barras_unique UNIQUE(codigo_barras);
ALTER TABLE produto ADD CONSTRAINT fsc_code_unique UNIQUE(fsc_code);
