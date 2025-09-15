-- src/main/resources/data.sql

-- Deleta todos os usuários existentes para garantir um começo limpo (opcional, mas bom para dev)
DELETE FROM tb_users;

-- Insere um usuário padrão. Como o ID é auto-incremento, o primeiro usuário inserido terá o ID 1.
INSERT INTO tb_users (name, email, password_hash, role) VALUES ('Evandro Sacramento', 'evandro@email.com', 'senha_super_criptografada_exemplo', 'REGULAR');