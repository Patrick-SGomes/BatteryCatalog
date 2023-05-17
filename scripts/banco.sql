CREATE DATABASE IF NOT EXISTS catalog_db;
USE catalog_db;

CREATE TABLE veiculo (
	id int auto_increment primary key,
    marca varchar(30),
    modelo varchar(20),
    ano int(15),
    tecnologia varchar(20)
);
CREATE TABLE ferramenta (
	id int auto_increment primary key,
    nome varchar(30),
    tamanho varchar(10),
    imagem varchar(50)
);
CREATE TABLE bateria (
    id int auto_increment primary key,
    codigo varchar(20),
    capacidade varchar(20),
    cca varchar(20),
    polaridade varchar(10),
    tecnologia varchar(30),
    dimensao varchar(30)
    img varchar(50),
);
