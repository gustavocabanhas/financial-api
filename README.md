# Financial API

Este projeto é uma API REST desenvolvida em Java com Spring Boot para gerenciamento de transações financeiras. A ideia foi criar uma aplicação do zero para praticar conceitos essenciais de back-end, organização de código e construção de APIs seguindo boas práticas de mercado.

A aplicação permite registrar e consultar transações, servindo como base para sistemas financeiros maiores ou para evolução com novas funcionalidades no futuro.

## Tecnologias Utilizadas

O projeto foi construído com as seguintes tecnologias:

- **Java 21 (LTS)**
- **Spring Boot 3.x**
- **Spring Web** – criação dos endpoints REST
- **Spring Data JPA** – acesso e persistência de dados
- **H2 Database** – banco em memória para testes e desenvolvimento rápido
- **Lombok** – redução de código repetitivo
- **Hibernate Validator** – validação dos dados recebidos pela API
- **Maven** – gerenciamento de dependências e build

## Estrutura do Projeto

A aplicação foi organizada em camadas para manter o código limpo, separado e fácil de evoluir.

### Controller

Responsável por receber as requisições HTTP, validar entradas básicas e retornar as respostas da API.

### Service

Camada onde fica a regra de negócio. É nela que acontece o processamento principal das operações financeiras.

### Repository

Responsável pela comunicação com o banco de dados através do Spring Data JPA.

### Model / Entity

Representa as entidades do sistema e o mapeamento das tabelas no banco.

### DTO

Objetos utilizados para troca de dados entre cliente e servidor, evitando expor diretamente as entidades internas.

## Como Executar o Projeto

### Pré-requisitos

Antes de iniciar, tenha instalado na máquina:

- Java 21
- Maven

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/SEU-USUARIO/financial-api.git```

2. Acesse a pasta do projeto:
cd financial-api

3. Execute a aplicação:
mvn spring-boot:run

4. A API estará disponível em:
http://localhost:8080

### Objetivo do Projeto

Mais do que apenas criar endpoints, este projeto foi desenvolvido para reforçar fundamentos importantes como:

**construção de APIs REST**
**arquitetura em camadas**
**validação de dados**
**organização de código**
**integração com banco de dados**
**boas práticas no ecossistema Spring**

Projeto desenvolvido como parte dos estudos práticos em Java e desenvolvimento back-end.
