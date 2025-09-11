CREATE DATABASE SisVendas

CREATE TABLE Clientes(
	id_C INT PRIMARY KEY AUTO_INCREMENT,
    nome varchar(90) NOT NULL,
    endereco varchar(180) NOT NULL,
    email varchar(200) NOT NULL
);

CREATE TABLE Produto(
	id_P INT PRIMARY KEY AUTO_INCREMENT,
    nome varchar(90),
    descricao varchar(200),
    preco_venda double,
    estoque int
);

CREATE TABLE Cabecalho_Nota(
	id_N INT PRIMARY KEY AUTO_INCREMENT,
    data_venda date,
    cliente_id int,
    valor_total DECIMAL(10, 2),
    FOREIGN KEY cliente_id REFERENCES Cliente(id_C)  
);

CREATE TABLE Itens_Nota(
    id_I INT PRIMARY KEY AUTO_INCREMENT,
    nota_id INT NOT NULL,          
    produto_id INT NOT NULL,        
    quantidade INT NOT NULL,
    preco_unitario_venda DECIMAL(10, 2) NOT NULL, 
    
    FOREIGN KEY (nota_id) REFERENCES Cabecalho_Nota(id_N),
    FOREIGN KEY (produto_id) REFERENCES Produto(id_P)
);