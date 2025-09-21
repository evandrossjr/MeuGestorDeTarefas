# Meu Gestor de Tarefas

> Uma aplicação web completa para gerenciamento de tarefas (To-Do List), construída com Java, Spring Boot e as melhores práticas de desenvolvimento, desde o design até o deploy automatizado com CI/CD.

---

##  STATUS
![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)

Este projeto está sendo desenvolvido como um estudo de caso prático para simular o dia a dia de um desenvolvedor Java, cobrindo todo o ciclo de vida de uma aplicação moderna.

## 🚀 FUNCIONALIDADES

-   [x] **Gerenciamento de Tarefas (CRUD):** Lógica de negócio completa para criar, ler, atualizar, finalizar e deletar tarefas.
-   [x] **Modelo de Dados Robusto:** Tarefas com título, descrição, status, prioridade e datas de controle.
-   [x] **Segurança por Usuário:** Acesso e modificação de tarefas restritos apenas ao usuário proprietário.
-   [x] **Frontend Interativo Básico:** Interface com Thymeleaf para listar e adicionar tarefas.
-   [x] **Autenticação de Usuários:** Sistema de registro e login com Spring Security.
-   [ ] **Pipeline de CI/CD:** Automação de build, teste e deploy contínuo.

## 🛠️ TECNOLOGIAS UTILIZADAS

-   **Backend:**
    -   Java 21
    -   Spring Boot 3.5.5
    -   Spring Web (API RESTful & MVC)
    -   Spring Data JPA (Persistência)
    -   Spring Security (Autenticação e Autorização)
-   **Frontend:**
    -   Thymeleaf (Server-Side Rendering)
-   **Banco de Dados:**
    -   PostgreSQL
-   **Testes:**
    -   JUnit 5, Mockito, MockMvc, AssertJ
-   **Build & Dependências:**
    -   Apache Maven
-   **Ambiente de Desenvolvimento:**
    -   Docker & Docker Compose
-   **Ferramentas de Gestão:**
    -   Git & GitHub (Versionamento)
    -   Azure Boards (Project Management)

## ⚙️ COMO EXECUTAR O PROJETO LOCALMENTE

Siga os passos abaixo para ter uma instância completa do projeto rodando na sua máquina.

### Pré-requisitos
Antes de começar, garanta que você tem as seguintes ferramentas instaladas:
-   [Java (JDK) 21+](https://adoptium.net/)
-   [Apache Maven 3.8+](https://maven.apache.org/download.cgi)
-   [Git](https://git-scm.com/downloads)
-   [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### Arquivos de Configuração

O projeto utiliza os seguintes arquivos para configurar o ambiente local:

-   `docker-compose.yml`: Define e orquestra os serviços do **PostgreSQL** e do **pgAdmin** (ferramenta de gerenciamento do banco via web).
-   `src/main/resources/application.properties`: Ativa o perfil de desenvolvimento (`dev`).
-   `src/main/resources/application-dev.properties`: Contém as credenciais e a URL para conectar a aplicação Spring ao container do PostgreSQL.
-   `src/main/resources/data.sql`: Popula o banco de dados com um usuário padrão na inicialização para facilitar os testes.

### Execução (Passo a Passo)

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/MeuGestorDeTarefas.git](https://github.com/seu-usuario/MeuGestorDeTarefas.git)
    ```

2.  **Navegue até a pasta do projeto:**
    ```bash
    cd MeuGestorDeTarefas
    ```

3.  **Inicie os serviços com Docker:**
    Este comando irá iniciar o container do PostgreSQL e do pgAdmin em segundo plano.
    ```bash
    docker compose up -d
    ```

4.  **Execute a aplicação Spring Boot:**
    Você pode fazer isso de duas formas:
    -   **Pela IDE:** Abra o projeto no IntelliJ (ou outra IDE) e execute a classe principal `MeuGestorDeTarefasApplication.java`.
    -   **Pelo Terminal:** Use o Maven para compilar e iniciar a aplicação.
        ```bash
        mvn spring-boot:run
        ```

### Acessando os Serviços

Após a inicialização, os seguintes serviços estarão disponíveis no seu navegador:

-   **Aplicação Principal:**
    -   URL: `http://localhost:8080`
    -   Descrição: A interface web da sua lista de tarefas.

-   **pgAdmin (Gerenciador do Banco de Dados):**
    -   URL: `http://localhost:5050`
    -   **Login:** `me@example.com`
    -   **Senha:** `1234567`
    -   *Observação: Dentro do pgAdmin, você precisará configurar uma nova conexão de servidor usando `pg-docker` como Host e as credenciais do banco para visualizar as tabelas.*

## ✅ COMO RODAR OS TESTES

Para garantir a qualidade e a integridade do código, execute todos os testes automatizados com o seguinte comando Maven:
```bash
mvn clean test