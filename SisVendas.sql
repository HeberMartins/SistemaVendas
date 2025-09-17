DROP DATABASE IF EXISTS SisVendas;
CREATE DATABASE SisVendas;
USE SisVendas;

CREATE TABLE Clientes(
    id_C INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(90) NOT NULL,
    endereco VARCHAR(180) NOT NULL,
    email VARCHAR(200) NOT NULL
);

CREATE TABLE Produto(
    id_P INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(90),
    descricao VARCHAR(200),
    preco_venda DECIMAL(10, 2),
    estoque INT
);

CREATE TABLE Cabecalho_Nota(
    id_N INT PRIMARY KEY AUTO_INCREMENT,
    data_venda DATE,
    cliente_id INT,
    valor_total DECIMAL(10, 2) DEFAULT 0.00,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id_C)
);

CREATE TABLE Itens_Nota(
    id_I INT PRIMARY KEY AUTO_INCREMENT,
    nota_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario_venda DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (nota_id) REFERENCES Cabecalho_Nota(id_N) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES Produto(id_P)
);

DELIMITER //

CREATE TRIGGER trg_after_insert_item
AFTER INSERT ON Itens_Nota
FOR EACH ROW
BEGIN
    UPDATE Cabecalho_Nota
    SET valor_total = valor_total + (NEW.quantidade * NEW.preco_unitario_venda)
    WHERE id_N = NEW.nota_id;
END //

CREATE TRIGGER trg_after_delete_item
AFTER DELETE ON Itens_Nota
FOR EACH ROW
BEGIN
    UPDATE Cabecalho_Nota
    SET valor_total = valor_total - (OLD.quantidade * OLD.preco_unitario_venda)
    WHERE id_N = OLD.nota_id;
END //

CREATE TRIGGER trg_after_update_item
AFTER UPDATE ON Itens_Nota
FOR EACH ROW
BEGIN
    UPDATE Cabecalho_Nota
    SET valor_total = valor_total - (OLD.quantidade * OLD.preco_unitario_venda) + (NEW.quantidade * NEW.preco_unitario_venda)
    WHERE id_N = NEW.nota_id;
END //

DELIMITER ;

INSERT INTO Clientes (nome, endereco, email) VALUES ('João da Silva', 'Rua A, 123', 'joao@email.com');
INSERT INTO Produto (nome, descricao, preco_venda, estoque) VALUES ('Mouse Gamer', 'Mouse RGB 16000 DPI', 150.00, 10);
INSERT INTO Produto (nome, descricao, preco_venda, estoque) VALUES ('Teclado Mecânico', 'Teclado com switches blue', 350.00, 5);

INSERT INTO Cabecalho_Nota (data_venda, cliente_id) VALUES (CURDATE(), 1);
SET @id_nova_nota = LAST_INSERT_ID();

INSERT INTO Itens_Nota (nota_id, produto_id, quantidade, preco_unitario_venda) VALUES (@id_nova_nota, 1, 2, 150.00);
INSERT INTO Itens_Nota (nota_id, produto_id, quantidade, preco_unitario_venda) VALUES (@id_nova_nota, 2, 1, 350.00);

SELECT * FROM Cabecalho_Nota WHERE id_N = @id_nova_nota;

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
INNER JOIN Produto p ON i.produto_id = p.id_P
WHERE n.id_N = @id_nova_nota;
