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
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id_C)  
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

DELIMITER //

CREATE TRIGGER after_insert_cabecalho
AFTER INSERT ON Cabecalho_Nota
FOR EACH ROW
BEGIN
   INSERT INTO Itens_Nota (nota_id, produto_id, quantidade, preco_unitario_venda)
   VALUES (NEW.id_N, 1, 1, 0.00);
END //

DELIMITER ;

INSERT INTO Clientes (nome, endereco, email) 
VALUES ('João da Silva', 'Rua A, 123', 'joao@email.com');

INSERT INTO Produto (nome, descricao, preco_venda, estoque) 
VALUES ('Mouse Gamer', 'Mouse RGB 16000 DPI', 150.00, 10);

INSERT INTO Cabecalho_Nota (data_venda, cliente_id, valor_total) 
VALUES (CURDATE(), 1, 300.00);

SELECT 
    n.id_N AS nota_id,
    n.data_venda,
    c.nome AS cliente,
    p.nome AS produto,
    i.quantidade,
    i.preco_unitario_venda,
    (i.quantidade * i.preco_unitario_venda) AS subtotal,
    n.valor_total
FROM Cabecalho_Nota n
INNER JOIN Clientes c ON n.cliente_id = c.id_C
INNER JOIN Itens_Nota i ON n.id_N = i.nota_id
INNER JOIN Produto p ON i.produto_id = p.id_P;


