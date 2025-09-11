# Meu Gestor de Tarefas

> Uma aplicação web completa para gerenciamento de tarefas (To-Do List), construída com Java, Spring Boot e as melhores práticas de desenvolvimento, desde o design até o deploy automatizado com CI/CD.

---

##  STATUS
![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)

Este projeto está sendo desenvolvido como um estudo de caso prático para simular o dia a dia de um desenvolvedor Java, cobrindo todo o ciclo de vida de uma aplicação moderna.

## 🚀 FUNCIONALIDADES

-   [x] **Gerenciamento de Tarefas (CRUD):** Lógica de negócio completa para criar, ler, atualizar e deletar tarefas.
-   [x] **Modelo de Dados Robusto:** Tarefas com título, descrição, status, prioridade e datas de controle.
-   [x] **Segurança por Usuário:** Acesso e modificação de tarefas restritos apenas ao usuário proprietário.
-   [ ] **Autenticação de Usuários:** Sistema de registro e login com Spring Security.
-   [ ] **Frontend Interativo:** Interface construída com Thymeleaf para interação via navegador.
-   [ ] **Pipeline de CI/CD:** Automação de build, teste e deploy contínuo com Azure DevOps e Docker.

## 🛠️ TECNOLOGIAS UTILIZADAS

A arquitetura deste projeto foi pensada para utilizar um stack moderno e amplamente requisitado no mercado de trabalho:

-   **Backend:**
    -   Java 17
    -   Spring Boot 3.x
    -   Spring Web (API RESTful)
    -   Spring Data JPA (Persistência de dados)
    -   Spring Security (Autenticação e Autorização)
-   **Frontend:**
    -   Thymeleaf (Server-Side Rendering)
-   **Banco de Dados:**
    -   PostgreSQL
-   **Testes:**
    -   JUnit 5
    -   Mockito
    -   MockMvc
    -   AssertJ
-   **Build & Dependências:**
    -   Apache Maven
-   **DevOps:**
    -   Git & GitHub (Versionamento de código)
    -   Docker (Containerização)
    -   Azure DevOps (CI/CD)
    -   Render (Plataforma de Deploy)

## ⚙️ COMO EXECUTAR O PROJETO LOCALMENTE

Siga os passos abaixo para ter uma instância do projeto rodando na sua máquina.

### Pré-requisitos
Antes de começar, garanta que você tem as seguintes ferramentas instaladas:
-   [Java (JDK) 21+](https://adoptium.net/)
-   [Apache Maven 3.8+](https://maven.apache.org/download.cgi)
-   [Git](https://git-scm.com/downloads)
-   [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
    ```

2.  **Navegue até a pasta do projeto:**
    ```bash
    cd seu-repositorio
    ```

3.  **Inicie o banco de dados com Docker:**
    Este projeto utiliza Docker Compose para simplificar a inicialização do banco de dados PostgreSQL.
    ```bash
    docker-compose up -d
    ```
    *O `-d` faz com que ele rode em segundo plano (detached mode).*

4.  **Execute a aplicação Spring Boot:**
    Use o Maven para compilar e iniciar a aplicação.
    ```bash
    mvn spring-boot:run
    ```

5.  **Acesse a aplicação:**
    Após a inicialização, a aplicação estará disponível em `http://localhost:8080`.

## ✅ COMO RODAR OS TESTES

Para garantir a qualidade e a integridade do código, execute todos os testes automatizados com o seguinte comando Maven:
```bash
mvn clean test
