CREATE DATABASE IF NOT EXISTS SisVendas;
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
CREATE TRIGGER trg_after_insert_item_valor
AFTER INSERT ON Itens_Nota
FOR EACH ROW
BEGIN
    UPDATE Cabecalho_Nota
    SET valor_total = valor_total + (NEW.quantidade * NEW.preco_unitario_venda)
    WHERE id_N = NEW.nota_id;
END //

CREATE TRIGGER trg_after_delete_item_valor
AFTER DELETE ON Itens_Nota
FOR EACH ROW
BEGIN
    UPDATE Cabecalho_Nota
    SET valor_total = valor_total - (OLD.quantidade * OLD.preco_unitario_venda)
    WHERE id_N = OLD.nota_id;
END //

CREATE TRIGGER trg_after_update_item_valor
AFTER UPDATE ON Itens_Nota
FOR EACH ROW
BEGIN
    UPDATE Cabecalho_Nota
    SET valor_total = valor_total - (OLD.quantidade * OLD.preco_unitario_venda) + (NEW.quantidade * NEW.preco_unitario_venda)
    WHERE id_N = NEW.nota_id;
END //
DELIMITER ;

DELIMITER //
CREATE TRIGGER trg_after_insert_item_estoque
AFTER INSERT ON Itens_Nota
FOR EACH ROW
BEGIN
    DECLARE estoque_atual INT;
    SELECT estoque INTO estoque_atual FROM Produto WHERE id_P = NEW.produto_id;

    IF estoque_atual < NEW.quantidade THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Erro: Estoque insuficiente para o produto.';
    ELSE
        UPDATE Produto SET estoque = estoque - NEW.quantidade WHERE id_P = NEW.produto_id;
    END IF;
END //

CREATE TRIGGER trg_after_delete_item_estoque
AFTER DELETE ON Itens_Nota
FOR EACH ROW
BEGIN
    UPDATE Produto SET estoque = estoque + OLD.quantidade WHERE id_P = OLD.produto_id;
END //

CREATE TRIGGER trg_after_update_item_estoque
AFTER UPDATE ON Itens_Nota
FOR EACH ROW
BEGIN
    UPDATE Produto SET estoque = estoque + OLD.quantidade - NEW.quantidade WHERE id_P = NEW.produto_id;
END //
DELIMITER ;


INSERT INTO Clientes (nome, endereco, email) VALUES ('João da Silva', 'Rua A, 123', 'joao@email.com'), ('Maria Souza', 'Avenida B, 456', 'maria@email.com');
INSERT INTO Produto (nome, descricao, preco_venda, estoque) VALUES ('Mouse Gamer', 'Mouse RGB 16000 DPI', 150.00, 20), ('Teclado Mecânico', 'Teclado com switches blue', 350.00, 15), ('Monitor 24"', 'Monitor Full HD 75Hz', 950.00, 10);
