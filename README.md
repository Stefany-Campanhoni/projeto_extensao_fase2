# Projeto de Extensão Fase 2

## Descrição

Este é um projeto de extensão da faculdade, que consiste em uma API REST para um programa de mentoria. A plataforma permite que mentores se cadastrem, e que usuários possam buscar por mentores de acordo com sua especialidade e cidade.

## Features

- Cadastro e login de mentores
- Busca de mentores por especialidade e cidade
- CRUD de mentores (apenas para usuários autenticados)
- Listagem de cidades e especialidades

## Segurança

A segurança da aplicação é feita utilizando Spring Security e JWT (JSON Web Tokens).

- **Autenticação:** A autenticação é feita através do endpoint `/mentors/login`. Em caso de sucesso, um token JWT é retornado, que deve ser utilizado no header `Authorization` das requisições subsequentes.
- **Autorização:** As rotas de `PUT` e `DELETE` para mentores são protegidas e exigem que o usuário esteja autenticado e tenha o papel de `ADMIN` ou `USER`.
- **Criptografia de Senhas:** As senhas dos usuários são armazenadas de forma segura utilizando o `BCryptPasswordEncoder`.
- **CORS:** A API é configurada para aceitar requisições de qualquer origem.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Security
- JWT
- PostgreSQL
- Flyway
- Lombok

## Como Executar

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
```
2. Configure o banco de dados no arquivo `src/main/resources/application.yml`
3. Execute a aplicação:
```bash
./gradlew bootRun
```

A API estará disponível em `http://localhost:8080`.

## Endpoints da API

A seguir estão os endpoints da API.

### Autenticação

- `POST /mentors/login`: Realiza o login do mentor e retorna um token JWT.

### Mentores

- `POST /mentors/register`: Registra um novo mentor.
- `GET /mentors`: Retorna todos os mentores.
- `GET /mentors/filter`: Filtra mentores por especialidade e/ou cidade.
- `PUT /mentors/{id}`: Atualiza um mentor (requer autenticação).
- `DELETE /mentors/{id}`: Deleta um mentor (requer autenticação).

### Cidades

- `GET /cities`: Retorna todas as cidades.

### Especialidades

- `GET /specialties`: Retorna todas as especialidades.
