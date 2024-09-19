# Sistema de Gerenciamento de Usuários - CRUD em Java
Este projeto implementa um sistema de gerenciamento de usuários em Java, utilizando um CRUD (Create, Read, Update, Delete) para realizar operações básicas no console. As senhas dos usuários são criptografadas antes de serem salvas no banco de dados, e o sistema permite a redefinição de senhas por meio de um e-mail com código de validação.

## Funcionalidades

Criação automática do banco de dados: O sistema cria o banco de dados e a tabela automaticamente, caso ela não exista.

Cadastro de Usuário: Permite criar um novo usuário com nome, e-mail e senha.

Listar Usuários: Exibe todos os usuários cadastrados.

Editar Usuário: Permite atualizar senha.

Excluir Usuário: Remove usuários do sistema.

Criptografia de Senha: As senhas são criptografadas antes de serem armazenadas no banco de dados para garantir a segurança.

Redefinição de Senha: O sistema envia um e-mail com um código de validação ao usuário para que ele possa redefinir sua senha.

## Fluxo de Redefinição de Senha
1. O usuário solicita a redefinição de senha informando o seu e-mail.
    
2. O sistema gera um código de validação e envia um e-mail para o usuário.

3. O usuário insere o código no sistema para validar a solicitação.

4. Se o código for válido, o sistema permite que o usuário defina uma nova senha, que será criptografada e salva no banco de dados.

## Tecnologias Utilizadas
Java: Linguagem de programação principal.

JDBC: Para interação com o banco de dados.

MySQL: Banco de dados utilizado.

JavaMail API: Para envio de e-mails.

BCrypt: Para criptografia de senhas.

## Contato
entre em contato:

E-mail: allangabrielmoreira010@gmail.com

LinkedIn: [Meu LinkedIn](https://www.linkedin.com/in/allan-gabriel-moreira-da-silva-9090a9271/)
