-- src/main/resources/data.sql


-- Insere um usuário padrão. Como o ID é auto-incremento, o primeiro usuário inserido terá o ID 1.
INSERT INTO tb_users (name, email, password_hash, role) VALUES ('Teste User', 'testeuser@email.com', 'senha_super_criptografada_exemplo', 'REGULAR');